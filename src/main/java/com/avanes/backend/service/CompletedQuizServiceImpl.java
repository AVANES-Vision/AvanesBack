package com.avanes.backend.service;

import com.avanes.backend.models.CompletedQuiz;
import com.avanes.backend.repository.CompletedQuizRepository;
import com.avanes.backend.service.interfaces.CompletedQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedQuizServiceImpl implements CompletedQuizService {

    @Autowired
    private CompletedQuizRepository completedQuizRepository;

    @Override
    public CompletedQuiz saveCompletedQuiz(CompletedQuiz completedQuiz) {
        return completedQuizRepository.save(completedQuiz);
    }

    @Override
    public CompletedQuiz getCompletedQuizById(Long id) {
        return completedQuizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Completed Quiz Not Found"));
    }

    @Override
    public List<CompletedQuiz> getCompletedQuizzesByUser(Long userId) {
        return completedQuizRepository.findByUserUserId(userId);
    }

    @Override
    public void deleteCompletedQuiz(Long id) {
        completedQuizRepository.deleteById(id);
    }
}
