package com.project.demo.rest.order;

import com.project.demo.logic.entity.order.Order;
import com.project.demo.logic.entity.order.OrderRepository;
import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.product.ProductRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.userBuyer.UserBuyer;
import com.project.demo.logic.entity.userBuyer.UserBuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserBuyerRepository userBuyerRepository;

    @Autowired
    private ProductRepository productRepository;



    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public Order addOrder(@RequestBody Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        // You can add more validations as needed

        return orderRepository.save(order);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    // Actualizar los campos del pedido
                    existingOrder.setQuantity(order.getQuantity());
                    existingOrder.setSubTotal(order.getSubTotal());
                    existingOrder.setShippingCost(order.getShippingCost());
                    existingOrder.setTotal(order.getTotal());
                    existingOrder.setStatus(order.getStatus());
                    existingOrder.setUpdatedAt(order.getUpdatedAt());

                    // Actualizar UserBuyer si se proporciona
                    if (order.getUserBuyer() != null && order.getUserBuyer().getId() != null) {
                        Optional<UserBuyer> userBuyer = userBuyerRepository.findById(order.getUserBuyer().getId());
                        if (userBuyer.isPresent()) {
                            existingOrder.setUserBuyer(userBuyer.get());
                        } else {
                            throw new RuntimeException("UserBuyer not found with id: " + order.getUserBuyer().getId());
                        }
                    }

                    // Actualizar Product si se proporciona
                    if (order.getProduct() != null && order.getProduct().getId() != null) {
                        Optional<Product> product = productRepository.findById(order.getProduct().getId());
                        if (product.isPresent()) {
                            existingOrder.setProduct(product.get());
                        } else {
                            throw new RuntimeException("Product not found with id: " + order.getProduct().getId());
                        }
                    }

                    return orderRepository.save(existingOrder);
                })
                .orElseGet(() -> {
                    // Si el pedido no existe, configuramos el ID y lo guardamos como nuevo
                    order.setId(id);
                    return orderRepository.save(order);
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public void deleteOrder(@PathVariable Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
