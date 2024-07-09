package com.project.demo.logic.entity.userBrand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBrandSevice {
    @Autowired
    private UserBrandRepository repository;


    public Long createUserBrand(UserBrand userBrand) {
        if (!repository.findByBrandName(userBrand.getBrandName()).isEmpty()) {
            throw new RuntimeException("User Brand with name " + userBrand.getBrandName() + " already exists");
        }
        repository.save(userBrand);
        return userBrand.getId();
    }

    public UserBrand get(Long id) {
        Optional<UserBrand> userBrand = repository.findById(id);
        return userBrand.orElse(null);
    }

    public List<UserBrand> getAllUserBrand() {
        return repository.findAll();
    }

    public void updateUserBrand(UserBrand userBrand) {
        repository.save(userBrand);
    }

    public void deleteUseBrandById(Long id) {
        repository.deleteById(id);
    }

    private void sendRegistrationEmail(String email) {}
}
