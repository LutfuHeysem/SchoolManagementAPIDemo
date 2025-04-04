package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.Personnel;
import com.example.schoolmanagement.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personnels")
public class PersonnelController {

    private final PersonnelService personnelService;

    @Autowired
    public PersonnelController(PersonnelService personnelService){
        this.personnelService = personnelService;
    }

    @GetMapping
    public List<Personnel> getPersonnels(){
        return personnelService.getPersonnels();
    }

    @GetMapping("/{ID}")
    public Personnel getPersonnel(@PathVariable Integer ID){
        return personnelService.getPersonnel(ID);
    }

    @PostMapping
    public ResponseEntity<String> addPersonnel(
            @RequestParam int ID,
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String email) {
        Personnel personnel = new Personnel(ID, name, age, email);
        personnelService.addPersonnel(personnel);

        return ResponseEntity.ok("Personnel created successfully");
    }
}
