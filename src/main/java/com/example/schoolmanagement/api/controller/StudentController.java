package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@RequestParam Integer id){
        return studentService.getStudent(id);
    }

    @PostMapping
    public String addStudent(@RequestBody Student student){
        studentService.addStudent(student);
        return "Student added successfully!";
    }
}
