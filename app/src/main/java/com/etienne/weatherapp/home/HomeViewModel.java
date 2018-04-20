package com.etienne.weatherapp.home;

import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.etienne.weatherapp.repository.SharedPreferencesUtility;

public class HomeViewModel {

    @NonNull
    private final Callback callback;
    @NonNull
    public final ObservableInt emptyBookmarksContainerVisibility;
    @NonNull
    private final SharedPreferencesUtility sharedPreferencesUtility;

    interface Callback {
        void addNewLocation();
    }

    HomeViewModel(@NonNull final Callback callback, @NonNull final SharedPreferencesUtility sharedPreferencesUtility) {
        this.callback = callback;
        this.emptyBookmarksContainerVisibility = new ObservableInt(View.VISIBLE);
        this.sharedPreferencesUtility = sharedPreferencesUtility;
        sharedPreferencesUtility.fetchBookmarkedLocations();
    }

    public void onAddLocationButtonClicked() {
        callback.addNewLocation();
    }
}
