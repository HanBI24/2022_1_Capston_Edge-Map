package com.example.hotplenavigation.di

import com.example.hotplenavigation.network.DuruApi
import com.example.hotplenavigation.network.NaverMapApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import javax.inject.Qualifier
import javax.inject.Singleton

// Retrofit 사용하기 위한 Module
// 보일러 플레이트 코드 감소 및 의존성을 제거하기 위해
// DI Library 중 하나인 Dagger-Hilt를 사용
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val baseGetResultPathURL = "https://naveropenapi.apigw.ntruss.com/map-direction/"
    private const val baseReverseGeoURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/"
    private const val baseSearchResultURL = "https://openapi.naver.com/"
    private const val baseGeoURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/"
    private const val baseDuruURL = "http://api.visitkorea.or.kr/"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GetResultPathType

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ReverseGeoType

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SearchResultType

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GeoType

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DuruType

    private val gson = GsonBuilder()
        .setLenient()
        .serializeNulls()
        .setDateFormat("yyyyMMddHHmmss")
        .create()

    @Singleton
    @Provides
    @GetResultPathType
    fun provideGetRetroServiceInterfaceGetResultPath(
        @GetResultPathType retrofit: Retrofit
    ): NaverMapApi =
        retrofit.create(NaverMapApi::class.java)

    @Singleton
    @Provides
    @GetResultPathType
    fun provideGetRetroInstanceGetResultPath(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseGetResultPathURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @ReverseGeoType
    fun provideGetRetroServiceInterfaceReverseGeo(
        @ReverseGeoType retrofit: Retrofit
    ): NaverMapApi =
        retrofit.create(NaverMapApi::class.java)

    @Singleton
    @Provides
    @ReverseGeoType
    fun provideGetRetroInstanceReverseGeo(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseReverseGeoURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @SearchResultType
    fun provideGetRetroServiceInterfaceSearchResult(
        @SearchResultType retrofit: Retrofit
    ): NaverMapApi =
        retrofit.create(NaverMapApi::class.java)

    @Singleton
    @Provides
    @SearchResultType
    fun provideGetRetroInstanceSearchResult(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseSearchResultURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @GeoType
    fun provideGetRetroServiceInterfaceGeoCode(
        @GeoType retrofit: Retrofit
    ): NaverMapApi =
        retrofit.create(NaverMapApi::class.java)

    @Singleton
    @Provides
    @GeoType
    fun provideGetRetroInstanceGeoCode(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseGeoURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @DuruType
    fun provideGetRetroServiceInterfaceDuru(
        @DuruType retrofit: Retrofit
    ): DuruApi =
        retrofit.create(DuruApi::class.java)

    @Singleton
    @Provides
    @DuruType
    fun provideGetRetroInstanceDuru(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseDuruURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
}
