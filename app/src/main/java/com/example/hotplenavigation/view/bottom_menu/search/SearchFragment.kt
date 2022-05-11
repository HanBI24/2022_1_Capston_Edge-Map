package com.example.hotplenavigation.view.bottom_menu.search

import android.widget.Toast
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingFragment
import com.example.hotplenavigation.databinding.FragmentBookmarkBinding
import com.example.hotplenavigation.databinding.FragmentSearchBinding
import com.iammert.library.ui.multisearchviewlib.MultiSearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override fun initView() {
        multiSearchViewInit()
    }

    private fun multiSearchViewInit() {
        binding.multiSearchView.setSearchViewListener(object : MultiSearchView.MultiSearchViewListener{
            override fun onItemSelected(index: Int, s: CharSequence) {

            }

            override fun onSearchComplete(index: Int, s: CharSequence) {

            }

            override fun onSearchItemRemoved(index: Int) {

            }

            override fun onTextChanged(index: Int, s: CharSequence) {

            }
        })
    }
}