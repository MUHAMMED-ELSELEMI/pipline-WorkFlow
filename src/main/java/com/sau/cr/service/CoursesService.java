package com.sau.cr.service;

import com.sau.cr.dtos.CoursesDTO;
import com.sau.cr.model.Courses;
import java.util.List;

public interface CoursesService {
    List<CoursesDTO> getAllCourses();
    CoursesDTO getCourseById(Long id);
    CoursesDTO createCourse(Courses course);
    CoursesDTO updateCourse(Long id, Courses course);
    void deleteCourse(Long id);
}