package com.example.product_service.Utility;

import com.example.product_service.DTO.CategoryRequest;
import com.example.common.DTO.CategoryResponse;
import com.example.product_service.Models.Category;

public class CategoryMapperUtils {
    public static CategoryResponse getCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .slug(category.getSlug())
                .parentCategoryId(category.getParentCategory().getId())
                .parentCategoryName(category.getParentCategory().getName())
                .build();
    }

    public static Category getCategoryEntity(CategoryRequest categoryRequest, Category parentCategory)  {

        return Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .parentCategory(parentCategory)
                .build();
    }
}
