package com.avanes.backend.controllers;

import com.avanes.backend.models.MagicLink;
import com.avanes.backend.service.MagicLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/magic-links")
public class MagicLinkController {

    private final MagicLinkService magicLinkService;

    @Autowired
    public MagicLinkController(MagicLinkService magicLinkService) {
        this.magicLinkService = magicLinkService;
    }

    @GetMapping
    public ResponseEntity<List<MagicLink>> getAllMagicLinks() {
        return ResponseEntity.ok(magicLinkService.getAllMagicLinks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MagicLink> getMagicLinkById(@PathVariable Long id) {
        Optional<MagicLink> magicLink = magicLinkService.getMagicLinkById(id);
        return magicLink.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/string/{randomString}")
    public ResponseEntity<MagicLink> getMagicLinkByRandomString(@PathVariable String randomString) {
        Optional<MagicLink> magicLink = magicLinkService.getMagicLinkByRandomString(randomString);
        return magicLink.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MagicLink>> getMagicLinksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(magicLinkService.getMagicLinksByUser(userId));
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<MagicLink>> getMagicLinksByExam(@PathVariable Long examId) {
        return ResponseEntity.ok(magicLinkService.getMagicLinksByExam(examId));
    }

    @PostMapping
    public ResponseEntity<MagicLink> createMagicLink(@RequestBody Map<String, Long> request) {
        Long examId = request.get("examId");
        Long userId = request.get("userId");

        if (examId == null || userId == null) {
            return ResponseEntity.badRequest().build();
        }

        MagicLink magicLink = magicLinkService.createMagicLink(examId, userId);
        return new ResponseEntity<>(magicLink, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MagicLink> updateMagicLinkStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {

        String statusStr = (String) request.get("status");
        String ip = (String) request.get("ip");
        String hwid = (String) request.get("hwid");

        if (statusStr == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            MagicLink.MagicLinkStatus status = MagicLink.MagicLinkStatus.valueOf(statusStr);
            MagicLink updatedLink = magicLinkService.updateMagicLinkStatus(id, status, ip, hwid);
            return ResponseEntity.ok(updatedLink);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/exam/{examId}/invalidate")
    public ResponseEntity<Void> invalidateAllMagicLinksForExam(@PathVariable Long examId) {
        try {
            magicLinkService.invalidateAllMagicLinksForExam(examId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validateAccess(@RequestBody Map<String, String> request) {
        String magicLinkString = request.get("magicLinkString");
        String ip = request.get("ip");
        String hwid = request.get("hwid");

        if (magicLinkString == null || ip == null || hwid == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean isValid = magicLinkService.validateAccess(magicLinkString, ip, hwid);
        Map<String, Boolean> response = Map.of("valid", isValid);
        return ResponseEntity.ok(response);
    }
}