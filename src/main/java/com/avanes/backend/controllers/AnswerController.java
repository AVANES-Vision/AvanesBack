package com.avanes.backend.controllers;


import com.avanes.backend.models.Answer;
import com.avanes.backend.service.AnswerService;
import com.avanes.backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v2/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @Autowired
    public AnswerController(
            AnswerService answerService,
            QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<Answer>> getAllAnswers() {
        return ResponseEntity.ok(answerService.getAllAnswers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable Long id) {
        return answerService.getAnswerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Answer>> getAnswersByQuestion(@PathVariable Long questionId) {
        return questionService.getQuestionById(questionId)
                .map(question -> ResponseEntity.ok(answerService.getAnswersByQuestion(question)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/question/{questionId}")
    public ResponseEntity<Answer> createAnswer(
            @PathVariable Long questionId,
            @RequestBody Answer answer) {

        return questionService.getQuestionById(questionId)
                .map(question -> {
                    answer.setQuestion(question);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(answerService.createAnswer(answer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Answer> updateAnswer(
            @PathVariable Long id,
            @RequestBody Answer answer) {

        return answerService.getAnswerById(id)
                .map(existingAnswer -> {
                    answer.setId(id);
                    answer.setQuestion(existingAnswer.getQuestion());
                    return ResponseEntity.ok(answerService.updateAnswer(answer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        if (!answerService.getAnswerById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}