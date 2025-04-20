package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false, columnDefinition = "ENUM('QUIZ_PUBLISHED', 'ASSIGNMENT_DUE', 'NEW_MATERIAL', 'ANNOUNCEMENT', 'GRADE_RELEASED', 'NOTICE_PUBLISHED','OTHER')")
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(nullable = false, columnDefinition = "ENUM('ALL_USERS', 'MODULE_STUDENTS', 'COURSE_STUDENTS', 'SPECIFIC_USER')")
    @Enumerated(EnumType.STRING)
    private NotificationAudience audience;

    private String audienceId; // For specific module, course, etc.

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum NotificationType {
        QUIZ_PUBLISHED,
        ASSIGNMENT_DUE,
        NEW_MATERIAL,
        ANNOUNCEMENT,
        GRADE_RELEASED,
        NOTICE_PUBLISHED,
        OTHER
    }

    public enum NotificationAudience {
        ALL_USERS,
        MODULE_STUDENTS,
        COURSE_STUDENTS,
        SPECIFIC_USER
    }
} 