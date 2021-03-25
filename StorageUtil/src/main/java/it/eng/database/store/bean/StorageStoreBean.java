package it.eng.database.store.bean;

import java.io.Serializable;

public abstract class StorageStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public enum StoreType { STORE, FUNCTION}
	protected StoreType type;

	public abstract String getErrmsgout();
	public abstract Integer getErrcodeout();
	public abstract String getErrcontextout();
	public abstract String getStoreName();
	public StoreType getType() {
		return type;
	}
}
