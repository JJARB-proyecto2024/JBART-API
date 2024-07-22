package com.project.demo.logic.entity.category;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class CategorySeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;


    public CategorySeeder(
            CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createCategory("Calzado", "Calzado");
        this.createCategory("Camisas", "Camisas");
        this.createCategory("Pantalones", "Pantalones");
    }

    private void createCategory(String name, String description) {
        Optional<Category> existingCategory = categoryRepository.findByName(name);
        if (existingCategory.isEmpty()) {
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            categoryRepository.save(category);
        }
    }
}
