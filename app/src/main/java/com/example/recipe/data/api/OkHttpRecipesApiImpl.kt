package com.example.recipe.data.api

import com.example.recipe.data.model.Recipe
import com.example.recipe.data.model.RecipeResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.lang.Exception


/**
 * Реализация [RecipesApi] с помощью [OkHttpClient]
 */
class OkHttpRecipesApiImpl(private val httpClient: OkHttpClient): RecipesApi {

    override fun get(query: String): List<Recipe> {

        val url: HttpUrl = Constants.url.toHttpUrl()
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
//            Log.e("error", response.code.toString())
            throw Exception()
        }
        return res.hits.map { hit -> hit.recipe }
    }
}
