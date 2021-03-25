package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaClassificatoreGenerico extends ContabiliaEntitaBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String tipoCodifica;
	
	public String getTipoCodifica() {
		return tipoCodifica;
	}
	
	public void setTipoCodifica(String tipoCodifica) {
		this.tipoCodifica = tipoCodifica;
	}
	
	@Override
	public String toString() {
		return "ContabiliaClassificatoreGenerico [tipoCodifica=" + tipoCodifica + "]";
	}
	
}
