package com.example.schoolmanagement.model;

public class Teacher {
    private int id;
    private String name;
    private int age;
    private String email;
    private int assignedClass;
    private boolean isOnLeave;
    private int remainingLeaveQuota;
    private int currentLeaveDays;

    public Teacher(){
        this.setId(0);
        this.setName("");
        this.setAge(0);
        this.setEmail("");
        this.setAssignedClass(0);
        this.setOnLeave(false);
        this.setRemainingLeaveQuota(15);
        this.setCurrentLeaveDays(0);
    }

    public Teacher(int id, String name, int age, String email, int assignedClass) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.assignedClass = assignedClass;
        this.isOnLeave = false;
        this.remainingLeaveQuota = 15;
        this.currentLeaveDays = 0;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAssignedClass() {
        return assignedClass;
    }

    public void setAssignedClass(int assignedClass) {
        this.assignedClass = assignedClass;
    }

    public boolean isOnLeave() {
        return this.isOnLeave;
    }

    public void setOnLeave(boolean isOnLeave) {
        this.isOnLeave = isOnLeave;
    }

    public int getRemainingLeaveQuota() {return this.remainingLeaveQuota;}

    public void setRemainingLeaveQuota(int remainingLeaveQuota) {this.remainingLeaveQuota = remainingLeaveQuota;}

    public int getCurrentLeaveDays() {return this.currentLeaveDays;}

    public void setCurrentLeaveDays(int currentLeaveDays) {this.currentLeaveDays = currentLeaveDays;}

}
