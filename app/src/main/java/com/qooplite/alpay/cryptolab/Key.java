package com.qooplite.alpay.cryptolab;

public class Key {

    private String name;
    private String key;
    private int variable1;
    private int variable2;
    private int variable3;


    public Key(String name, String key) {

        this.name = name;
        this.key = key;
    }

    public Key(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVariable1() {
        return variable1;
    }

    public void setVariable1(int variable1) {
        this.variable1 = variable1;
    }

    public int getVariable2() {
        return variable2;
    }

    public void setVariable2(int variable2) {
        this.variable2 = variable2;
    }

    public int getVariable3() {
        return variable3;
    }

    public void setVariable3(int variable3) {
        this.variable3 = variable3;
    }
}
