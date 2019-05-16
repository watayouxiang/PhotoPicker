package com.wata.photopicker;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

class PermissionRequest {
    private Listener mPermissionListener;
    private int REQUEST_CODE_PERMISSIONS = 1;

    /**
     * 申请运行时权限
     */
    void requestRuntimePermission(Activity activity, String[] permissions, Listener listener) {
        mPermissionListener = listener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取所有未同意的权限
            List<String> unPermissionList = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    unPermissionList.add(permission);
                }
            }
            if (!unPermissionList.isEmpty()) {
                //去申请权限，将回调到onRequestPermissionsResult
                ActivityCompat.requestPermissions(activity, unPermissionList.toArray(new String[0]), REQUEST_CODE_PERMISSIONS);
            } else {
                listener.onGranted();
            }
        } else {
            listener.onGranted();
        }
    }

    /**
     * 权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0) {
                //获取被拒绝的权限
                List<String> deniedPermissionList = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];//权限状态
                    String permission = permissions[i];//权限名称
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissionList.add(permission);
                    }
                }
                if (deniedPermissionList.isEmpty()) {
                    mPermissionListener.onGranted();
                } else {
                    mPermissionListener.onDenied(deniedPermissionList);
                }
            }
        }
    }

    interface Listener {
        /**
         * 所有权限已同意
         */
        void onGranted();

        /**
         * 被拒绝权限
         *
         * @param deniedPermissions
         */
        void onDenied(List<String> deniedPermissions);
    }
}
