package com.qooplite.alpay.cryptolab;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    ImageView keyBtn;
    ImageView lockBtn;
    ImageView unlockBtn;
    EditText eingabeText;
    TextView outputText;
    Button clrBtn;
    Spinner schluesselAuswahl;
    ConstraintLayout eingabeLayout;
    ConstraintLayout ausgabeLayout;
    EditText var1Txt;
    EditText var2Txt;
    ImageView copyLogo;
    ImageView goToAccuntsbtn;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-7177574010293341~7273031738");


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7177574010293341/7081460040");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        keyBtn =(ImageView) findViewById(R.id.keybtn);
        lockBtn =(ImageView) findViewById(R.id.lockbtn);
        unlockBtn =(ImageView) findViewById(R.id.unlockbtn);
        eingabeText = (EditText) findViewById(R.id.eingabetxt);
        outputText = (TextView) findViewById(R.id.outputtxtx);
        clrBtn = (Button) findViewById(R.id.clrbtn);
        schluesselAuswahl = (Spinner) findViewById(R.id.schluesselauswahl);
        eingabeLayout = (ConstraintLayout) findViewById(R.id.constraintLayoutout1);
        ausgabeLayout = (ConstraintLayout) findViewById(R.id.constraintLayoutout);
        var1Txt = (EditText) findViewById(R.id.var1);
        var2Txt = (EditText) findViewById(R.id.var2);
        copyLogo = (ImageView) findViewById(R.id.copylogo);
        goToAccuntsbtn  =(ImageView) findViewById(R.id.goToAccounts);



      spinnerUpdaten();


      copyLogo.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              //ad laden:
              if (mInterstitialAd.isLoaded()) {
                  mInterstitialAd.show();
              } else {
                  Toast.makeText(MainActivity.this, "Ad wurde nicht geladen!", Toast.LENGTH_SHORT).show();
              }

              if(outputText.getText().toString().equals("")) {



              } else {

                  Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

                  copyLogo.startAnimation(animation);



                  ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                  ClipData clip = ClipData.newPlainText("label", outputText.getText().toString());
                  clipboard.setPrimaryClip(clip);

                  Toast.makeText(MainActivity.this, "copied", Toast.LENGTH_SHORT).show();


              }






          }
      });


        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce1);

                eingabeText.startAnimation(animation);
                outputText.startAnimation(animation);
                eingabeText.setText("");
                outputText.setText("");
                var1Txt.setText("");
                var2Txt.setText("");





            }
        });




        //On Click Listener für das Key Logo oben Rechts
        keyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

                keyBtn.startAnimation(animation);
                startActivity(new Intent(MainActivity.this, keyActivity.class));
               finish();


            }
        });



        goToAccuntsbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PasswordManager.class));
                finish();
            }
        });




        //On Click Listener für Lock
        lockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager IpM = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                IpM.hideSoftInputFromWindow(view.getWindowToken(),0);

                String eingabe = eingabeText.getText().toString().trim();
               // String ausgabe = outputText.getText().toString().trim();
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

                lockBtn.startAnimation(animation);

                if (schluesselAuswahl.getSelectedItem().toString().equals("Custom")) {



                    if(var1Txt.getText().toString().equals("") || var2Txt.getText().toString().equals("")) {

                        Toast.makeText(MainActivity.this, "please give two variables!", Toast.LENGTH_SHORT).show();

                    } else {


                        if(Integer.parseInt(var1Txt.getText().toString()) > 10 || Integer.parseInt(var2Txt.getText().toString()) > 10) {

                            Toast.makeText(MainActivity.this, "numbers above 10 are not allowed!", Toast.LENGTH_SHORT).show();

                        } else {
                            ModVer(Integer.parseInt(var1Txt.getText().toString()), Integer.parseInt(var2Txt.getText().toString()));
                        }



                    }


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


        schluesselAuswahl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(schluesselAuswahl.getSelectedItem().toString().equals("Custom")) {

                    var1Txt.setVisibility(View.VISIBLE);
                    var2Txt.setVisibility(View.VISIBLE);

                } else {


                    var1Txt.setVisibility(View.INVISIBLE);
                    var2Txt.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //On Click Listener für Unlock
        unlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                InputMethodManager IpM = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                IpM.hideSoftInputFromWindow(view.getWindowToken(),0);

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

                unlockBtn.startAnimation(animation);

                if(schluesselAuswahl.getSelectedItem().toString().equals("Custom")) {



                    if(var1Txt.getText().toString().equals("") || var2Txt.getText().toString().equals("")) {

                        Toast.makeText(MainActivity.this, "please give two variables!", Toast.LENGTH_SHORT).show();

                    } else {


                        if(Integer.parseInt(var1Txt.getText().toString()) > 10 || Integer.parseInt(var2Txt.getText().toString()) > 10) {

                            Toast.makeText(MainActivity.this, "numbers above 10 are not allowed!", Toast.LENGTH_SHORT).show();

                        } else {
                            ModEnt(Integer.parseInt(var1Txt.getText().toString()), Integer.parseInt(var2Txt.getText().toString()));
                        }

                    }


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

        int anzahlCustomKeys = loadSharedPreferencesLogList(getApplicationContext()).size();


        keys[anzahlCustomKeys] = "Custom";



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

    public void hideKeyboard(View v ){
        InputMethodManager IpM = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        IpM.hideSoftInputFromWindow(v.getWindowToken(),0);
    }





}






