package com.example.hotplenavigation.view.bottom_menu.search.search_result

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.database.BookmarkFragmentEntity
import com.example.hotplenavigation.databinding.FragmentSearchResultBinding
import com.example.hotplenavigation.util.extension.setNaverMapRender
import com.example.hotplenavigation.view.bottom_menu.search.search_result.bottom_sheet.BottomSheetFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

// 검색 결과 Activity
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
    private lateinit var locationCallback: LocationCallback
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentCameraPosition: CameraPosition

    @SuppressLint("MissingPermission")
    override fun initView() {
        val word = intent.getStringExtra("search_fragment")
        setNaverMapRender(R.id.map_fragment, supportFragmentManager, this)

        // 현재 위치 찾기
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
//            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 3000
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(location: LocationResult) {
                super.onLocationResult(location)
                currentLocation = location.lastLocation
            }
        }

        Looper.myLooper()?.let {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                it
            )
        }

        // 지역 검색어의 위치를 위도, 경도로 바꿈
        if (word != null) {
            searchResultActivityViewModel.getInitialGeoApi(
                "uzlzuhd2pa",
                "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                word
            )
        }

        // API 호출을 통해 GeoCode를 얻어오면
        searchResultActivityViewModel.getInitialGeoCode.observe(this, {
            // 위도, 경도 저장
            LatLngY = it.y.toDouble()
            LatLngX = it.x.toDouble()

            Log.d("SearchFragment", "$LatLngY,$LatLngX")

            // 저장한 위도, 경도 값으로, 현재 위치부터 목적지(검색어)까지 최적의 경로 탐색
            searchResultActivityViewModel.getResultPath(
                "uzlzuhd2pa",
                "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                "${currentLocation.longitude},${currentLocation.latitude}",
                "$LatLngX,$LatLngY",
                "traavoidtoll"
            )
        })

        // 마커 정보 창 객체 생성
        infoWindow = InfoWindow()
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(p0: InfoWindow): CharSequence {
                return p0.marker?.tag as CharSequence
            }
        }

        // API 호출을 통해 위도, 경도를 얻어오면
        searchResultActivityViewModel.getCodeLatLng.observe(this, {
            // 마커 객체 생성 및 리스트에 추가
            marker = Marker()
            markerList.add(marker)
            try {
                // 마커 위치 설정
                marker.position = LatLng(
                    it.y.toDouble(),
                    it.x.toDouble()
                )

                // 마커 제목 설정 (음식점, 관광명소 등 이름)
                for (i in searchResultTitleList) {
                    marker.tag = i
                }

                // 마커 주소 설정 (음식점, 관광명소 등 이름)
                for (i in searchResultAddressList) {
                    marker.captionText = i
                }

                // 캡션 사이즈를 0으로 설정해 화면에 보이지 않도록 설정
                marker.captionTextSize = 0.0f

                for (i in markerList) {
                    // 마커 클릭 이벤트
                    i.setOnClickListener {
                        Log.d("SearchResultActivity", i.tag.toString())
                        // 마커 정보 창 열기
                        infoWindow.open(i)
                        // ViewModel의 LiveData에 값 설정 (다른 액티비티 및 Fragment에서도 사용하기 위해)
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
                        // BottomSheetFragment 오픈 소스 활용하여
                        // 사용자가 선택한 곳의 정보 출력
                        sheet.show(supportFragmentManager, "BottomSheetFragment")
                        true
                    }
                }
                // 마커 출력
                marker.map = naverMap
            } catch (npe: NullPointerException) {
            }
        })

        // API 호출을 통해 검색 결과의 응답을 받으면
        searchResultActivityViewModel.searchResult.observe(this, {
            for (i in it) {
                // 해당 장소(음식점, 관광명소 등)의 이름과 주소를 추가
                searchResultTitleList.add(i.title)
                searchResultAddressList.add(i.address)
                Log.d("SearchResultActivity", searchResultTitleList.toString())

                // 주소를 통해 위도, 경도를 얻어오기 위하여 API 호출
                searchResultActivityViewModel.getGeoApi(
                    "uzlzuhd2pa",
                    "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                    i.address
                )
            }
        })

        // API 호출을 통해 GeoCode 값을 얻어오면
        searchResultActivityViewModel.geoCode.observe(this, {
            // Coroutine을 통해 비동기 작업 실행
            CoroutineScope(Dispatchers.IO).launch {
                // API 호출을 통해 얻어온 값을 필터링하여 해당 지역 이름만 가져옴
                val regionName1 = it.region?.area3.toString().split("=")
                val regionName2 = regionName1[1].split(")")
                // 추출한 값을 리스트에 추가
                regionMutableList.add(regionName2[0])
                searchResultActivityViewModel.addRegionMutableLiveList(regionMutableList)

                // API를 호출하여 맛집을 기준으로 검색 결과를 얻어옴
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

        // API 호출을 통해 최적의 경로를 얻어오면
        searchResultActivityViewModel.getResultPath.observe(this, {
            val path = PathOverlay()
            var routesCount = 0
            val pathContainer: MutableList<LatLng> = mutableListOf(LatLng(0.1, 0.1))
            // 해당 경로의 모든 위도, 경도 데이터 받아옴
            if (it != null) {
                for (pathCode in it) {
                    for (pathCodeXY in pathCode.path) {
                        pathContainer.add(LatLng(pathCodeXY[1], pathCodeXY[0]))
                        routesCount++
                    }
                }
            }

            // 해당 경로의 모든 위도, 경도 데이터 임시 리스트에 추가
            if (it != null) {
                for (pathCode in it) {
                    for (pathCodeXY in pathCode.path) {
                        pathGuideX.add(pathCodeXY[1])
                        pathGuideY.add(pathCodeXY[0])
                    }
                }
            }

            // 해당 경로의 위도값의 크기만큼 검색 API 호출함
            for ((kk, _) in pathGuideX.withIndex()) {
                searchResultActivityViewModel.getReverseGeoApi(
                    "uzlzuhd2pa",
                    "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                    "${pathGuideY[kk]},${pathGuideX[kk]}"
                )
            }

            // 경로 그리기
            path.apply {
                coords = pathContainer.drop(1)
                color = Color.RED
                map = naverMap
            }

            // 사용자에게 보여질 지도 위치 조정
            val centerLatLng =
                LatLng(it[0].path[routesCount / 2][1], it[0].path[routesCount / 2][0])
            naverMap.moveCamera(
                CameraUpdate.scrollAndZoomTo(centerLatLng, zoomRatio)
                    .animate(CameraAnimation.Fly, 2000)
            )

            // 경로 그리기
            path.apply {
                coords = pathContainer.drop(1)
                color = Color.RED
                map = naverMap
            }
        })
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        // 네이버 맵을 클릭하면 마커 정보창 끄기
        naverMap.onMapClickListener = NaverMap.OnMapClickListener { _, _ ->
            infoWindow.close()
        }

        // 네이버 맵에 현재 위치 초기화
        val locationSource = FusedLocationSource(this, 1000)
        naverMap.locationSource = locationSource

        // 위치 오버레이 활성화
        val locationOverlay = naverMap.locationOverlay
        locationOverlay.isVisible = true

        // 위치 오버레이 Follow 모드로 전환
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        // 네이버 맵 이동 시 발생하는 이벤트
        naverMap.addOnCameraChangeListener { _, _ ->
            currentCameraPosition = naverMap.cameraPosition
        }

//        naverMap.addOnLocationChangeListener { location ->
//            Toast.makeText(this, "${location.latitude}, ${location.longitude}",
//                Toast.LENGTH_SHORT).show()
//        }
    }
}
