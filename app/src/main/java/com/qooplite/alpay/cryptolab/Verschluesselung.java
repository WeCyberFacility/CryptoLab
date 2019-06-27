package com.qooplite.alpay.cryptolab;

public class Verschluesselung {

    private String id;
    private String name;
    private int sicherheitslevel;
    private String beschreibung;
    private String input;
    private String output;


    public Verschluesselung(String id, String name, int sicherheitslevel, String beschreibung, String input, String output) {

        this.id = id;
        this.name = name;
        this.sicherheitslevel = sicherheitslevel;
        this.beschreibung = beschreibung;
        this.input = input;
        this.output = output;

    }

    public Verschluesselung(){}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSicherheitslevel() {
        return sicherheitslevel;
    }

    public void setSicherheitslevel(int sicherheitslevel) {
        this.sicherheitslevel = sicherheitslevel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
