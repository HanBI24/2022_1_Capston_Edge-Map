package com.example.hotplenavigation.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hotplenavigation.data.search_result.Item
import com.example.hotplenavigation.data.search_result.SearchResultData
import com.example.hotplenavigation.di.RetrofitModule
import com.example.hotplenavigation.network.NaverMapApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// 20165304 김성곤
// API 호출을 통한 검색 결과 응답 데이터 받아오는 Repository (Retrofit Open Library 사용)
class GetSearchResultRepository @Inject constructor(
    @RetrofitModule.SearchResultType private val searchResult: NaverMapApi
) {
    fun makeSearchResultApiCall(
        apiKeyId: String,
        apiKey: String,
        display: Int,
        start: Int,
        sort: String,
        query: String,
        liveData: MutableLiveData<List<Item>>
    ) {
        val call = searchResult.getSearchResult(apiKeyId, apiKey, display, start, sort, query)
        call.enqueue(object : Callback<SearchResultData> {
            override fun onResponse(call: Call<SearchResultData>, response: Response<SearchResultData>) {
                if (response.body() != null && response.isSuccessful) {
                    liveData.postValue(response.body()!!.items)
                } else {
                    Log.d("GetSearchRepository", "NULL")
                    Log.d("GetSearchRepository", response.errorBody()?.string()!!)
                }
            }

            override fun onFailure(call: Call<SearchResultData>, t: Throwable) {
                Log.d("GetSearchRepository", "FAIL")
            }
        })
    }
}
