package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private String title;

    private String content;

    private String author;

    private String module;

    private LocalDateTime scheduleDateTime;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    private LocalDateTime publishedDate;

    private Integer updatedUser;

    Notice(String title, String content, String author, LocalDateTime scheduleDateTime){
        this.title = title;
        this.content = content;
        this.author = author;
        this.scheduleDateTime = scheduleDateTime;
    }
}