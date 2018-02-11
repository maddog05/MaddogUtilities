package com.maddog05.maddogutilities.image;

/**
 * Created by andree on 14/09/2017.
 */

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.maddog05.maddogutilities.callback.Callback;

/**
 * Interface to abstract image library implementation.
 * With this you can change your image library without change all your project.
 */
public interface ImageLoader {
    /**
     * Initialize implementation
     *
     * @param context access to context
     * @return {@link ImageLoader ImageLoader} instance of your implementation
     */
    ImageLoader with(Context context);

    /**
     * Image to use until final image is loaded
     *
     * @param resDrawable drawable resource
     * @return {@link ImageLoader ImageLoader} instance of your implementation
     */
    ImageLoader placeholder(@DrawableRes int resDrawable);

    /**
     * Callback to check if process to load image is completed or not
     *
     * @param callback method done in {@link Callback Callback} class return true if loading is completed, false otherwise
     * @return {@link ImageLoader ImageLoader} instance of your implementation
     */
    ImageLoader callback(Callback<Boolean> callback);

    /**
     * Url or path to load
     * @param path url or path
     * @return {@link ImageLoader ImageLoader} instance of your implementation
     */
    ImageLoader load(String path);

    /**
     * ImageView target
     * @param imageView {@link ImageView ImageView} destination
     * @return {@link ImageLoader ImageLoader} instance of your implementation
     */
    ImageLoader target(ImageView imageView);

    /**
     * Start loading
     */
    void start();
}
