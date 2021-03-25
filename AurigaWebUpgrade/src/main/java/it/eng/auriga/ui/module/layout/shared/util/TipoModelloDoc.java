package it.eng.auriga.ui.module.layout.shared.util;

public enum TipoModelloDoc {
	
	ODT_CON_FREEMARKERS("odt_con_freemarkers"), 
	DOCX_CON_CAMPI_CONTROLLO("docx_con_campi_controllo"), 
	DOCX_CON_PLACEHOLDER("docx_con_placeholder");
	
	private String value;
	
	private TipoModelloDoc(String value){
		setValue(value);
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value+"";
	}
}