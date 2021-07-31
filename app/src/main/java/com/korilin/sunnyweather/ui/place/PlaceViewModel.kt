package com.korilin.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.korilin.sunnyweather.logic.dao.Place
import com.korilin.sunnyweather.logic.model.Repository

class PlaceViewModel : ViewModel() {
    // 缓存 UI 显示的城市数据
    val placeList = mutableListOf<Place>()

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

    // 接收搜索参数
    private val searchLiveData = MutableLiveData<String>()

    }
}