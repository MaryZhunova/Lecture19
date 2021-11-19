package com.example.recipe

import android.app.Application
import android.content.Context
import com.example.recipe.di.components.AppComponent
import com.example.recipe.di.components.DaggerAppComponent
import com.example.recipe.di.modules.NetworkModule

class NetworkApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule(this))
            .build()
    }

    companion object {
        fun appComponent(context: Context): AppComponent {
            val con = context.applicationContext as NetworkApp
            return con.appComponent
        }
    }
}