package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class RegisterHome extends AppCompatActivity {
    private EditText usernameEdt,userType, passwordEdt;
    private Button registerBtn;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        database = FirebaseDatabase.getInstance().getReference();

        usernameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        userType = findViewById(R.id.editUserType);
        registerBtn = findViewById(R.id.idBtnRegister);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userName = usernameEdt.getText().toString();

                if(TextUtils.isEmpty(userName))
                    usernameEdt.setError("Enter a valid username");
                String password = passwordEdt.getText().toString();

                if(TextUtils.isEmpty(password))
                    passwordEdt.setError("Enter a valid password");
                String type = userType.getText().toString().trim().toLowerCase(Locale.ROOT);
                if(TextUtils.isEmpty(type))
                    userType.setError("Enter a valid password");
                if(type.equals("student")) writeNewStudent(type, userName,password);
                if(type.equals("instructor")) writeNewInstructor(type,userName,password);
                if(type.equals("admin")) writeNewAdmin(type,userName,password);


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
    public void writeNewAdmin(String type,String name,String password){
        Account account = new AdminAccount(name,password);
        String id = database.push().getKey();
        database.child(type + 's').child(id).setValue(account);
        Toast.makeText(this,type + " added",Toast.LENGTH_SHORT).show();
    }
}