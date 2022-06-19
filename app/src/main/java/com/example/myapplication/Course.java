package com.example.myapplication;

/**
 * This is the course class
 * */
public class Course {

    private String code;
    private String name;
    private String instructor;
    private String description;
    private int hour;
    private int day;
    private int capacity;
    //constructor
    public Course(String code,String name){
        this.code = code;
        this.name = name;
        this.instructor = "";
        this.description = "";
        this.hour = 100;
        this.day = 100;
        this.capacity = 100;
    }

    public Course(){}

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Course(String code, String name, String instructor, int day,int capacity,int hour,String description){
        this.code = code;
        this.name = name;
        this.instructor = instructor;
        this.description = description;
        this.capacity = capacity;
        this.hour = hour;
        this.day = day;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

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
