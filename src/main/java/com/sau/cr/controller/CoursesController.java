package com.sau.cr.controller;

import com.sau.cr.dtos.CoursesDTO;
import com.sau.cr.model.Courses;
import com.sau.cr.service.CoursesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CoursesController {
    private final static Logger logger = LoggerFactory.getLogger(CoursesController.class);
    private final CoursesService coursesService;

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CoursesDTO>> getAllCourses() {
        return new ResponseEntity<>(coursesService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<CoursesDTO> getCourse(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        logger.info("Get course by id {}", id);
        return new ResponseEntity<>(coursesService.getCourseById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CoursesDTO> addCourse(@RequestBody Courses course) {
        try {
            return new ResponseEntity<>(coursesService.createCourse(course), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CoursesDTO> updateCourse(@PathVariable Long id, @RequestBody Courses course) {
        if (id == null || id == 0 || course == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(coursesService.updateCourse(id, course), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Courses> deleteCourse(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        coursesService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}