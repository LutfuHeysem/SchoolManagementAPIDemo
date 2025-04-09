package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.PendingRequest;
import com.example.schoolmanagement.service.ManagerService;
import com.example.schoolmanagement.service.PendingRequestService;
import com.example.schoolmanagement.service.StudentService;
import com.example.schoolmanagement.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;
    private final PendingRequestService pendingRequestService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public ManagerController(ManagerService managerService,
                             PendingRequestService pendingRequestService,
                             StudentService studentService, TeacherService teacherService) {
        this.managerService = managerService;
        this.pendingRequestService = pendingRequestService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @PostMapping("/approve")
    public ResponseEntity<String> approveRequest(
            @RequestParam String requestId,
            @RequestParam String username,
            @RequestParam String password) {

        // Authenticate manager
        if (!managerService.authenticate(username, password)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        PendingRequest request = pendingRequestService.getRequest(requestId);
        if (request == null) {
            return ResponseEntity.badRequest().body("Invalid request ID");
        }

        switch (request.getOperationType()) {
            case "CREATE_STUDENT":
                studentService.addStudent(request.getStudent());
                break;
            case "UPDATE_STUDENT":
                studentService.updateStudent(request.getStudent().getId(), request.getUpdatedStudent());
                break;
            case "DELETE_STUDENT":
                studentService.deleteStudent(request.getStudent().getId());
                break;
            case "CREATE_TEACHER":
                teacherService.addTeacher(request.getTeacher());
                break;
            case "DELETE_TEACHER":
                teacherService.deleteTeacher(request.getTeacher().getId());
                break;
            default:
                return ResponseEntity.badRequest().body("Unsupported operation");
        }

        pendingRequestService.deleteRequest(requestId);
        return ResponseEntity.ok("Operation approved and completed");
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectRequest(
            @RequestParam String requestId,
            @RequestParam String username,
            @RequestParam String password) {
        // Authenticate manager
        if (!managerService.authenticate(username, password)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        PendingRequest request = pendingRequestService.getRequest(requestId);
        if (request == null) {
            return ResponseEntity.badRequest().body("Invalid request ID");
        }
        if(!request.getOperationType().equals("CREATE_STUDENT") &&
            !request.getOperationType().equals("DELETE_STUDENT") &&
            !request.getOperationType().equals("UPDATE_STUDENT") &&
            !request.getOperationType().equals("LEAVE") &&
            !request.getOperationType().equals("CREATE_TEACHER") &&
            !request.getOperationType().equals("DELETE_TEACHER"))
            ResponseEntity.badRequest().body("Unsupported operation");

        pendingRequestService.deleteRequest(requestId);
        return ResponseEntity.ok("Operation rejected and deleted");
    }

    @GetMapping
    public Map<String, PendingRequest> getAllPendingRequests() {
        return pendingRequestService.getAllRequests();
    }

    @GetMapping("/{id}")
    public PendingRequest getRequest(@PathVariable String id) {
        return pendingRequestService.getRequest(id);
    }
}