package com.example.myapplication;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A class for instructor
 * */
public class Instructor extends User {
    public Instructor(String name,String password){
        this.name = name;
        this.password = password;
    }
    public Instructor(){}

    /**
     * Method for assigning the instructor name to the selected course.
     * @string key of the selected course
     * @String name of the instructor
     * */
    public static void assign(String key,String name){
        DatabaseReference courses = FirebaseDatabase.getInstance().getReference().child("courses");

        courses.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Course course = snapshot.getValue(Course.class);
                courses.child(key).setValue(new Course(course.getCode(),course.getName(),name,course.getDay(),course.getCapacity(),course.getHour(),course.getDescription()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void unAssign(String key) {
        DatabaseReference courses = FirebaseDatabase.getInstance().getReference().child("courses");
        courses.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Course course = snapshot.getValue(Course.class);
                courses.child(key).setValue(new Course(course.getCode(),course.getName(),"",0,0,0,""));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
