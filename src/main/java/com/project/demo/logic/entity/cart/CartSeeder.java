package com.project.demo.logic.entity.cart;

import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.product.ProductRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.entity.userBuyer.UserBuyer;
import com.project.demo.logic.entity.userBuyer.UserBuyerRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final UserBuyerRepository userBuyerRepository;
    private final ProductRepository productRepository;

    public CartSeeder(CartRepository cartRepository, UserRepository userRepository, UserBuyerRepository userBuyerRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.userBuyerRepository = userBuyerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createCart();
    }

    private void createCart() {
        Optional<UserBuyer> userOptional = userBuyerRepository.findByEmail("user.buyer@gmail.com");
        Optional<Product> productOptional = productRepository.findById(1L);
        if (userOptional.isEmpty()) {
            System.err.println("User not found");
            return;
        }
        Cart cart = new Cart();
        cart.setUserBuyer(userOptional.get());
        cart.setQuantity(10);
        cart.setProduct(productOptional.get());
        cartRepository.save(cart);
    }
}