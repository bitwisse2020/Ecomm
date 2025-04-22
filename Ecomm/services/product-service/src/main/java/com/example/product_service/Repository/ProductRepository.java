package com.example.product_service.Repository;

import com.example.product_service.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);

    boolean existsBySlugAndIdNot(String slug, Long id);

    Optional<Product> findBySku(String sku);

    @Query(value = """
            SELECT * FROM products p
            WHERE
                LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """, nativeQuery = true)
    Page<Product> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = """
            SELECT * FROM products p
            WHERE p.category_id=:categoryId
            """, nativeQuery = true)
    Page<Product> getProductsByCategoryId(Long categoryId, Pageable pageable);

    @Query(value = """
            SELECT * FROM products p
            INNER JOIN categories c on p.category_id=c.id
            WHERE c.slug=:categorySlug
            """, nativeQuery = true)
    Page<Product> getProductsByCategorySlug(String categorySlug, Pageable pageable);
}
