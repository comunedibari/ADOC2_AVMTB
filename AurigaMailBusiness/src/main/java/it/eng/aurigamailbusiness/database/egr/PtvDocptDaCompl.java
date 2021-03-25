package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PtvDocptDaCompl generated by hbm2java
 */
@Entity
@Table(name = "PTV_DOCPT_DA_COMPL")
public class PtvDocptDaCompl implements java.io.Serializable {

	private PtvDocptDaComplId id;

	public PtvDocptDaCompl() {
	}

	public PtvDocptDaCompl(PtvDocptDaComplId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDoc", column = @Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "codEnte", column = @Column(name = "COD_ENTE", nullable = false, length = 3)),
			@AttributeOverride(name = "tpPtg", column = @Column(name = "TP_PTG", length = 5)),
			@AttributeOverride(name = "annoPtg", column = @Column(name = "ANNO_PTG", precision = 4, scale = 0)),
			@AttributeOverride(name = "numPtg", column = @Column(name = "NUM_PTG", precision = 7, scale = 0)),
			@AttributeOverride(name = "tpRpr", column = @Column(name = "TP_RPR", length = 5)),
			@AttributeOverride(name = "annoRpr", column = @Column(name = "ANNO_RPR", precision = 4, scale = 0)),
			@AttributeOverride(name = "numRpr", column = @Column(name = "NUM_RPR", precision = 7, scale = 0)),
			@AttributeOverride(name = "txtOgg", column = @Column(name = "TXT_OGG", nullable = false, length = 500)),
			@AttributeOverride(name = "dtProt", column = @Column(name = "DT_PROT", length = 7)),
			@AttributeOverride(name = "uoProt", column = @Column(name = "UO_PROT", precision = 8, scale = 0)),
			@AttributeOverride(name = "uteProt", column = @Column(name = "UTE_PROT", precision = 6, scale = 0)),
			@AttributeOverride(name = "dtArrivo", column = @Column(name = "DT_ARRIVO", length = 7)),
			@AttributeOverride(name = "flgCompl", column = @Column(name = "FLG_COMPL", length = 1)),
			@AttributeOverride(name = "flgRsv", column = @Column(name = "FLG_RSV", length = 1)),
			@AttributeOverride(name = "flgTpProt", column = @Column(name = "FLG_TP_PROT", nullable = false, length = 2)),
			@AttributeOverride(name = "nroAlleg", column = @Column(name = "NRO_ALLEG", nullable = false, precision = 3, scale = 0)),
			@AttributeOverride(name = "rifProv", column = @Column(name = "RIF_PROV", length = 30)),
			@AttributeOverride(name = "protProv", column = @Column(name = "PROT_PROV", length = 20)),
			@AttributeOverride(name = "dtProv", column = @Column(name = "DT_PROV", length = 7)),
			@AttributeOverride(name = "flgMezzo", column = @Column(name = "FLG_MEZZO", length = 1)),
			@AttributeOverride(name = "uoInCarico", column = @Column(name = "UO_IN_CARICO", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "postInCarico", column = @Column(name = "POST_IN_CARICO", precision = 8, scale = 0)),
			@AttributeOverride(name = "dtTermPp", column = @Column(name = "DT_TERM_PP", length = 7)) })
	public PtvDocptDaComplId getId() {
		return this.id;
	}

	public void setId(PtvDocptDaComplId id) {
		this.id = id;
	}

}
