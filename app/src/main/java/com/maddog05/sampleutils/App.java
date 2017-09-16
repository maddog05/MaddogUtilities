package com.maddog05.sampleutils;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.maddog05.maddogutilities.logger.Logger2;

/**
 * Created by andree on 04/08/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //LOGGER
        Logger2.get().setEnabled(true);
        //USE VECTOR SUPPORT
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //FRESCO
        Fresco.initialize(this);
    }
}
