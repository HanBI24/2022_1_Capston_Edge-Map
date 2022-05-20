package com.example.hotplenavigation.view

import android.content.Intent
import androidx.activity.viewModels
import androidx.annotation.UiThread
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.databinding.ActivityMainBinding
import com.example.hotplenavigation.util.extension.setNaverMapRender
import com.example.hotplenavigation.view.bottom_menu.BottomMenuActivity
import com.example.hotplenavigation.view.bottom_menu.setting.CommActivity
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BindingActivity<ActivityMainBinding>(R.layout.activity_main), OnMapReadyCallback {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var naverMap: NaverMap

    override fun initView() {
        setNaverMapRender(R.id.container_map, supportFragmentManager, this)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val intent = Intent(this@MainActivity, BottomMenuActivity::class.java)
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_menu_search -> {
                    intent.putExtra("bottom_nav", "bnv_search")
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_menu_direction -> {
                    intent.putExtra("bottom_nav", "bnv_direction")
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_menu_bookmark -> {
                    intent.putExtra("bottom_nav", "bnv_bookmark")
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_menu_setting -> {
//                    intent.putExtra("bottom_nav", "bnv_setting")
//                    startActivity(intent)
                    val commIntent = Intent(this@MainActivity, CommActivity::class.java)
                    startActivity(commIntent)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        binding.btnLocation.map = naverMap

        val locationSource = FusedLocationSource(this, 1000)
        naverMap.locationSource = locationSource

        val locationOverlay = naverMap.locationOverlay
        locationOverlay.isVisible = true

        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }
}
