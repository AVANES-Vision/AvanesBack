package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MagicLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    @JsonIgnore
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String randomString;
    private String ip;
    private String hwid; // Hardware ID

    @Enumerated(EnumType.STRING)
    private MagicLinkStatus status;

    @JsonProperty("examId")
    public Long getExamId() {
        return exam != null ? exam.getId() : null;
    }

    public enum MagicLinkStatus {
        ACTIVE, USED, EXPIRED
    }
}