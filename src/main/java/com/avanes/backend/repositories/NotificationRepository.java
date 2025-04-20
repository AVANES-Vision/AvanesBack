package com.avanes.backend.repositories;

import com.avanes.backend.models.Notification;
import com.avanes.backend.models.Notification.NotificationAudience;
import com.avanes.backend.models.Notification.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByAudienceIdOrAudienceOrderByCreatedAtDesc(String audienceId, NotificationAudience audience);
    List<Notification> findByAudienceIdAndIsReadOrderByCreatedAtDesc(String audienceId, boolean isRead);
    List<Notification> findByTypeAndAudienceIdOrderByCreatedAtDesc(NotificationType type, String audienceId);
    long countByAudienceIdAndIsRead(String audienceId, boolean isRead);
} 