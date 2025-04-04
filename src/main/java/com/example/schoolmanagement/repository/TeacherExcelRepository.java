package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeacherExcelRepository {

    private final ExcelHelper excelHelper;

    public TeacherExcelRepository(ExcelHelper excelHelper) {
        this.excelHelper = excelHelper;
    }

    public List<Teacher> getAllTeachers() {
        return excelHelper.readTeachers();
    }

    public void saveAllTeachers(List<Teacher> teachers) {
        excelHelper.writeTeachers(teachers);
    }

    public Teacher getTeacherById(Integer id) {
        return excelHelper.readTeacherById(id);
    }
}