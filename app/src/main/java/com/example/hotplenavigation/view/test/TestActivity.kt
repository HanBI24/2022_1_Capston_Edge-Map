package com.example.hotplenavigation.view.test

import android.graphics.Color
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
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity :
    BindingActivity<ActivityTestBinding>(R.layout.activity_test),
    OnMapReadyCallback {
    private val testActivityViewModel: TestActivityViewModel by viewModels()
    private lateinit var naverMap: NaverMap
    private var zoomRatio: Double = 11.0

    override fun initView() {
        setNaverMapRender(R.id.map_fragment, supportFragmentManager, this)

        testActivityViewModel.getResultPath(
            "uzlzuhd2pa",
            "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
            "127.1058342,37.359708",
            "129.075986,35.179470",
            "traavoidtoll"
        )

        testActivityViewModel.getResultPath.observe(this, {
            val path = PathOverlay()
            var routesCount = 0
            val pathContainer: MutableList<LatLng> = mutableListOf(LatLng(0.1, 0.1))
            if (it != null) {
                for (pathCode in it) {
                    for (pathCodeXY in pathCode.path) {
                        pathContainer.add(LatLng(pathCodeXY[1], pathCodeXY[0]))
                        routesCount++
                    }
                }
            }
            path.coords = pathContainer.drop(1)
            path.color = Color.RED
            path.map = naverMap

            /*zoomRatio = when ((it[0].summary.distance * 0.001).toInt()) {
                in 10..29 -> 11.0
                in 30..59 -> 10.0
                in 60..89 -> 9.0
                in 90..99 -> 7.0
                in 100..999 -> 3.0
                else -> 11.0
            }*/

            val CenterLatlng =
                LatLng(it[0].path[routesCount / 2][1], it[0].path[routesCount / 2][0])
            naverMap.moveCamera(
                CameraUpdate.scrollAndZoomTo(CenterLatlng, zoomRatio)
                    .animate(CameraAnimation.Fly, 2000)
            )

            val marker = Marker()
            marker.position = LatLng(it[0].path[routesCount - 1][1], it[0].path[routesCount - 1][0])
            marker.map = naverMap
        })
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

    }
}
