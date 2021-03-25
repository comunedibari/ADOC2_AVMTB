package it.eng.aurigamailbusiness.bean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class TRubricaEmailBean extends AbstractBean implements Serializable {

	@Override
	public String toString() {
		return "TRubricaEmailBean [accountEmail=" + accountEmail + ", descrizioneVoce=" + descrizioneVoce + ", idVoceRubricaEmail=" + idVoceRubricaEmail + "]";
	}

	private static final long serialVersionUID = -7341833444762147789L;

	private String accountEmail;
	private String codAmministrazione;
	private String codAoo;
	private String descrizioneVoce;
	private boolean flgAnn;
	private boolean flgMailingList;
	private boolean flgPecVerificata;
	private boolean flgPresenteInIpa;
	private String idProvSoggRif;
	private String idUteIns;
	private String idUteUltimoAgg;
	private String idVoceRubricaEmail;
	private String tipoAccount;
	private String tipoSoggRif;
	private Date tsIns;
	private Date tsUltimoAgg;
	private String idFruitoreCasella;

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public String getCodAmministrazione() {
		return codAmministrazione;
	}

	public void setCodAmministrazione(String codAmministrazione) {
		this.codAmministrazione = codAmministrazione;
	}

	public String getCodAoo() {
		return codAoo;
	}

	public void setCodAoo(String codAoo) {
		this.codAoo = codAoo;
	}

	public String getDescrizioneVoce() {
		return descrizioneVoce;
	}

	public void setDescrizioneVoce(String descrizioneVoce) {
		this.descrizioneVoce = descrizioneVoce;
	}

	public boolean getFlgAnn() {
		return flgAnn;
	}

	public void setFlgAnn(boolean flgAnn) {
		this.flgAnn = flgAnn;
	}

	public boolean getFlgMailingList() {
		return flgMailingList;
	}

	public void setFlgMailingList(boolean flgMailingList) {
		this.flgMailingList = flgMailingList;
	}

	public boolean getFlgPecVerificata() {
		return flgPecVerificata;
	}

	public void setFlgPecVerificata(boolean flgPecVerificata) {
		this.flgPecVerificata = flgPecVerificata;
	}

	public boolean getFlgPresenteInIpa() {
		return flgPresenteInIpa;
	}

	public void setFlgPresenteInIpa(boolean flgPresenteInIpa) {
		this.flgPresenteInIpa = flgPresenteInIpa;
	}

	public String getIdProvSoggRif() {
		return idProvSoggRif;
	}

	public void setIdProvSoggRif(String idProvSoggRif) {
		this.idProvSoggRif = idProvSoggRif;
	}

	public String getIdUteIns() {
		return idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	public String getIdUteUltimoAgg() {
		return idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	public String getIdVoceRubricaEmail() {
		return idVoceRubricaEmail;
	}

	public void setIdVoceRubricaEmail(String idVoceRubricaEmail) {
		this.idVoceRubricaEmail = idVoceRubricaEmail;
	}

	public String getTipoAccount() {
		return tipoAccount;
	}

	public void setTipoAccount(String tipoAccount) {
		this.tipoAccount = tipoAccount;
	}

	public String getTipoSoggRif() {
		return tipoSoggRif;
	}

	public void setTipoSoggRif(String tipoSoggRif) {
		this.tipoSoggRif = tipoSoggRif;
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public String getIdFruitoreCasella() {
		return idFruitoreCasella;
	}

	public void setIdFruitoreCasella(String idFruitoreCasella) {
		this.idFruitoreCasella = idFruitoreCasella;
	}

}