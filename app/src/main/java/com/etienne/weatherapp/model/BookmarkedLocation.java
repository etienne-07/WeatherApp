package com.etienne.weatherapp.model;

import android.support.annotation.NonNull;

public class BookmarkedLocation {

    private final double latitude;
    private final double longitude;
    @NonNull
    private final String name;

    public BookmarkedLocation(double latitude, double longitude, @NonNull String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
