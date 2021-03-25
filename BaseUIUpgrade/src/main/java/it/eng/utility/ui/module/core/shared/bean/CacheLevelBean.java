package it.eng.utility.ui.module.core.shared.bean;

import com.smartgwt.client.data.AdvancedCriteria;

public class CacheLevelBean {

	private AdvancedCriteria criteria;	
	private Boolean flgMostraContenuti;
	
	public AdvancedCriteria getCriteria() {
		return criteria;
	}
	public void setCriteria(AdvancedCriteria criteria) {
		this.criteria = criteria;
	}
	public Boolean getFlgMostraContenuti() {
		return flgMostraContenuti;
	}
	public void setFlgMostraContenuti(Boolean flgMostraContenuti) {
		this.flgMostraContenuti = flgMostraContenuti;
	}
	
	
}
