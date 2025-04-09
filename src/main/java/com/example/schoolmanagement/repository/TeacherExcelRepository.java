package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeacherExcelRepository {

    private final TeacherExcelHelper teacherExcelHelper;

    public TeacherExcelRepository(TeacherExcelHelper teacherExcelHelper) {
        this.teacherExcelHelper = teacherExcelHelper;
    }

    public List<Teacher> getAllTeachers(){
        return teacherExcelHelper.readTeachers();
    }

    public void saveAllTeachers(List<Teacher> teachers){
        teacherExcelHelper.writeTeachers(teachers);
    }

    public Teacher getTeacherById(Integer id){
        return teacherExcelHelper.readTeacherById(id);
    }

    public void deleteTeacher(Integer id){
        teacherExcelHelper.deleteTeacherById(id);
    }
}
