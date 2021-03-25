package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * PttSyncDocVsAuriga generated by hbm2java
 */
@Entity
@Table(name = "PTT_SYNC_DOC_VS_AURIGA", uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID_DOC_EGR", "NUM_ALLEG_EGR", "ID_DOC_AURIGA" }))
public class PttSyncDocVsAuriga implements java.io.Serializable {

	private PttSyncDocVsAurigaId id;
	private PttDocumenti pttDocumenti;

	public PttSyncDocVsAuriga() {
	}

	public PttSyncDocVsAuriga(PttSyncDocVsAurigaId id) {
		this.id = id;
	}

	public PttSyncDocVsAuriga(PttSyncDocVsAurigaId id, PttDocumenti pttDocumenti) {
		this.id = id;
		this.pttDocumenti = pttDocumenti;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDocEgr", column = @Column(name = "ID_DOC_EGR", precision = 22, scale = 0)),
			@AttributeOverride(name = "numAllegEgr", column = @Column(name = "NUM_ALLEG_EGR", precision = 22, scale = 0)),
			@AttributeOverride(name = "idUdAuriga", column = @Column(name = "ID_UD_AURIGA", precision = 22, scale = 0)),
			@AttributeOverride(name = "idDocAuriga", column = @Column(name = "ID_DOC_AURIGA", precision = 22, scale = 0)),
			@AttributeOverride(name = "flgToSynchronize", column = @Column(name = "FLG_TO_SYNCHRONIZE", precision = 1, scale = 0)),
			@AttributeOverride(name = "tsLastAggAuriga", column = @Column(name = "TS_LAST_AGG_AURIGA", length = 7)),
			@AttributeOverride(name = "tsLastErr", column = @Column(name = "TS_LAST_ERR", length = 7)),
			@AttributeOverride(name = "errCode", column = @Column(name = "ERR_CODE", precision = 22, scale = 0)),
			@AttributeOverride(name = "errMsg", column = @Column(name = "ERR_MSG", length = 4000)),
			@AttributeOverride(name = "flgInLavorazione", column = @Column(name = "FLG_IN_LAVORAZIONE", precision = 1, scale = 0)) })
	public PttSyncDocVsAurigaId getId() {
		return this.id;
	}

	public void setId(PttSyncDocVsAurigaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DOC_EGR", insertable = false, updatable = false)
	public PttDocumenti getPttDocumenti() {
		return this.pttDocumenti;
	}

	public void setPttDocumenti(PttDocumenti pttDocumenti) {
		this.pttDocumenti = pttDocumenti;
	}

}
