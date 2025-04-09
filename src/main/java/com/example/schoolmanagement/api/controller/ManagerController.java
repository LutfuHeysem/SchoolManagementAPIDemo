package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.PendingRequest;
import com.example.schoolmanagement.service.ManagerService;
import com.example.schoolmanagement.service.PendingRequestService;
import com.example.schoolmanagement.service.StudentService;
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

    @Autowired
    public ManagerController(ManagerService managerService,
                             PendingRequestService pendingRequestService,
                             StudentService studentService) {
        this.managerService = managerService;
        this.pendingRequestService = pendingRequestService;
        this.studentService = studentService;
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
            case "CREATE":
                studentService.addStudent(request.getStudent());
                break;
            case "UPDATE":
                studentService.updateStudent(request.getStudent().getId(), request.getUpdatedStudent());
                break;
            case "DELETE":
                studentService.deleteStudent(request.getStudent().getId());
                break;
            default:
                return ResponseEntity.badRequest().body("Unsupported operation");
        }

        pendingRequestService.deleteRequest(requestId);
        return ResponseEntity.ok("Operation approved and completed");
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