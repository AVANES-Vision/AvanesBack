package com.avanes.backend.service;

import com.avanes.backend.models.QuestionAnswer;
import com.avanes.backend.models.QuestionAnswerId;
import com.avanes.backend.repository.QuestionAnswerRepository;
import com.avanes.backend.service.interfaces.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {
    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Override
    public QuestionAnswer saveQuestionAnswer(QuestionAnswer questionAnswer) {
        return questionAnswerRepository.save(questionAnswer);
    }

    @Override
    public QuestionAnswer updateQuestionAnswer(QuestionAnswer questionAnswer) {
        return questionAnswerRepository.save(questionAnswer);
    }

    @Override
    public void deleteQuestionAnswer(QuestionAnswerId id) {
        questionAnswerRepository.deleteById(id);
    }

    @Override
    public QuestionAnswer getQuestionAnswerById(QuestionAnswerId id) {
        return questionAnswerRepository.findById(id).orElse(null);
    }

    @Override
    public List<QuestionAnswer> getAllQuestionAnswers() {
        return questionAnswerRepository.findAll();
    }

    @Override
    public List<QuestionAnswer> getQuestionAnswersByQuestionId(Long questionId) {
        return questionAnswerRepository.findByIdQuestionId(questionId);
    }

}
