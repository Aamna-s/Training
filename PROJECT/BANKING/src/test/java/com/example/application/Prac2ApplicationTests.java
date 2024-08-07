package com.example.application;

import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
class Prac1ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@ParameterizedTest
	@ValueSource(strings = { "{\"username\":\"admin\",\"password\":\"Abc123\"}" })
	@Order(1)
	void testLoginAndAuthenticatedEndpoint(String loginPayload) throws Exception {
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

	@ParameterizedTest
	@ValueSource(strings = { "{\"username\":\"admin\",\"password\":\"Abc123\"}" })
	@Order(2)
	void testAddAccountPostSuccess(String loginPayload) throws Exception {
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

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("{\"username\":\"amna\",\"name\":\"Amna\",\"useremail\":\"amna@gmail.com\",\"address\":\"shadbagh\",\"password\":\"Abc123\",\"roles\":\"user\"}")
						.header("Authorization", "Bearer " + authToken))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.useremail", Matchers.is("amna@gmail.com")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is("shadbagh")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("amna")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.roles", Matchers.is("user")));
	}



	@ParameterizedTest
	@ValueSource(strings = { "{\"username\":\"admin\",\"password\":\"Abc123\"}" })
	@Order(5)
	void testUpdateAccounts(String loginPayload) throws Exception {
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

		String accountPayload = "{\"accountId\":\"1234567889\",\"address\":\"math\",\"useremail\":\"admin@gmail.com\"}";

		mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.content(accountPayload)
						.header("Authorization", "Bearer " + authToken))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address", Matchers.is("math")))
				.andExpect(jsonPath("$.useremail", Matchers.is("admin@gmail.com")));
	}

	@ParameterizedTest
	@ValueSource(strings = { "{\"username\":\"test4\",\"password\":\"Abc123\"}" })
	@Order(6)
	void testAddTransactionsPostSuccess(String loginPayload) throws Exception {
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

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transactions")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("{\"accountId\":1234567888,\"amount\":1.0,\"toFromAccountId\":1234567889}")
						.header("Authorization", "Bearer " + authToken))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.is(1.0)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.toFromAccountId", Matchers.is(1234567889)));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions/{id}", 1234567889)
						.header("Authorization", "Bearer " + authToken))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
	}

	@ParameterizedTest
	@ValueSource(strings = { "{\"username\":\"test4\",\"password\":\"Abc123\"}" })
	@Order(7)
	void testGetTransactionsWithUserAuthority(String loginPayload) throws Exception {
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

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions/{id}", 1234567889)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.header("Authorization", "Bearer " + authToken))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
	}

	@ParameterizedTest
	@ValueSource(strings = { "{\"username\":\"admin\",\"password\":\"Abc123\"}" })
	@Order(8)
	void testDeleteAccountsByAccountnumber(String loginPayload) throws Exception {
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

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/accounts/{id}", 1234567889)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + authToken))
				.andDo(print())
				.andExpect(status().isOk());
	}
	@ParameterizedTest
	@ValueSource(strings = { "{\"username\":\"admin\",\"password\":\"Abc123\"}" })
	@Order(3)
	void testGetAccountsWithAdminAuthority(String loginPayload) throws Exception {
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
						.param("page", "0")
						.param("size", "2")
						.contentType(MediaType.APPLICATION_JSON))
				;
	}
}
