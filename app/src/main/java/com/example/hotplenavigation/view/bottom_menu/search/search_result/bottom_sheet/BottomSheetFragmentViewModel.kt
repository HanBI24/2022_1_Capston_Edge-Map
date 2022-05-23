package com.example.hotplenavigation.view.bottom_menu.search.search_result.bottom_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.hotplenavigation.database.BookmarkFragmentEntity
import com.example.hotplenavigation.repository.BookmarkFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// Local DB(Room)와 통신하기 위하여 필요한 데이터를 필터링하는 ViewModel
@HiltViewModel
class BottomSheetFragmentViewModel @Inject constructor(
    // Repository 패턴을 사용하여 Local DB에 있는 값을 가져옴
    private val bookmarkFragmentRepository: BookmarkFragmentRepository
) : ViewModel() {

    private val _bookmarkPlace = MutableLiveData<BookmarkFragmentEntity>()
    val bookmarkPlace: LiveData<BookmarkFragmentEntity>
        get() = _bookmarkPlace

    private val _resultPlaceInfo = MutableLiveData<BookmarkFragmentEntity>()
    val resultPlaceInfo: LiveData<BookmarkFragmentEntity>
        get() = _resultPlaceInfo

    // DB에 추가
    fun insertBookmark(bookmarkFragmentEntity: BookmarkFragmentEntity) {
        // 해당 ViewModel을 따르는 Coroutine 생성
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkFragmentRepository.insertData(bookmarkFragmentEntity)
        }
    }

    // DB에서 제거 (이름을 기준으로 제거)
    fun deleteByTitle(number: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkFragmentRepository.deleteByTitle(number)
        }
    }

    // DB의 모든 데이터 가져오기
    fun getAllLikeData() = bookmarkFragmentRepository.getAllLikeData().asLiveData()

    // 해당 지역 정보 저장
    fun setPlaceInfo(searchResultData: BookmarkFragmentEntity) {
        _resultPlaceInfo.value = searchResultData
    }
}
