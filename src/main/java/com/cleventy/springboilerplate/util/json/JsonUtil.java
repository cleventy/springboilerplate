package com.cleventy.springboilerplate.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static String toJson(Object o) throws JsonProcessingException {
		return objectMapper.writeValueAsString(o);
	}
	public static <T> T toObject(String json, Class<T> clazz) throws JsonMappingException, JsonProcessingException {
		return objectMapper.readValue(json, clazz);
	}
	public static <T> T toObject(String json, TypeReference<T> typeReference) throws JsonMappingException, JsonProcessingException {
		JsonNode jsonNode = JsonUtil.toObject(json, JsonNode.class);
		return toObject(jsonNode, typeReference);
	}
	private static <T> T toObject(JsonNode jsonNode, TypeReference<T> typeReference) throws JsonMappingException, JsonProcessingException {
		return objectMapper.convertValue(jsonNode, typeReference);
	}
}
