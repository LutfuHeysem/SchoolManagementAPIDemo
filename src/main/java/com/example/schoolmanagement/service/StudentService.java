package com.example.schoolmanagement.service;

import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.repository.StudentExcelRepository;
import org.apache.commons.collections4.bag.SynchronizedSortedBag;
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
        for(Student studentt : students) {
            System.out.println(studentt);
            System.out.println(studentt.getName() + studentt.getID());
        }
        studentExcelRepository.saveAllStudents(students);
    }

    public boolean deleteStudent(Integer id) {
        return studentExcelRepository.deleteStudentById(id);
    }
}