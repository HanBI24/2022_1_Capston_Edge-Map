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

@HiltViewModel
class BookmarkFragmentViewModel @Inject constructor(
    private val bookmarkFragmentRepository: BookmarkFragmentRepository
) : ViewModel() {

    private val bookmarkDataList: LiveData<List<BookmarkFragmentEntity>> =
        bookmarkFragmentRepository.getAllData().asLiveData()

    fun getAllData() = bookmarkDataList

    fun deleteBookmark(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkFragmentRepository.deleteByTitle(title)
        }
    }
}
