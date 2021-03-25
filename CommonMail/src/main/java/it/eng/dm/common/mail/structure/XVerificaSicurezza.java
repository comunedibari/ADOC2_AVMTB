package it.eng.dm.common.mail.structure;

public enum XVerificaSicurezza {

	ERRORE("errore");
	
	private String value;
	
	private XVerificaSicurezza(String value) {
		this.value = value;
	}
	
	public static XVerificaSicurezza valueOfValue(String name){
		for(XVerificaSicurezza verificasicurezza:XVerificaSicurezza.values()){
			if(verificasicurezza.value.equals(name)){
				return verificasicurezza;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
}