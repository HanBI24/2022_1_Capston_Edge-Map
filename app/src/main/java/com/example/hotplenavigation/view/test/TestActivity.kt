package com.example.hotplenavigation.view.test

import com.example.hotplenavigation.R
import com.example.hotplenavigation.base.BindingActivity
import com.example.hotplenavigation.databinding.ActivityTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : BindingActivity<ActivityTestBinding>(R.layout.activity_test) {
    override fun initView() {
        binding.tv.text = "Test Activity"
    }
}
