package com.example.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class CourseTest {

    @Test
    public void testAddStudent(){
        Course a = new Course("seg2105", "intro to software engineering");
        String student = "Alex";
        String expected = " Alex";
        String actual = a.addStudent(student);
        assertEquals(expected,actual);
    }

    @Test
    public void testAddStudent1(){
        Course a = new Course("seg2105", "intro to software engineering");
        String students = " Alex Jay Eric";
        a.setStudents(students);
        String student = "Fahmi";
        String expected = " Alex Jay Eric Fahmi";
        String actual = a.addStudent(student);
        assertEquals(expected,actual);
    }

    @Test
    public void testAddStudent2(){
        Course a = new Course("seg2105", "intro to software engineering");
        String students = " Alex Jay Eric";
        a.setStudents(students);
        String student = "Jay";
        String expected = "false";
        String actual = a.addStudent(student);
        assertEquals(expected,actual);
    }

    @Test
    public void testDeleteStudent(){
        Course a = new Course("seg2105", "intro to software engineering");
        String students = "";
        a.setStudents(students);
        String student = "Jay";
        String expected = "false";
        String actual = a.deleteStudent(student);
        assertEquals(expected,actual);
    }


    @Test
    public void testDeleteStudent1(){
        Course a = new Course("seg2105", "intro to software engineering");
        String students = " Alex Jay Eric";
        a.setStudents(students);
        String student = "Jay";
        String expected = " Alex Eric ";
        String actual = a.deleteStudent(student);
        assertEquals(expected,actual);
    }

}