package com.example.recipe.data.api

import android.util.Log
import com.example.recipe.data.model.Recipe
import com.example.recipe.data.model.RecipeResponse
import com.google.gson.Gson
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.Exception
import java.util.concurrent.TimeUnit


/**
 * Реализация [RecipesApi] с помощью [OkHttpClient]
 */
class OkHttpRecipesApiImpl(): RecipesApi {

    override fun get(query: String): List<Recipe> {
        //Создать  OkHttp клиент
        val httpClient = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .cookieJar(CookieJar.NO_COOKIES)
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .build()

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
            Log.d("error body", "tt")
            throw Exception()
        }
        return res.hits.map { hit -> hit.recipe }
    }
}
