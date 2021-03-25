package it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;


public class AttributiDinamiciGruppiSoggettiXmlBean {	

	@XmlVariabile(nome = "FLG_INIBITA_ASS", tipo=TipoVariabile.SEMPLICE)
	private String flgInibitaAss;

	public String getFlgInibitaAss() {
		return flgInibitaAss;
	}

	public void setFlgInibitaAss(String flgInibitaAss) {
		this.flgInibitaAss = flgInibitaAss;
	}


	
}
