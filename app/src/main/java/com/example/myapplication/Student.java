package com.example.myapplication;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A class for student
 * */
public class Student extends User{
    public Student(){}
    public Student(String username,String password){
        this.name = username;
        this.password = password;
    }


    /**
     * Method for enrolling a student to the selected course.
     * @string key of the selected course
     * @String name of the student
     * */
    public static void enroll(String key, String name){
        DatabaseReference courses = FirebaseDatabase.getInstance().getReference().child("courses");

        courses.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Course course = snapshot.getValue(Course.class);

                courses.child(key).setValue(new Course(course.getCode(),course.getName(),course.getInstructor(),course.getDay(),course.getCapacity(),course.getHour(),course.getDescription(), course.addStudent(name)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Method for unenrolling a student from the selected course.
     * @string key of the selected course
     * @String name of the student
     */
    public static void unenroll(String key,String name) {
        DatabaseReference courses = FirebaseDatabase.getInstance().getReference().child("courses");
        courses.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Course course = snapshot.getValue(Course.class);
                String s = course.deleteStudent(name);
                if(s != "false")
                    courses.child(key).setValue(new Course(course.getCode(),course.getName(),course.getInstructor(), course.getDay(),course.getCapacity(), course.getHour(),course.getDescription(),course.deleteStudent(name)));
                else
                    courses.child(key).setValue(new Course(course.getCode(),course.getName(),course.getInstructor(), course.getDay(),course.getCapacity(), course.getHour(),course.getDescription(),course.getStudents()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
