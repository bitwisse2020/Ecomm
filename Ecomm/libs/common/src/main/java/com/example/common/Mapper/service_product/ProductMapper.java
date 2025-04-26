package com.example.common.Mapper.service_product;

import com.example.common.DTO.service_product.ProductResponse;
import com.example.common.Models.service_product.BaseProduct;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ProductMapper {

    // Convert Entity → DTO
    //@Mapping(target = "categoryName", source = "product.categoryName")
    ProductResponse toResponse(BaseProduct product);

    default Page<ProductResponse> toResponsePage(Page<? extends BaseProduct> page) {
        return page.map(this::toResponse);
    }



    /**
     * Cross-service unused Mappers for product data.


     // Convert DTO &rarr; Entity (for creation)
     @Mapping(target = "id", ignore = true)
     @Mapping(target = "category", ignore = true) // Handled separately in service
     @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
     Product toEntity(ProductCreateRequest request);

     // Update Entity from DTO
     @Mapping(target = "id", ignore = true)
     @Mapping(target = "category", ignore = true)
     void updateFromRequest(ProductUpdateRequest request, @MappingTarget BaseProduct product);

     // Custom mapping for inventory service
     @Mapping(target = "warehouseStock", constant = "0") // Default value
     InventoryProductResponse toInventoryResponse(Product product);

     */
}
