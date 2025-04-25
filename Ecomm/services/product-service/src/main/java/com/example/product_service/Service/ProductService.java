package com.example.product_service.Service;


import com.example.common.Mapper.ProductMapper;
import com.example.product_service.DTO.ProductRequest;
import com.example.common.DTO.ProductResponse;
import com.example.product_service.DTO.UpdateProductRequest;
import com.example.product_service.Exception.CategoryNotFoundException;
import com.example.product_service.Exception.ProductNotFoundException;
import com.example.product_service.Exception.ResourceConflictException;
import com.example.product_service.Models.Category;
import com.example.product_service.Models.Product;
import com.example.product_service.Repository.CategoryRepository;
import com.example.product_service.Repository.ProductRepository;
import com.example.product_service.Utility.SlugGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.example.product_service.Utility.ProductMapperUtils.getProductEntity;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest) throws CategoryNotFoundException, ResourceConflictException {
        logger.info("Attempting to create product with name: {}", productRequest.getName());

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found with id: " + productRequest.getCategoryId()));

        String baseSlug = SlugGenerator.generateSlug(productRequest.getName());
        String finalSlug = ensureUniqueProductSlug(baseSlug, null);
        Product productEntity = getProductEntity(productRequest, category,finalSlug);
        Product savedProduct = productRepository.save(productEntity);
        logger.info("Successfully created product with ID: {} and slug: {}", savedProduct.getId(), savedProduct.getSlug());
        return productMapper.toResponse(savedProduct);
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return productMapper.toResponsePage(products);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found with id: " + id));
        ;
        return productMapper.toResponse(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductByName(String keyword, Pageable pageable) {
        logger.debug("Searching products by name containing '{}' with pagination: {}", keyword, pageable);
        Page<Product> products = productRepository.searchByKeyword(keyword, pageable);
        return productMapper.toResponsePage(products);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductBySlug(String productSlug) throws ProductNotFoundException {
        logger.debug("Searching products by slug containing '{}'", productSlug);
        Product product = productRepository.findBySlug(productSlug)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found with slug: " + productSlug));
        return productMapper.toResponse(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductBySku(String sku) throws ProductNotFoundException {
        logger.debug("Searching products by sku containing '{}'", sku);
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found with slug: " + sku));
        return productMapper.toResponse(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByCategoryId(Long categoryId, Pageable pageable) throws CategoryNotFoundException {
        logger.debug("Fetching products for category ID: {} with pagination: {}", categoryId, pageable);
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category Not Found with id: " + categoryId);
        }
        return productMapper.toResponsePage(productRepository.getProductsByCategoryId(categoryId, pageable));
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByCategorySlug(String categorySlug, Pageable pageable) throws CategoryNotFoundException {
        logger.debug("Fetching products for category slug: {} with pagination: {}", categorySlug, pageable);
        if (categoryRepository.findBySlug(categorySlug).isEmpty()) {
            throw new CategoryNotFoundException("Category Not Found with categoryIdOrSlug : " + categorySlug);
        }
        return productRepository.getProductsByCategorySlug(categorySlug, pageable);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest updateProductRequest) throws CategoryNotFoundException, ProductNotFoundException, ResourceConflictException {
        logger.info("Attempting to update product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found with id: " + id));
        boolean slugNeedsUpdate = false;
        String newSlug = product.getSlug();

        // Update name and potentially slug
        if (updateProductRequest.getName() != null && !updateProductRequest.getName().isBlank() && !updateProductRequest.getName().equals(product.getName())) {
            logger.debug("Product name change detected for ID: {}. Old: '{}', New: '{}'", id, product.getName(), updateProductRequest.getName());
            product.setName(updateProductRequest.getName());
            newSlug = SlugGenerator.generateSlug(updateProductRequest.getName());
            slugNeedsUpdate = true;
        }

        // Update other fields if provided
        if (updateProductRequest.getDescription() != null) {
            product.setDescription(updateProductRequest.getDescription());
        }
        if (updateProductRequest.getPrice() != null) {
            product.setPrice(updateProductRequest.getPrice());
        }
        if (updateProductRequest.getStockQuantity() != null) {
            product.setStock(updateProductRequest.getStockQuantity());
        }
        if (updateProductRequest.getImageUrl() != null) {
            product.setImageUrl(updateProductRequest.getImageUrl());
        }

        // Update category if provided
        if (updateProductRequest.getCategoryId() != null && !updateProductRequest.getCategoryId().equals(product.getCategory().getId())) {
            logger.debug("Product category change detected for ID: {}. Old: '{}', New: '{}'", id, product.getCategory().getId(), updateProductRequest.getCategoryId());
            Category category = categoryRepository.findById(updateProductRequest.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category Not Found with id: " + updateProductRequest.getCategoryId()));
            product.setCategory(category);
        }

        // Ensure slug uniqueness if it was changed
        if (slugNeedsUpdate) {
            product.setSlug(ensureUniqueProductSlug(newSlug, id));
        }

        Product updatedProduct = productRepository.save(product);
        logger.info("Successfully updated product with ID: {}", updatedProduct.getId());
        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public void deleteProductById(Long id) throws ProductNotFoundException {
        logger.warn("Attempting to delete product with ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found with id: " + id));

        // Simple deletion - consider implications like orders containing this product
        productRepository.delete(product);
        logger.warn("Successfully deleted product with ID: {}", id);
    }

    private String ensureUniqueProductSlug(String baseSlug, Long currentId) throws ResourceConflictException {
        String finalSlug = baseSlug;
        int counter = 1;
        while (true) {
            boolean exists;
            if (currentId == null) {
                exists = productRepository.findBySlug(finalSlug).isPresent();
            } else {
                exists = productRepository.existsBySlugAndIdNot(finalSlug, currentId);
            }

            if (!exists) {
                break;
            }
            logger.warn("Product slug collision detected for '{}'. Appending suffix.", finalSlug);
            finalSlug = SlugGenerator.appendSuffix(baseSlug, counter++);
            if (counter > 100) { // Safety break
                logger.error("Could not find unique slug for product base '{}' after {} attempts.", baseSlug, counter);
                throw new ResourceConflictException("Could not generate unique slug for product name.");
            }
        }
        return finalSlug;
    }

}
