package com.maddog05.sampleutils.impl;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import android.widget.ImageView;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maddog05.maddogutilities.callback.Callback;
import com.maddog05.maddogutilities.image.ImageLoader;

/**
 * Created by andreetorres on 16/09/17.
 */

public class FrescoLoader implements ImageLoader {
    private Context context;
    private Callback<Boolean> callback;
    private int resDrawable;
    private String path;
    private SimpleDraweeView draweeView;

    public static FrescoLoader create() {
        return new FrescoLoader();
    }

    private FrescoLoader() {
        resDrawable = -1;
    }

    @Override
    public ImageLoader with(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public ImageLoader placeholder(@DrawableRes int resDrawable) {
        this.resDrawable = resDrawable;
        return this;
    }

    @Override
    public ImageLoader callback(Callback<Boolean> callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public ImageLoader load(String path) {
        this.path = path;
        return this;
    }

    @Override
    public ImageLoader target(ImageView imageView) {
        if (imageView instanceof SimpleDraweeView)
            draweeView = (SimpleDraweeView) imageView;
        return this;
    }

    @Override
    public void start() {
        try {
            ProgressBarDrawable progressBarDrawable = new ProgressBarDrawable();
            progressBarDrawable.setColor(ContextCompat.getColor(
                    context, com.maddog05.sampleutils.R.color.colorAccent));
            progressBarDrawable.setBackgroundColor(ContextCompat.getColor(
                    context, com.maddog05.sampleutils.R.color.colorPrimary));
            progressBarDrawable.setRadius(context.getResources()
                    .getDimensionPixelSize(com.maddog05.sampleutils.R.dimen.progress_radius_fresco));
            draweeView.getHierarchy().setProgressBarImage(progressBarDrawable);
            Uri uri = Uri.parse(path);
            if (resDrawable != -1) {
                draweeView.getHierarchy().setPlaceholderImage(resDrawable, ScalingUtils.ScaleType.FIT_CENTER);
                draweeView.getHierarchy().setFailureImage(resDrawable, ScalingUtils.ScaleType.FIT_CENTER);
                draweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
            }
            draweeView.setImageURI(uri);
            if (callback != null)
                callback.done(true);
        } catch (Exception e) {
            if (callback != null)
                callback.done(false);
        }
    }
}
