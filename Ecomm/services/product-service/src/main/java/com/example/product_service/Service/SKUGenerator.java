package com.example.product_service.Service;

import com.example.product_service.DTO.ProductRequest;
import org.springframework.stereotype.Service;

@Service
public class SKUGenerator {
    public String generateSku(ProductRequest product,String category) {
        return String.format("%s-%s-%s",
                product.getName(),
                category.substring(0,3).toUpperCase(),
                product.getDescription().substring(0,5)
        );
    }
}
