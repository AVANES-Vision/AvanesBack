package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Data
@Entity
@Table(name = "modules")
public class Modules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_id")
    private Integer moduleId;

    @Column(name = "`from`") // Escape reserved keyword 'from'
    private String from;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "`to`") // Escape reserved keyword 'to'
    private String to;

    @Column(name = "year")
    private Integer year;

    // Default constructor (required by JPA)
    public Modules() {}

    // Parameterized constructor (optional)
    public Modules(Integer moduleId, String from, String moduleName, Integer semester, String to, Integer year) {
        this.moduleId = moduleId;
        this.from = from;
        this.moduleName = moduleName;
        this.semester = semester;
        this.to = to;
        this.year = year;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}