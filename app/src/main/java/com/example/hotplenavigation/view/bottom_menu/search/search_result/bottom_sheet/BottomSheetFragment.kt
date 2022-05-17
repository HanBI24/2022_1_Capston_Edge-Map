package com.example.hotplenavigation.view.bottom_menu.search.search_result.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.example.hotplenavigation.R
import com.example.hotplenavigation.databinding.FragmentBottomSheetBinding
import com.example.hotplenavigation.util.extension.setNaverMapRender
import com.example.hotplenavigation.view.bottom_menu.search.search_result.SearchResultActivityViewModel
import com.github.heyalex.bottomdrawer.BottomDrawerFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFragment : BottomDrawerFragment(), OnMapReadyCallback {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val searchResultActivityViewModel: SearchResultActivityViewModel by activityViewModels()
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

        marker = Marker()
        marker.tag = searchResultActivityViewModel.bottomTitle.value
        marker.position = LatLng(
            searchResultActivityViewModel.bottomMarker.value?.position?.latitude!!,
            searchResultActivityViewModel.bottomMarker.value?.position?.longitude!!
        )

        binding.apply {
            tvTitle.text = searchResultActivityViewModel.bottomTitle.value
            tvAddress.text = searchResultActivityViewModel.bottomAddress.value
            ivThumb.load("https://picsum.photos/200/300") {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
                memoryCachePolicy(CachePolicy.DISABLED)
            }
        }


        return binding.root
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        marker.map = naverMap
        infoWindow = InfoWindow()

        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(context!!) {
            override fun getText(p0: InfoWindow): CharSequence {
                return p0.marker?.tag as CharSequence
            }
        }

        infoWindow.open(marker)

        naverMap.moveCamera(
            CameraUpdate.toCameraPosition(
                CameraPosition(
                    LatLng(
                        searchResultActivityViewModel.bottomMarker.value?.position?.latitude!!,
                        searchResultActivityViewModel.bottomMarker.value?.position?.longitude!!
                    ), 13.0
                )
            )
        )
    }
}