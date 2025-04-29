package com.sau.cr.service;

import com.sau.cr.dtos.CoursesDTO;
import com.sau.cr.exception.ErrorMessages;
import com.sau.cr.exception.ResourceNotFoundException;
import com.sau.cr.model.Courses;
import com.sau.cr.repository.CoursesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Transactional
public class CoursesServiceImpl implements CoursesService {
    private final CoursesRepository coursesRepository;

    public CoursesServiceImpl(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    @Override
    public List<CoursesDTO> getAllCourses() {
        return coursesRepository.findAll().stream()
                .map(Courses::viewAsCoursesDTO)
                .toList();
    }

    @Override
    public CoursesDTO getCourseById(Long id) {
        return coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_COURSE_NOT_FOUND + ": " + id))
                .viewAsCoursesDTO();
    }

    @Override
    public CoursesDTO createCourse(Courses course) {
        // Ensure we're creating a new course (ID should be null)
        if (course.getId() != null) {
            throw new IllegalArgumentException("New course must not have an ID");
        }

        // Validate required fields
        if (course.getTitle() == null || course.getSemester() == null) {
            throw new IllegalArgumentException("Title and semester are required");
        }

        return coursesRepository.save(course).viewAsCoursesDTO();
    }

    @Override
    public CoursesDTO updateCourse(Long id, Courses course) {
        Courses existingCourse = coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_COURSE_NOT_FOUND + ": " + id));

        // Update only non-null fields
        if (course.getTitle() != null) {
            existingCourse.setTitle(course.getTitle());
        }
        if (course.getDescription() != null) {
            existingCourse.setDescription(course.getDescription());
        }
        if (course.getSemester() != null) {
            existingCourse.setSemester(course.getSemester());
        }

        return coursesRepository.save(existingCourse).viewAsCoursesDTO();
    }

    @Override
    public void deleteCourse(Long id) {
        Courses course = coursesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_COURSE_NOT_FOUND + ": " + id));

        // Check if course has enrolments before deleting
        if (course.getEnrolments() != null && !course.getEnrolments().isEmpty()) {
            throw new IllegalStateException("Cannot delete course with existing enrolments");
        }

        coursesRepository.delete(course);
    }
}