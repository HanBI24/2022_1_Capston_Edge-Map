package com.example.hotplenavigation.view.bottom_menu.search.search_result.bottom_sheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.example.hotplenavigation.R
import com.example.hotplenavigation.databinding.FragmentBottomSheetBinding
import com.example.hotplenavigation.util.extension.setNaverMapRender
import com.example.hotplenavigation.view.bottom_menu.bookmark.webview.WebViewActivity
import com.example.hotplenavigation.view.bottom_menu.search.search_result.SearchResultActivityViewModel
import com.github.heyalex.bottomdrawer.BottomDrawerFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint

// 검색 결과 화면에서 사용자가 누른 마커의 정보를 출력해주는 Fragment
@AndroidEntryPoint
class BottomSheetFragment : BottomDrawerFragment(), OnMapReadyCallback {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val searchResultActivityViewModel: SearchResultActivityViewModel by activityViewModels()
    private val bottomSheetFragmentViewModel: BottomSheetFragmentViewModel by activityViewModels()
    private lateinit var naverMap: NaverMap
    private lateinit var marker: Marker
    private lateinit var infoWindow: InfoWindow

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        setNaverMapRender(R.id.container_map_temp, childFragmentManager, this)

        // 마커 추가
        marker = Marker()
        // 태그 설정
        marker.tag = searchResultActivityViewModel.bottomTitle.value
        // 위치 설정
        marker.position = LatLng(
            searchResultActivityViewModel.bottomMarker.value?.position?.latitude!!,
            searchResultActivityViewModel.bottomMarker.value?.position?.longitude!!
        )

        binding.apply {
            // 해당 위치의 이름과 주소 및 사진 출력
            tvTitle.text = searchResultActivityViewModel.bottomTitle.value
            tvAddress.text = searchResultActivityViewModel.bottomAddress.value
            ivThumb.load("https://picsum.photos/200/300") {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
                memoryCachePolicy(CachePolicy.DISABLED)
            }
        }

        // 버튼을 누르면 WebView Activity 호출
        binding.btnWeb.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("get_address", searchResultActivityViewModel.bottomAddress.value)
            startActivity(intent)
        }

        observeData()
        setClickListenerMethod()

        return binding.root
    }

    private fun setClickListenerMethod() {
        // 별 표시를 누르면 즐겨찾기에 추가 및 Local DB에 저장
        // (Local DB, Android Jetpack Room Library)
        binding.ivBookmark.setOnClickListener {
            when (it.tag) {
                "none" -> {
                    bottomSheetFragmentViewModel.bookmarkPlace.value?.like = true
                    searchResultActivityViewModel.bookmarkData.value?.like = true
                    bottomSheetFragmentViewModel.insertBookmark(searchResultActivityViewModel.bookmarkData.value!!)
                    binding.ivBookmark.setImageResource(R.drawable.ic_star_fill)
                    binding.ivBookmark.tag = "set"
                    Snackbar.make(
                        binding.mainContainer, "저장되었습니다",
                        BaseTransientBottomBar.LENGTH_SHORT
                    ).show()
                }
                "set" -> {
                    bottomSheetFragmentViewModel.bookmarkPlace.value?.like = false
                    searchResultActivityViewModel.bookmarkData.value?.like = false
                    bottomSheetFragmentViewModel.deleteByTitle(searchResultActivityViewModel.bookmarkData.value!!.title)
                    binding.ivBookmark.setImageResource(R.drawable.ic_star)
                    binding.ivBookmark.tag = "none"
                    Snackbar.make(
                        binding.mainContainer, "삭제되었습니다.",
                        BaseTransientBottomBar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun observeData() {
        // 즐겨찾기 목록이 추가되면 해당 정보를 LiveData에 저장
        bottomSheetFragmentViewModel.bookmarkPlace.observe(this, {
            bottomSheetFragmentViewModel.setPlaceInfo(it)
        })
    }

    override fun onResume() {
        super.onResume()
        // 해당 Fragment가 시작하면 Local DB에 저장되어있는 좋아요를 기준으로
        // DB에 저장되어있는 해당 리스트 출력
        bottomSheetFragmentViewModel.getAllLikeData().observe(this, { it ->
            it.iterator().forEach {
                if (it.title == binding.tvTitle.text.toString()) {
                    binding.ivBookmark.setImageResource(R.drawable.ic_star_fill)
                    binding.ivBookmark.tag = "set"
                }
            }
        })
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        marker.map = naverMap
        infoWindow = InfoWindow()

        // 마커 정보 창
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(context!!) {
            override fun getText(p0: InfoWindow): CharSequence {
                return p0.marker?.tag as CharSequence
            }
        }

        infoWindow.open(marker)

        // 사용자에게 보여지는 네이버 맵 위치 이동 (이전 화면에서 선택한 마커 위치로)
        naverMap.moveCamera(
            CameraUpdate.toCameraPosition(
                CameraPosition(
                    LatLng(
                        searchResultActivityViewModel.bottomMarker.value?.position?.latitude!!,
                        searchResultActivityViewModel.bottomMarker.value?.position?.longitude!!
                    ),
                    13.0
                )
            )
        )
    }
}
