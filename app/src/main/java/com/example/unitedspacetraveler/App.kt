package com.example.unitedspacetraveler

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.example.unitedspacetraveler.di.koinModules
import com.example.unitedspacetraveler.di.viewModelModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(arrayListOf(koinModules, viewModelModule))
        }
    }
}