package com.project.demo.logic.entity.notificationTemplate;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.notification.Notification;
import com.project.demo.logic.entity.notification.NotificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
    @Query("SELECT c FROM NotificationTemplate c WHERE c.title = ?1")
    Optional<NotificationTemplate> findByTitle(String title);
}
