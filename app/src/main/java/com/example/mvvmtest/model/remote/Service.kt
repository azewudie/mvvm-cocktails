package com.example.mvvmtest.model.remote

import com.example.mvvmtest.model.CocktailDetails
import com.example.mvvmtest.model.CocktailSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 1-Retrofit dependencies
 * 2-Create Retrofit Interface (Service)
 * 3-In the service Create the HTTP  verbs
 * 4- Create the Retrofit object.(Singleton)
 * base-> https://www.thecocktaildb.com/
 * end-> api/json/v1/1/search.php
 *query ->?s=margarita
 * www.thecocktaildb.com/api/json/v1/1/lookup.php?i=11007
 */
interface  Service{
    @GET(ENDPOINT_SEARCH)
    fun queryCocktailByName(
        @Query(ARG_SEARCH) searchInput : String
    ): Call<CocktailSearch>

    @GET(ENDPOINT_DETAIL)
    fun queryCocktailDetails(
        @Query(ARG_DETAIL) cocktailId : String
    ): Call<CocktailDetails>
}