package com.project.demo.rest.rateBrand;

import com.project.demo.logic.entity.rateBrand.RateBrand;
import com.project.demo.logic.entity.rateBrand.RateBrandRepository;
import com.project.demo.logic.entity.userBuyer.UserBuyer;
import com.project.demo.logic.entity.userBuyer.UserBuyerRepository;
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
@RequestMapping("/ratesBrand")
public class RateBrandController {

    @Autowired
    private RateBrandRepository rateBrandRepository;

    @Autowired
    private UserBrandRepository userBrandRepository;

    @Autowired
    private UserBuyerRepository userBuyerRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER_BRAND','SUPER_ADMIN', 'USER')")
    public List<RateBrand> getAllRatesBrand() {
        return rateBrandRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER_BRAND','SUPER_ADMIN')")
    public RateBrand addRateBrand(@RequestBody RateBrand rateBrand) {

        if (rateBrand == null || rateBrand.getRate() == null || rateBrand.getUserBrand().getId() == null || rateBrand.getUserBuyer().getId() == null) {
            throw new IllegalArgumentException("RateBrand or users cannot be null");
        }

        Long brandId = rateBrand.getUserBrand().getId();
        Long buyerId = rateBrand.getUserBuyer().getId();

        UserBrand userBrand = userBrandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("User Brand not found with id: " + brandId));

        UserBuyer userBuyer = userBuyerRepository.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("User Buyer not found with id: " + buyerId));

        rateBrand.setUserBrand(userBrand);
        rateBrand.setUserBuyer(userBuyer);

        return rateBrandRepository.save(rateBrand);
    }

    @GetMapping("/{id}")
    public RateBrand getRateBrandById(@PathVariable Long id) {
        return rateBrandRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
