package com.example.recipe.data.network

import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * Интерсептор для кэширования запроса
 */
class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val cacheControl: CacheControl = CacheControl.Builder()
            .maxAge(5, TimeUnit.DAYS)
            .build()
        response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
        Log.e("NetworkInterceptor", "kk")
        return response
    }
}