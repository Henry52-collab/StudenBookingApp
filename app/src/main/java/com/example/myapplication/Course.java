package com.example.myapplication;

/**
 * This is the course class
 * */
public class Course {

    private String code;
    private String name;
    //constructor
    public Course(String code,String name){
        this.code = code;
        this.name = name;
    }

    public Course(){}
    //getters and setters
    public String getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setName(String name){
        this.name = name;
    }

    /**
     * return a string representation of the course
     * */
    public String toString(){
        return "CourseCode: " + code + "/n" + "CourseName: " + name;
    }

    /**
     * Check if the courses are equal
     * @param o the other course
     * */
    public boolean equals(Object o){
        Course c = (Course)o;
        return c.name.equals(this.name) && c.code.equals(this.code);
    }


}
