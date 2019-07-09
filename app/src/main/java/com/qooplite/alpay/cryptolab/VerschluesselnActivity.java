package com.qooplite.alpay.cryptolab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class VerschluesselnActivity extends AppCompatActivity {


    public static Verschluesselung currentVerschluesselung;

    //Alle GUI Elemente deklarieren:
    TextView namever;
    ImageView stern1;
    ImageView stern2;
    ImageView stern3;
    ImageView stern4;
    ImageView stern5;
    ImageView infoVer;

    EditText inputTxt1;
    EditText inputTxt2;
    EditText inputTxt3;
    EditText inputTxt4;
    EditText outputTxt;


    ImageView verschluesselnBtn;

    ArrayList<ImageView> sterne = new ArrayList<>();
    ArrayList<EditText> inputs = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verschluesseln);

        namever = findViewById(R.id.nameec);
        stern1 = findViewById(R.id.stern1ec);
        stern2 = findViewById(R.id.stern2ec);
        stern3 = findViewById(R.id.stern3ec);
        stern4 = findViewById(R.id.stern4ec);
        stern5 = findViewById(R.id.stern5ec);
        verschluesselnBtn = findViewById(R.id.verschluesselnbtn);
        inputTxt1 = findViewById(R.id.input1txt);
        inputTxt2 = findViewById(R.id.input2txt);
        inputTxt3 = findViewById(R.id.input3txt);
        inputTxt4 = findViewById(R.id.input4txt);
        outputTxt = findViewById(R.id.outputtxtec);
        infoVer = findViewById(R.id.infoversc);

        sterne.add(stern1);
        sterne.add(stern2);
        sterne.add(stern3);
        sterne.add(stern4);
        sterne.add(stern5);

        inputs.add(inputTxt1);
        inputs.add(inputTxt2);
        inputs.add(inputTxt3);
        inputs.add(inputTxt4);


        infoLaden();
        inputsLaden();


        final Dialog infoDialog;
        infoDialog = new Dialog(this);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        infoDialog.setContentView(R.layout.verschluesselunginfolayout);
        infoDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final TextView infoName = infoDialog.findViewById(R.id.nameinfo);
        final TextView infotext = infoDialog.findViewById(R.id.infotext);
        final TextView infoinputtext = infoDialog.findViewById(R.id.inputtext);
        final TextView infoputputtext = infoDialog.findViewById(R.id.outputtext);

        infoVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoName.setText(currentVerschluesselung.getName().toString().trim());
                infotext.setText(currentVerschluesselung.getBeschreibung().toString().trim());
                infoinputtext.setText(currentVerschluesselung.getInput().toString().trim());
                infoputputtext.setText(currentVerschluesselung.getOutput().toString().trim());
                infoDialog.show();
            }
        });

        //clickListener:
        verschluesselnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean fehltwas = false;

                for(EditText currentET : inputs) {

                   if(currentET.getVisibility() == View.VISIBLE) {

                       if(inputTxt1.getText().toString().trim().equals("")) {

                           fehltwas = true;
                           break;
                       } else {

                           continue;
                       }
                   }
                }

                if(fehltwas) {

                    Toast.makeText(VerschluesselnActivity.this, "Please fill in all required inputs!", Toast.LENGTH_SHORT).show();

                } else {

                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

                    verschluesselnBtn.startAnimation(animation);

                    try {
                        verschluesseln(currentVerschluesselung.getName());
                    }catch (Exception e) {
                        Toast.makeText(VerschluesselnActivity.this, "something went wrong .. please check your inputs!", Toast.LENGTH_SHORT).show();

                    }



                }

            }
        });
    }

    public void infoLaden(){

        namever.setText(currentVerschluesselung.getName());

        for(int h = 0; h<currentVerschluesselung.getSicherheitslevel(); h++){

            sterne.get(h).setImageResource(R.mipmap.sternvoll);

        }


    }




    public void inputsLaden(){

        for(int i = currentVerschluesselung.getAnzahlInputs(); i<4; i++) {

            inputs.get(i).setVisibility(View.INVISIBLE);

        }

    }


    public void verschluesseln(String verschluesselungsName) {

        switch (verschluesselungsName) {
            case "XOR":
                outputTxt.setText(XOR(inputTxt1.getText().toString(), inputTxt2.getText().toString()));
                break;
            case "Modulo":
                outputTxt.setText(Modulo(Integer.parseInt(inputTxt1.getText().toString()), Integer.parseInt(inputTxt2.getText().toString())));
                break;
            case "Faktorization":
                outputTxt.setText(Faktorisierung(Integer.parseInt(inputTxt1.getText().toString())));
                break;
            case "Vignere":
                outputTxt.setText(Vignere(inputTxt1.getText().toString(), inputTxt2.getText().toString()));
                break;
            case "DES Key-Schedule":
                outputTxt.setText(getRoundKey(Integer.parseInt(inputTxt1.getText().toString()), inputTxt2.getText().toString()));
                break;
            case "DES R-Block":
                outputTxt.setText(getRBlockAfterRounds(inputTxt1.getText().toString(), Integer.parseInt(inputTxt2.getText().toString())));
                break;
            case "DES Feistel":
                outputTxt.setText(feistelFunktion(inputTxt1.getText().toString(), inputTxt2.getText().toString()));
                break;
            case "DES Round":
                outputTxt.setText(eineKompletteRunde(inputTxt1.getText().toString(), inputTxt2.getText().toString(),inputTxt3.getText().toString(), Integer.parseInt(inputTxt4.getText().toString())));
                break;
            case "AES GF8":
                outputTxt.setText(hexMultiplikation(inputTxt1.getText().toString(), inputTxt2.getText().toString()));
                break;
            case "AES Key-Expansion":
                outputTxt.setText(KeyExpansion(inputTxt1.getText().toString()));
                break;
            case "AES Mix-Columns":
                outputTxt.setText(MixColumns(inputTxt1.getText().toString()));
                break;
            case "AES Transformation":
            outputTxt.setText(AESTransformation(inputTxt1.getText().toString()));
                break;
            case "AES 3-Rounds":
            outputTxt.setText( AESDreiRunden(inputTxt1.getText().toString(), inputTxt2.getText().toString(), inputTxt3.getText().toString()));
                break;
            case "RC4 Loop":
            outputTxt.setText(RC4Loop(inputTxt1.getText().toString(), inputTxt2.getText().toString()));
                break;
            case "RC4 Key-Schedule":
            outputTxt.setText(RC4KeySchedule(inputTxt1.getText().toString()));
                break;
            case "RC4 Encryption":
            outputTxt.setText(RC4Verschluesselung(inputTxt1.getText().toString(), inputTxt2.getText().toString()));
                break;

                default:System.out.println("Verschlüsselung nicht gefunden!");


        }

    }
















    //Verschluesselungsmethoden:


    public String XOR(String ersterString, String zweiterString){


        if(ersterString.length() != zweiterString.length()) {
            if(ersterString.length() > zweiterString.length()) {
                while(ersterString.length() != zweiterString.length()) {
                    zweiterString = "0" + zweiterString;
                }

            } else {
                while(ersterString.length() != zweiterString.length()) {
                    ersterString = "0" + ersterString;
                }

            }

            //System.out.println("0 addiert!");

        }



        String bitStringEins = "";
        String bitStringZwei = "";

        for(int i = 0; i<ersterString.length(); i++) {

            bitStringEins = bitStringEins + HexToBit(Character.toString(ersterString.charAt(i)));


        }
        for(int i = 0; i<ersterString.length(); i++) {

            bitStringZwei = bitStringZwei + HexToBit(Character.toString(zweiterString.charAt(i)));


        }

        String xorString = "";
        for(int j = 0; j<bitStringEins.length(); j++) {

            String currentBit1 = Character.toString(bitStringEins.charAt(j));
            String currentBit2 = Character.toString(bitStringZwei.charAt(j));

            if(currentBit1.equals(currentBit2)) {

                xorString = xorString + "0";
                continue;
            } else {

                xorString = xorString + "1";
                continue;
            }


        }


        return xorString;
    }

    public String XORBits(String bitEins, String bitZwei) {

        String xorString = "";
        for(int j = 0; j<bitEins.length(); j++) {

            String currentBit1 = Character.toString(bitEins.charAt(j));
            String currentBit2 = Character.toString(bitZwei.charAt(j));

            if(currentBit1.equals(currentBit2)) {

                xorString = xorString + "0";
                continue;
            } else {

                xorString = xorString + "1";
                continue;
            }


        }


        return xorString;

    }


    public String HexToBit(String hexzahl){

        String noll = "0000";
        String eins = "0001";
        String zwei = "0010";
        String drei = "0011";
        String vier = "0100";
        String fünf = "0101";
        String sechs = "0110";
        String sieben = "0111";
        String acht = "1000";
        String neun = "1001";
        String a = "1010";
        String b = "1011";
        String c = "1100";
        String d = "1101";
        String e = "1110";
        String f = "1111";

        String Anoll = "0";
        String Aeins = "1";
        String Azwei = "2";
        String Adrei = "3";
        String Avier = "4";
        String Afünf = "5";
        String Asechs = "6";
        String Asieben = "7";
        String Aacht = "8";
        String Aneun = "9";
        String Aa = "a";
        String Ab = "b";
        String Ac = "c";
        String Ad = "d";
        String Ae = "e";
        String Af = "f";

        HashMap<String, String> mappingTabelle = new HashMap<>();

        mappingTabelle.put(Anoll, noll);
        mappingTabelle.put(Aeins, eins);
        mappingTabelle.put(Azwei, zwei);
        mappingTabelle.put(Adrei, drei);
        mappingTabelle.put(Avier, vier);
        mappingTabelle.put(Afünf, fünf);
        mappingTabelle.put(Asechs, sechs);
        mappingTabelle.put(Asieben, sieben);
        mappingTabelle.put(Aacht, acht);
        mappingTabelle.put(Aneun, neun);
        mappingTabelle.put(Aa, a);
        mappingTabelle.put(Ab, b);
        mappingTabelle.put(Ac, c);
        mappingTabelle.put(Ad, d);
        mappingTabelle.put(Ae, e);
        mappingTabelle.put(Af, f);



        return mappingTabelle.get(hexzahl);

    }

    public String Modulo(int zahl1, int zahl2){

        if(zahl1 < zahl2) {
            return String.valueOf(zahl1);

        } else if(zahl1 == zahl2) {

            return "0";
        } else {

            int zwischenSumme = zahl1;
            while(zwischenSumme > zahl2) {

                zwischenSumme = zwischenSumme - zahl2;

            }

            return String.valueOf(zwischenSumme);

        }
    }


    public String Faktorisierung(int zahl1){

        ArrayList<Integer> primzahlenListe = isPrim(zahl1);

        int counter = 0;

        String output = "";


        for(int i = primzahlenListe.size()-1; i>=0; i--) {

            if(zahl1<=1) {
                break;

            } else {

                while (zahl1 % primzahlenListe.get(i) == 0) {

                    zahl1 = zahl1 / primzahlenListe.get(i);
                    if(counter == 0) {

                        output = output + String.valueOf(primzahlenListe.get(i));
                    } else {
                        output = output + "*" + String.valueOf(primzahlenListe.get(i));

                    }

                    counter++;

                }
            }



        }




        return output;
    }

    public ArrayList<Integer> isPrim(int in) {

        ArrayList<Integer> primZahlenBisZahl = new ArrayList<>();

        int a = in;

        for ( int i = in ;i>0;i--) {
            int c = 1;
            int d =0;

            for (int t = i;t >0;t--) {


                if(d>2 ) {
                    break;
                }

                if(i %c==0) {
                    d++;
                }
                c++;
            }

            if(d == 2 ) {
                primZahlenBisZahl.add(i);
            }else {

            }

        }


        return primZahlenBisZahl;
    }

    //--------------------------------

    public String Vignere(String chiffreText, String Key){

        String[] BuchstabenArray = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] keyArray = WortInStringArray(Key);

        String loesung = "";

        for(int i = 0; i<chiffreText.length(); i++) {

            String currentBuchstabe = String.valueOf(chiffreText.charAt(i));

            int verschiebungsZahl = sucheVerschiebungsZahl(BuchstabenArray, keyArray[i%keyArray.length]);

            String[] verschobenesArray = verschiebeArray(verschiebungsZahl);

            int positionVonChiffreTextBuchstaben = sucheVerschiebungsZahl(verschobenesArray, currentBuchstabe.toLowerCase());

            loesung = loesung + BuchstabenArray[positionVonChiffreTextBuchstaben];


        }

        return loesung;
    }

    public String[] WortInStringArray(String wort){

        String[] wortArray = new String[wort.length()];


        for(int i = 0; i<wort.length(); i++){

            wortArray[i] =String.valueOf(wort.charAt(i));


        }


        return  wortArray;

    }
    public int sucheVerschiebungsZahl(String[] array, String buchstabe) {


        int zahl = 0;

        for(int i = 0; i<array.length; i++) {

            if(buchstabe.equals(array[i])) {
                zahl = i;
                break;

            }

        }

        return zahl;

    }

    public String[] verschiebeArray(int verschiebungszahl) {

        String[] BuchstabenArray = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] verschobenesArray = new String[BuchstabenArray.length];

        for(int i = 0; i<BuchstabenArray.length; i++) {

            verschobenesArray[i] = BuchstabenArray[(i+verschiebungszahl)%BuchstabenArray.length];
        }

        return verschobenesArray;
    }



    public String roundKey(String key, int anzahlRunden){

        HashMap<Integer, Integer> VerschiebungsMap = new HashMap<>();
        VerschiebungsMap.put(1, 1);
        VerschiebungsMap.put(2, 1);
        VerschiebungsMap.put(3, 2);
        VerschiebungsMap.put(4, 2);
        VerschiebungsMap.put(5, 2);
        VerschiebungsMap.put(6, 2);
        VerschiebungsMap.put(7, 2);
        VerschiebungsMap.put(8, 2);
        VerschiebungsMap.put(9, 1);
        VerschiebungsMap.put(10, 2);
        VerschiebungsMap.put(11, 2);
        VerschiebungsMap.put(12, 2);
        VerschiebungsMap.put(13, 2);
        VerschiebungsMap.put(14, 2);
        VerschiebungsMap.put(15, 2);
        VerschiebungsMap.put(16, 1);

        String cBlock = cBlockKriegen(key);
        String dBlock = dBlockKriegen(key);

        int anzahlVerschiebungen = 0;

        for(int i = 1; i<=anzahlRunden; i++) {

            anzahlVerschiebungen = anzahlVerschiebungen + VerschiebungsMap.get(i);

        }


        String[] cBlockArray = verschiebeArray(WortInStringArray(cBlock), anzahlVerschiebungen);

        String[] dBlockArray = verschiebeArray(WortInStringArray(dBlock), anzahlVerschiebungen);

        String solution = "";

        for(int h = 0; h<cBlockArray.length; h++) {

            solution = solution + cBlockArray[h];


        }

        for(int h = 0; h<dBlockArray.length; h++) {

            solution = solution + cBlockArray[h];


        }




        return Permutation(solution);
    }


    public String[] verschiebeArray(String[] anfangsArray, int verschiebungszahl) {

        String[] verschobenesArray = new String[anfangsArray.length];

        for(int i = 0; i<verschobenesArray.length; i++) {

            verschobenesArray[i] = anfangsArray[(i+verschiebungszahl)%anfangsArray.length];
        }

        System.out.println(Arrays.toString(verschobenesArray));

        return verschobenesArray;
    }


    public String cBlockKriegen(String bit){

        String[] bitArray = WortInStringArray(bit);

        Integer[][] KeyScheduleMatrx = new Integer[8][8];

        int counter = 1;

        for(int i = 0; i<KeyScheduleMatrx.length; i++) {
            for(int j = 0; j<KeyScheduleMatrx[i].length; j++) {
                KeyScheduleMatrx[i][j] = counter;
                counter++;
            }
        }

        matrixAnzeigen(KeyScheduleMatrx);

        int anzahlBits = 0;
        int counterr = 0;

        Integer[] Cblock = new Integer[28];

        for(int i = 7; i>=0; i--) {
            Cblock[counterr] = KeyScheduleMatrx[i][0];
            counterr++;
        }
        for(int i = 7; i>=0; i--) {
            Cblock[counterr] = KeyScheduleMatrx[i][1];
            counterr++;
        }
        for(int i = 7; i>=0; i--) {
            Cblock[counterr] = KeyScheduleMatrx[i][2];
            counterr++;
        }
        for(int i = 7; i>=4; i--) {
            Cblock[counterr] = KeyScheduleMatrx[i][3];
            counterr++;
        }



        String cBlockBit = "";

        for(int j = 0; j<Cblock.length; j++) {

            cBlockBit = cBlockBit + ( WortInStringArray(bit)[Cblock[j] - 1] );





        }


        return cBlockBit;
    }

    public String dBlockKriegen(String bit){

        String[] bitArray = WortInStringArray(bit);

        Integer[][] KeyScheduleMatrx = new Integer[8][8];

        int counter = 1;

        for(int i = 0; i<KeyScheduleMatrx.length; i++) {
            for(int j = 0; j<KeyScheduleMatrx[i].length; j++) {
                KeyScheduleMatrx[i][j] = counter;
                counter++;
            }
        }
        int counterr = 0;

        Integer[] DBlock = new Integer[28];

        for(int i = 7; i>=0; i--) {
            DBlock[counterr] = KeyScheduleMatrx[i][6];
            counterr++;
        }
        for(int i = 7; i>=0; i--) {
            DBlock[counterr] = KeyScheduleMatrx[i][5];
            counterr++;
        }
        for(int i = 7; i>=0; i--) {
            DBlock[counterr] = KeyScheduleMatrx[i][4];
            counterr++;
        }
        for(int i = 3; i>=0; i--) {
            DBlock[counterr] = KeyScheduleMatrx[i][3];
            counterr++;
        }


        String DBlockBit = "";

        for(int j = 0; j<DBlock.length; j++) {

            DBlockBit = DBlockBit +  ( WortInStringArray(bit)[DBlock[j] - 1] );


        }

        return DBlockBit;
    }

    public void matrixAnzeigen(Integer[][] matrix){

        for(int i = 0; i<matrix.length; i++) {
        }


    }

    public String Permutation(String rundenschluessel){

        String[] rundenschluesselArray = WortInStringArray(rundenschluessel);
        String output = "";

        Integer[] permutationTable = new Integer[] {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8,
                16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48,
                44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};


        for(int j = 0; j<permutationTable.length; j++) {

            output = output + rundenschluesselArray[permutationTable[j] - 1];


        }

        return output;

    }

    public int anzahlVerschiebungen(int rounds) {

        HashMap<Integer, Integer> VerschiebungsMap = new HashMap<>();
        VerschiebungsMap.put(1, 1);
        VerschiebungsMap.put(2, 1);
        VerschiebungsMap.put(3, 2);
        VerschiebungsMap.put(4, 2);
        VerschiebungsMap.put(5, 2);
        VerschiebungsMap.put(6, 2);
        VerschiebungsMap.put(7, 2);
        VerschiebungsMap.put(8, 2);
        VerschiebungsMap.put(9, 1);
        VerschiebungsMap.put(10, 2);
        VerschiebungsMap.put(11, 2);
        VerschiebungsMap.put(12, 2);
        VerschiebungsMap.put(13, 2);
        VerschiebungsMap.put(14, 2);
        VerschiebungsMap.put(15, 2);
        VerschiebungsMap.put(16, 1);


        int anzahlVerschiebungen = 0;

        for(int i = 1; i<=rounds; i++) {

            anzahlVerschiebungen = anzahlVerschiebungen + VerschiebungsMap.get(i);

        }


        return  anzahlVerschiebungen;
    }


    public String getRoundKey(int round, String key) {


        int leftShiftscounter = anzahlVerschiebungen(round);

        String blocksTogether = "";

        String cBlock= cBlockKriegen(key);
        String dBlock = dBlockKriegen(key);
        String cBlockShifted="";
        String dBlockShifted = "";

        String roundKey="";


        for(int i = 0; i<cBlock.length();i++) {
            cBlockShifted = cBlockShifted + cBlock.charAt((leftShiftscounter+i)%cBlock.length());
            dBlockShifted = dBlockShifted + dBlock.charAt((leftShiftscounter+i)%dBlock.length());
        }
        blocksTogether = cBlockShifted+dBlockShifted;




        int [][] pc2 = new int [][] {{13,16,10,23,0,4},
                {2,27,14,5,20,9},
                {22,18,11,3,25,7},
                {15,6,26,19,12,1},
                {40,51,30,36,46,54},
                {29,39,50,44,32,47},
                {43,48,38,55,33,52},
                {45,41,49,35,28,31}};

        for(int p = 0; p<pc2.length;p++) {
            for ( int j = 0; j<pc2[p].length;j++) {
                roundKey = roundKey + blocksTogether.charAt(pc2[p][j]);
            }
        }
        return roundKey;
    }


    public String getRBlockAfterRounds(String klartext, int runde){

        String ausgabe = "";

        ArrayList<String> ausgabeArray = new ArrayList<>();

        for(int i = 1; i<=runde; i++) {

            if(i == 1) {

                ausgabeArray = ersteRundeBerechnen(klartext);

            } else {

                ausgabeArray = eineRundeBerechnenR(ausgabeArray.get(0), ausgabeArray.get(1));

            }




        }




        return ausgabeArray.get(0);
    }

    public ArrayList<String> eineRundeBerechnenR(String rBlock, String lBlock){



        ArrayList<String> sBoxenEingabe = AchterGruppenFuerSBoxenKriegen(DESexpansionE(rBlock));

        String C = "";

        ArrayList<Integer[][]> sBoxen = initialisieresBoxen();

        int counter = 0;

        for(String currentAchtergruppe : sBoxenEingabe) {

            int vertikalIndex = sBoxVertikalIndexKriegen(currentAchtergruppe);
            int horizontal = sBoxHorizontalIndexKriegen(currentAchtergruppe);

            //    System.out.println("SBox Wert: " + sBoxen.get(counter)[vertikalIndex][horizontal]);
            C+= getBinary(sBoxen.get(counter)[vertikalIndex][horizontal]);
            counter++;
        }

        //  System.out.println("C: " + C + " lenght: " + C.length());

        ArrayList<String> ausgabeArray = new ArrayList<>();

        String desPermutation = DESPermutationNachSBox(C);

        String l = rBlock;
        String r = XORBits(lBlock, desPermutation);

        String Ausgabe = l + r;

        ausgabeArray.add(r);
        ausgabeArray.add(l);
        ausgabeArray.add(Ausgabe);

        return ausgabeArray;
    }

    public ArrayList<String> ersteRundeBerechnen(String klarText){

        String rBlock = rBlockaus64BitsKriegen(klarText);
        String lBlock = lBlockaus64BitsKriegen(klarText);
        //System.out.println("RBlock: " + rBlock);
        // System.out.println("LBlock: " + lBlock);



        String desXorKey = XORBits(DESexpansionE(rBlock), nullKey());
        //System.out.println("DES XOR 48Bit KEY: " + desXorKey);
        ArrayList<String> sBoxenEingabe = AchterGruppenFuerSBoxenKriegen(DESexpansionE(rBlock));

        String C = "";

        ArrayList<Integer[][]> sBoxen = initialisieresBoxen();

        int counter = 0;

        for(String currentAchtergruppe : sBoxenEingabe) {

            int vertikalIndex = sBoxVertikalIndexKriegen(currentAchtergruppe);
            int horizontal = sBoxHorizontalIndexKriegen(currentAchtergruppe);

            //  System.out.println("SBox Wert: " + sBoxen.get(counter)[vertikalIndex][horizontal]);
            C+= getBinary(sBoxen.get(counter)[vertikalIndex][horizontal]);
            counter++;
        }

        //  System.out.println("C: " + C + " lenght: " + C.length());

        ArrayList<String> ausgabeArray = new ArrayList<>();

        String desPermutation = DESPermutationNachSBox(C);

        String l = rBlock;
        String r = XORBits(lBlock, desPermutation);

        String Ausgabe = l + r;

        ausgabeArray.add(r);
        ausgabeArray.add(l);
        ausgabeArray.add(Ausgabe);

        return ausgabeArray;
    }


    public String nullKey(){

        String nullen = "";

        for(int i = 1; i<=48; i++ ) {

            nullen+="0";

        }

//        System.out.println("Nullen: " + nullen + " length: " + nullen.length());

        return nullen;
    }


    public String rBlockaus64BitsKriegen(String klarText){
        Integer[] RBlockReihenfolge = new Integer[]{57, 49, 41, 33, 25,17,9,1,59,51,43,35,27,19,11,3,61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7};


        String rBlock = "";

        for(int i = 0; i<RBlockReihenfolge.length; i++) {

            rBlock += klarText.charAt(RBlockReihenfolge[i]-1);

        }


        return rBlock;
    }

    public String lBlockaus64BitsKriegen(String klarText){
        Integer[] LBlockReihenfolge = new Integer[]{58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8};


        String lBlock = "";

        for(int i = 0; i<LBlockReihenfolge.length; i++) {

            lBlock += klarText.charAt(LBlockReihenfolge[i]-1);

        }


        return lBlock;
    }

    public String DESexpansionE(String rBlock){

        Integer[] desExpansionsReihenfolge = new Integer[]{32,1,2,3,4,5,4,5,6,7,8,9,8,9,
                10,11,12,13,12,13,14,15,16,17,16
                ,17,18,19,20,21,20,21,22,23,24,25,
                24,25,26,27,28,29,28,29,30,31,32,1};


        String desexpansionR0 = "";

        for(int i = 0; i<desExpansionsReihenfolge.length; i++) {

            desexpansionR0 += rBlock.charAt(desExpansionsReihenfolge[i]-1);

        }

        // System.out.println("DES Expansion: " + desexpansionR0 + " lenght: " + desexpansionR0.length());
        return desexpansionR0;
    }

    public String DESPermutationNachSBox(String rBlock){

        Integer[] desExpansionsReihenfolge = new Integer[]{16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25};


        String desPermutationC = "";

        for(int i = 0; i<desExpansionsReihenfolge.length; i++) {

            desPermutationC += rBlock.charAt(desExpansionsReihenfolge[i]-1);

        }

        return desPermutationC;
    }


    public ArrayList<String> AchterGruppenFuerSBoxenKriegen(String text){
        String block1 = "";
        String block2 = "";
        String block3 = "";
        String block4 = "";
        String block5 = "";
        String block6 = "";
        String block7 = "";
        String block8 = "";
        for(int i = 0; i< text.length(); i++) {
            if(i<6 && i>=0) {
                block1+=text.charAt(i);
            }
            if(i<12 && i>=6) {
                block2+=text.charAt(i);
            }
            if(i<18 && i>=12) {
                block3+=text.charAt(i);
            }
            if(i<24 && i>=18) {
                block4+=text.charAt(i);
            }
            if(i<30 && i>=24) {
                block5+=text.charAt(i);
            }
            if(i<36 && i>=30) {
                block6+=text.charAt(i);
            }
            if(i<42 && i>=36) {
                block7+=text.charAt(i);
            }
            if(i<48 && i>=42) {
                block8+=text.charAt(i);
            }

        }

    /*    System.out.print("" + block1);
        System.out.print("|" + block2);
        System.out.print("|" + block3);
        System.out.print("|" + block4);
        System.out.print("|" + block5);
        System.out.print("|" + block6);
        System.out.print("|" + block7);
        System.out.print("|" + block8);
*/




        ArrayList<String> achterGruppen = new ArrayList<>();
        achterGruppen.add(block1);
        achterGruppen.add(block2);
        achterGruppen.add(block3);
        achterGruppen.add(block4);
        achterGruppen.add(block5);
        achterGruppen.add(block6);
        achterGruppen.add(block7);
        achterGruppen.add(block8);

        return achterGruppen;

    }

    public ArrayList<Integer[][]> initialisieresBoxen() {

        Integer[][] sBox1 = new Integer[][] {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}};


        Integer[][] sBox2 = new Integer[][] {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}};


        Integer[][] sBox3 = new Integer[][] {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}};


        Integer[][] sBox4 = new Integer[][] {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 }};


        Integer[][] sBox5 = new Integer[][] {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                {4 ,2 ,1, 11, 10, 13, 7 ,8, 15, 9, 12, 5, 6, 3, 0, 14 },
                {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}};


        Integer[][] sBox6 = new Integer[][] {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                {9, 14, 15, 5, 2, 8, 12 ,3, 7 ,0, 4, 10, 1, 13, 11, 6 },
                {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}};


        Integer [][] sBox7 = new Integer [][] {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9,7, 5, 10, 6, 1},
                {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 }} ;


        Integer [][] sBox8 = new Integer [][] {{13 ,2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 }} ;


        ArrayList<Integer[][]> ListeMitSBoxen = new ArrayList<>();
        ListeMitSBoxen.add(sBox1);
        ListeMitSBoxen.add(sBox2);
        ListeMitSBoxen.add(sBox3);
        ListeMitSBoxen.add(sBox4);
        ListeMitSBoxen.add(sBox5);
        ListeMitSBoxen.add(sBox6);
        ListeMitSBoxen.add(sBox7);
        ListeMitSBoxen.add(sBox8);

        return ListeMitSBoxen;

    }



    public int sBoxVertikalIndexKriegen(String bits){

        String ersteBit = Character.toString(bits.charAt(0));
        String letzteBit = Character.toString(bits.charAt(5));
        int indexVertikal = getIndex("0" + "0" + ersteBit + letzteBit);
        //System.out.println(bits + " V: " + indexVertikal);
        return indexVertikal;
    }

    public int sBoxHorizontalIndexKriegen(String bits) {

        String zweiteBit = Character.toString(bits.charAt(1));
        String dritteBit = Character.toString(bits.charAt(2));
        String vierteBit = Character.toString(bits.charAt(3));
        String fünfteBit = Character.toString(bits.charAt(4));
        int indesHorizontal = getIndex(zweiteBit + dritteBit + vierteBit + fünfteBit);
        //System.out.println(bits + " H: " + indesHorizontal);

        return indesHorizontal;
    }


    public String getBinary(int toBinary) {
        String output = "";


        switch(toBinary) {
            case 0: output= "0000"; break;
            case 1: output= "0001"; break;
            case 2: output= "0010"; break;
            case 3: output= "0011"; break;
            case 4: output= "0100"; break;
            case 5: output= "0101"; break;
            case 6: output= "0110"; break;
            case 7: output= "0111"; break;
            case 8: output= "1000"; break;
            case 9: output= "1001"; break;
            case 10:output= "1010"; break;
            case 11:output= "1011"; break;
            case 12:output= "1100"; break;
            case 13:output= "1101"; break;
            case 14:output= "1110"; break;
            case 15:output= "1111"; break;

        }

        return output;
    }

    public int getIndex(String toIndex) {
        int output;


        switch(toIndex) {
            case "0000": output= 0; break;
            case "0001": output= 1; break;
            case "0010": output= 2; break;
            case "0011": output= 3; break;
            case "0100": output= 4; break;
            case "0101": output= 5; break;
            case "0110": output= 6; break;
            case "0111": output= 7; break;
            case "1000": output= 8; break;
            case "1001": output= 9; break;
            case "1010": output= 10; break;
            case "1011": output= 11; break;
            case "1100": output= 12; break;
            case "1101": output= 13; break;
            case "1110": output= 14; break;
            case "1111": output= 15; break;
            default: output=0;break;
        }

        return output;
    }


    public String feistelFunktion(String klarText, String key) {

        String rBlock = rBlock32BitKriegen(klarText);
        String lBlock = lBlock32BitKriegen(klarText);
        //System.out.println(rBlock + rBlock.length() + " --- " + lBlock + lBlock.length());
        // System.out.println(key + " KEY" + key.length());
        ArrayList<String> ausgabeArray = eineRundeMitKey(rBlock, lBlock, key);

        String output = XORBits(ausgabeArray.get(1), ausgabeArray.get(0));

        //Nur die neuen rBlock ausgeben!
        return ausgabeArray.get(0);
    }

    public ArrayList<String> eineRundeMitKey(String rBlock, String lBlock, String Key){




        String desXorKey = XORBits(DESexpansionE(rBlock), Key);
        //System.out.println("DES XOR 48Bit KEY: " + desXorKey);

        ArrayList<String> sBoxenEingabe = AchterGruppenFuerSBoxenKriegen(desXorKey);

        String C = "";

        ArrayList<Integer[][]> sBoxen = initialisieresBoxen();

        int counter = 0;

        for(String currentAchtergruppe : sBoxenEingabe) {

            int vertikalIndex = sBoxVertikalIndexKriegen(currentAchtergruppe);
            int horizontal = sBoxHorizontalIndexKriegen(currentAchtergruppe);

            //   System.out.println("SBox Wert: " + sBoxen.get(counter)[vertikalIndex][horizontal]);
            C+= getBinary(sBoxen.get(counter)[vertikalIndex][horizontal]);
            counter++;
        }

        // System.out.println("C: " + C + " lenght: " + C.length());

        ArrayList<String> ausgabeArray = new ArrayList<>();

        String desPermutation = DESPermutationNachSBox(C);

        String l = rBlock;
        String r = XORBits(lBlock, desPermutation);

        String Ausgabe = l + r;

        ausgabeArray.add(r);
        ausgabeArray.add(l);
        ausgabeArray.add(Ausgabe);

        return ausgabeArray;
    }


    public String lBlock32BitKriegen(String klarText){

        String output = "";

        for(int i = 0; i<klarText.length() ; i++) {
            if(i<32) {
                output+=Character.toString(klarText.charAt(i));
            }


        }

        return output;
    }

    public String rBlock32BitKriegen(String klarText){

        String output = "";

        for(int i = 0; i<klarText.length() ; i++) {
            if(i>=32) {
                output+=Character.toString(klarText.charAt(i));
            }


        }

        return output;
    }


    public String eineKompletteRunde(String lBlock, String rBlock, String key, int runde) {

        ArrayList<String> ausgabeArray = new ArrayList<>();

        ausgabeArray = eineRundeMitKey(rBlock, lBlock, getRoundKey(runde, key));


        return ausgabeArray.get(1) + ausgabeArray.get(0);


    }

    public String hexMultiplikation(String hex1, String hex2) {

        String hex1InBits = HexToBit(Character.toString(hex1.charAt(0))) + HexToBit(Character.toString(hex1.charAt(1)));
        String hex2InBits = HexToBit(Character.toString(hex2.charAt(0))) + HexToBit(Character.toString(hex2.charAt(1)));

        //System.out.println(hex1  + " hey 1" + " " + hex2 + " hex2");
        //System.out.println("Bit1: " + hex1InBits + " bit2: " + hex2InBits);

        int zahl1 = bitInDezimal(hex1InBits);
        int zahl2 = bitInDezimal(hex2InBits);

        //System.out.println("Zahl1: " + zahl1);
        //System.out.println("Zahl2: " + zahl2);
        int result = 0;
        String resultInBits = "";
        if(zahl2 == 3) {
            int zwischenResult = 2 * zahl1;
            String zwischenResultInBits = dezimalInBits(zwischenResult);
            //System.out.println("ZR: " +zwischenResultInBits);
            resultInBits = XORBits(zwischenResultInBits, "0000" + hex1InBits);
            //System.out.println("Result: " + resultInBits);

        } else {

            result = zahl1 * zahl2;
            resultInBits = dezimalInBits(result);
            //System.out.println("Result: " + resultInBits);
        }




        String modulo = "000100011011";

        String resultXorOrNot = "";

        if(xorMachen(resultInBits)) {

            // System.out.println(modulo);
            String xor = XORBits(resultInBits, modulo);
            // System.out.println("XOR: " + xor);
            resultXorOrNot = xor;

        } else {

            resultXorOrNot = resultInBits;


        }

        String ersterPart = bitsPartsKriegen(1, resultXorOrNot);
        String zweiterPart = bitsPartsKriegen(2, resultXorOrNot);

        //System.out.println("Erster Part: " + ersterPart + " Zweiter Part: " + zweiterPart);
        String ersterPartHex = bitsToHex(ersterPart);
        String zweiterPartHex = bitsToHex(zweiterPart);





        return  ersterPartHex + zweiterPartHex;
    }

    public String bitsPartsKriegen(int part, String bits) {

        String result = "";

        if(part == 1) {

            for(int i = 4; i<= 7; i++) {

                result+=Character.toString(bits.charAt(i));
            }

        } else {
            for(int i = 8; i<= 11; i++) {

                result+=Character.toString(bits.charAt(i));
            }


        }



        return result;
    }



    public int bitInDezimal(String bits) {

        int counter = 0;
        int result = 0;

        for(int i = bits.length()-1; i >= 0; i--) {

            if(Character.toString(bits.charAt(i)).equals("1")) {

                result+=Math.pow(2, counter);
                //System.out.println("Zwei hoch: " + counter + " = " + Math.pow(2, counter));
                counter++;

            }
            else {

                counter++;
            }

        }



        return result;
    }


    public boolean xorMachen(String bits) {

        boolean result = false;

        for(int i = 0; i<4; i++) {

            if(Character.toString(bits.charAt(i)).equals("1")){

                result = true;
            }

        }


        return result;
    }

    public String bitsToHex(String bits) {
        String output;


        switch(bits) {
            case "0000": output= "0"; break;
            case "0001": output= "1"; break;
            case "0010": output= "2"; break;
            case "0011": output= "3"; break;
            case "0100": output= "4"; break;
            case "0101": output= "5"; break;
            case "0110": output= "6"; break;
            case "0111": output= "7"; break;
            case "1000": output= "8"; break;
            case "1001": output= "9"; break;
            case "1010": output= "a"; break;
            case "1011": output= "b"; break;
            case "1100": output= "c"; break;
            case "1101": output= "d"; break;
            case "1110": output= "e"; break;
            case "1111": output= "f"; break;
            default: output="0";break;
        }

        return output;
    }



    public String dezimalInBits(int zahl) {

        int counter = zahl;
        String result = "";

        while(zahl >= 1) {
            if(zahl%2 == 0) {
                result+="0";
                zahl = zahl/2;
            } else {
                result+="1";
                zahl = zahl/2;

            }

        }

        return bitsAuffüllenUndUmdrehen(result);
    }

    public String bitsAuffüllenUndUmdrehen(String bits){

        while(bits.length() < 12) {
            bits+="0";
        }
        //System.out.println(bits);
        String result = "";

        for(int i = bits.length()-1; i>=0; i--) {

            result += Character.toString(bits.charAt(i));

        }

        // System.out.println("umgedreht: " + result);

        return result;

    }



    public String[] stringArrayKriegen(String bits){

        String[] sa = new String[bits.length()];

        for(int i = 7; i>=0; i-- ) {

            sa[i] = Character.toString(bits.charAt(i));
        }

        return sa;

    }



    public String KeyExpansion(String hexaKey){

        // hexaKey = "0cb4da23cab44ec34543b4293990be63";
        //Runde 1:
        String[][] MatrixAusKex_1 = getMatrixAusKey(hexaKey);
        String[][] original_1Kopie = getMatrixAusKey(hexaKey);
        String[][] rotWordMatrix_1 = rotateWord(MatrixAusKex_1);
        String[][] aftersBoxMatrix_1 = sBoxAnwenden(rotWordMatrix_1);
        String[][] afterRcon_1 = neueMatrixgenerieren(aftersBoxMatrix_1, original_1Kopie, 0);
        String keyBit1 = matrixZuKey(afterRcon_1);
        String keyHex1 = keyzuHexKey(keyBit1);

        //Runde 1:
        String[][] MatrixAusKex_2 = getMatrixAusKey(keyHex1);
        String[][] original_2Kopie = getMatrixAusKey(keyHex1);
        String[][] rotWordMatrix_2 = rotateWord(MatrixAusKex_2);
        String[][] aftersBoxMatrix_2 = sBoxAnwenden(rotWordMatrix_2);
        String[][] afterRcon_2 = neueMatrixgenerieren(aftersBoxMatrix_2, original_2Kopie, 1);
        String keyBit2 = matrixZuKey(afterRcon_2);
        String keyHex2 = keyzuHexKey(keyBit2);

        //Falls mehr gefordert einfach erweitern!
      /*  //Runde 1:
        String[][] MatrixAusKex_3 = getMatrixAusKey(keyHex2);
        String[][] original_3Kopie = getMatrixAusKey(keyHex2);
        String[][] rotWordMatrix_3 = rotateWord(MatrixAusKex_3);
        String[][] aftersBoxMatrix_3 = sBoxAnwenden(rotWordMatrix_3);
        String[][] afterRcon_3 = neueMatrixgenerieren(aftersBoxMatrix_3, original_3Kopie, 2);
        String keyBit3 = matrixZuKey(afterRcon_3);
        String keyHex3 = keyzuHexKey(keyBit3);*/

        String lösung = hexaKey + "_" + keyHex1 + "_" + keyHex2;
        // System.out.println("Lösung " + lösung);

        return lösung;
    }

    public String[][] rotateWord(String[][] matrix){

        String[][] matrixKopie = new String[][]{};
        matrixKopie = matrix.clone();
        String[][] rotWord = matrixKopie;

        String bits1 = matrix[0][3];
        String bits2 = matrix[1][3];
        String bits3 = matrix[2][3];
        String bits4 = matrix[3][3];

        rotWord[0][3] = bits2;
        rotWord[1][3] = bits3;
        rotWord[2][3] = bits4;
        rotWord[3][3] = bits1;

        return rotWord;


    }

    public String keyzuHexKey(String key) {

        String ausgabe = "";
        String currentBitString = "";
        int counter = 0;
        for (int i = 0; i<key.length(); i++) {
            currentBitString+=Character.toString(key.charAt(i));
            counter++;
            if(counter%4 == 0) {
                counter = 0;
                ausgabe+=bitsToHex(currentBitString);
                currentBitString = "";
                continue;
            } else {
                continue;

            }
        }



        return ausgabe;



    }





    public String[][] neueMatrixgenerieren(String[][] sBoxMatrix, String[][] normaleMatrix, int kexindex){

        String[][] ausgabe = new String[4][4];
        sBox sBoxrCon = new sBox();



        //Erste Spalte:
        ausgabe[0][0] = XORBits(XORBits(normaleMatrix[0][0], sBoxMatrix[0][3]), sBoxrCon.getrConString(kexindex));
        for(int i = 1; i<4; i++) {
            ausgabe[i][0] = XORBits(XORBits(normaleMatrix[i][0], sBoxMatrix[i][3]), "00000000");
        }

        //die anderen Spalten:
        for(int j = 1; j<4; j++) {
            for(int h = 0; h<4; h++) {

                ausgabe[h][j] = XORBits(normaleMatrix[h][j], ausgabe[h][j-1]);

            }

        }


        return ausgabe;
    }


    public String matrixZuKey(String[][] matrix) {

        String ausgabe = "";
        for(int i = 0; i<4; i++) {
            for(int h = 0; h<4; h++) {
                ausgabe+=matrix[h][i];
            }

        }


        return ausgabe;
    }

    public String matrixZuKeyAnders(String[][] matrix) {

        String ausgabe = "";
        for(int i = 0; i<4; i++) {
            for(int h = 0; h<4; h++) {
                ausgabe+=matrix[i][h];
            }

        }


        return ausgabe;
    }

    public String[][] sBoxAnwenden(String[][] matrix){
        String[][] ausgabe = matrix;
        sBox sBoxInitialize = new sBox();

        //HexWerte vor dem SubBytes:
        String bithex1 = bitsToHex(ausgabe[0][3].substring(0, 4)) + bitsToHex(ausgabe[0][3].substring(4, 8));
        String bithex2 = bitsToHex(ausgabe[1][3].substring(0, 4)) + bitsToHex(ausgabe[1][3].substring(4, 8));
        String bithex3 = bitsToHex(ausgabe[2][3].substring(0, 4)) + bitsToHex(ausgabe[2][3].substring(4, 8));
        String bithex4 = bitsToHex(ausgabe[3][3].substring(0, 4)) + bitsToHex(ausgabe[3][3].substring(4, 8));


        String sBox1 = sBoxInitialize.getSBoxString(hexToZahl(Character.toString(bithex1.charAt(0))), hexToZahl(Character.toString(bithex1.charAt(1))));
        String sBox2 = sBoxInitialize.getSBoxString(hexToZahl(Character.toString(bithex2.charAt(0))), hexToZahl(Character.toString(bithex2.charAt(1))));
        String sBox3 = sBoxInitialize.getSBoxString(hexToZahl(Character.toString(bithex3.charAt(0))), hexToZahl(Character.toString(bithex3.charAt(1))));
        String sBox4 = sBoxInitialize.getSBoxString(hexToZahl(Character.toString(bithex4.charAt(0))), hexToZahl(Character.toString(bithex4.charAt(1))));

        String neueSboxString1 = HexToBit(Character.toString(sBox1.charAt(0))) + HexToBit(Character.toString(sBox1.charAt(1)));
        String neueSboxString2 = HexToBit(Character.toString(sBox2.charAt(0))) + HexToBit(Character.toString(sBox2.charAt(1)));
        String neueSboxString3 = HexToBit(Character.toString(sBox3.charAt(0))) + HexToBit(Character.toString(sBox3.charAt(1)));
        String neueSboxString4 = HexToBit(Character.toString(sBox4.charAt(0))) + HexToBit(Character.toString(sBox4.charAt(1)));


        ausgabe[0][3] = neueSboxString1;
        ausgabe[1][3] = neueSboxString2;
        ausgabe[2][3] = neueSboxString3;
        ausgabe[3][3] = neueSboxString4;



        return ausgabe;

    }

    public String[][] subBytes(String[][] matrix) {

        String[][] ausgabe = new String[4][4];
        sBox sBoxInitialize = new sBox();

        for(int i = 0; i<4; i++) {
            for(int h = 0; h<4; h++) {
                String sBoxWert = sBoxInitialize.getSBoxString(hexToZahl(Character.toString(matrix[h][i].charAt(0))), hexToZahl(Character.toString(matrix[h][i].charAt(1))));
                ausgabe[h][i] = sBoxWert;
            }

        }
      /*  System.out.println("Original: ");
        System.out.println(Arrays.toString(matrix[0]));
        System.out.println(Arrays.toString(matrix[1]));
        System.out.println(Arrays.toString(matrix[2]));
        System.out.println(Arrays.toString(matrix[3]));

        System.out.println("SBOXEN: ");
        System.out.println(Arrays.toString(ausgabe[0]));
        System.out.println(Arrays.toString(ausgabe[1]));
        System.out.println(Arrays.toString(ausgabe[2]));
        System.out.println(Arrays.toString(ausgabe[3]));*/

        return ausgabe;


    }

    public int hexToZahl(String hex) {
        int output;


        switch(hex) {
            case "0": output= 0; break;
            case "1": output= 1; break;
            case "2": output= 2; break;
            case "3": output= 3; break;
            case "4": output= 4; break;
            case "5": output= 5; break;
            case "6": output= 6; break;
            case "7": output= 7; break;
            case "8": output= 8; break;
            case "9": output= 9; break;
            case "a": output= 10; break;
            case "b": output= 11; break;
            case "c": output= 12; break;
            case "d": output= 13; break;
            case "e": output= 14; break;
            case "f": output= 15; break;
            default: output=0;break;
        }

        return output;
    }


    public String[][] getMatrixAusKey(String hexaKey) {

        String hdeximal = getAlleHexToBit(hexaKey);
        String[] acterBlöckeBits = bitsInAchterBloeckeAufteilen(hdeximal);

        String[][] sMatrix = new String[4][4];
        int pos = 0;
        for (int i = 0; i < sMatrix.length; i++)
            for (int j = 0; j < sMatrix[i].length; j++)
                sMatrix[j][i] = acterBlöckeBits[pos++];

        return sMatrix;


    }

    public String[][] getHexMatrixAusBitMatrix(String[][] bitMatrix) {

        String[][] sMatrix = new String[4][4];
        int pos = 0;
        for (int i = 0; i < sMatrix.length; i++)
            for (int j = 0; j < sMatrix[i].length; j++)
                sMatrix[j][i] = bitsToHex(bitMatrix[j][i].substring(0,4)) + bitsToHex(bitMatrix[j][i].substring(4,8));

        return sMatrix;


    }

    public String[] bitsInAchterBloeckeAufteilen(String bits){

        String[] arrayBloecke = new String[16];
        int counter = 0;

        for(int i = 1; i<=bits.length(); i++) {

            if(i%8 != 0) {

                if(arrayBloecke[counter] == null) {

                    arrayBloecke[counter] = Character.toString(bits.charAt(i-1));
                } else {
                    arrayBloecke[counter]+= Character.toString(bits.charAt(i-1));
                }


            } else {

                if(arrayBloecke[counter] == null) {

                    arrayBloecke[counter] = Character.toString(bits.charAt(i-1));
                } else {
                    arrayBloecke[counter]+= Character.toString(bits.charAt(i-1));
                }
                counter++;
            }


        }

        return arrayBloecke;

    }

    public String getAlleHexToBit(String hexa){

        String result = "";

        for(int i = 0; i<hexa.length(); i++) {

            result+= HexToBit(Character.toString(hexa.charAt(i)));

        }


        return result;

    }


    public String MixColumns(String hexaKey) {


        String[][] original_1Kopie = getMatrixAusKey(hexaKey);

        //hexaKey = "d4bf5d30e0b452aeb84111f11e2798e5";
        String[][] MatrixAusKey = getMatrixAusKey(hexaKey);
        //System.out.println("Hexa Kex: " + hexaKey);

   /*     System.out.println("Matrix:");
        System.out.println(Arrays.toString(MatrixAusKey[0]));
        System.out.println(Arrays.toString(MatrixAusKey[1]));
        System.out.println(Arrays.toString(MatrixAusKey[2]));
        System.out.println(Arrays.toString(MatrixAusKey[3]));*/

        String mixColumnCHex = "02010103030201010103020101010302";
        String[][] mixColumnCMatrix = getMatrixAusKey(mixColumnCHex);

        String[][] ausgabeMatrix = new String[4][4];

        for(int i = 0; i<ausgabeMatrix.length; i++) {
            for (int h = 0; h<ausgabeMatrix[0].length; h++) {

                String zahl1 = hexMultiplikation(bitsToHex(MatrixAusKey[0][i].substring(0, 4)) + bitsToHex(MatrixAusKey[0][i].substring(4,8)), bitsToHex(mixColumnCMatrix[h][0].substring(0, 4)) + bitsToHex(mixColumnCMatrix[h][0].substring(4,8)));
                String zahl2 = hexMultiplikation(bitsToHex(MatrixAusKey[1][i].substring(0, 4)) + bitsToHex(MatrixAusKey[1][i].substring(4,8)), bitsToHex(mixColumnCMatrix[h][1].substring(0, 4)) + bitsToHex(mixColumnCMatrix[h][1].substring(4,8)));
                String zahl3 = hexMultiplikation(bitsToHex(MatrixAusKey[2][i].substring(0, 4)) + bitsToHex(MatrixAusKey[2][i].substring(4,8)), bitsToHex(mixColumnCMatrix[h][2].substring(0, 4)) + bitsToHex(mixColumnCMatrix[h][2].substring(4,8)));
                String zahl4 = hexMultiplikation(bitsToHex(MatrixAusKey[3][i].substring(0, 4)) + bitsToHex(MatrixAusKey[3][i].substring(4,8)), bitsToHex(mixColumnCMatrix[h][3].substring(0, 4)) + bitsToHex(mixColumnCMatrix[h][3].substring(4,8)));

              /*  System.out.println("einzelne Spalten: ");
                System.out.println(zahl1);
                System.out.println(zahl2);
                System.out.println(zahl3);
                System.out.println(zahl4);
                System.out.println(hexMultiplikation("02", "d4"));
                System.out.println(hexMultiplikation("03", "bf"));
                System.out.println(hexMultiplikation("01", "5d"));
                System.out.println(hexMultiplikation("01", "30"));*/


                String ersteHalf = XORBits(getAlleHexToBit(zahl1), getAlleHexToBit(zahl2));
                // System.out.println("XOR erste Hälfte: " + ersteHalf);
                String zweiteHalf = XORBits(ersteHalf, getAlleHexToBit(zahl3));
                //System.out.println("XOR zweite Hälfte: " + zweiteHalf);
                String finalXOR = XORBits(zweiteHalf, getAlleHexToBit(zahl4));
                //System.out.println("XOR final Hälfte: " + finalXOR);

                String mixedColumn = bitsToHex(finalXOR.substring(0, 4)) + bitsToHex(finalXOR.substring(4,8));

                //System.out.println("Mixed Column: " + mixedColumn);

                ausgabeMatrix[h][i] = mixedColumn;
            }
        }

        String neuerKey = HexMatrixZuKey(ausgabeMatrix);

        return neuerKey;
    }

    public String HexMatrixZuKey(String[][] HexMatrix) {

        String ausgabe = "";
        for(int i = 0; i<4; i++) {
            for(int h = 0; h<4; h++) {
                ausgabe+=HexMatrix[h][i];
            }

        }

        //System.out.println("Neuer Key: " + ausgabe);


        return ausgabe;
    }

    public String[][] shiftRows(String[][] matrix) {

        String[][] ausgabe = new String[4][4];

        ausgabe[0] = matrix[0];

        //Erste Spalte:
        for(int i = 0; i<4; i++) {
            ausgabe[1][i] = matrix[1][(i+1)%4];
        }
        //Zweite Spalte:
        for(int i = 0; i<4; i++) {
            ausgabe[2][i] = matrix[2][(i+2)%4];
        }
        //Dritte Spalte:
        for(int i = 0; i<4; i++) {
            ausgabe[3][i] = matrix[3][(i+3)%4];
        }

/*
        System.out.println("SHIFT ROWS:");
        System.out.println(Arrays.toString(ausgabe[0]));
        System.out.println(Arrays.toString(ausgabe[1]));
        System.out.println(Arrays.toString(ausgabe[2]));
        System.out.println(Arrays.toString(ausgabe[3]));*/

        return ausgabe;

    }



    public String AESTransformation(String hexWert) {

        String[][] matrix = getHexMatrixAusBitMatrix(getMatrixAusKey(hexWert));
        String[][] subBytes = subBytes(matrix);
        String[][] shiftRows = shiftRows(subBytes);


        String shiftRowsWert = matrixZuKey(shiftRows);
        //System.out.println(shiftRowsWert);

        String result = MixColumns(shiftRowsWert);

        addRoundKey(matrix,shiftRows);

        return result;
    }

    public String[][] addRoundKey(String[][] matrix, String[][] roundKey) {

        String[][] ausgabe = new String[4][4];

        for(int i=0; i<4; i++) {
            for(int h = 0; h<4; h++) {

                String xorBits = XORBits(HexToBit(Character.toString(matrix[h][i].charAt(0))) + HexToBit(Character.toString(matrix[h][i].charAt(1))), HexToBit(Character.toString(roundKey[h][i].charAt(0))) + HexToBit(Character.toString(roundKey[h][i].charAt(1))));
                ausgabe[h][i] = bitsToHex(xorBits.substring(0,4)) + bitsToHex(xorBits.substring(4,8));
            }
        }

        return ausgabe;
    }

    public String KeyFuerEineRunde(String hexaKey, int runde){

        // hexaKey = "0cb4da23cab44ec34543b4293990be63";
        String ausgabe = hexaKey;

        for(int i = 1; i<=runde; i++) {
            String[][] MatrixAusKex_1 = getMatrixAusKey(ausgabe);
            String[][] original_1Kopie = getMatrixAusKey(ausgabe);
            String[][] rotWordMatrix_1 = rotateWord(MatrixAusKex_1);
            String[][] aftersBoxMatrix_1 = sBoxAnwenden(rotWordMatrix_1);
            String[][] afterRcon_1 = neueMatrixgenerieren(aftersBoxMatrix_1, original_1Kopie, i);
            String keyBit1 = matrixZuKey(afterRcon_1);
            ausgabe = keyzuHexKey(keyBit1);

        }

        // System.out.println("Lösung " + lösung);

        return ausgabe;
    }

    public String standardRunde(String hexWert, String key, int runde) {

        String[][] matrix = getHexMatrixAusBitMatrix(getMatrixAusKey(hexWert));
        String[][] subBytes = subBytes(matrix);

        String[][] shiftRows = shiftRows(subBytes);

        String shiftRowsWert = matrixZuKey(shiftRows);
        //System.out.println(shiftRowsWert);

        String mixColumns = MixColumns(shiftRowsWert);

        String[][] matrixStandard = getHexMatrixAusBitMatrix(getMatrixAusKey(mixColumns));
        String[][] keyRunde = getHexMatrixAusBitMatrix(getMatrixAusKey(keyKriegen(key, runde)));

        String[][] ausgabeMatrix = addRoundKey(matrixStandard, keyRunde);
        String ausgabe = matrixZuKey(ausgabeMatrix);

        return ausgabe;
    }

    public String AESDreiRunden(String hexBlock, String key, String keyRoom) {


        String[][] hexBlockMatrix = getHexMatrixAusBitMatrix(getMatrixAusKey(hexBlock));
        String[][] keyMatrix = getHexMatrixAusBitMatrix(getMatrixAusKey(key));
/*
        System.out.println("key");
        System.out.println(Arrays.toString(keyMatrix[0]));
        System.out.println(Arrays.toString(keyMatrix[1]));
        System.out.println(Arrays.toString(keyMatrix[2]));
        System.out.println(Arrays.toString(keyMatrix[3]));*/

        String[][] hh = addRoundKey(hexBlockMatrix, keyMatrix);
        String ausgabeInitialize = matrixZuKey(hh);
        //System.out.println("initiale: " + ausgabeInitialize);

        String ausgabeRunde1 = standardRunde(ausgabeInitialize, key, 1);
        //System.out.println("r1: " + ausgabeRunde1);
        String ausgabeRunde2 = standardRunde(ausgabeRunde1, key, 2);
        //System.out.println("r2: " + ausgabeRunde2);


        String finalausgabe = ausgabeInitialize + "_" + ausgabeRunde1 + "_" + ausgabeRunde2;

        return finalausgabe;

    }

    public String keyKriegen(String hexaKey, int runde){

        // hexaKey = "0cb4da23cab44ec34543b4293990be63";
        //Runde 1:
        String[][] MatrixAusKex_1 = getMatrixAusKey(hexaKey);
        String[][] original_1Kopie = getMatrixAusKey(hexaKey);
        String[][] rotWordMatrix_1 = rotateWord(MatrixAusKex_1);
        String[][] aftersBoxMatrix_1 = sBoxAnwenden(rotWordMatrix_1);
        String[][] afterRcon_1 = neueMatrixgenerieren(aftersBoxMatrix_1, original_1Kopie, 0);
        String keyBit1 = matrixZuKey(afterRcon_1);
        String keyHex1 = keyzuHexKey(keyBit1);

        //Runde 2:
        String[][] MatrixAusKex_2 = getMatrixAusKey(keyHex1);
        String[][] original_2Kopie = getMatrixAusKey(keyHex1);
        String[][] rotWordMatrix_2 = rotateWord(MatrixAusKex_2);
        String[][] aftersBoxMatrix_2 = sBoxAnwenden(rotWordMatrix_2);
        String[][] afterRcon_2 = neueMatrixgenerieren(aftersBoxMatrix_2, original_2Kopie, 1);
        String keyBit2 = matrixZuKey(afterRcon_2);
        String keyHex2 = keyzuHexKey(keyBit2);

        //Falls mehr gefordert einfach erweitern!
        //Runde 3:
        String[][] MatrixAusKex_3 = getMatrixAusKey(keyHex2);
        String[][] original_3Kopie = getMatrixAusKey(keyHex2);
        String[][] rotWordMatrix_3 = rotateWord(MatrixAusKex_3);
        String[][] aftersBoxMatrix_3 = sBoxAnwenden(rotWordMatrix_3);
        String[][] afterRcon_3 = neueMatrixgenerieren(aftersBoxMatrix_3, original_3Kopie, 2);
        String keyBit3 = matrixZuKey(afterRcon_3);
        String keyHex3 = keyzuHexKey(keyBit3);

        String lösung = hexaKey + "_" + keyHex1 + "_" + keyHex2;
        // System.out.println("Lösung " + lösung);

        switch (runde) {
            case 0: return hexaKey;
            case 1: return keyHex1;
            case 2: return keyHex2;
            case 3: return keyHex3;
            default: return "";

        }



    }



    public String RC4Loop(String stateTable, String klarText){

        int[] key = stringInArrayOhneUnterzeichen(stateTable);
        //System.out.println(stateTable);
        key = rundeGenerieren(key, klarText.length());
        //System.out.println(Arrays.toString(key));
        String output = intArrayInStringUmwandeln(key);
        return output;

    }

    public int[] stringInArrayOhneUnterzeichen(String stateTable) {

        String[] strings = stateTable.split("_");

        int[] stringIntOhneUntenstrich = new int[strings.length];

        for(int i = 0; i<strings.length; i++) {

            stringIntOhneUntenstrich[i] = Integer.parseInt(strings[i]);

        }
        //System.out.println(Arrays.toString(stringIntOhneUntenstrich));

        return stringIntOhneUntenstrich;
    }

    public int[] rundeGenerieren(int[] schluessel, int wiederholungen) {

        int[] result = new int[wiederholungen];

        int i = 0;
        int j = 0;

        for (int h = 0; h < result.length; h++) {

            i = (i+1)%schluessel.length;
            j = (j + schluessel[i]) % schluessel.length;

            tauscheStatesUm(schluessel, schluessel[i], i, j);


            result[h] = schluessel[(schluessel[i] + schluessel[j]) % schluessel.length];
        }
        return result;
    }

    public String intArrayInStringUmwandeln(int[] currentArray) {

        String output = "";

        for(int i = 0; i<currentArray.length; i++) {
            output += String.valueOf(currentArray[i]);
        }

        return output;
    }

    public String intArrayInStringUmwandelnMitUntenstrich(int[] currentArray) {

        String output = "";

        for(int i = 0; i<currentArray.length; i++) {

            if(i == currentArray.length-1) {
                output += String.valueOf(currentArray[i]);
            } else {

                output += String.valueOf(currentArray[i]) + "_";
            }


        }

        return output;
    }


    public void tauscheStatesUm(int[] schluessel, int zuMerkendeState, int ersteState, int zweiteState) {

        schluessel[ersteState] = schluessel[zweiteState];
        schluessel[zweiteState] = zuMerkendeState;

    }


    public String RC4KeySchedule(String key){

        int[] keySchedule = stringInArrayOhneUnterzeichen(key);
        //System.out.println(key);
        String result = intArrayInStringUmwandelnMitUntenstrich(sBoxHandling(keySchedule));
        //System.out.println("Result: " + result);

        return result;
    }


    public int[] sBoxHandling(int[] key) {
        int[] sBox = new int[key.length];
        for (int i = 0; i < key.length; i++) {
            sBox[i] = i;
        }
        int j = 0;
        for (int i = 0; i < key.length; i++) {
            j = (j  + key[i % key.length] + sBox[i]) % key.length;
            tauscheStatesUm(sBox, sBox[i], i, j);
        }
        return sBox;
    }

    public String RC4Verschluesselung(String key, String klarText){

        int[] sBox = stringInArrayOhneUnterzeichen(key);

        sBox = sBoxHandling(sBox);
        sBox = rundeGenerieren(sBox, klarText.length());

        String result = "";

        for (int i = 0; i < klarText.length(); i++) {

            String currentKey = dezimalInByte(sBox[i]);
            String currentText = buchstabeToBinaer(Character.toString(klarText.charAt(i)));
            //System.out.println(currentKey + " currentKey" + " - " + sBox[i]);
            //System.out.println(currentText + " currentText" + " - " + Character.toString(klarText.charAt(i)));
            String xored = XORBits(currentKey, currentText);
            //System.out.println("X_ " + xored);
            result += xored;
        }

        //System.out.println(result);
        return result.toString();



    }

    public String buchstabeToBinaer(String currentBuchstabe) {


        String[][] BuchstabenInBinaer = new String[][] {
                { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C",
                        "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
                        "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a",
                        "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                        "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
                        "z" },
                {       "110000", "110001", "110010", "110011", "110100", "110101",
                        "110110", "110111", "111000", "111001", "1000001",
                        "1000010", "1000011", "1000100", "1000101", "1000110",
                        "1000111", "1001000", "1001001", "1001010", "1001011",
                        "1001100", "1001101", "1001110", "1001111", "1010000",
                        "1010001", "1010010", "1010011", "1010100", "1010101",
                        "1010110", "1010111", "1011000", "1011111", "1100000",
                        "1100001", "1100010", "1100011", "1100100", "1100101",
                        "1100110", "1100111", "1101000", "1101001", "1101010",
                        "1101011", "1101100", "1101101", "1101110", "1101111",
                        "1110000", "1110001", "1110010", "1110011", "1110100",
                        "1110101", "1110110", "1110111", "1111000", "1111001",
                        "1111010" }

        };

        String result = "";

        for(int i = 0; i<BuchstabenInBinaer[0].length; i++) {

            if(BuchstabenInBinaer[0][i].equals(currentBuchstabe)) {

                result = BuchstabenInBinaer[1][i];

            }

        }

        String echterResult = "0" + result;

        return echterResult;

    }

    public String dezimalInByte(int zahl) {

        String result = "";

        while(zahl >= 1) {
            if(zahl%2 == 0) {
                result+="0";
                zahl = zahl/2;
            } else {
                result+="1";
                zahl = zahl/2;

            }

        }

        return byteAuffüllenUndUmdrehen(result);
    }


    public String byteAuffüllenUndUmdrehen(String bits){

        while(bits.length() < 8) {
            bits += "0";
        }
        //System.out.println(bits);
        String result = "";
        for(int i = bits.length()-1; i>=0; i--) {
            result += Character.toString(bits.charAt(i));
        }
        return result;

    }






}
