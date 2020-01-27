package com.maddog05.sampleutils.activity;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.maddog05.maddogutilities.callback.Callback;
import com.maddog05.maddogutilities.image.ImageLoader;
import com.maddog05.sampleutils.R;
import com.maddog05.sampleutils.impl.FrescoLoader;
import com.maddog05.sampleutils.impl.GlideLoader;

public class ImageLoaderActivity extends AppCompatActivity {

    private AppCompatImageView photoIv;
    private AppCompatEditText urlEt;
    private ProgressBar loadingPbar;
    private SimpleDraweeView photoDrawee;

    private boolean isGlideSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        urlEt = findViewById(R.id.et_url);
        photoIv = findViewById(R.id.iv_photo_preview);
        photoDrawee = findViewById(R.id.drawee_fresco);
        loadingPbar = findViewById(R.id.pbar_loading);
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
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgroup_image_loader);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                isGlideSelected = i == R.id.rb_glide;
            }
        });
    }

    private void _showImage(String url) {
        photoIv.setVisibility(isGlideSelected ? View.VISIBLE : View.INVISIBLE);
        photoDrawee.setVisibility(!isGlideSelected ? View.VISIBLE : View.GONE);

        loadingPbar.setVisibility(View.VISIBLE);
        ImageLoader imageLoader = isGlideSelected ? GlideLoader.create() : FrescoLoader.create();
        imageLoader
                .with(ImageLoaderActivity.this)
                .load(url)
                .target(isGlideSelected ? photoIv : photoDrawee)
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
