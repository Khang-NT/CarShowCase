package com.hasbrain.chooseyourcar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hasbrain.chooseyourcar.datastore.AssetBasedCarDatastoreImpl;
import com.hasbrain.chooseyourcar.datastore.CarDatastore;
import com.hasbrain.chooseyourcar.datastore.OnCarReceivedListener;
import com.hasbrain.chooseyourcar.model.Car;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class CarListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new GsonBuilder().create();
        CarDatastore carDatastore = new AssetBasedCarDatastoreImpl(this, "car_data.json", gson);
        carDatastore.getCarList(new OnCarReceivedListener() {
            @Override
            public void onCarReceived(List<Car> cars, Exception ex) {

            }
        });
    }
}