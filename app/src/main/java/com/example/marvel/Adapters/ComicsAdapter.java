package com.example.marvel.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvel.Models.ComicItems;
import com.example.marvel.R;

import java.util.ArrayList;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder> {
    private ArrayList<ComicItems> comicItems;

    public ComicsAdapter(ArrayList<ComicItems> items) {
        this.comicItems = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView comic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comic = itemView.findViewById(R.id.comic);
        }
    }
    @NonNull
    @Override
    public ComicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View comic = layoutInflater.inflate(R.layout.comic_list, parent, false);
        return new ViewHolder(comic);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsAdapter.ViewHolder holder, int position) {
        holder.comic.setText(comicItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if(comicItems != null) {
            return comicItems.size();
        } else {
            return 0;
        }
    }
}
