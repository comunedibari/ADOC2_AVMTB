package it.eng.auriga.ui.module.layout.server.gestioneUtenti.bean;

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class UoAssociateUtenteBean {

	@NumeroColonna(numero="1")
	private String rowId;
		
	@NumeroColonna(numero="2")
	private String tipoRelazione;
	
	@NumeroColonna(numero="3")
	private String idUo;
	
	@NumeroColonna(numero="4")
	private String denominazioneUo;
	
	@NumeroColonna(numero="5")
	private String  codRapido;
	
	@NumeroColonna(numero="6")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtInizioVld;
	
	@NumeroColonna(numero="7")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtFineVld;

	@NumeroColonna(numero="8")
	private String idRuolo;
	
    @NumeroColonna(numero="9")
	private String descrizioneRuolo;

    @NumeroColonna(numero="10")
	private Boolean flgRuoloPrimario;
	
	@NumeroColonna(numero="11")
	private String flgRelazioneConScrivania;
	
	@NumeroColonna(numero="12")
	private String idScrivaniaUtente;

	@NumeroColonna(numero="13")
	private String denominazioneScrivaniaUtente;
	
	@NumeroColonna(numero="14")
	private Boolean flgIncluseSottoUo;
	
	@NumeroColonna(numero="15")
	private Boolean flgIncluseScrivanie;

	@NumeroColonna(numero="17")
	private String listaUOPuntoProtocolloEscluse;
	
	@NumeroColonna(numero="18")
	private Boolean flgUoPuntoProtocollo;
	
	// UO che ha in carico i documenti/fascicoli	
	@NumeroColonna(numero="19")
	private Integer flgPresentiDocFasc;
	
	@NumeroColonna(numero="20")
	private String flgTipoDestDoc;
	
	@NumeroColonna(numero="21")
	private String idUODestDocfasc;
	
	@NumeroColonna(numero="22")
	private String livelliUODestDocFasc;
	
	@NumeroColonna(numero="23")
	private String desUODestDocFasc;	
	
    // Situazione dei documenti/fascicoli assegnati alla UO
	@NumeroColonna(numero="24")
	private String statoSpostamentoDocFasc;
	
	@NumeroColonna(numero="25")
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date dataInizioSpostamentoDocFasc;
	
	@NumeroColonna(numero="26")
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date dataFineSpostamentoDocFasc;
	
	@NumeroColonna(numero="27")
	private Integer nrDocAssegnati;
	
	@NumeroColonna(numero="28")
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date dataConteggioDocAssegnati;
	
	@NumeroColonna(numero="29")
	private Integer nrFascAssegnati;
	
	@NumeroColonna(numero="30")
	@TipoData(tipo = Tipo.DATA_ESTESA)
	private Date dataConteggioFascAssegnati;
	
	// FIXME mettere numero colonnna
	private String listaUOPuntoProtocolloEreditarietaAbilitata;
	
	private String descTipoRelazione;
	
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
		
		if (tipoRelazione == null) {
			tipoRelazione = "";
		}
		
		this.tipoRelazione = tipoRelazione;
		
		if (tipoRelazione!=null && !tipoRelazione.equalsIgnoreCase("")){
			if (tipoRelazione.equalsIgnoreCase("A"))
				setDescTipoRelazione("Appartenenza gerarchica");
			else if (tipoRelazione.equalsIgnoreCase("D"))
				setDescTipoRelazione("Funzionale/delega");
			else if (tipoRelazione.equalsIgnoreCase("L"))
				setDescTipoRelazione("Gestione documentazione assegnata/inviata");
			else 
				setDescTipoRelazione("");
		}
		else{
			setDescTipoRelazione("");
		}
		
	}

	
	public String getDenominazioneUo() {
		return denominazioneUo;
	}

	public void setDenominazioneUo(String denominazioneUo) {
		this.denominazioneUo = denominazioneUo;
	}

	
	public String getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(String idRuolo) {
		this.idRuolo = idRuolo;
	}

	public Boolean isFlgRuoloPrimario() {
		return flgRuoloPrimario;
	}

	public void setFlgRuoloPrimario(Boolean flgRuoloPrimario) {
		this.flgRuoloPrimario = flgRuoloPrimario;
	}

	public String getFlgRelazioneConScrivania() {
		return flgRelazioneConScrivania;
	}

	public void setFlgRelazioneConScrivania(String flgRelazioneConScrivania) {
		this.flgRelazioneConScrivania = flgRelazioneConScrivania;
	}

	public String getIdScrivaniaUtente() {
		return idScrivaniaUtente;
	}

	public void setIdScrivaniaUtente(String idScrivaniaUtente) {
		this.idScrivaniaUtente = idScrivaniaUtente;
	}

	public String getDenominazioneScrivaniaUtente() {
		return denominazioneScrivaniaUtente;
	}

	public void setDenominazioneScrivaniaUtente(String denominazioneScrivaniaUtente) {
		this.denominazioneScrivaniaUtente = denominazioneScrivaniaUtente;
	}

	public Boolean isFlgIncluseSottoUo() {
		return flgIncluseSottoUo;
	}

	public void setFlgIncluseSottoUo(Boolean flgIncluseSottoUo) {
		this.flgIncluseSottoUo = flgIncluseSottoUo;
	}

	public Boolean isFlgIncluseScrivanie() {
		return flgIncluseScrivanie;
	}

	public void setFlgIncluseScrivanie(Boolean flgIncluseScrivanie) {
		this.flgIncluseScrivanie = flgIncluseScrivanie;
	}

	public String getDescTipoRelazione() {
		return descTipoRelazione;
	}

	public void setDescTipoRelazione(String descTipoRelazione) {
		this.descTipoRelazione = descTipoRelazione;
	}

	public Date getDtInizioVld() {
		return dtInizioVld;
	}

	public void setDtInizioVld(Date dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}

	public Date getDtFineVld() {
		return dtFineVld;
	}

	public void setDtFineVld(Date dtFineVld) {
		this.dtFineVld = dtFineVld;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}

	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}

	
	public Boolean isFlgUoPuntoProtocollo() {
		return flgUoPuntoProtocollo;
	}

	public void setFlgUoPuntoProtocollo(Boolean flgUoPuntoProtocollo) {
		this.flgUoPuntoProtocollo = flgUoPuntoProtocollo;
	}

	public String getListaUOPuntoProtocolloEscluse() {
		return listaUOPuntoProtocolloEscluse;
	}

	public void setListaUOPuntoProtocolloEscluse(
			String listaUOPuntoProtocolloEscluse) {
		this.listaUOPuntoProtocolloEscluse = listaUOPuntoProtocolloEscluse;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getFlgTipoDestDoc() {
		return flgTipoDestDoc;
	}

	public void setFlgTipoDestDoc(String flgTipoDestDoc) {
		this.flgTipoDestDoc = flgTipoDestDoc;
	}

	
	public String getLivelliUODestDocFasc() {
		return livelliUODestDocFasc;
	}

	public void setLivelliUODestDocFasc(String livelliUODestDocFasc) {
		this.livelliUODestDocFasc = livelliUODestDocFasc;
	}

	public String getDesUODestDocFasc() {
		return desUODestDocFasc;
	}

	public void setDesUODestDocFasc(String desUODestDocFasc) {
		this.desUODestDocFasc = desUODestDocFasc;
	}

	public String getStatoSpostamentoDocFasc() {
		return statoSpostamentoDocFasc;
	}

	public void setStatoSpostamentoDocFasc(String statoSpostamentoDocFasc) {
		this.statoSpostamentoDocFasc = statoSpostamentoDocFasc;
	}

	public Date getDataInizioSpostamentoDocFasc() {
		return dataInizioSpostamentoDocFasc;
	}

	public void setDataInizioSpostamentoDocFasc(Date dataInizioSpostamentoDocFasc) {
		this.dataInizioSpostamentoDocFasc = dataInizioSpostamentoDocFasc;
	}

	public Date getDataFineSpostamentoDocFasc() {
		return dataFineSpostamentoDocFasc;
	}

	public void setDataFineSpostamentoDocFasc(Date dataFineSpostamentoDocFasc) {
		this.dataFineSpostamentoDocFasc = dataFineSpostamentoDocFasc;
	}



	public Date getDataConteggioDocAssegnati() {
		return dataConteggioDocAssegnati;
	}

	public void setDataConteggioDocAssegnati(Date dataConteggioDocAssegnati) {
		this.dataConteggioDocAssegnati = dataConteggioDocAssegnati;
	}

	public Date getDataConteggioFascAssegnati() {
		return dataConteggioFascAssegnati;
	}

	public void setDataConteggioFascAssegnati(Date dataConteggioFascAssegnati) {
		this.dataConteggioFascAssegnati = dataConteggioFascAssegnati;
	}

	public Integer getFlgPresentiDocFasc() {
		return flgPresentiDocFasc;
	}

	public void setFlgPresentiDocFasc(Integer flgPresentiDocFasc) {
		this.flgPresentiDocFasc = flgPresentiDocFasc;
	}

	public String getIdUODestDocfasc() {
		return idUODestDocfasc;
	}

	public void setIdUODestDocfasc(String idUODestDocfasc) {
		this.idUODestDocfasc = idUODestDocfasc;
	}

	public Integer getNrDocAssegnati() {
		return nrDocAssegnati;
	}

	public void setNrDocAssegnati(Integer nrDocAssegnati) {
		this.nrDocAssegnati = nrDocAssegnati;
	}

	public Integer getNrFascAssegnati() {
		return nrFascAssegnati;
	}

	public void setNrFascAssegnati(Integer nrFascAssegnati) {
		this.nrFascAssegnati = nrFascAssegnati;
	}

	public String getListaUOPuntoProtocolloEreditarietaAbilitata() {
		return listaUOPuntoProtocolloEreditarietaAbilitata;
	}

	public void setListaUOPuntoProtocolloEreditarietaAbilitata(String listaUOPuntoProtocolloEreditarietaAbilitata) {
		this.listaUOPuntoProtocolloEreditarietaAbilitata = listaUOPuntoProtocolloEreditarietaAbilitata;
	}
	
}
