package com.project.demo.rest.rateProduct;


import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.product.ProductRepository;
import com.project.demo.logic.entity.rateBrand.RateBrand;
import com.project.demo.logic.entity.rateBrand.RateBrandRepository;
import com.project.demo.logic.entity.rateProduct.RateProduct;
import com.project.demo.logic.entity.rateProduct.RateProductRepository;
import com.project.demo.logic.entity.userBuyer.UserBuyer;
import com.project.demo.logic.entity.userBuyer.UserBuyerRepository;
import com.project.demo.rest.userBuyer.UserBuyerRestController;
import com.project.demo.logic.entity.userBrand.UserBrand;
import com.project.demo.logic.entity.userBrand.UserBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ratesProduct")
public class RateProductController {
    @Autowired
    private RateProductRepository rateProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserBuyerRepository userBuyerRepository;


    @Autowired
    private UserBuyerRestController userBuyerRestController;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER_BRAND','SUPER_ADMIN', 'USER')")
    public List<RateProduct> getAllRatesProduct() {
        return rateProductRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','SUPER_ADMIN')")
    public RateProduct addRateProduct(@RequestBody RateProduct rateProduct) {

        if (rateProduct == null || rateProduct.getRate() == null || rateProduct.getProduct().getId() == null) {
            throw new IllegalArgumentException("RateProduct or users cannot be null");
        }

        Long productId = rateProduct.getProduct().getId();
        Long buyerId = userBuyerRestController.authenticatedUser().getId();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));


        UserBuyer userBuyer = userBuyerRepository.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("User Buyer not found with id: " + buyerId));

        // Check if the user has already rated this brand
        Optional<RateProduct> existingRating = rateProductRepository.findByIdBuyerAndIdProduct(buyerId, productId);

        if (existingRating.isPresent()) {
            throw new IllegalArgumentException("User has already rated this brand.");
        }else {

            rateProduct.setProduct(product);
            rateProduct.setUserBuyer(userBuyer);

            return rateProductRepository.save(rateProduct);
        }
    }

    @GetMapping("/{id}")
    public RateProduct getProductById(@PathVariable Long id) {
        return rateProductRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @GetMapping("/rate/{productId}")
    @PreAuthorize("hasAnyRole('USER','SUPER_ADMIN')")
    public Optional<RateProduct> hasRatedProduct(@PathVariable Long productId) {
        Long userId = userBuyerRestController.authenticatedUser().getId();

        return rateProductRepository.findByIdBuyerAndIdProduct(userId,productId);
    }
}
