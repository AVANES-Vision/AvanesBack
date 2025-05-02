package com.avanes.backend.controllers;

import com.avanes.backend.model.ExamAnswer;
import com.avanes.backend.service.ExamAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exam-answers")
public class ExamAnswerController {

    private final ExamAnswerService examAnswerService;

    @Autowired
    public ExamAnswerController(ExamAnswerService examAnswerService) {
        this.examAnswerService = examAnswerService;
    }

    @GetMapping
    public List<ExamAnswer> getAllExamAnswers() {
        return examAnswerService.getAllExamAnswers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamAnswer> getExamAnswerById(@PathVariable Long id) {
        return examAnswerService.getExamAnswerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ExamAnswer createExamAnswer(@RequestBody ExamAnswer examAnswer) {
        return examAnswerService.createExamAnswer(examAnswer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamAnswer> updateExamAnswer(
            @PathVariable Long id,
            @RequestBody ExamAnswer examAnswerDetails) {
        try {
            ExamAnswer updatedExamAnswer = examAnswerService.updateExamAnswer(id, examAnswerDetails);
            return ResponseEntity.ok(updatedExamAnswer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamAnswer(@PathVariable Long id) {
        try {
            examAnswerService.deleteExamAnswer(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 