package com.maddog05.maddogutilities.string

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.StyleSpan

class TextTypefaceBuilder {
    var response: CharSequence = ""

    fun addNormal(text: String): TextTypefaceBuilder {
        add(text, Typeface.NORMAL)
        return this
    }

    fun addBold(text: String): TextTypefaceBuilder {
        add(text, Typeface.BOLD)
        return this
    }

    fun addItalic(text: String): TextTypefaceBuilder {
        add(text, Typeface.ITALIC)
        return this
    }

    fun addBoldItalic(text: String): TextTypefaceBuilder {
        add(text, Typeface.BOLD_ITALIC)
        return this
    }

    private fun add(text: String, typeface: Int) {
        val spannableString = SpannableString(text)
        val styleSpan = StyleSpan(typeface)
        spannableString.setSpan(styleSpan, 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        response = TextUtils.concat(response, spannableString)
    }

    fun clear() {
        response = ""
    }

    fun build(): CharSequence? {
        return response
    }
}