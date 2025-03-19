package com.avanes.backend.controllers;

import com.avanes.backend.dto.GlobalResponse;
import com.avanes.backend.dto.GetNoticeDto;
import com.avanes.backend.dto.GetNoticesDto;
import com.avanes.backend.models.Notice;
import com.avanes.backend.service.interfaces.NoticeService;
import com.avanes.backend.utils.ResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<GetNoticesDto>>> getAllNotices() {
        List<GetNoticesDto> notices = noticeService.getAllNotices();
        return ResponseHandler.success(notices, "Notices retrieved successfully");
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<GlobalResponse<GetNoticeDto>> getNotice(@PathVariable Integer noticeId) {
        GetNoticeDto notice = noticeService.getNotice(noticeId);
        return ResponseHandler.success(notice, "Notice retrieved successfully");
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<GetNoticeDto>> addNotice(@RequestHeader("Authorization") String token, @RequestBody Notice notice) {
        GetNoticeDto createdNotice = noticeService.addNotice(token,notice);
        return ResponseHandler.created(createdNotice, "Notice created successfully");
    }

    @PutMapping("/{noticeId}")
    public ResponseEntity<GlobalResponse<GetNoticeDto>> updateNotice(@PathVariable Long noticeId, @RequestBody Notice notice) {
        notice.setNoticeId(noticeId);
        GetNoticeDto updatedNotice = noticeService.updateNotice(notice);
        return ResponseHandler.success(updatedNotice, "Notice updated successfully");
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<GlobalResponse<String>> deleteNotice(@PathVariable Integer noticeId) {
        String result = noticeService.deleteNotice(noticeId);
        return ResponseHandler.success(result, "Notice deleted successfully");
    }
}
