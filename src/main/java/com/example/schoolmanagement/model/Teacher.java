package com.example.schoolmanagement.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

    private boolean isOnLeave;
    private int ID;
    private String name;
    private int age;
    private String email;
    private List<Integer> assignedClasses;

    public Teacher(){
        super();
        this.setOnLeave(false);
        List<Integer> assignedClasses = new ArrayList<>();
        this.setAssignedClasses(assignedClasses);
    }

    public Teacher(int ID, String name, int age, String email) {
        super(ID, name, age, email);
        this.assignedClasses = new ArrayList<>();
        this.isOnLeave = false;
    }

    public boolean isOnLeave(){
        return this.isOnLeave;
    }

    public void setOnLeave(boolean leave){
        this.isOnLeave = leave;
    }

    public void setAssignedClasses(List<Integer> classes){
        this.assignedClasses = classes;
    }

    public List<Integer> getAssignedClasses(){
        return this.assignedClasses;
    }

}
