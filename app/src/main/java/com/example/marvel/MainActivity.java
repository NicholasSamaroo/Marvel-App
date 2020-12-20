package com.example.marvel;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    long timeStamp;
    String privateKey;
    String publicKey;
    String hash;
    RecyclerView mRecyclerView;
    CharacterAdapter adapter;
    EditText editText;
    Button search;
    ArrayList<Results> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        search = findViewById(R.id.search);
        MarvelAPI marvelAPI = RetrofitInstance.getRetrofitInstance().create(MarvelAPI.class);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(editText.getText().toString().equals(""))) {
                    Call<Wrapper> call = marvelAPI.getCharacters(1, "9956cadc0e996a8117203816730f73f3", editText.getText().toString(),"c55b9a56c8fd2080fc238d4666c2386d");
                    call.enqueue(new Callback<Wrapper>() {
                        @Override
                        public void onResponse(Call<Wrapper> call, Response<Wrapper> response) {
                            if(response.isSuccessful()) {
                                if(response.body().getData().getResults().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Not found, please try again", Toast.LENGTH_LONG).show();
                                } else {
                                    results = response.body().getData().getResults();
                                    loadDataRecyclerView(results);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "That character was not found, please try again", Toast.LENGTH_LONG).show();
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

        /* publicKey = "c55b9a56c8fd2080fc238d4666c2386d";
        privateKey = "5b851630b7f50916442ac0b1d9edae6f30737b36";
        timeStamp = System.currentTimeMillis();
        hash = md5(publicKey + privateKey + timeStamp); */

    }

    /*public String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }*/

    public void loadDataRecyclerView(ArrayList<Results> response) {
        mRecyclerView = findViewById(R.id.recyclerView);
        adapter = new CharacterAdapter(getApplicationContext(),response);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mRecyclerView.setAdapter(adapter);
    }

}