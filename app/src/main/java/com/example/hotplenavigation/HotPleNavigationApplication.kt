package com.example.hotplenavigation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.hotplenavigation.util.PixelRatio
import com.example.hotplenavigation.util.PreferenceUtil
import dagger.hilt.android.HiltAndroidApp

// 상위 계층에서 Context를 얻어오기 위해 사용하는 Application Class
// DI, Extension에서 많ㄴ이 사용
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
