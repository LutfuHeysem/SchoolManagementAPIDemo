package com.example.schoolmanagement.model;

public class Personnel extends Person {

    private int ID;
    private String name;
    private int age;
    private String email;
    private boolean isOnLeave;


    public Personnel(){
        super();
        this.setOnLeave(false);
    }

    public Personnel(int ID, String name, int age, String email) {
        super(ID, name, age, email);
        this.isOnLeave = false;
    }

    public boolean isOnLeave(){
        return this.isOnLeave;
    }

    public void setOnLeave(boolean leave){
        this.isOnLeave = leave;
    }

}
