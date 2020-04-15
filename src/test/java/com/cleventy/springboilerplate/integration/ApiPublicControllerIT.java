package com.cleventy.springboilerplate.integration;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.AbstractMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cleventy.springboilerplate.util.json.JsonUtil;
import com.cleventy.springboilerplate.web.controller.api.ApiURL;
import com.cleventy.springboilerplate.web.controller.api.ApiUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApiPublicControllerIT {
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void getTest() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get(ApiURL.BASE+ApiURL.TEST)
				.accept(ApiUtils.ACCEPT_HEADER))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo(JsonUtil.toJson(new AbstractMap.SimpleEntry<String, String>("test", "test")))));
	}
	
}
