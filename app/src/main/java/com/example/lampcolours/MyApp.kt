package com.example.lampcolours

import android.app.Application
import com.example.lampcolours.di.dataModule
import com.example.lampcolours.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(dataModule, viewModelModule)
        }
    }

}