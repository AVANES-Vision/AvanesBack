package com.avanes.backend.repositories;

import com.avanes.backend.models.Notification;
import com.avanes.backend.models.Notification.NotificationAudience;
import com.avanes.backend.models.Notification.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByAudienceIdOrAudienceOrderByCreatedAtDesc(String audienceId, NotificationAudience audience);
    List<Notification> findByAudienceIdAndIsReadOrderByCreatedAtDesc(String audienceId, boolean isRead);
    List<Notification> findByTypeAndAudienceIdOrderByCreatedAtDesc(NotificationType type, String audienceId);
    long countByAudienceIdAndIsRead(String audienceId, boolean isRead);
    
    @Query("SELECT n FROM Notification n WHERE n.audienceId = :audienceId OR n.audience = :audience ORDER BY n.createdAt DESC")
    List<Notification> findForUserWithGlobalNotifications(String audienceId, NotificationAudience audience);
    
    @Query("SELECT n FROM Notification n WHERE (n.audienceId = :audienceId OR n.audience = :audience) AND n.isRead = :isRead ORDER BY n.createdAt DESC")
    List<Notification> findUnreadForUserWithGlobalNotifications(String audienceId, NotificationAudience audience, boolean isRead);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE (n.audienceId = :audienceId OR n.audience = :audience) AND n.isRead = :isRead")
    long countUnreadForUserWithGlobalNotifications(String audienceId, NotificationAudience audience, boolean isRead);
} 