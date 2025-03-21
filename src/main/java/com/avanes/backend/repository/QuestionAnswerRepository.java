package com.avanes.backend.repository;

import com.avanes.backend.models.QuestionAnswer;
import com.avanes.backend.models.QuestionAnswerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, QuestionAnswerId> {
    List<QuestionAnswer> findByIdQuestionId(Long questionId);
}
