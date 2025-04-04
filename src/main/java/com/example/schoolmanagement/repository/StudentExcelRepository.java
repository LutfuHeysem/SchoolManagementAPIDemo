package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentExcelRepository {

    private final ExcelHelper excelHelper;

    public StudentExcelRepository(ExcelHelper excelHelper) {
        this.excelHelper = excelHelper;
    }

    public List<Student> getAllStudents() {
        return excelHelper.readStudents();
    }

    public void saveAllStudents(List<Student> students) {
        excelHelper.writeStudents(students);
    }

    public Student getStudentById(Integer id) {
        return excelHelper.readStudentById(id);
    }

    public void deleteStudentById(Integer id) {
        excelHelper.deleteStudentById(id);
    }
}