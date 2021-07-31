package com.korilin.sunnyweather.logic.model

import androidx.lifecycle.liveData
import com.korilin.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository {

    /**
     * 搜索城市数据，不进行本地缓存，直接通过网络获取最新数据，利用 LiveData 感应数据的变化。
     *
     * [liveData] 调用挂起函数时，timeout 设置为 Dispatchers.IO 以开启子线程进行网络请求并将结果更新到创建的 liveData 中
     */
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("custom exception::response status is ${placeResponse.status}"))
            }
        } catch (e : Exception) {
            Result.failure(e)
        }
        // 通知 viewData 数据变化
        emit(result)
    }
}