package it.eng.auriga.module.business.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_RICHIESTA_PROTOCOLLAZIONE")
public class TRichiestaProtocollazione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private String idRichiesta;	
	private BigDecimal idJob;
	private String stato;
	private String xmlFragment;
	private String jsonRequest;
	private Date tsValidazione;
	private Date tsInizioElaborazione;
	private Date tsFineElaborazione;
	private BigDecimal idUd;
	private Date tsIns;
	private Date tsLastUpd;
	private String numProtocollo;
	private String tsMessaggioErrore;
	private Boolean protocollazioneAvvenuta;
	private String codApplicazione;
	private String operazione;


	public TRichiestaProtocollazione() {
	}
	
	@Id
	@Column(name = "ID_RICHIESTA", nullable = false)
	@GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator"
    )
	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	@Column(name = "ID_JOB", precision = 22, scale = 0)
	public BigDecimal getIdJob() {
		return idJob;
	}

	public void setIdJob(BigDecimal idJob) {
		this.idJob = idJob;
	}

	@Column(name = "STATO", nullable = false, length = 50)
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name = "XML_FRAGMENT", nullable = true)
	public String getXmlFragment() {
		return xmlFragment;
	}

	public void setXmlFragment(String xmlFragment) {
		this.xmlFragment = xmlFragment;
	}

	@Column(name = "JSON_REQUEST")
	public String getJsonRequest() {
		return jsonRequest;
	}

	public void setJsonRequest(String jsonRequest) {
		this.jsonRequest = jsonRequest;
	}

	@Column(name = "TS_INIZIO_ELABORAZIONE")
	public Date getTsInizioElaborazione() {
		return tsInizioElaborazione;
	}

	public void setTsInizioElaborazione(Date tsInizioElaborazione) {
		this.tsInizioElaborazione = tsInizioElaborazione;
	}

	@Column(name = "TS_FINE_ELABORAZIONE")
	public Date getTsFineElaborazione() {
		return tsFineElaborazione;
	}

	public void setTsFineElaborazione(Date tsFineElaborazione) {
		this.tsFineElaborazione = tsFineElaborazione;
	}

	@Column(name = "ID_UD", precision = 22, scale = 0)
	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	@Column(name = "TS_INS", nullable = false)
	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "TS_LAST_UPD", nullable = true)
	public Date getTsLastUpd() {
		return tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	@Column(name = "TS_VALIDAZIONE", nullable = true)
	public Date getTsValidazione() {
		return tsValidazione;
	}

	public void setTsValidazione(Date tsValidazione) {
		this.tsValidazione = tsValidazione;
	}
	
	@Column(name = "NUM_PROTOCOLLO", nullable = true, length = 20)
	public String getNumProtocollo() {
		return numProtocollo;
	}

	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	
	@Column(name = "TS_MESSAGGIO_ERRORE", nullable = true, length = 20)
	public String getTsMessaggioErrore() {
		return tsMessaggioErrore;
	}

	public void setTsMessaggioErrore(String tsMessaggioErrore) {
		this.tsMessaggioErrore = tsMessaggioErrore;
	}

	@Column(name = "PROTOCOLLAZIONE_AVVENUTA", precision = 1, scale = 0)
	public Boolean getProtocollazioneAvvenuta() {
		return protocollazioneAvvenuta;
	}

	public void setProtocollazioneAvvenuta(Boolean protocollazioneAvvenuta) {
		this.protocollazioneAvvenuta = protocollazioneAvvenuta;
	}

	@Column(name = "COD_APPLICAZIONE", nullable = true)
	public String getCodApplicazione() {
		return codApplicazione;
	}

	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}

	@Column(name = "OPERAZIONE", nullable = true)
	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}
	
	
	
}
