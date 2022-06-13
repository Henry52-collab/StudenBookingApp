package com.example.myapplication;

/**
 * This is the course class
 * */
public class Course {

    public String code; // needed to make these public as well
    public String name;

    public Course() {
        // default constructor; do not remove as this is needed for
        // the getValue method.
    }

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
