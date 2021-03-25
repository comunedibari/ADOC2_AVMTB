package it.eng.auriga.ui.module.layout.server.elenchiAlbi.datasource.bean;

import it.eng.document.NumeroColonna;

public class TipoElencoAlboBean {

	@NumeroColonna(numero = "1")
	private String idTipo;
	@NumeroColonna(numero = "2")
	private String nomeTipo;
	@NumeroColonna(numero = "3")
	private String abilInsModDel;
	
	
	public String getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(String idTipo) {
		this.idTipo = idTipo;
	}
	public String getNomeTipo() {
		return nomeTipo;
	}
	public void setNomeTipo(String nomeTipo) {
		this.nomeTipo = nomeTipo;
	}
	public String getAbilInsModDel() {
		return abilInsModDel;
	}
	public void setAbilInsModDel(String abilInsModDel) {
		this.abilInsModDel = abilInsModDel;
	}
	
}
