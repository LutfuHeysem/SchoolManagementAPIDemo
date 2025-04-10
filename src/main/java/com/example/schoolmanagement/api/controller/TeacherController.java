package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.repository.TeacherRepository;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final RuntimeService runtimeService;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository,
                             RuntimeService runtimeService) {
        this.teacherRepository = teacherRepository;
        this.runtimeService = runtimeService;
    }

    @PostMapping
    public ResponseEntity<String> addTeacher(
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String email,
            @RequestParam int assignedClass) {

        Teacher teacher = new Teacher(name, age, email, assignedClass);

        Map<String, Object> variables = new HashMap<>();
        variables.put("operationType", "CREATE_TEACHER");
        variables.put("teacherName", teacher.getName());
        variables.put("teacherAge", teacher.getAge());
        variables.put("teacherEmail", teacher.getEmail());
        variables.put("teacherAssignedClass", assignedClass);

        runtimeService.startProcessInstanceByKey("approvalProcess", variables);

        return ResponseEntity.ok("Teacher creation request submitted for approval");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Integer id) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("operationType", "DELETE_TEACHER");
        variables.put("teacherId", id);

        runtimeService.startProcessInstanceByKey("approvalProcess", variables);

        return ResponseEntity.ok("Teacher deletion request submitted for approval");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTeacher(
            @PathVariable Integer id,
            @RequestParam String newName,
            @RequestParam Integer newAge,
            @RequestParam String newEmail,
            @RequestParam Integer newAssignedClass) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("operationType", "UPDATE_TEACHER");
        variables.put("teacherId", id);
        variables.put("teacherName", newName);
        variables.put("teacherAge", newAge);
        variables.put("teacherEmail", newEmail);
        variables.put("teacherAssignedClass", newAssignedClass);

        runtimeService.startProcessInstanceByKey("approvalProcess", variables);

        return ResponseEntity.ok("Teacher update request submitted for approval");
    }

    @GetMapping
    public List<Teacher> getTeachers(){
        return teacherRepository.findAll();
    }

    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable Integer id){
        return teacherRepository.findById(id).get();
    }

}
