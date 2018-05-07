package edu.immune.cache.core;

import java.io.IOException;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.immune.cache.KVStore;

/** A LRU based Key Value pair that stores the value in Json Format.<br>
 * Value is converted to json format from object notation when pushed to the cache.<br>
 * Similarly, value is converted back to Object from json when pulled out from the cache.  
 * @author lalit mehra
 *
 * @param <K> Key
 * @param <V> Value
 */
public class JsonKVStore<K, V> implements KVStore<K, V> {
	
	private final ObjectMapper mapper;
	private Map<K, String> map;
	private final ReadWriteLock lock;
	private final Class<V> type;
	private Deque<K> queue;
	private final int maxSize;
	
	public JsonKVStore(Class<V> clazz) {
		mapper = new ObjectMapper();
		map = new ConcurrentHashMap<K, String>();
		lock = new ReentrantReadWriteLock();
		type = clazz;
		queue = new ConcurrentLinkedDeque<>();
		maxSize = 5;
	}

	@Override
	public V get(K key) throws IOException {
		V value = null;
		try {
			lock.readLock().lock();
			if(map.containsKey(key)) {
				value = mapper.readValue(map.get(key), type);
				queue.remove(key);
				queue.push(key);
			}
		} finally {
			lock.readLock().unlock();
		}
		return value;
	}

	@Override
	public void put(K key, V value) throws JsonProcessingException {
		try {
			lock.writeLock().lock();
			if(map.containsKey(key)) {
				String json = mapper.writeValueAsString(value);
				queue.remove(key);
				map.put(key, json);
				queue.push(key);
			} else {
				if(map.size() >= maxSize) {
					K qKey = queue.pop();
					map.remove(qKey);
					queue.add(key);
				} else {
					queue.add(key);
				}
				String json = mapper.writeValueAsString(value);
				map.put(key, json);
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void delete(K key) {
		try {
			lock.writeLock().lock();
			map.remove(key);
			queue.remove(key);
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void clear() {
		try {
			lock.writeLock().lock();
			Map<K,String> tempMap = map;
			map = new ConcurrentHashMap<>();
			Deque<K> tempDequeue = queue;
			queue = new ConcurrentLinkedDeque<>();
			tempMap.clear();
			tempMap = null;
			tempDequeue.clear();
			tempDequeue = null;
		} finally {
			lock.writeLock().unlock();
		}

	}

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

}
