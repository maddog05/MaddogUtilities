package com.maddog05.sampleutils;

import android.app.Application;

import com.maddog05.maddogutilities.logger.Logger2;

/**
 * Created by andree on 04/08/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger2.get().setEnabled(true);
    }
}
