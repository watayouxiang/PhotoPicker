package com.example.xuhong.takephotoandroidn_master.take_photo;

import android.Manifest;
import android.app.Activity;

import java.util.List;

public class PhotoManager {
    private Activity mActivity;
    private boolean mIsActicity;
    //FileProvider的主机名：一般是包名+".fileprovider"
    private String mFileProviderAuthority;
    //临时存储相片地址
    private String mImgPath;
    private PhotoHelper mPhotoTool;
    private PermissionRequest mPermissionRequest = new PermissionRequest();
    private PhotoManagerListener mTakePhotoListener;

    public PhotoManager(Activity activity) {
        mPhotoTool = new PhotoHelper(activity);
        mActivity = activity;
        mIsActicity = true;
        mFileProviderAuthority = mActivity.getPackageName() + ".fileprovider";
    }

    /**
     * 开始从图库获取
     */
    public void takePhotoByAlbum() {
        mImgPath = Utils.generateImgPath(mActivity);
        mPermissionRequest.requestRuntimePermission(mActivity,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                new PermissionRequestListener() {
                    @Override
                    public void onGranted() {
                        mPhotoTool.startAlbum();
                    }

                    @Override
                    public void onDenied(List<String> deniedPermissions) {
                        mTakePhotoListener.onPermissionDenied(deniedPermissions);
                    }
                });
    }


}
