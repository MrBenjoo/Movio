package com.example.movio

import android.app.Application
import com.example.movio.di.initKoin

class MovioApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}