package com.example.recipe

import android.app.Application
import android.content.Context
import com.example.recipe.utils.Constants
import com.example.recipe.di.AppComponent
import com.example.recipe.di.DaggerAppComponent
import com.example.recipe.di.NetworkModule

class NetworkApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule(Constants.URL, this))
            .build()
    }

    companion object {
        fun appComponent(context: Context): AppComponent {
            val con = context.applicationContext as NetworkApp
            return con.appComponent
        }
    }
}