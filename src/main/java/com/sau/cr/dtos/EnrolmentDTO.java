package com.sau.cr.dtos;

import java.time.LocalDate;

public class EnrolmentDTO {
    private Long id;
    private LocalDate classDate;
    private Double tuition;
    private Boolean attendance;
    private StudentDTO studentDTO;
    private CoursesDTO courseDTO;

    public EnrolmentDTO() {}

    public EnrolmentDTO(Long id, LocalDate classDate, Double tuition, Boolean attendance) {
        this.id = id;
        this.classDate = classDate;
        this.tuition = tuition;
        this.attendance = attendance;
    }

    public EnrolmentDTO(Long id, LocalDate classDate, Double tuition, Boolean attendance,
                        StudentDTO studentDTO, CoursesDTO courseDTO) {
        this.id = id;
        this.classDate = classDate;
        this.tuition = tuition;
        this.attendance = attendance;
        this.studentDTO = studentDTO;
        this.courseDTO = courseDTO;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getClassDate() {
        return classDate;
    }

    public void setClassDate(LocalDate classDate) {
        this.classDate = classDate;
    }

    public Double getTuition() {
        return tuition;
    }

    public void setTuition(Double tuition) {
        this.tuition = tuition;
    }

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public CoursesDTO getCourseDTO() {
        return courseDTO;
    }

    public void setCourseDTO(CoursesDTO courseDTO) {
        this.courseDTO = courseDTO;
    }
}