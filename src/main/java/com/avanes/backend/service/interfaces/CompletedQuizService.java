package com.avanes.backend.service.interfaces;

import com.avanes.backend.models.CompletedQuiz;

import java.util.List;

public interface CompletedQuizService {
    CompletedQuiz saveCompletedQuiz(CompletedQuiz completedQuiz);
    CompletedQuiz getCompletedQuizById(Long id);
    List<CompletedQuiz> getCompletedQuizzesByUser(Long userId);
    void deleteCompletedQuiz(Long id);
}

