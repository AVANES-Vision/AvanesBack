package com.avanes.backend.service.interfaces;

import com.avanes.backend.models.Notification;
import com.avanes.backend.models.Notification.NotificationType;

import java.util.List;

/**
 * Interface for handling notification-related operations in the application.
 * Provides methods for creating different types of notifications, retrieving user notifications,
 * and managing notification status.
 */
public interface NotificationService {

    /**
     * Creates a notification for students when a new quiz is published in a module.
     *
     * @param moduleId  The ID of the module where the quiz is published
     * @param quizTitle The title of the published quiz
     *
     * @apiNote {
     *   "purpose": "Notify students about a newly published quiz in their module",
     *   "audience": "All students enrolled in the specified module",
     *   "notificationType": "QUIZ_PUBLISHED",
     *   "behavior": "Creates and saves a notification with appropriate title and message"
     * }
     */
    void createQuizPublishedNotification(String moduleId, String quizTitle);

    /**
     * Creates a notification for students when an assignment due date is approaching.
     *
     * @param moduleId       The ID of the module containing the assignment
     * @param assignmentTitle The title of the assignment that is due soon
     *
     * @apiNote {
     *   "purpose": "Alert students about an upcoming assignment deadline",
     *   "audience": "All students enrolled in the specified module",
     *   "notificationType": "ASSIGNMENT_DUE",
     *   "behavior": "Creates and saves a notification with assignment due reminder"
     * }
     */
    void createAssignmentDueNotification(String moduleId, String assignmentTitle);

    /**
     * Creates a notification when new learning material is added to a module.
     *
     * @param moduleId      The ID of the module where material was added
     * @param materialTitle The title of the newly added learning material
     *
     * @apiNote {
     *   "purpose": "Inform students about newly available learning materials",
     *   "audience": "All students enrolled in the specified module",
     *   "notificationType": "NEW_MATERIAL",
     *   "behavior": "Creates and saves a notification about the new material"
     * }
     */
    void createNewMaterialNotification(String moduleId, String materialTitle);

    /**
     * Creates a notification for a specific user when their grade for an assessment is released.
     *
     * @param userId        The ID of the user receiving the grade
     * @param assessmentName The name of the assessment with the released grade
     *
     * @apiNote {
     *   "purpose": "Notify a student that their grade has been released",
     *   "audience": "Specific user identified by userId",
     *   "notificationType": "GRADE_RELEASED",
     *   "behavior": "Creates and saves a personalized notification about grade release"
     * }
     */
    void createGradeReleasedNotification(String userId, String assessmentName);

    /**
     * Creates custom notifications for multiple users with specified content and type.
     *
     * @param userIds List of user IDs who should receive the notification
     * @param title   The title of the notification
     * @param message The detailed message of the notification
     * @param type    The type of notification to create
     *
     * @apiNote {
     *   "purpose": "Send a custom notification to multiple specific users",
     *   "audience": "Multiple specific users identified by their userIds",
     *   "notificationType": "As specified in the parameter",
     *   "behavior": "Creates and saves multiple notifications, one for each user"
     * }
     */
    void createNotification(List<String> userIds, String title, String message, NotificationType type);

    /**
     * Retrieves all notifications relevant to a user (both specific and general).
     *
     * @param userId The ID of the user whose notifications to retrieve
     * @return A list of notifications ordered by creation date descending
     *
     * @apiNote {
     *   "purpose": "Get all notifications that a user should see",
     *   "returnValue": "List of notifications including both personal and general ones",
     *   "sorting": "Most recent notifications first",
     *   "includes": "Notifications addressed directly to the user and general notifications"
     * }
     */
    List<Notification> getUserNotifications(String userId);

    /**
     * Retrieves only unread notifications for a specific user.
     *
     * @param userId The ID of the user whose unread notifications to retrieve
     * @return A list of unread notifications ordered by creation date descending
     *
     * @apiNote {
     *   "purpose": "Get only notifications that haven't been read by the user",
     *   "returnValue": "List of unread notifications",
     *   "sorting": "Most recent notifications first",
     *   "useCases": "For notification badges or highlighting unread items"
     * }
     */
    List<Notification> getUnreadNotifications(String userId);

    /**
     * Marks a specific notification as read.
     *
     * @param notificationId The ID of the notification to mark as read
     *
     * @apiNote {
     *   "purpose": "Update a notification's status to read after user views it",
     *   "behavior": "Finds notification by ID and updates its isRead property to true",
     *   "errorHandling": "No action if notification with given ID doesn't exist"
     * }
     */
    void markAsRead(Long notificationId);

    /**
     * Marks all unread notifications for a user as read.
     *
     * @param userId The ID of the user whose notifications should be marked as read
     *
     * @apiNote {
     *   "purpose": "Update all unread notifications to read status in bulk",
     *   "behavior": "Finds all unread notifications for user and updates them",
     *   "useCases": "Mark all as read button functionality",
     *   "performance": "Executes as a batch update for efficiency"
     * }
     */
    void markAllAsRead(String userId);

    /**
     * Gets the count of unread notifications for a user.
     *
     * @param userId The ID of the user whose unread notification count to retrieve
     * @return The number of unread notifications
     *
     * @apiNote {
     *   "purpose": "Get count of notifications that need attention",
     *   "returnValue": "Integer count of unread notifications",
     *   "useCases": "For displaying notification badges or counters",
     *   "performance": "Executes count query without fetching full notifications"
     * }
     */
    long getUnreadCount(String userId);

    /**
     * Deletes a notification from the system.
     *
     * @param notificationId The ID of the notification to delete
     *
     * @apiNote {
     *   "purpose": "Permanently remove a notification from the database",
     *   "behavior": "Deletes the notification with the specified ID",
     *   "errorHandling": "No action if notification with given ID doesn't exist",
     *   "useCases": "Allow users to dismiss notifications or clean up old data"
     * }
     */
    void deleteNotification(Long notificationId);
}