package it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean;

/**
 * 
 * @author DANCRIST
 *
 */

public class ItemTagCommentiMailBean {
	
	private String itemLavTag;
	private String itemLavCommento;
	private Boolean flgInibitaModificaCancellazione;
	private Integer flgCommentoObbligatorioTag;
	
	public String getItemLavTag() {
		return itemLavTag;
	}
	
	public void setItemLavTag(String itemLavTag) {
		this.itemLavTag = itemLavTag;
	}
	
	public String getItemLavCommento() {
		return itemLavCommento;
	}
	
	public void setItemLavCommento(String itemLavCommento) {
		this.itemLavCommento = itemLavCommento;
	}

	public Boolean getFlgInibitaModificaCancellazione() {
		return flgInibitaModificaCancellazione;
	}

	public void setFlgInibitaModificaCancellazione(Boolean flgInibitaModificaCancellazione) {
		this.flgInibitaModificaCancellazione = flgInibitaModificaCancellazione;
	}

	public Integer getFlgCommentoObbligatorioTag() {
		return flgCommentoObbligatorioTag;
	}

	public void setFlgCommentoObbligatorioTag(Integer flgCommentoObbligatorioTag) {
		this.flgCommentoObbligatorioTag = flgCommentoObbligatorioTag;
	}

}