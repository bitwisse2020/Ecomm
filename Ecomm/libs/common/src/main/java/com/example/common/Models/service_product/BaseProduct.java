package com.example.common.Models.service_product;


import java.math.BigDecimal;

public interface BaseProduct {
    Long getId();

    String getName();

    String getDescription();

    BigDecimal getPrice();

    Integer getStock();

    String getImageUrl();

    // Optional Fields (Default Methods)
    default String getCategoryName() {
        return null; // Override in implementations
    }

    // Validation Support
    default boolean isInStock() {
        return getStock() != null && getStock() > 0;
    }

}

