package com.example.hotplenavigation.util

import android.app.Application
import android.content.SharedPreferences
import javax.inject.Inject

// Local DB 대신 간단한 값을 저장하기 위한 SharedPreference를 사용
// 어느 부분에서든 간단하게 저장 및 사용하기 위해 만든 확장 파일
class PreferenceUtil @Inject constructor(
    application: Application
) {
    private val prefs: SharedPreferences =
        application.getSharedPreferences("prefs_name", Application.MODE_PRIVATE)

    fun getString(key: String, defaultValue: String) =
        prefs.getString(key, defaultValue).toString()

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}
