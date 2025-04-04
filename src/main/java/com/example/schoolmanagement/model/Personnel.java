package com.example.schoolmanagement.model;

public class Personnel extends Person {

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
