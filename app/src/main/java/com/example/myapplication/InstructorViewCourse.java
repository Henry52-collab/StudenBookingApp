package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/**
 * This is the backend for activity_view_course.
 * When the user clicks on View Course, it takes them here.
 * */
public class InstructorViewCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
    }
}