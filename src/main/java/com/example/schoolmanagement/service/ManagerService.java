package com.example.schoolmanagement.service;

import com.example.schoolmanagement.model.Manager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {
    private final List<Manager> managers = new ArrayList<>();

    public ManagerService() {
        managers.add(new Manager("admin", "admin123"));
    }

    public boolean authenticate(String username, String password) {
        return managers.stream()
                .anyMatch(m -> m.getUsername().equals(username) && m.getPassword().equals(password));
    }
}