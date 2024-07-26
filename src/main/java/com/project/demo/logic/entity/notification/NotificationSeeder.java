package com.project.demo.logic.entity.notification;

import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.entity.userBrand.UserBrand;
import com.project.demo.logic.entity.userBrand.UserBrandRepository;
import com.project.demo.logic.entity.userBuyer.UserBuyer;
import com.project.demo.logic.entity.userBuyer.UserBuyerRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotificationSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final UserBuyerRepository userBuyerRepository;
    private final UserBrandRepository userBrandRepository;


    public NotificationSeeder(NotificationRepository roleRepository, UserRepository userRepository, UserBuyerRepository userBuyerRepository, UserBrandRepository userBrandRepository) {
        this.notificationRepository = roleRepository;
        this.userRepository = userRepository;
        this.userBuyerRepository = userBuyerRepository;
        this.userBrandRepository = userBrandRepository;
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createNotification();
    }

    private void createNotification() {
        Optional<User> userOptional = userRepository.findByEmail("super.admin@gmail.com");

        if (userOptional.isEmpty()) {
            System.err.println("User not found");
            return;
        }


        User user = new User();
        user.setId(userOptional.get().getId());
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle("Notification Title");
        notification.setDescription("Notification Description");
        notification.setSeen(false);
        notificationRepository.save(notification);
        notificationRepository.save(notification);
        notificationRepository.save(notification);
    }
}
