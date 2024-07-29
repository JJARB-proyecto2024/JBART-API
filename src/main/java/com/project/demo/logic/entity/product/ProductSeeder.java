package com.project.demo.logic.entity.product;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.category.CategoryRepository;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.entity.userBrand.UserBrand;
import com.project.demo.logic.entity.userBrand.UserBrandRepository;
import com.project.demo.logic.entity.userBuyer.UserBuyer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final UserBrandRepository userBrandRepository;



    public ProductSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            UserBrandRepository userBrandRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userBrandRepository = userBrandRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createProduct1();

    }

    private void createProduct1() {
        Product productNew = new Product();

        productNew.setName("Producto 1");
        productNew.setPrice(60.00);
        productNew.setPicture("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg");
        productNew.setQuantityInStock(6);
        productNew.setStatus("Activo");
        productNew.setRate(4);

        Optional<Category> optionalCategory = categoryRepository.findByName("Calzado");

        Optional<UserBrand> optionalUserBrand = userBrandRepository.findByName("Lorem Ipsum");

        if (optionalCategory.isEmpty()) {
            System.err.println("Category 'Calzado' not found.");
            return;
        }

        if (optionalUserBrand.isEmpty()) {
            System.err.println("User brand 'Lorem Ipsum' not found.");
            return;
        }

        Category category = optionalCategory.get();
        UserBrand userBrand = optionalUserBrand.get();

        var product = new Product();
        product.setName(productNew.getName());
        product.setPrice(productNew.getPrice());
        product.setPicture(productNew.getPicture());
        product.setQuantityInStock(productNew.getQuantityInStock());
        product.setStatus(productNew.getStatus());
        product.setRate(productNew.getRate());

        product.setCategory(category);
        product.setUserBrand(userBrand);
        productRepository.save(product);
    }

}
