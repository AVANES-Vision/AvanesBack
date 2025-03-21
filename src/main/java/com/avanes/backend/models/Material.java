package com.avanes.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "file")  // Storing the actual file content as binary data
    private byte[] file; // File content in binary format (byte array)

    @Column(name = "available_until")
    private LocalDateTime availableUntil;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    @Column(name = "description")
    private String description; // New description field

    @Column(name = "module_id")
    private Integer moduleId; // New moduleId field (foreign key for Module)
}
