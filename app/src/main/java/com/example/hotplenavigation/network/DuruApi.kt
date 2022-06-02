package com.example.hotplenavigation.network

import com.example.hotplenavigation.data.duru.Duru
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DuruApi {

    @GET("openapi/service/rest/Durunubi/routeList")
    fun getDuru(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("MobileOS") MobileOS: String = "AND",
        @Query("MobileApp") MobileApp: String = "핫플 네비게이션",
        @Query("brdDiv") brdDiv: String = "DNWW",
        @Query("_type") _type: String = "json"
    ): Call<Duru>
}
