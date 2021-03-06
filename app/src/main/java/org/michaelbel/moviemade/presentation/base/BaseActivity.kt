package org.michaelbel.moviemade.presentation.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.Review
import org.michaelbel.moviemade.core.utils.EXTRA_ACCOUNT_ID
import org.michaelbel.moviemade.core.utils.EXTRA_KEYWORD
import org.michaelbel.moviemade.core.utils.EXTRA_MOVIE
import org.michaelbel.moviemade.core.utils.EXTRA_REVIEW
import org.michaelbel.moviemade.presentation.features.favorites.FavoriteActivity
import org.michaelbel.moviemade.presentation.features.keywords.activity.KeywordActivity
import org.michaelbel.moviemade.presentation.features.keywords.activity.KeywordsActivity
import org.michaelbel.moviemade.presentation.features.movie.MovieActivity
import org.michaelbel.moviemade.presentation.features.recommendations.RcmdMoviesActivity
import org.michaelbel.moviemade.presentation.features.reviews.activity.ReviewActivity
import org.michaelbel.moviemade.presentation.features.reviews.activity.ReviewsActivity
import org.michaelbel.moviemade.presentation.features.similar.SimilarMoviesActivity
import org.michaelbel.moviemade.presentation.features.trailers.TrailersActivity
import org.michaelbel.moviemade.presentation.features.watchlist.WatchlistActivity

abstract class BaseActivity: AppCompatActivity(), MediaDelegate {

    override fun startMovie(movie: Movie) {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    override fun startTrailers(movie: Movie) {
        val intent = Intent(this, TrailersActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    override fun startReview(review: Review, movie: Movie) {
        val intent = Intent(this, ReviewActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        intent.putExtra(EXTRA_REVIEW, review)
        startActivity(intent)
    }

    override fun startReviews(movie: Movie) {
        val intent = Intent(this, ReviewsActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    override fun startFavorites(accountId: Int) {
        val intent = Intent(this, FavoriteActivity::class.java)
        intent.putExtra(EXTRA_ACCOUNT_ID, accountId)
        startActivity(intent)
    }

    override fun startWatchlist(accountId: Int) {
        val intent = Intent(this, WatchlistActivity::class.java)
        intent.putExtra(EXTRA_ACCOUNT_ID, accountId)
        startActivity(intent)
    }

    override fun startKeywords(movie: Movie) {
        val intent = Intent(this, KeywordsActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    override fun startKeyword(keyword: Keyword) {
        val intent = Intent(this, KeywordActivity::class.java)
        intent.putExtra(EXTRA_KEYWORD, keyword)
        startActivity(intent)
    }

    override fun startSimilarMovies(movie: Movie) {
        val intent = Intent(this, SimilarMoviesActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    override fun startRcmdsMovies(movie: Movie) {
        val intent = Intent(this, RcmdMoviesActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        startActivity(intent)
    }
}