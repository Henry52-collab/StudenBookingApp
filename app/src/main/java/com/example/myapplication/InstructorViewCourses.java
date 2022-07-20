package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InstructorViewCourses extends AppCompatActivity {

    private ListView myCourses;
    private ArrayAdapter adapter;
    private DatabaseReference db;
    private FirebaseUser user;
    private Query q;
    private String uid, username;
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<String> courseInfo = new ArrayList<>();
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_view_courses);

        myCourses = findViewById(R.id.MyCoursesList2);
        backBtn = findViewById(R.id.Back3);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        db = FirebaseDatabase.getInstance().getReference();
        username = "";

        /* Find username of current user */
        db.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.getValue(User.class).getName(); // get username
                q = db.child("courses").orderByChild("instructor").equalTo(username);

                /* Get all courses where instructor is current user */
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        courses.clear();
                        courseInfo.clear();

                        for (DataSnapshot data : snapshot.getChildren()) {
                            courses.add(data.getValue(Course.class));
                            courseInfo.add(data.getValue(Course.class).getDetail());
                        }

                        if (courseInfo.isEmpty()) {
                            Toast.makeText(InstructorViewCourses.this, "You are not assigned to any courses.", Toast.LENGTH_LONG).show();
                        }
                        adapter = new ArrayAdapter(InstructorViewCourses.this, android.R.layout.simple_list_item_1, courseInfo);
                        myCourses.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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

        myCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(InstructorViewCourses.this, StudentList.class);
                intent.putExtra("Students", courses.get(i).getStudents());
                intent.putExtra("Course Code", courses.get(i).getCode());
                intent.putExtra("Course Name", courses.get(i).getName());
                startActivity(intent);
            }
        });
    }
}