package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

/**
 * This class is for creating a course, and corresponds to
 * activity_admin_create_course.xml.
 *
 * Progress: Complete with error handling.
 */
public class adminCreateCourse extends AppCompatActivity {

    private Button cancelBtn;
    private Button confirmBtn;
    private EditText courseCode, courseName;
    private DatabaseReference db;
    private LinkedList<String> codes = new LinkedList<String>(); // used to store course codes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_course);
        /* Initialize all variables */
        cancelBtn = findViewById(R.id.Cancel1);
        confirmBtn = findViewById(R.id.Confirm1);
        courseCode = findViewById(R.id.courseCode);
        courseName = findViewById(R.id.courseName);
        db = FirebaseDatabase.getInstance().getReference().child("courses");

        /* Create listener to get the course codes currently in the database */
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                codes.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data: snapshot.getChildren()) { // loop through each child (course)
                        codes.add(data.getValue(Course.class).getCode()); // add their course code into codes
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /* Create listener for cancel button (returns to adminHome) */
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* Create listener for confirm button (creates course if all fields are valid) */
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Get the course code and name entered by user */
                String code = courseCode.getText().toString().toLowerCase().trim();
                String name = courseName.getText().toString().toLowerCase().trim();

                /* Check for errors and then add course */
                if (TextUtils.isEmpty(code) && TextUtils.isEmpty(name)) { // both fields are empty
                    courseCode.setError("Course code cannot be left blank.");
                    courseName.setError("Course name cannot be left blank.");
                } else if (TextUtils.isEmpty(code)) { // course code field is empty
                    courseCode.setError("Course code cannot be left blank.");
                } else if (TextUtils.isEmpty(name)) { // course name field is empty
                    courseName.setError("Course name cannot be left blank.");
                } else if (codes.contains(code)) { // course code already exists
                    courseCode.setError("A course with this code already exists.");
                } else { // otherwise, add course
                    AdminAccount.addCourse(new Course(code, name));
                    courseCode.setText(""); // reset text
                    courseName.setText("");
                    Toast.makeText(adminCreateCourse.this,"Course added", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}