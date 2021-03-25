package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PtvFascXScarto generated by hbm2java
 */
@Entity
@Table(name = "PTV_FASC_X_SCARTO")
public class PtvFascXScarto implements java.io.Serializable {

	private PtvFascXScartoId id;

	public PtvFascXScarto() {
	}

	public PtvFascXScarto(PtvFascXScartoId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idFascicolo", column = @Column(name = "ID_FASCICOLO", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "codEnte", column = @Column(name = "COD_ENTE", nullable = false, length = 3)),
			@AttributeOverride(name = "anno", column = @Column(name = "ANNO", nullable = false, precision = 4, scale = 0)),
			@AttributeOverride(name = "idTitolazione", column = @Column(name = "ID_TITOLAZIONE", nullable = false, precision = 25, scale = 0)),
			@AttributeOverride(name = "progrFasc", column = @Column(name = "PROGR_FASC", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "numSottofasc", column = @Column(name = "NUM_SOTTOFASC", precision = 22, scale = 0)),
			@AttributeOverride(name = "txtOgg", column = @Column(name = "TXT_OGG", nullable = false, length = 250)),
			@AttributeOverride(name = "dtApertura", column = @Column(name = "DT_APERTURA", length = 7)),
			@AttributeOverride(name = "dtChiusura", column = @Column(name = "DT_CHIUSURA", length = 7)),
			@AttributeOverride(name = "dtScartoAmm", column = @Column(name = "DT_SCARTO_AMM", length = 7)),
			@AttributeOverride(name = "note", column = @Column(name = "NOTE", length = 75)),
			@AttributeOverride(name = "noteStoria", column = @Column(name = "NOTE_STORIA", length = 250)),
			@AttributeOverride(name = "idProc", column = @Column(name = "ID_PROC", precision = 8, scale = 0)),
			@AttributeOverride(name = "uoInCarico", column = @Column(name = "UO_IN_CARICO", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "flgAcc", column = @Column(name = "FLG_ACC", length = 1)) })
	public PtvFascXScartoId getId() {
		return this.id;
	}

	public void setId(PtvFascXScartoId id) {
		this.id = id;
	}

}
