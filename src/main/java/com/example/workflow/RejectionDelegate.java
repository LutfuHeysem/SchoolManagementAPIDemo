package com.example.workflow;

import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rejectionDelegate")
public class RejectionDelegate implements JavaDelegate {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public RejectionDelegate(StudentRepository studentRepository,
                             TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Request rejected: " + execution.getVariables());
    }
}