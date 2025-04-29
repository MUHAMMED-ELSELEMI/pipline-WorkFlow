package com.sau.cr.dtos;

public class CoursesDTO {
    private Long id;
    private String title;
    private String description;
    private Integer semester;

    public CoursesDTO() {}

    public CoursesDTO(Long id, String title, String description, Integer semester) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.semester = semester;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
}