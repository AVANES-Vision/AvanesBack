package com.avanes.backend.repository;



import com.avanes.backend.models.Exam;
import com.avanes.backend.models.ExamAnswer;
import com.avanes.backend.models.Question;
import com.avanes.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Long> {
    List<ExamAnswer> findByExam(Exam exam);
    List<ExamAnswer> findByUser(User user);
    List<ExamAnswer> findByExamAndUser(Exam exam, User user);
    Optional<ExamAnswer> findByExamAndUserAndQuestion(Exam exam, User user, Question question);
    List<ExamAnswer> findByQuestion(Question question);
}