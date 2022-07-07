package com.example.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeCheckerTest {

    @Test
    public void testTimeConflictExists(){
        String a = "08:00";
        String b = "11:00";
        String c = "14:00";
        String d = "15:00";
        assertFalse("wrong",TimeChecker.timeConflictExists(a,b,c,d));
    }


    @Test
    public void testTimeConflictExists1(){
        String a = "08:00";
        String b = "11:00";
        String c = "09:00";
        String d = "15:00";
        assertTrue("wrong",TimeChecker.timeConflictExists(a,b,c,d));
    }

    @Test
    public void testEndIsAfter(){
        String a = "08:00";
        String b = "11:00";
        assertTrue("wrong",TimeChecker.endIsAfter(a,b));
    }


    @Test
    public void testEndIsAfter1(){
        String a = "14:00";
        String b = "11:00";
        assertFalse("wrong",TimeChecker.endIsAfter(a,b));
    }

    @Test
    public void testIsValidFormat(){
        String a = "14:00";
        assertTrue("wrong",TimeChecker.isValidFormat(a));
    }

    @Test
    public void testIsValidFormat1(){
        String a = "llllll";
        assertFalse("wrong",TimeChecker.isValidFormat(a));
    }


}