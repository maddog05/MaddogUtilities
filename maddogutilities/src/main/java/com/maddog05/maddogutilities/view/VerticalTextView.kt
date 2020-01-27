package com.maddog05.maddogutilities.view

import android.content.Context
import android.graphics.Canvas

import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import com.maddog05.maddogutilities.R

/*
 * Created by andreetorres on 10/02/18.
 */
class VerticalTextView(context: Context, attrs: AttributeSet, defStyleAttr: Int) : AppCompatTextView(context, attrs, defStyleAttr) {
    private var topDown = false

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.VerticalTextView)
        topDown = attributes.getBoolean(R.styleable.VerticalTextView_vtv_alignBottomInStart, true)
        attributes.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }

    override fun onDraw(canvas: Canvas) {
        val textPaint = paint
        textPaint.color = currentTextColor
        textPaint.drawableState = drawableState

        canvas.save()

        if (topDown) {
            canvas.translate(width.toFloat(), 0f)
            canvas.rotate(90f)
        } else {
            canvas.translate(0f, height.toFloat())
            canvas.rotate(-90f)
        }


        canvas.translate(compoundPaddingLeft.toFloat(), extendedPaddingTop.toFloat())

        layout.draw(canvas)
        canvas.restore()
    }
}