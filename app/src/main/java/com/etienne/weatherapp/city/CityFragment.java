package com.etienne.weatherapp.city;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.etienne.weatherapp.R;
import com.etienne.weatherapp.databinding.FragmentCityBinding;
import com.etienne.weatherapp.repository.SharedPreferencesUtility;

public class CityFragment extends Fragment implements CityViewModel.Callback {
    private static final String KEY_IS_NEW_LOCATION = "isNewLocation";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    public static Fragment newInstance(boolean isNewLocation, double lat, double lon) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_IS_NEW_LOCATION, isNewLocation);
        bundle.putDouble(KEY_LATITUDE, lat);
        bundle.putDouble(KEY_LONGITUDE, lon);
        Fragment fragment = new CityFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    private CityViewModel viewModel;
    private boolean isNewLocation;
    private double latitude;
    private double longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        readBundle(getArguments());
        FragmentCityBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_city, container, false);
        viewModel = new CityViewModel(isNewLocation, latitude, longitude, this, new SharedPreferencesUtility(getContext()));
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewModel != null) {
            viewModel.downloadForecast();
        }
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null
                && bundle.containsKey(KEY_IS_NEW_LOCATION)
                && bundle.containsKey(KEY_LATITUDE)
                && bundle.containsKey(KEY_LONGITUDE)) {
            isNewLocation = bundle.getBoolean(KEY_IS_NEW_LOCATION);
            latitude = bundle.getDouble(KEY_LATITUDE);
            longitude = bundle.getDouble(KEY_LONGITUDE);
        } else {
            throw new IllegalArgumentException("CityFragment must be created with the factory method");
        }
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getContext(), "Forecast could not loaded, please try again", Toast.LENGTH_LONG).show();
    }
}