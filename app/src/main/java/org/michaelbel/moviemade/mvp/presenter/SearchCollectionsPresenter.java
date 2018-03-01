package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpSearchView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.SEARCH;
import org.michaelbel.moviemade.rest.response.CollectionResponse;
import org.michaelbel.moviemade.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SearchCollectionsPresenter extends MvpPresenter<MvpSearchView> {

    public int page = 1;
    public int totalPages;
    public int totalResults;
    public boolean isLoading = false;
    public boolean isLastPage = false;

    private String currentQuery;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public void search(String query) {
        currentQuery = query;
        getViewState().searchStart();

        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        SEARCH service = ApiFactory.createService2(SEARCH.class);
        Observable<CollectionResponse> observable = service.searchCollections(Url.TMDB_API_KEY, Url.en_US, query, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<CollectionResponse>() {
            @Override
            public void onNext(CollectionResponse response) {
                totalPages = response.totalPages;
                totalResults = response.totalResults;
                List<TmdbObject> results = new ArrayList<>(response.collections);
                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
                    return;
                }
                getViewState().showResults(results, true);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
            }

            @Override
            public void onComplete() {}
        }));
    }

    public void loadNextPage() {
        SEARCH service = ApiFactory.createService2(SEARCH.class);
        Observable<CollectionResponse> observable = service.searchCollections(Url.TMDB_API_KEY, Url.en_US, currentQuery, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<CollectionResponse>() {
            @Override
            public void onNext(CollectionResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.collections);
                getViewState().showResults(results, false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        }));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}