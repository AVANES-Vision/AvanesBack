package com.avanes.backend.dto;

import com.avanes.backend.models.Notice;

import java.time.LocalDateTime;

public class GetNoticesDto {
    public String noticeTitle;
    public String noticeContent;
    public Long noticeId;
    public LocalDateTime publishedDate;

    public GetNoticesDto(Notice notice) {
        this.noticeTitle = notice.getTitle();
        this.noticeContent = notice.getContent();
        this.noticeId = notice.getNoticeId();
        this.publishedDate = notice.getPublishedDate();
    }
}
