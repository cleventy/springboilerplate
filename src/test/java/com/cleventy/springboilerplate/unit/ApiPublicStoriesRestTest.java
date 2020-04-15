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

import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.storyservice.cos.PublicStoryDetailsCO;
import com.cleventy.springboilerplate.business.services.storyservice.cos.StoryDetailsCO;
import com.cleventy.springboilerplate.web.controller.api.ApiPublicStoriesController;
import com.cleventy.springboilerplate.web.controller.api.ApiURL;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ApiPublicStoriesRestTest {

	private static final Long VALID_STORY_ID = 1L;
	private static final String VALID_STORY_TITLE = "Título";
	private static final double VALID_STORY_DIFFICULTY = 3.5;

	private static final Long INVALID_STORY_ID = 10L;


	@MockBean
	private ApiPublicStoriesController apiPublicStoriesController;
	
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

		PublicStoryDetailsCO storyDetailsCO = PublicStoryDetailsCO.builder()
				.id(VALID_STORY_ID)
				.title(VALID_STORY_TITLE)
				.difficulty(VALID_STORY_DIFFICULTY)
				.build();
		
		when(this.apiPublicStoriesController.getStoryDetails(VALID_STORY_ID)).thenReturn(ResponseEntity.ok().body(storyDetailsCO));
		
		when(this.apiPublicStoriesController.getStoryDetails(INVALID_STORY_ID)).thenThrow(InstanceNotFoundException.class);

	}


	@Test
	public void getValidStory() {
		ResponseEntity<PublicStoryDetailsCO> response = this.template.getForEntity(this.base.toString()+ApiURL.BASE+ApiURL.STORIES+ApiURL.SLASH+VALID_STORY_ID, PublicStoryDetailsCO.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		StoryDetailsCO responseStoryDetailsCO = response.getBody();
		assertThat(responseStoryDetailsCO.getId(), equalTo(VALID_STORY_ID));
		assertThat(responseStoryDetailsCO.getTitle(), equalTo(VALID_STORY_TITLE));
	}

	@Test
	public void getInvalidStory() {
		ResponseEntity<PublicStoryDetailsCO> response = this.template.getForEntity(this.base.toString()+ApiURL.BASE+ApiURL.STORIES+ApiURL.SLASH+INVALID_STORY_ID, PublicStoryDetailsCO.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
	}

}
