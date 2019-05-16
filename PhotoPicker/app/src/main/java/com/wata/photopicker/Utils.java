package com.wata.photopicker;

import android.content.Context;
import android.os.Environment;

import java.io.File;

class Utils {

    static File createDir(Context context, String dirPath) {
        String rootPath = getRootPath(context);
        File dir = new File(rootPath + dirPath);
        boolean mkdirs = dir.mkdirs();
        if (mkdirs) {
            return dir;
        } else {
            return null;
        }
    }

    static String getRootPath(Context context) {
        //有sd卡则获取sd卡目录，否则获取缓存目录
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return context.getCacheDir().getAbsolutePath();
        }
    }
}
