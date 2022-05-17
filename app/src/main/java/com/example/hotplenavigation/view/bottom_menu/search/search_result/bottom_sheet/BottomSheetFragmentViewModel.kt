package com.example.hotplenavigation.view.bottom_menu.search.search_result.bottom_sheet

import androidx.lifecycle.*
import com.example.hotplenavigation.database.BookmarkFragmentEntity
import com.example.hotplenavigation.repository.BookmarkFragmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetFragmentViewModel @Inject constructor(
    private val bookmarkFragmentRepository: BookmarkFragmentRepository
) : ViewModel() {

    private val _bookmarkPlace = MutableLiveData<BookmarkFragmentEntity>()
    val bookmarkPlace: LiveData<BookmarkFragmentEntity>
        get() = _bookmarkPlace

    private val _resultPlaceInfo = MutableLiveData<BookmarkFragmentEntity>()
    val resultPlaceInfo: LiveData<BookmarkFragmentEntity>
        get() = _resultPlaceInfo

    fun insertBookmark(bookmarkFragmentEntity: BookmarkFragmentEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkFragmentRepository.insertData(bookmarkFragmentEntity)
        }
    }

    fun deleteByNumber(number: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkFragmentRepository.deleteByNumber(number)
        }
    }

    fun getAllLikeData() = bookmarkFragmentRepository.getAllLikeData().asLiveData()

    fun setPlaceInfo(searchResultData: BookmarkFragmentEntity) {
        _resultPlaceInfo.value = searchResultData
    }
}