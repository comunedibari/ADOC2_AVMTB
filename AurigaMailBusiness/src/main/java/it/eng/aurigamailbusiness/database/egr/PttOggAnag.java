package it.eng.aurigamailbusiness.database.egr;

// Generated 22-set-2016 12.14.09 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PttOggAnag generated by hbm2java
 */
@Entity
@Table(name = "PTT_OGG_ANAG")
public class PttOggAnag implements java.io.Serializable {

	private PttOggAnagId id;

	public PttOggAnag() {
	}

	public PttOggAnag(PttOggAnagId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idDoc", column = @Column(name = "ID_DOC", precision = 8, scale = 0)),
			@AttributeOverride(name = "numCopia", column = @Column(name = "NUM_COPIA", precision = 4, scale = 0)),
			@AttributeOverride(name = "descrizione", column = @Column(name = "DESCRIZIONE")),
			@AttributeOverride(name = "idUoAssCc", column = @Column(name = "ID_UO_ASS_CC", precision = 8, scale = 0)) })
	public PttOggAnagId getId() {
		return this.id;
	}

	public void setId(PttOggAnagId id) {
		this.id = id;
	}

}