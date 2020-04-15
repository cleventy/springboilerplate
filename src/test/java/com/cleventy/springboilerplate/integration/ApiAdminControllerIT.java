package com.cleventy.springboilerplate.integration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cleventy.springboilerplate.business.services.userservice.cos.UserBasicDetailsCO;
import com.cleventy.springboilerplate.util.ApiAdminUsersTestUtil;
import com.cleventy.springboilerplate.util.ApiAuthTestUtil;
import com.cleventy.springboilerplate.util.TestUtil;
import com.cleventy.springboilerplate.util.jwt.TokenCO;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApiAdminControllerIT {

	@Autowired
	private ApiAuthTestUtil apiAuthTestUtil;
	
	@Autowired
	private ApiAdminUsersTestUtil apiAdminUsersTestUtil;
	
	private TokenCO tokenCO;

	@Before
	public void getTokenCO() throws Exception {
		this.tokenCO = this.apiAuthTestUtil.obtainTokenCO(TestUtil.ADMIN_USERNAME, TestUtil.ADMIN_PASSWORD);
	}

	@Test
	public void getUsers() throws Exception {
	    String accessToken = this.tokenCO.getAccess_token();
	    
	    this.apiAdminUsersTestUtil.adminUsersRequestOk(accessToken);
	}

	@Test
	public void sendWelcomeEmailToActiveUser() throws Exception {
	    String accessToken = this.tokenCO.getAccess_token();

		Set<UserBasicDetailsCO> userBasicDetailsCOs = this.apiAdminUsersTestUtil.adminUsersRequestOkAndReturn(accessToken);
	    
		int userIdInt = getLastId(userBasicDetailsCOs);
	    
	    this.apiAdminUsersTestUtil.adminUsersSendWelcomeEmailRequest(accessToken, userIdInt)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userId", is(userIdInt)))
				.andExpect(jsonPath("$.sent", is(Boolean.TRUE)));
	}

	@Test
	public void sendWelcomeEmailToInactiveUser() throws Exception {
	    String accessToken = this.tokenCO.getAccess_token();

		Set<UserBasicDetailsCO> userBasicDetailsCOs = this.apiAdminUsersTestUtil.adminUsersRequestOkAndReturn(accessToken);
	    
		int userIdInt = getLastId(userBasicDetailsCOs) + 1;
	    
	    this.apiAdminUsersTestUtil.adminUsersSendWelcomeEmailRequest(accessToken, userIdInt)
				.andExpect(status().is4xxClientError());
	}

	private static int getLastId(Set<UserBasicDetailsCO> orderedUserBasicDetailsCOs) {
		Long userIdLong = null;
	    for (UserBasicDetailsCO userBasicDetailsCO : orderedUserBasicDetailsCOs) {
	    	userIdLong = userBasicDetailsCO.getId();
	    }
	    assertNotNull(userIdLong);
	    return userIdLong.intValue();
	}


}
