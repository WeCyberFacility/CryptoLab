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

        Key hi = new Key("Alpay", "asdnja");
        Key hii = new Key("Metin", "asdnja");
        Key hiii = new Key("Cem", "asdnja");
        Key hiiii = new Key("Taha", "asdnja");

        keyliste.add(hi);
        keyliste.add(hii);
        keyliste.add(hiii);
        keyliste.add(hiiii);

        recycleViewKeys.setAdapter(new KeyListAdapter(keyliste, getApplicationContext()));



    }
}
