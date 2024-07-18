package com.project.demo.logic.entity.order;

import com.project.demo.logic.entity.userBuyer.UserBuyer;
import jakarta.persistence.*;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "user_buyer_id")
    private UserBuyer userBuyer;
}
