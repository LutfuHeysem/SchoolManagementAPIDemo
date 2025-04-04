package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getTeachers(){
        return teacherService.getTeachers();
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@RequestParam Integer id){
        return teacherService.getTeacher(id);
    }

    @PostMapping
    public String addTeacher(@RequestBody Teacher teacher){
        teacherService.addTeacher(teacher);
        return "Teacher added successfully!";
    }
}
