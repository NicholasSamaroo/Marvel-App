package com.example.marvel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;


public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private ArrayList<Results> resultsList;
    private Context context;

    public CharacterAdapter(Context applicationContext, ArrayList<Results> results) {
        this.context = applicationContext;
        this.resultsList = results;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView characterImage;
        public TextView characterText;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            characterImage = itemView.findViewById(R.id.characterThumbnail);
            characterText = itemView.findViewById(R.id.characterName);
            cardView = itemView.findViewById(R.id.card);
        }
    }
    @NonNull
    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View cardView = layoutInflater.inflate(R.layout.character_card, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.ViewHolder holder, int position) {
        // This is where I would set the image view with Picasso or Glide
        holder.characterText.setText(resultsList.get(position).getName());

        // http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73/portrait_xlarge.jpg
        String formImagePath = resultsList.get(position).getThumbnail().getPath().replace("http", "https")
                + "/landscape_medium." + resultsList.get(position).getThumbnail().getExtension();

        Picasso.with(context)
                .load(formImagePath)
                .fit()
                .placeholder(R.drawable.placeholder)
                .into(holder.characterImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("thumbnail", formImagePath);
                intent.putExtra("characterName", resultsList.get(position).getName());
                intent.putExtra("characterDescription", resultsList.get(position).getDescription());
                intent.putExtra("comicList", resultsList.get(position).getComics().getItems());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(resultsList != null) {
            return resultsList.size();
        } else {
            return 0;
        }
    }
}
