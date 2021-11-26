package com.example.recipe.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * Интерсептор для получения данных из кэша при отсутствии интернет-соединения
 */
class OfflineInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!isNetworkConnected(context)) {
            val cacheControl: CacheControl = CacheControl.Builder()
                .maxStale(30, TimeUnit.DAYS)
                .build()
            request = request.newBuilder()
                .cacheControl(cacheControl)
                .build()
        }
        Log.e("OfflineInterceptor", "kk")
        return chain.proceed(request)
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}