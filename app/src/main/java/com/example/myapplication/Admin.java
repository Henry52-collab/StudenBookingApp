package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A class for admin
 * */
public class Admin {
    public static final String USER_NAME = "admin";
    public static final String PASSWORD = "admin123";
    private String username;
    private String password;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("courses");
    public Admin(){};
    public Admin(String username,String password){
        this.password = password;
        this.username = username;
    }
    //Getters and Setters
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

    //Utility methods
    public void createCourse(String name,String code){
        Course course = new Course(name,code);
        String id = db.push().getKey();
        db.child(id).setValue(course);
    }

    public void editCourseName(String name){}

    public void editCourseCode(String code){}

    public void deleteCourse(String code){}

    public void deleteInstructor(String name){}

    public void deleteStudent(String name){}
}
