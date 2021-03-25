package it.eng.utility.email.operation.impl.archiveoperation.utils;

public enum MimeType {

	CARTACEO("cartaceo"),
	TELEMATICO("telematico"),
	MIME("mime");
	
	
	private String value;
	
	private MimeType(String value) {
		this.value = value;
	}
	
	public static MimeType valueOfValue(String name){
		for(MimeType stato:MimeType.values()){
			if(stato.value.equals(name)){
				return stato;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
	
}
