package com.project.demo.logic.entity.userBuyer;

import com.project.demo.logic.entity.category.CategoryRepository;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserBuyerSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;


    public UserBuyerSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            CategoryRepository categoryRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createUserBuyer();
    }

    private void createUserBuyer() {
        UserBuyer userBuyer = new UserBuyer();

        userBuyer.setName("User");
        userBuyer.setLastname("Buyer");
        userBuyer.setPicture("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg");
        userBuyer.setGenre("Masculino");
        userBuyer.setDeliveryLocation("San José, San Rafael Arriba de Desamparados");
        userBuyer.setPhoneNumber("71157914");
        userBuyer.setEmail("user.buyer@gmail.com");
        userBuyer.setPassword("userbuyer123");
        userBuyer.setStatus("Activo");
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        Optional<User> optionalUser = userRepository.findByEmail(userBuyer.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new UserBuyer();
        user.setName(userBuyer.getName());
        user.setLastname(userBuyer.getLastname());
        user.setPicture(userBuyer.getPicture());
        user.setGenre(userBuyer.getGenre());
        user.setDeliveryLocation(userBuyer.getDeliveryLocation());
        user.setPhoneNumber(userBuyer.getPhoneNumber());
        user.setEmail(userBuyer.getEmail());
        user.setPassword(passwordEncoder.encode(userBuyer.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBuyer.getStatus());
        userRepository.save(user);
    }

}
