package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This class is for deleting a course, and corresponds to
 * activity_admin_delete_course.xml.
 *
 * Progress: Complete (no error handling needed)
 */
public class adminDeleteCourse extends AppCompatActivity {

    private DatabaseReference db;
    private Button cancelBtn, deleteBtn;
    private Spinner viewCodes;
    private ArrayAdapter adapter;
    private ArrayList<String> codes = new ArrayList<>();
    private String key;
    private Query q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_course);

        /* Initialize buttons */
        cancelBtn = findViewById(R.id.idBtnCancel);
        deleteBtn = findViewById(R.id.idBtnDelete);
        viewCodes = findViewById(R.id.CourseSpinner);

        db = FirebaseDatabase.getInstance().getReference().child("courses");

        /* Create db listener to get course codes from database */
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                codes.clear();
                for (DataSnapshot data: snapshot.getChildren()) {
                    codes.add(data.getValue(Course.class).getCode());
                }
              
                /* Use an adapter to display the course codes in the spinner */
                adapter = new ArrayAdapter<>(adminDeleteCourse.this, android.R.layout.simple_spinner_dropdown_item, codes);
                viewCodes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /* Create listener for spinner to get the key of selected course */
        viewCodes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedCourse = adapterView.getItemAtPosition(i).toString();
                q = db.orderByChild("code").equalTo(selectedCourse); // query looks for course with the selected course code
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            key = data.getKey(); // save key
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { // probably won't ever be triggered
                Toast.makeText(adminDeleteCourse.this, "Please select a course.", Toast.LENGTH_SHORT).show();
            }
        });

        /* Create listener for cancelBtn to allow user to leave without deleting anything */
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* Create listener for deleteBtn */
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Delete course (no error handling needed here) */
                AdminAccount.deleteCourse(key);
                Toast.makeText(adminDeleteCourse.this, "Course deleted", Toast.LENGTH_SHORT).show();

            }
        });
    }
}