package com.example.marvel;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class DetailActivity extends AppCompatActivity {
    String characterThumbnail;
    String characterName;
    String characterDescription;
    ArrayList<ComicItems> comicList;

    ImageView characterThumbnailImageView;
    TextView characterDescriptionTextView;
    TextView characterNameDetail;

    RecyclerView recyclerView;
    ComicsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        characterDescriptionTextView = findViewById(R.id.characterDescription);
        characterThumbnailImageView = findViewById(R.id.characterThumbnailDetail);
        characterNameDetail = findViewById(R.id.characterNameDetail);

        Intent intent = getIntent();
        characterThumbnail = intent.hasExtra("thumbnail") ? intent.getStringExtra("thumbnail") : "Default";
        characterName = intent.hasExtra("characterName") ? intent.getStringExtra("characterName") : "Character name not provided";
        characterDescription = intent.hasExtra("characterDescription") ? intent.getStringExtra("characterDescription") : "Default";
        comicList = intent.hasExtra("comicList") ? intent.getParcelableArrayListExtra("comicList") : null;
        loadImage(characterThumbnail);

        if(characterDescription.equals("")) {
            String noDesc = "There is no description provided for " + characterName;
            characterDescriptionTextView.setText(noDesc);
        } else {
            characterDescriptionTextView.setText(characterDescription);
        }

        String comicsFeaturedIn = comicList.isEmpty() ? "There are no comics listed for " + characterName : "Comics where " + characterName + " is featured in:";
        characterNameDetail.setText(comicsFeaturedIn);

        instantiateRecyclerView(comicList);
    }

    public void loadImage(String imagePath) {
        Picasso.with(getApplicationContext())
                .load(imagePath)
                .placeholder(R.drawable.placeholder)
                .into(characterThumbnailImageView);
    }

    public void instantiateRecyclerView(ArrayList<ComicItems> items) {
        recyclerView = findViewById(R.id.detailRecyclerView);
        adapter = new ComicsAdapter(items);
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