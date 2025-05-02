package com.avanes.backend.service;

import com.avanes.backend.models.Exam;
import com.avanes.backend.models.MagicLink;
import com.avanes.backend.models.User;
import com.avanes.backend.repository.ExamRepository;
import com.avanes.backend.repository.MagicLinkRepository;
import com.avanes.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MagicLinkService {

    private final MagicLinkRepository magicLinkRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    @Autowired
    public MagicLinkService(
            MagicLinkRepository magicLinkRepository,
            ExamRepository examRepository,
            UserRepository userRepository) {
        this.magicLinkRepository = magicLinkRepository;
        this.examRepository = examRepository;
        this.userRepository = userRepository;
    }

    public List<MagicLink> getAllMagicLinks() {
        return magicLinkRepository.findAll();
    }

    public Optional<MagicLink> getMagicLinkById(Long id) {
        return magicLinkRepository.findById(id);
    }

    public Optional<MagicLink> getMagicLinkByRandomString(String randomString) {
        return magicLinkRepository.findByRandomString(randomString);
    }

    public List<MagicLink> getMagicLinksByUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        return magicLinkRepository.findByUser(user);
    }

    public List<MagicLink> getMagicLinksByExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        return magicLinkRepository.findByExam(exam);
    }

    @Transactional
    public MagicLink createMagicLink(Long examId, Long userId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        User user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Check if magic link already exists for this exam and user
        Optional<MagicLink> existingLink = magicLinkRepository.findByExamAndUser(exam, user);
        if (existingLink.isPresent()) {
            System.out.println("Magic link already exists for exam ID: " + examId + " and user ID: " + userId);
            return existingLink.get();
        }

        MagicLink magicLink = new MagicLink();
        magicLink.setExam(exam);
        magicLink.setUser(user);
        magicLink.setRandomString(UUID.randomUUID().toString());
        magicLink.setStatus(MagicLink.MagicLinkStatus.ACTIVE);

        System.out.println("Creating new magic link for exam ID: " + examId + " and user ID: " + userId);
        MagicLink savedLink = magicLinkRepository.save(magicLink);
        System.out.println("Magic link created successfully with ID: " + savedLink.getId());
        
        return savedLink;
    }

    @Transactional
    public MagicLink updateMagicLinkStatus(Long id, MagicLink.MagicLinkStatus status, String ip, String hwid) {
        MagicLink magicLink = magicLinkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Magic link not found with ID: " + id));

        magicLink.setStatus(status);
        magicLink.setIp(ip);
        magicLink.setHwid(hwid);

        return magicLinkRepository.save(magicLink);
    }

    @Transactional
    public void invalidateAllMagicLinksForExam(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        List<MagicLink> magicLinks = magicLinkRepository.findByExam(exam);

        for (MagicLink link : magicLinks) {
            link.setStatus(MagicLink.MagicLinkStatus.EXPIRED);
            magicLinkRepository.save(link);
        }
    }

    @Transactional
    public boolean validateAccess(String magicLinkString, String ip, String hwid) {
        Optional<MagicLink> magicLinkOpt = magicLinkRepository.findByRandomString(magicLinkString);

        if (magicLinkOpt.isEmpty()) {
            return false;
        }

        MagicLink magicLink = magicLinkOpt.get();

        // If link is not active, access is denied
        if (magicLink.getStatus() != MagicLink.MagicLinkStatus.ACTIVE) {
            return false;
        }

        // If link is already associated with different IP/HWID, access is denied
        if (magicLink.getIp() != null && !magicLink.getIp().isEmpty() &&
                !magicLink.getIp().equals(ip)) {
            return false;
        }

        if (magicLink.getHwid() != null && !magicLink.getHwid().isEmpty() &&
                !magicLink.getHwid().equals(hwid)) {
            return false;
        }

        // Update magic link with user's current IP and HWID
        magicLink.setIp(ip);
        magicLink.setHwid(hwid);
        magicLinkRepository.save(magicLink);

        return true;
    }

    @Transactional
    public void verifyMagicLinkExamId(Long magicLinkId) {
        MagicLink magicLink = magicLinkRepository.findById(magicLinkId)
                .orElseThrow(() -> new RuntimeException("Magic link not found with ID: " + magicLinkId));
        
        if (magicLink.getExam() == null) {
            System.err.println("ERROR: Magic link " + magicLinkId + " has no exam associated with it!");
        } else {
            System.out.println("Magic link " + magicLinkId + " is correctly associated with exam ID: " + magicLink.getExam().getId());
        }
    }
}