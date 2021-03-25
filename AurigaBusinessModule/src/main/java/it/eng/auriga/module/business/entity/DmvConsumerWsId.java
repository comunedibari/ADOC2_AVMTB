package it.eng.auriga.module.business.entity;
// Generated 20-giu-2018 9.32.53 by Hibernate Tools 3.6.0.Final

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DmvConsumerWsId generated by hbm2java
 */
@Embeddable
public class DmvConsumerWsId implements java.io.Serializable {

	private BigDecimal idSpAoo;
	private String userid;
	private String password;
	private String descrizione;
	private String ciApplicazione;
	private String ciIstanzaApplicazione;
	private String cifratura;
	private String chiaveCifratura;
	private String connToken;
	private BigDecimal idUser;
	private String idUtenteMail;

	public DmvConsumerWsId() {
	}

	public DmvConsumerWsId(BigDecimal idSpAoo, String ciApplicazione, BigDecimal idUser) {
		this.idSpAoo = idSpAoo;
		this.ciApplicazione = ciApplicazione;
		this.idUser = idUser;
	}

	public DmvConsumerWsId(BigDecimal idSpAoo, String userid, String password, String descrizione, String ciApplicazione, String ciIstanzaApplicazione,
			String cifratura, String chiaveCifratura, String connToken, BigDecimal idUser, String idUtenteMail) {
		this.idSpAoo = idSpAoo;
		this.userid = userid;
		this.password = password;
		this.descrizione = descrizione;
		this.ciApplicazione = ciApplicazione;
		this.ciIstanzaApplicazione = ciIstanzaApplicazione;
		this.cifratura = cifratura;
		this.chiaveCifratura = chiaveCifratura;
		this.connToken = connToken;
		this.idUser = idUser;
		this.idUtenteMail = idUtenteMail;
	}

	@Column(name = "ID_SP_AOO", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	@Column(name = "USERID", length = 4000)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "PASSWORD", length = 4000)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "DESCRIZIONE", length = 1000)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "CI_APPLICAZIONE", nullable = false, length = 30)
	public String getCiApplicazione() {
		return this.ciApplicazione;
	}

	public void setCiApplicazione(String ciApplicazione) {
		this.ciApplicazione = ciApplicazione;
	}

	@Column(name = "CI_ISTANZA_APPLICAZIONE", length = 30)
	public String getCiIstanzaApplicazione() {
		return this.ciIstanzaApplicazione;
	}

	public void setCiIstanzaApplicazione(String ciIstanzaApplicazione) {
		this.ciIstanzaApplicazione = ciIstanzaApplicazione;
	}

	@Column(name = "CIFRATURA", length = 4000)
	public String getCifratura() {
		return this.cifratura;
	}

	public void setCifratura(String cifratura) {
		this.cifratura = cifratura;
	}

	@Column(name = "CHIAVE_CIFRATURA", length = 4000)
	public String getChiaveCifratura() {
		return this.chiaveCifratura;
	}

	public void setChiaveCifratura(String chiaveCifratura) {
		this.chiaveCifratura = chiaveCifratura;
	}

	@Column(name = "CONN_TOKEN", length = 4000)
	public String getConnToken() {
		return this.connToken;
	}

	public void setConnToken(String connToken) {
		this.connToken = connToken;
	}

	@Column(name = "ID_USER", nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdUser() {
		return this.idUser;
	}

	public void setIdUser(BigDecimal idUser) {
		this.idUser = idUser;
	}

	@Column(name = "ID_UTENTE_MAIL", length = 64)
	public String getIdUtenteMail() {
		return this.idUtenteMail;
	}

	public void setIdUtenteMail(String idUtenteMail) {
		this.idUtenteMail = idUtenteMail;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DmvConsumerWsId))
			return false;
		DmvConsumerWsId castOther = (DmvConsumerWsId) other;

		return ((this.getIdSpAoo() == castOther.getIdSpAoo())
				|| (this.getIdSpAoo() != null && castOther.getIdSpAoo() != null && this.getIdSpAoo().equals(castOther.getIdSpAoo())))
				&& ((this.getUserid() == castOther.getUserid())
						|| (this.getUserid() != null && castOther.getUserid() != null && this.getUserid().equals(castOther.getUserid())))
				&& ((this.getPassword() == castOther.getPassword())
						|| (this.getPassword() != null && castOther.getPassword() != null && this.getPassword().equals(castOther.getPassword())))
				&& ((this.getDescrizione() == castOther.getDescrizione())
						|| (this.getDescrizione() != null && castOther.getDescrizione() != null && this.getDescrizione().equals(castOther.getDescrizione())))
				&& ((this.getCiApplicazione() == castOther.getCiApplicazione()) || (this.getCiApplicazione() != null && castOther.getCiApplicazione() != null
						&& this.getCiApplicazione().equals(castOther.getCiApplicazione())))
				&& ((this.getCiIstanzaApplicazione() == castOther.getCiIstanzaApplicazione()) || (this.getCiIstanzaApplicazione() != null
						&& castOther.getCiIstanzaApplicazione() != null && this.getCiIstanzaApplicazione().equals(castOther.getCiIstanzaApplicazione())))
				&& ((this.getCifratura() == castOther.getCifratura())
						|| (this.getCifratura() != null && castOther.getCifratura() != null && this.getCifratura().equals(castOther.getCifratura())))
				&& ((this.getChiaveCifratura() == castOther.getChiaveCifratura()) || (this.getChiaveCifratura() != null
						&& castOther.getChiaveCifratura() != null && this.getChiaveCifratura().equals(castOther.getChiaveCifratura())))
				&& ((this.getConnToken() == castOther.getConnToken())
						|| (this.getConnToken() != null && castOther.getConnToken() != null && this.getConnToken().equals(castOther.getConnToken())))
				&& ((this.getIdUser() == castOther.getIdUser())
						|| (this.getIdUser() != null && castOther.getIdUser() != null && this.getIdUser().equals(castOther.getIdUser())))
				&& ((this.getIdUtenteMail() == castOther.getIdUtenteMail()) || (this.getIdUtenteMail() != null && castOther.getIdUtenteMail() != null
						&& this.getIdUtenteMail().equals(castOther.getIdUtenteMail())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdSpAoo() == null ? 0 : this.getIdSpAoo().hashCode());
		result = 37 * result + (getUserid() == null ? 0 : this.getUserid().hashCode());
		result = 37 * result + (getPassword() == null ? 0 : this.getPassword().hashCode());
		result = 37 * result + (getDescrizione() == null ? 0 : this.getDescrizione().hashCode());
		result = 37 * result + (getCiApplicazione() == null ? 0 : this.getCiApplicazione().hashCode());
		result = 37 * result + (getCiIstanzaApplicazione() == null ? 0 : this.getCiIstanzaApplicazione().hashCode());
		result = 37 * result + (getCifratura() == null ? 0 : this.getCifratura().hashCode());
		result = 37 * result + (getChiaveCifratura() == null ? 0 : this.getChiaveCifratura().hashCode());
		result = 37 * result + (getConnToken() == null ? 0 : this.getConnToken().hashCode());
		result = 37 * result + (getIdUser() == null ? 0 : this.getIdUser().hashCode());
		result = 37 * result + (getIdUtenteMail() == null ? 0 : this.getIdUtenteMail().hashCode());
		return result;
	}

}
