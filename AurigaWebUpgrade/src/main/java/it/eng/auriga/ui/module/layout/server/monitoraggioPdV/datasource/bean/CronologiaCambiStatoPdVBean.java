package it.eng.auriga.ui.module.layout.server.monitoraggioPdV.datasource.bean;

import java.util.Date;

public class CronologiaCambiStatoPdVBean {

	private String stato;
	private Date dataAggStato;
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Date getDataAggStato() {
		return dataAggStato;
	}
	public void setDataAggStato(Date dataAggStato) {
		this.dataAggStato = dataAggStato;
	}	
	
}
