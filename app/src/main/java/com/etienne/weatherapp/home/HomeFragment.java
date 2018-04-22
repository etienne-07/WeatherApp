package com.etienne.weatherapp.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.etienne.weatherapp.MainActivity;
import com.etienne.weatherapp.R;
import com.etienne.weatherapp.city.CityFragment;
import com.etienne.weatherapp.databinding.FragmentHomeBinding;
import com.etienne.weatherapp.help.HelpFragment;
import com.etienne.weatherapp.model.BookmarkedLocation;
import com.etienne.weatherapp.repository.SharedPreferencesUtility;

public class HomeFragment extends Fragment implements HomeViewModel.Callback {

    @Nullable
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).enableBackButton(false);
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        HomeViewModel viewModel = new HomeViewModel(this, new SharedPreferencesUtility(getContext()));
        assert binding != null;
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                replaceFragment(new MapFragment());
                return true;
            case R.id.action_help:
                replaceFragment(new HelpFragment());
                return true;
        }
        return false;
    }

    @Override
    public void removeLocation(@NonNull BookmarkedLocation bookmarkedLocation) {
        if (binding != null) {
            ((BookmarkedLocationAdapter) binding.recyclerView.getAdapter()).removeItem(bookmarkedLocation);
        }
    }

    @Override
    public void goToCityScreen(@NonNull BookmarkedLocation bookmarkedLocation) {
        final Fragment fragment = CityFragment.newInstance(false, bookmarkedLocation.getLatitude(), bookmarkedLocation.getLongitude());
        replaceFragment(fragment);
    }

    private void replaceFragment(@NonNull Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
