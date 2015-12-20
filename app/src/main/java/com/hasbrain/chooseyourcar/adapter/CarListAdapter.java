package com.hasbrain.chooseyourcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.chooseyourcar.CarDetailActivity;
import com.hasbrain.chooseyourcar.R;
import com.hasbrain.chooseyourcar.loader.ImageLoaderCallback;
import com.hasbrain.chooseyourcar.loader.ImageLoaderThread;
import com.hasbrain.chooseyourcar.model.Car;

import java.util.List;

/**
 * Created by Khang on 17/12/2015.
 */
public class CarListAdapter extends RecyclerView.Adapter<ViewHolder> implements ImageLoaderCallback, View.OnClickListener {
    private static final String TAG = "CarListAdapter";
    private Context context;
    private List<Car> carList;
    private LruCache<String, Bitmap> cache;
    private int maxImgHeight;

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

        maxImgHeight = context.getResources().getDimensionPixelSize(R.dimen.img_height);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Car car = carList.get(position);
        final Bitmap bitmap = cache.get(car.getImageUrl());

        holder.bindData(car.getName(), bitmap);
        if (bitmap == null) {
            ImageLoaderThread task = new ImageLoaderThread(context, maxImgHeight, this);
            task.setTag("position", position);
            task.setTag("key", car.getImageUrl());
            task.execute(car.getImageUrl());
        }

        holder.root.setTag(position);
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    @Override
    public void onFinish(ImageLoaderThread task, Bitmap bitmap) {
        int position = (int) task.getTag("position");
        String key = (String) task.getTag("key");
        cache.put(key, bitmap);
        notifyItemChanged(position);
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Intent carDetailActivity = new Intent(context, CarDetailActivity.class);
        carDetailActivity.putExtra("position", position);
        carDetailActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(carDetailActivity);
    }
}
