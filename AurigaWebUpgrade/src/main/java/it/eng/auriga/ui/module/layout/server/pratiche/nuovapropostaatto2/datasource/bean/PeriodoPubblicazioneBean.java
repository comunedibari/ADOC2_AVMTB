package it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean;

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class PeriodoPubblicazioneBean {

	@NumeroColonna(numero = "1")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioPubblicazione;

	@NumeroColonna(numero = "2")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFinePubblicazione;

	public Date getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}

	public void setDataInizioPubblicazione(Date dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}

	public Date getDataFinePubblicazione() {
		return dataFinePubblicazione;
	}

	public void setDataFinePubblicazione(Date dataFinePubblicazione) {
		this.dataFinePubblicazione = dataFinePubblicazione;
	}
	
}
