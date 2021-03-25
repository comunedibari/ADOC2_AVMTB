package it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean;

import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;


public class FileDaConvertireBean extends IdFileBean {

	private String nomeFilePrec;
	private String uriPrec;	
	private MimeTypeFirmaBean infoFilePrec;
	
	public String getNomeFilePrec() {
		return nomeFilePrec;
	}
	public void setNomeFilePrec(String nomeFilePrec) {
		this.nomeFilePrec = nomeFilePrec;
	}
	public String getUriPrec() {
		return uriPrec;
	}
	public void setUriPrec(String uriPrec) {
		this.uriPrec = uriPrec;
	}
	public MimeTypeFirmaBean getInfoFilePrec() {
		return infoFilePrec;
	}
	public void setInfoFilePrec(MimeTypeFirmaBean infoFilePrec) {
		this.infoFilePrec = infoFilePrec;
	}
	
	
}
