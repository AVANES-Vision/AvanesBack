package com.avanes.backend.service;

import com.avanes.backend.models.CompletedQuizAnswer;
import com.avanes.backend.repository.CompletedQuizAnswerRepository;
import com.avanes.backend.service.interfaces.CompletedQuizAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedQuizAnswerServiceImpl implements CompletedQuizAnswerService {

    @Autowired
    private CompletedQuizAnswerRepository completedQuizAnswerRepository;

    @Override
    public CompletedQuizAnswer saveCompletedQuizAnswer(CompletedQuizAnswer completedQuizAnswer) {
        return completedQuizAnswerRepository.save(completedQuizAnswer);
    }

    @Override
    public List<CompletedQuizAnswer> getCompletedQuizAnswers(Long completedId) {
        return completedQuizAnswerRepository.findByCompletedQuizCompletedId(completedId);
    }
}
