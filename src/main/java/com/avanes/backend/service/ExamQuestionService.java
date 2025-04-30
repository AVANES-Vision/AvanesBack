package com.avanes.backend.service;


import com.avanes.backend.models.Exam;
import com.avanes.backend.models.ExamQuestion;
import com.avanes.backend.models.Question;
import com.avanes.backend.repository.ExamQuestionRepository;
import com.avanes.backend.repository.ExamRepository;
import com.avanes.backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ExamQuestionService {

    private final ExamQuestionRepository examQuestionRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public ExamQuestionService(
            ExamQuestionRepository examQuestionRepository,
            ExamRepository examRepository,
            QuestionRepository questionRepository) {
        this.examQuestionRepository = examQuestionRepository;
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
    }

    public List<ExamQuestion> getQuestionsByExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        return examQuestionRepository.findByExam(exam);
    }

    @Transactional
    public ExamQuestion addQuestionToExam(Long examId, Long questionId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

        // Check if the question is already added to the exam
        if (examQuestionRepository.existsByExamAndQuestion(exam, question)) {
            throw new RuntimeException("Question already added to this exam");
        }

        ExamQuestion examQuestion = new ExamQuestion();
        examQuestion.setExam(exam);
        examQuestion.setQuestion(question);

        return examQuestionRepository.save(examQuestion);
    }

    @Transactional
    public void removeQuestionFromExam(Long examId, Long questionId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

        List<ExamQuestion> examQuestions = examQuestionRepository.findByExam(exam);

        for (ExamQuestion eq : examQuestions) {
            if (eq.getQuestion().getId().equals(questionId)) {
                examQuestionRepository.delete(eq);
                break;
            }
        }
    }
}