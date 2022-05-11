package com.example.hotplenavigation.view.bottom_menu

import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.databinding.ActivityBottomMenuBinding
import com.example.hotplenavigation.util.extension.replace
import com.example.hotplenavigation.util.extension.replaceToBackStack
import com.example.hotplenavigation.view.bottom_menu.bookmark.BookmarkFragment
import com.example.hotplenavigation.view.bottom_menu.direction.DirectionFragment
import com.example.hotplenavigation.view.bottom_menu.search.SearchFragment
import com.example.hotplenavigation.view.bottom_menu.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomMenuActivity : BindingActivity<ActivityBottomMenuBinding>(R.layout.activity_bottom_menu) {
    private val searchFragment: SearchFragment by lazy { SearchFragment() }
    private val directionFragment: DirectionFragment by lazy { DirectionFragment() }
    private val bookmarkFragment: BookmarkFragment by lazy { BookmarkFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }

    override fun initView() {
        getDataFromActivity()
    }

    private fun getDataFromActivity() {
        val isSelectedBottomMenu = intent.extras?.getString("bottom_nav")!!
        replaceFragment(isSelectedBottomMenu)
    }

    private fun replaceFragment(bnvMenu: String) {
        when(bnvMenu) {
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

    private fun replaceSearchFragment() {
        replace(R.id.container_main, searchFragment)
    }

    private fun replaceDirectionFragment() {
        replace(R.id.container_main, directionFragment)
    }

    private fun replaceBookmarkFragment() {
        replace(R.id.container_main, bookmarkFragment)
    }

    private fun replaceSettingFragment() {
        replace(R.id.container_main, settingFragment)
    }
}