package com.project.demo.rest.notification;

import com.project.demo.logic.entity.notification.Notification;
import com.project.demo.logic.entity.notification.NotificationRepository;
import com.project.demo.logic.entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public List<Notification> getNotificationsByUserId(@PathVariable Long id) {
        return notificationRepository.findByUserId(id);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Notification addNotification(@RequestBody Notification notification) {
        return notificationRepository.save(notification);
    }

    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable Long id) {
        return notificationRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    @PutMapping("/user/{id}")
    public Notification updateNotification(@PathVariable Long id, @RequestBody Notification notification) {
        return notificationRepository.findById(id)
                .map(existingNotification -> {
                    existingNotification.setSeen(notification.isSeen());
                    return notificationRepository.save(existingNotification);
                })
                .orElseGet(() -> {
                    notification.setId(id);
                    return notificationRepository.save(notification);
                });
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteNotification(@PathVariable Long id) {
        notificationRepository.deleteById(id);
    }
}