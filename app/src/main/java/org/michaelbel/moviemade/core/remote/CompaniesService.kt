package org.michaelbel.moviemade.core.remote

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Company
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CompaniesService {

    @GET("company/{company_id}?")
    fun getDetails(
        @Path("company_id") id: Int,
        @Query("api_key") apiKey: String
    ): Observable<Company>

    // getAlternativeNames

    // getImages
}