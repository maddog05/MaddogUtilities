package com.maddog05.maddogutilities.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.maddog05.maddogutilities.R;

/**
 * Created by andreetorres on 13/05/17.
 */

/**
 * subclass of {@link AppCompatImageView AppCompatImageView} to create perfect square view
 */
public class SquareImageView extends AppCompatImageView {

    private boolean useHorizontal = true;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView);
        useDimensionHorizontal(attributes.getBoolean(R.styleable.SquareImageView_siv_useDimensionHorizontal, true));
        attributes.recycle();
    }

    private void useDimensionHorizontal(boolean useHorizontal) {
        this.useHorizontal = useHorizontal;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int dimension = useHorizontal ? getMeasuredWidth() : getMeasuredHeight();
        setMeasuredDimension(dimension, dimension);
    }
}
