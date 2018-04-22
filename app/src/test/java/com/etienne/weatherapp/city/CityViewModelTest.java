package com.etienne.weatherapp.city;

import android.view.View;

import com.etienne.weatherapp.model.BookmarkedLocation;
import com.etienne.weatherapp.model.Forecast;
import com.etienne.weatherapp.model.Main;
import com.etienne.weatherapp.model.Rain;
import com.etienne.weatherapp.model.Wind;
import com.etienne.weatherapp.repository.SharedPreferencesUtility;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityViewModelTest {

    private static final Double TEMPERATURE = 23.0;
    private static final Double HUMIDITY = 23.0;
    private static final Double WIND = 23.0;
    private static final Double RAIN = 23.0;
    private static final String NAME = "location";
    private static final String DEFAULT_PLACEHOLDER = "-";

    @Mock
    SharedPreferencesUtility mockSharedPreferencesUtility;
    @Mock
    CityViewModel.Callback mockCallback;
    @Mock
    Forecast mockForecast;
    @Mock
    Main mockMain;
    @Mock
    Wind mockWind;
    @Mock
    Rain mockRain;
    private CityViewModel subject;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        subject = new CityViewModel(true, 1, 1, mockCallback, mockSharedPreferencesUtility);
        when(mockForecast.getMain()).thenReturn(mockMain);
        when(mockForecast.getRain()).thenReturn(mockRain);
        when(mockForecast.getWind()).thenReturn(mockWind);
        when(mockMain.getTemp()).thenReturn(TEMPERATURE);
        when(mockMain.getTempMin()).thenReturn(TEMPERATURE);
        when(mockMain.getTempMax()).thenReturn(TEMPERATURE);
        when(mockMain.getHumidity()).thenReturn(HUMIDITY);
        when(mockMain.getHumidity()).thenReturn(HUMIDITY);
        when(mockRain.get3h()).thenReturn(RAIN);
        when(mockWind.getSpeed()).thenReturn(WIND);
        when(mockForecast.getName()).thenReturn(NAME);
    }

    @Test
    public void itShouldShowSpinnerByDefault() {
        assertEquals(View.VISIBLE, subject.spinnerVisibility.get());
    }

    @Test
    public void itShouldShowPlaceholderByDefault() {
        assertEquals(DEFAULT_PLACEHOLDER, subject.name.get());
        assertEquals(DEFAULT_PLACEHOLDER, subject.temperature.get());
        assertEquals(DEFAULT_PLACEHOLDER, subject.temperatureMin.get());
        assertEquals(DEFAULT_PLACEHOLDER, subject.temperatureMax.get());
        assertEquals(DEFAULT_PLACEHOLDER, subject.humidity.get());
        assertEquals(DEFAULT_PLACEHOLDER, subject.rain.get());
        assertEquals(DEFAULT_PLACEHOLDER, subject.wind.get());
    }

    @Test
    public void itShouldUpdateUiOnWeatherForecastReceived() {
        subject.onWeatherForecastReceived(mockForecast);
        assertEquals(View.GONE, subject.spinnerVisibility.get());
        assertEquals(String.valueOf(NAME), subject.name.get());
        assertEquals(String.valueOf(TEMPERATURE), subject.temperature.get());
        assertEquals(String.valueOf(TEMPERATURE), subject.temperatureMin.get());
        assertEquals(String.valueOf(TEMPERATURE), subject.temperatureMax.get());
        assertEquals(String.valueOf(HUMIDITY), subject.humidity.get());
        assertEquals(String.valueOf(RAIN), subject.rain.get());
        assertEquals(String.valueOf(WIND), subject.wind.get());
    }

    @Test
    public void itShouldSaveLocationIfItIsNewLocationOnWeatherForecastReceived() {
        subject = new CityViewModel(true, 1, 1, mockCallback, mockSharedPreferencesUtility);
        subject.onWeatherForecastReceived(mockForecast);
        verify(mockSharedPreferencesUtility).saveLocation(any(BookmarkedLocation.class));
    }

    @Test
    public void itShouldNotSaveLocationIfItIsNotNewLocationOnWeatherForecastReceived() {
        subject = new CityViewModel(false, 1, 1, mockCallback, mockSharedPreferencesUtility);
        subject.onWeatherForecastReceived(mockForecast);
        verify(mockSharedPreferencesUtility, never()).saveLocation(any(BookmarkedLocation.class));
    }

    @Test
    public void itShouldShowErrorMessageOnWeatherForecastRequestError() {
        subject.onWeatherForecastRequestError();
        verify(mockCallback).showErrorMessage();
    }
}