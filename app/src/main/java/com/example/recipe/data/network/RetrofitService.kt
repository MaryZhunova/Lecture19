package com.example.recipe.data.network

import com.example.recipe.models.data.api.RecipeResponse
import com.example.recipe.utils.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("v2?type=public")
    fun get(
        @Query("q") query: String,
        @Query("app_id") apiId: String = Constants.ID,
        @Query("app_key") apiKey: String = Constants.KEY
    ): Observable<RecipeResponse>
}