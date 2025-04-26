package com.example.common.Mapper.service_product;


import com.example.common.DTO.service_product.CategoryResponse;
import com.example.common.Models.service_product.BaseCategory;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(BaseCategory category);

    default Page<CategoryResponse> toResponsePage(Page<? extends BaseCategory> page) {
        return page.map(this::toResponse);
    }
}
