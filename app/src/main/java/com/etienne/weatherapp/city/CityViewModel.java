package com.etienne.weatherapp.city;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.etienne.weatherapp.model.Forecast;
import com.etienne.weatherapp.network.DownloadWeatherData;

public class CityViewModel implements DownloadWeatherData.Callback {

    private static final String DEFAULT_PLACEHOLDER = "-";

    public interface Callback {
        void showErrorMessage();
    }

    private final boolean isNewLocation;
    private final double latitude;
    private final double longitude;
    @NonNull
    private final DownloadWeatherData downloadWeatherData;
    @NonNull
    private final Callback callback;
    @NonNull
    public ObservableField<String> temperature;
    public ObservableField<String> humidity;
    public ObservableField<String> rain;

    CityViewModel(boolean isNewLocation, double latitude, double longitude, @NonNull Callback callback) {
        this.isNewLocation = isNewLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.downloadWeatherData = new DownloadWeatherData(this);
        this.callback = callback;
        this.temperature = new ObservableField<>(DEFAULT_PLACEHOLDER);
        this.humidity = new ObservableField<>(DEFAULT_PLACEHOLDER);
        this.rain = new ObservableField<>(DEFAULT_PLACEHOLDER);
    }

    void downloadForecast() {
        downloadWeatherData.execute(latitude, longitude);
    }

    @Override
    public void onWeatherForecastReceived(@NonNull Forecast forecast) {
        this.temperature.set(String.valueOf(forecast.getMain().getTemp()));
        this.humidity.set(String.valueOf(forecast.getMain().getHumidity()));
        if (forecast.getRain() != null) {
            this.rain.set(String.valueOf(forecast.getRain().get3h()));
        }
    }

    @Override
    public void onWeatherForecastRequestError() {
        callback.showErrorMessage();
    }
}
