package com.example.myapplication;

import java.util.ArrayList;

/**
 * This is the course class
 * */
public class Course {

    private String code;
    private String name;
    private String instructor;
    private String description;
    private String hours;
    private String days;
    private String students;
    private int capacity;

    public Course() {
        // default constructor; do not remove as this is needed for
        // the getValue method.
    }

    //constructor
    public Course(String code,String name){
        this.code = code;
        this.name = name;
        this.instructor = "";
        this.description = "";
        this.hours = "";
        this.days = "";
        this.capacity = 100;
        this.students = "";
    }

    public Course(String code, String name, String instructor, String days,int capacity, String hours,String description,String students){
        this.code = code;
        this.name = name;
        this.instructor = instructor;
        this.description = description;
        this.capacity = capacity;
        this.hours = hours;
        this.days = days;
        this.students = students;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getHour() {
        return hours;
    }

    public void setHour(String hours) {
        this.hours = hours;
    }

    public String getDay() {
        return days;
    }

    public void setDay(String days) {
        this.days = days;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStudents() {
        return this.students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

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
     * Add a student to the string of students
     * @param name Name of the student
     * @return String of students with the new student added
     * */
    public String addStudent(String name){
        if(!students.contains(name))
            return students + " " + name;
        return "false";
    }

    public String toSearch() {
        return name + "\n" + "Course Code: " + code + "\n" + "Instructor: " + instructor + "\n" + "Days: " + days;
    }

    /**
     * Delete a student from the string of students
     * @param name name of the student
     * @return The string of students with the student removed
     * */
    public String deleteStudent(String name){
        if(!students.contains(name))
            return "false";
        String[] s = students.split(" ");
        String result = "";
        for(int x = 0;x < s.length;x++){
            if(s[x].equals(name)) {
                s[x] = " ";
                continue;

            }
            else result += s[x] + " ";
        }
        return result;
    }

    /**
     * return a string representation of the course
     * */
    public String toString(){
        return "\nCourse Code: " + code + "\n" + "Course Name: " + name + "\n" + "Instructor: " + instructor + "\n";
    }

    /**
     * return the details of this course
     * @return details of this course
     * */
    public String getDetail() {

        StringBuilder info = new StringBuilder();
        info.append("Course code: " + code + "\nCourse Name: " + name + "\nInstructor: " + instructor + "\nTimes: ");

        if (!days.equals("")) {
            String[] arr1 = days.split(" ", 2);
            String[] arr2 = hours.split(" ", 2);
            info.append(arr1[0] + ", " + arr2[0]);

            if (arr1.length > 1 && !arr1[1].equals("")) {
                info.append("; " + arr1[1] + ", " + arr2[1]);
            }
        }

        info.append("\nCapacity: " + capacity + "\nCourse Description: " + description);

        return info.toString();
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
