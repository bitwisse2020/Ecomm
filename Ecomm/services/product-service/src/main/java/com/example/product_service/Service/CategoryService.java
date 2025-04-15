package com.example.product_service.Service;

import com.example.product_service.DTO.CategoryRequest;
import com.example.product_service.DTO.CategoryResponse;
import com.example.product_service.Exception.CategoryNotFoundException;
import com.example.product_service.Models.Category;
import com.example.product_service.Repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.example.product_service.Utility.CategoryMapperUtils.getCategoryEntity;
import static com.example.product_service.Utility.CategoryMapperUtils.getCategoryResponse;
import static com.example.product_service.Utility.CategoryMapperUtils.getCategoryResponse;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse createCategory(@Valid CategoryRequest categoryRequest) throws CategoryNotFoundException {
        Category categoryEntity = getCategoryEntity(categoryRequest);
        Long parentCategoryId= categoryRequest.getParentCategoryId();
        if( parentCategoryId!= null){
            Category parentCategory=categoryRepository.findById(parentCategoryId)
                    .orElseThrow(()->new CategoryNotFoundException("No such parent category exists with Id: "+parentCategoryId));
            categoryEntity.setParentCategory(parentCategory);
        }
        Category category=categoryRepository.save(categoryEntity);
        return getCategoryResponse(category);
    }


}
