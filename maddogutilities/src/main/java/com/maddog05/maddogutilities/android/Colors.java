package com.maddog05.maddogutilities.android;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by andreetorres on 10/05/17.
 */

public class Colors {
    private static final float MATERIAL_DESIGN_DARK_FACTOR = 0.8f;

    /**
     * Generate dark color
     * @param color base color
     * @return dar color
     */
    public static int getMaterialDarkColor(int color) {
        return manipulateColor(color, MATERIAL_DESIGN_DARK_FACTOR);
    }

    private static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }

    /**
     * Colorate status bar
     * @param activity activity to access getWindow
     * @param color color to apply
     */
    public static void setStatusBarColor(AppCompatActivity activity, int color) {
        if (AndroidVersions.isLollipop())
            activity.getWindow().setStatusBarColor(color);
    }

    /**
     * Colorate navigation bar
     * @param activity activity to access getWindow
     * @param color color to apply
     */
    public static void setNavigationBarColor(AppCompatActivity activity, int color) {
        if (AndroidVersions.isLollipop())
            activity.getWindow().setNavigationBarColor(color);
    }
}
