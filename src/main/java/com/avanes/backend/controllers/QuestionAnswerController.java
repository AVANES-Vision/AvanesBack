package com.avanes.backend.controllers;

import com.avanes.backend.models.QuestionAnswer;
import com.avanes.backend.models.QuestionAnswerId;
import com.avanes.backend.service.interfaces.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question-answers")
public class QuestionAnswerController {
    @Autowired
    private QuestionAnswerService questionAnswerService;

    @PostMapping
    public ResponseEntity<QuestionAnswer> createQuestionAnswer(@RequestBody QuestionAnswer questionAnswer) {
        QuestionAnswer savedQuestionAnswer = questionAnswerService.saveQuestionAnswer(questionAnswer);
        return ResponseEntity.ok(savedQuestionAnswer);
    }

    @PutMapping
    public ResponseEntity<QuestionAnswer> updateQuestionAnswer(@RequestBody QuestionAnswer questionAnswer) {
        QuestionAnswer updatedQuestionAnswer = questionAnswerService.updateQuestionAnswer(questionAnswer);
        return ResponseEntity.ok(updatedQuestionAnswer);
    }

    @DeleteMapping("/{questionId}/{answerId}")
    public ResponseEntity<Void> deleteQuestionAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
        QuestionAnswerId questionAnswerId = new QuestionAnswerId(questionId, answerId);
        questionAnswerService.deleteQuestionAnswer(questionAnswerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{questionId}/{answerId}")
    public ResponseEntity<QuestionAnswer> getQuestionAnswerById(@PathVariable Long questionId, @PathVariable Long answerId) {
        QuestionAnswerId questionAnswerId = new QuestionAnswerId(questionId, answerId);
        QuestionAnswer questionAnswer = questionAnswerService.getQuestionAnswerById(questionAnswerId);
        return ResponseEntity.ok(questionAnswer);
    }

    @GetMapping
    public ResponseEntity<List<QuestionAnswer>> getAllQuestionAnswers() {
        List<QuestionAnswer> questionAnswers = questionAnswerService.getAllQuestionAnswers();
        return ResponseEntity.ok(questionAnswers);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<QuestionAnswer>> getQuestionAnswersByQuestionId(@PathVariable Long questionId) {
        List<QuestionAnswer> questionAnswers = questionAnswerService.getQuestionAnswersByQuestionId(questionId);
        return ResponseEntity.ok(questionAnswers);
    }
}
