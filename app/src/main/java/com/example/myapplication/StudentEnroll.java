package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

public class StudentEnroll extends AppCompatActivity {
    private Spinner spinner;
    private TextView title,query;
    private Button enroll,unenroll,back;
    private DatabaseReference db;
    private ArrayList<String> course;
    private ArrayAdapter adapter;
    private String selected;
    private Query q;
    private String key;
    private String name;
    private FirebaseUser user;
    private String uID;
    private DatabaseReference users;
    private String student;
    private String students;
    private Course c;
    private ArrayList<Course> courses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enroll);
        //initialize attributes
        spinner = findViewById(R.id.spinner3);
        title = findViewById(R.id.Enroll);
        query = findViewById(R.id.Query);
        enroll = findViewById(R.id.btnEnroll);
        unenroll = findViewById(R.id.btnUnenroll);
        back = findViewById(R.id.back);
        db = FirebaseDatabase.getInstance().getReference().child("courses");
        course = new ArrayList<>();
        key = "";
        user = FirebaseAuth.getInstance().getCurrentUser();
        uID = user.getUid();
        student = user.getDisplayName();
        users = FirebaseDatabase.getInstance().getReference().child("Users");
        courses = new ArrayList<>();

        //Retrieve the user name
        users.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null) name = user.getName();
                else name = "Error";
                //Retrieve all courses
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        courses.clear();
                        for (DataSnapshot data: snapshot.getChildren()) {
                            if (EnrollmentChecker.isEnrolled(data.getValue(Course.class),name)) {
                                courses.add(data.getValue(Course.class));
                            }
                        }
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

        //Retrieve courses from database
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                course.clear();
                for (DataSnapshot data:snapshot.getChildren()) {
                    course.add(data.getValue(Course.class).getCode());
                }
                adapter = new ArrayAdapter<>(StudentEnroll.this,android.R.layout.simple_spinner_dropdown_item,course);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Set listener for the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                q = db.orderByChild("code").equalTo(selected);

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data: snapshot.getChildren()) {
                            key = data.getKey(); // save key
                            c = data.getValue(Course.class);
                            students = data.getValue(Course.class).getStudents();//save students
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(StudentEnroll.this, "Please select a course.", Toast.LENGTH_SHORT).show();
            }
        });

        //Enroll into course
        enroll.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(!students.contains(name) && Student.checkConflict(c,name,courses)){
                    Student.enroll(key,name);
                    Toast.makeText(StudentEnroll.this, "You have been enrolled", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(StudentEnroll.this, "unable to enroll", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Unenroll from course
        unenroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student.unenroll(key,name);
                if(!students.contains(name))Toast.makeText(StudentEnroll.this, "unable to unenroll", Toast.LENGTH_SHORT).show();
                else Toast.makeText(StudentEnroll.this, "You have been unenrolled", Toast.LENGTH_SHORT).show();
            }
        });

        //Back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}