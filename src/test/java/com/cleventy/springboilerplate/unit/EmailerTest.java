package com.cleventy.springboilerplate.unit;

import java.io.IOException;
import java.util.Locale;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cleventy.springboilerplate.util.date.DateUtil;
import com.cleventy.springboilerplate.util.email.EmailSender;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class EmailerTest {

	private static final String USER_USERNAME = "user";
	private static final String USER_EMAIL = "user@domain.com";
	private static final Locale LOCALE = Locale.getDefault();

	@Autowired
	private EmailSender emailSender;

	@Test
	public void sendEmail() throws IOException, MessagingException {
		
		this.emailSender.sendWelcomeEmail(USER_EMAIL, USER_USERNAME, DateUtil.getNow(), LOCALE);
		
	}

}
