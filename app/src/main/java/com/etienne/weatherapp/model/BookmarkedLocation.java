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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookmarkedLocation that = (BookmarkedLocation) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + name.hashCode();
        return result;
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
