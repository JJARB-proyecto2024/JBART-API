package com.project.demo.logic.entity.userBrand;

import com.project.demo.logic.entity.category.CategoryRepository;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserBrandSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserBrandRepository userBrandRepository;

    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;


    public UserBrandSeeder(
            RoleRepository roleRepository,
            UserBrandRepository userBrandRepository,
            PasswordEncoder passwordEncoder,
            CategoryRepository categoryRepository) {
        this.roleRepository = roleRepository;
        this.userBrandRepository = userBrandRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createUserBuyer();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    private void createUserBuyer() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLogoType("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif");
        userBrand.setBrandName("Lorem Ipsum");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalonetas");
        userBrand.setEmail("user.buyer@gmail.com");
        userBrand.setPassword("userbuyer123");
        userBrand.setActive(true);
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER_BRAND);
        Optional<UserBrand> optionalUser = userBrandRepository.findByEmail(userBrand.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new UserBrand();
        user.setLegalId(userBrand.getLegalId());
        user.setLogoType(userBrand.getLogoType());
        user.setBrandName(userBrand.getBrandName());
        user.setLegalRepresentativeName(userBrand.getLegalRepresentativeName());
        user.setMainLocationAddress(userBrand.getMainLocationAddress());
        user.setBrandCategories(userBrand.getBrandCategories());
        user.setEmail(userBrand.getEmail());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setActive(userBrand.isActive());
        userBrandRepository.save(user);
    }
}
