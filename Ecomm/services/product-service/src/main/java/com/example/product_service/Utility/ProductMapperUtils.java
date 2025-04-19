package com.example.product_service.Utility;

import com.example.product_service.DTO.ProductRequest;
import com.example.product_service.DTO.ProductResponse;
import com.example.product_service.Models.Category;
import com.example.product_service.Models.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductMapperUtils  {

    public static ProductResponse getProductResponse(Product product, Product productEntity) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStockQuantity())
                .categoryName(product.getCategory().getName())
                .imageUrl(productEntity.getImageUrl())
                .build();
    }

    public static Product getProductEntity(ProductRequest productRequest, Category category) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .sku(SKUGenerator.generateSku(productRequest, category.getName()))
                .slug(SlugGenerator.generateSlug(productRequest.getName()))
                .imageUrl(productRequest.getImageUrl())
                .category(category)
                .build();
    }
}
