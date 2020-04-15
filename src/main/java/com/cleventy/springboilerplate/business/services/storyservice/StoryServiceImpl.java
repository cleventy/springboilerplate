package com.cleventy.springboilerplate.business.services.storyservice;


import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cleventy.springboilerplate.business.entities.category.Category;
import com.cleventy.springboilerplate.business.entities.story.Story;
import com.cleventy.springboilerplate.business.entities.story.StoryConstants;
import com.cleventy.springboilerplate.business.entities.story.StoryRepository;
import com.cleventy.springboilerplate.business.services.exceptions.InstanceNotFoundException;
import com.cleventy.springboilerplate.business.services.storyservice.cos.CurrencyCO;
import com.cleventy.springboilerplate.business.services.storyservice.cos.PublicStoryDetailsCO;
import com.cleventy.springboilerplate.business.services.storyservice.cos.StoryCO;
import com.cleventy.springboilerplate.business.services.storyservice.cos.StoryCategoryCO;
import com.cleventy.springboilerplate.business.services.storyservice.cos.StoryDetailsBookCO;
import com.cleventy.springboilerplate.business.services.storyservice.cos.UserStoryDetailsCO;
import com.cleventy.springboilerplate.util.collection.IterableUtil;
import com.cleventy.springboilerplate.util.currency.CurrencyUtils;

@Service
public class StoryServiceImpl implements StoryService {
	
    @Autowired
    private StoryRepository storyRepository;
    
	@Override
	@Transactional(readOnly = true)
	public Set<StoryCO> getStories() {
		return IterableUtil.toStream(this.storyRepository.findAllByState(StoryConstants.STATE_ACTIVE))
				.map(story -> toStoryCO(story))
				.collect(Collectors.toSet());
	}

	@Override
	@Transactional(readOnly = true)
	public PublicStoryDetailsCO getStoryDetails(Long storyId) throws InstanceNotFoundException {
		Story story = this.storyRepository.findByIdAndState(storyId, StoryConstants.STATE_ACTIVE);
		if (story==null) {
			throw new InstanceNotFoundException(Long.valueOf(storyId), Story.class.getName());
		}
		return toPublicStoryDetailsCO(story, CurrencyUtils.EUR);
	}

	@Override
	@Transactional(readOnly = true)
	public UserStoryDetailsCO getStoryDetails(Long userId, Long storyId) throws InstanceNotFoundException {
		Story story = this.storyRepository.findByIdAndState(storyId, StoryConstants.STATE_ACTIVE);
		if (story==null) {
			throw new InstanceNotFoundException(Long.valueOf(storyId), Story.class.getName());
		}
		return toUserStoryDetailsCO(story, CurrencyUtils.EUR, userId);
	}
	
	
	//////////////////////////////////////////////////////////////////
	// AUX
	//////////////////////////////////////////////////////////////////

	// TRANSFORMATIONS
	public static StoryCO toStoryCO(Story story) {
		return StoryCO.builder()
				.id(story.getId())
				.title(story.getTitle())
				.description(story.getDescription())
				.image(story.getImage())
				.difficulty(story.getDifficulty())
				.categories(story.getCategories().stream().map(category -> toStoryCategoryCO(category)).collect(Collectors.toSet()))
				.build();
	}
	public static StoryCategoryCO toStoryCategoryCO(Category category) {
		return StoryCategoryCO.builder()
				.id(category.getId())
				.name(category.getName())
				.build();
	}
	public static StoryDetailsBookCO toStoryDetailsBookCO(Story story, CurrencyCO currency) {
		return StoryDetailsBookCO.builder()
				.enabled(story.getBookEnabled())
				.link(story.getWebUrl())
				.price(story.getBookPrice())
				.description(story.getBookDescription())
				.currency(currency)
				.build();
	}
	public static PublicStoryDetailsCO toPublicStoryDetailsCO(Story story, CurrencyCO currency) {
		return PublicStoryDetailsCO.builder()
				.id(story.getId())
				.title(story.getTitle())
				.description(story.getDescription())
				.image(story.getImage())
				.difficulty(story.getDifficulty())
				.categories(story.getCategories().stream().map(category -> toStoryCategoryCO(category)).collect(Collectors.toSet()))
				.book(toStoryDetailsBookCO(story, currency))
				.build();
	}
	public static UserStoryDetailsCO toUserStoryDetailsCO(Story story, CurrencyCO currency, Long userId) {
		return UserStoryDetailsCO.builder()
				.id(story.getId())
				.title(story.getTitle())
				.description(story.getDescription())
				.image(story.getImage())
				.difficulty(story.getDifficulty())
				.categories(story.getCategories().stream().map(category -> toStoryCategoryCO(category)).collect(Collectors.toSet()))
				.book(toStoryDetailsBookCO(story, CurrencyUtils.EUR))
				.build();
	}
}
