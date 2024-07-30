package com.example.application;


import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class Prac1ApplicationTests {

	@Autowired
	private MockMvc mockMvc;
@Order(1)
@Test
public void testLoginAndAuthenticatedEndpoint() throws Exception {
	// Login request
	String loginPayload = "{\"username\":\"admin\",\"password\":\"abc\"}";

	MvcResult result = mockMvc.perform(post("/api/v1/accounts/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(loginPayload))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token").isNotEmpty())
			.andExpect(jsonPath("$.expiresIn").isNumber())
			.andDo(print())
			.andReturn();

	String responseBody = result.getResponse().getContentAsString();
	String authToken = JsonPath.read(responseBody, "$.token");
}
	@Order(3)
	@Test
	public void testGetAccountsWithAdminAuthority() throws Exception {

		String loginPayload = "{\"username\":\"admin\",\"password\":\"abc\"}";

		MvcResult result = mockMvc.perform(post("/api/v1/accounts/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loginPayload))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isNotEmpty())
				.andExpect(jsonPath("$.expiresIn").isNumber())
				.andDo(print())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		String authToken = JsonPath.read(responseBody, "$.token");

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.header("Authorization", "Bearer " + authToken)
				)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
	}

	@Order(2)
	@Test
	public void testAddAccountPostSuccess() throws Exception {
		// Login request
		String loginPayload = "{\"username\":\"admin\",\"password\":\"abc\"}";

		MvcResult result = mockMvc.perform(post("/api/v1/accounts/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loginPayload))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.expiresIn").isNumber())
				.andDo(print())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		String authToken = JsonPath.read(responseBody, "$.token");

		// Perform POST request to add new user with authenticated token
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("{\"username\":\"amna\",\"name\":\"Amna\",\"useremail\":\"amna@gmail.com\",\"address\":\"shadbagh\",\"password\":\"abc\",\"roles\":\"user\"}")
						.header("Authorization", "Bearer " + authToken))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.useremail", Matchers.is("amna@gmail.com")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is("shadbagh")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("amna")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password").isNotEmpty()) // Avoid comparing passwords directly
				.andExpect(MockMvcResultMatchers.jsonPath("$.roles", Matchers.is("user")));
	}

	@Order(5)
	@Test
	public void testUpdateAccounts() throws Exception {
		String loginPayload = "{\"username\":\"admin\",\"password\":\"abc\"}";

		MvcResult result = mockMvc.perform(post("/api/v1/accounts/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loginPayload))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isNotEmpty())
				.andExpect(jsonPath("$.expiresIn").isNumber())
				.andDo(print())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		String authToken = JsonPath.read(responseBody, "$.token");

		// Correct JSON payload for updating account
		String accountPayload = "{\"accountId\":\"123\",\"address\":\"math\",\"useremail\":\"admin@gmail.com\"}";

		mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.content(accountPayload)
						.header("Authorization", "Bearer " + authToken))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address", Matchers.is("math")))
				.andExpect(jsonPath("$.useremail", Matchers.is("admin@gmail.com")));
	}
	@Order(6)
	@Test
	public void testAddTransactionsPostSuccess() throws Exception {
		// Login request
		String loginPayload = "{\"username\":\"user\",\"password\":\"abc\"}";

		MvcResult result = mockMvc.perform(post("/api/v1/accounts/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loginPayload))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.expiresIn").isNumber())
				.andDo(print())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		String authToken = JsonPath.read(responseBody, "$.token");

		// Perform POST request to add new user with authenticated token
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transactions")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("{\"accountId\":\"7\",\"amount\":\"1\",\"toFromAccountId\":\"12\"}")
						.header("Authorization", "Bearer " + authToken))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.accountId", Matchers.is(7)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.is(1.0)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.toFromAccountId", Matchers.is(12)));

	}

	
	
	@Order(8)
	@Test
	public void testDeleteAccountsbyAccountnumber() throws Exception {
		String loginPayload = "{\"username\":\"admin\",\"password\":\"abc\"}";

		MvcResult result = mockMvc.perform(post("/api/v1/accounts/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loginPayload))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isNotEmpty())
				.andExpect(jsonPath("$.expiresIn").isNumber())
				.andDo(print())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		String authToken = JsonPath.read(responseBody, "$.token");

		// Assuming account with id 7 exists
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/accounts/{id}", 14)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + authToken))
				.andDo(print())
				.andExpect(status().isOk());
	}
	@Order(7)
	@Test
	public void testGeTransactionsWithUserAuthority() throws Exception {

		String loginPayload = "{\"username\":\"user\",\"password\":\"abc\"}";

		MvcResult result = mockMvc.perform(post("/api/v1/accounts/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loginPayload))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").isNotEmpty())
				.andExpect(jsonPath("$.expiresIn").isNumber())
				.andDo(print())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		String authToken = JsonPath.read(responseBody, "$.token");

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions/{id}",7)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.header("Authorization", "Bearer " + authToken)
				)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
	}
}
