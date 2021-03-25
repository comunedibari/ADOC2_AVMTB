package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class Firmatario implements Serializable {

	@NumeroColonna(numero = "1")
	private String commonName;
	@NumeroColonna(numero = "2")
	@TipoData(tipo = Tipo.DATA)
	private Date dataOraFirma;
	@NumeroColonna(numero = "3")
	private Flag firmatoDaAuriga;
	@NumeroColonna(numero = "4")
	@TipoData(tipo = Tipo.DATA)
	private Date dataOraVerificaFirma;
	@NumeroColonna(numero = "5")
	private Flag firmaNonValida;
	@NumeroColonna(numero = "6")
	private String infoFirma;
	@NumeroColonna(numero = "7")
	private String tipoFirma;
	@NumeroColonna(numero = "8")
	private Flag bustaEsterna;

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public Date getDataOraFirma() {
		return dataOraFirma;
	}

	public void setDataOraFirma(Date dataOraFirma) {
		this.dataOraFirma = dataOraFirma;
	}

	public Flag getFirmatoDaAuriga() {
		return firmatoDaAuriga;
	}

	public void setFirmatoDaAuriga(Flag firmatoDaAuriga) {
		this.firmatoDaAuriga = firmatoDaAuriga;
	}

	public Date getDataOraVerificaFirma() {
		return dataOraVerificaFirma;
	}

	public void setDataOraVerificaFirma(Date dataOraVerificaFirma) {
		this.dataOraVerificaFirma = dataOraVerificaFirma;
	}

	public Flag getFirmaNonValida() {
		return firmaNonValida;
	}

	public void setFirmaNonValida(Flag firmaNonValida) {
		this.firmaNonValida = firmaNonValida;
	}

	public String getInfoFirma() {
		return infoFirma;
	}

	public void setInfoFirma(String infoFirma) {
		this.infoFirma = infoFirma;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public Flag getBustaEsterna() {
		return bustaEsterna;
	}

	public void setBustaEsterna(Flag bustaEsterna) {
		this.bustaEsterna = bustaEsterna;
	}

}
