package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PtvAssegnIntReg generated by hbm2java
 */
@Entity
@Table(name = "PTV_ASSEGN_INT_REG")
public class PtvAssegnIntReg implements java.io.Serializable {

	private PtvAssegnIntRegId id;

	public PtvAssegnIntReg() {
	}

	public PtvAssegnIntReg(PtvAssegnIntRegId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDoc", column = @Column(name = "ID_DOC", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "nroPosiz", column = @Column(name = "NRO_POSIZ", nullable = false, precision = 4, scale = 0)),
			@AttributeOverride(name = "idUo", column = @Column(name = "ID_UO", precision = 8, scale = 0)),
			@AttributeOverride(name = "idAnag", column = @Column(name = "ID_ANAG", precision = 8, scale = 0)),
			@AttributeOverride(name = "idIndice", column = @Column(name = "ID_INDICE", precision = 8, scale = 0)),
			@AttributeOverride(name = "idFascicolo", column = @Column(name = "ID_FASCICOLO", precision = 8, scale = 0)),
			@AttributeOverride(name = "numSottofasc", column = @Column(name = "NUM_SOTTOFASC", precision = 5, scale = 0)),
			@AttributeOverride(name = "flgCc", column = @Column(name = "FLG_CC", precision = 1, scale = 0)),
			@AttributeOverride(name = "dtIns", column = @Column(name = "DT_INS", length = 7)),
			@AttributeOverride(name = "uteIns", column = @Column(name = "UTE_INS", precision = 6, scale = 0)),
			@AttributeOverride(name = "dtUltMod", column = @Column(name = "DT_ULT_MOD", length = 7)),
			@AttributeOverride(name = "uteUltMod", column = @Column(name = "UTE_ULT_MOD", precision = 6, scale = 0)),
			@AttributeOverride(name = "idTpFis", column = @Column(name = "ID_TP_FIS", precision = 4, scale = 0)),
			@AttributeOverride(name = "desAnag", column = @Column(name = "DES_ANAG", length = 750)),
			@AttributeOverride(name = "flgTpAnag", column = @Column(name = "FLG_TP_ANAG", length = 1)),
			@AttributeOverride(name = "codFis", column = @Column(name = "COD_FIS", length = 16)),
			@AttributeOverride(name = "parIva", column = @Column(name = "PAR_IVA", length = 11)),
			@AttributeOverride(name = "matAnag", column = @Column(name = "MAT_ANAG", precision = 9, scale = 0)),
			@AttributeOverride(name = "dtIniVld", column = @Column(name = "DT_INI_VLD", length = 7)),
			@AttributeOverride(name = "dtFinVld", column = @Column(name = "DT_FIN_VLD", length = 7)),
			@AttributeOverride(name = "email", column = @Column(name = "EMAIL", length = 400)),
			@AttributeOverride(name = "indicepaCodAmm", column = @Column(name = "INDICEPA_COD_AMM", length = 100)),
			@AttributeOverride(name = "indicepaCodAoo", column = @Column(name = "INDICEPA_COD_AOO", length = 100)) })
	public PtvAssegnIntRegId getId() {
		return this.id;
	}

	public void setId(PtvAssegnIntRegId id) {
		this.id = id;
	}

}
