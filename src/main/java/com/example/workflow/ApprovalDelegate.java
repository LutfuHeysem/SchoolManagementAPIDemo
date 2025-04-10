package com.example.workflow;

import com.example.schoolmanagement.model.Student;
import com.example.schoolmanagement.model.Teacher;
import com.example.schoolmanagement.repository.StudentRepository;
import com.example.schoolmanagement.repository.TeacherRepository;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("approvalDelegate")
public class ApprovalDelegate implements JavaDelegate {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public ApprovalDelegate(StudentRepository studentRepository,
                            TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public void execute(DelegateExecution execution) {
        String operationType = (String) execution.getVariable("operationType");

        switch (operationType) {
            case "CREATE_STUDENT":
                Student student = new Student();
                student.setName((String) execution.getVariable("studentName"));
                student.setAge((Integer) execution.getVariable("studentAge"));
                student.setEmail((String) execution.getVariable("studentEmail"));
                student.setClassLevel((Integer) execution.getVariable("studentClassLevel"));
                student.setApprovalStatus("APPROVED");
                studentRepository.saveAndFlush(student);
                break;

            case "UPDATE_STUDENT":
                Student updatedStudent = new Student();
                updatedStudent.setName((String) execution.getVariable("studentName"));
                updatedStudent.setAge((Integer) execution.getVariable("studentAge"));
                updatedStudent.setEmail((String) execution.getVariable("studentEmail"));
                updatedStudent.setClassLevel((Integer) execution.getVariable("studentClassLevel"));
                updatedStudent.setApprovalStatus("APPROVED");
                Integer studentId1 = (Integer) execution.getVariable("studentId");
                studentRepository.deleteById(studentId1);
                studentRepository.saveAndFlush(updatedStudent);
                break;

            case "DELETE_STUDENT":
                Integer deleteStudentId = (Integer) execution.getVariable("studentId");
                studentRepository.deleteById(deleteStudentId);
                break;

            case "CREATE_TEACHER":
                Teacher teacher = new Teacher();
                teacher.setName((String) execution.getVariable("teacherName"));
                teacher.setAge((Integer) execution.getVariable("teacherAge"));
                teacher.setEmail((String) execution.getVariable("teacherEmail"));
                teacher.setAssignedClass((Integer) execution.getVariable("teacherAssignedClass"));
                teacher.setApprovalStatus("APPROVED");
                teacherRepository.saveAndFlush(teacher);
                break;

            case "UPDATE_TEACHER":
                Teacher updatedTeacher = new Teacher();
                updatedTeacher.setName((String) execution.getVariable("teacherName"));
                updatedTeacher.setAge((Integer) execution.getVariable("teacherAge"));
                updatedTeacher.setEmail((String) execution.getVariable("teacherEmail"));
                updatedTeacher.setAssignedClass((Integer) execution.getVariable("teacherAssignedClass"));
                updatedTeacher.setApprovalStatus("APPROVED");
                Integer teacherId1 = (Integer) execution.getVariable("teacherId");
                teacherRepository.deleteById(teacherId1);
                teacherRepository.saveAndFlush(updatedTeacher);
                break;

            case "DELETE_TEACHER":
                Integer deleteTeacherId = (Integer) execution.getVariable("teacherId");
                teacherRepository.deleteById(deleteTeacherId);
                break;
        }
    }
}