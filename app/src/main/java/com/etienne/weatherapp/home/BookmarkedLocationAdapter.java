package com.etienne.weatherapp.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etienne.weatherapp.R;
import com.etienne.weatherapp.model.BookmarkedLocation;

import java.util.List;

public class BookmarkedLocationAdapter extends RecyclerView.Adapter<BookmarkedLocationAdapter.ViewHolder> {

    private final List<BookmarkedLocation> bookmarkedLocations;

    public BookmarkedLocationAdapter(List<BookmarkedLocation> bookmarkedLocations) {
        this.bookmarkedLocations = bookmarkedLocations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarked_location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(bookmarkedLocations.get(position));
    }

    @Override
    public int getItemCount() {
        return bookmarkedLocations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView latitude;
        private TextView longitude;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);
        }

        void bind(BookmarkedLocation bookmarkedLocation) {
            if (!TextUtils.isEmpty(bookmarkedLocation.getName())) {
                name.setText(bookmarkedLocation.getName());
            }
            latitude.setText(String.valueOf(bookmarkedLocation.getLatitude()));
            longitude.setText(String.valueOf(bookmarkedLocation.getLongitude()));
        }
    }
}
