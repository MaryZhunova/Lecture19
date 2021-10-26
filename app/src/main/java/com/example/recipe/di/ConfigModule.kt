package com.example.recipe.di

import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import javax.inject.Named

@Module
class ConfigModule(httpClient: OkHttpClient, url: HttpUrl) {
    private val httpClientCM = httpClient
    private val urlCM = url


    @Provides
    @Named("HttpUrl")
    fun url(): HttpUrl {
        return urlCM
    }

    @Provides
    @Named("OkHttpClient")
    fun httpClient(): OkHttpClient {
        return httpClientCM
    }

}