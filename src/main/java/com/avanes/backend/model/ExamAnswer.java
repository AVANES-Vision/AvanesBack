package com.avanes.backend.model;

import com.avanes.backend.models.Answer;
import com.avanes.backend.models.Exam;
import com.avanes.backend.models.Question;
import com.avanes.backend.models.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "exam_answer")
@Data
public class ExamAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
} 