package com.example.schoolmanagement.model;

public class PendingRequest {
    private String operationType;
    private Student student;
    private Student updatedStudent;

    public PendingRequest(){
        this.setOperationType("");
        this.setStudent(new Student());
        this.setUpdatedStudent(null);
    }

    public PendingRequest(String operationType, Student student) {
        this.operationType = operationType;
        this.student = student;
        this.updatedStudent = null;
    }

    public PendingRequest(String operationType, Student student, Student updatedStudent) {
        this.operationType = operationType;
        this.student = student;
        this.updatedStudent = updatedStudent;
    }

    // Getters and Setters
    public String getOperationType() { return operationType; }
    public void setOperationType(String operationType) { this.operationType = operationType; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Student getUpdatedStudent() { return updatedStudent; }
    public void setUpdatedStudent(Student updatedStudent) { this.updatedStudent = updatedStudent; }
}