package com.example.hotplenavigation.view.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotplenavigation.base.BaseViewModel
import com.example.hotplenavigation.data.geo_reverse.Result1
import com.example.hotplenavigation.data.get_result_path.Traavoidtoll
import com.example.hotplenavigation.data.search_result.Item
import com.example.hotplenavigation.repository.GetResultPathRepository
import com.example.hotplenavigation.repository.GetReverseGeoCodeRepository
import com.example.hotplenavigation.repository.GetSearchResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// 20165304 김성곤, 20197138 장은지
// API 및 여러 기능을 테스트 하기 위한 테스트 뷰모델
@HiltViewModel
class TestActivityViewModel @Inject constructor(
    private val getResultPathRepository: GetResultPathRepository,
    private val getReverseGeoCodeRepository: GetReverseGeoCodeRepository,
    private val getSearchResultRepository: GetSearchResultRepository
) : BaseViewModel() {

    private val _getResultPath = MutableLiveData<List<Traavoidtoll>>()
    val getResultPath: LiveData<List<Traavoidtoll>>
        get() = _getResultPath

    private val _getResultPathGuide = MutableLiveData<List<Traavoidtoll>>()
    val getResultPathGuide: LiveData<List<Traavoidtoll>>
        get() = _getResultPathGuide

    private val _geoCode = MutableLiveData<Result1>()
    val geoCode: LiveData<Result1>
        get() = _geoCode

    private val _geoCodeList = MutableLiveData<List<Result1>>()
    val geoCodeList: LiveData<List<Result1>>
        get() = _geoCodeList

    private val _searchResult = MutableLiveData<List<Item>>()
    val searchResult: LiveData<List<Item>>
        get() = _searchResult

    fun getResultPath(
        apiKeyId: String,
        apiKey: String,
        start: String,
        goal: String,
        option: String
    ) {
        getResultPathRepository.makeGetResultPathApiCall(apiKeyId, apiKey, start, goal, option, _getResultPath)
    }

    fun getReverseGeoApi(
        apiKeyId: String,
        apiKey: String,
        coords: String,
    ) {
        getReverseGeoCodeRepository.makeReverseGeoApiCall(apiKeyId, apiKey, coords, _geoCode)
    }

    fun getSearchResult(
        apiKeyId: String,
        apiKey: String,
        display: Int,
        start: Int,
        sort: String,
        query: String,
    ) {
        getSearchResultRepository.makeSearchResultApiCall(apiKeyId, apiKey, display, start, sort, query, _searchResult)
    }
}
