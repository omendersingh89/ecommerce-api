package com.ecommerce.productservice.service;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCatalogService {

    @Autowired
    private ProductRepository productRepository;

    // Add Product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Update Product
    public Product updateProduct(Long productId, Product updatedProduct) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setAvailability(updatedProduct.getAvailability());
        return productRepository.save(product);
    }

    // Get Product by ID
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Get All Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Delete Product
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
