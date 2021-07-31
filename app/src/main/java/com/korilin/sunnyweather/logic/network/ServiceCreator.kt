package com.korilin.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>) : T = retrofit.create(serviceClass)

    // kotlin reified, use with inline
    // https://droidyue.com/blog/2019/07/28/kotlin-reified-generics/
    inline fun <reified T> create(): T = create(T::class.java)
}