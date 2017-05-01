package com.maddog05.maddogutilities.callback;

/**
 * Created by andree on 27/04/2017.
 */

public interface Callback<T> {
    /**
     * done for any response.
     */
    void done(T t);
}
