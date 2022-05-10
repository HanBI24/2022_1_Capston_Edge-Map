package com.example.hotplenavigation.view.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotplenavigation.base.BaseViewModel
import com.example.hotplenavigation.data.geo_reverse.Result1
import com.example.hotplenavigation.data.get_result_path.Traavoidtoll
import com.example.hotplenavigation.repository.GetResultPathRepository
import com.example.hotplenavigation.repository.GetReverseGeoCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestActivityViewModel @Inject constructor(
    private val getResultPathRepository: GetResultPathRepository,
    private val getReverseGeoCodeRepository: GetReverseGeoCodeRepository
) : BaseViewModel() {

    private val _getResultPath = MutableLiveData<List<Traavoidtoll>>()
    val getResultPath: LiveData<List<Traavoidtoll>>
        get() = _getResultPath

    private val _getResultPathGuide = MutableLiveData<List<Traavoidtoll>>()
    val getResultPathGuide: LiveData<List<Traavoidtoll>>
        get() = _getResultPathGuide

    private val _geoCode = MutableLiveData<Result1>()
    val geoCode: MutableLiveData<Result1>
        get() = _geoCode

    fun getResultPath(
        apiKeyId: String,
        apiKey: String,
        start: String,
        goal: String,
        option: String
    ) {
        getResultPathRepository.makeGetResultPathApiCall(apiKeyId, apiKey, start, goal, option, _getResultPath, _getResultPathGuide)
    }

    fun getReverseGeoApi(
        apiKeyId: String,
        apiKey: String,
        coords: String,
    ) {
        getReverseGeoCodeRepository.makeReverseGeoApiCall(apiKeyId, apiKey, coords, _geoCode)
    }
}
