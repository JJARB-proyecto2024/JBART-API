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
        Optional<UserBuyer> userBuyerOptional = userBuyerRepository.findByEmail("user.buyer@gmail.com");
        Optional<UserBrand> userBrandOptional = userBrandRepository.findByEmail("user.brand@gmail.com");

        if (userOptional.isEmpty()) {
            System.err.println("User not found");
            return;
        }
        if (userBuyerOptional.isEmpty()) {
            System.err.println("User not found");
            return;
        }
        if (userBrandOptional.isEmpty()) {
            System.err.println("User not found");
            return;
        }

        User user = userOptional.get();
        UserBuyer userBuyer = userBuyerOptional.get();
        UserBrand userBrand = userBrandOptional.get();

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setUserBuyer(userBuyer);
        notification.setUserBrand(userBrand);
        notification.setTitle("Notification Title");
        notification.setDescription("Notification Description");
        notification.setSeen(false);
        notificationRepository.save(notification);

        Notification notification2 = new Notification();
        notification2.setUser(user);
        notification2.setUserBuyer(userBuyer);
        notification2.setUserBrand(userBrand);
        notification2.setTitle("Notification Title");
        notification2.setDescription("Notification Description");
        notification2.setSeen(false);
        notificationRepository.save(notification2);
    }
}
