package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelHelper {

    private static final String STUDENT_FILE = "/Users/lheysemk/Desktop/ExcelFiles/Students.xlsx";

    String[] studentHeaders = {"id", "name", "age", "email", "classLevel"};

    /**
     * Write
     */
    public void writeToExcel(String filePath, List<Student> data, String[] headers) {
        // TODO: Implement writing data to Excel file
    }

    /**
     * Read all
     */
    public List<Student> readFromExcel(String filePath, String[] headers) {
        // TODO: Implement reading all data from Excel file
        return new ArrayList<>();
    }

    /**
     * Read by ID
     */
    public Student readFromExcelById(String filePath, Integer id, String[] headers) {
        // TODO: Implement reading data from Excel file by ID
        return null;
    }

    /**
     * Delete by ID
     */
    public boolean deleteFromExcelById(String filePath, Integer id, String[] headers) {
        // TODO: Implement deleting data from Excel file by ID
        return false;
    }

    /**
     * Update by ID
     */
    public boolean updateByID(String filePath, Integer id, String[] headers) {
        // TODO: Implement updating data in Excel file by ID
        return false;
    }

    // Convenience methods for Students
    public List<Student> readStudents() {
        return readFromExcel(STUDENT_FILE, studentHeaders);
    }

    public void writeStudents(List<Student> students) {
        writeToExcel(STUDENT_FILE, students, studentHeaders);
    }

    public Student readStudentById(Integer id) {
        return readFromExcelById(STUDENT_FILE, id, studentHeaders);
    }

    public boolean deleteStudentById(Integer id) {
        return deleteFromExcelById(STUDENT_FILE, id, studentHeaders);
    }

    public boolean updateStudentById(Integer id) {
        return updateByID(STUDENT_FILE, id, studentHeaders);
    }
}