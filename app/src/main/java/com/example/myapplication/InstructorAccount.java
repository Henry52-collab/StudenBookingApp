package com.example.myapplication;

import androidx.annotation.NonNull;

import com.google.android.gms.common.AccountPicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Instructor functionality for deliverable2.
 * */
public class InstructorAccount extends Account {
        public Instructor instructor;
        public static final String TYPE = "instructor";
        public InstructorAccount(){}
        public InstructorAccount(String username,String password){instructor = new Instructor(username,password);}
        public String getInstructorName(){return instructor.getName();}
        public String getInstructorPassword(){return instructor.getPassword();}
        public void setInstructorName(String name){instructor.setName(name);}
        public void setInstructorPassword(String password){instructor.setPassword(password);}
}
