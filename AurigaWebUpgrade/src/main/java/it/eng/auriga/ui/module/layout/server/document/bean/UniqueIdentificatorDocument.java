package it.eng.auriga.ui.module.layout.server.document.bean;

import java.util.Map;

public class UniqueIdentificatorDocument {

	private UniqueIdentificationFileBean primario;
	private Map<Integer, UniqueIdentificationFileBean> allegati;
	public UniqueIdentificationFileBean getPrimario() {
		return primario;
	}
	public void setPrimario(UniqueIdentificationFileBean primario) {
		this.primario = primario;
	}
	public Map<Integer, UniqueIdentificationFileBean> getAllegati() {
		return allegati;
	}
	public void setAllegati(Map<Integer, UniqueIdentificationFileBean> allegati) {
		this.allegati = allegati;
	}
	
}
