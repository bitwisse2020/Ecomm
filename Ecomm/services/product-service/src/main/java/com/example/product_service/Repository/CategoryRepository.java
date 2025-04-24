package com.example.product_service.Repository;

import com.example.product_service.Models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findBySlug(String categoryIdOrSlug);

//    @Query(value = """
//            Select * from categories c
//            where c.parent_category_id is null;
//            """)
//    Page<Category> getTopLevelCategories(Pageable pageable);

    Page<Category> findByParentCategoryIsNull(Pageable pageable);

    boolean existsBySlugAndIdNot(String finalSlug, Long currentId);

    @Query("SELECT c FROM Category c WHERE c.parentCategory.id = :parentId")
    Page<Category> getByParentCategoryId(@Param("parentId") Long parentId, Pageable pageable);

}
