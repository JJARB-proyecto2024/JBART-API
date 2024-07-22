//package com.project.demo.logic.entity.order;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface OrderRepository extends JpaRepository<Order, Long> {
//
//    @Query("SELECT o FROM Order o WHERE o.product.userBrand.id = ?1")
//    List<Order> findAllByUserBrandId(Long userBrandId);
//
//}