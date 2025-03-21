package com.avanes.backend.repository;

import com.avanes.backend.models.CompletedQuizAnswer;
import com.avanes.backend.models.CompletedQuizAnswerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompletedQuizAnswerRepository extends JpaRepository<CompletedQuizAnswer, CompletedQuizAnswerId> {
    List<CompletedQuizAnswer> findByCompletedQuizCompletedId(Long completedId);
}

