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
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Integer id) {
        return studentService.getStudent(id);
    }

    @PostMapping
    public ResponseEntity<String> addStudent(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String email,
            @RequestParam int classLevel) {
        Student student = new Student(id, name, age, email, classLevel);
        studentService.addStudent(student);

        return ResponseEntity.ok("Student created successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        boolean isDeleted = studentService.deleteStudent(id);
        if(isDeleted)
            return ResponseEntity.ok("Student deleted successfully");
        else
            return ResponseEntity.ok("Student not found");
    }


    @PatchMapping("/{id}/{newId}/{newName}/{newAge}/{newEmail}/{newClassLevel}")
    public ResponseEntity<String> updateStudent(@PathVariable Integer id,
                                                @PathVariable Integer newId,
                                                @PathVariable String newName,
                                                @PathVariable Integer newAge,
                                                @PathVariable String newEmail,
                                                @PathVariable Integer newClassLevel) {
        Student student = new Student(newId, newName, newAge, newEmail, newClassLevel);
        boolean isUpdated = studentService.updateStudent(id, student);
        if (isUpdated)
            return ResponseEntity.ok("Student updated successfully");
        else
            return ResponseEntity.ok("Student not found");
    }
}
