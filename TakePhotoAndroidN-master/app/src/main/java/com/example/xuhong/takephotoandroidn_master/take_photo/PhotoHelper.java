package com.example.xuhong.takephotoandroidn_master.take_photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class PhotoHelper {

    private boolean isTailor = false;
    private final int CODE_TAILOR_PHOTO = 103;
    private final int CODE_ORIGINAL_PHOTO_ALBUM = 102;
    private Activity mActivity;
    private PhotoHelperListener mListener;

    PhotoHelper(Activity activity) {
        this.mActivity = activity;
    }

    void startAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, CODE_ORIGINAL_PHOTO_ALBUM);
    }

    private void statTailor(File srcFile, File outFile) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Utils.getImageContentUri(mActivity, srcFile), "image/*");

        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边

        //aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        mActivity.startActivityForResult(intent, CODE_TAILOR_PHOTO);
    }

    /**
     * 获取到的相片回调方法，需要在当前的Activity或Fragment中的onActivityResult下调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void attachToActivityForResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_ORIGINAL_PHOTO_ALBUM) {
            albumResult(requestCode, requestCode, data);
        } else if (requestCode == CODE_TAILOR_PHOTO) {
            tailorResult(requestCode, requestCode, data);
        }
    }

    private void tailorResult(int requestCode, int code, Intent data) {

    }

    private void albumResult(int requestCode, int requestCode1, Intent data) {
        Uri sourceUri = data.getData();
        String photoPath = Utils.getPath(mActivity, sourceUri);
        File srcFile = new File(photoPath);

        if (isTailor) {
            //裁剪之后的文件和uri
            File outFile = new File(Utils.generateImgPath(mActivity));
            //发起裁剪请求
            statTailor(srcFile, outFile);
        } else {

        }
    }

}
