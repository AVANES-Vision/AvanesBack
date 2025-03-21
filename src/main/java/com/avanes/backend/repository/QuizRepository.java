package com.avanes.backend.repository;

import com.avanes.backend.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    Quiz findByTitle(String title);
}
