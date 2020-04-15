package com.cleventy.springboilerplate.business.services.storyservice;

import java.util.Set;

import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.storyservice.cos.PublicStoryDetailsCO;
import com.cleventy.springboilerplate.business.services.storyservice.cos.StoryCO;
import com.cleventy.springboilerplate.business.services.storyservice.cos.UserStoryDetailsCO;

public interface StoryService {

	public Set<StoryCO> getStories();

	public PublicStoryDetailsCO getStoryDetails(Long storyId) throws InstanceNotFoundException;

	public UserStoryDetailsCO getStoryDetails(Long userId, Long storyId) throws InstanceNotFoundException;

}
