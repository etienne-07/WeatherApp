package com.etienne.weatherapp.home;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.etienne.weatherapp.model.BookmarkedLocation;

import java.util.List;

public class HomeCustomBinding {

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView, List<BookmarkedLocation> bookmarkedLocations) {
        final Context context = recyclerView.getContext();
        BookmarkedLocationAdapter bookmarkedLocationAdapter = new BookmarkedLocationAdapter(bookmarkedLocations);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(bookmarkedLocationAdapter);
        recyclerView.addItemDecoration(itemDecoration);
    }
}
