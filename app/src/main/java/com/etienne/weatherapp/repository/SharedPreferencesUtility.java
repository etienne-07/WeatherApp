package com.etienne.weatherapp.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.etienne.weatherapp.model.BookmarkedLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesUtility {

    private static final String BOOKMARKED_LOCATION = "bookmarkedLocation";

    @NonNull
    private final SharedPreferences sharedPreferences;
    @NonNull
    private final Gson gson;

    public SharedPreferencesUtility(@NonNull Context context) {
        this.sharedPreferences = context.getSharedPreferences("WeatherApp", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public void saveLocation(@NonNull final BookmarkedLocation location) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final List<BookmarkedLocation> bookmarkedLocations = fetchBookmarkedLocations();
        final int position = bookmarkedLocations.indexOf(location);
        if (position > -1) {
            bookmarkedLocations.set(position, location);
        } else {
            bookmarkedLocations.add(location);
        }
        saveBookmarkedLocationString(editor, bookmarkedLocations);
    }

    public List<BookmarkedLocation> fetchBookmarkedLocations() {
        final String jsonLocations = sharedPreferences.getString(BOOKMARKED_LOCATION, null);
        if (jsonLocations == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<BookmarkedLocation>>() {
        }.getType();
        return gson.fromJson(jsonLocations, type);
    }

    public boolean deleteLocation(@NonNull final BookmarkedLocation location) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final List<BookmarkedLocation> bookmarkedLocations = fetchBookmarkedLocations();
        final int position = bookmarkedLocations.indexOf(location);
        if (position > -1) {
            bookmarkedLocations.remove(position);
            saveBookmarkedLocationString(editor, bookmarkedLocations);
            return true;
        }
        return false;
    }

    private void saveBookmarkedLocationString(SharedPreferences.Editor editor, List<BookmarkedLocation> bookmarkedLocations) {
        editor.putString(BOOKMARKED_LOCATION, gson.toJson(bookmarkedLocations));
        editor.commit();
    }
}
