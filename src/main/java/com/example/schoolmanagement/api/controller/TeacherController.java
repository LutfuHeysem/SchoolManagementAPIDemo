package com.example.schoolmanagement.api.controller;


import com.example.schoolmanagement.model.PendingRequest;
import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.service.PendingRequestService;
import com.example.schoolmanagement.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final PendingRequestService pendingRequestService;

    @Autowired
    public TeacherController(TeacherService teacherService,
                             PendingRequestService pendingRequestService) {
        this.teacherService = teacherService;
        this.pendingRequestService = pendingRequestService;
    }

    @GetMapping
    public List<Teacher> getTeachers(){
        return teacherService.getTeachers();
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable Integer id){
        return teacherService.getTeacher(id);
    }

    @PostMapping
    public ResponseEntity<String> addTeacher(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String email,
            @RequestParam int assignedClass) {
        Teacher teacher = new Teacher(id, name, age, email, assignedClass);

        String requestId = pendingRequestService.createRequest(new PendingRequest("CREATE_TEACHER", teacher));

        return ResponseEntity.ok("Request ID: " + requestId + ". Awaiting manager approval.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Integer id) {
        Teacher teacher = teacherService.getTeacher(id);
        if(teacher == null)
            return ResponseEntity.ok("Student not found");

        String requestId = pendingRequestService.createRequest(new PendingRequest("DELETE_TEACHER", teacher));

        return ResponseEntity.ok("Request ID: " + requestId + ". Awaiting manager approval.");
    }

}
