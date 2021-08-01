package com.korilin.sunnyweather.logic

import android.content.Context
import androidx.lifecycle.liveData
import com.korilin.sunnyweather.SunnyWeatherApplication
import com.korilin.sunnyweather.logic.dao.PlaceDao
import com.korilin.sunnyweather.logic.model.PlaceResponse
import com.korilin.sunnyweather.logic.model.Weather
import com.korilin.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }

    /**
     * 搜索城市数据，不进行本地缓存，直接通过网络获取最新数据，利用 LiveData 感应数据的变化。
     *
     * [liveData] 调用挂起函数时，timeout 设置为 Dispatchers.IO 以开启子线程进行网络请求并将结果更新到创建的 liveData 中
     */
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("custom exception::response status is ${placeResponse.status}"))
        }
    }

    /**
     * 获取天气和未来天气信息
     */
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }

            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()

            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(
                    realtimeResponse.result.realtime,
                    dailyResponse.result.daily
                )
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        """
                            custom exception::response status is
                            realtime -> ${realtimeResponse.status}
                            daily -> ${dailyResponse.status}
                        """.trimIndent()
                    )
                )
            }
        }
    }

    fun savePlace(place: PlaceResponse.Place) = PlaceDao.savePlace(place)

    fun getSavePlace() = PlaceDao.getSavedPlace()

    fun isPlaceSave() = PlaceDao.isPlaceSaved()
}