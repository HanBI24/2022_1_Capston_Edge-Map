package com.example.hotplenavigation.view.bottom_menu.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
) : ViewModel() {

    val searchWordData = MutableLiveData<String>()

}