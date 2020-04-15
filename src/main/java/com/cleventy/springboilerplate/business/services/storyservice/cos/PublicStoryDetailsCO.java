package com.cleventy.springboilerplate.business.services.storyservice.cos;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper=true) @Data @SuperBuilder
public class PublicStoryDetailsCO extends StoryDetailsCO implements Serializable {
	
	private static final long serialVersionUID = -2126029883754347647L;
	
	public PublicStoryDetailsCO() {
		super();
		// neccessary for tests due this class does not have any property
	}
}
