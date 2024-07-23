package com.project.demo.logic.entity.rateBrand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RateBrandRepository extends JpaRepository<RateBrand, Long> {

    @Query("SELECT r FROM RateBrand r WHERE r.userBrand = ?1")
    Optional<RateBrand> findByIdBrand(Long id);

    @Query("SELECT r FROM RateBrand r WHERE r.userBuyer = ?1")
    Optional<RateBrand> findByIdBuyer(Long id);
}
