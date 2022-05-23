package com.example.hotplenavigation.view.bottom_menu.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotplenavigation.data.duru.Duru
import com.example.hotplenavigation.repository.GetDuruRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// 검색 ViewModel
@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val getDuruRepository: GetDuruRepository
) : ViewModel() {

    private val _getDuruData1 = MutableLiveData<List<Duru>>()
    val getDuruData1: LiveData<List<Duru>>
        get() = _getDuruData1

    private val _getDuruData2 = MutableLiveData<List<Duru>>()
    val getDuruData2: LiveData<List<Duru>>
        get() = _getDuruData2

    private val _getDuruData3 = MutableLiveData<List<Duru>>()
    val getDuruData3: LiveData<List<Duru>>
        get() = _getDuruData3

    private val _getDuruData4 = MutableLiveData<List<Duru>>()
    val getDuruData4: LiveData<List<Duru>>
        get() = _getDuruData4

    fun getDuruData(
        serviceKey: String,
        pageNo: Int,
        numOfRows: Int,
        num: Int
    ) {
        when (num) {
            0 -> {
                getDuruRepository.makeGetDuruData(serviceKey, pageNo, numOfRows, _getDuruData1)
            }
            1 -> {
                getDuruRepository.makeGetDuruData(serviceKey, pageNo, numOfRows, _getDuruData2)
            }
            2 -> {
                getDuruRepository.makeGetDuruData(serviceKey, pageNo, numOfRows, _getDuruData3)
            }
            else -> {
                getDuruRepository.makeGetDuruData(serviceKey, pageNo, numOfRows, _getDuruData4)
            }
        }

    }
}
