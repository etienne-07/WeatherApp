package com.etienne.weatherapp.network;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.etienne.weatherapp.BuildConfig;
import com.etienne.weatherapp.model.Forecast;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownloadWeatherData extends AsyncTask<Double, Void, Forecast> {

    private static final String TAG = DownloadWeatherData.class.getName();
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric";

    public interface Callback {
        void onWeatherForecastReceived(@NonNull Forecast forecast);

        void onWeatherForecastRequestError();
    }

    @NonNull
    private final Callback callback;

    public DownloadWeatherData(@NonNull Callback callback) {
        this.callback = callback;
    }

    @Override
    protected Forecast doInBackground(Double... params) {
        if (params != null && params.length == 2) {
            final double latitude = params[0];
            final double longitude = params[1];
            try {
                final URL url = new URL(String.format(API_URL, latitude, longitude, BuildConfig.OPEN_WEATHER_API_KEY));
                return fetch(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(@Nullable Forecast forecast) {
        if (forecast == null) {
            callback.onWeatherForecastRequestError();
        } else {
            callback.onWeatherForecastReceived(forecast);
        }
    }

    private Forecast fetch(@NonNull URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                return new Gson().fromJson(bufferedReader, Forecast.class);
            }
        } catch (ProtocolException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
        } catch (JsonIOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}
