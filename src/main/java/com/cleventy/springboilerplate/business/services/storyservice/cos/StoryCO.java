package com.cleventy.springboilerplate.business.services.storyservice.cos;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StoryCO implements Serializable {
	private static final long serialVersionUID = -2126029883754347647L;
	private Long id;
    private String title;
    private String description;
    private String image;
    private double difficulty;
    private Set<StoryCategoryCO> categories;
    private Set<PublicRouteCO> routes;
}
