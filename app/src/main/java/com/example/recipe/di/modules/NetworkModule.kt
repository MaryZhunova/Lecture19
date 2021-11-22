package com.example.recipe.di.modules

import android.content.Context
import android.util.Log
import com.example.recipe.data.dao.RecipesDao
import com.example.recipe.data.dao.db.RecipesDatabase
import com.example.recipe.data.network.RetrofitService
import com.example.recipe.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Request

@Module(includes = [ViewModelModule::class])
class NetworkModule(val context: Context) {

    @Provides
    @Singleton
    fun providesRetrofitService(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache())
            .addInterceptor(httpLoggingInterceptor())
            .addNetworkInterceptor(onlineInterceptor())
            .addInterceptor(offlineInterceptor())
            .addInterceptor(retryInterceptor())
            .build()
    }

    private fun retryInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            while (response.code == 429) {
                // retry the request
                response.close()
                response = chain.proceed(request)
            }
            return@Interceptor response
        }
    }

    private fun cache(): Cache {
        return Cache(File(context.cacheDir, "cache"), (5 * 1024 * 1024).toLong())
    }

    private fun offlineInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!isNetworkConnected()) {
                val cacheControl: CacheControl = CacheControl.Builder()
                    .maxStale(30, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }

    private fun onlineInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cacheControl: CacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.DAYS)
                .build()
            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(
                "httpLoggingInterceptor",
                "log: http log: $message"
            )
        }
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    @Provides
    @Singleton
    @Named("dao")
    fun providesDao(): RecipesDao {
        return RecipesDatabase.getInstance(context).recipesDao
    }
}