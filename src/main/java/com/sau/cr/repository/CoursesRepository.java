package com.sau.cr.repository;

import com.sau.cr.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<Courses, Long> {
}
