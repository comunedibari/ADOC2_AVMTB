package it.eng.dm.common.mail.structure;

public enum XTrasporto {

	POSTA_CERTIFICATA("posta-certificata"),
	ERRORE("errore");
	
	private String value;
	
	private XTrasporto(String value) {
		this.value = value;
	}
	
	public static XTrasporto valueOfValue(String name){
		for(XTrasporto trasporto:XTrasporto.values()){
			if(trasporto.value.equals(name)){
				return trasporto;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
}