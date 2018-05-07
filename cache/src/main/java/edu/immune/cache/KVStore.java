package edu.immune.cache;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * binding contract for creating a key/value cache
 * @author lalit mehra
 *
 * @param <K> generic type key
 * @param <V> generic type value
 */
public interface KVStore<K, V> {
	/**
	 * Get value from the cache based on the supplied key
	 * @param key key to retrieve a value from the cache
	 * @return
	 * @throws IOException
	 */
	public V get(K key) throws IOException;

	/**
	 * Put key value pair to the cache
	 * @param key key to store a value in the cache
	 * @param value value of the supplied key
	 * @throws JsonProcessingException
	 */
	public void put(K key, V value) throws JsonProcessingException;

	/**
	 * Delete an entry from the cache
	 * @param key key to delete a value from the cache
	 */
	public void delete(K key);

	/**
	 * Clear the cache
	 */
	public void clear();

	/** Get current size of the cache 
	 * @return long size of the cache
	 */
	public long size();
}
