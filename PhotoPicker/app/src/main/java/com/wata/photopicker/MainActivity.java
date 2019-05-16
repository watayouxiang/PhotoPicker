package com.wata.photopicker;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PermissionRequest mPermissionRequest = new PermissionRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPermissionRequest.requestRuntimePermission(
                        MainActivity.this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        new PermissionRequest.Listener() {
                            @Override
                            public void onGranted() {
                                showToast("onGranted");
                            }

                            @Override
                            public void onDenied(List<String> deniedPermissions) {
                                showToast(deniedPermissions.toString());
                            }
                        });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showToast(String rootPath) {
        Toast.makeText(this, rootPath, Toast.LENGTH_SHORT).show();
    }
}
