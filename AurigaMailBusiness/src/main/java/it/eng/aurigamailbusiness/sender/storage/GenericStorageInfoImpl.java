package it.eng.aurigamailbusiness.sender.storage;

import it.eng.utility.storageutil.GenericStorageInfo;

public class GenericStorageInfoImpl implements GenericStorageInfo {
	
	private final String idUtilizzatore;
	
	public GenericStorageInfoImpl(String idUtilizzatore) {
		this.idUtilizzatore = idUtilizzatore;
 	}

	@Override
	public String getUtilizzatoreStorageId() {
		return idUtilizzatore;
	}

}
