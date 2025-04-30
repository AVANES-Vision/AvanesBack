package com.avanes.backend.controllers;

import com.avanes.backend.models.Exam;
import com.avanes.backend.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/exams")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable Long id) {
        return examService.getExamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Exam>> getExamsByStatus(@PathVariable Exam.ExamStatus status) {
        return ResponseEntity.ok(examService.getExamsByStatus(status));
    }

    @PostMapping
    public ResponseEntity<Exam> createExam(
            @RequestBody Map<String, Object> request) {

        Exam exam = new Exam();
        exam.setTitle((String) request.get("title"));
        exam.setStartTimestamp(java.time.LocalDateTime.parse((String) request.get("startTimestamp")));
        exam.setDuration((Integer) request.get("duration"));
        exam.setStatus(Exam.ExamStatus.valueOf((String) request.get("status")));

        @SuppressWarnings("unchecked")
        List<Long> questionIds = (List<Long>) request.get("questionIds");

        @SuppressWarnings("unchecked")
        List<Long> userIds = (List<Long>) request.get("userIds");

        Exam createdExam = examService.createExam(exam, questionIds, userIds);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdExam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> updateExam(
            @PathVariable Long id,
            @RequestBody Exam exam) {

        return examService.getExamById(id)
                .map(existingExam -> {
                    exam.setId(id);
                    return ResponseEntity.ok(examService.updateExam(exam));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateExamStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> status) {

        if (!examService.getExamById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Exam.ExamStatus newStatus = Exam.ExamStatus.valueOf(status.get("status"));
        examService.updateExamStatus(id, newStatus);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        if (!examService.getExamById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
}