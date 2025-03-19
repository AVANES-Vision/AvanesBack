package com.avanes.backend.service;

import com.avanes.backend.dto.GetNoticeDto;
import com.avanes.backend.dto.GetNoticesDto;
import com.avanes.backend.models.Notice;
import com.avanes.backend.repository.NoticeRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    NoticeService(NoticeRepository noticeRepository){
        this.noticeRepository = noticeRepository;
    }

    public List<GetNoticesDto> getAllNotices() {
        List<Notice> notices = this.noticeRepository.findAll();
        List<GetNoticesDto> noticesList = new ArrayList<>();
        for (Notice notice : notices) {
            noticesList.add(new GetNoticesDto(notice));
        }
        return noticesList;
    }

    public GetNoticeDto getNotice(Integer noticeId) {
        Notice notice = this.noticeRepository.findById(noticeId).orElse(null);

        if (notice == null) {
            return null;
        }else {
            return new GetNoticeDto(notice);
        }
    }

    public GetNoticeDto addNotice(Notice notice) {
        Notice savedNnotice = this.noticeRepository.save(notice);
        return new GetNoticeDto(savedNnotice);
    }

    public GetNoticeDto updateNotice(Notice notice) {
        Notice updatedNotice = this.noticeRepository.save(notice);
        return new GetNoticeDto(updatedNotice);
    }

    public String deleteNotice(Integer noticeId) {

        this.noticeRepository.deleteById(noticeId);

        return "Success Delete";
    }
}
