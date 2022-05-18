package com.example.hotplenavigation.view.bottom_menu.search.search_result

import android.app.ProgressDialog
import android.graphics.Color
import android.util.Log
import androidx.activity.viewModels
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.database.BookmarkFragmentEntity
import com.example.hotplenavigation.databinding.FragmentSearchResultBinding
import com.example.hotplenavigation.util.extension.setNaverMapRender
import com.example.hotplenavigation.view.bottom_menu.search.search_result.bottom_sheet.BottomSheetFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class SearchResultActivity :
    BindingActivity<FragmentSearchResultBinding>(R.layout.fragment_search_result),
    OnMapReadyCallback {
    private val searchResultActivityViewModel: SearchResultActivityViewModel by viewModels()
    private lateinit var naverMap: NaverMap
    private var zoomRatio: Double = 8.0
    private val regionMutableList = mutableSetOf<String>()
    private val searchResultTitleList = mutableSetOf<String>()
    private val searchResultAddressList = mutableSetOf<String>()
    private val pathGuideX = mutableListOf<Double>()
    private val pathGuideY = mutableListOf<Double>()
    private lateinit var infoWindow: InfoWindow
    private lateinit var marker: Marker
    private val markerList = mutableListOf<Marker>()
    private val sheet: BottomSheetFragment by lazy { BottomSheetFragment() }
    private var LatLngX by Delegates.notNull<Double>()
    private var LatLngY by Delegates.notNull<Double>()

    override fun initView() {
        val word = intent.getStringExtra("search_fragment")
        setNaverMapRender(R.id.map_fragment, supportFragmentManager, this)

        if (word != null) {
            searchResultActivityViewModel.getInitialGeoApi(
                "uzlzuhd2pa",
                "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                word
            )
        }

        searchResultActivityViewModel.getInitialGeoCode.observe(this, {
            LatLngY = it.y.toDouble()
            LatLngX = it.x.toDouble()

            Log.d("SearchFragment", "$LatLngY,$LatLngX")

            searchResultActivityViewModel.getResultPath(
                "uzlzuhd2pa",
                "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                "127.09431687965,37.513272317072",
                "$LatLngX,$LatLngY",
                "traavoidtoll"
            )
        })

        infoWindow = InfoWindow()
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(p0: InfoWindow): CharSequence {
                return p0.marker?.tag as CharSequence
            }
        }

//        marker.setOnClickListener {
//            Log.d("SearchResultActivity", marker.tag.toString())
//            infoWindow.open(marker)
//            true
//        }

        searchResultActivityViewModel.getCodeLatLng.observe(this, {
            marker = Marker()
            markerList.add(marker)
            try {
                marker.position = LatLng(
                    it.y.toDouble(),
                    it.x.toDouble()
                )

                for (i in searchResultTitleList) {
                    marker.tag = i
                }

                for (i in searchResultAddressList) {
                    marker.captionText = i
                }

                marker.captionTextSize = 0.0f

                for (i in markerList) {
                    i.setOnClickListener {
                        Log.d("SearchResultActivity", i.tag.toString())
                        infoWindow.open(i)
                        searchResultActivityViewModel.apply {
                            bottomTitle.value = i.tag.toString()
                            bottomAddress.value = i.captionText
                            bottomMarker.value = i
                            addPlace(
                                BookmarkFragmentEntity(
                                    i.tag.toString(),
                                    i.captionText,
                                    false
                                )
                            )
                        }
                        sheet.show(supportFragmentManager, "BottomSheetFragment")
                        true
                    }
                }
                marker.map = naverMap
            } catch (npe: NullPointerException) {
            }
        })

        searchResultActivityViewModel.searchResult.observe(this, {
            for (i in it) {
//                marker = Marker()
//                markerList.add(marker)

                searchResultTitleList.add(i.title)
                searchResultAddressList.add(i.address)
                Log.d("SearchResultActivity", searchResultTitleList.toString())

                searchResultActivityViewModel.getGeoApi(
                    "uzlzuhd2pa",
                    "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                    i.address
                )
//                Log.d("SearchResultActivity", i.address)
            }
//            Log.d("SearchResultActivity", searchResultTitleList.toString())
        })

        searchResultActivityViewModel.geoCode.observe(this, {
            CoroutineScope(Dispatchers.IO).launch {
                val regionName1 = it.region?.area3.toString().split("=")
                val regionName2 = regionName1[1].split(")")
                regionMutableList.add(regionName2[0])
                searchResultActivityViewModel.addRegionMutableLiveList(regionMutableList)

                searchResultActivityViewModel.getSearchResult(
                    "oViKm6VRvRLKpY8LpKuK",
                    "6JiG572cvL",
                    10,
                    1,
                    "comment",
                    regionName2[0] + "맛집"
                )
            }
        })

//         1. 경로 그리기
        searchResultActivityViewModel.getResultPath.observe(this, {
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

            if (it != null) {
                for (pathCode in it) {
                    for (pathCodeXY in pathCode.path) {
                        pathGuideX.add(pathCodeXY[1])
                        pathGuideY.add(pathCodeXY[0])
                    }
                }
            }

            for ((kk, _) in pathGuideX.withIndex()) {
                searchResultActivityViewModel.getReverseGeoApi(
                    "uzlzuhd2pa",
                    "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                    "${pathGuideY[kk]},${pathGuideX[kk]}"
                )
            }

            path.apply {
                coords = pathContainer.drop(1)
                color = Color.RED
                map = naverMap
            }

            val centerLatLng =
                LatLng(it[0].path[routesCount / 2][1], it[0].path[routesCount / 2][0])
            naverMap.moveCamera(
                CameraUpdate.scrollAndZoomTo(centerLatLng, zoomRatio)
                    .animate(CameraAnimation.Fly, 2000)
            )

            path.apply {
                coords = pathContainer.drop(1)
                color = Color.RED
                map = naverMap
            }
        })
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.onMapClickListener = NaverMap.OnMapClickListener { _, _ ->
            infoWindow.close()
        }

//        val locationSource = FusedLocationSource(this, 1000)
//        naverMap.locationSource = locationSource
//
//        val locationOverlay = naverMap.locationOverlay
//        locationOverlay.isVisible = true
//
//        naverMap.locationTrackingMode = LocationTrackingMode.Follow
//
//        naverMap.addOnCameraChangeListener { _, _ ->
//            currentCameraPosition = naverMap.cameraPosition
//        }
//
//        naverMap.addOnLocationChangeListener { location ->
//            Toast.makeText(this, "${location.latitude}, ${location.longitude}",
//                Toast.LENGTH_SHORT).show()
//        }
    }
}
