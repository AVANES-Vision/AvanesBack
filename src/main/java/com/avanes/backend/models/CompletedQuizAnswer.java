package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "completed_quiz_answer")
public class CompletedQuizAnswer {

    @EmbeddedId
    private CompletedQuizAnswerId id;

    @ManyToOne
    @MapsId("completedId")
    @JoinColumn(name = "completedid", nullable = false)
    private CompletedQuiz completedQuiz;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "questionid", nullable = false)
    private Quizquestion question;

    @Column(name = "given_answer", nullable = false)
    private String givenAnswer;
}
