package com.maddog05.sampleutils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.maddog05.maddogutilities.android.Permissions;
import com.maddog05.sampleutils.activity.BolderDemoActivity;
import com.maddog05.sampleutils.activity.ImageLoaderActivity;
import com.maddog05.sampleutils.activity.NumbersActivity;
import com.maddog05.sampleutils.activity.PhotoActivity;

public class DummyActivity extends AppCompatActivity {

    public static final int PERMISSION_DEMO_PHOTOS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        findViewById(R.id.btn_dummy_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDemoPhoto();
            }
        });
        findViewById(R.id.btn_dummy_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDemoNumbers();
            }
        });
        findViewById(R.id.btn_dummy_image_loader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDemoImageLoader();
            }
        });
        findViewById(R.id.btn_dummy_bolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDemoBolder();
            }
        });
    }

    private void openDemoBolder() {
        startActivity(new Intent(DummyActivity.this, BolderDemoActivity.class));
    }

    private void openDemoImageLoader() {
        startActivity(new Intent(DummyActivity.this, ImageLoaderActivity.class));
    }

    private void openDemoNumbers() {
        startActivity(new Intent(DummyActivity.this, NumbersActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_DEMO_PHOTOS) {
            if (Permissions.isPermissionGranted(grantResults)) {
                openDemoPhoto();
            } else {
                Toast.makeText(DummyActivity.this, R.string.error_need_permissions, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openDemoPhoto() {
        if (ActivityCompat.checkSelfPermission(DummyActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(DummyActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(DummyActivity.this, PhotoActivity.class));
        } else {
            ActivityCompat.requestPermissions(DummyActivity.this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_DEMO_PHOTOS);
        }
    }
}
