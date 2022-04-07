package com.example.hotplenavigation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.hotplenavigation.util.PixelRatio
import com.example.hotplenavigation.util.PreferenceUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HotPleNavigationApplication : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        prefs = PreferenceUtil(this)
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        pixelRatio = PixelRatio(this)
    }

    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var pixelRatio: PixelRatio
        lateinit var instance: HotPleNavigationApplication
    }
}
