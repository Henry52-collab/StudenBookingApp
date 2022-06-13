package com.example.myapplication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Admin functionality for deliverable 1.
 * */

//Test when all the activities are finished. Feel free to provide your own implementation.
//Courses should be added to the database, please provide database support.
public class AdminAccount extends Account
{

    private Admin admin;
    public static final String TYPE = "admin";
    private static boolean result = true;

    //constructors
    public AdminAccount(String name,String password){
        admin = new Admin(name,password);
    }

    public AdminAccount(String name,String password,String cName,String code){
        admin = new Admin(name,password);
    }

    //Getters and setters
    public String getAdminName(){return admin.getUserName();}
    public String getAdminPassword(){return admin.getPassword();}
    public void setAdminName(String name){admin.setUserName(name);}
    public void setAdminPassword(String password){admin.setPassword(password);}

    /**
     * Adds a course to the database.
     * @param course is the course to be added to the database.
     */
    public static void addCourse(Course course) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("courses");

        String id = db.push().getKey();
        db.child(id).setValue(course);

    }

    /**
     * Edits the course saved under the given key.
     * @param key is the key that the course is saved under
     * @param newCode is new code of the course
     * @param newName is the new name of the course
     * */
    public static void editCourse(String key, String newCode, String newName) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("courses");
        db.child(key).setValue(new Course(newCode, newName));
    }


    /**
     * This course delete the course by code
     * @param key is the key used to access the course in the database.
     * */
    public static void deleteCourse(String key){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("courses").child(key);
        db.removeValue();
    }

    public static void deleteAccount(String key){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        db.removeValue();
    }
}
