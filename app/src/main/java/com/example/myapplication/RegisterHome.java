package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private EditText usernameEdt, passwordEdt;
    private Spinner userType;
    private Button registerBtn;
    private DatabaseReference database;
    private String type;
    private ArrayAdapter adapter;
    private FirebaseAuth mAuth;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Make usertype into a dropdown menu, implement it here and in activity_main2.xml.
        database = FirebaseDatabase.getInstance().getReference();
        usernameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        userType = findViewById(R.id.UserTypeSpinner);
        registerBtn = findViewById(R.id.idBtnRegister);
        mAuth = FirebaseAuth.getInstance();
        backBtn = findViewById(R.id.button);

        /* set up dropdown menu for user type*/
        adapter = ArrayAdapter.createFromResource(RegisterHome.this, R.array.userTypes, android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter);

        userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
       /**
        * BackButton
        * */
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        /**
         * This method is called when register button is clicked.
         * */


        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userName = usernameEdt.getText().toString().trim();
                String password = passwordEdt.getText().toString().trim();

                //Error handling
                if(TextUtils.isEmpty(userName)){
                    usernameEdt.setError("Enter a valid username");
                    usernameEdt.requestFocus();
                    return;
                }

                //password length can't be less than 6
                if(password.length() < 6){
                    passwordEdt.setError("Password length can't be less than 6");
                    passwordEdt.requestFocus();
                    return;
                }

                //check if username contains invalid characters
                if(!userName.matches("[a-zA-Z]+")){
                    usernameEdt.setError("Enter a valid username");
                    usernameEdt.requestFocus();
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
                                    User student = new User(userName,password,"student");
                                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(student);
                                    Toast.makeText(RegisterHome.this,"Registered successfully as student", Toast.LENGTH_LONG).show();
                                    break;
                                case "instructor":
                                    User instructor = new User(userName,password,"instructor");
                                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(instructor);
                                    Toast.makeText(RegisterHome.this,"Registered successfully as instructor", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), instructorHome.class);
                                    startActivity(i);
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

}

