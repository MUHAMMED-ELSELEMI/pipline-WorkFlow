package com.sau.cr.controller;

import com.sau.cr.dtos.StudentDTO;
import com.sau.cr.model.Student;
import com.sau.cr.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        if (id == null || id == 0 || student == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(studentService.updateStudent(id, student), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}