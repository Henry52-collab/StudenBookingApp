package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class is the Welcome Screen, and corresponds to activity_display_message.xml.
 * By pressing Continue, the app moves to a home screen based on the user's role.
 * Progress: Aside from requiring the login page (MainActivity.java) to pass Username2 and Type2 here,
 * everything else should be fully implemented.
 */
public class DisplayMessageActivity extends AppCompatActivity {
    private Button logoutBtn;
    private Button continueBtn;
    private TextView username;
    private TextView role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        logoutBtn = (Button) findViewById(R.id.BtnLogout);
        continueBtn = (Button) findViewById(R.id.continueBtn);
        username = findViewById(R.id.idTVUserName);
        role = findViewById(R.id.Role);


        /* Set username and role TextView to the respective username and role of user */
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String name = "";
        String type = "";
        try {
            if (extras.containsKey("Username2")) { // user reached welcome page via login
                name = intent.getStringExtra("Username2");
                type = intent.getStringExtra("Type2");
            } else { // user reached welcome page via register
                name = intent.getStringExtra("Username");
                type = intent.getStringExtra("Type");
            }
        } catch (NullPointerException e) {
            System.out.println("Extras is null");
        }

        username.setText(name);
        role.setText(type);

        logoutBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               Intent i = new Intent(DisplayMessageActivity.this,MainActivity.class);
               startActivity(i);
               finish();
            }
        });

        String finalType = type;
        String finalName = name;
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j;

                if (finalType.equals("student")) {
                    j = new Intent(DisplayMessageActivity.this, studentHome.class);
                } else if (finalType.equals("instructor")) {
                    j = new Intent(DisplayMessageActivity.this, instructorHome.class);
                } else { // admin
                    j = new Intent(DisplayMessageActivity.this, adminHome.class);
                }

                j.putExtra("Username", finalName);
                startActivity(j);
                finish();
            }

        });


    }



}