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
        this.createOrders();
    }

    private void createOrders() {
        //estados de la orden
        List<String> orderStatuses = Arrays.asList("Pendiente", "En Proceso", "Enviado", "Entregado");


        UserBuyer userBuyer = userBuyerRepository.findById(2L).orElse(null);
        if (userBuyer == null) {
            System.out.println("UserBuyer no encontrado");
            return; // Usuario comprador no encontrado
        }

        Product product = productRepository.findById(1L).orElse(null);
        if (product == null) {
            System.out.println("Producto no encontrado");
            return; // Producto no encontrado
        }

        // Crear  orden
        for (String status : orderStatuses) {
            Order order = new Order();
            order.setUserBuyer(userBuyer);
            order.setProduct(product);
            order.setQuantity(2);
            order.setSubTotal(product.getPrice() * 2);
            order.setShippingCost(5.00);
            order.setTotal(order.getSubTotal() + order.getShippingCost());
            order.setStatus(status);

            orderRepository.save(order);
            System.out.println("Orden creada con ID: " + order.getId() + ", Estado: " + order.getStatus());
        }
    }

    /*
    private void createOrders() {
        Order order = new Order();

        UserBuyer userBuyer = userBuyerRepository.findById(2L).orElse(null);
        if (userBuyer == null) {
            System.out.println("UserBuyer no encontrado");
            return; // Usuario comprador no encontrado
        }

        Product product = productRepository.findById(1L).orElse(null);
        if (product == null) {
            System.out.println("Producto no encontrado");
            return; // Producto no encontrado
        }

        order.setUserBuyer(userBuyer);
        order.setProduct(product);
        order.setQuantity(2);
        order.setSubTotal(product.getPrice() * 2);
        order.setShippingCost(5.00);
        order.setTotal(order.getSubTotal() + order.getShippingCost());
        order.setStatus("Pendiente");

        orderRepository.save(order);
        System.out.println("Orden creada: " + order);
    }*/
}