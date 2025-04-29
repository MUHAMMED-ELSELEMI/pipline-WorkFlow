package com.sau.cr.model;

import com.sau.cr.dtos.StudentDTO;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;  // For optimistic locking

    @Column(length = 16, nullable = false)
    private String name;

    @Column(length = 16, nullable = false)
    private String department;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrolment> enrolments;

    // Constructors
    public Student() {}

    public Student(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public Student(StudentDTO studentDTO) {
        this.id = studentDTO.getId();
        this.name = studentDTO.getName();
        this.department = studentDTO.getDepartment();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public List<Enrolment> getEnrolments() { return enrolments; }
    public void setEnrolments(List<Enrolment> enrolments) { this.enrolments = enrolments; }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public StudentDTO viewAsStudentDTO() {
        return new StudentDTO(id, name, department);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}