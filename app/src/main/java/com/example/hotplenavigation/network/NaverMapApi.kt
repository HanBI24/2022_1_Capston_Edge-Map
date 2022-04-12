package com.example.hotplenavigation.network

import com.example.hotplenavigation.data.get_result_path.GetResultPath
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverMapApi {

    @GET("v1/driving")
    fun getResultPath(
        @Header("X-NCP-APIGW-API-KEY-ID") apiKeyID: String,
        @Header("X-NCP-APIGW-API-KEY") apiKey: String,
        @Query("start") start: String,
        @Query("goal") goal: String,
        @Query("option") option: String,
    ): Call<GetResultPath>
}