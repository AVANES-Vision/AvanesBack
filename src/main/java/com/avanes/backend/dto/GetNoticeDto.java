package com.avanes.backend.dto;

import com.avanes.backend.models.Notice;

import java.time.LocalDateTime;

public class GetNoticeDto {
    public String noticeTitle;
    public String noticeContent;
    public Long noticeId;
    public String author;
    public LocalDateTime publishedDate;

    public GetNoticeDto(Notice notice){
        this.noticeTitle = notice.getTitle();
        this.noticeContent = notice.getContent();
        this.noticeId = notice.getNoticeId();
        this.author = notice.getAuthor();
        this.publishedDate = notice.getPublishedDate();
    }
}
