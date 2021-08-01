package com.korilin.sunnyweather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.korilin.sunnyweather.SunnyWeatherApplication
import com.korilin.sunnyweather.logic.model.PlaceResponse

object PlaceDao {

    fun savePlace(place: PlaceResponse.Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace() = Gson().fromJson(
        sharedPreferences().getString("place", ""),
        PlaceResponse.Place::class.java
    )

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SunnyWeatherApplication.getPlaceSharePreference()
}