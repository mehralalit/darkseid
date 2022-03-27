package edu.immune.cache.store.memory.conf;

public class StorageConfiguration {

	private long defaultTTL;
	private long defaultReplicationFactor;
	private long defaultFileSize;
	private String fileLocation;

	public long getDefaultTTL() {
		return defaultTTL;
	}

	public void setDefaultTTL(long defaultTTL) {
		this.defaultTTL = defaultTTL;
	}

	public long getDefaultReplicationFactor() {
		return defaultReplicationFactor;
	}

	public void setDefaultReplicationFactor(long defaultReplicationFactor) {
		this.defaultReplicationFactor = defaultReplicationFactor;
	}

	public long getDefaultFileSize() {
		return defaultFileSize;
	}

	public void setDefaultFileSize(long defaultFileSize) {
		this.defaultFileSize = defaultFileSize;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

}
