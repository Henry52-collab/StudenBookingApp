package com.example.myapplication;

/**
 * A class for admin
 * */
public class Admin {
    public static final String USER_NAME = "admin";
    public static final String PASSWORD = "admin123";
    private String username;
    private String password;

    public Admin(String username,String password){
        this.password = password;
        this.username = username;
    }

    public String getUserName(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUserName(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
