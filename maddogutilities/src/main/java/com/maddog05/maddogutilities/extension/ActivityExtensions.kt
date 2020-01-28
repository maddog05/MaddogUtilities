package com.maddog05.maddogutilities.extension

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

private fun calculateMaterialDarkColor(color: Int): Int {
    val factor = 0.8f
    val a = Color.alpha(color)
    val r = Math.round(Color.red(color) * factor)
    val g = Math.round(Color.green(color) * factor)
    val b = Math.round(Color.blue(color) * factor)
    return Color.argb(a,
            Math.min(r, 255),
            Math.min(g, 255),
            Math.min(b, 255))
}

fun Activity.customStatusBarColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        this.window?.statusBarColor = calculateMaterialDarkColor(color)
}

fun Activity.customStatusBarResColor(@ColorRes resColor: Int) {
    customStatusBarColor(ContextCompat.getColor(this, resColor))
}

fun Activity.intentWazeNavigate(latitude: Double, longitude: Double): Intent {
    val uri = "waze://?ll=$latitude,$longitude&navigate=yes"
    return Intent(Intent.ACTION_VIEW, Uri.parse(uri))
}