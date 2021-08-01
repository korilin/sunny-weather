package com.korilin.sunnyweather.ui.place

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.korilin.sunnyweather.databinding.FragmentPlaceBinding

class PlaceFragment : Fragment() {

    // 懒加载获得 PlaceViewModel
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var placeAdapter: PlaceAdapter

    private lateinit var _binding: FragmentPlaceBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 使用 viewBinding 加载布局
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placeAdapter = PlaceAdapter(this, viewModel.placeList)

        binding.placeRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = placeAdapter
        }

        // 监听输入框内容变化，将查询参数传递给 viewModel.searchPlaces 获取最新数据
        binding.searchPlaceEdit.addTextChangedListener {
            val content = it.toString()
            if (content.isNotEmpty() || content.isNotBlank()) {
                viewModel.searchPlaces(content)
            } else {
                binding.apply {
                    placeRecyclerView.visibility = View.GONE
                    bgImageView.visibility = View.VISIBLE
                }
                viewModel.placeList.clear()

                // NotifyDataSetChanged:::warn...
                placeAdapter.notifyDataSetChanged()
            }
        }

        // 为 placeLiveData 添加 observer 监听数据变化
        viewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
            val places = result.getOrNull()
            if (places != null) {
                binding.apply {
                    placeRecyclerView.visibility = View.VISIBLE
                    bgImageView.visibility = View.GONE
                }
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                placeAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_LONG).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }
}
