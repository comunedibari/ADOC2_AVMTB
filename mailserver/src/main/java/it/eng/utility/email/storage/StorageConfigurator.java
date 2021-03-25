package it.eng.utility.email.storage;

public class StorageConfigurator {

	private String prefix;
	private String idPrefixStorage;
	private String storageDefault;

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getIdPrefixStorage() {
		return idPrefixStorage;
	}

	public void setIdPrefixStorage(String idPrefixStorage) {
		this.idPrefixStorage = idPrefixStorage;
	}

	public void setStorageDefault(String storageDefault) {
		this.storageDefault = storageDefault;
	}

	public String getStorageDefault() {
		return storageDefault;
	}
}
