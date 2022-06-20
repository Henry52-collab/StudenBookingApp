package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Locale;
/**
 * This class is used for assigning/unassigning an instructor to a course.
 *  Corresponds to activity_instructor_assign_course.xml.
 * */
public class InstructorAssignCourse extends AppCompatActivity {
    private Button assign;
    private Button unassign;
    private Button back;
    private Spinner spinner;
    private TextView title;
    private TextView instruction;
    private DatabaseReference db;
    private ArrayList<String> course;
    private ArrayAdapter adapter;
    private String selected;
    private String oldInstructor;
    private Query q;
    private String key;
    private String name;
    private FirebaseUser user;
    private String instructor;
    private String uID;
    private DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_assign_course);
        //Initialize attributes
        db = FirebaseDatabase.getInstance().getReference().child("courses");
        assign = findViewById(R.id.Assign);
        unassign = findViewById(R.id.Unassign);
        back = findViewById(R.id.back);
        spinner = findViewById(R.id.spinner2);
        title = findViewById(R.id.Title);
        instruction = findViewById(R.id.Query);
        course = new ArrayList<>();
        key = "";
        user = FirebaseAuth.getInstance().getCurrentUser();
        instructor = user.getDisplayName();
        uID = user.getUid();
        users = FirebaseDatabase.getInstance().getReference().child("Users");
        //Retrieve the user name
        users.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null) name = user.getName();
                else name = "Error";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Retrieve courses from database
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()) {
                     course.add(data.getValue(Course.class).getCode());
                }
                adapter = new ArrayAdapter<>(InstructorAssignCourse.this,android.R.layout.simple_spinner_dropdown_item,course);
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
                            oldInstructor = data.getValue(Course.class).getInstructor(); // save name
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(InstructorAssignCourse.this, "Please select a course.", Toast.LENGTH_SHORT).show();
            }
        });
        //Assign course
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!oldInstructor.equals("")) {
                    Toast.makeText(InstructorAssignCourse.this, "Unable to Assign", Toast.LENGTH_SHORT).show();
                    return;
                }
                Instructor.assign(key,name);
                Toast.makeText(InstructorAssignCourse.this, "You have been assigned", Toast.LENGTH_SHORT).show();
            }
        });
        //Unassign course
        unassign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instructor.unAssign(key);
                Toast.makeText(InstructorAssignCourse.this, "You have been unassigned", Toast.LENGTH_SHORT).show();
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