package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.service.ProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductCatalogController {

    @Autowired
    private ProductCatalogService productCatalogService;

    // Add Product Endpoint
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = productCatalogService.addProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Update Product Endpoint
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = productCatalogService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(product);
    }

    // Get Product by ID Endpoint
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productCatalogService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    // Get All Products Endpoint
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productCatalogService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Delete Product Endpoint
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productCatalogService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
