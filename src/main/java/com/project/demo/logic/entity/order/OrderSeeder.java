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
        this.createOrders2();
        this.createOrders3();
        this.createOrders4();
        this.createOrders5();
        this.createOrders6();
        this.createOrders7();

    }

    private void createOrders() {
        //estados de la orden
        List<String> orderStatuses = Arrays.asList("Pendiente", "En Proceso", "Enviado", "Entregado");

        String selectedStatus = orderStatuses.get(0);

        UserBuyer userBuyer = userBuyerRepository.findById(4L).orElse(null);
        if (userBuyer == null) {
            System.out.println("UserBuyer no encontrado");
            return; // Usuario comprador no encontrado
        }

        Product product = productRepository.findById(1L).orElse(null);
        if (product == null) {
            System.out.println("Producto 1 de la orden 1 no encontrado");
            return; // Producto no encontrado
        }

        // Crear la orden con el estado seleccionado
        Order order = new Order();
        order.setUserBuyer(userBuyer);
        order.setProduct(product);
        order.setQuantity(1);
        order.setSubtotal(product.getPrice() * 2);
        order.setShippingCost(5.00);
        order.setTotal(order.getSubtotal() + order.getShippingCost());
        order.setStatus(selectedStatus);

        orderRepository.save(order);

        /*
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
        }*/
    }

    private void createOrders2() {
        //estados de la orden
        List<String> orderStatuses = Arrays.asList("Pendiente", "En Proceso", "Enviado", "Entregado");

        String selectedStatus = orderStatuses.get(0);

        UserBuyer userBuyer = userBuyerRepository.findById(5L).orElse(null);
        if (userBuyer == null) {
            System.out.println("UserBuyer no encontrado");
            return; // Usuario comprador no encontrado
        }

        Product product = productRepository.findById(1L).orElse(null);
        if (product == null) {
            System.out.println("Producto 1 de la orden 2 no encontrado");
            return; // Producto no encontrado
        }

        // Crear la orden con el estado seleccionado
        Order order = new Order();
        order.setUserBuyer(userBuyer);
        order.setProduct(product);
        order.setQuantity(3);
        order.setSubtotal(product.getPrice() * 2);
        order.setShippingCost(10.00);
        order.setTotal(order.getSubtotal() + order.getShippingCost());
        order.setStatus(selectedStatus);

        orderRepository.save(order);
    }

    private void createOrders3() {
        //estados de la orden
        List<String> orderStatuses = Arrays.asList("Pendiente", "En Proceso", "Enviado", "Entregado");

        String selectedStatus = orderStatuses.get(1);

        UserBuyer userBuyer = userBuyerRepository.findById(5L).orElse(null);
        if (userBuyer == null) {
            System.out.println("UserBuyer no encontrado");
            return; // Usuario comprador no encontrado
        }

        Product product = productRepository.findById(2L).orElse(null);
        if (product == null) {
            System.out.println("Producto 2 de la orden 3 no encontrado");
            return; // Producto no encontrado
        }

        // Crear la orden con el estado seleccionado
        Order order = new Order();
        order.setUserBuyer(userBuyer);
        order.setProduct(product);
        order.setQuantity(3);
        order.setSubtotal(product.getPrice() * 2);
        order.setShippingCost(10.00);
        order.setTotal(order.getSubtotal() + order.getShippingCost());
        order.setStatus(selectedStatus);

        orderRepository.save(order);
    }

    private void createOrders4() {
        //estados de la orden
        List<String> orderStatuses = Arrays.asList("Pendiente", "En Proceso", "Enviado", "Entregado");

        String selectedStatus = orderStatuses.get(2);

        UserBuyer userBuyer = userBuyerRepository.findById(5L).orElse(null);
        if (userBuyer == null) {
            System.out.println("UserBuyer no encontrado");
            return; // Usuario comprador no encontrado
        }

        Product product = productRepository.findById(3L).orElse(null);
        if (product == null) {
            System.out.println("Producto 5 de la orden 4 no encontrado");
            return; // Producto no encontrado
        }

        // Crear la orden con el estado seleccionado
        Order order = new Order();
        order.setUserBuyer(userBuyer);
        order.setProduct(product);
        order.setQuantity(3);
        order.setSubtotal(product.getPrice() * 2);
        order.setShippingCost(10.00);
        order.setTotal(order.getSubtotal() + order.getShippingCost());
        order.setStatus(selectedStatus);

        orderRepository.save(order);
    }

    private void createOrders5() {
        //estados de la orden
        List<String> orderStatuses = Arrays.asList("Pendiente", "En Proceso", "Enviado", "Entregado");

        String selectedStatus = orderStatuses.get(0);

        UserBuyer userBuyer = userBuyerRepository.findById(6L).orElse(null);
        if (userBuyer == null) {
            System.out.println("UserBuyer no encontrado");
            return; // Usuario comprador no encontrado
        }

        Product product = productRepository.findById(3L).orElse(null);
        if (product == null) {
            System.out.println("Producto 3 de la orden 5 no encontrado");
            return; // Producto no encontrado
        }

        // Crear la orden con el estado seleccionado
        Order order = new Order();
        order.setUserBuyer(userBuyer);
        order.setProduct(product);
        order.setQuantity(5);
        order.setSubtotal(product.getPrice() * 2);
        order.setShippingCost(15.00);
        order.setTotal(order.getSubtotal() + order.getShippingCost());
        order.setStatus(selectedStatus);

        orderRepository.save(order);

    }

    private void createOrders6() {
        //estados de la orden
        List<String> orderStatuses = Arrays.asList("Pendiente", "En Proceso", "Enviado", "Entregado");

        String selectedStatus = orderStatuses.get(1);

        UserBuyer userBuyer = userBuyerRepository.findById(6L).orElse(null);
        if (userBuyer == null) {
            System.out.println("UserBuyer no encontrado");
            return; // Usuario comprador no encontrado
        }

        Product product = productRepository.findById(4L).orElse(null);
        if (product == null) {
            System.out.println("Producto 4 de la orden 6 no encontrado");
            return; // Producto no encontrado
        }

        // Crear la orden con el estado seleccionado
        Order order = new Order();
        order.setUserBuyer(userBuyer);
        order.setProduct(product);
        order.setQuantity(5);
        order.setSubtotal(product.getPrice() * 2);
        order.setShippingCost(15.00);
        order.setTotal(order.getSubtotal() + order.getShippingCost());
        order.setStatus(selectedStatus);

        orderRepository.save(order);

    }

    private void createOrders7() {
        //estados de la orden
        List<String> orderStatuses = Arrays.asList("Pendiente", "En Proceso", "Enviado", "Entregado");

        String selectedStatus = orderStatuses.get(3);

        UserBuyer userBuyer = userBuyerRepository.findById(6L).orElse(null);
        if (userBuyer == null) {
            System.out.println("UserBuyer no encontrado");
            return; // Usuario comprador no encontrado
        }

        Product product = productRepository.findById(5L).orElse(null);
        if (product == null) {
            System.out.println("Producto 5 de la orden 7 no encontrado");
            return; // Producto no encontrado
        }

        // Crear la orden con el estado seleccionado
        Order order = new Order();
        order.setUserBuyer(userBuyer);
        order.setProduct(product);
        order.setQuantity(5);
        order.setSubtotal(product.getPrice() * 2);
        order.setShippingCost(15.00);
        order.setTotal(order.getSubtotal() + order.getShippingCost());
        order.setStatus(selectedStatus);

        orderRepository.save(order);

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
        order.setDeliveryLocation("San Rafael Arriba, Desamparados");
        order.setCurrentLocation("San Rafael Abajo, Desamparados");
        orderRepository.save(order);
        System.out.println("Orden creada: " + order);
    }*/
}