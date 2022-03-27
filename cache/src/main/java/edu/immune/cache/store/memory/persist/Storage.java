package edu.immune.cache.store.memory.persist;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import edu.immune.cache.store.memory.exception.InvalidAttributeTypeException;
import edu.immune.cache.store.memory.exception.SchemaAttributeNotPresentException;

/**
 * Stores and manages actual data
 * 
 * @author Lalit Mehra
 *
 */
public class Storage {

	private static Storage _instance = new Storage();

	private static SecondaryIndex index = SecondaryIndex.getInstance();

	private Map<String, ConcurrentHashMap<String, StorageValue<?>>> store;

	private Storage() {
		store = new ConcurrentHashMap<String, ConcurrentHashMap<String, StorageValue<?>>>();
	}

	public static Storage getInstance() {
		return _instance;
	}

	/**
	 * Insert into storage
	 * 
	 * @param key
	 * @param values
	 * @throws InvalidAttributeTypeException
	 * @throws SchemaAttributeNotPresentException
	 */
	public void insert(String key, Map<String, Object> values)
			throws InvalidAttributeTypeException, SchemaAttributeNotPresentException {
		upsertNestedValues(key, values);
	}
	
	/**
	 * get value from the storage
	 * 
	 * @param key
	 * @return
	 */
	public ConcurrentHashMap<String, StorageValue<?>> get(String key) {
		return store.get(key);
	}

	/**
	 * delete from the storage and the secondary index
	 * 
	 * @param key
	 */
	public void delete(String key) {
		synchronized (this) {
			ConcurrentHashMap<String, StorageValue<?>> values = store.get(key);

			// delete from secondary index
			values.forEach((k, v) -> {
				index.deleteIndexValue(k + v.getValue().toString(), key);
			});
			
			// delete from data storage
			store.remove(key);
		}
	}

	/**
	 * upsert values in data storage
	 * 
	 * @param key
	 * @param values
	 */
	private void upsertNestedValues(String key, Map<String, Object> values) {
		ConcurrentHashMap<String, StorageValue<?>> nestedValues = getOrCreateStorageMap(key, values);
		Set<String> keys = values.keySet();

		for (String k : keys) {
			synchronized (this) {
				StorageValue<?> sv = new StorageValue<>(values.get(k));
				// insert column wise values
				nestedValues.put(k, sv);
				
				// insert into secondary index
				index.insert(k + values.get(k).toString(), key);
			}
		}
	}

	/**
	 * fetch existing values from the map for the said key, otherwise create a new map and return 
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	private ConcurrentHashMap<String, StorageValue<?>> getOrCreateStorageMap(String key, Map<String, Object> values) {
		ConcurrentHashMap<String, StorageValue<?>> map = store.get(key);

		if (null == map) {
			synchronized (this) {
				if (null == map) {
					map = new ConcurrentHashMap<String, StorageValue<?>>();
					store.put(key, map);
				}
			}
		}

		return map;
	}

	public boolean deleteAttribute(String key, String attribute) {
		// TODO
		return false;
	}

}
