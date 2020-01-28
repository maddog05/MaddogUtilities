package com.maddog05.maddogutilities.extension

import android.util.Patterns
import android.webkit.URLUtil

fun String.isEmailValid(): Boolean {
    val email = this.trim()
    if (email.isEmpty()) return false
    return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun String.isHttpUrlValid(): Boolean {
    val url = this.trim()
    if (url.isEmpty())
        return false
    return url.isNotEmpty() && URLUtil.isValidUrl(url)
}

fun String.capitalize(): String {
    val text = this
    val array = text.toCharArray()
    array[0] = Character.toUpperCase(array[0])

    for (i in 1 until array.size) {
        if (Character.isWhitespace(array[i - 1])) {
            array[i] = Character.toUpperCase(array[i])
        }
    }
    return String(array)
}