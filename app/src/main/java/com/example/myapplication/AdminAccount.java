package com.example.myapplication;

import java.util.ArrayList;

public class AdminAccount extends Account
{
    private ArrayList<Course> courses;
    private Admin admin;
    private CourseController c;

    public AdminAccount(){
        courses = new ArrayList<>();
        admin = new Admin("admin", "admin123");
        user = "Admin";

    }

    public AdminAccount(String name,String password){
        courses = new ArrayList<>();
        admin = new Admin(name,password);
        user = "Admin";
    }

    public AdminAccount(String name,String password,String cName,String code){
        courses = new ArrayList<>();
        admin = new Admin(name,password);
        c = new CourseController(new Course(cName,code));
        user = "Admin";
    }
    public int size(){
        return courses.size();
    }

    public void AddCourse(Course course){
        if(courses.contains(course))
            return;
        courses.add(course);

    }

    public void deleteCourseByName(String name){
        for(Course each:courses){
            if(each.getName().equals(name))
                courses.remove(each);
        }
    }

    public void deleteCourseByCode(String code){
        for(Course each : courses){
            if(each.getCode().equals(code)){
                courses.remove(each);
            }
        }
    }

    public Course createCourse(String name,String code){
        return new Course(name,code);
    }
}
