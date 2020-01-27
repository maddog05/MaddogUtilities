package com.maddog05.maddogutilities.view

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
import com.maddog05.maddogutilities.R

/*
 * Created by andreetorres on 10/02/18.
 */
class SquareImageView(context: Context, attrs: AttributeSet, defStyleAttr: Int) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var useHorizontal = true

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView)
        useDimensionHorizontal(attributes.getBoolean(R.styleable.SquareImageView_siv_useDimensionHorizontal, true))
        attributes.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val dimension = if (useHorizontal) {
            measuredWidth
        } else {
            measuredHeight
        }
        setMeasuredDimension(dimension, dimension)
    }

    private fun useDimensionHorizontal(useHorizontal: Boolean) {
        this.useHorizontal = useHorizontal
    }
}