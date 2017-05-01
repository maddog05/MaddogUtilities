package com.maddog05.maddogutilities.android;

import android.content.pm.PackageManager;

/**
 * Created by andreetorres on 1/05/17.
 */

public class Permissions {
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
