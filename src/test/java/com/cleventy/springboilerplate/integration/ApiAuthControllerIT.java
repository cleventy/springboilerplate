package com.cleventy.springboilerplate.integration;

//import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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
import org.springframework.test.web.servlet.ResultActions;

import com.cleventy.springboilerplate.business.entities.user.UserConstants;
import com.cleventy.springboilerplate.util.ApiAdminUsersTestUtil;
import com.cleventy.springboilerplate.util.ApiAuthTestUtil;
import com.cleventy.springboilerplate.util.TestUtil;
import com.cleventy.springboilerplate.util.jwt.JwtTokenUtil;
import com.cleventy.springboilerplate.web.spring.UserDetailsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApiAuthControllerIT {
	
	@Autowired
	private ApiAuthTestUtil apiAuthTestUtil;
	
	@Autowired
	private ApiAdminUsersTestUtil apiAdminTestUtil;
	
	@Test
	public void authLoginOk() throws Exception {
		expectLoginOk(
				this.apiAuthTestUtil.authLoginRequest(TestUtil.ADMIN_USERNAME, TestUtil.ADMIN_PASSWORD), 
				UserConstants.ROLE_ADMIN
		);
	}

	@Test
	public void authLoginWrongUsername() throws Exception {
		this.apiAuthTestUtil.authLoginRequest(TestUtil.ADMIN_WRONGUSERNAME, TestUtil.ADMIN_PASSWORD)
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void authLoginWrongPassword() throws Exception {
		this.apiAuthTestUtil.authLoginRequest(TestUtil.ADMIN_USERNAME, TestUtil.ADMIN_WRONGPASSWORD)
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void authRefreshToken() throws Exception {
		String refreshToken = this.apiAuthTestUtil.obtainTokenCO(TestUtil.ADMIN_USERNAME, TestUtil.ADMIN_PASSWORD).getRefresh_token();
		expectLoginOk(
				this.apiAuthTestUtil.authRefreshTokenRequest(refreshToken), 
				UserConstants.ROLE_ADMIN
		);
	}

	@Test
	public void userForbiddenResource() throws Exception {
		String accessToken = this.apiAuthTestUtil.obtainTokenCO(TestUtil.VALID_USER_USERNAME, TestUtil.VALID_USER_PASSWORD).getAccess_token();
	    this.apiAdminTestUtil.adminUsersRequest(accessToken)
				.andExpect(status().isForbidden());
	}

	private static ResultActions expectLoginOk(ResultActions resultActions, String role) throws Exception {
		return resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.access_token", is(notNullValue())))
				.andExpect(jsonPath("$.expires_in", is(Long.valueOf(JwtTokenUtil.JWT_ACCESS_TOKEN_VALIDITY_IN_SECONDS).intValue())))
				.andExpect(jsonPath("$.refresh_token", is(notNullValue())))
				.andExpect(jsonPath("$.scope", equalTo(UserDetailsService.getScopeFromRole(role))))
				.andExpect(jsonPath("$.token_type", is(JwtTokenUtil.TOKEN_TYPE_BEARER)));
	}


}
