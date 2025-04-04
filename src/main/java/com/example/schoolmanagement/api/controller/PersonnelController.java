package com.example.schoolmanagement.api.controller;

import com.example.schoolmanagement.model.Personnel;
import com.example.schoolmanagement.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public Personnel getPersonnel(@RequestParam Integer id){
        return personnelService.getPersonnel(id);
    }

    @PostMapping
    public String addPersonnel(@RequestBody Personnel personnel){
        personnelService.addPersonnel(personnel);
        return "Personnel added successfully!";
    }
}
