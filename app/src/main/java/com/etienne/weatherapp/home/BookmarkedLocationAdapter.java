package com.etienne.weatherapp.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.etienne.weatherapp.R;
import com.etienne.weatherapp.model.BookmarkedLocation;

import java.util.List;

public class BookmarkedLocationAdapter extends RecyclerView.Adapter<BookmarkedLocationAdapter.ViewHolder> {

    public interface Listener {
        void onBookmarkClicked(@NonNull final BookmarkedLocation bookmarkedLocation);

        void onDeleteBookmark(@NonNull final BookmarkedLocation bookmarkedLocation);
    }

    @NonNull
    private final List<BookmarkedLocation> bookmarkedLocations;
    @Nullable
    private Listener listener;

    public BookmarkedLocationAdapter(@NonNull List<BookmarkedLocation> bookmarkedLocations) {
        this.bookmarkedLocations = bookmarkedLocations;
    }

    public void setListener(@NonNull Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarked_location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(bookmarkedLocations.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return bookmarkedLocations.size();
    }

    public void removeItem(@NonNull final BookmarkedLocation bookmarkedLocation) {
        final int position = bookmarkedLocations.indexOf(bookmarkedLocation);
        if (position > -1) {
            bookmarkedLocations.remove(position);
            notifyItemRemoved(position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView latitude;
        private TextView longitude;
        private Button button;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);
            button = itemView.findViewById(R.id.delete);
        }

        void bind(final BookmarkedLocation bookmarkedLocation, @Nullable final Listener callback) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onBookmarkClicked(bookmarkedLocation);
                    }
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onDeleteBookmark(bookmarkedLocation);
                    }
                }
            });
            if (!TextUtils.isEmpty(bookmarkedLocation.getName())) {
                name.setText(bookmarkedLocation.getName());
            }
            latitude.setText(String.valueOf(bookmarkedLocation.getLatitude()));
            longitude.setText(String.valueOf(bookmarkedLocation.getLongitude()));
        }
    }
}
