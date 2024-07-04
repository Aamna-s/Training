package com.prac1;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.regex.Matcher;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.string;

@SpringBootTest
@AutoConfigureMockMvc
class Prac1ApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Test
	void Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hello")).
				andDo(MockMvcResultHandlers.print()).
				andExpect(MockMvcResultMatchers.status().isOk()).
				andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE)).
				andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("hello"))).
				andExpect(MockMvcResultMatchers.jsonPath("$.message2",Matchers.is("000000")));

		mockMvc.perform(MockMvcRequestBuilders.get("/hello")).
				andDo(MockMvcResultHandlers.print()).
				andExpect(MockMvcResultMatchers.status().isOk()).

				andExpect(MockMvcResultMatchers.content().string(containsString("hello")));



	}

}
