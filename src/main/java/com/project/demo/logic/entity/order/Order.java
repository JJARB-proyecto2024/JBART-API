package com.project.demo.logic.entity.order;

import com.project.demo.logic.entity.userBuyer.UserBuyer;
import com.project.demo.logic.entity.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "user_buyer_id")
    private UserBuyer userBuyer;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    private Double subTotal;
    private Double shippingCost;
    private Double total;
    private String status;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public UserBuyer getUserBuyer() {
        return userBuyer;
    }

    public void setUserBuyer(UserBuyer userBuyer) {
        this.userBuyer = userBuyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
