package com.example.common.Mapper;


import com.example.common.DTO.CategoryResponse;
import com.example.common.Models.BaseCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(BaseCategory category);

    default Page<CategoryResponse> toResponsePage(Page<? extends BaseCategory> page) {
        return page.map(this::toResponse);
    }
}
