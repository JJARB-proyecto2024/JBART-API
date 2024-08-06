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
        createUserBuyer("User", "Buyer", "user.buyer@gmail.com", "userbuyer123");
        createUserBuyer("Manuel", "Garro", "mg@gmail.com", "manuel123");
        createUserBuyer("Ashley", "Graham", "ag@gmail.com", "ashely123");
    }

    private void createUserBuyer(String name, String lastname, String email, String rawPassword) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        UserBuyer userBuyer = new UserBuyer();
        userBuyer.setName(name);
        userBuyer.setLastname(lastname);
        userBuyer.setPicture("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg");
        userBuyer.setGenre(name.equals("Ashley") ? "Femenino" : "Masculino");
        userBuyer.setDeliveryLocation("San José, San Rafael Arriba de Desamparados");
        userBuyer.setPhoneNumber("71157914");
        userBuyer.setEmail(email);
        userBuyer.setPassword(passwordEncoder.encode(rawPassword));
        userBuyer.setRole(optionalRole.get());
        userBuyer.setStatus("Activo");

        userRepository.save(userBuyer);
    }
}
