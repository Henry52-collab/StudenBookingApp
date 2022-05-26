package com.example.myapplication;

public class StudentAccount extends Account{
    private Student student;
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
}
