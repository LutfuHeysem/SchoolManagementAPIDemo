package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentExcelRepository {

    private final StudentExcelHelper studentExcelHelper;

    public StudentExcelRepository(StudentExcelHelper studentExcelHelper) {
        this.studentExcelHelper = studentExcelHelper;
    }

    public List<Student> getAllStudents() {
        return studentExcelHelper.readStudents();
    }

    public void saveAllStudents(List<Student> students) {
        studentExcelHelper.writeStudents(students);
    }

    public Student getStudentById(Integer id) {
        return studentExcelHelper.readStudentById(id);
    }

    public void deleteStudentById(Integer id) {
        studentExcelHelper.deleteStudentById(id);
    }

    public void updateStudentById(Integer id, Student student) {
        studentExcelHelper.updateStudentById(id, student);
    }
}