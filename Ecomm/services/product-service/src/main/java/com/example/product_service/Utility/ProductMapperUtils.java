package com.example.product_service.Utility;

import com.example.product_service.DTO.ProductRequest;
import com.example.common.DTO.ProductResponse;
import com.example.product_service.Models.Category;
import com.example.product_service.Models.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductMapperUtils  {

    public static Product getProductEntity(ProductRequest productRequest, Category category) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .sku(SKUGenerator.generateSku(productRequest, category.getName()))
                .slug(SlugGenerator.generateSlug(productRequest.getName()))
                .imageUrl(productRequest.getImageUrl())
                .category(category)
                .build();
    }

}
