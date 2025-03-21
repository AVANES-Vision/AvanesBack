package com.avanes.backend.dtos;

import com.avanes.backend.models.Notice;

import java.time.LocalDateTime;

public class GetNoticesDto {
    public String noticeTitle;
    public String noticeContent;
    public Long noticeId;
    public LocalDateTime scheduleDateTime;
    public LocalDateTime createdDate;

    public GetNoticesDto(Notice notice) {
        this.noticeTitle = notice.getTitle();
        this.noticeContent = notice.getContent();
        this.noticeId = notice.getNoticeId();
        this.scheduleDateTime = notice.getScheduleDateTime();
        this.createdDate = notice.getCreatedDate();
    }
}
