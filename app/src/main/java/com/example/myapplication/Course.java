package com.example.myapplication;

public class Course {
    private String code;
    private String name;
    public Course(String code,String name){
        this.code = code;
        this.name = name;
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

    public String toString(){
        return "CourseCode: " + code + "/n" + "CourseName: " + name;
    }

    public boolean equals(Object o){
        Course c = (Course)o;
        return c.name.equals(this.name) && c.code.equals(this.code);
    }


}
