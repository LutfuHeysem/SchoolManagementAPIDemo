package com.example.schoolmanagement.service;

import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.repository.TeacherExcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherExcelRepository teacherExcelRepository;

    public TeacherService(TeacherExcelRepository teacherExcelRepository) {
        this.teacherExcelRepository = teacherExcelRepository;
    }

    public List<Teacher> getTeachers() {
        return teacherExcelRepository.getAllTeachers();
    }

    public Teacher getTeacher(Integer id) {
        return teacherExcelRepository.getTeacherById(id);
    }

    public void addTeacher(Teacher teacher) {
        List<Teacher> teachers = teacherExcelRepository.getAllTeachers();
        teachers.add(teacher);
        teacherExcelRepository.saveAllTeachers(teachers);
    }

    public void deleteTeacher(Integer id) {
        teacherExcelRepository.deleteTeacher(id);
    }

}
