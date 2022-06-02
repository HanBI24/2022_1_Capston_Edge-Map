package com.example.hotplenavigation.view.bottom_menu.bookmark.webview

import android.webkit.WebViewClient
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.databinding.ActivityWebBinding
import dagger.hilt.android.AndroidEntryPoint

// 20197138 장은지
// 해당 위치 정보를 더욱 자세히 알기 위한 WebView Activity
@AndroidEntryPoint
class WebViewActivity : BindingActivity<ActivityWebBinding>(R.layout.activity_web) {

    override fun initView() {
        // 사용자가 클릭한 해당 위치의 주소를 가져옴
        val address = intent.getStringExtra("get_address")
        val recommend = intent.getStringExtra("get_recommend_title")

        // 웹뷰 초기 설정
        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportMultipleWindows(true)
        }

        // 웹뷰 띄우기
        binding.webView.apply {
            webViewClient = WebViewClient()
            if (address == null) {
                loadUrl("https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=$recommend")
            } else {
                loadUrl("https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=$address")
            }
        }
    }
}
