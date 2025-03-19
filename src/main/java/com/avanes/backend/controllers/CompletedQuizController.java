package com.avanes.backend.controllers;

import com.avanes.backend.models.CompletedQuiz;
import com.avanes.backend.service.interfaces.CompletedQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/completed-quizzes")
public class CompletedQuizController {

    @Autowired
    private CompletedQuizService completedQuizService;

    @PostMapping
    public ResponseEntity<CompletedQuiz> createCompletedQuiz(@RequestBody CompletedQuiz completedQuiz) {
        CompletedQuiz savedQuiz = completedQuizService.saveCompletedQuiz(completedQuiz);
        return ResponseEntity.ok(savedQuiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompletedQuiz> getCompletedQuiz(@PathVariable Long id) {
        CompletedQuiz quiz = completedQuizService.getCompletedQuizById(id);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CompletedQuiz>> getUserCompletedQuizzes(@PathVariable Long userId) {
        return ResponseEntity.ok(completedQuizService.getCompletedQuizzesByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompletedQuiz(@PathVariable Long id) {
        completedQuizService.deleteCompletedQuiz(id);
        return ResponseEntity.noContent().build();
    }
}
