package com.example.application;

import com.example.application.account.Account;
import com.example.application.account.AccountController;
import com.example.application.account.AccountRepository;
import com.example.application.account.AccountService;
import com.example.application.services.AuthenticationService;
import com.example.application.services.JwtService;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(7)));
	}
	@Test
	public void testGetAccountsbyAccountnumber() throws Exception {

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

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts/{accountNumber}", 123)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + authToken))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.accountId", Matchers.is(123)))
				.andExpect(jsonPath("$.roles",Matchers.is("admin")))
				.andExpect(jsonPath("$.username",Matchers.is("admin")));
	}
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
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/accounts/{id}", 7)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + authToken))
				.andDo(print())
				.andExpect(status().isOk());
	}
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

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions/{id}",1)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.header("Authorization", "Bearer " + authToken)
				)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(16)));
	}
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
						.content("{\"accountId\":\"1\",\"amount\":\"1\",\"toFromAccountId\":\"2\"}")
						.header("Authorization", "Bearer " + authToken))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.accountId", Matchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.is(1.0)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.toFromAccountId", Matchers.is(2)));

	}


}
