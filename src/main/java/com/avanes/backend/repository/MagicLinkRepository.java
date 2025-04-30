package com.avanes.backend.repository;



import com.avanes.backend.models.Exam;
import com.avanes.backend.models.MagicLink;
import com.avanes.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MagicLinkRepository extends JpaRepository<MagicLink, Long> {
    Optional<MagicLink> findByRandomString(String randomString);
    List<MagicLink> findByUser(User user);
    List<MagicLink> findByExam(Exam exam);
    Optional<MagicLink> findByExamAndUser(Exam exam, User user);
    List<MagicLink> findByStatus(MagicLink.MagicLinkStatus status);
}