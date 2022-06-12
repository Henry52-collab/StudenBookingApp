package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class adminHome extends AppCompatActivity {

    private Button buttonDeleteCourse;
    private Button buttonDeleteAccount;
    private Button buttonCreateCourse;
    private Button buttonEditCourse;

    private DatabaseReference rootDatabaseref;
    private DatabaseReference rootDatabaseref2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        buttonDeleteCourse = findViewById(R.id.buttonDeleteCourse);
        rootDatabaseref = FirebaseDatabase.getInstance().getReference().child("courses");
        rootDatabaseref2 = FirebaseDatabase.getInstance().getReference().child("students");

        buttonDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootDatabaseref.child("N49fHLKdMNzQDEoZoDu").setValue(null);

            }
        });


        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootDatabaseref.child("N49fHLKdMNzQDEoZoDu").setValue(null);

            }
        });

        /*

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("courses").orderByChild("code").equalTo("seg2105");

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

         */




    }

}