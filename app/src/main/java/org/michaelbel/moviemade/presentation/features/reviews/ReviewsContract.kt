package org.michaelbel.moviemade.presentation.features.reviews

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Review
import org.michaelbel.moviemade.core.entity.ReviewsResponse
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.presentation.base.BaseContract

interface ReviewsContract {

    interface View {
        fun setReviews(reviews: List<Review>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun getReviews(movieId: Int)
    }

    interface Repository {
        fun getReviews(movieId: Int): Observable<ReviewsResponse>
    }
}