package com.maddog05.maddogutilities.string;

import android.util.Patterns;
import android.webkit.URLUtil;

/**
 * Created by andree on 27/04/2017.
 */

public class Strings {

    /**
     * Check if text is a valid email.
     *
     * @param email, email to validate.
     * @return true if text is valid email, false otherwise.
     */
    public static boolean isStringEmail(String email) {
        if (email != null)
            email = email.trim();
        else
            return false;
        email = email.trim();
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Check if text is a valid url.
     *
     * @param url, url to validate.
     * @return true if text is valid url, false otherwise.
     */
    public static boolean isStringUrl(String url) {
        if (url != null)
            url = url.trim();
        else
            return false;
        return !url.isEmpty() && URLUtil.isValidUrl(url);
    }

    /**
     * Capitalize text.
     *
     * @param text, text to capitalize.
     * @return text capitalized.
     */
    public String capitalize(String text) {
        char[] array = text.toCharArray();
        array[0] = Character.toUpperCase(array[0]);

        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }

        return new String(array);
    }
}
