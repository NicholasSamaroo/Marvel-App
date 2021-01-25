package com.example.marvel.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marvel.Adapters.CharacterAdapter;
import com.example.marvel.MarvelAPI;
import com.example.marvel.Models.Results;
import com.example.marvel.Models.Wrapper;
import com.example.marvel.R;
import com.example.marvel.RetrofitInstance;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CharacterAdapter.OnCardListener {
    private long timeStamp;
    private String hash;
    private EditText editText;
    private ArrayList<Results> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        Button search = findViewById(R.id.search);

        /*  Part of the call to the API requires us to pass an MD5 hash of a time stamp, our private key, and our public key
            The time stamp is chosen from system time and you need to provide your private and public keys as indicated
        * */
        String publicKey = "c55b9a56c8fd2080fc238d4666c2386d";
        String privateKey = "5b851630b7f50916442ac0b1d9edae6f30737b36";
        timeStamp = System.currentTimeMillis();
        hash = MD5(timeStamp + privateKey + publicKey);

        MarvelAPI marvelAPI = RetrofitInstance.getRetrofitInstance().create(MarvelAPI.class);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(editText.getText().toString().equals(""))) {
                    Call<Wrapper> call = marvelAPI.getCharacters(timeStamp, hash, editText.getText().toString(),"c55b9a56c8fd2080fc238d4666c2386d");
                    call.enqueue(new Callback<Wrapper>() {
                        @Override
                        public void onResponse(Call<Wrapper> call, Response<Wrapper> response) {
                            if(response.isSuccessful()) {
                                if(response.body().getData().getResults().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Not found, please try again", Toast.LENGTH_LONG).show();
                                } else {
                                    editText.getText().clear();
                                    results = response.body().getData().getResults();
                                    loadDataRecyclerView();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Not a successful response received", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Wrapper> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a search term into the field", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Generate MD5 hash
    private String MD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger md5Data = new BigInteger(1, md.digest(input.getBytes()));
            return String.format("%032x", md5Data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadDataRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        CharacterAdapter adapter = new CharacterAdapter(this, results, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    /* Implemented the interface defined in the CharacterAdapter which was created to listen for recycler view clicks
    *  When an item is clicked in the recycler view, the position of the item is brought back to this activity
    *  Since the list we passed to the adapter was instantiated here, we can use the position received from the adapter
    *  to navigate to the DetailsActivity */
    @Override
    public void OnCardClick(int position) {
        String formImagePath = results.get(position).getThumbnail().getPath().replace("http", "https")
                + "/landscape_medium." + results.get(position).getThumbnail().getExtension();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("thumbnail", formImagePath);
        intent.putExtra("characterName", results.get(position).getName());
        intent.putExtra("characterDescription", results.get(position).getDescription());
        intent.putExtra("available", results.get(position).getComics().getAvailable());
        intent.putExtra("returned", results.get(position).getComics().getReturned());
        intent.putExtra("comicList", results.get(position).getComics().getItems());
        startActivity(intent);
    }
}