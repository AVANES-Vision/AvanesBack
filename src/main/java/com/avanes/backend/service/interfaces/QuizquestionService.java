package com.avanes.backend.service.interfaces;

import com.avanes.backend.models.Quizquestion;

import java.util.List;

public interface QuizquestionService {
    List<Quizquestion> getAllQuestions();
    Quizquestion getQuestionById(Long id);
    List<Quizquestion> getQuestionsByQuizId(Long quizId);
    Quizquestion saveQuestion(Quizquestion quizQuestion);
    Quizquestion updateQuestion(Long id, Quizquestion quizQuestion);
    void deleteQuestion(Long id);
}

