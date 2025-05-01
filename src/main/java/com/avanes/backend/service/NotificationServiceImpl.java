package com.avanes.backend.service;

import com.avanes.backend.models.Notification;
import com.avanes.backend.models.Notification.NotificationAudience;
import com.avanes.backend.models.Notification.NotificationType;
import com.avanes.backend.repositories.NotificationRepository;
import com.avanes.backend.service.interfaces.NotificationService;
import com.avanes.backend.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Transactional
    public void createQuizPublishedNotification(String moduleId, String quizTitle) {
        Notification notification = Notification.builder()
                .title("New Quiz Published")
                .message("A new quiz '" + quizTitle + "' has been published in your module")
                .type(NotificationType.QUIZ_PUBLISHED)
                .audience(NotificationAudience.MODULE_STUDENTS)
                .audienceId(moduleId)
                .isRead(false)
                .build();
        
        notificationRepository.save(notification);
    }

    @Transactional
    public void createAssignmentDueNotification(String moduleId, String assignmentTitle) {
        Notification notification = Notification.builder()
                .title("Assignment Due Soon")
                .message("Assignment '" + assignmentTitle + "' is due soon")
                .type(NotificationType.ASSIGNMENT_DUE)
                .audience(NotificationAudience.MODULE_STUDENTS)
                .audienceId(moduleId)
                .isRead(false)
                .build();
        
        notificationRepository.save(notification);
    }

    @Transactional
    public void createNewMaterialNotification(String moduleId, String materialTitle) {
        Notification notification = Notification.builder()
                .title("New Learning Material")
                .message("New material '" + materialTitle + "' has been added to your module")
                .type(NotificationType.NEW_MATERIAL)
                .audience(NotificationAudience.MODULE_STUDENTS)
                .audienceId(moduleId)
                .isRead(false)
                .build();
        
        notificationRepository.save(notification);
    }

    @Transactional
    public void createGradeReleasedNotification(String userId, String assessmentName) {
        Notification notification = Notification.builder()
                .title("Grade Released")
                .message("Your grade for '" + assessmentName + "' has been released")
                .type(NotificationType.GRADE_RELEASED)
                .audience(NotificationAudience.SPECIFIC_USER)
                .audienceId(userId)
                .isRead(false)
                .build();
        
        notificationRepository.save(notification);
    }

    @Transactional
    public void createNotification(List<String> userIds, String title, String message, NotificationType type) {
        List<Notification> notifications = userIds.stream().map(userId -> Notification.builder()
                        .title(title)
                        .message(message)
                        .type(type)
                        .audience(NotificationAudience.SPECIFIC_USER)
                        .audienceId(userId)
                        .isRead(false)
                        .build())
                .toList();

        notificationRepository.saveAll(notifications);
    }
    
    @Transactional
    public void createNoticeNotification(String moduleId, String title, String message) {
        Notification notification;
        
        if ("All".equals(moduleId)) {
            // Create notification for all users
            notification = Notification.builder()
                    .title(title)
                    .message(message)
                    .type(NotificationType.NOTICE_PUBLISHED)
                    .audience(NotificationAudience.ALL_USERS)
                    .isRead(false)
                    .build();
        } else {
            // Create notification for specific module
            notification = Notification.builder()
                    .title(title)
                    .message(message)
                    .type(NotificationType.NOTICE_PUBLISHED)
                    .audience(NotificationAudience.MODULE_STUDENTS)
                    .audienceId(moduleId)
                    .isRead(false)
                    .build();
        }
        
        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(String userId) {
        // Get notifications for the user based on their enrollments and specific notifications
        // The new method will correctly retrieve both personal notifications and global ones
        return notificationRepository.findForUserWithGlobalNotifications(userId, NotificationAudience.ALL_USERS);
    }

    public List<Notification> getUnreadNotifications(String userId) {
        return notificationRepository.findUnreadForUserWithGlobalNotifications(userId, NotificationAudience.ALL_USERS, false);
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    @Transactional
    public void markAllAsRead(String userId) {
        // Get all unread notifications for this user - including global ones
        List<Notification> unreadNotifications = notificationRepository.findUnreadForUserWithGlobalNotifications(
            userId, NotificationAudience.ALL_USERS, false);
            
        // Mark them all as read
        unreadNotifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }

    public long getUnreadCount(String userId) {
        return notificationRepository.countUnreadForUserWithGlobalNotifications(userId, NotificationAudience.ALL_USERS, false);
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}