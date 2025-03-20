package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "modules")
public class Modules {
    @Id
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

}