package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.TaskDTO;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final TaskService taskService;

    @Autowired
    public ManagerController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<TaskDTO> getPendingTasks() {
        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateGroup("manager")
                .includeProcessVariables()
                .list();

        return tasks.stream()
                .map(task -> {
                    Map<String, Object> variables = task.getProcessVariables();
                    String operationType = (String) variables.get("operationType");
                    String entityDetails = getEntityDetails(operationType, variables);

                    return new TaskDTO(
                            task.getId(),
                            task.getName(),
                            task.getProcessInstanceId(),
                            "Operation: " + operationType,  // Show operation type
                            entityDetails                  // Show entity-specific details
                    );
                })
                .collect(Collectors.toList());
    }

    private String getEntityDetails(String operationType, Map<String, Object> variables) {
        switch (operationType) {
            case "CREATE_STUDENT":
                return String.format("Name: %s, Age: %d, Class: %d",
                        variables.get("studentName"),
                        (Integer)variables.get("studentAge"),
                        (Integer)variables.get("studentClassLevel"));
            case "DELETE_STUDENT":
                return "Student ID: " + variables.get("studentId");
            case "CREATE_TEACHER":
                return String.format("Name: %s, Age: %d, AssignedClass: %d",
                        variables.get("teacherName"),
                        (Integer)variables.get("teacherAge"),
                        (Integer)variables.get("teacherAssignedClass"));
            case "DELETE_TEACHER":
                return "Teacher ID: " + variables.get("teacherId");
            default:
                return "No details available";
        }
    }

    @PostMapping("/approve/{taskId}")
    public ResponseEntity<String> approveTask(@PathVariable String taskId) {
        taskService.complete(taskId, Map.of("approved", true));
        return ResponseEntity.ok("Request approved");
    }

    @PostMapping("/reject/{taskId}")
    public ResponseEntity<String> rejectTask(@PathVariable String taskId) {
        taskService.complete(taskId, Map.of("approved", false));
        return ResponseEntity.ok("Request rejected");
    }
}