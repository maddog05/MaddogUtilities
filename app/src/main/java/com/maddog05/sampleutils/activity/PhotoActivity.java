package com.maddog05.sampleutils.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.maddog05.maddogutilities.image.Images;
import com.maddog05.maddogutilities.logger.Logger;
import com.maddog05.sampleutils.R;

public class PhotoActivity extends AppCompatActivity implements Logger {

    public static final int RESULT_LOAD_IMAGE = 102;
    public static final int RESULT_TAKE_PICTURE = 103;
    public static final int MAX_DIMENSION_PIXELS = 512;

    private AppCompatImageView photoIv;
    private AppCompatButton encodeBtn;

    private String pathPhoto;
    private Bitmap bitmap;
    private boolean withCompression = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        AppCompatCheckBox compressionCb = (AppCompatCheckBox) findViewById(R.id.cb_photo_compression);
        compressionCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                withCompression = isChecked;
            }
        });

        photoIv = (AppCompatImageView) findViewById(R.id.iv_photo_preview);
        encodeBtn = (AppCompatButton) findViewById(R.id.btn_photo_encode);
        encodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog _dialog = new ProgressDialog(PhotoActivity.this);
                _dialog.setMessage(getString(R.string.dialog_encoding_image));
                _dialog.setCancelable(false);
                _dialog.setIndeterminate(true);
                _dialog.show();
                new Images.EncodeBitmapBase64AsyncTask(bitmap) {
                    @Override
                    protected void onPostExecute(String encoded64) {
                        _dialog.dismiss();
                        logI("Encoded complete");
                    }
                }.execute();
            }
        });
        findViewById(R.id.btn_photo_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        findViewById(R.id.btn_photo_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    Images.InputPhoto pair = Images.getInputPhotoCamera(PhotoActivity.this, "com.maddog05.fileprovider");
                    if (pair.uri != null) {
                        pathPhoto = pair.path;
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pair.uri);
                        startActivityForResult(cameraIntent, RESULT_TAKE_PICTURE);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Images.OutputPhoto pair = Images.getOutputPhotoGallery(PhotoActivity.this, data);
                if (pair.bitmap != null) {
                    pathPhoto = pair.path;
                    loadBitmap(pair.bitmap);
                    encodeBtn.setEnabled(true);
                }
            }

        } else if (requestCode == RESULT_TAKE_PICTURE && resultCode == RESULT_OK) {
            Images.OutputPhoto pair = Images.getOutputPhotoCamera(PhotoActivity.this, data, pathPhoto);
            if (pair.bitmap != null) {
                loadBitmap(pair.bitmap);
                encodeBtn.setEnabled(true);
            }
        }
    }

    private void loadBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        photoIv.setImageBitmap(bitmap);
    }

    @Override
    public String logTag() {
        return "PhotoActivity";
    }

    @Override
    public void logV(String message) {

    }

    @Override
    public void logD(String message) {

    }

    @Override
    public void logI(String message) {
        Log.i(logTag(), message);
    }

    @Override
    public void logW(String message) {

    }

    @Override
    public void logE(String message) {

    }
}