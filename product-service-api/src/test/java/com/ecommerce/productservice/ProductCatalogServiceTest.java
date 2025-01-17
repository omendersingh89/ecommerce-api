package com.ecommerce.productservice;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductCatalogService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductCatalogServiceTest {

	@Autowired
	private ProductCatalogService productCatalogService;

	@Autowired
	private ProductRepository productRepository;

	@Before
	public void setUp() {
		productRepository.deleteAll();
	}

	@Test
	public void testAddProduct() {
		Product product = new Product();
		product.setName("Laptop");
		product.setDescription("High-end gaming laptop");
		product.setPrice(1500.00);
		product.setAvailability(10);

		Product createdProduct = productCatalogService.addProduct(product);

		assertNotNull(createdProduct);
		assertNotNull(createdProduct.getId());
		assertEquals("Laptop", createdProduct.getName());
	}

	@Test
	public void testUpdateProduct() {
		Product product = new Product();
		product.setName("Laptop");
		product.setDescription("High-end gaming laptop");
		product.setPrice(1500.00);
		product.setAvailability(10);
		Product savedProduct = productRepository.save(product);

		Product updatedProduct = new Product();
		updatedProduct.setName("Gaming Laptop");
		updatedProduct.setDescription("Updated high-end gaming laptop");
		updatedProduct.setPrice(1800.00);
		updatedProduct.setAvailability(5);

		Product result = productCatalogService.updateProduct(savedProduct.getId(), updatedProduct);

		assertNotNull(result);
		assertEquals("Gaming Laptop", result.getName());
		assertEquals(1800.00, result.getPrice(), 0.01);
		assertEquals(5, result.getAvailability());
	}

	@Test
	public void testGetProduct() {
		Product product = new Product();
		product.setName("Laptop");
		product.setDescription("High-end gaming laptop");
		product.setPrice(1500.00);
		product.setAvailability(10);
		Product savedProduct = productRepository.save(product);

		Product retrievedProduct = productCatalogService.getProduct(savedProduct.getId());

		assertNotNull(retrievedProduct);
		assertEquals("Laptop", retrievedProduct.getName());
	}

	@Test
	public void testGetAllProducts() {
		Product product1 = new Product();
		product1.setName("Laptop");
		product1.setDescription("High-end gaming laptop");
		product1.setPrice(1500.00);
		product1.setAvailability(10);
		productRepository.save(product1);

		Product product2 = new Product();
		product2.setName("Tablet");
		product2.setDescription("Portable tablet");
		product2.setPrice(500.00);
		product2.setAvailability(20);
		productRepository.save(product2);

		List<Product> products = productCatalogService.getAllProducts();

		assertNotNull(products);
		assertEquals(2, products.size());
	}

	@Test
	public void testDeleteProduct() {
		Product product = new Product();
		product.setName("Laptop");
		product.setDescription("High-end gaming laptop");
		product.setPrice(1500.00);
		product.setAvailability(10);
		Product savedProduct = productRepository.save(product);

		productCatalogService.deleteProduct(savedProduct.getId());

		Optional<Product> deletedProduct = productRepository.findById(savedProduct.getId());
		assertFalse(deletedProduct.isPresent());
	}
}

