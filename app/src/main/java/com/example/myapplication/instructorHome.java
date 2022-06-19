package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/**
 * This class is the home for the instructor.
 * Corresponds to activity_instructor_home.xml.
 */
public class instructorHome extends AppCompatActivity {
    private Button search;
    private Button view;
    private Button assign;
    private Button edit;
    private Button logout;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_home);
        search = findViewById(R.id.btnSearch);
        view = findViewById(R.id.btnView);
        assign = findViewById(R.id.btnAssign);
        logout = findViewById(R.id.btnlogout);
        edit = findViewById(R.id.btnEdit);
        //Onclick listener for the assign button.
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(instructorHome.this,InstructorAssignCourse.class);
                startActivity(intent);
            }
        });
        //Onclick listener for the unassign button.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Onclick listener for the viewCourse button.
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(instructorHome.this,InstructorViewCourse.class);
                startActivity(intent);
            }
        });
        //Onclick listener for the editCourse button.
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(instructorHome.this,InstructorEditCourse.class);
                startActivity(intent);
            }
        });
        //Onclick listener for the searchCourse button.
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(instructorHome.this,InstructorSearchCourse.class);
                startActivity(intent);
            }
        });
    }

}