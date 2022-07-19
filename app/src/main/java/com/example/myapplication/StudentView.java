package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
import java.util.Objects;

public class StudentView extends AppCompatActivity {

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
        setContentView(R.layout.activity_student_view);

        myCourses = findViewById(R.id.MyCoursesList);
        backBtn = findViewById(R.id.Back2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        db = FirebaseDatabase.getInstance().getReference();
        username = "";

        /* Find username of current user */
        db.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.getValue(User.class).getName();

                /* Find all courses where student is enrolled */
                db.child("courses").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        courses.clear();
                        courseInfo.clear();
                        for (DataSnapshot data: snapshot.getChildren()) {
                            if (EnrollmentChecker.isEnrolled(data.getValue(Course.class),username)) {
                                courses.add(data.getValue(Course.class));
                                courseInfo.add(data.getValue(Course.class).getDetail());
                            }
                        }

                        if (courseInfo.isEmpty()) {
                            Toast.makeText(StudentView.this, "You are not enrolled in any courses.", Toast.LENGTH_LONG).show();
                        }
                        adapter = new ArrayAdapter(StudentView.this, android.R.layout.simple_list_item_1, courseInfo);
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


    }


}