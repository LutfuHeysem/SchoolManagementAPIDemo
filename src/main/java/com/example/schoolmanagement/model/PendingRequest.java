package com.example.schoolmanagement.model;

public class PendingRequest {
    private String operationType;
    private Student student;
    private Student updatedStudent;
    private Teacher teacher;
    private int askingLeaveDays;

    public PendingRequest(){
        this.setOperationType("");
        this.setStudent(new Student());
        this.setUpdatedStudent(null);
        this.setTeacher(null);
    }

    public PendingRequest(String operationType, Student student) {
        this.setOperationType(operationType);
        this.setStudent(student);
        this.setUpdatedStudent(null);
        this.setTeacher(null);
    }

    public PendingRequest(String operationType, Student student, Student updatedStudent) {
        this.setOperationType(operationType);
        this.setStudent(student);
        this.setUpdatedStudent(updatedStudent);
        this.setTeacher(null);
    }

    public PendingRequest(String operationType, Teacher teacher) {
        this.setOperationType(operationType);
        this.setStudent(null);
        this.setUpdatedStudent(null);
        this.teacher = teacher;
    }

    public PendingRequest(String operationType, Teacher teacher, int askingLeaveDays) {
        this.setOperationType(operationType);
        this.setStudent(null);
        this.setUpdatedStudent(null);
        this.teacher = teacher;
        this.askingLeaveDays = askingLeaveDays;
    }

    // Getters and Setters
    public String getOperationType() { return operationType; }
    public void setOperationType(String operationType) { this.operationType = operationType; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Student getUpdatedStudent() { return updatedStudent; }
    public void setUpdatedStudent(Student updatedStudent) { this.updatedStudent = updatedStudent; }
    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    public int getAskingLeaveDays() { return askingLeaveDays; }
    public void setAskingLeaveDays(int askingLeaveDays) { this.askingLeaveDays = askingLeaveDays; }
}