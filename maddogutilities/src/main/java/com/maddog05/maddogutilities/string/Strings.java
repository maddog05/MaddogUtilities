package com.maddog05.maddogutilities.string;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Patterns;
import android.webkit.URLUtil;

/*
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

    /**
     * Copy simple text to clipboard
     *
     * @param context     access to context
     * @param labelToUser message to notify clipboard copied
     * @param textToCopy  text to clipboard
     */
    public static void copyToCipboard(Context context, String labelToUser, String textToCopy) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(labelToUser, textToCopy);
        clipboardManager.setPrimaryClip(clipData);
    }

    /**
     * Create CharSequence with normal, bold and italic text styles
     */
    public static class CharSequenceStyle {
        private CharSequence response;

        public CharSequenceStyle() {
            response = "";
        }

        public CharSequenceStyle addNormal(String text) {
            add(text, Typeface.NORMAL);
            return this;
        }

        public CharSequenceStyle addBold(String text) {
            add(text, Typeface.BOLD);
            return this;
        }

        public CharSequenceStyle addItalic(String text) {
            add(text, Typeface.ITALIC);
            return this;
        }

        public CharSequenceStyle addBoldItalic(String text) {
            add(text, Typeface.BOLD_ITALIC);
            return this;
        }

        private void add(String text, int typeface) {
            SpannableString spannableString = new SpannableString(text);
            StyleSpan styleSpan = new StyleSpan(typeface);
            spannableString.setSpan(styleSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            response = TextUtils.concat(response, spannableString);
        }

        public void clear() {
            response = "";
        }

        public CharSequence build() {
            return response;
        }
    }
}
