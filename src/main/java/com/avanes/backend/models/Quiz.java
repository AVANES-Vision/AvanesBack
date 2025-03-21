package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;
    private String title;
    private String subtitle;
    private Integer noOfQuiz;
    private Integer timeLimit;
    private LocalDate scheduleDate;
    private LocalTime scheduleTime;
    private String quizStatus;

    @ManyToOne
    @JoinColumn(name="userId",nullable = false)
    private User user;

    public void setQuizId(Long id) {
    }
}
