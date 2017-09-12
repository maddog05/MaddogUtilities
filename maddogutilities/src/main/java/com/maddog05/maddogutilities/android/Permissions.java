package com.maddog05.maddogutilities.android;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreetorres on 1/05/17.
 */

public class Permissions {
    /**
     * Check if all requested permissions are granted
     * @param grantResults result from onRequestPermissionResult
     * @return true if permissions are granted, false otherwise
     */
    public static boolean isPermissionGranted(int[] grantResults) {
        if (grantResults.length < 1)
            return false;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Class to manage permissions to request
     */
    public static class Checker{
        private Context context;
        private List<String> permissions;
        private List<String> toRequest;

        public Checker(Context context) {
            this.context = context;
            permissions = new ArrayList<>();
            toRequest = new ArrayList<>();
        }

        /**
         * add permission to checker
         * @param permission string permission from {@link android.Manifest.permission}
         * @return object itself to use builder pattern
         */
        public Checker addPermission(String permission) {
            if (!this.permissions.contains(permission))
                this.permissions.add(permission);
            return this;
        }

        /**
         * check if all permissions to check are granted
         * @return true if all permissions are granted, false otherwise
         */
        public boolean isPermissionsGranted() {
            boolean result = true;
            if (permissions.size() > 0) {
                for (int i = 0; i < permissions.size(); i++) {
                    if (ActivityCompat.checkSelfPermission(context, permissions.get(i)) == PackageManager.PERMISSION_DENIED) {
                        toRequest.add(permissions.get(i));
                        result = false;
                    }
                }
            }
            return result;
        }

        /**
         * get string array of permissions that have PERMISSION_DENIED value.
         * this function must be used after {@link #isPermissionsGranted() isPermissionsGranted} method
         * @return string array of permissions to send
         */
        public String[] getPermissionsToRequest() {
            String[] response = new String[toRequest.size()];
            for (int i = 0; i < toRequest.size(); i++)
                response[i] = toRequest.get(i);
            return response;
        }
    }

}
