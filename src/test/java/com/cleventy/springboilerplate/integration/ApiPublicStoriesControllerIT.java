package com.cleventy.springboilerplate.integration;

//import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cleventy.springboilerplate.web.controller.api.ApiURL;
import com.cleventy.springboilerplate.web.controller.api.ApiUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApiPublicStoriesControllerIT {
	
	private static final String STORY_1_TITLE = "Titulo historia 1";

	@Autowired
	private MockMvc mvc;

	@Test
	public void stories() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get(ApiURL.BASE+ApiURL.STORIES)
				.accept(ApiUtils.ACCEPT_HEADER))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.stories", hasSize(20)));
	}
	
	@Test
	public void storyDetails() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get(ApiURL.BASE+ApiURL.STORIES+ApiURL.SLASH+"1")
				.accept(ApiUtils.ACCEPT_HEADER))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", equalTo(STORY_1_TITLE)))
				.andExpect(jsonPath("$.difficulty", is(notNullValue())))
				.andExpect(jsonPath("$.difficulty", is(closeTo(2.0, 0))))
				.andExpect(jsonPath("$.categories", not(emptyArray())))
				.andExpect(jsonPath("$.routes", not(emptyArray())))
				.andExpect(jsonPath("$.book", is(notNullValue())));
	}
	
}
