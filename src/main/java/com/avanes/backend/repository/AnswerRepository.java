package com.avanes.backend.repository;



import com.avanes.backend.models.Answer;
import com.avanes.backend.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion(Question question);
    List<Answer> findByQuestionAndIsCorrect(Question question, boolean isCorrect);
}