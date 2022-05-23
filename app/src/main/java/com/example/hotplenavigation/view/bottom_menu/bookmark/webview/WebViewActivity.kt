package com.example.hotplenavigation.view.bottom_menu.bookmark.webview

import android.webkit.WebViewClient
import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.databinding.ActivityWebBinding

class WebViewActivity : BindingActivity<ActivityWebBinding>(R.layout.activity_web) {

    override fun initView() {
        val address = intent.getStringExtra("get_address")

        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportMultipleWindows(true)
        }

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl("https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=$address")
        }
    }
}
