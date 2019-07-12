package com.qooplite.alpay.cryptolab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

public class EncryptionWahl extends AppCompatActivity {


    ArrayList<Verschluesselung> verschluesselungsListe = new ArrayList<>();
    RecyclerView verschluesselungenrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption_wahl);
        hideSystemUI();

        verschluesselungenrv = (RecyclerView) findViewById(R.id.verschluesselungenrecycleview);
        verschluesselungenrv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        verschluesselungenLaden();



    }

    public void hideSystemUI(){


        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }



    public void verschluesselungenLaden(){

        Verschluesselung xor = new Verschluesselung("1", "XOR", 1,
                "Binäre XOR‐Verknüpfung zweier HEX‐Strings",
                "zwei HEX Strings",
                "Binäre Zahl als ein String (z.B. „101001010…“).", 2);
        Verschluesselung modulo = new Verschluesselung("2", "Modulo", 1,
                "Modulo‐Berechnung zweier Integer.",
                "Integerzahl1, Integerzahl2",
                "Integerzahl1 mod Integerzahl2", 2);
        Verschluesselung faktorization = new Verschluesselung("3", "Faktorization", 2,
                "Zufallszahl in Primfaktoren zerlegen.",
                "Zufallszahl als Integer",
                "Primfaktoren, aufsteigend sortiert und mit Sternchen(*) getrennt als String. (z.B.: 2*2*5*7)", 1);
        Verschluesselung vignere = new Verschluesselung("4", "Vignere", 3,
                "Entschlüsselung eines Chiffretexts per Vigenère‐Verfahren mit gegebenem Schlüssel.",
                "Chiffretext als String, Key als String",
                "Klartext als ein String (Groß‐/Kleinschreibung wird nicht berücksichtigt)", 2);
        Verschluesselung desKeyschedule = new Verschluesselung("5", "DES Key-Schedule", 3,
                "Berechnung des Rundenschlüssels für eine gegebene Runde und gegebenen Schlüssel.",
                "Key als Binär‐String, geforderte Runde (1 ‐ 16) als Integer",
                "Roundkey als ein Binär‐String (48Bit) (z.B. „1001111011…“)", 2);
        Verschluesselung desrblock = new Verschluesselung("6", "DES R-Block", 3,
                "Berechnung des R‐Blocks für eine gegebene Runde und gegebenen Input. Der Schlüssel wird hierbei als 0 angenommen.",
                "Binär‐String (64Bit), geforderte Runde (1 ‐ 16) als Integer",
                "R‐Block als Binär‐String (z.B. „1001111011…“)", 2);
        Verschluesselung desfeistel = new Verschluesselung("7", "DES Feistel", 4,
                "Einmalige Anwendung der f-Funktion mit vorgegebenem Rundenschlüssel. Die ersten 32 Bit des Inputs bilden den L-Block, die zweiten 32 Bit den R-Block.",
                "Input als Binär-String(64Bit), Rundenschlüssel als Binär-String (48Bit)",
                "L-Block XOR R-Block als Binär-String", 2);
        Verschluesselung desround = new Verschluesselung("8", "DES Round", 5,
                "Durchführung einer kompletten Runde inkl. Rundenschlüssel‐Berechnung.",
                "L‐Block der vorherigen Runde (Binär‐String, 32Bit), R‐Block der vorherigen Runde (Binär‐String, 32Bit), Key (64Bit), geforderte Runde als Integer",
                "Binär‐String (64Bit, zuerst L‐Block 32Bit dann R‐Block 32Bit) (z.B.„1001111011…“)", 4);
        Verschluesselung aesgf8 = new Verschluesselung("9", "AES GF8", 2,
                "Multiplikation zweier HEX‐Zahlen in GF(28)",
                "HEX‐Zahl1 als String, HEX‐Zahl2 als String",
                "Ergebnis der GF8‐Multiplikation als HEX‐String (Groß‐/Kleinschreibung wird nicht berücksichtigt.) (z.B. „2f“)", 2);
        Verschluesselung aeskeyexpansion = new Verschluesselung("10", "AES Key-Expansion", 4,
                "Generierung von drei Rundenschlüsseln. Als Output werden alle drei Schlüssel der Reihenfolge entsprechend jeweils durch einen Unterstrich („_“) getrennt und in Hexadezimal‐Darstellung ausgegeben..",
                "Key als HEX‐String (128 Bit)",
                "HEX‐String aller drei Rundenschlüssel (jeweils 128 Bit) jeweils durch einen Unterstrich („_“) getrennt (z.B. „a56e…_35c…_72a…“)", 1);
        Verschluesselung aesmixcolumns = new Verschluesselung("11", "AES Mix-Columns", 4,
                "Durchführung der MixColumns‐Funktion. Der Input ist spaltenweise angegeben. D.h. die ersten vier Byte des Inputs entsprechen der ersten Spalte.",
                "HEX‐String (128Bit)",
                "Gemischte Spalten als ein HEX‐String (128Bit) (z.B. „21ae5….“)", 1);
        Verschluesselung aestransformation = new Verschluesselung("12", "AES Transformation", 2,
                "Berechnung in der Reihenfolge von SubBytes(), ShiftRows() und MixColums() für einen gesamten Datenblock." ,
                "HEX‐String (128Bit)",
                "HEX‐String(128Bit) (z.B. „21ae5….“)", 1);
        Verschluesselung aes3rounds = new Verschluesselung("13", "AES 3-Rounds", 5,
                "Berechnung von „Initial Round“ und zwei weiterer „Standard Rounds“;.",
                "Datenblock als HEX‐String (128Bit), Key als HEX‐String (128Bit), Keyroom als Zahlen‐String (z.B. „128“)",
                "Ausgabe (des verschlüsselten Textes) aller drei Runden als ein HEX‐String (128Bit), wobei die verschlüsselten Texte der Reihenfolge nach sortiert sind und jeweils durch einen Unterstrich („_“) getrennt werden (z.B. 34e2…_e7c…_a45b…)", 3);
        Verschluesselung rc4loop = new Verschluesselung("14", "RC4 Loop", 3,
                "Generieren von pseudo‐zufälligen Bytes mithilfe des State‐Tables.",
                "State‐Table (Integer‐Werte durch Unterstrich („_“) getrennt. z.B.: 2_1_3_0 wären die State‐Table‐Werte an den Positionen null bis drei), Klartext als String",
                "Inhalt des State‐Tables an der Stelle t des jeweiligen Loops. Alle Werte werden in einem String (ohne Trennzeichen) hintereinander ausgegeben. (z.B. „2130“)", 2);
        Verschluesselung rc4keyschedule = new Verschluesselung("15", "RC4 Key-Schedule", 4,
                "RC4‐Schlüsselgenerierung.",
                "Key als Zahlen‐String (Integer‐Werte durch Unterstrich („_“) getrennt. z.B.: 1_7_1_7 wären die Schlüssel‐Werte an den Positionen null bis drei)",
                "State‐Table (Integer‐Werte durch Unterstrich („_“) getrennt. z.B.: 2_1_3_0 wären die State‐Table‐Werte an den Positionen null bis drei)", 1);
        Verschluesselung rc4encryption = new Verschluesselung("16", "RC4 Encryption", 5,
                "Verschlüsselung unter Verwendung des Generation Loops und des Keyschedulings.",
                "Key als Zahlen‐String (Integer‐Werte durch Unterstrich („_“) getrennt. z.B.: 1_7_1_7 wären die Schlüssel‐Werte an den Positionen null bis drei), Klartext als String",
                "Chiffretext als ein Binär‐String (z.B. „1000111101011…“)", 2);

        verschluesselungsListe.add(xor);
        verschluesselungsListe.add(modulo);
        verschluesselungsListe.add(faktorization);
        verschluesselungsListe.add(vignere);
        verschluesselungsListe.add(desKeyschedule);
        verschluesselungsListe.add(desrblock);
        verschluesselungsListe.add(desfeistel);
        verschluesselungsListe.add(desround);
        verschluesselungsListe.add(aesgf8);
        verschluesselungsListe.add(aeskeyexpansion);
        verschluesselungsListe.add(aesmixcolumns);
        verschluesselungsListe.add(aestransformation);
        verschluesselungsListe.add(aes3rounds);
        verschluesselungsListe.add(rc4loop);
        verschluesselungsListe.add(rc4keyschedule);
        verschluesselungsListe.add(rc4encryption);

        verschluesselungenrv.setAdapter(new VerschluesselungenAdapter(verschluesselungsListe, this));



    }


    public void hideKeyboard(View v ){
        InputMethodManager IpM = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        IpM.hideSoftInputFromWindow(v.getWindowToken(),0);
    }
}
