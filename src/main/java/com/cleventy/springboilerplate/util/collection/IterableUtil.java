package com.cleventy.springboilerplate.util.collection;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.cleventy.springboilerplate.business.services.exceptions.BadRequestException;

public class IterableUtil {

	public static final int CHUNK_SIZE = 1;
	
	public static <T> Stream<T> toStream(Iterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}
	public static <T> Chunk<T> toChunk(List<T> items, long total, Integer currentPage, int chunkSize) {
		boolean hasMore = ( (currentPage-1)*chunkSize+items.size() ) < total;
		return Chunk.<T>builder()
				.items(items)
				.total(total)
				.currentPage(currentPage)
				.chunkSize(chunkSize)
				.hasMore(hasMore)
				.build();
	}
	
	public static int getPageToRequestToRepositoryAndCheck(Integer page) {
		int pageToRequestToRepository = page.intValue()-1;
		if (pageToRequestToRepository<0) {
			throw new BadRequestException(page, "page not found");
		}
		return pageToRequestToRepository;
	}


}
