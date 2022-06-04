package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        userName = (TextView)findViewById(R.id.idTVUserName);
        String name = getIntent().getStringExtra("username");
        userName.setText(name);
    }
}