package com.sau.cr.controller;

import com.sau.cr.dtos.EnrolmentDTO;
import com.sau.cr.model.Enrolment;
import com.sau.cr.service.EnrolmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrolments")
public class EnrolmentController {
    private final EnrolmentService enrolmentService;

    public EnrolmentController(EnrolmentService enrolmentService) {
        this.enrolmentService = enrolmentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EnrolmentDTO>> getAllEnrolments() {
        return new ResponseEntity<>(enrolmentService.getAllEnrolments(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EnrolmentDTO> getEnrolment(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(enrolmentService.getEnrolmentById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<EnrolmentDTO> addEnrolment(@RequestBody Enrolment enrolment) {
        return new ResponseEntity<>(enrolmentService.createEnrolment(enrolment), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EnrolmentDTO> updateEnrolment(@PathVariable Long id, @RequestBody Enrolment enrolment) {
        if (id == null || id == 0 || enrolment == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(enrolmentService.updateEnrolment(id, enrolment), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEnrolment(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        enrolmentService.deleteEnrolment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}