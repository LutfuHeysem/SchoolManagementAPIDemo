package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Personnel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonnelExcelRepository {

    private final ExcelHelper excelHelper;

    public PersonnelExcelRepository(ExcelHelper excelHelper) {
        this.excelHelper = excelHelper;
    }

    public List<Personnel> getAllPersonnel() {
        return excelHelper.readPersonnel();
    }

    public void saveAllPersonnel(List<Personnel> personnel) {
        excelHelper.writePersonnel(personnel);
    }

    public Personnel getPersonnelById(Integer id){
        return excelHelper.readPersonnelById(id);
    }
}