package com.avanes.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data

@NoArgsConstructor
@Embeddable
public class QuestionAnswerId implements Serializable {
    @Column(name = "question_id")
    private Long questionId; // Part of the composite key, maps to Quizquestion.id

    @Column(name = "answer_id")
    private Long answerId;   // Part of the composite key, unique answer identifier

    public QuestionAnswerId(Long questionId, Long answerId) {
        this.answerId = answerId;
        this.questionId = questionId;
    }



    // equals() and hashCode() are required for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionAnswerId that = (QuestionAnswerId) o;
        return Objects.equals(questionId, that.questionId) &&
                Objects.equals(answerId, that.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, answerId);
    }

}
