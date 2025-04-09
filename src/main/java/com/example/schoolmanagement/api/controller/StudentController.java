package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.PendingRequest;
import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.service.PendingRequestService;
import com.example.schoolmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final PendingRequestService pendingRequestService;

    @Autowired
    public StudentController(StudentService studentService,
                             PendingRequestService pendingRequestService) {
        this.studentService = studentService;
        this.pendingRequestService = pendingRequestService;
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
//        studentService.addStudent(student);

        String requestId = pendingRequestService.createRequest(new PendingRequest("CREATE", student));

        return ResponseEntity.ok("Request ID: " + requestId + ". Awaiting manager approval.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        Student student = studentService.getStudent(id);
        if(student == null)
            return ResponseEntity.ok("Student not found");

        String requestId = pendingRequestService.createRequest(new PendingRequest("DELETE", student));

        return ResponseEntity.ok("Request ID: " + requestId + ". Awaiting manager approval.");
    }


    @PatchMapping("/{id}/{newId}/{newName}/{newAge}/{newEmail}/{newClassLevel}")
    public ResponseEntity<String> updateStudent(@PathVariable Integer id,
                                                @PathVariable Integer newId,
                                                @PathVariable String newName,
                                                @PathVariable Integer newAge,
                                                @PathVariable String newEmail,
                                                @PathVariable Integer newClassLevel) {
        Student checkStudent = studentService.getStudent(id);
        if(checkStudent == null)
            return ResponseEntity.ok("Student not found");
        Student student = new Student(newId, newName, newAge, newEmail, newClassLevel);

        String requestId = pendingRequestService.createRequest(new PendingRequest("UPDATE", checkStudent, student));

        return ResponseEntity.ok("Request ID: " + requestId + ". Awaiting manager approval.");
    }
}
