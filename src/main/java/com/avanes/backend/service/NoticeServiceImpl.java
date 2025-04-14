package com.avanes.backend.service;

import com.avanes.backend.dtos.GetNoticeDto;
import com.avanes.backend.dtos.GetNoticesDto;
import com.avanes.backend.models.Notice;
import com.avanes.backend.repository.NoticeRepository;
import com.avanes.backend.service.interfaces.NoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = Objects.requireNonNull(noticeRepository, "NoticeRepository cannot be null");
    }

    @Override
    public List<GetNoticesDto> getAllNotices() {
        try {
            List<Notice> notices = noticeRepository.findAll();
            if(notices.isEmpty()) {
                throw new EntityNotFoundException("No notices are currently published.");
            }
            List<GetNoticesDto> noticesList = new ArrayList<>();
            for (Notice notice : notices) {
                if (notice != null) {
                    noticesList.add(new GetNoticesDto(notice));
                }
            }
            return noticesList;
        } catch(EntityNotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve notices due to a system error: " + e.getMessage(), e);
        }
    }

    @Override
    public GetNoticeDto getNotice(Integer noticeId) {
        if (noticeId == null) {
            throw new IllegalArgumentException("Notice ID cannot be null");
        }

        try {
            return noticeRepository.findById(noticeId)
                    .map(GetNoticeDto::new)
                    .orElseThrow(() -> new EntityNotFoundException("Notice not found with ID: " + noticeId));
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve notice with ID " + noticeId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public GetNoticeDto addNotice(Notice notice) {
        validateNotice(notice);

        notice.setAuthor(null);
        notice.setUpdatedUser(null);

        try {
            Notice savedNotice = noticeRepository.save(notice);
            return new GetNoticeDto(savedNotice);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add notice: " + e.getMessage(), e);
        }
    }

    @Override
    public GetNoticeDto updateNotice(Notice notice) {
        validateNotice(notice);

        notice.setUpdatedUser(null);

        if (!noticeRepository.existsById(Math.toIntExact(notice.getNoticeId()))) {
            throw new EntityNotFoundException("Cannot update - Notice not found with ID: " + notice.getNoticeId());
        }

        try {
            Notice updatedNotice = noticeRepository.save(notice);
            return new GetNoticeDto(updatedNotice);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update notice with ID " + notice.getNoticeId() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public String deleteNotice(Integer noticeId) {
        if (noticeId == null) {
            throw new IllegalArgumentException("Notice ID cannot be null");
        }

        try {
            if (!noticeRepository.existsById(noticeId)) {
                throw new EntityNotFoundException("Cannot delete - Notice not found with ID: " + noticeId);
            }
            noticeRepository.deleteById(noticeId);
            return "Notice deleted successfully";
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete notice with ID " + noticeId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public List<GetNoticesDto> getAllNoticesByModuleIdAsAdmin(String moduleId) {
        if (moduleId == null) {
            throw new IllegalArgumentException("Module ID cannot be null");
        }

        try {
            List<Notice> notices = noticeRepository.findAllByModule(moduleId);
            List<Notice> allNotices = noticeRepository.findAllByModule("All");
            if (notices.isEmpty()) {
                throw new EntityNotFoundException("No notices found for module ID: " + moduleId);
            }
            List<GetNoticesDto> noticesList = new ArrayList<>();
            for (Notice notice : notices) {
                if (notice != null) {
                    noticesList.add(new GetNoticesDto(notice));
                }
            }
            if(!allNotices.isEmpty()) {
                for (Notice notice : allNotices) {
                    if (notice != null ) {
                        noticesList.add(new GetNoticesDto(notice));
                    }
                }
            }
            return noticesList;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve notices for module ID " + moduleId + ": " + e.getMessage(), e);
        }
    }




    @Override
    public List<GetNoticesDto> getAllNoticesByModuleIdAsUser(String moduleId) {
        if (moduleId == null) {
            throw new IllegalArgumentException("Module ID cannot be null");
        }

        try {
            List<Notice> notices = noticeRepository.findAllByModule(moduleId);
            List<Notice> allNotices = noticeRepository.findAllByModule("All");
            if (notices.isEmpty()) {
                throw new EntityNotFoundException("No notices found for module ID: " + moduleId);
            }
            List<GetNoticesDto> noticesList = new ArrayList<>();
            for (Notice notice : notices) {
                if (notice != null && notice.getScheduleDateTime().isBefore(java.time.LocalDateTime.now())) {
                    noticesList.add(new GetNoticesDto(notice));
                }
            }

            if(!allNotices.isEmpty()) {
                for (Notice notice : allNotices) {
                    if (notice != null && notice.getScheduleDateTime().isBefore(java.time.LocalDateTime.now())) {
                        noticesList.add(new GetNoticesDto(notice));
                    }
                }
            }
            return noticesList;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve notices for module ID " + moduleId + ": " + e.getMessage(), e);
        }
    }

    private void validateNotice(Notice notice) {
        if (notice == null) {
            throw new IllegalArgumentException("Notice cannot be null");
        }
//        if (notice.getNoticeId() == null) {
//            throw new IllegalArgumentException("Notice ID cannot be null");
//        }
        // Add additional validation as needed based on your Notice model requirements
    }
}