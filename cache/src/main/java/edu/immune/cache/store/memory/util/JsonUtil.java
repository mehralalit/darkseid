package edu.immune.cache.store.memory.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.immune.cache.store.memory.persist.StorageValue;

public class JsonUtil {
	
	public static String mapToJson(ConcurrentHashMap<String, StorageValue<?>> map) {
		return "";
	}
	
	public static Map<String, Object> jsonToMap(String json_value) {
		return new HashMap<String, Object>();
	}

}
