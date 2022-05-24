package com.example.hotplenavigation.view.test

import android.graphics.Color
import android.util.Log
import androidx.activity.viewModels
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.databinding.ActivityTestBinding
import com.example.hotplenavigation.util.extension.setNaverMapRender
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PathOverlay
import dagger.hilt.android.AndroidEntryPoint

// API 및 여러 기능을 테스트 하기 위한 테스트 액티비티
@AndroidEntryPoint
class TestActivity : BindingActivity<ActivityTestBinding>(R.layout.activity_test) {
    override fun initView() {

    }

}
// 빨리해 이제 사랑해
