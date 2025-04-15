package com.example.product_service.Controller;

import com.example.product_service.DTO.CategoryRequest;
import com.example.product_service.DTO.CategoryResponse;
import com.example.product_service.Exception.CategoryNotFoundException;
import com.example.product_service.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) throws CategoryNotFoundException {
        CategoryResponse categoryResponse=this.categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(categoryResponse);
    }
}
