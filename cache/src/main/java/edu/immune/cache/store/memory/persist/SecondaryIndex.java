package edu.immune.cache.store.memory.persist;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Store and manage secondary index
 * 
 * @author Lalit Mehra
 *
 */
public class SecondaryIndex {

	private static SecondaryIndex _instance = new SecondaryIndex();

	private Map<String, ConcurrentSkipListSet<String>> index;

	private SecondaryIndex() {
		index = new ConcurrentHashMap<String, ConcurrentSkipListSet<String>>();
	}

	public static SecondaryIndex getInstance() {
		return _instance;
	}
	
	/**
	 * insert into secondary index
	 * 
	 * @param key
	 * @param value
	 */
	public void insert(String key, String value) {
		if(index.containsKey(key)) {
			index.get(key).add(value);
		} else {
			ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>();
			set.add(value);
			index.put(key, set);
		}
	}
	
	/**
	 * get references to storage keys from secondary index
	 * 
	 * @param key
	 * @return
	 */
	public ConcurrentSkipListSet<String> get(String key) {
		return index.get(key);
	}
	
	/**
	 * delete an index
	 * 
	 * @param key
	 */
	public void deleteIndex(String key) {
		index.remove(key);
	}
	
	/**
	 * delete specific data storage key from the index
	 * 
	 * @param key
	 * @param value
	 */
	public void  deleteIndexValue(String key, String value) {
		index.get(key).remove(value);
	}

}
