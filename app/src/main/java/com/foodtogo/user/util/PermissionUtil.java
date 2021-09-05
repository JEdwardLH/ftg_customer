package com.foodtogo.user.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

public class PermissionUtil {

    public static final int CAMERA_GALLERY_REQUEST = 101;
    public static final String READ_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;


    public static boolean checkImagePermission(Context context) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, READ_STORAGE_PERMISSION) &&
                PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, WRITE_STORAGE_PERMISSION) &&
                PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, CAMERA_PERMISSION);
    }


    public static boolean isPermissionGranted(int[] results) {
        return results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED;
    }
}


