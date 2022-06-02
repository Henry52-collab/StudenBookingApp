package com.example.courseviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccount extends AppCompatActivity {
    private Button transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transfer = (Button) findViewById(R.id.button_switch);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                openCourseView();
            }
        });
    }
    public void openCourseView() {
        Intent intent = new Intent(this, CourseView.class);
        startActivity(intent);
    }
}