package com.avanes.backend.controllers;

import com.avanes.backend.models.Quiz;
import com.avanes.backend.models.Quizquestion;
import com.avanes.backend.repository.QuizRepository;
import com.avanes.backend.service.interfaces.QuizquestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuizquestionController {

    private final QuizquestionService quizQuestionService;
    private final QuizRepository quizRepository;

    @Autowired
    public QuizquestionController(QuizquestionService quizQuestionService, QuizRepository quizRepository) {
        this.quizQuestionService = quizQuestionService;
        this.quizRepository = quizRepository;
    }

    // ✅ Create a new Quiz Question
    @PostMapping("/quiz/{quizId}")
    public ResponseEntity<Quizquestion> createQuestion(@PathVariable Long quizId, @RequestBody Quizquestion quizQuestion) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz Not Found"));
        quizQuestion.setQuiz(quiz);
        Quizquestion savedQuestion = quizQuestionService.saveQuestion(quizQuestion);
        return ResponseEntity.ok(savedQuestion);
    }

    // ✅ Update an existing Quiz Question
    @PutMapping("/{id}")
    public ResponseEntity<Quizquestion> updateQuestion(@PathVariable Long id, @RequestBody Quizquestion quizQuestion) {
        Quizquestion updatedQuestion = quizQuestionService.updateQuestion(id, quizQuestion);
        return ResponseEntity.ok(updatedQuestion);
    }

    // ✅ Delete a Quiz Question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        quizQuestionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get a Quiz Question by ID
    @GetMapping("/{id}")
    public ResponseEntity<Quizquestion> getQuestionById(@PathVariable Long id) {
        Quizquestion question = quizQuestionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    // ✅ Get All Quiz Questions
    @GetMapping
    public ResponseEntity<List<Quizquestion>> getAllQuestions() {
        List<Quizquestion> questions = quizQuestionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    // ✅ Get All Questions for a Specific Quiz
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Quizquestion>> getQuestionsByQuizId(@PathVariable Long quizId) {
        List<Quizquestion> questions = quizQuestionService.getQuestionsByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }
}




