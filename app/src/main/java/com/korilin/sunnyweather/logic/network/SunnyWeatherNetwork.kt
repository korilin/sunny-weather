package com.korilin.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {

    // 动态创建 PlaceService
    private val placeService = ServiceCreator.create<PlaceService>()

    /**
     * 搜索城市数据，利用 [Call.await] 的 extension function 挂起当前协程等待 http 请求的响应
     *
     * @see Call.await
     */
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    /**
     * 这是一个 Call 的 extension function，用于将响应数据
     *
     * enqueue 是一个挂起函数，接收一个 Callback 接口对象来执行响应的回调。
     * 使用 suspendCoroutine 挂起该协程，等待响应时的回调执行 resume 来恢复该协程
     */
    private suspend fun <T> Call<T>.await(): T {

        // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/suspend-coroutine.html
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) it.resume(body)
                    else it.resumeWithException(RuntimeException("custom exception::response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }
}