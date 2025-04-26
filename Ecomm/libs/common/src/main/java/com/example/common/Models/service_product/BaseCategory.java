package com.example.common.Models.service_product;

public interface BaseCategory {
    Long getId();

    String getName();

    String getDescription();

    Long getParentCategoryId(); // ID of the parent category, null if top-level

    String getParentCategoryName();

    String getSlug();
}
