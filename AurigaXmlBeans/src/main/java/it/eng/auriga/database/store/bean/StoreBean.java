package it.eng.auriga.database.store.bean;

public abstract class StoreBean {

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
