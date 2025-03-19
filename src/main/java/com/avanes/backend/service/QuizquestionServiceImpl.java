package com.avanes.backend.service;

import com.avanes.backend.models.Quiz;
import com.avanes.backend.models.Quizquestion;
import com.avanes.backend.repository.QuizquestionRepository;
import com.avanes.backend.repository.QuizRepository;
import com.avanes.backend.service.interfaces.QuizquestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizquestionServiceImpl implements QuizquestionService {

    @Autowired
    private QuizquestionRepository quizQuestionRepository;

    @Autowired
    private QuizRepository quizRepository;

    // ✅ Get all quiz questions
    @Override
    public List<Quizquestion> getAllQuestions() {
        return quizQuestionRepository.findAll();
    }

    // ✅ Get a specific quiz question by ID
    @Override
    public Quizquestion getQuestionById(Long id) {
        return quizQuestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question Not Found"));
    }

    // ✅ Get all questions for a specific quiz
    @Override
    public List<Quizquestion> getQuestionsByQuizId(Long quizId) {
        return quizQuestionRepository.findByQuizQuizid(quizId);
    }

    // ✅ Save a new quiz question
    @Override
    public Quizquestion saveQuestion(Quizquestion quizQuestion) {
        return quizQuestionRepository.save(quizQuestion);
    }

    // ✅ Update an existing quiz question
    @Override
    public Quizquestion updateQuestion(Long id, Quizquestion quizQuestion) {
        Quizquestion existingQuestion = quizQuestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question Not Found"));

        // ✅ Update fields
        existingQuestion.setQuestion(quizQuestion.getQuestion());

        return quizQuestionRepository.save(existingQuestion);
    }

    // ✅ Delete a quiz question
    @Override
    public void deleteQuestion(Long id) {
        Quizquestion existingQuestion = quizQuestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question Not Found"));
        quizQuestionRepository.delete(existingQuestion);
    }
}
