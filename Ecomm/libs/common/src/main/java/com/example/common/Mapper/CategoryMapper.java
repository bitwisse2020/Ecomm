package com.example.common.Mapper;


import com.example.common.DTO.CategoryResponse;
import com.example.common.Models.BaseCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(BaseCategory category);
}
