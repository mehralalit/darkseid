package edu.immune.cache.store.file;

import java.io.FileNotFoundException;
import java.io.IOException;

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
	 * @throws IOException 
	 */
	public void put(K key, V value) throws IOException;

	/**
	 * Delete an entry from the cache
	 * @param key key to delete a value from the cache
	 * @throws IOException 
	 */
	public void delete(K key) throws IOException;

	/**
	 * Clear the cache
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void clear() throws FileNotFoundException, IOException;

	/** Get current size of the cache 
	 * @return long size of the cache
	 */
	public long size();
}
