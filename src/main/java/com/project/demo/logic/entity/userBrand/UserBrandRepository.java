package com.project.demo.logic.entity.userBrand;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBrandRepository extends JpaRepository<UserBrand, Long> {
    List<UserBrand> findByBrandName(String brandName);
}
