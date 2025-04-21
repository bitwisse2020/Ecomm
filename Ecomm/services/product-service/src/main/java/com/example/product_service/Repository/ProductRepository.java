package com.example.product_service.Repository;

import com.example.product_service.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query(value = """
                SELECT * FROM product p
                WHERE
                    LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
                """
            ,nativeQuery = true)
    Page<Product> searchByKeyword(@Param("keyword")String keyword, Pageable pageable);

    Optional<Object> findBySlug(String finalSlug);

    boolean existsBySlugAndIdNot(String finalSlug, Long currentId);
}
