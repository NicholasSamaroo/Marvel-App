package com.example.marvel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvel.Activities.DetailActivity;
import com.example.marvel.R;
import com.example.marvel.Models.Results;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    // Interface for passing the position of the clicked recycler view item to the Main Activity
    public interface OnCardListener {
        void OnCardClick(int position);
    }

    private final ArrayList<Results> resultsList;
    private final Context context;
    private final OnCardListener mOnCardListener;

    public CharacterAdapter(Context applicationContext, ArrayList<Results> results, OnCardListener onCardListener) {
        this.context = applicationContext;
        this.resultsList = results;
        this.mOnCardListener = onCardListener;
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView characterImage;
        public TextView characterText;
        public OnCardListener onCardListener;

        public CharacterViewHolder(@NonNull View itemView, OnCardListener onCardListener) {
            super(itemView);
            characterImage = itemView.findViewById(R.id.characterThumbnail);
            characterText = itemView.findViewById(R.id.characterName);
            this.onCardListener = onCardListener;

            // Refer to the View.OnClickListener interface the view holder is implementing
            itemView.setOnClickListener(this);
        }

        /* Whenever a view holder is clicked, we want to pass its position to the main activity
        *  where we navigate to the details activity with the pertinent information*/
        @Override
        public void onClick(View v) {
            onCardListener.OnCardClick(getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public CharacterAdapter.CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View cardView = layoutInflater.inflate(R.layout.character_card, parent, false);
        return new CharacterViewHolder(cardView, mOnCardListener);
    }

    /* Instead of setting an onClickListener every time we need to bind data to a view holder,
    *  we instead create an interface in the adapter that passes the item position back to the main activity */
    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.CharacterViewHolder holder, int position) {
        holder.characterText.setText(resultsList.get(position).getName());

        // Example path  http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73/portrait_xlarge.jpg
        String formImagePath = resultsList.get(position).getThumbnail().getPath().replace("http", "https")
                + "/landscape_medium." + resultsList.get(position).getThumbnail().getExtension();

        Picasso.with(context)
                .load(formImagePath)
                .fit()
                .placeholder(R.drawable.placeholder)
                .into(holder.characterImage);
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
