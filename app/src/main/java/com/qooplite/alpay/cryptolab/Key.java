package com.qooplite.alpay.cryptolab;

public class Key {

    private String name;

    private int variable1;
    private int variable2;


    public Key(String name, int var1, int var2) {

        this.name = name;
        this.variable1 = var1;
        this.variable2 = var2;
    }

    public Key() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}