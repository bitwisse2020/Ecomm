package com.example.Ecomm.Service;

import com.example.Ecomm.Models.Product;
import com.example.Ecomm.DTO.ProductRequestPayload;
import com.example.Ecomm.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(ProductRequestPayload requestPayload) {
        Product productEntity= Product.builder()
                .name(requestPayload.getName())
                .category(requestPayload.getCategory())
                .price(requestPayload.getPrice()).build();
        return productRepository.save(productEntity);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
