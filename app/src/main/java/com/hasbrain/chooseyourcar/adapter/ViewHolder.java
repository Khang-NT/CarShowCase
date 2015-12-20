package com.hasbrain.chooseyourcar.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hasbrain.chooseyourcar.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Khang on 17/12/2015.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.ivCarPicture)
    AppCompatImageView ivCarPicture;
    @Bind(R.id.tvCarName)
    AppCompatTextView tvCarName;

    View root;

    ViewHolder(View view) {
        super(view);
        this.root = view;
        ButterKnife.bind(this, view);
    }

    void bindData(String name, Bitmap bm) {
        tvCarName.setText(name);
        ivCarPicture.setImageBitmap(bm);
    }

}
