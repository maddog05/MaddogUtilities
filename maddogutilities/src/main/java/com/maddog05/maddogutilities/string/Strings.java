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

    /**
     * Concat two string, first with bold and second with normal
     *
     * @param boldText   bold
     * @param normalText normal
     * @return CharSequence ready to use in setText(result)
     */
    public static CharSequence boldConcatNormalText(String boldText, String normalText) {
        SpannableString txtSpannable = new SpannableString(boldText);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        txtSpannable.setSpan(boldSpan, 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return TextUtils.concat(txtSpannable, normalText);
    }

    /**
     * Concat two strings, first with normal and second with bold
     *
     * @param normalText normal
     * @param boldText   bold
     * @return CharSequence ready to use in setText(result)
     */
    public static CharSequence normalConcatBoldText(String normalText, String boldText) {
        SpannableString txtSpannable = new SpannableString(boldText);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        txtSpannable.setSpan(boldSpan, 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return TextUtils.concat(normalText, txtSpannable);
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
}
