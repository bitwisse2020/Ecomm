package com.example.common.Models;

public interface BaseCategory {
    Long getId();

    String getName();

    String getDescription();

    Long getParentCategoryId(); // ID of the parent category, null if top-level

    String getParentCategoryName();

    String getSlug();
}
