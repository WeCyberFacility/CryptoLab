package com.qooplite.alpay.cryptolab;

public class Password {


    private String name;
    private String password;


    public Password(String name, String password) {

        this.name = name;
        this.password = password;

    }

    public Password() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
