package com.example.schoolmanagement.model;

public class Manager {
    private String username;
    private String password;

    public Manager(){
        this.setUsername("");
        this.setPassword("");
    }

    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
