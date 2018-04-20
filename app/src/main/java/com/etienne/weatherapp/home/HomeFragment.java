package com.etienne.weatherapp.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.etienne.weatherapp.R;
import com.etienne.weatherapp.databinding.FragmentHomeBinding;
import com.etienne.weatherapp.repository.SharedPreferencesUtility;

public class HomeFragment extends Fragment implements HomeViewModel.Callback {

    @Nullable
    private HomeViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        viewModel = new HomeViewModel(this, new SharedPreferencesUtility(getContext()));
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
                addNewLocation();
                return true;
        }
        return false;
    }

    @Override
    public void addNewLocation() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new MapFragment())
                .addToBackStack(null)
                .commit();
    }
}
