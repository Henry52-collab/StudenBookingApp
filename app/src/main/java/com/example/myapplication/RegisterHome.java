package com.example.myapplication;


import android.content.Intent;
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
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class is the backend for the register screen, corresponds to activity_main2.xml.
 * */
public class RegisterHome extends AppCompatActivity {
    private EditText usernameEdt,passwordEdt;
    private String type = "";
    private Spinner types;
    private ArrayAdapter viewTypes;
    private LinkedList<String> usernames = new LinkedList<>();
    private Button registerBtn, cancelBtn;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /* Initialize all variables */
        db = FirebaseDatabase.getInstance().getReference();
        usernameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        registerBtn = findViewById(R.id.idBtnRegister);
        cancelBtn = findViewById(R.id.Cancel3);
        types = findViewById(R.id.UserTypeSpinner);
        usernames.add("admin"); // user cannot create an account with username "admin", even if password is different

        /* Set up spinner to display user types */
        viewTypes = ArrayAdapter.createFromResource(this, R.array.user_types, android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(viewTypes);

        /* Set up listener for spinner; saves whatever type is selected */
        types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /* Set up listener for database so that we can add all existing usernames into database */
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot student = snapshot.child("students");
                DataSnapshot instructor = snapshot.child("instructors");

                try {
                    /* Iterate through list of students in database */
                    for (DataSnapshot data : student.getChildren()) {
                        usernames.add(data.getValue(Student.class).getName());
                    }

                    /* Iterate through list of instructors in database*/
                    for (DataSnapshot data : instructor.getChildren()) {
                        usernames.add(data.getValue(Instructor.class).getName());
                    }
                } catch (Exception e) {
                    usernameEdt.setError(e.getCause().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /* Set up listener for cancel button; returns user to previous page (MainActivity) */
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* Set up listener for register button; registers user if all fields are valid */
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userName = usernameEdt.getText().toString().trim();
                String password = passwordEdt.getText().toString().trim();

                boolean isSuccessful = true; // used to check if all fields are valid

                /* Error handling */
                if(TextUtils.isEmpty(userName)){ // username cannot be empty
                    usernameEdt.setError("Please enter a username.");
                    isSuccessful = false;
                }

                if(password.length() < 6){ //password length can't be less than 6 characters
                    passwordEdt.setError("Password length cannot be less than 6 characters.");
                    isSuccessful = false;
                }

                if (usernames.contains(userName)) { // username cannot already exist in database
                    usernameEdt.setError("An account with this username already exists.");
                    isSuccessful = false;
                }

                /* If any field is invalid, return */
                if (!isSuccessful) {
                    return;
                }

                /* Add user to database according to their type */
                if (type.equals("student")) {
                    writeNewStudent(userName, password);
                } else {
                    writeNewInstructor(userName, password);
                }

                Intent intent = new Intent(RegisterHome.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void writeNewInstructor(String userName, String password) {
        Account account = new InstructorAccount(userName, password);
        String id = db.push().getKey();
        db.child("instructors").child(id).setValue(account);
        Toast.makeText(this,"New instructor added",Toast.LENGTH_LONG).show();
    }

    private void writeNewStudent(String userName, String password) {
        String id = db.push().getKey();
        Account account = new StudentAccount(userName,password);
        db.child("students").child(id).setValue(account);
        Toast.makeText(this,"New student added",Toast.LENGTH_SHORT).show();
    }
    }