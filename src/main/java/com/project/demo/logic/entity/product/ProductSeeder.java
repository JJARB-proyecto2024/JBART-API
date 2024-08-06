package com.project.demo.logic.entity.product;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.category.CategoryRepository;
import com.project.demo.logic.entity.userBrand.UserBrand;
import com.project.demo.logic.entity.userBrand.UserBrandRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserBrandRepository userBrandRepository;

    public ProductSeeder(
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            UserBrandRepository userBrandRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userBrandRepository = userBrandRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createProduct("Producto 1", 60.00, "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg", 6, "Activo", 2, "Calzado", "Lorem Ipsum");
        createProduct("Producto 2", 30.00, "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg", 3, "Activo", 3, "Pantalones", "Adidas");
        createProduct("Producto 3", 100.00, "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg", 9, "Activo", 5, "Sweaters", "Nike");
        createProduct("Producto 4", 100.00, "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg", 9, "Activo", 5, "Pantalones", "Nike");
        createProduct("Producto 5", 100.00, "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg", 9, "Activo", 5, "Camisas", "Adidas");
    }

    private void createProduct(String name, double price, String picture, int quantityInStock, String status, int rate, String categoryName, String userBrandName) {
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);
        Optional<UserBrand> optionalUserBrand = userBrandRepository.findByName(userBrandName);

        if (optionalCategory.isEmpty()) {
            System.err.println("Category '" + categoryName + "' not found.");
            return;
        }

        if (optionalUserBrand.isEmpty()) {
            System.err.println("User brand '" + userBrandName + "' not found.");
            return;
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setPicture(picture);
        product.setQuantityInStock(quantityInStock);
        product.setStatus(status);
        product.setRate(rate);
        product.setCategory(optionalCategory.get());
        product.setUserBrand(optionalUserBrand.get());

        productRepository.save(product);
    }
}
