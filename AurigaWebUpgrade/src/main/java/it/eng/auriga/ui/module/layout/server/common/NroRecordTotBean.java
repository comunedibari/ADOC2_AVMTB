package it.eng.auriga.ui.module.layout.server.common;

import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;


public class NroRecordTotBean {
	
	private AdvancedCriteria criteria;
	private Integer nroRecordTot;
	
	public AdvancedCriteria getCriteria() {
		return criteria;
	}
	public void setCriteria(AdvancedCriteria criteria) {
		this.criteria = criteria;
	}
	public Integer getNroRecordTot() {
		return nroRecordTot;
	}
	public void setNroRecordTot(Integer nroRecordTot) {
		this.nroRecordTot = nroRecordTot;
	}
	
}
