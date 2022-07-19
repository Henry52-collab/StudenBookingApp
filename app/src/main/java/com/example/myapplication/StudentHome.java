package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * A class for studentHome
 * */
public class StudentHome extends AppCompatActivity {
    private Button search;
    private Button view;
    private Button enroll;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        search = findViewById(R.id.Search);
        view = findViewById(R.id.View);
        enroll = findViewById(R.id.Enroll);
        logout = findViewById(R.id.logout);
        //onClick listener for Search course
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this,StudentSearch.class);
                startActivity(intent);
            }
        });
        //onClick listener for view course
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this,StudentView.class);
                startActivity(intent);
            }
        });
        //onClick listener for enroll/unenroll
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this,StudentEnroll.class);
                startActivity(intent);
            }
        });
        //onClick listener for logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}