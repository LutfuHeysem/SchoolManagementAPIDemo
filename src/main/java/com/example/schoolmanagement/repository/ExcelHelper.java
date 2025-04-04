package com.example.schoolmanagement.repository;

import com.example.schoolmanagement.model.Personnel;
import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.model.Teacher;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelHelper {

    private static final String STUDENT_FILE = "/Users/lheysemk/Desktop/ExcelFiles/Students.xlsx";
    private static final String TEACHER_FILE = "/Users/lheysemk/Desktop/ExcelFiles/Teachers.xlsx";
    private static final String PERSONNEL_FILE = "/Users/lheysemk/Desktop/ExcelFiles/Personnel.xlsx";

    /**
     * Read all
     */
    public <T> List<T> readFromExcel(String filePath, Class<T> clazz) {
        List<T> records = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                T obj = clazz.getDeclaredConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();

                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    Cell cell = row.getCell(i);

                    if (cell != null) {
                        if (cell.getCellType() == CellType.STRING) {
                            fields[i].set(obj, cell.getStringCellValue());
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            fields[i].set(obj, (int) cell.getNumericCellValue());
                        }
                    }
                }
                records.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }

    /**
     * Write
     */
    public <T> void writeToExcel(String filePath, List<T> data) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            if (!data.isEmpty()) {
                // Create header row
                Row headerRow = sheet.createRow(0);
                Field[] fields = data.get(0).getClass().getDeclaredFields();

                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    headerRow.createCell(i).setCellValue(fields[i].getName());
                }

                // Populate data rows
                int rowIdx = 1;
                for (T item : data) {
                    Row row = sheet.createRow(rowIdx++);
                    for (int i = 0; i < fields.length; i++) {
                        fields[i].setAccessible(true);
                        Object value = fields[i].get(item);
                        if (value != null) {
                            if (value instanceof String) {
                                row.createCell(i).setCellValue((String) value);
                            } else if (value instanceof Integer) {
                                row.createCell(i).setCellValue((Integer) value);
                            } else if (value instanceof Double) {
                                row.createCell(i).setCellValue((Double) value);
                            }
                        }
                    }
                }
            }

            // Save to file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read by ID
     */
    public <T> T readFromExcelById(String filePath, Class<T> clazz, Integer id) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Field[] fields = clazz.getDeclaredFields();

            // Find ID column index by header name "ID" (case-insensitive)
            Row headerRow = sheet.getRow(0);
            int idColumnIndex = -1;
            if (headerRow != null) {
                for (Cell cell : headerRow) {
                    String headerName = cell.getStringCellValue().trim();
                    if (headerName.equalsIgnoreCase("ID")) { // Explicitly look for "ID" header
                        idColumnIndex = cell.getColumnIndex();
                        break;
                    }
                }
            }

            if (idColumnIndex == -1) {
                throw new RuntimeException("ID column not found in the Excel sheet.");
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                Cell idCell = row.getCell(idColumnIndex);
                if (idCell == null) continue;

                int cellId;
                if (idCell.getCellType() == CellType.NUMERIC) {
                    cellId = (int) idCell.getNumericCellValue();
                } else if (idCell.getCellType() == CellType.STRING) {
                    try {
                        cellId = Integer.parseInt(idCell.getStringCellValue().trim());
                    } catch (NumberFormatException e) {
                        continue; // Skip invalid IDs
                    }
                } else {
                    continue; // Unsupported cell type
                }

                if (cellId == id) {
                    T obj = clazz.getDeclaredConstructor().newInstance();
                    for (int i = 0; i < fields.length; i++) {
                        fields[i].setAccessible(true);
                        Cell cell = row.getCell(i); // Map fields to columns in order

                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING:
                                    fields[i].set(obj, cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    fields[i].set(obj, (int) cell.getNumericCellValue());
                                    break;
                                default:
                                    // Handle other types if needed
                            }
                        }
                    }
                    return obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delete by ID
     */
    public <T> void deleteFromExcelById(String filePath, Class<T> clazz, Integer id) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean rowDeleted = false;

            // Find ID column index by header name "ID" (case-insensitive)
            Row headerRow = sheet.getRow(0);
            int idColumnIndex = -1;
            if (headerRow != null) {
                for (Cell cell : headerRow) {
                    String headerName = cell.getStringCellValue().trim();
                    if (headerName.equalsIgnoreCase("ID")) { // Explicitly look for "ID" header
                        idColumnIndex = cell.getColumnIndex();
                        break;
                    }
                }
            }

            if (idColumnIndex < 0) throw new IllegalArgumentException("ID column not found.");

            // Iterate rows to find the ID
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;

                Cell idCell = row.getCell(idColumnIndex);
                if (idCell == null) continue;

                int cellId;
                if (idCell.getCellType() == CellType.NUMERIC) {
                    cellId = (int) idCell.getNumericCellValue();
                } else if (idCell.getCellType() == CellType.STRING) {
                    try {
                        cellId = Integer.parseInt(idCell.getStringCellValue().trim());
                    } catch (NumberFormatException e) {
                        continue; // Skip invalid IDs
                    }
                } else {
                    continue; // Unsupported cell type
                }

                if (cellId == id) {
                    // Remove the row
                    sheet.removeRow(row);
                    // Shift subsequent rows up
                    if (rowIndex < sheet.getLastRowNum()) {
                        sheet.shiftRows(rowIndex + 1, sheet.getLastRowNum(), -1);
                    }
                    rowDeleted = true;
                    break;
                }
            }

            // Save changes
            if (rowDeleted) {
                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    workbook.write(fos);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Convenience methods for Students
    public List<Student> readStudents() {
        return readFromExcel(STUDENT_FILE, Student.class);
    }

    public void writeStudents(List<Student> students) {
        writeToExcel(STUDENT_FILE, students);
    }

    public Student readStudentById(Integer id) {
        return readFromExcelById(STUDENT_FILE, Student.class, id);
    }

    public void deleteStudentById(Integer id) {
        deleteFromExcelById(STUDENT_FILE, Student.class, id);
    }

    // Convenience methods for Teachers
    public List<Teacher> readTeachers() {
        return readFromExcel(TEACHER_FILE, Teacher.class);
    }

    public void writeTeachers(List<Teacher> teachers) {
        writeToExcel(TEACHER_FILE, teachers);
    }

    public Teacher readTeacherById(Integer id) {
        return readFromExcelById(TEACHER_FILE, Teacher.class, id);
    }

    // Convenience methods for Personnel
    public List<Personnel> readPersonnel() {
        return readFromExcel(PERSONNEL_FILE, Personnel.class);
    }

    public void writePersonnel(List<Personnel> personnel) {
        writeToExcel(PERSONNEL_FILE, personnel);
    }

    public Personnel readPersonnelById(Integer id) {
        return readFromExcelById(PERSONNEL_FILE, Personnel.class, id);
    }

}