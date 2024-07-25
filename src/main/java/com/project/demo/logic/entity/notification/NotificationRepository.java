package com.project.demo.logic.entity.notification;

import com.project.demo.logic.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE LOWER(n.title) LIKE %?1%")
    List<Notification> findProductsWithCharacterInName(String character);

    @Query("SELECT n FROM Notification n WHERE n.title = ?1")
    Optional<Notification> findByTitle(String title);
}
