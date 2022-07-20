package com.example.myapplication;


import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A class for student
 * */
public class Student extends User{
    public Student(){}
    public Student(String username,String password){
        this.name = username;
        this.password = password;
    }


    /**
     * Method for enrolling a student to the selected course.
     * @param key key of the selected course
     * @param name name of the student
     * */
    public static void enroll(String key, String name){
        DatabaseReference courses = FirebaseDatabase.getInstance().getReference().child("courses");

        courses.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Course course = snapshot.getValue(Course.class);

                courses.child(key).setValue(new Course(course.getCode(),course.getName(),course.getInstructor(),course.getDay(),course.getCapacity(),course.getHour(),course.getDescription(), course.addStudent(name)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Method for unenrolling a student from the selected course.
     * @param key key of the selected course
     * @param name name of the student
     */
    public static void unenroll(String key,String name) {
        DatabaseReference courses = FirebaseDatabase.getInstance().getReference().child("courses");
        courses.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Course course = snapshot.getValue(Course.class);
                String s = course.deleteStudent(name);
                if(s != "false")
                    courses.child(key).setValue(new Course(course.getCode(),course.getName(),course.getInstructor(), course.getDay(),course.getCapacity(), course.getHour(),course.getDescription(),course.deleteStudent(name)));
                else
                    courses.child(key).setValue(new Course(course.getCode(),course.getName(),course.getInstructor(), course.getDay(),course.getCapacity(), course.getHour(),course.getDescription(),course.getStudents()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Checks if a time conflict exists within a student's schedule
     * @param course The course this student is trying to enroll
     * @param name Name of this student
     * @return true if no time conflict, false otherwise.
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean checkConflict(Course course, String name,ArrayList<Course> courses){
        //getting the course hour for course
        String hour = course.getHour();
        String[] time = hour.split(" ");
        String day = course.getDay();
        String[] time1 = time[0].split("-");
        String[] time2 = time[1].split("-");
        //start times and end times for the course the student is trying to enroll
        String startTime1 = time1[0];
        String endTime1 = time1[1];
        String startTime2 = time2[0];
        String endTime2 = time2[1];
        for(Course each:courses){
            String[] eachTime = each.getHour().split(" ");
            String[] eachDay = each.getDay().split(" ");
            //Only check time conflict if courses are on the same day
            if(day.contains(eachDay[0]) || day.contains(eachDay[1])){
               String[] t1 = eachTime[0].split("-");
               String[] t2 = eachTime[1].split("-");
               //start times and end times for each course the student is currently enrolled in.
               String s1 = t1[0];
               String e1 = t1[1];
               String s2 = t2[0];
               String e2 = t2[1];
               if(TimeChecker.timeConflictExists(startTime1,endTime1,s1,e1))return false;
               if(TimeChecker.timeConflictExists(startTime2,endTime2,s2,e2))return false;
            }
        }
        return true;
    }
}
