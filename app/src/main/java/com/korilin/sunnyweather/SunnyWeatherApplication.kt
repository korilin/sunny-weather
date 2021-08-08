package com.korilin.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences


class SunnyWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "w8COnLuEMupFZdps"

        private const val placeSaveName = "sunny_weather_place"

        fun getPlaceSharePreference(): SharedPreferences = context.getSharedPreferences(placeSaveName, Context.MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}