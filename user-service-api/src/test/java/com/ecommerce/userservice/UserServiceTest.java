package com.ecommerce.userservice;

import com.ecommerce.userservice.controller.UserController;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setUp() {
		userRepository.deleteAll();
	}

	@Test
	public void testRegisterUser() {
		User user = new User();
		user.setName("John Doe");
		user.setEmail("johndoe@example.com");
		user.setPassword("password123");

		User registeredUser = userService.registerUser(user);

		assertNotNull(registeredUser);
		assertNotNull(registeredUser.getId());
		assertTrue(passwordEncoder.matches("password123", registeredUser.getPassword()));
	}

	@Test(expected = RuntimeException.class)
	public void testRegisterUserWithExistingEmail() {
		User user1 = new User();
		user1.setName("John Doe");
		user1.setEmail("johndoe@example.com");
		user1.setPassword("password123");
		userService.registerUser(user1);

		User user2 = new User();
		user2.setName("Jane Doe");
		user2.setEmail("johndoe@example.com");
		user2.setPassword("password456");
		userService.registerUser(user2);
	}

	@Test
	public void testAuthenticateUser() {
		User user = new User();
		user.setName("John Doe");
		user.setEmail("johndoe@example.com");
		user.setPassword(passwordEncoder.encode("password123"));
		userRepository.save(user);

		User authenticatedUser = userService.authenticateUser("johndoe@example.com", "password123");
		assertNotNull(authenticatedUser);
		assertEquals("johndoe@example.com", authenticatedUser.getEmail());
	}

	@Test(expected = RuntimeException.class)
	public void testAuthenticateUserWithInvalidCredentials() {
		User user = new User();
		user.setName("John Doe");
		user.setEmail("johndoe@example.com");
		user.setPassword(passwordEncoder.encode("password123"));
		userRepository.save(user);

		userService.authenticateUser("johndoe@example.com", "wrongpassword");
	}

	@Test
	public void testGetUserProfile() {
		User user = new User();
		user.setName("John Doe");
		user.setEmail("johndoe@example.com");
		user.setPassword("password123");
		User savedUser = userRepository.save(user);

		User retrievedUser = userService.getUserProfile(savedUser.getId());
		assertNotNull(retrievedUser);
		assertEquals("John Doe", retrievedUser.getName());
	}

	@Test
	public void testUpdateUserProfile() {
		User user = new User();
		user.setName("John Doe");
		user.setEmail("johndoe@example.com");
		user.setPassword("password123");
		User savedUser = userRepository.save(user);

		User updatedUser = new User();
		updatedUser.setName("John Updated");
		updatedUser.setPhone("1234567890");
		updatedUser.setAddress("123 Main St");

		User result = userService.updateUserProfile(savedUser.getId(), updatedUser);
		assertNotNull(result);
		assertEquals("John Updated", result.getName());
		assertEquals("1234567890", result.getPhone());
		assertEquals("123 Main St", result.getAddress());
	}

	@Test
	public void testDeleteUser() {
		User user = new User();
		user.setName("John Doe");
		user.setEmail("johndoe@example.com");
		user.setPassword("password123");
		User savedUser = userRepository.save(user);

		userService.deleteUser(savedUser.getId());

		assertFalse(userRepository.findById(savedUser.getId()).isPresent());
	}
}