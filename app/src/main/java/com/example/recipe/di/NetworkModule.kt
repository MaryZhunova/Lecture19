package com.example.recipe.di

import dagger.Module
import dagger.Provides
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class NetworkModule(val url: String) {

    @Provides
    fun providesUrl(): HttpUrl {
        return url.toHttpUrl()
    }

    @Provides
    fun providesHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .cookieJar(CookieJar.NO_COOKIES)
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .build()
    }
}