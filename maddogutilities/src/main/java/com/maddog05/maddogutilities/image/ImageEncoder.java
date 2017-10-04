package com.maddog05.maddogutilities.image;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import com.maddog05.maddogutilities.callback.Callback;

import java.io.ByteArrayOutputStream;

/**
 * Created by andreetorres on 3/10/17.
 */

/**
 * Base64 encoded from bitmap to {@link String} string with Builder pattern
 */
public class ImageEncoder {
    private static final String ERROR_RESPONSE = "";
    private Bitmap bitmap;
    private Callback<String> callback;

    private ImageEncoder() {

    }

    /**
     * Initializer
     * @param bitmap to encode
     * @return {@link ImageEncoder} new instance
     */
    public static ImageEncoder with(Bitmap bitmap) {
        ImageEncoder encoder = new ImageEncoder();
        encoder.bitmap = bitmap;
        return encoder;
    }

    /**
     * Set callback. In done function, you will have the encoded image. String if process was completed, empty string otherwise
     * @param callback callbank
     * @return {@link ImageEncoder} instance
     */
    public ImageEncoder callback(Callback<String> callback) {
        this.callback = callback;
        return this;
    }

    /**
     * Start encoding
     */
    public void encode() {
        new AsyncTask<Bitmap, Void, String>() {
            @Override
            protected String doInBackground(Bitmap... bitmaps) {
                if (bitmap == null)
                    return ERROR_RESPONSE;
                else if (callback == null)
                    return ERROR_RESPONSE;
                else {
                    String response;
                    try {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] b = stream.toByteArray();
                        response = Base64.encodeToString(b, Base64.DEFAULT);
                    } catch (Exception e) {
                        response = ERROR_RESPONSE;
                    }
                    return response;
                }
            }

            @Override
            protected void onPostExecute(String encodedBitmap) {
                callback.done(encodedBitmap);
            }
        }.execute(bitmap);
    }
}
