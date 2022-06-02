package com.example.hotplenavigation.view.bottom_menu.search.search_result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotplenavigation.data.geo.Addresse
import com.example.hotplenavigation.data.geo_reverse.Result1
import com.example.hotplenavigation.data.get_result_path.Traavoidtoll
import com.example.hotplenavigation.data.search_result.Item
import com.example.hotplenavigation.database.BookmarkFragmentEntity
import com.example.hotplenavigation.repository.GetGeoCodeRepository
import com.example.hotplenavigation.repository.GetResultPathRepository
import com.example.hotplenavigation.repository.GetReverseGeoCodeRepository
import com.example.hotplenavigation.repository.GetSearchResultRepository
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// 20165304 김성곤
// API와 통신하여 필요한 데이터를 필터링하는 ViewModel
@HiltViewModel
class SearchResultActivityViewModel @Inject constructor(
    // Repository 패턴을 사용하여 API 응답 값을 받아옴
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

    private val data = arrayListOf<BookmarkFragmentEntity>()

    private val _bookmarkData = MutableLiveData<BookmarkFragmentEntity>()
    val bookmarkData: LiveData<BookmarkFragmentEntity>
        get() = _bookmarkData

    private val _getInitialGeoCode = MutableLiveData<Addresse>()
    val getInitialGeoCode: LiveData<Addresse>
        get() = _getInitialGeoCode

    val bottomTitle = MutableLiveData<String>()
    val bottomAddress = MutableLiveData<String>()
    val bottomMarker = MutableLiveData<Marker>()

    // Naver Direction 5 API를 사용하여 경로 데이터 받아옴
    fun getResultPath(
        apiKeyId: String,
        apiKey: String,
        start: String,
        goal: String,
        option: String
    ) {
        getResultPathRepository.makeGetResultPathApiCall(apiKeyId, apiKey, start, goal, option, _getResultPath)
    }

    // Naver Reverse Geo API를 사용하여 위도, 경도 값을 위치 정보로 변환
    fun getReverseGeoApi(
        apiKeyId: String,
        apiKey: String,
        coords: String,
    ) {
        getReverseGeoCodeRepository.makeReverseGeoApiCall(apiKeyId, apiKey, coords, _geoCode)
        Log.d("SearchResultViewModel", _geoCode.value.toString())
    }

    // Naver Search API를 사용하여 검색 결과를 받아옴
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

    // 지역 정보를 추가함
    fun addRegionMutableLiveList(region: Set<String>) {
        _regionMutableLiveList.postValue(region)
    }

    // 실제 위치를 위도와 경도로 바꿈
    fun getGeoApi(
        apiKeyId: String,
        apiKey: String,
        query: String
    ) {
        getGeoCodeRepository.makeGetGeoCodeApiCall(apiKeyId, apiKey, query, _geoCodeLatLng)
    }

    // 실제 위치를 위도와 경도로 바꿈 (LiveData 분리를 위함)
    fun getInitialGeoApi(
        apiKeyId: String,
        apiKey: String,
        query: String
    ) {
        getGeoCodeRepository.makeGetGeoCodeApiCall(apiKeyId, apiKey, query, _getInitialGeoCode)
    }

    // API 응답을 통한 지역 추가
    fun addPlace(resultData: BookmarkFragmentEntity) {
        _bookmarkData.value = resultData
    }
}
