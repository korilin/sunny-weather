package com.korilin.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.korilin.sunnyweather.logic.Repository
import com.korilin.sunnyweather.logic.model.PlaceResponse

class PlaceViewModel : ViewModel() {
    // 接收搜索参数
    private val searchLiveData = MutableLiveData<String>()

    // 缓存 UI 显示的城市数据
    val placeList = mutableListOf<PlaceResponse.Place>()

    /**
     * 观察 [searchLiveData] 的值，当 [searchPlaces] 被调用时，
     * 将会执行 [Repository.searchPlaces] 来搜索数据并将返回的 LiveData 添加到监听 Map 中，
     * 当返回的 LiveData 的数据发送变化时，把数据更新到 placeLiveData 中。
     */
    val placeLiveData = Transformations.switchMap(searchLiveData) {
        Repository.searchPlaces(it)
    }


    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: PlaceResponse.Place) = Repository.savePlace(place)

    fun getSavePlace() = Repository.getSavePlace()

    fun isPlaceSaved() = Repository.isPlaceSave()
}