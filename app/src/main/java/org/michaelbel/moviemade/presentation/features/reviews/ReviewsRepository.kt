package org.michaelbel.moviemade.presentation.features.reviews

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.ReviewsResponse
import org.michaelbel.moviemade.core.remote.MoviesService
import java.util.*

class ReviewsRepository internal constructor(
        private val service: MoviesService
): ReviewsContract.Repository {

    // fixme add paging
    override fun getReviews(movieId: Int): Observable<ReviewsResponse> =
        service.getReviews(movieId, TMDB_API_KEY, Locale.getDefault().language, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}