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

    public UserBrandSeeder(
            RoleRepository roleRepository,
            UserBrandRepository userBrandRepository,
            PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userBrandRepository = userBrandRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createUserBrand("3101123456", "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif",
                "https://res.cloudinary.com/dbd6uaiux/image/upload/v1722045648/Tarea1_BraylieUre%C3%B1a_h2nush.pdf", "Lorem Ipsum", "Pedro Pascal",
                "San Rafael Arriba, Desamparados", "Camisas, Pantalonetas", "user.brand@gmail.com", "userbrand123", 0, "Activo");

        createUserBrand("987456321", "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif",
                null, "Adidas", "Pedro Pascal", "San Rafael Arriba, Desamparados", "Sweaters, Pantalones", "adidas@gmail.com",
                "adidas123", 4, "Activo");

        createUserBrand("123654789", "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif",
                null, "Nike", "Pedro Pascal", "San Rafael Arriba, Desamparados", "Camisas, Calzado", "nike@gmail.com",
                "nike123", 3, "Activo");
    }

    private void createUserBrand(String legalId, String logoType, String legalDocuments, String brandName,
                                 String legalRepresentativeName, String mainLocationAddress, String brandCategories,
                                 String email, String password, int rate, String status) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER_BRAND);
        Optional<UserBrand> optionalUser = userBrandRepository.findByEmail(email);

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong(legalId));
        userBrand.setLogoType(logoType);
        userBrand.setLegalDocuments(legalDocuments);
        userBrand.setBrandName(brandName);
        userBrand.setLegalRepresentativeName(legalRepresentativeName);
        userBrand.setMainLocationAddress(mainLocationAddress);
        userBrand.setBrandCategories(brandCategories);
        userBrand.setEmail(email);
        userBrand.setPassword(passwordEncoder.encode(password));
        userBrand.setRole(optionalRole.get());
        userBrand.setStatus(status);
        userBrand.setRate(rate);

        userBrandRepository.save(userBrand);
    }
}
