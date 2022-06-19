package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/**
 * This is the backend for activity_edit_course.When the user clicks on the edit course button,
 * it takes them here.
 * */
public class InstructorEditCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
    }
}