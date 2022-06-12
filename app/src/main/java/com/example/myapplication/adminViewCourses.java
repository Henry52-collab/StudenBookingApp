package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This class is for creating a course, and corresponds to
 * activity_admin_view_courses.xml.
 *
 * Progress: Complete with error handling.
 */
public class adminViewCourses extends AppCompatActivity {

    private Button backBtn;
    private Button editCourseBtn;
    private DatabaseReference db;
    private ListView courseListView;

    private ArrayList<String> courseInfo = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_courses);

        /* Initialize variables*/
        backBtn = findViewById(R.id.Back);
        editCourseBtn = findViewById(R.id.EditCourse);
        courseListView = findViewById(R.id.courseList);
        db = FirebaseDatabase.getInstance().getReference().child("courses");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseInfo.clear();

                for (DataSnapshot data: snapshot.getChildren()) {
                    Course course = data.getValue(Course.class);
                    courseInfo.add("Course code: " + course.getCode() + "\nCourse name: " + course.getName());
                }

                viewAllCourses();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (courseInfo.isEmpty()) {
                    Toast.makeText(adminViewCourses.this, "No courses to edit. Please add a course first.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(adminViewCourses.this, adminEditCourse.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void viewAllCourses() {

        if (courseInfo.isEmpty()) {
            Toast.makeText(this, "No courses currently", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseInfo);
            courseListView.setAdapter(adapter);
        }
    }
}