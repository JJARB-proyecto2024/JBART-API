package com.project.demo.logic.entity.order;

import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.product.ProductRepository;
import com.project.demo.logic.entity.userBuyer.UserBuyer;
import com.project.demo.logic.entity.userBuyer.UserBuyerRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class OrderSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserBuyerRepository userBuyerRepository;

    public OrderSeeder(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            UserBuyerRepository userBuyerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userBuyerRepository = userBuyerRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        List<OrderData> ordersData = Arrays.asList(
                new OrderData(4L, 1L, 1, 2, 5.00, "Pendiente"),
                new OrderData(5L, 1L, 3, 2, 10.00, "Pendiente"),
                new OrderData(5L, 2L, 3, 2, 10.00, "En Proceso"),
                new OrderData(5L, 3L, 3, 2, 10.00, "Enviado"),
                new OrderData(6L, 3L, 5, 2, 15.00, "Pendiente"),
                new OrderData(6L, 4L, 5, 2, 15.00, "En Proceso"),
                new OrderData(6L, 5L, 5, 2, 15.00, "Entregado")
        );

        ordersData.forEach(this::createOrder);
    }

    private void createOrder(OrderData data) {
        UserBuyer userBuyer = userBuyerRepository.findById(data.userBuyerId).orElse(null);
        if (userBuyer == null) {
            System.out.println("UserBuyer no encontrado");
            return;
        }

        Product product = productRepository.findById(data.productId).orElse(null);
        if (product == null) {
            System.out.println("Producto no encontrado");
            return;
        }

        Order order = new Order();
        order.setUserBuyer(userBuyer);
        order.setProduct(product);
        order.setQuantity(data.quantity);
        order.setSubTotal(product.getPrice() * data.quantity);
        order.setShippingCost(data.shippingCost);
        order.setTotal(order.getSubTotal() + order.getShippingCost());
        order.setStatus(data.status);

        orderRepository.save(order);
    }

    private static class OrderData {
        Long userBuyerId;
        Long productId;
        int quantity;
        double priceMultiplier;
        double shippingCost;
        String status;

        OrderData(Long userBuyerId, Long productId, int quantity, double priceMultiplier, double shippingCost, String status) {
            this.userBuyerId = userBuyerId;
            this.productId = productId;
            this.quantity = quantity;
            this.priceMultiplier = priceMultiplier;
            this.shippingCost = shippingCost;
            this.status = status;
        }
    }
}
