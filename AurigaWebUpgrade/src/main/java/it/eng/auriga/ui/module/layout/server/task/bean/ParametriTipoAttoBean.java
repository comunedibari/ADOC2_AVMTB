package it.eng.auriga.ui.module.layout.server.task.bean;

import java.util.List;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.UoProtocollanteBean;

public class ParametriTipoAttoBean {

	private List<AttributiCustomCablatiAttoXmlBean> attributiCustomCablati;
	
	private List<UoProtocollanteBean> valoriUfficioProponente;
	
	private Boolean flgAllegAttoParteIntDefaultXTipoAtto;
	private Boolean flgAllegAttoParteIntDefaultOrdPermanente;
	private Boolean flgAllegAttoParteIntDefaultOrdTemporanea;
	
	private Boolean flgAllegAttoPubblSepDefaultXTipoAtto;
	
	public List<AttributiCustomCablatiAttoXmlBean> getAttributiCustomCablati() {
		return attributiCustomCablati;
	}

	public void setAttributiCustomCablati(List<AttributiCustomCablatiAttoXmlBean> attributiCustomCablati) {
		this.attributiCustomCablati = attributiCustomCablati;
	}

	public List<UoProtocollanteBean> getValoriUfficioProponente() {
		return valoriUfficioProponente;
	}

	public void setValoriUfficioProponente(List<UoProtocollanteBean> valoriUfficioProponente) {
		this.valoriUfficioProponente = valoriUfficioProponente;
	}

	public Boolean getFlgAllegAttoParteIntDefaultXTipoAtto() {
		return flgAllegAttoParteIntDefaultXTipoAtto;
	}

	public void setFlgAllegAttoParteIntDefaultXTipoAtto(Boolean flgAllegAttoParteIntDefaultXTipoAtto) {
		this.flgAllegAttoParteIntDefaultXTipoAtto = flgAllegAttoParteIntDefaultXTipoAtto;
	}

	public Boolean getFlgAllegAttoParteIntDefaultOrdPermanente() {
		return flgAllegAttoParteIntDefaultOrdPermanente;
	}

	public void setFlgAllegAttoParteIntDefaultOrdPermanente(Boolean flgAllegAttoParteIntDefaultOrdPermanente) {
		this.flgAllegAttoParteIntDefaultOrdPermanente = flgAllegAttoParteIntDefaultOrdPermanente;
	}

	public Boolean getFlgAllegAttoParteIntDefaultOrdTemporanea() {
		return flgAllegAttoParteIntDefaultOrdTemporanea;
	}

	public void setFlgAllegAttoParteIntDefaultOrdTemporanea(Boolean flgAllegAttoParteIntDefaultOrdTemporanea) {
		this.flgAllegAttoParteIntDefaultOrdTemporanea = flgAllegAttoParteIntDefaultOrdTemporanea;
	}

	public Boolean getFlgAllegAttoPubblSepDefaultXTipoAtto() {
		return flgAllegAttoPubblSepDefaultXTipoAtto;
	}

	public void setFlgAllegAttoPubblSepDefaultXTipoAtto(Boolean flgAllegAttoPubblSepDefaultXTipoAtto) {
		this.flgAllegAttoPubblSepDefaultXTipoAtto = flgAllegAttoPubblSepDefaultXTipoAtto;
	}

}