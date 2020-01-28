package com.maddog05.maddogutilities.extension

import java.io.IOException
import java.text.DecimalFormat

fun Double.toStringWithNDecimals(count: Int = 2): String {
    if (count <= 0)
        throw IOException("Count must be more than 0")
    else {
        var formatText = "#0."
        for (i in 1..count)
            formatText += "0"
        val formatter = DecimalFormat(formatText)
        return formatter.format(this)
    }
}

fun Int.isLeapYear(): Boolean {
    return (this > 0) && (this % 4 == 0) && (this % 100 != 0 || this % 400 == 0)
}

fun Int.toStringWithNNumbers(count: Int = 2): String {
    if (count <= 0)
        throw IOException("Count must be more than 0")
    else {
        val formatText = "%0${count}d"
        return String.format(formatText, this)
    }
}

fun Long.isLeapYear(): Boolean = this.toInt().isLeapYear()
