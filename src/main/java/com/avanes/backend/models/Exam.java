package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime startTimestamp;

    private Integer duration; // in minutes

    @Enumerated(EnumType.STRING)
    private ExamStatus status;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<ExamQuestion> examQuestions;

    @OneToMany(mappedBy = "exam", cascade = {}, orphanRemoval = false)
    private List<MagicLink> magicLinks;

    public enum ExamStatus {
        SCHEDULED, ONGOING, ENDED
    }
}