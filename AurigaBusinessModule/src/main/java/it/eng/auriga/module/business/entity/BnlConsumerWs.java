package it.eng.auriga.module.business.entity;
// Generated 16-giu-2017 10.27.38 by Hibernate Tools 3.5.0.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

/**
 * BnlConsumerWs generated by hbm2java
 */
@Entity
@Immutable
@Table(name = "BNL_CONSUMER_WS")
public class BnlConsumerWs implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8254999889107855294L;
	private BnlConsumerWsId id;

	public BnlConsumerWs() {
	}

	public BnlConsumerWs(BnlConsumerWsId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "idSpAoo", column = @Column(name = "ID_SP_AOO", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "userid", column = @Column(name = "USERID", length = 4000)),
			@AttributeOverride(name = "password", column = @Column(name = "PASSWORD", length = 4000)),
			@AttributeOverride(name = "descrizione", column = @Column(name = "DESCRIZIONE", length = 1000)),
			@AttributeOverride(name = "ciApplicazione", column = @Column(name = "CI_APPLICAZIONE", nullable = false, length = 30)),
			@AttributeOverride(name = "ciIstanzaApplicazione", column = @Column(name = "CI_ISTANZA_APPLICAZIONE", length = 30)),
			@AttributeOverride(name = "cifratura", column = @Column(name = "CIFRATURA", length = 4000)),
			@AttributeOverride(name = "chiaveCifratura", column = @Column(name = "CHIAVE_CIFRATURA", length = 4000)),
			@AttributeOverride(name = "connToken", column = @Column(name = "CONN_TOKEN", length = 4000)),
			@AttributeOverride(name = "nomeSistemaCsEsterno", column = @Column(name = "NOME_SISTEMA_CS_ESTERNO", length = 4000)) })
	public BnlConsumerWsId getId() {
		return this.id;
	}

	public void setId(BnlConsumerWsId id) {
		this.id = id;
	}

}
