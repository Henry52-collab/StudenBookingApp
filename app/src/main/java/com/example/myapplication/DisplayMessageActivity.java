package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    private Button logoutBtn;
    private TextView usernameTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        logoutBtn = (Button) findViewById(R.id.BtnLogout);
        usernameTV = findViewById(R.id.idTVUserName);
        String name = getIntent().getStringExtra("username");
        usernameTV.setText(name);

        logoutBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               Intent i = new Intent(DisplayMessageActivity.this,MainActivity.class);
               startActivity(i);
               finish();
            }
        });
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }



}