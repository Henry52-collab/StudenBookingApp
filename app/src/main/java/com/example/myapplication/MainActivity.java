package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This is the back end for the login screen, corresponds to activity_main.xml
 * */
public class MainActivity extends AppCompatActivity {
    Button login, register;
    EditText usernameEdt, passwordEdt;
    Intent intent;
    DatabaseReference database;
    ArrayList<Account> accounts;
    AdminAccount admin = new AdminAccount("Admin", "admin123");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEdt = findViewById(R.id.editTextTextPersonName);
        passwordEdt = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.Login);
        register = findViewById(R.id.idBtnRegister);
        database = FirebaseDatabase.getInstance().getReference();
        accounts = new ArrayList<>();
        /**
         * This is the onclick method for the login button. If the login button is clicked, this method is called.
         * */
        login.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String username = usernameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                //Checking if the credentials are valid, if not, showing a warning
                if (TextUtils.isEmpty(username))
                    usernameEdt.setError("Please enter username");
                if(TextUtils.isEmpty(password))
                    passwordEdt.setError("Please enter password");
                //Otherwise, perform the login action
                //The app crashes when login button is pressed, fix it here.
                loginUser(username, password);
            }
        });


        /**
         * Method called when register button is called, takes the user to register screen
         * */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            //Do not change
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, RegisterHome.class);
                startActivity(intent);
            }
        });
    }

    /**
     * The method called when user is trying to login with correct credentials
     * */
    private void loginUser(String userName, String password) {
        /*
        * Please implement the login functionality here, be sure to check if the account exists in the database.
        *
        *  EDIT: Login crash occurs here, so you may want to remove everything that's currently here
        *  If you could also pass on the username and the role of the person logging in to DisplayMessageActivity,
        * (see lines 103-106 below) that would be greatly appreciated!
        * */
        ArrayList<StudentAccount> students = new ArrayList<>();
        ArrayList<AdminAccount> admins = new ArrayList<>();
        ArrayList<InstructorAccount> instructors = new ArrayList<>();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //implement
                //Loop over entries in database.
                for (DataSnapshot each : snapshot.getChildren()) {
                    StudentAccount account = each.getValue(StudentAccount.class);
                    accounts.add(account);
                }

                //Check if the entry exists.
                if (accounts.contains(new StudentAccount(userName, password))) {
                    Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                    intent.putExtra("Username2", userName); // EDIT: passes the username to Display activity under the key "username2"
                    // EDIT: if possible, please pass the role to Display activity under the key "Type2"
                    startActivity(intent);

                }


            }


            @Override
            /**
             * Method called when failed to retrieve an entry
             * */
            public void onCancelled(@NonNull DatabaseError error) {
                //Optional, You can implement if you want.
            }
        });
    }
}






