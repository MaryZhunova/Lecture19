package com.example.recipe

import android.app.Application
import android.content.Context
import com.example.recipe.data.api.Constants
import com.example.recipe.di.AppComponent
import com.example.recipe.di.ConfigModule
import com.example.recipe.di.DaggerAppComponent
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class NetworkApp : Application() {

private lateinit var appComponent: AppComponent

private val mUrl: HttpUrl = Constants.url.toHttpUrl()
private val httpClient = OkHttpClient.Builder()
        .readTimeout(3, TimeUnit.SECONDS)
        .writeTimeout(3, TimeUnit.SECONDS)
        .cookieJar(CookieJar.NO_COOKIES)
        .addNetworkInterceptor(HttpLoggingInterceptor())
        .build()

        override fun onCreate() {
                super.onCreate()
                appComponent = DaggerAppComponent.builder()
                .configModule(ConfigModule(httpClient, mUrl))
                .build()
        }

        companion object {
                fun appComponent(context: Context): AppComponent {
                        val con = context.applicationContext as NetworkApp
                        return con.appComponent
                }
        }


}