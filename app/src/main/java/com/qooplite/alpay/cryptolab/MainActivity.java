package com.qooplite.alpay.cryptolab;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public  class

MainActivity extends AppCompatActivity {



    ImageView keyBtn;
    ImageView lockBtn;
    ImageView unlockBtn;
    EditText eingabeText;
    TextView outputText;
    Button clrBtn;
    Spinner schluesselAuswahl;
    ConstraintLayout eingabeLayout;
    ConstraintLayout ausgabeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyBtn = findViewById(R.id.keybtn);
        lockBtn = findViewById(R.id.lockbtn);
        unlockBtn = findViewById(R.id.unlockbtn);
        eingabeText = findViewById(R.id.eingabetxt);
        outputText = findViewById(R.id.outputtxtx);
        clrBtn = findViewById(R.id.clrbtn);
        schluesselAuswahl = findViewById(R.id.schluesselauswahl);
        eingabeLayout = findViewById(R.id.constraintLayoutout1);
        ausgabeLayout = findViewById(R.id.constraintLayoutout);



      spinnerUpdaten();

        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce1);

                eingabeText.startAnimation(animation);
                outputText.startAnimation(animation);
                eingabeText.setText("");
                outputText.setText("");





            }
        });



        //On Click Listener für das Key Logo oben Rechts
        keyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

                keyBtn.startAnimation(animation);
                startActivity(new Intent(MainActivity.this, keyActivity.class));



            }
        });


        //On Click Listener für Lock
        lockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String eingabe = eingabeText.getText().toString().trim();
               // String ausgabe = outputText.getText().toString().trim();
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

                lockBtn.startAnimation(animation);

                if (schluesselAuswahl.getSelectedItem().toString().equals("Custom")) {

                    Toast.makeText(MainActivity.this, "Custom Mode choosen", Toast.LENGTH_SHORT).show();

                } else {
                    int i = 0;

                    while(schluesselAuswahl.getSelectedItem().toString().equals(loadSharedPreferencesLogList(getApplicationContext()).get(i).getName()) == false) {

                        i++;
                        continue;


                    }

                    Key gefundenerKey = loadSharedPreferencesLogList(getApplicationContext()).get(i);

                    Toast.makeText(MainActivity.this, "The Key: " + gefundenerKey.getName() + "was used!", Toast.LENGTH_SHORT).show();

                    ModVer(gefundenerKey.getVariable1(), gefundenerKey.getVariable2());


                }






            }
        });


        //On Click Listener für Unlock
        unlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

                unlockBtn.startAnimation(animation);

                if(schluesselAuswahl.getSelectedItem().toString().equals("Custom")) {

                    Toast.makeText(MainActivity.this, "Custom Mode choosen", Toast.LENGTH_SHORT).show();

                } else {

                    int i = 0;

                    while(schluesselAuswahl.getSelectedItem().toString().equals(loadSharedPreferencesLogList(getApplicationContext()).get(i).getName()) == false ) {

                        i++;
                        continue;


                    }

                    Key gefundenerKey = loadSharedPreferencesLogList(getApplicationContext()).get(i);

                    Toast.makeText(MainActivity.this, "The Key: " + gefundenerKey.getName() + "was used!", Toast.LENGTH_SHORT).show();

                    ModEnt(gefundenerKey.getVariable1(), gefundenerKey.getVariable2());


                }




            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        spinnerUpdaten();
    }

    public void spinnerUpdaten() {

        String[] keys = new String[loadSharedPreferencesLogList(getApplicationContext()).size() + 1];

        for(int t= 0; t<keys.length-1; t++) {

            keys[t] = loadSharedPreferencesLogList(getApplicationContext()).get(t).getName();

        }

        keys[loadSharedPreferencesLogList(getApplicationContext()).size()] = "Custom";


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, keys
                ); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        schluesselAuswahl.setAdapter(spinnerArrayAdapter);

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



    public void ModVer(int variable1, int variable2){
        int count=0;

        String inputPW = eingabeText.getText().toString().trim();

        String hashedPW;

        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü";

        String doubleAllChars= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü";


        char[] hashedChars = new char[inputPW.length()];


        for(int i =0;i<inputPW.length();i++) {

            for (int j = 0; j < allChars.length(); j++) {


                if (inputPW.charAt(i) == allChars.charAt(j)) {

                    if (i % 2 == 0) {
                        hashedChars[i] = doubleAllChars.charAt(i + j + variable1);
                        break;
                    } else {
                        hashedChars[i] = doubleAllChars.charAt(i + j + variable2);
                        break;
                    }


                } else {
                    count++;


                    if (count == allChars.length()) {
                        hashedChars[i] = inputPW.charAt(i);
                        count = 0;
                    }

                }

            }
        }

        hashedPW= String.valueOf(hashedChars);

        outputText.setText(hashedPW);
    }








    public void ModEnt(int variable1, int variable2){
        int count=0;

        String PW = eingabeText.getText().toString().trim();

        String hashedPW;

        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü";

        String reversedAll = new StringBuilder(allChars).reverse().toString();

        String doubleAllChars= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü"
                +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü";

        String reversedDoubleAll = new StringBuilder(doubleAllChars).reverse().toString();

        char[] hashedChars = new char[PW.length()];


        for(int i =0;i<PW.length();i++) {

            for (int j = 0; j <  reversedAll.length(); j++) {


                if (PW.charAt(i) ==  reversedAll.charAt(j)) {

                    if (i % 2 == 0) {
                        hashedChars[i] =reversedDoubleAll.charAt(i + j + variable1);
                        break;
                    } else {
                        hashedChars[i] = reversedDoubleAll.charAt(i + j + variable2);
                        break;
                    }


                } else {
                    count++;


                    if (count == reversedAll.length()) {
                        hashedChars[i] = PW.charAt(i);
                        count = 0;
                    }

                }

            }
        }

        hashedPW= String.valueOf(hashedChars);


        outputText.setText(hashedPW);
    }





}






