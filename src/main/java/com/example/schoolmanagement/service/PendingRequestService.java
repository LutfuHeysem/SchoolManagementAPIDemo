package com.example.schoolmanagement.service;

import com.example.schoolmanagement.model.PendingRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PendingRequestService {
    private final Map<String, PendingRequest> pendingRequests = new ConcurrentHashMap<>();

    public String createRequest(PendingRequest request) {
        String requestId = UUID.randomUUID().toString();
        pendingRequests.put(requestId, request);
        return requestId;
    }

    public PendingRequest getRequest(String requestId) {
        return pendingRequests.get(requestId);
    }

    public Map<String, PendingRequest> getAllRequests() {
        return pendingRequests;
    }

    public void deleteRequest(String requestId) {
        pendingRequests.remove(requestId);
    }
}