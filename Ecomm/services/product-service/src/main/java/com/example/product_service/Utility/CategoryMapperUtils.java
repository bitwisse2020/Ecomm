package com.example.product_service.Utility;

import com.example.product_service.DTO.CategoryRequest;
import com.example.product_service.Models.Category;

public class CategoryMapperUtils {

    public static Category getCategoryEntity(CategoryRequest categoryRequest, Category parentCategory, String finalSlug) {

        return Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .parentCategory(parentCategory)
                .slug(finalSlug)
                .build();
    }
}
