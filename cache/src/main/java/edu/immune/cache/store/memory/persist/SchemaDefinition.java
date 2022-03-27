package edu.immune.cache.store.memory.persist;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import edu.immune.cache.store.memory.exception.InvalidAttributeTypeException;
import edu.immune.cache.store.memory.exception.SchemaAttributeNotPresentException;

/**
 * Stores and manages schema definition (column types)
 * 
 * @author Lalit Mehra
 *
 */
public class SchemaDefinition {
	
	private static SchemaDefinition _instance = new SchemaDefinition();
	
	private Map<String, Class<?>> schema;
	
	private SchemaDefinition() {
		schema = new ConcurrentHashMap<String, Class<?>>();
	}
	
	public static SchemaDefinition getInstance() {
		return _instance;
	}
	
	
	/**
	 * Insert a new key value pair 
	 * 
	 * @param key
	 * @param value
	 */
	public void insert(String key, Class<?> value) {
		schema.put(key, value);
	}
	
	/**
	 * get type of a column 
	 * 
	 * @param key
	 * @return
	 */
	public Class<?> get(String key) {
		return schema.get(key);
	}
	
	/**
	 * check if a column definition already exists
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return schema.containsKey(key);
	}
	
	/**
	 * validate if the new definitions are same as the existing ones 
	 * and insert new definitions
	 * 
	 * @param key
	 * @param values
	 * @return
	 * @throws SchemaAttributeNotPresentException
	 * @throws InvalidAttributeTypeException
	 */
	public boolean validateAndInsertAttributeTypes(String key, Map<String, Object> values)
			throws InvalidAttributeTypeException {
		Set<String> keys = values.keySet();
		Map<String, Object> newDefinitions = null;
		
		boolean resp = true;
		for (String k : keys) {
			try {
			if (containsKey(k))
				resp = resp && validate(k, values.get(k).getClass());
			} catch(SchemaAttributeNotPresentException ex) {
				if(null == newDefinitions)
					newDefinitions = new HashMap<>();
				else
					newDefinitions.put(k, values.get(k));
			}
		}
		
		if(!resp)
			return false;

		insertNewColumnAndType(newDefinitions);
		
		return true;
	}
	
	/**
	 * insert new column and type
	 * 
	 * @param type
	 */
	private void insertNewColumnAndType(Map<String, Object> type) {
		Set<String> nk = type.keySet();
		for (String k : nk) {
			if (!containsKey(k))
				insert(k, type.get(k).getClass());
		}
	}
	
	/**
	 * validate correctness of individual column type 
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws SchemaAttributeNotPresentException
	 */
	private boolean validate(String key, Class<?> value) throws SchemaAttributeNotPresentException {
		if (schema.containsKey(key)) {
			return schema.get(key).equals(value);
		}
		
		throw new SchemaAttributeNotPresentException(String.format("No attribute with name: {0} is present", key));
	}
	
}
