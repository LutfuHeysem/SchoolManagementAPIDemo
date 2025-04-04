package com.example.schoolmanagement.service;

import com.example.schoolmanagement.model.Personnel;
import com.example.schoolmanagement.repository.PersonnelExcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelService {

    private final PersonnelExcelRepository personnelExcelRepository;

    public PersonnelService(PersonnelExcelRepository personnelExcelRepository) {
        this.personnelExcelRepository = personnelExcelRepository;
    }

    public List<Personnel> getPersonnels() {
        return personnelExcelRepository.getAllPersonnel();
    }

    public Personnel getPersonnel(Integer id){
        return personnelExcelRepository.getPersonnelById(id);
    }

    public void addPersonnel(Personnel personnel) {
        List<Personnel> personnels = personnelExcelRepository.getAllPersonnel();
        personnels.add(personnel);
        personnelExcelRepository.saveAllPersonnel(personnels);
    }
}