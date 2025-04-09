package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentExcelHelper {

    private static final String STUDENT_FILE = "/Users/lheysemk/Desktop/ExcelFiles/Students.xlsx";

    String[] studentHeaders = {"id", "name", "age", "email", "classLevel"};

    /**
     * Write
     */
    public void writeToExcel(String filePath, List<Student> data, String[] headers) {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet("Students");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Populate rows with data
            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(i + 1); // Start from row 1 as 0 is the header
                Student student = data.get(i);
                row.createCell(0).setCellValue(student.getId());
                row.createCell(1).setCellValue(student.getName());
                row.createCell(2).setCellValue(student.getAge());
                row.createCell(3).setCellValue(student.getEmail());
                row.createCell(4).setCellValue(student.getClassLevel());
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fileOut);

        } catch (IOException e) {
            throw new RuntimeException("Failed to write data to Excel file: " + filePath, e);
        }
    }

    /**
     * Read all
     */
    public List<Student> readFromExcel(String filePath) {
        List<Student> students = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Skip header row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    Student student = new Student();

                    student.setId((int) row.getCell(0).getNumericCellValue());
                    student.setName(row.getCell(1).getStringCellValue());
                    student.setAge((int) row.getCell(2).getNumericCellValue());
                    student.setEmail(row.getCell(3).getStringCellValue());
                    student.setClassLevel((int) row.getCell(4).getNumericCellValue());

                    students.add(student);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read data from Excel file: " + filePath, e);
        }

        return students;
    }

    /**
     * Read by ID
     */
    public Student readFromExcelById(String filePath, Integer id) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Skip header row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    int rowId = (int) row.getCell(0).getNumericCellValue();
                    if (rowId == id) {
                        Student student = new Student();

                        student.setId(rowId);
                        student.setName(row.getCell(1).getStringCellValue());
                        student.setAge((int) row.getCell(2).getNumericCellValue());
                        student.setEmail(row.getCell(3).getStringCellValue());
                        student.setClassLevel((int) row.getCell(4).getNumericCellValue());

                        return student;
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read data from Excel file by ID: " + filePath, e);
        }

        return null;
    }

    /**
     * Delete by ID
     */
    public void deleteFromExcelById(String filePath, Integer id) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    int rowId = (int) row.getCell(0).getNumericCellValue();
                    if (rowId == id) {
                        sheet.removeRow(row);

                        // Shift rows up to fill the gap
                        int lastRowNum = sheet.getLastRowNum();
                        if (i < lastRowNum) {
                            sheet.shiftRows(i + 1, lastRowNum, -1);
                        }
                        break;
                    }
                }
            }

            // Write the updated data back to the file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to delete data from Excel file by ID: " + filePath, e);
        }

    }

    /**
     * Update by ID
     */
    public void updateByID(String filePath, Integer id, Student student) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    int rowId = (int) row.getCell(0).getNumericCellValue();
                    if (rowId == id) {
                        int newId = student.getId();
                        String newName = student.getName();
                        int newAge = student.getAge();
                        String newEmail = student.getEmail();
                        int newClassLevel = student.getClassLevel();

                        if(newId != 0)
                            row.getCell(0).setCellValue(newId);
                        if(!newName.isEmpty())
                            row.getCell(1).setCellValue(newName);
                        if(newAge != 0)
                            row.getCell(2).setCellValue(newAge);
                        if(!newEmail.isEmpty())
                            row.getCell(3).setCellValue(newEmail);
                        if(newClassLevel != 0)
                            row.getCell(4).setCellValue(newClassLevel);
                        break;
                    }
                }
            }

            // Write the updated data back to the file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to update data in Excel file by ID: " + filePath, e);
        }

    }

    // Convenience methods for Students
    public List<Student> readStudents() {
        return readFromExcel(STUDENT_FILE);
    }

    public void writeStudents(List<Student> students) {
        writeToExcel(STUDENT_FILE, students, studentHeaders);
    }

    public Student readStudentById(Integer id) {
        return readFromExcelById(STUDENT_FILE, id);
    }

    public void deleteStudentById(Integer id) {
        deleteFromExcelById(STUDENT_FILE, id);
    }

    public void updateStudentById(Integer id, Student student) {
        updateByID(STUDENT_FILE, id, student);
    }
}