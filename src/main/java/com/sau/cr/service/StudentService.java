package com.sau.cr.service;

import com.sau.cr.dtos.StudentDTO;
import com.sau.cr.model.Student;
import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    StudentDTO getStudentById(Long id);
    StudentDTO createStudent(Student student);
    StudentDTO updateStudent(Long id, Student student);
    void deleteStudent(Long id);
}