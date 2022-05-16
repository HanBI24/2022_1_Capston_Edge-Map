package com.example.hotplenavigation.view.bottom_menu.search.search_result.set_marker

import android.util.Log
import androidx.activity.viewModels
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.data.geo_reverse.ReverseGeoApi
import com.example.hotplenavigation.databinding.ActivityMarkerBinding
import com.example.hotplenavigation.network.NaverMapApi
import com.example.hotplenavigation.view.bottom_menu.search.search_result.SearchResultActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IndexOutOfBoundsException

@AndroidEntryPoint
class MarkerActivity : BindingActivity<ActivityMarkerBinding>(R.layout.activity_marker) {
    private val searchResultActivityViewModel: SearchResultActivityViewModel by viewModels()

    override fun initView() {
        binding.tvTemp.text = searchResultActivityViewModel.regionMutableLiveList.value.toString()
    }
}