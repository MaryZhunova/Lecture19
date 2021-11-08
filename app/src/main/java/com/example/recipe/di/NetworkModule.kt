package com.example.recipe.di

import dagger.Module
import dagger.Provides
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule(val url: String) {

    @Provides
    @Named("httpUrl")
    fun providesUrl(): HttpUrl {
        return url.toHttpUrl()
    }

    @Provides
    @Named("httpClient")
    fun providesHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .cookieJar(CookieJar.NO_COOKIES)
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .build()
    }
}