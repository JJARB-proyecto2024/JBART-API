package com.project.demo.logic.entity.category;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class CategorySeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;

    public CategorySeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadCategories();
    }

    private void loadCategories() {
        String[] categoryNames = new String[]{"CAMISAS", "SWEATERS", "PANTALONES", "PANTALONETAS", "CALZADO"};
        Map<String, String> categoryDescriptionMap = Map.of(
                "CAMISAS", "Camisas",
                "SWEATERS", "Sweaters",
                "PANTALONES", "Pantalones",
                "PANTALONETAS", "Pantalonetas",
                "CALZADO", "Calzado"
        );
        Arrays.stream(categoryNames).forEach((categoryName) -> {
            Optional<Category> optionalCategory = categoryRepository.findByName(categoryName);

            optionalCategory.ifPresentOrElse(System.out::println, () -> {
                Category categoryToCreate = new Category();

                categoryToCreate.setName(categoryName);
                categoryToCreate.setDescription(categoryDescriptionMap.get(categoryName));
                categoryRepository.save(categoryToCreate);
            });
        });
    }
}
