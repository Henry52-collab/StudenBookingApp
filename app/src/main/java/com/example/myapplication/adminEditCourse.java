package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This class is for creating a course, and corresponds to
 * activity_admin_edit_course.xml.
 *
 * Progress: Complete with error handling.
 */
public class adminEditCourse extends AppCompatActivity {

    private DatabaseReference db;
    private Button cancelBtn, saveBtn;
    private EditText newCourseCode, newCourseName;
    private Spinner courses;
    private ArrayAdapter adapter;
    private ArrayList<String> codes = new ArrayList<>(); // used for the spinner
    private String selectedCourse; // stores the code of the course that admin wants to change
    private String oldCourseName; // stores the name of the course that admin wants to change
    private Query q; // used to find key of course that admin wants to change
    private String key = ""; // stores key of course that admin wants to change

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_course);

        /* Initialize buttons */
        cancelBtn = findViewById(R.id.Cancel2);
        saveBtn = findViewById(R.id.saveChanges);
        newCourseCode = findViewById(R.id.newCourseCode);
        newCourseName = findViewById(R.id.newCourseName);
        courses = findViewById(R.id.spinner);
        db = FirebaseDatabase.getInstance().getReference().child("courses");

        /* Create db listener to get course codes from database */
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data: snapshot.getChildren()) {
                    codes.add(data.getValue(Course.class).getCode());
                }

                /* Use an adapter to display the course codes in the spinner */
                adapter = new ArrayAdapter<>(adminEditCourse.this, android.R.layout.simple_spinner_dropdown_item, codes);
                courses.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /* Create listener for spinner to get values for selectedCourse, oldCourseName, and key */
        courses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCourse = adapterView.getItemAtPosition(i).toString();

                q = db.orderByChild("code").equalTo(selectedCourse); // query looks for course with the selected course code
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data: snapshot.getChildren()) {
                            key = data.getKey(); // save key
                            oldCourseName = data.getValue(Course.class).getName(); // save name
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { // probably won't ever be triggered
                Toast.makeText(adminEditCourse.this, "Please select a course.", Toast.LENGTH_SHORT).show();
            }
        });

        /* Create cancelBtn listener to allow user to return to View Courses without changing anything */
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* Create saveBtn listener to edit course information, if all fields are valid */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Get the course code and name inputted by user */
                String code = newCourseCode.getText().toString().trim().toLowerCase();
                String name = newCourseName.getText().toString().trim().toLowerCase();

                /* Check for errors before attempting to update course information */
                if (TextUtils.isEmpty(code) && TextUtils.isEmpty(name)) { // both fields are empty
                    Toast.makeText(adminEditCourse.this, "Please enter a new course code and/or name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isEmpty(code)) { // if code is not empty
                    if (codes.contains(code) && !selectedCourse.equals(code)) { // check that some other course does not have the same code
                        newCourseCode.setError("A different course with this code already exists.");
                        return;
                    }
                } else { // otherwise, set it to selectedCourse
                    code = selectedCourse;
                }

                if (TextUtils.isEmpty(name)) { // if name is empty, set name to what it was originally
                    name = oldCourseName;
                }

                /* Edit course information and return to last activity (View Courses) */
                AdminAccount.editCourse(key, code, name);
                Toast.makeText(adminEditCourse.this, "Changes saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}