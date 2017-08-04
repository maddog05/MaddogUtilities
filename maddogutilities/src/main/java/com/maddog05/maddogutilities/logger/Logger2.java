package com.maddog05.maddogutilities.logger;

import android.util.Log;

/**
 * Created by andree on 04/08/2017.
 */

public class Logger2 {
    private boolean isEnabled = false;
    private static Logger2 instance;

    private Logger2() {
    }

    public static synchronized Logger2 get() {
        if (instance == null)
            instance = new Logger2();
        return instance;
    }

    public Logger2 setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public void d(String tag, String message) {
        if (isEnabled)
            Log.d(tag, message);
    }

    public void e(String tag, String message) {
        if (isEnabled)
            Log.e(tag, message);
    }

}
