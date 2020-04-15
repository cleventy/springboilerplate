package com.cleventy.springboilerplate.util;

//import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cleventy.springboilerplate.util.json.JsonUtil;
import com.cleventy.springboilerplate.util.jwt.AuthLoginPasswordRequest;
import com.cleventy.springboilerplate.util.jwt.AuthRefreshTokenRequest;
import com.cleventy.springboilerplate.util.jwt.TokenCO;
import com.cleventy.springboilerplate.web.controller.api.ApiURL;
import com.cleventy.springboilerplate.web.controller.api.ApiUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class ApiAuthTestUtil {
	
	@Autowired
	private MockMvc mvc;

	public ResultActions authLoginRequest(String username, String password) throws JsonProcessingException, Exception {
		return this.mvc.perform(MockMvcRequestBuilders.post(ApiURL.BASE+ApiURL.LOGIN)
				.accept(ApiUtils.ACCEPT_HEADER)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(new AuthLoginPasswordRequest(username, password))));
	}
	
	public ResultActions authRefreshTokenRequest(String refreshToken) throws JsonProcessingException, Exception {
		return this.mvc.perform(MockMvcRequestBuilders.post(ApiURL.BASE+ApiURL.REFRESH_TOKEN)
				.accept(ApiUtils.ACCEPT_HEADER)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(new AuthRefreshTokenRequest(refreshToken))));
	}

	public TokenCO obtainTokenCO(String username, String password) throws JsonProcessingException, Exception {
		String responseJson = authLoginRequest(username, password)
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		return JsonUtil.toObject(responseJson, TokenCO.class);
	}

}
