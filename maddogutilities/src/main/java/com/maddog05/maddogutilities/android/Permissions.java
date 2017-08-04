package com.maddog05.maddogutilities.android;

import android.content.pm.PackageManager;

/**
 * Created by andreetorres on 1/05/17.
 */

public class Permissions {
    /**
     * Check if all requested permissions are granted
     * @param grantResults result from onRequestPermissionResult
     * @return true if permissions are granted, false otherwise
     */
    public static boolean isPermissionGranted(int[] grantResults) {
        if (grantResults.length < 1)
            return false;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
