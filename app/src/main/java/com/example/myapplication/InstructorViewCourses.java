package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class InstructorViewCourses extends AppCompatActivity {

    private ListView myCourses;
    private ArrayAdapter adapter;
    private DatabaseReference db;
    private FirebaseUser user;
    private String uid, username;
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<String> courseInfo = new ArrayList<>();
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_view_courses);
    }
}