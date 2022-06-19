package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This class is for deleting an account, and corresponds to
 * activity_admin_delete_account.xml.
 *
 * Progress: Complete (no error handling needed)
 */
public class adminDeleteAccount extends AppCompatActivity {

    private DatabaseReference db;
    private Button cancelBtn, deleteBtn;
    private Spinner viewNames;
    private ArrayAdapter adapter;
    private ArrayList<String> names = new ArrayList<>();
    private String key;
    private Query q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_account);

        /* Initialize buttons */
        cancelBtn = findViewById(R.id.idBtnCancel);
        deleteBtn = findViewById(R.id.idBtnDelete);
        viewNames = findViewById(R.id.idAccountSpinner);
        db = FirebaseDatabase.getInstance().getReference().child("Users");

        /* Create db listener to get course codes from database */
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                names.clear();
                for (DataSnapshot data: snapshot.getChildren()) {
                    if(!data.getValue(User.class).getType().equals("admin")) names.add(data.getValue(User.class).getName());
                }
              
                /* Use an adapter to display the course codes in the spinner */
                adapter = new ArrayAdapter<>(adminDeleteAccount.this, android.R.layout.simple_spinner_dropdown_item, names);
                viewNames.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /* Create listener for spinner to get the key of selected course */
        viewNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCourse = adapterView.getItemAtPosition(i).toString();
                q = db.orderByChild("name").equalTo(selectedCourse);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            key = data.getKey(); // save key
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { // probably won't ever be triggered
                Toast.makeText(adminDeleteAccount.this, "Please select an account.", Toast.LENGTH_SHORT).show();
            }
        });

        /* Create listener for cancelBtn to allow user to leave without deleting anything */
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* Create listener for deleteBtn */
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Delete course (no error handling needed here) */
                AdminAccount.deleteAccount(key);
                Toast.makeText(adminDeleteAccount.this, "Account deleted", Toast.LENGTH_SHORT).show();

            }
        });
    }
}