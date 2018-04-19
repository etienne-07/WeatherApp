package com.etienne.weatherapp.home;

import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

public class HomeViewModel {

    @NonNull
    private final Callback callback;
    @NonNull
    public final ObservableInt emptyBookmarksContainerVisibility;

    interface Callback {
        void addNewLocation();
    }

    HomeViewModel(@NonNull final Callback callback) {
        this.callback = callback;
        this.emptyBookmarksContainerVisibility = new ObservableInt(View.VISIBLE);
    }

    public void onAddLocationButtonClicked() {
        callback.addNewLocation();
    }
}
