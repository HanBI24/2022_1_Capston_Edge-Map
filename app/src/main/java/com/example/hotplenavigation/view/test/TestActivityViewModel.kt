package com.example.hotplenavigation.view.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotplenavigation.base.BaseViewModel
import com.example.hotplenavigation.data.get_result_path.Traavoidtoll
import com.example.hotplenavigation.repository.GetResultPathRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestActivityViewModel @Inject constructor(
    private val getResultPathRepository: GetResultPathRepository
) : BaseViewModel() {

    private val _getResultPath = MutableLiveData<List<Traavoidtoll>>()
    val getResultPath: LiveData<List<Traavoidtoll>>
        get() = _getResultPath

    fun getResultPath(
        apiKeyId: String,
        apiKey: String,
        start: String,
        goal: String,
        option: String
    ) {
        getResultPathRepository.makeGetResultPathApiCall(apiKeyId, apiKey, start, goal, option, _getResultPath)
    }
}