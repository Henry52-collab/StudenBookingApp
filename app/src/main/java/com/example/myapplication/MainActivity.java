
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This is the back end for the login screen, corresponds to activity_main.xml
 * */
public class MainActivity extends AppCompatActivity {
    Button login, register;
    EditText usernameEdt, passwordEdt;
    Intent intent;
    DatabaseReference database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEdt = findViewById(R.id.editTextTextPersonName);
        passwordEdt = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.Login);
        register = findViewById(R.id.idBtnRegister);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        /**
         * This is the onclick method for the login button. If the login button is clicked, this method is called.
         * */
        login.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                //Checking if the credentials are valid, if not, showing a warning
              loginUser();
            }
        });

        /**
         * Method called when register button is called, takes the user to register screen
         * */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            //Do not change
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, RegisterHome.class);
                startActivity(intent);
            }
        });
    }
/*
    /**
     * The method called when user is trying to login with correct credentials
     * */
    private void loginUser() {
      String userName = usernameEdt.getText().toString().trim();
      String password = passwordEdt.getText().toString().trim();
      String email = userName + "@firebase.com";
      String type = "student";
      //Error handling
      if(userName.isEmpty()){
          usernameEdt.setError("Please input username");
          usernameEdt.requestFocus();
          return;
      }
      if(password.isEmpty()){
          passwordEdt.setError("Please input password");
          passwordEdt.requestFocus();
          return;
      }
      //Authenticate user
      mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                mAuth.getCurrentUser().getDisplayName();
                Intent intent = new Intent(MainActivity.this,DisplayMessageActivity.class);
                intent.putExtra("name",userName);
                intent.putExtra("type",type);
                startActivity(intent);
            }
            else{
                Toast.makeText(MainActivity.this,"Failed to login",Toast.LENGTH_LONG).show();
            }
          }
      });
    }
}







