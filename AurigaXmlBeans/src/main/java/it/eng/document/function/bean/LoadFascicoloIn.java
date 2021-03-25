package it.eng.document.function.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoadFascicoloIn implements Serializable{

	private BigDecimal idFolderIn;
	private String flgSoloAbilAzioni;		
	private String flgFascInCreazione;	
	
	public BigDecimal getIdFolderIn() {
		return idFolderIn;
	}

	public void setIdFolderIn(BigDecimal idFolderIn) {
		this.idFolderIn = idFolderIn;
	}

	public String getFlgSoloAbilAzioni() {
		return flgSoloAbilAzioni;
	}

	public void setFlgSoloAbilAzioni(String flgSoloAbilAzioni) {
		this.flgSoloAbilAzioni = flgSoloAbilAzioni;
	}

	public String getFlgFascInCreazione() {
		return flgFascInCreazione;
	}

	public void setFlgFascInCreazione(String flgFascInCreazione) {
		this.flgFascInCreazione = flgFascInCreazione;
	}
	
}
