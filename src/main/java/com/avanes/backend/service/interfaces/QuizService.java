package com.avanes.backend.service.interfaces;
import com.avanes.backend.models.Quiz;

import java.util.List;
public interface QuizService {
    Quiz saveQuiz(Quiz quiz);
    Quiz updateQuiz(Quiz quiz);
    void deleteQuiz(Long quizId);
    Quiz getQuizById(Long quizId);
    List<Quiz>getAllQuizzes();


}
