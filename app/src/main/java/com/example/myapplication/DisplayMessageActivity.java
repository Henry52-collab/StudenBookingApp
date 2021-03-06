package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


/**
 * This class is the Welcome Screen, and corresponds to activity_display_message.xml.
 * By pressing Continue, the app moves to a home screen based on the user's role.
 * Progress: Aside from requiring the login page (MainActivity.java) to pass Username2 and Type2 here,
 * everything else should be fully implemented.
 */
public class DisplayMessageActivity extends AppCompatActivity {

    private FirebaseUser user;
    private String uID;
    private DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);


        user = FirebaseAuth.getInstance().getCurrentUser();
        uID = user.getUid();
        final TextView username = findViewById(R.id.idTVUserName);
        final TextView role = findViewById(R.id.Role);

        users = FirebaseDatabase.getInstance().getReference("Users");
        users.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null){
                    String name = user.getName();
                    String type = user.getType();
                    username.setText(name);
                    role.setText(type);
                    if(type.equals("admin")){
                        Intent intent = new Intent(DisplayMessageActivity.this,adminHome.class);
                        startActivity(intent);
                        finish();
                    }
                    if(type.equals("instructor")){
                        Intent intent = new Intent(DisplayMessageActivity.this,instructorHome.class);
                        startActivity(intent);
                        finish();
                    }
                    if(type.equals("student")){
                        Intent intent = new Intent(DisplayMessageActivity.this,StudentHome.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else username.setText("Empty");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}