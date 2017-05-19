package com.maddog05.maddogutilities.android;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by andreetorres on 1/05/17.
 */

public class Checkers {
    /**
     * Check if an application is installed checking its packageName.
     *
     * @param context, Application context.
     * @param packageName, Package name to find.
     * @return true if packageName was founded, false otherwise.
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean isAppInstalled;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            isAppInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            isAppInstalled = false;
        }
        return isAppInstalled;
    }

    /**
     * Chech if internet is available.
     *
     * @param context, Application context.
     * @return true if internet is available, false otherwise.
     */
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cmc = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cmc.getActiveNetworkInfo();
        return activeNetwork != null &&
                (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                        || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE);
    }
}
