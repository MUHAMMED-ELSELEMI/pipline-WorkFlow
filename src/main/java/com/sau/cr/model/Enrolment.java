package com.sau.cr.model;

import com.sau.cr.dtos.EnrolmentDTO;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrolments")
public class Enrolment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @Column(name = "class_date", nullable = false)
    private LocalDate classDate;

    @Column(nullable = false)
    private Double tuition;

    @Column(nullable = false)
    private Boolean attendance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

    // Constructors
    public Enrolment() {}

    public Enrolment(Long id, LocalDate classDate, Double tuition, Boolean attendance,
                     Student student, Courses course) {
        this.id = id;
        this.classDate = classDate;
        this.tuition = tuition;
        this.attendance = attendance;
        this.student = student;
        this.course = course;
    }

    public Enrolment(EnrolmentDTO enrolmentDTO) {
        this.id = enrolmentDTO.getId();
        this.classDate = enrolmentDTO.getClassDate();
        this.tuition = enrolmentDTO.getTuition();
        this.attendance = enrolmentDTO.getAttendance();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getClassDate() { return classDate; }
    public void setClassDate(LocalDate classDate) { this.classDate = classDate; }
    public Double getTuition() { return tuition; }
    public void setTuition(Double tuition) { this.tuition = tuition; }
    public Boolean getAttendance() { return attendance; }
    public void setAttendance(Boolean attendance) { this.attendance = attendance; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Courses getCourse() { return course; }
    public void setCourse(Courses course) { this.course = course; }

    public EnrolmentDTO viewAsEnrolmentDTO() {
        return new EnrolmentDTO(id, classDate, tuition, attendance);
    }

    @Override
    public String toString() {
        return "Enrolment{" +
                "id=" + id +
                ", classDate=" + classDate +
                ", tuition=" + tuition +
                ", attendance=" + attendance +
                '}';
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}