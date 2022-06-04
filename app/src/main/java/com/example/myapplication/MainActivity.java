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


public class MainActivity extends AppCompatActivity{
    Button login,register;
    EditText usernameEdt,passwordEdt;
    Intent intent;
    DatabaseReference database;
    ArrayList<Account> accounts;
    AdminAccount admin = new AdminAccount("Admin","admin123");
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password))
                    Toast.makeText(MainActivity.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                //intent = new Intent(MainActivity.this,DisplayMessageActivity.class);
                //startActivity(intent);
                loginUser(username,password);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, RegisterHome.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String userName,String password){
        ArrayList<StudentAccount> students = new ArrayList<>();
        ArrayList<AdminAccount> admins = new ArrayList<>();
        ArrayList<InstructorAccount> instructors = new ArrayList<>();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot each:snapshot.getChildren()){
                    StudentAccount account = each.getValue(StudentAccount.class);
                    //need testing
                    //if(account.getType().equals("student"))students.add((StudentAccount) account);
                    //else if(account.getType().equals("instructor"))instructors.add((InstructorAccount) account);
                    accounts.add(account);
                }
                if(accounts.contains(new StudentAccount(userName,password))){
                    Intent intent = new Intent(MainActivity.this,DisplayMessageActivity.class);
                    String message = "Welcome " + userName + " ,you have logged in as student";
                    intent.putExtra(EXTRA_MESSAGE,message);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }







        /*
        private void setClickListeners(){
            login.setOnClickListener(this);
            register.setOnClickListener(this);
        }

        public void onClick(View v){
            switch (v.getId()){
                case R.id.Login:
                    if (TextUtils.isEmpty(username.toString()) && TextUtils.isEmpty(password.toString()))
                        Toast.makeText(MainActivity.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                    if(username.getText().toString().trim().equals("Admin") && password.getText().toString().trim().equals("admin123")){
                        Intent intent = new Intent(this,DisplayMessageActivity.class);
                        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
                        String message = "Welcome " + username.getText().toString() + " You have logged in as admin";
                        intent.putExtra(EXTRA_MESSAGE,message);
                        startActivity(intent);
                    }
                    break;
                case R.id.idBtnRegister:
                    Intent intent = new Intent(this,MainActivity2.class);
                    startActivity(intent);
                    break;
                }
        }
        */
}

