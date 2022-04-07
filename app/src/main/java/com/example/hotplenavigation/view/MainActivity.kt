package com.example.hotplenavigation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotplenavigation.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
