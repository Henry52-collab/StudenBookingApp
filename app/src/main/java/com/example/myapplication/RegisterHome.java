package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


        /**
         * This method is called when register button is clicked.
         * */
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userName = usernameEdt.getText().toString();
                if(TextUtils.isEmpty(userName))
                    usernameEdt.setError("Enter a valid username");
                String password = passwordEdt.getText().toString();
                if(TextUtils.isEmpty(password))
                    passwordEdt.setError("Enter a valid password");
                //Do not change anything above this line
                //Implement here
                String type = userType.getText().toString().trim().toLowerCase(Locale.ROOT);
                if(TextUtils.isEmpty(type))
                    userType.setError("Enter a valid password");

                boolean isSuccessful = false; // EDIT: added this but could possibly be removed once more specific error handling is implemented
                if(type.equals("student")) {
                    writeNewStudent(type, userName,password);
                    isSuccessful = true;
                }
                if(type.equals("instructor")) {
                    writeNewInstructor(type,userName,password);
                    isSuccessful = true;
                }

                // EDIT: passes username and type to DisplayMessageActivity before going to said activity

                if (isSuccessful) {
                    Intent intent = new Intent(RegisterHome.this, DisplayMessageActivity.class);
                    intent.putExtra("Username", userName);
                    intent.putExtra("Type", type);
                    startActivity(intent);
                    finish();
                }
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


    /**
     * Method for writing new admins into database
     * */
    //Admins don't need to register, add it straight to database.
    /*
    public void writeNewAdmin(String type,String name,String password){
        Account account = new AdminAccount(name,password);
        String id = database.push().getKey();
        database.child(type + 's').child(id).setValue(account);
        Toast.makeText(this,type + " added",Toast.LENGTH_SHORT).show();
    }
    */
}