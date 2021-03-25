package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class DocumentoXmlOutBean implements Serializable {

	private static long serialVersionUID = -483078932590126871L;

	@XmlVariabile(nome = "#IdDocType", tipo = TipoVariabile.SEMPLICE)
	private String tipoDocumento;

	@XmlVariabile(nome = "#NomeDocType", tipo = TipoVariabile.SEMPLICE)
	private String nomeTipoDocumento;

	@XmlVariabile(nome = "#FlgTipoDocConVie", tipo = TipoVariabile.SEMPLICE)
	private Flag flgTipoDocConVie;

	@XmlVariabile(nome = "#FlgOggettoNonObblig", tipo = TipoVariabile.SEMPLICE)
	private Flag flgOggettoNonObblig;

	@XmlVariabile(nome = "#FlgTipoProtModulo", tipo = TipoVariabile.SEMPLICE)
	private String flgTipoProtModulo;

	@XmlVariabile(nome = "RowidDoc", tipo = TipoVariabile.SEMPLICE)
	private String rowidDoc;

	@XmlVariabile(nome = "#IdProcess", tipo = TipoVariabile.SEMPLICE)
	private String idProcess;

	@XmlVariabile(nome = "#EstremiProcess", tipo = TipoVariabile.SEMPLICE)
	private String estremiProcess;
	
	@XmlVariabile(nome = "#RuoloSmistamentoAtto", tipo = TipoVariabile.SEMPLICE)
	private String ruoloSmistamentoAtto;

	@XmlVariabile(nome = "#RegNumPrincipale", tipo = TipoVariabile.NESTED)
	private RegNumPrincipale estremiRegistrazione;
	
	@XmlVariabile(nome = "#EstremiRepertorioPerStruttura", tipo = TipoVariabile.SEMPLICE)
	private String estremiRepertorioPerStruttura;
	
	@XmlVariabile(nome = "#DesOgg", tipo = TipoVariabile.SEMPLICE)
	private String oggetto;

	@XmlVariabile(nome = "#FlgTipoProv", tipo = TipoVariabile.SEMPLICE)
	private TipoProvenienza flgTipoProv;

	@XmlVariabile(nome = "#TsArrivo", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataArrivo;

	@XmlVariabile(nome = "#DtStesura", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataStesura;

	@XmlVariabile(nome = "#IdDocPrimario", tipo = TipoVariabile.SEMPLICE)
	private String idDocPrimario;

	@XmlVariabile(nome = "#FilePrimario", tipo = TipoVariabile.NESTED)
	private FilePrimarioOutBean filePrimario;

	@XmlVariabile(nome = "#EscludiPubblPrimario", tipo = TipoVariabile.SEMPLICE)
	private Flag flgNoPubblPrimario;

	@XmlVariabile(nome = "#DatiSensibiliPrimario", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDatiSensibiliPrimario;

	@XmlVariabile(nome = "PRESSO_CENTRO_POSTA", tipo = TipoVariabile.SEMPLICE)
	private String docPressoCentroPosta;

	@XmlVariabile(nome = "#@MittentiEsterni", tipo = TipoVariabile.LISTA)
	private List<MittentiDocumentoOutBean> mittenti;

	@XmlVariabile(nome = "#@Allegati", tipo = TipoVariabile.LISTA)
	private List<AllegatiOutBean> allegati;

	@XmlVariabile(nome = "#@FileCompletiXAtti", tipo = TipoVariabile.LISTA)
	private List<FileCompletiXAttiBean> fileCompletiXAtti;

	@XmlVariabile(nome = "#@DocProcessFolder", tipo = TipoVariabile.LISTA)
	private List<AllegatiOutBean> documentiProcFolder;

	@XmlVariabile(nome = "#LivRiservatezza", tipo = TipoVariabile.SEMPLICE)
	private String livelloRiservatezza;

	@XmlVariabile(nome = "#DesLivRiservatezza", tipo = TipoVariabile.SEMPLICE)
	private String descrizioneRiservatezza;

	@XmlVariabile(nome = "#NoteUD", tipo = TipoVariabile.SEMPLICE)
	private String note;
	
	@XmlVariabile(nome = "NOTE_MANCANZA_FILE", tipo = TipoVariabile.SEMPLICE)
	private String noteMancanzaFile;
	
	@XmlVariabile(nome = "#LivEvidenza", tipo = TipoVariabile.SEMPLICE)
	private String priorita;

	@XmlVariabile(nome = "#DesLivEvidenza", tipo = TipoVariabile.SEMPLICE)
	private String descrizionePrioritaRiservatezza;

	@XmlVariabile(nome = "#CodMezzoTrasm", tipo = TipoVariabile.SEMPLICE)
	private String messoTrasmissione;

	@XmlVariabile(nome = "#NroRaccomandata", tipo = TipoVariabile.SEMPLICE)
	private String nroRaccomandata;

	@XmlVariabile(nome = "#DtRaccomandata", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtRaccomandata;

	@XmlVariabile(nome = "#NroListaRaccomandata", tipo = TipoVariabile.SEMPLICE)
	private String nroListaRaccomandata;

	@XmlVariabile(nome = "#DtTermineRiservatezza", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date termineRiservatezza;

	@XmlVariabile(nome = "#CollocazioneFisica", tipo = TipoVariabile.NESTED)
	private CollocazioneFisica collocazioneFisica;

	@XmlVariabile(nome = "#RifDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String rifDocRicevuto;

	@XmlVariabile(nome = "#EstremiRegDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String estremiRegDocRicevuto;

	@XmlVariabile(nome = "#AnnoDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	private String annoDocRicevuto;

	@XmlVariabile(nome = "#DtDocRicevuto", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtDocRicevuto;

	@XmlVariabile(nome = "#@DestinatariEsterni", tipo = TipoVariabile.LISTA)
	private List<DestinatariOutBean> destinatari;

	@XmlVariabile(nome = "#RegEmergenza", tipo = TipoVariabile.NESTED)
	private RegEmergenzaOutBean regEmergenza;

	@XmlVariabile(nome = "#RegNumDocPrecedente", tipo = TipoVariabile.NESTED)
	private DocCollegatoOutBean docCollegato;

	@XmlVariabile(nome = "#@ClassFascTitolario", tipo = TipoVariabile.LISTA)
	private List<ClassFascTitolarioOutBean> classifichefascicoli;

	@XmlVariabile(nome = "#@FolderCustom", tipo = TipoVariabile.LISTA)
	private List<FolderCustom> folderCustom;

	@XmlVariabile(nome = "#PresentiAssPreselMitt", tipo = TipoVariabile.SEMPLICE)
	private Boolean flgPresentiAssPreselMitt;

	@XmlVariabile(nome = "#@Assegnatari", tipo = TipoVariabile.LISTA)
	private List<AssegnatariOutBean> assegnatari;

	@XmlVariabile(nome = "#@DestInvioCC", tipo = TipoVariabile.LISTA)
	private List<DestInvioCCOutBean> destInvioCC;

	@XmlVariabile(nome = "#@UOCoinvolte", tipo = TipoVariabile.LISTA)
	private List<DestInvioCCOutBean> altreUoCoinvolte;

	@XmlVariabile(nome = "#Abilitazioni.Rispondi", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRispondi;

	@XmlVariabile(nome = "#Abilitazioni.AvviaIterAnnAtto", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRevocaAtto;
	
	@XmlVariabile(nome = "#Abilitazioni.SmistaPropAtto", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilSmistaPropAtto;
	
	@XmlVariabile(nome = "#Abilitazioni.SottoscrizioneCons", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilSottoscrizioneCons;
	
	@XmlVariabile(nome = "#Abilitazioni.RimuoviSottoscrizioneCons", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRimuoviSottoscrizioneCons;
	
	@XmlVariabile(nome = "#Abilitazioni.PresentazionePropAtto", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilPresentazionePropAtto;
	
	@XmlVariabile(nome = "#Abilitazioni.RitiroPropAtto", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRitiroPropAtto;
	
	@XmlVariabile(nome = "#Abilitazioni.AvviaEmendamento", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilAvviaEmendamento;
	
	@XmlVariabile(nome = "#Abilitazioni.AnnullaPropostaAtto", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilAnnullaPropostaAtto;
	
	@XmlVariabile(nome = "#Abilitazioni.RilascioVisto", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRilascioVisto;
	
	@XmlVariabile(nome = "#Abilitazioni.RifiutoVisto", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRifiutoVisto;
	
	@XmlVariabile(nome = "#Abilitazioni.ProtocollazioneEntrata", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilProtocollazioneEntrata;

	@XmlVariabile(nome = "#Abilitazioni.ProtocollazioneUscita", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilProtocollazioneUscita;

	@XmlVariabile(nome = "#Abilitazioni.ProtocollazioneInterna", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilProtocollazioneInterna;

	@XmlVariabile(nome = "#Abilitazioni.AssegnazioneSmistamento", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilAssegnazioneSmistamento;

	@XmlVariabile(nome = "#Abilitazioni.Condivisione", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilCondivisione;

	@XmlVariabile(nome = "#Abilitazioni.ClassificazioneFascicolazione", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilClassificazioneFascicolazione;

	@XmlVariabile(nome = "#Abilitazioni.ModificaDatiRegistrazione", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilModificaDatiRegistrazione;

	@XmlVariabile(nome = "#Abilitazioni.ModificaDati", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilModificaDati;

	@XmlVariabile(nome = "#Abilitazioni.AvvioIterWF", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilAvvioIterWF;

	@XmlVariabile(nome = "#Abilitazioni.InvioInConservazione", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilInvioInConservazione;

	@XmlVariabile(nome = "#Abilitazioni.AggiuntaFile", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilAggiuntaFile;

	@XmlVariabile(nome = "#Abilitazioni.InvioPEO", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilInvioPEO;

	@XmlVariabile(nome = "#Abilitazioni.InvioPEC", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilInvioPEC;

	@XmlVariabile(nome = "#Abilitazioni.RichAnnullamentoReg", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAnnullamentoReg;

	@XmlVariabile(nome = "#Abilitazioni.ModificaRichAnnullamentoReg", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilModificaRichAnnullamentoReg;

	@XmlVariabile(nome = "#Abilitazioni.EliminaRichAnnullamentoReg", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilEliminaRichAnnullamentoReg;

	@XmlVariabile(nome = "#Abilitazioni.AnnullamentoReg", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilAnnullamentoReg;

	@XmlVariabile(nome = "#Abilitazioni.PresaInCarico", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilPresaInCarico;

	@XmlVariabile(nome = "#Abilitazioni.Restituzione", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRestituzione;

	@XmlVariabile(nome = "#Abilitazioni.InvioConferma", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilInvioConferma;

	@XmlVariabile(nome = "#Abilitazioni.InvioAggiornamento", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilInvioAggiornamento;

	@XmlVariabile(nome = "#Abilitazioni.InvioAnnullamento", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilInvioAnnullamento;

	@XmlVariabile(nome = "#Abilitazioni.Archiviazione", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilArchiviazione;

	@XmlVariabile(nome = "#Abilitazioni.OsservazioniNotifiche", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilOsservazioniNotifiche;
	
	@XmlVariabile(nome = "#Abilitazioni.InviaALibroFirmaVistoRegCont", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilInviaALibroFirmaVistoRegCont;
	
	@XmlVariabile(nome = "#Abilitazioni.TogliaDaLibroFirmaVistoRegCont ", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilTogliaDaLibroFirmaVistoRegCont;
	
	@XmlVariabile(nome = "#Abilitazioni.ImpostaStatoAlVistoRegCont", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilImpostaStatoAlVistoRegCont;

	@XmlVariabile(nome = "#Abilitazioni.Firma", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilFirma;

	@XmlVariabile(nome = "#Abilitazioni.VistoElettronico", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilVistoElettronico;

	@XmlVariabile(nome = "#Abilitazioni.StampaCopertina", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilStampaCopertina;

	@XmlVariabile(nome = "#Abilitazioni.StampaRicevuta", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilStampaRicevuta;

	@XmlVariabile(nome = "#Abilitazioni.StampaEtichetta", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilStampaEtichetta;

	@XmlVariabile(nome = "#Abilitazioni.NuovoComeCopia", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilNuovoComeCopia;

	@XmlVariabile(nome = "#Abilitazioni.ModificaTipologia", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilModificaTipologia;

	@XmlVariabile(nome = "#Abilitazioni.Rispondi.InUscita", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRispondiUscita;
	
	@XmlVariabile(nome = "#Abilitazioni.InvioEmailRicevuta", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilInvioEmailRicevuta;
	
	@XmlVariabile(nome = "#Abilitazioni.Pubblicazione.Proroga", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilProrogaPubblicazione;
	
	@XmlVariabile(nome = "#Abilitazioni.Pubblicazione.Annullamento", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilAnnullamentoPubblicazione;
	
	@XmlVariabile(nome = "#Abilitazioni.Pubblicazione.Rettifica", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRettificaPubblicazione;
	
	@XmlVariabile(nome = "#AnnAtto.IdProcessType", tipo = TipoVariabile.SEMPLICE)
	private String idTipoProcRevocaAtto;

	@XmlVariabile(nome = "#AnnAtto.NomeProcessType", tipo = TipoVariabile.SEMPLICE)
	private String nomeTipoProcRevocaAtto;

	@XmlVariabile(nome = "#AnnAtto.IdDefFlussoWF", tipo = TipoVariabile.SEMPLICE)
	private String idDefFlussoWFRevocaAtto;

	@XmlVariabile(nome = "#AnnAtto.IdDocTypeProposta", tipo = TipoVariabile.SEMPLICE)
	private String idTipoDocPropostaRevocaAtto;

	@XmlVariabile(nome = "#AnnAtto.NomeDocTypeProposta", tipo = TipoVariabile.SEMPLICE)
	private String nomeTipoDocPropostaRevocaAtto;

	@XmlVariabile(nome = "#AnnAtto.SiglaProposta", tipo = TipoVariabile.SEMPLICE)
	private String siglaPropostaRevocaAtto;
	
	@XmlVariabile(nome = "#Emendamento.IdProcessType", tipo = TipoVariabile.SEMPLICE)
	private String idTipoProcEmendamento;

	@XmlVariabile(nome = "#Emendamento.NomeProcessType", tipo = TipoVariabile.SEMPLICE)
	private String nomeTipoProcEmendamento;

	@XmlVariabile(nome = "#Emendamento.IdDefFlussoWF", tipo = TipoVariabile.SEMPLICE)
	private String idDefFlussoWFEmendamento;

	@XmlVariabile(nome = "#Emendamento.IdDocTypeProposta", tipo = TipoVariabile.SEMPLICE)
	private String idTipoDocPropostaEmendamento;

	@XmlVariabile(nome = "#Emendamento.NomeDocTypeProposta", tipo = TipoVariabile.SEMPLICE)
	private String nomeTipoDocPropostaEmendamento;

	@XmlVariabile(nome = "#Emendamento.SiglaProposta", tipo = TipoVariabile.SEMPLICE)
	private String siglaPropostaEmendamento;
	
	@XmlVariabile(nome = "#Emendamento.TipoAttoRif", tipo = TipoVariabile.SEMPLICE)
	private String tipoAttoRifEmendamento;
	
	@XmlVariabile(nome = "#Emendamento.SiglaAttoRif", tipo = TipoVariabile.SEMPLICE)
	private String siglaAttoRifEmendamento;
	
	@XmlVariabile(nome = "#Emendamento.NumeroAttoRif", tipo = TipoVariabile.SEMPLICE)
	private String numeroAttoRifEmendamento;
	
	@XmlVariabile(nome = "#Emendamento.AnnoAttoRif", tipo = TipoVariabile.SEMPLICE)
	private String annoAttoRifEmendamento;
	
	@XmlVariabile(nome = "#Emendamento.IdEmendamentoRif", tipo = TipoVariabile.SEMPLICE)
	private String idEmendamentoRif;	

	@XmlVariabile(nome = "#Emendamento.NumeroEmendamentoRif", tipo = TipoVariabile.SEMPLICE)
	private String numeroEmendamentoRif;	

	@XmlVariabile(nome = "#ConDatiAnnullati", tipo = TipoVariabile.SEMPLICE)
	private Boolean conDatiAnnullati;

	@XmlVariabile(nome = "#ListaDatiAnnullati", tipo = TipoVariabile.SEMPLICE)
	private String listaDatiAnnullati;

	@XmlVariabile(nome = "#EmailProv", tipo = TipoVariabile.NESTED)
	private EmailProvBean emailProv;

	@XmlVariabile(nome = "#IdEmailArrivo", tipo = TipoVariabile.SEMPLICE)
	private String idEmailArrivo;
	
	@XmlVariabile(nome = "#IdUDFromEmail", tipo = TipoVariabile.SEMPLICE)
	private String idUDFromEmail;

	@XmlVariabile(nome = "#Casella.IsPEC", tipo = TipoVariabile.SEMPLICE)
	private Boolean casellaIsPEC;

	@XmlVariabile(nome = "#EmailArrivoInteroperabile", tipo = TipoVariabile.SEMPLICE)
	private Boolean emailArrivoInteroperabile;

	@XmlVariabile(nome = "#InviataPEC", tipo = TipoVariabile.SEMPLICE)
	private Boolean emailInviataFlgPEC;

	@XmlVariabile(nome = "#InviataPEO", tipo = TipoVariabile.SEMPLICE)
	private Boolean emailInviataFlgPEO;

	@XmlVariabile(nome = "#FlgInteroperabilita", tipo = TipoVariabile.SEMPLICE)
	private Boolean flgInteroperabilita;

	@XmlVariabile(nome = "#IdUserCtrlAmmissib", tipo = TipoVariabile.SEMPLICE)
	private String idUserCtrlAmmissib;

	@XmlVariabile(nome = "#ConfermaAssegnazione.IdUser", tipo = TipoVariabile.SEMPLICE)
	private String idUserConfermaAssegnazione;

	@XmlVariabile(nome = "#ConfermaAssegnazione.DesUser", tipo = TipoVariabile.SEMPLICE)
	private String desUserConfermaAssegnazione;

	@XmlVariabile(nome = "#ConfermaAssegnazione.Username", tipo = TipoVariabile.SEMPLICE)
	private String usernameConfermaAssegnazione;

	@XmlVariabile(nome = "#RicEccezioniInterop", tipo = TipoVariabile.SEMPLICE)
	private String ricEccezioniInterop;

	@XmlVariabile(nome = "#RicAggiornamentiInterop", tipo = TipoVariabile.SEMPLICE)
	private String ricAggiornamentiInterop;

	@XmlVariabile(nome = "#RicAnnullamentiInterop", tipo = TipoVariabile.SEMPLICE)
	private String ricAnnullamentiInterop;

	@XmlVariabile(nome = "#PresenzaDocCollegati", tipo = TipoVariabile.SEMPLICE)
	private String presenzaDocCollegati;

	@XmlVariabile(nome = "#EstremiTrasm", tipo = TipoVariabile.SEMPLICE)
	private String estremiTrasm;

	@XmlVariabile(nome = "#CodStatoDett", tipo = TipoVariabile.SEMPLICE)
	private String codStatoDett;

	@XmlVariabile(nome = "#CodStato", tipo = TipoVariabile.SEMPLICE)
	private String codStato;

	@XmlVariabile(nome = "#InviataMailInteroperabile", tipo = TipoVariabile.SEMPLICE)
	private Boolean inviataMailInteroperabile;

	@XmlVariabile(nome = "#RegNumSecondaria.Tipo", tipo = TipoVariabile.SEMPLICE)
	private String tipoRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.Sigla", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.Anno", tipo = TipoVariabile.SEMPLICE)
	private String annoRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.Nro", tipo = TipoVariabile.SEMPLICE)
	private String numeroRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.SubNro", tipo = TipoVariabile.SEMPLICE)
	private String subNroRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.TsRegistrazione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataRegistrazioneNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.IdUDAttoAutAnnullamento", tipo = TipoVariabile.SEMPLICE)
	private String idUdAttoAutAnnRegNumerazioneSecondaria;

	@XmlVariabile(nome = "#RegNumSecondaria.DesGruppo", tipo = TipoVariabile.SEMPLICE)
	private String regNumSecondariaDesGruppo;

	@XmlVariabile(nome = "#IdUOProponente", tipo = TipoVariabile.SEMPLICE)
	private String idUOProponente;

	@XmlVariabile(nome = "#LivelliUOProponente", tipo = TipoVariabile.SEMPLICE)
	private String codUOProponente;

	@XmlVariabile(nome = "#DesUOProponente", tipo = TipoVariabile.SEMPLICE)
	private String desUOProponente;

	@XmlVariabile(nome = "#DesDirProponente", tipo = TipoVariabile.SEMPLICE)
	private String desDirProponente;

	@XmlVariabile(nome = "#IdUserRespProc", tipo = TipoVariabile.SEMPLICE)
	private String idUserRespProc;

	@XmlVariabile(nome = "#DesUserRespProc", tipo = TipoVariabile.SEMPLICE)
	private String desUserRespProc;

	@XmlVariabile(nome = "#DataAvvioIterAtto", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAvvioIterAtto;

	@XmlVariabile(nome = "#FunzionarioIstruttoreAtto", tipo = TipoVariabile.SEMPLICE)
	private String funzionarioIstruttoreAtto;

	@XmlVariabile(nome = "#ResonsabileAtto", tipo = TipoVariabile.SEMPLICE)
	private String responsabileAtto;

	@XmlVariabile(nome = "#DirettoreFirmatarioAtto", tipo = TipoVariabile.SEMPLICE)
	private String direttoreFirmatarioAtto;

	@XmlVariabile(nome = "#DataFirmaAtto", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFirmaAtto;

	@XmlVariabile(nome = "#FunzionarioFirmaAtto", tipo = TipoVariabile.SEMPLICE)
	private String funzionarioFirmaAtto;

	@XmlVariabile(nome = "#DtPubblicazione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblicazioneAtto;

	@XmlVariabile(nome = "#LogIterAtto", tipo = TipoVariabile.SEMPLICE)
	private String logIterAtto;

	@XmlVariabile(nome = "#StatoConservazione", tipo = TipoVariabile.SEMPLICE)
	private String statoConservazione;

	@XmlVariabile(nome = "#TsInvioInConservazione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataInvioInConservazione;

	@XmlVariabile(nome = "#ErrMsgInvioInConservazione", tipo = TipoVariabile.SEMPLICE)
	private String erroreInInvio;

	@XmlVariabile(nome = "#@Contraenti", tipo = TipoVariabile.LISTA)
	private List<ContraentiOutBean> contraenti;

	@XmlVariabile(nome = "#IsCompilazioneModulo", tipo = TipoVariabile.SEMPLICE)
	private Flag flgCompilazioneModulo;

	// Proposta atto
	@XmlVariabile(nome = "#IsPropostaAtto", tipo = TipoVariabile.SEMPLICE)
	private Flag flgPropostaAtto;

	@XmlVariabile(nome = "SceltaIterPropostaDD", tipo = TipoVariabile.SEMPLICE)
	private String sceltaIterPropostaDD;

	@XmlVariabile(nome = "FlgRichParereRevisori", tipo = TipoVariabile.SEMPLICE)
	private String flgRichParereRevisori;

	@XmlVariabile(nome = "SiglaAttoRifSubImpegno", tipo = TipoVariabile.SEMPLICE)
	private String siglaAttoRifSubImpegno;

	@XmlVariabile(nome = "NumeroAttoRifSubImpegno", tipo = TipoVariabile.SEMPLICE)
	private String numeroAttoRifSubImpegno;

	@XmlVariabile(nome = "AnnoAttoRifSubImpegno", tipo = TipoVariabile.SEMPLICE)
	private String annoAttoRifSubImpegno;

	@XmlVariabile(nome = "DataAttoRifSubImpegno", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataAttoRifSubImpegno;

	@XmlVariabile(nome = "#UoRevisioneOrganigramma.Id", tipo = TipoVariabile.SEMPLICE)
	private String idUoRevisioneOrganigramma;

	@XmlVariabile(nome = "#UoRevisioneOrganigramma.IdPadre", tipo = TipoVariabile.SEMPLICE)
	private String idUoPadreRevisioneOrganigramma;

	@XmlVariabile(nome = "#UoRevisioneOrganigramma.CodRapido", tipo = TipoVariabile.SEMPLICE)
	private String codRapidoUoRevisioneOrganigramma;

	@XmlVariabile(nome = "#UoRevisioneOrganigramma.Nome", tipo = TipoVariabile.SEMPLICE)
	private String nomeUoRevisioneOrganigramma;

	@XmlVariabile(nome = "#UoRevisioneOrganigramma.CodTipo", tipo = TipoVariabile.SEMPLICE)
	private String tipoUoRevisioneOrganigramma;

	@XmlVariabile(nome = "#UoRevisioneOrganigramma.LivelloGerarchico", tipo = TipoVariabile.SEMPLICE)
	private String livelloUoRevisioneOrganigramma;

	@XmlVariabile(nome = "#SupportoOriginale", tipo = TipoVariabile.SEMPLICE)
	private String supportoOriginale;

	@XmlVariabile(nome = "#CodSupportoOrig", tipo = TipoVariabile.SEMPLICE)
	private String codSupportoOrig;

	@XmlVariabile(nome = "#FlgOriginaleCartaceo", tipo = TipoVariabile.SEMPLICE)
	private Flag flgOriginaleCartaceo;

	@XmlVariabile(nome = "#FlgCopiaSostitutiva", tipo = TipoVariabile.SEMPLICE)
	private Flag flgCopiaSostitutiva;

	@XmlVariabile(nome = "#@Esibenti", tipo = TipoVariabile.LISTA)
	private List<SoggettoEsternoBean> esibenti;

	@XmlVariabile(nome = "#@Interessati", tipo = TipoVariabile.LISTA)
	private List<SoggettoEsternoBean> interessati;

	@XmlVariabile(nome = "#@AltreUbicazioni", tipo = TipoVariabile.LISTA)
	private List<AltreUbicazioniBean> altreUbicazioni;

	@XmlVariabile(nome = "#@RelazioniVsUD", tipo = TipoVariabile.LISTA)
	private List<DocumentoCollegato> documentiCollegati;

	@XmlVariabile(nome = "#@AltriRiferimenti", tipo = TipoVariabile.LISTA)
	private List<AltriRiferimentiBean> altriRiferimenti;

	@XmlVariabile(nome = "#SezionaleOnlyOne", tipo = TipoVariabile.SEMPLICE)
	private String sezionaleOnlyOne;

	@XmlVariabile(nome = "#NumeroOnlyOne", tipo = TipoVariabile.SEMPLICE)
	private String numeroOnlyOne;

	@XmlVariabile(nome = "#AnnoOnlyOne", tipo = TipoVariabile.SEMPLICE)
	private String annoOnlyOne;

	@XmlVariabile(nome = "FLG_CARICAMENTO_PG_PREGRESSO_DA_GUI", tipo = TipoVariabile.SEMPLICE)
	private Flag flgCaricamentoPGPregressoDaGUI;

	@XmlVariabile(nome = "#Segnatura", tipo = TipoVariabile.SEMPLICE)
	private String segnatura;

	@XmlVariabile(nome = "#@ClientiContribuenti", tipo = TipoVariabile.LISTA)
	private List<SoggettoEsternoBean> listaContribuenti;

	@XmlVariabile(nome = "#AttivaTimbroTipologia", tipo = TipoVariabile.SEMPLICE)
	private String attivaTimbroTipologia;

	@XmlVariabile(nome = "#DefaultAzioneStampaEtichetta", tipo = TipoVariabile.SEMPLICE)
	private String defaultAzioneStampaEtichetta;

	@XmlVariabile(nome = "CodCategoriaRegPrimariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String codCategoriaRegPrimariaRisposta;

	@XmlVariabile(nome = "SiglaRegistroRegPrimariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegistroRegPrimariaRisposta;

	@XmlVariabile(nome = "DesRegistroRegPrimariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String desRegistroRegPrimariaRisposta;

	@XmlVariabile(nome = "CategoriaRepertorioRegPrimariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String categoriaRepertorioRegPrimariaRisposta;

	@XmlVariabile(nome = "CodCategoriaRegSecondariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String codCategoriaRegSecondariaRisposta;

	@XmlVariabile(nome = "SiglaRegistroRegSecondariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegistroRegSecondariaRisposta;

	@XmlVariabile(nome = "DesRegistroRegSecondariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String desRegistroRegSecondariaRisposta;

	@XmlVariabile(nome = "CategoriaRepertorioRegSecondariaRisposta", tipo = TipoVariabile.SEMPLICE)
	private String categoriaRepertorioRegSecondariaRisposta;

	// ***************** Richieste accesso atti *******************//

	@XmlVariabile(nome = "#IdScrivaniaRespIstruttoria", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaRespIstruttoria;

	@XmlVariabile(nome = "#DesScrivaniaRespIstruttoria", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaRespIstruttoria;

	@XmlVariabile(nome = "#LivelliUOScrivaniaRespIstruttoria", tipo = TipoVariabile.SEMPLICE)
	private String codUOScrivaniaRespIstruttoria;

	@XmlVariabile(nome = "#CodStatoRichAccessoAtti", tipo = TipoVariabile.SEMPLICE)
	private String codStatoRichAccessoAtti;

	@XmlVariabile(nome = "#DesStatoRichAccessoAtti", tipo = TipoVariabile.SEMPLICE)
	private String desStatoRichAccessoAtti;

	@XmlVariabile(nome = "#IsRichiestaAccessoAtti", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichiestaAccessoAtti;
	
	@XmlVariabile(nome = "FLG_RICH_ATTI_FABBRICA_VISURA_SUE", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichAttiFabbricaVisuraSue;
	
	@XmlVariabile(nome = "FLG_RICH_MODIFICHE_VISURA_SUE", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichModificheVisuraSue;
	
	@XmlVariabile(nome = "#@AttiRichiesti", tipo = TipoVariabile.LISTA)
	private List<AttiRichiestiXMLBean> attiRichiesti;
	
	@XmlVariabile(nome = "FLG_ALTRI_ATTI_DA_RICERCARE_VISURA_SUE", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAltriAttiDaRicercareVisuraSue;

	@XmlVariabile(nome = "#@Deleganti", tipo = TipoVariabile.LISTA)
	private List<SoggettoEsternoBean> deleganti;

	@XmlVariabile(nome = "#@IncaricatiRitiro", tipo = TipoVariabile.LISTA)
	private List<SoggettoEsternoBean> incaricatiRitiro;

	@XmlVariabile(nome = "#RichAccessoAttiConRichInterno", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichAccessoAttiConRichInterno;

	@XmlVariabile(nome = "MOTIVO_RICHIESTA_ACCESSO_ATTI", tipo = TipoVariabile.SEMPLICE)
	private String motivoRichiestaAccessoAtti;

	@XmlVariabile(nome = "DETTAGLI_RICHIESTA_ACCESSO_ATTI", tipo = TipoVariabile.SEMPLICE)
	private String dettagliRichiestaAccessoAtti;

	@XmlVariabile(nome = "#DataAppuntamento", tipo = TipoVariabile.SEMPLICE)
	private String dataAppuntamento;

	@XmlVariabile(nome = "#OrarioAppuntamento", tipo = TipoVariabile.SEMPLICE)
	private String orarioAppuntamento;

	@XmlVariabile(nome = "#ProvenienzaAppuntamento", tipo = TipoVariabile.SEMPLICE)
	private String provenienzaAppuntamento;

	@XmlVariabile(nome = "#DataPrelievo", tipo = TipoVariabile.SEMPLICE)
	private String dataPrelievo;

	@XmlVariabile(nome = "#DataRestituzionePrelievo", tipo = TipoVariabile.SEMPLICE)
	private String dataRestituzionePrelievo;

	@XmlVariabile(nome = "#RestituzionePrelievoAttestataDa", tipo = TipoVariabile.SEMPLICE)
	private String restituzionePrelievoAttestataDa;

	@XmlVariabile(nome = "#IdNodoDefaultRicercaAtti", tipo = TipoVariabile.SEMPLICE)
	private String idNodoDefaultRicercaAtti;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.VISUALIZZA_DETT_STD_PROT", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiVisualizzaDettStdProt;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.INVIO_IN_APPROVAZIONE", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiInvioInApprovazione;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.APPROVAZIONE", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiApprovazione;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.INVIO_ESITO_VERIFICA_ARCHIVIO", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiInvioEsitoVerificaArchivio;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.ABILITA_APPUNTAMENTO_DA_AGENDA", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiAbilitaAppuntamentoDaAgenda;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.SET_APPUNTAMENTO", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiSetAppuntamento;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.ANNULLAMENTO_APPUNTAMENTO", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiAnnullamentoAppuntamento;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.REGISTRA_PRELIEVO", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiRegistraPrelievo;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.REGISTRA_RESTITUZIONE", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiRegistraRestituzione;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.ANNULLAMENTO", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiAnnullamento;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.STAMPA_FOGLIO_PRELIEVO", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiStampaFoglioPrelievo;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.CHIUSURA", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiChiusura;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.RIAPERTURA", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiRiapertura;

	@XmlVariabile(nome = "#Abilitazioni.RichAccessoAtti.RIPRISTINO", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilRichAccessoAttiRipristino;

	@XmlVariabile(nome = "#IdUDTrasmessaInUscitaCon", tipo = TipoVariabile.SEMPLICE)
	private String idUDTrasmessaInUscitaCon;

	@XmlVariabile(nome = "#EstremiUDTrasmessaInUscitaCon", tipo = TipoVariabile.SEMPLICE)
	private String estremiUDTrasmessaInUscitaCon;
	
	@XmlVariabile(nome = "#IdRicercatoreVisuraSUE", tipo = TipoVariabile.SEMPLICE)
	private String idRicercatoreVisuraSUE;
	
	@XmlVariabile(nome = "#DesRicercatoreVisuraSUE", tipo = TipoVariabile.SEMPLICE)
	private String desRicercatoreVisuraSUE;

	// ***************** Nuova proposta atto 2 *******************//

	@XmlVariabile(nome = "FLG_DET_ANN_REVOCA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDettRevocaAtto;
	
	@XmlVariabile(nome = "COD_PROPOSTA_SIST_CONT", tipo = TipoVariabile.SEMPLICE)
	private String codPropostaSistContabile;

	@XmlVariabile(nome = "#IsDelibera", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDelibera;

	@XmlVariabile(nome = "#ShowDirigentiProponenti", tipo = TipoVariabile.SEMPLICE)
	private String showDirigentiProponenti;

	@XmlVariabile(nome = "#ShowAssessori", tipo = TipoVariabile.SEMPLICE)
	private String showAssessori;

	@XmlVariabile(nome = "#ShowConsiglieri", tipo = TipoVariabile.SEMPLICE)
	private String showConsiglieri;

	@XmlVariabile(nome = "#IdScrivaniaAdottante", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaAdottante;

	@XmlVariabile(nome = "#DesScrivaniaAdottante", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaAdottante;

	@XmlVariabile(nome = "#LivelliUOScrivaniaAdottante", tipo = TipoVariabile.SEMPLICE)
	private String codUOScrivaniaAdottante;

	@XmlVariabile(nome = "#FlgAdottanteAncheRespProc", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAdottanteAncheRespProc;

	@XmlVariabile(nome = "#FlgAdottanteAncheRUP", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAdottanteAncheRUP;
	
	@XmlVariabile(nome = "CDC_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String centroDiCosto;

	@XmlVariabile(nome = "#IdScrivaniaRespProc", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaRespProc;

	@XmlVariabile(nome = "#DesScrivaniaRespProc", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaRespProc;

	@XmlVariabile(nome = "#LivelliUOScrivaniaRespProc", tipo = TipoVariabile.SEMPLICE)
	private String codUOScrivaniaRespProc;

	@XmlVariabile(nome = "#FlgRespProcAncheRUP", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRespProcAncheRUP;

	@XmlVariabile(nome = "#IdScrivaniaRUP", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaRUP;

	@XmlVariabile(nome = "#DesScrivaniaRUP", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaRUP;

	@XmlVariabile(nome = "#LivelliUOScrivaniaRUP", tipo = TipoVariabile.SEMPLICE)
	private String codUOScrivaniaRUP;

	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichiediVistoDirettore;

	@XmlVariabile(nome = "#@RespDiConcerto", tipo = TipoVariabile.LISTA)
	private List<RespDiConcertoBean> responsabiliDiConcerto;

	@XmlVariabile(nome = "#@DirProponenti", tipo = TipoVariabile.LISTA)
	private List<ScrivaniaDirProponenteBean> dirigentiProponenti;

	@XmlVariabile(nome = "#@AssessoriProponenti", tipo = TipoVariabile.LISTA)
	private List<AssessoreProponenteBean> assessoriProponenti;

	@XmlVariabile(nome = "#@ConsiglieriProponenti", tipo = TipoVariabile.LISTA)
	private List<ConsigliereProponenteBean> consiglieriProponenti;

	@XmlVariabile(nome = "#@Estensori", tipo = TipoVariabile.LISTA)
	private List<ScrivaniaEstensoreBean> estensori;

	@XmlVariabile(nome = "#@RespSpesa", tipo = TipoVariabile.LISTA)
	private List<RespSpesaBean> responsabiliSpesa;

	@XmlVariabile(nome = "#IdUOCompSpesa", tipo = TipoVariabile.SEMPLICE)
	private String idUOCompSpesa;

	@XmlVariabile(nome = "#LivelliUOCompSpesa", tipo = TipoVariabile.SEMPLICE)
	private String codUOCompSpesa;

	@XmlVariabile(nome = "#DesUOCompSpesa", tipo = TipoVariabile.SEMPLICE)
	private String desUOCompSpesa;

	@XmlVariabile(nome = "#FlgAdottanteUnicoRespSpesa", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAdottanteUnicoRespSpesa;

	@XmlVariabile(nome = "RIFERIMENTI_NORMATIVI", tipo = TipoVariabile.LISTA)
	private List<ValueBean> riferimentiNormativi;

	@XmlVariabile(nome = "TASK_RESULT_2_FLG_OP_COMMERCIALE", tipo = TipoVariabile.SEMPLICE)
	private String flgOpCommerciale;
	 
	@XmlVariabile(nome = "TASK_RESULT_2_FLG_ESCL_CIG", tipo = TipoVariabile.SEMPLICE)
	private Flag flgEscludiCIG;
	 
	@XmlVariabile(nome = "MOTIVO_ESCLUSIONE_CIG", tipo = TipoVariabile.SEMPLICE)
	private String motivoEsclusioneCIG;
	
	@XmlVariabile(nome = "CIG", tipo = TipoVariabile.LISTA)
	private List<ValueBean> listaCIG;

	@XmlVariabile(nome = "ATTI_PRESUPPOSTI", tipo = TipoVariabile.SEMPLICE)
	private String attiPresupposti;

	@XmlVariabile(nome = "MOTIVAZIONI_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String motivazioniAtto;
	
	@XmlVariabile(nome = "PREMESSA_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String premessaAtto;

	@XmlVariabile(nome = "PREMESSA_ATTO_2", tipo = TipoVariabile.SEMPLICE)
	private String premessaAtto2;

	@XmlVariabile(nome = "DISPOSITIVO_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String dispositivoAtto;

	@XmlVariabile(nome = "LOGHI_DISPOSITIVO_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String loghiDispositivoAtto;

	@XmlVariabile(nome = "DISPOSITIVO_ATTO_2", tipo = TipoVariabile.SEMPLICE)
	private String dispositivoAtto2;

	@XmlVariabile(nome = "FLG_PUBBL_ALLEGATI_SEPARATA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgPubblicaAllegatiSeparati;
	
	@XmlVariabile(nome = "FLG_ATTO_CON_DATI_RISERVATI", tipo = TipoVariabile.SEMPLICE)
	private String flgPrivacy;
	
	@XmlVariabile(nome = "FLG_DATI_PROTETTI_TIPO_1", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDatiProtettiTipo1;
	
	@XmlVariabile(nome = "FLG_DATI_PROTETTI_TIPO_2", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDatiProtettiTipo2;
	
	@XmlVariabile(nome = "FLG_DATI_PROTETTI_TIPO_3", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDatiProtettiTipo3;
	
	@XmlVariabile(nome = "FLG_DATI_PROTETTI_TIPO_4", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDatiProtettiTipo4;
	
	@XmlVariabile(nome = "FLG_PUBBL_ALBO", tipo = TipoVariabile.SEMPLICE)
	private String flgPubblAlbo;
	
	@XmlVariabile(nome = "NRO_GIORNI_PUBBL_ALBO", tipo = TipoVariabile.SEMPLICE)
	private String numGiorniPubblAlbo;

	@XmlVariabile(nome = "TIPO_DECORRENZA_PUBBL_ALBO", tipo = TipoVariabile.SEMPLICE)
	private String tipoDecorrenzaPubblAlbo;
	
	@XmlVariabile(nome = "PUBBL_ALBO_DAL", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblAlboDal;

	@XmlVariabile(nome = "FLG_URGENTE_PUBBL_ALBO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgUrgentePubblAlbo;
	
	@XmlVariabile(nome = "PUBBL_ALBO_ENTRO", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblAlboEntro;
	
	@XmlVariabile(nome = "FLG_PUBBL_IN_TRASPARENZA", tipo = TipoVariabile.SEMPLICE)
	private String flgPubblAmmTrasp;
	
	@XmlVariabile(nome = "SEZ1_PUBBL_IN_TRASPARENZA", tipo = TipoVariabile.SEMPLICE)
	private String sezionePubblAmmTrasp;
	
	@XmlVariabile(nome = "SEZ2_PUBBL_IN_TRASPARENZA", tipo = TipoVariabile.SEMPLICE)
	private String sottoSezionePubblAmmTrasp;
		
	@XmlVariabile(nome = "FLG_PUBBL_BUR", tipo = TipoVariabile.SEMPLICE)
	private String flgPubblBUR;
	
	@XmlVariabile(nome = "ANNO_TERMINE_PUBBL_BUR", tipo = TipoVariabile.SEMPLICE)
	private String annoTerminePubblBUR;
	
	@XmlVariabile(nome = "TIPO_DECORRENZA_PUBBL_BUR", tipo = TipoVariabile.SEMPLICE)
	private String tipoDecorrenzaPubblBUR;
	
	@XmlVariabile(nome = "PUBBL_BUR_DAL", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblBURDal;
	
	@XmlVariabile(nome = "FLG_URGENTE_PUBBL_BUR", tipo = TipoVariabile.SEMPLICE)
	private Flag flgUrgentePubblBUR;
	
	@XmlVariabile(nome = "PUBBL_BUR_ENTRO", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblBUREntro;
	
	@XmlVariabile(nome = "#DataEsecutivita", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtEsecutivita;
	
	@XmlVariabile(nome = "FLG_IMMEDIATAMENTE_ESEGUIBILE", tipo = TipoVariabile.SEMPLICE)
	private Flag flgImmediatamenteEseg;
	
	@XmlVariabile(nome = "MOTIVI_IE", tipo = TipoVariabile.SEMPLICE)
	private String motiviImmediatamenteEseguibile;

	@XmlVariabile(nome = "IND_EMAIL_DEST_NOTIFICA_ATTO", tipo = TipoVariabile.LISTA)
	private List<DestNotificaAttoXmlBean> listaDestNotificaAtto;
	
	@XmlVariabile(nome = "TASK_RESULT_2_DET_CONTR_CON_GARA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetContrConGara;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_AGGIUDICA_GARA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetAggiudicaGara;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_RIMOD_SPESA_GARA_AGGIUD", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetRimodSpesaGaraAggiud;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_PERSONALE", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetPersonale;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_DI_CONCERTO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetDiConcerto;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_CON_SPESA", tipo = TipoVariabile.SEMPLICE)
	private String flgDetConSpesa;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_CON_VRF_BIL_CORR", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichVerificaDiBilancioCorrente;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_CON_VRF_BIL_CCAP", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichVerificaDiBilancioContoCapitale;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_CON_VRF_CONTABIL", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichVerificaDiContabilita;

	@XmlVariabile(nome = "TASK_RESULT_2_RICH_PRESA_VIS_CONTABILITA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichPresaVisContabilita;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_CON_SPESA_CORRENTE", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetConSpesaCorrente;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_CON_IMP_CORR_VALID", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetConImpCorrValid;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_CON_SPESA_CONTO_CAP", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetConSpesaContoCap;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_CON_IMP_CCAP_RIL", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetConImpCCapRil;

	@XmlVariabile(nome = "TASK_RESULT_2_DET_CON_SUB", tipo = TipoVariabile.SEMPLICE)
	private Flag flgSoloSubImpSubCrono;

	@XmlVariabile(nome = "TASK_RESULT_2_RICH_VERIFICA_CONTABILITA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichVerificaContabilita;

	@XmlVariabile(nome = "TASK_RESULT_2_RICH_PARERE_REV_CONTABILI", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichParereRevContabili;
	
	@XmlVariabile(nome = "FLG_DISATTIVA_AUTO_REQUEST_DATI_CONTAB_CORR_AMC", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDisattivaAutoRequestDatiContabiliSIBCorrente;

	@XmlVariabile(nome = "PRENOT_SPESA_DI_CORR", tipo = TipoVariabile.SEMPLICE)
	private String prenotazioneSpesaSIBDiCorrente;

	@XmlVariabile(nome = "MOD_INVIO_CONT_CORR", tipo = TipoVariabile.SEMPLICE)
	private String modalitaInvioDatiSpesaARagioneriaCorrente;

	@XmlVariabile(nome = "DATI_CONTABILI_CORR_AMC", tipo = TipoVariabile.LISTA)
	private List<DatiContabiliXmlBean> listaDatiContabiliSIBCorrente;

	@XmlVariabile(nome = "DATI_CONTABILI_CORR_AUR", tipo = TipoVariabile.LISTA)
	private List<DatiContabiliXmlBean> listaInvioDatiSpesaCorrente;

	@XmlVariabile(nome = "XLS_DATI_CONT_CORR", tipo = TipoVariabile.SEMPLICE)
	private String fileXlsCorrente;

	@XmlVariabile(nome = "NOTE_CONT_CORR", tipo = TipoVariabile.SEMPLICE)
	private String noteCorrente;
	
	@XmlVariabile(nome = "FLG_DISATTIVA_AUTO_REQUEST_DATI_CONTAB_CCAP_AMC", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDisattivaAutoRequestDatiContabiliSIBContoCapitale;

	@XmlVariabile(nome = "MOD_INVIO_CONT_CCAP", tipo = TipoVariabile.SEMPLICE)
	private String modalitaInvioDatiSpesaARagioneriaContoCapitale;

	@XmlVariabile(nome = "DATI_CONTABILI_CCAP_AMC", tipo = TipoVariabile.LISTA)
	private List<DatiContabiliXmlBean> listaDatiContabiliSIBContoCapitale;

	@XmlVariabile(nome = "DATI_CONTABILI_CCAP_AUR", tipo = TipoVariabile.LISTA)
	private List<DatiContabiliXmlBean> listaInvioDatiSpesaContoCapitale;

	@XmlVariabile(nome = "XLS_DATI_CONT_CCAP", tipo = TipoVariabile.SEMPLICE)
	private String fileXlsContoCapitale;

	@XmlVariabile(nome = "NOTE_CONT_CCAP", tipo = TipoVariabile.SEMPLICE)
	private String noteContoCapitale;

	@XmlVariabile(nome = "DATI_SPESA_ANNUALI_X_DET_PERSONALE", tipo = TipoVariabile.LISTA)
	private List<DatiSpesaAnnualiXDetPersonaleXmlBean> listaDatiSpesaAnnualiXDetPersonale;

	@XmlVariabile(nome = "DATI_SPESA_ANNUA_X_DET_PERS_COD_CAPITOLO", tipo = TipoVariabile.SEMPLICE)
	private String capitoloDatiSpesaAnnuaXDetPers;

	@XmlVariabile(nome = "DATI_SPESA_ANNUA_X_DET_PERS_ARTICOLO", tipo = TipoVariabile.SEMPLICE)
	private String articoloDatiSpesaAnnuaXDetPers;

	@XmlVariabile(nome = "DATI_SPESA_ANNUAX_DET_PERS_NRO_VOCE_PEG", tipo = TipoVariabile.SEMPLICE)
	private String numeroDatiSpesaAnnuaXDetPers;

	@XmlVariabile(nome = "DATI_SPESA_ANNUA_X_DET_PERS_IMPORTO", tipo = TipoVariabile.SEMPLICE)
	private String importoDatiSpesaAnnuaXDetPers;

	@XmlVariabile(nome = "OGGETTO_HTML", tipo = TipoVariabile.SEMPLICE)
	private String oggettoHtml;

	@XmlVariabile(nome = "TESTO_PROPOSTA_REVISIONE_ORGANIGRAMMA", tipo = TipoVariabile.SEMPLICE)
	private String testoPropostaRevisioneOrganigramma;

	// ***************** Nuova proposta atto 2 (completa) - INIZIO *******************//
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_TIPO_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String tipoAttoEmendamento;		
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_ATTO_SIGLA_REG", tipo = TipoVariabile.SEMPLICE)
	private String siglaAttoEmendamento;
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_ATTO_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numeroAttoEmendamento;	
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_ATTO_ANNO", tipo = TipoVariabile.SEMPLICE)
	private String annoAttoEmendamento;
	
	@XmlVariabile(nome = "EMENDAMENTO_ID", tipo = TipoVariabile.SEMPLICE)
	private String idEmendamento;		
		
	@XmlVariabile(nome = "EMENDAMENTO_SUB_SU_EM_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numeroEmendamento;		
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_FILE", tipo = TipoVariabile.SEMPLICE)
	private String flgEmendamentoSuFile;	
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_ALLEGATO_NRO", tipo = TipoVariabile.SEMPLICE)
	private String numeroAllegatoEmendamento;	
	
	@XmlVariabile(nome = "EMENDAMENTO_INTEGRALE_ALLEGATO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgEmendamentoIntegraleAllegato;	
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_PAGINA", tipo = TipoVariabile.SEMPLICE)
	private String numeroPaginaEmendamento;
	
	@XmlVariabile(nome = "EMENDAMENTO_SU_RIGA", tipo = TipoVariabile.SEMPLICE)
	private String numeroRigaEmendamento;	
	 
	@XmlVariabile(nome = "EMENDAMENTO_EFFETTO", tipo = TipoVariabile.SEMPLICE)
	private String effettoEmendamento;	
	
	@XmlVariabile(nome = "ATTIVA_SEZIONE_DESTINATARI", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAttivaDestinatari;	
	
	@XmlVariabile(nome = "DESTINATARI_ATTO", tipo = TipoVariabile.LISTA)
	private List<DestinatarioAttoBean> listaDestinatariAtto;
	
	@XmlVariabile(nome = "DESTINATARI_PC_ATTO", tipo = TipoVariabile.LISTA)
	private List<DestinatarioAttoBean> listaDestinatariPCAtto;
	
	@XmlVariabile(nome = "TASK_RESULT_2_INIZIATIVA_PROP_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String iniziativaPropAtto;

	@XmlVariabile(nome = "TASK_RESULT_2_ATTO_MERO_INDIRIZZO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAttoMeroIndirizzo;
	
	@XmlVariabile(nome = "TASK_RESULT_2_MODIFICA_REGOLAMENTO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgModificaRegolamento;
	
	@XmlVariabile(nome = "TASK_RESULT_2_MODIFICA_STATUTO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgModificaStatuto;
	
	@XmlVariabile(nome = "TASK_RESULT_2_DEL_NOMINA", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgNomina;
	
	@XmlVariabile(nome = "TASK_RESULT_2_RATIFICA_DEL_URGENZA", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgRatificaDeliberaUrgenza;
	
	@XmlVariabile(nome = "TASK_RESULT_2_ATTO_URGENTE", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAttoUrgente;
	
	@XmlVariabile(nome = "CIRCOSCRIZIONE_PROPONENTE", tipo = TipoVariabile.LISTA)
	private List<KeyValueBean> circoscrizioniProponenti;
	
	@XmlVariabile(nome = "TASK_RESULT_2_TIPO_INTERPELLANZA", tipo = TipoVariabile.SEMPLICE)
	private String tipoInterpellanza;
	
	@XmlVariabile(nome = "TASK_RESULT_2_TIPO_ORD_MOBILITA", tipo = TipoVariabile.SEMPLICE)
	private String tipoOrdMobilita;
	
	@XmlVariabile(nome = "INIZIO_VLD_ORDINANZA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioVldOrdinanza;
	
	@XmlVariabile(nome = "FINE_VLD_ORDINANZA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFineVldOrdinanza;
	
	@XmlVariabile(nome = "TIPO_LUOGO_ORD_MOBILITA", tipo = TipoVariabile.SEMPLICE)
	private String tipoLuogoOrdMobilita;
	
	@XmlVariabile(nome = "LUOGO_ORD_MOBILITA", tipo = TipoVariabile.SEMPLICE)
	private String luogoOrdMobilita;
	
	@XmlVariabile(nome = "CIRCOSCRIZIONE_ORD_MOBILITA", tipo = TipoVariabile.LISTA)
	private List<KeyValueBean> circoscrizioniOrdMobilita;
	
	@XmlVariabile(nome = "DESCRIZIONE_ORD_MOBILITA", tipo = TipoVariabile.SEMPLICE)
	private String descrizioneOrdMobilita;
		
	@XmlVariabile(nome = "TASK_RESULT_2_DET_RIACCERT", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetRiaccert;

	@XmlVariabile(nome = "TASK_RESULT_2_FLG_CORTE_CONTI", tipo = TipoVariabile.SEMPLICE)
	private Flag flgCorteConti;
	
	@XmlVariabile(nome = "TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG", tipo = TipoVariabile.SEMPLICE)
	private String tipoAttoInDelPeg;

	@XmlVariabile(nome = "TIPO_AFFIDAMENTO", tipo = TipoVariabile.SEMPLICE)
	private String tipoAffidamento;
	
	@XmlVariabile(nome = "MATERIA_NATURA_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String materiaNaturaAtto;
	
	@XmlVariabile(nome = "MATERIA_NATURA_ATTO_Decode", tipo = TipoVariabile.SEMPLICE)
	private String desMateriaNaturaAtto;
	
	@XmlVariabile(nome = "TASK_RESULT_2_FONDI_EUROPEI_PON", tipo = TipoVariabile.SEMPLICE)
	private Flag flgFondiEuropeiPON;
	
	@XmlVariabile(nome = "TASK_RESULT_2_FONDI_PRU", tipo = TipoVariabile.SEMPLICE)
	private Flag flgFondiPRU;
	
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_PAR_117_2013", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoPar117_2013;
	
	@XmlVariabile(nome = "TASK_RESULT_2_NOTIFICA_DA_MESSI", tipo = TipoVariabile.SEMPLICE)
	private Flag flgNotificaDaMessi;
	
	@XmlVariabile(nome = "FLG_LLPP", tipo = TipoVariabile.SEMPLICE)
	private String flgLLPP;
	
	@XmlVariabile(nome = "ANNO_PROGETTO_LLPP", tipo = TipoVariabile.SEMPLICE)
	private String annoProgettoLLPP;	
	
	@XmlVariabile(nome = "NRO_PROGETTO_LLPP", tipo = TipoVariabile.SEMPLICE)
	private String numProgettoLLPP;	
	
	@XmlVariabile(nome = "FLG_BENI_SERVIZI", tipo = TipoVariabile.SEMPLICE)
	private String flgBeniServizi;
	
	@XmlVariabile(nome = "ANNO_PROGETTO_BENI_SERVIZI", tipo = TipoVariabile.SEMPLICE)
	private String annoProgettoBeniServizi;	
	
	@XmlVariabile(nome = "NRO_PROGETTO_BENI_SERVIZI", tipo = TipoVariabile.SEMPLICE)
	private String numProgettoBeniServizi;
	
	@XmlVariabile(nome = "#CdCUOProponente", tipo = TipoVariabile.SEMPLICE)
	private String cdCUOProponente;

	@XmlVariabile(nome = "#FlgVistoDirSupUOProponente", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoDirSupUOProponente;

	@XmlVariabile(nome = "#IdScrivaniaDirRespRegTecnica", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaDirRespRegTecnica;

	@XmlVariabile(nome = "#LivelliUOScrivaniaDirRespRegTecnica", tipo = TipoVariabile.SEMPLICE)
	private String livelliUOScrivaniaDirRespRegTecnica;

	@XmlVariabile(nome = "#DesScrivaniaDirRespRegTecnica", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaDirRespRegTecnica;

	@XmlVariabile(nome = "#FlgDirAncheRespProc", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDirAncheRespProc;

	@XmlVariabile(nome = "#FlgDirAncheRUP", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDirAncheRUP;

	@XmlVariabile(nome = "#@AltriDirRespTecnica", tipo = TipoVariabile.LISTA)
	private List<DirRespRegTecnicaBean> altriDirRespTecnica;

	@XmlVariabile(nome = "#IdAssessoreProponente", tipo = TipoVariabile.SEMPLICE)
	private String idAssessoreProponente;

	@XmlVariabile(nome = "#DesAssessoreProponente", tipo = TipoVariabile.SEMPLICE)
	private String desAssessoreProponente;

	@XmlVariabile(nome = "#@AltriAssessori", tipo = TipoVariabile.LISTA)
	private List<AssessoreProponenteBean> altriAssessori;

	@XmlVariabile(nome = "#IdConsigliereProponente", tipo = TipoVariabile.SEMPLICE)
	private String idConsigliereProponente;

	@XmlVariabile(nome = "#DesConsigliereProponente", tipo = TipoVariabile.SEMPLICE)
	private String desConsigliereProponente;

	@XmlVariabile(nome = "#@AltriConsiglieri", tipo = TipoVariabile.LISTA)
	private List<ConsigliereProponenteBean> altriConsiglieri;
		
	@XmlVariabile(nome = "#IdScrivaniaDirProponente", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaDirProponente;
	
	@XmlVariabile(nome = "#LivelliUOScrivaniaDirProponente", tipo = TipoVariabile.SEMPLICE)
	private String livelliUOScrivaniaDirProponente;
	
	@XmlVariabile(nome = "#DesScrivaniaDirProponente", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaDirProponente;
	
	@XmlVariabile(nome = "#@AltriDirProponenti", tipo = TipoVariabile.LISTA)
	private List<ScrivaniaDirProponenteBean> altriDirProponenti;
	
	@XmlVariabile(nome = "#IdCoordinatoreCompetenteCirc", tipo = TipoVariabile.SEMPLICE)
	private String idCoordinatoreCompCirc;
	
	@XmlVariabile(nome = "#DesCoordinatoreCompetenteCirc", tipo = TipoVariabile.SEMPLICE)
	private String desCoordinatoreCompCirc;

	@XmlVariabile(nome = "#@RespVistiConformita", tipo = TipoVariabile.LISTA)
	private List<RespVistiConformitaBean> respVistiConformita;

	@XmlVariabile(nome = "#IdScrivaniaEstensoreMain", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaEstensoreMain;

	@XmlVariabile(nome = "#LivelliUOScrivaniaEstensoreMain", tipo = TipoVariabile.SEMPLICE)
	private String livelliUOScrivaniaEstensoreMain;

	@XmlVariabile(nome = "#DesScrivaniaEstensoreMain", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaEstensoreMain;

	@XmlVariabile(nome = "#@AltriEstensori", tipo = TipoVariabile.LISTA)
	private List<ScrivaniaEstensoreBean> altriEstensori;

	@XmlVariabile(nome = "#IdScrivaniaIstruttoreMain", tipo = TipoVariabile.SEMPLICE)
	private String idScrivaniaIstruttoreMain;

	@XmlVariabile(nome = "#LivelliUOScrivaniaIstruttoreMain", tipo = TipoVariabile.SEMPLICE)
	private String livelliUOScrivaniaIstruttoreMain;

	@XmlVariabile(nome = "#DesScrivaniaIstruttoreMain", tipo = TipoVariabile.SEMPLICE)
	private String desScrivaniaIstruttoreMain;

	@XmlVariabile(nome = "#@AltriIstruttori", tipo = TipoVariabile.LISTA)
	private List<ScrivaniaEstensoreBean> altriIstruttori;
	
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_1", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoDirSup1;
	
	@XmlVariabile(nome = "TASK_RESULT_2_VISTO_DIR_SUP_2", tipo = TipoVariabile.SEMPLICE)
	private Flag flgVistoDirSup2;
	
	@XmlVariabile(nome = "COD_CIRCOSCRIZIONE_X_PARERE", tipo = TipoVariabile.LISTA)
	private List<KeyValueBean> parereCircoscrizioni;

	@XmlVariabile(nome = "COD_COMMISSIONE_X_PARERE", tipo = TipoVariabile.LISTA)
	private List<KeyValueBean> parereCommissioni;
	
	@XmlVariabile(nome = "#DirigenteResponsabileContabilia", tipo = TipoVariabile.SEMPLICE)	
	private String dirigenteResponsabileContabilia;
	
	@XmlVariabile(nome = "#CentroResponsabilitaContabilia", tipo = TipoVariabile.SEMPLICE)	
	private String centroResponsabilitaContabilia;
	
	@XmlVariabile(nome = "#CentroCostoContabilia", tipo = TipoVariabile.SEMPLICE)	
	private String centroCostoContabilia;
	
	@XmlVariabile(nome = "MOVIMENTO_CONTABILIA", tipo = TipoVariabile.LISTA)
	private List<MovimentiContabiliaXmlBean> listaMovimentiContabilia;
	
	@XmlVariabile(nome = "MOVIMENTO_SICRA", tipo = TipoVariabile.LISTA)
	private List<MovimentiContabiliSICRAXmlBean> listaMovimentiContabiliSICRA;
	
	@XmlVariabile(nome = "FLG_LIQ_CONTESTUALE_IMPEGNO", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgLiqContestualeImpegno;
	
	@XmlVariabile(nome = "FLG_LIQ_CONTESTUALE_ALTRI_ASPETTI_RIL_CONT", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgLiqContestualeAltriAspettiRilCont;
	
	@XmlVariabile(nome = "TASK_RESULT_2_COMP_QUADRO_FIN_RAG_DEC", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgCompQuadroFinRagDec;
	
	@XmlVariabile(nome = "TASK_RESULT_2_OPZ_ASS_COMP_SPESA", tipo = TipoVariabile.SEMPLICE)	
	private String opzAssCompSpesa;
	
	@XmlVariabile(nome = "TASK_RESULT_2_FLG_NUOVI_IMP_ACC", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgNuoviImpAcc;
	
	@XmlVariabile(nome = "TASK_RESULT_2_FLG_IMPEGNI_SU_ANNO_CORRENTE", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgImpSuAnnoCorrente;
	
	@XmlVariabile(nome = "TASK_RESULT_2_INS_MOV_A_RAGIONERIA", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgInsMovARagioneria;
	
	@XmlVariabile(nome = "TASK_RESULT_2_FLG_VANTAGGI_ECONOMICI", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgVantaggiEconomici;
	
	@XmlVariabile(nome = "DEST_VANTAGGIO", tipo = TipoVariabile.LISTA)
	List<DestinatarioVantaggioBean> destinatariVantaggio;
	
	@XmlVariabile(nome = "TASK_RESULT_2_FLG_AFFIDAMENTO", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgAffidamento;
	
	@XmlVariabile(nome = "NORM_RIF_AFFIDAMENTO", tipo = TipoVariabile.SEMPLICE)	
	private String normRifAffidamento;
	
	@XmlVariabile(nome = "RESP_AFFIDAMENTO", tipo = TipoVariabile.SEMPLICE)	
	private String respAffidamento;
	
	@XmlVariabile(nome = "TASK_RESULT_2_OGG_LIQUIDAZIONE", tipo = TipoVariabile.SEMPLICE)	
	private String oggLiquidazione;
	
	@XmlVariabile(nome = "SCADENZA_LIQUIDAZIONE", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScadenzaLiquidazione;
	
	@XmlVariabile(nome = "URGENZA_LIQUIDAZIONE", tipo = TipoVariabile.SEMPLICE)	
	private String urgenzaLiquidazione;
	
	@XmlVariabile(nome = "TASK_RESULT_2_FLG_LIQ_X_UFF_CASSA", tipo = TipoVariabile.SEMPLICE)	
	private Flag flgLiqXUffCassa;
	
	@XmlVariabile(nome = "IMPORTO_ANTICIPO_CASSA", tipo = TipoVariabile.SEMPLICE)
	private String importoAnticipoCassa;

	@XmlVariabile(nome = "DECORRENZA_CONTRATTO", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataDecorrenzaContratto;
	
	@XmlVariabile(nome = "ANNI_DURATA_CONTRATTO", tipo = TipoVariabile.SEMPLICE)
	private String anniDurataContratto;
	
	@XmlVariabile(nome = "TASK_RESULT_2_DET_ACCERT_RADIAZ", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetAccertRadiaz;
	
	@XmlVariabile(nome = "TASK_RESULT_2_DET_VARIAZ_BIL", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDetVariazBil;
	
	@XmlVariabile(nome = "TASK_RESULT_2_DECRETO_REGGIO", tipo = TipoVariabile.SEMPLICE)
	private Flag flgDecretoReggio;
	
	@XmlVariabile(nome = "TASK_RESULT_2_AVVOCATURA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAvvocatura;

	@XmlVariabile(nome = "TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO", tipo = TipoVariabile.SEMPLICE)
	private String flgPubblNotiziario;
	
	@XmlVariabile(nome = "#IdGruppoLiquidatori", tipo = TipoVariabile.SEMPLICE)
	private String idGruppoLiquidatori;
	
	@XmlVariabile(nome = "#NomeGruppoLiquidatori", tipo = TipoVariabile.SEMPLICE)
	private String nomeGruppoLiquidatori;
	
	@XmlVariabile(nome = "#CodGruppoLiquidatori", tipo = TipoVariabile.SEMPLICE)
	private String codGruppoLiquidatori;
	
	@XmlVariabile(nome = "#IdAssegnatarioProcesso", tipo = TipoVariabile.SEMPLICE)
	private String idAssegnatarioProcesso;
	
	@XmlVariabile(nome = "#DesAssegnatarioProcesso", tipo = TipoVariabile.SEMPLICE)
	private String desAssegnatarioProcesso;
	
	@XmlVariabile(nome = "#NriLivelliAssegatarioProcesso", tipo = TipoVariabile.SEMPLICE)
	private String nriLivelliAssegatarioProcesso;
	
	@XmlVariabile(nome = "#IdDocPrimarioLiquidazione", tipo = TipoVariabile.SEMPLICE)
	private String idDocPrimarioLiquidazione;
	
	@XmlVariabile(nome = "#IdUDLiquidazione", tipo = TipoVariabile.SEMPLICE)
	private String idUdLiquidazione;
	
	@XmlVariabile(nome = "#CodTipoLiquidazioneXContabilia", tipo = TipoVariabile.SEMPLICE)
	private String codTipoLiquidazioneXContabilia;

	@XmlVariabile(nome = "#SiglaRegLiquidazione", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegLiquidazione;
	
	@XmlVariabile(nome = "#AnnoRegLiquidazione", tipo = TipoVariabile.SEMPLICE)
	private String annoRegLiquidazione;
	
	@XmlVariabile(nome = "#NroRegLiquidazione", tipo = TipoVariabile.SEMPLICE)
	private String nroRegLiquidazione;
	
	@XmlVariabile(nome = "#DataAdozioneLiquidazione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date dataAdozioneLiquidazione;
	
	@XmlVariabile(nome = "#EstremiLiquidazione", tipo = TipoVariabile.SEMPLICE)
	private String estremiAttoLiquidazione;
	
	@XmlVariabile(nome = "FLG_SCELTA_AGGR_ACTA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgAggregatoACTA;
		
	@XmlVariabile(nome = "FLG_SMIST_ACTA", tipo = TipoVariabile.SEMPLICE)
	private Flag flgSmistamentoACTA;
		
	@XmlVariabile(nome = "OPZ_CLASSIF_AGGR_ACTA", tipo = TipoVariabile.SEMPLICE)
	private String opzioneIndiceClassificazioneFascicoloACTA;
		
	@XmlVariabile(nome = "COD_INDICE_CLASSIF_ACTA", tipo = TipoVariabile.SEMPLICE)
	private String codIndiceClassificazioneACTA;
	
	@XmlVariabile(nome = "ID_INDICE_CLASSIF_ACTA", tipo = TipoVariabile.SEMPLICE)
	private String idClassificazioneACTA;
		
	@XmlVariabile(nome = "COD_VOCE_TITOLARIO_ACTA", tipo = TipoVariabile.SEMPLICE)
	private String codVoceTitolarioACTA;
		
	@XmlVariabile(nome = "NRO_AGGREGATO_ACTA", tipo = TipoVariabile.SEMPLICE)
	private String codFascicoloACTA;
	
	@XmlVariabile(nome = "SUFF_NRO_AGGREGATO_ACTA", tipo = TipoVariabile.SEMPLICE)
	private String suffissoCodFascicoloACTA;
		
	@XmlVariabile(nome = "ID_AGGREGATO_ACTA", tipo = TipoVariabile.SEMPLICE)
	private String idFascicoloACTA;
	
	@XmlVariabile(nome = "ID_NODO_DEST_SMIST_ACTA", tipo = TipoVariabile.SEMPLICE)
	private String idNodoSmistamentoACTA;
	
	@XmlVariabile(nome = "DES_NODO_DEST_SMIST_ACTA", tipo = TipoVariabile.SEMPLICE)
	private String desNodoSmistamentoACTA;	
	
	@XmlVariabile(nome = "CodAOOXSelNodoACTA", tipo = TipoVariabile.SEMPLICE)	
	private String codAOOXSelNodoACTA;
	
	@XmlVariabile(nome = "CodStrutturaXSelNodoACTA", tipo = TipoVariabile.SEMPLICE)	
	private String codStrutturaXSelNodoACTA;
	
	@XmlVariabile(nome = "NroDestinatariSopraSogliaInvioInteropStd", tipo = TipoVariabile.SEMPLICE)	
	private Boolean flgInvioPECMulti;
	
	// ***************** Nuova proposta atto 2 (completa) - FINE *******************//
	
	@XmlVariabile(nome = "PERIZIA_ADSP", tipo = TipoVariabile.LISTA)
	private List<PeriziaXmlBean> listaPerizie;
	
	@XmlVariabile(nome = "#DataInizioPubbl", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioPubbl;
	
	@XmlVariabile(nome = "#GGDurataPubbl", tipo = TipoVariabile.SEMPLICE)
	private String giorniDurataPubbl;
	
	@XmlVariabile(nome = "#UsernameRichPubblAlboPretorio", tipo = TipoVariabile.SEMPLICE)
	private String usernameRichPubblAlboPretorio;
	
	@XmlVariabile(nome = "#TipoDocAlboPretorio", tipo = TipoVariabile.SEMPLICE)
	private String tipoDocAlboPretorio;
	
	@XmlVariabile(nome = "#MittDocAlboPretorio", tipo = TipoVariabile.SEMPLICE)
	private String mittDocAlboPretorio;
	
	@XmlVariabile(nome = "#PubblicazioneAlbo.URIDocPrimario", tipo = TipoVariabile.SEMPLICE)
	private String uriDocPrimarioPubblAlbo;
	
	@XmlVariabile(nome = "#PubblicazioneAlbo.DispalyFilenameDocPrimario", tipo = TipoVariabile.SEMPLICE)
	private String nomeFileDocPrimarioPubblAlbo;
	
	@XmlVariabile(nome = "#PubblicazioneAlbo.URIVRC", tipo = TipoVariabile.SEMPLICE)
	private String uriAllegatoVRCPubblAlbo;
	
	@XmlVariabile(nome = "#PubblicazioneAlbo.DispalyFilenameVRC", tipo = TipoVariabile.SEMPLICE)
	private String nomeFileAllegatoVRCPubblAlbo;
	
	@XmlVariabile(nome = "#PubblicazioneAlbo.FirmatarioVRC", tipo = TipoVariabile.SEMPLICE)
	private String firmatarioVRCPubblAlbo;
	
	// ***************** Albo pretorio BACK OFFICE *******************//
	
	@XmlVariabile(nome = "#RichPubblicazione.Modificabile", tipo = TipoVariabile.SEMPLICE)
	private Boolean abilModificaAlboBK;
	
	@XmlVariabile(nome = "#RichPubblicazione.DtInizioPubbl", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtInizioPubblAlboBK;
		
	@XmlVariabile(nome = "#RichPubblicazione.DtFinePubbl", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtFinePubblAlboBK;
	
	@XmlVariabile(nome = "#RichPubblicazione.NroGGPubbl", tipo = TipoVariabile.SEMPLICE)
	private String nroGgPubblAlboBK;
	
	@XmlVariabile(nome = "#RichPubblicazione.FormaPubblicazione", tipo = TipoVariabile.SEMPLICE)
	private String formaPubblAlboBK;
	
	// ***************** idUo per AlboPretorio di Reggio Calabria *******************//
	
	@XmlVariabile(nome = "#IdUORichPubblXAlbo", tipo = TipoVariabile.SEMPLICE)
	private String idUoAlboReggio;
	
	// ***************** Avvio iter WF di risposta *******************//	
	
	@XmlVariabile(nome = "#IterWFRisposta.IdProcessType", tipo = TipoVariabile.SEMPLICE)
	private String idProcessTypeIterWFRisposta;
	
	@XmlVariabile(nome = "#IterWFRisposta.NomeProcessType", tipo = TipoVariabile.SEMPLICE)
	private String nomeProcessTypeIterWFRisposta;
	
	@XmlVariabile(nome = "#IterWFRisposta.NomeTipoFlussoWF", tipo = TipoVariabile.SEMPLICE)
	private String nomeTipoFlussoIterWFRisposta;
	
	@XmlVariabile(nome = "#IterWFRisposta.IdDocType", tipo = TipoVariabile.SEMPLICE)
	private String idDocTypeIterWFRisposta;
	
	@XmlVariabile(nome = "#IterWFRisposta.NomeDocType", tipo = TipoVariabile.SEMPLICE)
	private String nomeDocTypeIterWFRisposta;
	
	@XmlVariabile(nome = "#IterWFRisposta.CodCategoriaNumerazioneIniziale", tipo = TipoVariabile.SEMPLICE)
	private String codCategoriaNumIniIterWFRisposta;
	
	@XmlVariabile(nome = "#IterWFRisposta.SiglaNumerazioneIniziale", tipo = TipoVariabile.SEMPLICE)
	private String siglaNumIniIterWFRisposta;
	
	// ***************** Pubblicazione e ri-pubblicazione *******************//
	
	@XmlVariabile(nome = "#PresenzaPubblicazioni", tipo = TipoVariabile.SEMPLICE)
	private Boolean flgPresenzaPubblicazioni;
	
	@XmlVariabile(nome = "#Pubblicazione.Id", tipo = TipoVariabile.SEMPLICE)
	private String idPubblicazione;
	
	@XmlVariabile(nome = "#Pubblicazione.Nro", tipo = TipoVariabile.SEMPLICE)
	private String nroPubblicazione;
	
	@XmlVariabile(nome = "#Pubblicazione.Data", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPubblicazione;
	
	@XmlVariabile(nome = "#Pubblicazione.Dal", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataDalPubblicazione;
	
	@XmlVariabile(nome = "#Pubblicazione.Al", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAlPubblicazione;
	
	@XmlVariabile(nome = "#Pubblicazione.RichiestaDa", tipo = TipoVariabile.SEMPLICE)
	private String richiestaDaPubblicazione;
	
	@XmlVariabile(nome = "#Pubblicazione.Stato", tipo = TipoVariabile.SEMPLICE)
	private String statoPubblicazione;
	
	@XmlVariabile(nome = "#Pubblicazione.Forma", tipo = TipoVariabile.SEMPLICE)
	private String formaPubblicazione;
	
	@XmlVariabile(nome = "#Pubblicazione.RettificataDa", tipo = TipoVariabile.SEMPLICE)
	private String rettificataDaPubblicazione;
	
	@XmlVariabile(nome = "#Pubblicazione.MotivoRettifica", tipo = TipoVariabile.SEMPLICE)
	private String motivoRettificaPubblicazione;
	
	@XmlVariabile(nome = "#Pubblicazione.MotivoAnnullamento", tipo = TipoVariabile.SEMPLICE)
	private String motivoAnnullamentoPubblicazione;	
	
	@XmlVariabile(nome = "#Pubblicazione.Proroghe", tipo = TipoVariabile.SEMPLICE)
	private String proroghePubblicazione;
	
	@XmlVariabile(nome = "#Ripubblicazione.Id", tipo = TipoVariabile.SEMPLICE)
	private String idRipubblicazione;
	
	@XmlVariabile(nome = "#Ripubblicazione.Nro", tipo = TipoVariabile.SEMPLICE)
	private String nroRipubblicazione;
	
	@XmlVariabile(nome = "#Ripubblicazione.Data", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataRipubblicazione;
	
	@XmlVariabile(nome = "#Ripubblicazione.Dal", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataDalRipubblicazione;
	
	@XmlVariabile(nome = "#Ripubblicazione.Al", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAlRipubblicazione;
	
	@XmlVariabile(nome = "#Ripubblicazione.RichiestaDa", tipo = TipoVariabile.SEMPLICE)
	private String richiestaDaRipubblicazione;
	
	@XmlVariabile(nome = "#Ripubblicazione.Stato", tipo = TipoVariabile.SEMPLICE)
	private String statoRipubblicazione;
	
	@XmlVariabile(nome = "#Ripubblicazione.Forma", tipo = TipoVariabile.SEMPLICE)
	private String formaRipubblicazione;
	
	@XmlVariabile(nome = "#Ripubblicazione.RettificataDa", tipo = TipoVariabile.SEMPLICE)
	private String rettificataDaRipubblicazione;
	
	@XmlVariabile(nome = "#Ripubblicazione.MotivoRettifica", tipo = TipoVariabile.SEMPLICE)
	private String motivoRettificaRipubblicazione;
	
	@XmlVariabile(nome = "#Ripubblicazione.MotivoAnnullamento", tipo = TipoVariabile.SEMPLICE)
	private String motivoAnnullamentoRipubblicazione;	
	
	@XmlVariabile(nome = "#Ripubblicazione.Proroghe", tipo = TipoVariabile.SEMPLICE)
	private String prorogheRipubblicazione;
	
	@XmlVariabile(nome = "#DataAdozione", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAdozione;
	
	@XmlVariabile(nome = "#RichPubblicazione.Note", tipo = TipoVariabile.SEMPLICE)
	private String notePubblicazione;
	
	// ***************** Getter and Setter *******************//
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNomeTipoDocumento() {
		return nomeTipoDocumento;
	}

	public void setNomeTipoDocumento(String nomeTipoDocumento) {
		this.nomeTipoDocumento = nomeTipoDocumento;
	}

	public Flag getFlgTipoDocConVie() {
		return flgTipoDocConVie;
	}

	public void setFlgTipoDocConVie(Flag flgTipoDocConVie) {
		this.flgTipoDocConVie = flgTipoDocConVie;
	}

	public Flag getFlgOggettoNonObblig() {
		return flgOggettoNonObblig;
	}

	public void setFlgOggettoNonObblig(Flag flgOggettoNonObblig) {
		this.flgOggettoNonObblig = flgOggettoNonObblig;
	}

	public String getFlgTipoProtModulo() {
		return flgTipoProtModulo;
	}

	public void setFlgTipoProtModulo(String flgTipoProtModulo) {
		this.flgTipoProtModulo = flgTipoProtModulo;
	}

	public String getRowidDoc() {
		return rowidDoc;
	}

	public void setRowidDoc(String rowidDoc) {
		this.rowidDoc = rowidDoc;
	}

	public String getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}

	public String getEstremiProcess() {
		return estremiProcess;
	}

	public void setEstremiProcess(String estremiProcess) {
		this.estremiProcess = estremiProcess;
	}
	
	public String getRuoloSmistamentoAtto() {
		return ruoloSmistamentoAtto;
	}

	public void setRuoloSmistamentoAtto(String ruoloSmistamentoAtto) {
		this.ruoloSmistamentoAtto = ruoloSmistamentoAtto;
	}

	public RegNumPrincipale getEstremiRegistrazione() {
		return estremiRegistrazione;
	}

	public void setEstremiRegistrazione(RegNumPrincipale estremiRegistrazione) {
		this.estremiRegistrazione = estremiRegistrazione;
	}

	public String getEstremiRepertorioPerStruttura() {
		return estremiRepertorioPerStruttura;
	}

	public void setEstremiRepertorioPerStruttura(String estremiRepertorioPerStruttura) {
		this.estremiRepertorioPerStruttura = estremiRepertorioPerStruttura;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public TipoProvenienza getFlgTipoProv() {
		return flgTipoProv;
	}

	public void setFlgTipoProv(TipoProvenienza flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}

	public Date getDataArrivo() {
		return dataArrivo;
	}

	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public Date getDataStesura() {
		return dataStesura;
	}

	public void setDataStesura(Date dataStesura) {
		this.dataStesura = dataStesura;
	}

	public String getIdDocPrimario() {
		return idDocPrimario;
	}

	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}

	public FilePrimarioOutBean getFilePrimario() {
		return filePrimario;
	}

	public void setFilePrimario(FilePrimarioOutBean filePrimario) {
		this.filePrimario = filePrimario;
	}

	public Flag getFlgNoPubblPrimario() {
		return flgNoPubblPrimario;
	}

	public void setFlgNoPubblPrimario(Flag flgNoPubblPrimario) {
		this.flgNoPubblPrimario = flgNoPubblPrimario;
	}

	public Flag getFlgDatiSensibiliPrimario() {
		return flgDatiSensibiliPrimario;
	}

	public void setFlgDatiSensibiliPrimario(Flag flgDatiSensibiliPrimario) {
		this.flgDatiSensibiliPrimario = flgDatiSensibiliPrimario;
	}

	public String getDocPressoCentroPosta() {
		return docPressoCentroPosta;
	}

	public void setDocPressoCentroPosta(String docPressoCentroPosta) {
		this.docPressoCentroPosta = docPressoCentroPosta;
	}

	public List<MittentiDocumentoOutBean> getMittenti() {
		return mittenti;
	}

	public void setMittenti(List<MittentiDocumentoOutBean> mittenti) {
		this.mittenti = mittenti;
	}

	public List<AllegatiOutBean> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<AllegatiOutBean> allegati) {
		this.allegati = allegati;
	}
	
	public List<FileCompletiXAttiBean> getFileCompletiXAtti() {
		return fileCompletiXAtti;
	}

	public void setFileCompletiXAtti(List<FileCompletiXAttiBean> fileCompletiXAtti) {
		this.fileCompletiXAtti = fileCompletiXAtti;
	}

	public List<AllegatiOutBean> getDocumentiProcFolder() {
		return documentiProcFolder;
	}

	public void setDocumentiProcFolder(List<AllegatiOutBean> documentiProcFolder) {
		this.documentiProcFolder = documentiProcFolder;
	}

	public String getLivelloRiservatezza() {
		return livelloRiservatezza;
	}

	public void setLivelloRiservatezza(String livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}

	public String getDescrizioneRiservatezza() {
		return descrizioneRiservatezza;
	}

	public void setDescrizioneRiservatezza(String descrizioneRiservatezza) {
		this.descrizioneRiservatezza = descrizioneRiservatezza;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNoteMancanzaFile() {
		return noteMancanzaFile;
	}

	public void setNoteMancanzaFile(String noteMancanzaFile) {
		this.noteMancanzaFile = noteMancanzaFile;
	}

	public String getPriorita() {
		return priorita;
	}

	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}

	public String getDescrizionePrioritaRiservatezza() {
		return descrizionePrioritaRiservatezza;
	}

	public void setDescrizionePrioritaRiservatezza(String descrizionePrioritaRiservatezza) {
		this.descrizionePrioritaRiservatezza = descrizionePrioritaRiservatezza;
	}

	public String getMessoTrasmissione() {
		return messoTrasmissione;
	}

	public void setMessoTrasmissione(String messoTrasmissione) {
		this.messoTrasmissione = messoTrasmissione;
	}

	public String getNroRaccomandata() {
		return nroRaccomandata;
	}

	public void setNroRaccomandata(String nroRaccomandata) {
		this.nroRaccomandata = nroRaccomandata;
	}

	public Date getDtRaccomandata() {
		return dtRaccomandata;
	}

	public void setDtRaccomandata(Date dtRaccomandata) {
		this.dtRaccomandata = dtRaccomandata;
	}

	public String getNroListaRaccomandata() {
		return nroListaRaccomandata;
	}

	public void setNroListaRaccomandata(String nroListaRaccomandata) {
		this.nroListaRaccomandata = nroListaRaccomandata;
	}

	public Date getTermineRiservatezza() {
		return termineRiservatezza;
	}

	public void setTermineRiservatezza(Date termineRiservatezza) {
		this.termineRiservatezza = termineRiservatezza;
	}

	public CollocazioneFisica getCollocazioneFisica() {
		return collocazioneFisica;
	}

	public void setCollocazioneFisica(CollocazioneFisica collocazioneFisica) {
		this.collocazioneFisica = collocazioneFisica;
	}

	public String getRifDocRicevuto() {
		return rifDocRicevuto;
	}

	public void setRifDocRicevuto(String rifDocRicevuto) {
		this.rifDocRicevuto = rifDocRicevuto;
	}

	public String getEstremiRegDocRicevuto() {
		return estremiRegDocRicevuto;
	}

	public void setEstremiRegDocRicevuto(String estremiRegDocRicevuto) {
		this.estremiRegDocRicevuto = estremiRegDocRicevuto;
	}

	public String getAnnoDocRicevuto() {
		return annoDocRicevuto;
	}

	public void setAnnoDocRicevuto(String annoDocRicevuto) {
		this.annoDocRicevuto = annoDocRicevuto;
	}

	public Date getDtDocRicevuto() {
		return dtDocRicevuto;
	}

	public void setDtDocRicevuto(Date dtDocRicevuto) {
		this.dtDocRicevuto = dtDocRicevuto;
	}

	public List<DestinatariOutBean> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<DestinatariOutBean> destinatari) {
		this.destinatari = destinatari;
	}

	public RegEmergenzaOutBean getRegEmergenza() {
		return regEmergenza;
	}

	public void setRegEmergenza(RegEmergenzaOutBean regEmergenza) {
		this.regEmergenza = regEmergenza;
	}

	public DocCollegatoOutBean getDocCollegato() {
		return docCollegato;
	}

	public void setDocCollegato(DocCollegatoOutBean docCollegato) {
		this.docCollegato = docCollegato;
	}

	public List<ClassFascTitolarioOutBean> getClassifichefascicoli() {
		return classifichefascicoli;
	}

	public void setClassifichefascicoli(List<ClassFascTitolarioOutBean> classifichefascicoli) {
		this.classifichefascicoli = classifichefascicoli;
	}

	public List<FolderCustom> getFolderCustom() {
		return folderCustom;
	}

	public void setFolderCustom(List<FolderCustom> folderCustom) {
		this.folderCustom = folderCustom;
	}

	public Boolean getFlgPresentiAssPreselMitt() {
		return flgPresentiAssPreselMitt;
	}

	public void setFlgPresentiAssPreselMitt(Boolean flgPresentiAssPreselMitt) {
		this.flgPresentiAssPreselMitt = flgPresentiAssPreselMitt;
	}

	public List<AssegnatariOutBean> getAssegnatari() {
		return assegnatari;
	}

	public void setAssegnatari(List<AssegnatariOutBean> assegnatari) {
		this.assegnatari = assegnatari;
	}

	public List<DestInvioCCOutBean> getDestInvioCC() {
		return destInvioCC;
	}

	public void setDestInvioCC(List<DestInvioCCOutBean> destInvioCC) {
		this.destInvioCC = destInvioCC;
	}

	public List<DestInvioCCOutBean> getAltreUoCoinvolte() {
		return altreUoCoinvolte;
	}

	public void setAltreUoCoinvolte(List<DestInvioCCOutBean> altreUoCoinvolte) {
		this.altreUoCoinvolte = altreUoCoinvolte;
	}

	public Boolean getAbilRispondi() {
		return abilRispondi;
	}

	public void setAbilRispondi(Boolean abilRispondi) {
		this.abilRispondi = abilRispondi;
	}

	public Boolean getAbilRevocaAtto() {
		return abilRevocaAtto;
	}

	public void setAbilRevocaAtto(Boolean abilRevocaAtto) {
		this.abilRevocaAtto = abilRevocaAtto;
	}

	public Boolean getAbilSmistaPropAtto() {
		return abilSmistaPropAtto;
	}

	public void setAbilSmistaPropAtto(Boolean abilSmistaPropAtto) {
		this.abilSmistaPropAtto = abilSmistaPropAtto;
	}

	public Boolean getAbilSottoscrizioneCons() {
		return abilSottoscrizioneCons;
	}

	public void setAbilSottoscrizioneCons(Boolean abilSottoscrizioneCons) {
		this.abilSottoscrizioneCons = abilSottoscrizioneCons;
	}

	public Boolean getAbilRimuoviSottoscrizioneCons() {
		return abilRimuoviSottoscrizioneCons;
	}

	public void setAbilRimuoviSottoscrizioneCons(Boolean abilRimuoviSottoscrizioneCons) {
		this.abilRimuoviSottoscrizioneCons = abilRimuoviSottoscrizioneCons;
	}

	public Boolean getAbilPresentazionePropAtto() {
		return abilPresentazionePropAtto;
	}

	public void setAbilPresentazionePropAtto(Boolean abilPresentazionePropAtto) {
		this.abilPresentazionePropAtto = abilPresentazionePropAtto;
	}

	public Boolean getAbilRitiroPropAtto() {
		return abilRitiroPropAtto;
	}

	public void setAbilRitiroPropAtto(Boolean abilRitiroPropAtto) {
		this.abilRitiroPropAtto = abilRitiroPropAtto;
	}

	public Boolean getAbilAvviaEmendamento() {
		return abilAvviaEmendamento;
	}

	public void setAbilAvviaEmendamento(Boolean abilAvviaEmendamento) {
		this.abilAvviaEmendamento = abilAvviaEmendamento;
	}

	public Boolean getAbilAnnullaPropostaAtto() {
		return abilAnnullaPropostaAtto;
	}

	public void setAbilAnnullaPropostaAtto(Boolean abilAnnullaPropostaAtto) {
		this.abilAnnullaPropostaAtto = abilAnnullaPropostaAtto;
	}

	public Boolean getAbilRilascioVisto() {
		return abilRilascioVisto;
	}

	public void setAbilRilascioVisto(Boolean abilRilascioVisto) {
		this.abilRilascioVisto = abilRilascioVisto;
	}

	public Boolean getAbilRifiutoVisto() {
		return abilRifiutoVisto;
	}

	public void setAbilRifiutoVisto(Boolean abilRifiutoVisto) {
		this.abilRifiutoVisto = abilRifiutoVisto;
	}

	public Boolean getAbilProtocollazioneEntrata() {
		return abilProtocollazioneEntrata;
	}

	public void setAbilProtocollazioneEntrata(Boolean abilProtocollazioneEntrata) {
		this.abilProtocollazioneEntrata = abilProtocollazioneEntrata;
	}

	public Boolean getAbilProtocollazioneUscita() {
		return abilProtocollazioneUscita;
	}

	public void setAbilProtocollazioneUscita(Boolean abilProtocollazioneUscita) {
		this.abilProtocollazioneUscita = abilProtocollazioneUscita;
	}

	public Boolean getAbilProtocollazioneInterna() {
		return abilProtocollazioneInterna;
	}

	public void setAbilProtocollazioneInterna(Boolean abilProtocollazioneInterna) {
		this.abilProtocollazioneInterna = abilProtocollazioneInterna;
	}

	public Boolean getAbilAssegnazioneSmistamento() {
		return abilAssegnazioneSmistamento;
	}

	public void setAbilAssegnazioneSmistamento(Boolean abilAssegnazioneSmistamento) {
		this.abilAssegnazioneSmistamento = abilAssegnazioneSmistamento;
	}

	public Boolean getAbilCondivisione() {
		return abilCondivisione;
	}

	public void setAbilCondivisione(Boolean abilCondivisione) {
		this.abilCondivisione = abilCondivisione;
	}

	public Boolean getAbilClassificazioneFascicolazione() {
		return abilClassificazioneFascicolazione;
	}

	public void setAbilClassificazioneFascicolazione(Boolean abilClassificazioneFascicolazione) {
		this.abilClassificazioneFascicolazione = abilClassificazioneFascicolazione;
	}

	public Boolean getAbilModificaDatiRegistrazione() {
		return abilModificaDatiRegistrazione;
	}

	public void setAbilModificaDatiRegistrazione(Boolean abilModificaDatiRegistrazione) {
		this.abilModificaDatiRegistrazione = abilModificaDatiRegistrazione;
	}

	public Boolean getAbilModificaDati() {
		return abilModificaDati;
	}

	public void setAbilModificaDati(Boolean abilModificaDati) {
		this.abilModificaDati = abilModificaDati;
	}

	public Boolean getAbilAvvioIterWF() {
		return abilAvvioIterWF;
	}

	public void setAbilAvvioIterWF(Boolean abilAvvioIterWF) {
		this.abilAvvioIterWF = abilAvvioIterWF;
	}

	public Boolean getAbilInvioInConservazione() {
		return abilInvioInConservazione;
	}

	public void setAbilInvioInConservazione(Boolean abilInvioInConservazione) {
		this.abilInvioInConservazione = abilInvioInConservazione;
	}

	public Boolean getAbilAggiuntaFile() {
		return abilAggiuntaFile;
	}

	public void setAbilAggiuntaFile(Boolean abilAggiuntaFile) {
		this.abilAggiuntaFile = abilAggiuntaFile;
	}

	public Boolean getAbilInvioPEO() {
		return abilInvioPEO;
	}

	public void setAbilInvioPEO(Boolean abilInvioPEO) {
		this.abilInvioPEO = abilInvioPEO;
	}

	public Boolean getAbilInvioPEC() {
		return abilInvioPEC;
	}

	public void setAbilInvioPEC(Boolean abilInvioPEC) {
		this.abilInvioPEC = abilInvioPEC;
	}

	public Boolean getAbilRichAnnullamentoReg() {
		return abilRichAnnullamentoReg;
	}

	public void setAbilRichAnnullamentoReg(Boolean abilRichAnnullamentoReg) {
		this.abilRichAnnullamentoReg = abilRichAnnullamentoReg;
	}

	public Boolean getAbilModificaRichAnnullamentoReg() {
		return abilModificaRichAnnullamentoReg;
	}

	public void setAbilModificaRichAnnullamentoReg(Boolean abilModificaRichAnnullamentoReg) {
		this.abilModificaRichAnnullamentoReg = abilModificaRichAnnullamentoReg;
	}

	public Boolean getAbilEliminaRichAnnullamentoReg() {
		return abilEliminaRichAnnullamentoReg;
	}

	public void setAbilEliminaRichAnnullamentoReg(Boolean abilEliminaRichAnnullamentoReg) {
		this.abilEliminaRichAnnullamentoReg = abilEliminaRichAnnullamentoReg;
	}

	public Boolean getAbilAnnullamentoReg() {
		return abilAnnullamentoReg;
	}

	public void setAbilAnnullamentoReg(Boolean abilAnnullamentoReg) {
		this.abilAnnullamentoReg = abilAnnullamentoReg;
	}

	public Boolean getAbilPresaInCarico() {
		return abilPresaInCarico;
	}

	public void setAbilPresaInCarico(Boolean abilPresaInCarico) {
		this.abilPresaInCarico = abilPresaInCarico;
	}

	public Boolean getAbilRestituzione() {
		return abilRestituzione;
	}

	public void setAbilRestituzione(Boolean abilRestituzione) {
		this.abilRestituzione = abilRestituzione;
	}

	public Boolean getAbilInvioConferma() {
		return abilInvioConferma;
	}

	public void setAbilInvioConferma(Boolean abilInvioConferma) {
		this.abilInvioConferma = abilInvioConferma;
	}

	public Boolean getAbilInvioAggiornamento() {
		return abilInvioAggiornamento;
	}

	public void setAbilInvioAggiornamento(Boolean abilInvioAggiornamento) {
		this.abilInvioAggiornamento = abilInvioAggiornamento;
	}

	public Boolean getAbilInvioAnnullamento() {
		return abilInvioAnnullamento;
	}

	public void setAbilInvioAnnullamento(Boolean abilInvioAnnullamento) {
		this.abilInvioAnnullamento = abilInvioAnnullamento;
	}

	public Boolean getAbilArchiviazione() {
		return abilArchiviazione;
	}

	public void setAbilArchiviazione(Boolean abilArchiviazione) {
		this.abilArchiviazione = abilArchiviazione;
	}

	public Boolean getAbilOsservazioniNotifiche() {
		return abilOsservazioniNotifiche;
	}

	public void setAbilOsservazioniNotifiche(Boolean abilOsservazioniNotifiche) {
		this.abilOsservazioniNotifiche = abilOsservazioniNotifiche;
	}

	public Boolean getAbilInviaALibroFirmaVistoRegCont() {
		return abilInviaALibroFirmaVistoRegCont;
	}

	public void setAbilInviaALibroFirmaVistoRegCont(Boolean abilInviaALibroFirmaVistoRegCont) {
		this.abilInviaALibroFirmaVistoRegCont = abilInviaALibroFirmaVistoRegCont;
	}

	public Boolean getAbilTogliaDaLibroFirmaVistoRegCont() {
		return abilTogliaDaLibroFirmaVistoRegCont;
	}

	public void setAbilTogliaDaLibroFirmaVistoRegCont(Boolean abilTogliaDaLibroFirmaVistoRegCont) {
		this.abilTogliaDaLibroFirmaVistoRegCont = abilTogliaDaLibroFirmaVistoRegCont;
	}

	public Boolean getAbilImpostaStatoAlVistoRegCont() {
		return abilImpostaStatoAlVistoRegCont;
	}

	public void setAbilImpostaStatoAlVistoRegCont(Boolean abilImpostaStatoAlVistoRegCont) {
		this.abilImpostaStatoAlVistoRegCont = abilImpostaStatoAlVistoRegCont;
	}

	public Boolean getAbilFirma() {
		return abilFirma;
	}

	public void setAbilFirma(Boolean abilFirma) {
		this.abilFirma = abilFirma;
	}

	public Boolean getAbilVistoElettronico() {
		return abilVistoElettronico;
	}

	public void setAbilVistoElettronico(Boolean abilVistoElettronico) {
		this.abilVistoElettronico = abilVistoElettronico;
	}

	public Boolean getAbilStampaCopertina() {
		return abilStampaCopertina;
	}

	public void setAbilStampaCopertina(Boolean abilStampaCopertina) {
		this.abilStampaCopertina = abilStampaCopertina;
	}

	public Boolean getAbilStampaRicevuta() {
		return abilStampaRicevuta;
	}

	public void setAbilStampaRicevuta(Boolean abilStampaRicevuta) {
		this.abilStampaRicevuta = abilStampaRicevuta;
	}

	public Boolean getAbilStampaEtichetta() {
		return abilStampaEtichetta;
	}

	public void setAbilStampaEtichetta(Boolean abilStampaEtichetta) {
		this.abilStampaEtichetta = abilStampaEtichetta;
	}

	public Boolean getAbilNuovoComeCopia() {
		return abilNuovoComeCopia;
	}

	public void setAbilNuovoComeCopia(Boolean abilNuovoComeCopia) {
		this.abilNuovoComeCopia = abilNuovoComeCopia;
	}

	public Boolean getAbilModificaTipologia() {
		return abilModificaTipologia;
	}

	public void setAbilModificaTipologia(Boolean abilModificaTipologia) {
		this.abilModificaTipologia = abilModificaTipologia;
	}

	public Boolean getAbilRispondiUscita() {
		return abilRispondiUscita;
	}

	public void setAbilRispondiUscita(Boolean abilRispondiUscita) {
		this.abilRispondiUscita = abilRispondiUscita;
	}

	public String getIdTipoProcRevocaAtto() {
		return idTipoProcRevocaAtto;
	}

	public void setIdTipoProcRevocaAtto(String idTipoProcRevocaAtto) {
		this.idTipoProcRevocaAtto = idTipoProcRevocaAtto;
	}

	public String getNomeTipoProcRevocaAtto() {
		return nomeTipoProcRevocaAtto;
	}

	public void setNomeTipoProcRevocaAtto(String nomeTipoProcRevocaAtto) {
		this.nomeTipoProcRevocaAtto = nomeTipoProcRevocaAtto;
	}

	public String getIdDefFlussoWFRevocaAtto() {
		return idDefFlussoWFRevocaAtto;
	}

	public void setIdDefFlussoWFRevocaAtto(String idDefFlussoWFRevocaAtto) {
		this.idDefFlussoWFRevocaAtto = idDefFlussoWFRevocaAtto;
	}

	public String getIdTipoDocPropostaRevocaAtto() {
		return idTipoDocPropostaRevocaAtto;
	}

	public void setIdTipoDocPropostaRevocaAtto(String idTipoDocPropostaRevocaAtto) {
		this.idTipoDocPropostaRevocaAtto = idTipoDocPropostaRevocaAtto;
	}

	public String getNomeTipoDocPropostaRevocaAtto() {
		return nomeTipoDocPropostaRevocaAtto;
	}

	public void setNomeTipoDocPropostaRevocaAtto(String nomeTipoDocPropostaRevocaAtto) {
		this.nomeTipoDocPropostaRevocaAtto = nomeTipoDocPropostaRevocaAtto;
	}

	public String getSiglaPropostaRevocaAtto() {
		return siglaPropostaRevocaAtto;
	}

	public void setSiglaPropostaRevocaAtto(String siglaPropostaRevocaAtto) {
		this.siglaPropostaRevocaAtto = siglaPropostaRevocaAtto;
	}

	public String getIdTipoProcEmendamento() {
		return idTipoProcEmendamento;
	}

	public void setIdTipoProcEmendamento(String idTipoProcEmendamento) {
		this.idTipoProcEmendamento = idTipoProcEmendamento;
	}

	public String getNomeTipoProcEmendamento() {
		return nomeTipoProcEmendamento;
	}

	public void setNomeTipoProcEmendamento(String nomeTipoProcEmendamento) {
		this.nomeTipoProcEmendamento = nomeTipoProcEmendamento;
	}

	public String getIdDefFlussoWFEmendamento() {
		return idDefFlussoWFEmendamento;
	}

	public void setIdDefFlussoWFEmendamento(String idDefFlussoWFEmendamento) {
		this.idDefFlussoWFEmendamento = idDefFlussoWFEmendamento;
	}

	public String getIdTipoDocPropostaEmendamento() {
		return idTipoDocPropostaEmendamento;
	}

	public void setIdTipoDocPropostaEmendamento(String idTipoDocPropostaEmendamento) {
		this.idTipoDocPropostaEmendamento = idTipoDocPropostaEmendamento;
	}

	public String getNomeTipoDocPropostaEmendamento() {
		return nomeTipoDocPropostaEmendamento;
	}

	public void setNomeTipoDocPropostaEmendamento(String nomeTipoDocPropostaEmendamento) {
		this.nomeTipoDocPropostaEmendamento = nomeTipoDocPropostaEmendamento;
	}

	public String getSiglaPropostaEmendamento() {
		return siglaPropostaEmendamento;
	}

	public void setSiglaPropostaEmendamento(String siglaPropostaEmendamento) {
		this.siglaPropostaEmendamento = siglaPropostaEmendamento;
	}

	public String getTipoAttoRifEmendamento() {
		return tipoAttoRifEmendamento;
	}

	public void setTipoAttoRifEmendamento(String tipoAttoRifEmendamento) {
		this.tipoAttoRifEmendamento = tipoAttoRifEmendamento;
	}

	public String getSiglaAttoRifEmendamento() {
		return siglaAttoRifEmendamento;
	}

	public void setSiglaAttoRifEmendamento(String siglaAttoRifEmendamento) {
		this.siglaAttoRifEmendamento = siglaAttoRifEmendamento;
	}

	public String getNumeroAttoRifEmendamento() {
		return numeroAttoRifEmendamento;
	}

	public void setNumeroAttoRifEmendamento(String numeroAttoRifEmendamento) {
		this.numeroAttoRifEmendamento = numeroAttoRifEmendamento;
	}

	public String getAnnoAttoRifEmendamento() {
		return annoAttoRifEmendamento;
	}

	public void setAnnoAttoRifEmendamento(String annoAttoRifEmendamento) {
		this.annoAttoRifEmendamento = annoAttoRifEmendamento;
	}
	
	public String getIdEmendamentoRif() {
		return idEmendamentoRif;
	}

	public void setIdEmendamentoRif(String idEmendamentoRif) {
		this.idEmendamentoRif = idEmendamentoRif;
	}

	public String getNumeroEmendamentoRif() {
		return numeroEmendamentoRif;
	}

	public void setNumeroEmendamentoRif(String numeroEmendamentoRif) {
		this.numeroEmendamentoRif = numeroEmendamentoRif;
	}

	public Boolean getConDatiAnnullati() {
		return conDatiAnnullati;
	}

	public void setConDatiAnnullati(Boolean conDatiAnnullati) {
		this.conDatiAnnullati = conDatiAnnullati;
	}

	public String getListaDatiAnnullati() {
		return listaDatiAnnullati;
	}

	public void setListaDatiAnnullati(String listaDatiAnnullati) {
		this.listaDatiAnnullati = listaDatiAnnullati;
	}

	public EmailProvBean getEmailProv() {
		return emailProv;
	}

	public void setEmailProv(EmailProvBean emailProv) {
		this.emailProv = emailProv;
	}

	public String getIdEmailArrivo() {
		return idEmailArrivo;
	}

	public void setIdEmailArrivo(String idEmailArrivo) {
		this.idEmailArrivo = idEmailArrivo;
	}
	
	public String getIdUDFromEmail() {
		return idUDFromEmail;
	}

	public void setIdUDFromEmail(String idUDFromEmail) {
		this.idUDFromEmail = idUDFromEmail;
	}

	public Boolean getCasellaIsPEC() {
		return casellaIsPEC;
	}

	public void setCasellaIsPEC(Boolean casellaIsPEC) {
		this.casellaIsPEC = casellaIsPEC;
	}

	public Boolean getEmailArrivoInteroperabile() {
		return emailArrivoInteroperabile;
	}

	public void setEmailArrivoInteroperabile(Boolean emailArrivoInteroperabile) {
		this.emailArrivoInteroperabile = emailArrivoInteroperabile;
	}

	public Boolean getEmailInviataFlgPEC() {
		return emailInviataFlgPEC;
	}

	public void setEmailInviataFlgPEC(Boolean emailInviataFlgPEC) {
		this.emailInviataFlgPEC = emailInviataFlgPEC;
	}

	public Boolean getEmailInviataFlgPEO() {
		return emailInviataFlgPEO;
	}

	public void setEmailInviataFlgPEO(Boolean emailInviataFlgPEO) {
		this.emailInviataFlgPEO = emailInviataFlgPEO;
	}

	public Boolean getFlgInteroperabilita() {
		return flgInteroperabilita;
	}

	public void setFlgInteroperabilita(Boolean flgInteroperabilita) {
		this.flgInteroperabilita = flgInteroperabilita;
	}

	public String getIdUserCtrlAmmissib() {
		return idUserCtrlAmmissib;
	}

	public void setIdUserCtrlAmmissib(String idUserCtrlAmmissib) {
		this.idUserCtrlAmmissib = idUserCtrlAmmissib;
	}

	public String getIdUserConfermaAssegnazione() {
		return idUserConfermaAssegnazione;
	}

	public void setIdUserConfermaAssegnazione(String idUserConfermaAssegnazione) {
		this.idUserConfermaAssegnazione = idUserConfermaAssegnazione;
	}

	public String getDesUserConfermaAssegnazione() {
		return desUserConfermaAssegnazione;
	}

	public void setDesUserConfermaAssegnazione(String desUserConfermaAssegnazione) {
		this.desUserConfermaAssegnazione = desUserConfermaAssegnazione;
	}

	public String getUsernameConfermaAssegnazione() {
		return usernameConfermaAssegnazione;
	}

	public void setUsernameConfermaAssegnazione(String usernameConfermaAssegnazione) {
		this.usernameConfermaAssegnazione = usernameConfermaAssegnazione;
	}

	public String getRicEccezioniInterop() {
		return ricEccezioniInterop;
	}

	public void setRicEccezioniInterop(String ricEccezioniInterop) {
		this.ricEccezioniInterop = ricEccezioniInterop;
	}

	public String getRicAggiornamentiInterop() {
		return ricAggiornamentiInterop;
	}

	public void setRicAggiornamentiInterop(String ricAggiornamentiInterop) {
		this.ricAggiornamentiInterop = ricAggiornamentiInterop;
	}

	public String getRicAnnullamentiInterop() {
		return ricAnnullamentiInterop;
	}

	public void setRicAnnullamentiInterop(String ricAnnullamentiInterop) {
		this.ricAnnullamentiInterop = ricAnnullamentiInterop;
	}

	public String getPresenzaDocCollegati() {
		return presenzaDocCollegati;
	}

	public void setPresenzaDocCollegati(String presenzaDocCollegati) {
		this.presenzaDocCollegati = presenzaDocCollegati;
	}

	public String getEstremiTrasm() {
		return estremiTrasm;
	}

	public void setEstremiTrasm(String estremiTrasm) {
		this.estremiTrasm = estremiTrasm;
	}

	public String getCodStatoDett() {
		return codStatoDett;
	}

	public void setCodStatoDett(String codStatoDett) {
		this.codStatoDett = codStatoDett;
	}
	
	public String getCodStato() {
		return codStato;
	}

	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}

	public Boolean getInviataMailInteroperabile() {
		return inviataMailInteroperabile;
	}

	public void setInviataMailInteroperabile(Boolean inviataMailInteroperabile) {
		this.inviataMailInteroperabile = inviataMailInteroperabile;
	}

	public String getTipoRegNumerazioneSecondaria() {
		return tipoRegNumerazioneSecondaria;
	}

	public void setTipoRegNumerazioneSecondaria(String tipoRegNumerazioneSecondaria) {
		this.tipoRegNumerazioneSecondaria = tipoRegNumerazioneSecondaria;
	}

	public String getSiglaRegNumerazioneSecondaria() {
		return siglaRegNumerazioneSecondaria;
	}

	public void setSiglaRegNumerazioneSecondaria(String siglaRegNumerazioneSecondaria) {
		this.siglaRegNumerazioneSecondaria = siglaRegNumerazioneSecondaria;
	}

	public String getAnnoRegNumerazioneSecondaria() {
		return annoRegNumerazioneSecondaria;
	}

	public void setAnnoRegNumerazioneSecondaria(String annoRegNumerazioneSecondaria) {
		this.annoRegNumerazioneSecondaria = annoRegNumerazioneSecondaria;
	}

	public String getNumeroRegNumerazioneSecondaria() {
		return numeroRegNumerazioneSecondaria;
	}

	public void setNumeroRegNumerazioneSecondaria(String numeroRegNumerazioneSecondaria) {
		this.numeroRegNumerazioneSecondaria = numeroRegNumerazioneSecondaria;
	}

	public String getSubNroRegNumerazioneSecondaria() {
		return subNroRegNumerazioneSecondaria;
	}

	public void setSubNroRegNumerazioneSecondaria(String subNroRegNumerazioneSecondaria) {
		this.subNroRegNumerazioneSecondaria = subNroRegNumerazioneSecondaria;
	}

	public Date getDataRegistrazioneNumerazioneSecondaria() {
		return dataRegistrazioneNumerazioneSecondaria;
	}

	public void setDataRegistrazioneNumerazioneSecondaria(Date dataRegistrazioneNumerazioneSecondaria) {
		this.dataRegistrazioneNumerazioneSecondaria = dataRegistrazioneNumerazioneSecondaria;
	}

	public String getIdUdAttoAutAnnRegNumerazioneSecondaria() {
		return idUdAttoAutAnnRegNumerazioneSecondaria;
	}

	public void setIdUdAttoAutAnnRegNumerazioneSecondaria(String idUdAttoAutAnnRegNumerazioneSecondaria) {
		this.idUdAttoAutAnnRegNumerazioneSecondaria = idUdAttoAutAnnRegNumerazioneSecondaria;
	}

	public String getRegNumSecondariaDesGruppo() {
		return regNumSecondariaDesGruppo;
	}

	public void setRegNumSecondariaDesGruppo(String regNumSecondariaDesGruppo) {
		this.regNumSecondariaDesGruppo = regNumSecondariaDesGruppo;
	}

	public String getIdUOProponente() {
		return idUOProponente;
	}

	public void setIdUOProponente(String idUOProponente) {
		this.idUOProponente = idUOProponente;
	}

	public String getCodUOProponente() {
		return codUOProponente;
	}

	public void setCodUOProponente(String codUOProponente) {
		this.codUOProponente = codUOProponente;
	}

	public String getDesUOProponente() {
		return desUOProponente;
	}

	public void setDesUOProponente(String desUOProponente) {
		this.desUOProponente = desUOProponente;
	}

	public String getDesDirProponente() {
		return desDirProponente;
	}

	public void setDesDirProponente(String desDirProponente) {
		this.desDirProponente = desDirProponente;
	}

	public String getIdUserRespProc() {
		return idUserRespProc;
	}

	public void setIdUserRespProc(String idUserRespProc) {
		this.idUserRespProc = idUserRespProc;
	}

	public String getDesUserRespProc() {
		return desUserRespProc;
	}

	public void setDesUserRespProc(String desUserRespProc) {
		this.desUserRespProc = desUserRespProc;
	}

	public Date getDataAvvioIterAtto() {
		return dataAvvioIterAtto;
	}

	public void setDataAvvioIterAtto(Date dataAvvioIterAtto) {
		this.dataAvvioIterAtto = dataAvvioIterAtto;
	}

	public String getFunzionarioIstruttoreAtto() {
		return funzionarioIstruttoreAtto;
	}

	public void setFunzionarioIstruttoreAtto(String funzionarioIstruttoreAtto) {
		this.funzionarioIstruttoreAtto = funzionarioIstruttoreAtto;
	}

	public String getResponsabileAtto() {
		return responsabileAtto;
	}

	public void setResponsabileAtto(String responsabileAtto) {
		this.responsabileAtto = responsabileAtto;
	}

	public String getDirettoreFirmatarioAtto() {
		return direttoreFirmatarioAtto;
	}

	public void setDirettoreFirmatarioAtto(String direttoreFirmatarioAtto) {
		this.direttoreFirmatarioAtto = direttoreFirmatarioAtto;
	}

	public Date getDataFirmaAtto() {
		return dataFirmaAtto;
	}

	public void setDataFirmaAtto(Date dataFirmaAtto) {
		this.dataFirmaAtto = dataFirmaAtto;
	}

	public String getFunzionarioFirmaAtto() {
		return funzionarioFirmaAtto;
	}

	public void setFunzionarioFirmaAtto(String funzionarioFirmaAtto) {
		this.funzionarioFirmaAtto = funzionarioFirmaAtto;
	}

	public Date getDataPubblicazioneAtto() {
		return dataPubblicazioneAtto;
	}

	public void setDataPubblicazioneAtto(Date dataPubblicazioneAtto) {
		this.dataPubblicazioneAtto = dataPubblicazioneAtto;
	}

	public String getLogIterAtto() {
		return logIterAtto;
	}

	public void setLogIterAtto(String logIterAtto) {
		this.logIterAtto = logIterAtto;
	}

	public String getStatoConservazione() {
		return statoConservazione;
	}

	public void setStatoConservazione(String statoConservazione) {
		this.statoConservazione = statoConservazione;
	}

	public Date getDataInvioInConservazione() {
		return dataInvioInConservazione;
	}

	public void setDataInvioInConservazione(Date dataInvioInConservazione) {
		this.dataInvioInConservazione = dataInvioInConservazione;
	}

	public String getErroreInInvio() {
		return erroreInInvio;
	}

	public void setErroreInInvio(String erroreInInvio) {
		this.erroreInInvio = erroreInInvio;
	}

	public List<ContraentiOutBean> getContraenti() {
		return contraenti;
	}

	public void setContraenti(List<ContraentiOutBean> contraenti) {
		this.contraenti = contraenti;
	}

	public Flag getFlgCompilazioneModulo() {
		return flgCompilazioneModulo;
	}

	public void setFlgCompilazioneModulo(Flag flgCompilazioneModulo) {
		this.flgCompilazioneModulo = flgCompilazioneModulo;
	}

	public Flag getFlgPropostaAtto() {
		return flgPropostaAtto;
	}

	public void setFlgPropostaAtto(Flag flgPropostaAtto) {
		this.flgPropostaAtto = flgPropostaAtto;
	}

	public String getSceltaIterPropostaDD() {
		return sceltaIterPropostaDD;
	}

	public void setSceltaIterPropostaDD(String sceltaIterPropostaDD) {
		this.sceltaIterPropostaDD = sceltaIterPropostaDD;
	}

	public String getFlgRichParereRevisori() {
		return flgRichParereRevisori;
	}

	public void setFlgRichParereRevisori(String flgRichParereRevisori) {
		this.flgRichParereRevisori = flgRichParereRevisori;
	}

	public String getSiglaAttoRifSubImpegno() {
		return siglaAttoRifSubImpegno;
	}

	public void setSiglaAttoRifSubImpegno(String siglaAttoRifSubImpegno) {
		this.siglaAttoRifSubImpegno = siglaAttoRifSubImpegno;
	}

	public String getNumeroAttoRifSubImpegno() {
		return numeroAttoRifSubImpegno;
	}

	public void setNumeroAttoRifSubImpegno(String numeroAttoRifSubImpegno) {
		this.numeroAttoRifSubImpegno = numeroAttoRifSubImpegno;
	}

	public String getAnnoAttoRifSubImpegno() {
		return annoAttoRifSubImpegno;
	}

	public void setAnnoAttoRifSubImpegno(String annoAttoRifSubImpegno) {
		this.annoAttoRifSubImpegno = annoAttoRifSubImpegno;
	}

	public Date getDataAttoRifSubImpegno() {
		return dataAttoRifSubImpegno;
	}

	public void setDataAttoRifSubImpegno(Date dataAttoRifSubImpegno) {
		this.dataAttoRifSubImpegno = dataAttoRifSubImpegno;
	}

	public String getIdUoRevisioneOrganigramma() {
		return idUoRevisioneOrganigramma;
	}

	public void setIdUoRevisioneOrganigramma(String idUoRevisioneOrganigramma) {
		this.idUoRevisioneOrganigramma = idUoRevisioneOrganigramma;
	}

	public String getIdUoPadreRevisioneOrganigramma() {
		return idUoPadreRevisioneOrganigramma;
	}

	public void setIdUoPadreRevisioneOrganigramma(String idUoPadreRevisioneOrganigramma) {
		this.idUoPadreRevisioneOrganigramma = idUoPadreRevisioneOrganigramma;
	}

	public String getCodRapidoUoRevisioneOrganigramma() {
		return codRapidoUoRevisioneOrganigramma;
	}

	public void setCodRapidoUoRevisioneOrganigramma(String codRapidoUoRevisioneOrganigramma) {
		this.codRapidoUoRevisioneOrganigramma = codRapidoUoRevisioneOrganigramma;
	}

	public String getNomeUoRevisioneOrganigramma() {
		return nomeUoRevisioneOrganigramma;
	}

	public void setNomeUoRevisioneOrganigramma(String nomeUoRevisioneOrganigramma) {
		this.nomeUoRevisioneOrganigramma = nomeUoRevisioneOrganigramma;
	}

	public String getTipoUoRevisioneOrganigramma() {
		return tipoUoRevisioneOrganigramma;
	}

	public void setTipoUoRevisioneOrganigramma(String tipoUoRevisioneOrganigramma) {
		this.tipoUoRevisioneOrganigramma = tipoUoRevisioneOrganigramma;
	}

	public String getLivelloUoRevisioneOrganigramma() {
		return livelloUoRevisioneOrganigramma;
	}

	public void setLivelloUoRevisioneOrganigramma(String livelloUoRevisioneOrganigramma) {
		this.livelloUoRevisioneOrganigramma = livelloUoRevisioneOrganigramma;
	}

	public String getSupportoOriginale() {
		return supportoOriginale;
	}

	public void setSupportoOriginale(String supportoOriginale) {
		this.supportoOriginale = supportoOriginale;
	}

	public String getCodSupportoOrig() {
		return codSupportoOrig;
	}

	public void setCodSupportoOrig(String codSupportoOrig) {
		this.codSupportoOrig = codSupportoOrig;
	}

	public Flag getFlgOriginaleCartaceo() {
		return flgOriginaleCartaceo;
	}

	public void setFlgOriginaleCartaceo(Flag flgOriginaleCartaceo) {
		this.flgOriginaleCartaceo = flgOriginaleCartaceo;
	}

	public Flag getFlgCopiaSostitutiva() {
		return flgCopiaSostitutiva;
	}

	public void setFlgCopiaSostitutiva(Flag flgCopiaSostitutiva) {
		this.flgCopiaSostitutiva = flgCopiaSostitutiva;
	}

	public List<SoggettoEsternoBean> getEsibenti() {
		return esibenti;
	}

	public void setEsibenti(List<SoggettoEsternoBean> esibenti) {
		this.esibenti = esibenti;
	}

	public List<SoggettoEsternoBean> getInteressati() {
		return interessati;
	}

	public void setInteressati(List<SoggettoEsternoBean> interessati) {
		this.interessati = interessati;
	}

	public List<AltreUbicazioniBean> getAltreUbicazioni() {
		return altreUbicazioni;
	}

	public void setAltreUbicazioni(List<AltreUbicazioniBean> altreUbicazioni) {
		this.altreUbicazioni = altreUbicazioni;
	}

	public List<DocumentoCollegato> getDocumentiCollegati() {
		return documentiCollegati;
	}

	public void setDocumentiCollegati(List<DocumentoCollegato> documentiCollegati) {
		this.documentiCollegati = documentiCollegati;
	}

	public List<AltriRiferimentiBean> getAltriRiferimenti() {
		return altriRiferimenti;
	}

	public void setAltriRiferimenti(List<AltriRiferimentiBean> altriRiferimenti) {
		this.altriRiferimenti = altriRiferimenti;
	}

	public String getSezionaleOnlyOne() {
		return sezionaleOnlyOne;
	}

	public void setSezionaleOnlyOne(String sezionaleOnlyOne) {
		this.sezionaleOnlyOne = sezionaleOnlyOne;
	}

	public String getNumeroOnlyOne() {
		return numeroOnlyOne;
	}

	public void setNumeroOnlyOne(String numeroOnlyOne) {
		this.numeroOnlyOne = numeroOnlyOne;
	}

	public String getAnnoOnlyOne() {
		return annoOnlyOne;
	}

	public void setAnnoOnlyOne(String annoOnlyOne) {
		this.annoOnlyOne = annoOnlyOne;
	}

	public Flag getFlgCaricamentoPGPregressoDaGUI() {
		return flgCaricamentoPGPregressoDaGUI;
	}

	public void setFlgCaricamentoPGPregressoDaGUI(Flag flgCaricamentoPGPregressoDaGUI) {
		this.flgCaricamentoPGPregressoDaGUI = flgCaricamentoPGPregressoDaGUI;
	}

	public String getSegnatura() {
		return segnatura;
	}

	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}

	public List<SoggettoEsternoBean> getListaContribuenti() {
		return listaContribuenti;
	}

	public void setListaContribuenti(List<SoggettoEsternoBean> listaContribuenti) {
		this.listaContribuenti = listaContribuenti;
	}

	public String getAttivaTimbroTipologia() {
		return attivaTimbroTipologia;
	}

	public void setAttivaTimbroTipologia(String attivaTimbroTipologia) {
		this.attivaTimbroTipologia = attivaTimbroTipologia;
	}

	public String getDefaultAzioneStampaEtichetta() {
		return defaultAzioneStampaEtichetta;
	}

	public void setDefaultAzioneStampaEtichetta(String defaultAzioneStampaEtichetta) {
		this.defaultAzioneStampaEtichetta = defaultAzioneStampaEtichetta;
	}

	public String getCodCategoriaRegPrimariaRisposta() {
		return codCategoriaRegPrimariaRisposta;
	}

	public void setCodCategoriaRegPrimariaRisposta(String codCategoriaRegPrimariaRisposta) {
		this.codCategoriaRegPrimariaRisposta = codCategoriaRegPrimariaRisposta;
	}

	public String getSiglaRegistroRegPrimariaRisposta() {
		return siglaRegistroRegPrimariaRisposta;
	}

	public void setSiglaRegistroRegPrimariaRisposta(String siglaRegistroRegPrimariaRisposta) {
		this.siglaRegistroRegPrimariaRisposta = siglaRegistroRegPrimariaRisposta;
	}

	public String getDesRegistroRegPrimariaRisposta() {
		return desRegistroRegPrimariaRisposta;
	}

	public void setDesRegistroRegPrimariaRisposta(String desRegistroRegPrimariaRisposta) {
		this.desRegistroRegPrimariaRisposta = desRegistroRegPrimariaRisposta;
	}

	public String getCategoriaRepertorioRegPrimariaRisposta() {
		return categoriaRepertorioRegPrimariaRisposta;
	}

	public void setCategoriaRepertorioRegPrimariaRisposta(String categoriaRepertorioRegPrimariaRisposta) {
		this.categoriaRepertorioRegPrimariaRisposta = categoriaRepertorioRegPrimariaRisposta;
	}

	public String getCodCategoriaRegSecondariaRisposta() {
		return codCategoriaRegSecondariaRisposta;
	}

	public void setCodCategoriaRegSecondariaRisposta(String codCategoriaRegSecondariaRisposta) {
		this.codCategoriaRegSecondariaRisposta = codCategoriaRegSecondariaRisposta;
	}

	public String getSiglaRegistroRegSecondariaRisposta() {
		return siglaRegistroRegSecondariaRisposta;
	}

	public void setSiglaRegistroRegSecondariaRisposta(String siglaRegistroRegSecondariaRisposta) {
		this.siglaRegistroRegSecondariaRisposta = siglaRegistroRegSecondariaRisposta;
	}

	public String getDesRegistroRegSecondariaRisposta() {
		return desRegistroRegSecondariaRisposta;
	}

	public void setDesRegistroRegSecondariaRisposta(String desRegistroRegSecondariaRisposta) {
		this.desRegistroRegSecondariaRisposta = desRegistroRegSecondariaRisposta;
	}

	public String getCategoriaRepertorioRegSecondariaRisposta() {
		return categoriaRepertorioRegSecondariaRisposta;
	}

	public void setCategoriaRepertorioRegSecondariaRisposta(String categoriaRepertorioRegSecondariaRisposta) {
		this.categoriaRepertorioRegSecondariaRisposta = categoriaRepertorioRegSecondariaRisposta;
	}

	public String getIdScrivaniaRespIstruttoria() {
		return idScrivaniaRespIstruttoria;
	}

	public void setIdScrivaniaRespIstruttoria(String idScrivaniaRespIstruttoria) {
		this.idScrivaniaRespIstruttoria = idScrivaniaRespIstruttoria;
	}

	public String getDesScrivaniaRespIstruttoria() {
		return desScrivaniaRespIstruttoria;
	}

	public void setDesScrivaniaRespIstruttoria(String desScrivaniaRespIstruttoria) {
		this.desScrivaniaRespIstruttoria = desScrivaniaRespIstruttoria;
	}

	public String getCodUOScrivaniaRespIstruttoria() {
		return codUOScrivaniaRespIstruttoria;
	}

	public void setCodUOScrivaniaRespIstruttoria(String codUOScrivaniaRespIstruttoria) {
		this.codUOScrivaniaRespIstruttoria = codUOScrivaniaRespIstruttoria;
	}

	public String getCodStatoRichAccessoAtti() {
		return codStatoRichAccessoAtti;
	}

	public void setCodStatoRichAccessoAtti(String codStatoRichAccessoAtti) {
		this.codStatoRichAccessoAtti = codStatoRichAccessoAtti;
	}

	public String getDesStatoRichAccessoAtti() {
		return desStatoRichAccessoAtti;
	}

	public void setDesStatoRichAccessoAtti(String desStatoRichAccessoAtti) {
		this.desStatoRichAccessoAtti = desStatoRichAccessoAtti;
	}

	public Flag getFlgRichiestaAccessoAtti() {
		return flgRichiestaAccessoAtti;
	}

	public void setFlgRichiestaAccessoAtti(Flag flgRichiestaAccessoAtti) {
		this.flgRichiestaAccessoAtti = flgRichiestaAccessoAtti;
	}
	
	public Flag getFlgRichAttiFabbricaVisuraSue() {
		return flgRichAttiFabbricaVisuraSue;
	}

	public void setFlgRichAttiFabbricaVisuraSue(Flag flgRichAttiFabbricaVisuraSue) {
		this.flgRichAttiFabbricaVisuraSue = flgRichAttiFabbricaVisuraSue;
	}
	
	public Flag getFlgRichModificheVisuraSue() {
		return flgRichModificheVisuraSue;
	}

	public void setFlgRichModificheVisuraSue(Flag flgRichModificheVisuraSue) {
		this.flgRichModificheVisuraSue = flgRichModificheVisuraSue;
	}

	public List<AttiRichiestiXMLBean> getAttiRichiesti() {
		return attiRichiesti;
	}

	public void setAttiRichiesti(List<AttiRichiestiXMLBean> attiRichiesti) {
		this.attiRichiesti = attiRichiesti;
	}
	
	public Flag getFlgAltriAttiDaRicercareVisuraSue() {
		return flgAltriAttiDaRicercareVisuraSue;
	}

	public void setFlgAltriAttiDaRicercareVisuraSue(Flag flgAltriAttiDaRicercareVisuraSue) {
		this.flgAltriAttiDaRicercareVisuraSue = flgAltriAttiDaRicercareVisuraSue;
	}

	public List<SoggettoEsternoBean> getDeleganti() {
		return deleganti;
	}

	public void setDeleganti(List<SoggettoEsternoBean> deleganti) {
		this.deleganti = deleganti;
	}

	public List<SoggettoEsternoBean> getIncaricatiRitiro() {
		return incaricatiRitiro;
	}

	public void setIncaricatiRitiro(List<SoggettoEsternoBean> incaricatiRitiro) {
		this.incaricatiRitiro = incaricatiRitiro;
	}

	public Flag getFlgRichAccessoAttiConRichInterno() {
		return flgRichAccessoAttiConRichInterno;
	}

	public void setFlgRichAccessoAttiConRichInterno(Flag flgRichAccessoAttiConRichInterno) {
		this.flgRichAccessoAttiConRichInterno = flgRichAccessoAttiConRichInterno;
	}

	public String getMotivoRichiestaAccessoAtti() {
		return motivoRichiestaAccessoAtti;
	}

	public void setMotivoRichiestaAccessoAtti(String motivoRichiestaAccessoAtti) {
		this.motivoRichiestaAccessoAtti = motivoRichiestaAccessoAtti;
	}

	public String getDettagliRichiestaAccessoAtti() {
		return dettagliRichiestaAccessoAtti;
	}

	public void setDettagliRichiestaAccessoAtti(String dettagliRichiestaAccessoAtti) {
		this.dettagliRichiestaAccessoAtti = dettagliRichiestaAccessoAtti;
	}

	public String getDataAppuntamento() {
		return dataAppuntamento;
	}

	public void setDataAppuntamento(String dataAppuntamento) {
		this.dataAppuntamento = dataAppuntamento;
	}

	public String getOrarioAppuntamento() {
		return orarioAppuntamento;
	}

	public void setOrarioAppuntamento(String orarioAppuntamento) {
		this.orarioAppuntamento = orarioAppuntamento;
	}

	public String getProvenienzaAppuntamento() {
		return provenienzaAppuntamento;
	}

	public void setProvenienzaAppuntamento(String provenienzaAppuntamento) {
		this.provenienzaAppuntamento = provenienzaAppuntamento;
	}

	public String getDataPrelievo() {
		return dataPrelievo;
	}

	public void setDataPrelievo(String dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}

	public String getDataRestituzionePrelievo() {
		return dataRestituzionePrelievo;
	}

	public void setDataRestituzionePrelievo(String dataRestituzionePrelievo) {
		this.dataRestituzionePrelievo = dataRestituzionePrelievo;
	}

	public String getRestituzionePrelievoAttestataDa() {
		return restituzionePrelievoAttestataDa;
	}

	public void setRestituzionePrelievoAttestataDa(String restituzionePrelievoAttestataDa) {
		this.restituzionePrelievoAttestataDa = restituzionePrelievoAttestataDa;
	}

	public String getIdNodoDefaultRicercaAtti() {
		return idNodoDefaultRicercaAtti;
	}

	public void setIdNodoDefaultRicercaAtti(String idNodoDefaultRicercaAtti) {
		this.idNodoDefaultRicercaAtti = idNodoDefaultRicercaAtti;
	}

	public Boolean getAbilRichAccessoAttiVisualizzaDettStdProt() {
		return abilRichAccessoAttiVisualizzaDettStdProt;
	}

	public void setAbilRichAccessoAttiVisualizzaDettStdProt(Boolean abilRichAccessoAttiVisualizzaDettStdProt) {
		this.abilRichAccessoAttiVisualizzaDettStdProt = abilRichAccessoAttiVisualizzaDettStdProt;
	}

	public Boolean getAbilRichAccessoAttiInvioInApprovazione() {
		return abilRichAccessoAttiInvioInApprovazione;
	}

	public void setAbilRichAccessoAttiInvioInApprovazione(Boolean abilRichAccessoAttiInvioInApprovazione) {
		this.abilRichAccessoAttiInvioInApprovazione = abilRichAccessoAttiInvioInApprovazione;
	}

	public Boolean getAbilRichAccessoAttiApprovazione() {
		return abilRichAccessoAttiApprovazione;
	}

	public void setAbilRichAccessoAttiApprovazione(Boolean abilRichAccessoAttiApprovazione) {
		this.abilRichAccessoAttiApprovazione = abilRichAccessoAttiApprovazione;
	}

	public Boolean getAbilRichAccessoAttiInvioEsitoVerificaArchivio() {
		return abilRichAccessoAttiInvioEsitoVerificaArchivio;
	}

	public void setAbilRichAccessoAttiInvioEsitoVerificaArchivio(Boolean abilRichAccessoAttiInvioEsitoVerificaArchivio) {
		this.abilRichAccessoAttiInvioEsitoVerificaArchivio = abilRichAccessoAttiInvioEsitoVerificaArchivio;
	}

	public Boolean getAbilRichAccessoAttiAbilitaAppuntamentoDaAgenda() {
		return abilRichAccessoAttiAbilitaAppuntamentoDaAgenda;
	}

	public void setAbilRichAccessoAttiAbilitaAppuntamentoDaAgenda(Boolean abilRichAccessoAttiAbilitaAppuntamentoDaAgenda) {
		this.abilRichAccessoAttiAbilitaAppuntamentoDaAgenda = abilRichAccessoAttiAbilitaAppuntamentoDaAgenda;
	}

	public Boolean getAbilRichAccessoAttiSetAppuntamento() {
		return abilRichAccessoAttiSetAppuntamento;
	}

	public void setAbilRichAccessoAttiSetAppuntamento(Boolean abilRichAccessoAttiSetAppuntamento) {
		this.abilRichAccessoAttiSetAppuntamento = abilRichAccessoAttiSetAppuntamento;
	}

	public Boolean getAbilRichAccessoAttiAnnullamentoAppuntamento() {
		return abilRichAccessoAttiAnnullamentoAppuntamento;
	}

	public void setAbilRichAccessoAttiAnnullamentoAppuntamento(Boolean abilRichAccessoAttiAnnullamentoAppuntamento) {
		this.abilRichAccessoAttiAnnullamentoAppuntamento = abilRichAccessoAttiAnnullamentoAppuntamento;
	}

	public Boolean getAbilRichAccessoAttiRegistraPrelievo() {
		return abilRichAccessoAttiRegistraPrelievo;
	}

	public void setAbilRichAccessoAttiRegistraPrelievo(Boolean abilRichAccessoAttiRegistraPrelievo) {
		this.abilRichAccessoAttiRegistraPrelievo = abilRichAccessoAttiRegistraPrelievo;
	}

	public Boolean getAbilRichAccessoAttiRegistraRestituzione() {
		return abilRichAccessoAttiRegistraRestituzione;
	}

	public void setAbilRichAccessoAttiRegistraRestituzione(Boolean abilRichAccessoAttiRegistraRestituzione) {
		this.abilRichAccessoAttiRegistraRestituzione = abilRichAccessoAttiRegistraRestituzione;
	}

	public Boolean getAbilRichAccessoAttiAnnullamento() {
		return abilRichAccessoAttiAnnullamento;
	}

	public void setAbilRichAccessoAttiAnnullamento(Boolean abilRichAccessoAttiAnnullamento) {
		this.abilRichAccessoAttiAnnullamento = abilRichAccessoAttiAnnullamento;
	}

	public Boolean getAbilRichAccessoAttiStampaFoglioPrelievo() {
		return abilRichAccessoAttiStampaFoglioPrelievo;
	}

	public void setAbilRichAccessoAttiStampaFoglioPrelievo(Boolean abilRichAccessoAttiStampaFoglioPrelievo) {
		this.abilRichAccessoAttiStampaFoglioPrelievo = abilRichAccessoAttiStampaFoglioPrelievo;
	}

	public Boolean getAbilRichAccessoAttiChiusura() {
		return abilRichAccessoAttiChiusura;
	}

	public void setAbilRichAccessoAttiChiusura(Boolean abilRichAccessoAttiChiusura) {
		this.abilRichAccessoAttiChiusura = abilRichAccessoAttiChiusura;
	}

	public Boolean getAbilRichAccessoAttiRiapertura() {
		return abilRichAccessoAttiRiapertura;
	}

	public void setAbilRichAccessoAttiRiapertura(Boolean abilRichAccessoAttiRiapertura) {
		this.abilRichAccessoAttiRiapertura = abilRichAccessoAttiRiapertura;
	}

	public Boolean getAbilRichAccessoAttiRipristino() {
		return abilRichAccessoAttiRipristino;
	}

	public void setAbilRichAccessoAttiRipristino(Boolean abilRichAccessoAttiRipristino) {
		this.abilRichAccessoAttiRipristino = abilRichAccessoAttiRipristino;
	}

	public String getIdUDTrasmessaInUscitaCon() {
		return idUDTrasmessaInUscitaCon;
	}

	public void setIdUDTrasmessaInUscitaCon(String idUDTrasmessaInUscitaCon) {
		this.idUDTrasmessaInUscitaCon = idUDTrasmessaInUscitaCon;
	}

	public String getEstremiUDTrasmessaInUscitaCon() {
		return estremiUDTrasmessaInUscitaCon;
	}

	public void setEstremiUDTrasmessaInUscitaCon(String estremiUDTrasmessaInUscitaCon) {
		this.estremiUDTrasmessaInUscitaCon = estremiUDTrasmessaInUscitaCon;
	}		

	public String getIdRicercatoreVisuraSUE() {
		return idRicercatoreVisuraSUE;
	}

	public void setIdRicercatoreVisuraSUE(String idRicercatoreVisuraSUE) {
		this.idRicercatoreVisuraSUE = idRicercatoreVisuraSUE;
	}

	public String getDesRicercatoreVisuraSUE() {
		return desRicercatoreVisuraSUE;
	}

	public void setDesRicercatoreVisuraSUE(String desRicercatoreVisuraSUE) {
		this.desRicercatoreVisuraSUE = desRicercatoreVisuraSUE;
	}

	public Flag getFlgDettRevocaAtto() {
		return flgDettRevocaAtto;
	}

	public void setFlgDettRevocaAtto(Flag flgDettRevocaAtto) {
		this.flgDettRevocaAtto = flgDettRevocaAtto;
	}

	public String getCodPropostaSistContabile() {
		return codPropostaSistContabile;
	}

	public void setCodPropostaSistContabile(String codPropostaSistContabile) {
		this.codPropostaSistContabile = codPropostaSistContabile;
	}

	public Flag getFlgDelibera() {
		return flgDelibera;
	}

	public void setFlgDelibera(Flag flgDelibera) {
		this.flgDelibera = flgDelibera;
	}

	public String getShowDirigentiProponenti() {
		return showDirigentiProponenti;
	}

	public void setShowDirigentiProponenti(String showDirigentiProponenti) {
		this.showDirigentiProponenti = showDirigentiProponenti;
	}

	public String getShowAssessori() {
		return showAssessori;
	}

	public void setShowAssessori(String showAssessori) {
		this.showAssessori = showAssessori;
	}

	public String getShowConsiglieri() {
		return showConsiglieri;
	}

	public void setShowConsiglieri(String showConsiglieri) {
		this.showConsiglieri = showConsiglieri;
	}

	public String getIdScrivaniaAdottante() {
		return idScrivaniaAdottante;
	}

	public void setIdScrivaniaAdottante(String idScrivaniaAdottante) {
		this.idScrivaniaAdottante = idScrivaniaAdottante;
	}

	public String getDesScrivaniaAdottante() {
		return desScrivaniaAdottante;
	}

	public void setDesScrivaniaAdottante(String desScrivaniaAdottante) {
		this.desScrivaniaAdottante = desScrivaniaAdottante;
	}

	public String getCodUOScrivaniaAdottante() {
		return codUOScrivaniaAdottante;
	}

	public void setCodUOScrivaniaAdottante(String codUOScrivaniaAdottante) {
		this.codUOScrivaniaAdottante = codUOScrivaniaAdottante;
	}

	public Flag getFlgAdottanteAncheRespProc() {
		return flgAdottanteAncheRespProc;
	}

	public void setFlgAdottanteAncheRespProc(Flag flgAdottanteAncheRespProc) {
		this.flgAdottanteAncheRespProc = flgAdottanteAncheRespProc;
	}

	public Flag getFlgAdottanteAncheRUP() {
		return flgAdottanteAncheRUP;
	}

	public void setFlgAdottanteAncheRUP(Flag flgAdottanteAncheRUP) {
		this.flgAdottanteAncheRUP = flgAdottanteAncheRUP;
	}
	
	public String getCentroDiCosto() {
		return centroDiCosto;
	}

	public void setCentroDiCosto(String centroDiCosto) {
		this.centroDiCosto = centroDiCosto;
	}

	public String getIdScrivaniaRespProc() {
		return idScrivaniaRespProc;
	}

	public void setIdScrivaniaRespProc(String idScrivaniaRespProc) {
		this.idScrivaniaRespProc = idScrivaniaRespProc;
	}

	public String getDesScrivaniaRespProc() {
		return desScrivaniaRespProc;
	}

	public void setDesScrivaniaRespProc(String desScrivaniaRespProc) {
		this.desScrivaniaRespProc = desScrivaniaRespProc;
	}

	public String getCodUOScrivaniaRespProc() {
		return codUOScrivaniaRespProc;
	}

	public void setCodUOScrivaniaRespProc(String codUOScrivaniaRespProc) {
		this.codUOScrivaniaRespProc = codUOScrivaniaRespProc;
	}

	public Flag getFlgRespProcAncheRUP() {
		return flgRespProcAncheRUP;
	}

	public void setFlgRespProcAncheRUP(Flag flgRespProcAncheRUP) {
		this.flgRespProcAncheRUP = flgRespProcAncheRUP;
	}

	public String getIdScrivaniaRUP() {
		return idScrivaniaRUP;
	}

	public void setIdScrivaniaRUP(String idScrivaniaRUP) {
		this.idScrivaniaRUP = idScrivaniaRUP;
	}

	public String getDesScrivaniaRUP() {
		return desScrivaniaRUP;
	}

	public void setDesScrivaniaRUP(String desScrivaniaRUP) {
		this.desScrivaniaRUP = desScrivaniaRUP;
	}

	public String getCodUOScrivaniaRUP() {
		return codUOScrivaniaRUP;
	}

	public void setCodUOScrivaniaRUP(String codUOScrivaniaRUP) {
		this.codUOScrivaniaRUP = codUOScrivaniaRUP;
	}

	public Flag getFlgRichiediVistoDirettore() {
		return flgRichiediVistoDirettore;
	}

	public void setFlgRichiediVistoDirettore(Flag flgRichiediVistoDirettore) {
		this.flgRichiediVistoDirettore = flgRichiediVistoDirettore;
	}

	public List<RespDiConcertoBean> getResponsabiliDiConcerto() {
		return responsabiliDiConcerto;
	}

	public void setResponsabiliDiConcerto(List<RespDiConcertoBean> responsabiliDiConcerto) {
		this.responsabiliDiConcerto = responsabiliDiConcerto;
	}

	public List<ScrivaniaDirProponenteBean> getDirigentiProponenti() {
		return dirigentiProponenti;
	}

	public void setDirigentiProponenti(List<ScrivaniaDirProponenteBean> dirigentiProponenti) {
		this.dirigentiProponenti = dirigentiProponenti;
	}

	public List<AssessoreProponenteBean> getAssessoriProponenti() {
		return assessoriProponenti;
	}

	public void setAssessoriProponenti(List<AssessoreProponenteBean> assessoriProponenti) {
		this.assessoriProponenti = assessoriProponenti;
	}

	public List<ConsigliereProponenteBean> getConsiglieriProponenti() {
		return consiglieriProponenti;
	}

	public void setConsiglieriProponenti(List<ConsigliereProponenteBean> consiglieriProponenti) {
		this.consiglieriProponenti = consiglieriProponenti;
	}

	public List<ScrivaniaEstensoreBean> getEstensori() {
		return estensori;
	}

	public void setEstensori(List<ScrivaniaEstensoreBean> estensori) {
		this.estensori = estensori;
	}

	public List<RespSpesaBean> getResponsabiliSpesa() {
		return responsabiliSpesa;
	}

	public void setResponsabiliSpesa(List<RespSpesaBean> responsabiliSpesa) {
		this.responsabiliSpesa = responsabiliSpesa;
	}

	public String getIdUOCompSpesa() {
		return idUOCompSpesa;
	}

	public void setIdUOCompSpesa(String idUOCompSpesa) {
		this.idUOCompSpesa = idUOCompSpesa;
	}

	public String getCodUOCompSpesa() {
		return codUOCompSpesa;
	}

	public void setCodUOCompSpesa(String codUOCompSpesa) {
		this.codUOCompSpesa = codUOCompSpesa;
	}

	public String getDesUOCompSpesa() {
		return desUOCompSpesa;
	}

	public void setDesUOCompSpesa(String desUOCompSpesa) {
		this.desUOCompSpesa = desUOCompSpesa;
	}

	public Flag getFlgAdottanteUnicoRespSpesa() {
		return flgAdottanteUnicoRespSpesa;
	}

	public void setFlgAdottanteUnicoRespSpesa(Flag flgAdottanteUnicoRespSpesa) {
		this.flgAdottanteUnicoRespSpesa = flgAdottanteUnicoRespSpesa;
	}

	public List<ValueBean> getRiferimentiNormativi() {
		return riferimentiNormativi;
	}

	public void setRiferimentiNormativi(List<ValueBean> riferimentiNormativi) {
		this.riferimentiNormativi = riferimentiNormativi;
	}

	public String getFlgOpCommerciale() {
		return flgOpCommerciale;
	}

	public void setFlgOpCommerciale(String flgOpCommerciale) {
		this.flgOpCommerciale = flgOpCommerciale;
	}

	public Flag getFlgEscludiCIG() {
		return flgEscludiCIG;
	}

	public void setFlgEscludiCIG(Flag flgEscludiCIG) {
		this.flgEscludiCIG = flgEscludiCIG;
	}

	public String getMotivoEsclusioneCIG() {
		return motivoEsclusioneCIG;
	}

	public void setMotivoEsclusioneCIG(String motivoEsclusioneCIG) {
		this.motivoEsclusioneCIG = motivoEsclusioneCIG;
	}

	public List<ValueBean> getListaCIG() {
		return listaCIG;
	}

	public void setListaCIG(List<ValueBean> listaCIG) {
		this.listaCIG = listaCIG;
	}

	public String getAttiPresupposti() {
		return attiPresupposti;
	}

	public void setAttiPresupposti(String attiPresupposti) {
		this.attiPresupposti = attiPresupposti;
	}

	public String getMotivazioniAtto() {
		return motivazioniAtto;
	}

	public void setMotivazioniAtto(String motivazioniAtto) {
		this.motivazioniAtto = motivazioniAtto;
	}

	public String getPremessaAtto() {
		return premessaAtto;
	}

	public void setPremessaAtto(String premessaAtto) {
		this.premessaAtto = premessaAtto;
	}

	public String getPremessaAtto2() {
		return premessaAtto2;
	}

	public void setPremessaAtto2(String premessaAtto2) {
		this.premessaAtto2 = premessaAtto2;
	}

	public String getDispositivoAtto() {
		return dispositivoAtto;
	}

	public void setDispositivoAtto(String dispositivoAtto) {
		this.dispositivoAtto = dispositivoAtto;
	}

	public String getLoghiDispositivoAtto() {
		return loghiDispositivoAtto;
	}

	public void setLoghiDispositivoAtto(String loghiDispositivoAtto) {
		this.loghiDispositivoAtto = loghiDispositivoAtto;
	}

	public String getDispositivoAtto2() {
		return dispositivoAtto2;
	}

	public void setDispositivoAtto2(String dispositivoAtto2) {
		this.dispositivoAtto2 = dispositivoAtto2;
	}

	public Flag getFlgPubblicaAllegatiSeparati() {
		return flgPubblicaAllegatiSeparati;
	}

	public void setFlgPubblicaAllegatiSeparati(Flag flgPubblicaAllegatiSeparati) {
		this.flgPubblicaAllegatiSeparati = flgPubblicaAllegatiSeparati;
	}

	public String getFlgPrivacy() {
		return flgPrivacy;
	}

	public void setFlgPrivacy(String flgPrivacy) {
		this.flgPrivacy = flgPrivacy;
	}
	
	public Flag getFlgDatiProtettiTipo1() {
		return flgDatiProtettiTipo1;
	}

	public void setFlgDatiProtettiTipo1(Flag flgDatiProtettiTipo1) {
		this.flgDatiProtettiTipo1 = flgDatiProtettiTipo1;
	}

	public Flag getFlgDatiProtettiTipo2() {
		return flgDatiProtettiTipo2;
	}

	public void setFlgDatiProtettiTipo2(Flag flgDatiProtettiTipo2) {
		this.flgDatiProtettiTipo2 = flgDatiProtettiTipo2;
	}

	public Flag getFlgDatiProtettiTipo3() {
		return flgDatiProtettiTipo3;
	}

	public void setFlgDatiProtettiTipo3(Flag flgDatiProtettiTipo3) {
		this.flgDatiProtettiTipo3 = flgDatiProtettiTipo3;
	}

	public Flag getFlgDatiProtettiTipo4() {
		return flgDatiProtettiTipo4;
	}

	public void setFlgDatiProtettiTipo4(Flag flgDatiProtettiTipo4) {
		this.flgDatiProtettiTipo4 = flgDatiProtettiTipo4;
	}

	public String getFlgPubblAlbo() {
		return flgPubblAlbo;
	}

	public void setFlgPubblAlbo(String flgPubblAlbo) {
		this.flgPubblAlbo = flgPubblAlbo;
	}

	public String getNumGiorniPubblAlbo() {
		return numGiorniPubblAlbo;
	}

	public void setNumGiorniPubblAlbo(String numGiorniPubblAlbo) {
		this.numGiorniPubblAlbo = numGiorniPubblAlbo;
	}

	public String getTipoDecorrenzaPubblAlbo() {
		return tipoDecorrenzaPubblAlbo;
	}

	public void setTipoDecorrenzaPubblAlbo(String tipoDecorrenzaPubblAlbo) {
		this.tipoDecorrenzaPubblAlbo = tipoDecorrenzaPubblAlbo;
	}

	public Date getDataPubblAlboDal() {
		return dataPubblAlboDal;
	}

	public void setDataPubblAlboDal(Date dataPubblAlboDal) {
		this.dataPubblAlboDal = dataPubblAlboDal;
	}

	public Flag getFlgUrgentePubblAlbo() {
		return flgUrgentePubblAlbo;
	}

	public void setFlgUrgentePubblAlbo(Flag flgUrgentePubblAlbo) {
		this.flgUrgentePubblAlbo = flgUrgentePubblAlbo;
	}

	public Date getDataPubblAlboEntro() {
		return dataPubblAlboEntro;
	}

	public void setDataPubblAlboEntro(Date dataPubblAlboEntro) {
		this.dataPubblAlboEntro = dataPubblAlboEntro;
	}

	public String getFlgPubblAmmTrasp() {
		return flgPubblAmmTrasp;
	}

	public void setFlgPubblAmmTrasp(String flgPubblAmmTrasp) {
		this.flgPubblAmmTrasp = flgPubblAmmTrasp;
	}

	public String getSezionePubblAmmTrasp() {
		return sezionePubblAmmTrasp;
	}

	public void setSezionePubblAmmTrasp(String sezionePubblAmmTrasp) {
		this.sezionePubblAmmTrasp = sezionePubblAmmTrasp;
	}

	public String getSottoSezionePubblAmmTrasp() {
		return sottoSezionePubblAmmTrasp;
	}

	public void setSottoSezionePubblAmmTrasp(String sottoSezionePubblAmmTrasp) {
		this.sottoSezionePubblAmmTrasp = sottoSezionePubblAmmTrasp;
	}

	public String getFlgPubblBUR() {
		return flgPubblBUR;
	}

	public void setFlgPubblBUR(String flgPubblBUR) {
		this.flgPubblBUR = flgPubblBUR;
	}

	public String getAnnoTerminePubblBUR() {
		return annoTerminePubblBUR;
	}

	public void setAnnoTerminePubblBUR(String annoTerminePubblBUR) {
		this.annoTerminePubblBUR = annoTerminePubblBUR;
	}

	public String getTipoDecorrenzaPubblBUR() {
		return tipoDecorrenzaPubblBUR;
	}

	public void setTipoDecorrenzaPubblBUR(String tipoDecorrenzaPubblBUR) {
		this.tipoDecorrenzaPubblBUR = tipoDecorrenzaPubblBUR;
	}

	public Date getDataPubblBURDal() {
		return dataPubblBURDal;
	}

	public void setDataPubblBURDal(Date dataPubblBURDal) {
		this.dataPubblBURDal = dataPubblBURDal;
	}

	public Flag getFlgUrgentePubblBUR() {
		return flgUrgentePubblBUR;
	}

	public void setFlgUrgentePubblBUR(Flag flgUrgentePubblBUR) {
		this.flgUrgentePubblBUR = flgUrgentePubblBUR;
	}

	public Date getDataPubblBUREntro() {
		return dataPubblBUREntro;
	}

	public void setDataPubblBUREntro(Date dataPubblBUREntro) {
		this.dataPubblBUREntro = dataPubblBUREntro;
	}

	public Date getDtEsecutivita() {
		return dtEsecutivita;
	}

	public void setDtEsecutivita(Date dtEsecutivita) {
		this.dtEsecutivita = dtEsecutivita;
	}

	public Flag getFlgImmediatamenteEseg() {
		return flgImmediatamenteEseg;
	}

	public void setFlgImmediatamenteEseg(Flag flgImmediatamenteEseg) {
		this.flgImmediatamenteEseg = flgImmediatamenteEseg;
	}

	public String getMotiviImmediatamenteEseguibile() {
		return motiviImmediatamenteEseguibile;
	}

	public void setMotiviImmediatamenteEseguibile(String motiviImmediatamenteEseguibile) {
		this.motiviImmediatamenteEseguibile = motiviImmediatamenteEseguibile;
	}

	public List<DestNotificaAttoXmlBean> getListaDestNotificaAtto() {
		return listaDestNotificaAtto;
	}

	public void setListaDestNotificaAtto(List<DestNotificaAttoXmlBean> listaDestNotificaAtto) {
		this.listaDestNotificaAtto = listaDestNotificaAtto;
	}

	public Flag getFlgDetContrConGara() {
		return flgDetContrConGara;
	}

	public void setFlgDetContrConGara(Flag flgDetContrConGara) {
		this.flgDetContrConGara = flgDetContrConGara;
	}

	public Flag getFlgDetAggiudicaGara() {
		return flgDetAggiudicaGara;
	}

	public void setFlgDetAggiudicaGara(Flag flgDetAggiudicaGara) {
		this.flgDetAggiudicaGara = flgDetAggiudicaGara;
	}

	public Flag getFlgDetRimodSpesaGaraAggiud() {
		return flgDetRimodSpesaGaraAggiud;
	}

	public void setFlgDetRimodSpesaGaraAggiud(Flag flgDetRimodSpesaGaraAggiud) {
		this.flgDetRimodSpesaGaraAggiud = flgDetRimodSpesaGaraAggiud;
	}

	public Flag getFlgDetPersonale() {
		return flgDetPersonale;
	}

	public void setFlgDetPersonale(Flag flgDetPersonale) {
		this.flgDetPersonale = flgDetPersonale;
	}

	public Flag getFlgDetDiConcerto() {
		return flgDetDiConcerto;
	}

	public void setFlgDetDiConcerto(Flag flgDetDiConcerto) {
		this.flgDetDiConcerto = flgDetDiConcerto;
	}

	public String getFlgDetConSpesa() {
		return flgDetConSpesa;
	}

	public void setFlgDetConSpesa(String flgDetConSpesa) {
		this.flgDetConSpesa = flgDetConSpesa;
	}

	public Flag getFlgRichVerificaDiBilancioCorrente() {
		return flgRichVerificaDiBilancioCorrente;
	}

	public void setFlgRichVerificaDiBilancioCorrente(Flag flgRichVerificaDiBilancioCorrente) {
		this.flgRichVerificaDiBilancioCorrente = flgRichVerificaDiBilancioCorrente;
	}

	public Flag getFlgRichVerificaDiBilancioContoCapitale() {
		return flgRichVerificaDiBilancioContoCapitale;
	}

	public void setFlgRichVerificaDiBilancioContoCapitale(Flag flgRichVerificaDiBilancioContoCapitale) {
		this.flgRichVerificaDiBilancioContoCapitale = flgRichVerificaDiBilancioContoCapitale;
	}

	public Flag getFlgRichVerificaDiContabilita() {
		return flgRichVerificaDiContabilita;
	}

	public void setFlgRichVerificaDiContabilita(Flag flgRichVerificaDiContabilita) {
		this.flgRichVerificaDiContabilita = flgRichVerificaDiContabilita;
	}

	public Flag getFlgRichPresaVisContabilita() {
		return flgRichPresaVisContabilita;
	}

	public void setFlgRichPresaVisContabilita(Flag flgRichPresaVisContabilita) {
		this.flgRichPresaVisContabilita = flgRichPresaVisContabilita;
	}

	public Flag getFlgDetConSpesaCorrente() {
		return flgDetConSpesaCorrente;
	}

	public void setFlgDetConSpesaCorrente(Flag flgDetConSpesaCorrente) {
		this.flgDetConSpesaCorrente = flgDetConSpesaCorrente;
	}

	public Flag getFlgDetConImpCorrValid() {
		return flgDetConImpCorrValid;
	}

	public void setFlgDetConImpCorrValid(Flag flgDetConImpCorrValid) {
		this.flgDetConImpCorrValid = flgDetConImpCorrValid;
	}

	public Flag getFlgDetConSpesaContoCap() {
		return flgDetConSpesaContoCap;
	}

	public void setFlgDetConSpesaContoCap(Flag flgDetConSpesaContoCap) {
		this.flgDetConSpesaContoCap = flgDetConSpesaContoCap;
	}

	public Flag getFlgDetConImpCCapRil() {
		return flgDetConImpCCapRil;
	}

	public void setFlgDetConImpCCapRil(Flag flgDetConImpCCapRil) {
		this.flgDetConImpCCapRil = flgDetConImpCCapRil;
	}

	public Flag getFlgSoloSubImpSubCrono() {
		return flgSoloSubImpSubCrono;
	}

	public void setFlgSoloSubImpSubCrono(Flag flgSoloSubImpSubCrono) {
		this.flgSoloSubImpSubCrono = flgSoloSubImpSubCrono;
	}

	public Flag getFlgRichVerificaContabilita() {
		return flgRichVerificaContabilita;
	}

	public void setFlgRichVerificaContabilita(Flag flgRichVerificaContabilita) {
		this.flgRichVerificaContabilita = flgRichVerificaContabilita;
	}

	public Flag getFlgRichParereRevContabili() {
		return flgRichParereRevContabili;
	}

	public void setFlgRichParereRevContabili(Flag flgRichParereRevContabili) {
		this.flgRichParereRevContabili = flgRichParereRevContabili;
	}
	
	public Flag getFlgDisattivaAutoRequestDatiContabiliSIBCorrente() {
		return flgDisattivaAutoRequestDatiContabiliSIBCorrente;
	}

	public void setFlgDisattivaAutoRequestDatiContabiliSIBCorrente(Flag flgDisattivaAutoRequestDatiContabiliSIBCorrente) {
		this.flgDisattivaAutoRequestDatiContabiliSIBCorrente = flgDisattivaAutoRequestDatiContabiliSIBCorrente;
	}

	public String getPrenotazioneSpesaSIBDiCorrente() {
		return prenotazioneSpesaSIBDiCorrente;
	}

	public void setPrenotazioneSpesaSIBDiCorrente(String prenotazioneSpesaSIBDiCorrente) {
		this.prenotazioneSpesaSIBDiCorrente = prenotazioneSpesaSIBDiCorrente;
	}

	public String getModalitaInvioDatiSpesaARagioneriaCorrente() {
		return modalitaInvioDatiSpesaARagioneriaCorrente;
	}

	public void setModalitaInvioDatiSpesaARagioneriaCorrente(String modalitaInvioDatiSpesaARagioneriaCorrente) {
		this.modalitaInvioDatiSpesaARagioneriaCorrente = modalitaInvioDatiSpesaARagioneriaCorrente;
	}

	public List<DatiContabiliXmlBean> getListaDatiContabiliSIBCorrente() {
		return listaDatiContabiliSIBCorrente;
	}

	public void setListaDatiContabiliSIBCorrente(List<DatiContabiliXmlBean> listaDatiContabiliSIBCorrente) {
		this.listaDatiContabiliSIBCorrente = listaDatiContabiliSIBCorrente;
	}

	public List<DatiContabiliXmlBean> getListaInvioDatiSpesaCorrente() {
		return listaInvioDatiSpesaCorrente;
	}

	public void setListaInvioDatiSpesaCorrente(List<DatiContabiliXmlBean> listaInvioDatiSpesaCorrente) {
		this.listaInvioDatiSpesaCorrente = listaInvioDatiSpesaCorrente;
	}

	public String getFileXlsCorrente() {
		return fileXlsCorrente;
	}

	public void setFileXlsCorrente(String fileXlsCorrente) {
		this.fileXlsCorrente = fileXlsCorrente;
	}

	public String getNoteCorrente() {
		return noteCorrente;
	}

	public void setNoteCorrente(String noteCorrente) {
		this.noteCorrente = noteCorrente;
	}

	public Flag getFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale() {
		return flgDisattivaAutoRequestDatiContabiliSIBContoCapitale;
	}

	public void setFlgDisattivaAutoRequestDatiContabiliSIBContoCapitale(Flag flgDisattivaAutoRequestDatiContabiliSIBContoCapitale) {
		this.flgDisattivaAutoRequestDatiContabiliSIBContoCapitale = flgDisattivaAutoRequestDatiContabiliSIBContoCapitale;
	}

	public String getModalitaInvioDatiSpesaARagioneriaContoCapitale() {
		return modalitaInvioDatiSpesaARagioneriaContoCapitale;
	}

	public void setModalitaInvioDatiSpesaARagioneriaContoCapitale(String modalitaInvioDatiSpesaARagioneriaContoCapitale) {
		this.modalitaInvioDatiSpesaARagioneriaContoCapitale = modalitaInvioDatiSpesaARagioneriaContoCapitale;
	}

	public List<DatiContabiliXmlBean> getListaDatiContabiliSIBContoCapitale() {
		return listaDatiContabiliSIBContoCapitale;
	}

	public void setListaDatiContabiliSIBContoCapitale(List<DatiContabiliXmlBean> listaDatiContabiliSIBContoCapitale) {
		this.listaDatiContabiliSIBContoCapitale = listaDatiContabiliSIBContoCapitale;
	}

	public List<DatiContabiliXmlBean> getListaInvioDatiSpesaContoCapitale() {
		return listaInvioDatiSpesaContoCapitale;
	}

	public void setListaInvioDatiSpesaContoCapitale(List<DatiContabiliXmlBean> listaInvioDatiSpesaContoCapitale) {
		this.listaInvioDatiSpesaContoCapitale = listaInvioDatiSpesaContoCapitale;
	}

	public String getFileXlsContoCapitale() {
		return fileXlsContoCapitale;
	}

	public void setFileXlsContoCapitale(String fileXlsContoCapitale) {
		this.fileXlsContoCapitale = fileXlsContoCapitale;
	}

	public String getNoteContoCapitale() {
		return noteContoCapitale;
	}

	public void setNoteContoCapitale(String noteContoCapitale) {
		this.noteContoCapitale = noteContoCapitale;
	}

	public List<DatiSpesaAnnualiXDetPersonaleXmlBean> getListaDatiSpesaAnnualiXDetPersonale() {
		return listaDatiSpesaAnnualiXDetPersonale;
	}

	public void setListaDatiSpesaAnnualiXDetPersonale(
			List<DatiSpesaAnnualiXDetPersonaleXmlBean> listaDatiSpesaAnnualiXDetPersonale) {
		this.listaDatiSpesaAnnualiXDetPersonale = listaDatiSpesaAnnualiXDetPersonale;
	}

	public String getCapitoloDatiSpesaAnnuaXDetPers() {
		return capitoloDatiSpesaAnnuaXDetPers;
	}

	public void setCapitoloDatiSpesaAnnuaXDetPers(String capitoloDatiSpesaAnnuaXDetPers) {
		this.capitoloDatiSpesaAnnuaXDetPers = capitoloDatiSpesaAnnuaXDetPers;
	}

	public String getArticoloDatiSpesaAnnuaXDetPers() {
		return articoloDatiSpesaAnnuaXDetPers;
	}

	public void setArticoloDatiSpesaAnnuaXDetPers(String articoloDatiSpesaAnnuaXDetPers) {
		this.articoloDatiSpesaAnnuaXDetPers = articoloDatiSpesaAnnuaXDetPers;
	}

	public String getNumeroDatiSpesaAnnuaXDetPers() {
		return numeroDatiSpesaAnnuaXDetPers;
	}

	public void setNumeroDatiSpesaAnnuaXDetPers(String numeroDatiSpesaAnnuaXDetPers) {
		this.numeroDatiSpesaAnnuaXDetPers = numeroDatiSpesaAnnuaXDetPers;
	}

	public String getImportoDatiSpesaAnnuaXDetPers() {
		return importoDatiSpesaAnnuaXDetPers;
	}

	public void setImportoDatiSpesaAnnuaXDetPers(String importoDatiSpesaAnnuaXDetPers) {
		this.importoDatiSpesaAnnuaXDetPers = importoDatiSpesaAnnuaXDetPers;
	}

	public String getOggettoHtml() {
		return oggettoHtml;
	}

	public void setOggettoHtml(String oggettoHtml) {
		this.oggettoHtml = oggettoHtml;
	}

	public String getTestoPropostaRevisioneOrganigramma() {
		return testoPropostaRevisioneOrganigramma;
	}

	public void setTestoPropostaRevisioneOrganigramma(String testoPropostaRevisioneOrganigramma) {
		this.testoPropostaRevisioneOrganigramma = testoPropostaRevisioneOrganigramma;
	}

	public String getTipoAttoEmendamento() {
		return tipoAttoEmendamento;
	}

	public void setTipoAttoEmendamento(String tipoAttoEmendamento) {
		this.tipoAttoEmendamento = tipoAttoEmendamento;
	}

	public String getSiglaAttoEmendamento() {
		return siglaAttoEmendamento;
	}

	public void setSiglaAttoEmendamento(String siglaAttoEmendamento) {
		this.siglaAttoEmendamento = siglaAttoEmendamento;
	}

	public String getNumeroAttoEmendamento() {
		return numeroAttoEmendamento;
	}

	public void setNumeroAttoEmendamento(String numeroAttoEmendamento) {
		this.numeroAttoEmendamento = numeroAttoEmendamento;
	}

	public String getAnnoAttoEmendamento() {
		return annoAttoEmendamento;
	}

	public void setAnnoAttoEmendamento(String annoAttoEmendamento) {
		this.annoAttoEmendamento = annoAttoEmendamento;
	}

	public String getIdEmendamento() {
		return idEmendamento;
	}

	public void setIdEmendamento(String idEmendamento) {
		this.idEmendamento = idEmendamento;
	}

	public String getNumeroEmendamento() {
		return numeroEmendamento;
	}

	public void setNumeroEmendamento(String numeroEmendamento) {
		this.numeroEmendamento = numeroEmendamento;
	}

	public String getFlgEmendamentoSuFile() {
		return flgEmendamentoSuFile;
	}

	public void setFlgEmendamentoSuFile(String flgEmendamentoSuFile) {
		this.flgEmendamentoSuFile = flgEmendamentoSuFile;
	}

	public String getNumeroAllegatoEmendamento() {
		return numeroAllegatoEmendamento;
	}

	public void setNumeroAllegatoEmendamento(String numeroAllegatoEmendamento) {
		this.numeroAllegatoEmendamento = numeroAllegatoEmendamento;
	}

	public Flag getFlgEmendamentoIntegraleAllegato() {
		return flgEmendamentoIntegraleAllegato;
	}

	public void setFlgEmendamentoIntegraleAllegato(Flag flgEmendamentoIntegraleAllegato) {
		this.flgEmendamentoIntegraleAllegato = flgEmendamentoIntegraleAllegato;
	}

	public String getNumeroPaginaEmendamento() {
		return numeroPaginaEmendamento;
	}

	public void setNumeroPaginaEmendamento(String numeroPaginaEmendamento) {
		this.numeroPaginaEmendamento = numeroPaginaEmendamento;
	}

	public String getNumeroRigaEmendamento() {
		return numeroRigaEmendamento;
	}

	public void setNumeroRigaEmendamento(String numeroRigaEmendamento) {
		this.numeroRigaEmendamento = numeroRigaEmendamento;
	}

	public String getEffettoEmendamento() {
		return effettoEmendamento;
	}

	public void setEffettoEmendamento(String effettoEmendamento) {
		this.effettoEmendamento = effettoEmendamento;
	}
	
	public Flag getFlgAttivaDestinatari() {
		return flgAttivaDestinatari;
	}

	public void setFlgAttivaDestinatari(Flag flgAttivaDestinatari) {
		this.flgAttivaDestinatari = flgAttivaDestinatari;
	}

	public List<DestinatarioAttoBean> getListaDestinatariAtto() {
		return listaDestinatariAtto;
	}

	public void setListaDestinatariAtto(List<DestinatarioAttoBean> listaDestinatariAtto) {
		this.listaDestinatariAtto = listaDestinatariAtto;
	}

	public List<DestinatarioAttoBean> getListaDestinatariPCAtto() {
		return listaDestinatariPCAtto;
	}

	public void setListaDestinatariPCAtto(List<DestinatarioAttoBean> listaDestinatariPCAtto) {
		this.listaDestinatariPCAtto = listaDestinatariPCAtto;
	}

	public String getIniziativaPropAtto() {
		return iniziativaPropAtto;
	}

	public void setIniziativaPropAtto(String iniziativaPropAtto) {
		this.iniziativaPropAtto = iniziativaPropAtto;
	}

	public Flag getFlgAttoMeroIndirizzo() {
		return flgAttoMeroIndirizzo;
	}

	public void setFlgAttoMeroIndirizzo(Flag flgAttoMeroIndirizzo) {
		this.flgAttoMeroIndirizzo = flgAttoMeroIndirizzo;
	}

	public Flag getFlgModificaRegolamento() {
		return flgModificaRegolamento;
	}

	public void setFlgModificaRegolamento(Flag flgModificaRegolamento) {
		this.flgModificaRegolamento = flgModificaRegolamento;
	}

	public Flag getFlgModificaStatuto() {
		return flgModificaStatuto;
	}

	public void setFlgModificaStatuto(Flag flgModificaStatuto) {
		this.flgModificaStatuto = flgModificaStatuto;
	}

	public Flag getFlgNomina() {
		return flgNomina;
	}

	public void setFlgNomina(Flag flgNomina) {
		this.flgNomina = flgNomina;
	}

	public Flag getFlgRatificaDeliberaUrgenza() {
		return flgRatificaDeliberaUrgenza;
	}

	public void setFlgRatificaDeliberaUrgenza(Flag flgRatificaDeliberaUrgenza) {
		this.flgRatificaDeliberaUrgenza = flgRatificaDeliberaUrgenza;
	}

	public Flag getFlgAttoUrgente() {
		return flgAttoUrgente;
	}

	public void setFlgAttoUrgente(Flag flgAttoUrgente) {
		this.flgAttoUrgente = flgAttoUrgente;
	}

	public List<KeyValueBean> getCircoscrizioniProponenti() {
		return circoscrizioniProponenti;
	}

	public void setCircoscrizioniProponenti(List<KeyValueBean> circoscrizioniProponenti) {
		this.circoscrizioniProponenti = circoscrizioniProponenti;
	}

	public String getTipoInterpellanza() {
		return tipoInterpellanza;
	}

	public void setTipoInterpellanza(String tipoInterpellanza) {
		this.tipoInterpellanza = tipoInterpellanza;
	}

	public String getTipoOrdMobilita() {
		return tipoOrdMobilita;
	}

	public void setTipoOrdMobilita(String tipoOrdMobilita) {
		this.tipoOrdMobilita = tipoOrdMobilita;
	}

	public Date getDataInizioVldOrdinanza() {
		return dataInizioVldOrdinanza;
	}

	public void setDataInizioVldOrdinanza(Date dataInizioVldOrdinanza) {
		this.dataInizioVldOrdinanza = dataInizioVldOrdinanza;
	}

	public Date getDataFineVldOrdinanza() {
		return dataFineVldOrdinanza;
	}

	public void setDataFineVldOrdinanza(Date dataFineVldOrdinanza) {
		this.dataFineVldOrdinanza = dataFineVldOrdinanza;
	}

	public String getTipoLuogoOrdMobilita() {
		return tipoLuogoOrdMobilita;
	}

	public void setTipoLuogoOrdMobilita(String tipoLuogoOrdMobilita) {
		this.tipoLuogoOrdMobilita = tipoLuogoOrdMobilita;
	}

	public String getLuogoOrdMobilita() {
		return luogoOrdMobilita;
	}

	public void setLuogoOrdMobilita(String luogoOrdMobilita) {
		this.luogoOrdMobilita = luogoOrdMobilita;
	}

	public List<KeyValueBean> getCircoscrizioniOrdMobilita() {
		return circoscrizioniOrdMobilita;
	}

	public void setCircoscrizioniOrdMobilita(List<KeyValueBean> circoscrizioniOrdMobilita) {
		this.circoscrizioniOrdMobilita = circoscrizioniOrdMobilita;
	}
	
	public String getDescrizioneOrdMobilita() {
		return descrizioneOrdMobilita;
	}

	public void setDescrizioneOrdMobilita(String descrizioneOrdMobilita) {
		this.descrizioneOrdMobilita = descrizioneOrdMobilita;
	}

	public Flag getFlgDetRiaccert() {
		return flgDetRiaccert;
	}

	public void setFlgDetRiaccert(Flag flgDetRiaccert) {
		this.flgDetRiaccert = flgDetRiaccert;
	}

	public Flag getFlgCorteConti() {
		return flgCorteConti;
	}

	public void setFlgCorteConti(Flag flgCorteConti) {
		this.flgCorteConti = flgCorteConti;
	}

	public String getTipoAttoInDelPeg() {
		return tipoAttoInDelPeg;
	}

	public void setTipoAttoInDelPeg(String tipoAttoInDelPeg) {
		this.tipoAttoInDelPeg = tipoAttoInDelPeg;
	}

	public String getTipoAffidamento() {
		return tipoAffidamento;
	}

	public void setTipoAffidamento(String tipoAffidamento) {
		this.tipoAffidamento = tipoAffidamento;
	}

	public String getMateriaNaturaAtto() {
		return materiaNaturaAtto;
	}

	public void setMateriaNaturaAtto(String materiaNaturaAtto) {
		this.materiaNaturaAtto = materiaNaturaAtto;
	}

	public String getDesMateriaNaturaAtto() {
		return desMateriaNaturaAtto;
	}

	public void setDesMateriaNaturaAtto(String desMateriaNaturaAtto) {
		this.desMateriaNaturaAtto = desMateriaNaturaAtto;
	}
	
	public Flag getFlgFondiEuropeiPON() {
		return flgFondiEuropeiPON;
	}

	public void setFlgFondiEuropeiPON(Flag flgFondiEuropeiPON) {
		this.flgFondiEuropeiPON = flgFondiEuropeiPON;
	}

	public Flag getFlgFondiPRU() {
		return flgFondiPRU;
	}

	public void setFlgFondiPRU(Flag flgFondiPRU) {
		this.flgFondiPRU = flgFondiPRU;
	}

	public Flag getFlgVistoPar117_2013() {
		return flgVistoPar117_2013;
	}

	public void setFlgVistoPar117_2013(Flag flgVistoPar117_2013) {
		this.flgVistoPar117_2013 = flgVistoPar117_2013;
	}

	public Flag getFlgNotificaDaMessi() {
		return flgNotificaDaMessi;
	}

	public void setFlgNotificaDaMessi(Flag flgNotificaDaMessi) {
		this.flgNotificaDaMessi = flgNotificaDaMessi;
	}

	public String getFlgLLPP() {
		return flgLLPP;
	}

	public void setFlgLLPP(String flgLLPP) {
		this.flgLLPP = flgLLPP;
	}

	public String getAnnoProgettoLLPP() {
		return annoProgettoLLPP;
	}

	public void setAnnoProgettoLLPP(String annoProgettoLLPP) {
		this.annoProgettoLLPP = annoProgettoLLPP;
	}

	public String getNumProgettoLLPP() {
		return numProgettoLLPP;
	}

	public void setNumProgettoLLPP(String numProgettoLLPP) {
		this.numProgettoLLPP = numProgettoLLPP;
	}

	public String getFlgBeniServizi() {
		return flgBeniServizi;
	}

	public void setFlgBeniServizi(String flgBeniServizi) {
		this.flgBeniServizi = flgBeniServizi;
	}

	public String getAnnoProgettoBeniServizi() {
		return annoProgettoBeniServizi;
	}

	public void setAnnoProgettoBeniServizi(String annoProgettoBeniServizi) {
		this.annoProgettoBeniServizi = annoProgettoBeniServizi;
	}

	public String getNumProgettoBeniServizi() {
		return numProgettoBeniServizi;
	}

	public void setNumProgettoBeniServizi(String numProgettoBeniServizi) {
		this.numProgettoBeniServizi = numProgettoBeniServizi;
	}

	public String getCdCUOProponente() {
		return cdCUOProponente;
	}

	public void setCdCUOProponente(String cdCUOProponente) {
		this.cdCUOProponente = cdCUOProponente;
	}

	public Flag getFlgVistoDirSupUOProponente() {
		return flgVistoDirSupUOProponente;
	}

	public void setFlgVistoDirSupUOProponente(Flag flgVistoDirSupUOProponente) {
		this.flgVistoDirSupUOProponente = flgVistoDirSupUOProponente;
	}

	public String getIdScrivaniaDirRespRegTecnica() {
		return idScrivaniaDirRespRegTecnica;
	}

	public void setIdScrivaniaDirRespRegTecnica(String idScrivaniaDirRespRegTecnica) {
		this.idScrivaniaDirRespRegTecnica = idScrivaniaDirRespRegTecnica;
	}

	public String getLivelliUOScrivaniaDirRespRegTecnica() {
		return livelliUOScrivaniaDirRespRegTecnica;
	}

	public void setLivelliUOScrivaniaDirRespRegTecnica(String livelliUOScrivaniaDirRespRegTecnica) {
		this.livelliUOScrivaniaDirRespRegTecnica = livelliUOScrivaniaDirRespRegTecnica;
	}

	public String getDesScrivaniaDirRespRegTecnica() {
		return desScrivaniaDirRespRegTecnica;
	}

	public void setDesScrivaniaDirRespRegTecnica(String desScrivaniaDirRespRegTecnica) {
		this.desScrivaniaDirRespRegTecnica = desScrivaniaDirRespRegTecnica;
	}

	public Flag getFlgDirAncheRespProc() {
		return flgDirAncheRespProc;
	}

	public void setFlgDirAncheRespProc(Flag flgDirAncheRespProc) {
		this.flgDirAncheRespProc = flgDirAncheRespProc;
	}

	public Flag getFlgDirAncheRUP() {
		return flgDirAncheRUP;
	}

	public void setFlgDirAncheRUP(Flag flgDirAncheRUP) {
		this.flgDirAncheRUP = flgDirAncheRUP;
	}

	public List<DirRespRegTecnicaBean> getAltriDirRespTecnica() {
		return altriDirRespTecnica;
	}

	public void setAltriDirRespTecnica(List<DirRespRegTecnicaBean> altriDirRespTecnica) {
		this.altriDirRespTecnica = altriDirRespTecnica;
	}

	public String getIdAssessoreProponente() {
		return idAssessoreProponente;
	}

	public void setIdAssessoreProponente(String idAssessoreProponente) {
		this.idAssessoreProponente = idAssessoreProponente;
	}

	public String getDesAssessoreProponente() {
		return desAssessoreProponente;
	}

	public void setDesAssessoreProponente(String desAssessoreProponente) {
		this.desAssessoreProponente = desAssessoreProponente;
	}

	public List<AssessoreProponenteBean> getAltriAssessori() {
		return altriAssessori;
	}

	public void setAltriAssessori(List<AssessoreProponenteBean> altriAssessori) {
		this.altriAssessori = altriAssessori;
	}

	public String getIdConsigliereProponente() {
		return idConsigliereProponente;
	}

	public void setIdConsigliereProponente(String idConsigliereProponente) {
		this.idConsigliereProponente = idConsigliereProponente;
	}

	public String getDesConsigliereProponente() {
		return desConsigliereProponente;
	}

	public void setDesConsigliereProponente(String desConsigliereProponente) {
		this.desConsigliereProponente = desConsigliereProponente;
	}

	public List<ConsigliereProponenteBean> getAltriConsiglieri() {
		return altriConsiglieri;
	}

	public void setAltriConsiglieri(List<ConsigliereProponenteBean> altriConsiglieri) {
		this.altriConsiglieri = altriConsiglieri;
	}

	public String getIdScrivaniaDirProponente() {
		return idScrivaniaDirProponente;
	}

	public void setIdScrivaniaDirProponente(String idScrivaniaDirProponente) {
		this.idScrivaniaDirProponente = idScrivaniaDirProponente;
	}

	public String getLivelliUOScrivaniaDirProponente() {
		return livelliUOScrivaniaDirProponente;
	}

	public void setLivelliUOScrivaniaDirProponente(String livelliUOScrivaniaDirProponente) {
		this.livelliUOScrivaniaDirProponente = livelliUOScrivaniaDirProponente;
	}

	public String getDesScrivaniaDirProponente() {
		return desScrivaniaDirProponente;
	}

	public void setDesScrivaniaDirProponente(String desScrivaniaDirProponente) {
		this.desScrivaniaDirProponente = desScrivaniaDirProponente;
	}

	public List<ScrivaniaDirProponenteBean> getAltriDirProponenti() {
		return altriDirProponenti;
	}

	public void setAltriDirProponenti(List<ScrivaniaDirProponenteBean> altriDirProponenti) {
		this.altriDirProponenti = altriDirProponenti;
	}

	public String getIdCoordinatoreCompCirc() {
		return idCoordinatoreCompCirc;
	}

	public void setIdCoordinatoreCompCirc(String idCoordinatoreCompCirc) {
		this.idCoordinatoreCompCirc = idCoordinatoreCompCirc;
	}

	public String getDesCoordinatoreCompCirc() {
		return desCoordinatoreCompCirc;
	}

	public void setDesCoordinatoreCompCirc(String desCoordinatoreCompCirc) {
		this.desCoordinatoreCompCirc = desCoordinatoreCompCirc;
	}

	public List<RespVistiConformitaBean> getRespVistiConformita() {
		return respVistiConformita;
	}

	public void setRespVistiConformita(List<RespVistiConformitaBean> respVistiConformita) {
		this.respVistiConformita = respVistiConformita;
	}

	public String getIdScrivaniaEstensoreMain() {
		return idScrivaniaEstensoreMain;
	}

	public void setIdScrivaniaEstensoreMain(String idScrivaniaEstensoreMain) {
		this.idScrivaniaEstensoreMain = idScrivaniaEstensoreMain;
	}

	public String getLivelliUOScrivaniaEstensoreMain() {
		return livelliUOScrivaniaEstensoreMain;
	}

	public void setLivelliUOScrivaniaEstensoreMain(String livelliUOScrivaniaEstensoreMain) {
		this.livelliUOScrivaniaEstensoreMain = livelliUOScrivaniaEstensoreMain;
	}

	public String getDesScrivaniaEstensoreMain() {
		return desScrivaniaEstensoreMain;
	}

	public void setDesScrivaniaEstensoreMain(String desScrivaniaEstensoreMain) {
		this.desScrivaniaEstensoreMain = desScrivaniaEstensoreMain;
	}

	public List<ScrivaniaEstensoreBean> getAltriEstensori() {
		return altriEstensori;
	}

	public void setAltriEstensori(List<ScrivaniaEstensoreBean> altriEstensori) {
		this.altriEstensori = altriEstensori;
	}

	public String getIdScrivaniaIstruttoreMain() {
		return idScrivaniaIstruttoreMain;
	}

	public void setIdScrivaniaIstruttoreMain(String idScrivaniaIstruttoreMain) {
		this.idScrivaniaIstruttoreMain = idScrivaniaIstruttoreMain;
	}

	public String getLivelliUOScrivaniaIstruttoreMain() {
		return livelliUOScrivaniaIstruttoreMain;
	}

	public void setLivelliUOScrivaniaIstruttoreMain(String livelliUOScrivaniaIstruttoreMain) {
		this.livelliUOScrivaniaIstruttoreMain = livelliUOScrivaniaIstruttoreMain;
	}

	public String getDesScrivaniaIstruttoreMain() {
		return desScrivaniaIstruttoreMain;
	}

	public void setDesScrivaniaIstruttoreMain(String desScrivaniaIstruttoreMain) {
		this.desScrivaniaIstruttoreMain = desScrivaniaIstruttoreMain;
	}

	public List<ScrivaniaEstensoreBean> getAltriIstruttori() {
		return altriIstruttori;
	}

	public void setAltriIstruttori(List<ScrivaniaEstensoreBean> altriIstruttori) {
		this.altriIstruttori = altriIstruttori;
	}
	
	public Flag getFlgVistoDirSup1() {
		return flgVistoDirSup1;
	}

	public void setFlgVistoDirSup1(Flag flgVistoDirSup1) {
		this.flgVistoDirSup1 = flgVistoDirSup1;
	}

	public Flag getFlgVistoDirSup2() {
		return flgVistoDirSup2;
	}

	public void setFlgVistoDirSup2(Flag flgVistoDirSup2) {
		this.flgVistoDirSup2 = flgVistoDirSup2;
	}

	public List<KeyValueBean> getParereCircoscrizioni() {
		return parereCircoscrizioni;
	}

	public void setParereCircoscrizioni(List<KeyValueBean> parereCircoscrizioni) {
		this.parereCircoscrizioni = parereCircoscrizioni;
	}

	public List<KeyValueBean> getParereCommissioni() {
		return parereCommissioni;
	}

	public void setParereCommissioni(List<KeyValueBean> parereCommissioni) {
		this.parereCommissioni = parereCommissioni;
	}

	public String getDirigenteResponsabileContabilia() {
		return dirigenteResponsabileContabilia;
	}

	public void setDirigenteResponsabileContabilia(String dirigenteResponsabileContabilia) {
		this.dirigenteResponsabileContabilia = dirigenteResponsabileContabilia;
	}

	public String getCentroResponsabilitaContabilia() {
		return centroResponsabilitaContabilia;
	}

	public void setCentroResponsabilitaContabilia(String centroResponsabilitaContabilia) {
		this.centroResponsabilitaContabilia = centroResponsabilitaContabilia;
	}

	public String getCentroCostoContabilia() {
		return centroCostoContabilia;
	}

	public void setCentroCostoContabilia(String centroCostoContabilia) {
		this.centroCostoContabilia = centroCostoContabilia;
	}

	public List<MovimentiContabiliaXmlBean> getListaMovimentiContabilia() {
		return listaMovimentiContabilia;
	}

	public void setListaMovimentiContabilia(List<MovimentiContabiliaXmlBean> listaMovimentiContabilia) {
		this.listaMovimentiContabilia = listaMovimentiContabilia;
	}

	public List<MovimentiContabiliSICRAXmlBean> getListaMovimentiContabiliSICRA() {
		return listaMovimentiContabiliSICRA;
	}

	public void setListaMovimentiContabiliSICRA(List<MovimentiContabiliSICRAXmlBean> listaMovimentiContabiliSICRA) {
		this.listaMovimentiContabiliSICRA = listaMovimentiContabiliSICRA;
	}

	public Flag getFlgLiqContestualeImpegno() {
		return flgLiqContestualeImpegno;
	}

	public void setFlgLiqContestualeImpegno(Flag flgLiqContestualeImpegno) {
		this.flgLiqContestualeImpegno = flgLiqContestualeImpegno;
	}

	public Flag getFlgLiqContestualeAltriAspettiRilCont() {
		return flgLiqContestualeAltriAspettiRilCont;
	}

	public void setFlgLiqContestualeAltriAspettiRilCont(Flag flgLiqContestualeAltriAspettiRilCont) {
		this.flgLiqContestualeAltriAspettiRilCont = flgLiqContestualeAltriAspettiRilCont;
	}
	
	public Flag getFlgCompQuadroFinRagDec() {
		return flgCompQuadroFinRagDec;
	}

	public void setFlgCompQuadroFinRagDec(Flag flgCompQuadroFinRagDec) {
		this.flgCompQuadroFinRagDec = flgCompQuadroFinRagDec;
	}

	public String getOpzAssCompSpesa() {
		return opzAssCompSpesa;
	}

	public void setOpzAssCompSpesa(String opzAssCompSpesa) {
		this.opzAssCompSpesa = opzAssCompSpesa;
	}

	public Flag getFlgNuoviImpAcc() {
		return flgNuoviImpAcc;
	}

	public void setFlgNuoviImpAcc(Flag flgNuoviImpAcc) {
		this.flgNuoviImpAcc = flgNuoviImpAcc;
	}
	
	public Flag getFlgImpSuAnnoCorrente() {
		return flgImpSuAnnoCorrente;
	}

	public void setFlgImpSuAnnoCorrente(Flag flgImpSuAnnoCorrente) {
		this.flgImpSuAnnoCorrente = flgImpSuAnnoCorrente;
	}

	public Flag getFlgInsMovARagioneria() {
		return flgInsMovARagioneria;
	}

	public void setFlgInsMovARagioneria(Flag flgInsMovARagioneria) {
		this.flgInsMovARagioneria = flgInsMovARagioneria;
	}

	public Flag getFlgVantaggiEconomici() {
		return flgVantaggiEconomici;
	}

	public void setFlgVantaggiEconomici(Flag flgVantaggiEconomici) {
		this.flgVantaggiEconomici = flgVantaggiEconomici;
	}

	public List<DestinatarioVantaggioBean> getDestinatariVantaggio() {
		return destinatariVantaggio;
	}

	public void setDestinatariVantaggio(List<DestinatarioVantaggioBean> destinatariVantaggio) {
		this.destinatariVantaggio = destinatariVantaggio;
	}

	public Flag getFlgAffidamento() {
		return flgAffidamento;
	}

	public void setFlgAffidamento(Flag flgAffidamento) {
		this.flgAffidamento = flgAffidamento;
	}

	public String getNormRifAffidamento() {
		return normRifAffidamento;
	}

	public void setNormRifAffidamento(String normRifAffidamento) {
		this.normRifAffidamento = normRifAffidamento;
	}

	public String getRespAffidamento() {
		return respAffidamento;
	}

	public void setRespAffidamento(String respAffidamento) {
		this.respAffidamento = respAffidamento;
	}

	public String getOggLiquidazione() {
		return oggLiquidazione;
	}

	public void setOggLiquidazione(String oggLiquidazione) {
		this.oggLiquidazione = oggLiquidazione;
	}

	public Date getDataScadenzaLiquidazione() {
		return dataScadenzaLiquidazione;
	}

	public void setDataScadenzaLiquidazione(Date dataScadenzaLiquidazione) {
		this.dataScadenzaLiquidazione = dataScadenzaLiquidazione;
	}

	public String getUrgenzaLiquidazione() {
		return urgenzaLiquidazione;
	}

	public void setUrgenzaLiquidazione(String urgenzaLiquidazione) {
		this.urgenzaLiquidazione = urgenzaLiquidazione;
	}

	public Flag getFlgLiqXUffCassa() {
		return flgLiqXUffCassa;
	}

	public void setFlgLiqXUffCassa(Flag flgLiqXUffCassa) {
		this.flgLiqXUffCassa = flgLiqXUffCassa;
	}

	public String getImportoAnticipoCassa() {
		return importoAnticipoCassa;
	}

	public void setImportoAnticipoCassa(String importoAnticipoCassa) {
		this.importoAnticipoCassa = importoAnticipoCassa;
	}

	public Date getDataDecorrenzaContratto() {
		return dataDecorrenzaContratto;
	}

	public void setDataDecorrenzaContratto(Date dataDecorrenzaContratto) {
		this.dataDecorrenzaContratto = dataDecorrenzaContratto;
	}

	public String getAnniDurataContratto() {
		return anniDurataContratto;
	}

	public void setAnniDurataContratto(String anniDurataContratto) {
		this.anniDurataContratto = anniDurataContratto;
	}

	public Flag getFlgDetAccertRadiaz() {
		return flgDetAccertRadiaz;
	}

	public void setFlgDetAccertRadiaz(Flag flgDetAccertRadiaz) {
		this.flgDetAccertRadiaz = flgDetAccertRadiaz;
	}

	public Flag getFlgDetVariazBil() {
		return flgDetVariazBil;
	}

	public void setFlgDetVariazBil(Flag flgDetVariazBil) {
		this.flgDetVariazBil = flgDetVariazBil;
	}

	public Flag getFlgDecretoReggio() {
		return flgDecretoReggio;
	}

	public void setFlgDecretoReggio(Flag flgDecretoReggio) {
		this.flgDecretoReggio = flgDecretoReggio;
	}

	public Flag getFlgAvvocatura() {
		return flgAvvocatura;
	}

	public void setFlgAvvocatura(Flag flgAvvocatura) {
		this.flgAvvocatura = flgAvvocatura;
	}

	public String getFlgPubblNotiziario() {
		return flgPubblNotiziario;
	}

	public void setFlgPubblNotiziario(String flgPubblNotiziario) {
		this.flgPubblNotiziario = flgPubblNotiziario;
	}
	
	public String getIdGruppoLiquidatori() {
		return idGruppoLiquidatori;
	}

	public void setIdGruppoLiquidatori(String idGruppoLiquidatori) {
		this.idGruppoLiquidatori = idGruppoLiquidatori;
	}

	public String getNomeGruppoLiquidatori() {
		return nomeGruppoLiquidatori;
	}

	public void setNomeGruppoLiquidatori(String nomeGruppoLiquidatori) {
		this.nomeGruppoLiquidatori = nomeGruppoLiquidatori;
	}

	public String getCodGruppoLiquidatori() {
		return codGruppoLiquidatori;
	}

	public void setCodGruppoLiquidatori(String codGruppoLiquidatori) {
		this.codGruppoLiquidatori = codGruppoLiquidatori;
	}

	public String getIdAssegnatarioProcesso() {
		return idAssegnatarioProcesso;
	}

	public void setIdAssegnatarioProcesso(String idAssegnatarioProcesso) {
		this.idAssegnatarioProcesso = idAssegnatarioProcesso;
	}

	public String getDesAssegnatarioProcesso() {
		return desAssegnatarioProcesso;
	}

	public void setDesAssegnatarioProcesso(String desAssegnatarioProcesso) {
		this.desAssegnatarioProcesso = desAssegnatarioProcesso;
	}

	public String getNriLivelliAssegatarioProcesso() {
		return nriLivelliAssegatarioProcesso;
	}

	public void setNriLivelliAssegatarioProcesso(String nriLivelliAssegatarioProcesso) {
		this.nriLivelliAssegatarioProcesso = nriLivelliAssegatarioProcesso;
	}

	public String getIdDocPrimarioLiquidazione() {
		return idDocPrimarioLiquidazione;
	}

	public void setIdDocPrimarioLiquidazione(String idDocPrimarioLiquidazione) {
		this.idDocPrimarioLiquidazione = idDocPrimarioLiquidazione;
	}

	public String getIdUdLiquidazione() {
		return idUdLiquidazione;
	}

	public void setIdUdLiquidazione(String idUdLiquidazione) {
		this.idUdLiquidazione = idUdLiquidazione;
	}

	public String getCodTipoLiquidazioneXContabilia() {
		return codTipoLiquidazioneXContabilia;
	}

	public void setCodTipoLiquidazioneXContabilia(String codTipoLiquidazioneXContabilia) {
		this.codTipoLiquidazioneXContabilia = codTipoLiquidazioneXContabilia;
	}

	public String getSiglaRegLiquidazione() {
		return siglaRegLiquidazione;
	}

	public void setSiglaRegLiquidazione(String siglaRegLiquidazione) {
		this.siglaRegLiquidazione = siglaRegLiquidazione;
	}

	public String getAnnoRegLiquidazione() {
		return annoRegLiquidazione;
	}

	public void setAnnoRegLiquidazione(String annoRegLiquidazione) {
		this.annoRegLiquidazione = annoRegLiquidazione;
	}

	public String getNroRegLiquidazione() {
		return nroRegLiquidazione;
	}

	public void setNroRegLiquidazione(String nroRegLiquidazione) {
		this.nroRegLiquidazione = nroRegLiquidazione;
	}

	public Date getDataAdozioneLiquidazione() {
		return dataAdozioneLiquidazione;
	}

	public void setDataAdozioneLiquidazione(Date dataAdozioneLiquidazione) {
		this.dataAdozioneLiquidazione = dataAdozioneLiquidazione;
	}
	
	public String getEstremiAttoLiquidazione() {
		return estremiAttoLiquidazione;
	}

	public void setEstremiAttoLiquidazione(String estremiAttoLiquidazione) {
		this.estremiAttoLiquidazione = estremiAttoLiquidazione;
	}
	
	public Flag getFlgAggregatoACTA() {
		return flgAggregatoACTA;
	}

	public void setFlgAggregatoACTA(Flag flgAggregatoACTA) {
		this.flgAggregatoACTA = flgAggregatoACTA;
	}

	public Flag getFlgSmistamentoACTA() {
		return flgSmistamentoACTA;
	}

	public void setFlgSmistamentoACTA(Flag flgSmistamentoACTA) {
		this.flgSmistamentoACTA = flgSmistamentoACTA;
	}

	public String getOpzioneIndiceClassificazioneFascicoloACTA() {
		return opzioneIndiceClassificazioneFascicoloACTA;
	}

	public void setOpzioneIndiceClassificazioneFascicoloACTA(String opzioneIndiceClassificazioneFascicoloACTA) {
		this.opzioneIndiceClassificazioneFascicoloACTA = opzioneIndiceClassificazioneFascicoloACTA;
	}

	public String getCodIndiceClassificazioneACTA() {
		return codIndiceClassificazioneACTA;
	}

	public void setCodIndiceClassificazioneACTA(String codIndiceClassificazioneACTA) {
		this.codIndiceClassificazioneACTA = codIndiceClassificazioneACTA;
	}

	public String getIdClassificazioneACTA() {
		return idClassificazioneACTA;
	}

	public void setIdClassificazioneACTA(String idClassificazioneACTA) {
		this.idClassificazioneACTA = idClassificazioneACTA;
	}

	public String getCodVoceTitolarioACTA() {
		return codVoceTitolarioACTA;
	}

	public void setCodVoceTitolarioACTA(String codVoceTitolarioACTA) {
		this.codVoceTitolarioACTA = codVoceTitolarioACTA;
	}

	public String getCodFascicoloACTA() {
		return codFascicoloACTA;
	}

	public void setCodFascicoloACTA(String codFascicoloACTA) {
		this.codFascicoloACTA = codFascicoloACTA;
	}

	public String getSuffissoCodFascicoloACTA() {
		return suffissoCodFascicoloACTA;
	}

	public void setSuffissoCodFascicoloACTA(String suffissoCodFascicoloACTA) {
		this.suffissoCodFascicoloACTA = suffissoCodFascicoloACTA;
	}

	public String getIdFascicoloACTA() {
		return idFascicoloACTA;
	}

	public void setIdFascicoloACTA(String idFascicoloACTA) {
		this.idFascicoloACTA = idFascicoloACTA;
	}

	public String getIdNodoSmistamentoACTA() {
		return idNodoSmistamentoACTA;
	}

	public void setIdNodoSmistamentoACTA(String idNodoSmistamentoACTA) {
		this.idNodoSmistamentoACTA = idNodoSmistamentoACTA;
	}

	public String getDesNodoSmistamentoACTA() {
		return desNodoSmistamentoACTA;
	}

	public void setDesNodoSmistamentoACTA(String desNodoSmistamentoACTA) {
		this.desNodoSmistamentoACTA = desNodoSmistamentoACTA;
	}

	public String getCodAOOXSelNodoACTA() {
		return codAOOXSelNodoACTA;
	}

	public void setCodAOOXSelNodoACTA(String codAOOXSelNodoACTA) {
		this.codAOOXSelNodoACTA = codAOOXSelNodoACTA;
	}

	public String getCodStrutturaXSelNodoACTA() {
		return codStrutturaXSelNodoACTA;
	}

	public void setCodStrutturaXSelNodoACTA(String codStrutturaXSelNodoACTA) {
		this.codStrutturaXSelNodoACTA = codStrutturaXSelNodoACTA;
	}

	public List<PeriziaXmlBean> getListaPerizie() {
		return listaPerizie;
	}

	public void setListaPerizie(List<PeriziaXmlBean> listaPerizie) {
		this.listaPerizie = listaPerizie;
	}

	public Date getDataInizioPubbl() {
		return dataInizioPubbl;
	}

	public void setDataInizioPubbl(Date dataInizioPubbl) {
		this.dataInizioPubbl = dataInizioPubbl;
	}

	public String getGiorniDurataPubbl() {
		return giorniDurataPubbl;
	}

	public void setGiorniDurataPubbl(String giorniDurataPubbl) {
		this.giorniDurataPubbl = giorniDurataPubbl;
	}

	public String getUsernameRichPubblAlboPretorio() {
		return usernameRichPubblAlboPretorio;
	}

	public void setUsernameRichPubblAlboPretorio(String usernameRichPubblAlboPretorio) {
		this.usernameRichPubblAlboPretorio = usernameRichPubblAlboPretorio;
	}

	public String getTipoDocAlboPretorio() {
		return tipoDocAlboPretorio;
	}

	public void setTipoDocAlboPretorio(String tipoDocAlboPretorio) {
		this.tipoDocAlboPretorio = tipoDocAlboPretorio;
	}

	public String getMittDocAlboPretorio() {
		return mittDocAlboPretorio;
	}

	public void setMittDocAlboPretorio(String mittDocAlboPretorio) {
		this.mittDocAlboPretorio = mittDocAlboPretorio;
	}

	public String getUriDocPrimarioPubblAlbo() {
		return uriDocPrimarioPubblAlbo;
	}

	public void setUriDocPrimarioPubblAlbo(String uriDocPrimarioPubblAlbo) {
		this.uriDocPrimarioPubblAlbo = uriDocPrimarioPubblAlbo;
	}

	public String getNomeFileDocPrimarioPubblAlbo() {
		return nomeFileDocPrimarioPubblAlbo;
	}

	public void setNomeFileDocPrimarioPubblAlbo(String nomeFileDocPrimarioPubblAlbo) {
		this.nomeFileDocPrimarioPubblAlbo = nomeFileDocPrimarioPubblAlbo;
	}

	public String getUriAllegatoVRCPubblAlbo() {
		return uriAllegatoVRCPubblAlbo;
	}

	public void setUriAllegatoVRCPubblAlbo(String uriAllegatoVRCPubblAlbo) {
		this.uriAllegatoVRCPubblAlbo = uriAllegatoVRCPubblAlbo;
	}

	public String getNomeFileAllegatoVRCPubblAlbo() {
		return nomeFileAllegatoVRCPubblAlbo;
	}

	public void setNomeFileAllegatoVRCPubblAlbo(String nomeFileAllegatoVRCPubblAlbo) {
		this.nomeFileAllegatoVRCPubblAlbo = nomeFileAllegatoVRCPubblAlbo;
	}

	public String getFirmatarioVRCPubblAlbo() {
		return firmatarioVRCPubblAlbo;
	}

	public void setFirmatarioVRCPubblAlbo(String firmatarioVRCPubblAlbo) {
		this.firmatarioVRCPubblAlbo = firmatarioVRCPubblAlbo;
	}

	public Boolean getAbilModificaAlboBK() {
		return abilModificaAlboBK;
	}

	public void setAbilModificaAlboBK(Boolean abilModificaAlboBK) {
		this.abilModificaAlboBK = abilModificaAlboBK;
	}

	public Date getDtInizioPubblAlboBK() {
		return dtInizioPubblAlboBK;
	}

	public void setDtInizioPubblAlboBK(Date dtInizioPubblAlboBK) {
		this.dtInizioPubblAlboBK = dtInizioPubblAlboBK;
	}
	
	public Date getDtFinePubblAlboBK() {
		return dtFinePubblAlboBK;
	}

	public void setDtFinePubblAlboBK(Date dtFinePubblAlboBK) {
		this.dtFinePubblAlboBK = dtFinePubblAlboBK;
	}

	public String getNroGgPubblAlboBK() {
		return nroGgPubblAlboBK;
	}

	public void setNroGgPubblAlboBK(String nroGgPubblAlboBK) {
		this.nroGgPubblAlboBK = nroGgPubblAlboBK;
	}

	public String getFormaPubblAlboBK() {
		return formaPubblAlboBK;
	}

	public void setFormaPubblAlboBK(String formaPubblAlboBK) {
		this.formaPubblAlboBK = formaPubblAlboBK;
	}
	
	public Boolean getAbilInvioEmailRicevuta() {
		return abilInvioEmailRicevuta;
	}
	
	public void setAbilInvioEmailRicevuta(Boolean abilInvioEmailRicevuta) {
		this.abilInvioEmailRicevuta = abilInvioEmailRicevuta;
	}

	public Boolean getAbilProrogaPubblicazione() {
		return abilProrogaPubblicazione;
	}

	public void setAbilProrogaPubblicazione(Boolean abilProrogaPubblicazione) {
		this.abilProrogaPubblicazione = abilProrogaPubblicazione;
	}

	public Boolean getAbilAnnullamentoPubblicazione() {
		return abilAnnullamentoPubblicazione;
	}

	public void setAbilAnnullamentoPubblicazione(Boolean abilAnnullamentoPubblicazione) {
		this.abilAnnullamentoPubblicazione = abilAnnullamentoPubblicazione;
	}

	public Boolean getAbilRettificaPubblicazione() {
		return abilRettificaPubblicazione;
	}

	public void setAbilRettificaPubblicazione(Boolean abilRettificaPubblicazione) {
		this.abilRettificaPubblicazione = abilRettificaPubblicazione;
	}

	public Boolean getFlgInvioPECMulti() {
		return flgInvioPECMulti;
	}

	public void setFlgInvioPECMulti(Boolean flgInvioPECMulti) {
		this.flgInvioPECMulti = flgInvioPECMulti;
	}
	
	public String getIdUoAlboReggio() {
		return idUoAlboReggio;
	}

	public void setIdUoAlboReggio(String idUoAlboReggio) {
		this.idUoAlboReggio = idUoAlboReggio;
	}

	public String getIdProcessTypeIterWFRisposta() {
		return idProcessTypeIterWFRisposta;
	}

	public void setIdProcessTypeIterWFRisposta(String idProcessTypeIterWFRisposta) {
		this.idProcessTypeIterWFRisposta = idProcessTypeIterWFRisposta;
	}

	public String getNomeProcessTypeIterWFRisposta() {
		return nomeProcessTypeIterWFRisposta;
	}

	public void setNomeProcessTypeIterWFRisposta(String nomeProcessTypeIterWFRisposta) {
		this.nomeProcessTypeIterWFRisposta = nomeProcessTypeIterWFRisposta;
	}

	public String getNomeTipoFlussoIterWFRisposta() {
		return nomeTipoFlussoIterWFRisposta;
	}

	public void setNomeTipoFlussoIterWFRisposta(String nomeTipoFlussoIterWFRisposta) {
		this.nomeTipoFlussoIterWFRisposta = nomeTipoFlussoIterWFRisposta;
	}

	public String getIdDocTypeIterWFRisposta() {
		return idDocTypeIterWFRisposta;
	}

	public void setIdDocTypeIterWFRisposta(String idDocTypeIterWFRisposta) {
		this.idDocTypeIterWFRisposta = idDocTypeIterWFRisposta;
	}

	public String getNomeDocTypeIterWFRisposta() {
		return nomeDocTypeIterWFRisposta;
	}

	public void setNomeDocTypeIterWFRisposta(String nomeDocTypeIterWFRisposta) {
		this.nomeDocTypeIterWFRisposta = nomeDocTypeIterWFRisposta;
	}

	public String getCodCategoriaNumIniIterWFRisposta() {
		return codCategoriaNumIniIterWFRisposta;
	}

	public void setCodCategoriaNumIniIterWFRisposta(String codCategoriaNumIniIterWFRisposta) {
		this.codCategoriaNumIniIterWFRisposta = codCategoriaNumIniIterWFRisposta;
	}

	public String getSiglaNumIniIterWFRisposta() {
		return siglaNumIniIterWFRisposta;
	}

	public void setSiglaNumIniIterWFRisposta(String siglaNumIniIterWFRisposta) {
		this.siglaNumIniIterWFRisposta = siglaNumIniIterWFRisposta;
	}

	public Boolean getFlgPresenzaPubblicazioni() {
		return flgPresenzaPubblicazioni;
	}

	public void setFlgPresenzaPubblicazioni(Boolean flgPresenzaPubblicazioni) {
		this.flgPresenzaPubblicazioni = flgPresenzaPubblicazioni;
	}

	public String getIdPubblicazione() {
		return idPubblicazione;
	}

	public void setIdPubblicazione(String idPubblicazione) {
		this.idPubblicazione = idPubblicazione;
	}

	public String getNroPubblicazione() {
		return nroPubblicazione;
	}

	public void setNroPubblicazione(String nroPubblicazione) {
		this.nroPubblicazione = nroPubblicazione;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public Date getDataDalPubblicazione() {
		return dataDalPubblicazione;
	}

	public void setDataDalPubblicazione(Date dataDalPubblicazione) {
		this.dataDalPubblicazione = dataDalPubblicazione;
	}

	public Date getDataAlPubblicazione() {
		return dataAlPubblicazione;
	}

	public void setDataAlPubblicazione(Date dataAlPubblicazione) {
		this.dataAlPubblicazione = dataAlPubblicazione;
	}

	public String getRichiestaDaPubblicazione() {
		return richiestaDaPubblicazione;
	}

	public void setRichiestaDaPubblicazione(String richiestaDaPubblicazione) {
		this.richiestaDaPubblicazione = richiestaDaPubblicazione;
	}

	public String getStatoPubblicazione() {
		return statoPubblicazione;
	}

	public void setStatoPubblicazione(String statoPubblicazione) {
		this.statoPubblicazione = statoPubblicazione;
	}

	public String getFormaPubblicazione() {
		return formaPubblicazione;
	}

	public void setFormaPubblicazione(String formaPubblicazione) {
		this.formaPubblicazione = formaPubblicazione;
	}

	public String getRettificataDaPubblicazione() {
		return rettificataDaPubblicazione;
	}

	public void setRettificataDaPubblicazione(String rettificataDaPubblicazione) {
		this.rettificataDaPubblicazione = rettificataDaPubblicazione;
	}
	
	public String getMotivoRettificaPubblicazione() {
		return motivoRettificaPubblicazione;
	}

	public void setMotivoRettificaPubblicazione(String motivoRettificaPubblicazione) {
		this.motivoRettificaPubblicazione = motivoRettificaPubblicazione;
	}

	public String getMotivoAnnullamentoPubblicazione() {
		return motivoAnnullamentoPubblicazione;
	}

	public void setMotivoAnnullamentoPubblicazione(String motivoAnnullamentoPubblicazione) {
		this.motivoAnnullamentoPubblicazione = motivoAnnullamentoPubblicazione;
	}

	public String getProroghePubblicazione() {
		return proroghePubblicazione;
	}

	public void setProroghePubblicazione(String proroghePubblicazione) {
		this.proroghePubblicazione = proroghePubblicazione;
	}

	public String getIdRipubblicazione() {
		return idRipubblicazione;
	}

	public void setIdRipubblicazione(String idRipubblicazione) {
		this.idRipubblicazione = idRipubblicazione;
	}

	public String getNroRipubblicazione() {
		return nroRipubblicazione;
	}

	public void setNroRipubblicazione(String nroRipubblicazione) {
		this.nroRipubblicazione = nroRipubblicazione;
	}

	public Date getDataRipubblicazione() {
		return dataRipubblicazione;
	}

	public void setDataRipubblicazione(Date dataRipubblicazione) {
		this.dataRipubblicazione = dataRipubblicazione;
	}

	public Date getDataDalRipubblicazione() {
		return dataDalRipubblicazione;
	}

	public void setDataDalRipubblicazione(Date dataDalRipubblicazione) {
		this.dataDalRipubblicazione = dataDalRipubblicazione;
	}

	public Date getDataAlRipubblicazione() {
		return dataAlRipubblicazione;
	}

	public void setDataAlRipubblicazione(Date dataAlRipubblicazione) {
		this.dataAlRipubblicazione = dataAlRipubblicazione;
	}

	public String getRichiestaDaRipubblicazione() {
		return richiestaDaRipubblicazione;
	}

	public void setRichiestaDaRipubblicazione(String richiestaDaRipubblicazione) {
		this.richiestaDaRipubblicazione = richiestaDaRipubblicazione;
	}

	public String getStatoRipubblicazione() {
		return statoRipubblicazione;
	}

	public void setStatoRipubblicazione(String statoRipubblicazione) {
		this.statoRipubblicazione = statoRipubblicazione;
	}

	public String getFormaRipubblicazione() {
		return formaRipubblicazione;
	}

	public void setFormaRipubblicazione(String formaRipubblicazione) {
		this.formaRipubblicazione = formaRipubblicazione;
	}

	public String getRettificataDaRipubblicazione() {
		return rettificataDaRipubblicazione;
	}

	public void setRettificataDaRipubblicazione(String rettificataDaRipubblicazione) {
		this.rettificataDaRipubblicazione = rettificataDaRipubblicazione;
	}

	public String getMotivoRettificaRipubblicazione() {
		return motivoRettificaRipubblicazione;
	}

	public void setMotivoRettificaRipubblicazione(String motivoRettificaRipubblicazione) {
		this.motivoRettificaRipubblicazione = motivoRettificaRipubblicazione;
	}

	public String getMotivoAnnullamentoRipubblicazione() {
		return motivoAnnullamentoRipubblicazione;
	}

	public void setMotivoAnnullamentoRipubblicazione(String motivoAnnullamentoRipubblicazione) {
		this.motivoAnnullamentoRipubblicazione = motivoAnnullamentoRipubblicazione;
	}

	public String getProrogheRipubblicazione() {
		return prorogheRipubblicazione;
	}

	public void setProrogheRipubblicazione(String prorogheRipubblicazione) {
		this.prorogheRipubblicazione = prorogheRipubblicazione;
	}

	public String getNotePubblicazione() {
		return notePubblicazione;
	}

	public void setNotePubblicazione(String notePubblicazione) {
		this.notePubblicazione = notePubblicazione;
	}

	public Date getDataAdozione() {
		return dataAdozione;
	}

	public void setDataAdozione(Date dataAdozione) {
		this.dataAdozione = dataAdozione;
	}
	
}