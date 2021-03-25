package it.eng.utility.storageutil.manager.entity;

// Generated 29-mar-2012 15.55.01 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UtilizzatoriStorageH generated by hbm2java
 */
@Entity
@Table(name = "utilizzatori_storage_h")
public class UtilizzatoriStorageH implements java.io.Serializable {

	private UtilizzatoriStorageHId id;
	private Storages storages;

	public UtilizzatoriStorageH() {
	}

	public UtilizzatoriStorageH(UtilizzatoriStorageHId id) {
		this.id = id;
	}

	public UtilizzatoriStorageH(UtilizzatoriStorageHId id, Storages storages) {
		this.id = id;
		this.storages = storages;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idUtilizzatore", column = @Column(name = "id_utilizzatore", nullable = false, length = 150)),
			@AttributeOverride(name = "tsFinoAl", column = @Column(name = "ts_fino_al", nullable = false, length = 19)) })
	public UtilizzatoriStorageHId getId() {
		return this.id;
	}

	public void setId(UtilizzatoriStorageHId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_storage")
	public Storages getStorages() {
		return this.storages;
	}

	public void setStorages(Storages storages) {
		this.storages = storages;
	}

}
