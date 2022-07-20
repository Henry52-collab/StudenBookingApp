package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentList extends AppCompatActivity {

    private TextView courseCode, courseName;
    private Button backBtn;
    private ListView studentList;
    private ArrayList<String> students = new ArrayList<>();
    private ArrayAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        courseCode = findViewById(R.id.tvCourseCode);
        courseName = findViewById(R.id.tvCourseName);
        studentList = findViewById(R.id.MyStudentsList);
        backBtn = findViewById(R.id.Back4);

        /* Get course code, course name, and students from previous activity */
        Intent intent = getIntent();
        courseCode.setText(courseCode.getText() + intent.getStringExtra("Course Code"));
        courseName.setText(courseName.getText() + intent.getStringExtra("Course Name"));
        String[] stu = intent.getStringExtra("Students").split(" ");

        /* Add all (non-empty) students to ArrayList */
        for (int i = 0; i < stu.length; i++) {
            if (!stu[i].isEmpty()) {
                students.add(stu[i]);
            }
        }

        /* Set up adapter to ListView */
        if (students.isEmpty()) {
            Toast.makeText(StudentList.this, "There are no students enrolled in this course yet.", Toast.LENGTH_LONG).show();
        }

        adapter = new ArrayAdapter(StudentList.this, android.R.layout.simple_list_item_1, students);
        studentList.setAdapter(adapter);

        /* Set up listener for back button */
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}