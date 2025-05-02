package com.avanes.backend.service;

import com.avanes.backend.models.Exam;
import com.avanes.backend.models.MagicLink;
import com.avanes.backend.models.User;
import com.avanes.backend.repository.ExamRepository;
import com.avanes.backend.repository.MagicLinkRepository;
import com.avanes.backend.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamQuestionService examQuestionService;
    private final MagicLinkRepository magicLinkRepository;
    private final UserService userService;
    private final MagicLinkService magicLinkService;

    @Autowired
    public ExamService(
            ExamRepository examRepository,
            ExamQuestionService examQuestionService,
            MagicLinkRepository magicLinkRepository,
            UserService userService,
            MagicLinkService magicLinkService) {
        this.examRepository = examRepository;
        this.examQuestionService = examQuestionService;
        this.magicLinkRepository = magicLinkRepository;
        this.userService = userService;
        this.magicLinkService = magicLinkService;
    }

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }

    public List<Exam> getExamsByStatus(Exam.ExamStatus status) {
        return examRepository.findByStatus(status);
    }

    @Transactional
    public Exam createExam(Exam exam, List<Long> questionIds, List<Long> userIds) {
        // Save the exam first
        exam = examRepository.save(exam);

        // Add questions to the exam
        for (Long questionId : questionIds) {
            examQuestionService.addQuestionToExam(exam.getId(), questionId);
        }

        // Generate magic links for each user
        for (Long userId : userIds) {
            User user = userService.getUserById(Math.toIntExact(userId));

            generateMagicLink(exam, user);
        }

        return exam;
    }

    private MagicLink generateMagicLink(Exam exam, User user) {
        MagicLink magicLink = new MagicLink();
        magicLink.setExam(exam);
        magicLink.setUser(user);
        magicLink.setRandomString(UUID.randomUUID().toString());
        magicLink.setStatus(MagicLink.MagicLinkStatus.ACTIVE);

        return magicLinkRepository.save(magicLink);
    }

    @Transactional
    public Exam updateExam(Exam exam) {
        return examRepository.save(exam);
    }

    @Transactional
    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }

    @Transactional
    public void updateExamStatus(Long examId, Exam.ExamStatus status) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        // Get current status
        Exam.ExamStatus currentStatus = exam.getStatus();
        
        // Update exam status
        exam.setStatus(status);
        exam = examRepository.save(exam);

        // Handle magic links based on status change
        if (status == Exam.ExamStatus.ENDED) {
            // When exam ends, invalidate all magic links
            magicLinkService.invalidateAllMagicLinksForExam(examId);
        } else if (currentStatus == Exam.ExamStatus.ENDED && status != Exam.ExamStatus.ENDED) {
            // If exam is being reactivated from ENDED state, we might want to handle this case
            System.out.println("Exam " + examId + " is being reactivated from ENDED state");
        }
    }

    public void checkAndUpdateExamStatuses() {
        LocalDateTime now = LocalDateTime.now();

        // Find all scheduled exams
        List<Exam> scheduledExams = examRepository.findByStatus(Exam.ExamStatus.SCHEDULED);

        for (Exam exam : scheduledExams) {
            // If start time has passed, change status to ONGOING
            if (exam.getStartTimestamp().isBefore(now)) {
                exam.setStatus(Exam.ExamStatus.ONGOING);
                examRepository.save(exam);
            }
        }

        // Find all ongoing exams
        List<Exam> ongoingExams = examRepository.findByStatus(Exam.ExamStatus.ONGOING);

        for (Exam exam : ongoingExams) {
            // If exam has ended (start time + duration), change status to ENDED
            LocalDateTime endTime = exam.getStartTimestamp().plusMinutes(exam.getDuration());
            if (endTime.isBefore(now)) {
                exam.setStatus(Exam.ExamStatus.ENDED);
                examRepository.save(exam);
            }
        }
    }
}