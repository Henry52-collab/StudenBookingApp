package com.example.myapplication;

import java.util.ArrayList;

/**
 * Course controller class for a course as to uphold the MVC design principle. Provides control to a course instance
 * */
public class CourseController {


    private Course course;
    public CourseController(Course course){
        this.course = course;
    }

    /**
     * This method sets a course name
     * @param name new name
     * */
    public void setCourseName(String name){
        course.setName(name);
    }

    /**
     * This method sets a course code
     * @param code new code
     * */
    public void setCourseCode(String code){
        course.setCode(code);
    }

    /**
     * This method gets the Courscode
     * */
    public String getCourseCode(){
        return course.getCode();
    }

    /**
     * This method gets the course name
     * */
    public String getCourseName(){
        return course.getName();
    }

}
