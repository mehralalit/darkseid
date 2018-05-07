package edu.immune.cache;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.immune.cache.core.JsonKVStore;

public class JsonKVStoreTest {

	public static JsonKVStore<String, String> kvStore;
	
	@BeforeClass
	public static void setUp() {
		kvStore = new JsonKVStore<String, String>(String.class);
	}
	
	@Test
	public void verifyPutMethod() {
		try {
			kvStore.put("key1", "value1");
			kvStore.put("key2", "value2");
			kvStore.put("key3", "value3");
			kvStore.put("key4", "value4");
			kvStore.put("key5", "value5");
			Assert.assertTrue(kvStore.size() == 5);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void verifyDeleteMethod() {
		JsonKVStore<String, String> kvStore = new JsonKVStore<String, String>(String.class);
		try {
			kvStore.put("key1", "value1");
			kvStore.put("key2", "value2");
			kvStore.put("key3", "value3");
			kvStore.delete("key2");
			Assert.assertTrue(kvStore.size() == 2);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void verifyClearMethod() {
		JsonKVStore<String, String> kvStore = new JsonKVStore<String, String>(String.class);
		try {
			kvStore.put("key1", "value1");
			kvStore.put("key2", "value2");
			kvStore.put("key3", "value3");
			kvStore.clear();
			Assert.assertTrue(kvStore.size() == 0);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void verifyGetMethod() throws IOException {
		JsonKVStore<String, String> kvStore = new JsonKVStore<String, String>(String.class);
		try {
			kvStore.put("key2", "value2");
			kvStore.put("key1", "value1");
			kvStore.put("key3", "value3");
			Assert.assertEquals(kvStore.get("key1"), "value1");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void verifyGetMethod2() throws IOException {
		JsonKVStore<String, String> kvStore = new JsonKVStore<String, String>(String.class);
		try {
			kvStore.put("key2", "value2");
			kvStore.put("key1", "value1");
			kvStore.put("key3", "value3");
			kvStore.get("key1");
			Assert.assertTrue(kvStore.size() == 3);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
