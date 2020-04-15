package com.cleventy.springboilerplate.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.storyservice.StoryService;
import com.cleventy.springboilerplate.business.services.storyservice.cos.UserStoryDetailsCO;
import com.cleventy.springboilerplate.web.spring.SessionUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(ApiURL.BASE+ApiURL.USER)
@Slf4j
public class ApiUserStoriesController {
	
	@Autowired
	private StoryService storyService;
	
	@GetMapping(path=ApiURL.STORIES + ApiURL.SLASH + ApiURL.VAR_INI + ApiURL.VAR_ID + ApiURL.VAR_END, produces = ApiUtils.ACCEPT_HEADER)
	public ResponseEntity<UserStoryDetailsCO> getStoryDetails(@PathVariable(ApiURL.VAR_ID) Long storyId) throws InstanceNotFoundException {
		log.debug("api user story details");
		return ResponseEntity.ok().body(this.storyService.getStoryDetails(SessionUtil.getLoggedUserId(), storyId));
	}

}
