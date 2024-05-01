package com.spotify.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spotify.R;

public class PlaylistHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textViewTitle;

    public PlaylistHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageViewPlaylist);
        textViewTitle = itemView.findViewById(R.id.textViewPlaylist);
    }
}
