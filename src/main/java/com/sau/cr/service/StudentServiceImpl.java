// StudentServiceImpl.java
package com.sau.cr.service;

import com.sau.cr.dtos.StudentDTO;
import com.sau.cr.exception.ErrorMessages;
import com.sau.cr.exception.ResourceAlreadyExistsException;
import com.sau.cr.exception.ResourceNotFoundException;
import com.sau.cr.model.Student;
import com.sau.cr.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(Student::viewAsStudentDTO)
                .toList();
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_STUDENT_NOT_FOUND + ": " + id))
                .viewAsStudentDTO();
    }

    @Override
    public StudentDTO createStudent(Student student) {
        // Ensure we're creating a new student (ID should be null)
        if (student.getId() != null) {
            throw new IllegalArgumentException("New student must not have an ID");
        }

        // Validate required fields
        if (student.getName() == null || student.getDepartment() == null) {
            throw new IllegalArgumentException("Name and department are required");
        }

        return studentRepository.save(student).viewAsStudentDTO();
    }

    @Override
    public StudentDTO updateStudent(Long id, Student student) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_STUDENT_NOT_FOUND + ": " + id));

        // Update only non-null fields
        if (student.getName() != null) {
            existingStudent.setName(student.getName());
        }
        if (student.getDepartment() != null) {
            existingStudent.setDepartment(student.getDepartment());
        }

        return studentRepository.save(existingStudent).viewAsStudentDTO();
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_STUDENT_NOT_FOUND + ": " + id));

        // Check if student has enrolments before deleting
        if (student.getEnrolments() != null && !student.getEnrolments().isEmpty()) {
            throw new IllegalStateException("Cannot delete student with existing enrolments");
        }

        studentRepository.delete(student);
    }
}