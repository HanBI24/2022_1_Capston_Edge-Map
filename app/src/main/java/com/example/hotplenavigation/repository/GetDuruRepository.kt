package com.example.hotplenavigation.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hotplenavigation.data.duru.Duru
import com.example.hotplenavigation.di.RetrofitModule
import com.example.hotplenavigation.network.DuruApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetDuruRepository @Inject constructor(
    @RetrofitModule.DuruType private val duruData: DuruApi
) {
    private val duruList = mutableListOf<Duru>()

    fun makeGetDuruData(
        serviceKey: String,
        pageNo: Int,
        numOfRows: Int,
        getDuruLiveData: MutableLiveData<List<Duru>>
    ) {
        val call = duruData.getDuru(serviceKey, pageNo, numOfRows)
        call.enqueue(object : Callback<Duru> {
            override fun onResponse(call: Call<Duru>, response: Response<Duru>) {
                if (response.isSuccessful && response.body() != null) {
                    duruList.add(response.body()!!)
                    getDuruLiveData.postValue(duruList)
                    Log.d("GetDuruRepository Suc", duruList.toString())
                } else {
                    Log.d("GetDuruRepository Err", response.errorBody()?.string()!!)
                }
            }

            override fun onFailure(call: Call<Duru>, t: Throwable) {
                Log.d("GetDuruRepository Fail", t.toString())
            }
        })
    }
}
