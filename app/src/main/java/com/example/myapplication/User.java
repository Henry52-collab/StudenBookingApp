package com.example.myapplication;

/**
 * User super class for Student, Instructor, Admin.
 * */
public class User {
    protected String name;
    protected String password;
    protected String type;
    public User(){}

    public String getType() {
        return type;
    }

    public User(String name, String password, String type){
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
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
