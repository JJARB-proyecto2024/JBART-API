package com.project.demo.unit_test;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.userBrand.UserBrand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductTest {

    private Product product;


    @BeforeEach
    void setUp() {
        product = new Product();
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String name = "Coffee Premium";
        Double price = 10.99;
        String size = "Small";
        Integer quantity = 1500;
        String status = "Active";
        Integer rate = 5;
        String model = "Bag 500g";

        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setSize(size);
        product.setQuantityInStock(quantity);
        product.setStatus(status);
        product.setRate(rate);
        product.setModel(model);

        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(size, product.getSize());
        assertEquals(quantity, product.getQuantityInStock());
        assertEquals(status, product.getStatus());
        assertEquals(rate, product.getRate());
        assertEquals(model, product.getModel());
    }

    @Test
    void testRelations_Between_Objets() {
        Category category = new Category();
        category.setName("Drinks");

        product.setCategory(category);
        assertNotNull(product.getCategory());
        assertEquals("Drinks", product.getCategory().getName());

        UserBrand userBrand = new UserBrand();
        userBrand.setName("Starbucks");

        product.setUserBrand(userBrand);
        assertNotNull(product.getUserBrand());
        assertEquals("Starbucks", product.getUserBrand().getName());
    }
}
