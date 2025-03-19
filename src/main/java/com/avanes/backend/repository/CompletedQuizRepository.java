package com.avanes.backend.repository;

import com.avanes.backend.models.CompletedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long> {
    List<CompletedQuiz> findByUserUserId(Long userId);
}
