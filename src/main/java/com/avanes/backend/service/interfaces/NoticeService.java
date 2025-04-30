package com.avanes.backend.service.interfaces;

import com.avanes.backend.dtos.GetNoticeDto;
import com.avanes.backend.dtos.GetNoticesDto;
import com.avanes.backend.models.Notice;

import java.util.List;

public interface NoticeService {

    List<GetNoticesDto> getAllNotices();

    GetNoticeDto getNotice(Integer noticeId);

    GetNoticeDto addNotice(Notice notice);

    GetNoticeDto updateNotice(Notice notice);

    String deleteNotice(Integer noticeId);

    List<GetNoticesDto> getAllNoticesByModuleIdAsAdmin(String moduleId);

    List<GetNoticesDto> getAllNoticesByModuleIdAsUser(String moduleId);
}