package com.example.recipe.data.network

import com.example.recipe.data.network.models.RecipeResponse
import com.example.recipe.utils.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Интерфейс для получения данных с API
 */
interface RetrofitService {

    /**
     * Получить список рецептов, соответствующих параметрам запроса
     *
     * @param query параметр для запроса данных
     * @param cont параметр, указывающий на следующую страницу
     * @param apiKey апи ключ
     * @param apiId апи id
     * @return Observable<RecipeResponse> полученные данные
     */
    @GET("v2?type=public")
    fun get(
        @Query("q") query: String,
        @Query("_cont", encoded = true) cont: String? = null,
        @Query("app_key") apiKey: String = Constants.KEY,
        @Query("app_id") apiId: String = Constants.ID
    ): Observable<RecipeResponse>
}