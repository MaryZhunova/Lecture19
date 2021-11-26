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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Named
import javax.inject.Singleton
import com.example.recipe.data.network.NetworkInterceptor
import com.example.recipe.data.network.OfflineInterceptor
import com.example.recipe.data.network.RetryInterceptor

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
            .addNetworkInterceptor(NetworkInterceptor())
            .addInterceptor(OfflineInterceptor(context))
            .addInterceptor(RetryInterceptor())
            .build()
    }

    private fun cache(): Cache {
        return Cache(File(context.cacheDir, "cache"), (5 * 1024 * 1024).toLong())
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


    @Provides
    @Singleton
    @Named("dao")
    fun providesDao(): RecipesDao {
        return RecipesDatabase.getInstance(context).recipesDao
    }
}