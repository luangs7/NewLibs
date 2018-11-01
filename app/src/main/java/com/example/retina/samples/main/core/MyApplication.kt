package com.example.retina.samples.main.core

import android.app.Application
import com.example.retina.samples.koin.di.remoteModule
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {



    override fun onCreate() {
        super.onCreate()

        startKoin(listOf(remoteModule))

    }
}