package com.example.product_service.Service;


import com.example.product_service.DTO.ProductRequest;
import com.example.product_service.DTO.ProductResponse;
import com.example.product_service.DTO.UpdateProductRequest;
import com.example.product_service.Exception.CategoryNotFoundException;
import com.example.product_service.Models.Category;
import com.example.product_service.Models.Product;
import com.example.product_service.Repository.CategoryRepository;
import com.example.product_service.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.product_service.Utility.ProductMapperUtils.getProductEntity;
import static com.example.product_service.Utility.ProductMapperUtils.getProductResponse;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse addProduct(ProductRequest productRequest) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found with id: "+productRequest.getCategoryId()));

        Product productEntity= getProductEntity(productRequest, category);
        Product product=productRepository.save(productEntity);
        return getProductResponse(product, productEntity);
    }


    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Product updateProduct(Long id, UpdateProductRequest updateProductRequest) {
        return productRepository.save();
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
