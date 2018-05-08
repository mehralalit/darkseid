package edu.immune.cache.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.immune.cache.KVStore;

/**
 * A LRU based Key Value pair that stores the value in Json Format.<br>
 * Value is converted to json format from object notation when pushed to the
 * cache.<br>
 * Similarly, value is converted back to Object from json when pulled out from
 * the cache.
 * 
 * @author lalit mehra
 *
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class JsonKVStore<K, V> implements KVStore<K, V> {

	private final ObjectMapper mapper;
	private Map<K, String> map;
	private final ReadWriteLock lock;
	private final Class<V> type;
	private Deque<K> queue;
	private final int maxSize;
	private File file;
	private File tmpFile;
	private final String pairDelimiter;

	public JsonKVStore(Class<V> clazz) {
		mapper = new ObjectMapper();
		map = new ConcurrentHashMap<K, String>();
		lock = new ReentrantReadWriteLock();
		type = clazz;
		queue = new ConcurrentLinkedDeque<>();
		maxSize = 100;
		file = new File("/tmp/cache");
		tmpFile = new File("/tmp/tmpcache");
		pairDelimiter = ":";
	}

	/* (non-Javadoc)
	 * @see edu.immune.cache.KVStore#get(java.lang.Object)
	 */
	@Override
	public V get(K key) throws IOException {
		V value = null;
		try {
			lock.readLock().lock();
			if (map.containsKey(key)) {
				value = mapper.readValue(map.get(key), type);
				queue.remove(key);
				queue.push(key);
			} else {
				// check if file contains the value and return it
				try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
					if (reader.ready()) {
						String[] kvp;
						String pair, pKey, pValue;
						while ((pair = reader.readLine()) != null) {
							kvp = pair.split(":");
							pKey = kvp[0];
							pValue = kvp[1];
							if (pKey.equals(key)) {
								this.put(key, mapper.readValue(pValue, type));
								break;
							}
						}
					}
				}
			}
		} finally {
			lock.readLock().unlock();
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see edu.immune.cache.KVStore#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void put(K key, V value) throws IOException {
		try {
			lock.writeLock().lock();
			if (map.containsKey(key)) {
				String json = mapper.writeValueAsString(value);
				queue.remove(key);
				map.put(key, json);
				queue.push(key);
			} else {
				if (map.size() >= maxSize) {
					K qKey = queue.pop();
					map.remove(qKey);
					queue.add(key);
				} else {
					queue.add(key);
				}
				String json = mapper.writeValueAsString(value);
				// write key value pair to the file, delimited by :
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
					writer.append(key + pairDelimiter + json);
				}
				map.put(key, json);
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	/* 
	 * @see edu.immune.cache.KVStore#delete(java.lang.Object)
	 * Data is not removed from the underlying file. <br> 
	 * A separate scheduled task should be run from time to time to remove unwanted entries from the file. 
	 */
	@Override
	public void delete(K key) throws IOException {
		try {
			lock.writeLock().lock();
			map.remove(key);
			queue.remove(key);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see edu.immune.cache.KVStore#clear()
	 */
	@Override
	public void clear() throws FileNotFoundException, IOException {
		try {
			lock.writeLock().lock();
			Map<K, String> tempMap = map;
			map = new ConcurrentHashMap<>();
			Deque<K> tempDequeue = queue;
			queue = new ConcurrentLinkedDeque<>();
			tempMap.clear();
			tempMap = null;
			tempDequeue.clear();
			tempDequeue = null;

			// clear the file data
			clearFile();
		} finally {
			lock.writeLock().unlock();
		}

	}

	/* (non-Javadoc)
	 * @see edu.immune.cache.KVStore#size()
	 */
	@Override
	public long size() {
		int size = 0;
		try {
			lock.readLock().lock();
			size = map.size();
		} finally {
			lock.readLock().unlock();
		}
		return size;
	}

	/**
	 * Check if the files exist, if they do then remove the existing ones and create new ones replacing them
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void clearFile() throws FileNotFoundException, IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(file));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile))) {
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			if (tmpFile.exists()) {
				file.delete();
				file.createNewFile();
			}
		}
	}

}
