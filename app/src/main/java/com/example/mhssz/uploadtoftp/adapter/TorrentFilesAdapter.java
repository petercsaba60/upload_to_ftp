package com.example.mhssz.uploadtoftp.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mhssz.uploadtoftp.R;

import java.io.File;
import java.util.List;

public class TorrentFilesAdapter extends RecyclerView.Adapter<TorrentFilesAdapter.TorrentFilesViewHolder>{
    private List<File> torrentFiles;

    private int selectedItem = 0;

    public TorrentFilesAdapter(List<File> torrentFiles) {
        this.torrentFiles = torrentFiles;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    public int getSelectedItem() {
        return this.selectedItem;
    }

    @NonNull
    @Override
    public TorrentFilesAdapter.TorrentFilesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.torrent_files_list_row, viewGroup, false);

        return new TorrentFilesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TorrentFilesAdapter.TorrentFilesViewHolder torrentFilesViewHolder, int i) {
        if ( getItemCount() > 0) {
            torrentFilesViewHolder.torrentFileName.setText((CharSequence) torrentFiles.get(selectedItem));
        }
    }

    @Override
    public int getItemCount() {
        return torrentFiles.size();
    }

    public class TorrentFilesViewHolder extends RecyclerView.ViewHolder {
        public TextView torrentFileName;

        public TorrentFilesViewHolder(View view) {
            super(view);
            torrentFileName = (TextView) view.findViewById(R.id.torrent_file);
        }
    }
}
