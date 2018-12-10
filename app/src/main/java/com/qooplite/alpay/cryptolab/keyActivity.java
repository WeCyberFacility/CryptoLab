package com.qooplite.alpay.cryptolab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class keyActivity extends AppCompatActivity {

    RecyclerView recycleViewKeys;
    ArrayList<Key> keyliste = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);


        recycleViewKeys = findViewById(R.id.rvkeys);
        recycleViewKeys.setLayoutManager(new LinearLayoutManager(getApplicationContext()));





        recycleViewKeys.setAdapter(new KeyListAdapter(keyliste, getApplicationContext()));



    }
}
