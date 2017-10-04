package com.maddog05.maddogutilities.image;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Base64;

import com.maddog05.maddogutilities.android.AndroidVersions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by andreetorres on 1/05/17.
 */

public class Images {

    public static class InputPhoto {
        public String path;
        public Uri uri;
    }

    /**
     * Get InputPhoto, ONLY FOR CAMERA.
     *
     * @param context,      Context to initialize temp file.
     * @param fileProvider, Name of file provider declared in manifest.
     */
    public static InputPhoto getInputPhotoCamera(Context context, String fileProvider) {
        InputPhoto pair = new InputPhoto();
        File photoFile = null;
        try {
            String timeStamp = String.valueOf(System.currentTimeMillis());
            String imageFileName = "maddog05_" + timeStamp + "_";
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            pair.path = image.getAbsolutePath();
            photoFile = image;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        if (photoFile != null) {
            pair.uri = FileProvider.getUriForFile(context,
                    fileProvider,
                    photoFile);
        }
        return pair;
    }

    public static class OutputPhoto {
        public String path;
        public Bitmap bitmap;
    }

    /**
     * Get OutputPhoto, ONLY FOR GALLERY.
     *
     * @param context, Context to initialize temp file.
     * @param data,    Data recovered by activity.
     */
    public static OutputPhoto getOutputPhotoGallery(Context context, Intent data) {
        OutputPhoto pair = new OutputPhoto();
        Uri selectedImage = data.getData();
        pair.path = getPathFromUri(context, selectedImage);
        try {
            pair.bitmap = getBitmap(context, selectedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pair;
    }

    /**
     * Get OutputPhoto, ONLY FOR GALLERY.
     *
     * @param context, Context to initialize temp file.
     * @param data,    Data recovered by activity.
     */
    public static OutputPhoto getOutputPhotoGalleryCompressed(Context context, Intent data, int maxDimensionPixels) {
        OutputPhoto pair = new OutputPhoto();
        Uri selectedImage = data.getData();
        pair.path = getPathFromUri(context, selectedImage);
        try {
            pair.bitmap = getBitmapCompressed(context, selectedImage, maxDimensionPixels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pair;
    }

    /**
     * Get OutputPhoto, ONLY FOR GALLERY.
     *
     * @param context,   Context to initialize temp file.
     * @param data,      Data recovered by activity.
     * @param photoPath, Path to photo temp.
     */
    public static OutputPhoto getOutputPhotoCamera(Context context, Intent data, String photoPath) {
        OutputPhoto pair = new OutputPhoto();
        if (data != null) {
            Uri takenPhotoUri = Uri.parse(photoPath);
            try {
                Bitmap rotated;
                rotated = getBitmap(context, takenPhotoUri);
                if (rotated != null) {
                    pair.bitmap = rotated;
                }
            } catch (Exception e) {
                Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                try {
                    pair.bitmap = getBitmap(context, getImageUri(context, takenImage));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {/*SAMSUNG*/
            try {
                pair.bitmap = getBitmap(
                        context, getImageUri(
                                context, BitmapFactory.decodeFile(photoPath)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return pair;
    }

    /**
     * Get OutputPhoto compressed, ONLY FOR GALLERY.
     *
     * @param context,   Context to initialize temp file.
     * @param data,      Data recovered by activity.
     * @param photoPath, Path to photo temp.
     */
    public static OutputPhoto getOutputPhotoCameraCompressed(Context context, Intent data, String photoPath, int maxDimensionPixels) {
        OutputPhoto pair = new OutputPhoto();
        if (data != null) {
            Uri takenPhotoUri = Uri.parse(photoPath);
            try {
                Bitmap rotated;
                rotated = getBitmap(context, takenPhotoUri);
                if (rotated != null) {
                    pair.bitmap = rotated;
                }
            } catch (Exception e) {
                Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                try {
                    pair.bitmap = getBitmapCompressed(context, getImageUri(context, takenImage), maxDimensionPixels);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {/*SAMSUNG*/
            try {
                pair.bitmap = getBitmap(
                        context, getImageUri(
                                context, BitmapFactory.decodeFile(photoPath)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return pair;
    }

    private static Bitmap getBitmap(Context context, Uri photoUri) throws IOException {
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        if (is != null)
            is.close();

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        srcBitmap = BitmapFactory.decodeStream(is);
        if (is != null)
            is.close();
        return srcBitmap;
    }

    private static Bitmap getBitmapCompressed(Context context, Uri photoUri, int maxDimensionPixels) {
        Bitmap srcBitmap = null;
        try {
            InputStream is = context.getContentResolver().openInputStream(photoUri);
            BitmapFactory.Options dbo = new BitmapFactory.Options();
            dbo.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, dbo);
            if (is != null)
                is.close();

            int rotatedWidth, rotatedHeight;
            int orientation = getOrientation(context, photoUri);

            if (orientation == 90 || orientation == 270) {
                rotatedWidth = dbo.outHeight;
                rotatedHeight = dbo.outWidth;
            } else {
                rotatedWidth = dbo.outWidth;
                rotatedHeight = dbo.outHeight;
            }

            is = context.getContentResolver().openInputStream(photoUri);

            if (rotatedWidth > maxDimensionPixels || rotatedHeight > maxDimensionPixels) {
                float widthRatio = ((float) rotatedWidth) / ((float) maxDimensionPixels);
                float heightRatio = ((float) rotatedHeight) / ((float) maxDimensionPixels);
                float maxRatio = Math.max(widthRatio, heightRatio);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = (int) maxRatio;
                srcBitmap = BitmapFactory.decodeStream(is, null, options);
            } else {
                srcBitmap = BitmapFactory.decodeStream(is);
            }
            if (is != null)
                is.close();

            if (orientation > 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(orientation);

                srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                        srcBitmap.getHeight(), matrix, true);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return srcBitmap;
    }

    private static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor == null || cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        int response = cursor.getInt(0);
        cursor.close();
        return response;
    }

    private static String getPathFromUri(Context context, Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            return picturePath;
        } else
            return null;
    }

    private static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    /** Class to encode bitmap to base64 string
     * @deprecated use {@link ImageEncoder}
     */
    @Deprecated
    public static class EncodeBitmapBase64AsyncTask extends AsyncTask<Void, Void, String> {
        Bitmap _bitmap;

        public EncodeBitmapBase64AsyncTask(Bitmap bitmap) {
            this._bitmap = bitmap;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return Images.encodeImageBase64(_bitmap);
        }
    }

    private static String encodeImageBase64(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] b = stream.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * Get bitmap from vector drawable
     *
     * @param context    Context
     * @param drawableId Resource id of target vector drawable
     * @return vector's bitmap
     */
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (!AndroidVersions.isLollipop()) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
