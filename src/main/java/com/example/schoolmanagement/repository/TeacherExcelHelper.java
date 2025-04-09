package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Teacher;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TeacherExcelHelper {

    private static final String TEACHER_FILE = "/Users/lheysemk/Desktop/ExcelFiles/Teachers.xlsx";

    String[] teacherHeaders = {"id", "name", "age", "email", "assignedClass", "isOnLeave", "remainingLeaveQuota", "currentLeaveDays"};

    /**
     * Write
     */
    public void writeToExcel(String filePath, List<Teacher> data, String[] headers) {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet("Teachers");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Populate rows with data
            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(i + 1); // Start from row 1 as 0 is the header
                Teacher teacher = data.get(i);
                row.createCell(0).setCellValue(teacher.getId());
                row.createCell(1).setCellValue(teacher.getName());
                row.createCell(2).setCellValue(teacher.getAge());
                row.createCell(3).setCellValue(teacher.getEmail());
                row.createCell(4).setCellValue(teacher.getAssignedClass());
                //TODO: add the rest
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
    public List<Teacher> readFromExcel(String filePath) {
        List<Teacher> teachers = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Skip header row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    Teacher teacher = new Teacher();

                    teacher.setId((int) row.getCell(0).getNumericCellValue());
                    teacher.setName(row.getCell(1).getStringCellValue());
                    teacher.setAge((int) row.getCell(2).getNumericCellValue());
                    teacher.setEmail(row.getCell(3).getStringCellValue());
                    teacher.setAssignedClass((int) row.getCell(4).getNumericCellValue());
                    //TODO: add the rest

                    teachers.add(teacher);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read data from Excel file: " + filePath, e);
        }

        return teachers;
    }

    /**
     * Read by ID
     */
    public Teacher readFromExcelById(String filePath, Integer id) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Skip header row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    int rowId = (int) row.getCell(0).getNumericCellValue();
                    if (rowId == id) {
                        Teacher teacher = new Teacher();

                        teacher.setId(rowId);
                        teacher.setName(row.getCell(1).getStringCellValue());
                        teacher.setAge((int) row.getCell(2).getNumericCellValue());
                        teacher.setEmail(row.getCell(3).getStringCellValue());
                        teacher.setAssignedClass((int) row.getCell(4).getNumericCellValue());
                        //TODO: add the rest

                        return teacher;
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
    public void updateByID(String filePath, Integer id, Teacher teacher) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    int rowId = (int) row.getCell(0).getNumericCellValue();
                    if (rowId == id) {
                        int newId = teacher.getId();
                        String newName = teacher.getName();
                        int newAge = teacher.getAge();
                        String newEmail = teacher.getEmail();
                        int newClassLevel = teacher.getAssignedClass();
                        //TODO: add the rest

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
                        //TODO: add the rest

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

    // Convenience methods for Teachers
    public List<Teacher> readTeachers() {
        return readFromExcel(TEACHER_FILE);
    }

    public void writeTeachers(List<Teacher> teachers) {
        writeToExcel(TEACHER_FILE, teachers, teacherHeaders);
    }

    public Teacher readTeacherById(Integer id) {
        return readFromExcelById(TEACHER_FILE, id);
    }

    public void deleteTeacherById(Integer id) {
        deleteFromExcelById(TEACHER_FILE, id);
    }

    public void updateTeacherById(Integer id, Teacher teacher) {
        updateByID(TEACHER_FILE, id, teacher);
    }

}
