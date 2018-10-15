package com.inonity;

/**
 * @author ruhul
 */

public class Student {
    int id;
    String name;
    String subject;
    Double marks;

    public Student(int id, String test) {
        this.id = id;
        this.name = test;
    }

    public Student(int id,String subject,String name) {
        this.id = id;
        this.name = name;
        this.subject = subject;
    }

    public Student(String name, String subject, Double marks) {
        this.name = name;
        this.subject = subject;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }
}
