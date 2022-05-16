package com.example.hotplenavigation.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hotplenavigation.data.geo.Addresse
import com.example.hotplenavigation.data.geo.GeoApi
import com.example.hotplenavigation.di.RetrofitModule
import com.example.hotplenavigation.network.NaverMapApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetGeoCodeRepository @Inject constructor(
    @RetrofitModule.GeoType private val getGeoCode: NaverMapApi
) {
    fun makeGetGeoCodeApiCall(
        apiKeyId: String,
        apiKey: String,
        query: String,
        getGeoCodeLatLngLiveDataX: MutableLiveData<Addresse>
    ) {
        val call = getGeoCode.getGeoCode(apiKeyId, apiKey, query)
        call.enqueue(object : Callback<GeoApi> {
            override fun onResponse(call: Call<GeoApi>, response: Response<GeoApi>) {
                if(response.isSuccessful && response.body() != null) {
                    getGeoCodeLatLngLiveDataX.postValue(response.body()!!.addresses[0])

                } else {
                    Log.d("SearchResultActivity", response.errorBody()?.string()!!)
                }
            }

            override fun onFailure(call: Call<GeoApi>, t: Throwable) {
                Log.d("SearchResultActivity", t.toString())
            }

        })
    }
}