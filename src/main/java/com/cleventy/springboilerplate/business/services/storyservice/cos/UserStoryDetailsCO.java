package com.cleventy.springboilerplate.business.services.storyservice.cos;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper=true) @Data @SuperBuilder
public class UserStoryDetailsCO extends StoryDetailsCO implements Serializable {
	
	private static final long serialVersionUID = 7377048535310159311L;
	
}
