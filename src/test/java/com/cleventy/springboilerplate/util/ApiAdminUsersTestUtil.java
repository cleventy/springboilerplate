package com.cleventy.springboilerplate.util;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

//import static org.hamcrest.Matchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cleventy.springboilerplate.business.services.userservice.cos.UserBasicDetailsCO;
import com.cleventy.springboilerplate.util.json.JsonUtil;
import com.cleventy.springboilerplate.web.controller.api.ApiURL;
import com.cleventy.springboilerplate.web.controller.api.ApiUtils;
import com.cleventy.springboilerplate.web.spring.AuthRequestFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class ApiAdminUsersTestUtil {

	@Autowired
	private MockMvc mvc;
	
	public ResultActions adminUsersRequest(String accessToken) throws JsonProcessingException, Exception {
		return this.mvc.perform(MockMvcRequestBuilders.get(ApiURL.BASE+ApiURL.ADMIN+ApiURL.USERS)
				.accept(ApiUtils.ACCEPT_HEADER)
				.header(AuthRequestFilter.AUTH_HEADER_NAME, AuthRequestFilter.AUTH_HEADER_BEARER_PREFIX_VALUE + " " + accessToken));
	}
	
	public ResultActions adminUsersRequestOk(String accessToken) throws JsonProcessingException, Exception {
		return this.adminUsersRequest(accessToken)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", not(empty())));
	}
	
	public Set<UserBasicDetailsCO> adminUsersRequestOkAndReturn(String accessToken) throws JsonProcessingException, Exception {
		return JsonUtil.toObject(
				this.adminUsersRequestOk(accessToken).andReturn().getResponse().getContentAsString(), 
				new TypeReference<Set<UserBasicDetailsCO>>(){}
		);
	}
	
	public ResultActions adminUsersSendWelcomeEmailRequest(String accessToken, int userId) throws JsonProcessingException, Exception {
		return this.mvc.perform(MockMvcRequestBuilders.post(ApiURL.BASE+ApiURL.ADMIN+ApiURL.USERS+ApiURL.SLASH+userId+ApiURL.SLASH+ApiURL.SEND_WELCOME_EMAIL)
				.accept(ApiUtils.ACCEPT_HEADER)
				.header(AuthRequestFilter.AUTH_HEADER_NAME, AuthRequestFilter.AUTH_HEADER_BEARER_PREFIX_VALUE + " " + accessToken));
	}
	
}
