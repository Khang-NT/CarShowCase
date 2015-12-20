package com.hasbrain.chooseyourcar.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Khang on 17/12/2015.
 */
public class ImageLoaderThread extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "asd";
    private int reqHeight;
    private WeakReference<ImageLoaderCallback> callback;
    private Context context;


    public ImageLoaderThread(Context context, int maxHeight, ImageLoaderCallback callback) {
        this.context = context;
        this.reqHeight = maxHeight;
        this.callback = new WeakReference<>(callback);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        int rawID = context.getResources().getIdentifier(params[0], "raw", context.getPackageName());
        InputStream is = context.getResources().openRawResource(rawID);

        final BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, ops);

        ops.inSampleSize = calculateInSampleSize(ops);
        ops.inJustDecodeBounds = false;

        return BitmapFactory.decodeStream(is, null, ops);
    }

    private int calculateInSampleSize(BitmapFactory.Options ops) {
        int height = ops.outHeight;
        int inSampleSize = 1;

        while ((height /= 2) > reqHeight)
            inSampleSize *= 2;

        return inSampleSize;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null && callback != null && callback.get() != null) {
            callback.get().onFinish(this, bitmap);
        }
    }

    private HashMap<String, Object> tags = new HashMap<>();

    public void setTag(String key, Object tag) {
        tags.put(key, tag);
    }

    public Object getTag(String key) {
        return tags.get(key);
    }

    private Object nullKeyTag;

    public void setTag(Object tag) {
        nullKeyTag = tag;
    }

    public Object getTag() {
        return nullKeyTag;
    }

}
