package org.michaelbel.moviemade.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpResultsView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.MOVIES;
import org.michaelbel.moviemade.rest.response.ReviewResponse;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ReviewsMoviePresenter extends MvpPresenter<MvpResultsView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    private int movieId;

    private Disposable disposable1;
    private Disposable disposable2;

    public void loadReviews(int movieId) {
        this.movieId = movieId;

        if (movieId == 0) {
            getViewState().showError(EmptyViewMode.MODE_NO_REVIEWS);
            return;
        }

        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<ReviewResponse> observable = service.getReviews(movieId, Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable1 = observable.subscribeWith(new DisposableObserver<ReviewResponse>() {
            @Override
            public void onNext(ReviewResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.reviews);
                getViewState().showResults(results);
                totalPages = response.totalPages;
                page++;
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_REVIEWS);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }

    public void loadResults() {
        MOVIES service = ApiFactory.createService(MOVIES.class);
        Observable<ReviewResponse> observable = service.getReviews(movieId, Url.TMDB_API_KEY, Url.en_US, page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposable2 = observable.subscribeWith(new DisposableObserver<ReviewResponse>() {
            @Override
            public void onNext(ReviewResponse response) {
                List<TmdbObject> results = new ArrayList<>(response.reviews);
                getViewState().showResults(results);
                loading = false;
                page++;
            }

            @Override
            public void onError(Throwable e) {
                loadingLocked = true;
                loading = false;
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable1.dispose();
                loading = true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable1.dispose();
        disposable2.dispose();
    }
}