package com.sau.cr.service;

import com.sau.cr.dtos.EnrolmentDTO;
import com.sau.cr.exception.ErrorMessages;
import com.sau.cr.exception.ResourceNotFoundException;
import com.sau.cr.model.Courses;
import com.sau.cr.model.Enrolment;
import com.sau.cr.model.Student;
import com.sau.cr.repository.CoursesRepository;
import com.sau.cr.repository.EnrolmentRepository;
import com.sau.cr.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class EnrolmentServiceImpl implements EnrolmentService {
    private final EnrolmentRepository enrolmentRepository;
    private final StudentRepository studentRepository;
    private final CoursesRepository coursesRepository;

    public EnrolmentServiceImpl(EnrolmentRepository enrolmentRepository,
                                StudentRepository studentRepository,
                                CoursesRepository coursesRepository) {
        this.enrolmentRepository = enrolmentRepository;
        this.studentRepository = studentRepository;
        this.coursesRepository = coursesRepository;
    }

    @Override
    public List<EnrolmentDTO> getAllEnrolments() {
        return enrolmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public EnrolmentDTO getEnrolmentById(Long id) {
        Enrolment enrolment = enrolmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_ENROLMENT_NOT_FOUND + ": " + id));
        return convertToDTO(enrolment);
    }

    @Override
    public EnrolmentDTO createEnrolment(Enrolment enrolment) {
        // Validate new enrolment
        if (enrolment.getId() != null) {
            throw new IllegalArgumentException("New enrolment must not have an ID");
        }

        validateEnrolmentFields(enrolment);

        // Verify and load complete student and course entities
        Student student = validateAndGetStudent(enrolment.getStudent());
        Courses course = validateAndGetCourse(enrolment.getCourse());

        // Set the complete entities
        enrolment.setStudent(student);
        enrolment.setCourse(course);

        // Save and convert to DTO
        Enrolment savedEnrolment = enrolmentRepository.save(enrolment);
        return convertToDTO(savedEnrolment);
    }

    @Override
    public EnrolmentDTO updateEnrolment(Long id, Enrolment enrolment) {
        // Validate existing enrolment
        Enrolment existingEnrolment = enrolmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_ENROLMENT_NOT_FOUND + ": " + id));

        // Update fields if provided
        if (enrolment.getClassDate() != null) {
            existingEnrolment.setClassDate(enrolment.getClassDate());
        }
        if (enrolment.getTuition() != null) {
            existingEnrolment.setTuition(enrolment.getTuition());
        }
        if (enrolment.getAttendance() != null) {
            existingEnrolment.setAttendance(enrolment.getAttendance());
        }

        // Update student if provided
        if (enrolment.getStudent() != null && enrolment.getStudent().getId() != null) {
            Student student = validateAndGetStudent(enrolment.getStudent());
            existingEnrolment.setStudent(student);
        }

        // Update course if provided
        if (enrolment.getCourse() != null && enrolment.getCourse().getId() != null) {
            Courses course = validateAndGetCourse(enrolment.getCourse());
            existingEnrolment.setCourse(course);
        }

        Enrolment updatedEnrolment = enrolmentRepository.save(existingEnrolment);
        return convertToDTO(updatedEnrolment);
    }

    @Override
    public void deleteEnrolment(Long id) {
        Enrolment enrolment = enrolmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_ENROLMENT_NOT_FOUND + ": " + id));
        enrolmentRepository.delete(enrolment);
    }

    // Helper methods
    private void validateEnrolmentFields(Enrolment enrolment) {
        if (enrolment.getClassDate() == null) {
            throw new IllegalArgumentException("Class date is required");
        }
        if (enrolment.getTuition() == null || enrolment.getTuition() < 0) {
            throw new IllegalArgumentException("Valid tuition amount is required");
        }
        if (enrolment.getAttendance() == null) {
            throw new IllegalArgumentException("Attendance status is required");
        }
        if (enrolment.getStudent() == null || enrolment.getStudent().getId() == null) {
            throw new IllegalArgumentException("Student ID is required");
        }
        if (enrolment.getCourse() == null || enrolment.getCourse().getId() == null) {
            throw new IllegalArgumentException("Course ID is required");
        }
    }

    private Student validateAndGetStudent(Student student) {
        return studentRepository.findById(student.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_STUDENT_NOT_FOUND + ": " + student.getId()));
    }

    private Courses validateAndGetCourse(Courses course) {
        return coursesRepository.findById(course.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_COURSE_NOT_FOUND + ": " + course.getId()));
    }

    private EnrolmentDTO convertToDTO(Enrolment enrolment) {
        EnrolmentDTO dto = new EnrolmentDTO(
                enrolment.getId(),
                enrolment.getClassDate(),
                enrolment.getTuition(),
                enrolment.getAttendance()
        );
        dto.setStudentDTO(enrolment.getStudent().viewAsStudentDTO());
        dto.setCourseDTO(enrolment.getCourse().viewAsCoursesDTO());
        return dto;
    }
}