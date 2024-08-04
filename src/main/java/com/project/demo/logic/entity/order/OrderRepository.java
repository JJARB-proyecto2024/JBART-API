package com.project.demo.logic.entity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.product.userBrand.id = ?1 ORDER BY o.createdAt DESC")
    List<Order> findByBrandId(Long userBrandId);

    @Query("SELECT o FROM Order o WHERE o.userBuyer.id = ?1 ORDER BY o.createdAt DESC")
    List<Order> findByUserId(Long userId);

    @Query("SELECT p.name AS productName, " +
        "c.name AS categoryName, SUM(o.quantity) AS totalQuantitySold " +
        "FROM Order o " +
        "JOIN o.product p " +
        "JOIN p.category c " +
        "WHERE p.userBrand.id = :userBrandId " +
        "GROUP BY p.Id, p.name, c.name " +
        "ORDER BY totalQuantitySold DESC")
    List<Object[]> getMostSoldProducts(@Param("userBrandId") Long userBrandId);

    @Query("SELECT p.userBrand.id AS userBrandId, " +
            "p.userBrand.brandName AS brandName, " +
            "SUM(o.quantity * p.price) AS totalEarnings " +
            "FROM Order o " +
            "JOIN o.product p " +
            "GROUP BY p.userBrand.id, p.userBrand.brandName " +
            "ORDER BY totalEarnings DESC")
    List<Object[]> getTotalEarningsByBrand();



}
