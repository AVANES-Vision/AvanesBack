package com.avanes.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String title;
    private String content;
    private String author;
    private int year;
    public Notice(int id, String title, String content, String author, int year) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.year = year;
    }
}
