package com.ecommerce.orderservice;

import com.ecommerce.orderservice.controller.OrderController;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.NotificationService;
import com.ecommerce.orderservice.service.OrderService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@MockBean
	private NotificationService notificationService;

	@Before
	public void setUp() {
		orderRepository.deleteAll();
	}

	@Test
	public void testCreateOrder() {
		Order order = new Order();
		order.setUserId(1L);
		order.setProduct("Laptop");
		order.setQuantity(1);
		order.setPrice(1000.00);

		Order createdOrder = orderService.createOrder(order);

		assertNotNull(createdOrder);
		assertNotNull(createdOrder.getId());
		assertEquals("CREATED", createdOrder.getStatus());
	}

	@Test
	public void testUpdateOrder() {
		Order order = new Order();
		order.setUserId(1L);
		order.setProduct("Laptop");
		order.setQuantity(1);
		order.setPrice(1000.00);
		Order savedOrder = orderRepository.save(order);

		Order updatedOrder = new Order();
		updatedOrder.setProduct("Tablet");
		updatedOrder.setQuantity(2);
		updatedOrder.setPrice(500.00);
		updatedOrder.setStatus("PAID");

		Order result = orderService.updateOrder(savedOrder.getId(), updatedOrder);

		assertNotNull(result);
		assertEquals("Tablet", result.getProduct());
		assertEquals(2, result.getQuantity());
		assertEquals(500.00, result.getPrice(), 0.01);
		assertEquals("PAID", result.getStatus());
	}

	@Test
	public void testGetOrder() {
		Order order = new Order();
		order.setUserId(1L);
		order.setProduct("Laptop");
		order.setQuantity(1);
		order.setPrice(1000.00);
		Order savedOrder = orderRepository.save(order);

		Order retrievedOrder = orderService.getOrder(savedOrder.getId());

		assertNotNull(retrievedOrder);
		assertEquals("Laptop", retrievedOrder.getProduct());
	}

	@Test
	public void testNotifyCustomerAfterPayment() {
		Order order = new Order();
		order.setUserId(1L);
		order.setProduct("Laptop");
		order.setQuantity(1);
		order.setPrice(1000.00);
		order.setStatus("PAID");
		Order savedOrder = orderRepository.save(order);

		orderService.notifyCustomerAfterPayment(savedOrder.getId());

		verify(notificationService, times(1))
				.sendNotification(eq(1L), contains("Your payment was successful"));
	}
}