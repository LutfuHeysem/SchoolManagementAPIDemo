package com.example.schoolmanagement.service;

import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.StudentExcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentExcelRepository studentExcelRepository;

    public StudentService(StudentExcelRepository studentExcelRepository) {
        this.studentExcelRepository = studentExcelRepository;
    }

    public List<Student> getStudents() {
        return studentExcelRepository.getAllStudents();
    }

    public Student getStudent(Integer id){
        return studentExcelRepository.getStudentById(id);
    }

    public void addStudent(Student student) {
        List<Student> students = studentExcelRepository.getAllStudents();
        students.add(student);
        studentExcelRepository.saveAllStudents(students);
    }

    public void deleteStudent(Integer id) {
        studentExcelRepository.deleteStudentById(id);
    }

    public void updateStudent(Integer id, Student student) {
        studentExcelRepository.updateStudentById(id, student);
    }
}