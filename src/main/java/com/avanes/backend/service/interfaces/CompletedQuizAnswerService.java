package com.avanes.backend.service.interfaces;

import com.avanes.backend.models.CompletedQuizAnswer;

import java.util.List;

public interface CompletedQuizAnswerService {
    CompletedQuizAnswer saveCompletedQuizAnswer(CompletedQuizAnswer completedQuizAnswer);
    List<CompletedQuizAnswer> getCompletedQuizAnswers(Long completedId);
}
