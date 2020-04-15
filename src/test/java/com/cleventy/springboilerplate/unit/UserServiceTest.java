package com.cleventy.springboilerplate.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cleventy.springboilerplate.business.entities.user.User;
import com.cleventy.springboilerplate.business.entities.user.UserConstants;
import com.cleventy.springboilerplate.business.entities.user.UserRepository;
import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.userservice.UserService;
import com.cleventy.springboilerplate.util.TestUtil;
import com.cleventy.springboilerplate.util.date.DateUtil;
import com.cleventy.springboilerplate.util.email.EmailSender;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private EmailSender emailSender;

	@Before
	public void init() {

		doNothing().when(this.emailSender).sendEmailWelcome();

		User userActive = User.builder()
				.id(TestUtil.VALID_USER_ID)
				.username(TestUtil.VALID_USER_USERNAME)
				.password(TestUtil.VALID_USER_PASSWORD)
				.email(TestUtil.VALID_USER_EMAIL)
				.name("name")
				.role(UserConstants.ROLE_ADMIN)
				.state(UserConstants.STATE_ACTIVE)
				.registerDate(DateUtil.getNow())
				.build();
		
		User userInactive = User.builder()
				.id(TestUtil.INACTIVE_USER_ID)
				.username(TestUtil.INACTIVE_USER_USERNAME)
				.state(UserConstants.STATE_INACTIVE)
				.build();
		
		when(this.userRepository.findByUsername(TestUtil.VALID_USER_USERNAME)).thenReturn(userActive);
		when(this.userRepository.findByUsername(TestUtil.INACTIVE_USER_USERNAME)).thenReturn(userInactive);
		when(this.userRepository.findByUsername(TestUtil.INVALID_USER_USERNAME)).thenReturn(null);
		when(this.userRepository.findById(TestUtil.VALID_USER_ID)).thenReturn(Optional.of(userActive));
		when(this.userRepository.findById(TestUtil.INACTIVE_USER_ID)).thenReturn(Optional.empty());

	}

	@Test
	public void getValidUser() throws InstanceNotFoundException {
		User user = this.userService.findUserAndCheckNullAndState(TestUtil.VALID_USER_USERNAME);
		assertEquals(user.getUsername(), TestUtil.VALID_USER_USERNAME);
		assertEquals(user.getEmail(), TestUtil.VALID_USER_EMAIL);
		assertEquals(user.getState(), UserConstants.STATE_ACTIVE);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void getInactiveUser() throws InstanceNotFoundException {
		this.userService.findUserAndCheckNullAndState(TestUtil.INACTIVE_USER_USERNAME);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void getInvalidUser() throws InstanceNotFoundException {
		this.userService.findUserAndCheckNullAndState(TestUtil.INVALID_USER_USERNAME);
	}

	@Test
	public void sendWelcomeEmailToValidUser() throws InstanceNotFoundException, IOException, MessagingException {
		this.userService.sendWelcomeEmail(TestUtil.VALID_USER_ID);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void sendWelcomeEmailToInvalidUser() throws InstanceNotFoundException, IOException, MessagingException {
		this.userService.sendWelcomeEmail(TestUtil.INACTIVE_USER_ID);
	}

}
