package com.maddog05.maddogutilities.logger;

/**
 * Created by andree on 27/04/2017.
 */

public interface Logger {
    String logTag();

    void logV(String message);

    void logD(String message);

    void logI(String message);

    void logW(String message);

    void logE(String message);
}
