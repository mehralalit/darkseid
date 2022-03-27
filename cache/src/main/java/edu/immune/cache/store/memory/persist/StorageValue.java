package edu.immune.cache.store.memory.persist;

public class StorageValue<T> {

	private T value;

	public StorageValue(T value) {
		super();
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
