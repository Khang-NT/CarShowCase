package com.hasbrain.chooseyourcar.loader;

import android.graphics.Bitmap;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/16/15.
 */
public interface ImageLoaderCallback {
    void onFinish(int position, String key, Bitmap bitmap);
}
