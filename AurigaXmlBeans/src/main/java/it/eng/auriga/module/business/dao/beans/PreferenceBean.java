package it.eng.auriga.module.business.dao.beans;

import it.eng.core.business.beans.AbstractBean;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean for capturing grid preference
 * 
 * @author Mattia Zanin
 *
 */

@XmlRootElement
public class PreferenceBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 5900110905504295012L;
	
	// 1) USERID della T_USER_PREFERENCES
	// 2) PREF_NAME della T_USER_PREFERENCES
	// 3) SETTING_TIME della T_USER_PREFERENCES nel formato GG/MM/AAAA HH24:MI:SS
	// 4) ID_UO della T_USER_PREFERENCES
	// 5) FLG_VISIB_SOTTOUO della T_USER_PREFERENCES (valori 1/NULL)
	// 6) FLG_GEST_SOTTOUO della T_USER_PREFERENCES (valori 1/NULL)
	// 11) VALUE della T_USER_PREFERENCES
	
	@NumeroColonna(numero = "1")
	private String userid;
	private String prefKey;
	@NumeroColonna(numero = "2")
	private String prefName;
	@NumeroColonna(numero = "11")
	private String value;
	@NumeroColonna(numero = "3")
	@TipoData(tipo = Tipo.DATA)
	private Date settingTime;
	@NumeroColonna(numero = "4")
	private BigDecimal idUo;
	@NumeroColonna(numero = "5")
	private Boolean flgVisibSottoUo;
	@NumeroColonna(numero = "6")
	private Boolean flgGestSottoUo;

	public void setUserid(String userid) {
		this.userid = userid;
		this.getUpdatedProperties().add("userid");
	}

	public String getUserid() {
		return userid;
	}

	public void setPrefKey(String prefKey) {
		this.prefKey = prefKey;
		this.getUpdatedProperties().add("prefKey");
	}

	public String getPrefKey() {
		return prefKey;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
		this.getUpdatedProperties().add("prefName");
	}

	public String getPrefName() {
		return prefName;
	}

	public void setValue(String value) {
		this.value = value;
		this.getUpdatedProperties().add("value");
	}

	public String getValue() {
		return value;
	}

	public void setSettingTime(Date settingTime) {
		this.settingTime = settingTime;
		this.getUpdatedProperties().add("settingTime");
	}

	public Date getSettingTime() {
		return settingTime;
	}

	public void setIdUo(BigDecimal idUo) {
		this.idUo = idUo;
		this.getUpdatedProperties().add("idUo");
	}

	public BigDecimal getIdUo() {
		return idUo;
	}

	public void setFlgVisibSottoUo(Boolean flgVisibSottoUo) {
		this.flgVisibSottoUo = flgVisibSottoUo;
		this.getUpdatedProperties().add("flgVisibSottoUo");
	}

	public Boolean getFlgVisibSottoUo() {
		return flgVisibSottoUo;
	}

	public void setFlgGestSottoUo(Boolean flgGestSottoUo) {
		this.flgGestSottoUo = flgGestSottoUo;
		this.getUpdatedProperties().add("flgGestSottoUo");
	}

	public Boolean getFlgGestSottoUo() {
		return flgGestSottoUo;
	}

}
