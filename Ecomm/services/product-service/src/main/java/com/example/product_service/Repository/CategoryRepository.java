package com.example.product_service.Repository;

import com.example.product_service.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Collection<Category> findBySlug(String categoryIdOrSlug);
}
