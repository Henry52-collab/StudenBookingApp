package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Backend for the welcome screen, corresponds to activity_register.
 * */
public class RegisterActivity extends AppCompatActivity {
    private TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        //Implement here
        userName = (TextView)findViewById(R.id.idTVUserName);
        //Show the appropriate welcome message with user rile, name and username.
        String name = getIntent().getStringExtra("username");
        userName.setText(name);
    }
}