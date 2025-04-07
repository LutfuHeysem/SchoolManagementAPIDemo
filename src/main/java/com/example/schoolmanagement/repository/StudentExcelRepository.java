package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Student;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
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

    public boolean deleteStudentById(Integer id) {
        return excelHelper.deleteStudentById(id);
    }

    public boolean updateStudentById(Integer id) {
        return excelHelper.updateStudentById(id);
    }
}