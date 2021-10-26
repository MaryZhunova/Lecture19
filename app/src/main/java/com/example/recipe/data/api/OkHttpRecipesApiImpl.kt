package com.example.recipe.data.api

import android.util.Log
import com.example.recipe.models.data.RecipeR
import com.example.recipe.models.data.RecipeResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.HttpUrl
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named


/**
 * Реализация [RecipesApi] с помощью [OkHttpClient]
 */


class OkHttpRecipesApiImpl @Inject constructor(@Named("OkHttpClient") private val httpClient: OkHttpClient, @Named("HttpUrl") private val url: HttpUrl): RecipesApi {

    override fun get(query: String): List<RecipeR> {

        //Создать url с параметрами (тип, ключевое слово, айди и ключ)
        val httpBuilder: HttpUrl.Builder = url.newBuilder()
        httpBuilder.addQueryParameter("type", "public")
        httpBuilder.addQueryParameter("q", query)
        httpBuilder.addQueryParameter("app_id", Constants.id)
        httpBuilder.addQueryParameter("app_key", Constants.key)

        //Создать get запрос
        val request = Request.Builder()
            .url(httpBuilder.build())
            .get()
            .build()

        //Отправить get запрос
        val response = httpClient.newCall(request).execute()
        val body = response.body?.string()
        //Преобразовать полученный результат в модель Recipe
        val res = Gson().fromJson(body, RecipeResponse::class.java)
        if (res.hits.isEmpty()) {
            Log.d("error body", "tt")
            throw Exception()
        }
        return res.hits.map { hit -> hit.recipe }
    }
}
