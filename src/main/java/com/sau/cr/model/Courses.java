package com.sau.cr.model;

import com.sau.cr.dtos.CoursesDTO;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @Column(length = 32, nullable = false)
    private String title;

    @Column(length = 32)
    private String description;

    @Column(nullable = false)
    private Integer semester;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrolment> enrolments;

    // Constructors
    public Courses() {}

    public Courses(Long id, String title, String description, Integer semester) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.semester = semester;
    }

    public Courses(CoursesDTO coursesDTO) {
        this.id = coursesDTO.getId();
        this.title = coursesDTO.getTitle();
        this.description = coursesDTO.getDescription();
        this.semester = coursesDTO.getSemester();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }
    public List<Enrolment> getEnrolments() { return enrolments; }
    public void setEnrolments(List<Enrolment> enrolments) { this.enrolments = enrolments; }

    public CoursesDTO viewAsCoursesDTO() {
        return new CoursesDTO(id, title, description, semester);
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", semester=" + semester +
                '}';
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}