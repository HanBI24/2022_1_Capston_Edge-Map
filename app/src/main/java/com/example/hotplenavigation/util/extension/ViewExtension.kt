package com.example.hotplenavigation.util.extension

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replace(
    @IdRes frameId: Int,
    fragment: androidx.fragment.app.Fragment
) {
    supportFragmentManager
        .beginTransaction()
        .replace(frameId, fragment, null)
        .commitAllowingStateLoss()
}

fun AppCompatActivity.replaceToBackStack(
    @IdRes frameId: Int,
    fragment: androidx.fragment.app.Fragment
) {
    supportFragmentManager
        .beginTransaction()
        .replace(frameId, fragment, null)
        .addToBackStack(null)
        .commit()
}

fun AppCompatActivity.add(
    fragment: androidx.fragment.app.Fragment
) {
    supportFragmentManager
        .beginTransaction()
        .add(fragment, null)
        .commit()
}
