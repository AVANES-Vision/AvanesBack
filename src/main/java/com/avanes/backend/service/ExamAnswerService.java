package com.avanes.backend.service;

import com.avanes.backend.model.ExamAnswer;
import com.avanes.backend.repository.ExamAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamAnswerService {

    private final ExamAnswerRepository examAnswerRepository;

    @Autowired
    public ExamAnswerService(ExamAnswerRepository examAnswerRepository) {
        this.examAnswerRepository = examAnswerRepository;
    }

    public List<ExamAnswer> getAllExamAnswers() {
        return examAnswerRepository.findAll();
    }

    public Optional<ExamAnswer> getExamAnswerById(Long id) {
        return examAnswerRepository.findById(id);
    }

    public ExamAnswer createExamAnswer(ExamAnswer examAnswer) {
        return examAnswerRepository.save(examAnswer);
    }

    public ExamAnswer updateExamAnswer(Long id, ExamAnswer examAnswerDetails) {
        ExamAnswer examAnswer = examAnswerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ExamAnswer not found with id: " + id));

        examAnswer.setAnswer(examAnswerDetails.getAnswer());
        examAnswer.setExam(examAnswerDetails.getExam());
        examAnswer.setQuestion(examAnswerDetails.getQuestion());
        examAnswer.setUser(examAnswerDetails.getUser());

        return examAnswerRepository.save(examAnswer);
    }

    public void deleteExamAnswer(Long id) {
        examAnswerRepository.deleteById(id);
    }
}