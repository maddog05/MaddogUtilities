package com.maddog05.maddogutilities.android;

import android.graphics.Color;

/**
 * Created by andreetorres on 10/05/17.
 */

public class Colors {
    private static final float MATERIAL_DESIGN_DARK_FACTOR = 0.8f;

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
}
