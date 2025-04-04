package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{ID}")
    public Teacher getTeacher(@PathVariable Integer ID){
        return teacherService.getTeacher(ID);
    }

    @PostMapping
    public ResponseEntity<String> addTeacher(
            @RequestParam int ID,
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String email) {
        Teacher teacher = new Teacher(ID, name, age, email);
        teacherService.addTeacher(teacher);

        return ResponseEntity.ok("Teacher created successfully");
    }
}
