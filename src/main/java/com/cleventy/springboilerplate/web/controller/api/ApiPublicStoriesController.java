package com.cleventy.springboilerplate.web.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.storyservice.StoryService;
import com.cleventy.springboilerplate.business.services.storyservice.cos.PublicStoryDetailsCO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(ApiURL.BASE)
@Slf4j
public class ApiPublicStoriesController {
	
	@Autowired
	private StoryService storyService;
	
	@GetMapping(path=ApiURL.STORIES, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<Map<String, Object>> getStories() {
		log.debug("api stories");
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("stories",this.storyService.getStories());
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping(path=ApiURL.STORIES + ApiURL.SLASH + ApiURL.VAR_INI + ApiURL.VAR_ID + ApiURL.VAR_END, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<PublicStoryDetailsCO> getStoryDetails(@PathVariable(ApiURL.VAR_ID) Long storyId) throws InstanceNotFoundException {
		log.debug("api story details");
		return ResponseEntity.ok().body(this.storyService.getStoryDetails(storyId));
	}
	
}
