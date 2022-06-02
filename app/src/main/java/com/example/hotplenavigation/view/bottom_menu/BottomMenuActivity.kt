package com.example.hotplenavigation.view.bottom_menu

import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.databinding.ActivityBottomMenuBinding
import com.example.hotplenavigation.util.extension.replace
import com.example.hotplenavigation.view.bottom_menu.bookmark.BookmarkFragment
import com.example.hotplenavigation.view.bottom_menu.search.SearchFragment
import com.example.hotplenavigation.view.bottom_menu.setting.CommFragment
import dagger.hilt.android.AndroidEntryPoint

// 20165304 김성곤
// Fragment 전환 액티비티
@AndroidEntryPoint
class BottomMenuActivity : BindingActivity<ActivityBottomMenuBinding>(R.layout.activity_bottom_menu) {
    private val searchFragment: SearchFragment by lazy { SearchFragment() }
    private val bookmarkFragment: BookmarkFragment by lazy { BookmarkFragment() }
    private val commFragment: CommFragment by lazy { CommFragment() }

    override fun initView() {
        getDataFromActivity()
    }

    override fun onRestart() {
        super.onRestart()
        getDataFromActivity()
    }

    private fun getDataFromActivity() {
        val isSelectedBottomMenu = intent.extras?.getString("bottom_nav")!!
        replaceFragment(isSelectedBottomMenu)
    }

    private fun replaceFragment(bnvMenu: String) {
        when (bnvMenu) {
            "bnv_search" -> {
                replaceSearchFragment()
            }
            "bnv_direction" -> {
                replaceDirectionFragment()
            }
            "bnv_bookmark" -> {
                replaceBookmarkFragment()
            }
            "bnv_setting" -> {
                replaceSettingFragment()
            }
        }
    }

    // Fragment 전환
    private fun replaceSearchFragment() {
        replace(R.id.container_main, searchFragment)
    }

    private fun replaceDirectionFragment() {
    }

    private fun replaceBookmarkFragment() {
        replace(R.id.container_main, bookmarkFragment)
    }

    private fun replaceSettingFragment() {
        replace(R.id.container_main, commFragment)
    }
}
