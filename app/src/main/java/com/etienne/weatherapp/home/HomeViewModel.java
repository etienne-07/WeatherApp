package com.etienne.weatherapp.home;

import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.etienne.weatherapp.model.BookmarkedLocation;
import com.etienne.weatherapp.repository.SharedPreferencesUtility;

import java.util.List;

public class HomeViewModel {

    @NonNull
    private final Callback callback;
    @NonNull
    public final ObservableInt emptyBookmarksContainerVisibility;
    @NonNull
    public final List<BookmarkedLocation> bookmarkedLocations;

    interface Callback {
        void addNewLocation();
    }

    HomeViewModel(@NonNull final Callback callback, @NonNull final SharedPreferencesUtility sharedPreferencesUtility) {
        this.callback = callback;
        this.bookmarkedLocations = sharedPreferencesUtility.fetchBookmarkedLocations();
        this.emptyBookmarksContainerVisibility = new ObservableInt(getEmptyContainerVisibility());
    }

    public void onAddLocationButtonClicked() {
        callback.addNewLocation();
    }

    private int getEmptyContainerVisibility() {
        return bookmarkedLocations.size() > 0 ? View.GONE : View.VISIBLE;
    }
}
