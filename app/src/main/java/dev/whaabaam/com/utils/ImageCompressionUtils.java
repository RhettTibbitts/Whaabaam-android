package dev.whaabaam.com.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ImageCompressionUtils extends AsyncTask<String, Void, File> {

    private File file;
    private OnImageCompressedCallback callback;
    private int uploadType;

    public ImageCompressionUtils(Context context, OnImageCompressedCallback callback, int uploadType) {
        String randomFileName = String.valueOf(new Date().getTime()) + ".jpg";
        file = new File(context.getCacheDir(), randomFileName);
        this.callback = callback;
        this.uploadType = uploadType;
    }

    @Override
    protected File doInBackground(String... params) {


        Bitmap original = BitmapFactory.decodeFile(params[0]);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(original, original.getWidth() / 2,
                original.getHeight() / 2, false);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, out);
        try {
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(out.toByteArray());
            fo.flush();
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("original", new File(params[0]).length() + "");
        Log.e("compressed", file.length() + "");

        return file;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        callback.onImageCompressed(file, uploadType);
    }
}
