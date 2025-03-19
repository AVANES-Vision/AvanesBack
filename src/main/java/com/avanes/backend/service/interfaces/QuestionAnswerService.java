package com.avanes.backend.service.interfaces;

import com.avanes.backend.models.QuestionAnswer;
import com.avanes.backend.models.QuestionAnswerId;

import java.util.List;

public interface QuestionAnswerService {
    QuestionAnswer saveQuestionAnswer(QuestionAnswer questionAnswer);
    QuestionAnswer updateQuestionAnswer(QuestionAnswer questionAnswer);
    void deleteQuestionAnswer(QuestionAnswerId id);
    QuestionAnswer getQuestionAnswerById(QuestionAnswerId id);
    List<QuestionAnswer> getAllQuestionAnswers();
    List<QuestionAnswer> getQuestionAnswersByQuestionId(Long questionId);
}
