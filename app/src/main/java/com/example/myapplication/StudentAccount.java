package com.example.myapplication;

/**
 * Student functionality for deliverable 3.
 * */
public class StudentAccount extends Account{
    private Student student;
    String name;
    String password;

    public static final String TYPE = "student";
    public StudentAccount(){}
    public StudentAccount(String name,String password){
        student = new Student(name,password);
    }
    public void setStudentName(String name){
        student.setName(name);
    }
    public void setStudentPassword(String password){
        student.setPassword(password);
    }
    public String getStudentPassword(){
        return student.getPassword();
    }
    public String getStudentName(){
        return student.getName();
    }
    public boolean equals(Object o){
        StudentAccount other = (StudentAccount)o;
        if(this.getStudentName().equals(other.getStudentName()) && this.getStudentName().equals(other.getStudentName())) return true;
        return false;
    }
}
