package com.maddog05.sampleutils.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.maddog05.maddogutilities.callback.Callback;
import com.maddog05.maddogutilities.image.ImageLoader;
import com.maddog05.sampleutils.R;
import com.maddog05.sampleutils.impl.GlideLoader;

public class ImageLoaderActivity extends AppCompatActivity {
    /*
    * renzo propone crear un customimageview, que reciba la clase que
    * implemente el imageloader
    * y pueda setear sus datos y cargar automatico la imagen
    * */
    private AppCompatImageView photoIv;
    private AppCompatEditText urlEt;
    private ProgressBar loadingPbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        urlEt = (AppCompatEditText) findViewById(R.id.et_url);
        photoIv = (AppCompatImageView) findViewById(R.id.iv_photo_preview);
        loadingPbar = (ProgressBar) findViewById(R.id.pbar_loading);
        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlEt.getText().toString().trim().isEmpty()) {
                    showToast("ERROR");
                } else {
                    _showImage(urlEt.getText().toString());
                }
            }
        });
    }

    private void _showImage(String url) {
        loadingPbar.setVisibility(View.VISIBLE);
        ImageLoader imageLoader = GlideLoader.create();
        imageLoader
                .with(ImageLoaderActivity.this)
                .load(url)
                .target(photoIv)
                .placeholder(R.drawable.ic_photo)
                .callback(new Callback<Boolean>() {
                    @Override
                    public void done(Boolean isCompleted) {
                        if (isCompleted)
                            loadingPbar.setVisibility(View.GONE);
                        else {
                            showToast("ERROR DURING LOAD IMAGE");
                        }
                    }
                })
                .start();
    }

    private void showToast(String text) {
        Toast.makeText(ImageLoaderActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
