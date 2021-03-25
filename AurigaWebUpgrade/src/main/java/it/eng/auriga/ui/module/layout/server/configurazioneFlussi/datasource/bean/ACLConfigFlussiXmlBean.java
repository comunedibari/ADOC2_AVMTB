package it.eng.auriga.ui.module.layout.server.configurazioneFlussi.datasource.bean;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

@XmlRootElement
public class ACLConfigFlussiXmlBean {

	@NumeroColonna(numero = "1")
	private String tipoDestinatario;
	@NumeroColonna(numero = "2")
	private String idDestinatario;
	@NumeroColonna(numero = "3")
	private String denominazioneDestinatario;	
	@NumeroColonna(numero = "5")	
	private String codiceRapido;		
	@NumeroColonna(numero = "13")	
	private String idUoRuoloAmm;
	@NumeroColonna(numero = "14")	
	private String descrizioneUoRuoloAmm;
	@NumeroColonna(numero = "15")	
	private String codiceUoRuoloAmm;
	@NumeroColonna(numero = "16")
	private String flgUoSubordinateRuoloAmm;	
	@NumeroColonna(numero = "17")	
	private String idRuoloProcUoRuoloAmm;
	@NumeroColonna(numero = "18")	
	private String idRuoloProcSvRuoloAmm;
	@NumeroColonna(numero = "19")
	private String flgVisibilita;
	@NumeroColonna(numero = "20")
	private String flgEsecuzione;
	
	public String getTipoDestinatario() {
		return tipoDestinatario;
	}
	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	public String getIdDestinatario() {
		return idDestinatario;
	}
	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}
	public String getDenominazioneDestinatario() {
		return denominazioneDestinatario;
	}
	public void setDenominazioneDestinatario(String denominazioneDestinatario) {
		this.denominazioneDestinatario = denominazioneDestinatario;
	}
	public String getCodiceRapido() {
		return codiceRapido;
	}
	public void setCodiceRapido(String codiceRapido) {
		this.codiceRapido = codiceRapido;
	}
	public String getIdUoRuoloAmm() {
		return idUoRuoloAmm;
	}
	public void setIdUoRuoloAmm(String idUoRuoloAmm) {
		this.idUoRuoloAmm = idUoRuoloAmm;
	}
	public String getDescrizioneUoRuoloAmm() {
		return descrizioneUoRuoloAmm;
	}
	public void setDescrizioneUoRuoloAmm(String descrizioneUoRuoloAmm) {
		this.descrizioneUoRuoloAmm = descrizioneUoRuoloAmm;
	}
	public String getCodiceUoRuoloAmm() {
		return codiceUoRuoloAmm;
	}
	public void setCodiceUoRuoloAmm(String codiceUoRuoloAmm) {
		this.codiceUoRuoloAmm = codiceUoRuoloAmm;
	}
	public String getFlgUoSubordinateRuoloAmm() {
		return flgUoSubordinateRuoloAmm;
	}
	public void setFlgUoSubordinateRuoloAmm(String flgUoSubordinateRuoloAmm) {
		this.flgUoSubordinateRuoloAmm = flgUoSubordinateRuoloAmm;
	}
	public String getIdRuoloProcUoRuoloAmm() {
		return idRuoloProcUoRuoloAmm;
	}
	public void setIdRuoloProcUoRuoloAmm(String idRuoloProcUoRuoloAmm) {
		this.idRuoloProcUoRuoloAmm = idRuoloProcUoRuoloAmm;
	}	
	public String getIdRuoloProcSvRuoloAmm() {
		return idRuoloProcSvRuoloAmm;
	}
	public void setIdRuoloProcSvRuoloAmm(String idRuoloProcSvRuoloAmm) {
		this.idRuoloProcSvRuoloAmm = idRuoloProcSvRuoloAmm;
	}	
	public String getFlgVisibilita() {
		return flgVisibilita;
	}
	public void setFlgVisibilita(String flgVisibilita) {
		this.flgVisibilita = flgVisibilita;
	}
	public String getFlgEsecuzione() {
		return flgEsecuzione;
	}
	public void setFlgEsecuzione(String flgEsecuzione) {
		this.flgEsecuzione = flgEsecuzione;
	}
	
}
