package com.avanes.backend.controllers;

import com.avanes.backend.dto.GetNoticeDto;
import com.avanes.backend.dto.GetNoticesDto;
import com.avanes.backend.models.Notice;
import com.avanes.backend.service.NoticeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping()
    public List<GetNoticesDto> getAllNotices(){
        return this.noticeService.getAllNotices();
    }

    @GetMapping("{noticeId}")
    public GetNoticeDto getNotices(@PathVariable Integer noticeId){
        return this.noticeService.getNotice(noticeId);
    }


    @PostMapping()
    public GetNoticeDto addNotice(@RequestBody Notice notice){
        return this.noticeService.addNotice(notice);
    }

    @PutMapping("{noticeId}")
    public GetNoticeDto updateNotice(@PathVariable Long noticeId ,@RequestBody Notice notice){
        notice.setNoticeId(noticeId);
        return this.noticeService.updateNotice(notice);

    }

    @DeleteMapping("{noticeId}")
    public String deleteNotice(@PathVariable Integer noticeId){
        return this.noticeService.deleteNotice(noticeId);
    }
}
