package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final RuntimeService runtimeService;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(RuntimeService runtimeService,
                             StudentRepository studentRepository) {
        this.runtimeService = runtimeService;
        this.studentRepository = studentRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<String> addStudent(
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String email,
            @RequestParam int classLevel) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("operationType", "CREATE_STUDENT");
        variables.put("studentName", name);
        variables.put("studentAge", age);
        variables.put("studentEmail", email);
        variables.put("studentClassLevel", classLevel);

        runtimeService.startProcessInstanceByKey("approvalProcess", variables);

        return ResponseEntity.ok("Student creation request submitted for approval");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("operationType", "DELETE_STUDENT");
        variables.put("studentId", id);

        runtimeService.startProcessInstanceByKey("approvalProcess", variables);

        return ResponseEntity.ok("Student deletion request submitted for approval");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateStudent(
            @PathVariable Integer id,
            @RequestParam String newName,
            @RequestParam Integer newAge,
            @RequestParam String newEmail,
            @RequestParam Integer newClassLevel) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("operationType", "UPDATE_STUDENT");
        variables.put("studentId", id);
        variables.put("studentName", newName);
        variables.put("studentAge", newAge);
        variables.put("studentEmail", newEmail);
        variables.put("studentClassLevel", newClassLevel);

        runtimeService.startProcessInstanceByKey("approvalProcess", variables);

        return ResponseEntity.ok("Student update request submitted for approval");
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Integer id) {
        return studentRepository.findById(id).get();
    }

}

