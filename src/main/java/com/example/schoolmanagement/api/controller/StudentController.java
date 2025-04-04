package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{ID}")
    public Student getStudent(@PathVariable Integer ID) {
        return studentService.getStudent(ID);
    }

    @PostMapping
    public ResponseEntity<String> addStudent(
            @RequestParam int ID,
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String email,
            @RequestParam int classLevel) {
        Student student = new Student(ID, name, age, email, classLevel);
        studentService.addStudent(student);

        return ResponseEntity.ok("Student created successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam Integer ID) {
        studentService.deleteStudent(ID);
        return ResponseEntity.ok("Student deleted successfully");
    }

}
