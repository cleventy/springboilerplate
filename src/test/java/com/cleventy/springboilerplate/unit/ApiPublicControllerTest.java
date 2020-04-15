package com.cleventy.springboilerplate.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.storyservice.StoryService;
import com.cleventy.springboilerplate.business.services.storyservice.cos.PublicStoryDetailsCO;
import com.cleventy.springboilerplate.web.controller.api.ApiPublicStoriesController;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class ApiPublicControllerTest {

	private static final Long VALID_STORY_ID = 1L;
	private static final String VALID_STORY_TITLE = "Título";
	private static final double VALID_STORY_DIFFICULTY = 3.5;

	private static final Long INVALID_STORY_ID = 10L;

	@Autowired
	private ApiPublicStoriesController apiPublicStoriesController;
	
	@MockBean
	private StoryService storyService;

	@Before
	public void init() throws InstanceNotFoundException {

		PublicStoryDetailsCO storyDetailsCO = PublicStoryDetailsCO.builder()
				.id(VALID_STORY_ID)
				.title(VALID_STORY_TITLE)
				.difficulty(VALID_STORY_DIFFICULTY)
				.build();
		
		when(this.storyService.getStoryDetails(VALID_STORY_ID)).thenReturn(storyDetailsCO);

		when(this.storyService.getStoryDetails(INVALID_STORY_ID)).thenThrow(InstanceNotFoundException.class);
		
	}

	@Test
	public void getStoryDetails() throws InstanceNotFoundException {
		
		ResponseEntity<PublicStoryDetailsCO> storyDetailsCOResponseEntity = this.apiPublicStoriesController.getStoryDetails(VALID_STORY_ID);
		
		assertEquals(storyDetailsCOResponseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void getInvalidStoryDetails() throws InstanceNotFoundException {
		
		this.apiPublicStoriesController.getStoryDetails(INVALID_STORY_ID);
		
	}

}
