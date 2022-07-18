package com.example.myapplication;


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
     * @string key of the selected course
     * @String name of the student
     * */
    public static void enroll(String key, String name){

    }

    /**
     * Method for unenrolling a student from the selected course.
     * @string key of the selected course
     * @String name of the student
     */
    public static void unenroll(String key) {

    }

    /**
     *
     */

}
