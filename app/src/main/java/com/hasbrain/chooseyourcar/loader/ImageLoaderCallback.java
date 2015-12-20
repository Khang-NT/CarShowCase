package com.hasbrain.chooseyourcar.loader;

import android.graphics.Bitmap;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/16/15.
 */
public interface ImageLoaderCallback {
    void onFinish(ImageLoaderThread task, Bitmap bitmap);
}
