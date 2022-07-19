package com.example.myapplication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnrollmentChecker {
    /**
     * Returns true if the student is enrolled in the course,
     * and false otherwise.
     *
     * @param course is the course that the student is or is not enrolled in
     * @param username is the username of the student
     * @return true if student is enrolled, and false otherwise
     */
    public static boolean isEnrolled(Course course, String username) {
        /* Get all students from the course */
        String str = course.getStudents();

        if (str.isEmpty()) { // no students enrolled in this course
            return false;
        }

        /* Turn String to String[] */
        String[] students = str.split(" ");

        /* Traverse array to find username */
        for (int i = 0; i < students.length; i++) {
            if (students[i].equals(username)) {
                return true; // username found; thus, student is enrolled
            }
        }

        return false; // username not found; thus, student is not enrolled
    }
}
