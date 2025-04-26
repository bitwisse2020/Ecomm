package com.example.common.DTO.service_product;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields in JSON
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String slug;
    private Long parentCategoryId; // ID of the parent category, null if top-level
    private String parentCategoryName;


//    TODO: Enhancements
//    For hierarchical categories (optional)
//    private Long parentId;
//    private List<CategoryResponse> subCategories;
//
//    For product counts (if needed)
//    private Integer productCount;

}
