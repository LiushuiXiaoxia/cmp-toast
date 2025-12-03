package com.example.cmp_toast

import android.app.Application

fun getAppContext(): AppContext = AppContext.instance

class AppContext : Application() {

    companion object {
        lateinit var instance: AppContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}