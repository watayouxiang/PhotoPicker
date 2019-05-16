package com.example.xuhong.takephotoandroidn_master.take_photo;

import java.util.List;

public interface PhotoManagerListener {
    void onPermissionDenied(List<String> deniedPermissions);
}
