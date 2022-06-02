package com.example.hotplenavigation.view.bottom_menu.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.hotplenavigation.database.BookmarkFragmentEntity
import com.example.hotplenavigation.repository.BookmarkFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// 20165304 김성곤
// Local DB(Room)와 통신하기 위하여 필요한 데이터를 필터링하는 ViewModel
@HiltViewModel
class BookmarkFragmentViewModel @Inject constructor(
    // Repository 패턴을 사용하여 Local DB에 있는 값을 가져옴
    private val bookmarkFragmentRepository: BookmarkFragmentRepository
) : ViewModel() {

    private val bookmarkDataList: LiveData<List<BookmarkFragmentEntity>> =
        bookmarkFragmentRepository.getAllData().asLiveData()

    // DB에 있는 모든 데이터 가져옴
    fun getAllData() = bookmarkDataList

    // 이름을 기준으로 DB에서 삭제
    fun deleteBookmark(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkFragmentRepository.deleteByTitle(title)
        }
    }
}
