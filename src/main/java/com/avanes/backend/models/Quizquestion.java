package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_question")
public class Quizquestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private String question;
    private int questionNo;

    @Lob
    private byte[] voice;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "quizId", nullable = false)
    private Quiz quiz;
}

