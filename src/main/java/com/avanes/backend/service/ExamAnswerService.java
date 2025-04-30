package com.avanes.backend.service;


import com.avanes.backend.models.*;
import com.avanes.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamAnswerService {

    private final ExamAnswerRepository examAnswerRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public ExamAnswerService(
            ExamAnswerRepository examAnswerRepository,
            ExamRepository examRepository,
            UserRepository userRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository) {
        this.examAnswerRepository = examAnswerRepository;
        this.examRepository = examRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public List<ExamAnswer> getAnswersByExamAndUser(Long examId, Long userId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        User user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        return examAnswerRepository.findByExamAndUser(exam, user);
    }

    @Transactional
    public ExamAnswer saveAnswer(Long examId, Long userId, Long questionId, Long answerId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        User user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found with ID: " + answerId));

        // Check if this question belongs to this exam
        if (!exam.getExamQuestions().stream()
                .anyMatch(eq -> eq.getQuestion().getId().equals(questionId))) {
            throw new RuntimeException("Question doesn't belong to this exam");
        }

        // Check if this answer belongs to this question
        if (!answer.getQuestion().getId().equals(questionId)) {
            throw new RuntimeException("Answer doesn't belong to this question");
        }

        // Check if the exam is ongoing
        if (exam.getStatus() != Exam.ExamStatus.ONGOING) {
            throw new RuntimeException("Exam is not in progress");
        }

        // Find existing answer if any
        Optional<ExamAnswer> existingAnswer = examAnswerRepository
                .findByExamAndUserAndQuestion(exam, user, question);

        ExamAnswer examAnswer;
        if (existingAnswer.isPresent()) {
            examAnswer = existingAnswer.get();
            examAnswer.setAnswer(answer);
        } else {
            examAnswer = new ExamAnswer();
            examAnswer.setExam(exam);
            examAnswer.setUser(user);
            examAnswer.setQuestion(question);
            examAnswer.setAnswer(answer);
        }

        return examAnswerRepository.save(examAnswer);
    }

    public int calculateScore(Long examId, Long userId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        User user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        List<ExamAnswer> userAnswers = examAnswerRepository.findByExamAndUser(exam, user);

        // Count correct answers
        int correctAnswers = 0;
        for (ExamAnswer examAnswer : userAnswers) {
            if (examAnswer.getAnswer().isCorrect()) {
                correctAnswers++;
            }
        }

        return correctAnswers;
    }

    public int getTotalQuestions(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        return exam.getExamQuestions().size();
    }

    public List<ExamAnswer> getAllAnswersForExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        return examAnswerRepository.findByExam(exam);
    }
}