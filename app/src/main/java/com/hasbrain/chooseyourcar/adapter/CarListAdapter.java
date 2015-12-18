package com.hasbrain.chooseyourcar.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.chooseyourcar.R;
import com.hasbrain.chooseyourcar.loader.ImageLoaderCallback;
import com.hasbrain.chooseyourcar.loader.ImageLoaderThread;
import com.hasbrain.chooseyourcar.model.Car;

import java.util.List;

/**
 * Created by Khang on 17/12/2015.
 */
public class CarListAdapter extends RecyclerView.Adapter<ViewHolder> implements ImageLoaderCallback {
    private static final String TAG = "CarListAdapter";
    private Context context;
    private List<Car> carList;
    private LruCache<String, Bitmap> cache;

    public CarListAdapter(Context context, List<Car> carList) {
        this.context = context;
        this.carList = carList;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 5;

        cache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Car car = carList.get(position);
        final Bitmap bitmap = cache.get(car.getImageUrl());

        holder.bindData(car.getName(), bitmap);
        if (bitmap == null) {
            ImageLoaderThread task = new ImageLoaderThread(context, position, 200, this);
            task.execute(car.getImageUrl());
        }
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    @Override
    public void onFinish(int position, String key, Bitmap bitmap) {
        cache.put(key, bitmap);
        notifyItemChanged(position);
    }
}
