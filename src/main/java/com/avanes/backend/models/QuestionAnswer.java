package com.avanes.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question_answer")

//@IdClass(QuestionAnswerId.class) // Composite Key
public class QuestionAnswer {
    @EmbeddedId
    private QuestionAnswerId id; // Composite key embedding questionId and answerId

    @ManyToOne
    @MapsId("questionId") // Maps questionId from the composite key to this relationship
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
    private Quizquestion question; // Foreign key reference to Quizquestion

    private Integer answerNo;
    private String answerText;
    private Boolean isCorrect;

    @Lob
    private byte[] answerVoice;


}



