package com.hasbrain.chooseyourcar.fragment;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.hasbrain.chooseyourcar.R;
import com.hasbrain.chooseyourcar.loader.ImageLoaderCallback;
import com.hasbrain.chooseyourcar.loader.ImageLoaderThread;
import com.hasbrain.chooseyourcar.model.Car;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Khang on 18/12/2015.
 */
public class CarDetailFragment extends Fragment implements ImageLoaderCallback {
    private static final String TAG = "CarDetailFragment";
    private static final String CAR_NAME = "CAR_NAME", BRAND = "BRAND", IMG_KEY = "IMG_KEY";
    @Bind(R.id.ivCar)
    AppCompatImageView ivCar;
    @Bind(R.id.tvCarName)
    AppCompatTextView tvCarName;
    @Bind(R.id.tv_Brand)
    AppCompatTextView tvBrand;

    public static CarDetailFragment newInstance(Car detail) {
        Bundle bundle = new Bundle();
        bundle.putString(CAR_NAME, detail.getName());
        bundle.putString(BRAND, detail.getBrand());
        bundle.putString(IMG_KEY, detail.getImageUrl());
        CarDetailFragment carDetailFragment = new CarDetailFragment();
        carDetailFragment.setArguments(bundle);
        return carDetailFragment;
    }


    private String name, brand, img_key;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        name = bundle.getString(CAR_NAME);
        brand = bundle.getString(BRAND);
        img_key = bundle.getString(IMG_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_detail_fragment, container, false);
        ButterKnife.bind(this, view);
        tvCarName.setText(name);
        tvBrand.setText(brand);

        ivCar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            boolean removed = false;

            @Override
            public void onGlobalLayout() {
                if (removed)
                    return;
                removed = true;
                if (Build.VERSION.SDK_INT >= 16)
                    ivCar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ImageLoaderThread task = new ImageLoaderThread(getContext(), ivCar.getHeight(), CarDetailFragment.this);
                task.execute(img_key);
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onFinish(ImageLoaderThread task, Bitmap bitmap) {
        if (ivCar != null)
            ivCar.setImageBitmap(bitmap);
    }
}
