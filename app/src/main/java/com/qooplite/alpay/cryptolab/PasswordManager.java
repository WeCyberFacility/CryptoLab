package com.qooplite.alpay.cryptolab;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PasswordManager extends AppCompatActivity {

    public SharedPreferences pref;
    public SharedPreferences.Editor prefEditor;

    RecyclerView recycleViewPasswords;
    ArrayList<Password> passwordListe = new ArrayList<>();
    Dialog PasshinzuefuegenDialog;
    ImageView addPswBtn;

    //Dialog Komponente:

    Button PasswordAddenBtn;
    EditText nameEingabePM;
    EditText passwordEingabePM;
    Spinner schlüsselPM;
    TextView ErrorOutPM;
    int checkCounter =0 ;

    private String KEY = "myCryptoLabKEY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_manager);



        pref = this.getSharedPreferences("DATEN",MODE_PRIVATE);
        prefEditor = pref.edit();




        addPswBtn = findViewById(R.id.addpswbtn);
        PasshinzuefuegenDialog = new Dialog(PasswordManager.this);
        PasshinzuefuegenDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        PasshinzuefuegenDialog.setContentView(R.layout.passwordhinzuegfuegendialog);

        PasswordAddenBtn = PasshinzuefuegenDialog.findViewById(R.id.passwordaddenbtn);
        nameEingabePM = PasshinzuefuegenDialog.findViewById(R.id.nameingabepm);
        passwordEingabePM = PasshinzuefuegenDialog.findViewById(R.id.passwordeingabetxt);
        schlüsselPM = PasshinzuefuegenDialog.findViewById(R.id.spinnerpm);
        ErrorOutPM = PasshinzuefuegenDialog.findViewById(R.id.dialogErrorpm);


        recycleViewPasswords = findViewById(R.id.rvpasswords);
        recycleViewPasswords.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        spinnerUpdaten();

        passwordListe = loadSharedPreferencesLogList(getApplicationContext());



        recycleViewPasswords.setAdapter(new PasswordListAdapter(passwordListe, getApplicationContext()));


        addPswBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PasshinzuefuegenDialog.show();


            }
        });


        PasswordAddenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {







                passwordListe = loadSharedPreferencesLogList(getApplicationContext());

                int i = 0;

                while(schlüsselPM.getSelectedItem().toString().equals(loadKeys(getApplicationContext()).get(i).getName()) == false ) {

                    i++;
                    continue;


                }

                Key gefundenerKey = loadKeys(getApplicationContext()).get(i);

                String verschluesseltesPassword = ModVer(gefundenerKey.getVariable1(), gefundenerKey.getVariable2());

                Password neuesPassword = new Password(nameEingabePM.getText().toString(), verschluesseltesPassword);

                passwordListe.add(neuesPassword);

                saveSharedPreferencesLogList(getApplicationContext(), passwordListe);

                recycleViewPasswords.setAdapter(new PasswordListAdapter(passwordListe, getApplicationContext()));




            }
        });

    }



    public static ArrayList<Password> loadSharedPreferencesLogList(Context context) {
        ArrayList<Password> passwordlisst = new ArrayList<Password>();
        SharedPreferences mPrefs = context.getSharedPreferences("PasswordListe", context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson2", "");
        if (json.isEmpty()) {
            passwordlisst = new ArrayList<Password>();
        } else {
            Type type = new TypeToken<ArrayList<Key>>() {
            }.getType();
            passwordlisst = gson.fromJson(json, type);
        }

        return passwordlisst;
    }



    public static void saveSharedPreferencesLogList(Context context, ArrayList<Password> paswordlisteeee) {
        SharedPreferences mPrefs = context.getSharedPreferences("PasswordListe", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(paswordlisteeee);
        prefsEditor.putString("myJson2", json);
        prefsEditor.commit();
    }


    public void spinnerUpdaten() {

        String[] Passwords = new String[loadKeys(getApplicationContext()).size()];

        for(int t= 0; t<Passwords.length; t++) {

            Passwords[t] = loadKeys(getApplicationContext()).get(t).getName();

        }




        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, Passwords
                ); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        schlüsselPM.setAdapter(spinnerArrayAdapter);

    }


    public static ArrayList<Key> loadKeys(Context context) {
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


    public String ModVer(int variable1, int variable2){
        int count=0;

        String inputPW = passwordEingabePM.getText().toString().trim();

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

       return hashedPW;
    }

}
