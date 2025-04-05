package com.example.schoolmanagement.model;

public class Student extends Person {

    private int ID;
    private String name;
    private int age;
    private String email;
    private int classLevel;



    public Student(){
        super();
        this.setClassLevel(0);
    }

    public Student(int ID, String name, int age, String email, int classLevel) {
        super(ID, name, age, email);
        this.classLevel = classLevel;
    }

    public int getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(int classLevel) {
        this.classLevel = classLevel;
    }

}
