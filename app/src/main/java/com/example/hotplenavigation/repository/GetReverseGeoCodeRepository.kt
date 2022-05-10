package com.example.hotplenavigation.repository

import androidx.lifecycle.MutableLiveData
import com.example.hotplenavigation.data.geo_reverse.Result1
import com.example.hotplenavigation.data.geo_reverse.ReverseGeoApi
import com.example.hotplenavigation.di.RetrofitModule
import com.example.hotplenavigation.network.NaverMapApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IndexOutOfBoundsException
import javax.inject.Inject

class GetReverseGeoCodeRepository @Inject constructor(
    @RetrofitModule.ReverseGeoType private val naverMapApiReverseGeo: NaverMapApi
) {
    fun makeReverseGeoApiCall(
        apiKeyId: String,
        apiKey: String,
        coords: String,
        liveDataList: MutableLiveData<Result1>
    ) {
        val call = naverMapApiReverseGeo.getReverseGeo(apiKeyId, apiKey, coords)
        call.enqueue(object : Callback<ReverseGeoApi> {
            override fun onResponse(call: Call<ReverseGeoApi>, response: Response<ReverseGeoApi>) {
                try {
                    if (response.body() != null && response.isSuccessful)
                        liveDataList.postValue(response.body()!!.results?.get(0))
                    else
                        liveDataList.postValue(null)
                } catch (indexOut: IndexOutOfBoundsException) {

                }
            }

            override fun onFailure(call: Call<ReverseGeoApi>, t: Throwable) {

            }
        })
    }
}