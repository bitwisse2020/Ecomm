package com.example.product_service.Controller;


import com.example.product_service.DTO.ProductRequest;
import com.example.common.DTO.ProductResponse;
import com.example.product_service.DTO.UpdateProductRequest;
import com.example.product_service.Exception.CategoryNotFoundException;
import com.example.product_service.Exception.ProductNotFoundException;
import com.example.product_service.Exception.ResourceConflictException;
import com.example.product_service.Models.Product;
import com.example.product_service.Service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductRequest productRequest) throws CategoryNotFoundException {
        ProductResponse savedProduct = productService.addProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product getProduct = productService.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found with id: " + id));
        ;
        return ResponseEntity.ok(getProduct);
    }

    @GetMapping("/getProducts")
    public ResponseEntity<?> getProductByName(@RequestParam(required = false) String search,
                                              @PageableDefault(size = 20,sort = "name")Pageable pageable) throws ProductNotFoundException {
        Page<ProductResponse> products;
        if (search != null && !search.isBlank()) {
            products = productService.getProductByName(search, pageable);
        } else {
            products = productService.getAllProducts(pageable);
        }
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest productRequest) throws CategoryNotFoundException, ProductNotFoundException, ResourceConflictException {
        ProductResponse updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
}