package com.project.demo.logic.entity.product;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.category.CategoryRepository;
import com.project.demo.logic.entity.rol.Role;
import com.project.demo.logic.entity.rol.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
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



    public ProductSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            CategoryRepository categoryRepository,
            ProductRepository productRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createProduct1();
        this.createProduct2();
        this.createProduct3();
        this.createProduct4();
        this.createProduct5();
        this.createProduct6();
        this.createProduct7();
        this.createProduct8();
        this.createProduct9();
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

        var product = new Product();
        product.setName(productNew.getName());
        product.setPrice(productNew.getPrice());
        product.setPicture(productNew.getPicture());
        product.setQuantityInStock(productNew.getQuantityInStock());
        product.setStatus(productNew.getStatus());
        product.setRate(productNew.getRate());

        product.setCategory(optionalCategory.get());
        productRepository.save(product);
    }

    private void createProduct2() {
        Product productNew = new Product();

        productNew.setName("Producto 2");
        productNew.setPrice(60.00);
        productNew.setPicture("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg");
        productNew.setQuantityInStock(6);
        productNew.setStatus("Activo");
        productNew.setRate(1);

        Optional<Category> optionalCategory = categoryRepository.findByName("Camisas");

        var product = new Product();
        product.setName(productNew.getName());
        product.setPrice(productNew.getPrice());
        product.setPicture(productNew.getPicture());
        product.setQuantityInStock(productNew.getQuantityInStock());
        product.setStatus(productNew.getStatus());
        product.setRate(productNew.getRate());

        product.setCategory(optionalCategory.get());
        productRepository.save(product);
    }

    private void createProduct3() {
        Product productNew = new Product();

        productNew.setName("Producto 3");
        productNew.setPrice(60.00);
        productNew.setPicture("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg");
        productNew.setQuantityInStock(6);
        productNew.setStatus("Activo");
        productNew.setRate(1);

        Optional<Category> optionalCategory = categoryRepository.findByName("Pantalones");

        var product = new Product();
        product.setName(productNew.getName());
        product.setPrice(productNew.getPrice());
        product.setPicture(productNew.getPicture());
        product.setQuantityInStock(productNew.getQuantityInStock());
        product.setStatus(productNew.getStatus());
        product.setRate(productNew.getRate());

        product.setCategory(optionalCategory.get());
        productRepository.save(product);
    }

    private void createProduct4() {
        Product productNew = new Product();

        productNew.setName("Producto 4");
        productNew.setPrice(60.00);
        productNew.setPicture("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg");
        productNew.setQuantityInStock(6);
        productNew.setStatus("Activo");
        productNew.setRate(4);

        Optional<Category> optionalCategory = categoryRepository.findByName("Pantalones");

        var product = new Product();
        product.setName(productNew.getName());
        product.setPrice(productNew.getPrice());
        product.setPicture(productNew.getPicture());
        product.setQuantityInStock(productNew.getQuantityInStock());
        product.setStatus(productNew.getStatus());
        product.setRate(productNew.getRate());

        product.setCategory(optionalCategory.get());
        productRepository.save(product);
    }

    private void createProduct5() {
        Product productNew = new Product();

        productNew.setName("Producto 5");
        productNew.setPrice(60.00);
        productNew.setPicture("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg");
        productNew.setQuantityInStock(6);
        productNew.setStatus("Activo");
        productNew.setRate(2);

        Optional<Category> optionalCategory = categoryRepository.findByName("Camisas");

        var product = new Product();
        product.setName(productNew.getName());
        product.setPrice(productNew.getPrice());
        product.setPicture(productNew.getPicture());
        product.setQuantityInStock(productNew.getQuantityInStock());
        product.setStatus(productNew.getStatus());
        product.setRate(productNew.getRate());

        product.setCategory(optionalCategory.get());
        productRepository.save(product);
    }

    private void createProduct6() {
        Product productNew = new Product();

        productNew.setName("Producto 6");
        productNew.setPrice(60.00);
        productNew.setPicture("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg");
        productNew.setQuantityInStock(6);
        productNew.setStatus("Activo");
        productNew.setRate(3);

        Optional<Category> optionalCategory = categoryRepository.findByName("Calzado");

        var product = new Product();
        product.setName(productNew.getName());
        product.setPrice(productNew.getPrice());
        product.setPicture(productNew.getPicture());
        product.setQuantityInStock(productNew.getQuantityInStock());
        product.setStatus(productNew.getStatus());
        product.setRate(productNew.getRate());

        product.setCategory(optionalCategory.get());
        productRepository.save(product);
    }
    private void createProduct7() {
        Product productNew = new Product();

        productNew.setName("Producto 7");
        productNew.setPrice(60.00);
        productNew.setPicture("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg");
        productNew.setQuantityInStock(6);
        productNew.setStatus("Activo");
        productNew.setRate(5);

        Optional<Category> optionalCategory = categoryRepository.findByName("Calzado");

        var product = new Product();
        product.setName(productNew.getName());
        product.setPrice(productNew.getPrice());
        product.setPicture(productNew.getPicture());
        product.setQuantityInStock(productNew.getQuantityInStock());
        product.setStatus(productNew.getStatus());
        product.setRate(productNew.getRate());

        product.setCategory(optionalCategory.get());
        productRepository.save(product);
    }

    private void createProduct8() {
        Product productNew = new Product();

        productNew.setName("Producto 8");
        productNew.setPrice(60.00);
        productNew.setPicture("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg");
        productNew.setQuantityInStock(6);
        productNew.setStatus("Activo");
        productNew.setRate(2);

        Optional<Category> optionalCategory = categoryRepository.findByName("Camisas");

        var product = new Product();
        product.setName(productNew.getName());
        product.setPrice(productNew.getPrice());
        product.setPicture(productNew.getPicture());
        product.setQuantityInStock(productNew.getQuantityInStock());
        product.setStatus(productNew.getStatus());
        product.setRate(productNew.getRate());

        product.setCategory(optionalCategory.get());
        productRepository.save(product);
    }

    private void createProduct9() {
        Product productNew = new Product();

        productNew.setName("Producto 9");
        productNew.setPrice(60.00);
        productNew.setPicture("https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg");
        productNew.setQuantityInStock(6);
        productNew.setStatus("Activo");
        productNew.setRate(2);

        Optional<Category> optionalCategory = categoryRepository.findByName("Pantalones");

        var product = new Product();
        product.setName(productNew.getName());
        product.setPrice(productNew.getPrice());
        product.setPicture(productNew.getPicture());
        product.setQuantityInStock(productNew.getQuantityInStock());
        product.setStatus(productNew.getStatus());
        product.setRate(productNew.getRate());

        product.setCategory(optionalCategory.get());
        productRepository.save(product);
    }


}
