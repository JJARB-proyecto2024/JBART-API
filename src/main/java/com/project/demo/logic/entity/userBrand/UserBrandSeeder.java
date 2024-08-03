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
        this.createUserBrand();
        this.createUserBrand2();
        this.createUserBrand3();
        this.createUserBrand4();
        this.createUserBrand5();
        this.createUserBrand6();
        this.createUserBrand7();
        this.createUserBrand8();
        this.createUserBrand9();
        this.createUserBrand10();
        this.createUserBrand11();
        this.createUserBrand12();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    private void createUserBrand() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("3101123456"));
        userBrand.setLogoType("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1721022831/logo-compania_125964-228_s1iogw.avif");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722045648/Tarea1_BraylieUre%C3%B1a_h2nush.pdf");
        userBrand.setBrandName("Lorem Ipsum");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalonetas");
        userBrand.setEmail("user.brand@gmail.com");
        userBrand.setPassword("userbrand123");
        userBrand.setRate(0);
        userBrand.setStatus("Activo");
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER_BRAND);
        Optional<UserBrand> optionalUser = userBrandRepository.findByEmail(userBrand.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new UserBrand();
        user.setLegalId(userBrand.getLegalId());
        user.setLogoType(userBrand.getLogoType());
        user.setLegalDocuments(userBrand.getLegalDocuments());
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

    private void createUserBrand2() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("3101874456"));
        userBrand.setLogoType("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674249/Guess.png");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722675548/Guess-documento_knhvwe.pdf");
        userBrand.setBrandName("Guess");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalones, Calzado, Pantalonetas, Sweaters");
        userBrand.setEmail("guess@gmail.com");
        userBrand.setPassword("guess123");
        userBrand.setRate(0);
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
        user.setLegalDocuments(userBrand.getLegalDocuments());
        user.setEmail(userBrand.getEmail());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBrand3() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("129634789"));
        userBrand.setLogoType("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674249/Converse.png");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722675488/Converse-documento_sck41y.pdf");
        userBrand.setBrandName("Converse");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalones, Calzado, Pantalonetas, Sweaters");
        userBrand.setEmail("converse@gmail.com");
        userBrand.setPassword("converse123");
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
        user.setLegalDocuments(userBrand.getLegalDocuments());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBrand4() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("129612889"));
        userBrand.setLogoType("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674249/CalvinKlein.png");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674971/CalvinKlein-documento_bgi8jq.pdf");
        userBrand.setBrandName("Calvin Klein");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalones, Calzado, Pantalonetas, Sweaters");
        userBrand.setEmail("calvinklein@gmail.com");
        userBrand.setPassword("calvinklein123");
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
        user.setLegalDocuments(userBrand.getLegalDocuments());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBrand5() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("736634789"));
        userBrand.setLogoType("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674249/Nike.png");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722675604/Nike-documento_pxtjx1.pdf");
        userBrand.setBrandName("Nike");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalones, Calzado, Pantalonetas, Sweaters");
        userBrand.setEmail("nike@gmail.com");
        userBrand.setPassword("nike123");
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
        user.setLegalDocuments(userBrand.getLegalDocuments());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBrand6() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("866634789"));
        userBrand.setLogoType("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674249/Levis.png");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722675564/Levis-documento_rpstv4.pdf");
        userBrand.setBrandName("Levis");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalones, Calzado, Pantalonetas, Sweaters");
        userBrand.setEmail("levis@gmail.com");
        userBrand.setPassword("levis123");
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
        user.setLegalDocuments(userBrand.getLegalDocuments());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBrand7() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("986734789"));
        userBrand.setLogoType("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674249/Adidas.png");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674925/Adidas-documento_md0hia.pdf");
        userBrand.setBrandName("Adidas");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalones, Calzado, Pantalonetas, Sweaters");
        userBrand.setEmail("adidas@gmail.com");
        userBrand.setPassword("levis123");
        userBrand.setRate(0);
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
        user.setLegalDocuments(userBrand.getLegalDocuments());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBrand8() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("986945789"));
        userBrand.setLogoType("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674250/NewBalance.png");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722675585/NewBalance-documento_gqb0am.pdf");
        userBrand.setBrandName("New Balance");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalones, Calzado, Pantalonetas, Sweaters");
        userBrand.setEmail("newbalance@gmail.com");
        userBrand.setPassword("newbalance123");
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
        user.setLegalDocuments(userBrand.getLegalDocuments());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBrand9() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("351645789"));
        userBrand.setLogoType("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674250/Puma.png");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722675623/Puma-documento_xadezo.pdf");
        userBrand.setBrandName("Puma");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalones, Calzado, Pantalonetas, Sweaters");
        userBrand.setEmail("puma@gmail.com");
        userBrand.setPassword("puma123");
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
        user.setLegalDocuments(userBrand.getLegalDocuments());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBrand10() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("351758789"));
        userBrand.setLogoType("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674250/Reebok.png");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722675643/Reebok-documento_izppyx.pdf");
        userBrand.setBrandName("Reebok");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalones, Calzado, Pantalonetas, Sweaters");
        userBrand.setEmail("reebok@gmail.com");
        userBrand.setPassword("reebok123");
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
        user.setLegalDocuments(userBrand.getLegalDocuments());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBrand11() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("241947689"));
        userBrand.setLogoType("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674250/Zara.png");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722675675/Zara-documento_bv0iku.pdf");
        userBrand.setBrandName("Zara");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalones, Calzado, Pantalonetas, Sweaters");
        userBrand.setEmail("zara@gmail.com");
        userBrand.setPassword("zara123");
        userBrand.setRate(0);
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
        user.setLegalDocuments(userBrand.getLegalDocuments());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }

    private void createUserBrand12() {
        UserBrand userBrand = new UserBrand();
        userBrand.setLegalId(Long.parseLong("241945789"));
        userBrand.setLogoType("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722674250/Tommy.png");
        userBrand.setLegalDocuments("https://res.cloudinary.com/dbd6uaiux/image/upload/v1722675659/TommyHilfiger-documento_xidgfv.pdf");
        userBrand.setBrandName("Tommy Hilfiger");
        userBrand.setLegalRepresentativeName("Pedro Pascal");
        userBrand.setMainLocationAddress("San Rafael Arriba, Desamparados");
        userBrand.setBrandCategories("Camisas, Pantalones, Calzado, Pantalonetas, Sweaters");
        userBrand.setEmail("tommyhilfiger@gmail.com");
        userBrand.setPassword("tommyhilfiger123");
        userBrand.setRate(0);
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
        user.setLegalDocuments(userBrand.getLegalDocuments());
        user.setPassword(passwordEncoder.encode(userBrand.getPassword()));
        user.setRole(optionalRole.get());
        user.setStatus(userBrand.getStatus());
        user.setRate(userBrand.getRate());
        userBrandRepository.save(user);
    }
}
