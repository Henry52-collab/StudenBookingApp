package com.example.myapplication;

import com.google.android.gms.common.AccountPicker;

/**
 * Instructor functionality for deliverable2.
 * */
public class InstructorAccount extends Account {
        private Instructor instructor;
        public static final String TYPE = "instructor";
        public InstructorAccount(String username,String password){instructor = new Instructor(username,password);}
        public String getInstructorName(){return instructor.getName();}
        public String getInstructorPassword(){return instructor.getPassword();}
        public void setInstructorName(String name){instructor.setName(name);}
        public void setInstructorPassword(String password){instructor.setPassword(password);}








}
