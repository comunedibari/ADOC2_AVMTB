package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;


import it.eng.document.NumeroColonna;


public class UoAssociateUtenteXmlBean {

	@NumeroColonna(numero="1")
	private String rowId;
		
	@NumeroColonna(numero="2")
	private String tipoRelazione;
	
	@NumeroColonna(numero="3")
	private String idUo;
	
	@NumeroColonna(numero="6")
	private String dtInizioVld;
	
	@NumeroColonna(numero="7")
	private String dtFineVld;

	@NumeroColonna(numero="8")
	private String idRuolo;

	@NumeroColonna(numero="9")
	private String descrizioneRuolo;
	
    @NumeroColonna(numero="11")
	private String flgRuoloPrimario;
	
	@NumeroColonna(numero="13")
	private String denominazioneScrivaniaUtente;
	
	@NumeroColonna(numero="14")
	private String flgIncluseSottoUo;
	
	@NumeroColonna(numero="15")
	private String flgIncluseScrivanie;
	
	@NumeroColonna(numero="17")
	private String listaUOPuntoProtocolloEscluse;
	
	@NumeroColonna(numero="18")
	private String flgTipoDestDoc;
	
	@NumeroColonna(numero="19")
	private String idUODestDocfasc;
	
	// FIXME Mettere numero colonna
	private String listaUOPuntoProtocolloEreditarietaAbilitata;

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getTipoRelazione() {
		return tipoRelazione;
	}

	public void setTipoRelazione(String tipoRelazione) {
		this.tipoRelazione = tipoRelazione;
	}

	public String getDtInizioVld() {
		return dtInizioVld;
	}

	public void setDtInizioVld(String dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}

	public String getDtFineVld() {
		return dtFineVld;
	}

	public void setDtFineVld(String dtFineVld) {
		this.dtFineVld = dtFineVld;
	}

	public String getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(String idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getFlgRuoloPrimario() {
		return flgRuoloPrimario;
	}

	public void setFlgRuoloPrimario(String flgRuoloPrimario) {
		this.flgRuoloPrimario = flgRuoloPrimario;
	}

	public String getFlgIncluseSottoUo() {
		return flgIncluseSottoUo;
	}

	public void setFlgIncluseSottoUo(String flgIncluseSottoUo) {
		this.flgIncluseSottoUo = flgIncluseSottoUo;
	}

	public String getFlgIncluseScrivanie() {
		return flgIncluseScrivanie;
	}

	public void setFlgIncluseScrivanie(String flgIncluseScrivanie) {
		this.flgIncluseScrivanie = flgIncluseScrivanie;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}

	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}

	public String getDenominazioneScrivaniaUtente() {
		return denominazioneScrivaniaUtente;
	}

	public void setDenominazioneScrivaniaUtente(String denominazioneScrivaniaUtente) {
		this.denominazioneScrivaniaUtente = denominazioneScrivaniaUtente;
	}

	public String getListaUOPuntoProtocolloEscluse() {
		return listaUOPuntoProtocolloEscluse;
	}

	public void setListaUOPuntoProtocolloEscluse(
			String listaUOPuntoProtocolloEscluse) {
		this.listaUOPuntoProtocolloEscluse = listaUOPuntoProtocolloEscluse;
	}
	
	public String getFlgTipoDestDoc() {
		return flgTipoDestDoc;
	}

	public void setFlgTipoDestDoc(String flgTipoDestDoc) {
		this.flgTipoDestDoc = flgTipoDestDoc;
	}

	public String getIdUODestDocfasc() {
		return idUODestDocfasc;
	}

	public void setIdUODestDocfasc(String idUODestDocfasc) {
		this.idUODestDocfasc = idUODestDocfasc;
	}
	
	public String getListaUOPuntoProtocolloEreditarietaAbilitata() {
		return listaUOPuntoProtocolloEreditarietaAbilitata;
	}
	
	public void setListaUOPuntoProtocolloEreditarietaAbilitata(String listaUOPuntoProtocolloEreditarietaAbilitata) {
		this.listaUOPuntoProtocolloEreditarietaAbilitata = listaUOPuntoProtocolloEreditarietaAbilitata;
	}

}
