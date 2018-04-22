package com.etienne.weatherapp.home;

import android.view.View;

import com.etienne.weatherapp.model.BookmarkedLocation;
import com.etienne.weatherapp.repository.SharedPreferencesUtility;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class HomeViewModelTest {

    @Mock
    SharedPreferencesUtility mockSharedPreferencesUtility;
    @Mock
    HomeViewModel.Callback mockCallback;
    @Mock
    BookmarkedLocation mockBookmarkedLocation;
    private HomeViewModel subject;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        subject = new HomeViewModel(mockCallback, mockSharedPreferencesUtility);
    }

    @Test
    public void itShouldShowEmptyBookmarkContainerIfBookmarkedListIsEmpty() {
        when(mockSharedPreferencesUtility.fetchBookmarkedLocations()).thenReturn(Collections.<BookmarkedLocation>emptyList());
        subject = new HomeViewModel(mockCallback, mockSharedPreferencesUtility);
        assertEquals(View.VISIBLE, subject.emptyBookmarksContainerVisibility.get());
    }

    @Test
    public void itShouldNotShowEmptyBookmarkContainerIfBookmarkedListIsNotEmpty() {
        final List<BookmarkedLocation> bookmarkedLocations = new ArrayList<>();
        bookmarkedLocations.add(mockBookmarkedLocation);
        when(mockSharedPreferencesUtility.fetchBookmarkedLocations()).thenReturn(bookmarkedLocations);
        subject = new HomeViewModel(mockCallback, mockSharedPreferencesUtility);
        assertEquals(View.GONE, subject.emptyBookmarksContainerVisibility.get());
    }

    @Test
    public void itShouldGoToCityScreenOnBookmarkClicked() {
        subject.listener.onBookmarkClicked(mockBookmarkedLocation);
        verify(mockCallback).goToCityScreen(mockBookmarkedLocation);
    }

    @Test
    public void itShouldDeleteLocationOnDeleteBookmark() {
        when(mockSharedPreferencesUtility.deleteLocation(mockBookmarkedLocation)).thenReturn(true);
        subject.listener.onDeleteBookmark(mockBookmarkedLocation);
        verify(mockSharedPreferencesUtility).deleteLocation(mockBookmarkedLocation);
        verify(mockCallback).removeLocation(mockBookmarkedLocation);
    }

    @Test
    public void itShouldNotForwardRemoveLocationIfDeleteLocationFailedOnDeleteBookmark() {
        when(mockSharedPreferencesUtility.deleteLocation(mockBookmarkedLocation)).thenReturn(false);
        subject.listener.onDeleteBookmark(mockBookmarkedLocation);
        verify(mockSharedPreferencesUtility).deleteLocation(mockBookmarkedLocation);
        verify(mockCallback, never()).removeLocation(mockBookmarkedLocation);
    }
}