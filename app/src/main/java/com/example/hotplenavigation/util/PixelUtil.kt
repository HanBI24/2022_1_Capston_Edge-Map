package com.example.hotplenavigation.util

import android.app.Application
import androidx.annotation.Px
import com.example.hotplenavigation.HotPleNavigationApplication
import javax.inject.Inject
import kotlin.math.roundToInt

// 해당 기기의 픽셀을 가져와 Alert Dialog를 띄울 때 사용하는 확장 파일
class PixelRatio @Inject constructor(
    private val application: Application
) {
    private val displayMetrics
        get() = application.resources.displayMetrics

    val screenWidth
        get() = displayMetrics.widthPixels

    val screenHeight
        get() = displayMetrics.heightPixels

    val screenShort
        get() = screenWidth.coerceAtMost(screenHeight)

    val screenLong
        get() = screenWidth.coerceAtLeast(screenHeight)

    @Px
    fun toPixel(dp: Int) = (dp * displayMetrics.density).roundToInt()

    fun toDP(@Px pixel: Int) = (pixel / displayMetrics.density).roundToInt()
}

val Number.pixel: Int
    @Px get() = HotPleNavigationApplication.pixelRatio.toDP(this.toInt())

val Number.dp: Int
    get() = HotPleNavigationApplication.pixelRatio.toPixel(this.toInt())

val Number.pixelFloat: Float
    @Px get() = HotPleNavigationApplication.pixelRatio.toDP(this.toInt()).toFloat()

val Number.dpFloat: Float
    get() = HotPleNavigationApplication.pixelRatio.toPixel(this.toInt()).toFloat()
