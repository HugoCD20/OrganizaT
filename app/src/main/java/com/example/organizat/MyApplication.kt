package com.example.organizat

import android.app.Application
import android.content.Context

class MyApplication: Application() {
    companion object{
        private lateinit var instace: MyApplication

        fun getAppContext(): Context {
            return instace.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instace=this
    }
}