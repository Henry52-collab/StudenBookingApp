package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

/**
 * This class is the backend for the register screen, corresponds to activity_main2.xml.
 * */
public class RegisterHome extends AppCompatActivity {
    private EditText usernameEdt,userType, passwordEdt;
    private Button registerBtn;
    private DatabaseReference database;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Make usertype into a dropdown menu, implement it here and in activity_main2.xml.
        database = FirebaseDatabase.getInstance().getReference();
        usernameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        userType = findViewById(R.id.editUserType);
        registerBtn = findViewById(R.id.idBtnRegister);
        mAuth = FirebaseAuth.getInstance();
        /**
         * This method is called when register button is clicked.
         * */
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userName = usernameEdt.getText().toString().trim();
                String password = passwordEdt.getText().toString().trim();
                String type = userType.getText().toString().trim().toLowerCase(Locale.ROOT);
                //Error handling
                if(TextUtils.isEmpty(userName)){
                    usernameEdt.setError("Enter a valid username");
                    usernameEdt.requestFocus();
                    return;
                }
                //password can't be empty
                if(TextUtils.isEmpty(password)){
                    passwordEdt.setError("Enter a valid password");
                    passwordEdt.requestFocus();
                    return;
                }
                //password length can't be less than 6
                if(password.length() < 6){
                    passwordEdt.setError("Password length can't be less than 6");
                    passwordEdt.requestFocus();
                    return;
                }
                //type can't be empty
                if(TextUtils.isEmpty(type)){
                    userType.setError("Enter a valid password");
                    userType.requestFocus();
                    return;
                }
                String email = userName + "@firebase.com";
                //create user and add to database
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            switch(type){
                                case "student":
                                    writeNewStudent("student",userName,password);
                                    break;
                                case "instructor":
                                    writeNewInstructor("instructor",userName,password);
                                    break;
                            }
                        }
                        else{
                            Toast.makeText(RegisterHome.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * Method for writing new students
     * */
    public void writeNewStudent(String type,String name, String password){
        String id = database.push().getKey();
        Account account = new StudentAccount(name,password);
        database.child(type + "s").child(id).setValue(account);
        Toast.makeText(this,type + " added",Toast.LENGTH_SHORT).show();
    }

    /**
     * Method for writing new instrutors into database
     * */
    public void writeNewInstructor(String type,String name,String password){
        Account account = new InstructorAccount(name,password);
        String id = database.push().getKey();
        database.child(type + "s").child(id).setValue(account);
        Toast.makeText(this,type + " added",Toast.LENGTH_LONG).show();
    }


}