package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
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

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * This is the backend for activity_edit_course.When the user clicks on the edit course button,
 * it takes them here.
 * */
public class InstructorEditCourse extends AppCompatActivity {
    private Spinner courseSpinner;
    private Spinner day1Spinner, day2Spinner;
    private ArrayAdapter adapter1, adapter2; // adapters for the spinners
    private TextView tvCode, tvName;
    private EditText edtCapacity, edtStart1, edtStart2, edtEnd1, edtEnd2, edtCourseDescription;
    private Button cancelBtn, saveChangesBtn;
    private FirebaseUser user;
    private String uid;
    private String username = "";
    private DatabaseReference db;
    private Query q1, q2;
    private ArrayList<String> codes = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    private String day1, day2, courseKey, courseDescription, students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_edit_course);

        /* Initialize all variables */
        courseSpinner = findViewById(R.id.EditCourseSpinner);
        day1Spinner = findViewById(R.id.DaySpinner1);
        day2Spinner = findViewById(R.id.DaySpinner2);
        edtStart1 = findViewById(R.id.StartTime1);
        edtEnd1 = findViewById(R.id.EndTime1);
        edtStart2 = findViewById(R.id.StartTime2);
        edtEnd2 = findViewById(R.id.EndTime2);
        tvCode = findViewById(R.id.CourseCode2);
        tvName = findViewById(R.id.CourseName2);
        edtCapacity = findViewById(R.id.EditCapacity);
        edtCourseDescription = findViewById(R.id.CourseDescription);
        cancelBtn = findViewById(R.id.Cancel3);
        saveChangesBtn = findViewById(R.id.SaveChanges);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        db = FirebaseDatabase.getInstance().getReference();

        /* Find username of current user and then set up courseSpinner accordingly */
        db.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.getValue(User.class).getName(); // get username
                q1 = db.child("courses").orderByChild("instructor").equalTo(username); // find all courses where instructor is current user

                q1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        codes.clear();
                        courses.clear();

                        /* Collect instructor's courses and set up dropdown menu for courseSpinner */
                        for (DataSnapshot data : snapshot.getChildren()) {
                            courses.add(data.getValue(Course.class));
                            codes.add(data.getValue(Course.class).getCode());
                        }

                        if (codes.isEmpty()) { // if instructor is not assigned to any course, leave activity
                            Toast.makeText(InstructorEditCourse.this, "No courses to edit. Please assign yourself to a course first.", Toast.LENGTH_LONG).show();
                            finish();
                        } else { // otherwise, set up adapter
                            adapter1 = new ArrayAdapter<>(InstructorEditCourse.this, android.R.layout.simple_spinner_dropdown_item, codes);
                            courseSpinner.setAdapter(adapter1);
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

        /* Set up the dropdown menus for day spinner */
        adapter2 = ArrayAdapter.createFromResource(InstructorEditCourse.this, R.array.daysOfTheWeek, android.R.layout.simple_spinner_dropdown_item);
        day1Spinner.setAdapter(adapter2);
        day2Spinner.setAdapter(adapter2);

        /* Set up listeners for spinners */
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCourse = adapterView.getItemAtPosition(i).toString();

                q2 = db.child("courses").orderByChild("code").equalTo(selectedCourse);
                q2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data: snapshot.getChildren()) {
                            courseKey = data.getKey();
                            tvCode.setText("Course code: " + data.getValue(Course.class).getCode());
                            tvName.setText("Course name: " + data.getValue(Course.class).getName());
                            students = data.getValue(Course.class).getStudents();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        day1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                day1 = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        day2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                day2 = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        /* Let cancelBtn return user to instructorHome */
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                String code = tvCode.getText().toString().substring(13);
                String name = tvName.getText().toString().substring(13);
                courseDescription = edtCourseDescription.getText().toString();

                /* ERROR HANDLING (Note: Course description CAN be empty) */
                boolean allValid = true;
                int capacity = 0;

                // 1) capacity is not empty or zero
                String cap = edtCapacity.getText().toString();

                if (cap.equals("")) {
                    edtCapacity.setError("Capacity cannot be left blank.");
                    allValid = false;
                } else {
                    capacity = Integer.parseInt(cap);

                    if (capacity == 0) {
                        edtCapacity.setError("Capacity cannot be zero.");
                        allValid = false;
                    }
                }

                // 2) both day spinners are not set to "none"
                if (day1.equals("none") && day2.equals("none")) {
                    Toast.makeText(InstructorEditCourse.this, "Please select at least one day to instruct the course.", Toast.LENGTH_SHORT).show();
                    allValid = false;
                }

                String days = "";
                String hours = "";
                String start1 = edtStart1.getText().toString();
                String end1 = edtEnd1.getText().toString();
                String start2 = edtStart2.getText().toString();
                String end2 = edtEnd2.getText().toString();

                if (!day1.equals("none")) { // 3) if day1 is selected

                    days += day1 + " "; // add day1 to days string

                    // i) start1 and end1 are not empty and are a valid format
                    if (start1.equals("")) {
                        edtStart1.setError("Please add a start time.");
                        allValid = false;
                    } else if (!TimeChecker.isValidFormat(start1)) {
                        edtStart1.setError("Invalid format. Please try again.");
                        allValid = false;
                    }

                    if (end1.equals("")) {
                        edtEnd1.setError("Please add an end time.");
                        allValid = false;
                    } else if (!TimeChecker.isValidFormat(end1)) {
                        edtEnd1.setError("Invalid format. Please try again.");
                        allValid = false;
                    }

                    // ii) end1 is not before start1
                    if (allValid && ! TimeChecker.endIsAfter(start1, end1)) { // everything is valid so far, but end is before or equal to start
                        edtEnd1.setError("End time cannot be before/at start time.");
                        allValid = false;
                    }

                    // iii) time does not conflict with times set up in other courses
                    if (allValid && courses.size() > 1) {
                        for (int i = 0; i < courses.size(); i++) { // iterate through the instructor's courses
                            if (!courses.get(i).getCode().equals(code) && !courses.get(i).getDay().equals("")) { // if course is not the one currently selected
                                String[] ds = courses.get(i).getDay().split(" ", 2);
                                for (int j = 0; j < ds.length; j++) {
                                    if (ds[j].equals(day1)) { // if one of the course days is equal to day1
                                        String[] hs = courses.get(i).getHour().split(" ", 2);
                                        String t1 = hs[j].split("-", 2)[0];
                                        String t2 = hs[j].split("-", 2)[1];

                                        if (TimeChecker.timeConflictExists(start1, end1, t1, t2)) {
                                            edtStart1.setError("You have a lecture for " + courses.get(i).getCode() + " during this time. Please edit that course first or use a different time.");
                                            allValid = false;
                                        }
                                    }
                                }
                            }
                        }
                    }


                    hours += start1 + "-" + end1 + " ";
                }

                if (!day2.equals("none")) { // 4) if day2 is selected

                    days += day2;

                    // i) start2 and end2 are not empty and are a valid format
                    if (start2.equals("")) {
                        edtStart2.setError("Please add a start time.");
                        allValid = false;
                    } else if (!TimeChecker.isValidFormat(start2)) {
                        edtStart2.setError("Invalid format. Please try again.");
                        allValid = false;
                    }

                    if (end2.equals("")) {
                        edtEnd2.setError("Please add an end time.");
                        allValid = false;
                    } else if (!TimeChecker.isValidFormat(end2)) {
                        edtEnd2.setError("Invalid format. Please try again.");
                        allValid = false;
                    }

                    // ii) end2 is not before or equal to start2
                    if (allValid && ! TimeChecker.endIsAfter(start2, end2)) { // everything is valid so far, but end is before or equal to start
                        edtEnd2.setError("End time cannot be before start time.");
                        allValid = false;
                    }

                    // iii) there are no time conflicts with previously established times (might handle this later)

                   if (allValid && courses.size() > 1) {
                       for (int i = 0; i < courses.size(); i++) { // iterate through the instructor's courses
                           if (!courses.get(i).getCode().equals(code) && !courses.get(i).getDay().equals("")) { // if course is not the one currently selected
                               String[] ds = courses.get(i).getDay().split(" ", 2);
                               for (int j = 0; j < ds.length; j++) {
                                   if (ds[j].equals(day2)) { // if one of the course days is equal to day1
                                       String[] hs = courses.get(i).getHour().split(" ", 2);
                                       String t1 = hs[j].split("-", 2)[0];
                                       String t2 = hs[j].split("-", 2)[1];

                                       if (TimeChecker.timeConflictExists(start2, end2, t1, t2)) {
                                           edtStart2.setError("You have a lecture for " + courses.get(i).getCode() + " during this time. Please edit that course first or use a different time.");
                                           allValid = false;
                                       }
                                   }
                               }
                           }
                       }
                   }

                    hours += start2 + "-" + end2;
                }

                // 5) if day1 and day2 were both selected, check that their times do not conflict
                if (allValid && !day1.equals("none") && day1.equals(day2)) {
                    if (TimeChecker.timeConflictExists(start1, end1, start2, end2)) {
                        edtStart2.setError("This conflicts with the other lecture you have for this course. Please enter a different time.");
                        allValid = false;
                    }
                }

                /* If input passes all checks, edit the course; otherwise, don't do anything */
                if (allValid) {
                    Course course = new Course(code, name, username, days, capacity, hours, courseDescription, students);
                    Instructor.editCourse(courseKey, course);
                    Toast.makeText(InstructorEditCourse.this, "Successfully edited " + code, Toast.LENGTH_LONG).show();
                } else {
                    return;
                }
            }
        });

    }



}