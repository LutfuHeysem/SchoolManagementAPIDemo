package com.example.schoolmanagement.model;

public abstract class Person {

    private int ID;
    private String name;
    private int age;
    private String email;

    public Person(){
        this.age = 0;
        this.name = "";
        this.ID = 0;
        this.email = "";
    }

    public Person(int ID, String name, int age, String email){
        this.age = age;
        this.name = name;
        this.ID = ID;
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }
}
