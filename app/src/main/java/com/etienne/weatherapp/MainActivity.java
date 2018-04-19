package com.etienne.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.etienne.weatherapp.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new HomeFragment())
                .commit();
    }
}
