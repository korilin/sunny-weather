package com.korilin.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


class SunnyWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "w8COnLuEMupFZdps"

        const val placeSaveName = "sunny_weather_place"

        fun getPlaceSharePreference() = context.getSharedPreferences(placeSaveName, Context.MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}