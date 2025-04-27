package com.example.product_service.Controller;

import com.example.product_service.DTO.CategoryRequest;
import com.example.common.DTO.service_product.CategoryResponse;
import com.example.product_service.DTO.UpdateCategoryRequest;
import com.example.product_service.Exception.CategoryNotFoundException;
import com.example.product_service.Exception.ResourceConflictException;
import com.example.product_service.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public ResponseEntity<?> getAllCategories(
            @RequestParam(value = "type", required = false) String type,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        List<CategoryResponse> categories;
        if ("top-level".equalsIgnoreCase(type)) {
            categories = categoryService.getTopLevelCategories(pageable);
        } else {
            categories = categoryService.getAllCategories(pageable);
        }
        return ResponseEntity.ok(categories);
    }


    @GetMapping("/{idOrSlug}") // Can accept ID or Slug
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String idOrSlug) throws CategoryNotFoundException {
        CategoryResponse categoryResponse;
        try {
            // Try interpreting as ID first
            Long id = Long.parseLong(idOrSlug);
            categoryResponse = categoryService.getCategoryById(id);
        } catch (NumberFormatException e) {
            // If not a number, assume it's a slug
            categoryResponse = categoryService.getCategoryBySlug(idOrSlug);
        }
        return ResponseEntity.ok(categoryResponse);
    }


    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<?> getSubcategories(@PathVariable Long parentId,
                                                                   @PageableDefault(size = 20, sort = "name") Pageable pageable) throws CategoryNotFoundException {
        List<CategoryResponse> subcategories = categoryService.getSubCategories(parentId,pageable);
        return ResponseEntity.ok(subcategories);
    }


    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) throws CategoryNotFoundException, ResourceConflictException {
        CategoryResponse createdCategory = categoryService.createCategory(request);

        // Build the location URI for the newly created resource
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategory.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdCategory); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody UpdateCategoryRequest request) throws ResourceConflictException, CategoryNotFoundException {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(updatedCategory); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws CategoryNotFoundException {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
