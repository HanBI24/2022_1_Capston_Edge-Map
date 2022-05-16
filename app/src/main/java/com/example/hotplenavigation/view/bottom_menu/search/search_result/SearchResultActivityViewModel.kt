package com.example.hotplenavigation.view.bottom_menu.search.search_result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotplenavigation.data.geo.Addresse
import com.example.hotplenavigation.data.geo_reverse.Result1
import com.example.hotplenavigation.data.get_result_path.Traavoidtoll
import com.example.hotplenavigation.data.search_result.Item
import com.example.hotplenavigation.repository.GetGeoCodeRepository
import com.example.hotplenavigation.repository.GetResultPathRepository
import com.example.hotplenavigation.repository.GetReverseGeoCodeRepository
import com.example.hotplenavigation.repository.GetSearchResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchResultActivityViewModel @Inject constructor(
    private val getResultPathRepository: GetResultPathRepository,
    private val getReverseGeoCodeRepository: GetReverseGeoCodeRepository,
    private val getSearchResultRepository: GetSearchResultRepository,
    private val getGeoCodeRepository: GetGeoCodeRepository
) : ViewModel() {

    private val _getResultPath = MutableLiveData<List<Traavoidtoll>>()
    val getResultPath: LiveData<List<Traavoidtoll>>
        get() = _getResultPath

    private val _getResultPathGuide = MutableLiveData<List<Traavoidtoll>>()
    val getResultPathGuide: LiveData<List<Traavoidtoll>>
        get() = _getResultPathGuide

    private val _geoCode = MutableLiveData<Result1>()
    val geoCode: LiveData<Result1>
        get() = _geoCode

    private val _searchResult = MutableLiveData<List<Item>>()
    val searchResult: LiveData<List<Item>>
        get() = _searchResult

    private val _regionMutableLiveList = MutableLiveData<Set<String>>()
    val regionMutableLiveList: MutableLiveData<Set<String>>
        get() = _regionMutableLiveList

    private val _geoCodeLatLng = MutableLiveData<Addresse>()
    val getCodeLatLng: LiveData<Addresse>
        get() = _geoCodeLatLng

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
        Log.d("SearchResultViewModel", _geoCode.value.toString())
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

    fun addRegionMutableLiveList(region: Set<String>) {
        _regionMutableLiveList.postValue(region)
    }

    fun getGeoApi(
        apiKeyId: String,
        apiKey: String,
        query: String
    ) {
        getGeoCodeRepository.makeGetGeoCodeApiCall(apiKeyId, apiKey, query, _geoCodeLatLng)
    }
}