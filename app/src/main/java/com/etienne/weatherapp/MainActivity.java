package com.etienne.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.etienne.weatherapp.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
//                onBackPressed();
            }
        });
        enableBackButton(false);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new HomeFragment())
                    .commit();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.help_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    public void enableBackButton(final boolean enable) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        getSupportActionBar().setHomeButtonEnabled(enable);
    }

//    @Override
//    public void onBackPressed() {
//        getSupportFragmentManager().popBackStack();
//    }
}
