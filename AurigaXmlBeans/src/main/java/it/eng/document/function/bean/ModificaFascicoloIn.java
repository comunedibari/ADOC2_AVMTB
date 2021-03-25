package it.eng.document.function.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModificaFascicoloIn extends AllegatiBean implements Serializable{

	private BigDecimal idFolderIn;
	private BigDecimal idLibrary;
	private String nomeLibrary;
	private XmlFascicoloIn modificaFascicolo;
	private String folderPath;
	private Flag flgAppendDocFascIstruttoria;
	
	public BigDecimal getIdFolderIn() {
		return idFolderIn;
	}

	public void setIdFolderIn(BigDecimal idFolderIn) {
		this.idFolderIn = idFolderIn;
	}

	public XmlFascicoloIn getModificaFascicolo() {
		return modificaFascicolo;
	}

	public void setModificaFascicolo(XmlFascicoloIn modificaFascicolo) {
		this.modificaFascicolo = modificaFascicolo;
	}

	public void setIdLibrary(BigDecimal idLibrary) {
		this.idLibrary = idLibrary;
	}

	public BigDecimal getIdLibrary() {
		return idLibrary;
	}

	public void setNomeLibrary(String library) {
		this.nomeLibrary = library;
	}

	public String getNomeLibrary() {
		return nomeLibrary;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public Flag getFlgAppendDocFascIstruttoria() {
		return flgAppendDocFascIstruttoria;
	}

	public void setFlgAppendDocFascIstruttoria(Flag flgAppendDocFascIstruttoria) {
		this.flgAppendDocFascIstruttoria = flgAppendDocFascIstruttoria;
	}
}
