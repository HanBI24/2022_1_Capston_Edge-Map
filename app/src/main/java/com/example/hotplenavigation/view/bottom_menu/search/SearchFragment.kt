package com.example.hotplenavigation.view.bottom_menu.search

import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingFragment
import com.example.hotplenavigation.databinding.FragmentSearchBinding
import com.example.hotplenavigation.view.bottom_menu.search.search_result.SearchResultActivity
import com.iammert.library.ui.multisearchviewlib.MultiSearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchFragmentViewModel: SearchFragmentViewModel by activityViewModels()

    override fun initView() {
        multiSearchViewInit()
    }

    private fun multiSearchViewInit() {
        binding.multiSearchView.setSearchViewListener(object : MultiSearchView.MultiSearchViewListener {
            override fun onItemSelected(index: Int, s: CharSequence) {
            }

            override fun onSearchComplete(index: Int, s: CharSequence) {
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("search_fragment", s.toString())
                startActivity(intent)
            }

            override fun onSearchItemRemoved(index: Int) {
            }

            override fun onTextChanged(index: Int, s: CharSequence) {
            }
        })
    }
}
