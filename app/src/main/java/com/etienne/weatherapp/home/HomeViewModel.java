package com.etienne.weatherapp.home;

import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.etienne.weatherapp.model.BookmarkedLocation;
import com.etienne.weatherapp.repository.SharedPreferencesUtility;

import java.util.List;

public class HomeViewModel {

    interface Callback {
        void removeLocation(@NonNull BookmarkedLocation bookmarkedLocation);

        void goToCityScreen(@NonNull BookmarkedLocation bookmarkedLocation);
    }

    @NonNull
    private final Callback callback;
    @NonNull
    public final ObservableInt emptyBookmarksContainerVisibility;
    @NonNull
    public final List<BookmarkedLocation> bookmarkedLocations;
    @NonNull
    final SharedPreferencesUtility sharedPreferencesUtility;
    @NonNull
    public final BookmarkedLocationAdapter.Listener listener = new BookmarkedLocationAdapter.Listener() {
        @Override
        public void onBookmarkClicked(@NonNull BookmarkedLocation bookmarkedLocation) {
            callback.goToCityScreen(bookmarkedLocation);
        }

        @Override
        public void onDeleteBookmark(@NonNull BookmarkedLocation bookmarkedLocation) {
            final boolean succeed = sharedPreferencesUtility.deleteLocation(bookmarkedLocation);
            if (succeed) {
                callback.removeLocation(bookmarkedLocation);
            }
        }
    };

    HomeViewModel(@NonNull final Callback callback, @NonNull final SharedPreferencesUtility sharedPreferencesUtility) {
        this.callback = callback;
        this.sharedPreferencesUtility = sharedPreferencesUtility;
        this.bookmarkedLocations = sharedPreferencesUtility.fetchBookmarkedLocations();
        this.emptyBookmarksContainerVisibility = new ObservableInt(getEmptyContainerVisibility());
    }

    private int getEmptyContainerVisibility() {
        return bookmarkedLocations.size() > 0 ? View.GONE : View.VISIBLE;
    }
}
