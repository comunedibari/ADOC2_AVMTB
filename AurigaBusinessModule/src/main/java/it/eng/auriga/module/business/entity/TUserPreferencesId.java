package it.eng.auriga.module.business.entity;

// Generated il giorno 29-gen-2013 14.08.02 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TUserPreferencesId generated by hbm2java
 */
@Embeddable
public class TUserPreferencesId implements java.io.Serializable {

	private String userid;
	private String prefKey;
	private String prefName;

	public TUserPreferencesId() {
	}

	public TUserPreferencesId(String userid, String prefKey, String prefName) {
		this.userid = userid;
		this.prefKey = prefKey;
		this.prefName = prefName;
	}

	@Column(name = "USERID", nullable = false, length = 64)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "PREF_KEY", nullable = false, length = 150)
	public String getPrefKey() {
		return this.prefKey;
	}

	public void setPrefKey(String prefKey) {
		this.prefKey = prefKey;
	}

	@Column(name = "PREF_NAME", nullable = false, length = 150)
	public String getPrefName() {
		return this.prefName;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TUserPreferencesId))
			return false;
		TUserPreferencesId castOther = (TUserPreferencesId) other;

		return ((this.getUserid() == castOther.getUserid()) || (this
				.getUserid() != null && castOther.getUserid() != null && this
				.getUserid().equals(castOther.getUserid())))
				&& ((this.getPrefKey() == castOther.getPrefKey()) || (this
						.getPrefKey() != null && castOther.getPrefKey() != null && this
						.getPrefKey().equals(castOther.getPrefKey())))
				&& ((this.getPrefName() == castOther.getPrefName()) || (this
						.getPrefName() != null
						&& castOther.getPrefName() != null && this
						.getPrefName().equals(castOther.getPrefName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserid() == null ? 0 : this.getUserid().hashCode());
		result = 37 * result
				+ (getPrefKey() == null ? 0 : this.getPrefKey().hashCode());
		result = 37 * result
				+ (getPrefName() == null ? 0 : this.getPrefName().hashCode());
		return result;
	}

}
