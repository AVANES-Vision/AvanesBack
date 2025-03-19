package com.avanes.backend.service.interfaces;

import com.avanes.backend.dto.GetNoticeDto;
import com.avanes.backend.dto.GetNoticesDto;
import com.avanes.backend.models.Notice;
import java.util.List;

public interface NoticeService {

    List<GetNoticesDto> getAllNotices();

    GetNoticeDto getNotice(Integer noticeId);

    GetNoticeDto addNotice(Notice notice);

    GetNoticeDto updateNotice(Notice notice);

    String deleteNotice(Integer noticeId);
}