package com.maddog05.maddogutilities.android;

import android.os.Build;

/**
 * Created by andree on 04/08/2017.
 */

public class AndroidVersions {
    /**
     * @return true if is Lollipop or higher, false otherwise
     */
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * @return true if is Marshmallow or higher, false otherwise
     */
    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
