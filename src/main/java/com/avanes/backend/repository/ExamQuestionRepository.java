package com.avanes.backend.repository;


import com.avanes.backend.models.Exam;
import com.avanes.backend.models.ExamQuestion;
import com.avanes.backend.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {
    List<ExamQuestion> findByExam(Exam exam);
    List<ExamQuestion> findByQuestion(Question question);
    boolean existsByExamAndQuestion(Exam exam, Question question);
}