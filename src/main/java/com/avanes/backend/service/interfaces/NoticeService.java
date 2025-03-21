package com.avanes.backend.service.interfaces;

import com.avanes.backend.dtos.GetNoticeDto;
import com.avanes.backend.dtos.GetNoticesDto;
import com.avanes.backend.models.Notice;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface NoticeService {

    List<GetNoticesDto> getAllNotices();

    GetNoticeDto getNotice(Integer noticeId);

    GetNoticeDto addNotice(@RequestHeader("Authorization") String token, Notice notice);

    GetNoticeDto updateNotice(Notice notice);

    String deleteNotice(Integer noticeId);
}