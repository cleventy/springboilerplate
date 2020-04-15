package com.cleventy.springboilerplate.business.services.storyservice.cos;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public abstract class StoryDetailsCO implements Serializable {
	
	private static final long serialVersionUID = -2126029883754347647L;
	
	protected Long id;
	protected String title;
	protected String description;
	protected String image;
	protected double difficulty;
	protected Set<StoryCategoryCO> categories;
    protected Set<? extends RouteCO> routes;
    protected StoryDetailsBookCO book;
    
}
