package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * This is the backend for activity_search_course.xml. When the user clicks on search course, it takes them here.
 * */
public class InstructorSearchCourse extends AppCompatActivity {

    private EditText identify;
    private Button backBtn;
    private ListView courseViewer;
    private DatabaseReference db;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private ArrayList<String> popupContent = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        //Initialize attributes
        backBtn = (Button) findViewById(R.id.backBtn);
        db = FirebaseDatabase.getInstance().getReference("courses");
        courseViewer = (ListView) findViewById(R.id.courseList);
        identify = (EditText) findViewById(R.id.courseIdentifier);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        courseViewer.setAdapter(adapter);

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(Course.class).toSearch();
                String popupValue = snapshot.getValue(Course.class).getDetail();
                arrayList.add(value);
                popupContent.add(popupValue);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        identify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = s.toString().replaceAll("\\s","");
                adapter.getFilter().filter(temp);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do Nothing
            }
        });
        courseViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = courseViewer.getItemAtPosition(position);
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window, null);
                TextView courseInfo = popupView.findViewById(R.id.courseInfo);
                String holder = (String) courseViewer.getItemAtPosition(position);
                int temp = arrayList.indexOf(holder);
                holder = popupContent.get(temp);
                courseInfo.setText(holder);
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}