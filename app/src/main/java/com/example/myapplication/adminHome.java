package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class adminHome extends AppCompatActivity {

    private Button createCourseBtn, viewEditCourseBtn, deleteCourseBtn, deleteAccountBtn;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        createCourseBtn = findViewById(R.id.buttonCreateCourse);
        viewEditCourseBtn = findViewById(R.id.buttonEditCourse);
        deleteCourseBtn = findViewById(R.id.buttonDeleteCourse);
        deleteAccountBtn = findViewById(R.id.buttonDeleteAccount);
        logoutBtn = findViewById(R.id.BtnLogout2);

        /* Set up all buttons to go where needed */
        createCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminHome.this, adminCreateCourse.class);
                startActivity(intent);
            }
        });

        viewEditCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminHome.this, adminViewCourses.class);
                startActivity(intent);
            }
        });

        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminHome.this, adminDeleteCourse.class);
                startActivity(intent);
            }
        });

        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminHome.this, adminDeleteAccount.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

}