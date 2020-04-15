package com.cleventy.springboilerplate.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cleventy.springboilerplate.commons.properties.CommonProperties;
import com.cleventy.springboilerplate.web.controller.web.WebURL;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WebPublicControllerIT {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private CommonProperties commonProperties;
	
	@Test
	public void getVersion() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get(WebURL.VERSION))
//	    .andDo(print())
		.andExpect(view().name("pages/version"))
	    .andExpect(status().isOk())
	    .andExpect(model().attribute("version", this.commonProperties.getVersion()));
	}
}
