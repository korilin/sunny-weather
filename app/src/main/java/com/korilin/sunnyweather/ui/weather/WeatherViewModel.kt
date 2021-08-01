package com.korilin.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.korilin.sunnyweather.logic.Repository
import com.korilin.sunnyweather.logic.model.PlaceResponse

class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<PlaceResponse.Location>()

    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        Repository.refreshWeather(locationLng, locationLat)
    }

    fun refreshWeather(lng: String, lat:String) {
        locationLiveData.value = PlaceResponse.Location(lng, lat)
    }
}