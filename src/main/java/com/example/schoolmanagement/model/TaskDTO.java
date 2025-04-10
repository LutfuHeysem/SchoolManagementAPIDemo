package com.example.schoolmanagement.model;

public class TaskDTO {
    private String id;
    private String name;
    private String processInstanceId;
    private String description;
    private String operationType;
    private String entityDetails;

    public TaskDTO() {
        super();
    }

    public TaskDTO(String id, String name, String processInstanceId, String description) {
        this.id = id;
        this.name = name;
        this.processInstanceId = processInstanceId;
        this.description = description;
    }

    public TaskDTO(String id, String name, String processInstanceId,
                   String operationType, String entityDetails) {
        this.id = id;
        this.name = name;
        this.processInstanceId = processInstanceId;
        this.operationType = operationType;
        this.entityDetails = entityDetails;
    }


    public String getId() { return id; }
    public String getName() { return name; }
    public String getProcessInstanceId() { return processInstanceId; }
    public String getDescription() { return description; }
    public String getOperationType() { return operationType; }
    public String getEntityDetails() { return entityDetails; }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
    public void setEntityDetails(String entityDetails) {
        this.entityDetails = entityDetails;
    }
}