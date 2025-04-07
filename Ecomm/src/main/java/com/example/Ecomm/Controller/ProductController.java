package com.example.Ecomm.Controller;


import com.example.Ecomm.Exception.ProductNotFoundException;
import com.example.Ecomm.Models.Product;
import com.example.Ecomm.DTO.ProductRequestPayload;
import com.example.Ecomm.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody ProductRequestPayload requestPayload){
        Product savedProduct=productService.addProduct(requestPayload);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product getProduct= productService.getProductById(id)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found with id: "+id));;
        return ResponseEntity.ok(getProduct);
    }
    @GetMapping("/getProductByName/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable("name") String name) throws ProductNotFoundException {
        Product getProduct= productService.getProductByName(name)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found with Name: "+name));
        return new ResponseEntity<>(getProduct, HttpStatus.OK);
    }
    @PutMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        Product updatedProduct= productService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/deleteProductById/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") Long id){
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
}