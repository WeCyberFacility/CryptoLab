package com.qooplite.alpay.cryptolab;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class keyActivity extends AppCompatActivity {


public SharedPreferences pref;
public SharedPreferences.Editor prefEditor;

    RecyclerView recycleViewKeys;
    ArrayList<Key> keyliste = new ArrayList<>();
    Dialog keyhinzuefuegenDialog;
    ImageView addkeyBtn;

    //Dialog Komponente:
    EditText nmbr1Eingabe;
    EditText nmbr2Eingabe;
    Button keyAddenBtn;
    EditText nameEingabe;
    private String KEY = "myCryptoLabKEY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);



        pref = this.getSharedPreferences("DATEN",MODE_PRIVATE);
        prefEditor = pref.edit();




        addkeyBtn = findViewById(R.id.addkeybtn);
        keyhinzuefuegenDialog = new Dialog(keyActivity.this);
        keyhinzuefuegenDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        keyhinzuefuegenDialog.setContentView(R.layout.keyhinzuefuegendialoglayout);

        nmbr1Eingabe = keyhinzuefuegenDialog.findViewById(R.id.number1eingabe);
        nmbr2Eingabe = keyhinzuefuegenDialog.findViewById(R.id.number2eingabe);
        keyAddenBtn = keyhinzuefuegenDialog.findViewById(R.id.keyaddenbtn);
        nameEingabe = keyhinzuefuegenDialog.findViewById(R.id.nameingabe);



        recycleViewKeys = findViewById(R.id.rvkeys);
        recycleViewKeys.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        keyliste = loadSharedPreferencesLogList(getApplicationContext());



        recycleViewKeys.setAdapter(new KeyListAdapter(keyliste, getApplicationContext()));


        addkeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                keyhinzuefuegenDialog.show();


            }
        });


        keyAddenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Key neu = new Key(nameEingabe.getText().toString(),Integer.parseInt(nmbr1Eingabe.getText().toString()),Integer.parseInt(nmbr2Eingabe.getText().toString()));


                keyliste.add(neu);

                saveSharedPreferencesLogList(getApplicationContext(), keyliste);


                recycleViewKeys.setAdapter(new KeyListAdapter(keyliste, getApplicationContext()));

                Toast.makeText(keyActivity.this, "Key addet successfully!", Toast.LENGTH_SHORT).show();
                //Hier der Code um den Key bei shared Pref zu speichern:



            }
          });

    }



    public static ArrayList<Key> loadSharedPreferencesLogList(Context context) {
        ArrayList<Key> keylistee = new ArrayList<Key>();
        SharedPreferences mPrefs = context.getSharedPreferences("KeyListe", context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            keylistee = new ArrayList<Key>();
        } else {
            Type type = new TypeToken<ArrayList<Key>>() {
            }.getType();
            keylistee = gson.fromJson(json, type);
        }

        return keylistee;
    }



    public static void saveSharedPreferencesLogList(Context context, ArrayList<Key> keylisteee) {
        SharedPreferences mPrefs = context.getSharedPreferences("KeyListe", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(keylisteee);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
    }

}
