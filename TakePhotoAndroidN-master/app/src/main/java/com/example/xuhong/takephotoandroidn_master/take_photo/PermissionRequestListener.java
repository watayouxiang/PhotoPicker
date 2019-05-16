package com.example.xuhong.takephotoandroidn_master.take_photo;

import java.util.List;

interface PermissionRequestListener {
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
