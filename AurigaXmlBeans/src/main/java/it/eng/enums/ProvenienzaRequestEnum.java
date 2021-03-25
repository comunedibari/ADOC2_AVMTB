package it.eng.enums;


public enum ProvenienzaRequestEnum {
	
	REQ_ORIGIN_LOTTO ("LOTTO_DOC"),
	REQ_ORIGIN_ELENCO("ELENCO_DOC");
	
	private final String provRequest;
	
	ProvenienzaRequestEnum(String provRequest){
		this.provRequest = provRequest;
	}

	public String getProvenienzaRequest() {
		return provRequest;
	}
	
	public static final ProvenienzaRequestEnum fromString(String provRequest) {
		for (ProvenienzaRequestEnum provRequestEnum : values()) {
			if (provRequestEnum.getProvenienzaRequest().equals(provRequest))
				return provRequestEnum;
		}
		return null;
	}
	
}
