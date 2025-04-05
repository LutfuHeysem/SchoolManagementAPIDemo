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
    private static final String PERSONNEL_FILE = "/Users/lheysemk/Desktop/ExcelFiles/Personnel.xlx";

    String[] studentHeaders = {"ID", "name", "age", "email", "classLevel"};
    String[] teacherHeaders = {"ID", "name", "age", "email", "isOnLeave", "assignedClasses"};
    String[] personnelHeaders = {"ID", "name", "age", "email", "isOnLeave"};

    /**
     * Read all
     */
    public <T> List<T> readFromExcel(String filePath, Class<T> clazz, String[] headers) {
        List<T> records = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                T obj = clazz.getDeclaredConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();

                for (int i = 0; i < headers.length; i++) {
                    Cell cell = row.getCell(i);
                    Field field = getFieldByName(fields, headers[i]);
                    field.setAccessible(true);

                    if (cell != null) {
                        if (cell.getCellType() == CellType.STRING) {
                            field.set(obj, cell.getStringCellValue());
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            field.set(obj, (int) cell.getNumericCellValue());
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
    public <T> void writeToExcel(String filePath, List<T> data, String[] headers) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Populate data rows
            int rowIdx = 1;
            for (T item : data) {
                Row row = sheet.createRow(rowIdx++);

                // Map fields based on headers
                Field[] fields = item.getClass().getDeclaredFields();
                for (int i = 0; i < headers.length; i++) {
                    Field field = getFieldByName(fields, headers[i]);
                    field.setAccessible(true);
                    Object value = field.get(item);

                    if (value != null) {
                        if (value instanceof String) {
                            row.createCell(i).setCellValue((String) value);
                        } else if (value instanceof Integer) {
                            row.createCell(i).setCellValue((Integer) value);
                        }
                    }
                }
            }

            // Save to file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to find field by name
    private Field getFieldByName(Field[] fields, String name) {
        for (Field field : fields) {
            if (field.getName().equalsIgnoreCase(name)) {
                return field;
            }
        }
        throw new RuntimeException("Field not found: " + name);
    }

    /**
     * Read by ID
     */
    public <T> T readFromExcelById(String filePath, Class<T> clazz, Integer id, String[] headers) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Field[] fields = clazz.getDeclaredFields();

            int idColumnIndex = 0;

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
    public <T> boolean deleteFromExcelById(String filePath, Class<T> clazz, Integer id, String[] headers) {
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
            return rowDeleted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Convenience methods for Students
    public List<Student> readStudents() {
        return readFromExcel(STUDENT_FILE, Student.class, studentHeaders);
    }

    public void writeStudents(List<Student> students) {
        writeToExcel(STUDENT_FILE, students, studentHeaders);
    }

    public Student readStudentById(Integer id) {
        return readFromExcelById(STUDENT_FILE, Student.class, id, studentHeaders);
    }

    public boolean deleteStudentById(Integer id) {
        return deleteFromExcelById(STUDENT_FILE, Student.class, id, studentHeaders);
    }

    // Convenience methods for Teachers
    public List<Teacher> readTeachers() {
        return readFromExcel(TEACHER_FILE, Teacher.class, teacherHeaders);
    }

    public void writeTeachers(List<Teacher> teachers) {
        writeToExcel(TEACHER_FILE, teachers, teacherHeaders);
    }

    public Teacher readTeacherById(Integer id) {
        return readFromExcelById(TEACHER_FILE, Teacher.class, id, teacherHeaders);
    }

    // Convenience methods for Personnel
    public List<Personnel> readPersonnel() {
        return readFromExcel(PERSONNEL_FILE, Personnel.class, personnelHeaders);
    }

    public void writePersonnel(List<Personnel> personnel) {
        writeToExcel(PERSONNEL_FILE, personnel, personnelHeaders);
    }

    public Personnel readPersonnelById(Integer id) {
        return readFromExcelById(PERSONNEL_FILE, Personnel.class, id, personnelHeaders);
    }

}