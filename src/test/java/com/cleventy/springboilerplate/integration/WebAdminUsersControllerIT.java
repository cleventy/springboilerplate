package com.cleventy.springboilerplate.integration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cleventy.springboilerplate.business.services.userservice.UserService;
import com.cleventy.springboilerplate.util.TestUtil;
import com.cleventy.springboilerplate.web.controller.web.WebURL;
import com.cleventy.springboilerplate.web.spring.UserDetailsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WebAdminUsersControllerIT {

	private URL base;

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost");
	}

	@Test
	public void getAdminUsersNoAuth() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get(WebURL.ADMIN+WebURL.USERS))
//	    .andDo(print());
		.andExpect(redirectedUrl(this.base+WebURL.AUTH+WebURL.LOGIN));
	}
	
	@Test
	@WithMockUser(username = TestUtil.ADMIN_USERNAME, authorities = { UserDetailsService.AUTHORITY_ADMIN })
	public void getAdminUsers() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get(WebURL.ADMIN+WebURL.USERS))
//	    .andDo(print())
		.andExpect(view().name("pages/admin/users"))
	    .andExpect(status().isOk())
	    .andExpect(model().attribute("users", this.userService.getUsers()));
	}
}
