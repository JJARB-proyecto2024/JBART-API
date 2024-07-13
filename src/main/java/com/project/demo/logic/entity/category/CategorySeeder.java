package com.project.demo.logic.entity.category;

import com.project.demo.logic.entity.category.CategoryEnum;
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
        CategoryEnum[] categoryNames = new CategoryEnum[]{CategoryEnum.CAMISAS, CategoryEnum.SWEATERS, CategoryEnum.PANTALONES, CategoryEnum.PANTALONETAS, CategoryEnum.CALZADO};
        Map<CategoryEnum, String> categoryDescriptionMap = Map.of(
                CategoryEnum.CAMISAS, "Camisas",
                CategoryEnum.SWEATERS, "Sweaters",
                CategoryEnum.PANTALONES, "Pantalones",
                CategoryEnum.PANTALONETAS, "Pantalonetas",
                CategoryEnum.CALZADO, "Calzado"
        );
        Arrays.stream(categoryNames).forEach((categoryName) -> {
            Optional<Category> optionalRole = categoryRepository.findByName(categoryName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Category categoryToCreate = new Category();

                categoryToCreate.setName(categoryName);
                categoryToCreate.setDescription(categoryDescriptionMap.get(categoryName));
                categoryRepository.save(categoryToCreate);
            });
        });
    }
}