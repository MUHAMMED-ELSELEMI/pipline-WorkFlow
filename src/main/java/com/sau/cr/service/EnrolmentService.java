package com.sau.cr.service;

import com.sau.cr.dtos.EnrolmentDTO;
import com.sau.cr.model.Enrolment;
import java.util.List;

public interface EnrolmentService {
    List<EnrolmentDTO> getAllEnrolments();
    EnrolmentDTO getEnrolmentById(Long id);
    EnrolmentDTO createEnrolment(Enrolment enrolment);
    EnrolmentDTO updateEnrolment(Long id, Enrolment enrolment);
    void deleteEnrolment(Long id);
}