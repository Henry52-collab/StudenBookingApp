package com.example.myapplication;

import java.util.ArrayList;


/**
 * Admin functionality for deliverable 1.
 * */

//Test when all the activities are finished. Feel free to provide your own implementation.
public class AdminAccount extends Account
{
    //List of all the courses
    private ArrayList<Course> courses;
    private Admin admin;
    private CourseController c;
    public static final String TYPE = "admin";


    //constructors
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




    //Getters and setters
    public String getAdminName(){return admin.getUserName();}
    public String getAdminPassword(){return admin.getPassword();}
    public void setAdminName(String name){admin.setUserName(name);}
    public void setAdminPassword(String password){admin.setPassword(password);}

    public void AddCourse(Course course){
        if(courses.contains(course))
            return;
        courses.add(course);
    }

    /**
     * Method for getting the number of courses
     */
    public int size(){
        return courses.size();
    }

    /**
     * This method deletes the coruse by name
     * @param name name of the course to be deleted
     * */
    public void deleteCourseByName(String name){
        for(Course each:courses){
            if(each.getName().equals(name))
                courses.remove(each);
        }
    }

    /**
     * This course delete the course by code
     * @param code code of the course
     * */
    public void deleteCourseByCode(String code){
        for(Course each : courses){
            if(each.getCode().equals(code)){
                courses.remove(each);
            }
        }
    }

    /**
     * This method creates a new course
     * @param name name of the course
     * @param code code of the course
     * */
    public Course createCourse(String name,String code){
        return new Course(name,code);
    }

    /**
     * This method deletes an instructor from the database
     * @param name name of the instructor
     * */
    public void deleteInstructor(String name){
        //implement
    }

    /**
     * This methhod deletes a student from the database
     * @param name name of the student
     * */
    public void deleteStudent(String name){
        //implement
    }

    /**
     * This method edit a course code
     * @param code Old code of the course
     * @param newCode new code of the course
     * */

    public void editCode(String code, String newCode){
        //Implement
    }

    /**
     * THis method edit a course name
     * @param name old name
     * @param newName new name
     * */
    public void editName(String name,String newName){
        //Implement
    }
}
