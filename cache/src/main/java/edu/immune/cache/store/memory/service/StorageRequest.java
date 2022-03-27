package edu.immune.cache.store.memory.service;

import java.util.Map;

import edu.immune.cache.store.memory.exception.InvalidAttributeTypeException;
import edu.immune.cache.store.memory.exception.SchemaAttributeNotPresentException;
import edu.immune.cache.store.memory.persist.SchemaDefinition;
import edu.immune.cache.store.memory.persist.Storage;
import edu.immune.cache.store.memory.util.JsonUtil;
import edu.immune.cache.store.memory.util.StorageUtil;

/**
 * Storage access page 
 * 
 * @author Lalit Mehra
 *
 */
public class StorageRequest {

	private static SchemaDefinition sdef = SchemaDefinition.getInstance();
	private static Storage storage = Storage.getInstance();

	public void save(String key, String json_value)
			throws InvalidAttributeTypeException, SchemaAttributeNotPresentException {
		Map<String, Object> map =  JsonUtil.jsonToMap(json_value);
		sdef.validateAndInsertAttributeTypes(key, map);
		StorageUtil.addTTLIfNotExist(map);
		storage.insert(key, map);
	}

	public void delete(String key) {
		storage.delete(key);
	}

	public void delete(String key, String argument) {
		storage.deleteAttribute(key, argument);
	}

	public String get(String key) {
		return JsonUtil.mapToJson(storage.get(key));
	}
	
}
