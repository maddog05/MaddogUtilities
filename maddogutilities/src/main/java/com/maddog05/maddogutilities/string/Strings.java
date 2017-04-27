package com.maddog05.maddogutilities.string;

import android.webkit.URLUtil;

/**
 * Created by andree on 27/04/2017.
 */

public class Strings {
    public static boolean isStringUrl(String url) {
        return url != null && !url.isEmpty() && URLUtil.isValidUrl(url);
    }

    public static String valueOfIntegerTwoNumbers(int n) {
        return String.format("%02d", n);
    }
}
