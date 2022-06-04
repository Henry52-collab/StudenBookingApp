package com.example.myapplication;

public class User {
     protected String name;
     protected String password;

    public User(){}
    public User(String name,String password){
        this.name = name;
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
