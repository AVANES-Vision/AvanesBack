package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MagicLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String randomString;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String ip;

    private String hwid; // Hardware ID

    @Enumerated(EnumType.STRING)
    private MagicLinkStatus status;

    public enum MagicLinkStatus {
        ACTIVE, USED, EXPIRED
    }
}