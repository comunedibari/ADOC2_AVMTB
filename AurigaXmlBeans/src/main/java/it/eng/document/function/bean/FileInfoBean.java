package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class FileInfoBean implements Serializable{

	private GenericFile allegatoRiferimento;
	private TipoFile tipo;
	private Integer posizione;

	public void setAllegatoRiferimento(GenericFile allegatoRiferimento) {
		this.allegatoRiferimento = allegatoRiferimento;
	}

	public GenericFile getAllegatoRiferimento() {
		return allegatoRiferimento;
	}

	public void setTipo(TipoFile tipo) {
		this.tipo = tipo;
	}

	public TipoFile getTipo() {
		return tipo;
	}

	public void setPosizione(Integer posizione) {
		this.posizione = posizione;
	}

	public Integer getPosizione() {
		return posizione;
	}
	
}
