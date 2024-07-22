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
        this.createUserBuyer2();
        this.createUserBuyer3();
        this.createUserBuyer4();
        this.createUserBuyer5();
        this.createUserBuyer6();
        this.createUserBuyer7();
        this.createUserBuyer8();
        this.createUserBuyer9();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    private void createUserBuyer() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("3101123456"));
        userBrand.setLogoType("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif");
        userBrand.setBrandName("Lorem Ipsum");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalonetas");
        userBrand.setEmail("user.brand@gmail.com");
        userBrand.setPassword("userbrand123");
        userBrand.setRate(2);
        userBrand.setStatus("Activo");
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
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBuyer2() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("3101123986"));
        userBrand.setLogoType("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif");
        userBrand.setBrandName("Ldfsf");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalonetas");
        userBrand.setEmail("fdfsds@gmail.com");
        userBrand.setPassword("userbrand123");
        userBrand.setRate(4);
        userBrand.setStatus("Activo");
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
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBuyer3() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("3101123385"));
        userBrand.setLogoType("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif");
        userBrand.setBrandName("vfsbgdfgb");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalonetas");
        userBrand.setEmail("fgsgdfvg@gmail.com");
        userBrand.setPassword("userbrand123");
        userBrand.setRate(3);
        userBrand.setStatus("Activo");
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
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBuyer4() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("3101123758"));
        userBrand.setLogoType("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif");
        userBrand.setBrandName("fdgbdfbxdb");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalonetas");
        userBrand.setEmail("gfbhxbg@gmail.com");
        userBrand.setPassword("userbrand123");
        userBrand.setRate(5);
        userBrand.setStatus("Activo");
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
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBuyer5() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("3101123158"));
        userBrand.setLogoType("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif");
        userBrand.setBrandName("gbfxgfdgg");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalonetas");
        userBrand.setEmail("fasdfsaf@gmail.com");
        userBrand.setPassword("userbrand123");
        userBrand.setRate(4);
        userBrand.setStatus("Activo");
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
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBuyer6() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("3101123658"));
        userBrand.setLogoType("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif");
        userBrand.setBrandName("wtregg");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalonetas");
        userBrand.setEmail("retertgf@gmail.com");
        userBrand.setPassword("userbrand123");
        userBrand.setRate(5);
        userBrand.setStatus("Activo");
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
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBuyer7() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("3101123989"));
        userBrand.setLogoType("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif");
        userBrand.setBrandName("retergvfd");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalonetas");
        userBrand.setEmail("rgtrfgf@gmail.com");
        userBrand.setPassword("userbrand123");
        userBrand.setRate(1);
        userBrand.setStatus("Activo");
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
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBuyer8() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("3101123363"));
        userBrand.setLogoType("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif");
        userBrand.setBrandName("fgrvdfgvxrt");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalonetas");
        userBrand.setEmail("rfzvdzxv@gmail.com");
        userBrand.setPassword("userbrand123");
        userBrand.setRate(2);
        userBrand.setStatus("Activo");
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
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBuyer9() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("3101123789"));
        userBrand.setLogoType("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif");
        userBrand.setBrandName("wqasdawd");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalonetas");
        userBrand.setEmail("wedasx@gmail.com");
        userBrand.setPassword("userbrand123");
        userBrand.setRate(3);
        userBrand.setStatus("Activo");
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
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }
}
