package com.cleventy.springboilerplate.unit;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cleventy.springboilerplate.business.services.cos.TestCO;
import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.web.controller.api.ApiPublicController;
import com.cleventy.springboilerplate.web.controller.api.ApiURL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ApiPublicRestTest {

	@MockBean
	private ApiPublicController apiPublicController;
	
	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;


	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + this.port);
	}
	
	@Before
	public void init() throws InstanceNotFoundException {

		TestCO testCO = new TestCO("test");
		
		when(this.apiPublicController.test()).thenReturn(ResponseEntity.ok().body(testCO));
	}


	@Test
	public void getTest() throws JsonMappingException, JsonProcessingException {
		
		ResponseEntity<TestCO> response = this.template.getForEntity(this.base.toString()+ApiURL.BASE+ApiURL.TEST, TestCO.class);
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(response.getBody(), equalTo(new TestCO("test")));
	}

}
