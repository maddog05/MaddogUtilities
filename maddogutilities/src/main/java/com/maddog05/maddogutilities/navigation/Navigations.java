package com.maddog05.maddogutilities.navigation;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by andreetorres on 1/05/17.
 */

public class Navigations {
    /**
     * Intent to navigate with Waze.
     *
     * @param context,   Context to start waze or Play Store.
     * @param latitude,  Latitude.
     * @param longitude, Longitude.
     */
    public static void goWithWaze(Context context, double latitude, double longitude) {
        try {
            String uri = "waze://?ll=" + latitude + "," + longitude + "&navigate=yes";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            context.startActivity(intent);
        } catch (ActivityNotFoundException anfE) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.waze"));
            context.startActivity(intent);
        }
    }
}
