package it.eng.auriga.ui.module.layout.server.sub_profili.datasource.bean;

import java.math.BigDecimal;
import it.eng.document.NumeroColonna;																				

public class SubProfiliXmlBean
{
  
  @NumeroColonna(numero="1")
  private BigDecimal idGruppoPriv;
	
  @NumeroColonna(numero="2")
  private String nomeGruppoPriv;
  
  @NumeroColonna(numero="6")
  private String noteGruppoPriv;

public BigDecimal getIdGruppoPriv() {
	return idGruppoPriv;
}

public void setIdGruppoPriv(BigDecimal idGruppoPriv) {
	this.idGruppoPriv = idGruppoPriv;
}

public String getNomeGruppoPriv() {
	return nomeGruppoPriv;
}

public void setNomeGruppoPriv(String nomeGruppoPriv) {
	this.nomeGruppoPriv = nomeGruppoPriv;
}

public String getNoteGruppoPriv() {
	return noteGruppoPriv;
}

public void setNoteGruppoPriv(String noteGruppoPriv) {
	this.noteGruppoPriv = noteGruppoPriv;
}

   
}
