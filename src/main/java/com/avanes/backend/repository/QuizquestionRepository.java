package com.avanes.backend.repository;

import com.avanes.backend.models.Quizquestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizquestionRepository extends JpaRepository<Quizquestion, Long> {
    List<Quizquestion> findByQuizQuizId(Long quizId);
}

