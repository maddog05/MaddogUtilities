package com.maddog05.maddogutilities.view

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.maddog05.maddogutilities.R

/*
 * Created by andreetorres on 10/02/18.
 */
class SquareImageView : AppCompatImageView {

    private var useHorizontal = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView)
        useDimensionHorizontal(attributes.getBoolean(R.styleable.SquareImageView_siv_useDimensionHorizontal, true))
        attributes.recycle()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
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