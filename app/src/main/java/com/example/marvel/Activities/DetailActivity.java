package com.example.marvel.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.marvel.Adapters.ComicsAdapter;
import com.example.marvel.Models.ComicItems;
import com.example.marvel.R;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private ImageView characterThumbnailImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        // Initialize our views
        TextView characterDescriptionTextView = findViewById(R.id.characterDescription);
        characterThumbnailImageView = findViewById(R.id.characterThumbnailDetail);
        TextView characterNameDetail = findViewById(R.id.characterNameDetail);
        TextView logistics = findViewById(R.id.availableAndReturned);
        View relativeLayout = findViewById(R.id.detailRelativeLayout);

        // Get the values from the intent passed from the Character Adapter
        Intent intent = getIntent();
        String characterThumbnail = intent.hasExtra("thumbnail") ? intent.getStringExtra("thumbnail") : "Default";
        String characterName = intent.hasExtra("characterName") ? intent.getStringExtra("characterName") : "Character name not provided";
        String characterDescription = intent.hasExtra("characterDescription") ? intent.getStringExtra("characterDescription") : "Default";
        int available = intent.hasExtra("available") ? intent.getIntExtra("available", 0) : 0;
        int returned = intent.hasExtra("returned") ? intent.getIntExtra("returned", 0) : 0;
        ArrayList<ComicItems> comicList = intent.hasExtra("comicList") ? intent.getParcelableArrayListExtra("comicList") : null;

        loadImage(characterThumbnail);

        // Set character description
        if (characterDescription.equals("")) {
            String noDesc = "There is no description provided for " + characterName + ". All data provided by Marvel © 2020 MARVEL.";
            characterDescriptionTextView.setText(noDesc);
        } else {
            characterDescriptionTextView.setText(characterDescription);
        }

        // If there are no comics returned from the API, hide the recycler view
        String comicsFeaturedIn;
        if(comicList.isEmpty()) {
            comicsFeaturedIn = "There are no comics listed for " + characterName + ". All data provided by Marvel © 2020 MARVEL.";
            relativeLayout.setVisibility(View.GONE);
        } else {
            comicsFeaturedIn = "Comics where " + characterName + " is featured in:";
            String logisticString = "Available: " + available + ", Returned: " + returned;
            logistics.setText(logisticString);
            instantiateRecyclerView(comicList);
        }

        // Header text above recycler view
        characterNameDetail.setText(comicsFeaturedIn);
    }

    private void loadImage(String imagePath) {
        Picasso.with(getApplicationContext())
                .load(imagePath)
                .placeholder(R.drawable.placeholder)
                .into(characterThumbnailImageView);
    }

    private void instantiateRecyclerView(ArrayList<ComicItems> items) {
        RecyclerView recyclerView = findViewById(R.id.detailRecyclerView);
        ComicsAdapter adapter = new ComicsAdapter(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return (super.onOptionsItemSelected(item));
    }
}