package com.example.product_service.Service;

import com.example.product_service.DTO.CategoryRequest;
import com.example.product_service.DTO.CategoryResponse;
import com.example.product_service.Models.Category;
import com.example.product_service.Repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse createCategory(@Valid CategoryRequest categoryRequest) {
        Category category=Category.builder()
                .name()
                .description()
                .imageUrl()
                .slug()
                .build();
    }
}
