package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TimeChecker {
    /**
     * Checks if a time conflict exists between start-end and t1-t2.
     * @param start is the start time
     * @param end is the end time
     * @param t1 is the other start time
     * @param t2 is the other end time
     * @return true if a time conflict exists, and false otherwise.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean timeConflictExists(String start, String end, String t1, String t2) {

        /* Ensure that all times are in a valid format for LocalTime */
        if (start.length() == 4 && start.charAt(1) == ':') {
            start = "0" + start;
        }

        if (end.length() == 4 && end.charAt(1) == ':') {
            end = "0" + end;
        }

        if (t1.length() == 4 && t1.charAt(1) == ':') {
            t1 = "0" + t1;
        }

        if (t2.length() == 4 && t2.charAt(1) == ':') {
            t2 = "0" + t2;
        }

        /* Parse the times */
        LocalTime s1 = LocalTime.parse(start);
        LocalTime s2 = LocalTime.parse(end);
        LocalTime s3 = LocalTime.parse(t1);
        LocalTime s4 = LocalTime.parse(t2);

        /* Check that s3 is not between s1 and s2 */
        if (s3.compareTo(s1) >= 0 && s3.compareTo(s2) <= 0) {
            return true;
        }

        /* Check that s4 is not between s1 and s2 */
        if (s4.compareTo(s1) >= 0 && s4.compareTo(s2) <= 0) {
            return true;
        }

        return false;
    }


    /**
     * Checks if end is after start.
     * @param start is the start time
     * @param end is the end time
     * @return true if end is after, and false otherwise.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean endIsAfter(String start, String end) {

        if (start.length() == 4 && start.charAt(1) == ':') {
            start = "0" + start;
        }

        if (end.length() == 4 && end.charAt(1) == ':') {
            end = "0" + end;
        }

        LocalTime t1 = LocalTime.parse(start);
        LocalTime t2 = LocalTime.parse(end);

        if (t2.compareTo(t1) <= 0) { // end time is before start time
            return false;
        } else {
            return true;
        }

    }
    /**
     * Checks if time is a valid format by attempting to parse it into LocalTime.
     * @param time is the String whose format needs to be checked
     * @return true if successful, and false otherwise
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isValidFormat(String time) {

        try {
            if (time.length() == 4 && time.charAt(1) == ':') {
                time = "0" + time;
            }
            LocalTime.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
