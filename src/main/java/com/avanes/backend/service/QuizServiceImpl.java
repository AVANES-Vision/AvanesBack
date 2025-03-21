package com.avanes.backend.service;

import com.avanes.backend.models.Quiz;
import com.avanes.backend.repository.QuizRepository;
import com.avanes.backend.service.interfaces.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepository;
    @Override
    public Quiz saveQuiz(Quiz quiz){return quizRepository.save(quiz);}

    @Override
    public Quiz updateQuiz(Quiz quiz){return quizRepository.save(quiz);}

    @Override
    public void deleteQuiz(Long quizId) {quizRepository.deleteById(quizId);}

    @Override
    public Quiz getQuizById(Long  quizid){return quizRepository.findById(quizid).orElse(null);}

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

}
