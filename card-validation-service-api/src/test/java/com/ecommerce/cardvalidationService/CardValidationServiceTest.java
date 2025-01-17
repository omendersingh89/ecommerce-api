package com.ecommerce.cardvalidationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CardValidationServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testValidateCardNumber_Valid() throws Exception {
		String cardNumber = "1234567812345678";

		mockMvc.perform(post("/api/card-validation/validate-card-number")
						.content(cardNumber)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
	}

	@Test
	public void testValidateCardNumber_Invalid() throws Exception {
		String cardNumber = "12345";

		mockMvc.perform(post("/api/card-validation/validate-card-number")
						.content(cardNumber)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

	@Test
	public void testValidateExpirationDate_Valid() throws Exception {
		String expirationDate = "12/25";

		mockMvc.perform(post("/api/card-validation/validate-expiration-date")
						.content(expirationDate)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
	}

	@Test
	public void testValidateExpirationDate_InvalidMonth() throws Exception {
		String expirationDate = "13/25";

		mockMvc.perform(post("/api/card-validation/validate-expiration-date")
						.content(expirationDate)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

	@Test
	public void testValidateExpirationDate_Expired() throws Exception {
		String expirationDate = "01/20";

		mockMvc.perform(post("/api/card-validation/validate-expiration-date")
						.content(expirationDate)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

	@Test
	public void testValidateCVV_Valid() throws Exception {
		String cvv = "123";

		mockMvc.perform(post("/api/card-validation/validate-cvv")
						.content(cvv)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
	}

	@Test
	public void testValidateCVV_Invalid() throws Exception {
		String cvv = "12a";

		mockMvc.perform(post("/api/card-validation/validate-cvv")
						.content(cvv)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}
}

