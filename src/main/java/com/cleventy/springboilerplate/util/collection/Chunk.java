package com.cleventy.springboilerplate.util.collection;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Chunk<T> {
	private List<T> items;
	private long total;
	private Integer currentPage;
	private int chunkSize;
	private boolean hasMore;
}
