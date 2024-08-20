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
        createOrders();
    }

    private void createOrders() {
        List<String> orderStatuses = Arrays.asList("Pendiente", "En Proceso", "Enviado", "Entregado");
        Object[][] ordersData = {
                {4L, 1L, 1, 2, 5.00, orderStatuses.get(0)},
                {5L, 1L, 3, 2, 10.00, orderStatuses.get(0)},
                {5L, 2L, 3, 2, 10.00, orderStatuses.get(1)},
                {5L, 3L, 3, 2, 10.00, orderStatuses.get(2)},
                {6L, 3L, 5, 2, 15.00, orderStatuses.get(0)},
                {6L, 4L, 5, 2, 15.00, orderStatuses.get(1)},
                {6L, 5L, 5, 2, 15.00, orderStatuses.get(3)}
        };

        for (Object[] orderData : ordersData) {
            createOrder(orderData);
        }
    }

    private void createOrder(Object[] orderData) {
        UserBuyer userBuyer = userBuyerRepository.findById((Long) orderData[0]).orElse(null);
        if (userBuyer == null) {
            System.out.println("UserBuyer no encontrado");
            return;
        }

        Product product = productRepository.findById((Long) orderData[1]).orElse(null);
        if (product == null) {
            System.out.println("Producto no encontrado");
            return;
        }

        Order order = new Order();
        order.setUserBuyer(userBuyer);
        order.setProduct(product);
        order.setQuantity((Integer) orderData[2]);
        order.setSubtotal(product.getPrice() * (Integer) orderData[3]);
        order.setShippingCost((Double) orderData[4]);
        order.setTotal(order.getSubtotal() + order.getShippingCost());
        order.setStatus((String) orderData[5]);

        orderRepository.save(order);
    }
}