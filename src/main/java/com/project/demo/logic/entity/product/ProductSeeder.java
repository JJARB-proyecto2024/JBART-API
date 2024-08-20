package com.project.demo.logic.entity.product;

import com.project.demo.logic.entity.category.CategoryRepository;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.entity.userBrand.UserBrandRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        createProducts();
    }

    private void createProducts() {
        Object[][] productsData = {
                {"Producto 1", 60.00, "48", "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg", 6, "Activo", "Calzado", "Lorem Ipsum"},
                {"Producto 2", 30.00, "24" ,"https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg", 3, "Activo", "Pantalones", "Adidas"},
                {"Producto 3", 100.00, "M" , "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg", 9, "Activo", "Sweaters", "Nike"},
                {"Producto 4", 100.00,"34", "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg", 9, "Activo", "Pantalones", "Nike"},
                {"Producto 5", 100.00, "M", "https://res.cloudinary.com/drlznypvr/image/upload/c_fill,w_200,h_200/v1720842225/439973425_8044300855602982_95489407312113055_n_bhwkz1.jpg", 9, "Activo", "Camisas", "Adidas"}
        };

        for (Object[] productData : productsData) {
            Product product = new Product();
            product.setName((String) productData[0]);
            product.setPrice((Double) productData[1]);
            product.setSize((String) productData[2]);
            product.setModel((String) productData[3]);
            product.setQuantityInStock((Integer) productData[4]);
            product.setStatus((String) productData[5]);

            categoryRepository.findByName((String) productData[6]).ifPresent(product::setCategory);
            userBrandRepository.findByName((String) productData[7]).ifPresent(product::setUserBrand);

            if (product.getCategory() != null && product.getUserBrand() != null) {
                productRepository.save(product);
            }
        }
    }



}
