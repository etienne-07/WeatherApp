package com.etienne.weatherapp.city;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.etienne.weatherapp.model.BookmarkedLocation;
import com.etienne.weatherapp.model.Forecast;
import com.etienne.weatherapp.network.DownloadWeatherData;
import com.etienne.weatherapp.repository.SharedPreferencesUtility;

public class CityViewModel implements DownloadWeatherData.Callback {

    private static final String DEFAULT_PLACEHOLDER = "-";

    public interface Callback {
        void showErrorMessage();
    }

    private final boolean isNewLocation;
    private final double latitude;
    private final double longitude;
    @NonNull
    private final Callback callback;
    @NonNull
    private final SharedPreferencesUtility sharedPreferencesUtility;
    public ObservableField<String> temperature;
    public ObservableField<String> humidity;
    public ObservableField<String> rain;
    public ObservableInt spinnerVisibility;

    CityViewModel(boolean isNewLocation, double latitude, double longitude, @NonNull Callback callback, @NonNull SharedPreferencesUtility sharedPreferencesUtility) {
        this.isNewLocation = isNewLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.callback = callback;
        this.temperature = new ObservableField<>(DEFAULT_PLACEHOLDER);
        this.humidity = new ObservableField<>(DEFAULT_PLACEHOLDER);
        this.rain = new ObservableField<>(DEFAULT_PLACEHOLDER);
        this.sharedPreferencesUtility = sharedPreferencesUtility;
        this.spinnerVisibility = new ObservableInt(View.VISIBLE);
    }

    void downloadForecast() {
        new DownloadWeatherData(this).execute(latitude, longitude);
    }

    @Override
    public void onWeatherForecastReceived(@NonNull Forecast forecast) {
        this.spinnerVisibility.set(View.GONE);
        this.temperature.set(String.valueOf(forecast.getMain().getTemp()));
        this.humidity.set(String.valueOf(forecast.getMain().getHumidity()));
        if (forecast.getRain() != null) {
            this.rain.set(String.valueOf(forecast.getRain().get3h()));
        }

        if (isNewLocation) {
            saveNewLocation(forecast.getName());
        }
    }

    @Override
    public void onWeatherForecastRequestError() {
        callback.showErrorMessage();
    }

    private void saveNewLocation(@NonNull String name) {
        final BookmarkedLocation location = new BookmarkedLocation(latitude, longitude, name);
        sharedPreferencesUtility.saveLocation(location);
    }
}
