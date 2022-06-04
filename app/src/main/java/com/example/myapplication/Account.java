package com.example.myapplication;

public class Account {
    String user;
    public static final String TYPE = " ";
    public Account(){}
    public void viewCourse(Course course){
        System.out.println(course.toString());
    }
    public String getType(){return TYPE;}
}
