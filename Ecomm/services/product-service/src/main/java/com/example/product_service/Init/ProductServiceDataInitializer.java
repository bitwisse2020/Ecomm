package com.example.product_service.Init;

import com.example.product_service.Models.Category;
import com.example.product_service.Models.Product;
import com.example.product_service.Repository.CategoryRepository;
import com.example.product_service.Repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

@Component
public class ProductServiceDataInitializer {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;
    private final PlatformTransactionManager transactionManager;


    public ProductServiceDataInitializer(CategoryRepository categoryRepository, ProductRepository productRepository, EntityManager entityManager, PlatformTransactionManager transactionManager) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.entityManager = entityManager;
        this.transactionManager = transactionManager;
    }

    @PostConstruct
    @Transactional
    public void initAll() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            initCategories();
            initProducts();
            return null;
        });
    }

    private void initCategories() {
        if (categoryRepository.count() == 0) {
            // Clear persistence context to avoid conflicts
            entityManager.clear();

            // 1. Create parent category (Furniture)
            Category furniture = new Category();
            furniture.setName("Furniture");
            furniture.setSlug("furniture");
            furniture.setDescription("Furniture for home and office");
            furniture = categoryRepository.save(furniture); // Let DB generate ID

            // 2. Create child category (Beds)
            Category beds = new Category();
            beds.setName("Beds");
            beds.setSlug("beds");
            beds.setDescription("Comfortable sleeping solutions");
            beds.setParentCategory(furniture);
            categoryRepository.save(beds);

            entityManager.flush();
            System.out.println("Initialized 2 category records");
        }
    }

    private void initProducts() {
        if (productRepository.count() == 0 && categoryRepository.count() >= 2) {
            entityManager.clear();

            Category bedsCategory = categoryRepository.findBySlug("beds")
                    .orElseThrow(() -> new IllegalStateException("Beds category not found"));

            // 1. Queen Bed
            Product queenBed = new Product();
            queenBed.setName("Queen Size Bed");
            queenBed.setDescription("Bed with size 78x60");
            queenBed.setPrice(new BigDecimal("23399.00"));
            queenBed.setStock(41);
            queenBed.setSku("BED-QUEEN-001");
            queenBed.setSlug("queen-size-bed");
            queenBed.setCategory(bedsCategory);
            productRepository.save(queenBed);

            // 2. King Bed
            Product kingBed = new Product();
            kingBed.setName("King Size Bed");
            kingBed.setDescription("Bed with size 78x72");
            kingBed.setPrice(new BigDecimal("33399.00"));
            kingBed.setStock(4);
            kingBed.setSku("BED-KING-001");
            kingBed.setSlug("king-size-bed");
            kingBed.setCategory(bedsCategory);
            productRepository.save(kingBed);

            entityManager.flush();
            System.out.println("Initialized 2 product records");
        }
    }
}
