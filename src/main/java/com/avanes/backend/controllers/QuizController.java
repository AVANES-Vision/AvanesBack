package com.avanes.backend.controllers;

import com.avanes.backend.models.Quiz;
import com.avanes.backend.service.interfaces.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    QuizService quizService;
    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz){
        Quiz savedQuiz =quizService.saveQuiz(quiz);
        return ResponseEntity.ok(savedQuiz);
    }
    @PostMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id,@RequestBody Quiz quiz){
        quiz.setQuizId(id);
        Quiz updatedQuiz=quizService.updateQuiz(quiz);
        return ResponseEntity.ok(updatedQuiz);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id){
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id){
        Quiz quiz= quizService.getQuizById(id);
        return ResponseEntity.ok(quiz);
    }
    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes(){
        List<Quiz>quizzes=quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

}
