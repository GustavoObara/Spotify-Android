package com.spotify.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spotify.R;
import com.spotify.model.Playlist;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistHolder> {
    private List<Playlist.PlaylistItem> playlists;
    private Context context;

    public PlaylistAdapter(Context context, List<Playlist.PlaylistItem> playlists) {
        this.context = context;
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaylistHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistHolder holder, int position) {
        Playlist.PlaylistItem playlist = playlists.get(position);
        holder.textViewTitle.setText(playlist.getName());

        new Thread(() -> {
            URL url = null;
            try {
                url = new URL(playlist.getImageUrl());
                Log.e("PlaylistAdapter", String.valueOf(url));
                final Bitmap bmp;
                bmp = BitmapFactory
                        .decodeStream(url.openConnection()
                                .getInputStream());
                new Handler(Looper.getMainLooper()).post(()-> {
                    holder.imageView.setImageBitmap(bmp);
                });
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }
}
