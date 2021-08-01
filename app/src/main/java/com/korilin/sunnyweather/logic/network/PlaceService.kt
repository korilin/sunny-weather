package com.korilin.sunnyweather.logic.network

import com.korilin.sunnyweather.SunnyWeatherApplication
import com.korilin.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String) : Call<PlaceResponse>
}