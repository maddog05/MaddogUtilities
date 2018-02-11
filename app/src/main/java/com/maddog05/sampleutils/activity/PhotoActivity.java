package com.maddog05.sampleutils.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.CompoundButton;

import com.maddog05.maddogutilities.callback.Callback;
import com.maddog05.maddogutilities.image.ImageEncoder;
import com.maddog05.maddogutilities.image.Images;
import com.maddog05.maddogutilities.logger.Logger2;
import com.maddog05.sampleutils.R;

public class PhotoActivity extends AppCompatActivity {

    public static final int RESULT_LOAD_IMAGE = 102;
    public static final int RESULT_TAKE_PICTURE = 103;

    private AppCompatImageView photoIv;
    private AppCompatButton encodeBtn;

    private String pathPhoto;
    private Bitmap bitmap;
    private boolean withCompression = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        AppCompatCheckBox compressionCb = findViewById(R.id.cb_photo_compression);
        compressionCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                withCompression = isChecked;
            }
        });

        photoIv = findViewById(R.id.iv_photo_preview);
        encodeBtn = findViewById(R.id.btn_photo_encode);
        encodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog _dialog = new ProgressDialog(PhotoActivity.this);
                _dialog.setMessage(getString(R.string.dialog_encoding_image));
                _dialog.setCancelable(false);
                _dialog.setIndeterminate(true);
                _dialog.show();
                ImageEncoder.with(bitmap)
                        .callback(new Callback<String>() {
                            @Override
                            public void done(String encoded) {
                                _dialog.dismiss();
                                Logger2.get().d("#Maddog", "encoding result = " + (encoded.isEmpty()))
                                ;
                            }
                        }).encode();
            }
        });
        findViewById(R.id.btn_photo_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(Images.getIntentGallery("Select app"), RESULT_LOAD_IMAGE);
            }
        });
        findViewById(R.id.btn_photo_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = Images.getIntentCamera(getPackageManager());
                if (cameraIntent != null) {
                    Images.InputPhoto pair = Images.getInputPhotoCamera(PhotoActivity.this, "com.maddog05.fileprovider");
                    if (pair.uri != null) {
                        pathPhoto = pair.path;
                        cameraIntent.putExtra(Images.PARAMETER_CAMERA_OUTPUT, pair.uri);
                        startActivityForResult(cameraIntent, RESULT_TAKE_PICTURE);
                    } else {
                        Logger2.get().e("#Andree", "error in create path for image");
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
            Images.OutputPhoto pair = Images.getOutputPhotoCamera(pathPhoto);
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

}
