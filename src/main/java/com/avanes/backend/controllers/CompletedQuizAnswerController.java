package com.avanes.backend.controllers;

import com.avanes.backend.models.CompletedQuizAnswer;
import com.avanes.backend.service.interfaces.CompletedQuizAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/completed-quiz-answers")
public class CompletedQuizAnswerController {

    @Autowired
    private CompletedQuizAnswerService completedQuizAnswerService;

    @PostMapping
    public ResponseEntity<CompletedQuizAnswer> createCompletedQuizAnswer(@RequestBody CompletedQuizAnswer answer) {
        CompletedQuizAnswer savedAnswer = completedQuizAnswerService.saveCompletedQuizAnswer(answer);
        return ResponseEntity.ok(savedAnswer);
    }

    @GetMapping("/{completedId}")
    public ResponseEntity<List<CompletedQuizAnswer>> getCompletedQuizAnswers(@PathVariable Long completedId) {
        return ResponseEntity.ok(completedQuizAnswerService.getCompletedQuizAnswers(completedId));
    }
}

