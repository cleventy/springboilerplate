package com.cleventy.springboilerplate.business.entities.story;

import org.springframework.data.repository.CrudRepository;

public interface StoryRepository extends CrudRepository<Story, Long> {

	public Iterable<Story> findAllByState(Integer state);

	public Story findByIdAndState(Long storyId, Integer state);

}
