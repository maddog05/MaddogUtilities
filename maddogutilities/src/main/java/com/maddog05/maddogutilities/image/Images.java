package com.maddog05.maddogutilities.image;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;

import com.maddog05.maddogutilities.android.AndroidVersions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/*
 * Created by andreetorres on 1/05/17.
 */

public class Images {

    public static final String PARAMETER_CAMERA_OUTPUT = MediaStore.EXTRA_OUTPUT;

    public static class InputPhoto {
        public String path;
        public Uri uri;
    }

    /**
     * Create an intent for select photo in gallery
     *
     * @param titleSelectApp title for select source
     * @return An intent for startActivityForResult
     */
    public static Intent getIntentGallery(String titleSelectApp) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return Intent.createChooser(intent, titleSelectApp);
    }

    /**
     * Create an intent for take a picture from camera
     *
     * @param packageManager param to check if intent can be resolved
     * @return An intent for startActivityForResult
     */
    public static Intent getIntentCamera(PackageManager packageManager) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return intent.resolveActivity(packageManager) != null ? intent : null;
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
     * @param photoPath, Path to photo temp.
     */
    public static OutputPhoto getOutputPhotoCamera(String photoPath) {
        OutputPhoto pair = new OutputPhoto();
        Uri takenPhotoUri = Uri.parse(photoPath);
        pair.bitmap = BitmapFactory.decodeFile(takenPhotoUri.getPath());
        return pair;
    }

    /**
     * Get OutputPhoto compressed, ONLY FOR GALLERY.
     *
     * @param photoPath, Path to photo temp.
     */
    public static OutputPhoto getOutputPhotoCameraCompressed(String photoPath, int maxDimensionPixels) {
        OutputPhoto pair = new OutputPhoto();
        Uri takenPhotoUri = Uri.parse(photoPath);
        pair.bitmap = getBitmapCompressed(BitmapFactory.decodeFile(takenPhotoUri.getPath()), maxDimensionPixels);
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

    public static Bitmap getBitmapCompressed(Bitmap originalBitmap, int maxDimensionPixels) {
        float factor = maxDimensionPixels / (float) originalBitmap.getWidth();
        return Bitmap.createScaledBitmap(originalBitmap, maxDimensionPixels, (int) (originalBitmap.getHeight() * factor), true);
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
