package com.hasbrain.chooseyourcar.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Khang on 17/12/2015.
 */
public class ImageLoaderThread extends AsyncTask<String, Void, Bitmap> {
    private int position;
    private int maxHeight;
    private ImageLoaderCallback callback;
    private Context context;
    private String key;


    public ImageLoaderThread(Context context, int position, int maxHeight, ImageLoaderCallback callback) {
        this.context = context;
        this.position = position;
        this.maxHeight = maxHeight;
        this.callback = callback;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        key = params[0];

        int rawID = context.getResources().getIdentifier(params[0], "raw", context.getPackageName());

        final BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), rawID, ops);

        ops.inSampleSize = calculateInSampleSize(ops);
        Log.e("inSampleSize", "doInBackground: " + ops.inSampleSize);
        ops.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(context.getResources(), rawID, ops);
    }

    private int calculateInSampleSize(BitmapFactory.Options options) {
//        final int height = options.outHeight;
//        Log.e("inSampleSize", "original height: " + options.outHeight );


        return 8;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null && key != null && callback != null)
            callback.onFinish(position, key, bitmap);
    }
}
