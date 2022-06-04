package com.example.myapplication;

import java.util.ArrayList;

public class CourseController {


    private Course course;
    public CourseController(Course course){
        this.course = course;
    }

    public void setCourseName(String name){
        course.setName(name);
    }

    public void setCourseCode(String code){
        course.setCode(code);
    }

    public String getCourseCode(){
        return course.getCode();
    }

    public String getCourseName(){
        return course.getName();
    }

}
