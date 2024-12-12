package com.example.nbabrowser

import android.app.Application
import com.example.nbabrowser.data.AppContainer
import com.example.nbabrowser.data.DefaultAppContainer

class NBABrowserApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}