package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "quizId", nullable = false)
    private Quiz quiz;

    // ✅ Corrected getter method
    public Quiz getQuiz() {
        return quiz;  // ✅ Changed from "Quiz" to "quiz" (correct variable name)
    }

    // ✅ Corrected setter method
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}







