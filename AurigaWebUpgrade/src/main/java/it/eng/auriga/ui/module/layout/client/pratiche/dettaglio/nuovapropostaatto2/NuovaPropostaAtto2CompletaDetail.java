package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupRubricaEmailPopup;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.DocumentItem;
import it.eng.auriga.ui.module.layout.client.common.items.SelectItemValoriDizionario;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskFlussoInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.AltriDirRespRegTecnicaCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.AltriDirigentiProponentiCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.AssessoriItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.AttiRiferimentoItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.CIGItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ConsiglieriItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.CoordinatoriCompCircItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.DatiContabiliStoriciWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.DestVantaggioItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.DestinatariAttoItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.DirigenteAdottanteCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.DirigenteRespRegTecnicaCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.DirigentiConcertoCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.DirigentiProponentiCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.EstensoreCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.IstruttoreCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ListaDatiContabiliSIBItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ListaInvioDatiSpesaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ListaInvioMovimentiContabiliSICRAItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ListaMovimentiContabilia2Item;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ListaMovimentiContabiliaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.RespVistiConformitaCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ResponsabileDiProcedimentoCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ResponsabileUnicoProvvedimentoCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ResponsabiliPEGCompletaItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.RiferimentiNormativiItem;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.ValoriDizionarioItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiGridItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloAction;
import it.eng.auriga.ui.module.layout.client.protocollazione.SaveModelloWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.SelezionaUOItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.pgweb.AltreVieItem;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.IDatiSensibiliItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ComboBoxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class NuovaPropostaAtto2CompletaDetail extends CustomDetail {
		
	public static final String _TAB_DATI_SCHEDA_ID = "DATI_SCHEDA";
	public static final String _TAB_DATI_DISPOSITIVO_ID = "DATI_DISPOSITIVO"; 
	public static final String _TAB_DATI_DISPOSITIVO_2_ID = "DATI_DISPOSITIVO_2"; 
	public static final String _TAB_ALLEGATI_ID = "ALLEGATI";
	public static final String _TAB_DATI_PUBBL_ID = "DATI_PUBBL"; 
	public static final String _TAB_MOVIMENTI_CONTABILI_ID = "MOVIMENTI_CONTABILI"; 
	public static final String _TAB_DATI_SPESA_CORRENTE_ID = "DATI_SPESA_CORRENTE";
	public static final String _TAB_DATI_SPESA_CONTO_CAPITALE_ID = "DATI_SPESA_CONTO_CAPITALE";
	public static final String _TAB_AGGREGATO_SMISTAMENTO_ACTA_ID = "AGGREGATO_SMISTAMENTO_ACTA"; 
	
	public static final String _FLG_SI = "SI";
//	public static final String _FLG_SI_SENZA_VLD_RIL_IMP = "SI, ma senza movimenti contabili";
	public static final String _FLG_NO = "NO";
	public static final String _FLG_OP_COMMERCIALE_NA = "N.A.";
	
	public static final String _PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_A = "uff. competente per la definizione della spesa";
	public static final String _PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B = "uff. Bilancio Centrale (Ragioneria)";
	public static final String _MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1 = "compilazione griglia";
	public static final String _MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B2 = "xls importabile in SIB";
	public static final String _MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B3 = "associazione impegni su SIB a cura del proponente";
	public static final String _MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B4 = "registrazione su SIB";
	
	public static final String _MANDATORY = "mandatory"; 
	public static final String _OPTIONAL = "optional";
	
	public static final String _DECORR_PUBBL_STD = "standard";
	public static final String _DECORR_PUBBL_POST = "posticipata";
	
	public static final String _FLG_EMENDAMENTO_SU_FILE_D = "D";
	public static final String _FLG_EMENDAMENTO_SU_FILE_A = "A";	
	
	public static final String _STRUTT_COMP_DEF_DATI_CONT_SEMPRE_UGUALE_UO_PROPONENTE = "sempre_uguale_uo_proponente";
	public static final String _STRUTT_COMP_DEF_DATI_CONT_DEFAULT_UGUALE_UO_PROPONENTE = "default_uguale_uo_proponente";
	
	public static final String _PERMANENTE = "permanente";
	public static final String _TEMPORANEA = "temporanea";
	
	public static final String _TIPO_LUOGO_DA_TOPONOMASTICA = "da toponomastica";
	public static final String _TIPO_LUOGO_TESTO_LIBERO = "testo libero";
	
	protected NuovaPropostaAtto2CompletaDetail instance;
	
	protected String tipoDocumento;
	protected String rowidDoc;
	protected Record recordFromLoadDett;
	
	protected VLayout mainLayout;
	protected ToolStrip mainToolStrip;
	protected GWTRestDataSource modelliDS;
	protected SelectItem modelliSelectItem;
	protected ListGrid modelliPickListProperties;
	protected SaveModelloWindow saveModelloWindow;
	protected ToolStripButton salvaComeModelloButton;

	protected TabSet tabSet;
	protected Tab tabDatiScheda;
	protected Tab tabDatiDispositivo;
	protected Tab tabDatiDispositivo2;
	protected Tab tabAllegati;
	protected Tab tabPubblicazioneNotifiche;
	protected Tab tabMovimentiContabili;
	protected Tab tabDatiSpesaCorrente;
	protected Tab tabDatiSpesaContoCapitale;
	protected Tab tabAggregatoSmistamentoACTA;
	
//	protected boolean toSaveAndReloadTask;
	
	protected Record recordParametriTipoAtto;
	protected Boolean flgPubblicazioneAllegatiUguale;
	protected Boolean flgAvvioLiquidazioneContabilia;
	protected Boolean flgSoloPreparazioneVersPubblicazione;
	protected Boolean flgCtrlMimeTypeAllegPI;
	protected Boolean flgProtocollazioneProsa;
	protected Boolean flgFirmaVersPubblAggiornata;
	protected HashMap<String, Record> attributiCustomCablati;
	protected LinkedHashMap<String, String> ufficioProponenteValueMap;
	protected Boolean flgAllegAttoParteIntDefaultXTipoAtto;
	protected Boolean flgAllegAttoParteIntDefaultOrdPermanente;
	protected Boolean flgAllegAttoParteIntDefaultOrdTemporanea;
	protected Boolean flgAllegAttoPubblSepDefaultXTipoAtto;
	
	/*******************
	 * TAB DATI SCHEDA *
	 *******************/

	/* Hidden */
	protected DynamicForm hiddenForm;
	protected HiddenItem idUdHiddenItem; 
	protected HiddenItem tipoDocumentoHiddenItem; 
	protected HiddenItem nomeTipoDocumentoHiddenItem; 
	protected HiddenItem rowidDocHiddenItem;
	protected HiddenItem idDocPrimarioHiddenItem; 
	protected HiddenItem nomeFilePrimarioHiddenItem;
	protected HiddenItem uriFilePrimarioHiddenItem;
	protected HiddenItem remoteUriFilePrimarioHiddenItem;
	protected HiddenItem infoFilePrimarioHiddenItem;
	protected HiddenItem isChangedFilePrimarioHiddenItem;
	protected HiddenItem idDocPrimarioOmissisHiddenItem; 
	protected HiddenItem nomeFilePrimarioOmissisHiddenItem;
	protected HiddenItem uriFilePrimarioOmissisHiddenItem;
	protected HiddenItem remoteUriFilePrimarioOmissisHiddenItem;
	protected HiddenItem infoFilePrimarioOmissisHiddenItem;		
	protected HiddenItem isChangedFilePrimarioOmissisHiddenItem;
	protected HiddenItem idPropostaAMCHiddenItem;
	protected HiddenItem listaEmendamentiItem;
	protected HiddenItem listaEmendamentiBloccoRiordinoAutItem;
	protected HiddenItem uriDocGeneratoFormatoOdtItem;
	protected HiddenItem codAOOXSelNodoACTAItem;
	protected HiddenItem codStrutturaXSelNodoACTAItem;
	
	/* Dati scheda - Registrazione */
	protected HeaderNuovaPropostaAtto2CompletaDetailSection detailSectionRegistrazione;
	protected DynamicForm registrazioneForm;
	protected ImgItem iconaTipoRegistrazioneItem; 
	protected TextItem siglaRegistrazioneItem;
	protected NumericItem numeroRegistrazioneItem;
	protected DateTimeItem dataRegistrazioneItem;
	protected TextItem desUserRegistrazioneItem;
	protected TextItem desUORegistrazioneItem;
	protected TextItem estremiRepertorioPerStrutturaItem;
	protected ImgItem iconaTipoRegProvvisoriaItem; 
	protected TextItem siglaRegProvvisoriaItem;
	protected NumericItem numeroRegProvvisoriaItem;
	protected DateTimeItem dataRegProvvisoriaItem;
	protected TextItem desUserRegProvvisoriaItem;
	protected TextItem desUORegProvvisoriaItem;
	protected DynamicForm invioACTAForm;
	protected StaticTextItem esitoInvioACTASerieAttiIntegraliItem;
	protected StaticTextItem esitoInvioACTASeriePubblItem;
	protected DynamicForm attoLiquidazioneForm;
	protected HiddenItem idUdLiquidazioneItem; 
	protected TextItem estremiAttoLiquidazioneItem;
	
	/* Dati scheda - Pubblicazione */
//	protected HeaderNuovaPropostaAtto2CompletaDetailSection detailSectionPubblicazione;
//	protected DynamicForm pubblicazioneForm;
//	protected DateItem dataInizioPubblicazioneItem;
//	protected NumericItem giorniPubblicazioneItem;
    	
	/* Dati scheda - Emenda */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionEmendamento;
	protected DynamicForm emendamentoForm1;
	protected SelectItem tipoAttoEmendamentoItem;
	protected TextItem siglaAttoEmendamentoItem;
	protected NumericItem numeroAttoEmendamentoItem;
	protected AnnoItem annoAttoEmendamentoItem;
	protected NumericItem idEmendamentoItem;
	protected NumericItem numeroEmendamentoItem;
	protected DynamicForm emendamentoForm2;
	protected RadioGroupItem flgEmendamentoSuFileItem;
	protected NumericItem numeroAllegatoEmendamentoItem;
	protected CheckboxItem flgEmendamentoIntegraleAllegatoItem;
	protected NumericItem numeroPaginaEmendamentoItem;
	protected NumericItem numeroRigaEmendamentoItem;
	protected SelectItem effettoEmendamentoItem;
	
	/* Dati scheda - Destinatari */
	
	protected DynamicForm attivaDestinatariForm;
	protected CheckboxItem flgAttivaDestinatariItem;
	
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionDestinatariAtto;
	protected DynamicForm destinatariAttoForm;
	protected DestinatariAttoItem listaDestinatariAttoItem;	
	
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionDestinatariPCAtto;
	protected DynamicForm destinatariPCAttoForm;
	protected DestinatariAttoItem listaDestinatariPCAttoItem;
		
	/* Dati scheda - Tipo proposta */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionTipoProposta;
	protected DynamicForm tipoPropostaForm;
	protected SelectItem iniziativaPropostaItem;
	protected CheckboxItem flgAttoMeroIndirizzoItem;	
	protected CheckboxItem flgModificaRegolamentoItem;	
	protected CheckboxItem flgModificaStatutoItem;
	protected CheckboxItem flgNominaItem;
	protected CheckboxItem flgRatificaDeliberaUrgenzaItem;
	protected CheckboxItem flgAttoUrgenteItem;		
	
	/* Dati scheda - Circoscrizioni proponenti delibera */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionCircoscrizioni;
	protected DynamicForm circoscrizioniForm;
	protected ValoriDizionarioItem listaCircoscrizioniItem;
	
	/* Dati scheda - Interpellanza */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionInterpellanza;
	protected DynamicForm interpellanzaForm;
	protected SelectItem tipoInterpellanzaItem;
	
	/* Dati scheda - Ordinanza di mobilità */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionOrdMobilita;
	protected DynamicForm ordMobilitaForm1;
	protected SelectItem tipoOrdMobilitaItem;
	protected DateItem dataInizioVldOrdinanzaItem;
	protected DateItem dataFineVldOrdinanzaItem;
	protected DynamicForm ordMobilitaForm2;
	protected RadioGroupItem tipoLuogoOrdMobilitaItem;
	protected AltreVieItem listaIndirizziOrdMobilitaItem;
	protected DynamicForm ordMobilitaForm3;
	protected CKEditorItem luogoOrdMobilitaItem;
	protected DynamicForm ordMobilitaForm4;
	protected ValoriDizionarioItem listaCircoscrizioniOrdMobilitaItem;
	protected DynamicForm ordMobilitaForm5;
	protected CKEditorItem descrizioneOrdMobilitaItem;
	
	/* Dati scheda - Ruoli */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionRuoli;
	protected DynamicForm ruoliForm;
	protected HiddenItem codUfficioProponenteItem;
	protected HiddenItem desUfficioProponenteItem;
	protected SelezionaUOItem listaUfficioProponenteItem;
	protected SelectItem ufficioProponenteItem;
	protected DirigenteAdottanteCompletaItem listaAdottanteItem;
	protected SelectItem centroDiCostoItem;
	protected DirigentiConcertoCompletaItem listaDirigentiConcertoItem;
	protected DirigenteRespRegTecnicaCompletaItem listaDirRespRegTecnicaItem;
	protected AltriDirRespRegTecnicaCompletaItem listaAltriDirRespRegTecnicaItem;
	protected ResponsabileDiProcedimentoCompletaItem listaRdPItem;
	protected ResponsabileUnicoProvvedimentoCompletaItem listaRUPItem;
	protected AssessoriItem listaAssessoriItem;
	protected AssessoriItem listaAltriAssessoriItem;
	protected ConsiglieriItem listaConsiglieriItem;
	protected ConsiglieriItem listaAltriConsiglieriItem;
	protected DirigentiProponentiCompletaItem listaDirigentiProponentiItem;
	protected AltriDirigentiProponentiCompletaItem listaAltriDirigentiProponentiItem;
	protected CoordinatoriCompCircItem listaCoordinatoriCompCircItem;	
	protected CheckboxItem flgRichiediVistoDirettoreItem;
	protected RespVistiConformitaCompletaItem listaRespVistiConformitaItem;
	protected EstensoreCompletaItem listaEstensoriItem;
	protected EstensoreCompletaItem listaAltriEstensoriItem;
	protected IstruttoreCompletaItem listaIstruttoriItem;
	protected IstruttoreCompletaItem listaAltriIstruttoriItem;
	
	/* Dati scheda - Visti dir. superiori */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionVistiDirSuperiori;
	protected DynamicForm vistiDirSuperioriForm;
	protected CheckboxItem flgVistoDirSup1Item;
	protected CheckboxItem flgVistoDirSup2Item;
	
	/* Dati scheda - Parere della/e circoscrizioni */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionParereCircoscrizioni;
	protected DynamicForm parereCircoscrizioniForm;
	protected ValoriDizionarioItem listaParereCircoscrizioniItem;
	
	/* Dati scheda - Parere della/e commissioni */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionParereCommissioni;
	protected DynamicForm parereCommissioniForm;
	protected ValoriDizionarioItem listaParereCommissioniItem;	
	
	/* Dati scheda - Oggetto */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionOggetto;
	protected DynamicForm oggettoForm;
	protected HiddenItem oggettoItem;
	protected CKEditorItem oggettoHtmlItem;
	
	/* Dati scheda - Atto di riferimento */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionAttoRiferimento;
	protected DynamicForm attoRiferimentoForm;
	protected AttiRiferimentoItem listaAttiRiferimentoItem;
//	protected RadioGroupItem flgAttoRifASistemaItem;
//	protected HiddenItem idUdAttoDeterminaAContrarreItem;
//	protected SelectItem categoriaRegAttoDeterminaAContrarreItem;
//	protected ExtendedTextItem siglaAttoDeterminaAContrarreItem;
//	protected ExtendedNumericItem numeroAttoDeterminaAContrarreItem;
//	protected AnnoItem annoAttoDeterminaAContrarreItem;		
//	protected ImgButtonItem lookupArchivioAttoDeterminaAContrarreButton;
	
	/* Dati scheda - Specificità del provvedimento */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionCaratteristicheProvvedimento;
	protected DynamicForm caratteristicheProvvedimentoForm;
	protected SelectItem oggLiquidazioneItem; 
	protected ImgButtonItem showInfoOggLiquidazioneButton;
	protected DateItem dataScadenzaLiquidazioneItem; 
	protected SelectItem urgenzaLiquidazioneItem;
	protected CheckboxItem flgLiqXUffCassaItem; 
	protected ExtendedNumericItem importoAnticipoCassaItem;
	protected DateItem dataDecorrenzaContrattoItem; 
	protected NumericItem anniDurataContrattoItem;
	protected CheckboxItem flgAffidamentoItem;
	protected CheckboxItem flgDeterminaAContrarreTramiteProceduraGaraItem;
	protected CheckboxItem flgDeterminaAggiudicaProceduraGaraItem;
	protected CheckboxItem flgDeterminaRimodulazioneSpesaGaraAggiudicataItem;
	protected CheckboxItem flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem;
	protected CheckboxItem flgDeterminaRiaccertamentoItem;
	protected CheckboxItem flgDeterminaAccertRadiazItem;
	protected CheckboxItem flgDeterminaVariazBilItem;
	protected CheckboxItem flgVantaggiEconomiciItem;
	protected CheckboxItem flgDecretoReggioItem;
	protected CheckboxItem flgAvvocaturaItem;
	protected RadioGroupItem flgSpesaItem;
	protected CheckboxItem flgCorteContiItem;
	protected CheckboxItem flgLiqContestualeImpegnoItem; 
	protected CheckboxItem flgLiqContestualeAltriAspettiRilContItem;
	protected CheckboxItem flgCompQuadroFinRagDecItem;
	protected CheckboxItem flgNuoviImpAccItem;
	protected CheckboxItem flgImpSuAnnoCorrenteItem;
	protected CheckboxItem flgInsMovARagioneriaItem;
	protected CheckboxItem flgPresaVisioneContabilitaItem;	
	protected CheckboxItem flgSpesaCorrenteItem;
	protected CheckboxItem flgImpegniCorrenteGiaValidatiItem;
	protected CheckboxItem flgSpesaContoCapitaleItem;
	protected CheckboxItem flgImpegniContoCapitaleGiaRilasciatiItem;
	protected CheckboxItem flgSoloSubImpSubCronoItem;
	protected SelectItem tipoAttoInDeliberaPEGItem;
	protected SelectItem tipoAffidamentoItem; 
	protected SelectItem normRifAffidamentoItem; 
	protected TextItem respAffidamentoItem;
	protected FilteredSelectItem materiaTipoAttoItem;
	protected HiddenItem desMateriaTipoAttoItem;
	protected CheckboxItem flgFondiEuropeiPONItem;
	protected CheckboxItem flgFondiPRUItem;
	protected CheckboxItem flgVistoPar117_2013Item;
	protected CheckboxItem flgNotificaDaMessiItem;
	protected RadioGroupItem flgLLPPItem;
	protected AnnoItem annoProgettoLLPPItem;
	protected TextItem numProgettoLLPPItem;
	protected RadioGroupItem flgBeniServiziItem;
	protected AnnoItem annoProgettoBeniServiziItem;
	protected TextItem numProgettoBeniServiziItem;
	protected RadioGroupItem flgPrivacyItem;
	protected CheckboxItem flgDatiProtettiTipo1Item;
	protected CheckboxItem flgDatiProtettiTipo2Item;
	protected CheckboxItem flgDatiProtettiTipo3Item;
	protected CheckboxItem flgDatiProtettiTipo4Item;
	
	/* Dati scheda - Dest. vantaggio */
	
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionDestVantaggio;
	protected DynamicForm destVantaggioForm;
	protected DestVantaggioItem listaDestVantaggioItem;	
		
	/* Dati scheda - Ruoli e visti per dati contabili */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionRuoliEVistiXDatiContabili;
	protected DynamicForm ruoliXDatiContabiliForm1;
	protected CheckboxItem flgAdottanteUnicoRespPEGItem;	
	protected ResponsabiliPEGCompletaItem listaResponsabiliPEGItem;
	protected DynamicForm ruoliXDatiContabiliForm2;
	protected HiddenItem codUfficioDefinizioneSpesaItem;
	protected HiddenItem desUfficioDefinizioneSpesaItem;
	protected SelezionaUOItem listaUfficioDefinizioneSpesaItem;
	protected RadioGroupItem opzAssCompSpesaItem;
	protected DynamicForm vistiXDatiContabiliForm;		
	protected CheckboxItem flgRichVerificaDiBilancioCorrenteItem; 
	protected CheckboxItem flgRichVerificaDiBilancioContoCapitaleItem; 
	protected CheckboxItem flgRichVerificaDiContabilitaItem;
	protected CheckboxItem flgConVerificaContabilitaItem;
	protected CheckboxItem flgRichiediParereRevisoriContabiliItem;
	
	/* Dati scheda - CIG */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionCIG;
	protected DynamicForm CIGForm;
	protected RadioGroupItem flgOpCommercialeItem;
	protected CheckboxItem flgEscludiCIGItem;
	protected SelectItemValoriDizionario motivoEsclusioneCIGItem;
	protected CIGItem listaCIGItem;
	
	/************************
	 * TAB DATI DISPOSITIVO *
	 ************************/
	
	/* Dati dispositivo - Riferimenti normativi */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionRiferimentiNormativi;
	protected DynamicForm riferimentiNormativiForm;
	protected RiferimentiNormativiItem listaRiferimentiNormativiItem;
	
	/* Dati dispositivo - Atti presupposti */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionAttiPresupposti;
	protected DynamicForm attiPresuppostiForm;
//	protected AttiPresuppostiItem listaAttiPresuppostiItem;
	protected CKEditorItem attiPresuppostiItem;
		
	/* Dati dispositivo - Motivazioni */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionMotivazioni;
	protected DynamicForm motivazioniForm;
	protected CKEditorItem motivazioniItem;
	
	/* Dati dispositivo - Premessa */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionPremessa;
	protected DynamicForm premessaForm;
	protected CKEditorItem premessaItem;
	
	/* Dati dispositivo - Dispositivo */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionDispositivo;
	protected DynamicForm dispositivoForm;
	protected CKEditorItem dispositivoItem;
	protected SelectItemValoriDizionario loghiAggiuntiviDispositivoItem;
	
	/**************************
	 * TAB DATI DISPOSITIVO 2 *
	 **************************/
	
	/* Dati dispositivo 2 - Premessa 2 */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionPremessa2;
	protected DynamicForm premessa2Form;
	protected CKEditorItem premessa2Item;
	
	/* Dati dispositivo 2 - Dispositivo 2 */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionDispositivo2;
	protected DynamicForm dispositivo2Form;
	protected CKEditorItem dispositivo2Item;
	
	/****************
	 * TAB ALLEGATI *
	 ****************/
	
	/* Allegati */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionAllegati;	
	protected DynamicForm allegatiForm;
	protected CanvasItem listaAllegatiItem;
	
	/*******************************
	 * TAB PUBBLICAZIONE/NOTIFICHE *
	 *******************************/
	
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionPubblAlbo;
	protected DynamicForm pubblAlboForm;
	protected RadioGroupItem flgPubblAlboItem;
	protected NumericItem numGiorniPubblAlboItem;
	protected SelectItem tipoDecorrenzaPubblAlboItem;
	protected DateItem dataPubblAlboDalItem;
	protected CheckboxItem flgUrgentePubblAlboItem;
	protected DateItem dataPubblAlboEntroItem;
	
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionPubblAmmTrasp;
	protected DynamicForm pubblAmmTraspForm;
	protected RadioGroupItem flgPubblAmmTraspItem;
	protected SelectItem sezionePubblAmmTraspItem;
	protected SelectItem sottoSezionePubblAmmTraspItem;
	
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionPubblBUR;
	protected DynamicForm pubblBURForm;	
	protected RadioGroupItem flgPubblBURItem;
	protected AnnoItem annoTerminePubblBURItem;
	protected SelectItem tipoDecorrenzaPubblBURItem;
	protected DateItem dataPubblBURDalItem;
	protected CheckboxItem flgUrgentePubblBURItem;
	protected DateItem dataPubblBUREntroItem;
	
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionPubblNotiziario;
	protected DynamicForm pubblNotiziarioForm;	
	protected RadioGroupItem flgPubblNotiziarioItem;
	
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionEsecutivita;
	protected DynamicForm esecutivitaForm1;	
	protected DateItem dataEsecutivitaDalItem;
	protected CheckboxItem flgImmediatamenteEseguibileItem;
	protected DynamicForm esecutivitaForm2;	
	protected CKEditorItem motiviImmediatamenteEseguibileItem;
	
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionNotifiche;
	protected DynamicForm notificheForm;
	private ComboBoxItem listaDestNotificaAttoItem;
	private ImgButtonItem lookupRubricaEmailDestinatariButton;
	
	/***************************
	 * TAB MOVIMENTI CONTABILI *
	 ***************************/
	
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionMovimentiContabili;
	protected DynamicForm movimentiContabiliForm;
	protected GridItem listaMovimentiContabiliaItem;
	protected ListaInvioMovimentiContabiliSICRAItem listaInvioMovimentiContabiliSICRAItem;
	
	protected HashSet<String> vociPEGNoVerifDisp = new HashSet<String>();
	
	/***************************
	 * TAB DATI SPESA CORRENTE *
	 ***************************/

	protected NuovaPropostaAtto2CompletaDetailSection detailSectionOpzioniSpesaCorrente;	
	protected DynamicForm opzioniSpesaCorrenteForm1;
	protected CheckboxItem flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem;
	protected DynamicForm opzioniSpesaCorrenteForm2;
	protected RadioGroupItem prenotazioneSpesaSIBDiCorrenteItem;
	protected RadioGroupItem modalitaInvioDatiSpesaARagioneriaCorrenteItem;
	
	/* Dati spesa corrente - Impegni e accertamenti */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionDatiContabiliSIBCorrente;	
	protected DynamicForm datiContabiliSIBCorrenteForm;
	protected ListaDatiContabiliSIBItem listaDatiContabiliSIBCorrenteItem;
	
	/* Dati spesa corrente - Compilazione griglia */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionInvioDatiSpesaCorrente;	
	protected DynamicForm invioDatiSpesaCorrenteForm;
	protected ListaInvioDatiSpesaItem listaInvioDatiSpesaCorrenteItem;
	
	/* Dati spesa corrente - Upload xls importabile in SIB */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionFileXlsCorrente;	
	protected DynamicForm fileXlsCorrenteForm;
	protected DocumentItem fileXlsCorrenteItem;
	protected HiddenItem nomeFileTracciatoXlsCorrenteItem;
	protected HiddenItem uriFileTracciatoXlsCorrenteItem;
	protected ImgButtonItem scaricaTracciatoXlsCorrenteButton;
	
	/* Dati spesa corrente - Note */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionNoteCorrente;	
	protected DynamicForm noteCorrenteForm;
	protected CKEditorItem noteCorrenteItem;
		
	/*********************************
	 * TAB DATI SPESA CONTO CAPITALE *
	 *********************************/

	protected NuovaPropostaAtto2CompletaDetailSection detailSectionOpzioniSpesaContoCapitale;	
	protected DynamicForm opzioniSpesaContoCapitaleForm1;	
	protected CheckboxItem flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem;
	protected DynamicForm opzioniSpesaContoCapitaleForm2;	
	protected RadioGroupItem modalitaInvioDatiSpesaARagioneriaContoCapitaleItem;
	
	/* Dati spesa conto capitale - Impegni e accertamenti */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionDatiContabiliSIBContoCapitale;	
	protected DynamicForm datiContabiliSIBContoCapitaleForm;
	protected ListaDatiContabiliSIBItem listaDatiContabiliSIBContoCapitaleItem;
	
	/* Dati spesa conto capitale - Compilazione griglia */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionInvioDatiSpesaContoCapitale;	
	protected DynamicForm invioDatiSpesaContoCapitaleForm;
	protected ListaInvioDatiSpesaItem listaInvioDatiSpesaContoCapitaleItem;
	
	/* Dati spesa conto capitale - Upload xls importabile in SIB */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionFileXlsContoCapitale;	
	protected DynamicForm fileXlsContoCapitaleForm;
	protected DocumentItem fileXlsContoCapitaleItem;
	protected HiddenItem nomeFileTracciatoXlsContoCapitaleItem;
	protected HiddenItem uriFileTracciatoXlsContoCapitaleItem;
	protected ImgButtonItem scaricaTracciatoXlsContoCapitaleButton;
	
	/* Dati spesa conto capitale - Note */
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionNoteContoCapitale;	
	protected DynamicForm noteContoCapitaleForm;
	protected CKEditorItem noteContoCapitaleItem;
	
	/**********************************
	 * TAB AGGREGATO/SMISTAMENTO ACTA *
	 **********************************/
	
	protected NuovaPropostaAtto2CompletaDetailSection detailSectionAggregatoSmistamentoACTA;
	protected DynamicForm flgAggregatoACTAForm;
	protected CheckboxItem flgAggregatoACTAItem;
	protected DynamicForm aggregatoACTAForm;
	protected CheckboxItem flgIndiceClassificazioneACTAItem;
	protected ExtendedTextItem codIndiceClassificazioneACTAItem;
	protected HiddenItem flgPresenzaClassificazioneACTAItem;
	protected CheckboxItem flgFascicoloACTAItem;
	protected ExtendedTextItem codVoceTitolarioACTAItem;
	protected ExtendedTextItem codFascicoloACTAItem;
	protected ExtendedTextItem suffissoCodFascicoloACTAItem;	
	protected HiddenItem idFascicoloACTAItem;
	protected DynamicForm flgSmistamentoACTAForm;
	protected CheckboxItem flgSmistamentoACTAItem;
	protected DynamicForm smistamentoACTAForm;
	protected SelectItem idNodoSmistamentoACTAItem;
	protected HiddenItem desNodoSmistamentoACTAItem;

	/******************************
	 * TAB ATTRIBUTI DINAMICI DOC *
	 ******************************/

	protected LinkedHashMap<String, String> attributiAddDocTabs;
	protected HashMap<String, VLayout> attributiAddDocLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddDocDetails;
	protected Map<String, Object> attributiDinamiciDocDaCopiare;
	
	protected EmendamentiWindow emendamentiWindow;
	protected ListaEmendamentiPopup listaEmendamentiPopup;
	
	public NuovaPropostaAtto2CompletaDetail(String nomeEntita) {
		this(nomeEntita, null, null);
	}
	
	public NuovaPropostaAtto2CompletaDetail(String nomeEntita, LinkedHashMap<String, String> attributiAddDocTabs) {
		this(nomeEntita, attributiAddDocTabs, null);
	}
	
	public NuovaPropostaAtto2CompletaDetail(String nomeEntita, LinkedHashMap<String, String> attributiAddDocTabs, String idTipoDoc) {
		
		super(nomeEntita);

		instance = this;
		this.attributiAddDocTabs = attributiAddDocTabs != null ? attributiAddDocTabs : new LinkedHashMap<String, String>();
		this.tipoDocumento = idTipoDoc;
		
		setPaddingAsLayoutMargin(false);		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		if(!skipSuperBuild()) {
			build();
		}
		
		/****** [EMEND] ELIMINA RIGA PER EMENDAMENTI ******
	    setCanDragResize(false); 
	    ****** [EMEND] ELIMINA RIGA PER EMENDAMENTI ******/
	}
	
	public boolean skipSuperBuild() {
		return false;
	}
	
	public void build() {
		
		// lista di definizione degli attributi custom cablati a maschera (label, obbligatorietà ecc..)
		RecordList listaAttributiCustomCablati = recordParametriTipoAtto != null ? recordParametriTipoAtto.getAttributeAsRecordList("attributiCustomCablati") : null;
		if(listaAttributiCustomCablati != null) {
			attributiCustomCablati = new HashMap<String, Record>();
			for(int i = 0; i < listaAttributiCustomCablati.getLength(); i++) {				
				attributiCustomCablati.put(listaAttributiCustomCablati.get(i).getAttribute("attrName"), listaAttributiCustomCablati.get(i));
			}			
		}
		
		RecordList listaValoriUfficioProponente = recordParametriTipoAtto != null ? recordParametriTipoAtto.getAttributeAsRecordList("valoriUfficioProponente") : null;
		if(listaValoriUfficioProponente != null) {
			ufficioProponenteValueMap = new LinkedHashMap<String, String>();
			for(int i = 0; i < listaValoriUfficioProponente.getLength(); i++) {				
				ufficioProponenteValueMap.put(listaValoriUfficioProponente.get(i).getAttribute("idUo"), listaValoriUfficioProponente.get(i).getAttribute("descrizione"));
			}			
		}
		
		flgAllegAttoParteIntDefaultXTipoAtto = recordParametriTipoAtto != null ? recordParametriTipoAtto.getAttributeAsBoolean("flgAllegAttoParteIntDefaultXTipoAtto") : null;
		flgAllegAttoParteIntDefaultOrdPermanente = recordParametriTipoAtto != null ? recordParametriTipoAtto.getAttributeAsBoolean("flgAllegAttoParteIntDefaultOrdPermanente") : null;
		flgAllegAttoParteIntDefaultOrdTemporanea = recordParametriTipoAtto != null ? recordParametriTipoAtto.getAttributeAsBoolean("flgAllegAttoParteIntDefaultOrdTemporanea") : null;
		
		flgAllegAttoPubblSepDefaultXTipoAtto = recordParametriTipoAtto != null ? recordParametriTipoAtto.getAttributeAsBoolean("flgAllegAttoPubblSepDefaultXTipoAtto") : null;
		
		createMainLayout();
		setMembers(mainLayout);

		redrawCKEditorItems();
		enableDisableTabs();
		showHideSections();		
		openSections();
		
		/****** [EMEND] ELIMINA RIGA PER EMENDAMENTI] ******
		createEmendamentiWindows(); 
		****** [EMEND] ELIMINA RIGA PER EMENDAMENTI ******/
	}
	
	public HashMap<String, Record> recuperaAttributiCustomCablati() {
		return null;
	}
	
	public boolean isAvvioPropostaAtto() {
		return false;
	}	
	
	public boolean isAttivaRequestMovimentiDaAMC() {
		return false;
	}
	
	public boolean isAttivaSalvataggioMovimentiDaAMC() {
		return false;
	}		
	
	public boolean isEseguibile() {
		return editing;
	}
	
	public boolean isDatiSpesaEditabili() {
		return false;
	}
	
	/*
	public boolean isEsitoTaskSelezionatoOk() {
		return false;
	}
	*/
	
	public String getMessaggioTab(String tabID) {
		return null;
	}

	/**
	 * Metodo per costruire il layout della maschera
	 * 
	 */
	protected void createMainLayout() {
		
		createMainToolStrip();
		createTabSet();
		
		mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();		
		mainLayout.addMember(mainToolStrip);		
		mainLayout.addMember(tabSet);
		
		if (!showMainToolStrip()) {
			mainToolStrip.hide();
		}
	}
	
	/**
	 * Metodo che indica se mostrare o meno la barra superiore
	 * 
	 */
	public boolean showMainToolStrip() {
		return showModelliSelectItem() || showSalvaModello();
	}
	
	/**
	 * Metodo per costruire la barra superiore contenente la select dei modelli
	 * 
	 */
	protected void createMainToolStrip() {

		createModelliSelectItem();
		createSalvaComeModelloButton();
		
		mainToolStrip = new ToolStrip();
		mainToolStrip.setBackgroundColor("transparent");
		mainToolStrip.setBackgroundImage("blank.png");
		mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		mainToolStrip.setBorder("0px");
		mainToolStrip.setWidth100();
		mainToolStrip.setHeight(30);
		mainToolStrip.setRedrawOnResize(true);
		
		modelliSelectItem.setVisible(showModelliSelectItem());
		salvaComeModelloButton.setVisible(showSalvaModello());
		
		if (!showModelliSelectItem() && showSalvaModello()) {
			mainToolStrip.addButton(salvaComeModelloButton);
			mainToolStrip.addFormItem(modelliSelectItem);
		} else {
			mainToolStrip.addFormItem(modelliSelectItem);
			mainToolStrip.addButton(salvaComeModelloButton);
		}
		
		mainToolStrip.addFill();
	}
	
	/**
	 * Metodo che indica se mostrare o meno la select dei modelli
	 * 
	 */
	public boolean showModelliSelectItem() {
		return isAvvioPropostaAtto();
	}
	
	/**
	 * Metodo che indica se mostrare o meno il pulsante di salvataggio del modello
	 * 
	 */
	public boolean showSalvaModello() {
		return false;
	}
	
	/**
	 * Metodo che indica se è possibile rimuovere il modello dalla select
	 * 
	 */
	public boolean isAbilToRemoveModello(Record record) {
		return (record.getAttribute("key") != null && !"".equals(record.getAttributeAsString("key"))) && 
			   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
	}

	/**
	 * Metodo per costruire la select dei modelli (per il settaggio dei valori
	 * dei campi a maschera)
	 * 
	 */
	protected void createModelliSelectItem() throws IllegalStateException {

		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		modelliDS.addParam("prefKey", getPrefKeyModelliDSprefix() + ".modelli");
		modelliDS.addParam("isAbilToPublic", Layout.isPrivilegioAttivo("MRP") ? "1" : "0");

		modelliSelectItem = new SelectItem("modelli");
		modelliSelectItem.setValueField("key");
		modelliSelectItem.setDisplayField("displayValue");
		modelliSelectItem.setTitle("<b>" + I18NUtil.getMessages().protocollazione_detail_modelliSelectItem_title() + "</b>");
		modelliSelectItem.setCachePickListResults(false);
		modelliSelectItem.setRedrawOnChange(true);
		modelliSelectItem.setShowOptionsFromDataSource(true);
		modelliSelectItem.setOptionDataSource(modelliDS);
		modelliSelectItem.setAllowEmptyValue(true);

		ListGridField modelliRemoveField = new ListGridField("remove");
		modelliRemoveField.setType(ListGridFieldType.ICON);
		modelliRemoveField.setWidth(18);
		modelliRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isAbilToRemoveModello(record)) {
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				}
				return null;
			}
		});
		modelliRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				if (isAbilToRemoveModello(event.getRecord())) {
					final String key = event.getRecord().getAttribute("key");
					modelliDS.removeData(event.getRecord(), new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							String value = (String) modelliSelectItem.getValue();
							if (key != null && value != null && key.equals(value)) {
								modelliSelectItem.setValue((String) null);
							}
						} 
					});
				}				
			}   
		});
		ListGridField modelliKeyField = new ListGridField("key");
		modelliKeyField.setHidden(true);
		ListGridField modelliDisplayValueField = new ListGridField("displayValue");
		modelliDisplayValueField.setWidth("100%");
		modelliSelectItem.setPickListFields(modelliRemoveField, modelliKeyField, modelliDisplayValueField);

		modelliPickListProperties = new ListGrid();
		modelliPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		modelliPickListProperties.setShowHeader(false);
		// apply the selected preference from the SelectItem
		modelliPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {				
				boolean isRemoveField = isAbilToRemoveModello(event.getRecord()) && event.getColNum() == 0;
				if(!isRemoveField) {									
					String userid = (String) event.getRecord().getAttribute("userid");
					String prefName = (String) event.getRecord().getAttribute("prefName");
					if (prefName != null && !"".equals(prefName)) {
						AdvancedCriteria criteria = new AdvancedCriteria();
						criteria.addCriteria("prefName", OperatorId.EQUALS, prefName);
						criteria.addCriteria("flgIncludiPubbl", userid.startsWith("PUBLIC.") ? "1" : "0");
						if (userid.startsWith("UO.")) {
							String idUo = (String) event.getRecord().getAttribute("idUo");
							criteria.addCriteria("idUo", idUo);
						}
						modelliDS.addParam("isControllaValiditaIndirizzoMezzoTrasm",  "1"); 					
						modelliDS.fetchData(criteria, new DSCallback() {
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Record[] data = response.getData();
									if (data.length != 0) {
										Record record = new Record(JSON.decode(data[0].getAttribute("value")));								
										editNewRecordFromModello(record.toMap());
										setAttributiDinamiciDocDaCopiare(record.getAttributeAsMap("valori")); //TODO non funziona, non carica correttamente gli attributi dinamici a maschera
										newMode();
									}
								}
							}
						});
					}					
				}
			}
		});

		modelliSelectItem.setPickListProperties(modelliPickListProperties);
	}
	
	public String getPrefKeyModelliDSprefix() {
		return "";
	}

	public void editNewRecordFromModello(Map valuesFromModello) {
//		if (isAbilToSelUffPropEsteso()) {		
//			RecordList listaUfficioProponente = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaUfficioProponente") : null;
//			valuesFromModello.put("listaUfficioProponente", listaUfficioProponente);	
//		} else {
//			valuesFromModello.put("ufficioProponente", getValueAsString("ufficioProponente"));
//			valuesFromModello.put("codUfficioProponente", getValueAsString("codUfficioProponente"));
//			valuesFromModello.put("desUfficioProponente", getValueAsString("desUfficioProponente"));	
//		}
		editNewRecord(valuesFromModello);
//		if (isAbilToSelUffPropEsteso()) {
//			listaUfficioProponenteItem.manageChangedUoSelezionata();
//		} else {
//			ufficioProponenteItem.fireEvent(new ChangedEvent(ufficioProponenteItem.getJsObj()));
//		}
		if (isAbilToSelUffPropEsteso()) {	
			//TODO se il valore non è più selezionabile nella select lo sbianco
		} else {
			if(getUfficioProponenteValueMap().size() == 1) {
				String key = getUfficioProponenteValueMap().keySet().toArray(new String[1])[0];
				String value = getUfficioProponenteValueMap().get(key);
				ufficioProponenteItem.setValue((key != null && key.startsWith("UO")) ? key.substring(2) : key);
				if(value != null && !"".equals(value)) {
					codUfficioProponenteItem.setValue(value.substring(0, value.indexOf(" - ")));
					desUfficioProponenteItem.setValue(value.substring(value.indexOf(" - ") + 3));
				}			
				afterSelezioneUoProponente();
			} else if (valuesFromModello.get("ufficioProponente") != null && !getUfficioProponenteValueMap().containsKey(valuesFromModello.get("ufficioProponente"))) {		
				ufficioProponenteItem.setValue("");
				codUfficioProponenteItem.setValue("");
				desUfficioProponenteItem.setValue("");
				afterSelezioneUoProponente();
			}
		}
	}

	/**
	 * Metodo per costruire la finestra di salvataggio del modello
	 * 
	 */
	protected void createSaveModelloWindow(String nomeEntita) {

		if (saveModelloWindow == null) {
			SaveModelloAction saveModelloAction = new SaveModelloAction(modelliDS, modelliSelectItem) {

				@Override
				public Map getMapValuesForAdd() {
					Record lRecordToSave = getRecordToSave();
					Map values = lRecordToSave != null ? lRecordToSave.toMap() : vm.getValues();
					removeValuesToSkipInModello(values);		
					if (attributiAddDocDetails != null) {
						values.put("valori", getAttributiDinamiciDocForLoad());
					}
					return values;
				}

				@Override
				public Map getMapValuesForUpdate() {
					Record lRecordToSave = getRecordToSave();
					Map values = lRecordToSave != null ? lRecordToSave.toMap() : vm.getValues();
					removeValuesToSkipInModello(values);  
					if (attributiAddDocDetails != null) {
						values.put("valori", getAttributiDinamiciDocForLoad());
					}
					return values;
				}
			};
			saveModelloWindow = new SaveModelloWindow(
					I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(), getPrefKeyModelliDSprefix(),
					saveModelloAction) {

				@Override
				public boolean isAbilToSavePublic() {
					return Layout.isPrivilegioAttivo("MRP");
				}
			};
		}
	}
	
	public void removeValuesToSkipInModello(Map<String, Object> values) {
		removeValuesToSkipInCopia(values, false);
	}
	
	public void removeValuesToSkipInNuovoComeCopia(Map<String, Object> values) {
		removeValuesToSkipInCopia(values, true);
	}
	
	public void removeValuesToSkipInCopia(Map<String, Object> values, boolean isNuovoComeCopia) {
		values.remove("idUd");
		values.remove("rowidDoc");
		values.remove("idDocPrimario");		
		values.remove("nomeFilePrimario");
		values.remove("uriFilePrimario");
		values.remove("remoteUriFilePrimario");
		values.remove("infoFilePrimario");
		values.remove("isChangedFilePrimario");
		values.remove("idDocPrimarioOmissis");
		values.remove("nomeFilePrimarioOmissis");
		values.remove("uriFilePrimarioOmissis");
		values.remove("remoteUriFilePrimarioOmissis");
		values.remove("infoFilePrimarioOmissis");
		values.remove("isChangedFilePrimarioOmissis");
		values.remove("idPropostaAMC");
		values.remove("listaEmendamenti");
		values.remove("uriDocGeneratoFormatoOdt");
		values.remove("codAOOXSelNodoACTA");
		values.remove("codStrutturaXSelNodoACTA");		
		values.remove("siglaRegistrazione");
		values.remove("numeroRegistrazione");
		values.remove("dataRegistrazione");
		values.remove("desUserRegistrazione");		
		values.remove("desUORegistrazione");		
		values.remove("estremiRepertorioPerStruttura");	
		values.remove("siglaRegProvvisoria");		
		values.remove("numeroRegProvvisoria");		
		values.remove("dataRegProvvisoria");		
		values.remove("desUserRegProvvisoria");		
		values.remove("desUORegProvvisoria");
		values.remove("esitoInvioACTASerieAttiIntegrali");
		values.remove("esitoInvioACTASeriePubbl");
		values.remove("idUdLiquidazione");
		values.remove("estremiAttoLiquidazione");
//		values.remove("listaUfficioProponente");
//		values.remove("codUfficioProponente");
//		values.remove("desUfficioProponente");
//		values.remove("ufficioProponente");
		values.remove("listaAllegati");
		values.remove("listaMovimentiContabilia");
		values.remove("listaInvioMovimentiContabiliSICRA");		
		values.remove("listaDatiContabiliSIBCorrente");
		values.remove("listaInvioDatiSpesaCorrente");
		values.remove("listaDatiContabiliSIBContoCapitale");
		values.remove("listaInvioDatiSpesaContoCapitale");
		if(isNuovoComeCopia) {
			values.remove("flgAggregatoACTA");
			values.remove("flgIndiceClassificazioneACTA");
			values.remove("codIndiceClassificazioneACTA");
			values.remove("flgPresenzaClassificazioneACTA");
			values.remove("flgFascicoloACTA");
			values.remove("codVoceTitolarioACTA");
			values.remove("codFascicoloACTA");
			values.remove("suffissoCodFascicoloACTA");	
			values.remove("idFascicoloACTA");
			values.remove("flgSmistamentoACTA");
			values.remove("idNodoSmistamentoACTA");
			values.remove("desNodoSmistamentoACTA");
		}
	}

	/**
	 * Metodo per costruire il bottone "Salva come modello"
	 * 
	 */
	protected void createSalvaComeModelloButton() {

		salvaComeModelloButton = new ToolStripButton(
				I18NUtil.getMessages().protocollazione_detail_salvaComeModelloButton_prompt(),
				"buttons/template_save.png");
		salvaComeModelloButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clickSalvaComeModello();
			}
		});
	}

	/**
	 * Metodo che implementa l'azione del bottone "Salva come modello"
	 * 
	 */
	public void clickSalvaComeModello() {

		createSaveModelloWindow(nomeEntita);
		if ((!saveModelloWindow.isDrawn()) || (!saveModelloWindow.isVisible())) {
			saveModelloWindow.clearValues();
			saveModelloWindow.selezionaModello(modelliSelectItem.getSelectedRecord());
			saveModelloWindow.redrawForms();
			saveModelloWindow.redraw();
			saveModelloWindow.show();
		}
	}
	
	/**
	 * Metodo per costruire il TabSet
	 * 
	 */
	protected void createTabSet() throws IllegalStateException {

		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);
		tabSet.addTabSelectedHandler(new TabSelectedHandler() {
			
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				final List<ReplicableItem> listaReplicableItemWithError = new ArrayList<ReplicableItem>();
				String tabID = event.getTab().getAttribute("tabID");
				for(DynamicForm form : getTabForms(tabID)) {
					for (FormItem item : form.getFields()) {
						if (item != null) {
							if (item instanceof ReplicableItem) {
								ReplicableItem lReplicableItem = (ReplicableItem) item;
								if (lReplicableItem.getMapErrors() != null && lReplicableItem.getMapErrors().size() > 0) {
									listaReplicableItemWithError.add(lReplicableItem);	
								}																				
							} 
						}
					}
				}
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						for(ReplicableItem lReplicableItem : listaReplicableItemWithError) {
							if (lReplicableItem.getMapErrors() == null || lReplicableItem.getMapErrors().size() == 0) {
								lReplicableItem.validate();
							}
						}
					}
				});				
			}
		});
		
		createTabDatiScheda();
//		tabSet.addTab(tabDatiScheda);

		createTabDatiDispositivo();
//		tabSet.addTab(tabDatiDispositivo);
		
		createTabDatiDispositivo2();
//		tabSet.addTab(tabDatiDispositivo2);
		
		createTabAllegati();
//		tabSet.addTab(tabAllegati);
			
		createTabPubblicazioneNotifiche();
//		tabSet.addTab(tabPubblicazioneNotifiche);
			
		createTabMovimentiContabili();
//		tabSet.addTab(tabMovimentiContabili);

		if(!isAvvioPropostaAtto() && isAttivoSIB()) {
			
			createTabDatiSpesaCorrente();
//			tabSet.addTab(tabDatiSpesaCorrente);
			
			createTabDatiSpesaContoCapitale();
//			tabSet.addTab(tabDatiSpesaContoCapitale);
		}
			
		createTabAggregatoSmistamentoACTA();
//		tabSet.addTab(tabAggregatoSmistamentoACTA);		
	}

	/**
	 * Metodo per costruire il pane associato ad un tab generico
	 * 
	 */
	protected VLayout createTabPane(VLayout layout) {

		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		
		return layoutTab;
	}

	/******************* 
	 * TAB DATI SCHEDA *
	 *******************/	
	
	/**
	 * Metodo per costruire il tab "Dati scheda"
	 * 
	 */
	protected void createTabDatiScheda() {

		tabDatiScheda = new Tab("<b>" + I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiScheda_prompt() + "</b>");
		tabDatiScheda.setAttribute("tabID", _TAB_DATI_SCHEDA_ID);
		tabDatiScheda.setPrompt(I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiScheda_prompt());
		tabDatiScheda.setPane(createTabPane(getLayoutDatiScheda()));
	}

	/**
	 * Metodo che restituisce il layout del tab "Dati scheda"
	 * 
	 */
	public VLayout getLayoutDatiScheda() {

		VLayout layoutDatiScheda = new VLayout(5);
		
		createHiddenForm();
		layoutDatiScheda.addMember(hiddenForm);
		
		createDetailSectionRegistrazione();
		detailSectionRegistrazione.setVisible(false);
		layoutDatiScheda.addMember(detailSectionRegistrazione);
		
//		createDetailSectionPubblicazione();
//		detailSectionPubblicazione.setVisible(false);
//		layoutDatiScheda.addMember(detailSectionPubblicazione);
		
		createDetailSectionEmendamento();
		layoutDatiScheda.addMember(detailSectionEmendamento);
		
		createAttivaDestinatariForm();
		layoutDatiScheda.addMember(attivaDestinatariForm);
		
		createDetailSectionDestinatariAtto();
		layoutDatiScheda.addMember(detailSectionDestinatariAtto);
		
		createDetailSectionDestinatariPCAtto();
		layoutDatiScheda.addMember(detailSectionDestinatariPCAtto);
		
		createDetailSectionTipoProposta();
		layoutDatiScheda.addMember(detailSectionTipoProposta);
		
		createDetailSectionCircoscrizioni();
		layoutDatiScheda.addMember(detailSectionCircoscrizioni);
		
		createDetailSectionInterpellanza();
		layoutDatiScheda.addMember(detailSectionInterpellanza);
		
		createDetailSectionOrdMobilita();
		layoutDatiScheda.addMember(detailSectionOrdMobilita);
		
		createDetailSectionRuoli();
		layoutDatiScheda.addMember(detailSectionRuoli);
		
		createDetailSectionVistiDirSuperiori();
		layoutDatiScheda.addMember(detailSectionVistiDirSuperiori);
				
		createDetailSectionParereCircoscrizioni();
		layoutDatiScheda.addMember(detailSectionParereCircoscrizioni);
		
		createDetailSectionParereCommissioni();
		layoutDatiScheda.addMember(detailSectionParereCommissioni);
		
		createDetailSectionOggetto();
		layoutDatiScheda.addMember(detailSectionOggetto);
		
		createDetailSectionAttoRiferimento();
		layoutDatiScheda.addMember(detailSectionAttoRiferimento);
		
		createDetailSectionCaratteristicheProvvedimento();
		layoutDatiScheda.addMember(detailSectionCaratteristicheProvvedimento);
		
		createDetailSectionDestVantaggio();
		layoutDatiScheda.addMember(detailSectionDestVantaggio);
		
		createDetailSectionRuoliEVistiXDatiContabili();
		layoutDatiScheda.addMember(detailSectionRuoliEVistiXDatiContabili);
		
		createDetailSectionCIG();
		layoutDatiScheda.addMember(detailSectionCIG);
	
		return layoutDatiScheda;
	}
	
	protected void createHiddenForm() {
		
		hiddenForm = new DynamicForm();
		hiddenForm.setValuesManager(vm);
		hiddenForm.setOverflow(Overflow.HIDDEN);
		hiddenForm.setTabSet(tabSet);
		hiddenForm.setTabID(_TAB_DATI_SCHEDA_ID);
		hiddenForm.setHeight(0);
		
		idUdHiddenItem = new HiddenItem("idUd");
		tipoDocumentoHiddenItem = new HiddenItem("tipoDocumento");
		nomeTipoDocumentoHiddenItem = new HiddenItem("nomeTipoDocumento");
		rowidDocHiddenItem = new HiddenItem("rowidDoc");
		idDocPrimarioHiddenItem = new HiddenItem("idDocPrimario");		
		nomeFilePrimarioHiddenItem = new HiddenItem("nomeFilePrimario");
		uriFilePrimarioHiddenItem = new HiddenItem("uriFilePrimario");
		remoteUriFilePrimarioHiddenItem = new HiddenItem("remoteUriFilePrimario");
		infoFilePrimarioHiddenItem = new HiddenItem("infoFilePrimario");
		isChangedFilePrimarioHiddenItem = new HiddenItem("isChangedFilePrimario");
		idDocPrimarioOmissisHiddenItem = new HiddenItem("idDocPrimarioOmissis");
		nomeFilePrimarioOmissisHiddenItem = new HiddenItem("nomeFilePrimarioOmissis");
		uriFilePrimarioOmissisHiddenItem = new HiddenItem("uriFilePrimarioOmissis");
		remoteUriFilePrimarioOmissisHiddenItem = new HiddenItem("remoteUriFilePrimarioOmissis");
		infoFilePrimarioOmissisHiddenItem = new HiddenItem("infoFilePrimarioOmissis");
		isChangedFilePrimarioOmissisHiddenItem = new HiddenItem("isChangedFilePrimarioOmissis");
		idPropostaAMCHiddenItem = new HiddenItem("idPropostaAMC");
		listaEmendamentiItem = new HiddenItem("listaEmendamenti");
		listaEmendamentiBloccoRiordinoAutItem = new HiddenItem("listaEmendamentiBloccoRiordinoAut");
		uriDocGeneratoFormatoOdtItem = new HiddenItem("uriDocGeneratoFormatoOdt");
		
		codAOOXSelNodoACTAItem = new HiddenItem("codAOOXSelNodoACTA");
		codStrutturaXSelNodoACTAItem = new HiddenItem("codStrutturaXSelNodoACTA");
		
		hiddenForm.setFields(
			idUdHiddenItem, 
			tipoDocumentoHiddenItem, 
			nomeTipoDocumentoHiddenItem, 
			rowidDocHiddenItem,
			idDocPrimarioHiddenItem, 
			nomeFilePrimarioHiddenItem,
			uriFilePrimarioHiddenItem,
			remoteUriFilePrimarioHiddenItem,
			infoFilePrimarioHiddenItem,
			isChangedFilePrimarioHiddenItem,
			idDocPrimarioOmissisHiddenItem, 
			nomeFilePrimarioOmissisHiddenItem,
			uriFilePrimarioOmissisHiddenItem,
			remoteUriFilePrimarioOmissisHiddenItem,
			infoFilePrimarioOmissisHiddenItem,		
			isChangedFilePrimarioOmissisHiddenItem,
			idPropostaAMCHiddenItem,
			listaEmendamentiItem,
			listaEmendamentiBloccoRiordinoAutItem,
			uriDocGeneratoFormatoOdtItem,
			codAOOXSelNodoACTAItem,
			codStrutturaXSelNodoACTAItem
		);
	}
	
	/******************************* 
	 * DATI SCHEDA - REGISTRAZIONE *
	 *******************************/
		
	public boolean showDetailSectionRegistrazione() {
		return !"".equals(getValueAsString("numeroRegistrazione")) || !"".equals(getValueAsString("numeroRegProvvisoria"));
	}
	
	protected void createDetailSectionRegistrazione() {
		
		createRegistrazioneForm();
		
		detailSectionRegistrazione = new HeaderNuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionRegistrazione_title(), true, true, false, registrazioneForm, invioACTAForm, attoLiquidazioneForm);
	}
	
	public boolean showDesUORegistrazioneItem() {
		return true;
	}
	
	protected void createRegistrazioneForm() {

		registrazioneForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		registrazioneForm.setValuesManager(vm);
		registrazioneForm.setWidth100();
		registrazioneForm.setPadding(5);
		registrazioneForm.setWrapItemTitles(false);
		registrazioneForm.setNumCols(20);
		registrazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		registrazioneForm.setTabSet(tabSet);
		registrazioneForm.setTabID(_TAB_DATI_SCHEDA_ID);
		registrazioneForm.setHeight(1);
		
		//Numerazione atto
		
		iconaTipoRegistrazioneItem = new ImgItem("iconaTipoRegistrazione", "menu/iter_atti.png", I18NUtil.getMessages().nuovaPropostaAtto2_detail_iconaTipoRegistrazione_prompt());
		iconaTipoRegistrazioneItem.setColSpan(1);
		iconaTipoRegistrazioneItem.setIconWidth(16);
		iconaTipoRegistrazioneItem.setIconHeight(16);
		iconaTipoRegistrazioneItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaTipoRegistrazioneItem.setAlign(Alignment.LEFT);
		iconaTipoRegistrazioneItem.setWidth(16);
		iconaTipoRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione"));
			}
		});

		siglaRegistrazioneItem = new TextItem("siglaRegistrazione",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_siglaRegistrazione_title());
		siglaRegistrazioneItem.setWidth(100);
		siglaRegistrazioneItem.setColSpan(1);
		siglaRegistrazioneItem.setShowTitle(false);
		siglaRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione"));
			}
		});

		numeroRegistrazioneItem = new NumericItem("numeroRegistrazione",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_numeroRegistrazione_title());
		numeroRegistrazioneItem.setColSpan(1);
		numeroRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione"));
			}
		});
		
		dataRegistrazioneItem = new DateTimeItem("dataRegistrazione",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_dataRegistrazione_title());
		dataRegistrazioneItem.setColSpan(1);
		dataRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione"));
			}
		});

		desUserRegistrazioneItem = new TextItem("desUserRegistrazione",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_desUserRegistrazione_title());
		desUserRegistrazioneItem.setWidth(200);
		desUserRegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione")) && !"".equals(getValueAsString("desUserRegistrazione"));
			}
		});

		desUORegistrazioneItem = new TextItem("desUORegistrazione",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_desUORegistrazione_title());
		desUORegistrazioneItem.setWidth(200);
		desUORegistrazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegistrazione")) && !"".equals(getValueAsString("desUORegistrazione")) && showDesUORegistrazioneItem();				 
			}
		});
		
		// Numero per struttura
		
		estremiRepertorioPerStrutturaItem = new TextItem("estremiRepertorioPerStruttura", "N° per struttura");
		estremiRepertorioPerStrutturaItem.setWidth(200);
		estremiRepertorioPerStrutturaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("estremiRepertorioPerStruttura"));		 
			}
		});

		// Numerazione provvisoria di proposta atto
		
		iconaTipoRegProvvisoriaItem = new ImgItem("iconaTipoRegProvvisoria", "protocollazione/provvisoria.png", I18NUtil.getMessages().nuovaPropostaAtto2_detail_iconaTipoRegProvvisoria_prompt());
		iconaTipoRegProvvisoriaItem.setColSpan(1);
		iconaTipoRegProvvisoriaItem.setIconWidth(16);
		iconaTipoRegProvvisoriaItem.setIconHeight(16);
		iconaTipoRegProvvisoriaItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaTipoRegProvvisoriaItem.setAlign(Alignment.LEFT);
		iconaTipoRegProvvisoriaItem.setWidth(16);
		iconaTipoRegProvvisoriaItem.setStartRow(true);		
		iconaTipoRegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria"));
			}
		});

		siglaRegProvvisoriaItem = new TextItem("siglaRegProvvisoria",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_siglaRegProvvisoria_title());
		siglaRegProvvisoriaItem.setWidth(100);
		siglaRegProvvisoriaItem.setColSpan(1);
		siglaRegProvvisoriaItem.setShowTitle(false);
		siglaRegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria"));
			}
		});

		numeroRegProvvisoriaItem = new NumericItem("numeroRegProvvisoria",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_numeroRegProvvisoria_title());
		numeroRegProvvisoriaItem.setColSpan(1);
		numeroRegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria"));
			}
		});

		dataRegProvvisoriaItem = new DateTimeItem("dataRegProvvisoria",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_dataRegProvvisoria_title());
		dataRegProvvisoriaItem.setColSpan(1);
		dataRegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria"));
			}
		});

		desUserRegProvvisoriaItem = new TextItem("desUserRegProvvisoria",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_desUserRegProvvisoria_title());
		desUserRegProvvisoriaItem.setWidth(250);
		desUserRegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria")) && !"".equals(getValueAsString("desUserRegProvvisoria")) && "".equals(getValueAsString("numeroRegistrazione"));				
			}
		});

		desUORegProvvisoriaItem = new TextItem("desUORegProvvisoria",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_desUORegProvvisoria_title());
		desUORegProvvisoriaItem.setWidth(250);
		desUORegProvvisoriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("numeroRegProvvisoria")) && !"".equals(getValueAsString("desUORegProvvisoria")) && "".equals(getValueAsString("numeroRegistrazione")) && showDesUORegistrazioneItem();				
			}
		});	

		/****** [EMEND] ELIMINA RIGA PER EMENDAMENTI ******
		ImgButtonItem visualizzaEmendamentiButton = new ImgButtonItem("visualizzaEmendamentiButton", "opener_closed.png", "Visualizza emendamenti");
		visualizzaEmendamentiButton.setAlwaysEnabled(true);
		visualizzaEmendamentiButton.setEndRow(false);
		visualizzaEmendamentiButton.setColSpan(1);
		visualizzaEmendamentiButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				// emendamentiFloatPanel.animateShow(AnimationEffect.SLIDE);
				emendamentiPanel.show();
				
			}
		});
		****** [EMEND] ELIMINA RIGA PER EMENDAMENTI ******/

		registrazioneForm.setFields(
			// Numerazione atto
			iconaTipoRegistrazioneItem, 
			siglaRegistrazioneItem,
			numeroRegistrazioneItem,
			dataRegistrazioneItem,
			desUserRegistrazioneItem,
			desUORegistrazioneItem,
			// Numero per struttura
			estremiRepertorioPerStrutturaItem,
			// Numerazione provvisoria di proposta atto
			iconaTipoRegProvvisoriaItem, 
			siglaRegProvvisoriaItem,
			numeroRegProvvisoriaItem,
			dataRegProvvisoriaItem,
			desUserRegProvvisoriaItem,
			desUORegProvvisoriaItem
			/****** [EMEND] ELIMINA RIGA PER EMENDAMENTI ******
			visualizzaEmendamentiButton
			****** [EMEND] ELIMINA RIGA PER EMENDAMENTI ******/
		);
		
		invioACTAForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		invioACTAForm.setValuesManager(vm);
		invioACTAForm.setWidth100();
		invioACTAForm.setPadding(5);
		invioACTAForm.setWrapItemTitles(false);
		invioACTAForm.setNumCols(20);
		invioACTAForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		invioACTAForm.setTabSet(tabSet);
		invioACTAForm.setTabID(_TAB_DATI_SCHEDA_ID);
		invioACTAForm.setHeight(1);
		
		TitleItem esitoInvioACTATitleItem = new TitleItem("Esito archiviazione in ACTA");
		esitoInvioACTATitleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				String fascSmistACTA = AurigaLayout.getParametroDB("FASC_SMIST_ACTA");
				if(fascSmistACTA != null && (NuovaPropostaAtto2CompletaDetail._MANDATORY.equalsIgnoreCase(fascSmistACTA) || NuovaPropostaAtto2CompletaDetail._OPTIONAL.equalsIgnoreCase(fascSmistACTA))) {
					return !"".equals(getValueAsString("esitoInvioACTASerieAttiIntegrali")) || !"".equals(getValueAsString("esitoInvioACTASeriePubbl"));
				}
				return false;
			}
		});
		
		esitoInvioACTASerieAttiIntegraliItem = new StaticTextItem("esitoInvioACTASerieAttiIntegrali");
		esitoInvioACTASerieAttiIntegraliItem.setShowTitle(false);
		esitoInvioACTASerieAttiIntegraliItem.setShowValueIconOnly(true);
		esitoInvioACTASerieAttiIntegraliItem.setHint("(serie atti in vers. integrale)&nbsp;&nbsp;");
		esitoInvioACTASerieAttiIntegraliItem.setWrapHintText(false);
		esitoInvioACTASerieAttiIntegraliItem.setHintStyle(it.eng.utility.Styles.formTitle);
		esitoInvioACTASerieAttiIntegraliItem.setWidth(16);
		esitoInvioACTASerieAttiIntegraliItem.setHeight(16);
		esitoInvioACTASerieAttiIntegraliItem.setColSpan(1);
		esitoInvioACTASerieAttiIntegraliItem.setValueIconSize(16);
		esitoInvioACTASerieAttiIntegraliItem.setVAlign(VerticalAlignment.CENTER);
		esitoInvioACTASerieAttiIntegraliItem.setCellStyle(it.eng.utility.Styles.staticTextItem);
		esitoInvioACTASerieAttiIntegraliItem.setAlign(Alignment.LEFT);
		Map<String, String> esitoInvioACTASerieAttiIntegraliValueIcons = new HashMap<String, String>();
		esitoInvioACTASerieAttiIntegraliValueIcons.put("OK", "ok.png");
		esitoInvioACTASerieAttiIntegraliValueIcons.put("KO", "ko.png");
		esitoInvioACTASerieAttiIntegraliValueIcons.put("", "blank.png");
		esitoInvioACTASerieAttiIntegraliItem.setValueIcons(esitoInvioACTASerieAttiIntegraliValueIcons);
		esitoInvioACTASerieAttiIntegraliItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				if("OK".equals(getValueAsString("esitoInvioACTASerieAttiIntegrali"))) {
					return "archiviato in ACTA nella serie degli atti in versione integrale";
				} else if("KO".equals(getValueAsString("esitoInvioACTASerieAttiIntegrali"))) {
					return "fallita archiviazione in ACTA nella serie degli atti in versione integrale";				
				}
				return null;
			}
		});
		esitoInvioACTASerieAttiIntegraliItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String fascSmistACTA = AurigaLayout.getParametroDB("FASC_SMIST_ACTA");
				if(fascSmistACTA != null && (NuovaPropostaAtto2CompletaDetail._MANDATORY.equalsIgnoreCase(fascSmistACTA) || NuovaPropostaAtto2CompletaDetail._OPTIONAL.equalsIgnoreCase(fascSmistACTA))) {
					return !"".equals(getValueAsString("esitoInvioACTASerieAttiIntegrali"));
				}
				return false;
			}
		});
		
		esitoInvioACTASeriePubblItem = new StaticTextItem("esitoInvioACTASeriePubbl");
		esitoInvioACTASeriePubblItem.setShowTitle(false);
		esitoInvioACTASeriePubblItem.setShowValueIconOnly(true);
		esitoInvioACTASeriePubblItem.setHint("(serie di pubbl.)&nbsp;&nbsp;");
		esitoInvioACTASeriePubblItem.setWrapHintText(false);
		esitoInvioACTASeriePubblItem.setHintStyle(it.eng.utility.Styles.formTitle);
		esitoInvioACTASeriePubblItem.setWidth(16);
		esitoInvioACTASeriePubblItem.setHeight(16);
		esitoInvioACTASeriePubblItem.setColSpan(1);
		esitoInvioACTASeriePubblItem.setValueIconSize(16);
		esitoInvioACTASeriePubblItem.setVAlign(VerticalAlignment.CENTER);
		esitoInvioACTASeriePubblItem.setCellStyle(it.eng.utility.Styles.staticTextItem);
		esitoInvioACTASeriePubblItem.setAlign(Alignment.LEFT);
		Map<String, String> esitoInvioACTASeriePubblValueIcons = new HashMap<String, String>();
		esitoInvioACTASeriePubblValueIcons.put("OK", "ok.png");
		esitoInvioACTASeriePubblValueIcons.put("KO", "ko.png");
		esitoInvioACTASeriePubblValueIcons.put("", "blank.png");
		esitoInvioACTASeriePubblItem.setValueIcons(esitoInvioACTASeriePubblValueIcons);
		esitoInvioACTASeriePubblItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				if("OK".equals(getValueAsString("esitoInvioACTASeriePubbl"))) {
					return "archiviato in ACTA nella serie di pubblicazione";
				} else if("KO".equals(getValueAsString("esitoInvioACTASeriePubbl"))) {
					return "fallita archiviazione in ACTA nella serie di pubblicazione";				
				}
				return null;
			}
		});
		esitoInvioACTASeriePubblItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String fascSmistACTA = AurigaLayout.getParametroDB("FASC_SMIST_ACTA");
				if(fascSmistACTA != null && (NuovaPropostaAtto2CompletaDetail._MANDATORY.equalsIgnoreCase(fascSmistACTA) || NuovaPropostaAtto2CompletaDetail._OPTIONAL.equalsIgnoreCase(fascSmistACTA))) {
					return !"".equals(getValueAsString("esitoInvioACTASeriePubbl"));
				}
				return false;
			}
		});		
					
		invioACTAForm.setFields(
			esitoInvioACTATitleItem,
			esitoInvioACTASerieAttiIntegraliItem,
			esitoInvioACTASeriePubblItem
		);
		
		attoLiquidazioneForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		attoLiquidazioneForm.setValuesManager(vm);
		attoLiquidazioneForm.setWidth100();
		attoLiquidazioneForm.setPadding(5);
		attoLiquidazioneForm.setWrapItemTitles(false);
		attoLiquidazioneForm.setNumCols(20);
		attoLiquidazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		attoLiquidazioneForm.setTabSet(tabSet);
		attoLiquidazioneForm.setTabID(_TAB_DATI_SCHEDA_ID);
		attoLiquidazioneForm.setHeight(1);
	
		// Atto di liquidazione
		
		idUdLiquidazioneItem = new HiddenItem("idUdLiquidazione");

		estremiAttoLiquidazioneItem = new TextItem("estremiAttoLiquidazione", "Atto liquidazione");
		estremiAttoLiquidazioneItem.setWidth(250);
		estremiAttoLiquidazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("estremiAttoLiquidazione"));		 
			}
		});
		
		ImgButtonItem dettaglioUdAttoLiquidazioneButton = new ImgButtonItem("dettaglioUdAttoLiquidazioneButton", "buttons/detail.png", "Visualizza dettaglio atto liquidazione");
		dettaglioUdAttoLiquidazioneButton.setColSpan(1);
		dettaglioUdAttoLiquidazioneButton.setAlwaysEnabled(true);
		dettaglioUdAttoLiquidazioneButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {				
				Record record = new Record();
				record.setAttribute("idUd", getValueAsString("idUdLiquidazione"));
				new DettaglioRegProtAssociatoWindow(record, "Dettaglio atto liquidazione " + getValueAsString("estremiAttoLiquidazione"));									
			}
		});
		dettaglioUdAttoLiquidazioneButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("estremiAttoLiquidazione")) && !"".equals(getValueAsString("idUdLiquidazione"));
			}
		});
				
		attoLiquidazioneForm.setFields(
			// Atto di liquidazione
			idUdLiquidazioneItem,
			estremiAttoLiquidazioneItem,
			dettaglioUdAttoLiquidazioneButton
		);
	}
	
	/******************************* 
	 * DATI SCHEDA - PUBBLICAZIONE *
	 *******************************/

//	public boolean showDetailSectionPubblicazione() {
//		return false;
//	}
//		
//	public Date getDataInizioPubblicazioneValue() {
//		return null;
//	}
//	
//	public String getGiorniPubblicazioneValue() {
//		return null;
//	}
//	
//	protected void createDetailSectionPubblicazione() {
//		
//		createPubblicazioneForm();
//		
//		detailSectionPubblicazione = new HeaderNuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionPubblicazione_title(), true, true, false, pubblicazioneForm);
//	}
//	
//	protected void createPubblicazioneForm() {
//		
//		pubblicazioneForm = new DynamicForm();
//		pubblicazioneForm.setValuesManager(vm);
//		pubblicazioneForm.setWidth100();
//		pubblicazioneForm.setPadding(5);
//		pubblicazioneForm.setWrapItemTitles(false);
//		pubblicazioneForm.setNumCols(20);
//		pubblicazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
//		pubblicazioneForm.setTabSet(tabSet);
//		pubblicazioneForm.setTabID(_TAB_DATI_SCHEDA_ID);
//		pubblicazioneForm.setHeight(1);
//		
//		dataInizioPubblicazioneItem = new DateItem("dataInizioPubblicazione", I18NUtil.getMessages().nuovaPropostaAtto2_detail_dataInizioPubblicazione_title());
//		dataInizioPubblicazioneItem.setColSpan(1);
//		dataInizioPubblicazioneItem.setAttribute("obbligatorio", true);
//		dataInizioPubblicazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem formItem, Object value) {
//				return showDetailSectionPubblicazione();
//			}
//		}));
//		
//		giorniPubblicazioneItem = new NumericItem("giorniPubblicazione", I18NUtil.getMessages().nuovaPropostaAtto2_detail_giorniPubblicazione_title(), false);
//		giorniPubblicazioneItem.setColSpan(1);
//		giorniPubblicazioneItem.setAttribute("obbligatorio", true);
//		giorniPubblicazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem formItem, Object value) {
//				return showDetailSectionPubblicazione();
//			}
//		}));
//				
//		pubblicazioneForm.setFields(dataInizioPubblicazioneItem, giorniPubblicazioneItem);			
//	}
	
	/************************ 
	 * DATI SCHEDA - EMENDA *
	 ************************/
	
	public boolean showDetailSectionEmendamento() {
		return showAttributoCustomCablato("DATI_EMANDAMENTO");
	}
	
	public String getTitleDetailSectionEmendamento() {
		String label = getLabelAttributoCustomCablato("DATI_EMANDAMENTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Emenda"; 
	}
	
	protected void createDetailSectionEmendamento() {
		
		createEmendamentoForm();
		
		detailSectionEmendamento = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionEmendamento(), true, true, false, emendamentoForm1, emendamentoForm2);
	}
	
	public boolean showTipoAttoEmendamentoItem() {
		return showDetailSectionEmendamento() && showAttributoCustomCablato("EMENDAMENTO_SU_TIPO_ATTO");
	}
	
	public String getTitleTipoAttoEmendamentoItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_SU_TIPO_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Tipo atto"; 
	}
	
	public boolean isRequiredTipoAttoEmendamentoItem() {
		return showTipoAttoEmendamentoItem() && getFlgObbligatorioAttributoCustomCablato("EMENDAMENTO_SU_TIPO_ATTO");
	}	
	
	public String getAltriParamLoadComboTipoAttoEmendamentoItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("EMENDAMENTO_SU_TIPO_ATTO");
	}
	
	public boolean showSiglaAttoEmendamentoItem() {
		return showDetailSectionEmendamento() && showAttributoCustomCablato("EMENDAMENTO_SU_ATTO_SIGLA_REG");
	}
	
	public String getTitleSiglaAttoEmendamentoItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_SU_ATTO_SIGLA_REG");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Sigla"; 
	}
	
	public boolean isRequiredSiglaAttoEmendamentoItem() {
		return showSiglaAttoEmendamentoItem() && getFlgObbligatorioAttributoCustomCablato("EMENDAMENTO_SU_ATTO_SIGLA_REG");
	}	
	
	public boolean showNumeroAttoEmendamentoItem() {
		return showDetailSectionEmendamento() && showAttributoCustomCablato("EMENDAMENTO_SU_ATTO_NRO");
	}
	
	public String getTitleNumeroAttoEmendamentoItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_SU_ATTO_NRO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "N°"; 
	}
	
	public boolean isRequiredNumeroAttoEmendamentoItem() {
		return showNumeroAttoEmendamentoItem() && getFlgObbligatorioAttributoCustomCablato("EMENDAMENTO_SU_ATTO_NRO");
	}	
	
	public boolean showAnnoAttoEmendamentoItem() {
		return showDetailSectionEmendamento() && showAttributoCustomCablato("EMENDAMENTO_SU_ATTO_ANNO");
	}
	
	public String getTitleAnnoAttoEmendamentoItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_SU_ATTO_ANNO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Anno"; 
	}
	
	public boolean isRequiredAnnoAttoEmendamentoItem() {
		return showAnnoAttoEmendamentoItem() && getFlgObbligatorioAttributoCustomCablato("EMENDAMENTO_SU_ATTO_ANNO");
	}	
	
	public boolean showIdEmendamentoItem() {
		return showDetailSectionEmendamento() && showAttributoCustomCablato("EMENDAMENTO_ID");
	} 
	
	public String getTitleIdEmendamentoItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_ID");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Id."; 
	}
	
	public boolean isRequiredIdEmendamentoItem() {
		return showIdEmendamentoItem() && getFlgObbligatorioAttributoCustomCablato("EMENDAMENTO_ID");
	}	
	
	public boolean showNumeroEmendamentoItem() {
		return showDetailSectionEmendamento() && showAttributoCustomCablato("EMENDAMENTO_SUB_SU_EM_NRO");
	}
	
	public String getTitleNumeroEmendamentoItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_SUB_SU_EM_NRO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Emendamento N°"; 
	}
	
	public boolean isRequiredNumeroEmendamentoItem() {
		return showNumeroEmendamentoItem() && getFlgObbligatorioAttributoCustomCablato("EMENDAMENTO_SUB_SU_EM_NRO");
	}	
	
	public boolean showFlgEmendamentoSuFileItem() {
		return showDetailSectionEmendamento() && showAttributoCustomCablato("EMENDAMENTO_SU_FILE");
	}
	
	public String getTitleFlgEmendamentoSuFileItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_SU_FILE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "su"; 
	}
	
	public boolean isRequiredFlgEmendamentoSuFileItem() {
		return showFlgEmendamentoSuFileItem() && getFlgObbligatorioAttributoCustomCablato("EMENDAMENTO_SU_FILE");
	}
	
	public String getDefaultValueFlgEmendamentoSuFileItem() {
		return getValoreFissoAttributoCustomCablato("EMENDAMENTO_SU_FILE");
	}
	
	public boolean showNumeroAllegatoEmendamentoItem() {
		return showDetailSectionEmendamento() && isEmendamentoSuFileAllegato() && showAttributoCustomCablato("EMENDAMENTO_SU_ALLEGATO_NRO");
	}
	
	public String getTitleNumeroAllegatoEmendamentoItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_SU_ALLEGATO_NRO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Alleg. N°"; 
	}
	
	public boolean isRequiredNumeroAllegatoEmendamentoItem() {
		return showNumeroAllegatoEmendamentoItem() && getFlgObbligatorioAttributoCustomCablato("EMENDAMENTO_SU_ALLEGATO_NRO");
	}	
	
	public boolean showFlgEmendamentoIntegraleAllegatoItem() {
		return showDetailSectionEmendamento() && isEmendamentoSuFileAllegato() && showAttributoCustomCablato("EMENDAMENTO_INTEGRALE_ALLEGATO");
	}
	
	public String getTitleFlgEmendamentoIntegraleAllegatoItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_INTEGRALE_ALLEGATO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "relativo a intero documento"; 
	}
	
	public boolean showNumeroPaginaEmendamentoItem() {
		return showDetailSectionEmendamento() && (!showFlgEmendamentoIntegraleAllegatoItem() || !isEmendamentoIntegraleAllegato()) && showAttributoCustomCablato("EMENDAMENTO_SU_PAGINA") ;
	}
	
	public String getTitleNumeroPaginaEmendamentoItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_SU_PAGINA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "su pag. N°"; 
	}
	
	public boolean isRequiredNumeroPaginaEmendamentoItem() {
		return showNumeroPaginaEmendamentoItem() && getFlgObbligatorioAttributoCustomCablato("EMENDAMENTO_SU_PAGINA");
	}	
	
	public boolean showNumeroRigaEmendamentoItem() {
		return showDetailSectionEmendamento() && (!showFlgEmendamentoIntegraleAllegatoItem() || !isEmendamentoIntegraleAllegato()) && showAttributoCustomCablato("EMENDAMENTO_SU_RIGA") ;
	}
	
	public String getTitleNumeroRigaEmendamentoItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_SU_RIGA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "su riga N°"; 
	}
	
	public boolean isRequiredNumeroRigaEmendamentoItem() {
		return showNumeroRigaEmendamentoItem() && getFlgObbligatorioAttributoCustomCablato("EMENDAMENTO_SU_RIGA");
	}
	
	public boolean showEffettoEmendamentoItem() {
		return showDetailSectionEmendamento() && showAttributoCustomCablato("EMENDAMENTO_EFFETTO");
	}
	
	public String getTitleEffettoEmendamentoItem() {
		String label = getLabelAttributoCustomCablato("EMENDAMENTO_EFFETTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "effetto"; 
	}
	
	public boolean isRequiredEffettoEmendamentoItem() {
		return showEffettoEmendamentoItem() && getFlgObbligatorioAttributoCustomCablato("EMENDAMENTO_EFFETTO");
	}	
	
	public String[] getValoriPossibiliEffettoEmendamentoItem() {
		String[] valoriPossibili = getValoriPossibiliAttributoCustomCablato("EMENDAMENTO_EFFETTO");
		if(valoriPossibili != null && valoriPossibili.length > 0) {
			return valoriPossibili;			
		} else {
			return new String[] {"aggiunta", "eliminazione", "sostituzione"};
		}
	} 
	
	protected void createEmendamentoForm() {
		
		emendamentoForm1 = new DynamicForm();
		emendamentoForm1.setValuesManager(vm);
		emendamentoForm1.setWidth100();
		emendamentoForm1.setPadding(5);
		emendamentoForm1.setWrapItemTitles(false);
		emendamentoForm1.setNumCols(20);
		emendamentoForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		emendamentoForm1.setTabSet(tabSet);
		emendamentoForm1.setTabID(_TAB_DATI_SCHEDA_ID);
		emendamentoForm1.setHeight(1);
		
		GWTRestDataSource tipoAttoEmendamentoDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		tipoAttoEmendamentoDS.addParam("altriParamLoadCombo", getAltriParamLoadComboTipoAttoEmendamentoItem());
		 		
		tipoAttoEmendamentoItem = new SelectItem("tipoAttoEmendamento", getTitleTipoAttoEmendamentoItem());
//		tipoAttoEmendamentoItem.setTitleOrientation(TitleOrientation.TOP);		
		tipoAttoEmendamentoItem.setWidth(200);
		tipoAttoEmendamentoItem.setStartRow(true);
		tipoAttoEmendamentoItem.setValueField("key");
		tipoAttoEmendamentoItem.setDisplayField("value");
		tipoAttoEmendamentoItem.setOptionDataSource(tipoAttoEmendamentoDS);		
		if(isRequiredTipoAttoEmendamentoItem()) {
			tipoAttoEmendamentoItem.setAttribute("obbligatorio", true);
		} else {
			tipoAttoEmendamentoItem.setAllowEmptyValue(true);			
		}
		tipoAttoEmendamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredTipoAttoEmendamentoItem();
			}
		}));
		tipoAttoEmendamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showTipoAttoEmendamentoItem();
			}
		});
		
		siglaAttoEmendamentoItem = new TextItem("siglaAttoEmendamento", getTitleSiglaAttoEmendamentoItem());
		siglaAttoEmendamentoItem.setWidth(100);
		siglaAttoEmendamentoItem.setColSpan(1);
		siglaAttoEmendamentoItem.setLength(10);
		if(isRequiredSiglaAttoEmendamentoItem()) {
			siglaAttoEmendamentoItem.setAttribute("obbligatorio", true);
		}
		siglaAttoEmendamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredSiglaAttoEmendamentoItem();
			}
		}));
		siglaAttoEmendamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showSiglaAttoEmendamentoItem();
			}
		});
		
		numeroAttoEmendamentoItem = new NumericItem("numeroAttoEmendamento", getTitleNumeroAttoEmendamentoItem(), false);
		numeroAttoEmendamentoItem.setColSpan(1);
		numeroAttoEmendamentoItem.setLength(7);
		if(isRequiredNumeroAttoEmendamentoItem()) {
			numeroAttoEmendamentoItem.setAttribute("obbligatorio", true);
		}
		numeroAttoEmendamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredNumeroAttoEmendamentoItem();
			}
		}));
		numeroAttoEmendamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showNumeroAttoEmendamentoItem();
			}
		});
				
		annoAttoEmendamentoItem = new AnnoItem("annoAttoEmendamento", getTitleAnnoAttoEmendamentoItem());
		annoAttoEmendamentoItem.setColSpan(1);
		if(isRequiredAnnoAttoEmendamentoItem()) {
			annoAttoEmendamentoItem.setAttribute("obbligatorio", true);
		}
		annoAttoEmendamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredAnnoAttoEmendamentoItem();
			}
		}));
		annoAttoEmendamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showAnnoAttoEmendamentoItem();
			}
		});
		
		idEmendamentoItem = new NumericItem("idEmendamento", getTitleIdEmendamentoItem(), false);
		idEmendamentoItem.setWidth(100);
		idEmendamentoItem.setColSpan(1);
		if(isRequiredIdEmendamentoItem()) {
			idEmendamentoItem.setAttribute("obbligatorio", true);
		}
		idEmendamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredIdEmendamentoItem();
			}
		}));
		idEmendamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showIdEmendamentoItem();
			}
		});
		
		numeroEmendamentoItem = new NumericItem("numeroEmendamento", getTitleNumeroEmendamentoItem(), false);
		numeroEmendamentoItem.setWidth(50);
		numeroEmendamentoItem.setColSpan(1);
		numeroEmendamentoItem.setLength(3);
		if(isRequiredNumeroEmendamentoItem()) {
			numeroEmendamentoItem.setAttribute("obbligatorio", true);
		}
		numeroEmendamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredNumeroEmendamentoItem();
			}
		}));
		numeroEmendamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showNumeroEmendamentoItem();
			}
		});
		
		emendamentoForm1.setFields(tipoAttoEmendamentoItem, siglaAttoEmendamentoItem, numeroAttoEmendamentoItem, annoAttoEmendamentoItem, idEmendamentoItem, numeroEmendamentoItem);	
		
		emendamentoForm2 = new DynamicForm();
		emendamentoForm2.setValuesManager(vm);
		emendamentoForm2.setWidth100();
		emendamentoForm2.setPadding(5);
		emendamentoForm2.setWrapItemTitles(false);
		emendamentoForm2.setNumCols(20);
		emendamentoForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		emendamentoForm2.setTabSet(tabSet);
		emendamentoForm2.setTabID(_TAB_DATI_SCHEDA_ID);
		emendamentoForm2.setHeight(1);		
		
		flgEmendamentoSuFileItem = new RadioGroupItem("flgEmendamentoSuFile", getTitleFlgEmendamentoSuFileItem());
		Map<String, String> flgEmendamentoSuFileValueMap = new HashMap<String, String>();
		flgEmendamentoSuFileValueMap.put(_FLG_EMENDAMENTO_SU_FILE_D, "testo");
		flgEmendamentoSuFileValueMap.put(_FLG_EMENDAMENTO_SU_FILE_A, "allegato");
		flgEmendamentoSuFileItem.setValueMap(flgEmendamentoSuFileValueMap);
		flgEmendamentoSuFileItem.setDefaultValue(getDefaultValueFlgEmendamentoSuFileItem());
		flgEmendamentoSuFileItem.setVertical(false);
		flgEmendamentoSuFileItem.setWrap(false);
		flgEmendamentoSuFileItem.setShowDisabled(false);
		if(isRequiredFlgEmendamentoSuFileItem()) {
			flgEmendamentoSuFileItem.setAttribute("obbligatorio", true);
		}
		flgEmendamentoSuFileItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgEmendamentoSuFileItem();
			}
		}));
		flgEmendamentoSuFileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgEmendamentoSuFileItem();
			}
		});			
		flgEmendamentoSuFileItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				emendamentoForm2.markForRedraw();
			}
		});
		
		numeroAllegatoEmendamentoItem = new NumericItem("numeroAllegatoEmendamento", getTitleNumeroAllegatoEmendamentoItem(), false);
		numeroAllegatoEmendamentoItem.setWidth(50);
		numeroAllegatoEmendamentoItem.setColSpan(1);
		numeroAllegatoEmendamentoItem.setLength(3);
		if(isRequiredNumeroAllegatoEmendamentoItem()) {
			numeroAllegatoEmendamentoItem.setAttribute("obbligatorio", true);
		}
		numeroAllegatoEmendamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredNumeroAllegatoEmendamentoItem();
			}
		}));
		numeroAllegatoEmendamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showNumeroAllegatoEmendamentoItem();
			}
		});
		
		flgEmendamentoIntegraleAllegatoItem = new CheckboxItem("flgEmendamentoIntegraleAllegato", getTitleFlgEmendamentoIntegraleAllegatoItem());
		flgEmendamentoIntegraleAllegatoItem.setDefaultValue(false);
		flgEmendamentoIntegraleAllegatoItem.setColSpan(1);
		flgEmendamentoIntegraleAllegatoItem.setWidth("*");
		flgEmendamentoIntegraleAllegatoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {		
				emendamentoForm2.markForRedraw();
			}
		});
		flgEmendamentoIntegraleAllegatoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgEmendamentoIntegraleAllegatoItem();
			}
		});
		
		numeroPaginaEmendamentoItem = new NumericItem("numeroPaginaEmendamento", getTitleNumeroPaginaEmendamentoItem(), false);
		numeroPaginaEmendamentoItem.setWidth(50);
		numeroPaginaEmendamentoItem.setColSpan(1);
		numeroPaginaEmendamentoItem.setLength(3);
		if(isRequiredNumeroPaginaEmendamentoItem()) {
			numeroPaginaEmendamentoItem.setAttribute("obbligatorio", true);
		}
		numeroPaginaEmendamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredNumeroPaginaEmendamentoItem();
			}
		}));
		numeroPaginaEmendamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showNumeroPaginaEmendamentoItem();
			}
		});
		
		numeroRigaEmendamentoItem = new NumericItem("numeroRigaEmendamento", getTitleNumeroRigaEmendamentoItem(), false);
		numeroRigaEmendamentoItem.setWidth(50);
		numeroRigaEmendamentoItem.setColSpan(1);
		numeroRigaEmendamentoItem.setLength(3);
		if(isRequiredNumeroRigaEmendamentoItem()) {
			numeroRigaEmendamentoItem.setAttribute("obbligatorio", true);
		}
		numeroRigaEmendamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredNumeroRigaEmendamentoItem();
			}
		}));
		numeroRigaEmendamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showNumeroRigaEmendamentoItem();
			}
		});
		
		effettoEmendamentoItem = new SelectItem("effettoEmendamento", getTitleEffettoEmendamentoItem());
		effettoEmendamentoItem.setWidth(200);
		effettoEmendamentoItem.setValueMap(getValoriPossibiliEffettoEmendamentoItem());					
		if(isRequiredEffettoEmendamentoItem()) {
			effettoEmendamentoItem.setAttribute("obbligatorio", true);
		} else {
			effettoEmendamentoItem.setAllowEmptyValue(true);			
		}
		effettoEmendamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredEffettoEmendamentoItem();
			}
		}));
		effettoEmendamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showEffettoEmendamentoItem();
			}
		});
		
		emendamentoForm2.setFields(flgEmendamentoSuFileItem, numeroAllegatoEmendamentoItem, flgEmendamentoIntegraleAllegatoItem, numeroPaginaEmendamentoItem, numeroRigaEmendamentoItem, effettoEmendamentoItem);			
	}
	
	/***************************** 
	 * DATI SCHEDA - DESTINATARI *
	 *****************************/
	
	public boolean showFlgAttivaDestinatariItem() {
		return showAttributoCustomCablato("ATTIVA_SEZIONE_DESTINATARI");
	}
	
	public String getTitleFlgAttivaDestinatariItem() {
		String label = getLabelAttributoCustomCablato("ATTIVA_SEZIONE_DESTINATARI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "attiva sezione destinatari"; 
	}
	
	public boolean getDefaultValueAsBooleanFlgAttivaDestinatariItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("ATTIVA_SEZIONE_DESTINATARI");
	}
	
	protected void createAttivaDestinatariForm() {
		
		attivaDestinatariForm = new DynamicForm();
		attivaDestinatariForm.setValuesManager(vm);
		attivaDestinatariForm.setWidth100();
		attivaDestinatariForm.setPadding(5);
		attivaDestinatariForm.setWrapItemTitles(false);
		attivaDestinatariForm.setNumCols(20);
		attivaDestinatariForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		attivaDestinatariForm.setTabSet(tabSet);
		attivaDestinatariForm.setTabID(_TAB_DATI_SCHEDA_ID);
		attivaDestinatariForm.setHeight(1);
		
		flgAttivaDestinatariItem = new CheckboxItem("flgAttivaDestinatari", getTitleFlgAttivaDestinatariItem());
		flgAttivaDestinatariItem.setDefaultValue(getDefaultValueAsBooleanFlgAttivaDestinatariItem());
		flgAttivaDestinatariItem.setColSpan(1);
		flgAttivaDestinatariItem.setWidth("*");
		flgAttivaDestinatariItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {						
				redrawTabForms(_TAB_DATI_SCHEDA_ID);
				showHideSections();
			}
		});
		flgAttivaDestinatariItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgAttivaDestinatariItem();
			}
		});		
			
		attivaDestinatariForm.setFields(
			flgAttivaDestinatariItem
		);			
	}
	
	public boolean showDetailSectionDestinatariAtto() {
		return showDestinatariAttoItem();
	}
	
	public String getTitleDetailSectionDestinatariAtto() {
		return getTitleDestinatariAttoItem();
	}
	
	public boolean isRequiredDetailSectionDestinatariAtto() {
		return isRequiredDestinatariAttoItem();
	}		
	
	protected void createDetailSectionDestinatariAtto() {
		
		createDestinatariAttoForm();
		
		detailSectionDestinatariAtto = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionDestinatariAtto(), true, true, isRequiredDetailSectionDestinatariAtto(), destinatariAttoForm);
	}
	
	public boolean showDestinatariAttoItem() {
		return showAttributoCustomCablato("DESTINATARI_ATTO") && (!showFlgAttivaDestinatariItem() || getValueAsBoolean("flgAttivaDestinatari"));
	}
	
	public String getTitleDestinatariAttoItem() {
		String label = getLabelAttributoCustomCablato("DESTINATARI_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Destinatari"; 
	}
	
	public boolean isRequiredDestinatariAttoItem() {
		return showDestinatariAttoItem() && getFlgObbligatorioAttributoCustomCablato("DESTINATARI_ATTO");
	}
	
	protected void createDestinatariAttoForm() {
		
		destinatariAttoForm = new DynamicForm();
		destinatariAttoForm.setValuesManager(vm);
		destinatariAttoForm.setWidth100();
		destinatariAttoForm.setPadding(5);
		destinatariAttoForm.setWrapItemTitles(false);
		destinatariAttoForm.setNumCols(20);
		destinatariAttoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		destinatariAttoForm.setTabSet(tabSet);
		destinatariAttoForm.setTabID(_TAB_DATI_SCHEDA_ID);
		destinatariAttoForm.setHeight(1);
		
		listaDestinatariAttoItem = new DestinatariAttoItem() {
			
			@Override
			public boolean skipValidation() {
				if(showDestinatariAttoItem()) {
					return super.skipValidation(); //TODO Verificare se quando chiamo super.skipValidation() mi torna true quando sono su un altro tab
				}
				return true;
			}
			
			@Override
			public String getIdDocTypeAtto() {
				return tipoDocumento;
			}
			
			@Override
			public boolean showPrefisso() {
				return showAttributoCustomCablato("DESTINATARI_ATTO_PREFISSO");
			}
			
			@Override
			public String getTitlePrefisso() {
				String label = getLabelAttributoCustomCablato("DESTINATARI_ATTO_PREFISSO");
				if(label != null && !"".equals(label)) {
					return label;
				}
				return "Prefisso";
			}
			
			@Override
			public boolean isRequiredPrefisso() {
				return getFlgObbligatorioAttributoCustomCablato("DESTINATARI_ATTO_PREFISSO");
			}
			
			@Override
			public boolean isEditablePrefisso() {
				return getFlgEditabileAttributoCustomCablato("DESTINATARI_ATTO_PREFISSO");
			}
			
			@Override
			public String getTitleDenominazione() {
				String label = getLabelAttributoCustomCablato("DESTINATARI_ATTO_NOME");
				if(label != null && !"".equals(label)) {
					return label;
				}
				return "Nome";
			}
			
			@Override
			public boolean isRequiredDenominazione() {
				return getFlgObbligatorioAttributoCustomCablato("DESTINATARI_ATTO_NOME");
			}
			
			@Override
			public boolean isEditableDenominazione() {
				return getFlgEditabileAttributoCustomCablato("DESTINATARI_ATTO_NOME");
			}
			
			@Override
			public boolean showIndirizzo() {
				return showAttributoCustomCablato("DESTINATARI_ATTO_INDIRIZZO");
			}
			
			@Override
			public String getTitleIndirizzo() {
				String label = getLabelAttributoCustomCablato("DESTINATARI_ATTO_INDIRIZZO");
				if(label != null && !"".equals(label)) {
					return label;
				}
				return "Sede/indirizzo";
			}
			
			@Override
			public boolean isRequiredIndirizzo() {
				return getFlgObbligatorioAttributoCustomCablato("DESTINATARI_ATTO_INDIRIZZO");
			}
			
			@Override
			public boolean isEditableIndirizzo() {
				return getFlgEditabileAttributoCustomCablato("DESTINATARI_ATTO_INDIRIZZO");
			}
			
			@Override
			public boolean showCorteseAttenzione() {
				return showAttributoCustomCablato("DESTINATARI_ATTO_CA");
			}
			
			@Override
			public String getTitleCorteseAttenzione() {
				String label = getLabelAttributoCustomCablato("DESTINATARI_ATTO_CA");
				if(label != null && !"".equals(label)) {
					return label;
				}
				return "C.A.";
			}
			
			@Override
			public boolean isRequiredCorteseAttenzione() {
				return getFlgObbligatorioAttributoCustomCablato("DESTINATARI_ATTO_CA");
			}
			
			@Override
			public boolean isEditableCorteseAttenzione() {
				return getFlgEditabileAttributoCustomCablato("DESTINATARI_ATTO_CA");
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
		};
		listaDestinatariAttoItem.setName("listaDestinatariAtto");
		listaDestinatariAttoItem.setStartRow(true);
		listaDestinatariAttoItem.setShowTitle(false);
		listaDestinatariAttoItem.setColSpan(20);
		if(isRequiredDestinatariAttoItem()) {
			listaDestinatariAttoItem.setAttribute("obbligatorio", true);
		}
		listaDestinatariAttoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDestinatariAttoItem();
			}
		});
			
		destinatariAttoForm.setFields(
			listaDestinatariAttoItem
		);			
	}
	
	public boolean showDetailSectionDestinatariPCAtto() {
		return showDestinatariPCAttoItem();
	}
	
	public String getTitleDetailSectionDestinatariPCAtto() {
		return getTitleDestinatariPCAttoItem();
	}
	
	public boolean isRequiredDetailSectionDestinatariPCAtto() {
		return isRequiredDestinatariPCAttoItem();
	}		
	
	protected void createDetailSectionDestinatariPCAtto() {
		
		createDestinatariPCAttoForm();
		
		detailSectionDestinatariPCAtto = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionDestinatariPCAtto(), true, true, isRequiredDetailSectionDestinatariPCAtto(), destinatariPCAttoForm);
	}
	
	public boolean showDestinatariPCAttoItem() {
		return showAttributoCustomCablato("DESTINATARI_PC_ATTO") && (!showFlgAttivaDestinatariItem() || getValueAsBoolean("flgAttivaDestinatari"));
	}
	
	public String getTitleDestinatariPCAttoItem() {
		String label = getLabelAttributoCustomCablato("DESTINATARI_PC_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Destinatari P.C."; 
	}
	
	public boolean isRequiredDestinatariPCAttoItem() {
		return showDestinatariPCAttoItem() && getFlgObbligatorioAttributoCustomCablato("DESTINATARI_PC_ATTO");
	}
	
	protected void createDestinatariPCAttoForm() {
		
		destinatariPCAttoForm = new DynamicForm();
		destinatariPCAttoForm.setValuesManager(vm);
		destinatariPCAttoForm.setWidth100();
		destinatariPCAttoForm.setPadding(5);
		destinatariPCAttoForm.setWrapItemTitles(false);
		destinatariPCAttoForm.setNumCols(20);
		destinatariPCAttoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		destinatariPCAttoForm.setTabSet(tabSet);
		destinatariPCAttoForm.setTabID(_TAB_DATI_SCHEDA_ID);
		destinatariPCAttoForm.setHeight(1);
		
		listaDestinatariPCAttoItem = new DestinatariAttoItem() {
			
			@Override
			public boolean skipValidation() {
				if(showDestinatariPCAttoItem()) {
					return super.skipValidation(); //TODO Verificare se quando chiamo super.skipValidation() mi torna true quando sono su un altro tab
				}
				return true;
			}
			
			@Override
			public String getIdDocTypeAtto() {
				return tipoDocumento;
			}
			
			@Override
			public boolean showPrefisso() {
				return showAttributoCustomCablato("DESTINATARI_PC_ATTO_PREFISSO");
			}
			
			@Override
			public String getTitlePrefisso() {
				String label = getLabelAttributoCustomCablato("DESTINATARI_PC_ATTO_PREFISSO");
				if(label != null && !"".equals(label)) {
					return label;
				}
				return "Prefisso";
			}
			
			@Override
			public boolean isRequiredPrefisso() {
				return getFlgObbligatorioAttributoCustomCablato("DESTINATARI_PC_ATTO_PREFISSO");
			}
			
			@Override
			public boolean isEditablePrefisso() {
				return getFlgEditabileAttributoCustomCablato("DESTINATARI_PC_ATTO_PREFISSO");
			}
			
			@Override
			public String getTitleDenominazione() {
				String label = getLabelAttributoCustomCablato("DESTINATARI_PC_ATTO_NOME");
				if(label != null && !"".equals(label)) {
					return label;
				}
				return "Nome";
			}
			
			@Override
			public boolean isRequiredDenominazione() {
				return getFlgObbligatorioAttributoCustomCablato("DESTINATARI_PC_ATTO_NOME");
			}
			
			@Override
			public boolean isEditableDenominazione() {
				return getFlgEditabileAttributoCustomCablato("DESTINATARI_PC_ATTO_NOME");
			}
			
			@Override
			public boolean showIndirizzo() {
				return showAttributoCustomCablato("DESTINATARI_PC_ATTO_INDIRIZZO");
			}
			
			@Override
			public String getTitleIndirizzo() {
				String label = getLabelAttributoCustomCablato("DESTINATARI_PC_ATTO_INDIRIZZO");
				if(label != null && !"".equals(label)) {
					return label;
				}
				return "Sede/indirizzo";
			}
			
			@Override
			public boolean isRequiredIndirizzo() {
				return getFlgObbligatorioAttributoCustomCablato("DESTINATARI_PC_ATTO_INDIRIZZO");
			}
			
			@Override
			public boolean isEditableIndirizzo() {
				return getFlgEditabileAttributoCustomCablato("DESTINATARI_PC_ATTO_INDIRIZZO");
			}
			
			@Override
			public boolean showCorteseAttenzione() {
				return showAttributoCustomCablato("DESTINATARI_PC_ATTO_CA");
			}
			
			@Override
			public String getTitleCorteseAttenzione() {
				String label = getLabelAttributoCustomCablato("DESTINATARI_PC_ATTO_CA");
				if(label != null && !"".equals(label)) {
					return label;
				}
				return "C.A.";
			}
			
			@Override
			public boolean isRequiredCorteseAttenzione() {
				return getFlgObbligatorioAttributoCustomCablato("DESTINATARI_PC_ATTO_CA");
			}
			
			@Override
			public boolean isEditableCorteseAttenzione() {
				return getFlgEditabileAttributoCustomCablato("DESTINATARI_PC_ATTO_CA");
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
		};
		listaDestinatariPCAttoItem.setName("listaDestinatariPCAtto");
		listaDestinatariPCAttoItem.setStartRow(true);
		listaDestinatariPCAttoItem.setShowTitle(false);
		listaDestinatariPCAttoItem.setColSpan(20);
		if(isRequiredDestinatariPCAttoItem()) {
			listaDestinatariPCAttoItem.setAttribute("obbligatorio", true);
		}
		listaDestinatariPCAttoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDestinatariPCAttoItem();
			}
		});
			
		destinatariPCAttoForm.setFields(
			listaDestinatariPCAttoItem
		);			
	}
	
	/******************************* 
	 * DATI SCHEDA - TIPO PROPOSTA *
	 *******************************/
	
	public boolean showDetailSectionTipoProposta() {
		return showIniziativaPropostaItem() ||
			   showFlgAttoMeroIndirizzoItem() ||
			   showFlgModificaRegolamentoItem() ||
			   showFlgModificaStatutoItem() ||
			   showFlgNominaItem() ||
			   showFlgRatificaDeliberaUrgenzaItem() ||
			   showFlgAttoUrgenteItem();
	}
	
	public String getTitleDetailSectionTipoProposta() {
		String label = getLabelAttributoCustomCablato("DETT_TIPO_PROPOSTA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Tipo proposta"; 
	}
	
	protected void createDetailSectionTipoProposta() {
		
		createTipoPropostaForm();
		
		detailSectionTipoProposta = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionTipoProposta(), true, true, false, tipoPropostaForm);
	}
	
	public boolean showIniziativaPropostaItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_INIZIATIVA_PROP_ATTO");
	}
	
	public String getTitleIniziativaPropostaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_INIZIATIVA_PROP_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Iniziativa proposta"; 
	}
	
	public boolean isRequiredIniziativaPropostaItem() {
		return showIniziativaPropostaItem() && getFlgObbligatorioAttributoCustomCablato("TASK_RESULT_2_INIZIATIVA_PROP_ATTO");
	}	
	
	public String[] getValoriPossibiliIniziativaPropostaItem() {
		String[] valoriPossibili = getValoriPossibiliAttributoCustomCablato("TASK_RESULT_2_INIZIATIVA_PROP_ATTO");
		if(valoriPossibili != null && valoriPossibili.length > 0) {
			return valoriPossibili;			
		} else {
			return new String[] {"popolare", "circoscrizione"};
		}		
	}
	
	public boolean showFlgAttoMeroIndirizzoItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_ATTO_MERO_INDIRIZZO");
	}
	
	public String getTitleFlgAttoMeroIndirizzoItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_ATTO_MERO_INDIRIZZO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "atto di mero indirizzo"; 
	}
	
	public boolean showFlgModificaRegolamentoItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_MODIFICA_REGOLAMENTO");
	}
	
	public String getTitleFlgModificaRegolamentoItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_MODIFICA_REGOLAMENTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "modifica regolamento"; 
	}
	
	public boolean showFlgModificaStatutoItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_MODIFICA_STATUTO");
	}
	
	public String getTitleFlgModificaStatutoItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_MODIFICA_STATUTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "modifica statuto"; 
	}
	
	public boolean showFlgNominaItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_DEL_NOMINA");
	}
	
	public String getTitleFlgNominaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DEL_NOMINA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "nomina"; 
	}
	
	public boolean showFlgRatificaDeliberaUrgenzaItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_RATIFICA_DEL_URGENZA");
	}
	
	public String getTitleFlgRatificaDeliberaUrgenzaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_RATIFICA_DEL_URGENZA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "ratifica delibera d'urgenza"; 
	}
	
	
	public boolean showFlgAttoUrgenteItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_ATTO_URGENTE");
	}
	
	public String getTitleFlgAttoUrgenteItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_ATTO_URGENTE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "atto urgente"; 
	}
	
	protected void createTipoPropostaForm() {
		
		tipoPropostaForm = new DynamicForm();
		tipoPropostaForm.setValuesManager(vm);
		tipoPropostaForm.setWidth100();
		tipoPropostaForm.setPadding(5);
		tipoPropostaForm.setWrapItemTitles(false);
		tipoPropostaForm.setNumCols(20);
		tipoPropostaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		tipoPropostaForm.setTabSet(tabSet);
		tipoPropostaForm.setTabID(_TAB_DATI_SCHEDA_ID);
		tipoPropostaForm.setHeight(1);
		
		iniziativaPropostaItem = new SelectItem("iniziativaProposta", getTitleIniziativaPropostaItem());
		iniziativaPropostaItem.setWidth(500);
		iniziativaPropostaItem.setColSpan(20);
		iniziativaPropostaItem.setEndRow(true);
		iniziativaPropostaItem.setValueMap(getValoriPossibiliIniziativaPropostaItem());	
		iniziativaPropostaItem.setClearable(true);
		if(isRequiredIniziativaPropostaItem()) {
			iniziativaPropostaItem.setAttribute("obbligatorio", true);
		}
		iniziativaPropostaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredIniziativaPropostaItem();
			}
		}));
		iniziativaPropostaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showIniziativaPropostaItem();
			}
		});
		iniziativaPropostaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();
				showHideSections();
			}
		});
		
		SpacerItem spacerCheckboxTipoPropostaItem = new SpacerItem();
		spacerCheckboxTipoPropostaItem.setColSpan(1);
		spacerCheckboxTipoPropostaItem.setStartRow(true);
		spacerCheckboxTipoPropostaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return showFlgAttoMeroIndirizzoItem() ||
					   showFlgModificaRegolamentoItem() ||
					   showFlgModificaStatutoItem() ||
					   showFlgNominaItem() ||
					   showFlgRatificaDeliberaUrgenzaItem() ||
					   showFlgAttoUrgenteItem();
			}
		});
		
		flgAttoMeroIndirizzoItem = new CheckboxItem("flgAttoMeroIndirizzo", getTitleFlgAttoMeroIndirizzoItem());
		flgAttoMeroIndirizzoItem.setDefaultValue(false);
		flgAttoMeroIndirizzoItem.setColSpan(1);
		flgAttoMeroIndirizzoItem.setWidth("*");
		flgAttoMeroIndirizzoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {						
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();
				showHideSections();
				afterChangedFlgAttoMeroIndirizzo();
			}
		});
		flgAttoMeroIndirizzoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgAttoMeroIndirizzoItem();
			}
		});		
		
		flgModificaRegolamentoItem = new CheckboxItem("flgModificaRegolamento", getTitleFlgModificaRegolamentoItem());
		flgModificaRegolamentoItem.setDefaultValue(false);
		flgModificaRegolamentoItem.setColSpan(1);
		flgModificaRegolamentoItem.setWidth("*");
		flgModificaRegolamentoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {						
//				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
//				enableDisableTabs();
//				showHideSections();
			}
		});
		flgModificaRegolamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgModificaRegolamentoItem();
			}
		});
		
		flgModificaStatutoItem = new CheckboxItem("flgModificaStatuto", getTitleFlgModificaStatutoItem());
		flgModificaStatutoItem.setDefaultValue(false);
		flgModificaStatutoItem.setColSpan(1);
		flgModificaStatutoItem.setWidth("*");
		flgModificaStatutoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {						
//				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
//				enableDisableTabs();
//				showHideSections();
			}
		});
		flgModificaStatutoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgModificaStatutoItem();
			}
		});
		
		flgNominaItem = new CheckboxItem("flgNomina", getTitleFlgNominaItem());
		flgNominaItem.setDefaultValue(false);
		flgNominaItem.setColSpan(1);
		flgNominaItem.setWidth("*");
		flgNominaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {						
//				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
//				enableDisableTabs();
//				showHideSections();
			}
		});
		flgNominaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgNominaItem();
			}
		});
		
		flgRatificaDeliberaUrgenzaItem = new CheckboxItem("flgRatificaDeliberaUrgenza", getTitleFlgRatificaDeliberaUrgenzaItem());
		flgRatificaDeliberaUrgenzaItem.setDefaultValue(false);
		flgRatificaDeliberaUrgenzaItem.setColSpan(1);
		flgRatificaDeliberaUrgenzaItem.setWidth("*");
		flgRatificaDeliberaUrgenzaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {						
//				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
//				enableDisableTabs();
//				showHideSections();
			}
		});
		flgRatificaDeliberaUrgenzaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgRatificaDeliberaUrgenzaItem();
			}
		});
		
		flgAttoUrgenteItem = new CheckboxItem("flgAttoUrgente", getTitleFlgAttoUrgenteItem());
		flgAttoUrgenteItem.setDefaultValue(false);
		flgAttoUrgenteItem.setColSpan(1);
		flgAttoUrgenteItem.setWidth("*");
		flgAttoUrgenteItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {						
//				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
//				enableDisableTabs();
//				showHideSections();
			}
		});
		flgAttoUrgenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgAttoUrgenteItem();
			}
		});
		
		tipoPropostaForm.setFields(
			iniziativaPropostaItem, spacerCheckboxTipoPropostaItem, flgAttoMeroIndirizzoItem, flgModificaRegolamentoItem, flgModificaStatutoItem, flgNominaItem, flgRatificaDeliberaUrgenzaItem, flgAttoUrgenteItem
		);			
	}
	
	/**************************************************** 
	 * DATI SCHEDA - CIRCOSCRIZIONI PROPONENTI DELIBERA *
	 ****************************************************/
	
	public boolean showDetailSectionCircoscrizioni() {
		return showCircoscrizioniItem();
	}
	
	public String getTitleDetailSectionCircoscrizioni() {
		return getTitleCircoscrizioniItem();
	}
	
	public boolean isRequiredDetailSectionCircoscrizioni() {
		return isRequiredCircoscrizioniItem();
	}		
	
	protected void createDetailSectionCircoscrizioni() {
		
		createCircoscrizioniForm();
		
		detailSectionCircoscrizioni = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionCircoscrizioni(), true, true, isRequiredDetailSectionCircoscrizioni(), circoscrizioniForm);
	}
	
	public boolean showCircoscrizioniItem() {
		return showIniziativaPropostaItem() && "CIRCOSCRIZIONE".equalsIgnoreCase(getValueAsString("iniziativaProposta")) && showAttributoCustomCablato("CIRCOSCRIZIONE_PROPONENTE");
	}
	
	public String getTitleCircoscrizioniItem() {
		String label = getLabelAttributoCustomCablato("CIRCOSCRIZIONE_PROPONENTE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Circoscrizioni proponenti delibera";
	}
	
	public boolean isRequiredCircoscrizioniItem() {
		return showCircoscrizioniItem() && getFlgObbligatorioAttributoCustomCablato("CIRCOSCRIZIONE_PROPONENTE");
	}	
	
	public String getAltriParamLoadComboCircoscrizioniItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("CIRCOSCRIZIONE_PROPONENTE");
	}
	
	protected void createCircoscrizioniForm() {
		
		circoscrizioniForm = new DynamicForm();
		circoscrizioniForm.setValuesManager(vm);
		circoscrizioniForm.setWidth100();
		circoscrizioniForm.setPadding(5);
		circoscrizioniForm.setWrapItemTitles(false);
		circoscrizioniForm.setNumCols(20);
		circoscrizioniForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		circoscrizioniForm.setTabSet(tabSet);
		circoscrizioniForm.setTabID(_TAB_DATI_SCHEDA_ID);
		circoscrizioniForm.setHeight(1);
		
		listaCircoscrizioniItem = new ValoriDizionarioItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboCircoscrizioniItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showCircoscrizioniItem()) {
					return super.skipValidation(); //TODO Verificare se quando chiamo super.skipValidation() mi torna true quando sono su un altro tab
				}
				return true;
			}
		};
		listaCircoscrizioniItem.setName("listaCircoscrizioni");
		listaCircoscrizioniItem.setStartRow(true);
		listaCircoscrizioniItem.setShowTitle(false);
		listaCircoscrizioniItem.setColSpan(20);
//		if(showAttributoCustomCablato("CIRCOSCRIZIONE_PROPONENTE") && getFlgObbligatorioAttributoCustomCablato("CIRCOSCRIZIONE_PROPONENTE")) {
//			listaCircoscrizioniItem.setAttribute("obbligatorio", true);
//		}
		listaCircoscrizioniItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredCircoscrizioniItem()) {
					listaCircoscrizioniItem.setAttribute("obbligatorio", true);
				} else {
					listaCircoscrizioniItem.setAttribute("obbligatorio", false);
				}
				listaCircoscrizioniItem.storeValue(form.getValueAsRecordList(listaCircoscrizioniItem.getName()));				
				return showCircoscrizioniItem();
			}
		});
		
		circoscrizioniForm.setFields(listaCircoscrizioniItem);			
	}
	
	/************************************ 
	 * DATI SCHEDA - TIPO INTERPELLANZA *
	 ************************************/
	
	public boolean showDetailSectionInterpellanza() {
		return showTipoInterpellanzaItem();
	}
	
	public String getTitleDetailSectionInterpellanza() {
		return getTitleTipoInterpellanzaItem();
	}
	
	public boolean isRequiredDetailSectionInterpellanza() {
		return isRequiredTipoInterpellanzaItem();
	}		
	
	protected void createDetailSectionInterpellanza() {
		
		createInterpellanzaForm();
		
		detailSectionInterpellanza = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionInterpellanza(), true, true, isRequiredDetailSectionInterpellanza(), interpellanzaForm);
	}
	
	public boolean showTipoInterpellanzaItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_TIPO_INTERPELLANZA");
	}
	
	public String getTitleTipoInterpellanzaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_TIPO_INTERPELLANZA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Tipo interpellanza"; 
	}
	
	public boolean isRequiredTipoInterpellanzaItem() {
		return showTipoInterpellanzaItem() && getFlgObbligatorioAttributoCustomCablato("TASK_RESULT_2_TIPO_INTERPELLANZA");
	}
	
	public String getAltriParamLoadComboTipoInterpellanzaItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("TASK_RESULT_2_TIPO_INTERPELLANZA");
	}
	
	protected void createInterpellanzaForm() {
		
		interpellanzaForm = new DynamicForm();
		interpellanzaForm.setValuesManager(vm);
		interpellanzaForm.setWidth100();
		interpellanzaForm.setPadding(5);
		interpellanzaForm.setWrapItemTitles(false);
		interpellanzaForm.setNumCols(20);
		interpellanzaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		interpellanzaForm.setTabSet(tabSet);
		interpellanzaForm.setTabID(_TAB_DATI_SCHEDA_ID);
		interpellanzaForm.setHeight(1);
		
		GWTRestDataSource tipoInterpellanzaDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		tipoInterpellanzaDS.addParam("altriParamLoadCombo", getAltriParamLoadComboTipoInterpellanzaItem());
		
		tipoInterpellanzaItem = new SelectItem("tipoInterpellanza", getTitleTipoInterpellanzaItem());
		tipoInterpellanzaItem.setShowTitle(false);
		tipoInterpellanzaItem.setWidth(500);
		tipoInterpellanzaItem.setValueField("key");
		tipoInterpellanzaItem.setDisplayField("value");
		tipoInterpellanzaItem.setOptionDataSource(tipoInterpellanzaDS);
		tipoInterpellanzaItem.setClearable(true);		
		if(isRequiredTipoInterpellanzaItem()) {
			tipoInterpellanzaItem.setAttribute("obbligatorio", true);
		}
		tipoInterpellanzaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredTipoInterpellanzaItem();
			}
		}));
		tipoInterpellanzaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showTipoInterpellanzaItem();
			}
		});
		
		interpellanzaForm.setFields(tipoInterpellanzaItem);			
	}
	
	/**************************************** 
	 * DATI SCHEDA - ORDINANZA DI MOBILITA' *
	 ****************************************/
	
	public boolean showDetailSectionOrdMobilita() {
		return showTipoOrdMobilitaItem() ||
			   showDataInizioVldOrdinanzaItem() ||
			   showDataFineVldOrdinanzaItem() ||
			   showTipoLuogoOrdMobilitaItem() ||
			   showIndirizziOrdMobilitaItem() ||
			   showLuogoOrdMobilitaItem() ||
			   showCircoscrizioniOrdMobilitaItem() ||
			   showDescrizioneOrdMobilitaItem();		
	}
	
	public String getTitleDetailSectionOrdMobilita() {
		return "Dati ordinanza";
	}
	
	protected void createDetailSectionOrdMobilita() {
		
		createOrdMobilitaForm();
		
		detailSectionOrdMobilita = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionOrdMobilita(), true, true, false, ordMobilitaForm1, ordMobilitaForm2, ordMobilitaForm3, ordMobilitaForm4, ordMobilitaForm5);
	}
	
	public boolean showTipoOrdMobilitaItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_TIPO_ORD_MOBILITA");
	}
	
	public String getTitleTipoOrdMobilitaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_TIPO_ORD_MOBILITA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return null;
	}
	
	public boolean isRequiredTipoOrdMobilitaItem() {
		return showTipoOrdMobilitaItem() && getFlgObbligatorioAttributoCustomCablato("TASK_RESULT_2_TIPO_ORD_MOBILITA");
	}
	
	public HashMap<String, String> getValueMapTipoOrdMobilitaItem() {
		return getValueMapAttributoCustomCablato("TASK_RESULT_2_TIPO_ORD_MOBILITA");
	}
	
	public String getDefaultValueTipoOrdMobilitaItem() {
		return getValoreFissoAttributoCustomCablato("TASK_RESULT_2_TIPO_ORD_MOBILITA");
	}
	
	public boolean showDataInizioVldOrdinanzaItem() {
		return showAttributoCustomCablato("INIZIO_VLD_ORDINANZA");
	}
	
	public String getTitleDataInizioVldOrdinanzaItem() {
		String label = getLabelAttributoCustomCablato("INIZIO_VLD_ORDINANZA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "dal";
	}
	
	public boolean isRequiredDataInizioVldOrdinanzaItem() {
		if(showDataInizioVldOrdinanzaItem()) {
//			if(showTipoOrdMobilitaItem() && _TEMPORANEA.equalsIgnoreCase(getValueAsString("tipoOrdMobilita"))) {
//				return true;
//			}
			return getFlgObbligatorioAttributoCustomCablato("INIZIO_VLD_ORDINANZA");
		}
		return false;
	}
	
	public boolean showDataFineVldOrdinanzaItem() {
		return showAttributoCustomCablato("FINE_VLD_ORDINANZA");
	}
	
	public String getTitleDataFineVldOrdinanzaItem() {
		String label = getLabelAttributoCustomCablato("FINE_VLD_ORDINANZA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "al";
	}
	
	public boolean isRequiredDataFineVldOrdinanzaItem() {
		if(showDataFineVldOrdinanzaItem()) {
//			if(showTipoOrdMobilitaItem() && _TEMPORANEA.equalsIgnoreCase(getValueAsString("tipoOrdMobilita"))) {
//				return true;
//			}
			return getFlgObbligatorioAttributoCustomCablato("FINE_VLD_ORDINANZA");
		}
		return false;
	}
	
	public boolean showTipoLuogoOrdMobilitaItem() {
		return showAttributoCustomCablato("TIPO_LUOGO_ORD_MOBILITA");
	}
	
	public String getTitleTipoLuogoOrdMobilitaItem() {
		String label = getLabelAttributoCustomCablato("TIPO_LUOGO_ORD_MOBILITA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return null;
	}
	
	public boolean isRequiredTipoLuogoOrdMobilitaItem() {
		return showTipoLuogoOrdMobilitaItem() && getFlgObbligatorioAttributoCustomCablato("TIPO_LUOGO_ORD_MOBILITA");
	}
	
	public String getDefaultValueTipoLuogoOrdMobilitaItem() {
		return getValoreFissoAttributoCustomCablato("TIPO_LUOGO_ORD_MOBILITA");
	}
	
	public boolean showIndirizziOrdMobilitaItem() {
		return (showTipoLuogoOrdMobilitaItem() && _TIPO_LUOGO_DA_TOPONOMASTICA.equalsIgnoreCase(getValueAsString("tipoLuogoOrdMobilita"))) && showAttributoCustomCablato("ALTRE_UBICAZIONI");
	}

	public String getTitleIndirizziOrdMobilitaItem() {
		String label = getLabelAttributoCustomCablato("ALTRE_UBICAZIONI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Ubicazione/i";
	}
	
	public boolean isRequiredIndirizziOrdMobilitaItem() {
		return showIndirizziOrdMobilitaItem() && getFlgObbligatorioAttributoCustomCablato("ALTRE_UBICAZIONI");
	}	
	
	public String getAltriParamLoadComboIndirizziOrdMobilitaItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ALTRE_UBICAZIONI");
	}
	
	public boolean showLuogoOrdMobilitaItem() {
		boolean isTipoLuogoTestoLibero = showTipoLuogoOrdMobilitaItem() && _TIPO_LUOGO_TESTO_LIBERO.equalsIgnoreCase(getValueAsString("tipoLuogoOrdMobilita"));
		return isTipoLuogoTestoLibero && showAttributoCustomCablato("LUOGO_ORD_MOBILITA");
	}
	
	public String getTitleLuogoOrdMobilitaItem() {
		String label = getLabelAttributoCustomCablato("LUOGO_ORD_MOBILITA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Ubicazione/i";
	}
	
	public boolean isRequiredLuogoOrdMobilitaItem() {
		return showLuogoOrdMobilitaItem() && getFlgObbligatorioAttributoCustomCablato("LUOGO_ORD_MOBILITA");
	}
	
	public int getAltezzaInRigheLuogoOrdMobilitaItem() {
		Integer altezzaInRighe = getAltezzaInRigheAttributoCustomCablato("LUOGO_ORD_MOBILITA");
		return altezzaInRighe != null ? altezzaInRighe.intValue() : 10;
	}
	
	public boolean showCircoscrizioniOrdMobilitaItem() {
		return showAttributoCustomCablato("CIRCOSCRIZIONE_ORD_MOBILITA");
	}
	
	public String getTitleCircoscrizioniOrdMobilitaItem() {
		String label = getLabelAttributoCustomCablato("CIRCOSCRIZIONE_ORD_MOBILITA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Circoscrizioni";
	}
	
	public boolean isRequiredCircoscrizioniOrdMobilitaItem() {
		return showCircoscrizioniOrdMobilitaItem() && getFlgObbligatorioAttributoCustomCablato("CIRCOSCRIZIONE_ORD_MOBILITA");
	}	
		
	public String getAltriParamLoadComboCircoscrizioniOrdMobilitaItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("CIRCOSCRIZIONE_ORD_MOBILITA");
	}
	
	public boolean showDescrizioneOrdMobilitaItem() {
		return showAttributoCustomCablato("DESCRIZIONE_ORD_MOBILITA");
	}
	
	public String getTitleDescrizioneOrdMobilitaItem() {
		String label = getLabelAttributoCustomCablato("DESCRIZIONE_ORD_MOBILITA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Descrizione";
	}
	
	public boolean isRequiredDescrizioneOrdMobilitaItem() {
		return showDescrizioneOrdMobilitaItem() && getFlgObbligatorioAttributoCustomCablato("DESCRIZIONE_ORD_MOBILITA");
	}
	
	public int getAltezzaInRigheDescrizioneOrdMobilitaItem() {
		Integer altezzaInRighe = getAltezzaInRigheAttributoCustomCablato("DESCRIZIONE_ORD_MOBILITA");
		return altezzaInRighe != null ? altezzaInRighe.intValue() : 10;
	}

	protected void createOrdMobilitaForm() {
		
		ordMobilitaForm1 = new DynamicForm();
		ordMobilitaForm1.setValuesManager(vm);
		ordMobilitaForm1.setWidth100();
		ordMobilitaForm1.setPadding(5);
		ordMobilitaForm1.setWrapItemTitles(false);
		ordMobilitaForm1.setNumCols(20);
		ordMobilitaForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ordMobilitaForm1.setTabSet(tabSet);
		ordMobilitaForm1.setTabID(_TAB_DATI_SCHEDA_ID);
		ordMobilitaForm1.setHeight(1);
		
		tipoOrdMobilitaItem = new SelectItem("tipoOrdMobilita");
		tipoOrdMobilitaItem.setWidth(200);
		String titleTipoOrdMobilita = getTitleTipoOrdMobilitaItem();
		if(titleTipoOrdMobilita != null) {
			tipoOrdMobilitaItem.setTitle(titleTipoOrdMobilita);
		} else {			
			tipoOrdMobilitaItem.setShowTitle(false);
		}
		Map<String, String> tipoOrdMobilitaValueMap = getValueMapTipoOrdMobilitaItem();
		if(tipoOrdMobilitaValueMap != null && tipoOrdMobilitaValueMap.keySet().size() > 0) {
			tipoOrdMobilitaItem.setValueMap(tipoOrdMobilitaValueMap);			
		} else {			
			tipoOrdMobilitaItem.setValueMap(_PERMANENTE, _TEMPORANEA);	
		}		
		tipoOrdMobilitaItem.setDefaultValue(getDefaultValueTipoOrdMobilitaItem());		
		if(isRequiredTipoOrdMobilitaItem()) {
			tipoOrdMobilitaItem.setAttribute("obbligatorio", true);
		} else {
			tipoOrdMobilitaItem.setAllowEmptyValue(true);			
		}
		tipoOrdMobilitaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredTipoOrdMobilitaItem();
			}
		}));
		tipoOrdMobilitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showTipoOrdMobilitaItem();
			}
		});
		tipoOrdMobilitaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_SCHEDA_ID);
			}
		});
		
		dataInizioVldOrdinanzaItem = new DateItem("dataInizioVldOrdinanza", getTitleDataInizioVldOrdinanzaItem());
		dataInizioVldOrdinanzaItem.setColSpan(1);
		dataInizioVldOrdinanzaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredDataInizioVldOrdinanzaItem()) {
					dataInizioVldOrdinanzaItem.setAttribute("obbligatorio", true);
					dataInizioVldOrdinanzaItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleDataInizioVldOrdinanzaItem()));
				} else {
					dataInizioVldOrdinanzaItem.setAttribute("obbligatorio", false);
					dataInizioVldOrdinanzaItem.setTitle(getTitleDataInizioVldOrdinanzaItem());
				}
				return showDataInizioVldOrdinanzaItem();
			}
		});
		dataInizioVldOrdinanzaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredDataInizioVldOrdinanzaItem();
			}
		}));
		
		dataFineVldOrdinanzaItem = new DateItem("dataFineVldOrdinanza", getTitleDataFineVldOrdinanzaItem());
		dataFineVldOrdinanzaItem.setColSpan(1);
		dataFineVldOrdinanzaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredDataFineVldOrdinanzaItem()) {
					dataFineVldOrdinanzaItem.setAttribute("obbligatorio", true);
					dataFineVldOrdinanzaItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleDataFineVldOrdinanzaItem()));
				} else {
					dataFineVldOrdinanzaItem.setAttribute("obbligatorio", false);
					dataFineVldOrdinanzaItem.setTitle(getTitleDataFineVldOrdinanzaItem());
				}
				return showDataFineVldOrdinanzaItem();
			}
		});
		dataFineVldOrdinanzaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredDataFineVldOrdinanzaItem();
			}
		}));
		
		ordMobilitaForm1.setFields(tipoOrdMobilitaItem, dataInizioVldOrdinanzaItem, dataFineVldOrdinanzaItem);	
		
		ordMobilitaForm2 = new DynamicForm();
		ordMobilitaForm2.setValuesManager(vm);
		ordMobilitaForm2.setWidth100();
		ordMobilitaForm2.setPadding(5);
		ordMobilitaForm2.setWrapItemTitles(false);
		ordMobilitaForm2.setNumCols(20);
		ordMobilitaForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ordMobilitaForm2.setTabSet(tabSet);
		ordMobilitaForm2.setTabID(_TAB_DATI_SCHEDA_ID);
		ordMobilitaForm2.setHeight(1);
		
		tipoLuogoOrdMobilitaItem = new RadioGroupItem("tipoLuogoOrdMobilita");
		String titleTipoLuogoOrdMobilita = getTitleTipoLuogoOrdMobilitaItem();
		if(titleTipoLuogoOrdMobilita != null) {
			tipoLuogoOrdMobilitaItem.setTitle(titleTipoLuogoOrdMobilita);
		} else {			
			tipoLuogoOrdMobilitaItem.setShowTitle(false);
		}
		tipoLuogoOrdMobilitaItem.setValueMap(_TIPO_LUOGO_DA_TOPONOMASTICA, _TIPO_LUOGO_TESTO_LIBERO);		
		tipoLuogoOrdMobilitaItem.setDefaultValue(getDefaultValueTipoLuogoOrdMobilitaItem());
		tipoLuogoOrdMobilitaItem.setVertical(false);
		tipoLuogoOrdMobilitaItem.setWrap(false);
		tipoLuogoOrdMobilitaItem.setShowDisabled(false);
		if(isRequiredTipoLuogoOrdMobilitaItem()) {
			tipoLuogoOrdMobilitaItem.setAttribute("obbligatorio", true);
		}
		tipoLuogoOrdMobilitaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredTipoLuogoOrdMobilitaItem();
			}
		}));
		tipoLuogoOrdMobilitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showTipoLuogoOrdMobilitaItem();
			}
		});			
		tipoLuogoOrdMobilitaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_SCHEDA_ID);	
				luogoOrdMobilitaItem.redraw();
				if(showLuogoOrdMobilitaItem()) {
					createLuogoOrdMobilitaItem();
					ordMobilitaForm3.setFields(luogoOrdMobilitaItem);
				} else {
					ordMobilitaForm3.setFields(new FormItem[0]);
				}
			}
		});
		
		listaIndirizziOrdMobilitaItem = new AltreVieItem() {
			
//			@Override
//			public boolean showFlgFuoriComune() {
//				return false;
//			}
//			
//			@Override
//			public boolean getFlgFuoriComune() {
//				return false;
//			}
			
			@Override
			public boolean isIndirizzoObbligatorioInCanvas() {
//				return isIndirizzoObbligatorioInPropostaAtto();
				return true;
			}			
			
//			@Override
//			public boolean isCivicoObbligatorioInCanvas() {
//				return isCivicoObbligatorioInPropostaAtto();
//			}
						
			@Override
			public boolean skipValidation() {
				if(showIndirizziOrdMobilitaItem()) {
					return super.skipValidation(); //TODO Verificare se quando chiamo super.skipValidation() mi torna true quando sono su un altro tab
				}
				return true;
			}			

			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredDirigentiConcertoItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleIndirizziOrdMobilitaItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleIndirizziOrdMobilitaItem() + "</span>");
				}				
				return lVLayout;
			}
		};
		listaIndirizziOrdMobilitaItem.setName("listaIndirizziOrdMobilita");
		listaIndirizziOrdMobilitaItem.setStartRow(true);
		listaIndirizziOrdMobilitaItem.setShowTitle(false);
		listaIndirizziOrdMobilitaItem.setColSpan(20);		
//		if(showAttributoCustomCablato("ALTRE_UBICAZIONI") && getFlgObbligatorioAttributoCustomCablato("ALTRE_UBICAZIONI")) {
//			listaIndirizziOrdMobilitaItem.setAttribute("obbligatorio", true);
//		}
		listaIndirizziOrdMobilitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredIndirizziOrdMobilitaItem()) {
					listaIndirizziOrdMobilitaItem.setAttribute("obbligatorio", true);				
				} else {
					listaIndirizziOrdMobilitaItem.setAttribute("obbligatorio", false);
				}	
				listaIndirizziOrdMobilitaItem.storeValue(form.getValueAsRecordList(listaIndirizziOrdMobilitaItem.getName()));				
				return showIndirizziOrdMobilitaItem();
			}
		});
		
		ordMobilitaForm2.setFields(tipoLuogoOrdMobilitaItem, listaIndirizziOrdMobilitaItem);
		
		ordMobilitaForm3 = new DynamicForm();
		ordMobilitaForm3.setValuesManager(vm);
		ordMobilitaForm3.setWidth100();
		ordMobilitaForm3.setPadding(5);
		ordMobilitaForm3.setWrapItemTitles(false);
		ordMobilitaForm3.setNumCols(20);
		ordMobilitaForm3.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ordMobilitaForm3.setTabSet(tabSet);
		ordMobilitaForm3.setTabID(_TAB_DATI_SCHEDA_ID);
		ordMobilitaForm3.setHeight(1);		
			
//		if(showLuogoOrdMobilitaItem()) {
			createLuogoOrdMobilitaItem();
			ordMobilitaForm3.setFields(luogoOrdMobilitaItem);	
//		} else {
//			ordMobilitaForm3.setFields(new FormItem[0]);
//		}
		
		ordMobilitaForm4 = new DynamicForm();
		ordMobilitaForm4.setValuesManager(vm);
		ordMobilitaForm4.setWidth100();
		ordMobilitaForm4.setPadding(5);
		ordMobilitaForm4.setWrapItemTitles(false);
		ordMobilitaForm4.setNumCols(20);
		ordMobilitaForm4.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ordMobilitaForm4.setTabSet(tabSet);
		ordMobilitaForm4.setTabID(_TAB_DATI_SCHEDA_ID);
		ordMobilitaForm4.setHeight(1);
			
		listaCircoscrizioniOrdMobilitaItem = new ValoriDizionarioItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboCircoscrizioniOrdMobilitaItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showCircoscrizioniOrdMobilitaItem()) {
					return super.skipValidation(); //TODO Verificare se quando chiamo super.skipValidation() mi torna true quando sono su un altro tab
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredCircoscrizioniOrdMobilitaItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleCircoscrizioniOrdMobilitaItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleCircoscrizioniOrdMobilitaItem() + "</span>");
				}				
				return lVLayout;
			}
		};
		listaCircoscrizioniOrdMobilitaItem.setName("listaCircoscrizioniOrdMobilita");
		listaCircoscrizioniOrdMobilitaItem.setStartRow(true);
		listaCircoscrizioniOrdMobilitaItem.setShowTitle(false);
		listaCircoscrizioniOrdMobilitaItem.setColSpan(20);
		if(isRequiredCircoscrizioniOrdMobilitaItem()) {
			listaCircoscrizioniOrdMobilitaItem.setAttribute("obbligatorio", true);
		}
		listaCircoscrizioniOrdMobilitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCircoscrizioniOrdMobilitaItem();
			}
		});
		
		ordMobilitaForm4.setFields(listaCircoscrizioniOrdMobilitaItem);		
		
		ordMobilitaForm5 = new DynamicForm();
		ordMobilitaForm5.setValuesManager(vm);
		ordMobilitaForm5.setWidth100();
		ordMobilitaForm5.setPadding(5);
		ordMobilitaForm5.setWrapItemTitles(false);
		ordMobilitaForm5.setNumCols(20);
		ordMobilitaForm5.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ordMobilitaForm5.setTabSet(tabSet);
		ordMobilitaForm5.setTabID(_TAB_DATI_SCHEDA_ID);
		ordMobilitaForm5.setHeight(1);		
			
		descrizioneOrdMobilitaItem = new CKEditorItem("descrizioneOrdMobilita", -1, "STANDARD", getAltezzaInRigheDescrizioneOrdMobilitaItem(), -1, "") {
			
			@Override
			public Boolean validate() {
				if(showDescrizioneOrdMobilitaItem()) {
					return super.validate();				
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredDescrizioneOrdMobilitaItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleDescrizioneOrdMobilitaItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleDescrizioneOrdMobilitaItem() + "</span>");
				}				
				return lVLayout;
			}
		};
		descrizioneOrdMobilitaItem.setShowTitle(false);
		descrizioneOrdMobilitaItem.setColSpan(20);
		descrizioneOrdMobilitaItem.setWidth("100%");
		descrizioneOrdMobilitaItem.setRequired(isRequiredDescrizioneOrdMobilitaItem());
		descrizioneOrdMobilitaItem.setVisible(showDescrizioneOrdMobilitaItem());
		
		ordMobilitaForm5.setFields(descrizioneOrdMobilitaItem);	
	}
	
	public void createLuogoOrdMobilitaItem() {
		luogoOrdMobilitaItem = new CKEditorItem("luogoOrdMobilita", -1, "STANDARD", getAltezzaInRigheLuogoOrdMobilitaItem(), -1, "") {
			
			@Override
			public Boolean validate() {
				if(showLuogoOrdMobilitaItem()) {
					return super.validate();				
				}
				return true;
			}
			
			@Override
			public void redraw() {
				luogoOrdMobilitaItem.setTitle(isRequiredLuogoOrdMobilitaItem() ? FrontendUtil.getRequiredFormItemTitle(getTitleLuogoOrdMobilitaItem()) : getTitleLuogoOrdMobilitaItem());
				luogoOrdMobilitaItem.setRequired(isRequiredLuogoOrdMobilitaItem());
				luogoOrdMobilitaItem.setVisible(showLuogoOrdMobilitaItem());
				super.redraw();
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredLuogoOrdMobilitaItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleLuogoOrdMobilitaItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleLuogoOrdMobilitaItem() + "</span>");
				}				
				return lVLayout;
			}
		};
//		luogoOrdMobilitaItem.setShowTitle(true);
//		luogoOrdMobilitaItem.setTitleOrientation(TitleOrientation.TOP);
		luogoOrdMobilitaItem.setColSpan(20);
		luogoOrdMobilitaItem.setWidth("100%");				
//		luogoOrdMobilitaItem.setTitle(isRequiredLuogoOrdMobilitaItem() ? FrontendUtil.getRequiredFormItemTitle(getTitleLuogoOrdMobilitaItem()) : getTitleLuogoOrdMobilitaItem());
//		luogoOrdMobilitaItem.setRequired(isRequiredLuogoOrdMobilitaItem());
//		luogoOrdMobilitaItem.setVisible(showLuogoOrdMobilitaItem());		
		luogoOrdMobilitaItem.setShowIfCondition(new FormItemIfFunction() {
		
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredLuogoOrdMobilitaItem()) {
					luogoOrdMobilitaItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleLuogoOrdMobilitaItem()));
					luogoOrdMobilitaItem.setRequired(true);
				} else {
					luogoOrdMobilitaItem.setTitle(getTitleLuogoOrdMobilitaItem());
					luogoOrdMobilitaItem.setRequired(false);
				}
				return showLuogoOrdMobilitaItem();
			}
		});
	}
	
	/*********************** 
	 * DATI SCHEDA - RUOLI *
	 ***********************/
	
	public boolean showDetailSectionRuoli() {
		return showUfficioProponenteItem() || 
			   showUfficioProponenteEstesoItem() || 
			   showAdottanteItem() ||
			   showCentroDiCostoItem() ||
			   showDirigentiConcertoItem() ||
			   showDirRespRegTecnicaItem() ||
			   showAltriDirRespRegTecnicaItem() ||
			   showRdPItem() ||
			   showRUPItem() ||
			   showAssessoriItem() ||	
			   showAltriAssessoriItem() ||
			   showConsiglieriItem() ||
			   showAltriConsiglieriItem() ||
			   showDirigentiProponentiItem() ||
			   showAltriDirigentiProponentiItem() ||
			   showCoordinatoriCompCircItem() ||
			   showFlgRichiediVistoDirettoreItem() ||
			   showRespVistiConformitaItem() ||
			   showEstensoriItem() ||
			   showAltriEstensoriItem() ||
			   showIstruttoriItem() ||
			   showAltriIstruttoriItem();		
	}
	
	protected void createDetailSectionRuoli() {
		
		createRuoliForm();
		
		detailSectionRuoli = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionRuoli_title(), true, true, false, ruoliForm);
	}
	
	public boolean isAbilToSelUffPropEsteso() {
		return Layout.isPrivilegioAttivo("APE");
	}
		
	public boolean showUfficioProponenteEstesoItem() {
		return isAvvioPropostaAtto() && showAttributoCustomCablato("ID_UO_PROPONENTE_ESTESA");
	}
	
	public String getTitleUfficioProponenteEstesoItem() {
		String label = getLabelAttributoCustomCablato("ID_UO_PROPONENTE_ESTESA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Struttura proponente";
	}
	
	public boolean showUfficioProponenteItem() {
		return isAvvioPropostaAtto() && showAttributoCustomCablato("ID_UO_PROPONENTE");
	}
	
	public String getTitleUfficioProponenteItem() {
		String label = getLabelAttributoCustomCablato("ID_UO_PROPONENTE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Struttura proponente";
	}
	
//	public String getAltriParamLoadComboUfficioProponenteItem() {
//		return getAltriParametriLoadComboAttributoCustomCablato("ID_UO_PROPONENTE");
//	}
	
	public LinkedHashMap<String, String> getUfficioProponenteValueMap() {
//		LinkedHashMap<String, String> uoCollegateUtenteValueMap = AurigaLayout.getUoCollegateUtenteValueMap();
//		LinkedHashMap<String, String> ufficioProponenteValueMap = new LinkedHashMap<String, String>();
//		for (String key : uoCollegateUtenteValueMap.keySet()) {	
//			String idUoProponente = (key != null && key.startsWith("UO")) ? key.substring(2) : key;
//			ufficioProponenteValueMap.put(idUoProponente, uoCollegateUtenteValueMap.get(key));			
//		}
		return ufficioProponenteValueMap;
	}
	
	public void afterSelezioneUoProponente() {		
		if(listaAdottanteItem != null) {
			listaAdottanteItem.resetAfterChangedParams();
		}
		if(listaDirRespRegTecnicaItem != null) {
			listaDirRespRegTecnicaItem.resetAfterChangedParams();
		}
		if(listaRdPItem != null) {
			listaRdPItem.resetAfterChangedParams();
		}
		if(listaRUPItem != null) {
			listaRUPItem.resetAfterChangedParams();
		}										
		if(listaAssessoriItem != null) {
			listaAssessoriItem.resetAfterChangedParams();
		}
		if(listaAltriAssessoriItem != null) {
			listaAltriAssessoriItem.resetAfterChangedParams();
		}
		if(listaDirigentiProponentiItem != null) {
			listaDirigentiProponentiItem.resetAfterChangedParams();
		}
		if(listaCoordinatoriCompCircItem != null) {
			listaCoordinatoriCompCircItem.resetAfterChangedParams();
		}					
		if(listaEstensoriItem != null) {
			listaEstensoriItem.resetAfterChangedParams();
		}
		if(listaAltriEstensoriItem != null) {
			listaAltriEstensoriItem.resetAfterChangedParams();
		}					
		if(listaIstruttoriItem != null) {
			listaIstruttoriItem.resetAfterChangedParams();
		}
		if(listaAltriIstruttoriItem != null) {
			listaAltriIstruttoriItem.resetAfterChangedParams();
		}					
		if(sezionePubblAmmTraspItem != null) {
			resetSezionePubblAmmTraspAfterChangedParams();
		}
		if(sottoSezionePubblAmmTraspItem != null) {
			resetSottoSezionePubblAmmTraspAfterChangedParams();
		}
		setUfficioDefinizioneSpesaFromUoProponente();
	}
	
	public void afterChangedFlgAttoMeroIndirizzo() {
		if(listaIstruttoriItem != null) {
			listaIstruttoriItem.resetAfterChangedParams();
		}
	}
	
	public boolean showAdottanteItem() {
		return showAttributoCustomCablato("ID_SV_ADOTTANTE");
	}
	
	public boolean isRequiredAdottanteItem() {
		return showAdottanteItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_ADOTTANTE");
	}
	
	public String getTitleAdottanteItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_ADOTTANTE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Dir. adottante";
	}
	
	public String getAltriParamLoadComboAdottanteItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_ADOTTANTE");
	}
	
	public void afterSelezioneScrivaniaAdottante() {	
		if(centroDiCostoItem != null) {
			resetCentroDiCostoAfterChangedAdottante();
		}		
	}
	
	public boolean showCentroDiCostoItem() {
		if(isDeterminaSenzaSpesa()) {
			return false;
		}
		return showAttributoCustomCablato("CDC_ATTO");
	}
	
	public String getTitleCentroDiCostoItem() {
		String label = getLabelAttributoCustomCablato("CDC_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Centro di Costo";
	}
	
	public boolean isRequiredCentroDiCostoItem() {
		return showCentroDiCostoItem() && getFlgObbligatorioAttributoCustomCablato("CDC_ATTO");
	}
	
	public void resetCentroDiCostoAfterChangedAdottante() {
		if(centroDiCostoItem != null) {
			final String value = centroDiCostoItem.getValueAsString();
			centroDiCostoItem.fetchData(new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					if (data.getLength() == 1) {
						centroDiCostoItem.setValue(data.get(0).getAttribute("key"));
						centroDiCostoItem.fireEvent(new ChangedEvent(centroDiCostoItem.getJsObj()));
					} else {
						boolean trovato = false;
						if (data.getLength() > 0) {						
							for (int i = 0; i < data.getLength(); i++) {
								String key = data.get(i).getAttribute("key");
								if (value.equals(key)) {
									trovato = true;
									break;
								}
							}
						}
						if (!trovato) {
							centroDiCostoItem.setValue("");
							centroDiCostoItem.fireEvent(new ChangedEvent(centroDiCostoItem.getJsObj()));
						}
					}
				}
			});
		}
	}
	
	public boolean showDirigentiConcertoItem() {
		return showAttributoCustomCablato("ID_SV_RESP_DI_CONCERTO");
	}
	
	public boolean isRequiredDirigentiConcertoItem() {
		return showDirigentiConcertoItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_RESP_DI_CONCERTO");
	}
	
	public String getTitleDirigentiConcertoItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_RESP_DI_CONCERTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Adottanti di concerto";	
	}

	public String getAltriParamLoadComboDirigentiConcertoItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_RESP_DI_CONCERTO");
	}
	
	public boolean showFlgFirmatarioDirigentiConcertoItem() {
		return getFlgMostraFirmatarioAttributoCustomCablato("ID_SV_RESP_DI_CONCERTO");
	}
	
	public boolean showDirRespRegTecnicaItem() {
		if(isAttoMeroIndirizzo()) {
			return false;
		}
		return showAttributoCustomCablato("ID_SV_DIR_RESP_REG_TECNICA");
	}
	
	public boolean isRequiredDirRespRegTecnicaItem() {
		return showDirRespRegTecnicaItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_DIR_RESP_REG_TECNICA");
	}
	
	public String getTitleDirRespRegTecnicaItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_DIR_RESP_REG_TECNICA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Dir. resp reg. tecnica";
	}
	
	public String getAltriParamLoadComboDirRespRegTecnicaItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_DIR_RESP_REG_TECNICA");
	}
	
	public boolean showAltriDirRespRegTecnicaItem() {
		if(isAttoMeroIndirizzo()) {
			return false;
		}
		return showAttributoCustomCablato("ID_SV_ALTRI_DIR_REG_TECNICA");
	}
	
	public boolean isRequiredAltriDirRespRegTecnicaItem() {
		return showAltriDirRespRegTecnicaItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_ALTRI_DIR_REG_TECNICA");
	}
	
	public String getTitleAltriDirRespRegTecnicaItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_ALTRI_DIR_REG_TECNICA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Altri pareri reg. tecnica";		
	}
	
	public String getAltriParamLoadComboAltriDirRespRegTecnicaItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_ALTRI_DIR_REG_TECNICA");
	}
	
	public boolean showFlgFirmatarioAltriDirRespRegTecnicaItem() {
		return getFlgMostraFirmatarioAttributoCustomCablato("ID_SV_ALTRI_DIR_REG_TECNICA");
	}
	
	public boolean showRdPItem() {
		return showRdPItem(false);
	}
	
	public boolean showRdPItem(boolean skipFlgAncheRdP) {
		if(AurigaLayout.getParametroDBAsBoolean("FORZA_RDP_UGUALE_ADOTTANTE")) {
			return false;
		}
		if(AurigaLayout.getParametroDBAsBoolean("FORZA_RDP_UGUALE_DIR")) {
			return false;
		}				
		boolean flgAdottanteAncheRdP = false;
		RecordList listaAdottante = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaAdottante") : null;
		if(listaAdottante != null && listaAdottante.getLength() > 0) {
			flgAdottanteAncheRdP = listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRdP") != null && listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRdP");
		}
		boolean flgDirRespRegTecnicaAncheRdP = false;
		RecordList listaDirRespRegTecnica = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaDirRespRegTecnica") : null;
		if(listaDirRespRegTecnica != null && listaDirRespRegTecnica.getLength() > 0) {
			flgDirRespRegTecnicaAncheRdP = listaDirRespRegTecnica.get(0).getAttributeAsBoolean("flgDirRespRegTecnicaAncheRdP") != null && listaDirRespRegTecnica.get(0).getAttributeAsBoolean("flgDirRespRegTecnicaAncheRdP");
		}	
		return (skipFlgAncheRdP || (!flgAdottanteAncheRdP && !flgDirRespRegTecnicaAncheRdP)) && showAttributoCustomCablato("ID_SV_RESP_PROC");
	}
	
	public boolean isRequiredRdPItem() {
		return showRdPItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_RESP_PROC");
	}
	
	public String getTitleRdPItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_RESP_PROC");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "RdP (Resp. di Procedimento)";	
	}
	
	public String getAltriParamLoadComboRdPItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_RESP_PROC");
	}
	
	public boolean showRUPItem() {
		return showRUPItem(false);
	}
	
	public boolean showRUPItem(boolean skipFlgAncheRUP) {
		if(AurigaLayout.getParametroDBAsBoolean("FORZA_RUP_UGUALE_ADOTTANTE")) {
			return false;
		}
		if(AurigaLayout.getParametroDBAsBoolean("FORZA_RUP_UGUALE_DIR")) {
			return false;
		}				
		boolean flgAdottanteAncheRdP = false;
		boolean flgAdottanteAncheRUP = false;
		RecordList listaAdottante = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaAdottante") : null;
		if(listaAdottante != null && listaAdottante.getLength() > 0) {
			flgAdottanteAncheRdP = listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRdP") != null && listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRdP");
			flgAdottanteAncheRUP = listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRUP") != null && listaAdottante.get(0).getAttributeAsBoolean("flgAdottanteAncheRUP");
		}				
		boolean flgDirRespRegTecnicaAncheRdP = false;
		boolean flgDirRespRegTecnicaAncheRUP = false;
		RecordList listaDirRespRegTecnica = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaDirRespRegTecnica") : null;
		if(listaDirRespRegTecnica != null && listaDirRespRegTecnica.getLength() > 0) {
			flgDirRespRegTecnicaAncheRdP = listaDirRespRegTecnica.get(0).getAttributeAsBoolean("flgDirRespRegTecnicaAncheRdP") != null && listaDirRespRegTecnica.get(0).getAttributeAsBoolean("flgDirRespRegTecnicaAncheRdP");
			flgDirRespRegTecnicaAncheRUP = listaDirRespRegTecnica.get(0).getAttributeAsBoolean("flgDirRespRegTecnicaAncheRUP") != null && listaDirRespRegTecnica.get(0).getAttributeAsBoolean("flgDirRespRegTecnicaAncheRUP");					
		}				
		boolean flgRdPAncheRUP = false;
		if(!flgAdottanteAncheRdP && !flgDirRespRegTecnicaAncheRdP) {
			RecordList listaRdP = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaRdP") : null;
			if(listaRdP != null && listaRdP.getLength() > 0) {
				flgRdPAncheRUP = listaRdP.get(0).getAttributeAsBoolean("flgRdPAncheRUP") != null && listaRdP.get(0).getAttributeAsBoolean("flgRdPAncheRUP");
			}
		}
		return (skipFlgAncheRUP || (!flgAdottanteAncheRUP && !flgDirRespRegTecnicaAncheRUP && !flgRdPAncheRUP)) && showAttributoCustomCablato("ID_SV_RUP");
	}
	
	public boolean isRequiredRUPItem() {
		return showRUPItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_RUP");
	}
	
	public String getTitleRUPItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_RUP");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "RUP";
	}
	
	public String getAltriParamLoadComboRUPItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_RUP");
	}
	
	public boolean showAssessoriItem() {
		return showAttributoCustomCablato("ID_ASSESSORE_PROPONENTE");
	}
	
	public boolean isRequiredAssessoriItem() {
		return showAssessoriItem() && getFlgObbligatorioAttributoCustomCablato("ID_ASSESSORE_PROPONENTE");
	}
	
	public String getTitleAssessoriItem() {
		String label = getLabelAttributoCustomCablato("ID_ASSESSORE_PROPONENTE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Assessore proponente";	
	}
	
	public String getAltriParamLoadComboAssessoriItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_ASSESSORE_PROPONENTE");
	}
	
	public boolean showAltriAssessoriItem() {
		return showAttributoCustomCablato("ID_ALTRI_ASSESSORI");
	}
	
	public boolean isRequiredAltriAssessoriItem() {
		return showAltriAssessoriItem() && getFlgObbligatorioAttributoCustomCablato("ID_ALTRI_ASSESSORI");
	}
	
	public String getTitleAltriAssessoriItem() {
		String label = getLabelAttributoCustomCablato("ID_ALTRI_ASSESSORI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Altri assessori";
	}
	
	public String getAltriParamLoadComboAltriAssessoriItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_ALTRI_ASSESSORI");
	}
	
	public boolean showFlgFirmatarioAltriAssessoriItem() {
		return getFlgMostraFirmatarioAttributoCustomCablato("ID_ALTRI_ASSESSORI");
	}
	
	public boolean showConsiglieriItem() {
		if(showIniziativaPropostaItem() && ("POPOLARE".equalsIgnoreCase(getValueAsString("iniziativaProposta")) || "CIRCOSCRIZIONE".equalsIgnoreCase(getValueAsString("iniziativaProposta")))) {
			return false;
		}
		return showAttributoCustomCablato("ID_CONSIGLIERE_PROPONENTE");
	}
	
	public boolean isRequiredConsiglieriItem() {
		return showConsiglieriItem() && getFlgObbligatorioAttributoCustomCablato("ID_CONSIGLIERE_PROPONENTE");
	}
	
	public String getTitleConsiglieriItem() {
		String label = getLabelAttributoCustomCablato("ID_CONSIGLIERE_PROPONENTE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Consigliere proponente";
	}
	
	public String getAltriParamLoadComboConsiglieriItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_CONSIGLIERE_PROPONENTE");
	}
	
	public boolean showAltriConsiglieriItem() {
		if(showIniziativaPropostaItem() && ("POPOLARE".equalsIgnoreCase(getValueAsString("iniziativaProposta")) || "CIRCOSCRIZIONE".equalsIgnoreCase(getValueAsString("iniziativaProposta")))) {
			return false;
		}
		return showAttributoCustomCablato("ID_ALTRI_CONSIGLIERI");
	}
	
	public boolean isRequiredAltriConsiglieriItem() {
		return showAltriConsiglieriItem() && getFlgObbligatorioAttributoCustomCablato("ID_ALTRI_CONSIGLIERI");
	}
	
	public String getTitleAltriConsiglieriItem() {
		String label = getLabelAttributoCustomCablato("ID_ALTRI_CONSIGLIERI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Altri consiglieri";
	}
	
	public String getAltriParamLoadComboAltriConsiglieriItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_ALTRI_CONSIGLIERI");
	}
	
	public boolean showFlgFirmatarioAltriConsiglieriItem() {
		return getFlgMostraFirmatarioAttributoCustomCablato("ID_ALTRI_CONSIGLIERI");
	}
	
	public boolean showDirigentiProponentiItem() {
		return showAttributoCustomCablato("ID_SV_DIR_PROPONENTE");
	}
	
	public boolean isRequiredDirigentiProponentiItem() {
		return showDirigentiProponentiItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_DIR_PROPONENTE");
	}
	
	public String getTitleDirigentiProponentiItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_DIR_PROPONENTE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Dirigente proponente";
	}
	
	public String getAltriParamLoadComboDirigentiProponentiItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_DIR_PROPONENTE");
	}
		
	public boolean showAltriDirigentiProponentiItem() {
		if(isAttoMeroIndirizzo()) {
			return false;
		}
		return showAttributoCustomCablato("ID_SV_ALTRI_DIR_PROPONENTI");
	}
	
	public boolean isRequiredAltriDirigentiProponentiItem() {
		return showAltriDirigentiProponentiItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_ALTRI_DIR_PROPONENTI");
	}
	
	public String getTitleAltriDirigentiProponentiItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_ALTRI_DIR_PROPONENTI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Altri dirigenti proponenti";		
	}
	
	public String getAltriParamLoadComboAltriDirigentiProponentiItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_ALTRI_DIR_PROPONENTI");
	}
	
	public boolean showFlgFirmatarioAltriDirigentiProponentiItem() {
		return getFlgMostraFirmatarioAttributoCustomCablato("ID_SV_ALTRI_DIR_PROPONENTI");
	}
	
	public boolean showMotiviAltriDirigentiProponentiItem() {
		String flgMostraMotivi = getFlgMostraMotiviAttributoCustomCablato("ID_SV_ALTRI_DIR_PROPONENTI");
		return flgMostraMotivi != null && !"".equals(flgMostraMotivi);				
	}
	
	public boolean isRequiredMotiviAltriDirigentiProponentiItem() {
		String flgMostraMotivi = getFlgMostraMotiviAttributoCustomCablato("ID_SV_ALTRI_DIR_PROPONENTI");
		return flgMostraMotivi != null && _MANDATORY.equalsIgnoreCase(flgMostraMotivi);		
	}
	
	public boolean showCoordinatoriCompCircItem() {
		return showAttributoCustomCablato("ID_COORDINATORE_COMP_CIRC");
	}
	
	public boolean isRequiredCoordinatoriCompCircItem() {
		return showCoordinatoriCompCircItem() && getFlgObbligatorioAttributoCustomCablato("ID_COORDINATORE_COMP_CIRC");
	}
	
	public String getTitleCoordinatoriCompCircItem() {
		String label = getLabelAttributoCustomCablato("ID_COORDINATORE_COMP_CIRC");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Coordinatore competente per materia";
	}
	
	public String getAltriParamLoadComboCoordinatoriCompCircItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_COORDINATORE_COMP_CIRC");
	}
	
	public boolean showFlgRichiediVistoDirettoreItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_VISTO_DIR_SUP");
	}
		
	public String getTitleFlgRichiediVistoDirettoreItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_VISTO_DIR_SUP");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Richiesto visto Dir. sovraordinato";	
	}
	
	public boolean showRespVistiConformitaItem() {
		return showAttributoCustomCablato("ID_SV_RESP_VISTI_CONFORMITA");
	}
	
	public boolean isRequiredRespVistiConformitaItem() {
		return showRespVistiConformitaItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_RESP_VISTI_CONFORMITA");
	}
	
	public String getTitleRespVistiConformitaItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_RESP_VISTI_CONFORMITA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Visti di conformità";		
	}
	
	public String getAltriParamLoadComboRespVistiConformitaItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_RESP_VISTI_CONFORMITA");
	}
	
	public boolean showFlgFirmatarioRespVistiConformitaItem() {
		return getFlgMostraFirmatarioAttributoCustomCablato("ID_SV_RESP_VISTI_CONFORMITA");
	}
	
	public boolean showFlgRiacqVistoInRitornoIterRespVistiConformitaItem() {
		return getFlgMostraVistoInRitornoIterAttributoCustomCablato("ID_SV_RESP_VISTI_CONFORMITA");
	}	
	
	public boolean showMotiviRespVistiConformitaItem() {
		String flgMostraMotivi = getFlgMostraMotiviAttributoCustomCablato("ID_SV_RESP_VISTI_CONFORMITA");
		return flgMostraMotivi != null && !"".equals(flgMostraMotivi);				
	}
	
	public boolean isRequiredMotiviRespVistiConformitaItem() {
		String flgMostraMotivi = getFlgMostraMotiviAttributoCustomCablato("ID_SV_RESP_VISTI_CONFORMITA");
		return flgMostraMotivi != null && _MANDATORY.equalsIgnoreCase(flgMostraMotivi);		
	}
	
	public boolean showEstensoriItem() {
		return showAttributoCustomCablato("ID_SV_ESTENSORE_MAIN");
	}
	
	public boolean isRequiredEstensoriItem() {
		return showEstensoriItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_ESTENSORE_MAIN");
	}
	
	public String getTitleEstensoriItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_ESTENSORE_MAIN");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Estensore principale";
	}
	
	public String getAltriParamLoadComboEstensoriItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_ESTENSORE_MAIN");
	}
	
	public boolean showAltriEstensoriItem() {
		return showAttributoCustomCablato("ID_SV_ALTRI_ESTENSORI");
	}
	
	public boolean isRequiredAltriEstensoriItem() {
		return showAltriEstensoriItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_ALTRI_ESTENSORI");
	}
	
	public String getTitleAltriEstensoriItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_ALTRI_ESTENSORI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Altri estensori";
	}
	
	public String getAltriParamLoadComboAltriEstensoriItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_ALTRI_ESTENSORI");
	}
	
	public boolean showIstruttoriItem() {
		return showAttributoCustomCablato("ID_SV_ISTRUTTORE_MAIN");
	}
	
	public boolean isRequiredIstruttoriItem() {
		return showIstruttoriItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_ISTRUTTORE_MAIN");
	}
	
	public String getTitleIstruttoriItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_ISTRUTTORE_MAIN");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Resp. istruttoria";
	}
	
	public String getAltriParamLoadComboIstruttoriItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_ISTRUTTORE_MAIN");
	}
	
	public boolean showAltriIstruttoriItem() {
		return showAttributoCustomCablato("ID_SV_ALTRI_ISTRUTTORI");
	}
	
	public boolean isRequiredAltriIstruttoriItem() {
		return showAltriIstruttoriItem() && getFlgObbligatorioAttributoCustomCablato("ID_SV_ALTRI_ISTRUTTORI");
	}
	
	public String getTitleAltriIstruttoriItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_ALTRI_ISTRUTTORI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Altri istruttori";
	}
	
	public String getAltriParamLoadComboAltriIstruttoriItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_ALTRI_ISTRUTTORI");
	}	
		
	protected void createRuoliForm() {
		
		ruoliForm = new DynamicForm();
		ruoliForm.setValuesManager(vm);
		ruoliForm.setWidth100();
		ruoliForm.setPadding(5);
		ruoliForm.setWrapItemTitles(false);
		ruoliForm.setNumCols(20);
		ruoliForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ruoliForm.setTabSet(tabSet);
		ruoliForm.setTabID(_TAB_DATI_SCHEDA_ID);
		ruoliForm.setHeight(1);
		
		codUfficioProponenteItem = new HiddenItem("codUfficioProponente");
		desUfficioProponenteItem = new HiddenItem("desUfficioProponente");
		
		if (isAbilToSelUffPropEsteso()) {	
			
			listaUfficioProponenteItem = new SelezionaUOItem() {
				
				@Override
				public int getSelectItemOrganigrammaWidth() {
					return 650;
				}
				
				@Override
				public boolean skipValidation() {
					if(showUfficioProponenteEstesoItem()) {
						return super.skipValidation();
					}
					return true;
				}
				
				@Override
				protected VLayout creaVLayout() {
					VLayout lVLayout = super.creaVLayout();
					lVLayout.setWidth100();
					lVLayout.setPadding(11);
					lVLayout.setMargin(4);
					lVLayout.setIsGroup(true);
					lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleUfficioProponenteEstesoItem()) + "</span>");
					return lVLayout;
				}
				
				@Override
				public Boolean getShowRemoveButton() {
					return true;
				}
				
				@Override
				public void manageChangedUoSelezionata() {						
					afterSelezioneUoProponente();
				}
			};
			listaUfficioProponenteItem.setName("listaUfficioProponente");
			listaUfficioProponenteItem.setShowTitle(false);
			listaUfficioProponenteItem.setColSpan(20);
			listaUfficioProponenteItem.setNotReplicable(true);
			listaUfficioProponenteItem.setAttribute("obbligatorio", true);
			listaUfficioProponenteItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return showUfficioProponenteEstesoItem();
				}
			});
			
		} else {
			
//			GWTRestDataSource ufficioProponenteDS = new GWTRestDataSource("UfficioProponenteAttoDatasource", "idUo", FieldType.TEXT);
//			ufficioProponenteDS.addParam("altriParamLoadCombo", getAltriParamLoadComboUfficioProponenteItem());
			
			ufficioProponenteItem = new SelectItem("ufficioProponente", getTitleUfficioProponenteItem()) {
				
				@Override
				public void onOptionClick(Record record) {
					super.onOptionClick(record);
					String descrizione = record.getAttribute("descrizione");
					if(descrizione != null && !"".equals(descrizione)) {
						ruoliForm.setValue("codUfficioProponente", descrizione.substring(0, descrizione.indexOf(" - ")));
						ruoliForm.setValue("desUfficioProponente", descrizione.substring(descrizione.indexOf(" - ") + 3));
					} else {
						ruoliForm.setValue("codUfficioProponente", "");
						ruoliForm.setValue("desUfficioProponente", "");			
					}
				}
				
				@Override
				protected void clearSelect() {
					super.clearSelect();
					ruoliForm.setValue("codUfficioProponente", "");
					ruoliForm.setValue("desUfficioProponente", "");				
				}
				
				@Override
				public void setValue(String value) {
					super.setValue(value);
					if (value == null || "".equals(value)) {
						ruoliForm.setValue("codUfficioProponente", "");
						ruoliForm.setValue("desUfficioProponente", "");	
					}
	            }
			};
//			ufficioProponenteItem.setTitleOrientation(TitleOrientation.TOP);
			ufficioProponenteItem.setWidth(500);
			ufficioProponenteItem.setDisplayField("descrizione");
			ufficioProponenteItem.setValueField("idUo");
//			ufficioProponenteItem.setOptionDataSource(ufficioProponenteDS);
//			ufficioProponenteItem.setAutoFetchData(true);
//			ufficioProponenteItem.addDataArrivedHandler(new DataArrivedHandler() {			
//				@Override
//				public void onDataArrived(DataArrivedEvent event) {					
//					RecordList data = event.getData();
//					if(data != null && data.getLength() > 0) {
//						ufficioProponenteValueMap = new LinkedHashMap<String, String>();
//						for (int i = 0; i < data.getLength(); i++) {	
//							ufficioProponenteValueMap.put(data.get(i).getAttribute("idUo"), data.get(i).getAttribute("descrizione"));			
//						}
//						if(data.getLength() == 1) {
//							Record record = data.get(0);
//							String key = record.getAttribute("idUo");
//							String value = record.getAttribute("descrizione");
//							ruoliForm.setValue("ufficioProponente", (key != null && key.startsWith("UO")) ? key.substring(2) : key);
//							if(value != null && !"".equals(value)) {
//								ruoliForm.setValue("codUfficioProponente", value.substring(0, value.indexOf(" - ")));
//								ruoliForm.setValue("desUfficioProponente", value.substring(value.indexOf(" - ") + 3));
//							}
//							afterSelezioneUoProponente();
//						}
//					}				
//				};
//			});
			ufficioProponenteItem.setValueMap(getUfficioProponenteValueMap());
			if (getUfficioProponenteValueMap().size() == 1) {
				String key = getUfficioProponenteValueMap().keySet().toArray(new String[1])[0];
				String value = getUfficioProponenteValueMap().get(key);
				ufficioProponenteItem.setValue((key != null && key.startsWith("UO")) ? key.substring(2) : key);
				if(value != null && !"".equals(value)) {
					codUfficioProponenteItem.setValue(value.substring(0, value.indexOf(" - ")));
					desUfficioProponenteItem.setValue(value.substring(value.indexOf(" - ") + 3));
				}
			}
			ufficioProponenteItem.setAttribute("obbligatorio", true);
			ufficioProponenteItem.setAllowEmptyValue(false);
			ufficioProponenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return showUfficioProponenteItem();
				}
			}));
			ufficioProponenteItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return showUfficioProponenteItem();
				}
			});
			ufficioProponenteItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {					
					afterSelezioneUoProponente();
				}
			});
			
		} 
		
		listaAdottanteItem = new DirigenteAdottanteCompletaItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboAdottanteItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showAdottanteItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public String getIdUdAttoDaAnn() {
				return getIdUdAttoDaAnnullare();
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}
			
			@Override
			public boolean showFlgAncheRdP() {
				return showRdPItem(true);
			}
			
			@Override
			public boolean showFlgAncheRUP() {
				return showRUPItem(true);
			}
		
			@Override
			public void manageOnChangedFlgAdottanteAncheRdP(boolean value) {
				ruoliForm.markForRedraw();
			}
		
			@Override
			public void manageOnChangedFlgAdottanteAncheRUP(boolean value) {
				if(value) {
					listaRdPItem.clearFlgRdPAncheRUP();
				}
				ruoliForm.markForRedraw();
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);		
				if(isRequiredAdottanteItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleAdottanteItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleAdottanteItem() + "</span>");
				}
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
			
			@Override
			public boolean selectUniqueValueAfterChangedParams() {
				return isRequiredAdottanteItem();
			}
			
			@Override
			public void manageChangedScrivaniaSelezionata() {	
				afterSelezioneScrivaniaAdottante();
			}
		};
		listaAdottanteItem.setName("listaAdottante");
		listaAdottanteItem.setShowTitle(false);
		listaAdottanteItem.setColSpan(20);
		listaAdottanteItem.setNotReplicable(true);
		if(isRequiredAdottanteItem()) {
			listaAdottanteItem.setAttribute("obbligatorio", true);	
		}
		listaAdottanteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showAdottanteItem();
			}
		});
		
		GWTRestDataSource centroDiCostoDS = new GWTRestDataSource("LoadComboCdCAdottanteDataSource", "key", FieldType.TEXT);
		 		
		centroDiCostoItem = new SelectItem("centroDiCosto", getTitleCentroDiCostoItem()) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid centroDiCostoPickListProperties = super.builPickListProperties();	
				centroDiCostoPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource centroDiCostoDS = (GWTRestDataSource) centroDiCostoItem.getOptionDataSource();		
						centroDiCostoDS.addParam("adottante", getDirigenteAdottante());				
						centroDiCostoItem.setOptionDataSource(centroDiCostoDS);
						centroDiCostoItem.invalidateDisplayValueCache();
					}
				});
				return centroDiCostoPickListProperties;
			}
		};
//		centroDiCostoItem.setTitleOrientation(TitleOrientation.TOP);		
		centroDiCostoItem.setWidth(160);
		centroDiCostoItem.setStartRow(true);
		centroDiCostoItem.setValueField("key");
		centroDiCostoItem.setDisplayField("value");
		centroDiCostoItem.setOptionDataSource(centroDiCostoDS);		
		if(isRequiredCentroDiCostoItem()) {
			centroDiCostoItem.setAttribute("obbligatorio", true);
		} else {
			centroDiCostoItem.setAllowEmptyValue(true);
		}
		centroDiCostoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredCentroDiCostoItem();
			}
		}));
		centroDiCostoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCentroDiCostoItem();
			}
		});	
		
		listaDirigentiConcertoItem = new DirigentiConcertoCompletaItem()  {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboDirigentiConcertoItem();
			}
			
			@Override
			public boolean showFlgFirmatario() {
				return showFlgFirmatarioDirigentiConcertoItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showDirigentiConcertoItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredDirigentiConcertoItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleDirigentiConcertoItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleDirigentiConcertoItem() + "</span>");
				}				
				return lVLayout;
			}
		};
		listaDirigentiConcertoItem.setName("listaDirigentiConcerto");
		listaDirigentiConcertoItem.setStartRow(true);
		listaDirigentiConcertoItem.setShowTitle(false);
		listaDirigentiConcertoItem.setColSpan(20);	
		if(isRequiredDirigentiConcertoItem()) {
			listaDirigentiConcertoItem.setAttribute("obbligatorio", true);	
		}
		listaDirigentiConcertoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDirigentiConcertoItem();
			}
		});

		listaDirRespRegTecnicaItem = new DirigenteRespRegTecnicaCompletaItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboDirRespRegTecnicaItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showDirRespRegTecnicaItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public String getIdUdAttoDaAnn() {
				return getIdUdAttoDaAnnullare();
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}
			
			@Override
			public boolean showFlgAncheRdP() {
				return showRdPItem(true);
			}
			
			@Override
			public boolean showFlgAncheRUP() {
				return showRUPItem(true);
			}
		
			@Override
			public void manageOnChangedFlgDirRespRegTecnicaAncheRdP(boolean value) {
				ruoliForm.markForRedraw();
			}
		
			@Override
			public void manageOnChangedFlgDirRespRegTecnicaAncheRUP(boolean value) {
				if(value) {
					listaRdPItem.clearFlgRdPAncheRUP();
				}
				ruoliForm.markForRedraw();
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredDirRespRegTecnicaItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleDirRespRegTecnicaItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleDirRespRegTecnicaItem() + "</span>");
				}				
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
		};
		listaDirRespRegTecnicaItem.setName("listaDirRespRegTecnica");
		listaDirRespRegTecnicaItem.setShowTitle(false);
		listaDirRespRegTecnicaItem.setColSpan(20);
		listaDirRespRegTecnicaItem.setNotReplicable(true);
		if(isRequiredDirRespRegTecnicaItem()) {
			listaDirRespRegTecnicaItem.setAttribute("obbligatorio", true);
		}
		listaDirRespRegTecnicaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDirRespRegTecnicaItem();
			}
		});
		
		listaAltriDirRespRegTecnicaItem = new AltriDirRespRegTecnicaCompletaItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboAltriDirRespRegTecnicaItem();
			}
			
			@Override
			public boolean showFlgFirmatario() {
				return showFlgFirmatarioAltriDirRespRegTecnicaItem(); 
			}
			
			@Override
			public boolean skipValidation() {
				if(showAltriDirRespRegTecnicaItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);		
				if(isRequiredAltriDirRespRegTecnicaItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleAltriDirRespRegTecnicaItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleAltriDirRespRegTecnicaItem() + "</span>");
				}
				return lVLayout;
			}
		};
		listaAltriDirRespRegTecnicaItem.setName("listaAltriDirRespRegTecnica");
		listaAltriDirRespRegTecnicaItem.setStartRow(true);
		listaAltriDirRespRegTecnicaItem.setShowTitle(false);
		listaAltriDirRespRegTecnicaItem.setColSpan(20);
//		if(showAttributoCustomCablato("ID_SV_ALTRI_DIR_REG_TECNICA") && getFlgObbligatorioAttributoCustomCablato("ID_SV_ALTRI_DIR_REG_TECNICA")) {
//			listaAltriDirRespRegTecnicaItem.setAttribute("obbligatorio", true);
//		}
		listaAltriDirRespRegTecnicaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredAltriDirRespRegTecnicaItem()) {
					listaAltriDirRespRegTecnicaItem.setAttribute("obbligatorio", true);
				} else {
					listaAltriDirRespRegTecnicaItem.setAttribute("obbligatorio", false);
				}
				listaAltriDirRespRegTecnicaItem.storeValue(form.getValueAsRecordList(listaAltriDirRespRegTecnicaItem.getName()));				
				return showAltriDirRespRegTecnicaItem();
			}
		});
		
		listaRdPItem = new ResponsabileDiProcedimentoCompletaItem() {
			
			@Override
			public boolean showFlgAncheRUP() {
				return showRUPItem(true);
			}
			
			@Override
			public void manageOnChangedFlgRdPAncheRUP(boolean value) {
				if(value) {
					listaAdottanteItem.clearFlgAdottanteAncheRUP();
				}
				ruoliForm.markForRedraw();
			}
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboRdPItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showRdPItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			};
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);					
				if(isRequiredRdPItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleRdPItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleRdPItem() + "</span>");
				}
				return lVLayout;
			}
		};
		listaRdPItem.setName("listaRdP");
		listaRdPItem.setShowTitle(false);
		listaRdPItem.setColSpan(20);
		listaRdPItem.setNotReplicable(true);
		if(isRequiredRdPItem()) {
			listaRdPItem.setAttribute("obbligatorio", true);
		}
		listaRdPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showRdPItem();
			}
		});			
		
		listaRUPItem = new ResponsabileUnicoProvvedimentoCompletaItem() {
						
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboRUPItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showRUPItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			};
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredRUPItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleRUPItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleRUPItem() + "</span>");
				}
				return lVLayout;
			}
		};
		listaRUPItem.setName("listaRUP");
		listaRUPItem.setShowTitle(false);
		listaRUPItem.setColSpan(20);
		listaRUPItem.setNotReplicable(true);
		if(isRequiredRUPItem()) {
			listaRUPItem.setAttribute("obbligatorio", true);
		}
		listaRUPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showRUPItem();		
			}
		});
		
		String titleAssessoriItem = "<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleAssessoriItem() + "</span>";
		if(isRequiredAssessoriItem()) {
			titleAssessoriItem = "<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleAssessoriItem()) + "</span>";
		}
		listaAssessoriItem = new AssessoriItem(titleAssessoriItem) {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboAssessoriItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showAssessoriItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
			
			@Override
			public boolean selectUniqueValueAfterChangedParams() {
				return isRequiredAssessoriItem();
			}
		};
		listaAssessoriItem.setName("listaAssessori");
		listaAssessoriItem.setShowTitle(false);
		listaAssessoriItem.setColSpan(20);
		listaAssessoriItem.setNotReplicable(true);
		if(isRequiredAssessoriItem()) {
			listaAssessoriItem.setAttribute("obbligatorio", true);
		}
		listaAssessoriItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showAssessoriItem();
			}
		});
		
		String titleAltriAssessoriItem = "<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleAltriAssessoriItem() + "</span>";
		if(isRequiredAltriAssessoriItem()) {
			titleAltriAssessoriItem = "<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleAltriAssessoriItem()) + "</span>";
		}
		listaAltriAssessoriItem = new AssessoriItem(titleAltriAssessoriItem) {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboAltriAssessoriItem();
			}
			
			@Override
			public boolean showFlgFirmatario() {
				return showFlgFirmatarioAltriAssessoriItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showAltriAssessoriItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}
		};
		listaAltriAssessoriItem.setName("listaAltriAssessori");
		listaAltriAssessoriItem.setShowTitle(false);
		listaAltriAssessoriItem.setColSpan(20);
		if(isRequiredAltriAssessoriItem()) {
			listaAltriAssessoriItem.setAttribute("obbligatorio", true);
		}
		listaAltriAssessoriItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showAltriAssessoriItem();
			}
		});
		
		String titleConsiglieriItem = "<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleConsiglieriItem() + "</span>";
		if(isRequiredConsiglieriItem()) {
			titleConsiglieriItem = "<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleConsiglieriItem()) + "</span>";
		}
		listaConsiglieriItem = new ConsiglieriItem(titleConsiglieriItem) {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboConsiglieriItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showConsiglieriItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
		};
		listaConsiglieriItem.setName("listaConsiglieri");
		listaConsiglieriItem.setShowTitle(false);
		listaConsiglieriItem.setColSpan(20);
		listaConsiglieriItem.setNotReplicable(true);		
//		if(showAttributoCustomCablato("ID_CONSIGLIERE_PROPONENTE") && getFlgObbligatorioAttributoCustomCablato("ID_CONSIGLIERE_PROPONENTE")) {
//			listaConsiglieriItem.setAttribute("obbligatorio", true);
//		}
		listaConsiglieriItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredConsiglieriItem()) {
					listaConsiglieriItem.setAttribute("obbligatorio", true);
				} else {
					listaConsiglieriItem.setAttribute("obbligatorio", false);
				}
				listaConsiglieriItem.storeValue(form.getValueAsRecordList(listaConsiglieriItem.getName()));				
				return showConsiglieriItem();
			}
		});	
		
		String titleAltriConsiglieriItem = "<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleAltriConsiglieriItem() + "</span>";
		if(isRequiredAltriConsiglieriItem()) {
			titleAltriConsiglieriItem = "<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleAltriConsiglieriItem()) + "</span>";
		}
		listaAltriConsiglieriItem = new ConsiglieriItem(titleAltriConsiglieriItem) {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboAltriConsiglieriItem();
			}
			
			@Override
			public boolean showFlgFirmatario() {
				return showFlgFirmatarioAltriConsiglieriItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showAltriConsiglieriItem()) {
					return super.skipValidation();
				}
				return true;
			}
		};
		listaAltriConsiglieriItem.setName("listaAltriConsiglieri");
		listaAltriConsiglieriItem.setShowTitle(false);
		listaAltriConsiglieriItem.setColSpan(20);
//		if(showAttributoCustomCablato("ID_ALTRI_CONSIGLIERI") && getFlgObbligatorioAttributoCustomCablato("ID_ALTRI_CONSIGLIERI")) {
//			listaAltriConsiglieriItem.setAttribute("obbligatorio", true);
//		}
		listaAltriConsiglieriItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredAltriConsiglieriItem()) {
					listaAltriConsiglieriItem.setAttribute("obbligatorio", true);
				} else {
					listaAltriConsiglieriItem.setAttribute("obbligatorio", false);
				}
				listaAltriConsiglieriItem.storeValue(form.getValueAsRecordList(listaAltriConsiglieriItem.getName()));				
				return showAltriConsiglieriItem();
			}
		});	
		
		listaDirigentiProponentiItem = new DirigentiProponentiCompletaItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboDirigentiProponentiItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showDirigentiProponentiItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);		
				if(isRequiredDirigentiProponentiItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleDirigentiProponentiItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleDirigentiProponentiItem() + "</span>");
				}
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
		};
		listaDirigentiProponentiItem.setName("listaDirigentiProponenti");
		listaDirigentiProponentiItem.setShowTitle(false);
		listaDirigentiProponentiItem.setColSpan(20);
		listaDirigentiProponentiItem.setNotReplicable(true);
		if(isRequiredDirigentiProponentiItem()) {
			listaDirigentiProponentiItem.setAttribute("obbligatorio", true);	
		}
		listaDirigentiProponentiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDirigentiProponentiItem();
			}
		});
			
		listaAltriDirigentiProponentiItem = new AltriDirigentiProponentiCompletaItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboAltriDirigentiProponentiItem();
			}
			
			@Override
			public boolean showFlgFirmatario() {
				return showFlgFirmatarioAltriDirigentiProponentiItem();
			}
			
			@Override
			public boolean showMotivi() {
				return showMotiviAltriDirigentiProponentiItem();
			}
			
			@Override
			public boolean isRequiredMotivi() {
				return isRequiredMotiviAltriDirigentiProponentiItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showAltriDirigentiProponentiItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);	
				if(isRequiredAltriDirigentiProponentiItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleAltriDirigentiProponentiItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleAltriDirigentiProponentiItem() + "</span>");
				}
				return lVLayout;
			}
		};
		listaAltriDirigentiProponentiItem.setName("listaAltriDirigentiProponenti");
		listaAltriDirigentiProponentiItem.setStartRow(true);
		listaAltriDirigentiProponentiItem.setShowTitle(false);
		listaAltriDirigentiProponentiItem.setColSpan(20);
		if(isRequiredAltriDirigentiProponentiItem()) {
			listaAltriDirigentiProponentiItem.setAttribute("obbligatorio", true);	
		}
		listaAltriDirigentiProponentiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showAltriDirigentiProponentiItem();
			}
		});
		
		String titleCoordinatoriCompCircItem = "<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleCoordinatoriCompCircItem() + "</span>";
		if(isRequiredCoordinatoriCompCircItem()) {
			titleCoordinatoriCompCircItem = "<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleCoordinatoriCompCircItem()) + "</span>";
		}
		listaCoordinatoriCompCircItem = new CoordinatoriCompCircItem(titleCoordinatoriCompCircItem) {
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboCoordinatoriCompCircItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showCoordinatoriCompCircItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
		};
		listaCoordinatoriCompCircItem.setName("listaCoordinatoriCompCirc");
		listaCoordinatoriCompCircItem.setShowTitle(false);
		listaCoordinatoriCompCircItem.setColSpan(20);
		listaCoordinatoriCompCircItem.setNotReplicable(true);		
		if(isRequiredCoordinatoriCompCircItem()) {
			listaCoordinatoriCompCircItem.setAttribute("obbligatorio", true);
		}
		listaCoordinatoriCompCircItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCoordinatoriCompCircItem();
			}
		});	
		
		flgRichiediVistoDirettoreItem = new CheckboxItem("flgRichiediVistoDirettore", getTitleFlgRichiediVistoDirettoreItem());
		flgRichiediVistoDirettoreItem.setDefaultValue(false);
		flgRichiediVistoDirettoreItem.setStartRow(true);
		flgRichiediVistoDirettoreItem.setColSpan(20);
		flgRichiediVistoDirettoreItem.setWidth("*");
		flgRichiediVistoDirettoreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgRichiediVistoDirettoreItem();
			}
		});
		
		listaRespVistiConformitaItem = new RespVistiConformitaCompletaItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboRespVistiConformitaItem();
			}
			
			@Override
			public boolean showFlgFirmatario() {
				return showFlgFirmatarioRespVistiConformitaItem();
			}
			
			@Override
			public boolean showMotivi() {
				return showMotiviRespVistiConformitaItem();
			}
						
			@Override
			public boolean isRequiredMotivi() {
				return isRequiredMotiviRespVistiConformitaItem();
			}
			
			@Override
			public boolean showFlgRiacqVistoInRitornoIter() {
				return showFlgRiacqVistoInRitornoIterRespVistiConformitaItem();
			}			
			
			@Override
			public boolean skipValidation() {
				if(showRespVistiConformitaItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);	
				if(isRequiredRespVistiConformitaItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleRespVistiConformitaItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleRespVistiConformitaItem() + "</span>");
				}
				return lVLayout;
			}
		};
		listaRespVistiConformitaItem.setName("listaRespVistiConformita");
		listaRespVistiConformitaItem.setStartRow(true);
		listaRespVistiConformitaItem.setShowTitle(false);
		listaRespVistiConformitaItem.setColSpan(20);
		if(isRequiredRespVistiConformitaItem()) {
			listaRespVistiConformitaItem.setAttribute("obbligatorio", true);	
		}
		listaRespVistiConformitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showRespVistiConformitaItem();
			}
		});
		
		listaEstensoriItem = new EstensoreCompletaItem() {
				
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboEstensoriItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showEstensoriItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}			
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredEstensoriItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleEstensoriItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleEstensoriItem() + "</span>");
				}				
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
		};
		listaEstensoriItem.setName("listaEstensori");
		listaEstensoriItem.setShowTitle(false);
		listaEstensoriItem.setColSpan(20);
		listaEstensoriItem.setNotReplicable(true);				
		if(isRequiredEstensoriItem()) {
			listaEstensoriItem.setAttribute("obbligatorio", true);			
		}	
		listaEstensoriItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showEstensoriItem();
			}
		});	
		
		listaAltriEstensoriItem = new EstensoreCompletaItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboAltriEstensoriItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showAltriEstensoriItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}			
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredAltriEstensoriItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleAltriEstensoriItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleAltriEstensoriItem() + "</span>");
				}				
				return lVLayout;
			}
		};
		listaAltriEstensoriItem.setName("listaAltriEstensori");
		listaAltriEstensoriItem.setShowTitle(false);
		listaAltriEstensoriItem.setColSpan(20);			
		if(isRequiredAltriEstensoriItem()) {
			listaAltriEstensoriItem.setAttribute("obbligatorio", true);			
		}	
		listaAltriEstensoriItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showAltriEstensoriItem();
			}
		});	
		
		listaIstruttoriItem = new IstruttoreCompletaItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboIstruttoriItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showIstruttoriItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}			
			
			@Override
			public Boolean getFlgAttoMeroIndirizzo() {
				return isAttoMeroIndirizzo();
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredIstruttoriItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleIstruttoriItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleIstruttoriItem() + "</span>");
				}				
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
		};
		listaIstruttoriItem.setName("listaIstruttori");
		listaIstruttoriItem.setShowTitle(false);
		listaIstruttoriItem.setColSpan(20);
		listaIstruttoriItem.setNotReplicable(true);				
		if(isRequiredIstruttoriItem()) {
			listaIstruttoriItem.setAttribute("obbligatorio", true);			
		}	
		listaIstruttoriItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showIstruttoriItem();
			}
		});	
		
		listaAltriIstruttoriItem = new IstruttoreCompletaItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboAltriIstruttoriItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showAltriIstruttoriItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public String getUoProponenteCorrente() {
				return getIdUoProponente();
			}			
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredAltriIstruttoriItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleAltriIstruttoriItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleAltriIstruttoriItem() + "</span>");
				}				
				return lVLayout;
			}
		};
		listaAltriIstruttoriItem.setName("listaAltriIstruttori");
		listaAltriIstruttoriItem.setShowTitle(false);
		listaAltriIstruttoriItem.setColSpan(20);			
		if(isRequiredAltriIstruttoriItem()) {
			listaAltriIstruttoriItem.setAttribute("obbligatorio", true);			
		}	
		listaAltriIstruttoriItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showAltriIstruttoriItem();
			}
		});	
		
		ruoliForm.setFields(
			codUfficioProponenteItem,
			desUfficioProponenteItem,				
			isAbilToSelUffPropEsteso() ? listaUfficioProponenteItem : ufficioProponenteItem,
			listaAdottanteItem,
			centroDiCostoItem,
			listaDirigentiConcertoItem,
			listaDirRespRegTecnicaItem,
			listaAltriDirRespRegTecnicaItem,
			listaRdPItem, 
			listaRUPItem,
			listaAssessoriItem,
			listaAltriAssessoriItem,
			listaConsiglieriItem,
			listaAltriConsiglieriItem,
			listaDirigentiProponentiItem,
			listaAltriDirigentiProponentiItem,
			listaCoordinatoriCompCircItem,
			flgRichiediVistoDirettoreItem,
			listaRespVistiConformitaItem,
			listaEstensoriItem,
			listaAltriEstensoriItem,
			listaIstruttoriItem,
			listaAltriIstruttoriItem
		);		
	}
	
	/************************************** 
	 * DATI SCHEDA - VISTI DIR. SUPERIORI *
	 **************************************/
	
	public boolean showDetailSectionVistiDirSuperiori() {
		return showAttributoCustomCablato("VISTI_DIR_SUPERIORI");
	}
	
	public String getTitleDetailSectionVistiDirSuperiori() {
		String label = getLabelAttributoCustomCablato("VISTI_DIR_SUPERIORI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Visti dir. superiori";	 
	}
	
	protected void createDetailSectionVistiDirSuperiori() {
		
		createVistiDirSuperioriForm();
		
		detailSectionVistiDirSuperiori = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionVistiDirSuperiori(), true, true, false, vistiDirSuperioriForm);
	}
	
	public boolean showFlgVistoDirSup1Item() {
		return showDetailSectionVistiDirSuperiori() && showAttributoCustomCablato("TASK_RESULT_2_VISTO_DIR_SUP_1");
	}
		
	public String getTitleFlgVistoDirSup1Item() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_VISTO_DIR_SUP_1");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "visto dirigente di area";	
	}
	
	public boolean getDefaultValueAsBooleanFlgVistoDirSup1Item() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_VISTO_DIR_SUP_1");
	}
	
	public boolean showFlgVistoDirSup2Item() {
		return showDetailSectionVistiDirSuperiori() && showAttributoCustomCablato("TASK_RESULT_2_VISTO_DIR_SUP_2");
	}
		
	public String getTitleFlgVistoDirSup2Item() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_VISTO_DIR_SUP_2");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "visto direttore di divisione";	
	}
	
	public boolean getDefaultValueAsBooleanFlgVistoDirSup2Item() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_VISTO_DIR_SUP_2");
	}
	
	protected void createVistiDirSuperioriForm() {
		
		vistiDirSuperioriForm = new DynamicForm();
		vistiDirSuperioriForm.setValuesManager(vm);
		vistiDirSuperioriForm.setWidth100();
		vistiDirSuperioriForm.setPadding(5);
		vistiDirSuperioriForm.setWrapItemTitles(false);
		vistiDirSuperioriForm.setNumCols(20);
		vistiDirSuperioriForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		vistiDirSuperioriForm.setTabSet(tabSet);
		vistiDirSuperioriForm.setTabID(_TAB_DATI_SCHEDA_ID);
		vistiDirSuperioriForm.setHeight(1);
		
		flgVistoDirSup1Item = new CheckboxItem("flgVistoDirSup1", getTitleFlgVistoDirSup1Item());
		flgVistoDirSup1Item.setDefaultValue(getDefaultValueAsBooleanFlgVistoDirSup1Item());
		flgVistoDirSup1Item.setColSpan(1);
		flgVistoDirSup1Item.setWidth("*");			
		flgVistoDirSup1Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgVistoDirSup1Item();
			}
		});
		
		flgVistoDirSup2Item = new CheckboxItem("flgVistoDirSup2", getTitleFlgVistoDirSup2Item());
		flgVistoDirSup2Item.setDefaultValue(getDefaultValueAsBooleanFlgVistoDirSup2Item());
		flgVistoDirSup2Item.setColSpan(1);
		flgVistoDirSup2Item.setWidth("*");			
		flgVistoDirSup2Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgVistoDirSup2Item();
			}
		});
		
		vistiDirSuperioriForm.setFields(flgVistoDirSup1Item, flgVistoDirSup2Item);			
	}
	
	/*********************************************** 
	 * DATI SCHEDA - PARERE DELLA/E CIRCOSCRIZIONI *
	 ***********************************************/
	
	public boolean showDetailSectionParereCircoscrizioni() {
		return showParereCircoscrizioniItem();
	}
	
	public String getTitleDetailSectionParereCircoscrizioni() {
		return getTitleParereCircoscrizioniItem();
	}
	
	public boolean isRequiredDetailSectionParereCircoscrizioni() {
		return isRequiredParereCircoscrizioniItem();
	}		
	
	protected void createDetailSectionParereCircoscrizioni() {
		
		createParereCircoscrizioniForm();
		
		detailSectionParereCircoscrizioni = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionParereCircoscrizioni(), true, true, isRequiredDetailSectionParereCircoscrizioni(), parereCircoscrizioniForm);
	}
	
	public boolean showParereCircoscrizioniItem() {
		return showAttributoCustomCablato("COD_CIRCOSCRIZIONE_X_PARERE");
	}
	
	public String getTitleParereCircoscrizioniItem() {
		String label = getLabelAttributoCustomCablato("COD_CIRCOSCRIZIONE_X_PARERE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Parere della/e circoscrizioni"; 
	}
	
	public boolean isRequiredParereCircoscrizioniItem() {
		return showParereCircoscrizioniItem() && getFlgObbligatorioAttributoCustomCablato("COD_CIRCOSCRIZIONE_X_PARERE");
	}	
	
	public String getAltriParamLoadComboParereCircoscrizioniItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("COD_CIRCOSCRIZIONE_X_PARERE");
	}
	
	protected void createParereCircoscrizioniForm() {
		
		parereCircoscrizioniForm = new DynamicForm();
		parereCircoscrizioniForm.setValuesManager(vm);
		parereCircoscrizioniForm.setWidth100();
		parereCircoscrizioniForm.setPadding(5);
		parereCircoscrizioniForm.setWrapItemTitles(false);
		parereCircoscrizioniForm.setNumCols(20);
		parereCircoscrizioniForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		parereCircoscrizioniForm.setTabSet(tabSet);
		parereCircoscrizioniForm.setTabID(_TAB_DATI_SCHEDA_ID);
		parereCircoscrizioniForm.setHeight(1);
		
		listaParereCircoscrizioniItem = new ValoriDizionarioItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboParereCircoscrizioniItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showParereCircoscrizioniItem()) {
					return super.skipValidation(); //TODO Verificare se quando chiamo super.skipValidation() mi torna true quando sono su un altro tab
				}
				return true;
			}
		};
		listaParereCircoscrizioniItem.setName("listaParereCircoscrizioni");
		listaParereCircoscrizioniItem.setStartRow(true);
		listaParereCircoscrizioniItem.setShowTitle(false);
		listaParereCircoscrizioniItem.setColSpan(20);
		if(isRequiredParereCircoscrizioniItem()) {
			listaParereCircoscrizioniItem.setAttribute("obbligatorio", true);
		}
		listaParereCircoscrizioniItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showParereCircoscrizioniItem();
			}
		});
		
		parereCircoscrizioniForm.setFields(listaParereCircoscrizioniItem);			
	}
	
	/******************************************** 
	 * DATI SCHEDA - PARERE DELLA/E COMMISSIONI *
	 ********************************************/
	
	public boolean showDetailSectionParereCommissioni() {
		return showParereCommissioniItem();
	}
	
	public String getTitleDetailSectionParereCommissioni() {
		return getTitleParereCommissioniItem();
	}
	
	public boolean isRequiredDetailSectionParereCommissioni() {
		return isRequiredParereCommissioniItem();
	}		
	
	protected void createDetailSectionParereCommissioni() {
		
		createParereCommissioniForm();
		
		detailSectionParereCommissioni = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionParereCommissioni(), true, true, isRequiredDetailSectionParereCommissioni(), parereCommissioniForm);
	}
	
	public boolean showParereCommissioniItem() {
		return showAttributoCustomCablato("COD_COMMISSIONE_X_PARERE");
	}
	
	public String getTitleParereCommissioniItem() {
		String label = getLabelAttributoCustomCablato("COD_COMMISSIONE_X_PARERE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Parere della/e commissioni"; 
	}
	
	public boolean isRequiredParereCommissioniItem() {
		return showParereCommissioniItem() && getFlgObbligatorioAttributoCustomCablato("COD_COMMISSIONE_X_PARERE");
	}	
	
	
	public String getAltriParamLoadComboParereCommissioniItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("COD_COMMISSIONE_X_PARERE");
	}
	
	protected void createParereCommissioniForm() {
		
		parereCommissioniForm = new DynamicForm();
		parereCommissioniForm.setValuesManager(vm);
		parereCommissioniForm.setWidth100();
		parereCommissioniForm.setPadding(5);
		parereCommissioniForm.setWrapItemTitles(false);
		parereCommissioniForm.setNumCols(20);
		parereCommissioniForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		parereCommissioniForm.setTabSet(tabSet);
		parereCommissioniForm.setTabID(_TAB_DATI_SCHEDA_ID);
		parereCommissioniForm.setHeight(1);
		
		listaParereCommissioniItem = new ValoriDizionarioItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboParereCommissioniItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showParereCommissioniItem()) {
					return super.skipValidation(); //TODO Verificare se quando chiamo super.skipValidation() mi torna true quando sono su un altro tab
				}
				return true;
			}
		};
		listaParereCommissioniItem.setName("listaParereCommissioni");
		listaParereCommissioniItem.setStartRow(true);
		listaParereCommissioniItem.setShowTitle(false);
		listaParereCommissioniItem.setColSpan(20);
		if(isRequiredParereCommissioniItem()) {
			listaParereCommissioniItem.setAttribute("obbligatorio", true);
		}
		listaParereCommissioniItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showParereCommissioniItem();
			}
		});
		
		parereCommissioniForm.setFields(listaParereCommissioniItem);			
	}

	/************************* 
	 * DATI SCHEDA - OGGETTO *
	 *************************/
	
	public boolean showDetailSectionOggetto() {
		return showOggettoHtmlItem();
	}
	
	public boolean isRequiredDetailSectionOggetto() {
		return isRequiredOggettoHtmlItem();
	}
				
	protected void createDetailSectionOggetto() {
		
		createOggettoForm();
		
		detailSectionOggetto = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionOggetto_title(), true, true, isRequiredDetailSectionOggetto(), oggettoForm);
	}
	
	public boolean showOggettoHtmlItem() {
		if(isPresenteAttributoCustomCablato("NASCONDI_OGGETTO")) {
			return false;
		}
		return true;
	}
	
	public boolean isRequiredOggettoHtmlItem() {
		return showOggettoHtmlItem();
	}
	
	public int getLengthOggettoHtmlItem() {
		String length = AurigaLayout.getParametroDB("OGGETTO_ATTI_LENGHT");
		return length != null && !"".equals(length) ? Integer.parseInt(length) : 4000;
	}
	
	public boolean getUpperCaseOggettoHtmlItem() {
		Boolean uppercase = AurigaLayout.getParametroDBAsBoolean("UPPERCASE_OGGETTO_ATTO");
		return uppercase != null && uppercase;
	}
	
	protected void createOggettoForm() {
		
		oggettoForm = new DynamicForm();
		oggettoForm.setValuesManager(vm);
		oggettoForm.setWidth100();
		oggettoForm.setPadding(5);
		oggettoForm.setWrapItemTitles(false);
		oggettoForm.setNumCols(20);
		oggettoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		oggettoForm.setTabSet(tabSet);
		oggettoForm.setTabID(_TAB_DATI_SCHEDA_ID);
		oggettoForm.setHeight(1);
		
		oggettoItem = new HiddenItem("oggetto");
//		oggettoItem.setRequired(true);
//		oggettoItem.setShowTitle(false);
//		oggettoItem.setColSpan(20);
//		oggettoItem.setLength(4000);
//		oggettoItem.setHeight(60);
//		oggettoItem.setWidth("100%");
		
		oggettoHtmlItem = new CKEditorItem("oggettoHtml", getLengthOggettoHtmlItem(), "restricted", 4, -1, "", getUpperCaseOggettoHtmlItem()) {
			
			@Override
			public Boolean validate() {
				if(showOggettoHtmlItem()) {
					return super.validate();
				}
				return true;			
			}
		};
		oggettoHtmlItem.setShowTitle(false);
		oggettoHtmlItem.setColSpan(20);
		oggettoHtmlItem.setWidth("100%");	
		oggettoHtmlItem.setRequired(isRequiredOggettoHtmlItem());
		oggettoHtmlItem.setVisible(showOggettoHtmlItem());	
		
		oggettoForm.setFields(oggettoItem, oggettoHtmlItem);			
	}
	
	/************************************* 
	 * DATI SCHEDA - ATTO DI RIFERIMENTO *
	 *************************************/
	
	public boolean showDetailSectionAttoRiferimento() {
		return showAttributoCustomCablato("ATTO_RIFERIMENTO");
	}
			
	public String getTitleDetailSectionAttoRiferimento() {
		String label = getLabelAttributoCustomCablato("ATTO_RIFERIMENTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Atto di riferimento";
	}
	
	public boolean isRequiredDetailSectionAttoRiferimento() {
		return getFlgObbligatorioAttributoCustomCablato("ATTO_RIFERIMENTO");
	}
	
	public boolean isNotReplicableDetailSectionAttiRiferimento() {
		Integer maxNumValori = getMaxNumValoriAttributoCustomCablato("ATTO_RIFERIMENTO");
		return maxNumValori != null && maxNumValori.intValue() == 1;
	}
	
	protected void createDetailSectionAttoRiferimento() {
		
		createAttoRiferimentoForm();
		
		detailSectionAttoRiferimento = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionAttoRiferimento(), true, true, isRequiredDetailSectionAttoRiferimento(), attoRiferimentoForm);
	}
	
	public boolean showFlgAttoRifASistemaItem() {
		return showAttributoCustomCablato("ATTO_RIF_A_SISTEMA");
	}
	
	public String getTitleFlgAttoRifASistemaItem() {
		String label = getLabelAttributoCustomCablato("ATTO_RIF_A_SISTEMA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Presente a sistema"; 
	}
	
	public boolean isRequiredFlgAttoRifASistemaItem() {
		return showFlgAttoRifASistemaItem() && getFlgObbligatorioAttributoCustomCablato("ATTO_RIF_A_SISTEMA");
	}
	
	public String getDefaultValueFlgAttoRifASistemaItem() {
		return getValoreFissoAttributoCustomCablato("ATTO_RIF_A_SISTEMA");
	}
	
	protected void createAttoRiferimentoForm() {
		
		attoRiferimentoForm = new DynamicForm();
		attoRiferimentoForm.setValuesManager(vm);
		attoRiferimentoForm.setWidth100();
		attoRiferimentoForm.setPadding(5);
		attoRiferimentoForm.setWrapItemTitles(false);
		attoRiferimentoForm.setNumCols(20);
		attoRiferimentoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		attoRiferimentoForm.setTabSet(tabSet);
		attoRiferimentoForm.setTabID(_TAB_DATI_SCHEDA_ID);
		attoRiferimentoForm.setHeight(1);
		
		/*
		CustomValidator attoDeterminaAContrarreRequiredValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(showDetailSectionAttoRiferimento()) {
					if(isRequiredDetailSectionAttoRiferimento() || isDeterminaAggiudicaProceduraGara() || isDeterminaRimodulazioneSpesaGaraAggiudicata()) {					
						String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
						String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
						String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
						String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";														
						return ("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno);
					}
				}
				return true;
			}
		};
		attoDeterminaAContrarreRequiredValidator.setErrorMessage("Campo obbligatorio");
		
		CustomValidator attoDeterminaAContrarreEstremiProtCompletiValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {		
				if(showDetailSectionAttoRiferimento()) {					
					String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
					if("PG".equals(categoriaReg)) {
						String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
						String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";									
						return (!"".equals(numero) && !"".equals(anno)) || ("".equals(numero) && "".equals(anno));
					}
				}
				return true;
			}
		};
		attoDeterminaAContrarreEstremiProtCompletiValidator.setErrorMessage("Estremi di protocollo incompleti: inserire numero e anno");		
		
		CustomValidator attoDeterminaAContrarreEstremiRegRepertorioCompletiValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {				
				if(showDetailSectionAttoRiferimento()) {					
					String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
					if("R".equals(categoriaReg)) {
						String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
						String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
						String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";									
						return (!"".equals(sigla) && !"".equals(numero) && !"".equals(anno)) || ("".equals(sigla) && "".equals(numero) && "".equals(anno));
					}
				}
				return true;
			}
		};
		attoDeterminaAContrarreEstremiRegRepertorioCompletiValidator.setErrorMessage("Estremi di registrazione repertorio incompleti: inserire sigla, numero e anno");		
		
		CustomValidator attoDeterminaAContrarreEsistenteValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(showDetailSectionAttoRiferimento()) {	
					if(showFlgAttoRifASistemaItem() && _FLG_NO.equalsIgnoreCase(getValueAsString("flgAttoRifASistema"))) {
						return true;
					}
					String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
					String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
					String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
					String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";					
					if(("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
						String idUd = idUdAttoDeterminaAContrarreItem.getValue() != null ? String.valueOf(idUdAttoDeterminaAContrarreItem.getValue()) : null;
						return idUd != null && !"".equals(idUd);
					}
				}
				return true;
			}
		};
		attoDeterminaAContrarreEsistenteValidator.setErrorMessage("Atto non presente in Auriga");		
		
		CustomValidator attoDeterminaAContrarreDeliberaUrgenzaValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(showDetailSectionAttoRiferimento() && isRatificaDeliberaUrgenza()) {	
					//TODO devo verificare che sia una delibera di urgenza					
				}
				return true;
			}
		};
		attoDeterminaAContrarreDeliberaUrgenzaValidator.setErrorMessage("Atto non valido: deve essere una delibera di urgenza");		
		
		flgAttoRifASistemaItem = new RadioGroupItem("flgAttoRifASistema", getTitleFlgAttoRifASistemaItem());
		flgAttoRifASistemaItem.setValueMap(_FLG_SI, _FLG_NO);
		flgAttoRifASistemaItem.setDefaultValue(getDefaultValueFlgAttoRifASistemaItem());
		flgAttoRifASistemaItem.setVertical(false);
		flgAttoRifASistemaItem.setWrap(false);
		flgAttoRifASistemaItem.setShowDisabled(false);
		if(isRequiredFlgAttoRifASistemaItem()) {
			flgAttoRifASistemaItem.setAttribute("obbligatorio", true);
		}
		flgAttoRifASistemaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgAttoRifASistemaItem();
			}
		}));
		flgAttoRifASistemaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgAttoRifASistemaItem();
			}
		});	
		flgAttoRifASistemaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				event.getForm().markForRedraw();
			}
		});
		
		idUdAttoDeterminaAContrarreItem = new HiddenItem("idUdAttoDeterminaAContrarre");
		
		categoriaRegAttoDeterminaAContrarreItem = new SelectItem("categoriaRegAttoDeterminaAContrarre");		
		categoriaRegAttoDeterminaAContrarreItem.setValidators(attoDeterminaAContrarreRequiredValidator, attoDeterminaAContrarreEstremiProtCompletiValidator, attoDeterminaAContrarreEstremiRegRepertorioCompletiValidator, attoDeterminaAContrarreEsistenteValidator, attoDeterminaAContrarreDeliberaUrgenzaValidator);
		categoriaRegAttoDeterminaAContrarreItem.setShowTitle(false);
		LinkedHashMap<String, String> categoriaRegAttoDeterminaAContrarreValueMap = new LinkedHashMap<String, String>();
		categoriaRegAttoDeterminaAContrarreValueMap.put("PG", "Prot. Generale");
		categoriaRegAttoDeterminaAContrarreValueMap.put("R", "Repertorio");		
		categoriaRegAttoDeterminaAContrarreItem.setValueMap(categoriaRegAttoDeterminaAContrarreValueMap);
		categoriaRegAttoDeterminaAContrarreItem.setDefaultValue("R");
		categoriaRegAttoDeterminaAContrarreItem.setWidth(150);
		categoriaRegAttoDeterminaAContrarreItem.setWrapTitle(true);
		categoriaRegAttoDeterminaAContrarreItem.setRowSpan(3);
		categoriaRegAttoDeterminaAContrarreItem.setColSpan(1);
		categoriaRegAttoDeterminaAContrarreItem.setAttribute("obbligatorio", true);
		categoriaRegAttoDeterminaAContrarreItem.setAllowEmptyValue(false);
		categoriaRegAttoDeterminaAContrarreItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				event.getForm().markForRedraw();
			}
		});
		categoriaRegAttoDeterminaAContrarreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showFlgAttoRifASistemaItem() && _FLG_NO.equalsIgnoreCase(getValueAsString("flgAttoRifASistema"))) {
					return false;
				}
				return true;
			}
		});
		
		siglaAttoDeterminaAContrarreItem = new ExtendedTextItem("siglaAttoDeterminaAContrarre", I18NUtil.getMessages().nuovaPropostaAtto2_detail_siglaAttoDeterminaAContrarre_title());
		siglaAttoDeterminaAContrarreItem.setWidth(100);
		siglaAttoDeterminaAContrarreItem.setRowSpan(3);
		siglaAttoDeterminaAContrarreItem.setColSpan(1);
		siglaAttoDeterminaAContrarreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUdAttoDeterminaAContrarre", "");
				recuperaIdUdAttoDeterminaAContrarre(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUdAttoDeterminaAContrarre) {
						if(idUdAttoDeterminaAContrarre != null && !"".equals(idUdAttoDeterminaAContrarre)) {
							event.getForm().setValue("idUdAttoDeterminaAContrarre", idUdAttoDeterminaAContrarre);							
						}
						event.getForm().markForRedraw();
					}
					
					@Override
					public void manageError() {
						event.getForm().markForRedraw();
					}
				});
			}
		});
		siglaAttoDeterminaAContrarreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "R".equals(getValueAsString("categoriaRegAttoDeterminaAContrarre"));
			}
		});
		
		numeroAttoDeterminaAContrarreItem = new ExtendedNumericItem("numeroAttoDeterminaAContrarre",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_numeroAttoDeterminaAContrarre_title(), false);
		numeroAttoDeterminaAContrarreItem.setRowSpan(3);
		numeroAttoDeterminaAContrarreItem.setColSpan(1);
		numeroAttoDeterminaAContrarreItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUdAttoDeterminaAContrarre", "");
				recuperaIdUdAttoDeterminaAContrarre(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUdAttoDeterminaAContrarre) {
						if(idUdAttoDeterminaAContrarre != null && !"".equals(idUdAttoDeterminaAContrarre)) {
							event.getForm().setValue("idUdAttoDeterminaAContrarre", idUdAttoDeterminaAContrarre);							
						}
						event.getForm().markForRedraw();
					}
					
					@Override
					public void manageError() {
						event.getForm().markForRedraw();
					}
				});
			}
		});	
		
		annoAttoDeterminaAContrarreItem = new AnnoItem("annoAttoDeterminaAContrarre",
				I18NUtil.getMessages().nuovaPropostaAtto2_detail_annoAttoDeterminaAContrarre_title());
		annoAttoDeterminaAContrarreItem.setRowSpan(3);
		annoAttoDeterminaAContrarreItem.setColSpan(1);
		annoAttoDeterminaAContrarreItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUdAttoDeterminaAContrarre", "");
				recuperaIdUdAttoDeterminaAContrarre(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUdAttoDeterminaAContrarre) {
						if(idUdAttoDeterminaAContrarre != null && !"".equals(idUdAttoDeterminaAContrarre)) {
							event.getForm().setValue("idUdAttoDeterminaAContrarre", idUdAttoDeterminaAContrarre);							
						}
						event.getForm().markForRedraw();
					}
					
					@Override
					public void manageError() {
						event.getForm().markForRedraw();
					}
				});
			}
		});
		
		ImgButtonItem visualizzaDettAttoDeterminaAContrarreButton = new ImgButtonItem("visualizzaDettAttoDeterminaAContrarreButton", "buttons/detail.png", "Visualizza dettaglio atto");
		visualizzaDettAttoDeterminaAContrarreButton.setAlwaysEnabled(true);
		visualizzaDettAttoDeterminaAContrarreButton.setEndRow(false);
		visualizzaDettAttoDeterminaAContrarreButton.setColSpan(1);
		visualizzaDettAttoDeterminaAContrarreButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {	
				String idUd = idUdAttoDeterminaAContrarreItem.getValue() != null ? String.valueOf(idUdAttoDeterminaAContrarreItem.getValue()) : null;				
				String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
				String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
				String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
				String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";									
				Record lRecord = new Record();
				lRecord.setAttribute("idUd", idUd);
				String title = "";
				if("PG".equals(categoriaReg)) {
					title = "Dettaglio atto Prot. " + numero + "/" + anno;
				} else {
					title = "Dettaglio atto " + sigla + " " + numero + "/" + anno;					
				}
				new DettaglioRegProtAssociatoWindow(lRecord, title);
			}
		});		
		visualizzaDettAttoDeterminaAContrarreButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showFlgAttoRifASistemaItem() && _FLG_NO.equalsIgnoreCase(getValueAsString("flgAttoRifASistema"))) {
					return false;
				}
				String idUd = idUdAttoDeterminaAContrarreItem.getValue() != null ? String.valueOf(idUdAttoDeterminaAContrarreItem.getValue()) : null;
				String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
				String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
				String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
				String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";									
				if (("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
					return idUd != null && !"".equals(idUd);	
				}
				return false;
			}
		});
		
		lookupArchivioAttoDeterminaAContrarreButton = new ImgButtonItem("lookupArchivioAttoDeterminaAContrarreButton", "lookup/archivio.png", "Seleziona da archivio");
		lookupArchivioAttoDeterminaAContrarreButton.setEndRow(false);
		lookupArchivioAttoDeterminaAContrarreButton.setColSpan(1);
		lookupArchivioAttoDeterminaAContrarreButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {						
				AttoDeterminaAContrarreLookupArchivio lookupArchivioPopup = new AttoDeterminaAContrarreLookupArchivio(event.getForm().getValuesAsRecord(), "/");
				lookupArchivioPopup.show();
			}
		});	
		lookupArchivioAttoDeterminaAContrarreButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showFlgAttoRifASistemaItem() && _FLG_NO.equalsIgnoreCase(getValueAsString("flgAttoRifASistema"))) {
					return false;
				}
				return true;
			}
		});
		
		
		attoRiferimentoForm.setFields(flgAttoRifASistemaItem, idUdAttoDeterminaAContrarreItem, categoriaRegAttoDeterminaAContrarreItem, siglaAttoDeterminaAContrarreItem, numeroAttoDeterminaAContrarreItem, annoAttoDeterminaAContrarreItem, visualizzaDettAttoDeterminaAContrarreButton, lookupArchivioAttoDeterminaAContrarreButton);
		*/
		
		listaAttiRiferimentoItem = new AttiRiferimentoItem() {
			
			@Override
			public boolean isRequiredAttoRiferimento() {
				return isRequiredDetailSectionAttoRiferimento();
			}

			@Override
			public boolean isFromDeterminaAggiudicaProceduraGara() {
				return isDeterminaAggiudicaProceduraGara();
			}
			
			@Override
			public boolean isFromDeterminaRimodulazioneSpesaGaraAggiudicata() {
				return isDeterminaRimodulazioneSpesaGaraAggiudicata();
			}
			
			@Override
			public boolean isFromRatificaDeliberaUrgenza() {
				return isRatificaDeliberaUrgenza();
			}
						
			@Override
			public boolean showFlgPresentaASistemaItem() {
				return showFlgAttoRifASistemaItem();
			}
			
			@Override
			public String getTitleFlgPresentaASistemaItem() {
				return getTitleFlgAttoRifASistemaItem();
			}
			
			@Override
			public String getDefaultValueFlgPresentaASistemaItem() {
				return getDefaultValueFlgAttoRifASistemaItem();
			}
			
			@Override
			public boolean isRequiredFlgPresentaASistemaItem() {
				return isRequiredFlgAttoRifASistemaItem();
			}
			
			@Override
			public boolean isEditabileFlgPresentaASistemaItem() {
				return getFlgEditabileAttributoCustomCablato("ATTO_RIF_A_SISTEMA");
			}
			
			@Override
			public boolean skipValidation() {
				if(showDetailSectionAttoRiferimento()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
			
		};
		listaAttiRiferimentoItem.setName("listaAttiRiferimento");
		listaAttiRiferimentoItem.setShowTitle(false);
		listaAttiRiferimentoItem.setColSpan(20);
		if(isNotReplicableDetailSectionAttiRiferimento()) {
			listaAttiRiferimentoItem.setNotReplicable(true);
		}
		if(isRequiredDetailSectionAttoRiferimento()) {
			listaAttiRiferimentoItem.setAttribute("obbligatorio", true);
		}
		listaAttiRiferimentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionAttoRiferimento();
			}
		});	
		
		attoRiferimentoForm.setFields(listaAttiRiferimentoItem);
	}
	
	/******************************************** 
	 * DATI SCHEDA - SPECIFICITA' PROVVEDIMENTO *
	 ********************************************/
	
	public boolean showDetailSectionCaratteristicheProvvedimento() {
		return showOggLiquidazioneItem() ||
			   showDataScadenzaLiquidazioneItem() ||
			   showUrgenzaLiquidazioneItem() || 
			   showFlgLiqXUffCassaItem() ||
			   showImportoAnticipoCassaItem() ||
			   showDataDecorrenzaContrattoItem() ||
			   showAnniDurataContrattoItem() ||
			   showFlgAffidamentoItem() ||
			   showFlgDeterminaAContrarreTramiteProceduraGaraItem() || 
			   showFlgDeterminaAContrarreTramiteProceduraGaraItem() ||
			   showFlgDeterminaAggiudicaProceduraGaraItem() ||
			   showFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem() ||
			   showFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem() ||
			   showFlgDeterminaRiaccertamentoItem() ||
			   showFlgDeterminaAccertRadiazItem() ||
			   showFlgDeterminaVariazBilItem() ||
			   showFlgVantaggiEconomiciItem() ||
			   showFlgDecretoReggioItem() ||
			   showFlgAvvocaturaItem() ||
			   showFlgSpesaItem() ||
			   showFlgCorteContiItem() ||
			   showFlgLiqContestualeImpegnoItem() || 
			   showFlgLiqContestualeAltriAspettiRilContItem() ||
			   showFlgCompQuadroFinRagDecItem() ||
			   showFlgNuoviImpAccItem() ||
			   showFlgImpSuAnnoCorrenteItem() ||
			   showFlgInsMovARagioneriaItem() ||
			   showFlgPresaVisioneContabilitaItem() ||	
			   showFlgSpesaCorrenteItem() ||
			   showFlgImpegniCorrenteGiaValidatiItem() ||
			   showFlgSpesaContoCapitaleItem() ||
			   showFlgImpegniContoCapitaleGiaRilasciatiItem() ||
			   showFlgSoloSubImpSubCronoItem() ||
			   showTipoAttoInDeliberaPEGItem() ||
			   showTipoAffidamentoItem() ||
			   showNormRifAffidamentoItem() ||
			   showRespAffidamentoItem() ||
			   showMateriaTipoAttoItem() ||
			   showFlgFondiEuropeiPONItem() ||
			   showFlgFondiPRUItem() ||
			   showFlgVistoPar117_2013Item() ||
			   showFlgNotificaDaMessiItem() ||		
			   showFlgLLPPItem() ||
			   showFlgBeniServiziItem() ||
			   showFlgPrivacyItem() ||
			   showFlgDatiProtettiTipo1Item() ||
			   showFlgDatiProtettiTipo2Item() ||
			   showFlgDatiProtettiTipo3Item() ||
			   showFlgDatiProtettiTipo4Item();		
	}
	
	protected void createDetailSectionCaratteristicheProvvedimento() {
		
		createCaratteristicheProvvedimentoForm();
		
		detailSectionCaratteristicheProvvedimento = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionCaratteristicheProvvedimento_title(), true, true, false, caratteristicheProvvedimentoForm);
	}
	
	public boolean showOggLiquidazioneItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_OGG_LIQUIDAZIONE");
	}
	
	public String getTitleOggLiquidazioneItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_OGG_LIQUIDAZIONE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Liquidazione di";
	}
	
	public boolean isRequiredOggLiquidazioneItem() {
		return showOggLiquidazioneItem() && getFlgObbligatorioAttributoCustomCablato("TASK_RESULT_2_OGG_LIQUIDAZIONE");
	}
		
	public String getDefaultValueOggLiquidazioneItem() {
		return getValoreFissoAttributoCustomCablato("TASK_RESULT_2_OGG_LIQUIDAZIONE");
	}
	
	public String getAltriParamLoadComboOggLiquidazioneItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("TASK_RESULT_2_OGG_LIQUIDAZIONE");
	}
	
	public boolean showDataScadenzaLiquidazioneItem() {
		return showAttributoCustomCablato("SCADENZA_LIQUIDAZIONE");
	}
	
	public String getTitleDataScadenzaLiquidazioneItem() {
		String label = getLabelAttributoCustomCablato("SCADENZA_LIQUIDAZIONE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Scadenza liquidazione";
	}
	
	public boolean isRequiredDataScadenzaLiquidazioneItem() {
		return showDataScadenzaLiquidazioneItem() && getFlgObbligatorioAttributoCustomCablato("SCADENZA_LIQUIDAZIONE");
	}
		
	public String getDefaultValueDataScadenzaLiquidazioneItem() {
		return getValoreFissoAttributoCustomCablato("SCADENZA_LIQUIDAZIONE");
	}
	
	public boolean showUrgenzaLiquidazioneItem() {
		return showAttributoCustomCablato("URGENZA_LIQUIDAZIONE");
	}
	
	public String getTitleUrgenzaLiquidazioneItem() {
		String label = getLabelAttributoCustomCablato("URGENZA_LIQUIDAZIONE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Urgenza liquidazione";
	}
	
	public boolean isRequiredUrgenzaLiquidazioneItem() {
		return showUrgenzaLiquidazioneItem() && getFlgObbligatorioAttributoCustomCablato("URGENZA_LIQUIDAZIONE");
	}
		
	public String getDefaultValueUrgenzaLiquidazioneItem() {
		return getValoreFissoAttributoCustomCablato("URGENZA_LIQUIDAZIONE");
	}
	
	public String getAltriParamLoadComboUrgenzaLiquidazioneItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("URGENZA_LIQUIDAZIONE");
	}
	
	public boolean showFlgLiqXUffCassaItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_FLG_LIQ_X_UFF_CASSA");
	}
	
	public String getTitleFlgLiqXUffCassaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FLG_LIQ_X_UFF_CASSA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "per ufficio cassa";
	}
		
	public boolean getDefaultValueAsBooleanFlgLiqXUffCassaItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_FLG_LIQ_X_UFF_CASSA");
	}
	
	public boolean showImportoAnticipoCassaItem() {
		boolean isLiqXUffCassa = showFlgLiqXUffCassaItem() && getValueAsBoolean("flgLiqXUffCassa");		
		return isLiqXUffCassa && showAttributoCustomCablato("IMPORTO_ANTICIPO_CASSA");
	}
	
	public String getTitleImportoAnticipoCassaItem() {
		String label = getLabelAttributoCustomCablato("IMPORTO_ANTICIPO_CASSA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Anticipo cassa (&euro;)";
	}
	
	public boolean isRequiredImportoAnticipoCassaItem() {
		return showImportoAnticipoCassaItem() && getFlgObbligatorioAttributoCustomCablato("IMPORTO_ANTICIPO_CASSA");
	}
		
	public String getDefaultValueImportoAnticipoCassaItem() {
		return getValoreFissoAttributoCustomCablato("IMPORTO_ANTICIPO_CASSA");
	}
	
	public boolean showDataDecorrenzaContrattoItem() {
		if(showOggLiquidazioneItem() && !getValueAsString("oggLiquidazione").toUpperCase().contains("CONTRATTO")) {
			return false;
		}
		return showAttributoCustomCablato("DECORRENZA_CONTRATTO");
	}
	
	public String getTitleDataDecorrenzaContrattoItem() {
		String label = getLabelAttributoCustomCablato("DECORRENZA_CONTRATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Decorrenza contratto";
	}
	
	public boolean isRequiredDataDecorrenzaContrattoItem() {
		return showDataDecorrenzaContrattoItem() && getFlgObbligatorioAttributoCustomCablato("DECORRENZA_CONTRATTO");
	}
		
	public String getDefaultValueDataDecorrenzaContrattoItem() {
		return getValoreFissoAttributoCustomCablato("DECORRENZA_CONTRATTO");
	}
	
	public boolean showAnniDurataContrattoItem() {
		if(showOggLiquidazioneItem() && !getValueAsString("oggLiquidazione").toUpperCase().contains("CONTRATTO")) {
			return false;
		}
		return showAttributoCustomCablato("ANNI_DURATA_CONTRATTO");
	}
	
	public String getTitleAnniDurataContrattoItem() {
		String label = getLabelAttributoCustomCablato("ANNI_DURATA_CONTRATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Anni durata contratto";
	}
	
	public boolean isRequiredAnniDurataContrattoItem() {
		return showAnniDurataContrattoItem() && getFlgObbligatorioAttributoCustomCablato("ANNI_DURATA_CONTRATTO");
	}
		
	public String getDefaultValueAnniDurataContrattoItem() {
		return getValoreFissoAttributoCustomCablato("ANNI_DURATA_CONTRATTO");
	}
	
	public boolean showFlgAffidamentoItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_FLG_AFFIDAMENTO");
	}
		
	public String getTitleFlgAffidamentoItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FLG_AFFIDAMENTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "affidamento";	
	}
	
	public boolean getDefaultValueAsBooleanFlgAffidamentoItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_FLG_AFFIDAMENTO");
	}
	
	public boolean showFlgDeterminaAContrarreTramiteProceduraGaraItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_DET_CONTR_CON_GARA");
	}
		
	public String getTitleFlgDeterminaAContrarreTramiteProceduraGaraItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_CONTR_CON_GARA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "determina a contrarre tramite procedura di gara"; 		
	}
	
	public boolean showFlgDeterminaAggiudicaProceduraGaraItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_DET_AGGIUDICA_GARA");
	}
		
	public String getTitleFlgDeterminaAggiudicaProceduraGaraItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_AGGIUDICA_GARA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "determina di aggiudica di procedura di gara";		
	}
	
	public boolean showFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_DET_RIMOD_SPESA_GARA_AGGIUD");
	}
		
	public String getTitleFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_RIMOD_SPESA_GARA_AGGIUD");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "determina di rimodulazione spesa gara aggiudicata"; 	
	}
	
	public boolean showFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_DET_PERSONALE");
	}
		
	public String getTitleFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_PERSONALE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "determina spesa di personale";		
	}
	
	public boolean showFlgDeterminaRiaccertamentoItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_DET_RIACCERT");
	}
		
	public String getTitleFlgDeterminaRiaccertamentoItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_RIACCERT");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "determina di riaccertamento";	
	}	
	
	public boolean showFlgDeterminaAccertRadiazItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_DET_ACCERT_RADIAZ");
	}
		
	public String getTitleFlgDeterminaAccertRadiazItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_ACCERT_RADIAZ");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "solo accertamento o radiazione";	
	}
	
	public boolean getDefaultValueAsBooleanFlgDeterminaAccertRadiazItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_DET_ACCERT_RADIAZ");
	}		
	
	public boolean showFlgDeterminaVariazBilItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_DET_VARIAZ_BIL");
	}
		
	public String getTitleFlgDeterminaVariazBilItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_VARIAZ_BIL");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "variazioni bilancio";	
	}
	
	public boolean getDefaultValueAsBooleanFlgDeterminaVariazBilItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_DET_VARIAZ_BIL");
	}		
	
	public boolean showFlgVantaggiEconomiciItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_FLG_VANTAGGI_ECONOMICI");
	}
		
	public String getTitleFlgVantaggiEconomiciItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FLG_VANTAGGI_ECONOMICI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "vantaggi economici";	
	}
	
	public boolean getDefaultValueAsBooleanFlgVantaggiEconomiciItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_FLG_VANTAGGI_ECONOMICI");
	}
	
	public boolean showFlgDecretoReggioItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_DECRETO_REGGIO");
	}
		
	public String getTitleFlgDecretoReggioItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DECRETO_REGGIO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "decreto REGGIO";
	}	
	
	public boolean getDefaultValueAsBooleanFlgDecretoReggioItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_DECRETO_REGGIO");
	}
	
	public boolean showFlgAvvocaturaItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_AVVOCATURA");
	}
		
	public String getTitleFlgAvvocaturaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_AVVOCATURA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "AVVOCATURA";
	}	
	
	public boolean getDefaultValueAsBooleanFlgAvvocaturaItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_AVVOCATURA");
	}
	
	public boolean showFlgSpesaItem() {
		String[] flgSpesaValueMap = getValoriPossibiliFlgSpesaItem();
		if(flgSpesaValueMap != null && flgSpesaValueMap.length == 1) {
			return false; // se la mappa contiene un solo valore nascondo il radio e forzo quel valore
		}
		return showAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA");
	}
		
	public String getTitleFlgSpesaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Determina con spesa";
	}
	
	public boolean isRequiredFlgSpesaItem() {
		return showFlgSpesaItem() /*&& getFlgObbligatorioAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA")*/;
	}
	
	public String[] getValoriPossibiliFlgSpesaItem() {
		String[] valoriPossibili = getValoriPossibiliAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA");
		if(valoriPossibili != null && valoriPossibili.length > 0) {
			return valoriPossibili;			
		} else {
			return new String[] {_FLG_SI, getFLG_SI_SENZA_VLD_RIL_IMP(), _FLG_NO};
		}
	}
	
	public boolean hasValoreFlgSpesaSiSenzaVldRilImp() {
		HashSet<String> flgSpesaValueSet = new HashSet<String>();
		String[] valoriPossibiliFlgSpesa = getValoriPossibiliFlgSpesaItem();
		if(valoriPossibiliFlgSpesa != null && valoriPossibiliFlgSpesa.length > 0) {
			for(String key : valoriPossibiliFlgSpesa) {
				flgSpesaValueSet.add(key);
			}
		}
		return flgSpesaValueSet.contains(getFLG_SI_SENZA_VLD_RIL_IMP());
	}
	
	public String getDefaultValueFlgSpesaItem() {
		return getValoreFissoAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA");
	}
	
	public boolean showFlgCorteContiItem() {
		return (isDeterminaConSpesa() || isDeterminaConSpesaSenzaImpegni()) && showAttributoCustomCablato("TASK_RESULT_2_FLG_CORTE_CONTI");
	}
		
	public String getTitleFlgCorteContiItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FLG_CORTE_CONTI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "per Corte dei Conti";
	}	
	
	public boolean getDefaultValueAsBooleanFlgCorteContiItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_FLG_CORTE_CONTI");
	}
	
	public boolean showFlgLiqContestualeImpegnoItem() {
		return isDeterminaConSpesa() && showAttributoCustomCablato("FLG_LIQ_CONTESTUALE_IMPEGNO");
	}
		
	public String getTitleFlgLiqContestualeImpegnoItem() {
		String label = getLabelAttributoCustomCablato("FLG_LIQ_CONTESTUALE_IMPEGNO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "liquidazione contestuale all'assunzione di impegno o sub-impegno";
	}	
	
	public boolean getDefaultValueAsBooleanFlgLiqContestualeImpegnoItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("FLG_LIQ_CONTESTUALE_IMPEGNO");
	}
	
	public boolean showFlgLiqContestualeAltriAspettiRilContItem() {
		return isDeterminaConSpesa() && showAttributoCustomCablato("FLG_LIQ_CONTESTUALE_ALTRI_ASPETTI_RIL_CONT");
	}
		
	public String getTitleFlgLiqContestualeAltriAspettiRilContItem() {
		String label = getLabelAttributoCustomCablato("FLG_LIQ_CONTESTUALE_ALTRI_ASPETTI_RIL_CONT");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "liquidazione contestuale ad aspetti di rilevanza contabile diversi dall'assunzione di impegno o sub-impegno";
	}	
	
	public boolean getDefaultValueAsBooleanFlgLiqContestualeAltriAspettiRilContItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("FLG_LIQ_CONTESTUALE_ALTRI_ASPETTI_RIL_CONT");
	}
	
	public boolean showFlgCompQuadroFinRagDecItem() {
		return isDeterminaConSpesa() && showAttributoCustomCablato("TASK_RESULT_2_COMP_QUADRO_FIN_RAG_DEC");
	}
		
	public String getTitleFlgCompQuadroFinRagDecItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_COMP_QUADRO_FIN_RAG_DEC");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "compilazione dettaglio quadro finanziario demandata a Ragioneria decentrata";
	}	
	
	public boolean getDefaultValueAsBooleanFlgCompQuadroFinRagDecItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_COMP_QUADRO_FIN_RAG_DEC");
	}
	
	public boolean showFlgNuoviImpAccItem() {
		return isDeterminaConSpesa() && showAttributoCustomCablato("TASK_RESULT_2_FLG_NUOVI_IMP_ACC");
	}
		
	public String getTitleFlgNuoviImpAccItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FLG_NUOVI_IMP_ACC");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "solo nuovi impegni e/o accertamenti";	
	}
	
	public boolean getDefaultValueAsBooleanFlgNuoviImpAccItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_FLG_NUOVI_IMP_ACC");
	}
	
	public boolean showFlgImpSuAnnoCorrenteItem() {
		return isDeterminaConSpesa() && showAttributoCustomCablato("TASK_RESULT_2_FLG_IMPEGNI_SU_ANNO_CORRENTE");
	}
		
	public String getTitleFlgImpSuAnnoCorrenteItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FLG_IMPEGNI_SU_ANNO_CORRENTE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "impegni su anno corrente";	
	}
	
	public boolean getDefaultValueAsBooleanFlgImpSuAnnoCorrenteItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_FLG_IMPEGNI_SU_ANNO_CORRENTE");
	}
	
	public boolean showFlgInsMovARagioneriaItem() {
		return isDeterminaConSpesa() && showAttributoCustomCablato("TASK_RESULT_2_INS_MOV_A_RAGIONERIA");
	}
		
	public String getTitleFlgInsMovARagioneriaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_INS_MOV_A_RAGIONERIA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "inserimento movimenti contabili demandato a Ragioneria";	
	}
	
	public boolean getDefaultValueAsBooleanFlgInsMovARagioneriaItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_INS_MOV_A_RAGIONERIA");
	}
	
	public boolean showFlgPresaVisioneContabilitaItem() {
		return isDeterminaSenzaSpesa() && showAttributoCustomCablato("TASK_RESULT_2_RICH_PRESA_VIS_CONTABILITA");
	}
		
	public String getTitleFlgPresaVisioneContabilitaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_RICH_PRESA_VIS_CONTABILITA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "richiesta presa visione contabilità";	
	}
	
	public boolean showFlgSpesaCorrenteItem() {
		return isDeterminaConSpesa() && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA_CORRENTE");
	}
		
	public String getTitleFlgSpesaCorrenteItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA_CORRENTE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "spesa su parte corrente";	
	}	  
	
	public boolean showFlgImpegniCorrenteGiaValidatiItem() {
		return isDeterminaConSpesaCorrente() && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_IMP_CORR_VALID");
	}
		
	public String getTitleFlgImpegniCorrenteGiaValidatiItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_CON_IMP_CORR_VALID");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "impegni su parte corrente tutti già validati";		
	}
	
	public boolean showFlgSpesaContoCapitaleItem() {
		return isDeterminaConSpesa() && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA_CONTO_CAP");
	}
		
	public String getTitleFlgSpesaContoCapitaleItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA_CONTO_CAP");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "spesa in conto capitale";
	}
	
	public boolean showFlgImpegniContoCapitaleGiaRilasciatiItem() {
		return isDeterminaConSpesaContoCapitale() && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_IMP_CCAP_RIL");
	}
		
	public String getTitleFlgImpegniContoCapitaleGiaRilasciatiItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_CON_IMP_CCAP_RIL");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "impegni in conto capitale tutti già rilasciati";	
	}
	
	public boolean showFlgSoloSubImpSubCronoItem() {
		return isDeterminaConSpesa() && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_SUB");
	}
		
	public String getTitleFlgSoloSubImpSubCronoItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_CON_SUB");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "solo sub-impegni o sub-crono";
	}
	
	public boolean showTipoAttoInDeliberaPEGItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG") && (isDeterminaConSpesa() || isDeterminaConSpesaSenzaImpegni());
	}
		
	public String getTitleTipoAttoInDeliberaPEGItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Fattispecie indicata in delibera PEG";
	}
	
	public boolean isRequiredTipoAttoInDeliberaPEGItem() {
		return showTipoAttoInDeliberaPEGItem() && getFlgObbligatorioAttributoCustomCablato("TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG");
	}
		
	public String getAltriParamLoadComboTipoAttoInDeliberaPEGItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG");
	}
	
	public String[] getValoriPossibiliTipoAttoInDeliberaPEGItem() {
		return getValoriPossibiliAttributoCustomCablato("TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG");
	}
	
	public boolean showTipoAffidamentoItem() {
		if(showFlgAffidamentoItem() && !getValueAsBoolean("flgAffidamento")) {
			return false;
		}
		return showAttributoCustomCablato("TIPO_AFFIDAMENTO");
	}
	
	public String getTitleTipoAffidamentoItem() {
		String label = getLabelAttributoCustomCablato("TIPO_AFFIDAMENTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Tipo di affidamento";
	}
	
	public boolean isRequiredTipoAffidamentoItem() {
		return showTipoAffidamentoItem() && getFlgObbligatorioAttributoCustomCablato("TIPO_AFFIDAMENTO");
	}
	
	public String getAltriParamLoadComboTipoAffidamentoItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("TIPO_AFFIDAMENTO");
	}
	
	public boolean showNormRifAffidamentoItem() {
		if(showFlgAffidamentoItem() && !getValueAsBoolean("flgAffidamento")) {
			return false;
		}
		return showAttributoCustomCablato("NORM_RIF_AFFIDAMENTO");
	}
	
	public String getTitleNormRifAffidamentoItem() {
		String label = getLabelAttributoCustomCablato("NORM_RIF_AFFIDAMENTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Normativa di rif.";
	}
	
	public boolean isRequiredNormRifAffidamentoItem() {
		return showNormRifAffidamentoItem() && getFlgObbligatorioAttributoCustomCablato("NORM_RIF_AFFIDAMENTO");
	}
	
	public String getAltriParamLoadComboNormRifAffidamentoItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("NORM_RIF_AFFIDAMENTO");
	}
	   
	public boolean showRespAffidamentoItem() {
		if(showFlgAffidamentoItem() && !getValueAsBoolean("flgAffidamento")) {
			return false;
		}
		return showAttributoCustomCablato("RESP_AFFIDAMENTO");
	}
	
	public String getTitleRespAffidamentoItem() {
		String label = getLabelAttributoCustomCablato("RESP_AFFIDAMENTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Resp. proc.";
	}
	
	public boolean isRequiredRespAffidamentoItem() {
		return showRespAffidamentoItem() && getFlgObbligatorioAttributoCustomCablato("RESP_AFFIDAMENTO");
	}
	
	public boolean showMateriaTipoAttoItem() {
		return showAttributoCustomCablato("MATERIA_NATURA_ATTO");
	}
	
	public String getTitleMateriaTipoAttoItem() {
		String label = getLabelAttributoCustomCablato("MATERIA_NATURA_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Materia/tipo atto (ex codice atto)";
	}
	
	public boolean isRequiredMateriaTipoAttoItem() {
		return showMateriaTipoAttoItem() && getFlgObbligatorioAttributoCustomCablato("MATERIA_NATURA_ATTO");
	}
	
	public String getAltriParamLoadComboMateriaTipoAttoItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("MATERIA_NATURA_ATTO");
	}
	
	public boolean showFlgFondiEuropeiPONItem() {
		return (isDeterminaConSpesa() || isDeterminaConSpesaSenzaImpegni()) && showAttributoCustomCablato("TASK_RESULT_2_FONDI_EUROPEI_PON");
	}
		
	public String getTitleFlgFondiEuropeiPONItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FONDI_EUROPEI_PON");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "fondi europei/PON";
	}
	
	public boolean getDefaultValueAsBooleanFlgFondiEuropeiPONItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_FONDI_EUROPEI_PON");
	}
	
	public boolean showFlgFondiPRUItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_FONDI_PRU");
	}
		
	public String getTitleFlgFondiPRUItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FONDI_PRU");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "fondi PRU";
	}
	
	public boolean getDefaultValueAsBooleanFlgFondiPRUItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_FONDI_PRU");
	}
	
	public boolean showFlgVistoPar117_2013Item() {
		return showAttributoCustomCablato("TASK_RESULT_2_VISTO_PAR_117_2013");
	}
		
	public String getTitleFlgVistoPar117_2013Item() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_VISTO_PAR_117_2013");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "visto parere favorevole ai sensi ai sensi della DD 117/2013 ssmm";
	}
	
	public boolean getDefaultValueAsBooleanFlgVistoPar117_2013Item() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_VISTO_PAR_117_2013");
	}
	
	public boolean showFlgNotificaDaMessiItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_NOTIFICA_DA_MESSI");
	}
		
	public String getTitleFlgNotificaDaMessiItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_NOTIFICA_DA_MESSI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "notifica tramite messi";
	}
	
	public boolean getDefaultValueAsBooleanFlgNotificaDaMessiItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_NOTIFICA_DA_MESSI");
	}
	
	public boolean showFlgLLPPItem() {
		return showAttributoCustomCablato("FLG_LLPP");
	}
	
	public String getTitleFlgLLPPItem() {
		String label = getLabelAttributoCustomCablato("FLG_LLPP");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "LL.PP.";
	}
	
	public boolean isRequiredFlgLLPPItem() {
		return showFlgLLPPItem() && getFlgObbligatorioAttributoCustomCablato("FLG_LLPP");
	}
	
	public String getDefaultValueFlgLLPPItem() {
		return getValoreFissoAttributoCustomCablato("FLG_LLPP");
	}
	
	public boolean showAnnoProgettoLLPPItem() {
		return isLLPP() && showAttributoCustomCablato("ANNO_PROGETTO_LLPP");
	}
	
	public String getTitleAnnoProgettoLLPPItem() {
		String label = getLabelAttributoCustomCablato("ANNO_PROGETTO_LLPP");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Anno progetto LL.PP.";
	}
	
	public boolean isRequiredAnnoProgettoLLPPItem() {
		return showAnnoProgettoLLPPItem() && getFlgObbligatorioAttributoCustomCablato("ANNO_PROGETTO_LLPP");
	}
	
	public boolean showNumProgettoLLPPItem() {
		return isLLPP() && showAttributoCustomCablato("NRO_PROGETTO_LLPP");
	}
	
	public String getTitleNumProgettoLLPPItem() {
		String label = getLabelAttributoCustomCablato("NRO_PROGETTO_LLPP");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "N° progetto LL.PP.";
	}
	
	public boolean isRequiredNumProgettoLLPPItem() {
		return showNumProgettoLLPPItem() && getFlgObbligatorioAttributoCustomCablato("NRO_PROGETTO_LLPP");
	}
	
	public boolean showFlgBeniServiziItem() {
		return showAttributoCustomCablato("FLG_BENI_SERVIZI");
	}
	
	public String getTitleFlgBeniServiziItem() {
		String label = getLabelAttributoCustomCablato("FLG_BENI_SERVIZI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Beni e Servizi";
	}
	
	public boolean isRequiredFlgBeniServiziItem() {
		return showFlgBeniServiziItem() && getFlgObbligatorioAttributoCustomCablato("FLG_BENI_SERVIZI");
	}
	
	public String getDefaultValueFlgBeniServiziItem() {
		return getValoreFissoAttributoCustomCablato("FLG_BENI_SERVIZI");
	}
	
	public boolean showAnnoProgettoBeniServiziItem() {
		return isBeniServizi() && showAttributoCustomCablato("ANNO_PROGETTO_BENI_SERVIZI");
	}
	
	public String getTitleAnnoProgettoBeniServiziItem() {
		String label = getLabelAttributoCustomCablato("ANNO_PROGETTO_BENI_SERVIZI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Anno progetto Beni e Servizi";
	}
	
	public boolean isRequiredAnnoProgettoBeniServiziItem() {
		return showAnnoProgettoBeniServiziItem() && getFlgObbligatorioAttributoCustomCablato("ANNO_PROGETTO_BENI_SERVIZI");
	}
	
	public boolean showNumProgettoBeniServiziItem() {
		return isBeniServizi() && showAttributoCustomCablato("NRO_PROGETTO_BENI_SERVIZI");
	}
	
	public String getTitleNumProgettoBeniServiziItem() {
		String label = getLabelAttributoCustomCablato("NRO_PROGETTO_BENI_SERVIZI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "N° progetto Beni e Servizi";
	}
	
	public boolean isRequiredNumProgettoBeniServiziItem() {
		return showNumProgettoBeniServiziItem() && getFlgObbligatorioAttributoCustomCablato("NRO_PROGETTO_BENI_SERVIZI");
	}
	
	public boolean showFlgPrivacyItem() {
		return showAttributoCustomCablato("FLG_ATTO_CON_DATI_RISERVATI");
	}
	
	public String getTitleFlgPrivacyItem() {
		String label = getLabelAttributoCustomCablato("FLG_ATTO_CON_DATI_RISERVATI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Possibile presenza di dati coperti da privacy o riservati";
	}
	
	public boolean isRequiredFlgPrivacyItem() {
		return showFlgPrivacyItem() && getFlgObbligatorioAttributoCustomCablato("FLG_ATTO_CON_DATI_RISERVATI");
	}
	
	public String getDefaultValueFlgPrivacyItem() {
		return getValoreFissoAttributoCustomCablato("FLG_ATTO_CON_DATI_RISERVATI");
	}
	
	public boolean showFlgDatiProtettiTipo1Item() {
		return (!showFlgPrivacyItem() || isDatiRiservati()) && showAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_1");
	}
		
	public String getTitleFlgDatiProtettiTipo1Item() {
		String label = getLabelAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_1");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "dati protetti tipo 1";
	}
	
	public boolean getDefaultValueAsBooleanFlgDatiProtettiTipo1Item() {
		return getValoreFissoAsBooleanAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_1");
	}
	
	public boolean showFlgDatiProtettiTipo2Item() {
		return (!showFlgPrivacyItem() || isDatiRiservati()) && showAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_2");
	}
		
	public String getTitleFlgDatiProtettiTipo2Item() {
		String label = getLabelAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_2");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "dati protetti tipo 2";
	}
	
	public boolean getDefaultValueAsBooleanFlgDatiProtettiTipo2Item() {
		return getValoreFissoAsBooleanAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_2");
	}
		
	public boolean showFlgDatiProtettiTipo3Item() {
		return (!showFlgPrivacyItem() || isDatiRiservati()) && showAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_3");
	}
		
	public String getTitleFlgDatiProtettiTipo3Item() {
		String label = getLabelAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_3");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "dati protetti tipo 3";
	}
	
	public boolean getDefaultValueAsBooleanFlgDatiProtettiTipo3Item() {
		return getValoreFissoAsBooleanAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_3");
	}
		
	public boolean showFlgDatiProtettiTipo4Item() {
		return (!showFlgPrivacyItem() || isDatiRiservati()) && showAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_4");
	}
		
	public String getTitleFlgDatiProtettiTipo4Item() {
		String label = getLabelAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_4");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "dati protetti tipo 4";
	}
	
	public boolean getDefaultValueAsBooleanFlgDatiProtettiTipo4Item() {
		return getValoreFissoAsBooleanAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_4");
	}
	
//	public boolean isIndirizzoObbligatorioInPropostaAtto() {
////		return getValoreFissoAsBooleanAttributoCustomCablato("");
//		return false;
//	}
//	
//	public boolean isCivicoObbligatorioInPropostaAtto() {
////		return getValoreFissoAsBooleanAttributoCustomCablato("");
//		return false;
//	}
	
	protected void createCaratteristicheProvvedimentoForm() {
		
		caratteristicheProvvedimentoForm = new DynamicForm();
		caratteristicheProvvedimentoForm.setValuesManager(vm);
		caratteristicheProvvedimentoForm.setWidth100();
		caratteristicheProvvedimentoForm.setPadding(5);
		caratteristicheProvvedimentoForm.setWrapItemTitles(false);
		caratteristicheProvvedimentoForm.setNumCols(20);
		caratteristicheProvvedimentoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		caratteristicheProvvedimentoForm.setTabSet(tabSet);
		caratteristicheProvvedimentoForm.setTabID(_TAB_DATI_SCHEDA_ID);
		caratteristicheProvvedimentoForm.setHeight(1);
		
		GWTRestDataSource oggLiquidazioneDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		oggLiquidazioneDS.addParam("altriParamLoadCombo", getAltriParamLoadComboOggLiquidazioneItem());
		 		
		oggLiquidazioneItem = new SelectItem("oggLiquidazione", getTitleOggLiquidazioneItem());
//		oggLiquidazioneItem.setTitleOrientation(TitleOrientation.TOP);		
		oggLiquidazioneItem.setWidth(500);
		oggLiquidazioneItem.setColSpan(17);		
		oggLiquidazioneItem.setStartRow(true);
		oggLiquidazioneItem.setValueField("key");
		oggLiquidazioneItem.setDisplayField("value");
		oggLiquidazioneItem.setOptionDataSource(oggLiquidazioneDS);	
		oggLiquidazioneItem.setClearable(true);		
		oggLiquidazioneItem.setDefaultValue(getDefaultValueOggLiquidazioneItem());
		oggLiquidazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showOggLiquidazioneItem();
			}
		});
		if(isRequiredOggLiquidazioneItem()) {
			oggLiquidazioneItem.setAttribute("obbligatorio", true);
		}
		oggLiquidazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredOggLiquidazioneItem();
			}
		}));
		oggLiquidazioneItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm.markForRedraw();
			}
		});
		
		final String titleShowInfoOggLiquidazioneButton = "Informazioni sull'uso del campo \"" + getTitleOggLiquidazioneItem() + "\"";
		
		showInfoOggLiquidazioneButton = new ImgButtonItem("showInfoOggLiquidazioneButton", "about.png", titleShowInfoOggLiquidazioneButton);
		showInfoOggLiquidazioneButton.setAlwaysEnabled(true);
		showInfoOggLiquidazioneButton.setShowTitle(false);
		showInfoOggLiquidazioneButton.setWidth(16);
		showInfoOggLiquidazioneButton.setColSpan(1);
		showInfoOggLiquidazioneButton.setValueIconSize(32);
		showInfoOggLiquidazioneButton.setPrompt(titleShowInfoOggLiquidazioneButton);
		showInfoOggLiquidazioneButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
				lNuovaPropostaAtto2CompletaDataSource.performCustomOperation("getInfoOggLiquidazione", getRecordToSave(), new DSCallback() {							
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record recordPreview = response.getData()[0];
							PreviewWindow lPreviewWindow = new PreviewWindow(recordPreview.getAttribute("uri"), false, new InfoFileRecord(recordPreview.getAttributeAsRecord("infoFile")), "FileToExtractBean",	recordPreview.getAttribute("nomeFile"));lPreviewWindow.setShowTitle(true);
							lPreviewWindow.setTitle(titleShowInfoOggLiquidazioneButton);
							lPreviewWindow.show();
						} 				
					}
				}, new DSRequest());
			}
		});
		showInfoOggLiquidazioneButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String uriInfoOggLiquidazione = AurigaLayout.getParametroDB("URI_INFO_OGG_LIQUIDAZIONE");
				return showOggLiquidazioneItem() && uriInfoOggLiquidazione != null && !"".equals(uriInfoOggLiquidazione);
			}
		});
		
		SpacerItem endRowScadenzaItem = new SpacerItem();
		endRowScadenzaItem.setColSpan(1);
		endRowScadenzaItem.setEndRow(true);
		endRowScadenzaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDataScadenzaLiquidazioneItem() || showUrgenzaLiquidazioneItem();
			}
		});
		
		dataScadenzaLiquidazioneItem = new DateItem("dataScadenzaLiquidazione", getTitleDataScadenzaLiquidazioneItem());
		dataScadenzaLiquidazioneItem.setColSpan(1);
		dataScadenzaLiquidazioneItem.setDefaultValue(getDefaultValueDataScadenzaLiquidazioneItem());
		dataScadenzaLiquidazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDataScadenzaLiquidazioneItem();
			}
		});
		if(isRequiredDataScadenzaLiquidazioneItem()) {
			dataScadenzaLiquidazioneItem.setAttribute("obbligatorio", true);
		}
		dataScadenzaLiquidazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredDataScadenzaLiquidazioneItem();
			}
		}));
		
		GWTRestDataSource urgenzaLiquidazioneDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		urgenzaLiquidazioneDS.addParam("altriParamLoadCombo", getAltriParamLoadComboUrgenzaLiquidazioneItem());
		 		
		urgenzaLiquidazioneItem = new SelectItem("urgenzaLiquidazione", getTitleUrgenzaLiquidazioneItem());
//		urgenzaLiquidazioneItem.setTitleOrientation(TitleOrientation.TOP);		
		urgenzaLiquidazioneItem.setWidth(169);
		urgenzaLiquidazioneItem.setColSpan(1);		
		urgenzaLiquidazioneItem.setValueField("key");
		urgenzaLiquidazioneItem.setDisplayField("value");
		urgenzaLiquidazioneItem.setOptionDataSource(urgenzaLiquidazioneDS);	
		urgenzaLiquidazioneItem.setClearable(true);		
		urgenzaLiquidazioneItem.setDefaultValue(getDefaultValueUrgenzaLiquidazioneItem());
		urgenzaLiquidazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showUrgenzaLiquidazioneItem();
			}
		});
		if(isRequiredUrgenzaLiquidazioneItem()) {
			urgenzaLiquidazioneItem.setAttribute("obbligatorio", true);
		}
		urgenzaLiquidazioneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredUrgenzaLiquidazioneItem();
			}
		}));
		
		SpacerItem spacerFlgLiqXUffCassaItem = new SpacerItem();
		spacerFlgLiqXUffCassaItem.setColSpan(1);
		spacerFlgLiqXUffCassaItem.setStartRow(true);
		spacerFlgLiqXUffCassaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgLiqXUffCassaItem();
			}
		});
		
		flgLiqXUffCassaItem = new CheckboxItem("flgLiqXUffCassa", getTitleFlgLiqXUffCassaItem());
		flgLiqXUffCassaItem.setDefaultValue(getDefaultValueAsBooleanFlgLiqXUffCassaItem());
		flgLiqXUffCassaItem.setColSpan(1);
		flgLiqXUffCassaItem.setWidth("*");			
		flgLiqXUffCassaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgLiqXUffCassaItem();
			}
		});
		flgLiqXUffCassaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm.markForRedraw();
			}
		});
		
		RegExpValidator importoPrecisionValidator = new RegExpValidator();
		importoPrecisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$");
		importoPrecisionValidator.setErrorMessage("Valore non valido o superato il limite di 2 cifre decimali");
		
		CustomValidator importoMaggioreDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(showImportoAnticipoCassaItem()) {					
					// se l'importo è vuoto, ma non è obbligatorio, la validazione deve andare a buon fine
					if(!isRequiredImportoAnticipoCassaItem() && (value == null || "".equals(value))) {
						return true;
					}
					String pattern = "#,##0.00";
					double importo = 0;
					if(value != null && !"".equals(value)) {
						importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
					}
					return importo > 0;
				}
				return true;
			}
		};
		importoMaggioreDiZeroValidator.setErrorMessage("Valore non valido: l'importo deve essere maggiore di zero");
		
		importoAnticipoCassaItem = new ExtendedNumericItem("importoAnticipoCassa", getTitleImportoAnticipoCassaItem()); 
		importoAnticipoCassaItem.setKeyPressFilter("[0-9.,]");
		importoAnticipoCassaItem.setColSpan(1);
		importoAnticipoCassaItem.setWidth(150);
		importoAnticipoCassaItem.setDefaultValue(getDefaultValueImportoAnticipoCassaItem());
		importoAnticipoCassaItem.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				importoAnticipoCassaItem.setValue(NumberFormatUtility.getFormattedValue((String) event.getValue()));
			}
		});		
		importoAnticipoCassaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				importoAnticipoCassaItem.setValue(NumberFormatUtility.getFormattedValue(importoAnticipoCassaItem.getValueAsString()));
				if(isRequiredImportoAnticipoCassaItem()) {
					importoAnticipoCassaItem.setAttribute("obbligatorio", true);
					importoAnticipoCassaItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleImportoAnticipoCassaItem()));
				} else {
					importoAnticipoCassaItem.setAttribute("obbligatorio", false);
					importoAnticipoCassaItem.setTitle(getTitleImportoAnticipoCassaItem());
				}					
				return showImportoAnticipoCassaItem();
			}
		});
		importoAnticipoCassaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredImportoAnticipoCassaItem();
			}
		}), importoPrecisionValidator, importoMaggioreDiZeroValidator);
		
		SpacerItem endRowContrattoItem = new SpacerItem();
		endRowContrattoItem.setColSpan(1);
		endRowContrattoItem.setEndRow(true);
		endRowContrattoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDataDecorrenzaContrattoItem() || showAnniDurataContrattoItem();
			}
		});
		
		dataDecorrenzaContrattoItem = new DateItem("dataDecorrenzaContratto", getTitleDataDecorrenzaContrattoItem()); 
		dataDecorrenzaContrattoItem.setColSpan(1);
		dataDecorrenzaContrattoItem.setDefaultValue(getDefaultValueDataDecorrenzaContrattoItem());
		dataDecorrenzaContrattoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredDataDecorrenzaContrattoItem()) {
					dataDecorrenzaContrattoItem.setAttribute("obbligatorio", true);
					dataDecorrenzaContrattoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleDataDecorrenzaContrattoItem()));
				} else {
					dataDecorrenzaContrattoItem.setAttribute("obbligatorio", false);
					dataDecorrenzaContrattoItem.setTitle(getTitleDataDecorrenzaContrattoItem());
				}				
				return showDataDecorrenzaContrattoItem();
			}
		});
		dataDecorrenzaContrattoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredDataDecorrenzaContrattoItem();
			}
		}));
		
		anniDurataContrattoItem = new NumericItem("anniDurataContratto", getTitleAnniDurataContrattoItem(), false); 
		anniDurataContrattoItem.setColSpan(1);
		anniDurataContrattoItem.setWidth(70);
		anniDurataContrattoItem.setDefaultValue(getDefaultValueAnniDurataContrattoItem());
		anniDurataContrattoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredAnniDurataContrattoItem()) {
					anniDurataContrattoItem.setAttribute("obbligatorio", true);
					anniDurataContrattoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleAnniDurataContrattoItem()));
				} else {
					anniDurataContrattoItem.setAttribute("obbligatorio", false);
					anniDurataContrattoItem.setTitle(getTitleAnniDurataContrattoItem());
				}	
				return showAnniDurataContrattoItem();
			}
		});
		anniDurataContrattoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredAnniDurataContrattoItem();
			}
		}));
		
		SpacerItem spacerFlgAffidamentoItem = new SpacerItem();
		spacerFlgAffidamentoItem.setColSpan(1);
		spacerFlgAffidamentoItem.setStartRow(true);
		spacerFlgAffidamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgAffidamentoItem();
			}
		});
		
		flgAffidamentoItem = new CheckboxItem("flgAffidamento", getTitleFlgAffidamentoItem());
		flgAffidamentoItem.setDefaultValue(getDefaultValueAsBooleanFlgAffidamentoItem());
		flgAffidamentoItem.setColSpan(18);
		flgAffidamentoItem.setWidth("*");
//		flgAffidamentoItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(final ChangeEvent event) {
//				if(!isAvvioPropostaAtto()) {
//					if (event.getValue() != null && (Boolean) event.getValue()) {					
//						if(isDeterminaPersonale()) {
//							toSaveAndReloadTask = true;							
//						}
//					}
//				}
//			}
//			
//		});
		flgAffidamentoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);	
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);
					flgDeterminaRiaccertamentoItem.setValue(false);					
					flgDeterminaAccertRadiazItem.setValue(false);	
					flgDeterminaVariazBilItem.setValue(false);	
					flgVantaggiEconomiciItem.setValue(false);			
					flgDecretoReggioItem.setValue(false);	
					flgAvvocaturaItem.setValue(false);
					if(showFlgSpesaItem() && !isDeterminaConSpesa()) {
						flgSpesaItem.setValue(_FLG_SI);
						flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
					}
				}				
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();
				showHideSections();
//				if(toSaveAndReloadTask) {
//					toSaveAndReloadTask = false;
//					saveAndReloadTask();
//				}
			}
		});
		flgAffidamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgAffidamentoItem();
			}
		});
		
		SpacerItem spacerFlgDeterminaAContrarreTramiteProceduraGaraItem = new SpacerItem();
		spacerFlgDeterminaAContrarreTramiteProceduraGaraItem.setColSpan(1);
		spacerFlgDeterminaAContrarreTramiteProceduraGaraItem.setStartRow(true);
		spacerFlgDeterminaAContrarreTramiteProceduraGaraItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDeterminaAContrarreTramiteProceduraGaraItem();
			}
		});
		
		flgDeterminaAContrarreTramiteProceduraGaraItem = new CheckboxItem("flgDeterminaAContrarreTramiteProceduraGara", getTitleFlgDeterminaAContrarreTramiteProceduraGaraItem());
		flgDeterminaAContrarreTramiteProceduraGaraItem.setDefaultValue(false);
		flgDeterminaAContrarreTramiteProceduraGaraItem.setColSpan(18);
		flgDeterminaAContrarreTramiteProceduraGaraItem.setWidth("*");
//		flgDeterminaAContrarreTramiteProceduraGaraItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(final ChangeEvent event) {
//				if(!isAvvioPropostaAtto()) {
//					if (event.getValue() != null && (Boolean) event.getValue()) {					
//						if(isDeterminaPersonale()) {
//							toSaveAndReloadTask = true;							
//						}
//					}
//				}
//			}
//			
//		});
		flgDeterminaAContrarreTramiteProceduraGaraItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgAffidamentoItem.setValue(false);	
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);
					flgDeterminaRiaccertamentoItem.setValue(false);				
					flgDeterminaAccertRadiazItem.setValue(false);	
					flgDeterminaVariazBilItem.setValue(false);	
					flgVantaggiEconomiciItem.setValue(false);			
					flgDecretoReggioItem.setValue(false);	
					flgAvvocaturaItem.setValue(false);								
					if(showFlgSpesaItem() && !isDeterminaConSpesa()) {
						flgSpesaItem.setValue(_FLG_SI);
						flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
					}
				}
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();
				showHideSections();
//				if(toSaveAndReloadTask) {
//					toSaveAndReloadTask = false;
//					saveAndReloadTask();
//				}
			}
		});
		flgDeterminaAContrarreTramiteProceduraGaraItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgDeterminaAContrarreTramiteProceduraGaraItem();
			}
		});
		
		SpacerItem spacerFlgDeterminaAggiudicaProceduraGaraItem = new SpacerItem();
		spacerFlgDeterminaAggiudicaProceduraGaraItem.setColSpan(1);
		spacerFlgDeterminaAggiudicaProceduraGaraItem.setStartRow(true);
		spacerFlgDeterminaAggiudicaProceduraGaraItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDeterminaAggiudicaProceduraGaraItem();
			}
		});
		
		flgDeterminaAggiudicaProceduraGaraItem = new CheckboxItem("flgDeterminaAggiudicaProceduraGara", getTitleFlgDeterminaAggiudicaProceduraGaraItem());
		flgDeterminaAggiudicaProceduraGaraItem.setDefaultValue(false);
		flgDeterminaAggiudicaProceduraGaraItem.setColSpan(18);
		flgDeterminaAggiudicaProceduraGaraItem.setWidth("*");
//		flgDeterminaAggiudicaProceduraGaraItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(final ChangeEvent event) {
//				if(!isAvvioPropostaAtto()) {
//					if (event.getValue() != null && (Boolean) event.getValue()) {					
//						if(isDeterminaPersonale()) {
//							toSaveAndReloadTask = true;							
//						}
//					}
//				}
//			}
//			
//		});
		flgDeterminaAggiudicaProceduraGaraItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgAffidamentoItem.setValue(false);	
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);
					flgDeterminaRiaccertamentoItem.setValue(false);				
					flgDeterminaAccertRadiazItem.setValue(false);	
					flgDeterminaVariazBilItem.setValue(false);	
					flgVantaggiEconomiciItem.setValue(false);		
					flgDecretoReggioItem.setValue(false);	
					flgAvvocaturaItem.setValue(false);							
//					if(showFlgSpesaItem()) {
//						flgSpesaItem.clearValue();
//						flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
//					}
				} 
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();	
				showHideSections();
//				if(toSaveAndReloadTask) {
//					toSaveAndReloadTask = false;
//					saveAndReloadTask();
//				}
			}
		});
		flgDeterminaAggiudicaProceduraGaraItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgDeterminaAggiudicaProceduraGaraItem();
			}
		});

		SpacerItem spacerFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem = new SpacerItem();
		spacerFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setColSpan(1);
		spacerFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setStartRow(true);
		spacerFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem();
			}
		});
		
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem = new CheckboxItem("flgDeterminaRimodulazioneSpesaGaraAggiudicata", getTitleFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem());
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setDefaultValue(false);
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setColSpan(18);
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setWidth("*");
//		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(final ChangeEvent event) {
//				if(!isAvvioPropostaAtto()) {
//					if (event.getValue() != null && (Boolean) event.getValue()) {					
//						if(isDeterminaPersonale()) {
//							toSaveAndReloadTask = true;							
//						}
//					}
//				}
//			}
//			
//		});
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgAffidamentoItem.setValue(false);	
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);
					flgDeterminaRiaccertamentoItem.setValue(false);					
					flgDeterminaAccertRadiazItem.setValue(false);	
					flgDeterminaVariazBilItem.setValue(false);	
					flgVantaggiEconomiciItem.setValue(false);		
					flgDecretoReggioItem.setValue(false);	
					flgAvvocaturaItem.setValue(false);							
					if(showFlgSpesaItem() && !isDeterminaConSpesa()) {
						flgSpesaItem.setValue(_FLG_SI);
						flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
					}
				}
				redrawTabForms(_TAB_DATI_SCHEDA_ID);
				enableDisableTabs();			
				showHideSections();
//				if(toSaveAndReloadTask) {
//					toSaveAndReloadTask = false;
//					saveAndReloadTask();
//				}
			}
		});
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem();
			}
		});		
		
		SpacerItem spacerFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem = new SpacerItem();
		spacerFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setColSpan(1);
		spacerFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setStartRow(true);
		spacerFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem();
			}
		});
		
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem = new CheckboxItem("flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro", getTitleFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem());
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setDefaultValue(false);
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setColSpan(18);
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setWidth("*");
//		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(final ChangeEvent event) {
//				if(!isAvvioPropostaAtto()) {
//					toSaveAndReloadTask = true;					
//				}
//			}
//			
//		});
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgAffidamentoItem.setValue(false);	
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);
					flgDeterminaRiaccertamentoItem.setValue(false);			
					flgDeterminaAccertRadiazItem.setValue(false);	
					flgDeterminaVariazBilItem.setValue(false);	
					flgVantaggiEconomiciItem.setValue(false);			
					flgDecretoReggioItem.setValue(false);	
					flgAvvocaturaItem.setValue(false);									
					if(showFlgSpesaItem() && !isDeterminaConSpesa()) {
						flgSpesaItem.setValue(_FLG_SI);
						flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
					}
				}
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();
				showHideSections();
//				if(toSaveAndReloadTask) {
//					toSaveAndReloadTask = false;
//					saveAndReloadTask();
//				}
			}
		});
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem();
			}
		});			
		
		SpacerItem spacerFlgDeterminaRiaccertamentoItem = new SpacerItem();
		spacerFlgDeterminaRiaccertamentoItem.setColSpan(1);
		spacerFlgDeterminaRiaccertamentoItem.setStartRow(true);
		spacerFlgDeterminaRiaccertamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDeterminaRiaccertamentoItem();
			}
		});
		
		flgDeterminaRiaccertamentoItem = new CheckboxItem("flgDeterminaRiaccertamento", getTitleFlgDeterminaRiaccertamentoItem());
		flgDeterminaRiaccertamentoItem.setDefaultValue(false);
		flgDeterminaRiaccertamentoItem.setColSpan(18);
		flgDeterminaRiaccertamentoItem.setWidth("*");
//		flgDeterminaRiaccertamentoItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(final ChangeEvent event) {
//				if(!isAvvioPropostaAtto()) {
//					if (event.getValue() != null && (Boolean) event.getValue()) {					
//						if(isDeterminaPersonale()) {
//							toSaveAndReloadTask = true;							
//						}
//					}
//				}
//			}
//			
//		});
		flgDeterminaRiaccertamentoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgAffidamentoItem.setValue(false);	
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);					
					flgVantaggiEconomiciItem.setValue(false);		
					flgDecretoReggioItem.setValue(false);	
					flgAvvocaturaItem.setValue(false);							
					if(showFlgSpesaItem()) {
//						flgSpesaItem.clearValue();
//						flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
						if(hasValoreFlgSpesaSiSenzaVldRilImp() && !isDeterminaConSpesaSenzaImpegni()) {
							flgSpesaItem.setValue(getFLG_SI_SENZA_VLD_RIL_IMP());						
							flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
						} else if(!isDeterminaConSpesa()) {
							flgSpesaItem.setValue(_FLG_SI);						
							flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
						}				
					}
				} 
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();	
				showHideSections();
//				if(toSaveAndReloadTask) {
//					toSaveAndReloadTask = false;
//					saveAndReloadTask();
//				}
			}
		});
		flgDeterminaRiaccertamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgDeterminaRiaccertamentoItem();
			}
		});		
		
		SpacerItem spacerFlgDeterminaAccertRadiazItem = new SpacerItem();
		spacerFlgDeterminaAccertRadiazItem.setColSpan(1);
		spacerFlgDeterminaAccertRadiazItem.setStartRow(true);
		spacerFlgDeterminaAccertRadiazItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDeterminaAccertRadiazItem();
			}
		});
		
		flgDeterminaAccertRadiazItem = new CheckboxItem("flgDeterminaAccertRadiaz", getTitleFlgDeterminaAccertRadiazItem());
		flgDeterminaAccertRadiazItem.setDefaultValue(getDefaultValueAsBooleanFlgDeterminaAccertRadiazItem());		
		flgDeterminaAccertRadiazItem.setColSpan(18);
		flgDeterminaAccertRadiazItem.setWidth("*");
//		flgDeterminaAccertRadiazItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(final ChangeEvent event) {
//				if(!isAvvioPropostaAtto()) {
//					if (event.getValue() != null && (Boolean) event.getValue()) {					
//						if(isDeterminaPersonale()) {
//							toSaveAndReloadTask = true;							
//						}
//					}
//				}
//			}
//			
//		});
		flgDeterminaAccertRadiazItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgAffidamentoItem.setValue(false);	
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);	
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);
					flgDeterminaRiaccertamentoItem.setValue(false);				
					flgDeterminaVariazBilItem.setValue(false);	
					flgVantaggiEconomiciItem.setValue(false);		
					flgDecretoReggioItem.setValue(false);	
					flgAvvocaturaItem.setValue(false);
					if(showFlgSpesaItem() && !isDeterminaConSpesa()) {
						flgSpesaItem.setValue(_FLG_SI);
						flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
					}
				}
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();
				showHideSections();
//				if(toSaveAndReloadTask) {
//					toSaveAndReloadTask = false;
//					saveAndReloadTask();
//				}
			}
		});
		flgDeterminaAccertRadiazItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgDeterminaAccertRadiazItem();
			}
		});	
		
		SpacerItem spacerFlgDeterminaVariazBilItem = new SpacerItem();
		spacerFlgDeterminaVariazBilItem.setColSpan(1);
		spacerFlgDeterminaVariazBilItem.setStartRow(true);
		spacerFlgDeterminaVariazBilItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDeterminaVariazBilItem();
			}
		});
		
		flgDeterminaVariazBilItem = new CheckboxItem("flgDeterminaVariazBil", getTitleFlgDeterminaVariazBilItem());
		flgDeterminaVariazBilItem.setDefaultValue(getDefaultValueAsBooleanFlgDeterminaVariazBilItem());		
		flgDeterminaVariazBilItem.setColSpan(18);
		flgDeterminaVariazBilItem.setWidth("*");
//		flgDeterminaVariazBilItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(final ChangeEvent event) {
//				if(!isAvvioPropostaAtto()) {
//					if (event.getValue() != null && (Boolean) event.getValue()) {					
//						if(isDeterminaPersonale()) {
//							toSaveAndReloadTask = true;							
//						}
//					}
//				}
//			}
//			
//		});
		flgDeterminaVariazBilItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgAffidamentoItem.setValue(false);	
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);	
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);
					flgDeterminaRiaccertamentoItem.setValue(false);				
					flgDeterminaAccertRadiazItem.setValue(false);	
					flgVantaggiEconomiciItem.setValue(false);		
					flgDecretoReggioItem.setValue(false);	
					flgAvvocaturaItem.setValue(false);
					if(showFlgSpesaItem()) {
						if(hasValoreFlgSpesaSiSenzaVldRilImp() && !isDeterminaConSpesaSenzaImpegni()) {
							flgSpesaItem.setValue(getFLG_SI_SENZA_VLD_RIL_IMP());						
							flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
						} else if(!isDeterminaConSpesa()) {
							flgSpesaItem.setValue(_FLG_SI);						
							flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
						}				
					}
				}
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();
				showHideSections();
//				if(toSaveAndReloadTask) {
//					toSaveAndReloadTask = false;
//					saveAndReloadTask();
//				}
			}
		});
		flgDeterminaVariazBilItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgDeterminaVariazBilItem();
			}
		});	
		
		SpacerItem spacerFlgVantaggiEconomiciItem = new SpacerItem();
		spacerFlgVantaggiEconomiciItem.setColSpan(1);
		spacerFlgVantaggiEconomiciItem.setStartRow(true);
		spacerFlgVantaggiEconomiciItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgVantaggiEconomiciItem();
			}
		});
		
		flgVantaggiEconomiciItem = new CheckboxItem("flgVantaggiEconomici", getTitleFlgVantaggiEconomiciItem());
		flgVantaggiEconomiciItem.setDefaultValue(getDefaultValueAsBooleanFlgVantaggiEconomiciItem());
		flgVantaggiEconomiciItem.setColSpan(18);
		flgVantaggiEconomiciItem.setWidth("*");
//		flgVantaggiEconomiciItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(final ChangeEvent event) {
//				if(!isAvvioPropostaAtto()) {
//					if (event.getValue() != null && (Boolean) event.getValue()) {					
//						if(isDeterminaPersonale()) {
//							toSaveAndReloadTask = true;							
//						}
//					}
//				}
//			}
//			
//		});
		flgVantaggiEconomiciItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgAffidamentoItem.setValue(false);	
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);	
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);
					flgDeterminaRiaccertamentoItem.setValue(false);				
					flgDeterminaAccertRadiazItem.setValue(false);	
					flgDeterminaVariazBilItem.setValue(false);						
					flgDecretoReggioItem.setValue(false);	
					flgAvvocaturaItem.setValue(false);
					if(showFlgSpesaItem() && !isDeterminaSenzaSpesa()) {
						flgSpesaItem.setValue(_FLG_NO);
						flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
					}
				}
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();
				showHideSections();
//				if(toSaveAndReloadTask) {
//					toSaveAndReloadTask = false;
//					saveAndReloadTask();
//				}
			}
		});
		flgVantaggiEconomiciItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgVantaggiEconomiciItem();
			}
		});	
		
		SpacerItem spacerFlgDecretoReggioItem = new SpacerItem();
		spacerFlgDecretoReggioItem.setColSpan(1);
		spacerFlgDecretoReggioItem.setStartRow(true);
		spacerFlgDecretoReggioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDecretoReggioItem();
			}
		});
		
		flgDecretoReggioItem = new CheckboxItem("flgDecretoReggio", getTitleFlgDecretoReggioItem());
		flgDecretoReggioItem.setDefaultValue(getDefaultValueAsBooleanFlgDecretoReggioItem());
		flgDecretoReggioItem.setColSpan(18);
		flgDecretoReggioItem.setWidth("*");			
//		flgDecretoReggioItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(final ChangeEvent event) {
//				if(!isAvvioPropostaAtto()) {
//					if (event.getValue() != null && (Boolean) event.getValue()) {					
//						if(isDeterminaPersonale()) {
//							toSaveAndReloadTask = true;							
//						}
//					}
//				}
//			}
//			
//		});
		flgDecretoReggioItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgAffidamentoItem.setValue(false);	
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);	
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);
					flgDeterminaRiaccertamentoItem.setValue(false);				
					flgDeterminaAccertRadiazItem.setValue(false);	
					flgDeterminaVariazBilItem.setValue(false);	
					flgVantaggiEconomiciItem.setValue(false);	
					flgAvvocaturaItem.setValue(false);	
					if(showFlgSpesaItem() && !isDeterminaConSpesa()) {
						flgSpesaItem.setValue(_FLG_SI);
						flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
					}
				}
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();
				showHideSections();
//				if(toSaveAndReloadTask) {
//					toSaveAndReloadTask = false;
//					saveAndReloadTask();
//				}
			}
		});		
		flgDecretoReggioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgDecretoReggioItem();
			}
		});
		
		SpacerItem spacerFlgAvvocaturaItem = new SpacerItem();
		spacerFlgAvvocaturaItem.setColSpan(1);
		spacerFlgAvvocaturaItem.setStartRow(true);
		spacerFlgAvvocaturaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgAvvocaturaItem();
			}
		});
		
		flgAvvocaturaItem = new CheckboxItem("flgAvvocatura", getTitleFlgAvvocaturaItem());
		flgAvvocaturaItem.setDefaultValue(getDefaultValueAsBooleanFlgAvvocaturaItem());
		flgAvvocaturaItem.setColSpan(18);
		flgAvvocaturaItem.setWidth("*");	
//		flgAvvocaturaItem.addChangeHandler(new ChangeHandler() {
//
//			@Override
//			public void onChange(final ChangeEvent event) {
//				if(!isAvvioPropostaAtto()) {
//					if (event.getValue() != null && (Boolean) event.getValue()) {					
//						if(isDeterminaPersonale()) {
//							toSaveAndReloadTask = true;							
//						}
//					}
//				}
//			}
//			
//		});
		flgAvvocaturaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgAffidamentoItem.setValue(false);	
					flgDeterminaAContrarreTramiteProceduraGaraItem.setValue(false);	
					flgDeterminaAggiudicaProceduraGaraItem.setValue(false);
					flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setValue(false);
					flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setValue(false);
					flgDeterminaRiaccertamentoItem.setValue(false);				
					flgDeterminaAccertRadiazItem.setValue(false);	
					flgDeterminaVariazBilItem.setValue(false);	
					flgVantaggiEconomiciItem.setValue(false);	
					flgDecretoReggioItem.setValue(false);	
					if(showFlgSpesaItem() && !isDeterminaSenzaSpesa()) {
						flgSpesaItem.setValue(_FLG_NO);
						flgSpesaItem.fireEvent(new ChangedEvent(flgSpesaItem.getJsObj()));
					}
				}
				redrawTabForms(_TAB_DATI_SCHEDA_ID);				
				enableDisableTabs();
				showHideSections();
//				if(toSaveAndReloadTask) {
//					toSaveAndReloadTask = false;
//					saveAndReloadTask();
//				}
			}
		});
		flgAvvocaturaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgAvvocaturaItem();
			}
		});
				
		flgSpesaItem = new RadioGroupItem("flgSpesa", getTitleFlgSpesaItem());
		flgSpesaItem.setStartRow(true);
		flgSpesaItem.setColSpan(18);
		String[] flgSpesaValueMap = getValoriPossibiliFlgSpesaItem();
		flgSpesaItem.setValueMap(flgSpesaValueMap);					
		if(flgSpesaValueMap != null && flgSpesaValueMap.length == 1) {
			flgSpesaItem.setDefaultValue(flgSpesaValueMap[0]);
		} else {
			flgSpesaItem.setDefaultValue(getDefaultValueFlgSpesaItem());
		}
		flgSpesaItem.setVertical(false);
		flgSpesaItem.setWrap(false);
		flgSpesaItem.setShowDisabled(false);
		if(isRequiredFlgSpesaItem()) {
			flgSpesaItem.setAttribute("obbligatorio", true);
		}
		flgSpesaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgSpesaItem();
			}
		}));
		flgSpesaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showFlgSpesaItem()) {
					if(isAttoMeroIndirizzo() || isVantaggiEconomici() || isAvvocatura()) {
						item.setDisabled(true);
						item.setValue(_FLG_NO);		
			 		} else if(isAffidamento() || isDeterminaAContrarreTramiteProceduraGara() || isDeterminaRimodulazioneSpesaGaraAggiudicata() || isDeterminaPersonale() || isDecretoReggio()) {
						item.setDisabled(true);
						item.setValue(_FLG_SI);		
					} else if(isDeterminaRiaccertamento()) {
						item.setDisabled(true);
						item.setValue(hasValoreFlgSpesaSiSenzaVldRilImp() ? getFLG_SI_SENZA_VLD_RIL_IMP() : _FLG_SI);									
					} else if(isDeterminaAccertRadiaz()) {
						item.setDisabled(false);
						item.setValue(_FLG_SI);	
					} else if(isDeterminaVariazBil()) {
						item.setDisabled(false);
						item.setValue(hasValoreFlgSpesaSiSenzaVldRilImp() ? getFLG_SI_SENZA_VLD_RIL_IMP() : _FLG_SI);									
					} else {
						item.setDisabled(false);					
					}
					return true;
				}
				return false;
			}
		});
		flgSpesaItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				if(event.getValue() != null && _FLG_NO.equals(event.getValue())) {	
					if(isDeterminaAccertRadiaz()) {
						event.cancel();
						AurigaLayout.addMessage(new MessageBean("Opzione non selezionabile per " + getTitleFlgDeterminaAccertRadiazItem(), "", MessageType.ERROR));
					} else if(isDeterminaVariazBil()) {
						event.cancel();		
						AurigaLayout.addMessage(new MessageBean("Opzione non selezionabile per " + getTitleFlgDeterminaVariazBilItem(), "", MessageType.ERROR));
					}			
				}
			}
		});
		flgSpesaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {	
				if(showFlgAdottanteUnicoRespPEGItem()) {
					if(isDeterminaConSpesa()) {
						flgAdottanteUnicoRespPEGItem.setValue(true);
						flgAdottanteUnicoRespPEGItem.setDisabled(false);
					} else if(isDeterminaConSpesaSenzaImpegni()) {						
						flgAdottanteUnicoRespPEGItem.setValue(true);
						flgAdottanteUnicoRespPEGItem.setDisabled(true);
					} 
				} else if(isDeterminaSenzaSpesa()) {
					flgAdottanteUnicoRespPEGItem.setValue(false);
					flgAdottanteUnicoRespPEGItem.setDisabled(false);
				}				
				setUfficioDefinizioneSpesaFromUoProponente();				
				redrawTabForms(_TAB_DATI_SCHEDA_ID);
				enableDisableTabs();
				showHideSections();
			}
		});
		
		SpacerItem spacerFlgCorteContiItem = new SpacerItem();
		spacerFlgCorteContiItem.setColSpan(1);
		spacerFlgCorteContiItem.setStartRow(true);
		spacerFlgCorteContiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgCorteContiItem();
			}
		});
		
		flgCorteContiItem = new CheckboxItem("flgCorteConti", getTitleFlgCorteContiItem());
		flgCorteContiItem.setDefaultValue(getDefaultValueAsBooleanFlgCorteContiItem());
		flgCorteContiItem.setColSpan(18);
		flgCorteContiItem.setWidth("*");	
		flgCorteContiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgCorteContiItem();
			}
		});
		
		SpacerItem spacerFlgLiqContestualeImpegnoItem = new SpacerItem();
		spacerFlgLiqContestualeImpegnoItem.setColSpan(1);
		spacerFlgLiqContestualeImpegnoItem.setStartRow(true);
		spacerFlgLiqContestualeImpegnoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgLiqContestualeImpegnoItem();
			}
		});
		
		flgLiqContestualeImpegnoItem = new CheckboxItem("flgLiqContestualeImpegno", getTitleFlgLiqContestualeImpegnoItem());
		flgLiqContestualeImpegnoItem.setDefaultValue(getDefaultValueAsBooleanFlgLiqContestualeImpegnoItem());
		flgLiqContestualeImpegnoItem.setColSpan(18);
		flgLiqContestualeImpegnoItem.setWidth("*");	
		flgLiqContestualeImpegnoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgLiqContestualeAltriAspettiRilContItem.setValue(false);							
				} 
			}
		});
		flgLiqContestualeImpegnoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgLiqContestualeImpegnoItem();
			}
		});
		
		SpacerItem spacerFlgLiqContestualeAltriAspettiRilContItem = new SpacerItem();
		spacerFlgLiqContestualeAltriAspettiRilContItem.setColSpan(1);
		spacerFlgLiqContestualeAltriAspettiRilContItem.setStartRow(true);
		spacerFlgLiqContestualeAltriAspettiRilContItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgLiqContestualeAltriAspettiRilContItem();
			}
		});
		
		flgLiqContestualeAltriAspettiRilContItem = new CheckboxItem("flgLiqContestualeAltriAspettiRilCont", getTitleFlgLiqContestualeAltriAspettiRilContItem());
		flgLiqContestualeAltriAspettiRilContItem.setDefaultValue(getDefaultValueAsBooleanFlgLiqContestualeAltriAspettiRilContItem());
		flgLiqContestualeAltriAspettiRilContItem.setColSpan(18);
		flgLiqContestualeAltriAspettiRilContItem.setWidth("*");	
		flgLiqContestualeAltriAspettiRilContItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgLiqContestualeImpegnoItem.setValue(false);							
				} 
			}
		});
		flgLiqContestualeAltriAspettiRilContItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgLiqContestualeAltriAspettiRilContItem();
			}
		});
		
		SpacerItem spacerFlgCompQuadroFinRagDecItem = new SpacerItem();
		spacerFlgCompQuadroFinRagDecItem.setColSpan(1);
		spacerFlgCompQuadroFinRagDecItem.setStartRow(true);
		spacerFlgCompQuadroFinRagDecItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgCompQuadroFinRagDecItem();
			}
		});
		
		flgCompQuadroFinRagDecItem = new CheckboxItem("flgCompQuadroFinRagDec", getTitleFlgCompQuadroFinRagDecItem());
		flgCompQuadroFinRagDecItem.setDefaultValue(getDefaultValueAsBooleanFlgCompQuadroFinRagDecItem());
		flgCompQuadroFinRagDecItem.setColSpan(18);
		flgCompQuadroFinRagDecItem.setWidth("*");			
		flgCompQuadroFinRagDecItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgCompQuadroFinRagDecItem();
			}
		});
		
		SpacerItem spacerFlgNuoviImpAccItem = new SpacerItem();
		spacerFlgNuoviImpAccItem.setColSpan(1);
		spacerFlgNuoviImpAccItem.setStartRow(true);
		spacerFlgNuoviImpAccItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgNuoviImpAccItem();
			}
		});
		
		flgNuoviImpAccItem = new CheckboxItem("flgNuoviImpAcc", getTitleFlgNuoviImpAccItem());
		flgNuoviImpAccItem.setDefaultValue(getDefaultValueAsBooleanFlgNuoviImpAccItem());
		flgNuoviImpAccItem.setColSpan(18);
		flgNuoviImpAccItem.setWidth("*");	
		flgNuoviImpAccItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm.markForRedraw();
			}
		});
		flgNuoviImpAccItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgNuoviImpAccItem();
			}
		});
		
		SpacerItem spacerFlgImpSuAnnoCorrenteItem = new SpacerItem();
		spacerFlgImpSuAnnoCorrenteItem.setColSpan(1);
		spacerFlgImpSuAnnoCorrenteItem.setStartRow(true);
		spacerFlgImpSuAnnoCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgImpSuAnnoCorrenteItem();
			}
		});
		
		flgImpSuAnnoCorrenteItem = new CheckboxItem("flgImpSuAnnoCorrente", getTitleFlgImpSuAnnoCorrenteItem());
		flgImpSuAnnoCorrenteItem.setDefaultValue(getDefaultValueAsBooleanFlgImpSuAnnoCorrenteItem());
		flgImpSuAnnoCorrenteItem.setColSpan(18);
		flgImpSuAnnoCorrenteItem.setWidth("*");	
		flgImpSuAnnoCorrenteItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm.markForRedraw();
			}
		});
		flgImpSuAnnoCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgImpSuAnnoCorrenteItem();
			}
		});
		
		SpacerItem spacerFlgInsMovARagioneriaItem = new SpacerItem();
		spacerFlgInsMovARagioneriaItem.setColSpan(1);
		spacerFlgInsMovARagioneriaItem.setStartRow(true);
		spacerFlgInsMovARagioneriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgInsMovARagioneriaItem();
			}
		});
		
		flgInsMovARagioneriaItem = new CheckboxItem("flgInsMovARagioneria", getTitleFlgInsMovARagioneriaItem());
		flgInsMovARagioneriaItem.setDefaultValue(getDefaultValueAsBooleanFlgInsMovARagioneriaItem());
		flgInsMovARagioneriaItem.setColSpan(18);
		flgInsMovARagioneriaItem.setWidth("*");	
		flgInsMovARagioneriaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {	
				if(showFlgInsMovARagioneriaItem()) {
					if(showFlgNuoviImpAccItem() && !getValueAsBoolean("flgNuoviImpAcc")) {
						item.setDisabled(true);
						item.setValue(true);		
			 		} else {
						item.setDisabled(false);
					}
					return true;
				}
				return false;
			}
		});
		
		SpacerItem spacerFlgPresaVisioneContabilitaItem = new SpacerItem();
		spacerFlgPresaVisioneContabilitaItem.setColSpan(1);
		spacerFlgPresaVisioneContabilitaItem.setStartRow(true);
		spacerFlgPresaVisioneContabilitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgPresaVisioneContabilitaItem();
			}
		});
		
		flgPresaVisioneContabilitaItem = new CheckboxItem("flgPresaVisioneContabilita", getTitleFlgPresaVisioneContabilitaItem());
		flgPresaVisioneContabilitaItem.setDefaultValue(false);
		flgPresaVisioneContabilitaItem.setColSpan(18);
		flgPresaVisioneContabilitaItem.setWidth("*");
		flgPresaVisioneContabilitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgPresaVisioneContabilitaItem();
			}
		});
		
		CustomValidator tipoSpesaValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(showFlgSpesaCorrenteItem() || showFlgSpesaContoCapitaleItem()) {
					return isDeterminaConSpesaCorrente() || isDeterminaConSpesaContoCapitale() || 
						   (showFlgSoloSubImpSubCronoItem() && getValueAsBoolean("flgSoloSubImpSubCrono"));
				}
				return true;
			}
		};
		tipoSpesaValidator.setErrorMessage("Campo obbligatorio: selezionare almeno una delle opzioni");
		
//		TitleItem tipoSpesaTitleItem = new TitleItem(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_tipoSpesa_title(), true));
		StaticTextItem tipoSpesaTitleItem = new StaticTextItem();
		tipoSpesaTitleItem.setDefaultValue("<span style=\"color:#37505F\">" + FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_tipoSpesa_title(), true) + "</b></span>");		
		tipoSpesaTitleItem.setCellStyle(it.eng.utility.Styles.formTitle);		
		tipoSpesaTitleItem.setShowTitle(false);
		tipoSpesaTitleItem.setAlign(Alignment.RIGHT);
		tipoSpesaTitleItem.setWidth("*");
		tipoSpesaTitleItem.setWrap(false);
		tipoSpesaTitleItem.setColSpan(1);
		tipoSpesaTitleItem.setAttribute("obbligatorio", true);
		tipoSpesaTitleItem.setStartRow(true);
		tipoSpesaTitleItem.setValidators(tipoSpesaValidator);
		tipoSpesaTitleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgSpesaCorrenteItem() || showFlgSpesaContoCapitaleItem();	
			}
		});
		
		flgSpesaCorrenteItem = new CheckboxItem("flgSpesaCorrente", getTitleFlgSpesaCorrenteItem());
		flgSpesaCorrenteItem.setDefaultValue(false);
		flgSpesaCorrenteItem.setColSpan(1);
		flgSpesaCorrenteItem.setWidth("*");
		flgSpesaCorrenteItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm.markForRedraw();
				enableDisableTabs();
				showHideSections();
			}
		});
		flgSpesaCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgSpesaCorrenteItem();
			}
		});
		
		flgImpegniCorrenteGiaValidatiItem = new CheckboxItem("flgImpegniCorrenteGiaValidati", getTitleFlgImpegniCorrenteGiaValidatiItem());
		flgImpegniCorrenteGiaValidatiItem.setDefaultValue(false);
		flgImpegniCorrenteGiaValidatiItem.setColSpan(1);
		flgImpegniCorrenteGiaValidatiItem.setWidth("*");
		flgImpegniCorrenteGiaValidatiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgImpegniCorrenteGiaValidatiItem();
			}
		});
		
		SpacerItem spacerTipoSpesaItem = new SpacerItem();
		spacerTipoSpesaItem.setColSpan(1);
		spacerTipoSpesaItem.setWidth(30);
		spacerTipoSpesaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgImpegniCorrenteGiaValidatiItem() || showFlgImpegniContoCapitaleGiaRilasciatiItem();
			}
		});
		
		flgSpesaContoCapitaleItem = new CheckboxItem("flgSpesaContoCapitale", getTitleFlgSpesaContoCapitaleItem());
		flgSpesaContoCapitaleItem.setDefaultValue(false);
		flgSpesaContoCapitaleItem.setColSpan(1);
		flgSpesaContoCapitaleItem.setWidth("*");
		flgSpesaContoCapitaleItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {		
				caratteristicheProvvedimentoForm.markForRedraw();
				enableDisableTabs();
				showHideSections();
			}
		});
		flgSpesaContoCapitaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgSpesaContoCapitaleItem();
			}
		});
		
		flgImpegniContoCapitaleGiaRilasciatiItem = new CheckboxItem("flgImpegniContoCapitaleGiaRilasciati", getTitleFlgImpegniContoCapitaleGiaRilasciatiItem());
		flgImpegniContoCapitaleGiaRilasciatiItem.setDefaultValue(false);
		flgImpegniContoCapitaleGiaRilasciatiItem.setColSpan(1);
		flgImpegniContoCapitaleGiaRilasciatiItem.setWidth("*");
		flgImpegniContoCapitaleGiaRilasciatiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgImpegniContoCapitaleGiaRilasciatiItem();
			}
		});					
		
		SpacerItem spacerFlgSoloSubImpSubCronoItem = new SpacerItem();
		spacerFlgSoloSubImpSubCronoItem.setColSpan(1);
		spacerFlgSoloSubImpSubCronoItem.setStartRow(true);
		spacerFlgSoloSubImpSubCronoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgSoloSubImpSubCronoItem();
			}
		});
		
		flgSoloSubImpSubCronoItem = new CheckboxItem("flgSoloSubImpSubCrono", getTitleFlgSoloSubImpSubCronoItem());
		flgSoloSubImpSubCronoItem.setDefaultValue(false);
		flgSoloSubImpSubCronoItem.setColSpan(18);
		flgSoloSubImpSubCronoItem.setWidth("*");	
		flgSoloSubImpSubCronoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				vistiXDatiContabiliForm.markForRedraw();				
			}
		});
		flgSoloSubImpSubCronoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgSoloSubImpSubCronoItem();
			}
		});
		
		tipoAttoInDeliberaPEGItem = new SelectItem("tipoAttoInDeliberaPEG", getTitleTipoAttoInDeliberaPEGItem());		
//		tipoAttoInDeliberaPEGItem.setTitleOrientation(TitleOrientation.TOP);
		tipoAttoInDeliberaPEGItem.setWidth(800);
		tipoAttoInDeliberaPEGItem.setColSpan(18);		
		tipoAttoInDeliberaPEGItem.setStartRow(true);
		String[] tipoAttoInDeliberaPEGValueMap = getValoriPossibiliTipoAttoInDeliberaPEGItem();
		if(tipoAttoInDeliberaPEGValueMap != null && tipoAttoInDeliberaPEGValueMap.length > 0) {
			tipoAttoInDeliberaPEGItem.setValueMap(tipoAttoInDeliberaPEGValueMap);			
		} else {
			GWTRestDataSource tipoAttoInDeliberaPEGDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
			tipoAttoInDeliberaPEGDS.addParam("altriParamLoadCombo", getAltriParamLoadComboTipoAttoInDeliberaPEGItem());			
			tipoAttoInDeliberaPEGItem.setValueField("key");
			tipoAttoInDeliberaPEGItem.setDisplayField("value");
			tipoAttoInDeliberaPEGItem.setOptionDataSource(tipoAttoInDeliberaPEGDS);	
		}
		tipoAttoInDeliberaPEGItem.setClearable(true);		
		tipoAttoInDeliberaPEGItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredTipoAttoInDeliberaPEGItem();
			}
		}));
		tipoAttoInDeliberaPEGItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredTipoAttoInDeliberaPEGItem()) {
					tipoAttoInDeliberaPEGItem.setAttribute("obbligatorio", true);
					tipoAttoInDeliberaPEGItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleTipoAttoInDeliberaPEGItem()));
				} else {
					tipoAttoInDeliberaPEGItem.setAttribute("obbligatorio", false);
					tipoAttoInDeliberaPEGItem.setTitle(getTitleTipoAttoInDeliberaPEGItem());
				}
				return showTipoAttoInDeliberaPEGItem();
			}
		});
			
		GWTRestDataSource tipoAffidamentoDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		tipoAffidamentoDS.addParam("altriParamLoadCombo", getAltriParamLoadComboTipoAffidamentoItem());
		 		
		tipoAffidamentoItem = new SelectItem("tipoAffidamento", getTitleTipoAffidamentoItem());
//		tipoAffidamentoItem.setTitleOrientation(TitleOrientation.TOP);		
		tipoAffidamentoItem.setWidth(500);
		tipoAffidamentoItem.setColSpan(18);		
		tipoAffidamentoItem.setStartRow(true);
		tipoAffidamentoItem.setValueField("key");
		tipoAffidamentoItem.setDisplayField("value");
		tipoAffidamentoItem.setOptionDataSource(tipoAffidamentoDS);	
		tipoAffidamentoItem.setClearable(true);		
		tipoAffidamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredTipoAffidamentoItem()) {
					tipoAffidamentoItem.setAttribute("obbligatorio", true);
					tipoAffidamentoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleTipoAffidamentoItem()));
				} else {
					tipoAffidamentoItem.setAttribute("obbligatorio", false);
					tipoAffidamentoItem.setTitle(getTitleTipoAffidamentoItem());
				}
				return showTipoAffidamentoItem();
			}
		});
		tipoAffidamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredTipoAffidamentoItem();
			}
		}));		
		tipoAffidamentoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if(normRifAffidamentoItem != null) {
					final String value = normRifAffidamentoItem.getValueAsString();
					normRifAffidamentoItem.fetchData(new DSCallback() {
			
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							RecordList data = response.getDataAsRecordList();
							boolean trovato = false;
							if (data.getLength() > 0) {
								for (int i = 0; i < data.getLength(); i++) {
									String key = data.get(i).getAttribute("key");
									if (value.equals(key)) {
										trovato = true;
										break;
									}
								}
							}
							if (!trovato) {
								normRifAffidamentoItem.setValue("");
								normRifAffidamentoItem.fireEvent(new ChangedEvent(normRifAffidamentoItem.getJsObj()));
							}
						}
					});
				}
			}
		});
		
		GWTRestDataSource normRifAffidamentoDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		normRifAffidamentoDS.addParam("altriParamLoadCombo", getAltriParamLoadComboNormRifAffidamentoItem());
		 		
		normRifAffidamentoItem = new SelectItem("normRifAffidamento", getTitleNormRifAffidamentoItem()) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid normRifAffidamentoPickListProperties = super.builPickListProperties();	
				normRifAffidamentoPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource normRifAffidamentoDS = (GWTRestDataSource) normRifAffidamentoItem.getOptionDataSource();		
						normRifAffidamentoDS.addParam("codValoreVincolo", tipoAffidamentoItem.getValueAsString());									
						normRifAffidamentoItem.setOptionDataSource(normRifAffidamentoDS);
						normRifAffidamentoItem.invalidateDisplayValueCache();
					}
				});
				return normRifAffidamentoPickListProperties;
			}
		};
//		normRifAffidamentoItem.setTitleOrientation(TitleOrientation.TOP);		
		normRifAffidamentoItem.setWidth(500);
		normRifAffidamentoItem.setColSpan(18);		
		normRifAffidamentoItem.setStartRow(true);
		normRifAffidamentoItem.setValueField("key");
		normRifAffidamentoItem.setDisplayField("value");
		normRifAffidamentoItem.setOptionDataSource(normRifAffidamentoDS);	
		normRifAffidamentoItem.setClearable(true);
		normRifAffidamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredNormRifAffidamentoItem()) {
					normRifAffidamentoItem.setAttribute("obbligatorio", true);
					normRifAffidamentoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleNormRifAffidamentoItem()));
				} else {
					normRifAffidamentoItem.setAttribute("obbligatorio", false);
					normRifAffidamentoItem.setTitle(getTitleNormRifAffidamentoItem());
				}
				return showNormRifAffidamentoItem();
			}
		});
		normRifAffidamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredNormRifAffidamentoItem();
			}
		}));
		
		respAffidamentoItem = new TextItem("respAffidamento", getTitleRespAffidamentoItem());
		respAffidamentoItem.setWidth(481);
		respAffidamentoItem.setColSpan(18);		
		respAffidamentoItem.setStartRow(true);
		respAffidamentoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredRespAffidamentoItem();
			}
		}));
		respAffidamentoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredRespAffidamentoItem()) {
					respAffidamentoItem.setAttribute("obbligatorio", true);
					respAffidamentoItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleRespAffidamentoItem()));
				} else {
					respAffidamentoItem.setAttribute("obbligatorio", false);
					respAffidamentoItem.setTitle(getTitleRespAffidamentoItem());
				}
				return showRespAffidamentoItem();
			}
		});
		   
		GWTRestDataSource materiaTipoAttoDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT, true);
		materiaTipoAttoDS.addParam("altriParamLoadCombo", getAltriParamLoadComboMateriaTipoAttoItem());
		 		
		materiaTipoAttoItem = new FilteredSelectItem("materiaTipoAtto", getTitleMateriaTipoAttoItem()) {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);	
				caratteristicheProvvedimentoForm.setValue("desMateriaTipoAtto", record.getAttributeAsString("value"));
			}			
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				caratteristicheProvvedimentoForm.setValue("materiaTipoAtto", "");	
				caratteristicheProvvedimentoForm.setValue("desMateriaTipoAtto", "");	
			};		
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					caratteristicheProvvedimentoForm.setValue("materiaTipoAtto", "");
					caratteristicheProvvedimentoForm.setValue("desMateriaTipoAtto", "");
				}
			}

			@Override
			protected ListGrid builPickListProperties() {
				ListGrid materiaTipoAttoItemPickListProperties = super.builPickListProperties();	
				materiaTipoAttoItemPickListProperties.setShowHeader(false);
				return materiaTipoAttoItemPickListProperties;
			}
		};
//		materiaTipoAttoItem.setTitleOrientation(TitleOrientation.TOP);
		materiaTipoAttoItem.setWidth(500);
		materiaTipoAttoItem.setColSpan(18);		
		materiaTipoAttoItem.setStartRow(true);		
		materiaTipoAttoItem.setValueField("key");
		materiaTipoAttoItem.setDisplayField("value");
		ListGridField valueField = new ListGridField("value", "Descrizione");
		valueField.setWidth("*");
		valueField.setCanFilter(true);		
		materiaTipoAttoItem.setPickListFields(valueField);
		materiaTipoAttoItem.setOptionDataSource(materiaTipoAttoDS);
		materiaTipoAttoItem.setClearable(true);		
		materiaTipoAttoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showMateriaTipoAttoItem();
			}
		});
		if(isRequiredMateriaTipoAttoItem()) {
			materiaTipoAttoItem.setAttribute("obbligatorio", true);
		}
		materiaTipoAttoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredMateriaTipoAttoItem();
			}
		}));
		
		desMateriaTipoAttoItem = new HiddenItem("desMateriaTipoAtto");
		
		SpacerItem spacerFlgFondiEuropeiPONItem = new SpacerItem();
		spacerFlgFondiEuropeiPONItem.setColSpan(1);
		spacerFlgFondiEuropeiPONItem.setStartRow(true);
		spacerFlgFondiEuropeiPONItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgFondiEuropeiPONItem();
			}
		});
		
		flgFondiEuropeiPONItem = new CheckboxItem("flgFondiEuropeiPON", getTitleFlgFondiEuropeiPONItem());
		flgFondiEuropeiPONItem.setDefaultValue(getDefaultValueAsBooleanFlgFondiEuropeiPONItem());
		flgFondiEuropeiPONItem.setColSpan(18);
		flgFondiEuropeiPONItem.setWidth("*");	
		flgFondiEuropeiPONItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgFondiEuropeiPONItem();
			}
		});
		
		SpacerItem spacerFlgFondiPRUItem = new SpacerItem();
		spacerFlgFondiPRUItem.setColSpan(1);
		spacerFlgFondiPRUItem.setStartRow(true);
		spacerFlgFondiPRUItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgFondiPRUItem();
			}
		});
		
		flgFondiPRUItem = new CheckboxItem("flgFondiPRU", getTitleFlgFondiPRUItem());
		flgFondiPRUItem.setDefaultValue(getDefaultValueAsBooleanFlgFondiPRUItem());
		flgFondiPRUItem.setColSpan(18);
		flgFondiPRUItem.setWidth("*");	
		flgFondiPRUItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgFondiPRUItem();
			}
		});
		
		SpacerItem spacerFlgVistoPar117_2013Item = new SpacerItem();
		spacerFlgVistoPar117_2013Item.setColSpan(1);
		spacerFlgVistoPar117_2013Item.setStartRow(true);
		spacerFlgVistoPar117_2013Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgVistoPar117_2013Item();
			}
		});
		
		flgVistoPar117_2013Item = new CheckboxItem("flgVistoPar117_2013", getTitleFlgVistoPar117_2013Item());
		flgVistoPar117_2013Item.setDefaultValue(getDefaultValueAsBooleanFlgVistoPar117_2013Item());
		flgVistoPar117_2013Item.setColSpan(18);
		flgVistoPar117_2013Item.setWidth("*");	
		flgVistoPar117_2013Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgVistoPar117_2013Item();
			}
		});
		
		SpacerItem spacerFlgNotificaDaMessiItem = new SpacerItem();
		spacerFlgNotificaDaMessiItem.setColSpan(1);
		spacerFlgNotificaDaMessiItem.setStartRow(true);
		spacerFlgNotificaDaMessiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgNotificaDaMessiItem();
			}
		});
		
		flgNotificaDaMessiItem = new CheckboxItem("flgNotificaDaMessi", getTitleFlgNotificaDaMessiItem());
		flgNotificaDaMessiItem.setDefaultValue(getDefaultValueAsBooleanFlgNotificaDaMessiItem());
		flgNotificaDaMessiItem.setColSpan(18);
		flgNotificaDaMessiItem.setWidth("*");	
		flgNotificaDaMessiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgNotificaDaMessiItem();
			}
		});
		
		flgLLPPItem = new RadioGroupItem("flgLLPP", getTitleFlgLLPPItem());
		flgLLPPItem.setStartRow(true);
		flgLLPPItem.setValueMap(_FLG_SI, _FLG_NO);		
		flgLLPPItem.setDefaultValue(getDefaultValueFlgLLPPItem());
		flgLLPPItem.setVertical(false);
		flgLLPPItem.setWrap(false);
		flgLLPPItem.setShowDisabled(false);
		if(isRequiredFlgLLPPItem()) {
			flgLLPPItem.setAttribute("obbligatorio", true);
		}
		flgLLPPItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgLLPPItem();
			}
		}));
		flgLLPPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgLLPPItem();
			}
		});			
		flgLLPPItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm.markForRedraw();
			}
		});
		
		annoProgettoLLPPItem = new AnnoItem("annoProgettoLLPP", getTitleAnnoProgettoLLPPItem());
		annoProgettoLLPPItem.setColSpan(1);
		if(isRequiredAnnoProgettoLLPPItem()) {
			annoProgettoLLPPItem.setAttribute("obbligatorio", true);
		}
		annoProgettoLLPPItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredAnnoProgettoLLPPItem();
			}
		}));
		annoProgettoLLPPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showAnnoProgettoLLPPItem();
			}
		});
		
		numProgettoLLPPItem = new TextItem("numProgettoLLPP", getTitleNumProgettoLLPPItem());
		numProgettoLLPPItem.setColSpan(1);
		if(isRequiredNumProgettoLLPPItem()) {
			numProgettoLLPPItem.setAttribute("obbligatorio", true);
		}
		numProgettoLLPPItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredNumProgettoLLPPItem();
			}
		}));
		numProgettoLLPPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showNumProgettoLLPPItem();
			}
		});
		
		flgBeniServiziItem = new RadioGroupItem("flgBeniServizi", getTitleFlgBeniServiziItem());
		flgBeniServiziItem.setStartRow(true);
		flgBeniServiziItem.setValueMap(_FLG_SI, _FLG_NO);		
		flgBeniServiziItem.setDefaultValue(getDefaultValueFlgBeniServiziItem());
		flgBeniServiziItem.setVertical(false);
		flgBeniServiziItem.setWrap(false);
		flgBeniServiziItem.setShowDisabled(false);
		if(isRequiredFlgBeniServiziItem()) {
			flgBeniServiziItem.setAttribute("obbligatorio", true);
		}
		flgBeniServiziItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgBeniServiziItem();
			}
		}));
		flgBeniServiziItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgBeniServiziItem();
			}
		});	
		flgBeniServiziItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm.markForRedraw();
			}
		});
		
		annoProgettoBeniServiziItem = new AnnoItem("annoProgettoBeniServizi", getTitleAnnoProgettoBeniServiziItem());
		annoProgettoBeniServiziItem.setColSpan(1);
		if(isRequiredAnnoProgettoBeniServiziItem()) {
			annoProgettoBeniServiziItem.setAttribute("obbligatorio", true);
		}
		annoProgettoBeniServiziItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredAnnoProgettoBeniServiziItem();
			}
		}));
		annoProgettoBeniServiziItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showAnnoProgettoBeniServiziItem();
			}
		});
		
		numProgettoBeniServiziItem = new TextItem("numProgettoBeniServizi", getTitleNumProgettoBeniServiziItem());
		numProgettoBeniServiziItem.setColSpan(1);
		if(isRequiredNumProgettoBeniServiziItem()) {
			numProgettoBeniServiziItem.setAttribute("obbligatorio", true);
		}
		numProgettoBeniServiziItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredNumProgettoBeniServiziItem();
			}
		}));
		numProgettoBeniServiziItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showNumProgettoBeniServiziItem();
			}
		});
		
		flgPrivacyItem = new RadioGroupItem("flgPrivacy", getTitleFlgPrivacyItem());
		flgPrivacyItem.setStartRow(true);
		flgPrivacyItem.setValueMap(_FLG_SI, _FLG_NO);		
		flgPrivacyItem.setDefaultValue(getDefaultValueFlgPrivacyItem());		
		flgPrivacyItem.setVertical(false);
		flgPrivacyItem.setWrap(false);
		flgPrivacyItem.setShowDisabled(false);
		if(isRequiredFlgPrivacyItem()) {
			flgPrivacyItem.setAttribute("obbligatorio", true);
		}
		flgPrivacyItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgPrivacyItem();
			}
		}));
		flgPrivacyItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showFlgPrivacyItem() && !isDatiRiservati() && hasDatiSensibili()) {				
					item.setDisabled(true);
					item.setValue(_FLG_SI);		
					caratteristicheProvvedimentoForm.markForRedraw();
				} else {
					item.setDisabled(false);					
				}
				return showFlgPrivacyItem();
			}
		});	
		flgPrivacyItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				caratteristicheProvvedimentoForm.markForRedraw();
			}
		});
		
		SpacerItem spacerFlgDatiProtettiTipo1Item = new SpacerItem();
		spacerFlgDatiProtettiTipo1Item.setColSpan(1);
		spacerFlgDatiProtettiTipo1Item.setStartRow(true);
		spacerFlgDatiProtettiTipo1Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDatiProtettiTipo1Item();
			}
		});
		
		flgDatiProtettiTipo1Item = new CheckboxItem("flgDatiProtettiTipo1", getTitleFlgDatiProtettiTipo1Item());
		flgDatiProtettiTipo1Item.setDefaultValue(getDefaultValueAsBooleanFlgDatiProtettiTipo1Item());
		flgDatiProtettiTipo1Item.setColSpan(18);
		flgDatiProtettiTipo1Item.setWidth("*");	
		flgDatiProtettiTipo1Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {		
				if(showFlgDatiProtettiTipo1Item()) {
					return true;
				} else {
					flgDatiProtettiTipo1Item.setValue(false);
					return false;
				}
			}
		});
		
		SpacerItem spacerFlgDatiProtettiTipo2Item = new SpacerItem();
		spacerFlgDatiProtettiTipo2Item.setColSpan(1);
		spacerFlgDatiProtettiTipo2Item.setStartRow(true);
		spacerFlgDatiProtettiTipo2Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDatiProtettiTipo2Item();
			}
		});
		
		flgDatiProtettiTipo2Item = new CheckboxItem("flgDatiProtettiTipo2", getTitleFlgDatiProtettiTipo2Item());
		flgDatiProtettiTipo2Item.setDefaultValue(getDefaultValueAsBooleanFlgDatiProtettiTipo2Item());
		flgDatiProtettiTipo2Item.setColSpan(18);
		flgDatiProtettiTipo2Item.setWidth("*");	
		flgDatiProtettiTipo2Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {	
				if(showFlgDatiProtettiTipo2Item()) {
					return true;
				} else {
					flgDatiProtettiTipo2Item.setValue(false);
					return false;
				}
			}
		});
		   
		SpacerItem spacerFlgDatiProtettiTipo3Item = new SpacerItem();
		spacerFlgDatiProtettiTipo3Item.setColSpan(1);
		spacerFlgDatiProtettiTipo3Item.setStartRow(true);
		spacerFlgDatiProtettiTipo3Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDatiProtettiTipo3Item();
			}
		});
		
		flgDatiProtettiTipo3Item = new CheckboxItem("flgDatiProtettiTipo3", getTitleFlgDatiProtettiTipo3Item());
		flgDatiProtettiTipo3Item.setDefaultValue(getDefaultValueAsBooleanFlgDatiProtettiTipo3Item());
		flgDatiProtettiTipo3Item.setColSpan(18);
		flgDatiProtettiTipo3Item.setWidth("*");	
		flgDatiProtettiTipo3Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {	
				if(showFlgDatiProtettiTipo3Item()) {
					return true;
				} else {
					flgDatiProtettiTipo3Item.setValue(false);
					return false;
				}
			}
		});
		
		SpacerItem spacerFlgDatiProtettiTipo4Item = new SpacerItem();
		spacerFlgDatiProtettiTipo4Item.setColSpan(1);
		spacerFlgDatiProtettiTipo4Item.setStartRow(true);
		spacerFlgDatiProtettiTipo4Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {							
				return  showFlgDatiProtettiTipo4Item();
			}
		});
		
		flgDatiProtettiTipo4Item = new CheckboxItem("flgDatiProtettiTipo4", getTitleFlgDatiProtettiTipo4Item());
		flgDatiProtettiTipo4Item.setDefaultValue(getDefaultValueAsBooleanFlgDatiProtettiTipo4Item());
		flgDatiProtettiTipo4Item.setColSpan(18);
		flgDatiProtettiTipo4Item.setWidth("*");	
		flgDatiProtettiTipo4Item.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {		
				if(showFlgDatiProtettiTipo4Item()) {
					return true;
				} else {
					flgDatiProtettiTipo4Item.setValue(false);
					return false;
				}
			}
		});
		
		caratteristicheProvvedimentoForm.setFields(			
			oggLiquidazioneItem, showInfoOggLiquidazioneButton,
			endRowScadenzaItem, dataScadenzaLiquidazioneItem, urgenzaLiquidazioneItem,
			spacerFlgLiqXUffCassaItem, flgLiqXUffCassaItem,	importoAnticipoCassaItem,
			endRowContrattoItem, dataDecorrenzaContrattoItem, anniDurataContrattoItem,
			spacerFlgAffidamentoItem, flgAffidamentoItem, 
			spacerFlgDeterminaAContrarreTramiteProceduraGaraItem, flgDeterminaAContrarreTramiteProceduraGaraItem, 
			spacerFlgDeterminaAggiudicaProceduraGaraItem, flgDeterminaAggiudicaProceduraGaraItem, 
			spacerFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem, flgDeterminaRimodulazioneSpesaGaraAggiudicataItem,
			spacerFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem, flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem,
			spacerFlgDeterminaRiaccertamentoItem, flgDeterminaRiaccertamentoItem,		
			spacerFlgDeterminaAccertRadiazItem, flgDeterminaAccertRadiazItem,
			spacerFlgDeterminaVariazBilItem, flgDeterminaVariazBilItem,
			spacerFlgVantaggiEconomiciItem, flgVantaggiEconomiciItem, 
			spacerFlgDecretoReggioItem, flgDecretoReggioItem,
			spacerFlgAvvocaturaItem, flgAvvocaturaItem,
			flgSpesaItem,
			spacerFlgCorteContiItem, flgCorteContiItem,
			spacerFlgLiqContestualeImpegnoItem, flgLiqContestualeImpegnoItem, 
			spacerFlgLiqContestualeAltriAspettiRilContItem, flgLiqContestualeAltriAspettiRilContItem,
			spacerFlgCompQuadroFinRagDecItem, flgCompQuadroFinRagDecItem,			
			spacerFlgNuoviImpAccItem, flgNuoviImpAccItem, 
			spacerFlgImpSuAnnoCorrenteItem, flgImpSuAnnoCorrenteItem,
			spacerFlgInsMovARagioneriaItem, flgInsMovARagioneriaItem,
			spacerFlgPresaVisioneContabilitaItem, flgPresaVisioneContabilitaItem,
			tipoSpesaTitleItem, flgSpesaCorrenteItem, flgImpegniCorrenteGiaValidatiItem, spacerTipoSpesaItem, flgSpesaContoCapitaleItem, flgImpegniContoCapitaleGiaRilasciatiItem, 
			spacerFlgSoloSubImpSubCronoItem, flgSoloSubImpSubCronoItem,
			tipoAttoInDeliberaPEGItem,
			tipoAffidamentoItem,
			normRifAffidamentoItem,
			respAffidamentoItem,
			materiaTipoAttoItem,
			desMateriaTipoAttoItem,
			spacerFlgFondiEuropeiPONItem, flgFondiEuropeiPONItem,
			spacerFlgFondiPRUItem, flgFondiPRUItem,
			spacerFlgVistoPar117_2013Item, flgVistoPar117_2013Item,
			spacerFlgNotificaDaMessiItem, flgNotificaDaMessiItem,
			flgLLPPItem,
			annoProgettoLLPPItem,
			numProgettoLLPPItem,			
			flgBeniServiziItem,
			annoProgettoBeniServiziItem,
			numProgettoBeniServiziItem,
			flgPrivacyItem,
			spacerFlgDatiProtettiTipo1Item, flgDatiProtettiTipo1Item,
			spacerFlgDatiProtettiTipo2Item, flgDatiProtettiTipo2Item,
			spacerFlgDatiProtettiTipo3Item, flgDatiProtettiTipo3Item,
			spacerFlgDatiProtettiTipo4Item, flgDatiProtettiTipo4Item
		);	
	}
	
	/********************************* 
	 * DATI SCHEDA - DEST. VANTAGGIO *
	 *********************************/	
	
	public boolean showDetailSectionDestVantaggio() {
		return showDestVantaggioItem();
	}	
	
	public String getTitleDetailSectionDestVantaggio() {		
		return getTitleDestVantaggioItem();
	}
	
	public boolean isRequiredDetailSectionDestVantaggio() {
		return isRequiredDestVantaggioItem();
	}
	
	protected void createDetailSectionDestVantaggio() {
		
		createDestVantaggioForm();
		
		detailSectionDestVantaggio = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionDestVantaggio(), true, true, isRequiredDetailSectionDestVantaggio(), destVantaggioForm);
	}
	
	public boolean showDestVantaggioItem() {
		return showFlgVantaggiEconomiciItem() && isVantaggiEconomici() && showAttributoCustomCablato("DEST_VANTAGGIO");
	}
	
	public boolean isRequiredDestVantaggioItem() {
		return showDestVantaggioItem() && getFlgObbligatorioAttributoCustomCablato("DEST_VANTAGGIO");
	}
	
	public String getTitleDestVantaggioItem() {
		String label = getLabelAttributoCustomCablato("DEST_VANTAGGIO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Dest. vantaggio";
	}
	
	public boolean isNotReplicableDestVantaggioItem() {
		Integer maxNumValori = getMaxNumValoriAttributoCustomCablato("DEST_VANTAGGIO");
		return maxNumValori != null && maxNumValori.intValue() == 1;
	}
	
	protected void createDestVantaggioForm() {
		
		destVantaggioForm = new DynamicForm();
		destVantaggioForm.setValuesManager(vm);
		destVantaggioForm.setWidth100();
		destVantaggioForm.setPadding(5);
		destVantaggioForm.setWrapItemTitles(false);
		destVantaggioForm.setNumCols(12);
		destVantaggioForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		destVantaggioForm.setTabSet(tabSet);
		destVantaggioForm.setTabID(_TAB_DATI_SCHEDA_ID);
		destVantaggioForm.setHeight(1);
		
		listaDestVantaggioItem = new DestVantaggioItem() {
			
			@Override
			public boolean isRequiredDestVantaggio() {
				return isRequiredDestVantaggioItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showDestVantaggioItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			}
		};
		listaDestVantaggioItem.setName("listaDestVantaggio");
		listaDestVantaggioItem.setShowTitle(false);
		listaDestVantaggioItem.setColSpan(20);
		if(isNotReplicableDestVantaggioItem()) {
			listaDestVantaggioItem.setNotReplicable(true);
		}	
//		if(showAttributoCustomCablato("DEST_VANTAGGIO") && getFlgObbligatorioAttributoCustomCablato("DEST_VANTAGGIO")) {
//			listaDestVantaggioItem.setAttribute("obbligatorio", true);
//		}
		listaDestVantaggioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredDestVantaggioItem()) {
					listaDestVantaggioItem.setAttribute("obbligatorio", true);
				} else {
					listaDestVantaggioItem.setAttribute("obbligatorio", false);
				}
				listaDestVantaggioItem.storeValue(form.getValueAsRecordList(listaDestVantaggioItem.getName()));				
				return showDestVantaggioItem();
			}
		});	
		
		destVantaggioForm.setFields(listaDestVantaggioItem);	
	}
	
	/************************************************** 
	 * DATI SCHEDA - RUOLI E VISTI PER DATI CONTABILI *
	 **************************************************/	
	
	public boolean showDetailSectionRuoliEVistiXDatiContabili() {
		return showAttributoCustomCablato("DATI_CONTABILI") && (isDeterminaConSpesa() || isDeterminaConSpesaSenzaImpegni());
	}	
	
	public String getTitleDetailSectionRuoliEVistiXDatiContabili() {
		String label = getLabelAttributoCustomCablato("DATI_CONTABILI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Ruoli e visti per dati contabili";
	}
	
	protected void createDetailSectionRuoliEVistiXDatiContabili() {
		
		createRuoliEVistiXDatiContabiliForm();
		
		detailSectionRuoliEVistiXDatiContabili = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionRuoliEVistiXDatiContabili(), true, true, false, ruoliXDatiContabiliForm1, ruoliXDatiContabiliForm2, vistiXDatiContabiliForm);
	}
	
	public boolean showVistiXDatiContabiliForm() {
		return true;
	}
		
	public boolean showUfficioDefinizioneSpesaItem() {
		String struttCompDefDatiCont = AurigaLayout.getParametroDB("STRUTT_COMP_DEF_DATI_CONT");
		if(struttCompDefDatiCont != null && _STRUTT_COMP_DEF_DATI_CONT_SEMPRE_UGUALE_UO_PROPONENTE.equalsIgnoreCase(struttCompDefDatiCont)) {
			return false;
		}
		return showDetailSectionRuoliEVistiXDatiContabili() && isDeterminaConSpesa() && showAttributoCustomCablato("ID_UO_COMP_SPESA");
	}
	
	protected void setUfficioDefinizioneSpesaFromUoProponente() {
		if(listaUfficioDefinizioneSpesaItem != null) {
			String struttCompDefDatiCont = AurigaLayout.getParametroDB("STRUTT_COMP_DEF_DATI_CONT");
			if(struttCompDefDatiCont != null) {
				boolean showUfficioDefinizioneSpesa = showUfficioDefinizioneSpesaItem();
				boolean hasValueUfficioDefinizioneSpesa = listaUfficioDefinizioneSpesaItem.hasValue();
				if(_STRUTT_COMP_DEF_DATI_CONT_SEMPRE_UGUALE_UO_PROPONENTE.equalsIgnoreCase(struttCompDefDatiCont) 
					|| (_STRUTT_COMP_DEF_DATI_CONT_DEFAULT_UGUALE_UO_PROPONENTE.equalsIgnoreCase(struttCompDefDatiCont) && showUfficioDefinizioneSpesa && !hasValueUfficioDefinizioneSpesa)) {
					Record lRecordUfficioDefinizioneSpesa = new Record();
					if(isAbilToSelUffPropEsteso()) {
						RecordList listaUfficioProponente = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaUfficioProponente") : null;
						if(listaUfficioProponente != null && listaUfficioProponente.getLength() > 0) {
							if(listaUfficioProponente.get(0).getAttribute("idUo") != null && !"".equals(listaUfficioProponente.get(0).getAttribute("idUo"))) {
								lRecordUfficioDefinizioneSpesa.setAttribute("idUo", listaUfficioProponente.get(0).getAttribute("idUo"));
								lRecordUfficioDefinizioneSpesa.setAttribute("codRapido", listaUfficioProponente.get(0).getAttribute("codRapido"));
								lRecordUfficioDefinizioneSpesa.setAttribute("descrizione", listaUfficioProponente.get(0).getAttribute("descrizione"));
								lRecordUfficioDefinizioneSpesa.setAttribute("descrizioneEstesa", listaUfficioProponente.get(0).getAttribute("descrizione"));
								lRecordUfficioDefinizioneSpesa.setAttribute("organigramma", listaUfficioProponente.get(0).getAttribute("organigramma"));			
							}
						}
					} else if (getValueAsString("ufficioProponente") != null && !"".equals(getValueAsString("ufficioProponente"))) {
						lRecordUfficioDefinizioneSpesa.setAttribute("idUo", getValueAsString("ufficioProponente"));
						lRecordUfficioDefinizioneSpesa.setAttribute("codRapido", getValueAsString("codUfficioProponente"));
						lRecordUfficioDefinizioneSpesa.setAttribute("descrizione", getValueAsString("desUfficioProponente"));
						lRecordUfficioDefinizioneSpesa.setAttribute("descrizioneEstesa", getValueAsString("desUfficioProponente"));						
						lRecordUfficioDefinizioneSpesa.setAttribute("organigramma", "UO" + getValueAsString("ufficioProponente"));
					}
					codUfficioDefinizioneSpesaItem.setValue(lRecordUfficioDefinizioneSpesa.getAttribute("codRapido"));
					desUfficioDefinizioneSpesaItem.setValue(lRecordUfficioDefinizioneSpesa.getAttribute("descrizione"));
					RecordList listaUfficioDefinizioneSpesa = new RecordList();
					listaUfficioDefinizioneSpesa.add(lRecordUfficioDefinizioneSpesa);
					listaUfficioDefinizioneSpesaItem.drawAndSetValue(listaUfficioDefinizioneSpesa);					
				}	
			}
		}
	}
	
	public String getTitleUfficioDefinizioneSpesaItem() {
		String label = getLabelAttributoCustomCablato("ID_UO_COMP_SPESA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Struttura comp. def. dati contabili";		
	}
	
	public boolean showOpzAssCompSpesaItem() {
		return showDetailSectionRuoliEVistiXDatiContabili() && isDeterminaConSpesa() && showAttributoCustomCablato("TASK_RESULT_2_OPZ_ASS_COMP_SPESA");
	}
	
	public String getTitleOpzAssCompSpesaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_OPZ_ASS_COMP_SPESA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Definizione dati contabili in carico a"; 
	}
	
	public boolean isRequiredOpzAssCompSpesaItem() {
		return showOpzAssCompSpesaItem() && getFlgObbligatorioAttributoCustomCablato("TASK_RESULT_2_OPZ_ASS_COMP_SPESA");
	}
	
	public HashMap<String, String> getValueMapOpzAssCompSpesaItem() {
		return getValueMapAttributoCustomCablato("TASK_RESULT_2_OPZ_ASS_COMP_SPESA");
	}
	
	public String getDefaultValueOpzAssCompSpesaItem() {
		return getValoreFissoAttributoCustomCablato("TASK_RESULT_2_OPZ_ASS_COMP_SPESA");
	}
	
	public boolean showFlgAdottanteUnicoRespPEGItem() {
		return showDetailSectionRuoliEVistiXDatiContabili() && showAdottanteItem() && showAttributoCustomCablato("TASK_RESULT_2_FLG_ADOTTANTE_UNICO_RESP_SPESA");
	}
	
	public String getTitleFlgAdottanteUnicoRespPEGItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FLG_ADOTTANTE_UNICO_RESP_SPESA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "adottante unico resp. cap. spesa";		
	}
	
	public boolean showResponsabiliPEGItem() {
		return showDetailSectionRuoliEVistiXDatiContabili() && isDeterminaConSpesa() && (!showFlgAdottanteUnicoRespPEGItem() || !getValueAsBoolean("flgAdottanteUnicoRespPEG")) && showAttributoCustomCablato("ID_SV_RESP_SPESA");
	}
		
	public String getTitleResponsabiliPEGItem() {
		String label = getLabelAttributoCustomCablato("ID_SV_RESP_SPESA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Altri resp. cap. spesa";		
	}
	
	public String getAltriParamLoadComboResponsabiliPEGItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("ID_SV_RESP_SPESA");
	}
	
	public boolean showFlgRichVerificaDiBilancioCorrenteItem() {
		return showDetailSectionRuoliEVistiXDatiContabili() && isDeterminaConSpesaSenzaImpegni() && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_VRF_BIL_CORR");
	}
		
	public String getTitleFlgRichVerificaDiBilancioCorrenteItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_CON_VRF_BIL_CORR");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "bilancio corrente";	
	}
	
	public boolean showFlgRichVerificaDiBilancioContoCapitaleItem() {
		return showDetailSectionRuoliEVistiXDatiContabili() && isDeterminaConSpesaSenzaImpegni() && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_VRF_BIL_CCAP");
	}
		
	public String getTitleFlgRichVerificaDiBilancioContoCapitaleItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_CON_VRF_BIL_CCAP");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "bilancio conto capitale";			
	}
	
	public boolean showFlgRichVerificaDiContabilitaItem() {
		return showDetailSectionRuoliEVistiXDatiContabili() && isDeterminaConSpesaSenzaImpegni() && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_VRF_CONTABIL");
	}
		
	public String getTitleFlgRichVerificaDiContabilitaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_DET_CON_VRF_CONTABIL");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "contabilità";			
	}
	
	public boolean showFlgConVerificaContabilitaItem() {
		return showDetailSectionRuoliEVistiXDatiContabili() && isDeterminaConSpesa() && showAttributoCustomCablato("TASK_RESULT_2_RICH_VERIFICA_CONTABILITA");
	}
		
	public String getTitleFlgConVerificaContabilitaItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_RICH_VERIFICA_CONTABILITA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "richiesta verifica contabilità";	
	}
	
	public boolean showFlgRichiediParereRevisoriContabiliItem() {
		return showDetailSectionRuoliEVistiXDatiContabili() && isDeterminaConSpesa() && showAttributoCustomCablato("TASK_RESULT_2_RICH_PARERE_REV_CONTABILI");
	}
		
	public String getTitleFlgRichiediParereRevisoriContabiliItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_RICH_PARERE_REV_CONTABILI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "richiesto parere revisori contabil";
	}
	
	protected void createRuoliEVistiXDatiContabiliForm() {
		
		ruoliXDatiContabiliForm1 = new DynamicForm();
		ruoliXDatiContabiliForm1.setValuesManager(vm);
		ruoliXDatiContabiliForm1.setWidth100();
		ruoliXDatiContabiliForm1.setPadding(5);
		ruoliXDatiContabiliForm1.setWrapItemTitles(false);
		ruoliXDatiContabiliForm1.setNumCols(20);
		ruoliXDatiContabiliForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ruoliXDatiContabiliForm1.setTabSet(tabSet);
		ruoliXDatiContabiliForm1.setTabID(_TAB_DATI_SCHEDA_ID);
		ruoliXDatiContabiliForm1.setHeight(1);
		
		codUfficioDefinizioneSpesaItem = new HiddenItem("codUfficioDefinizioneSpesa");
		desUfficioDefinizioneSpesaItem = new HiddenItem("desUfficioDefinizioneSpesa");
		
		listaUfficioDefinizioneSpesaItem = new SelezionaUOItem() {
			
			@Override
			public int getSelectItemOrganigrammaWidth() {
				return 650;
			}
			
			@Override
			public boolean skipValidation() {
				if(showUfficioDefinizioneSpesaItem()) {
					return super.skipValidation();
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);				
				lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleUfficioDefinizioneSpesaItem()) + "</span>");	
				return lVLayout;
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return true;
			};
		};
		listaUfficioDefinizioneSpesaItem.setName("listaUfficioDefinizioneSpesa");
		listaUfficioDefinizioneSpesaItem.setStartRow(true);
		listaUfficioDefinizioneSpesaItem.setShowTitle(false);
		listaUfficioDefinizioneSpesaItem.setColSpan(20);
		listaUfficioDefinizioneSpesaItem.setNotReplicable(true);
		listaUfficioDefinizioneSpesaItem.setAttribute("obbligatorio", true);
		listaUfficioDefinizioneSpesaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showUfficioDefinizioneSpesaItem();
			}
		});
		
		opzAssCompSpesaItem = new RadioGroupItem("opzAssCompSpesa", getTitleOpzAssCompSpesaItem());
		opzAssCompSpesaItem.setValueMap(getValueMapOpzAssCompSpesaItem());
		opzAssCompSpesaItem.setDefaultValue(getDefaultValueOpzAssCompSpesaItem());
		opzAssCompSpesaItem.setVertical(false);
		opzAssCompSpesaItem.setWrap(false);
		opzAssCompSpesaItem.setShowDisabled(false);
		if(isRequiredOpzAssCompSpesaItem()) {
			opzAssCompSpesaItem.setAttribute("obbligatorio", true);
		}
		opzAssCompSpesaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredOpzAssCompSpesaItem();
			}
		}));
		opzAssCompSpesaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showOpzAssCompSpesaItem();
			}
		});			
		
		ruoliXDatiContabiliForm1.setFields(
			codUfficioDefinizioneSpesaItem,
			desUfficioDefinizioneSpesaItem,
			listaUfficioDefinizioneSpesaItem,
			opzAssCompSpesaItem
		);		
		
		ruoliXDatiContabiliForm2 = new DynamicForm();
		ruoliXDatiContabiliForm2.setValuesManager(vm);
		ruoliXDatiContabiliForm2.setWidth100();
		ruoliXDatiContabiliForm2.setPadding(5);
		ruoliXDatiContabiliForm2.setWrapItemTitles(false);
		ruoliXDatiContabiliForm2.setNumCols(20);
		ruoliXDatiContabiliForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		ruoliXDatiContabiliForm2.setTabSet(tabSet);
		ruoliXDatiContabiliForm2.setTabID(_TAB_DATI_SCHEDA_ID);
		ruoliXDatiContabiliForm2.setHeight(1);		
		
		flgAdottanteUnicoRespPEGItem = new CheckboxItem("flgAdottanteUnicoRespPEG", getTitleFlgAdottanteUnicoRespPEGItem());
		if(showFlgAdottanteUnicoRespPEGItem()) {
			flgAdottanteUnicoRespPEGItem.setDefaultValue(true);
		}
		flgAdottanteUnicoRespPEGItem.setStartRow(true);
		flgAdottanteUnicoRespPEGItem.setColSpan(1);
		flgAdottanteUnicoRespPEGItem.setWidth("*");
		flgAdottanteUnicoRespPEGItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				ruoliXDatiContabiliForm2.markForRedraw();
			}
		});
		flgAdottanteUnicoRespPEGItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showFlgAdottanteUnicoRespPEGItem()) {
					if(isDeterminaConSpesaSenzaImpegni()) {
						item.setDisabled(true);
						item.setValue(true);
					} else {
						item.setDisabled(false);
					}
					return true;
				}
				return false;
			}
		});
		
		listaResponsabiliPEGItem = new ResponsabiliPEGCompletaItem() {
			
			@Override
			public String getAltriParamLoadCombo() {
				return getAltriParamLoadComboResponsabiliPEGItem();
			}
			
			@Override
			public boolean skipValidation() {
				if(showResponsabiliPEGItem()) {
					return super.skipValidation(); //TODO Verificare se quando chiamo super.skipValidation() mi torna true quando sono su un altro tab
				}
				return true;
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleResponsabiliPEGItem()) + "</span>");	
				return lVLayout;
			}
		};
		listaResponsabiliPEGItem.setName("listaResponsabiliPEG");
		listaResponsabiliPEGItem.setStartRow(true);
		listaResponsabiliPEGItem.setShowTitle(false);
		listaResponsabiliPEGItem.setColSpan(20);
		listaResponsabiliPEGItem.setAttribute("obbligatorio", true);
		listaResponsabiliPEGItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showResponsabiliPEGItem();
			}
		});
		
		ruoliXDatiContabiliForm2.setFields(
			flgAdottanteUnicoRespPEGItem, 
			listaResponsabiliPEGItem
		);		
		
		vistiXDatiContabiliForm = new DynamicForm();
		vistiXDatiContabiliForm.setValuesManager(vm);
		vistiXDatiContabiliForm.setWidth100();
		vistiXDatiContabiliForm.setPadding(5);
		vistiXDatiContabiliForm.setWrapItemTitles(false);
		vistiXDatiContabiliForm.setNumCols(20);
		vistiXDatiContabiliForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		vistiXDatiContabiliForm.setTabSet(tabSet);
		vistiXDatiContabiliForm.setTabID(_TAB_DATI_SCHEDA_ID);
		vistiXDatiContabiliForm.setHeight(1);		
		
		CustomValidator richVerificaDiValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if (showDetailSectionRuoliEVistiXDatiContabili() && isDeterminaConSpesaSenzaImpegni() && 
					(showFlgRichVerificaDiBilancioCorrenteItem() || showFlgRichVerificaDiBilancioContoCapitaleItem() || showFlgRichVerificaDiContabilitaItem())) {
						return (showFlgRichVerificaDiBilancioCorrenteItem() && getValueAsBoolean("flgRichVerificaDiBilancioCorrente")) || 
							   (showFlgRichVerificaDiBilancioContoCapitaleItem() && getValueAsBoolean("flgRichVerificaDiBilancioContoCapitale")) || 
							   (showFlgRichVerificaDiContabilitaItem() && getValueAsBoolean("flgRichVerificaDiContabilita"));
				}
				return true;
			}
		};
		richVerificaDiValidator.setErrorMessage("Campo obbligatorio: selezionare almeno una delle opzioni");
		
		TitleItem richVerificaDiTitleItem = new TitleItem(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_richVerificaDi_title(), true));
		richVerificaDiTitleItem.setAttribute("obbligatorio", true);
		richVerificaDiTitleItem.setValidators(richVerificaDiValidator);
		richVerificaDiTitleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showDetailSectionRuoliEVistiXDatiContabili() && isDeterminaConSpesaSenzaImpegni() && 
						(showFlgRichVerificaDiBilancioCorrenteItem() || showFlgRichVerificaDiBilancioContoCapitaleItem() || showFlgRichVerificaDiContabilitaItem());
			}
		});
		
		flgRichVerificaDiBilancioCorrenteItem = new CheckboxItem("flgRichVerificaDiBilancioCorrente", getTitleFlgRichVerificaDiBilancioCorrenteItem());
		flgRichVerificaDiBilancioCorrenteItem.setDefaultValue(false);
		flgRichVerificaDiBilancioCorrenteItem.setColSpan(1);
		flgRichVerificaDiBilancioCorrenteItem.setWidth("*");
		flgRichVerificaDiBilancioCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgRichVerificaDiBilancioCorrenteItem();
			}
		});
		
		flgRichVerificaDiBilancioContoCapitaleItem = new CheckboxItem("flgRichVerificaDiBilancioContoCapitale", getTitleFlgRichVerificaDiBilancioContoCapitaleItem());
		flgRichVerificaDiBilancioContoCapitaleItem.setDefaultValue(false);
		flgRichVerificaDiBilancioContoCapitaleItem.setColSpan(1);
		flgRichVerificaDiBilancioContoCapitaleItem.setWidth("*");
		flgRichVerificaDiBilancioContoCapitaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgRichVerificaDiBilancioContoCapitaleItem();
			}
		});
		
		flgRichVerificaDiContabilitaItem = new CheckboxItem("flgRichVerificaDiContabilita", getTitleFlgRichVerificaDiContabilitaItem());
		if(showFlgRichVerificaDiContabilitaItem()) {
			flgRichVerificaDiContabilitaItem.setDefaultValue(true);
		}
		flgRichVerificaDiContabilitaItem.setColSpan(1);
		flgRichVerificaDiContabilitaItem.setWidth("*");
		flgRichVerificaDiContabilitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgRichVerificaDiContabilitaItem();
			}
		});
		
		flgConVerificaContabilitaItem = new CheckboxItem("flgConVerificaContabilita", getTitleFlgConVerificaContabilitaItem());
		if(showFlgConVerificaContabilitaItem()) {
			flgConVerificaContabilitaItem.setDefaultValue(true);
		}
		flgConVerificaContabilitaItem.setStartRow(true);
		flgConVerificaContabilitaItem.setColSpan(20);
		flgConVerificaContabilitaItem.setWidth("*");	
		flgConVerificaContabilitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showFlgConVerificaContabilitaItem()) {
					if(showFlgSoloSubImpSubCronoItem() && getValueAsBoolean("flgSoloSubImpSubCrono")) {
						item.setDisabled(true);
						item.setValue(true);
					} else {
						item.setDisabled(false);
					}
					return true;
				}
				return false;
			}
		});
		
		flgRichiediParereRevisoriContabiliItem = new CheckboxItem("flgRichiediParereRevisoriContabili", getTitleFlgRichiediParereRevisoriContabiliItem());
		flgRichiediParereRevisoriContabiliItem.setDefaultValue(false);
		flgRichiediParereRevisoriContabiliItem.setStartRow(true);
		flgRichiediParereRevisoriContabiliItem.setColSpan(20);
		flgRichiediParereRevisoriContabiliItem.setWidth("*");
		flgRichiediParereRevisoriContabiliItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgRichiediParereRevisoriContabiliItem();
			}
		});

		vistiXDatiContabiliForm.setFields(
			richVerificaDiTitleItem, flgRichVerificaDiBilancioCorrenteItem, flgRichVerificaDiBilancioContoCapitaleItem, flgRichVerificaDiContabilitaItem,				
			flgConVerificaContabilitaItem, 
			flgRichiediParereRevisoriContabiliItem
		);	
		
	}
	
	/********************* 
	 * DATI SCHEDA - CIG *
	 *********************/	
	
	public boolean showDetailSectionCIG() {
		return showFlgOpCommercialeItem() ||
			   showFlgEscludiCIGItem() ||
			   showMotivoEsclusioneCIGItem() ||
			   showCIGItem();
	}
	
	protected void createDetailSectionCIG() {
		
		createCIGForm();
		
		detailSectionCIG = new NuovaPropostaAtto2CompletaDetailSection("CIG", true, true, false, CIGForm);
	}
	
	public boolean showFlgOpCommercialeItem() {
		return showAttributoCustomCablato("TASK_RESULT_2_FLG_OP_COMMERCIALE");
	}
	
	public String getTitleFlgOpCommercialeItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FLG_OP_COMMERCIALE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Operazione commerciale";
	}
	
	public boolean isRequiredFlgOpCommercialeItem() {
		return showFlgOpCommercialeItem() && getFlgObbligatorioAttributoCustomCablato("TASK_RESULT_2_FLG_OP_COMMERCIALE");
	}
	
	public String[] getValoriPossibiliFlgOpCommercialeItem() {
		String[] valoriPossibili = getValoriPossibiliAttributoCustomCablato("TASK_RESULT_2_FLG_OP_COMMERCIALE");
		if(valoriPossibili != null && valoriPossibili.length > 0) {
			return valoriPossibili;			
		} else {
			return new String[] {_FLG_SI, _FLG_NO, _FLG_OP_COMMERCIALE_NA};
		}
	}
	
	public String getDefaultValueFlgOpCommercialeItem() {
		return getValoreFissoAttributoCustomCablato("TASK_RESULT_2_FLG_OP_COMMERCIALE");
	}
	
	public boolean showFlgEscludiCIGItem() {
		if(showFlgOpCommercialeItem() && !_FLG_SI.equals(getValueAsString("flgOpCommerciale"))) {
			return false;
		}
		return showAttributoCustomCablato("TASK_RESULT_2_FLG_ESCL_CIG");
	}
	
	public String getTitleFlgEscludiCIGItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FLG_ESCL_CIG");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "escludi CIG";
	}
	
	public boolean getDefaultValueAsBooleanFlgEscludiCIGItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("TASK_RESULT_2_FLG_ESCL_CIG");
	}
	
	public boolean showMotivoEsclusioneCIGItem() {
		if(showFlgEscludiCIGItem() && !getValueAsBoolean("flgEscludiCIG")) {
			return false;
		}
		if(showFlgOpCommercialeItem() && !_FLG_SI.equals(getValueAsString("flgOpCommerciale"))) {
			return false;
		}
		return showAttributoCustomCablato("MOTIVO_ESCLUSIONE_CIG");
	}
	
	public String getTitleMotivoEsclusioneCIGItem() {
		String label = getLabelAttributoCustomCablato("MOTIVO_ESCLUSIONE_CIG");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Motivo esclusione CIG";
	}
	
	public boolean isRequiredMotivoEsclusioneCIGItem() {
		return showMotivoEsclusioneCIGItem() && getFlgObbligatorioAttributoCustomCablato("MOTIVO_ESCLUSIONE_CIG");
	}
	
	public String getDefaultValueMotivoEsclusioneCIGItem() {
		return getValoreFissoAttributoCustomCablato("MOTIVO_ESCLUSIONE_CIG");
	}
	
	public boolean showCIGItem() {
		if(showFlgEscludiCIGItem() && getValueAsBoolean("flgEscludiCIG")) {
			return false;
		}
		if(showFlgOpCommercialeItem() && !_FLG_SI.equals(getValueAsString("flgOpCommerciale"))) {
			return false;
		}
		return showAttributoCustomCablato("CIG");
	}
	
	public boolean isEsclusoCIG() {
		return (showAttributoCustomCablato("CIG") && !showCIGItem()) || !"".equals(getValueAsString("motivoEsclusioneCIG").trim());
	}
	
	protected void createCIGForm() {
		
		CIGForm = new DynamicForm();
		CIGForm.setValuesManager(vm);
		CIGForm.setWidth100();
		CIGForm.setPadding(5);
		CIGForm.setWrapItemTitles(false);
		CIGForm.setNumCols(20);
		CIGForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		CIGForm.setTabSet(tabSet);
		CIGForm.setTabID(_TAB_DATI_SCHEDA_ID);
		CIGForm.setHeight(1);		
				
		flgOpCommercialeItem = new RadioGroupItem("flgOpCommerciale", getTitleFlgOpCommercialeItem());
		flgOpCommercialeItem.setStartRow(true);
		flgOpCommercialeItem.setColSpan(1);
		String[] flgOpCommercialeValueMap = getValoriPossibiliFlgOpCommercialeItem();
		flgOpCommercialeItem.setValueMap(flgOpCommercialeValueMap);					
		if(flgOpCommercialeValueMap != null && flgOpCommercialeValueMap.length == 1) {
			flgOpCommercialeItem.setDefaultValue(flgOpCommercialeValueMap[0]);
		} else {
			flgOpCommercialeItem.setDefaultValue(getDefaultValueFlgOpCommercialeItem());
		}
		flgOpCommercialeItem.setVertical(false);
		flgOpCommercialeItem.setWrap(false);
		flgOpCommercialeItem.setShowDisabled(false);
		if(isRequiredFlgOpCommercialeItem()) {
			flgOpCommercialeItem.setAttribute("obbligatorio", true);
		}
		flgOpCommercialeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgOpCommercialeItem();
			}
		}));
		flgOpCommercialeItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgOpCommercialeItem();
			}
		});
		flgOpCommercialeItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {	
				CIGForm.markForRedraw();
			}
		});
		
		flgEscludiCIGItem = new CheckboxItem("flgEscludiCIG", getTitleFlgEscludiCIGItem());
		flgEscludiCIGItem.setDefaultValue(getDefaultValueAsBooleanFlgEscludiCIGItem());		
		flgEscludiCIGItem.setColSpan(1);
		flgEscludiCIGItem.setWidth("*");
		flgEscludiCIGItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgEscludiCIGItem();
			}
		});		
		flgEscludiCIGItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				CIGForm.markForRedraw();
			}
		});
		
		motivoEsclusioneCIGItem = new SelectItemValoriDizionario("motivoEsclusioneCIG", getTitleMotivoEsclusioneCIGItem(), "MOTIVO_ESCLUSIONE_CIG");
		motivoEsclusioneCIGItem.setDefaultValue(getDefaultValueMotivoEsclusioneCIGItem());
		motivoEsclusioneCIGItem.setStartRow(true);
		motivoEsclusioneCIGItem.setColSpan(20);
		motivoEsclusioneCIGItem.setWidth(500);		
		motivoEsclusioneCIGItem.setAllowEmptyValue(true);				
		motivoEsclusioneCIGItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredMotivoEsclusioneCIGItem();
			}
		}));
		motivoEsclusioneCIGItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredMotivoEsclusioneCIGItem()) {
					motivoEsclusioneCIGItem.setAttribute("obbligatorio", true);
					motivoEsclusioneCIGItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleMotivoEsclusioneCIGItem()));
				} else {
					motivoEsclusioneCIGItem.setAttribute("obbligatorio", false);
					motivoEsclusioneCIGItem.setTitle(getTitleMotivoEsclusioneCIGItem());
				}				
				return showMotivoEsclusioneCIGItem();
			}
		});
		
		listaCIGItem = new CIGItem();
		listaCIGItem.setName("listaCIG");
		listaCIGItem.setShowTitle(false);
		listaCIGItem.setColSpan(20);
		listaCIGItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showCIGItem();
			}
		});
				
		CIGForm.setFields(flgOpCommercialeItem, flgEscludiCIGItem, motivoEsclusioneCIGItem, listaCIGItem);			
	}
	
	/************************ 
	 * TAB DATI DISPOSITIVO *
	 ************************/
	
	public boolean showTabDatiDispositivo() {
		Integer maxNumValori = getMaxNumValoriAttributoCustomCablato("DATI_TESTO");
		boolean hideTabDatiDispositivo = maxNumValori != null && maxNumValori.intValue() == 0;
		return !hideTabDatiDispositivo;
	}
	
	public String getTitleTabDatiDispositivo() {
		String label = getLabelAttributoCustomCablato("DATI_TESTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiDispositivo_prompt();
	}
	
	/**
	 * Metodo per costruire il tab "Dati dispositivo"
	 * 
	 */
	protected void createTabDatiDispositivo() {

		tabDatiDispositivo = new Tab("<b>" + getTitleTabDatiDispositivo() + "</b>");
		tabDatiDispositivo.setAttribute("tabID", _TAB_DATI_DISPOSITIVO_ID);
		tabDatiDispositivo.setPrompt(getTitleTabDatiDispositivo());
		tabDatiDispositivo.setPane(createTabPane(getLayoutDatiDispositivo()));
	}

	/**
	 * Metodo che restituisce il layout del tab "Dati dispositivo"
	 * 
	 */
	public VLayout getLayoutDatiDispositivo() {

		VLayout layoutDatiDispositivo = new VLayout(5);
		
		createDetailSectionRiferimentiNormativi();
		layoutDatiDispositivo.addMember(detailSectionRiferimentiNormativi);
		
		createDetailSectionAttiPresupposti();		
		layoutDatiDispositivo.addMember(detailSectionAttiPresupposti);
		
		createDetailSectionMotivazioni();		
		layoutDatiDispositivo.addMember(detailSectionMotivazioni);

		createDetailSectionPremessa();
		layoutDatiDispositivo.addMember(detailSectionPremessa);

		createDetailSectionDispositivo();			
		layoutDatiDispositivo.addMember(detailSectionDispositivo);
	
		return layoutDatiDispositivo;
	}
	
	/******************************************** 
	 * DATI DISPOSITIVO - RIFERIMENTI NORMATIVI *
	 ********************************************/	
	
	public boolean showDetailSectionRiferimentiNormativi() {
		return showRiferimentiNormativiItem();
	}
	
	public String getTitleDetailSectionRiferimentiNormativi() {		
		return getTitleRiferimentiNormativiItem();
	}
	
	public boolean isRequiredDetailSectionRiferimentiNormativi() {
		return isRequiredRiferimentiNormativiItem();
	}
	
	protected void createDetailSectionRiferimentiNormativi() {
		
		createRiferimentiNormativiForm();
		
		detailSectionRiferimentiNormativi = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionRiferimentiNormativi(), true, true, isRequiredDetailSectionRiferimentiNormativi(), riferimentiNormativiForm);
	}
	
	public boolean showRiferimentiNormativiItem() {
		return showTabDatiDispositivo() && showAttributoCustomCablato("RIFERIMENTI_NORMATIVI");
	}
	
	public String getTitleRiferimentiNormativiItem() {
		String label = getLabelAttributoCustomCablato("RIFERIMENTI_NORMATIVI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Riferimenti normativi";
	}
	
	public boolean isRequiredRiferimentiNormativiItem() {
		return showRiferimentiNormativiItem() && getFlgObbligatorioAttributoCustomCablato("RIFERIMENTI_NORMATIVI");
	}
	
	protected void createRiferimentiNormativiForm() {
		
		riferimentiNormativiForm = new DynamicForm();
		riferimentiNormativiForm.setValuesManager(vm);
		riferimentiNormativiForm.setWidth100();
		riferimentiNormativiForm.setPadding(5);
		riferimentiNormativiForm.setWrapItemTitles(false);
		riferimentiNormativiForm.setNumCols(20);
		riferimentiNormativiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		riferimentiNormativiForm.setTabSet(tabSet);
		riferimentiNormativiForm.setTabID(_TAB_DATI_DISPOSITIVO_ID);
		riferimentiNormativiForm.setHeight(1);		
		
		listaRiferimentiNormativiItem = new RiferimentiNormativiItem() {
			
			@Override
			public boolean skipValidation() {
				if(showDetailSectionRiferimentiNormativi()) {
					return super.skipValidation();
				}
				return true;
			}
		};
		listaRiferimentiNormativiItem.setName("listaRiferimentiNormativi");
		listaRiferimentiNormativiItem.setShowTitle(false);
		listaRiferimentiNormativiItem.setColSpan(20);
		if(isRequiredRiferimentiNormativiItem()) {
			listaRiferimentiNormativiItem.setAttribute("obbligatorio", true);
		}
		listaRiferimentiNormativiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showRiferimentiNormativiItem();
			}
		});
		
		riferimentiNormativiForm.setFields(listaRiferimentiNormativiItem);			
	}
	
	/**************************************** 
	 * DATI DISPOSITIVO - ATTI PRESUPOPOSTI *
	 ****************************************/
	
	public boolean showDetailSectionAttiPresupposti() {
		return showAttiPresuppostiItem();
	}
	
	public String getTitleDetailSectionAttiPresupposti() {		
		return getTitleAttiPresuppostiItem();
	}
	
	public boolean isRequiredDetailSectionAttiPresupposti() {
		return isRequiredAttiPresuppostiItem();
	}
		
	protected void createDetailSectionAttiPresupposti() {
		
		createAttiPresuppostiForm();
		
		detailSectionAttiPresupposti = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionAttiPresupposti(), true, true, isRequiredDetailSectionAttiPresupposti(), attiPresuppostiForm);
	}
	
	public boolean showAttiPresuppostiItem() {
		return showTabDatiDispositivo() && showAttributoCustomCablato("ATTI_PRESUPPOSTI");
	}
	
	public String getTitleAttiPresuppostiItem() {
		String label = getLabelAttributoCustomCablato("ATTI_PRESUPPOSTI");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Atti presupposti";
	}
	
	public boolean isRequiredAttiPresuppostiItem() {
		return showAttiPresuppostiItem() && getFlgObbligatorioAttributoCustomCablato("ATTI_PRESUPPOSTI");
	}
	
	public int getAltezzaInRigheAttiPresuppostiItem() {
		Integer altezzaInRighe = getAltezzaInRigheAttributoCustomCablato("ATTI_PRESUPPOSTI");
		return altezzaInRighe != null ? altezzaInRighe.intValue() : 10;
	}
	
	protected void createAttiPresuppostiForm() {
		
		attiPresuppostiForm = new DynamicForm();
		attiPresuppostiForm.setValuesManager(vm);
		attiPresuppostiForm.setWidth100();
		attiPresuppostiForm.setPadding(5);
		attiPresuppostiForm.setWrapItemTitles(false);
		attiPresuppostiForm.setNumCols(20);
		attiPresuppostiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		attiPresuppostiForm.setTabSet(tabSet);
		attiPresuppostiForm.setTabID(_TAB_DATI_DISPOSITIVO_ID);
		attiPresuppostiForm.setHeight(1);		
		
//		listaAttiPresuppostiItem = new AttiPresuppostiItem() {
//		
//			@Override
//			public boolean skipValidation() {
//				if(showDetailSectionAttiPresupposti()) {
//					return super.skipValidation();
//				}
//				return true;
//			}
//		};
//		listaAttiPresuppostiItem.setName("listaAttiPresupposti");
//		listaAttiPresuppostiItem.setShowTitle(false);
//		listaAttiPresuppostiItem.setColSpan(20);
//		if(isRequiredDetailSectionAttiPresupposti()) {
//			listaAttiPresuppostiItem.setAttribute("obbligatorio", true);
//		}
//		
//		attiPresuppostiForm.setFields(listaAttiPresuppostiItem);			
		
		attiPresuppostiItem = new CKEditorItem("attiPresupposti", -1, "STANDARD", getAltezzaInRigheAttiPresuppostiItem(), -1, "") {
			
			@Override
			public Boolean validate() {
				if(showAttiPresuppostiItem()) {
					return super.validate();
				}
				return true;			
			}
		};
		attiPresuppostiItem.setShowTitle(false);
		attiPresuppostiItem.setColSpan(20);
		attiPresuppostiItem.setWidth("100%");
		attiPresuppostiItem.setRequired(isRequiredAttiPresuppostiItem());
		attiPresuppostiItem.setVisible(showAttiPresuppostiItem());			
		
		attiPresuppostiForm.setFields(attiPresuppostiItem);	
	}
	
	/********************************** 
	 * DATI DISPOSITIVO - MOTIVAZIONI *
	 **********************************/	
	
	public boolean showDetailSectionMotivazioni() {
		return showMotivazioniItem();
	}
	
	public String getTitleDetailSectionMotivazioni() {		
		return getTitleMotivazioniItem();
	}
	
	public boolean isRequiredDetailSectionMotivazioni() {
		return isRequiredMotivazioniItem();
	}
	
	protected void createDetailSectionMotivazioni() {
		
		createMotivazioniForm();
		
		detailSectionMotivazioni = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionMotivazioni(), true, true, isRequiredDetailSectionMotivazioni(), motivazioniForm);
	}
	
	public boolean showMotivazioniItem() {
		return showTabDatiDispositivo() && showAttributoCustomCablato("MOTIVAZIONI_ATTO");
	}
	
	public String getTitleMotivazioniItem() {
		String label = getLabelAttributoCustomCablato("MOTIVAZIONI_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Motivazioni";
	}
	
	public boolean isRequiredMotivazioniItem() {
		return showMotivazioniItem() && getFlgObbligatorioAttributoCustomCablato("MOTIVAZIONI_ATTO");
	}	
	
	public int getAltezzaInRigheMotivazioniItem() {
		Integer altezzaInRighe = getAltezzaInRigheAttributoCustomCablato("MOTIVAZIONI_ATTO");
		return altezzaInRighe != null ? altezzaInRighe.intValue() : 10;
	}
	
	protected void createMotivazioniForm() {
		
		motivazioniForm = new DynamicForm();
		motivazioniForm.setValuesManager(vm);
		motivazioniForm.setWidth100();
		motivazioniForm.setPadding(5);
		motivazioniForm.setWrapItemTitles(false);
		motivazioniForm.setNumCols(20);
		motivazioniForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		motivazioniForm.setTabSet(tabSet);
		motivazioniForm.setTabID(_TAB_DATI_DISPOSITIVO_ID);
		motivazioniForm.setHeight(1);		
		
		motivazioniItem = new CKEditorItem("motivazioni", -1, "STANDARD", getAltezzaInRigheMotivazioniItem(), -1, "") {
			
			@Override
			public Boolean validate() {
				if(showMotivazioniItem()) {
					return super.validate();
				}
				return true;			
			}
		};
		motivazioniItem.setShowTitle(false);
		motivazioniItem.setColSpan(20);
		motivazioniItem.setWidth("100%");
		motivazioniItem.setRequired(isRequiredMotivazioniItem());
		motivazioniItem.setVisible(showMotivazioniItem());	
				
		motivazioniForm.setFields(motivazioniItem);		
	}
	
	/******************************* 
	 * DATI DISPOSITIVO - PREMESSA *
	 *******************************/	
	
	public boolean showDetailSectionPremessa() {
		return showPremessaItem();
	}
	
	public String getTitleDetailSectionPremessa() {		
		return getTitlePremessaItem();
	}
	
	public boolean isRequiredDetailSectionPremessa() {
		return isRequiredPremessaItem();
	}
		
	protected void createDetailSectionPremessa() {
		
		createPremessaForm();
		
		detailSectionPremessa = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionPremessa(), true, true, isRequiredDetailSectionPremessa(), premessaForm);
	}
	
	public boolean showPremessaItem() {
		return showTabDatiDispositivo() && showAttributoCustomCablato("PREMESSA_ATTO");
	}
	
	public String getTitlePremessaItem() {
		String label = getLabelAttributoCustomCablato("PREMESSA_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Premessa";
	}
	
	public boolean isRequiredPremessaItem() {
		return showPremessaItem() && getFlgObbligatorioAttributoCustomCablato("PREMESSA_ATTO");
	}
	
	public int getAltezzaInRighePremessaItem() {
		Integer altezzaInRighe = getAltezzaInRigheAttributoCustomCablato("PREMESSA_ATTO");
		return altezzaInRighe != null ? altezzaInRighe.intValue() : 10;
	}
		
	protected void createPremessaForm() {
		
		premessaForm = new DynamicForm();
		premessaForm.setValuesManager(vm);
		premessaForm.setWidth100();
		premessaForm.setPadding(5);
		premessaForm.setWrapItemTitles(false);
		premessaForm.setNumCols(20);
		premessaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		premessaForm.setTabSet(tabSet);
		premessaForm.setTabID(_TAB_DATI_DISPOSITIVO_ID);
		premessaForm.setHeight(1);		
		
		premessaItem = new CKEditorItem("premessa", -1, "STANDARD", getAltezzaInRighePremessaItem(), -1, "") {
			
			@Override
			public Boolean validate() {
				if(showPremessaItem()) {
					return super.validate();
				}
				return true;			
			}
		};
		premessaItem.setShowTitle(false);
		premessaItem.setColSpan(20);
		premessaItem.setWidth("100%");
		premessaItem.setRequired(isRequiredPremessaItem());
		premessaItem.setVisible(showPremessaItem());	

		premessaForm.setFields(premessaItem);			
	}
	
	/********************************** 
	 * DATI DISPOSITIVO - DISPOSITIVO *
	 **********************************/
	
	public boolean showDetailSectionDispositivo() {
		return showDispositivoItem();
	}
	
	public String getTitleDetailSectionDispositivo() {		
		return getTitleDispositivoItem();
	}
	
	public boolean isRequiredDetailSectionDispositivo() {
		return isRequiredDispositivoItem();
	}
	
	protected void createDetailSectionDispositivo() {
		
		createDispositivoForm();
		
		detailSectionDispositivo = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionDispositivo(), true, true, isRequiredDetailSectionDispositivo(), dispositivoForm);
	}
		
	public boolean showDispositivoItem() {
		return showTabDatiDispositivo() && showAttributoCustomCablato("DISPOSITIVO_ATTO");
	}
	
	public String getTitleDispositivoItem() {
		String label = getLabelAttributoCustomCablato("DISPOSITIVO_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Dispositivo";
	}
	
	public boolean isRequiredDispositivoItem() {
		return showDispositivoItem() && getFlgObbligatorioAttributoCustomCablato("DISPOSITIVO_ATTO");
	}
	
	public int getAltezzaInRigheDispositivo() {
		Integer altezzaInRighe = getAltezzaInRigheAttributoCustomCablato("DISPOSITIVO_ATTO");
		return altezzaInRighe != null ? altezzaInRighe.intValue() : 10;
	}
	
	public boolean showLoghiAggiuntiviDispositivoItem() {
		return showTabDatiDispositivo() && showAttributoCustomCablato("LOGHI_DISPOSITIVO_ATTO");		
	}
	
	public String getTitleLoghiAggiuntiviDispositivoItem() {
		String label = getLabelAttributoCustomCablato("LOGHI_DISPOSITIVO_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Loghi aggiuntivi da inserire";
	}
	
	protected void createDispositivoForm() {
		
		dispositivoForm = new DynamicForm();
		dispositivoForm.setValuesManager(vm);
		dispositivoForm.setWidth100();
		dispositivoForm.setPadding(5);
		dispositivoForm.setWrapItemTitles(false);
		dispositivoForm.setNumCols(20);
		dispositivoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		dispositivoForm.setTabSet(tabSet);
		dispositivoForm.setTabID(_TAB_DATI_DISPOSITIVO_ID);
		dispositivoForm.setHeight(1);		
		
		dispositivoItem = new CKEditorItem("dispositivo", -1, "STANDARD", getAltezzaInRigheDispositivo(), -1, "") {
			
			@Override
			public Boolean validate() {
				if(showDispositivoItem()) {
					return super.validate();
				}
				return true;			
			}
		};
		dispositivoItem.setShowTitle(false);
		dispositivoItem.setColSpan(20);
		dispositivoItem.setWidth("100%");
		dispositivoItem.setRequired(isRequiredDispositivoItem());
		dispositivoItem.setVisible(showDispositivoItem());	
		
		loghiAggiuntiviDispositivoItem = new SelectItemValoriDizionario("loghiAggiuntiviDispositivo", getTitleLoghiAggiuntiviDispositivoItem(), "LOGHI_X_TEMPLATE_DOC");
		loghiAggiuntiviDispositivoItem.setStartRow(true);
		loghiAggiuntiviDispositivoItem.setWidth(500);
		loghiAggiuntiviDispositivoItem.setAllowEmptyValue(true);
		loghiAggiuntiviDispositivoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showLoghiAggiuntiviDispositivoItem();
			}
		});
		
		dispositivoForm.setFields(dispositivoItem, loghiAggiuntiviDispositivoItem);			
	}
	
	/************************** 
	 * TAB DATI DISPOSITIVO 2 *
	 **************************/
	
	public boolean showTabDatiDispositivo2() { 
		return showAttributoCustomCablato("DATI_TESTO_2");
	}
	
	public String getTitleTabDatiDispositivo2() {
		String label = getLabelAttributoCustomCablato("DATI_TESTO_2");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiDispositivo2_prompt();
	}
	
	/**
	 * Metodo per costruire il tab "Dati dispositivo 2"
	 * 
	 */
	protected void createTabDatiDispositivo2() {

		tabDatiDispositivo2 = new Tab("<b>" + getTitleTabDatiDispositivo2() + "</b>");
		tabDatiDispositivo2.setAttribute("tabID", _TAB_DATI_DISPOSITIVO_2_ID);
		tabDatiDispositivo2.setPrompt(getTitleTabDatiDispositivo2());
		tabDatiDispositivo2.setPane(createTabPane(getLayoutDatiDispositivo2()));
	}

	/**
	 * Metodo che restituisce il layout del tab "Dati dispositivo 2"
	 * 
	 */
	public VLayout getLayoutDatiDispositivo2() {

		VLayout layoutDatiDispositivo2 = new VLayout(5);
		
		createDetailSectionPremessa2();
		layoutDatiDispositivo2.addMember(detailSectionPremessa2);

		createDetailSectionDispositivo2();			
		layoutDatiDispositivo2.addMember(detailSectionDispositivo2);
	
		return layoutDatiDispositivo2;
	}
	
	/*********************************** 
	 * DATI DISPOSITIVO 2 - PREMESSA 2 *
	 ***********************************/	
	
	public boolean showDetailSectionPremessa2() {
		return showPremessa2Item();
	}
	
	public String getTitleDetailSectionPremessa2() {		
		return getTitlePremessa2Item();
	}
	
	public boolean isRequiredDetailSectionPremessa2() {
		return isRequiredPremessa2Item();
	}
		
	protected void createDetailSectionPremessa2() {
		
		createPremessa2Form();
		
		detailSectionPremessa2 = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionPremessa2(), true, true, isRequiredDetailSectionPremessa2(), premessa2Form);
	}
	
	public boolean showPremessa2Item() {
		return showTabDatiDispositivo2() && showAttributoCustomCablato("PREMESSA_ATTO_2");
	}
	
	public String getTitlePremessa2Item() {
		String label = getLabelAttributoCustomCablato("PREMESSA_ATTO_2");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Premessa 2";
	}
	
	public boolean isRequiredPremessa2Item() {
		return showPremessa2Item() && getFlgObbligatorioAttributoCustomCablato("PREMESSA_ATTO_2");
	}
	
	public int getAltezzaInRighePremessa2Item() {
		Integer altezzaInRighe = getAltezzaInRigheAttributoCustomCablato("PREMESSA_ATTO_2");
		return altezzaInRighe != null ? altezzaInRighe.intValue() : 10;
	}
		
	protected void createPremessa2Form() {
		
		premessa2Form = new DynamicForm();
		premessa2Form.setValuesManager(vm);
		premessa2Form.setWidth100();
		premessa2Form.setPadding(5);
		premessa2Form.setWrapItemTitles(false);
		premessa2Form.setNumCols(20);
		premessa2Form.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		premessa2Form.setTabSet(tabSet);
		premessa2Form.setTabID(_TAB_DATI_DISPOSITIVO_2_ID);
		premessa2Form.setHeight(1);		
		
		premessa2Item = new CKEditorItem("premessa2", -1, "STANDARD", getAltezzaInRighePremessa2Item(), -1, "") {
			
			@Override
			public Boolean validate() {
				if(showPremessa2Item()) {
					return super.validate();
				}
				return true;			
			}
		};
		premessa2Item.setShowTitle(false);
		premessa2Item.setColSpan(20);
		premessa2Item.setWidth("100%");
		premessa2Item.setRequired(isRequiredPremessa2Item());
		premessa2Item.setVisible(showPremessa2Item());	

		premessa2Form.setFields(premessa2Item);			
	}
	
	/************************************** 
	 * DATI DISPOSITIVO 2 - DISPOSITIVO 2 *
	 **************************************/
	
	public boolean showDetailSectionDispositivo2() {
		return showDispositivo2Item();
	}
	
	public String getTitleDetailSectionDispositivo2() {		
		return getTitleDispositivo2Item();
	}
	
	public boolean isRequiredDetailSectionDispositivo2() {
		return isRequiredDispositivo2Item();
	}
	
	protected void createDetailSectionDispositivo2() {
		
		createDispositivo2Form();
		
		detailSectionDispositivo2 = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionDispositivo2(), true, true, isRequiredDetailSectionDispositivo2(), dispositivo2Form);
	}
		
	public boolean showDispositivo2Item() {
		return showTabDatiDispositivo2() && showAttributoCustomCablato("DISPOSITIVO_ATTO_2");
	}
	
	public String getTitleDispositivo2Item() {
		String label = getLabelAttributoCustomCablato("DISPOSITIVO_ATTO_2");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Dispositivo 2";
	}
	
	public boolean isRequiredDispositivo2Item() {
		return showDispositivo2Item() && getFlgObbligatorioAttributoCustomCablato("DISPOSITIVO_ATTO_2");
	}
	
	public int getAltezzaInRigheDispositivo2() {
		Integer altezzaInRighe = getAltezzaInRigheAttributoCustomCablato("DISPOSITIVO_ATTO_2");
		return altezzaInRighe != null ? altezzaInRighe.intValue() : 10;
	}
	
	protected void createDispositivo2Form() {
		
		dispositivo2Form = new DynamicForm();
		dispositivo2Form.setValuesManager(vm);
		dispositivo2Form.setWidth100();
		dispositivo2Form.setPadding(5);
		dispositivo2Form.setWrapItemTitles(false);
		dispositivo2Form.setNumCols(20);
		dispositivo2Form.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		dispositivo2Form.setTabSet(tabSet);
		dispositivo2Form.setTabID(_TAB_DATI_DISPOSITIVO_2_ID);
		dispositivo2Form.setHeight(1);		
		
		dispositivo2Item = new CKEditorItem("dispositivo2", -1, "STANDARD", getAltezzaInRigheDispositivo2(), -1, "") {
			
			@Override
			public Boolean validate() {
				if(showDispositivo2Item()) {
					return super.validate();
				}
				return true;			
			}
		};
		dispositivo2Item.setShowTitle(false);
		dispositivo2Item.setColSpan(20);
		dispositivo2Item.setWidth("100%");
		dispositivo2Item.setRequired(isRequiredDispositivo2Item());
		dispositivo2Item.setVisible(showDispositivo2Item());	
		
		dispositivo2Form.setFields(dispositivo2Item);			
	}
	
	/**************** 
	 * TAB ALLEGATI *
	 ****************/	
	
	public String getTitleTabAllegati() {
		String label = AurigaLayout.getParametroDB("LABEL_TAB_ALLEG_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabAllegatiPareri_prompt();
	}
	
	public Boolean getFlgAllegAttoParteIntDefaultXTipoAtto() {
		return flgAllegAttoParteIntDefaultXTipoAtto != null ? flgAllegAttoParteIntDefaultXTipoAtto : AurigaLayout.getParametroDBAsBoolean("FLG_ALLEG_ATTO_PARTE_INT_DEFAULT");
	}
	
	public Boolean getFlgAllegAttoParteIntDefaultOrdPermanente() {
		return flgAllegAttoParteIntDefaultOrdPermanente != null ? flgAllegAttoParteIntDefaultOrdPermanente : getFlgAllegAttoParteIntDefaultXTipoAtto();
	}
	
	public Boolean getFlgAllegAttoParteIntDefaultOrdTemporanea() {
		return flgAllegAttoParteIntDefaultOrdTemporanea != null ? flgAllegAttoParteIntDefaultOrdTemporanea : getFlgAllegAttoParteIntDefaultXTipoAtto();
	}
	
	public Boolean getFlgAllegAttoPubblSepDefaultXTipoAtto() {
		return flgAllegAttoPubblSepDefaultXTipoAtto != null ? flgAllegAttoPubblSepDefaultXTipoAtto : AurigaLayout.getParametroDBAsBoolean("FLG_ALLEG_ATTO_PUBBL_SEPARATA_DEFAULT");
	}
	
	public boolean hasModelloAllegatiParteIntSeparatiXPubbl() {
		return false;					
	}
	
	/**
	 * Metodo per costruire il tab "Allegati"
	 * 
	 */
	protected void createTabAllegati() {

		tabAllegati = new Tab("<b>" + getTitleTabAllegati() + "</b>");
		tabAllegati.setAttribute("tabID", _TAB_ALLEGATI_ID);
		tabAllegati.setPrompt(getTitleTabAllegati());
		
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_GRID_ALLEGATI_IN_ATTI")) {
			// faccio in modo che la lista occupi tutto lo spazio disponibile
			VLayout layoutAllegati = getLayoutAllegati();
			layoutAllegati.setHeight100();
			VLayout layoutTabAllegati = new VLayout();
			layoutTabAllegati.setWidth100();
			layoutTabAllegati.setHeight100();
			layoutTabAllegati.addMember(layoutAllegati);
			layoutTabAllegati.setRedrawOnResize(true);
			tabAllegati.setPane(layoutTabAllegati);
		} else {
			tabAllegati.setPane(createTabPane(getLayoutAllegati()));
		}
	}

	/**
	 * Metodo che restituisce il layout del tab "Allegati"
	 * 
	 */
	public VLayout getLayoutAllegati() {

		VLayout layoutAllegati = new VLayout(5);
				
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_GRID_ALLEGATI_IN_ATTI")) {
			createAllegatiForm();
			layoutAllegati.addMember(allegatiForm);
		} else {
			createDetailSectionAllegati();
			layoutAllegati.addMember(detailSectionAllegati);	
		}
		
		return layoutAllegati;
	}
		
	protected void createDetailSectionAllegati() {
		
		createAllegatiForm();
		
		detailSectionAllegati = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionAllegati_title(), true, true, false, allegatiForm);		
	}
	
	protected void createAllegatiForm() {
		
		allegatiForm = new DynamicForm();
		allegatiForm.setValuesManager(vm);
		allegatiForm.setWidth100();
		allegatiForm.setPadding(5);
		allegatiForm.setWrapItemTitles(false);
		allegatiForm.setNumCols(20);
		allegatiForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		allegatiForm.setTabSet(tabSet);
		allegatiForm.setTabID(_TAB_ALLEGATI_ID);
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_GRID_ALLEGATI_IN_ATTI")) {
			// faccio in modo che la lista occupi tutto lo spazio disponibile
			allegatiForm.setHeight100();
		} else {
			allegatiForm.setHeight(1);		
		}
			
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_GRID_ALLEGATI_IN_ATTI")) {
			/* NUOVA GESTIONE ALLEGATI CON GRIDITEM */
			listaAllegatiItem = new AllegatiGridItem("listaAllegati", "listaAllegatiAtto") {
	
				@Override
				public String getIdProcess() {
					return getIdProcessTask();
				}
	
				@Override
				public String getIdProcessType() {
					return getIdProcessTypeTask();
				}
	
				@Override
				public String getIdTaskCorrenteAllegati() {
					return getIdTaskCorrente();
				}
	
				@Override
				public HashSet<String> getTipiModelliAttiAllegati() {
					return getTipiModelliAttiInAllegati();
				}
				
				@Override
				public Record getDetailRecord() {
					return getDetailRecordInAllegati();
				}
								
				@Override
				public boolean isObbligatorioFile() {
					return isObbligatorioFileInAllegati();
				}
	
				@Override
				public boolean isFormatoAmmessoParteIntegrante(InfoFileRecord info) {
					return isFormatoAmmessoParteIntegranteInAllegati(info);
				}
				
				@Override
				public String getRifiutoAllegatiConFirmeNonValide() {
					return getRifiutoAllegatiConFirmeNonValideInAllegati();
				}
				
				@Override
				public boolean isDisattivaUnioneAllegatiFirmati() {
					return isDisattivaUnioneAllegatiFirmatiInAllegati();
				}
				
				@Override
				public boolean isPubblicazioneSeparataPdfProtetti() {
					return isPubblicazioneSeparataPdfProtettiInAllegati();
				}
				
				@Override
				public boolean isAttivaVociBarcode() {
					String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
					return idUd != null && !"".equals(idUd);
				}								
				
				@Override
				public boolean getShowGeneraDaModello() {
					return true;
				}
				
				@Override
				public void caricaModelloAllegato(String idModello, String tipoModello, String flgConvertiInPdf, final ServiceCallback<Record> callback) {
					caricaModelloInAllegati(idModello, tipoModello, flgConvertiInPdf, callback);
				}
	
				@Override
				public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
					return getRecordCaricaModelloInAllegati(idModello, tipoModello);
				}
				
				@Override
				public boolean getFlgAllegAttoParteIntDefault() {
					if(showTipoOrdMobilitaItem() && _PERMANENTE.equalsIgnoreCase(getValueAsString("tipoOrdMobilita"))) {
						return getFlgAllegAttoParteIntDefaultOrdPermanente();
					}					
					if(showTipoOrdMobilitaItem() && _TEMPORANEA.equalsIgnoreCase(getValueAsString("tipoOrdMobilita"))) {
						return getFlgAllegAttoParteIntDefaultOrdTemporanea();
					}
					return getFlgAllegAttoParteIntDefaultXTipoAtto();
				}
				
				@Override
				public boolean getFlgAllegAttoPubblSepDefault() {
					return getFlgAllegAttoPubblSepDefaultXTipoAtto();
				}
				
				@Override
				public Integer getWidthDescrizioneFileAllegato() {
					return 250;
				}
				
				@Override
				public Integer getWidthNomeFileAllegato() {
					return 250;
				}
				
				@Override
				public boolean getShowFlgDatiProtettiTipo1() {
					return showAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_1_SU_ALLEG");
				}
				
				@Override
				public String getTitleFlgDatiProtettiTipo1() {
					String label = getLabelAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_1_SU_ALLEG");
					if(label != null && !"".equals(label)) {
						return label;
					}
					return "dati protetti tipo 1";
				}
				
				@Override
				public boolean getShowFlgDatiProtettiTipo2() {
					return showAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_2_SU_ALLEG");
				}
				
				@Override
				public String getTitleFlgDatiProtettiTipo2() {
					String label = getLabelAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_2_SU_ALLEG");
					if(label != null && !"".equals(label)) {
						return label;
					}
					return "dati protetti tipo 2";
				}
				
				@Override
				public boolean getShowFlgDatiProtettiTipo3() {
					return showAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_3_SU_ALLEG");
				}
				
				@Override
				public String getTitleFlgDatiProtettiTipo3() {
					String label = getLabelAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_3_SU_ALLEG");
					if(label != null && !"".equals(label)) {
						return label;
					}
					return "dati protetti tipo 3";
				}
				
				@Override
				public boolean getShowFlgDatiProtettiTipo4() {
					return showAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_4_SU_ALLEG");
				}
				
				@Override
				public String getTitleFlgDatiProtettiTipo4() {
					String label = getLabelAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_4_SU_ALLEG");
					if(label != null && !"".equals(label)) {
						return label;
					}
					return "dati protetti tipo 4";
				}
				
				@Override
				public boolean getShowVersioneOmissis() {
					return true;
				}
				
				@Override
				public boolean getShowFlgProvEsterna() {
					return AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PROV_ALLEG_ATTI");
				}
				
				@Override
				public boolean getShowFlgParere() {
					return true;
				}
				
				@Override
				public boolean getSortByFlgParteDispositivo() {
					return !AurigaLayout.getParametroDBAsBoolean("DISATTIVA_ORD_PARTE_INT_IN_GRID_ALLEG_ATTI");
				}
				
				@Override
				public boolean getShowFlgParteDispositivo() {
					return true;
				}
				
				@Override
				public boolean getShowFlgNoPubblAllegato() {
					return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ESCL_PUBBL_FILE_IN_ATTI");
				}
				
				@Override
				public boolean getShowFlgPubblicaSeparato() {			
					return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PUBBL_SEPARATA_FILE_IN_ATTI");
				}
				
				@Override
				public boolean getFlgPubblicazioneAllegatiUguale() {
					return flgPubblicazioneAllegatiUguale != null && flgPubblicazioneAllegatiUguale;
				}
				
				@Override
				public boolean getFlgSoloPreparazioneVersPubblicazione() {
					return flgSoloPreparazioneVersPubblicazione != null && flgSoloPreparazioneVersPubblicazione;
				}
				
				@Override
				public boolean getShowImportaFileDaDocumenti() {
					return true;
				}
				
				@Override
				public boolean getShowCollegaDocumentiImportati() {
					return false;
				}			
				
				@Override
				public long getDimAlertAllegato() {
					return AurigaLayout.getParametroDB("DIM_ALERT_ALLEGATO_ATTO") != null && !"".equals(AurigaLayout.getParametroDB("DIM_ALERT_ALLEGATO_ATTO")) ? Long.parseLong(AurigaLayout.getParametroDB("DIM_ALERT_ALLEGATO_ATTO")) : -1;
				}		
				
				@Override
				public long getDimMaxAllegatoXPubbl() {
					return AurigaLayout.getParametroDB("MAX_DIM_ALLEG_ATTO_X_PUBBL") != null && !"".equals(AurigaLayout.getParametroDB("MAX_DIM_ALLEG_ATTO_X_PUBBL")) ? Long.parseLong(AurigaLayout.getParametroDB("MAX_DIM_ALLEG_ATTO_X_PUBBL")) : -1;
				}	
	
				@Override
				public boolean isGrigliaEditabile() {
					return true;
				}
				
				@Override
				public boolean getShowTimbraBarcodeMenuOmissis() {
					return true;
				}
				
				@Override
				public String getIdUd() {
					return idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;
				}
				
				@Override
				public boolean hasListaAllegatiParteIntSeparatiXPubbl() {
					return hasModelloAllegatiParteIntSeparatiXPubbl();
				}
			};
			listaAllegatiItem.setShowTitle(false);
			listaAllegatiItem.setColSpan(20);
			listaAllegatiItem.setHeight("95%");
		} else {
			/* VECCHIA GESTIONE ALLEGATI CON REPLICABLEITEM */		
			listaAllegatiItem = new AllegatiItem() {								
	
				@Override
				public String getIdProcess() {
					return getIdProcessTask();
				}
	
				@Override
				public String getIdProcessType() {
					return getIdProcessTypeTask();
				}
	
				@Override
				public String getIdTaskCorrenteAllegati() {
					return getIdTaskCorrente();
				}
	
				@Override
				public HashSet<String> getTipiModelliAttiAllegati() {
					return getTipiModelliAttiInAllegati();
				}
				
				@Override
				public boolean isObbligatorioFile() {
					return isObbligatorioFileInAllegati();
				}
				
				@Override
				public boolean isFormatoAmmessoParteIntegrante(InfoFileRecord info) {
					return isFormatoAmmessoParteIntegranteInAllegati(info);
				}
				
				@Override
				public String getRifiutoAllegatiConFirmeNonValide() {
					return getRifiutoAllegatiConFirmeNonValideInAllegati();
				}
				
				@Override
				public boolean isDisattivaUnioneAllegatiFirmati() {
					return isDisattivaUnioneAllegatiFirmatiInAllegati();
				}
				
				@Override
				public boolean isPubblicazioneSeparataPdfProtetti() {
					return isPubblicazioneSeparataPdfProtettiInAllegati();
				}
				
				@Override
				public Record getDetailRecord() {
					return getDetailRecordInAllegati();
				}
				
				@Override
				public boolean isAttivaVociBarcode() {
					String idUd = (idUdHiddenItem.getValue() != null) ? String.valueOf(idUdHiddenItem.getValue()) : null;
					return idUd != null && !"".equals(idUd);
				}
				
				@Override
				public boolean showGeneraDaModello() {
					return true;
				}
				
				@Override
				public void caricaModelloAllegato(String idModello, String tipoModello, String flgConvertiInPdf, final ServiceCallback<Record> callback) {
					caricaModelloInAllegati(idModello, tipoModello, flgConvertiInPdf, callback);
				}
	
				@Override
				public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
					return getRecordCaricaModelloInAllegati(idModello, tipoModello);
				}
				
				@Override
				public boolean getFlgAllegAttoParteIntDefault() {
					if(showTipoOrdMobilitaItem() && _PERMANENTE.equalsIgnoreCase(getValueAsString("tipoOrdMobilita"))) {
						return getFlgAllegAttoParteIntDefaultOrdPermanente();
					}					
					if(showTipoOrdMobilitaItem() && _TEMPORANEA.equalsIgnoreCase(getValueAsString("tipoOrdMobilita"))) {
						return getFlgAllegAttoParteIntDefaultOrdTemporanea();
					}
					return getFlgAllegAttoParteIntDefaultXTipoAtto();
				}
				
				@Override
				public boolean getFlgAllegAttoPubblSepDefault() {
					return getFlgAllegAttoPubblSepDefaultXTipoAtto();
				}
				
				@Override
				public String getTitleFlgParteDispositivo() {
					return I18NUtil.getMessages().nuovaPropostaAtto2_detail_allegati_flgParteIntegrante_title();
				}
				
				@Override
				public Integer getWidthDescrizioneFileAllegato() {
					return 250;
				}
				
				@Override
				public Integer getWidthNomeFileAllegato() {
					return 250;
				}
				
				@Override
				public boolean getShowVersioneOmissis() {
					return true;
				}			
				
				@Override
				public long getDimAlertAllegato() {
					return AurigaLayout.getParametroDB("DIM_ALERT_ALLEGATO_ATTO") != null && !"".equals(AurigaLayout.getParametroDB("DIM_ALERT_ALLEGATO_ATTO")) ? Long.parseLong(AurigaLayout.getParametroDB("DIM_ALERT_ALLEGATO_ATTO")) : -1;
				}
				
				@Override
				public long getDimMaxAllegatoXPubbl() {
					return AurigaLayout.getParametroDB("MAX_DIM_ALLEG_ATTO_X_PUBBL") != null && !"".equals(AurigaLayout.getParametroDB("MAX_DIM_ALLEG_ATTO_X_PUBBL")) ? Long.parseLong(AurigaLayout.getParametroDB("MAX_DIM_ALLEG_ATTO_X_PUBBL")) : -1;
				}	
				
				@Override
				public boolean showTimbraBarcodeMenuOmissis() {
					return true;
				}
				
				@Override
				public String getIdUd() {
					return idUdHiddenItem.getValue() != null ? String.valueOf(idUdHiddenItem.getValue()) : null;
				}
				
				@Override
				public boolean hasListaAllegatiParteIntSeparatiXPubbl() {
					return hasModelloAllegatiParteIntSeparatiXPubbl();					
				}
			};
			listaAllegatiItem.setName("listaAllegati");
			listaAllegatiItem.setShowTitle(false);
			listaAllegatiItem.setColSpan(20);
			((AllegatiItem)listaAllegatiItem).setShowFlgProvEsterna(AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PROV_ALLEG_ATTI"));
			((AllegatiItem)listaAllegatiItem).setShowFlgParere(true);
			((AllegatiItem)listaAllegatiItem).setShowFlgParteDispositivo(true);
			((AllegatiItem)listaAllegatiItem).setShowFlgNoPubblAllegato(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ESCL_PUBBL_FILE_IN_ATTI"));	
			((AllegatiItem)listaAllegatiItem).setShowFlgPubblicaSeparato(AurigaLayout.getParametroDBAsBoolean("ATTIVA_PUBBL_SEPARATA_FILE_IN_ATTI"));
			((AllegatiItem)listaAllegatiItem).setFlgPubblicazioneAllegatiUguale(flgPubblicazioneAllegatiUguale != null && flgPubblicazioneAllegatiUguale);
			((AllegatiItem)listaAllegatiItem).setFlgSoloPreparazioneVersPubblicazione(flgSoloPreparazioneVersPubblicazione != null && flgSoloPreparazioneVersPubblicazione);
			((AllegatiItem)listaAllegatiItem).setShowImportaFileDaDocumenti(true);
			((AllegatiItem)listaAllegatiItem).setShowCollegaDocumentiImportati(false);
		}
		
		allegatiForm.setFields(listaAllegatiItem);				
	}
	
	public boolean isFormatoAmmessoParteIntegranteInAllegati(InfoFileRecord info) {
		String mimetype = info != null ? info.getMimetype() : "";
		String mimetypeAmmessiAllegatiParteIntegranteAtti = AurigaLayout.getParametroDB("MIMETYPE_AMM_ALL_PI_ATTO");
		if(mimetypeAmmessiAllegatiParteIntegranteAtti != null && !"".equals(mimetypeAmmessiAllegatiParteIntegranteAtti)) {
			String modalitaControlloMimetypeAllegatiParteIntegranteAtti = AurigaLayout.getParametroDB("MOD_CTRL_MIMETYPE_ALL_PI_ATTO");
			if(modalitaControlloMimetypeAllegatiParteIntegranteAtti != null && "sempre".equalsIgnoreCase(modalitaControlloMimetypeAllegatiParteIntegranteAtti)) {				
				StringSplitterClient st = new StringSplitterClient(mimetypeAmmessiAllegatiParteIntegranteAtti, ";");
				for(int i = 0; i < st.getTokens().length; i++) {
					if(mimetype.equals(st.getTokens()[i])) {
						return true;
					}
				}
				return false;			
			}
		}
		return true;
	}
	
	public String getRifiutoAllegatiConFirmeNonValideInAllegati() {
		return AurigaLayout.getParametroDB("RIFIUTO_ALLEGATI_ATTI_CON_FIRME_NON_VALIDE");
	}
	
	public boolean isDisattivaUnioneAllegatiFirmatiInAllegati() {
		return AurigaLayout.getParametroDBAsBoolean("DISATTIVA_UNIONE_ALLEGATI_ATTI_FIRMATI");
	}
	
	public boolean isPubblicazioneSeparataPdfProtettiInAllegati() {
		// Non serve più controllare se un pdf è protetto dato che con la variabile unethicalreading di itext ora si riesce a manipolarli
		return false;
	}
	
	/******************************* 
	 * TAB PUBBLICAZIONE/NOTIFICHE *
	 *******************************/	
	
	public boolean showTabPubblicazioneNotifiche() {
		return showAttributoCustomCablato("DATI_PUBBL");
	}
	
	public String getTitleTabPubblicazioneNotifiche() {
		String label = getLabelAttributoCustomCablato("DATI_PUBBL");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Pubblicazione/notifiche";
	}
	
	/**
	 * Metodo per costruire il tab "Pubblicazione/notifiche"
	 * 
	 */
	protected void createTabPubblicazioneNotifiche() {

		tabPubblicazioneNotifiche = new Tab("<b>" + getTitleTabPubblicazioneNotifiche() + "</b>");
		tabPubblicazioneNotifiche.setAttribute("tabID", _TAB_DATI_PUBBL_ID);
		tabPubblicazioneNotifiche.setPrompt(getTitleTabPubblicazioneNotifiche());
		tabPubblicazioneNotifiche.setPane(createTabPane(getLayoutPubblicazioneNotifiche()));
	}

	/**
	 * Metodo che restituisce il layout del tab "Pubblicazione/notifiche"
	 * 
	 */
	public VLayout getLayoutPubblicazioneNotifiche() {

		VLayout layoutPubblicazioneNotifiche = new VLayout(5);
		
		createDetailSectionPubblAlbo();
		layoutPubblicazioneNotifiche.addMember(detailSectionPubblAlbo);
				
		createDetailSectionPubblAmmTrasp();
		layoutPubblicazioneNotifiche.addMember(detailSectionPubblAmmTrasp);
		
		createDetailSectionPubblBUR();
		layoutPubblicazioneNotifiche.addMember(detailSectionPubblBUR);
		
		createDetailSectionPubblNotiziario();
		layoutPubblicazioneNotifiche.addMember(detailSectionPubblNotiziario);
		
		createDetailSectionEsecutivita();
		layoutPubblicazioneNotifiche.addMember(detailSectionEsecutivita);		

		createDetailSectionNotifiche();
		layoutPubblicazioneNotifiche.addMember(detailSectionNotifiche);
		
		return layoutPubblicazioneNotifiche;
	}
	
	/**************************************************** 
	 * PUBBLICAZIONE/NOTIFICHE - PUBBLICAZIONE ALL'ALBO *
	 ****************************************************/
	
	public boolean showDetailSectionPubblAlbo() {
		return showTabPubblicazioneNotifiche() && showAttributoCustomCablato("DATI_PUBBL_ALBO");
	}
	
	public String getTitleDetailSectionPubblAlbo() {
		String label = getLabelAttributoCustomCablato("DATI_PUBBL_ALBO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Pubblicazione all'Albo";
	}
	
	public boolean showFlgPubblAlboItem() {
		return showDetailSectionPubblAlbo() && showAttributoCustomCablato("FLG_PUBBL_ALBO");
	}
	
	public String getTitleFlgPubblAlboItem() {
		String label = getLabelAttributoCustomCablato("FLG_PUBBL_ALBO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Pubblicazione all'Albo";
	}
	
	public boolean isRequiredFlgPubblAlboItem() {
		return showFlgPubblAlboItem() && getFlgObbligatorioAttributoCustomCablato("FLG_PUBBL_ALBO");
	}
	
	public String getDefaultValueFlgPubblAlboItem() {
		return getValoreFissoAttributoCustomCablato("FLG_PUBBL_ALBO");
	}
		
	public boolean showNumGiorniPubblAlboItem() {
		return showDetailSectionPubblAlbo() && (!showFlgPubblAlboItem() || isPubblAlbo()) && showAttributoCustomCablato("NRO_GIORNI_PUBBL_ALBO");
	}
	
	public String getTitleNumGiorniPubblAlboItem() {
		String label = getLabelAttributoCustomCablato("NRO_GIORNI_PUBBL_ALBO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "N° giorni pubblicazione";
	}
	
	public boolean isRequiredNumGiorniPubblAlboItem() {
		return showNumGiorniPubblAlboItem() && getFlgObbligatorioAttributoCustomCablato("NRO_GIORNI_PUBBL_ALBO");
	}
	
	public String getDefaultValueNumGiorniPubblAlboItem() {
		return getValoreFissoAttributoCustomCablato("NRO_GIORNI_PUBBL_ALBO");
	}
	
	public boolean showTipoDecorrenzaPubblAlboItem() {
		return showDetailSectionPubblAlbo() && (!showFlgPubblAlboItem() || isPubblAlbo()) && showAttributoCustomCablato("TIPO_DECORRENZA_PUBBL_ALBO");
	}
	
	public String getTitleTipoDecorrenzaPubblAlboItem() {
		String label = getLabelAttributoCustomCablato("TIPO_DECORRENZA_PUBBL_ALBO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Decorrenza pubblicazione"; 		
	}
	
	public HashMap<String, String> getValueMapTipoDecorrenzaPubblAlboItem() {
		return getValueMapAttributoCustomCablato("TIPO_DECORRENZA_PUBBL_ALBO");
	}
	
	public String getDefaultValueTipoDecorrenzaPubblAlboItem() {
		return getValoreFissoAttributoCustomCablato("TIPO_DECORRENZA_PUBBL_ALBO");
	}
	
	public boolean showDataPubblAlboDalItem() {
		return showDetailSectionPubblAlbo() && (!showFlgPubblAlboItem() || isPubblAlbo()) && isDecorrenzaPubblAlboPosticipata() && showAttributoCustomCablato("PUBBL_ALBO_DAL");
	}
	
	public String getTitleDataPubblAlboDalItem() {
		String label = getLabelAttributoCustomCablato("PUBBL_ALBO_DAL");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "a partire dal";
	}
	
	public boolean isRequiredDataPubblAlboDalItem() {
		return showDataPubblAlboDalItem() && getFlgObbligatorioAttributoCustomCablato("PUBBL_ALBO_DAL");
	}
	
	public boolean showFlgUrgentePubblAlboItem() {
		return showDetailSectionPubblAlbo() && (!showFlgPubblAlboItem() || isPubblAlbo()) && showAttributoCustomCablato("FLG_URGENTE_PUBBL_ALBO");
	}
	
	public String getTitleFlgUrgentePubblAlboItem() {
		String label = getLabelAttributoCustomCablato("FLG_URGENTE_PUBBL_ALBO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "pubblicazione urgente";
	}
	
	public boolean getDefaultValueAsBooleanFlgUrgentePubblAlboItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("FLG_URGENTE_PUBBL_ALBO");
	}
	
	public boolean showDataPubblAlboEntroItem() {
		return showDetailSectionPubblAlbo() && (!showFlgPubblAlboItem() || isPubblAlbo()) && isUrgentePubblAlbo() && showAttributoCustomCablato("PUBBL_ALBO_ENTRO");
	}
	
	public String getTitleDataPubblAlboEntroItem() {
		String label = getLabelAttributoCustomCablato("PUBBL_ALBO_ENTRO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "entro il";
	}
	
	public boolean isRequiredDataPubblAlboEntroItem() {
		return showDataPubblAlboEntroItem() && getFlgObbligatorioAttributoCustomCablato("PUBBL_ALBO_ENTRO");
	}
	
	protected void createDetailSectionPubblAlbo() {
		
		createPubblAlboForm();
		
		detailSectionPubblAlbo = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionPubblAlbo(), true, true, false, pubblAlboForm);
	}
	
	protected void createPubblAlboForm() {
		
		pubblAlboForm = new DynamicForm();
		pubblAlboForm.setValuesManager(vm);
		pubblAlboForm.setWidth100();
		pubblAlboForm.setPadding(5);
		pubblAlboForm.setWrapItemTitles(false);
		pubblAlboForm.setNumCols(20);
		pubblAlboForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		pubblAlboForm.setTabSet(tabSet);
		pubblAlboForm.setTabID(_TAB_DATI_PUBBL_ID);
		pubblAlboForm.setHeight(1);	
		
		flgPubblAlboItem = new RadioGroupItem("flgPubblAlbo", getTitleFlgPubblAlboItem());
		flgPubblAlboItem.setStartRow(true);
		flgPubblAlboItem.setValueMap(_FLG_SI, _FLG_NO);		
		flgPubblAlboItem.setDefaultValue(getDefaultValueFlgPubblAlboItem());
		flgPubblAlboItem.setVertical(false);
		flgPubblAlboItem.setWrap(false);
		flgPubblAlboItem.setShowDisabled(false);
		if(isRequiredFlgPubblAlboItem()) {
			flgPubblAlboItem.setAttribute("obbligatorio", true);
		}
		flgPubblAlboItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgPubblAlboItem();
			}
		}));
		flgPubblAlboItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgPubblAlboItem();
			}
		});		
		flgPubblAlboItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_PUBBL_ID);
			}
		});
		
		numGiorniPubblAlboItem = new NumericItem("numGiorniPubblAlbo", getTitleNumGiorniPubblAlboItem(), false);
		numGiorniPubblAlboItem.setColSpan(1);
		numGiorniPubblAlboItem.setWidth(70);
		numGiorniPubblAlboItem.setDefaultValue(getDefaultValueNumGiorniPubblAlboItem());
		numGiorniPubblAlboItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showNumGiorniPubblAlboItem();
			}
		});
		if(isRequiredNumGiorniPubblAlboItem()) {
			numGiorniPubblAlboItem.setAttribute("obbligatorio", true);
		}
		numGiorniPubblAlboItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredNumGiorniPubblAlboItem();
			}
		}));
		
		tipoDecorrenzaPubblAlboItem = new SelectItem("tipoDecorrenzaPubblAlbo", getTitleTipoDecorrenzaPubblAlboItem());
		tipoDecorrenzaPubblAlboItem.setWidth(200);
		Map<String, String> tipoDecorrenzaPubblAlboValueMap = getValueMapTipoDecorrenzaPubblAlboItem();
		if(tipoDecorrenzaPubblAlboValueMap != null && tipoDecorrenzaPubblAlboValueMap.keySet().size() > 0) {
			tipoDecorrenzaPubblAlboItem.setValueMap(tipoDecorrenzaPubblAlboValueMap);			
		} else {
			tipoDecorrenzaPubblAlboValueMap = new HashMap<String, String>();
			tipoDecorrenzaPubblAlboValueMap.put(_DECORR_PUBBL_STD, "dal giorno successivo all'adozione");
			tipoDecorrenzaPubblAlboValueMap.put(_DECORR_PUBBL_POST, "posticipata");
			tipoDecorrenzaPubblAlboItem.setValueMap(tipoDecorrenzaPubblAlboValueMap);
		}		
		tipoDecorrenzaPubblAlboItem.setDefaultValue(getDefaultValueTipoDecorrenzaPubblAlboItem());
		tipoDecorrenzaPubblAlboItem.setAttribute("obbligatorio", true);
		tipoDecorrenzaPubblAlboItem.setAllowEmptyValue(false);
		tipoDecorrenzaPubblAlboItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showTipoDecorrenzaPubblAlboItem();
			}
		}));
		tipoDecorrenzaPubblAlboItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showTipoDecorrenzaPubblAlboItem();
			}
		});
		tipoDecorrenzaPubblAlboItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_PUBBL_ID);
			}
		});
		
		dataPubblAlboDalItem = new DateItem("dataPubblAlboDal", getTitleDataPubblAlboDalItem());
		dataPubblAlboDalItem.setColSpan(1);
		dataPubblAlboDalItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDataPubblAlboDalItem();
			}
		});
		if(isRequiredDataPubblAlboDalItem()) {
			dataPubblAlboDalItem.setAttribute("obbligatorio", true);
		}
		dataPubblAlboDalItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredDataPubblAlboDalItem();
			}
		}));
		
		flgUrgentePubblAlboItem = new CheckboxItem("flgUrgentePubblAlbo", getTitleFlgUrgentePubblAlboItem());
		flgUrgentePubblAlboItem.setDefaultValue(getDefaultValueAsBooleanFlgUrgentePubblAlboItem());		
		flgUrgentePubblAlboItem.setColSpan(1);
		flgUrgentePubblAlboItem.setWidth("*");
		flgUrgentePubblAlboItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgUrgentePubblAlboItem();
			}
		});		
		flgUrgentePubblAlboItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_PUBBL_ID);
			}
		});
		
		dataPubblAlboEntroItem = new DateItem("dataPubblAlboEntro", getTitleDataPubblAlboEntroItem());
		dataPubblAlboEntroItem.setColSpan(1);
		dataPubblAlboEntroItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDataPubblAlboEntroItem();
			}
		});
		if(isRequiredDataPubblAlboEntroItem()) {
			dataPubblAlboEntroItem.setAttribute("obbligatorio", true);
		}
		dataPubblAlboEntroItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredDataPubblAlboEntroItem();
			}
		}));
				
		pubblAlboForm.setFields(flgPubblAlboItem, numGiorniPubblAlboItem, tipoDecorrenzaPubblAlboItem, dataPubblAlboDalItem, flgUrgentePubblAlboItem, dataPubblAlboEntroItem);			
	}
	
	/************************************************************************** 
	 * PUBBLICAZIONE/NOTIFICHE - PUBBLICAZIONE IN AMMINISTRAZIONE TRASPARENTE *
	 **************************************************************************/

	public boolean showDetailSectionPubblAmmTrasp() {
		return showTabPubblicazioneNotifiche() && showAttributoCustomCablato("DATI_PUBBL_AMM_TRASP");
	}
	
	public String getTitleDetailSectionPubblAmmTrasp() {
		String label = getLabelAttributoCustomCablato("DATI_PUBBL_AMM_TRASP");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Pubblicazione in Amm. Trasparente";
	}
	
	public boolean showFlgPubblAmmTraspItem() {
		return showDetailSectionPubblAmmTrasp() && showAttributoCustomCablato("FLG_PUBBL_IN_TRASPARENZA");
	}
	
	public String getTitleFlgPubblAmmTraspItem() {
		String label = getLabelAttributoCustomCablato("FLG_PUBBL_IN_TRASPARENZA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Da pubblicare";
	}
	
	public boolean isRequiredFlgPubblAmmTraspItem() {
		return showFlgPubblAmmTraspItem() && getFlgObbligatorioAttributoCustomCablato("FLG_PUBBL_IN_TRASPARENZA");
	}
	
	public String getDefaultValueFlgPubblAmmTraspItem() {
		return getValoreFissoAttributoCustomCablato("FLG_PUBBL_IN_TRASPARENZA");
	}
	
	public boolean showSezionePubblAmmTraspItem() {
		return showDetailSectionPubblAmmTrasp() && (!showFlgPubblAmmTraspItem() || isPubblAmmTrasp()) && showAttributoCustomCablato("SEZ1_PUBBL_IN_TRASPARENZA");
	}
	
	public String getTitleSezionePubblAmmTraspItem() {
		String label = getLabelAttributoCustomCablato("SEZ1_PUBBL_IN_TRASPARENZA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Sezione di pubblicazione";
	}
	
	public boolean isRequiredSezionePubblAmmTraspItem() {
		return showSezionePubblAmmTraspItem() && getFlgObbligatorioAttributoCustomCablato("SEZ1_PUBBL_IN_TRASPARENZA");
	}
	
	public String getAltriParamLoadComboSezionePubblAmmTraspItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("SEZ1_PUBBL_IN_TRASPARENZA");
	}
	
	public void resetSezionePubblAmmTraspAfterChangedParams() {
		if(sezionePubblAmmTraspItem != null) {
			final String value = sezionePubblAmmTraspItem.getValueAsString();
			sezionePubblAmmTraspItem.fetchData(new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String key = data.get(i).getAttribute("key");
							if (value.equals(key)) {
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						sezionePubblAmmTraspItem.setValue("");
						sezionePubblAmmTraspItem.fireEvent(new ChangedEvent(sezionePubblAmmTraspItem.getJsObj()));
					}
				}
			});
		}
	}
	
	public boolean showSottoSezionePubblAmmTraspItem() {
		return showDetailSectionPubblAmmTrasp() && (!showFlgPubblAmmTraspItem() || isPubblAmmTrasp()) && showAttributoCustomCablato("SEZ2_PUBBL_IN_TRASPARENZA");
	}
	
	public String getTitleSottoSezionePubblAmmTraspItem() {
		String label = getLabelAttributoCustomCablato("SEZ2_PUBBL_IN_TRASPARENZA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Sotto-sezione di pubblicazione";
	}
	
	public boolean isRequiredSottoSezionePubblAmmTraspItem() {
		return showSottoSezionePubblAmmTraspItem() && getFlgObbligatorioAttributoCustomCablato("SEZ2_PUBBL_IN_TRASPARENZA");
	}
	
	public String getAltriParamLoadComboSottoSezionePubblAmmTraspItem() {
		return getAltriParametriLoadComboAttributoCustomCablato("SEZ2_PUBBL_IN_TRASPARENZA");
	}
	
	public void resetSottoSezionePubblAmmTraspAfterChangedParams() {
		if(sottoSezionePubblAmmTraspItem != null) {
			final String value = sottoSezionePubblAmmTraspItem.getValueAsString();
			sottoSezionePubblAmmTraspItem.fetchData(new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String key = data.get(i).getAttribute("key");
							if (value.equals(key)) {
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						sottoSezionePubblAmmTraspItem.setValue("");
						sottoSezionePubblAmmTraspItem.fireEvent(new ChangedEvent(sottoSezionePubblAmmTraspItem.getJsObj()));
					}
				}
			});
		}
	}
	
	protected void createDetailSectionPubblAmmTrasp() {
		
		createPubblAmmTraspForm();
		
		detailSectionPubblAmmTrasp = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionPubblAmmTrasp(), true, true, false, pubblAmmTraspForm);
	}
	
	protected void createPubblAmmTraspForm() {
		
		pubblAmmTraspForm = new DynamicForm();
		pubblAmmTraspForm.setValuesManager(vm);
		pubblAmmTraspForm.setWidth100();
		pubblAmmTraspForm.setPadding(5);
		pubblAmmTraspForm.setWrapItemTitles(false);
		pubblAmmTraspForm.setNumCols(20);
		pubblAmmTraspForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		pubblAmmTraspForm.setTabSet(tabSet);
		pubblAmmTraspForm.setTabID(_TAB_DATI_PUBBL_ID);
		pubblAmmTraspForm.setHeight(1);		
		
		flgPubblAmmTraspItem = new RadioGroupItem("flgPubblAmmTrasp", getTitleFlgPubblAmmTraspItem());
		flgPubblAmmTraspItem.setStartRow(true);
		flgPubblAmmTraspItem.setValueMap(_FLG_SI, _FLG_NO);		
		flgPubblAmmTraspItem.setDefaultValue(getDefaultValueFlgPubblAmmTraspItem());
		flgPubblAmmTraspItem.setVertical(false);
		flgPubblAmmTraspItem.setWrap(false);
		flgPubblAmmTraspItem.setShowDisabled(false);
		if(isRequiredFlgPubblAmmTraspItem()) {
			flgPubblAmmTraspItem.setAttribute("obbligatorio", true);
		}
		flgPubblAmmTraspItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgPubblAmmTraspItem();
			}
		}));
		flgPubblAmmTraspItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isPubblAmmTrasp()) {
					detailSectionPubblBUR.setTitle(getTitleDetailSectionPubblBUR() + " e in Trasparenza");
				} else {
					detailSectionPubblBUR.setTitle(getTitleDetailSectionPubblBUR());
				}
				return showFlgPubblAmmTraspItem();
			}
		});		
		flgPubblAmmTraspItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_PUBBL_ID);
			}
		});
		
		GWTRestDataSource sezionePubblAmmTraspDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		sezionePubblAmmTraspDS.addParam("altriParamLoadCombo", getAltriParamLoadComboSezionePubblAmmTraspItem());
		 
		sezionePubblAmmTraspItem = new SelectItem("sezionePubblAmmTrasp", getTitleSezionePubblAmmTraspItem()) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid sezionePubblAmmTraspPickListProperties = super.builPickListProperties();	
				sezionePubblAmmTraspPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource sezionePubblAmmTraspDS = (GWTRestDataSource) sezionePubblAmmTraspItem.getOptionDataSource();		
						sezionePubblAmmTraspDS.addParam("uoProponente", getIdUoProponente());						
						sezionePubblAmmTraspItem.setOptionDataSource(sezionePubblAmmTraspDS);
						sezionePubblAmmTraspItem.invalidateDisplayValueCache();
					}
				});
				return sezionePubblAmmTraspPickListProperties;
			}
		};
		sezionePubblAmmTraspItem.setColSpan(1);
		sezionePubblAmmTraspItem.setValueField("key");
		sezionePubblAmmTraspItem.setDisplayField("value");
		sezionePubblAmmTraspItem.setOptionDataSource(sezionePubblAmmTraspDS);		
		sezionePubblAmmTraspItem.setAllowEmptyValue(true);					
		sezionePubblAmmTraspItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredSezionePubblAmmTraspItem()) {
					sezionePubblAmmTraspItem.setAttribute("obbligatorio", true);
					sezionePubblAmmTraspItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleSezionePubblAmmTraspItem()));
				} else {
					sezionePubblAmmTraspItem.setAttribute("obbligatorio", false);
					sezionePubblAmmTraspItem.setTitle(getTitleSezionePubblAmmTraspItem());
				}
				return showSezionePubblAmmTraspItem();
			}
		});
		sezionePubblAmmTraspItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredSezionePubblAmmTraspItem();
			}
		}));
		sezionePubblAmmTraspItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if(sottoSezionePubblAmmTraspItem != null) {
					resetSottoSezionePubblAmmTraspAfterChangedParams();
				}
			}
		});
		
		GWTRestDataSource sottoSezionePubblAmmTraspDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT);
		sottoSezionePubblAmmTraspDS.addParam("altriParamLoadCombo", getAltriParamLoadComboSottoSezionePubblAmmTraspItem());
		 		
		sottoSezionePubblAmmTraspItem = new SelectItem("sottoSezionePubblAmmTrasp", getTitleSottoSezionePubblAmmTraspItem()) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid sottoSezionePubblAmmTraspPickListProperties = super.builPickListProperties();	
				sottoSezionePubblAmmTraspPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource sottoSezionePubblAmmTraspDS = (GWTRestDataSource) sottoSezionePubblAmmTraspItem.getOptionDataSource();		
						sottoSezionePubblAmmTraspDS.addParam("uoProponente", getIdUoProponente());
						sottoSezionePubblAmmTraspDS.addParam("codValoreVincolo", sezionePubblAmmTraspItem.getValueAsString());									
						sottoSezionePubblAmmTraspItem.setOptionDataSource(sottoSezionePubblAmmTraspDS);
						sottoSezionePubblAmmTraspItem.invalidateDisplayValueCache();
					}
				});
				return sottoSezionePubblAmmTraspPickListProperties;
			}
		};
		sottoSezionePubblAmmTraspItem.setColSpan(1);
		sottoSezionePubblAmmTraspItem.setValueField("key");
		sottoSezionePubblAmmTraspItem.setDisplayField("value");
		sottoSezionePubblAmmTraspItem.setOptionDataSource(sottoSezionePubblAmmTraspDS);		
		sottoSezionePubblAmmTraspItem.setAllowEmptyValue(true);			
		sottoSezionePubblAmmTraspItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredSottoSezionePubblAmmTraspItem()) {
					sottoSezionePubblAmmTraspItem.setAttribute("obbligatorio", true);
					sottoSezionePubblAmmTraspItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleSottoSezionePubblAmmTraspItem()));
				} else {
					sottoSezionePubblAmmTraspItem.setAttribute("obbligatorio", false);
					sottoSezionePubblAmmTraspItem.setTitle(getTitleSottoSezionePubblAmmTraspItem());
				}
				return showSottoSezionePubblAmmTraspItem();
			}
		});
		sottoSezionePubblAmmTraspItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredSottoSezionePubblAmmTraspItem();
			}
		}));
		
		pubblAmmTraspForm.setFields(flgPubblAmmTraspItem, sezionePubblAmmTraspItem, sottoSezionePubblAmmTraspItem);			
	}
	
	/*************************************************** 
	 * PUBBLICAZIONE/NOTIFICHE - PUBBLICAZIONE AL B.U. *
	 ***************************************************/

	public boolean showDetailSectionPubblBUR() {
		return showTabPubblicazioneNotifiche() && showAttributoCustomCablato("DATI_PUBBL_BUR");
	}
	
	public String getTitleDetailSectionPubblBUR() {
		String label = getLabelAttributoCustomCablato("DATI_PUBBL_BUR");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Pubblicazione al B.U.";
	}
	
	public boolean showFlgPubblBURItem() {
		return showDetailSectionPubblBUR() && showAttributoCustomCablato("FLG_PUBBL_BUR");
	}
	
	public String getTitleFlgPubblBURItem() {
		String label = getLabelAttributoCustomCablato("FLG_PUBBL_BUR");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Da pubblicare";
	}
	
	public boolean isRequiredFlgPubblBURItem() {
		return showFlgPubblBURItem() && getFlgObbligatorioAttributoCustomCablato("FLG_PUBBL_BUR");
	}
	
	public String getDefaultValueFlgPubblBURItem() {
		return getValoreFissoAttributoCustomCablato("FLG_PUBBL_BUR");
	}
		
	public boolean showAnnoTerminePubblBURItem() {
		return showDetailSectionPubblBUR() && (!showFlgPubblBURItem() || isPubblBUR()) && showAttributoCustomCablato("ANNO_TERMINE_PUBBL_BUR");
	}
	
	public String getTitleAnnoTerminePubblBURItem() {
		String label = getLabelAttributoCustomCablato("ANNO_TERMINE_PUBBL_BUR");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Anno di termine pubblicazione";
	}
	
	public boolean isRequiredAnnoTerminePubblBURItem() {
		return showAnnoTerminePubblBURItem() && getFlgObbligatorioAttributoCustomCablato("ANNO_TERMINE_PUBBL_BUR");
	}
	
	public String getDefaultValueAnnoTerminePubblBURItem() {
		return getValoreFissoAttributoCustomCablato("ANNO_TERMINE_PUBBL_BUR");
	}
	
	public boolean showTipoDecorrenzaPubblBURItem() {
		return showDetailSectionPubblBUR() && (!showFlgPubblBURItem() || isPubblBUR()) && showAttributoCustomCablato("TIPO_DECORRENZA_PUBBL_BUR");
	}
	
	public String getTitleTipoDecorrenzaPubblBURItem() {
		String label = getLabelAttributoCustomCablato("TIPO_DECORRENZA_PUBBL_BUR");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Decorrenza pubblicazione"; 		
	}
	
	public HashMap<String, String> getValueMapTipoDecorrenzaPubblBURItem() {
		return getValueMapAttributoCustomCablato("TIPO_DECORRENZA_PUBBL_BUR");
	}
	
	public String getDefaultValueTipoDecorrenzaPubblBURItem() {
		return getValoreFissoAttributoCustomCablato("TIPO_DECORRENZA_PUBBL_BUR");
	}
	
	public boolean showDataPubblBURDalItem() {
		return showDetailSectionPubblBUR() && (!showFlgPubblBURItem() || isPubblBUR()) && isDecorrenzaPubblBURPosticipata() && showAttributoCustomCablato("PUBBL_BUR_DAL");
	}
	
	public String getTitleDataPubblBURDalItem() {
		String label = getLabelAttributoCustomCablato("PUBBL_BUR_DAL");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "a partire dal";
	}
	
	public boolean isRequiredDataPubblBURDalItem() {
		return showDataPubblBURDalItem() && getFlgObbligatorioAttributoCustomCablato("PUBBL_BUR_DAL");
	}	
	
	public boolean showFlgUrgentePubblBURItem() {
		return showDetailSectionPubblBUR() && (!showFlgPubblBURItem() || isPubblBUR()) && showAttributoCustomCablato("FLG_URGENTE_PUBBL_BUR");
	}
	
	public String getTitleFlgUrgentePubblBURItem() {
		String label = getLabelAttributoCustomCablato("FLG_URGENTE_PUBBL_BUR");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "pubblicazione urgente";
	}
	
	public boolean getDefaultValueAsBooleanFlgUrgentePubblBURItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("FLG_URGENTE_PUBBL_BUR");
	}
	
	public boolean showDataPubblBUREntroItem() {
		return showDetailSectionPubblBUR() && (!showFlgPubblBURItem() || isPubblBUR()) && isUrgentePubblBUR() && showAttributoCustomCablato("PUBBL_BUR_ENTRO");
	}
	
	public String getTitleDataPubblBUREntroItem() {
		String label = getLabelAttributoCustomCablato("PUBBL_BUR_ENTRO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "entro il";
	}
	
	public boolean isRequiredDataPubblBUREntroItem() {
		return showDataPubblBUREntroItem() && getFlgObbligatorioAttributoCustomCablato("PUBBL_BUR_ENTRO");
	}
	
	protected void createDetailSectionPubblBUR() {
		
		createPubblBURForm();
		
		detailSectionPubblBUR = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionPubblBUR(), true, true, false, pubblBURForm);
	}
	
	protected void createPubblBURForm() {
		
		pubblBURForm = new DynamicForm();
		pubblBURForm.setValuesManager(vm);
		pubblBURForm.setWidth100();
		pubblBURForm.setPadding(5);
		pubblBURForm.setWrapItemTitles(false);
		pubblBURForm.setNumCols(20);
		pubblBURForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		pubblBURForm.setTabSet(tabSet);
		pubblBURForm.setTabID(_TAB_DATI_PUBBL_ID);
		pubblBURForm.setHeight(1);		
		
		flgPubblBURItem = new RadioGroupItem("flgPubblBUR", getTitleFlgPubblBURItem());
		flgPubblBURItem.setStartRow(true);
		flgPubblBURItem.setValueMap(_FLG_SI, _FLG_NO);		
		flgPubblBURItem.setDefaultValue(getDefaultValueFlgPubblBURItem());
		flgPubblBURItem.setVertical(false);
		flgPubblBURItem.setWrap(false);
		flgPubblBURItem.setShowDisabled(false);
		if(isRequiredFlgPubblBURItem()) {
			flgPubblBURItem.setAttribute("obbligatorio", true);
		}
		flgPubblBURItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgPubblBURItem();
			}
		}));
		flgPubblBURItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgPubblBURItem();
			}
		});		
		flgPubblBURItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_PUBBL_ID);
			}
		});
		
		annoTerminePubblBURItem = new AnnoItem("annoTerminePubblBUR", getTitleAnnoTerminePubblBURItem());
		annoTerminePubblBURItem.setColSpan(1);
		annoTerminePubblBURItem.setDefaultValue(getDefaultValueAnnoTerminePubblBURItem());		
		annoTerminePubblBURItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showAnnoTerminePubblBURItem();
			}
		});	
		if(isRequiredAnnoTerminePubblBURItem()) {
			annoTerminePubblBURItem.setAttribute("obbligatorio", true);
		}
		annoTerminePubblBURItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredAnnoTerminePubblBURItem();
			}
		}));
		
		tipoDecorrenzaPubblBURItem = new SelectItem("tipoDecorrenzaPubblBUR", getTitleTipoDecorrenzaPubblBURItem());
		tipoDecorrenzaPubblBURItem.setWidth(200);		
		Map<String, String> tipoDecorrenzaPubblBURValueMap = getValueMapTipoDecorrenzaPubblBURItem();
		if(tipoDecorrenzaPubblBURValueMap != null && tipoDecorrenzaPubblBURValueMap.keySet().size() > 0) {
			tipoDecorrenzaPubblBURItem.setValueMap(tipoDecorrenzaPubblBURValueMap);			
		} else {
			tipoDecorrenzaPubblBURValueMap = new HashMap<String, String>();
			tipoDecorrenzaPubblBURValueMap.put(_DECORR_PUBBL_STD, "dal giorno successivo all'adozione");
			tipoDecorrenzaPubblBURValueMap.put(_DECORR_PUBBL_POST, "posticipata");
			tipoDecorrenzaPubblBURItem.setValueMap(tipoDecorrenzaPubblBURValueMap);
		}	
		tipoDecorrenzaPubblBURItem.setDefaultValue(getDefaultValueTipoDecorrenzaPubblBURItem());
		tipoDecorrenzaPubblBURItem.setAttribute("obbligatorio", true);
		tipoDecorrenzaPubblBURItem.setAllowEmptyValue(false);
		tipoDecorrenzaPubblBURItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showTipoDecorrenzaPubblBURItem();
			}
		}));
		tipoDecorrenzaPubblBURItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showTipoDecorrenzaPubblBURItem();
			}
		});
		tipoDecorrenzaPubblBURItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_PUBBL_ID);
			}
		});
			
		dataPubblBURDalItem = new DateItem("dataPubblBURDal", getTitleDataPubblBURDalItem());
		dataPubblBURDalItem.setColSpan(1);
		dataPubblBURDalItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDataPubblBURDalItem();
			}
		});
		if(isRequiredDataPubblBURDalItem()) {
			dataPubblBURDalItem.setAttribute("obbligatorio", true);
		}
		dataPubblBURDalItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredDataPubblBURDalItem();
			}
		}));
		
		flgUrgentePubblBURItem = new CheckboxItem("flgUrgentePubblBUR", getTitleFlgUrgentePubblBURItem());
		flgUrgentePubblBURItem.setDefaultValue(getDefaultValueAsBooleanFlgUrgentePubblBURItem());		
		flgUrgentePubblBURItem.setColSpan(1);
		flgUrgentePubblBURItem.setWidth("*");
		flgUrgentePubblBURItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgUrgentePubblBURItem();
			}
		});		
		flgUrgentePubblBURItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_PUBBL_ID);
			}
		});
		
		dataPubblBUREntroItem = new DateItem("dataPubblBUREntro", getTitleDataPubblBUREntroItem());
		dataPubblBUREntroItem.setColSpan(1);
		dataPubblBUREntroItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDataPubblBUREntroItem();
			}
		});
		if(isRequiredDataPubblBUREntroItem()) {
			dataPubblBUREntroItem.setAttribute("obbligatorio", true);
		}
		dataPubblBUREntroItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredDataPubblBUREntroItem();
			}
		}));
		
		pubblBURForm.setFields(flgPubblBURItem, annoTerminePubblBURItem, tipoDecorrenzaPubblBURItem, dataPubblBURDalItem, flgUrgentePubblBURItem, dataPubblBUREntroItem);			
	}
	
	/********************************************************** 
	 * PUBBLICAZIONE/NOTIFICHE - PUBBLICAZIONE SUL NOTIZIARIO *
	 **********************************************************/

	public boolean showDetailSectionPubblNotiziario() {
//		return showTabPubblicazioneNotifiche() && showAttributoCustomCablato("DATI_PUBBL_NOTIZIARIO");
		return showFlgPubblNotiziarioItem();
	}
	
	public String getTitleDetailSectionPubblNotiziario() {
		String label = getLabelAttributoCustomCablato("DATI_PUBBL_NOTIZIARIO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Pubblicazione sul notiziario";
	}
	
	public boolean showFlgPubblNotiziarioItem() {
//		return showDetailSectionPubblNotiziario() && showAttributoCustomCablato("TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO");
		return showTabPubblicazioneNotifiche() && showAttributoCustomCablato("TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO");
	}
	
	public String getTitleFlgPubblNotiziarioItem() {
		String label = getLabelAttributoCustomCablato("TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Da pubblicare";
	}
	
	public boolean isRequiredFlgPubblNotiziarioItem() {
		return showFlgPubblNotiziarioItem() && getFlgObbligatorioAttributoCustomCablato("TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO");
	}
	
	public String getDefaultValueFlgPubblNotiziarioItem() {
		return getValoreFissoAttributoCustomCablato("TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO");
	}
	
	protected void createDetailSectionPubblNotiziario() {
		
		createPubblNotiziarioForm();
		
		detailSectionPubblNotiziario = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionPubblNotiziario(), true, true, false, pubblNotiziarioForm);
	}
	
	protected void createPubblNotiziarioForm() {
		
		pubblNotiziarioForm = new DynamicForm();
		pubblNotiziarioForm.setValuesManager(vm);
		pubblNotiziarioForm.setWidth100();
		pubblNotiziarioForm.setPadding(5);
		pubblNotiziarioForm.setWrapItemTitles(false);
		pubblNotiziarioForm.setNumCols(20);
		pubblNotiziarioForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		pubblNotiziarioForm.setTabSet(tabSet);
		pubblNotiziarioForm.setTabID(_TAB_DATI_PUBBL_ID);
		pubblNotiziarioForm.setHeight(1);		
		
		flgPubblNotiziarioItem = new RadioGroupItem("flgPubblNotiziario", getTitleFlgPubblNotiziarioItem());
		flgPubblNotiziarioItem.setStartRow(true);
		flgPubblNotiziarioItem.setValueMap(_FLG_SI, _FLG_NO);		
		flgPubblNotiziarioItem.setDefaultValue(getDefaultValueFlgPubblNotiziarioItem());
		flgPubblNotiziarioItem.setVertical(false);
		flgPubblNotiziarioItem.setWrap(false);
		flgPubblNotiziarioItem.setShowDisabled(false);
		if(isRequiredFlgPubblNotiziarioItem()) {
			flgPubblNotiziarioItem.setAttribute("obbligatorio", true);
		}
		flgPubblNotiziarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredFlgPubblNotiziarioItem();
			}
		}));
		flgPubblNotiziarioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showFlgPubblNotiziarioItem();
			}
		});		
		flgPubblNotiziarioItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_PUBBL_ID);
			}
		});
		
		pubblNotiziarioForm.setFields(flgPubblNotiziarioItem);			
	}
	
	/***************************************** 
	 * PUBBLICAZIONE/NOTIFICHE - ESECUTIVITA *
	 *****************************************/
	
	public boolean showDetailSectionEsecutivita() {
		return showTabPubblicazioneNotifiche() && showAttributoCustomCablato("DATI_ESECUTIVITA");
	}
	
	public String getTitleDetailSectionEsecutivita() {
		String label = getLabelAttributoCustomCablato("DATI_ESECUTIVITA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Esecutività";
	}
	
	public boolean showDataEsecutivitaDalItem() {
		return showDetailSectionEsecutivita() && showAttributoCustomCablato("DATA_ESECUTIVITA");
	}
	
	public String getTitleDataEsecutivitaDalItem() {
		String label = getLabelAttributoCustomCablato("DATA_ESECUTIVITA");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Esecutività dal";
	}	

	public boolean showFlgImmediatamenteEseguibileItem() {
		return showDetailSectionEsecutivita() && showAttributoCustomCablato("FLG_IMMEDIATAMENTE_ESEGUIBILE");
	}
	
	public String getTitleFlgImmediatamenteEseguibileItem() {
		String label = getLabelAttributoCustomCablato("FLG_IMMEDIATAMENTE_ESEGUIBILE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "immediatamente eseguibile";
	}
	
	public boolean isRequiredFlgImmediatamenteEseguibileItem() {
		return showFlgImmediatamenteEseguibileItem() && getFlgObbligatorioAttributoCustomCablato("FLG_IMMEDIATAMENTE_ESEGUIBILE");
	}
	
	public boolean getDefaultValueAsBooleanFlgImmediatamenteEseguibileItem() {
		return getValoreFissoAsBooleanAttributoCustomCablato("FLG_IMMEDIATAMENTE_ESEGUIBILE");
	}
	
	public boolean showMotiviImmediatamenteEseguibileItem() {
		boolean isImmediatamenteEseguibile = showFlgImmediatamenteEseguibileItem() && getValueAsBoolean("flgImmediatamenteEseguibile");
		return showDetailSectionEsecutivita() && isImmediatamenteEseguibile && showAttributoCustomCablato("MOTIVI_IE");
	}
	
	public String getTitleMotiviImmediatamenteEseguibileItem() {
		String label = getLabelAttributoCustomCablato("MOTIVI_IE");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Motivi immediata eseguibilità";
	}
	
	public boolean isRequiredMotiviImmediatamenteEseguibileItem() {
		return showMotiviImmediatamenteEseguibileItem() && getFlgObbligatorioAttributoCustomCablato("MOTIVI_IE");
	}
	
	public int getAltezzaInRigheMotiviImmediatamenteEseguibileItem() {
		Integer altezzaInRighe = getAltezzaInRigheAttributoCustomCablato("MOTIVI_IE");
		return altezzaInRighe != null ? altezzaInRighe.intValue() : 10;
	}
		
	protected void createDetailSectionEsecutivita() {
		
		createEsecutivitaForm();
		
		detailSectionEsecutivita = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionEsecutivita(), true, true, false, esecutivitaForm1, esecutivitaForm2);
	}
	
	protected void createEsecutivitaForm() {
		
		esecutivitaForm1 = new DynamicForm();
		esecutivitaForm1.setValuesManager(vm);
		esecutivitaForm1.setWidth100();
		esecutivitaForm1.setPadding(5);
		esecutivitaForm1.setWrapItemTitles(false);
		esecutivitaForm1.setNumCols(20);
		esecutivitaForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		esecutivitaForm1.setTabSet(tabSet);
		esecutivitaForm1.setTabID(_TAB_DATI_PUBBL_ID);
		esecutivitaForm1.setHeight(1);		
		
		dataEsecutivitaDalItem = new DateItem("dataEsecutivitaDal", getTitleDataEsecutivitaDalItem());
		dataEsecutivitaDalItem.setColSpan(1);
		dataEsecutivitaDalItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDataEsecutivitaDalItem();
			}
		});		
		
		flgImmediatamenteEseguibileItem = new CheckboxItem("flgImmediatamenteEseguibile", getTitleFlgImmediatamenteEseguibileItem());
		flgImmediatamenteEseguibileItem.setDefaultValue(getDefaultValueAsBooleanFlgImmediatamenteEseguibileItem());
		flgImmediatamenteEseguibileItem.setColSpan(1);
		flgImmediatamenteEseguibileItem.setWidth("*");
		flgImmediatamenteEseguibileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgImmediatamenteEseguibileItem();
			}
		});		
		flgImmediatamenteEseguibileItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_PUBBL_ID);				
				motiviImmediatamenteEseguibileItem.redraw();
				if(showMotiviImmediatamenteEseguibileItem()) {
					createMotiviImmediatamenteEseguibileItem();
					esecutivitaForm2.setFields(motiviImmediatamenteEseguibileItem);
				} else {
					esecutivitaForm2.setFields(new FormItem[0]);
				}
			}
		});
				
		esecutivitaForm1.setFields(dataEsecutivitaDalItem, flgImmediatamenteEseguibileItem);	
		
		esecutivitaForm2 = new DynamicForm();
		esecutivitaForm2.setValuesManager(vm);
		esecutivitaForm2.setWidth100();
		esecutivitaForm2.setPadding(5);
		esecutivitaForm2.setWrapItemTitles(false);
		esecutivitaForm2.setNumCols(20);
		esecutivitaForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		esecutivitaForm2.setTabSet(tabSet);
		esecutivitaForm2.setTabID(_TAB_DATI_PUBBL_ID);
		esecutivitaForm2.setHeight(1);		
		
//		if(showMotiviImmediatamenteEseguibileItem()) {
			createMotiviImmediatamenteEseguibileItem();
			esecutivitaForm2.setFields(motiviImmediatamenteEseguibileItem);
//		} else {
//			esecutivitaForm2.setFields(new FormItem[0]);
//		}		
	}
	
	public void createMotiviImmediatamenteEseguibileItem() {
		motiviImmediatamenteEseguibileItem = new CKEditorItem("motiviImmediatamenteEseguibile", -1, "STANDARD", getAltezzaInRigheMotiviImmediatamenteEseguibileItem(), -1, "") {
			
			@Override
			public Boolean validate() {
				if(showMotiviImmediatamenteEseguibileItem()) {
					return super.validate();
				}
				return true;			
			}
			
			@Override
			public void redraw() {
				motiviImmediatamenteEseguibileItem.setTitle(isRequiredMotiviImmediatamenteEseguibileItem() ? FrontendUtil.getRequiredFormItemTitle(getTitleMotiviImmediatamenteEseguibileItem()) : getTitleMotiviImmediatamenteEseguibileItem());
				motiviImmediatamenteEseguibileItem.setRequired(isRequiredMotiviImmediatamenteEseguibileItem());
				motiviImmediatamenteEseguibileItem.setVisible(showMotiviImmediatamenteEseguibileItem());
				super.redraw();
			}
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				lVLayout.setWidth100();
				lVLayout.setPadding(11);
				lVLayout.setMargin(4);
				lVLayout.setIsGroup(true);
				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
				if(isRequiredMotiviImmediatamenteEseguibileItem()) {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(getTitleMotiviImmediatamenteEseguibileItem()) + "</span>");
				} else {
					lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + getTitleMotiviImmediatamenteEseguibileItem() + "</span>");
				}				
				return lVLayout;
			}
		};
//		motiviImmediatamenteEseguibileItem.setShowTitle(true);
//		motiviImmediatamenteEseguibileItem.setTitleOrientation(TitleOrientation.TOP);
		motiviImmediatamenteEseguibileItem.setColSpan(20);
		motiviImmediatamenteEseguibileItem.setWidth("100%");			
//		motiviImmediatamenteEseguibileItem.setTitle(isRequiredMotiviImmediatamenteEseguibileItem() ? FrontendUtil.getRequiredFormItemTitle(getTitleMotiviImmediatamenteEseguibileItem()) : getTitleMotiviImmediatamenteEseguibileItem());
//		motiviImmediatamenteEseguibileItem.setRequired(isRequiredMotiviImmediatamenteEseguibileItem());
//		motiviImmediatamenteEseguibileItem.setVisible(showMotiviImmediatamenteEseguibileItem());		
		motiviImmediatamenteEseguibileItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isRequiredMotiviImmediatamenteEseguibileItem()) {
					motiviImmediatamenteEseguibileItem.setTitle(FrontendUtil.getRequiredFormItemTitle(getTitleMotiviImmediatamenteEseguibileItem()));
					motiviImmediatamenteEseguibileItem.setRequired(true);
				} else {
					motiviImmediatamenteEseguibileItem.setTitle(getTitleMotiviImmediatamenteEseguibileItem());
					motiviImmediatamenteEseguibileItem.setRequired(false);
				}
				return showMotiviImmediatamenteEseguibileItem();
			}
		});
	}

	/*************************************** 
	 * PUBBLICAZIONE/NOTIFICHE - NOTIFICHE *
	 ***************************************/

	public boolean showDetailSectionNotifiche() {
		return showDestNotificaAttoItem();
	}
	
	public String getTitleDetailSectionNotifiche() {
		return getTitleDestNotificaAttoItem();
	}
	
	public boolean isRequiredDetailSectionNotifiche() {
		return isRequiredDestNotificaAttoItem();
	}
	
	protected void createDetailSectionNotifiche() {
		
		createNotificheForm();
		
		detailSectionNotifiche = new NuovaPropostaAtto2CompletaDetailSection(getTitleDetailSectionNotifiche(), true, true, isRequiredDetailSectionNotifiche(), notificheForm);
	}
	
	public boolean showDestNotificaAttoItem() {
		return showTabPubblicazioneNotifiche() && showAttributoCustomCablato("IND_EMAIL_DEST_NOTIFICA_ATTO");
	}
	
	public String getTitleDestNotificaAttoItem() {
		String label = getLabelAttributoCustomCablato("IND_EMAIL_DEST_NOTIFICA_ATTO");
		if(label != null && !"".equals(label)) {
			return label;
		}
		return "Da notificare a";
	}
	
	public boolean isRequiredDestNotificaAttoItem() {
		return showDestNotificaAttoItem() && getFlgObbligatorioAttributoCustomCablato("IND_EMAIL_DEST_NOTIFICA_ATTO");
	}
	
	protected void createNotificheForm() {
		
		notificheForm = new DynamicForm();
		notificheForm.setValuesManager(vm);
		notificheForm.setWidth100();
		notificheForm.setPadding(5);
		notificheForm.setWrapItemTitles(false);
		notificheForm.setNumCols(20);
		notificheForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		notificheForm.setTabSet(tabSet);
		notificheForm.setTabID(_TAB_DATI_PUBBL_ID);
		notificheForm.setHeight(1);		
								 
		GWTRestDataSource destNotificaAttoDS = new GWTRestDataSource("AurigaAutoCompletamentoDataSource");
		
		listaDestNotificaAttoItem = new ComboBoxItem("listaDestNotificaAtto");
		listaDestNotificaAttoItem.setWidth(800);
		listaDestNotificaAttoItem.setColSpan(19);
		listaDestNotificaAttoItem.setShowTitle(false);
		listaDestNotificaAttoItem.setShowPickerIcon(false);
		listaDestNotificaAttoItem.setTextBoxStyle(it.eng.utility.Styles.textItem);
		listaDestNotificaAttoItem.setStartRow(true);
		listaDestNotificaAttoItem.setValueField("indirizzoEmail");
		listaDestNotificaAttoItem.setDisplayField("indirizzoEmail");
		listaDestNotificaAttoItem.setAutoFetchData(false);
		listaDestNotificaAttoItem.setAlwaysFetchMissingValues(true);
		listaDestNotificaAttoItem.setAddUnknownValues(true);
		listaDestNotificaAttoItem.setOptionDataSource(destNotificaAttoDS);
		listaDestNotificaAttoItem.setFetchDelay(500);
		listaDestNotificaAttoItem.setValidateOnChange(false);
		listaDestNotificaAttoItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return (String) listaDestNotificaAttoItem.getValue();
			}
		});
		listaDestNotificaAttoItem.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				listaDestNotificaAttoItem.validate();
			}
		});
		ListGridField descVoceRubricaField = new ListGridField("descVoceRubrica");
		descVoceRubricaField.setWidth("100%");
		descVoceRubricaField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				if (record != null) {
					String res = null;
					String descVoceRubrica = record.getAttribute("descVoceRubrica") != null ? record.getAttributeAsString("descVoceRubrica") : null;
					if (record.getAttributeAsString("tipoIndirizzo") != null && "C".equals(record.getAttributeAsString("tipoIndirizzo"))) {
						res = buildIconHtml("coccarda.png", descVoceRubrica);
						return res;
					} else if (record.getAttributeAsString("tipoIndirizzo") != null && "O".equals(record.getAttributeAsString("tipoIndirizzo"))) { 
						res = buildIconHtml("mail/PEO.png", descVoceRubrica);
						return res;
					} else {
						res = buildIconHtml("mail/mailingList.png", descVoceRubrica);
						return res;
					}
				}
				return null;
			}
		});
		listaDestNotificaAttoItem.setPickListFields(descVoceRubricaField);
		ListGrid listaDestNotificaAttoPickListProperties = new ListGrid();
		listaDestNotificaAttoPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		listaDestNotificaAttoPickListProperties.setShowHeader(false);
		listaDestNotificaAttoPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				event.cancel();
				listaDestNotificaAttoItem.setValue(event.getRecord().getAttribute("indirizzoEmail").replace(" ", ""));
				notificheForm.markForRedraw();
			}
		});
		listaDestNotificaAttoPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {	
				String destinatario = listaDestNotificaAttoItem != null && listaDestNotificaAttoItem.getValue() != null ? (String) listaDestNotificaAttoItem.getValue() : null;
				GWTRestDataSource destNotificaAttoDS = (GWTRestDataSource) listaDestNotificaAttoItem.getOptionDataSource();
				destNotificaAttoDS.addParam("destinatario", destinatario);		
				listaDestNotificaAttoItem.setOptionDataSource(destNotificaAttoDS);
				listaDestNotificaAttoItem.invalidateDisplayValueCache();
			}
		});
		listaDestNotificaAttoItem.setPickListProperties(listaDestNotificaAttoPickListProperties);
		listaDestNotificaAttoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDestNotificaAttoItem();
			}
		});
		if(isRequiredDestNotificaAttoItem()) {
			listaDestNotificaAttoItem.setAttribute("obbligatorio", true);
		}
		CustomValidator lDestNotificaAttoValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(showDestNotificaAttoItem()) {
					if (value == null || value.equals("")) {
						return true;
					}
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					String lString = (String) value;
					String[] list = IndirizziEmailSplitter.split(lString);
					boolean res = true;
					for(int i=0; i < list.length; i++){
						if (list[i] == null || list[i].equals(""))
							res = res && true;
						else
							res = res && regExp.test(list[i].trim());
					}
					return res;
				}
				return true;
			}
		};
		lDestNotificaAttoValidator.setErrorMessage(I18NUtil.getMessages().invionotificainteropform_destinatariValidatorErrorMessage());
		listaDestNotificaAttoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isRequiredDestNotificaAttoItem();
			}
		}), lDestNotificaAttoValidator);
		
		//Inserimento del pulsante da affiancare alla text di inserimento del destinatario
		lookupRubricaEmailDestinatariButton = new ImgButtonItem("lookupRubricaEmailDestinatariButton", "lookup/rubricaemail.png", I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariButton.setShowTitle(false);
		lookupRubricaEmailDestinatariButton.setWidth(16);
		lookupRubricaEmailDestinatariButton.setColSpan(1);
		lookupRubricaEmailDestinatariButton.setValueIconSize(32);
		lookupRubricaEmailDestinatariButton.setStartRow(false);
		lookupRubricaEmailDestinatariButton.setEndRow(true);
		lookupRubricaEmailDestinatariButton.setPrompt(I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailDestinatariButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioMailMultiLookupRubricaEmailPopup(notificheForm, "listaDestNotificaAtto");
				lookupRubricaEmailPopup.show();
			}
		});
		lookupRubricaEmailDestinatariButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDestNotificaAttoItem();
			}
		});
		
		notificheForm.setFields(listaDestNotificaAttoItem, lookupRubricaEmailDestinatariButton);
	}
		
	/*************************** 
	 * TAB MOVIMENTI CONTABILI *
	 ***************************/
	
	public boolean showTabMovimentiContabili() {
		return !isAvvioPropostaAtto() && ((isAttivoSICRA() && showAttributoCustomCablato("MOVIMENTO_SICRA")) || (isAttivoContabilia() && showAttributoCustomCablato("MOVIMENTO_CONTABILIA") && !"".equals(getValueAsString("idPropostaAMC"))));	
	}
	
	public String getTitleTabMovimentiContabili() {
		return "Movimenti contabili";
	}
	
	/**
	 * Metodo per costruire il tab "Movimenti contabili"
	 * 
	 */
	protected void createTabMovimentiContabili() {

		tabMovimentiContabili = new Tab("<b>" + getTitleTabMovimentiContabili() + "</b>");
		tabMovimentiContabili.setAttribute("tabID", _TAB_MOVIMENTI_CONTABILI_ID);
		tabMovimentiContabili.setPrompt(getTitleTabMovimentiContabili());		
		
		VLayout layoutMovimentiContabili = getLayoutMovimentiContabili();
		layoutMovimentiContabili.setHeight100();
		VLayout layoutTabMovimentiContabili = new VLayout();
		layoutTabMovimentiContabili.setWidth100();
		layoutTabMovimentiContabili.setHeight100();
		layoutTabMovimentiContabili.addMember(layoutMovimentiContabili);
		layoutTabMovimentiContabili.setRedrawOnResize(true);
		tabMovimentiContabili.setPane(layoutTabMovimentiContabili);
	}

	/**
	 * Metodo che restituisce il layout del tab "Movimenti contabili"
	 * 
	 */
	public VLayout getLayoutMovimentiContabili() {

		VLayout layoutMovimentiContabili = new VLayout(5);
		
		createMovimentiContabiliForm();
		layoutMovimentiContabili.addMember(movimentiContabiliForm);
		
		return layoutMovimentiContabili;
	}
	
	public void refreshListaMovimentiContabilia(final ServiceCallback<Record> callback) {
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		if(isAttivaRequestMovimentiDaAMC()) {
			lNuovaPropostaAtto2CompletaDataSource.addParam("flgAttivaRequestMovimentiDaAMC", "true");
		}	
		lNuovaPropostaAtto2CompletaDataSource.performCustomOperation("getListaMovimentiContabilia", getRecordToSave(), new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					listaMovimentiContabiliaItem.setData(record.getAttributeAsRecordList("listaMovimentiContabilia"));
					if(record.getAttribute("errorMessageMovimentiContabilia") != null && !"".equals(record.getAttribute("errorMessageMovimentiContabilia"))) {
						listaMovimentiContabiliaItem.setGridEmptyMessage(record.getAttribute("errorMessageMovimentiContabilia"));
					} else {
						listaMovimentiContabiliaItem.setGridEmptyMessage("Nessun dato trovato");
					}					
					redrawTabForms(_TAB_MOVIMENTI_CONTABILI_ID);
					if(callback != null) {
						callback.execute(record);
					}
				} 				
			}
		}, new DSRequest());
	}
	
	protected void createMovimentiContabiliForm() {
		
		movimentiContabiliForm = new DynamicForm();
		movimentiContabiliForm.setValuesManager(vm);
		movimentiContabiliForm.setWidth100();
		movimentiContabiliForm.setPadding(5);
		movimentiContabiliForm.setWrapItemTitles(false);
		movimentiContabiliForm.setNumCols(20);
		movimentiContabiliForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		movimentiContabiliForm.setTabSet(tabSet);
		movimentiContabiliForm.setTabID(_TAB_MOVIMENTI_CONTABILI_ID);
		movimentiContabiliForm.setHeight100();
		
		String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
		if(lSistAMC != null) {
			if("CONTABILIA".equalsIgnoreCase(lSistAMC)) {
				listaMovimentiContabiliaItem = new ListaMovimentiContabiliaItem("listaMovimentiContabilia") {
					
					public void onClickRefreshListButton() {
						refreshListaMovimentiContabilia(new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								showHideSections();						
								afterShow();
							}
						});
					}
				};
				listaMovimentiContabiliaItem.setGridEmptyMessage("Nessun dato trovato");
				listaMovimentiContabiliaItem.setStartRow(true);
				listaMovimentiContabiliaItem.setShowTitle(false);
				listaMovimentiContabiliaItem.setHeight("95%");
				movimentiContabiliForm.setFields(listaMovimentiContabiliaItem);	
			} else if("CONTABILIA2".equalsIgnoreCase(lSistAMC)) {
				listaMovimentiContabiliaItem = new ListaMovimentiContabilia2Item("listaMovimentiContabilia") {
					
					public void onClickRefreshListButton() {
						refreshListaMovimentiContabilia(new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record object) {
								showHideSections();						
								afterShow();
							}
						});
					}
				};
				listaMovimentiContabiliaItem.setGridEmptyMessage("Nessun dato trovato");
				listaMovimentiContabiliaItem.setStartRow(true);
				listaMovimentiContabiliaItem.setShowTitle(false);
				listaMovimentiContabiliaItem.setHeight("95%");
				movimentiContabiliForm.setFields(listaMovimentiContabiliaItem);	
			} else if("SICRA".equalsIgnoreCase(lSistAMC)) {
				listaInvioMovimentiContabiliSICRAItem = new ListaInvioMovimentiContabiliSICRAItem("listaInvioMovimentiContabiliSICRA") {
					
					@Override
					public boolean isEsclusoCIGProposta() {
						return isEsclusoCIG();
					}
					
					@Override
					public String[] getCIGValueMap() {
						if(showCIGItem()) {
							List<String> listaCodCIG = new ArrayList<String>();
							RecordList listaCIG = CIGForm.getValueAsRecordList("listaCIG");
							for(int i=0; i < listaCIG.getLength(); i++) {
								if(listaCIG.get(i).getAttribute("codiceCIG") != null &&
										!"".equals(listaCIG.get(i).getAttribute("codiceCIG"))) {
									listaCodCIG.add(listaCIG.get(i).getAttribute("codiceCIG"));
								}
							}
							return listaCodCIG.toArray(new String[listaCodCIG.size()]);
						}
						return null;
					}
					
					@Override
					public HashSet<String> getVociPEGNoVerifDisp() {
						return vociPEGNoVerifDisp;
					}
					
					@Override
					public String getIdUdProposta() {
						return getIdUd();
					}
					
					@Override
					public String getUoProponenteCorrente() {
						return getIdUoProponente();
					}
					
					@Override
					public boolean isEscludiFiltroCdC() {
						return isEscludiFiltroCdCVsAMC();
					}
					
					@Override
					public boolean isGrigliaEditabile() {
						return true;
					}
					
					@Override
					public boolean isShowRefreshListButton() {
						return false;
					}
				};
				listaInvioMovimentiContabiliSICRAItem.setStartRow(true);
				listaInvioMovimentiContabiliSICRAItem.setShowTitle(false);
				listaInvioMovimentiContabiliSICRAItem.setHeight("95%");					
				movimentiContabiliForm.setFields(listaInvioMovimentiContabiliSICRAItem);	
			}
		}
	}
	
	/***************************
	 * TAB DATI SPESA CORRENTE *
	 ***************************/
	
	public boolean showTabDatiSpesaCorrente() {
		String[] flgSpesaValueMap = getValoriPossibiliFlgSpesaItem();
		if(flgSpesaValueMap != null && flgSpesaValueMap.length == 1 && _FLG_NO.equalsIgnoreCase(flgSpesaValueMap[0])) {
			return false;
		}
		return !isAvvioPropostaAtto() && (isAttivoSIB() && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA") && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA_CORRENTE"));
	}
	
	public boolean isAttivoTabDatiSpesaCorrente() {
		return showTabDatiSpesaCorrente() && isDeterminaConSpesa() && !isDeterminaPersonale() && getValueAsBoolean("flgSpesaCorrente");
	}
	
	public boolean isModalitaGrigliaCorrente() {
		return getValueAsString("prenotazioneSpesaSIBDiCorrente").equals(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B) && getValueAsString("modalitaInvioDatiSpesaARagioneriaCorrente").equals(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1);
	}
	
	public boolean isPresentiDatiContabiliSIBCorrente() {
		return datiContabiliSIBCorrenteForm != null && datiContabiliSIBCorrenteForm.getValueAsRecordList("listaDatiContabiliSIBCorrente") != null && datiContabiliSIBCorrenteForm.getValueAsRecordList("listaDatiContabiliSIBCorrente").getLength() > 0;
	}
	
	public boolean isPresentiDatiStoriciCorrente() {
		return invioDatiSpesaCorrenteForm != null && invioDatiSpesaCorrenteForm.getValueAsRecordList("listaInvioDatiSpesaCorrente") != null && invioDatiSpesaCorrenteForm.getValueAsRecordList("listaInvioDatiSpesaCorrente").getLength() > 0;
	}
	
	/**
	 * Metodo per costruire il tab "Dati spesa corrente"
	 * 
	 */
	protected void createTabDatiSpesaCorrente() {

		tabDatiSpesaCorrente = new Tab("<b>" + I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiSpesaCorrente_prompt() + "</b>");
		tabDatiSpesaCorrente.setAttribute("tabID", _TAB_DATI_SPESA_CORRENTE_ID);
		tabDatiSpesaCorrente.setPrompt(I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiSpesaCorrente_prompt());
		tabDatiSpesaCorrente.setPane(createTabPane(getLayoutDatiSpesaCorrente()));	
	}
	
	/**
	 * Metodo che restituisce il layout del tab "Dati spesa corrente"
	 * 
	 */
	protected VLayout getLayoutDatiSpesaCorrente() {
		
		VLayout layoutDatiSpesaCorrente = new VLayout(5);

		createDetailSectionOpzioniSpesaCorrente();
		layoutDatiSpesaCorrente.addMember(detailSectionOpzioniSpesaCorrente);
		
		createDetailSectionDatiContabiliSIBCorrente();
		detailSectionDatiContabiliSIBCorrente.setVisible(false);		
		layoutDatiSpesaCorrente.addMember(detailSectionDatiContabiliSIBCorrente);
		
		createDetailSectionInvioDatiSpesaCorrente();
		detailSectionInvioDatiSpesaCorrente.setVisible(false);		
		layoutDatiSpesaCorrente.addMember(detailSectionInvioDatiSpesaCorrente);
		
		createDetailSectionFileXlsCorrente();
		detailSectionFileXlsCorrente.setVisible(false);		
		layoutDatiSpesaCorrente.addMember(detailSectionFileXlsCorrente);
		
//		createDetailSectionAllegatiCorrente();
//		layoutDatiSpesaCorrente.addMember(detailSectionAllegatiCorrente);
		
		createDetailSectionNoteCorrente();
		layoutDatiSpesaCorrente.addMember(detailSectionNoteCorrente);
		
		return layoutDatiSpesaCorrente;
	}
	
	protected void createDetailSectionOpzioniSpesaCorrente() {
		
		createOpzioniSpesaCorrenteForm();
		
		detailSectionOpzioniSpesaCorrente = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionOpzioniSpesaCorrente_title(), true, true, false, opzioniSpesaCorrenteForm1, opzioniSpesaCorrenteForm2);
	}
	
	protected void createOpzioniSpesaCorrenteForm() {
		
		opzioniSpesaCorrenteForm1 = new DynamicForm();
		opzioniSpesaCorrenteForm1.setValuesManager(vm);
		opzioniSpesaCorrenteForm1.setWidth100();
		opzioniSpesaCorrenteForm1.setPadding(5);
		opzioniSpesaCorrenteForm1.setWrapItemTitles(false);
		opzioniSpesaCorrenteForm1.setNumCols(20);
		opzioniSpesaCorrenteForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		opzioniSpesaCorrenteForm1.setTabSet(tabSet);
		opzioniSpesaCorrenteForm1.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem = new CheckboxItem("flgDisattivaAutoRequestDatiContabiliSIBCorrente", "disattiva caricamento automatico da SIB all'apertura del task");
		flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem.setDefaultValue(false);
		flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem.setStartRow(true);
		flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem.setColSpan(10);
		flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem.setWidth("*");
		
		opzioniSpesaCorrenteForm1.setFields(flgDisattivaAutoRequestDatiContabiliSIBCorrenteItem);
		
		opzioniSpesaCorrenteForm2 = new DynamicForm();
		opzioniSpesaCorrenteForm2.setValuesManager(vm);
		opzioniSpesaCorrenteForm2.setWidth100();
		opzioniSpesaCorrenteForm2.setPadding(5);
		opzioniSpesaCorrenteForm2.setWrapItemTitles(false);
		opzioniSpesaCorrenteForm2.setNumCols(20);
		opzioniSpesaCorrenteForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		opzioniSpesaCorrenteForm2.setTabSet(tabSet);
		opzioniSpesaCorrenteForm2.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		prenotazioneSpesaSIBDiCorrenteItem = new RadioGroupItem("prenotazioneSpesaSIBDiCorrente", I18NUtil.getMessages().nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_title());
		prenotazioneSpesaSIBDiCorrenteItem.setStartRow(true);
		LinkedHashMap<String, String> prenotazioneSpesaSIBDiCorrenteValueMap = new LinkedHashMap<String, String>();
		prenotazioneSpesaSIBDiCorrenteValueMap.put(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_A, I18NUtil.getMessages().nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_opzioneA());
		prenotazioneSpesaSIBDiCorrenteValueMap.put(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B, I18NUtil.getMessages().nuovaPropostaAtto2_detail_prenotazioneSpesaSIBDi_opzioneB());		
		prenotazioneSpesaSIBDiCorrenteItem.setValueMap(prenotazioneSpesaSIBDiCorrenteValueMap);
		prenotazioneSpesaSIBDiCorrenteItem.setVertical(false);
		prenotazioneSpesaSIBDiCorrenteItem.setWrap(false);
		prenotazioneSpesaSIBDiCorrenteItem.setDefaultValue(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_A);
		prenotazioneSpesaSIBDiCorrenteItem.setAttribute("obbligatorio", true);
		prenotazioneSpesaSIBDiCorrenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isAttivoTabDatiSpesaCorrente() && isDatiSpesaEditabili();
			}
		}));
		prenotazioneSpesaSIBDiCorrenteItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_SPESA_CORRENTE_ID);
				showHideSections();
			}
		});
		
		modalitaInvioDatiSpesaARagioneriaCorrenteItem = new RadioGroupItem("modalitaInvioDatiSpesaARagioneriaCorrente", I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_title());
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setStartRow(true);
		LinkedHashMap<String, String> modalitaInvioDatiSpesaARagioneriaCorrenteValueMap = new LinkedHashMap<String, String>();
		modalitaInvioDatiSpesaARagioneriaCorrenteValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB1());
		modalitaInvioDatiSpesaARagioneriaCorrenteValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B2, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB2());		
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setValueMap(modalitaInvioDatiSpesaARagioneriaCorrenteValueMap);
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setVertical(false);
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setWrap(false);
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setAttribute("obbligatorio", true);
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setDefaultValue(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1);
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isAttivoTabDatiSpesaCorrente() && getValueAsString("prenotazioneSpesaSIBDiCorrente").equals(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B) && isDatiSpesaEditabili();
			}
		}));
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return getValueAsString("prenotazioneSpesaSIBDiCorrente").equals(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B);
			}
		});
		modalitaInvioDatiSpesaARagioneriaCorrenteItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_SPESA_CORRENTE_ID);
				showHideSections();
			}
		});
		
		opzioniSpesaCorrenteForm2.setFields(
			prenotazioneSpesaSIBDiCorrenteItem,
			modalitaInvioDatiSpesaARagioneriaCorrenteItem
		);	
	}
	
	public boolean showDetailSectionDatiContabiliSIBCorrente() {
		return getValueAsString("prenotazioneSpesaSIBDiCorrente").equals(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_A) || isPresentiDatiContabiliSIBCorrente();
	}
		
	protected void createDetailSectionDatiContabiliSIBCorrente() {

		createDatiContabiliSIBCorrenteForm();
		
		detailSectionDatiContabiliSIBCorrente = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionDatiContabiliSIBCorrente_title(), true, true, false, datiContabiliSIBCorrenteForm);
	}
	
	public void refreshListaDatiContabiliSIBCorrente(final ServiceCallback<Record> callback) {
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		if(isAttivaRequestMovimentiDaAMC()) {
			lNuovaPropostaAtto2CompletaDataSource.addParam("flgAttivaRequestMovimentiDaAMC", "true");
		}	
		lNuovaPropostaAtto2CompletaDataSource.performCustomOperation("getListaDatiContabiliSIBCorrente", getRecordToSave(), new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					listaDatiContabiliSIBCorrenteItem.setData(record.getAttributeAsRecordList("listaDatiContabiliSIBCorrente"));
					if(record.getAttribute("errorMessageDatiContabiliSIBCorrente") != null && !"".equals(record.getAttribute("errorMessageDatiContabiliSIBCorrente"))) {
						listaDatiContabiliSIBCorrenteItem.setGridEmptyMessage(record.getAttribute("errorMessageDatiContabiliSIBCorrente"));
					} else {
						listaDatiContabiliSIBCorrenteItem.setGridEmptyMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage());
					}					
					redrawTabForms(_TAB_DATI_SPESA_CORRENTE_ID);
					if(callback != null) {
						callback.execute(record);
					}
				} 				
			}
		}, new DSRequest());
	}
	
	protected void createDatiContabiliSIBCorrenteForm() {
		
		datiContabiliSIBCorrenteForm = new DynamicForm();
		datiContabiliSIBCorrenteForm.setValuesManager(vm);
		datiContabiliSIBCorrenteForm.setWidth100();
		datiContabiliSIBCorrenteForm.setPadding(5);
		datiContabiliSIBCorrenteForm.setWrapItemTitles(false);
		datiContabiliSIBCorrenteForm.setNumCols(20);
		datiContabiliSIBCorrenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiContabiliSIBCorrenteForm.setTabSet(tabSet);
		datiContabiliSIBCorrenteForm.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		listaDatiContabiliSIBCorrenteItem = new ListaDatiContabiliSIBItem("listaDatiContabiliSIBCorrente") {
			
			@Override
			public boolean isShowDatiStoriciButton() {
				return isModalitaGrigliaCorrente() && isPresentiDatiContabiliSIBCorrente() && isPresentiDatiStoriciCorrente();
			}
			
			public void onClickDatiStoriciButton() {
				DatiContabiliStoriciWindow lDatiContabiliStoriciWindow = new DatiContabiliStoriciWindow("datiContabiliStoriciWindow", invioDatiSpesaCorrenteForm.getValueAsRecordList("listaInvioDatiSpesaCorrente")) {
					
					@Override
					public String getSIBDataSourceName() {
						return "SIBDataSource";
					}
				};
				lDatiContabiliStoriciWindow.show();
			}
			
			public void onClickRefreshListButton() {
				refreshListaDatiContabiliSIBCorrente(new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						showHideSections();						
						afterShow();
						controllaTotali(false);
					}
				});
			}
		};
		listaDatiContabiliSIBCorrenteItem.setGridEmptyMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage());
		listaDatiContabiliSIBCorrenteItem.setStartRow(true);
		listaDatiContabiliSIBCorrenteItem.setShowTitle(false);
		listaDatiContabiliSIBCorrenteItem.setHeight(245);		
		listaDatiContabiliSIBCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showDetailSectionDatiContabiliSIBCorrente();
			}
		});
		
		datiContabiliSIBCorrenteForm.setFields(listaDatiContabiliSIBCorrenteItem);	
	}

	public boolean showDetailSectionInvioDatiSpesaCorrente() {
		return isModalitaGrigliaCorrente() && !isPresentiDatiContabiliSIBCorrente();
	}
	
	protected void createDetailSectionInvioDatiSpesaCorrente() {
		
		createInvioDatiSpesaCorrenteForm();
		
		detailSectionInvioDatiSpesaCorrente = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionInvioDatiSpesaCorrente_title(), true, true, false, invioDatiSpesaCorrenteForm);
	}
	
	protected void createInvioDatiSpesaCorrenteForm() {
		
		invioDatiSpesaCorrenteForm = new DynamicForm();
		invioDatiSpesaCorrenteForm.setValuesManager(vm);
		invioDatiSpesaCorrenteForm.setWidth100();
		invioDatiSpesaCorrenteForm.setPadding(5);
		invioDatiSpesaCorrenteForm.setWrapItemTitles(false);
		invioDatiSpesaCorrenteForm.setNumCols(20);
		invioDatiSpesaCorrenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		invioDatiSpesaCorrenteForm.setTabSet(tabSet);
		invioDatiSpesaCorrenteForm.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		listaInvioDatiSpesaCorrenteItem = new ListaInvioDatiSpesaItem("listaInvioDatiSpesaCorrente") {
			
			@Override
			public boolean isEsclusoCIGProposta() {
				return isEsclusoCIG();
			}
			
			@Override
			public String[] getCIGValueMap() {
				if(showCIGItem()) {
					List<String> listaCodCIG = new ArrayList<String>();
					RecordList listaCIG = CIGForm.getValueAsRecordList("listaCIG");
					for(int i=0; i < listaCIG.getLength(); i++) {
						if(listaCIG.get(i).getAttribute("codiceCIG") != null &&
								!"".equals(listaCIG.get(i).getAttribute("codiceCIG"))) {
							listaCodCIG.add(listaCIG.get(i).getAttribute("codiceCIG"));
						}
					}
					return listaCodCIG.toArray(new String[listaCodCIG.size()]);
				}
				return null;
			}
			
			@Override
			public HashSet<String> getVociPEGNoVerifDisp() {
				return vociPEGNoVerifDisp;
			}
			
			@Override
			public String getSIBDataSourceName() {
				return "SIBDataSource";
			}
			
			@Override
			public boolean isGrigliaEditabile() {
				return true;
			}
			
			public void onClickRefreshListButton() {
				refreshListaDatiContabiliSIBCorrente(new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						showHideSections();						
						afterShow();
						controllaTotali(false);
					}
				});														
			}
		};
		listaInvioDatiSpesaCorrenteItem.setStartRow(true);
		listaInvioDatiSpesaCorrenteItem.setShowTitle(false);
		listaInvioDatiSpesaCorrenteItem.setHeight(245);		
		listaInvioDatiSpesaCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionInvioDatiSpesaCorrente();
			}
		});
		
		invioDatiSpesaCorrenteForm.setFields(listaInvioDatiSpesaCorrenteItem);	
	}
	
	public boolean showDetailSectionFileXlsCorrente() {
		return getValueAsString("prenotazioneSpesaSIBDiCorrente").equals(_PRENOTAZIONE_SPESA_SIB_DI_OPZIONE_B) && getValueAsString("modalitaInvioDatiSpesaARagioneriaCorrente").equals(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B2); 
	}
	
	protected void createDetailSectionFileXlsCorrente() {
		
		createFileXlsCorrenteForm();
		
		detailSectionFileXlsCorrente = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionFileXlsCorrente_title(), true, true, false, fileXlsCorrenteForm);
	}
	
	protected void createFileXlsCorrenteForm() {
		
		fileXlsCorrenteForm = new DynamicForm();
		fileXlsCorrenteForm.setValuesManager(vm);
		fileXlsCorrenteForm.setWidth100();
		fileXlsCorrenteForm.setPadding(5);
		fileXlsCorrenteForm.setWrapItemTitles(false);
		fileXlsCorrenteForm.setNumCols(20);
		fileXlsCorrenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		fileXlsCorrenteForm.setTabSet(tabSet);
		fileXlsCorrenteForm.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		fileXlsCorrenteItem = new DocumentItem() {
			
			@Override
			public int getWidth() {
				return 250;
			}
			
			@Override
			public boolean showVisualizzaVersioniMenuItem() {
				return false;
			}
			
			@Override
			public boolean showAcquisisciDaScannerMenuItem() {
				return false;
			}
			
			@Override
			public boolean showFirmaMenuItem() {
				return false;
			}
			
			@Override
			public boolean isFormatoAmmesso(InfoFileRecord info) {	
				String correctName = info != null ? info.getCorrectFileName() : "";
				return correctName.toLowerCase().endsWith(".xls") || correctName.toLowerCase().endsWith(".xlsx");
			}
		};
		fileXlsCorrenteItem.setStartRow(true);
		fileXlsCorrenteItem.setName("fileXlsCorrente");
		fileXlsCorrenteItem.setShowTitle(false);
		fileXlsCorrenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionFileXlsCorrente();
			}
		});
		/*
		fileXlsCorrenteItem.setAttribute("obbligatorio", true);
		fileXlsCorrenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showTabDatiSpesaCorrente() && showDetailSectionFileXlsCorrente() && isDatiSpesaEditabili() && isEsitoTaskSelezionatoOk();
			}
		}));
		*/
		
		nomeFileTracciatoXlsCorrenteItem = new HiddenItem("nomeFileTracciatoXlsCorrente");
		uriFileTracciatoXlsCorrenteItem = new HiddenItem("uriFileTracciatoXlsCorrente");
		
		scaricaTracciatoXlsCorrenteButton = new ImgButtonItem("scaricaTracciatoXlsCorrenteButton", "file/download_manager.png", "Scarica tracciato");
		scaricaTracciatoXlsCorrenteButton.setAlwaysEnabled(true);
		scaricaTracciatoXlsCorrenteButton.setColSpan(1);
		scaricaTracciatoXlsCorrenteButton.setIconWidth(16);
		scaricaTracciatoXlsCorrenteButton.setIconHeight(16);
		scaricaTracciatoXlsCorrenteButton.setIconVAlign(VerticalAlignment.BOTTOM);
		scaricaTracciatoXlsCorrenteButton.setAlign(Alignment.LEFT);
		scaricaTracciatoXlsCorrenteButton.setWidth(16);
		scaricaTracciatoXlsCorrenteButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				if(uriFileTracciatoXlsCorrenteItem.getValue() != null && !"".equals((String) uriFileTracciatoXlsCorrenteItem.getValue())) {
					Record lRecord = new Record();
					if(nomeFileTracciatoXlsCorrenteItem.getValue() != null && !"".equals((String) nomeFileTracciatoXlsCorrenteItem.getValue())) {
						lRecord.setAttribute("displayFilename", nomeFileTracciatoXlsCorrenteItem.getValue());
					} else {
						lRecord.setAttribute("displayFilename", "Tracciato_SIB.xls");
					}
					lRecord.setAttribute("uri", (String) uriFileTracciatoXlsCorrenteItem.getValue());
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", true);
					DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
				} else {
					AurigaLayout.addMessage(new MessageBean("Nessun tracciato disponibile", "", MessageType.ERROR));
				}
			}
		});
		scaricaTracciatoXlsCorrenteButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionFileXlsCorrente();
			}
		});
		
		fileXlsCorrenteForm.setFields(fileXlsCorrenteItem, nomeFileTracciatoXlsCorrenteItem, uriFileTracciatoXlsCorrenteItem, scaricaTracciatoXlsCorrenteButton);	
	}
	
	protected void createDetailSectionNoteCorrente() {
		
		createNoteCorrenteForm();
		
		detailSectionNoteCorrente = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionNoteCorrente_title(), true, true, false, noteCorrenteForm);
	}
	
	protected void createNoteCorrenteForm() {
		
		noteCorrenteForm = new DynamicForm();
		noteCorrenteForm.setValuesManager(vm);
		noteCorrenteForm.setWidth100();
		noteCorrenteForm.setPadding(5);
		noteCorrenteForm.setWrapItemTitles(false);
		noteCorrenteForm.setNumCols(20);
		noteCorrenteForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		noteCorrenteForm.setTabSet(tabSet);
		noteCorrenteForm.setTabID(_TAB_DATI_SPESA_CORRENTE_ID);
		
		noteCorrenteItem = new CKEditorItem("noteCorrente");
		noteCorrenteItem.setShowTitle(false);
		noteCorrenteItem.setColSpan(20);
		noteCorrenteItem.setWidth("100%");
				
		noteCorrenteForm.setFields(noteCorrenteItem);			
	}
	
	/*********************************
	 * TAB DATI SPESA CONTO CAPITALE *
	 *********************************/
	
	public boolean showTabDatiSpesaContoCapitale() {
		String[] flgSpesaValueMap = getValoriPossibiliFlgSpesaItem();
		if(flgSpesaValueMap != null && flgSpesaValueMap.length == 1 && _FLG_NO.equalsIgnoreCase(flgSpesaValueMap[0])) {
			return false;
		}
		return !isAvvioPropostaAtto() && (isAttivoSIB() && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA") && showAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA_CONTO_CAP"));
	}
	
	public boolean isAttivoTabDatiSpesaContoCapitale() {
		return showTabDatiSpesaContoCapitale() && isDeterminaConSpesa() && !isDeterminaPersonale() && getValueAsBoolean("flgSpesaContoCapitale");
	}
	
	public boolean isModalitaGrigliaContoCapitale() {
		return getValueAsString("modalitaInvioDatiSpesaARagioneriaContoCapitale").equals(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1);
	}
	
	public boolean isPresentiDatiContabiliSIBContoCapitale() {
		return datiContabiliSIBContoCapitaleForm != null && datiContabiliSIBContoCapitaleForm.getValueAsRecordList("listaDatiContabiliSIBContoCapitale") != null && datiContabiliSIBContoCapitaleForm.getValueAsRecordList("listaDatiContabiliSIBContoCapitale").getLength() > 0;
	}
	
	public boolean isPresentiDatiStoriciContoCapitale() {
		return invioDatiSpesaContoCapitaleForm != null && invioDatiSpesaContoCapitaleForm.getValueAsRecordList("listaInvioDatiSpesaContoCapitale") != null && invioDatiSpesaContoCapitaleForm.getValueAsRecordList("listaInvioDatiSpesaContoCapitale").getLength() > 0;
	}
	
	/**
	 * Metodo per costruire il tab "Dati spesa in conto capitale"
	 * 
	 */
	protected void createTabDatiSpesaContoCapitale() {

		tabDatiSpesaContoCapitale = new Tab("<b>" + I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiSpesaContoCapitale_prompt() + "</b>");
		tabDatiSpesaContoCapitale.setAttribute("tabID", _TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		tabDatiSpesaContoCapitale.setPrompt(I18NUtil.getMessages().nuovaPropostaAtto2_detail_tabDatiSpesaContoCapitale_prompt());
		tabDatiSpesaContoCapitale.setPane(createTabPane(getLayoutDatiSpesaContoCapitale()));
	}
	
	/**
	 * Metodo che restituisce il layout del tab "Dati spesa in conto capitale"
	 * 
	 */
	protected VLayout getLayoutDatiSpesaContoCapitale() {
		
		VLayout layoutDatiSpesaContoCapitale = new VLayout(5);
		
		createDetailSectionOpzioniSpesaContoCapitale();
		layoutDatiSpesaContoCapitale.addMember(detailSectionOpzioniSpesaContoCapitale);
		
		createDetailSectionDatiContabiliSIBContoCapitale();
		detailSectionDatiContabiliSIBContoCapitale.setVisible(false);		
		layoutDatiSpesaContoCapitale.addMember(detailSectionDatiContabiliSIBContoCapitale);
		
		createDetailSectionInvioDatiSpesaContoCapitale();
		detailSectionInvioDatiSpesaContoCapitale.setVisible(false);		
		layoutDatiSpesaContoCapitale.addMember(detailSectionInvioDatiSpesaContoCapitale);
		
		createDetailSectionFileXlsContoCapitale();
		detailSectionFileXlsContoCapitale.setVisible(false);		
		layoutDatiSpesaContoCapitale.addMember(detailSectionFileXlsContoCapitale);
		
//		createDetailSectionAllegatiContoCapitale();
//		layoutDatiSpesaContoCapitale.addMember(detailSectionAllegatiContoCapitale);
		
		createDetailSectionNoteContoCapitale();
		layoutDatiSpesaContoCapitale.addMember(detailSectionNoteContoCapitale);
		
		return layoutDatiSpesaContoCapitale;
	}
	
	protected void createDetailSectionOpzioniSpesaContoCapitale() {
		
		createOpzioniSpesaContoCapitaleForm();
		
		detailSectionOpzioniSpesaContoCapitale = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionOpzioniSpesaContoCapitale_title(), true, true, false, opzioniSpesaContoCapitaleForm1, opzioniSpesaContoCapitaleForm2);
	}
	
	public LinkedHashMap<String, String> buildModalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap() {
		LinkedHashMap<String, String> modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap = new LinkedHashMap<String, String>();
		modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB1());
		modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B2, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB2());		
		if(getValueAsBoolean("flgImpegniContoCapitaleGiaRilasciati")) {
			modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B3, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB3());					
		}
		if(getValueAsBoolean("flgSoloSubImpSubCrono")) {
			modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap.put(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B4, I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_opzioneB4());					
		}
		return modalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap;
	}
	
	protected void createOpzioniSpesaContoCapitaleForm() {
		
		opzioniSpesaContoCapitaleForm1 = new DynamicForm();
		opzioniSpesaContoCapitaleForm1.setValuesManager(vm);
		opzioniSpesaContoCapitaleForm1.setWidth100();
		opzioniSpesaContoCapitaleForm1.setPadding(5);
		opzioniSpesaContoCapitaleForm1.setWrapItemTitles(false);
		opzioniSpesaContoCapitaleForm1.setNumCols(20);
		opzioniSpesaContoCapitaleForm1.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		opzioniSpesaContoCapitaleForm1.setTabSet(tabSet);
		opzioniSpesaContoCapitaleForm1.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem = new CheckboxItem("flgDisattivaAutoRequestDatiContabiliSIBContoCapitale", "disattiva caricamento automatico da SIB all'apertura del task");
		flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem.setDefaultValue(false);
		flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem.setStartRow(true);
		flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem.setColSpan(10);
		flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem.setWidth("*");		
		
		opzioniSpesaContoCapitaleForm1.setFields(flgDisattivaAutoRequestDatiContabiliSIBContoCapitaleItem);
		
		opzioniSpesaContoCapitaleForm2 = new DynamicForm();
		opzioniSpesaContoCapitaleForm2.setValuesManager(vm);
		opzioniSpesaContoCapitaleForm2.setWidth100();
		opzioniSpesaContoCapitaleForm2.setPadding(5);
		opzioniSpesaContoCapitaleForm2.setWrapItemTitles(false);
		opzioniSpesaContoCapitaleForm2.setNumCols(20);
		opzioniSpesaContoCapitaleForm2.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		opzioniSpesaContoCapitaleForm2.setTabSet(tabSet);
		opzioniSpesaContoCapitaleForm2.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem = new RadioGroupItem("modalitaInvioDatiSpesaARagioneriaContoCapitale", I18NUtil.getMessages().nuovaPropostaAtto2_detail_modalitaInvioDatiSpesaARagioneria_title());
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setStartRow(true);		
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setValueMap(buildModalitaInvioDatiSpesaARagioneriaContoCapitaleValueMap());
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setVertical(false);
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setWrap(false);
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setAttribute("obbligatorio", true);
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setDefaultValue(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B1);
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isAttivoTabDatiSpesaContoCapitale() && isDatiSpesaEditabili();
			}
		}));
		modalitaInvioDatiSpesaARagioneriaContoCapitaleItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				redrawTabForms(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
				showHideSections();
			}
		});
	
		opzioniSpesaContoCapitaleForm2.setFields(modalitaInvioDatiSpesaARagioneriaContoCapitaleItem);	
	}
	
	public boolean showDetailSectionDatiContabiliSIBContoCapitale() {
		return isPresentiDatiContabiliSIBContoCapitale();
	}
	
	protected void createDetailSectionDatiContabiliSIBContoCapitale() {

		createDatiContabiliSIBContoCapitaleForm();
		
		detailSectionDatiContabiliSIBContoCapitale = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionDatiContabiliSIBContoCapitale_title(), true, true, false, datiContabiliSIBContoCapitaleForm);
	}
	
	public void refreshListaDatiContabiliSIBContoCapitale(final ServiceCallback<Record> callback) {
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		if(isAttivaRequestMovimentiDaAMC()) {
			lNuovaPropostaAtto2CompletaDataSource.addParam("flgAttivaRequestMovimentiDaAMC", "true");
		}	
		lNuovaPropostaAtto2CompletaDataSource.performCustomOperation("getListaDatiContabiliSIBContoCapitale", getRecordToSave(), new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					listaDatiContabiliSIBContoCapitaleItem.setData(record.getAttributeAsRecordList("listaDatiContabiliSIBContoCapitale"));
					if(record.getAttribute("errorMessageDatiContabiliSIBContoCapitale") != null && !"".equals(record.getAttribute("errorMessageDatiContabiliSIBContoCapitale"))) {						
						listaDatiContabiliSIBContoCapitaleItem.setGridEmptyMessage(record.getAttribute("errorMessageDatiContabiliSIBContoCapitale"));
					} else {
						listaDatiContabiliSIBContoCapitaleItem.setGridEmptyMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage());						
					}
					redrawTabForms(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
					if(callback != null) {
						callback.execute(record);
					}					
				} 				
			}
		}, new DSRequest());
	}
	
	protected void createDatiContabiliSIBContoCapitaleForm() {
		
		datiContabiliSIBContoCapitaleForm = new DynamicForm();
		datiContabiliSIBContoCapitaleForm.setValuesManager(vm);
		datiContabiliSIBContoCapitaleForm.setWidth100();
		datiContabiliSIBContoCapitaleForm.setPadding(5);
		datiContabiliSIBContoCapitaleForm.setWrapItemTitles(false);
		datiContabiliSIBContoCapitaleForm.setNumCols(20);
		datiContabiliSIBContoCapitaleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		datiContabiliSIBContoCapitaleForm.setTabSet(tabSet);
		datiContabiliSIBContoCapitaleForm.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		listaDatiContabiliSIBContoCapitaleItem = new ListaDatiContabiliSIBItem("listaDatiContabiliSIBContoCapitale") {
			
			@Override
			public boolean isShowDatiStoriciButton() {
				return isModalitaGrigliaContoCapitale() && isPresentiDatiContabiliSIBContoCapitale() && isPresentiDatiStoriciContoCapitale();
			}
			
			public void onClickDatiStoriciButton() {
				DatiContabiliStoriciWindow lDatiContabiliStoriciWindow = new DatiContabiliStoriciWindow("datiContabiliStoriciWindow", invioDatiSpesaContoCapitaleForm.getValueAsRecordList("listaInvioDatiSpesaContoCapitale")) {
					
					@Override
					public String getSIBDataSourceName() {
						return "SIBDataSource";
					}
				};
				lDatiContabiliStoriciWindow.show();
			}
			
			public void onClickRefreshListButton() {
				refreshListaDatiContabiliSIBContoCapitale(new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						showHideSections();
						afterShow();
						controllaTotali(false);
					}
				});
			}
		};
		listaDatiContabiliSIBContoCapitaleItem.setGridEmptyMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabiliSIB_emptyMessage());
		listaDatiContabiliSIBContoCapitaleItem.setStartRow(true);
		listaDatiContabiliSIBContoCapitaleItem.setShowTitle(false);
		listaDatiContabiliSIBContoCapitaleItem.setHeight(245);		
		listaDatiContabiliSIBContoCapitaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showDetailSectionDatiContabiliSIBContoCapitale();
			}
		});
		
		datiContabiliSIBContoCapitaleForm.setFields(listaDatiContabiliSIBContoCapitaleItem);	
	}
	
	public boolean showDetailSectionInvioDatiSpesaContoCapitale() {
		return isModalitaGrigliaContoCapitale() && !isPresentiDatiContabiliSIBContoCapitale();
	}
	
	protected void createDetailSectionInvioDatiSpesaContoCapitale() {
		
		createInvioDatiSpesaContoCapitaleForm();
		
		detailSectionInvioDatiSpesaContoCapitale = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionInvioDatiSpesaContoCapitale_title(), true, true, false, invioDatiSpesaContoCapitaleForm);
	}
	
	protected void createInvioDatiSpesaContoCapitaleForm() {
		
		invioDatiSpesaContoCapitaleForm = new DynamicForm();
		invioDatiSpesaContoCapitaleForm.setValuesManager(vm);
		invioDatiSpesaContoCapitaleForm.setWidth100();
		invioDatiSpesaContoCapitaleForm.setPadding(5);
		invioDatiSpesaContoCapitaleForm.setWrapItemTitles(false);
		invioDatiSpesaContoCapitaleForm.setNumCols(20);
		invioDatiSpesaContoCapitaleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		invioDatiSpesaContoCapitaleForm.setTabSet(tabSet);
		invioDatiSpesaContoCapitaleForm.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		listaInvioDatiSpesaContoCapitaleItem = new ListaInvioDatiSpesaItem("listaInvioDatiSpesaContoCapitale") {
			
			@Override
			public boolean isEsclusoCIGProposta() {
				return isEsclusoCIG();
			}
			
			@Override
			public String[] getCIGValueMap() {
				if(showCIGItem()) {
					List<String> listaCodCIG = new ArrayList<String>();
					RecordList listaCIG = CIGForm.getValueAsRecordList("listaCIG");
					for(int i=0; i < listaCIG.getLength(); i++) {
						if(listaCIG.get(i).getAttribute("codiceCIG") != null &&
								!"".equals(listaCIG.get(i).getAttribute("codiceCIG"))) {
							listaCodCIG.add(listaCIG.get(i).getAttribute("codiceCIG"));
						}
					}
					return listaCodCIG.toArray(new String[listaCodCIG.size()]);
				}
				return null;
			}
			
			@Override
			public HashSet<String> getVociPEGNoVerifDisp() {
				return vociPEGNoVerifDisp;
			}
			
			@Override
			public String getSIBDataSourceName() {
				return "SIBDataSource";
			}
			
			@Override
			public boolean isGrigliaEditabile() {
				return true;
			}
			
			public void onClickRefreshListButton() {
				refreshListaDatiContabiliSIBContoCapitale(new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						showHideSections();
						afterShow();
						controllaTotali(false);
					}
				});
			}
		};
		listaInvioDatiSpesaContoCapitaleItem.setStartRow(true);
		listaInvioDatiSpesaContoCapitaleItem.setShowTitle(false);
		listaInvioDatiSpesaContoCapitaleItem.setHeight(245);		
		listaInvioDatiSpesaContoCapitaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionInvioDatiSpesaContoCapitale();
			}
		});
		
		invioDatiSpesaContoCapitaleForm.setFields(listaInvioDatiSpesaContoCapitaleItem);	
	}
	
	public boolean showDetailSectionFileXlsContoCapitale() {
		return getValueAsString("modalitaInvioDatiSpesaARagioneriaContoCapitale").equals(_MODALITA_INVIO_DATI_SPESA_RAGIONERIA_OPZIONE_B2);
	}
	
	protected void createDetailSectionFileXlsContoCapitale() {
		
		createFileXlsContoCapitaleForm();
		
		detailSectionFileXlsContoCapitale = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionFileXlsContoCapitale_title(), true, true, false, fileXlsContoCapitaleForm);
	}
	
	protected void createFileXlsContoCapitaleForm() {
		
		fileXlsContoCapitaleForm = new DynamicForm();
		fileXlsContoCapitaleForm.setValuesManager(vm);
		fileXlsContoCapitaleForm.setWidth100();
		fileXlsContoCapitaleForm.setPadding(5);
		fileXlsContoCapitaleForm.setWrapItemTitles(false);
		fileXlsContoCapitaleForm.setNumCols(20);
		fileXlsContoCapitaleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		fileXlsContoCapitaleForm.setTabSet(tabSet);
		fileXlsContoCapitaleForm.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		fileXlsContoCapitaleItem = new DocumentItem() {
			
			@Override
			public int getWidth() {
				return 250;
			}
			
			@Override
			public boolean showVisualizzaVersioniMenuItem() {
				return false;
			}
			
			@Override
			public boolean showAcquisisciDaScannerMenuItem() {
				return false;
			}
			
			@Override
			public boolean showFirmaMenuItem() {
				return false;
			}		
			
			@Override
			public boolean isFormatoAmmesso(InfoFileRecord info) {	
				String correctName = info != null ? info.getCorrectFileName() : "";
				return correctName.toLowerCase().endsWith(".xls") || correctName.toLowerCase().endsWith(".xlsx");
			}
		};
		fileXlsContoCapitaleItem.setStartRow(true);
		fileXlsContoCapitaleItem.setName("fileXlsContoCapitale");
		fileXlsContoCapitaleItem.setShowTitle(false);
		fileXlsContoCapitaleItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionFileXlsContoCapitale();
			}
		});
		/*
		fileXlsContoCapitaleItem.setAttribute("obbligatorio", true);
		fileXlsContoCapitaleItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showTabDatiSpesaContoCapitale() && showDetailSectionFileXlsContoCapitale() && isDatiSpesaEditabili() && isEsitoTaskSelezionatoOk();
			}
		}));
		*/
		
		nomeFileTracciatoXlsContoCapitaleItem = new HiddenItem("nomeFileTracciatoXlsContoCapitale");
		uriFileTracciatoXlsContoCapitaleItem = new HiddenItem("uriFileTracciatoXlsContoCapitale");
		
		scaricaTracciatoXlsContoCapitaleButton = new ImgButtonItem("scaricaTracciatoXlsContoCapitaleButton", "file/download_manager.png", "Scarica tracciato");
		scaricaTracciatoXlsContoCapitaleButton.setAlwaysEnabled(true);
		scaricaTracciatoXlsContoCapitaleButton.setColSpan(1);
		scaricaTracciatoXlsContoCapitaleButton.setIconWidth(16);
		scaricaTracciatoXlsContoCapitaleButton.setIconHeight(16);
		scaricaTracciatoXlsContoCapitaleButton.setIconVAlign(VerticalAlignment.BOTTOM);
		scaricaTracciatoXlsContoCapitaleButton.setAlign(Alignment.LEFT);
		scaricaTracciatoXlsContoCapitaleButton.setWidth(16);
		scaricaTracciatoXlsContoCapitaleButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				if(uriFileTracciatoXlsContoCapitaleItem.getValue() != null && !"".equals((String) uriFileTracciatoXlsContoCapitaleItem.getValue())) {
					Record lRecord = new Record();
					if(nomeFileTracciatoXlsContoCapitaleItem.getValue() != null && !"".equals((String) nomeFileTracciatoXlsContoCapitaleItem.getValue())) {
						lRecord.setAttribute("displayFilename", nomeFileTracciatoXlsContoCapitaleItem.getValue());
					} else {
						lRecord.setAttribute("displayFilename", "Tracciato_SIB.xls");
					}
					lRecord.setAttribute("uri", (String) uriFileTracciatoXlsContoCapitaleItem.getValue());
					lRecord.setAttribute("sbustato", "false");
					lRecord.setAttribute("remoteUri", true);
					DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
				} else {
					AurigaLayout.addMessage(new MessageBean("Nessun tracciato disponibile", "", MessageType.ERROR));
				}
			}
		});
		scaricaTracciatoXlsContoCapitaleButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showDetailSectionFileXlsContoCapitale();
			}
		});
		
		fileXlsContoCapitaleForm.setFields(fileXlsContoCapitaleItem, nomeFileTracciatoXlsContoCapitaleItem, uriFileTracciatoXlsContoCapitaleItem, scaricaTracciatoXlsContoCapitaleButton);	
	}
	
	protected void createDetailSectionNoteContoCapitale() {
		
		createNoteContoCapitaleForm();
		
		detailSectionNoteContoCapitale = new NuovaPropostaAtto2CompletaDetailSection(I18NUtil.getMessages().nuovaPropostaAtto2_detail_detailSectionNoteContoCapitale_title(), true, true, false, noteContoCapitaleForm);
	}
	
	protected void createNoteContoCapitaleForm() {
		
		noteContoCapitaleForm = new DynamicForm();
		noteContoCapitaleForm.setValuesManager(vm);
		noteContoCapitaleForm.setWidth100();
		noteContoCapitaleForm.setPadding(5);
		noteContoCapitaleForm.setWrapItemTitles(false);
		noteContoCapitaleForm.setNumCols(20);
		noteContoCapitaleForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		noteContoCapitaleForm.setTabSet(tabSet);
		noteContoCapitaleForm.setTabID(_TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		
		noteContoCapitaleItem = new CKEditorItem("noteContoCapitale");
		noteContoCapitaleItem.setShowTitle(false);
		noteContoCapitaleItem.setColSpan(20);
		noteContoCapitaleItem.setWidth("100%");
				
		noteContoCapitaleForm.setFields(noteContoCapitaleItem);			
	}
	
	/********************************** 
	 * TAB AGGREGATO/SMISTAMENTO ACTA *
	 **********************************/	
	
	public boolean showTabAggregatoSmistamentoACTA() {		
		String fascSmistACTA = AurigaLayout.getParametroDB("FASC_SMIST_ACTA");
		return !isAvvioPropostaAtto() && fascSmistACTA != null && (_MANDATORY.equalsIgnoreCase(fascSmistACTA) || _OPTIONAL.equalsIgnoreCase(fascSmistACTA));
	}
	
	public String getTitleTabAggregatoSmistamentoACTA() {
		return "Aggregazione documentale/smistamento ACTA";
	}
	
	/**
	 * Metodo per costruire il tab "Aggregato/smistamento ACTA"
	 * 
	 */
	protected void createTabAggregatoSmistamentoACTA() {

		tabAggregatoSmistamentoACTA = new Tab("<b>" + getTitleTabAggregatoSmistamentoACTA() + "</b>");
		tabAggregatoSmistamentoACTA.setAttribute("tabID", _TAB_AGGREGATO_SMISTAMENTO_ACTA_ID);
		tabAggregatoSmistamentoACTA.setPrompt(getTitleTabAggregatoSmistamentoACTA());
		tabAggregatoSmistamentoACTA.setPane(createTabPane(getLayoutAggregatoSmistamentoACTA()));
	}

	/**
	 * Metodo che restituisce il layout del tab "Aggregato/smistamento ACTA"
	 * 
	 */
	public VLayout getLayoutAggregatoSmistamentoACTA() {

		VLayout layoutAggregatoSmistamentoACTA = new VLayout(5);
		
		createDetailSectionAggregatoSmistamentoACTA();
		layoutAggregatoSmistamentoACTA.addMember(detailSectionAggregatoSmistamentoACTA);
		
		return layoutAggregatoSmistamentoACTA;
	}
	
	public boolean isRequiredDetailSectionAggregatoSmistamentoACTA() {
		String fascSmistACTA = AurigaLayout.getParametroDB("FASC_SMIST_ACTA");
		return fascSmistACTA != null && _MANDATORY.equalsIgnoreCase(fascSmistACTA);		
	}
	
	protected void createDetailSectionAggregatoSmistamentoACTA() {
		
		createFlgAggregatoACTAForm();		
		createAggregatoACTAForm();		
		createFlgSmistamentoACTAForm();		
		createSmistamentoACTAForm();		
		
		detailSectionAggregatoSmistamentoACTA = new NuovaPropostaAtto2CompletaDetailSection("Indicazione struttura aggregativa/smistamento", true, true, isRequiredDetailSectionAggregatoSmistamentoACTA(), flgAggregatoACTAForm, aggregatoACTAForm, flgSmistamentoACTAForm, smistamentoACTAForm);
	}	
	
	protected void createFlgAggregatoACTAForm() {
		
		flgAggregatoACTAForm = new DynamicForm();
		flgAggregatoACTAForm.setValuesManager(vm);
		flgAggregatoACTAForm.setWidth100();
		flgAggregatoACTAForm.setPadding(5);
		flgAggregatoACTAForm.setWrapItemTitles(false);
		flgAggregatoACTAForm.setNumCols(20);
		flgAggregatoACTAForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		flgAggregatoACTAForm.setTabSet(tabSet);
		flgAggregatoACTAForm.setTabID(_TAB_AGGREGATO_SMISTAMENTO_ACTA_ID);
		flgAggregatoACTAForm.setHeight(1);		
	
		flgAggregatoACTAItem = new CheckboxItem("flgAggregatoACTA", "STRUTTURA AGGREGATIVA") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(it.eng.utility.Styles.formTitle);
			}
		};
//		flgAggregatoACTAItem.setLabelAsTitle(true);
//		flgAggregatoACTAItem.setShowTitle(true);
//		flgAggregatoACTAItem.setTitleOrientation(TitleOrientation.RIGHT);        		
		flgAggregatoACTAItem.setDefaultValue(false);
		flgAggregatoACTAItem.setColSpan(1);
		flgAggregatoACTAItem.setWidth("*");
		flgAggregatoACTAItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {	
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgSmistamentoACTAItem.setValue(false);						
				} 
				redrawTabForms(_TAB_AGGREGATO_SMISTAMENTO_ACTA_ID);
			}
		});
		
		flgAggregatoACTAForm.setFields(flgAggregatoACTAItem);			
	}
	
	protected void createAggregatoACTAForm() {
		
		aggregatoACTAForm = new DynamicForm();
		aggregatoACTAForm.setLeft(100);
		aggregatoACTAForm.setBorder("1px solid grey");
		aggregatoACTAForm.setValuesManager(vm);
		aggregatoACTAForm.setWidth100();
		aggregatoACTAForm.setMargin(10);
		aggregatoACTAForm.setPadding(5);
		aggregatoACTAForm.setCellPadding(5);
		aggregatoACTAForm.setWrapItemTitles(false);
		aggregatoACTAForm.setNumCols(20);
		aggregatoACTAForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		aggregatoACTAForm.setTabSet(tabSet);
		aggregatoACTAForm.setTabID(_TAB_AGGREGATO_SMISTAMENTO_ACTA_ID);
		aggregatoACTAForm.setHeight(1);	
		
		flgIndiceClassificazioneACTAItem = new CheckboxItem("flgIndiceClassificazioneACTA", "");
		flgIndiceClassificazioneACTAItem.setStartRow(true);
		flgIndiceClassificazioneACTAItem.setDefaultValue(false);
		flgIndiceClassificazioneACTAItem.setColSpan(1);
		flgIndiceClassificazioneACTAItem.setWidth("*");
		flgIndiceClassificazioneACTAItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(getValueAsBoolean("flgAggregatoACTA")) {
					item.setCanEdit(editing);								
				} else {
					item.clearValue();
					item.setCanEdit(false);									
				}
				return true;
			}
		});
		flgIndiceClassificazioneACTAItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {						
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgFascicoloACTAItem.setValue(false);						
				} 
				aggregatoACTAForm.markForRedraw();
			}
		});
		
		codIndiceClassificazioneACTAItem = new ExtendedTextItem("codIndiceClassificazioneACTA", "Indice classificazione esteso");
		codIndiceClassificazioneACTAItem.setColSpan(15);
		codIndiceClassificazioneACTAItem.setWidth(766);
		codIndiceClassificazioneACTAItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(getValueAsBoolean("flgAggregatoACTA") && getValueAsBoolean("flgIndiceClassificazioneACTA")) {
					item.setCanEdit(editing);								
				} else {
					item.clearValue();
					item.setCanEdit(false);									
				}
				return true;
			}
		});
		codIndiceClassificazioneACTAItem.setAttribute("obbligatorio", true);
		codIndiceClassificazioneACTAItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
		
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return getValueAsBoolean("flgIndiceClassificazioneACTA");
			}
		}));
		codIndiceClassificazioneACTAItem.addChangedBlurHandler(new ChangedHandler(
				) {
			
			@Override
			public void onChanged(ChangedEvent event) {
				aggregatoACTAForm.clearErrors(true);
				aggregatoACTAForm.setValue("flgPresenzaClassificazioneACTA", false);
				verificaPresenzaIndiceClassificazioneACTA(new ServiceCallback<Boolean>() {
					
					@Override
					public void execute(Boolean presenzaIndiceClassificazione) {
						if(presenzaIndiceClassificazione != null && presenzaIndiceClassificazione) {
							aggregatoACTAForm.setValue("flgPresenzaClassificazioneACTA", true);							
						}
						aggregatoACTAForm.markForRedraw();
					}
					
					@Override
					public void manageError() {
						aggregatoACTAForm.markForRedraw();
					}
				});
			}
		});
		
		flgPresenzaClassificazioneACTAItem = new HiddenItem("flgPresenzaClassificazioneACTA");
		flgPresenzaClassificazioneACTAItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(!getValueAsBoolean("flgAggregatoACTA") || !getValueAsBoolean("flgIndiceClassificazioneACTA")) {
					item.clearValue();								
				}
				return false;
			}
		});
		
		ImgButtonItem iconaClassificazioneACTAPresente = new ImgButtonItem("iconaClassificazioneACTAPresente", "ok.png", "Indice di classif. esteso presente in ACTA");
		iconaClassificazioneACTAPresente.setAlwaysEnabled(true);
		iconaClassificazioneACTAPresente.setColSpan(1);
		iconaClassificazioneACTAPresente.setIconWidth(16);
		iconaClassificazioneACTAPresente.setIconHeight(16);
		iconaClassificazioneACTAPresente.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaClassificazioneACTAPresente.setAlign(Alignment.LEFT);
		iconaClassificazioneACTAPresente.setWidth(16);
		iconaClassificazioneACTAPresente.setRedrawOnChange(true);
		iconaClassificazioneACTAPresente.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return getValueAsBoolean("flgPresenzaClassificazioneACTA");
			}
		});
		
		flgFascicoloACTAItem = new CheckboxItem("flgFascicoloACTA", "");
		flgFascicoloACTAItem.setStartRow(true);
		flgFascicoloACTAItem.setDefaultValue(false);
		flgFascicoloACTAItem.setColSpan(1);
		flgFascicoloACTAItem.setWidth("*");
		flgFascicoloACTAItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(getValueAsBoolean("flgAggregatoACTA")) {
					item.setCanEdit(editing);								
				} else {
					item.clearValue();
					item.setCanEdit(false);									
				}
				return true;
			}
		});
		flgFascicoloACTAItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {						
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgIndiceClassificazioneACTAItem.setValue(false);						
				} 
				aggregatoACTAForm.markForRedraw();
			}
		});
		
		codVoceTitolarioACTAItem = new ExtendedTextItem("codVoceTitolarioACTA", "Voce di titolario");
		codVoceTitolarioACTAItem.setKeyPressFilter("[0-9a-zA-Z.]");		
		codVoceTitolarioACTAItem.setWidth(200);		
		codVoceTitolarioACTAItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Indicare i codici dei vari livelli di classificazione separati con \".\", ad es: 50.50.3";
			}
		});
		codVoceTitolarioACTAItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(getValueAsBoolean("flgAggregatoACTA") && getValueAsBoolean("flgFascicoloACTA")) {
					item.setCanEdit(editing);								
				} else {
					item.clearValue();
					item.setCanEdit(false);									
				}
				return true;
			}
		});
		codVoceTitolarioACTAItem.setAttribute("obbligatorio", true);
		codVoceTitolarioACTAItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
		
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return getValueAsBoolean("flgFascicoloACTA");
			}
		}));
		codVoceTitolarioACTAItem.addChangedBlurHandler(new ChangedHandler(
				) {
			
			@Override
			public void onChanged(ChangedEvent event) {
				aggregatoACTAForm.clearErrors(true);
				aggregatoACTAForm.setValue("idFascicoloACTA", "");
				recuperaIdFascicoloDossierACTA(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idFascicoloDossier) {
						if(idFascicoloDossier != null && !"".equals(idFascicoloDossier)) {
							aggregatoACTAForm.setValue("idFascicoloACTA", idFascicoloDossier);							
						}
						aggregatoACTAForm.markForRedraw();
					}
					
					@Override
					public void manageError() {
						aggregatoACTAForm.markForRedraw();
					}
				});
			}
		});
		
		codFascicoloACTAItem = new ExtendedTextItem("codFascicoloACTA", "Numero fascicolo/dossier");
		codFascicoloACTAItem.setWidth(200);
		codFascicoloACTAItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Indicare il n.ro del fascicolo o dossier senza il cod. AOO che va nel campo successivo";
			}
		});
		codFascicoloACTAItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(getValueAsBoolean("flgAggregatoACTA") && getValueAsBoolean("flgFascicoloACTA")) {
					item.setCanEdit(editing);								
				} else {
					item.clearValue();
					item.setCanEdit(false);									
				}
				return true;
			}
		});
		codFascicoloACTAItem.setAttribute("obbligatorio", true);
		codFascicoloACTAItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
		
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return getValueAsBoolean("flgFascicoloACTA");
			}
		}));
		codFascicoloACTAItem.addChangedBlurHandler(new ChangedHandler(
				) {
			
			@Override
			public void onChanged(ChangedEvent event) {
				aggregatoACTAForm.clearErrors(true);
				aggregatoACTAForm.setValue("idFascicoloACTA", "");
				recuperaIdFascicoloDossierACTA(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idFascicoloDossier) {
						if(idFascicoloDossier != null && !"".equals(idFascicoloDossier)) {
							aggregatoACTAForm.setValue("idFascicoloACTA", idFascicoloDossier);							
						}
						aggregatoACTAForm.markForRedraw();
					}
					
					@Override
					public void manageError() {
						aggregatoACTAForm.markForRedraw();
					}
				});
			}
		});
		
		suffissoCodFascicoloACTAItem = new ExtendedTextItem("suffissoCodFascicoloACTA", "/");
		suffissoCodFascicoloACTAItem.setWidth(200);
		suffissoCodFascicoloACTAItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Indicare il cod. AOO dell'AOO cui appartiene il fascicolo/dossier";
			}
		});
		suffissoCodFascicoloACTAItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(getValueAsBoolean("flgAggregatoACTA") && getValueAsBoolean("flgFascicoloACTA")) {
					item.setCanEdit(editing);								
				} else {
					item.clearValue();
					item.setCanEdit(false);									
				}
				return true;
			}
		});
//		suffissoCodFascicoloACTAItem.setAttribute("obbligatorio", true); //lo commento altrimenti si vede l'asterisco con il trattino
		suffissoCodFascicoloACTAItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
		
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return getValueAsBoolean("flgFascicoloACTA");
			}
		}));
		suffissoCodFascicoloACTAItem.addChangedBlurHandler(new ChangedHandler(
				) {
			
			@Override
			public void onChanged(ChangedEvent event) {
				aggregatoACTAForm.clearErrors(true);
				aggregatoACTAForm.setValue("idFascicoloACTA", "");
				recuperaIdFascicoloDossierACTA(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idFascicoloDossier) {
						if(idFascicoloDossier != null && !"".equals(idFascicoloDossier)) {
							aggregatoACTAForm.setValue("idFascicoloACTA", idFascicoloDossier);							
						}
						aggregatoACTAForm.markForRedraw();
					}
					
					@Override
					public void manageError() {
						aggregatoACTAForm.markForRedraw();
					}
				});
			}
		});
		
		idFascicoloACTAItem = new HiddenItem("idFascicoloACTA");
		idFascicoloACTAItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(!getValueAsBoolean("flgAggregatoACTA") || !getValueAsBoolean("flgFascicoloACTA")) {
					item.clearValue();								
				}
				return false;
			}
		});
		
		ImgButtonItem iconaFascicoloACTAPresente = new ImgButtonItem("iconaFascicoloACTAPresente", "ok.png", "Fascicolo/dossier presente in ACTA");
		iconaFascicoloACTAPresente.setAlwaysEnabled(true);
		iconaFascicoloACTAPresente.setColSpan(1);
		iconaFascicoloACTAPresente.setIconWidth(16);
		iconaFascicoloACTAPresente.setIconHeight(16);
		iconaFascicoloACTAPresente.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaFascicoloACTAPresente.setAlign(Alignment.LEFT);
		iconaFascicoloACTAPresente.setWidth(16);
		iconaFascicoloACTAPresente.setRedrawOnChange(true);
		iconaFascicoloACTAPresente.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !"".equals(getValueAsString("idFascicoloACTA"));
			}
		});
		
		aggregatoACTAForm.setFields(
			flgIndiceClassificazioneACTAItem, 
			codIndiceClassificazioneACTAItem, 
			flgPresenzaClassificazioneACTAItem, 
			iconaClassificazioneACTAPresente,
			flgFascicoloACTAItem,
			codVoceTitolarioACTAItem,
			codFascicoloACTAItem,
			suffissoCodFascicoloACTAItem,
			idFascicoloACTAItem,
			iconaFascicoloACTAPresente
		);			
	}
	
	public String getCodAOOXSelNodoACTA() {
		return getValueAsString("codAOOXSelNodoACTA");
	}
	
	public String getCodStrutturaXSelNodoACTA() {
		return getValueAsString("codStrutturaXSelNodoACTA");
	}
			
	public void verificaPresenzaIndiceClassificazioneACTA(final ServiceCallback<Boolean> callback) {
		if(!"".equals(getValueAsString("codIndiceClassificazioneACTA"))) {
			Record lRecord = new Record();			
			lRecord.setAttribute("indiceClassificazioneEstesa", getValueAsString("codIndiceClassificazioneACTA"));
			GWTRestDataSource lACTADataSource = new GWTRestDataSource("ACTADataSource");
			lACTADataSource.addParam("aooCode", getCodAOOXSelNodoACTA());
			lACTADataSource.addParam("structurCode", getCodStrutturaXSelNodoACTA());
			lACTADataSource.performCustomOperation("getClassificazioneEstesa", lRecord, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						if(callback != null) {
							callback.execute(response.getData()[0].getAttributeAsBoolean("presenzaIndiceClassificazione"));
						} 
					} else {
						if(callback != null) {
							callback.execute(null);
						} 
					}
				}
			});
		}
	}

	public void recuperaIdFascicoloDossierACTA(final ServiceCallback<String> callback) {
		if(!"".equals(getValueAsString("codVoceTitolarioACTA")) && !"".equals(getValueAsString("codFascicoloACTA")) && !"".equals(getValueAsString("suffissoCodFascicoloACTA"))) {
			Record lRecord = new Record();			
			lRecord.setAttribute("descrizioneVoceTitolario", getValueAsString("codVoceTitolarioACTA"));
			lRecord.setAttribute("codiceFascicoloDossier", getValueAsString("codFascicoloACTA"));
			lRecord.setAttribute("suffissoCodiceFascicoloDossier", getValueAsString("suffissoCodFascicoloACTA"));
			GWTRestDataSource lACTADataSource = new GWTRestDataSource("ACTADataSource");
			lACTADataSource.addParam("aooCode", getCodAOOXSelNodoACTA());
			lACTADataSource.addParam("structurCode", getCodStrutturaXSelNodoACTA());			
			lACTADataSource.performCustomOperation("getFascicoloDossierInVoceTitolario", lRecord, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						if(callback != null) {
							callback.execute(response.getData()[0].getAttributeAsString("idFascicoloDossier"));
						} 
					} else {
						if(callback != null) {
							callback.execute(null);
						} 
					}
				}
			});
		}
	}

	protected void createFlgSmistamentoACTAForm() {
		
		flgSmistamentoACTAForm = new DynamicForm();
		flgSmistamentoACTAForm.setValuesManager(vm);
		flgSmistamentoACTAForm.setWidth100();
		flgSmistamentoACTAForm.setPadding(5);
		flgSmistamentoACTAForm.setWrapItemTitles(false);
		flgSmistamentoACTAForm.setNumCols(20);
		flgSmistamentoACTAForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		flgSmistamentoACTAForm.setTabSet(tabSet);
		flgSmistamentoACTAForm.setTabID(_TAB_AGGREGATO_SMISTAMENTO_ACTA_ID);
		flgSmistamentoACTAForm.setHeight(1);		
	
		flgSmistamentoACTAItem = new CheckboxItem("flgSmistamentoACTA", "SMISTAMENTO") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(it.eng.utility.Styles.formTitle);
			}
		};
//		flgSmistamentoACTAItem.setLabelAsTitle(true);
//		flgSmistamentoACTAItem.setShowTitle(true);
//		flgSmistamentoACTAItem.setTitleOrientation(TitleOrientation.RIGHT);        
		flgSmistamentoACTAItem.setDefaultValue(false);
		flgSmistamentoACTAItem.setColSpan(1);
		flgSmistamentoACTAItem.setWidth("*");
		flgSmistamentoACTAItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {						
				if (event.getValue() != null && (Boolean) event.getValue()) {
					flgAggregatoACTAItem.setValue(false);						
				} 
				redrawTabForms(_TAB_AGGREGATO_SMISTAMENTO_ACTA_ID);
			}
		});
		
		flgSmistamentoACTAForm.setFields(flgSmistamentoACTAItem);			
	}
	
	protected void createSmistamentoACTAForm() {
		
		smistamentoACTAForm = new DynamicForm();
		smistamentoACTAForm.setBorder("1px solid grey");
		smistamentoACTAForm.setLeft(100);
		smistamentoACTAForm.setValuesManager(vm);
		smistamentoACTAForm.setWidth100();
		smistamentoACTAForm.setMargin(10);
		smistamentoACTAForm.setPadding(5);
		smistamentoACTAForm.setCellPadding(5);
		smistamentoACTAForm.setWrapItemTitles(false);
		smistamentoACTAForm.setNumCols(20);
		smistamentoACTAForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		smistamentoACTAForm.setTabSet(tabSet);
		smistamentoACTAForm.setTabID(_TAB_AGGREGATO_SMISTAMENTO_ACTA_ID);
		smistamentoACTAForm.setHeight(1);
		
		GWTRestDataSource nodiSmistamentoActaDS = new GWTRestDataSource("ACTADataSource", "idNodo", FieldType.TEXT);
				
		idNodoSmistamentoACTAItem = new SelectItem("idNodoSmistamentoACTA", "Nodo a cui smistare") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);				
				smistamentoACTAForm.clearErrors(true);				
				smistamentoACTAForm.setValue("desNodoSmistamentoACTA", record.getAttributeAsString("codiceNodo"));				
			}
	
			@Override
			protected void clearSelect() {
				super.clearSelect();
				smistamentoACTAForm.clearErrors(true);				
				smistamentoACTAForm.setValue("idNodoSmistamentoACTA", "");
				smistamentoACTAForm.setValue("desNodoSmistamentoACTA", "");				
			};
	
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					smistamentoACTAForm.clearErrors(true);				
					smistamentoACTAForm.setValue("idNodoSmistamentoACTA", "");
					smistamentoACTAForm.setValue("desNodoSmistamentoACTA", "");
				}
			}
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid idNodoSmistamentoACTAPickListProperties = super.builPickListProperties();	
				idNodoSmistamentoACTAPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource nodiSmistamentoActaDS = (GWTRestDataSource) idNodoSmistamentoACTAItem.getOptionDataSource();		
						nodiSmistamentoActaDS.addParam("aooCode", getCodAOOXSelNodoACTA());
						nodiSmistamentoActaDS.addParam("structurCode", getCodStrutturaXSelNodoACTA());
						idNodoSmistamentoACTAItem.setOptionDataSource(nodiSmistamentoActaDS);
						idNodoSmistamentoACTAItem.invalidateDisplayValueCache();
					}
				});
				return idNodoSmistamentoACTAPickListProperties;
			}
		};
		idNodoSmistamentoACTAItem.setColSpan(15);
		idNodoSmistamentoACTAItem.setWidth(766);
		idNodoSmistamentoACTAItem.setValueField("idNodo");
		idNodoSmistamentoACTAItem.setDisplayField("codiceNodo");
		idNodoSmistamentoACTAItem.setOptionDataSource(nodiSmistamentoActaDS);		
		idNodoSmistamentoACTAItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(getValueAsBoolean("flgSmistamentoACTA")) {
					item.setCanEdit(editing);								
				} else {
					item.clearValue();
					item.setCanEdit(false);									
				}
				return true;
			}
		});
		idNodoSmistamentoACTAItem.setAttribute("obbligatorio", true);
		idNodoSmistamentoACTAItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return getValueAsBoolean("flgSmistamentoACTA");
			}
		}));
		
		desNodoSmistamentoACTAItem = new HiddenItem("desNodoSmistamentoACTA");
	
		smistamentoACTAForm.setFields(idNodoSmistamentoACTAItem, desNodoSmistamentoACTAItem);			
	}
	
	/*******************************************************************************************************************************************************************/	

//	public void saveAndReloadTask() {
//		
//	}
	
	public void openSections() {		
		Set<DetailSection> sections = new HashSet<DetailSection>();
		for(DynamicForm form : getTabForms(_TAB_DATI_SCHEDA_ID)) {
			if(form.getDetailSection() != null) {
				sections.add(form.getDetailSection());
			}
		}			
		for(DynamicForm form : getTabForms(_TAB_DATI_DISPOSITIVO_ID)) {
			if(form.getDetailSection() != null) {
				sections.add(form.getDetailSection());
			}
		}			
		for(DynamicForm form : getTabForms(_TAB_DATI_DISPOSITIVO_2_ID)) {
			if(form.getDetailSection() != null) {
				sections.add(form.getDetailSection());
			}
		}
		for(DynamicForm form : getTabForms(_TAB_ALLEGATI_ID)) {
			if(form.getDetailSection() != null) {
				sections.add(form.getDetailSection());
			}
		}	
		for(DynamicForm form : getTabForms(_TAB_DATI_PUBBL_ID)) {
			if(form.getDetailSection() != null) {
				sections.add(form.getDetailSection());
			}
		}
		for(DynamicForm form : getTabForms(_TAB_MOVIMENTI_CONTABILI_ID)) {
			if(form.getDetailSection() != null) {
				sections.add(form.getDetailSection());
			}
		}
		for(DynamicForm form : getTabForms(_TAB_AGGREGATO_SMISTAMENTO_ACTA_ID)) {
			if(form.getDetailSection() != null) {
				sections.add(form.getDetailSection());
			}
		}
		for(DetailSection section : sections) {
			section.open();
		}
	}
	
	@Override
	public void editNewRecord() {
		
		super.editNewRecord();
		
		if(ufficioProponenteItem != null) {
			if (getUfficioProponenteValueMap().size() == 1) {
				String key = getUfficioProponenteValueMap().keySet().toArray(new String[1])[0];
				String value = getUfficioProponenteValueMap().get(key);
				ufficioProponenteItem.setValue((key != null && key.startsWith("UO")) ? key.substring(2) : key);
				if(value != null && !"".equals(value)) {
					codUfficioProponenteItem.setValue(value.substring(0, value.indexOf(" - ")));
					desUfficioProponenteItem.setValue(value.substring(value.indexOf(" - ") + 3));
				}
				afterSelezioneUoProponente();
			}
		}
		
		vociPEGNoVerifDisp = new HashSet<String>();
		
		redrawCKEditorItems();
		enableDisableTabs();
		showHideSections();
		openSections();
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		
		manageLoadSelectInEditNewRecord(initialValues, ufficioProponenteItem, "ufficioProponente", new String[] {"codUfficioProponente", "desUfficioProponente"}, " - ", "idUo");
		manageLoadSelectInEditNewRecord(initialValues, materiaTipoAttoItem, "materiaTipoAtto", new String[] {"desMateriaTipoAtto"}, "", "key");
		manageLoadSelectInEditNewRecord(initialValues, idNodoSmistamentoACTAItem, "idNodoSmistamentoACTA", new String[] {"desNodoSmistamentoACTA"}, "", "idNodo");
		
		super.editNewRecord(initialValues);
		
		if(ufficioProponenteItem != null) {
			if(ufficioProponenteItem.getValue() == null || "".equals(ufficioProponenteItem.getValue())) {
				if (getUfficioProponenteValueMap().size() == 1) {
					String key = getUfficioProponenteValueMap().keySet().toArray(new String[1])[0];
					String value = getUfficioProponenteValueMap().get(key);
					ufficioProponenteItem.setValue((key != null && key.startsWith("UO")) ? key.substring(2) : key);
					if(value != null && !"".equals(value)) {
						codUfficioProponenteItem.setValue(value.substring(0, value.indexOf(" - ")));
						desUfficioProponenteItem.setValue(value.substring(value.indexOf(" - ") + 3));
					}
					afterSelezioneUoProponente();
				}
			}
		}
		
		vociPEGNoVerifDisp = new HashSet<String>();
				
		// Devo settare manualmente i valori dei CKEditor
		if (luogoOrdMobilitaItem != null) {
			luogoOrdMobilitaItem.setValue((String) initialValues.get("luogoOrdMobilita"));
		}
		if (descrizioneOrdMobilitaItem != null) {
			descrizioneOrdMobilitaItem.setValue((String) initialValues.get("descrizioneOrdMobilita"));
		}		
		if (oggettoHtmlItem != null) {
			oggettoHtmlItem.setValue((String) initialValues.get("oggettoHtml"));
		}
		if(attiPresuppostiItem != null) {
			attiPresuppostiItem.setValue((String) initialValues.get("attiPresupposti"));
		}
		if(motivazioniItem != null) {
			motivazioniItem.setValue((String) initialValues.get("motivazioni"));
		}
		if(premessaItem != null) {
			premessaItem.setValue((String) initialValues.get("premessa"));
		}
		if(dispositivoItem != null) {
			dispositivoItem.setValue((String) initialValues.get("dispositivo"));
		}
		if(premessa2Item != null) {
			premessa2Item.setValue((String) initialValues.get("premessa2"));
		}
		if(dispositivo2Item != null) {
			dispositivo2Item.setValue((String) initialValues.get("dispositivo2"));
		}
		if(motiviImmediatamenteEseguibileItem != null) {
			motiviImmediatamenteEseguibileItem.setValue((String) initialValues.get("motiviImmediatamenteEseguibile"));
		}
		
		if(listaAllegatiItem != null) {
			if(listaAllegatiItem instanceof AllegatiGridItem) {
				((AllegatiGridItem) listaAllegatiItem).setFlgPubblicaAllegatiSeparati(initialValues.get("flgPubblicaAllegatiSeparati") != null ? (Boolean) initialValues.get("flgPubblicaAllegatiSeparati") : false);
			} else if(listaAllegatiItem instanceof AllegatiItem) {
				((AllegatiItem) listaAllegatiItem).setFlgPubblicaAllegatiSeparati(initialValues.get("flgPubblicaAllegatiSeparati") != null ? (Boolean) initialValues.get("flgPubblicaAllegatiSeparati") : false);
			}
		}
		
		redrawCKEditorItems();
		enableDisableTabs();
		showHideSections();		
		openSections();
	}
		
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, ufficioProponenteItem, "ufficioProponente", new String[] {"codUfficioProponente", "desUfficioProponente"}, " - ", "idUo");
		manageLoadSelectInEditRecord(record, materiaTipoAttoItem, "materiaTipoAtto", new String[] {"desMateriaTipoAtto"}, "", "key");
		manageLoadSelectInEditRecord(record, idNodoSmistamentoACTAItem, "idNodoSmistamentoACTA", new String[] {"desNodoSmistamentoACTA"}, "", "idNodo");
		
		super.editRecord(record);
		
		vociPEGNoVerifDisp = new HashSet<String>();
		if(record.getAttributeAsRecordList("listaVociPEGNoVerifDisp") != null) {
			for(int i = 0; i < record.getAttributeAsRecordList("listaVociPEGNoVerifDisp").getLength(); i++) {
				vociPEGNoVerifDisp.add(record.getAttributeAsRecordList("listaVociPEGNoVerifDisp").get(i).getAttribute("key"));
			}
		}
		
		// Quando sono in un task i dati di pubblicazione vengono caricati dalla CallExecAtt (vedi TaskNuovaPropostaAtto2CompletaDetail)
//		if(dataInizioPubblicazioneItem != null) {
//			dataInizioPubblicazioneItem.setValue(getDataInizioPubblicazioneValue());
//		}
//		if(giorniPubblicazioneItem != null) {
//			giorniPubblicazioneItem.setValue(getGiorniPubblicazioneValue());
//		}
		
		// Devo settare manualmente i valori dei CKEditor
		if (luogoOrdMobilitaItem != null) {
			luogoOrdMobilitaItem.setValue(record.getAttribute("luogoOrdMobilita"));
		}
		if (descrizioneOrdMobilitaItem != null) {
			descrizioneOrdMobilitaItem.setValue(record.getAttribute("descrizioneOrdMobilita"));
		}		
		if(oggettoHtmlItem != null) {
			oggettoHtmlItem.setValue(record.getAttribute("oggettoHtml"));
		}
		if(attiPresuppostiItem != null) {
			attiPresuppostiItem.setValue(record.getAttribute("attiPresupposti"));
		}
		if(motivazioniItem != null) {
			motivazioniItem.setValue(record.getAttribute("motivazioni"));
		}
		if(premessaItem != null) {
			premessaItem.setValue(record.getAttribute("premessa"));
		}
		if(dispositivoItem != null) {
			dispositivoItem.setValue(record.getAttribute("dispositivo"));
		}
		if(premessa2Item != null) {
			premessa2Item.setValue(record.getAttribute("premessa2"));
		}
		if(dispositivo2Item != null) {
			dispositivo2Item.setValue(record.getAttribute("dispositivo2"));
		}
		if(motiviImmediatamenteEseguibileItem != null) {
			motiviImmediatamenteEseguibileItem.setValue(record.getAttribute("motiviImmediatamenteEseguibile"));
		}
		
		if(listaAllegatiItem != null) {
			if(listaAllegatiItem instanceof AllegatiGridItem) {
				((AllegatiGridItem) listaAllegatiItem).setFlgPubblicaAllegatiSeparati(record.getAttributeAsBoolean("flgPubblicaAllegatiSeparati"));
			} else if(listaAllegatiItem instanceof AllegatiItem) {
				((AllegatiItem) listaAllegatiItem).setFlgPubblicaAllegatiSeparati(record.getAttributeAsBoolean("flgPubblicaAllegatiSeparati"));
			}
		}
		
		if(listaMovimentiContabiliaItem != null) {
			if(record.getAttribute("errorMessageMovimentiContabilia") != null && !"".equals(record.getAttribute("errorMessageMovimentiContabilia"))) {
				listaMovimentiContabiliaItem.setGridEmptyMessage(record.getAttribute("errorMessageMovimentiContabilia"));
			} else {
				listaMovimentiContabiliaItem.setGridEmptyMessage("Nessun dato trovato");
			}	
		}
		
		redrawCKEditorItems();
		enableDisableTabs();
		showHideSections();
		openSections();
		
		/****** [EMEND] ELIMINA RIGA PER EMENDAMENTI ******
		Record recordEmendamenti = new Record();
		recordEmendamenti.setAttribute("listaEmendamenti", record.getAttributeAsRecordList("listaEmendamenti"));
		emendamentiWindow.initContent(recordEmendamenti);
		****** [EMEND] ELIMINA RIGA PER EMENDAMENTI ******/
	}
	
	/**
	 * Metodo che abilita/disabilita i tab
	 * 
	 */
	public void enableDisableTabs() {
		
		for (Tab tab : tabSet.getTabs()) {
			if(tab != null) {
				String tabID = tab.getAttribute("tabID");
				if(tabID.equals("SPESAPERS")) {
					if(isDeterminaPersonale()) {					
						tabSet.enableTab(tab);
					} else {
						tabSet.disableTab(tab);
					}
				} else if(tabID.equals("SPESACORR")) {
					if(isDeterminaConSpesaCorrente()) {					
						tabSet.enableTab(tab);
					} else {
						tabSet.disableTab(tab);
					}
				} else if(tabID.equals("SPESACCAP")) {
					if(isDeterminaConSpesaContoCapitale()) {					
						tabSet.enableTab(tab);
					} else {
						tabSet.disableTab(tab);
					}
				} else if(tabID.equals("DATICONT")) {
					if(isDeterminaConSpesaCorrente() || isDeterminaConSpesaContoCapitale() || isDeterminaPersonale()) {					
						tabSet.disableTab(tab);
					} else {
						tabSet.enableTab(tab);
					}
				}						
				if(!isAvvioPropostaAtto()) {
					if(tabDatiSpesaCorrente != null) {
						if(isAttivoTabDatiSpesaCorrente()) {
							tabSet.enableTab(tabDatiSpesaCorrente);			
						} else {
							tabSet.disableTab(tabDatiSpesaCorrente);							
						}
					}	
					if(tabDatiSpesaContoCapitale != null) {
						if(isAttivoTabDatiSpesaContoCapitale()) {
							tabSet.enableTab(tabDatiSpesaContoCapitale);			
						} else {
							tabSet.disableTab(tabDatiSpesaContoCapitale);							
						}
					}	
				}
			}
		}
	}
	
	/**
	 * Metodo che mostra/nasconde le sezioni
	 * 
	 */
	public void showHideSections() {
		
		if(detailSectionRegistrazione != null) {			
			if(showDetailSectionRegistrazione()) {
				detailSectionRegistrazione.show();
			} else {
				detailSectionRegistrazione.hide();	
			}
		}
		
//		if(detailSectionPubblicazione != null) {			
//			if(showDetailSectionPubblicazione()) {
//				detailSectionPubblicazione.show();
//			} else {
//				detailSectionPubblicazione.hide();	
//			}
//		}
		
		if(detailSectionEmendamento != null) {
			if(showDetailSectionEmendamento()) {				
				detailSectionEmendamento.show();
			} else {
				detailSectionEmendamento.hide();	
			}
		}
		
		if(detailSectionDestinatariAtto != null) {
			if(showDetailSectionDestinatariAtto()) {				
				detailSectionDestinatariAtto.show();
			} else {
				detailSectionDestinatariAtto.hide();	
			}
		}
		
		if(detailSectionDestinatariPCAtto != null) {
			if(showDetailSectionDestinatariPCAtto()) {				
				detailSectionDestinatariPCAtto.show();
			} else {
				detailSectionDestinatariPCAtto.hide();	
			}
		}
		
		if(detailSectionTipoProposta != null) {
			if(showDetailSectionTipoProposta()) {				
				detailSectionTipoProposta.show();
			} else {
				detailSectionTipoProposta.hide();	
			}
		}
		
		if(detailSectionCircoscrizioni != null) {
			if(showDetailSectionCircoscrizioni()) {				
				detailSectionCircoscrizioni.show();
				if(isRequiredDetailSectionCircoscrizioni()) {
					detailSectionCircoscrizioni.setRequired(true);
				} else {
					detailSectionCircoscrizioni.setRequired(false);
				}
			} else {
				detailSectionCircoscrizioni.hide();	
			}
		}
		
		if(detailSectionInterpellanza != null) {
			if(showDetailSectionInterpellanza()) {				
				detailSectionInterpellanza.show();
			} else {
				detailSectionInterpellanza.hide();	
			}
		}
		
		if(detailSectionOrdMobilita != null) {
			if(showDetailSectionOrdMobilita()) {				
				detailSectionOrdMobilita.show();
			} else {
				detailSectionOrdMobilita.hide();	
			}
		}
		
		if(detailSectionRuoli != null) {
			if(showDetailSectionRuoli()) {				
				detailSectionRuoli.show();
			} else {
				detailSectionRuoli.hide();	
			}
		}
		
		if(detailSectionVistiDirSuperiori != null) {
			if(showDetailSectionVistiDirSuperiori()) {				
				detailSectionVistiDirSuperiori.show();
			} else {
				detailSectionVistiDirSuperiori.hide();	
			}
		}
		
		if(detailSectionParereCircoscrizioni != null) {
			if(showDetailSectionParereCircoscrizioni()) {				
				detailSectionParereCircoscrizioni.show();
			} else {
				detailSectionParereCircoscrizioni.hide();	
			}
		}
		
		if(detailSectionParereCommissioni != null) {
			if(showDetailSectionParereCommissioni()) {				
				detailSectionParereCommissioni.show();
			} else {
				detailSectionParereCommissioni.hide();	
			}
		}
		
		if(detailSectionOggetto != null) {
			if(showDetailSectionOggetto()) {				
				detailSectionOggetto.show();
			} else {
				detailSectionOggetto.hide();	
			}
		}
		
		if(detailSectionAttoRiferimento != null) {
			if(showDetailSectionAttoRiferimento()) {				
				detailSectionAttoRiferimento.show();
				if(isRequiredDetailSectionAttoRiferimento() || isDeterminaAggiudicaProceduraGara() || isDeterminaRimodulazioneSpesaGaraAggiudicata()) {
					detailSectionAttoRiferimento.setRequired(true);
				} else {
					detailSectionAttoRiferimento.setRequired(false);
				}
			} else {
				detailSectionAttoRiferimento.hide();	
			}			
		}
		
		if(detailSectionCaratteristicheProvvedimento != null) {
			if(showDetailSectionCaratteristicheProvvedimento()) {				
				detailSectionCaratteristicheProvvedimento.show();				
			} else {
				detailSectionCaratteristicheProvvedimento.hide();	
			}
		}
		
		if(detailSectionDestVantaggio != null) {
			if(showDetailSectionDestVantaggio()) {				
				detailSectionDestVantaggio.show();
				if(isRequiredDetailSectionDestVantaggio()) {
					detailSectionDestVantaggio.setRequired(true);
				} else {
					detailSectionDestVantaggio.setRequired(false);
				}
			} else {
				detailSectionDestVantaggio.hide();	
			}
		}
		
		if(detailSectionRuoliEVistiXDatiContabili != null) {
			if(showDetailSectionRuoliEVistiXDatiContabili()) {				
				detailSectionRuoliEVistiXDatiContabili.show();
				if(showVistiXDatiContabiliForm()) {
					vistiXDatiContabiliForm.show();
				} else {
					vistiXDatiContabiliForm.hide();
				}
			} else {
				detailSectionRuoliEVistiXDatiContabili.hide();	
			}
		}
		
		if(detailSectionCIG != null) {
			if(showDetailSectionCIG()) {				
				detailSectionCIG.show();
			} else {
				detailSectionCIG.hide();	
			}
		}
		
		if(detailSectionRiferimentiNormativi != null) {
			if(showDetailSectionRiferimentiNormativi()) {				
				detailSectionRiferimentiNormativi.show();
			} else {
				detailSectionRiferimentiNormativi.hide();	
			}
		}
		
		if(detailSectionAttiPresupposti != null) {
			if(showDetailSectionAttiPresupposti()) {				
				detailSectionAttiPresupposti.show();
			} else {
				detailSectionAttiPresupposti.hide();	
			}
		}
		
		if(detailSectionMotivazioni != null) {
			if(showDetailSectionMotivazioni()) {				
				detailSectionMotivazioni.show();
			} else {
				detailSectionMotivazioni.hide();	
			}
		}
		
		if(detailSectionPremessa != null) {
			if(showDetailSectionPremessa()) {				
				detailSectionPremessa.show();
			} else {
				detailSectionPremessa.hide();	
			}
		}
		
		if(detailSectionDispositivo != null) {
			if(showDetailSectionDispositivo()) {				
				detailSectionDispositivo.show();
			} else {
				detailSectionDispositivo.hide();	
			}
		}
		
		if(detailSectionPremessa2 != null) {
			if(showDetailSectionPremessa2()) {				
				detailSectionPremessa2.show();
			} else {
				detailSectionPremessa2.hide();	
			}
		}
		
		if(detailSectionDispositivo2 != null) {
			if(showDetailSectionDispositivo2()) {				
				detailSectionDispositivo2.show();
			} else {
				detailSectionDispositivo2.hide();	
			}
		}
		
		if(detailSectionPubblAlbo != null) {
			if(showDetailSectionPubblAlbo()) {				
				detailSectionPubblAlbo.show();
			} else {
				detailSectionPubblAlbo.hide();	
			}
		}
		
		if(detailSectionPubblAmmTrasp != null) {
			if(showDetailSectionPubblAmmTrasp()) {				
				detailSectionPubblAmmTrasp.show();
			} else {
				detailSectionPubblAmmTrasp.hide();	
			}
		}
		
		if(detailSectionPubblBUR != null) {
			if(showDetailSectionPubblBUR()) {				
				detailSectionPubblBUR.show();
			} else {
				detailSectionPubblBUR.hide();	
			}
		}
		
		if(detailSectionPubblNotiziario != null) {
			if(showDetailSectionPubblNotiziario()) {				
				detailSectionPubblNotiziario.show();
			} else {
				detailSectionPubblNotiziario.hide();	
			}
		}
		
		if(detailSectionEsecutivita != null) {
			if(showDetailSectionEsecutivita()) {				
				detailSectionEsecutivita.show();
			} else {
				detailSectionEsecutivita.hide();	
			}
		}
		
		if(detailSectionNotifiche != null) {
			if(showDetailSectionNotifiche()) {				
				detailSectionNotifiche.show();
			} else {
				detailSectionNotifiche.hide();	
			}
		}
		
		if(detailSectionDatiContabiliSIBCorrente != null) {
			if(showDetailSectionDatiContabiliSIBCorrente()) {				
				detailSectionDatiContabiliSIBCorrente.show();
			} else {
				detailSectionDatiContabiliSIBCorrente.hide();	
			}
		}
		
		if(detailSectionInvioDatiSpesaCorrente != null) {			
			if(showDetailSectionInvioDatiSpesaCorrente()) {				
				detailSectionInvioDatiSpesaCorrente.show();
			} else {
				detailSectionInvioDatiSpesaCorrente.hide();	
			}
		}
		
		if(detailSectionFileXlsCorrente != null) {			
			if(showDetailSectionFileXlsCorrente()) {								
				detailSectionFileXlsCorrente.show();
			} else {
				detailSectionFileXlsCorrente.hide();	
			}
		}
		
		if(detailSectionDatiContabiliSIBContoCapitale != null) {
			if(showDetailSectionDatiContabiliSIBContoCapitale()) {
				detailSectionDatiContabiliSIBContoCapitale.show();
			} else {
				detailSectionDatiContabiliSIBContoCapitale.hide();	
			}
		}
		
		if(detailSectionInvioDatiSpesaContoCapitale != null) {
			if(showDetailSectionInvioDatiSpesaContoCapitale()) {
				detailSectionInvioDatiSpesaContoCapitale.show();
			} else {
				detailSectionInvioDatiSpesaContoCapitale.hide();	
			}
		}
		
		if(detailSectionFileXlsContoCapitale != null) {
			if(showDetailSectionFileXlsContoCapitale()) {
				detailSectionFileXlsContoCapitale.show();
			} else {
				detailSectionFileXlsContoCapitale.hide();	
			}
		}
	}
	
	public void redrawCKEditorItems() {
		if(luogoOrdMobilitaItem != null) {
			luogoOrdMobilitaItem.redraw();			
		}
		if(motiviImmediatamenteEseguibileItem != null) {
			motiviImmediatamenteEseguibileItem.redraw();						
		}	
	}	
	
	public DynamicForm[] getTabForms(String tabID) {
		
		ArrayList<DynamicForm> listaTabForms = new ArrayList<DynamicForm>();
		for(DynamicForm form : vm.getMembers()) {
			if(form.getTabID() != null && form.getTabID().equalsIgnoreCase(tabID)) {
				listaTabForms.add(form);
			}
		}
		return listaTabForms.toArray(new DynamicForm[listaTabForms.size()]);
	}
	
	public void addTabValues(Record record, String tabID) {
		
		for(DynamicForm form : getTabForms(tabID)) {
			addFormValues(record, form);
		}
	}
	
	public void redrawTabForms(String tabID) {
		
		for(DynamicForm form : getTabForms(tabID)) {
			form.markForRedraw();
		}
	}
	
	public Record getRecordToSave() {
		
		final Record lRecordToSave = new Record();
		
		lRecordToSave.setAttribute("parametriTipoAtto", recordParametriTipoAtto);
		
		addTabValues(lRecordToSave, _TAB_DATI_SCHEDA_ID);
		if(showTabDatiDispositivo()) {
			addTabValues(lRecordToSave, _TAB_DATI_DISPOSITIVO_ID);
		}
		if(showTabDatiDispositivo2()) {
			addTabValues(lRecordToSave, _TAB_DATI_DISPOSITIVO_2_ID);
		}
		addTabValues(lRecordToSave, _TAB_ALLEGATI_ID);	
		if(showTabPubblicazioneNotifiche()) {
			addTabValues(lRecordToSave, _TAB_DATI_PUBBL_ID);
		}
		if(showTabMovimentiContabili()) {
			addTabValues(lRecordToSave, _TAB_MOVIMENTI_CONTABILI_ID);
		}
		if(isAttivoTabDatiSpesaCorrente()) {
			addTabValues(lRecordToSave, _TAB_DATI_SPESA_CORRENTE_ID);
		}
		if(isAttivoTabDatiSpesaContoCapitale()) {
			addTabValues(lRecordToSave, _TAB_DATI_SPESA_CONTO_CAPITALE_ID);
		}
		if(showTabAggregatoSmistamentoACTA()) {
			addTabValues(lRecordToSave, _TAB_AGGREGATO_SMISTAMENTO_ACTA_ID);
		}
		
//		if(getValueAsDate("dataInizioPubblicazione") == null) {
//			lRecordToSave.setAttribute("dataInizioPubblicazione", getDataInizioPubblicazioneValue());
//		}
//		if("".equals(getValueAsString("giorniPubblicazione"))) {
//			lRecordToSave.setAttribute("giorniPubblicazione", getGiorniPubblicazioneValue());
//		}
		
		if(isAbilToSelUffPropEsteso()) {
			RecordList listaUfficioProponente = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaUfficioProponente") : null;
			if(listaUfficioProponente != null && listaUfficioProponente.getLength() > 0) {
				lRecordToSave.setAttribute("ufficioProponente", listaUfficioProponente.get(0).getAttribute("idUo"));		
				lRecordToSave.setAttribute("codUfficioProponente", listaUfficioProponente.get(0).getAttribute("codRapido"));
				lRecordToSave.setAttribute("desUfficioProponente", listaUfficioProponente.get(0).getAttribute("descrizione"));
			}
		}
		
		// Setto i valori dei CKEditor		
		lRecordToSave.setAttribute("luogoOrdMobilita", luogoOrdMobilitaItem != null ? luogoOrdMobilitaItem.getValue() : null);		
		lRecordToSave.setAttribute("descrizioneOrdMobilita", descrizioneOrdMobilitaItem != null ? descrizioneOrdMobilitaItem.getValue() : null);		
		lRecordToSave.setAttribute("oggettoHtml", oggettoHtmlItem != null ? oggettoHtmlItem.getValue() : null);
		if(showTabDatiDispositivo()) {
			lRecordToSave.setAttribute("attiPresupposti", attiPresuppostiItem != null ? attiPresuppostiItem.getValue() : null);
			lRecordToSave.setAttribute("motivazioni", motivazioniItem != null ? motivazioniItem.getValue() : null);
			lRecordToSave.setAttribute("premessa", premessaItem != null ? premessaItem.getValue() : null);
			lRecordToSave.setAttribute("dispositivo", dispositivoItem != null ? dispositivoItem.getValue() : null);
		}		
		if(showTabDatiDispositivo2()) {
			lRecordToSave.setAttribute("premessa2", premessa2Item != null ? premessa2Item.getValue() : null);
			lRecordToSave.setAttribute("dispositivo2", dispositivo2Item != null ? dispositivo2Item.getValue() : null);
		}
		if(showTabPubblicazioneNotifiche()) {
			lRecordToSave.setAttribute("motiviImmediatamenteEseguibile", motiviImmediatamenteEseguibileItem != null ? motiviImmediatamenteEseguibileItem.getValue() : null);
		}
		
		if(!showDetailSectionDestinatariAtto()) {
			lRecordToSave.setAttribute("listaDestinatariAtto", new RecordList());
		}
		
		if(!showDetailSectionDestinatariPCAtto()) {
			lRecordToSave.setAttribute("listaDestinatariPCAtto", new RecordList());
		}
		
		String[] flgSpesaValueMap = getValoriPossibiliFlgSpesaItem();
		if(flgSpesaValueMap != null && flgSpesaValueMap.length == 1) {
			lRecordToSave.setAttribute("flgSpesa", flgSpesaValueMap[0]);
		}
		
		// Dati di spesa
		RecordList listaUfficioDefinizioneSpesa = ruoliXDatiContabiliForm1 != null ? ruoliXDatiContabiliForm1.getValueAsRecordList("listaUfficioDefinizioneSpesa") : null;
		if(listaUfficioDefinizioneSpesa != null && listaUfficioDefinizioneSpesa.getLength() > 0) {
			lRecordToSave.setAttribute("ufficioDefinizioneSpesa", listaUfficioDefinizioneSpesa.get(0).getAttribute("idUo"));				
			lRecordToSave.setAttribute("codUfficioDefinizioneSpesa", listaUfficioDefinizioneSpesa.get(0).getAttribute("codRapido"));
			lRecordToSave.setAttribute("desUfficioDefinizioneSpesa", listaUfficioDefinizioneSpesa.get(0).getAttribute("descrizione"));				
		}			
		
		// Attributi dinamici doc
		if (attributiAddDocDetails != null) {
			lRecordToSave.setAttribute("rowidDoc", rowidDoc);
			lRecordToSave.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordToSave.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
			lRecordToSave.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
		}
		
		if(hasDatiSensibili()) {
			lRecordToSave.setAttribute("flgDatiSensibili", true);
			lRecordToSave.setAttribute("flgPrivacy", _FLG_SI);
		}
		
		if(listaAllegatiItem != null) {
			if(listaAllegatiItem instanceof AllegatiGridItem) {
				lRecordToSave.setAttribute("flgPubblicaAllegatiSeparati", ((AllegatiGridItem) listaAllegatiItem).isFlgPubblicaAllegatiSeparati());
			} else if(listaAllegatiItem instanceof AllegatiItem) {
				lRecordToSave.setAttribute("flgPubblicaAllegatiSeparati", ((AllegatiItem) listaAllegatiItem).isFlgPubblicaAllegatiSeparati());
			}
		}
		
		if(isAttivoSICRA()) {
			if(listaInvioMovimentiContabiliSICRAItem != null) {
				lRecordToSave.setAttribute("listaMovimentiSICRAToDelete", listaInvioMovimentiContabiliSICRAItem.getListaMovimentiToDelete());
				lRecordToSave.setAttribute("listaMovimentiSICRAToInsert", listaInvioMovimentiContabiliSICRAItem.getListaMovimentiToInsert());
			}
		}
		
		return lRecordToSave;
	}
	
	public boolean isTaskDetail() {
		return instance instanceof TaskFlussoInterface;
	}
	
	public String getIdUoProponente() {
		
		if(isAbilToSelUffPropEsteso()) {
			RecordList listaUfficioProponente = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaUfficioProponente") : null;
			if(listaUfficioProponente != null && listaUfficioProponente.getLength() > 0) {
				return listaUfficioProponente.get(0).getAttribute("idUo");
			}
		} else {
			return getValueAsString("ufficioProponente");
		}
		return null;
	}
	
	public String getDirigenteAdottante() {
		RecordList listaAdottante = ruoliForm != null ? ruoliForm.getValueAsRecordList("listaAdottante") : null;
		if(listaAdottante != null && listaAdottante.getLength() > 0) {
			return listaAdottante.get(0).getAttribute("dirigenteAdottante");
		}
		return null;
	}
	
	protected String getIdUd() {
		return getValueAsString("idUd");
	}
	
	protected String getIdUdAttoDaAnnullare() {
		return null;
	}
	
	protected String getIdProcessTask() {
		return null;
	}

	protected String getIdProcessTypeTask() {
		return null;
	}
	
	protected String getIdTaskCorrente() {
		return null;
	}
	
	public boolean isEscludiFiltroCdCVsAMC() {
		return false;
	}
	
	/**
	 * Metodo che restituisce la mappa dei modelli relativi agli atti associati
	 * al task, da passare alla sezione degli allegati
	 * 
	 */
	public HashSet<String> getTipiModelliAttiInAllegati() {
		return null;
	}
	
	public boolean isObbligatorioFileInAllegati() {
		return true;
	}
	
	/**
	 * Metodo che chiama il servizio che genera l'allegato da modello iniettando i 
	 * valori presenti in maschera
	 * 
	 */
	// Questo metodo è sovrascritto in TaskNuovaPropostaAtto2CompletaDetail
	public void caricaModelloInAllegati(String idModello, String tipoModello, String flgConvertiInPdf, final ServiceCallback<Record> callback) {
		final GWTRestDataSource lGeneraDaModelloWithDatiDocDataSource = new GWTRestDataSource("GeneraDaModelloWithDatiDocDataSource");
		lGeneraDaModelloWithDatiDocDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
		lGeneraDaModelloWithDatiDocDataSource.executecustom("caricaModello", getRecordCaricaModelloInAllegati(idModello, tipoModello), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (callback != null) {
						callback.execute(response.getData()[0]);
					}
				}
			}
		});
	}
	
	/**
	 * Metodo che costruisce il record per la chiamata al servizio che genera
	 * l'allegato da modello iniettando i valori presenti in maschera
	 * 
	 */
	public Record getRecordCaricaModelloInAllegati(String idModello, String tipoModello) {
		
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);
		modelloRecord.setAttribute("idUd", getIdUd());
		if (attributiAddDocDetails != null) {
			modelloRecord.setAttribute("valori", getAttributiDinamiciDoc());
			modelloRecord.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
			modelloRecord.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
		}		
		return modelloRecord;
	}
		
	public boolean hasPrimarioDatiSensibili() {
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				if (item != null && item instanceof IDatiSensibiliItem) {
					if((item instanceof AllegatiItem) || (item instanceof AllegatiGridItem)) {
						// per vedere se ho dati sensibili non considero gli allegati ma solo il file priamrio
						continue;
					} else if(((IDatiSensibiliItem)item).hasDatiSensibili()) {
						return true;
					}
				}
			}
		}
//		if (attributiAddDocTabs != null) {
//			for (String key : attributiAddDocTabs.keySet()) {
//				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null && attributiAddDocDetails.get(key).hasDatiSensibili()) {
//					return true;
//				}
//			}
//		}
		return false;		 		
	}
	
	@Override
	public boolean hasDatiSensibili() {
		if(super.hasDatiSensibili()) {
			return true;
		}				
//		if (attributiAddDocTabs != null) {
//			for (String key : attributiAddDocTabs.keySet()) {
//				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null && attributiAddDocDetails.get(key).hasDatiSensibili()) {
//					return true;
//				}
//			}
//		}
		return false;		 		
	}
	
	public Object getValue(String fieldName) {
		return vm.getValue(fieldName) != null ? vm.getValue(fieldName) : "";
	}
	
	public String getValueAsString(String fieldName) {
		return vm.getValue(fieldName) != null ? (String) vm.getValue(fieldName) : "";
	}
	
	public Date getValueAsDate(String fieldName) {
		return vm.getValue(fieldName) != null ? (Date) vm.getValue(fieldName) : null;
	}
	
	public boolean getValueAsBoolean(String fieldName) {
		return vm.getValue(fieldName) != null ? (Boolean) vm.getValue(fieldName) : false;
	}
	
	@Override
	public boolean customValidate() {
		
		boolean valid = super.customValidate();
		
		// Faccio la validazione dei CKEditor obbligatori		
		if(luogoOrdMobilitaItem != null) {
			valid = luogoOrdMobilitaItem.validate() && valid;
		}		
		if(descrizioneOrdMobilitaItem != null) {
			valid = descrizioneOrdMobilitaItem.validate() && valid;
		}		
		if(oggettoHtmlItem != null) {
			valid = oggettoHtmlItem.validate() && valid;
		}
		if(attiPresuppostiItem != null) {
			valid = attiPresuppostiItem.validate() && valid;
		}
		if(motivazioniItem != null) {
			valid = motivazioniItem.validate() && valid;
		}
		if(premessaItem != null) {
			valid = premessaItem.validate() && valid;
		}
		if(dispositivoItem != null) {
			valid = dispositivoItem.validate() && valid;
		}
		if(premessa2Item != null) {
			valid = premessa2Item.validate() && valid;
		}
		if(dispositivo2Item != null) {
			valid = dispositivo2Item.validate() && valid;
		}
		if(motiviImmediatamenteEseguibileItem != null) {
			valid = motiviImmediatamenteEseguibileItem.validate() && valid;
		}
		
		if(listaAllegatiItem != null) {
			if(listaAllegatiItem instanceof AllegatiGridItem) {
				valid = ((AllegatiGridItem)listaAllegatiItem).validate() && valid;
			}
		}
				
		if(showCIGItem() && listaCIGItem != null && listaCIGItem.getEditing()) {
			HashSet<String> setCodiciCIG = new HashSet<String>();
			RecordList listaCIG = CIGForm.getValueAsRecordList("listaCIG");
			if(listaCIG != null) {
				for(int i=0; i < listaCIG.getLength(); i++) {
					if(listaCIG.get(i).getAttribute("codiceCIG") != null &&	!"".equals(listaCIG.get(i).getAttribute("codiceCIG"))) {
						if(setCodiciCIG.contains(listaCIG.get(i).getAttribute("codiceCIG"))) {
							CIGForm.setFieldErrors("listaCIG", "Uno o più CIG risultano uguali");
							valid = false;
							break;
						} else {
							setCodiciCIG.add(listaCIG.get(i).getAttribute("codiceCIG"));
						}
					}
				}				
			}
		}
		
		if(showTabAggregatoSmistamentoACTA()) {
			if(!getValueAsBoolean("flgAggregatoACTA") && !getValueAsBoolean("flgSmistamentoACTA")) {
				if(isRequiredDetailSectionAggregatoSmistamentoACTA()) {
					flgAggregatoACTAForm.setFieldErrors("flgAggregatoACTA", "Obbligatorio indicare la struttura aggregativa o lo smistamento");
					flgSmistamentoACTAForm.setFieldErrors("flgSmistamentoACTA", "Obbligatorio indicare la struttura aggregativa o lo smistamento");
					valid = false;			
				}
			} else if(getValueAsBoolean("flgAggregatoACTA")) {
				if(!getValueAsBoolean("flgIndiceClassificazioneACTA") && !getValueAsBoolean("flgFascicoloACTA")) {
					aggregatoACTAForm.setFieldErrors("flgIndiceClassificazioneACTA", "Obbligatorio indicare l'indice di classif. esteso o il fascicolo/dossier");
					aggregatoACTAForm.setFieldErrors("flgFascicoloACTA", "Obbligatorio indicare l'indice di classif. esteso o il fascicolo/dossier");
					valid = false;		
				} else if(getValueAsBoolean("flgIndiceClassificazioneACTA")) {
					if(!"".equals(getValueAsString("codIndiceClassificazioneACTA")) && !getValueAsBoolean("flgPresenzaClassificazioneACTA")) {
						aggregatoACTAForm.setFieldErrors("flgIndiceClassificazioneACTA", "Indice di classif. esteso non presente in ACTA");
						valid = false;	
					}
				} else if(getValueAsBoolean("flgFascicoloACTA")) {
					if(!"".equals(getValueAsString("codVoceTitolarioACTA")) && !"".equals(getValueAsString("codFascicoloACTA")) && !"".equals(getValueAsString("suffissoCodFascicoloACTA")) && "".equals(getValueAsString("idFascicoloACTA"))) {
						aggregatoACTAForm.setFieldErrors("flgFascicoloACTA", "Fascicolo/dossier non presente in ACTA");
						valid = false;	
					}
				}
			}	
		}
		
		if (attributiAddDocDetails != null) {
			for (String key : attributiAddDocDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
				if(!detail.customValidate()) {
					valid = false;
				}
				for (DynamicForm form : detail.getForms()) {
					form.clearErrors(true);
					valid = form.validate() && valid;
					for (FormItem item : form.getFields()) {
						if (item instanceof ReplicableItem) {
							ReplicableItem lReplicableItem = (ReplicableItem) item;
							boolean itemValid = lReplicableItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lReplicableItem != null && lReplicableItem.getForm() != null && lReplicableItem.getForm().getDetailSection() != null) {
									lReplicableItem.getForm().getDetailSection().open();
								}
							}
						}
					}
				}
			}
		}
		
		return valid;
	}
	
	public void salvaAttributiDinamiciDoc(boolean flgIgnoreObblig, String rowidDoc, String activityNameWF, String esitoActivityWF, final ServiceCallback<Record> callback) {
		if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
			Record lRecordDoc = new Record();
			lRecordDoc.setAttribute("rowId", rowidDoc);
			lRecordDoc.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordDoc.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
//			lRecordDoc.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
			lRecordDoc.setAttribute("activityNameWF", activityNameWF);
			lRecordDoc.setAttribute("esitoActivityWF", esitoActivityWF);
			GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("AttributiDinamiciDocDatasource");
			if (flgIgnoreObblig) {
				lGWTRestService.addParam("flgIgnoreObblig", "1");
			}
			lGWTRestService.call(lRecordDoc, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					if (callback != null) {
						callback.execute(object);
					}
				}
			});
		} else {
			if (callback != null) {
				callback.execute(new Record());
			}
		}
	}
	
	public void salvaAttributiDinamiciDocAfterSalva(boolean flgIgnoreObblig, String rowidDoc, String activityNameWF, String esitoActivityWF, final ServiceCallback<Record> callback) {		
		if (!AurigaLayout.getParametroDBAsBoolean("SET_ATTR_DINAMICI_DOC_AFTER_SAVE_ATTI")) {
			salvaAttributiDinamiciDoc(flgIgnoreObblig, rowidDoc, activityNameWF, esitoActivityWF, callback);
		} else {
			if (callback != null) {
				callback.execute(new Record());
			}			
		}
	}
	
	public void manageAttributiAddSenzaCategoria(RecordList attributiAddSenzaCategoria, Map mappaDettAttrLista, Map mappaValoriAttrLista, Map mappaVariazioniAttrLista) {
		
	}
	
	public void caricaAttributiDinamiciDoc(String nomeFlussoWF, String processNameWF, String activityName, String idTipoDoc, String rowidDoc) {
		
		final boolean isReload = (attributiAddDocDetails != null && attributiAddDocDetails.size() > 0);
		attributiAddDocLayouts = new HashMap<String, VLayout>();
		attributiAddDocDetails = new HashMap<String, AttributiDinamiciDetail>();
		if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			lGwtRestService.addParam("nomeFlussoWF", nomeFlussoWF);
			lGwtRestService.addParam("processNameWF", processNameWF);
			lGwtRestService.addParam("activityNameWF", activityName);
			Record lAttributiDinamiciRecord = new Record();
			lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_DOCUMENTS");
			lAttributiDinamiciRecord.setAttribute("rowId", rowidDoc);
			lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoDoc);
			lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					ricaricaTabSet();
					RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
					if (attributiAdd != null && !attributiAdd.isEmpty()) {						
						RecordList attributiAddSenzaCategoria = new RecordList();												
						HashMap<String, RecordList> mappaAttributiAddCategoria = new HashMap<String, RecordList>();						
						for (int i = 0; i < attributiAdd.getLength(); i++) {
							Record attr = attributiAdd.get(i);
							if (attr.getAttribute("categoria") != null && !"".equals(attr.getAttribute("categoria"))) {
								if(!mappaAttributiAddCategoria.containsKey(attr.getAttribute("categoria"))) {
									mappaAttributiAddCategoria.put(attr.getAttribute("categoria"), new RecordList());
								} 
								mappaAttributiAddCategoria.get(attr.getAttribute("categoria")).add(attr);
							} else {
								attributiAddSenzaCategoria.add(attr);
							}
						}						
						manageAttributiAddSenzaCategoria(attributiAddSenzaCategoria, object.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object.getAttributeAsMap("mappaVariazioniAttrLista"));						
						for (final String key : attributiAddDocTabs.keySet()) {							
							RecordList attributiAddCategoria = mappaAttributiAddCategoria.get(key) != null ? mappaAttributiAddCategoria.get(key) : new RecordList();							
							if (!attributiAddCategoria.isEmpty()) {
								if(key.equals("#HIDDEN")) {
									// Gli attributi che fanno parte di questo gruppo non li considero
								} else if (key.startsWith("HEADER_")) {
									AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
											.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
											.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null, tabSet, "HEADER");
									detail.setCanEdit(new Boolean(isEseguibile()));
									attributiAddDocDetails.put(key, detail);
									VLayout layoutFirstTab = (VLayout) tabSet.getTab(0).getPane();
									VLayout layout = (VLayout) layoutFirstTab.getMembers()[0];
									attributiAddDocLayouts.put(key, layout);
									int pos = 0;
									for (Canvas member : layout.getMembers()) {
										if (member instanceof HeaderDetailSection) {
											pos++;
										} else {
											break;
										}
									}
									for (DetailSection detailSection : attributiAddDocDetails.get(key).getDetailSections()) {
										if (isReload) {																
											DetailSection detailSectionToReload = ((DetailSection) layout.getMember(pos++));
											detailSectionToReload.setForms(detailSection.getForms());
										} else {			
											layout.addMember(detailSection, pos++);
										}
									}
								} else {								
									AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
											.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
											.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null, tabSet, key);
									detail.setCanEdit(new Boolean(isEseguibile()));
									String messaggioTab = getMessaggioTab(key);
									if (messaggioTab != null && !"".equals(messaggioTab)) {
										Label labelMessaggioTab = new Label(messaggioTab);
										labelMessaggioTab.setAlign(Alignment.LEFT);
										labelMessaggioTab.setWidth100();
										labelMessaggioTab.setHeight(2);
										labelMessaggioTab.setPadding(5);
										detail.addMember(labelMessaggioTab, 0);
									}
									attributiAddDocDetails.put(key, detail);
									VLayout layout = new VLayout();
									layout.setHeight100();
									layout.setWidth100();
									layout.setMembers(detail);
									attributiAddDocLayouts.put(key, layout);
									VLayout layoutTab = new VLayout();
									layoutTab.addMember(layout);									
									if (tabSet.getTabWithID(key) != null) {
										tabSet.getTabWithID(key).setPane(layoutTab);
									} else {
										Tab tab = new Tab("<b>" + attributiAddDocTabs.get(key) + "</b>");
										tab.setAttribute("tabID", key);
										tab.setPrompt(attributiAddDocTabs.get(key));
										tab.setPane(layoutTab);
										tabSet.addTab(tab);
									}									
								}
							}
						}						
					}
					afterCaricaAttributiDinamiciDoc();
				}
			});
		} else {
			ricaricaTabSet();
			afterCaricaAttributiDinamiciDoc();
		}
	}
	
	protected void ricaricaTabSet() {
		List<Tab> listaTab = new ArrayList<Tab>();	
		listaTab.add(tabDatiScheda);
		if(showTabDatiDispositivo()) {			
			listaTab.add(tabDatiDispositivo);
		}		
		if(showTabDatiDispositivo2()) {			
			listaTab.add(tabDatiDispositivo2);
		}
//		if(!isAvvioPropostaAtto()) {
			listaTab.add(tabAllegati);	
//		}
		if(showTabPubblicazioneNotifiche()) {
			listaTab.add(tabPubblicazioneNotifiche);
		}
		if(showTabMovimentiContabili()) {
			listaTab.add(tabMovimentiContabili);
		}
		if(showTabDatiSpesaCorrente()) {
			listaTab.add(tabDatiSpesaCorrente);
		}
		if(showTabDatiSpesaContoCapitale()) {
			listaTab.add(tabDatiSpesaContoCapitale);
		}
		if(showTabAggregatoSmistamentoACTA()) {
			listaTab.add(tabAggregatoSmistamentoACTA);
		}	
		tabSet.setTabs(listaTab.toArray(new Tab[listaTab.size()]));
	}
		
	/**
	 * Metodo che viene richiamato alla fine del caricamento degli attributi
	 * dinamici del documento
	 * 
	 */
	protected void afterCaricaAttributiDinamiciDoc() {
		if (attributiDinamiciDocDaCopiare != null) {
			setAttributiDinamiciDoc(attributiDinamiciDocDaCopiare);
			attributiDinamiciDocDaCopiare = null;
		}
		redrawCKEditorItems();
		enableDisableTabs();
		showHideSections();
		openSections();		
	}


	/**
	 * Metodo per settare i valori degli attributi dinamici associati al
	 * documento dopo l'azione del bottone "Nuova protocollazione come copia"
	 * 
	 */
	protected void setAttributiDinamiciDocDaCopiare(Map<String, Object> attributiDinamiciDocDaCopiare) {
		this.attributiDinamiciDocDaCopiare = attributiDinamiciDocDaCopiare;
		if (attributiDinamiciDocDaCopiare != null) {
			setAttributiDinamiciDoc(attributiDinamiciDocDaCopiare);
		}
	}

	/**
	 * Metodo per settare i valori degli attributi dinamici associati al
	 * documento
	 * 
	 */
	protected void setAttributiDinamiciDoc(Map<String, Object> valori) {
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					attributiAddDocDetails.get(key).editNewRecord(valori);
				}
			}
		}
	}

	/**
	 * Metodo per il recupero da maschera dei valori degli attributi dinamici 
	 * associati al documento, nella modalità per il caricamento dei dati a maschera:
	 * gli attributi LISTA hanno i valori interni mappati con i NOMI delle colonne 
	 * 
	 */
	public Map<String, Object> getAttributiDinamiciDocForLoad() {
		Map<String, Object> attributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (attributiDinamiciDoc == null) {
						attributiDinamiciDoc = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, 
					// i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					attributiDinamiciDoc.putAll(detailRecord.toMap());
				}
			}
		}
		return attributiDinamiciDoc;
	}
	
	/**
	 * Metodo per il recupero da maschera dei valori degli attributi dinamici
	 * associati al documento, nella modalità per il salvataggio dei dati da maschera:
	 * gli attributi LISTA hanno i valori interni mappati con i NUMERI delle colonne
	 * 
	 */
	public Map<String, Object> getAttributiDinamiciDoc() {
		Map<String, Object> attributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (attributiDinamiciDoc == null) {
						attributiDinamiciDoc = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, 
					// i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					attributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaValori(detailRecord));
				}
			}
		}
		return attributiDinamiciDoc;
	}

	/**
	 * Metodo per il recupero da maschera dei tipi degli attributi dinamici
	 * associati al documento
	 * 
	 */
	public Map<String, String> getTipiAttributiDinamiciDoc() {
		Map<String, String> tipiAttributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (tipiAttributiDinamiciDoc == null) {
						tipiAttributiDinamiciDoc = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal
					// vm, i valori degli attributi lista non li prende
					// correttamente
					// final Record detailRecord = new
					// Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					tipiAttributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaTipiValori(detailRecord));
				}
			}
		}
		return tipiAttributiDinamiciDoc;
	}
	
	/**
	 * Metodo per il recupero da maschera delle mappe delle colonne degli attributi dinamici
	 * di tipo LISTA associati al documento
	 * 
	 */
	public Map<String, String> getColonneListeAttributiDinamiciDoc() {
		Map<String, String> mappaColonneListaAttributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (mappaColonneListaAttributiDinamiciDoc == null) {
						mappaColonneListaAttributiDinamiciDoc = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal
					// vm, i valori degli attributi lista non li prende
					// correttamente
					// final Record detailRecord = new
					// Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					mappaColonneListaAttributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaColonneListe(detailRecord));
				}
			}
		}
		return mappaColonneListaAttributiDinamiciDoc;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		
		super.setCanEdit(canEdit);
		
		// i campi della sezione "Registrazione" devono essere sempre read-only
		setCanEdit(false, registrazioneForm); 
		setCanEdit(false, invioACTAForm);
		setCanEdit(false, attoLiquidazioneForm);
				
		if(siglaRegistrazioneItem != null) {
			siglaRegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			siglaRegistrazioneItem.setTabIndex(-1);
		}
		
		if(numeroRegistrazioneItem != null) {
			numeroRegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			numeroRegistrazioneItem.setTabIndex(-1);
		}
		
		if(dataRegistrazioneItem != null) {
			dataRegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			dataRegistrazioneItem.setTabIndex(-1);
		}
		
		if(desUserRegistrazioneItem != null) {
			desUserRegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			desUserRegistrazioneItem.setTabIndex(-1);
		}
		
		if(desUORegistrazioneItem != null) {
			desUORegistrazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			desUORegistrazioneItem.setTabIndex(-1);
		}
		
		if(estremiRepertorioPerStrutturaItem != null) {
			estremiRepertorioPerStrutturaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			estremiRepertorioPerStrutturaItem.setTabIndex(-1);
		}
		
		if(estremiAttoLiquidazioneItem != null) {
			estremiAttoLiquidazioneItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			estremiAttoLiquidazioneItem.setTabIndex(-1);
		}
		
		if(siglaRegProvvisoriaItem != null) {
			siglaRegProvvisoriaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			siglaRegProvvisoriaItem.setTabIndex(-1);
		}
		
		if(numeroRegProvvisoriaItem != null) {
			numeroRegProvvisoriaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			numeroRegProvvisoriaItem.setTabIndex(-1);
		}
		
		if(dataRegProvvisoriaItem != null) {
			dataRegProvvisoriaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			dataRegProvvisoriaItem.setTabIndex(-1);
		}
		
		if(desUserRegProvvisoriaItem != null) {
			desUserRegProvvisoriaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			desUserRegProvvisoriaItem.setTabIndex(-1);
		}
		
		if(desUORegProvvisoriaItem != null) {
			desUORegProvvisoriaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			desUORegProvvisoriaItem.setTabIndex(-1);
		}

		if (getUfficioProponenteValueMap().size() == 1) {
			if (ufficioProponenteItem != null) {
				ufficioProponenteItem.setCanEdit(false);
				ufficioProponenteItem.setTabIndex(-1);
			}
		}

		boolean isReadOnly = this instanceof TaskNuovaPropostaAtto2CompletaDetail && ((TaskNuovaPropostaAtto2CompletaDetail)this).isReadOnly();
		
		if(isAvvioEmendamentoFromAttoRif()) {
			tipoAttoEmendamentoItem.setCanEdit(false);
			siglaAttoEmendamentoItem.setCanEdit(false);
			numeroAttoEmendamentoItem.setCanEdit(false);
			annoAttoEmendamentoItem.setCanEdit(false);	
			idEmendamentoItem.setCanEdit(false);
			numeroEmendamentoItem.setCanEdit(false);
		} else {
			tipoAttoEmendamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_SU_TIPO_ATTO") ? canEdit : false);
			siglaAttoEmendamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_SU_ATTO_SIGLA_REG") ? canEdit : false);
			numeroAttoEmendamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_SU_ATTO_NRO") ? canEdit : false);
			annoAttoEmendamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_SU_ATTO_ANNO") ? canEdit : false);		 
			idEmendamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_ID") ? canEdit : false);
			numeroEmendamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_SUB_SU_EM_NRO") ? canEdit : false);
		}
		flgEmendamentoSuFileItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_SU_FILE") ? canEdit : false);
		numeroAllegatoEmendamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_SU_ALLEGATO_NRO") ? canEdit : false);
		flgEmendamentoIntegraleAllegatoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_INTEGRALE_ALLEGATO") ? canEdit : false);	
		numeroPaginaEmendamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_SU_PAGINA") ? canEdit : false);	
		numeroRigaEmendamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_SU_RIGA") ? canEdit : false);				
		effettoEmendamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("EMENDAMENTO_EFFETTO") ? canEdit : false);				
		flgAttivaDestinatariItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ATTIVA_SEZIONE_DESTINATARI") ? canEdit : false);	 
		listaDestinatariAttoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("DESTINATARI_ATTO") ? canEdit : false); 
		listaDestinatariPCAttoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("DESTINATARI_PC_ATTO") ? canEdit : false); 
		iniziativaPropostaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_INIZIATIVA_PROP_ATTO") ? canEdit : false);
		flgAttoMeroIndirizzoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_ATTO_MERO_INDIRIZZO") ? canEdit : false);
		flgModificaRegolamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_MODIFICA_REGOLAMENTO") ? canEdit : false);		
		flgModificaStatutoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_MODIFICA_STATUTO") ? canEdit : false);
		flgNominaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DEL_NOMINA") ? canEdit : false);
		flgRatificaDeliberaUrgenzaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_RATIFICA_DEL_URGENZA") ? canEdit : false);		
		flgAttoUrgenteItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_ATTO_URGENTE") ? canEdit : false);
		listaCircoscrizioniItem.setCanEdit(getFlgEditabileAttributoCustomCablato("CIRCOSCRIZIONE_PROPONENTE") ? canEdit : false);
		tipoInterpellanzaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_TIPO_INTERPELLANZA") ? canEdit : false);
		tipoOrdMobilitaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_TIPO_ORD_MOBILITA") ? canEdit : false);
		dataInizioVldOrdinanzaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("INIZIO_VLD_ORDINANZA") ? canEdit : false);
		dataFineVldOrdinanzaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FINE_VLD_ORDINANZA") ? canEdit : false);
		tipoLuogoOrdMobilitaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TIPO_LUOGO_ORD_MOBILITA") ? canEdit : false);
		listaIndirizziOrdMobilitaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ALTRE_UBICAZIONI") ? canEdit : false);
		luogoOrdMobilitaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("LUOGO_ORD_MOBILITA") ? canEdit : false);
		listaCircoscrizioniOrdMobilitaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("CIRCOSCRIZIONE_ORD_MOBILITA") ? canEdit : false);
		descrizioneOrdMobilitaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("DESCRIZIONE_ORD_MOBILITA") ? canEdit : false);
		listaAdottanteItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_ADOTTANTE") ? canEdit : false);
		centroDiCostoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("CDC_ATTO") ? canEdit : false);
		listaDirigentiConcertoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_RESP_DI_CONCERTO") ? canEdit : false);
		listaDirRespRegTecnicaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_DIR_RESP_REG_TECNICA") ? canEdit : false);
		listaAltriDirRespRegTecnicaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_ALTRI_DIR_REG_TECNICA") ? canEdit : false);
		listaRdPItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_RESP_PROC") ? canEdit : false);
		listaRUPItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_RUP") ? canEdit : false);
		listaAssessoriItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_ASSESSORE_PROPONENTE") ? canEdit : false);
		listaAltriAssessoriItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_ALTRI_ASSESSORI") ? canEdit : false);
		listaConsiglieriItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_CONSIGLIERE_PROPONENTE") ? canEdit : false);
		listaAltriConsiglieriItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_ALTRI_CONSIGLIERI") ? canEdit : false);
		listaDirigentiProponentiItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_DIR_PROPONENTE") ? canEdit : false);
		listaAltriDirigentiProponentiItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_ALTRI_DIR_PROPONENTI") ? canEdit : false);
		listaCoordinatoriCompCircItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_COORDINATORE_COMP_CIRC") ? canEdit : false);		
		flgRichiediVistoDirettoreItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_VISTO_DIR_SUP") ? canEdit : false);
		listaRespVistiConformitaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_RESP_VISTI_CONFORMITA") ? canEdit : false);
		listaEstensoriItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_ESTENSORE_MAIN") ? canEdit : false);
		listaAltriEstensoriItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_ALTRI_ESTENSORI") ? canEdit : false);
		listaIstruttoriItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_ISTRUTTORE_MAIN") ? canEdit : false);
		listaAltriIstruttoriItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_ALTRI_ISTRUTTORI") ? canEdit : false);
		flgVistoDirSup1Item.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_VISTO_DIR_SUP_1") ? canEdit : false);
		flgVistoDirSup2Item.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_VISTO_DIR_SUP_2") ? canEdit : false);
		listaParereCircoscrizioniItem.setCanEdit(getFlgEditabileAttributoCustomCablato("COD_CIRCOSCRIZIONE_X_PARERE") ? canEdit : false);
		listaParereCommissioniItem.setCanEdit(getFlgEditabileAttributoCustomCablato("COD_COMMISSIONE_X_PARERE") ? canEdit : false);
		if(!isReadOnly) {
			oggettoHtmlItem.setCanEdit((!isPresenteAttributoCustomCablato("OGGETTO_HTML") || getFlgEditabileAttributoCustomCablato("OGGETTO_HTML")) ? canEdit : false);
		}
		listaAttiRiferimentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ATTO_RIFERIMENTO") ? canEdit : false);
//		flgAttoRifASistemaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ATTO_RIF_A_SISTEMA") ? canEdit : false);
//		categoriaRegAttoDeterminaAContrarreItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ATTO_RIFERIMENTO") ? canEdit : false);
//		siglaAttoDeterminaAContrarreItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ATTO_RIFERIMENTO") ? canEdit : false);
//		numeroAttoDeterminaAContrarreItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ATTO_RIFERIMENTO") ? canEdit : false);
//		annoAttoDeterminaAContrarreItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ATTO_RIFERIMENTO") ? canEdit : false);	
//		lookupArchivioAttoDeterminaAContrarreButton.setCanEdit(getFlgEditabileAttributoCustomCablato("ATTO_RIFERIMENTO") ? canEdit : false);	
		oggLiquidazioneItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_OGG_LIQUIDAZIONE") ? canEdit : false);  
		dataScadenzaLiquidazioneItem.setCanEdit(getFlgEditabileAttributoCustomCablato("SCADENZA_LIQUIDAZIONE") ? canEdit : false);  
		urgenzaLiquidazioneItem.setCanEdit(getFlgEditabileAttributoCustomCablato("URGENZA_LIQUIDAZIONE") ? canEdit : false);  
		flgLiqXUffCassaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FLG_LIQ_X_UFF_CASSA") ? canEdit : false);  
		importoAnticipoCassaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("IMPORTO_ANTICIPO_CASSA") ? canEdit : false);  
		dataDecorrenzaContrattoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("DECORRENZA_CONTRATTO") ? canEdit : false);  
		anniDurataContrattoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ANNI_DURATA_CONTRATTO") ? canEdit : false);  
		flgAffidamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FLG_AFFIDAMENTO") ? canEdit : false);	
		flgDeterminaAContrarreTramiteProceduraGaraItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_CONTR_CON_GARA") ? canEdit : false);
		flgDeterminaAggiudicaProceduraGaraItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_AGGIUDICA_GARA") ? canEdit : false);
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_RIMOD_SPESA_GARA_AGGIUD") ? canEdit : false);
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_PERSONALE") ? canEdit : false);
		flgDeterminaRiaccertamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_RIACCERT") ? canEdit : false);		
		flgDeterminaAccertRadiazItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_ACCERT_RADIAZ") ? canEdit : false); 
		flgDeterminaVariazBilItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_VARIAZ_BIL") ? canEdit : false);
		flgVantaggiEconomiciItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FLG_VANTAGGI_ECONOMICI") ? canEdit : false);	
		flgDecretoReggioItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DECRETO_REGGIO") ? canEdit : false);
		flgAvvocaturaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_AVVOCATURA") ? canEdit : false);
		flgSpesaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA") ? canEdit : false);	
		flgCorteContiItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FLG_CORTE_CONTI") ? canEdit : false);
		flgLiqContestualeImpegnoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_LIQ_CONTESTUALE_IMPEGNO") ? canEdit : false);
		flgLiqContestualeAltriAspettiRilContItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_LIQ_CONTESTUALE_ALTRI_ASPETTI_RIL_CONT") ? canEdit : false);		
		flgCompQuadroFinRagDecItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_COMP_QUADRO_FIN_RAG_DEC") ? canEdit : false);		
		flgNuoviImpAccItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FLG_NUOVI_IMP_ACC") ? canEdit : false);	
		flgImpSuAnnoCorrenteItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FLG_IMPEGNI_SU_ANNO_CORRENTE") ? canEdit : false);	
		flgInsMovARagioneriaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_INS_MOV_A_RAGIONERIA") ? canEdit : false);	
		flgPresaVisioneContabilitaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_RICH_PRESA_VIS_CONTABILITA") ? canEdit : false);	
		flgSpesaCorrenteItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA_CORRENTE") ? canEdit : false);
		flgImpegniCorrenteGiaValidatiItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_CON_IMP_CORR_VALID") ? canEdit : false);
		flgSpesaContoCapitaleItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_CON_SPESA_CONTO_CAP") ? canEdit : false);
		flgImpegniContoCapitaleGiaRilasciatiItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_CON_IMP_CCAP_RIL") ? canEdit : false);
		flgSoloSubImpSubCronoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_CON_SUB") ? canEdit : false);
		tipoAttoInDeliberaPEGItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_TIPO_ATTO_IN_DEL_PEG") ? canEdit : false);
		tipoAffidamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TIPO_AFFIDAMENTO") ? canEdit : false);
		normRifAffidamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("NORM_RIF_AFFIDAMENTO") ? canEdit : false);
		respAffidamentoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("RESP_AFFIDAMENTO") ? canEdit : false);
		materiaTipoAttoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("MATERIA_NATURA_ATTO") ? canEdit : false);		
		flgFondiEuropeiPONItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FONDI_EUROPEI_PON") ? canEdit : false);
		flgFondiPRUItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FONDI_PRU") ? canEdit : false);
		flgVistoPar117_2013Item.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_VISTO_PAR_117_2013") ? canEdit : false);
		flgNotificaDaMessiItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_NOTIFICA_DA_MESSI") ? canEdit : false);
		flgLLPPItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_LLPP") ? canEdit : false);
		annoProgettoLLPPItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ANNO_PROGETTO_LLPP") ? canEdit : false);
		numProgettoLLPPItem.setCanEdit(getFlgEditabileAttributoCustomCablato("NRO_PROGETTO_LLPP") ? canEdit : false);		
		flgBeniServiziItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_BENI_SERVIZI") ? canEdit : false);
		annoProgettoBeniServiziItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ANNO_PROGETTO_BENI_SERVIZI") ? canEdit : false);
		numProgettoBeniServiziItem.setCanEdit(getFlgEditabileAttributoCustomCablato("NRO_PROGETTO_BENI_SERVIZI") ? canEdit : false);	
		flgPrivacyItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_ATTO_CON_DATI_RISERVATI") ? canEdit : false);		
		flgDatiProtettiTipo1Item.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_1") ? canEdit : false);	
		flgDatiProtettiTipo2Item.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_2") ? canEdit : false);	
		flgDatiProtettiTipo3Item.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_3") ? canEdit : false);	
		flgDatiProtettiTipo4Item.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_DATI_PROTETTI_TIPO_4") ? canEdit : false);	
		listaDestVantaggioItem.setCanEdit(getFlgEditabileAttributoCustomCablato("DEST_VANTAGGIO") ? canEdit : false);
		listaUfficioDefinizioneSpesaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_UO_COMP_SPESA") ? canEdit : false);
		opzAssCompSpesaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_OPZ_ASS_COMP_SPESA") ? canEdit : false);
		flgAdottanteUnicoRespPEGItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FLG_ADOTTANTE_UNICO_RESP_SPESA") ? canEdit : false);	
		listaResponsabiliPEGItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ID_SV_RESP_SPESA") ? canEdit : false);	
		flgRichVerificaDiBilancioCorrenteItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_CON_VRF_BIL_CORR") ? canEdit : false); 
		flgRichVerificaDiBilancioContoCapitaleItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_CON_VRF_BIL_CCAP") ? canEdit : false); 
		flgRichVerificaDiContabilitaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_DET_CON_VRF_CONTABIL") ? canEdit : false);
		flgConVerificaContabilitaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_RICH_VERIFICA_CONTABILITA") ? canEdit : false); 
		flgRichiediParereRevisoriContabiliItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_RICH_PARERE_REV_CONTABILI") ? canEdit : false);
		flgOpCommercialeItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FLG_OP_COMMERCIALE") ? canEdit : false);
		flgEscludiCIGItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FLG_ESCL_CIG") ? canEdit : false);
		motivoEsclusioneCIGItem.setCanEdit(getFlgEditabileAttributoCustomCablato("MOTIVO_ESCLUSIONE_CIG") ? canEdit : false);
		listaCIGItem.setCanEdit(getFlgEditabileAttributoCustomCablato("CIG") ? canEdit : false);
		listaRiferimentiNormativiItem.setCanEdit(getFlgEditabileAttributoCustomCablato("RIFERIMENTI_NORMATIVI") ? canEdit : false);
		attiPresuppostiItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ATTI_PRESUPPOSTI") ? canEdit : false);
		motivazioniItem.setCanEdit(getFlgEditabileAttributoCustomCablato("MOTIVAZIONI_ATTO") ? canEdit : false);
		premessaItem.setCanEdit(getFlgEditabileAttributoCustomCablato("PREMESSA_ATTO") ? canEdit : false);
		dispositivoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("DISPOSITIVO_ATTO") ? canEdit : false);
		premessa2Item.setCanEdit(getFlgEditabileAttributoCustomCablato("PREMESSA_ATTO_2") ? canEdit : false);
		dispositivo2Item.setCanEdit(getFlgEditabileAttributoCustomCablato("DISPOSITIVO_ATTO_2") ? canEdit : false);
		loghiAggiuntiviDispositivoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("LOGHI_DISPOSITIVO_ATTO") ? canEdit : false);
		if(!isReadOnly) {
			if(isPresenteAttributoCustomCablato("#ALLEGATI_PARTE_INTEGRANTE") && !getFlgEditabileAttributoCustomCablato("#ALLEGATI_PARTE_INTEGRANTE")) {
				if(listaAllegatiItem != null) {
					if(listaAllegatiItem instanceof AllegatiGridItem) {
						((AllegatiGridItem)listaAllegatiItem).readOnlyMode();
					} else if(listaAllegatiItem instanceof AllegatiItem) {
						((AllegatiItem)listaAllegatiItem).readOnlyMode();
					} 			
				}
			} else if(listaAllegatiItem != null) {
				listaAllegatiItem.setCanEdit(canEdit);
			}
		}
		flgPubblAlboItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_PUBBL_ALBO") ? canEdit : false);
		numGiorniPubblAlboItem.setCanEdit(getFlgEditabileAttributoCustomCablato("NRO_GIORNI_PUBBL_ALBO") ? canEdit : false);
		tipoDecorrenzaPubblAlboItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TIPO_DECORRENZA_PUBBL_ALBO") ? canEdit : false);
		dataPubblAlboDalItem.setCanEdit(getFlgEditabileAttributoCustomCablato("PUBBL_ALBO_DAL") ? canEdit : false);
		flgUrgentePubblAlboItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_URGENTE_PUBBL_ALBO") ? canEdit : false);
		dataPubblAlboEntroItem.setCanEdit(getFlgEditabileAttributoCustomCablato("PUBBL_ALBO_ENTRO") ? canEdit : false);		
		flgPubblAmmTraspItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_PUBBL_IN_TRASPARENZA") ? canEdit : false);
		sezionePubblAmmTraspItem.setCanEdit(getFlgEditabileAttributoCustomCablato("SEZ1_PUBBL_IN_TRASPARENZA") ? canEdit : false);
		sottoSezionePubblAmmTraspItem.setCanEdit(getFlgEditabileAttributoCustomCablato("SEZ2_PUBBL_IN_TRASPARENZA") ? canEdit : false);		
		flgPubblBURItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_PUBBL_BUR") ? canEdit : false);
		annoTerminePubblBURItem.setCanEdit(getFlgEditabileAttributoCustomCablato("ANNO_TERMINE_PUBBL_BUR") ? canEdit : false);
		tipoDecorrenzaPubblBURItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TIPO_DECORRENZA_PUBBL_BUR") ? canEdit : false);
		dataPubblBURDalItem.setCanEdit(getFlgEditabileAttributoCustomCablato("PUBBL_BUR_DAL") ? canEdit : false);
		flgUrgentePubblBURItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_URGENTE_PUBBL_BUR") ? canEdit : false);
		dataPubblBUREntroItem.setCanEdit(getFlgEditabileAttributoCustomCablato("PUBBL_BUR_ENTRO") ? canEdit : false);
		flgPubblNotiziarioItem.setCanEdit(getFlgEditabileAttributoCustomCablato("TASK_RESULT_2_FLG_PUBBL_NOTIZIARIO") ? canEdit : false);		
		dataEsecutivitaDalItem.setCanEdit(false); // è sempre read-only
		flgImmediatamenteEseguibileItem.setCanEdit(getFlgEditabileAttributoCustomCablato("FLG_IMMEDIATAMENTE_ESEGUIBILE") ? canEdit : false);
		motiviImmediatamenteEseguibileItem.setCanEdit(getFlgEditabileAttributoCustomCablato("MOTIVI_IE") ? canEdit : false);
		listaDestNotificaAttoItem.setCanEdit(getFlgEditabileAttributoCustomCablato("IND_EMAIL_DEST_NOTIFICA_ATTO") ? canEdit : false);
		if(isAttivoSICRA()) {
			if(listaInvioMovimentiContabiliSICRAItem != null) {
				// anche quando il dettaglio è in readOnly, questa lista può essere editabile (passi della ragioneria)
				listaInvioMovimentiContabiliSICRAItem.setCanEdit((isPresenteAttributoCustomCablato("MOVIMENTO_SICRA") && getFlgEditabileAttributoCustomCablato("MOVIMENTO_SICRA")) ? (canEdit || isReadOnly) : false);				
			}
		}
		
		openSections();
	}
	
	public boolean isAvvioEmendamentoFromAttoRif() {
		return false;
	}

	protected String buildIconHtml(String src, String value) {
		return "<div align=\"left\"><img src=\"images/" + src + "\" height=\"10\" width=\"10\" alt=\"\" />&nbsp;&nbsp;" + value + "</div>";
	}
	
	public boolean isEmendamentoSuFileAllegato() {
		return showFlgEmendamentoSuFileItem() && getValueAsString("flgEmendamentoSuFile") != null && _FLG_EMENDAMENTO_SU_FILE_A.equals(getValueAsString("flgEmendamentoSuFile"));	
	}
	
	public boolean isEmendamentoIntegraleAllegato() {
		return showFlgEmendamentoIntegraleAllegatoItem() && getValueAsBoolean("flgEmendamentoIntegraleAllegato");	
	}
		
	public boolean isAttoMeroIndirizzo() {
		return showFlgAttoMeroIndirizzoItem() && getValueAsBoolean("flgAttoMeroIndirizzo");
	}
	
	public boolean isModificaRegolamento() {
		return showFlgModificaRegolamentoItem() && getValueAsBoolean("flgModificaRegolamento");
	}
	
	public boolean isModificaStatuto() {
		return showFlgModificaStatutoItem() && getValueAsBoolean("flgModificaStatuto");
	}
	
	public boolean isNomina() {
		return showFlgNominaItem() && getValueAsBoolean("flgNomina");
	}
	
	public boolean isRatificaDeliberaUrgenza() {
		return showFlgRatificaDeliberaUrgenzaItem() && getValueAsBoolean("flgRatificaDeliberaUrgenza");
	}
	
	public boolean isAttoUrgente() {
		return showFlgAttoUrgenteItem() && getValueAsBoolean("flgAttoUrgente");
	}
	
	public boolean isDeterminaConSpesa() {
		String[] flgSpesaValueMap = getValoriPossibiliFlgSpesaItem();
		if(flgSpesaValueMap != null && flgSpesaValueMap.length == 1) {
			return _FLG_SI.equalsIgnoreCase(flgSpesaValueMap[0]);
		}
		return showFlgSpesaItem() && _FLG_SI.equalsIgnoreCase(getValueAsString("flgSpesa"));
	}
	
	public boolean isDeterminaConSpesaSenzaImpegni() {
		String[] flgSpesaValueMap = getValoriPossibiliFlgSpesaItem();
		if(flgSpesaValueMap != null && flgSpesaValueMap.length == 1) {
			return getFLG_SI_SENZA_VLD_RIL_IMP().equalsIgnoreCase(flgSpesaValueMap[0]);
		}
		
		return showFlgSpesaItem() && getFLG_SI_SENZA_VLD_RIL_IMP().equalsIgnoreCase(getValueAsString("flgSpesa"));
	}
	
	public boolean isDeterminaSenzaSpesa() {
		String[] flgSpesaValueMap = getValoriPossibiliFlgSpesaItem();
		if(flgSpesaValueMap != null && flgSpesaValueMap.length == 1) {
			return _FLG_NO.equalsIgnoreCase(flgSpesaValueMap[0]);
		}
		return !showFlgSpesaItem() || (showFlgSpesaItem() && _FLG_NO.equalsIgnoreCase(getValueAsString("flgSpesa")));
	}
		
	public boolean isDeterminaConSpesaCorrente() {
		return isDeterminaConSpesa() && showFlgSpesaCorrenteItem() && getValueAsBoolean("flgSpesaCorrente");				
	}
	
	public boolean isDeterminaConSpesaContoCapitale() {
		return isDeterminaConSpesa() && showFlgSpesaContoCapitaleItem() && getValueAsBoolean("flgSpesaContoCapitale");		
	}
	
	public boolean isAffidamento() {
		return showFlgAffidamentoItem() && getValueAsBoolean("flgAffidamento");
	}
	
	public boolean isDeterminaAContrarreTramiteProceduraGara() {
		return showFlgDeterminaAContrarreTramiteProceduraGaraItem() && getValueAsBoolean("flgDeterminaAContrarreTramiteProceduraGara");
	}
	
	public boolean isDeterminaAggiudicaProceduraGara() {
		return showFlgDeterminaAggiudicaProceduraGaraItem() && getValueAsBoolean("flgDeterminaAggiudicaProceduraGara");
	}

	public boolean isDeterminaRimodulazioneSpesaGaraAggiudicata() {
		return showFlgDeterminaRimodulazioneSpesaGaraAggiudicataItem() && getValueAsBoolean("flgDeterminaRimodulazioneSpesaGaraAggiudicata");
	}
	
	public boolean isDeterminaPersonale() {
		return showFlgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem() && getValueAsBoolean("flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoro");
	}
	
	public boolean isDeterminaRiaccertamento() {
		return showFlgDeterminaRiaccertamentoItem() && getValueAsBoolean("flgDeterminaRiaccertamento");
	}
	
	public boolean isDeterminaAccertRadiaz() {
		return showFlgDeterminaAccertRadiazItem() && getValueAsBoolean("flgDeterminaAccertRadiaz");
	}
	
	public boolean isDeterminaVariazBil() {
		return showFlgDeterminaVariazBilItem() && getValueAsBoolean("flgDeterminaVariazBil");
	}
	
	public boolean isVantaggiEconomici() {
		return showFlgVantaggiEconomiciItem() && getValueAsBoolean("flgVantaggiEconomici");
	}
	
	public boolean isDecretoReggio() {
		return showFlgDecretoReggioItem() && getValueAsBoolean("flgDecretoReggio");
	}
	
	public boolean isAvvocatura() {
		return showFlgAvvocaturaItem() && getValueAsBoolean("flgAvvocatura");
	}
	
	public boolean isPubblAlbo() {
		return showFlgPubblAlboItem() && getValueAsString("flgPubblAlbo") != null && _FLG_SI.equals(getValueAsString("flgPubblAlbo"));	
	}
	
	public boolean isDecorrenzaPubblAlboPosticipata() {
		return showTipoDecorrenzaPubblAlboItem() && getValueAsString("tipoDecorrenzaPubblAlbo") != null && _DECORR_PUBBL_POST.equals(getValueAsString("tipoDecorrenzaPubblAlbo"));	
	}
	
	public boolean isUrgentePubblAlbo() {
		return showFlgUrgentePubblAlboItem() && getValueAsBoolean("flgUrgentePubblAlbo");	
	}
	
	public boolean isPubblAmmTrasp() {
		return showFlgPubblAmmTraspItem() && getValueAsString("flgPubblAmmTrasp") != null && _FLG_SI.equals(getValueAsString("flgPubblAmmTrasp"));	
	}

	public boolean isPubblBUR() {
		return showFlgPubblBURItem() && getValueAsString("flgPubblBUR") != null && _FLG_SI.equals(getValueAsString("flgPubblBUR"));	
	}
	
	public boolean isDecorrenzaPubblBURPosticipata() {
		return showTipoDecorrenzaPubblBURItem() && getValueAsString("tipoDecorrenzaPubblBUR") != null && _DECORR_PUBBL_POST.equals(getValueAsString("tipoDecorrenzaPubblBUR"));	
	}
	
	public boolean isUrgentePubblBUR() {
		return showFlgUrgentePubblBURItem() && getValueAsBoolean("flgUrgentePubblBUR");	
	}
	
	public boolean isPubblNotiziario() {
		return showFlgPubblNotiziarioItem() && getValueAsString("flgPubblNotiziario") != null && _FLG_SI.equals(getValueAsString("flgPubblNotiziario"));	
	}
	
	public boolean isLLPP() {
		return showFlgLLPPItem() && getValueAsString("flgLLPP") != null && _FLG_SI.equals(getValueAsString("flgLLPP"));	
	}

	public boolean isBeniServizi() {
		return showFlgBeniServiziItem() && getValueAsString("flgBeniServizi") != null && _FLG_SI.equals(getValueAsString("flgBeniServizi"));
	}
	
	public boolean isDatiRiservati() {
		return showFlgPrivacyItem() && getValueAsString("flgPrivacy") != null && _FLG_SI.equals(getValueAsString("flgPrivacy"));
	}
	
	public Record getDetailRecordInAllegati() {
		Record lRecordAllegato = new Record();
		lRecordAllegato.setAttribute("idUd", getValueAsString("idUd"));
		lRecordAllegato.setAttribute("siglaProtocollo", !"".equals(getValueAsString("siglaRegistrazione")) ? getValueAsString("siglaRegistrazione") : getValueAsString("siglaRegProvvisoria"));
		lRecordAllegato.setAttribute("nroProtocollo", !"".equals(getValueAsString("numeroRegistrazione")) ? getValueAsString("numeroRegistrazione") : getValueAsString("numeroRegProvvisoria"));
		lRecordAllegato.setAttribute("dataProtocollo", getValueAsDate("dataRegistrazione") != null ? getValueAsDate("dataRegistrazione") : getValueAsDate("dataRegProvvisoria"));
		lRecordAllegato.setAttribute("desUserProtocollo", !"".equals(getValueAsString("desUserRegistrazione")) ? getValueAsString("desUserRegistrazione") : getValueAsString("desUserRegProvvisoria"));
		lRecordAllegato.setAttribute("desUOProtocollo", !"".equals(getValueAsString("desUORegistrazione")) ? getValueAsString("desUORegistrazione") : getValueAsString("desUORegProvvisoria"));
		return lRecordAllegato;
	}
	
	public String getIdModDispositivo() {
		return null;
	}
	
	public String getNomeModDispositivo() {
		return null;
	}
	
	public String getDisplayFilenameModDispositivo() {
		return null;
	}
	
	public void anteprimaProvvedimento(String nomeFilePrimario, String uriFilePrimario, InfoFileRecord infoFilePrimario, final String nomeFilePrimarioOmissis, final String uriFilePrimarioOmissis, final InfoFileRecord infoFilePrimarioOmissis) {
		if(uriFilePrimario != null && !"".equals(uriFilePrimario) && infoFilePrimario != null && infoFilePrimario.isFirmato()) {
			if(uriFilePrimarioOmissis != null && !"".equals(uriFilePrimarioOmissis)) {
				new PreviewWindow(uriFilePrimario, true, infoFilePrimario, "FileToExtractBean",	nomeFilePrimario) {
					
					@Override
					public void manageCloseClick() {
						super.manageCloseClick();
						new PreviewWindow(uriFilePrimarioOmissis, true, infoFilePrimarioOmissis, "FileToExtractBean", nomeFilePrimarioOmissis);
					};
				};		
			} else {
				new PreviewWindow(uriFilePrimario, true, infoFilePrimario, "FileToExtractBean", nomeFilePrimario);		
			} 
		} else {
			anteprimaDispositivoDaModello(hasPrimarioDatiSensibili());
		}
	}
	
	public void anteprimaDispositivoDaModello(boolean hasDatiSensibili) {
		if(hasDatiSensibili) {
			generaDispositivoDaModelloVersIntegrale(new ServiceCallback<Record>() {
				
				@Override
				public void execute(final Record recordPreview) {
					generaDispositivoDaModelloVersConOmissis(new ServiceCallback<Record>() {
						
						@Override
						public void execute(final Record recordPreviewOmissis) {
							previewWithCallback(recordPreview, new ServiceCallback<Record>() {
						
								@Override
								public void execute(Record object) {
									preview(recordPreviewOmissis);
								}
							});
						}
					});
				}
			});
		} else {
			generaDispositivoDaModelloVersIntegrale(new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record recordPreview) {
					preview(recordPreview);
				}
			});
		}
	}
	
	public void generaDispositivoDaModelloVersIntegrale() {
		generaDispositivoDaModelloVersIntegrale(null);
	}
	
	public void generaDispositivoDaModelloVersIntegrale(final ServiceCallback<Record> callback) {
		generaDispositivoDaModello(true, callback);
	}
	
	public void generaDispositivoDaModelloVersConOmissis() {
		generaDispositivoDaModelloVersConOmissis(null);
	}
	
	public void generaDispositivoDaModelloVersConOmissis(final ServiceCallback<Record> callback) {
		generaDispositivoDaModello(false, callback);
	}
	
	public void generaDispositivoDaModelloVersPerVerificaOmissis() {
		generaDispositivoDaModelloVersPerVerificaOmissis(null);
	}
	
	public void generaDispositivoDaModelloVersPerVerificaOmissis(final ServiceCallback<Record> callback) {
		generaDispositivoDaModello(true, true, callback);
	}
		
	public void generaDispositivoDaModello(boolean flgMostraDatiSensibili, final ServiceCallback<Record> callback) {
		generaDispositivoDaModello(flgMostraDatiSensibili, false, callback);
	}
	
	public void generaDispositivoDaModello(boolean flgMostraDatiSensibili, boolean flgMostraOmissisBarrati, final ServiceCallback<Record> callback) {			
		Record record = getRecordToSave();
		record.setAttribute("idModello", getIdModDispositivo());
		record.setAttribute("nomeModello", getNomeModDispositivo());
		record.setAttribute("displayFilenameModello", getDisplayFilenameModDispositivo());
		record.setAttribute("flgMostraDatiSensibili", flgMostraDatiSensibili);
		if(flgMostraDatiSensibili) {
			record.setAttribute("flgMostraOmissisBarrati", flgMostraOmissisBarrati);
		}		
		Layout.showWaitPopup("Generazione anteprima provvedimento in corso...");				
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("generaDispositivoDaModello", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordPreview = response.getData()[0];
					if(callback != null) {
						callback.execute(recordPreview);
					}
				}				
			}		
		});
	}
	
	public String getIdModAppendice() {
		return null;
	}
	
	public String getNomeModAppendice() {
		return null;
	}
	
	public void anteprimaDatiSpesaDaModello() {
		generaDatiSpesaDaModello(new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordPreview) {
				preview(recordPreview);
			}
		});
	}
	
	public void generaDatiSpesaDaModello(final ServiceCallback<Record> callback) {
		
		Record record = getRecordToSave();
		record.setAttribute("idModello", getIdModAppendice());
		record.setAttribute("nomeModello", getNomeModAppendice());
		record.setAttribute("displayFilenameModello", "");
		
		Layout.showWaitPopup("Generazione anteprima movimenti contabili in corso...");				
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("generaDatiSpesaDaModello", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record recordPreview = response.getData()[0];
					if(callback != null) {
						callback.execute(recordPreview);
					}
				}				
			}		
		});
	}

	public void generaFileUnione(String esito, String nomeFileUnione, String nomeFileUnioneOmissis,  Record impostazioniUnioneFile, final ServiceCallback<Record> callback) {
		
		final Record record = getRecordToSave();		
		
		// aggiungo le impostazioni per l'unione file raccolte dalla callexecatt
		record.setAttribute("impostazioniUnioneFile", impostazioniUnioneFile);
			
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");				
		lNuovaPropostaAtto2CompletaDataSource.addParam("nomeFileUnione", nomeFileUnione);
		lNuovaPropostaAtto2CompletaDataSource.addParam("nomeFileUnioneOmissis", nomeFileUnioneOmissis);
		lNuovaPropostaAtto2CompletaDataSource.addParam("esitoTask", esito);
		
		Layout.showWaitPopup("Generazione del file unione in corso...");				
		lNuovaPropostaAtto2CompletaDataSource.executecustom("unioneFile", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordUnioneFile = response.getData()[0];
					if(callback != null) {
						callback.execute(recordUnioneFile);
					}					
				}	
			}
		});
	}
	
	public void anteprimaFileUnioneVersIntegrale(String nomeFileUnione,  Record impostazioniUnioneFile, final ServiceCallback<Record> callback) {
			
			final Record record = getRecordToSave();		
			
			// aggiungo le impostazioni per l'unione file raccolte dalla callexecatt
			record.setAttribute("impostazioniUnioneFile", impostazioniUnioneFile);
				
			final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");				
			lNuovaPropostaAtto2CompletaDataSource.addParam("nomeFileUnione", nomeFileUnione);
			lNuovaPropostaAtto2CompletaDataSource.addParam("isVersIntegrale", "true");
			Layout.showWaitPopup("Generazione del file unione (vers. integrale) in corso...");				
			lNuovaPropostaAtto2CompletaDataSource.executecustom("unioneFile", record, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record recordUnioneFile = response.getData()[0];
						if(callback != null) {
							callback.execute(recordUnioneFile);
						}					
					}	
				}
			});
		}

	public void anteprimaFileUnioneVersXPubbl(String nomeFileUnioneOmissis,  Record impostazioniUnioneFile, final ServiceCallback<Record> callback) {
		
		final Record record = getRecordToSave();		
		
		// aggiungo le impostazioni per l'unione file raccolte dalla callexecatt
		record.setAttribute("impostazioniUnioneFile", impostazioniUnioneFile);
			
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");				
		lNuovaPropostaAtto2CompletaDataSource.addParam("nomeFileUnioneOmissis", nomeFileUnioneOmissis);
		lNuovaPropostaAtto2CompletaDataSource.addParam("isVersXPubbl", "true");
		Layout.showWaitPopup("Generazione del file unione (vers. per pubbl.) in corso...");				
		lNuovaPropostaAtto2CompletaDataSource.executecustom("unioneFile", record, new DSCallback() {
	
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordUnioneFile = response.getData()[0];
					if(callback != null) {
						callback.execute(recordUnioneFile);
					}					
				}	
			}
		});
	}

	public void preview(final Record recordPreview) {
		previewWithCallback(recordPreview, null);
	}
	
	public void previewWithCallback(final Record recordPreview, final ServiceCallback<Record> closeCallback) {
		if (recordPreview.getAttribute("nomeFile") != null && recordPreview.getAttribute("nomeFile").endsWith(".pdf")) {
			PreviewWindow lPreviewWindow = new PreviewWindow(recordPreview.getAttribute("uri"), false, new InfoFileRecord(recordPreview.getAttributeAsRecord("infoFile")), "FileToExtractBean",	recordPreview.getAttribute("nomeFile")) {
			
				@Override
				public void manageCloseClick() {
					super.manageCloseClick();
					if(closeCallback != null) {
						closeCallback.execute(recordPreview);
					}
				};
			};
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", recordPreview.getAttribute("nomeFile"));
			lRecord.setAttribute("uri", recordPreview.getAttribute("uri"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", "false");
			DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
			if(closeCallback != null) {
				closeCallback.execute(recordPreview);
			}
		}
	}
	
	public void controllaTotali(boolean afterLoadDati) {
		//TODO Errore nella somma dei fl 
		//Sarebbe preferibile fare le somme utilizzando gli interi: basta moltiplicare per 100 gli importi, togliendo la virgola prima delle due cifre decimali. 
		//Una volta fatta la somma bisogna riaggiungere la virgola.		
		String pattern = "#,##0.00";
		float totaleEntrateAurigaCorrente = 0;
		float totaleUsciteAurigaCorrente = 0;
		float totaleEntrateSIBCorrente = 0;
		float totaleUsciteSIBCorrente = 0;
		float totaleEntrateAurigaContoCapitale = 0;
		float totaleUsciteAurigaContoCapitale = 0;
		float totaleEntrateSIBContoCapitale = 0;
		float totaleUsciteSIBContoCapitale = 0;
		if(listaInvioDatiSpesaCorrenteItem != null && listaInvioDatiSpesaCorrenteItem.getValueAsRecordList() != null) {
			for(int i = 0; i < listaInvioDatiSpesaCorrenteItem.getValueAsRecordList().getLength(); i++) {
				Record record = listaInvioDatiSpesaCorrenteItem.getValueAsRecordList().get(i);
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				if("E".equals(flgEntrataUscita)) {
					totaleEntrateAurigaCorrente += importo;
				} else if("U".equals(flgEntrataUscita)) {
					totaleUsciteAurigaCorrente += importo;
				}
			}
		}
		if(listaDatiContabiliSIBCorrenteItem != null && listaDatiContabiliSIBCorrenteItem.getValueAsRecordList() != null) {
			for(int i = 0; i < listaDatiContabiliSIBCorrenteItem.getValueAsRecordList().getLength(); i++) {
				Record record = listaDatiContabiliSIBCorrenteItem.getValueAsRecordList().get(i);
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				if("E".equals(flgEntrataUscita)) {
					totaleEntrateSIBCorrente += importo;
				} else if("U".equals(flgEntrataUscita)) {
					totaleUsciteSIBCorrente += importo;
				}
			}
		}
		if(listaInvioDatiSpesaContoCapitaleItem != null && listaInvioDatiSpesaContoCapitaleItem.getValueAsRecordList() != null) {
			for(int i = 0; i < listaInvioDatiSpesaContoCapitaleItem.getValueAsRecordList().getLength(); i++) {
				Record record = listaInvioDatiSpesaContoCapitaleItem.getValueAsRecordList().get(i);
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				if("E".equals(flgEntrataUscita)) {
					totaleEntrateAurigaContoCapitale += importo;
				} else if("U".equals(flgEntrataUscita)) {
					totaleUsciteAurigaContoCapitale += importo;
				}
			}
		}
		if(listaDatiContabiliSIBContoCapitaleItem != null && listaDatiContabiliSIBContoCapitaleItem.getValueAsRecordList() != null) {
			for(int i = 0; i < listaDatiContabiliSIBContoCapitaleItem.getValueAsRecordList().getLength(); i++) {
				Record record = listaDatiContabiliSIBContoCapitaleItem.getValueAsRecordList().get(i);
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				if("E".equals(flgEntrataUscita)) {
					totaleEntrateSIBContoCapitale += importo;
				} else if("U".equals(flgEntrataUscita)) {
					totaleUsciteSIBContoCapitale += importo;
				}
			}
		}
		
		totaleEntrateAurigaCorrente = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleEntrateAurigaCorrente))).floatValue();
		totaleUsciteAurigaCorrente = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleUsciteAurigaCorrente))).floatValue();
		totaleEntrateSIBCorrente = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleEntrateSIBCorrente))).floatValue();
		totaleUsciteSIBCorrente = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleUsciteSIBCorrente))).floatValue();
		totaleEntrateAurigaContoCapitale = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleEntrateAurigaContoCapitale))).floatValue();
		totaleUsciteAurigaContoCapitale = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleUsciteAurigaContoCapitale))).floatValue();
		totaleEntrateSIBContoCapitale = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleEntrateSIBContoCapitale))).floatValue();
		totaleUsciteSIBContoCapitale = new Float(NumberFormat.getFormat(pattern).parse(NumberFormat.getFormat(pattern).format(totaleUsciteSIBContoCapitale))).floatValue();
		
		ArrayList<String> warningMessages = new ArrayList<String>();
		if((isModalitaGrigliaCorrente() && totaleUsciteAurigaCorrente > 0 && totaleUsciteSIBCorrente > 0 && totaleUsciteAurigaCorrente != totaleUsciteSIBCorrente) || 
		   (isModalitaGrigliaContoCapitale() && totaleUsciteAurigaContoCapitale > 0 && totaleUsciteSIBContoCapitale > 0 && totaleUsciteAurigaContoCapitale != totaleUsciteSIBContoCapitale)) {
			warningMessages.add("la spesa indicata su SIB non coincide con quella compilata in Auriga");
		} 
		if((isModalitaGrigliaCorrente() && totaleEntrateAurigaCorrente > 0 && totaleEntrateSIBCorrente > 0 && totaleEntrateAurigaCorrente != totaleEntrateSIBCorrente) || 
		   (isModalitaGrigliaContoCapitale() && totaleEntrateAurigaContoCapitale > 0 && totaleEntrateSIBContoCapitale > 0 && totaleEntrateAurigaContoCapitale != totaleEntrateSIBContoCapitale)) {
			warningMessages.add("il totale di entrate indicato in SIB non coincide con quello delle entrate indicate in Auriga");
		}
		if(afterLoadDati) {
			if((isModalitaGrigliaCorrente() && totaleUsciteAurigaCorrente > 0 && totaleEntrateAurigaCorrente > 0 && totaleUsciteAurigaCorrente != totaleEntrateAurigaCorrente) || 
			   (isModalitaGrigliaContoCapitale() && totaleUsciteAurigaContoCapitale > 0 && totaleEntrateAurigaContoCapitale > 0 && totaleUsciteAurigaContoCapitale != totaleEntrateAurigaContoCapitale)) {
				warningMessages.add("il totale di entrate e uscite indicati in Auriga non coincidono come in genere dovrebbe essere");
			} 
			if((totaleUsciteSIBCorrente > 0 && totaleEntrateSIBCorrente > 0 && totaleUsciteSIBCorrente != totaleEntrateSIBCorrente) || 
			   (totaleUsciteSIBContoCapitale > 0 && totaleEntrateSIBContoCapitale > 0 && totaleUsciteSIBContoCapitale != totaleEntrateSIBContoCapitale)) {
				warningMessages.add("il totale di entrate e uscite indicati in SIB non coincidono come in genere dovrebbe essere");
			}
		}
		if(warningMessages.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<ul>");
			for(int i = 0; i < warningMessages.size(); i++) {
				buffer.append("<li>" + warningMessages.get(i) + "</li>");			
			}
			buffer.append("</ul>");			
			SC.warn(buffer.toString());
		}
	}
	
	@Override
	public void clearTabErrors() {
		clearTabErrors(tabSet);
	}
	
	@Override
	public void clearErrors() {
		super.clearErrors();
		if(listaAllegatiItem != null && (listaAllegatiItem instanceof AllegatiGridItem)) {
			((AllegatiGridItem) listaAllegatiItem).clearErrors();
		}
	}
	
	@Override
	public void showTabErrors() {
		showTabErrors(tabSet);
	}
	
	@Override
	public void showTabErrors(TabSet tabSet) {
		super.showTabErrors(tabSet);
		if(listaAllegatiItem != null && (listaAllegatiItem instanceof AllegatiGridItem)) {
			Map errors = ((AllegatiGridItem) listaAllegatiItem).getMapErrors();
			if(errors != null && errors.size() > 0) {
				tabSet.showTabErrors(_TAB_ALLEGATI_ID);	
			}			
		}
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					attributiAddDocDetails.get(key).showTabErrors(tabSet);
				}
			}
		}
	}	
	
	public boolean isAttivoSIB() {
		String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
		return lSistAMC != null && "SIB".equalsIgnoreCase(lSistAMC);
	}
	
	public boolean isAttivoContabilia() {
		String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
		return lSistAMC != null && ("CONTABILIA".equalsIgnoreCase(lSistAMC) || "CONTABILIA2".equalsIgnoreCase(lSistAMC));
	}
	
	public boolean isAttivoSICRA() {
		String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
		return lSistAMC != null && "SICRA".equalsIgnoreCase(lSistAMC);
	}
	
	public class HeaderNuovaPropostaAtto2CompletaDetailSection extends HeaderDetailSection {

		public HeaderNuovaPropostaAtto2CompletaDetailSection(String pTitle, boolean pCanCollapse, boolean pShowOpen, boolean pIsRequired, DynamicForm... forms) {
			super(pTitle, pCanCollapse, pShowOpen, pIsRequired, forms);
		}

		@Override
		public boolean showFirstCanvasWhenEmptyAfterOpen() {
			return true;
		}
	}
		  
	public class NuovaPropostaAtto2CompletaDetailSection extends DetailSection {

		public NuovaPropostaAtto2CompletaDetailSection(String pTitle, boolean pCanCollapse, boolean pShowOpen, boolean pIsRequired, DynamicForm... forms) {
			super(pTitle, pCanCollapse, pShowOpen, pIsRequired, forms);
		}

		@Override
		public boolean showFirstCanvasWhenEmptyAfterOpen() {
			return true;
		}
	}
	
	public void afterShow() {
		
	}
	
	@Override
	protected void onDestroy() {
		if(saveModelloWindow != null) {
			saveModelloWindow.destroy();
		}
		if(modelliDS != null) {
			modelliDS.destroy();
		}
		super.onDestroy();
	}
	
	/*
	public void recuperaIdUdAttoDeterminaAContrarre(final ServiceCallback<String> callback) {
		if(showFlgAttoRifASistemaItem() && _FLG_NO.equalsIgnoreCase(getValueAsString("flgAttoRifASistema"))) {
			return;
		}
		String categoriaReg = categoriaRegAttoDeterminaAContrarreItem.getValueAsString() != null ? categoriaRegAttoDeterminaAContrarreItem.getValueAsString() : "";
		String sigla = siglaAttoDeterminaAContrarreItem.getValueAsString() != null ? siglaAttoDeterminaAContrarreItem.getValueAsString() : "";
		String numero = numeroAttoDeterminaAContrarreItem.getValueAsString() != null ? numeroAttoDeterminaAContrarreItem.getValueAsString() : "";
		String anno = annoAttoDeterminaAContrarreItem.getValueAsString() != null ? annoAttoDeterminaAContrarreItem.getValueAsString() : "";									
		if (("PG".equals(categoriaReg) || !"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
			Record lRecord = new Record();			
			lRecord.setAttribute("categoriaRegAttoDeterminaAContrarre", categoriaReg);
			lRecord.setAttribute("siglaAttoDeterminaAContrarre", sigla);
			lRecord.setAttribute("numeroAttoDeterminaAContrarre", numero);
			lRecord.setAttribute("annoAttoDeterminaAContrarre", anno);			
			final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
			lNuovaPropostaAtto2CompletaDataSource.performCustomOperation("recuperaIdUdAttoDeterminaAContrarre", getRecordToSave(), new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						if(callback != null) {
							callback.execute(response.getData()[0].getAttributeAsString("idUdAttoDeterminaAContrarre"));
						} 
					} else {
						if(callback != null) {
							callback.execute(null);
						} 
					}
				}
			});
		}		
	}	
	
	private void setFormValuesFromRecordArchivio(Record record) {
		attoRiferimentoForm.clearErrors(true);
		attoRiferimentoForm.setValue("idUdAttoDeterminaAContrarre", record.getAttribute("idUdFolder"));
		String segnaturaXOrd = record.getAttribute("segnaturaXOrd");	
		if(segnaturaXOrd != null) {
			StringSplitterClient st = new StringSplitterClient(segnaturaXOrd, "-");						
			if(st.getTokens()[0] != null) {
				if("1".equals(st.getTokens()[0])) {
					attoRiferimentoForm.setValue("categoriaRegAttoDeterminaAContrarre", "PG");							
				} else if("4".equals(st.getTokens()[0])) {
					attoRiferimentoForm.setValue("categoriaRegAttoDeterminaAContrarre", "R");						
				}
			}
			attoRiferimentoForm.setValue("siglaAttoDeterminaAContrarre", st.getTokens()[1] != null ? st.getTokens()[1].trim() : null);
			attoRiferimentoForm.setValue("annoAttoDeterminaAContrarre", st.getTokens()[2] != null ? st.getTokens()[2].trim() : null);
			attoRiferimentoForm.setValue("numeroAttoDeterminaAContrarre", st.getTokens()[3] != null ? st.getTokens()[3].trim() : null);
		}
		attoRiferimentoForm.markForRedraw();
	}	
	
	public class AttoDeterminaAContrarreLookupArchivio extends LookupArchivioPopup {

		public AttoDeterminaAContrarreLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);
		}
		
		@Override
		public String getWindowTitle() {
			return "Seleziona da archivio";
		}
		
		@Override
		public String getFinalita() {
			return "SEL_ATTI";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordArchivio(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}
	*/
	
	public class InvioMailMultiLookupRubricaEmailPopup extends LookupRubricaEmailPopup {

		private DynamicForm form;
		private String fieldName;
		
		private HashMap<String, Integer> indirizzoRefCount = new HashMap<String, Integer>();

		public InvioMailMultiLookupRubricaEmailPopup(DynamicForm pForm, String pFieldName) {
			super(false);
			this.form = pForm;
			this.fieldName = pFieldName;
			String value = form.getValueAsString(fieldName);
			if (value != null && !value.equals("")) {
				StringSplitterClient st = new StringSplitterClient(value, ";");
				for (int i = 0; i < st.getTokens().length; i++) {
					String indirizzo = st.getTokens()[i];
					incrementaIndirizzoRefCount(indirizzo);
				}
			}
		}

		@Override
		public void manageLookupBack(Record record) {
		}

		@Override
		public void manageMultiLookupBack(Record record) {
			if ("G".equals(record.getAttributeAsString("tipoIndirizzo"))) {
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idVoceRubrica", FieldType.TEXT);
				datasource.performCustomOperation("trovaMembriGruppo", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							RecordList listaMembri = record.getAttributeAsRecordList("listaMembri");
							if (listaMembri != null && listaMembri.getLength() > 0) {
								for (int i = 0; i < listaMembri.getLength(); i++) {
									String indirizzo = listaMembri.get(i).getAttribute("indirizzoEmail");
									incrementaIndirizzoRefCount(indirizzo);
									form.setValue(fieldName, appendIndirizzoEmail(getValueAsString(fieldName), indirizzo));
								}
							}
						}
					}
				}, new DSRequest());
			} else {
				String indirizzo = record.getAttribute("indirizzoEmail");
				incrementaIndirizzoRefCount(indirizzo);
				form.setValue(fieldName, appendIndirizzoEmail(getValueAsString(fieldName), indirizzo));
			}
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			if ("G".equals(record.getAttributeAsString("tipoIndirizzo"))) {
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idVoceRubrica", FieldType.TEXT);
				datasource.performCustomOperation("trovaMembriGruppo", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							RecordList listaMembri = record.getAttributeAsRecordList("listaMembri");
							if (listaMembri != null && listaMembri.getLength() > 0) {
								for (int i = 0; i < listaMembri.getLength(); i++) {
									String indirizzo = listaMembri.get(i).getAttribute("indirizzoEmail");
									decrementaIndirizzoRefCount(indirizzo);
									if (!indirizzoRefCount.containsKey(indirizzo)) {
										form.setValue(fieldName, removeIndirizzoEmail(getValueAsString(fieldName), indirizzo));
									}
								}
							}
						}
					}
				}, new DSRequest());
			} else {
				String indirizzo = record.getAttribute("indirizzoEmail");
				decrementaIndirizzoRefCount(indirizzo);
				if (!indirizzoRefCount.containsKey(indirizzo)) {
					form.setValue(fieldName, removeIndirizzoEmail(getValueAsString(fieldName), indirizzo));
				}
			}
		}
		
		// Appende a str la stringa strToAppend, preceduta dal carattere ; se questo non è presente alla fine di str
		private String appendIndirizzoEmail(String str, String strToAppend) {
			String res = "";
			if (str == null || str.equals("")) {
				res = strToAppend;
			} else if (strToAppend != null && !strToAppend.equals("") && !str.toLowerCase().contains(strToAppend.toLowerCase())) {
				String lastChar = str.substring(str.length() - 1);
				if (lastChar.equalsIgnoreCase(";")) {
					res = str + strToAppend;
				} else {
					res = str + ";" + strToAppend;
				}
			} else {
				res = str;
			}
			return res;
		}

		private String removeIndirizzoEmail(String str, String strToRemove) {
			String res = "";
			if (str != null && !str.equals("") && strToRemove != null && !strToRemove.equals("") && str.toLowerCase().contains(strToRemove.toLowerCase())) {
				StringSplitterClient st = new StringSplitterClient(str, ";");
				for (int i = 0; i < st.getTokens().length; i++) {
					if (!st.getTokens()[i].equalsIgnoreCase(strToRemove)) {
						res += st.getTokens()[i];
						if (i < (st.getTokens().length - 1)) {
							res += ";";
						}
					}
				}
			} else {
				res = str;
			}
			return res;
		}

		private void incrementaIndirizzoRefCount(String indirizzo) {
			if (indirizzoRefCount.containsKey(indirizzo)) {
				indirizzoRefCount.put(indirizzo, indirizzoRefCount.get(indirizzo) + 1);
			} else {
				indirizzoRefCount.put(indirizzo, new Integer(1));
			}
		}

		private void decrementaIndirizzoRefCount(String indirizzo) {
			if (indirizzoRefCount.containsKey(indirizzo) && indirizzoRefCount.get(indirizzo).intValue() > 1) {
				indirizzoRefCount.put(indirizzo, indirizzoRefCount.get(indirizzo) - 1);
			} else {
				indirizzoRefCount.remove(indirizzo);
			}
		}
	}
	
	public boolean isPresenteAttributoCustomCablato(String attrName) {
		return attributiCustomCablati != null && attributiCustomCablati.get(attrName) != null;
	}
	
	public boolean showAttributoCustomCablato(String attrName) {
		return attributiCustomCablati == null || isPresenteAttributoCustomCablato(attrName);
	}
		
	public String getLabelAttributoCustomCablato(String attrName) {
		return isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("attrLabel") : null;
	}

	public boolean getFlgObbligatorioAttributoCustomCablato(String attrName) {
		String flgObbligatorio = isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("flgObbligatorio") : null;
		return attributiCustomCablati == null || (flgObbligatorio != null && "1".equals(flgObbligatorio));
	}
		
	public Integer getMaxNumValoriAttributoCustomCablato(String attrName) {
		String maxNumValori = isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("maxNumValori") : null;
		return maxNumValori != null && !"".equals(maxNumValori) ? Integer.parseInt(maxNumValori) : null;
	}

	public String[] getValoriPossibiliAttributoCustomCablato(String attrName) {
		String valoriPossibili = isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("valoriPossibili") : null;
		if(valoriPossibili != null) {
			StringSplitterClient st = new StringSplitterClient(valoriPossibili, "|*|");
			return st.getTokens();
		}
		return new String[0];
	}

	public String getTipoLoadComboAttributoCustomCablato(String attrName) {
		return isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("tipoLoadCombo") : null;	
	}
		
	public String getAltriParametriLoadComboAttributoCustomCablato(String attrName) {
		return isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("altriParametriLoadCombo") : null;	
	}

	public boolean getFlgSoloVldLoadComboAttributoCustomCablato(String attrName) {
		String flgSoloVldLoadCombo = isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("flgSoloVldLoadCombo") : null;
		return flgSoloVldLoadCombo != null && "1".equals(flgSoloVldLoadCombo);
	}

	public String getValoreFissoAttributoCustomCablato(String attrName) {
		return isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("valoreFisso") : null;
	}
	
	public boolean getValoreFissoAsBooleanAttributoCustomCablato(String attrName) {
		String valoreFisso = getValoreFissoAttributoCustomCablato(attrName);
		return valoreFisso != null && ("1".equals(valoreFisso) || "true".equalsIgnoreCase(valoreFisso));
	}
	
	public boolean getFlgEditabileAttributoCustomCablato(String attrName) {
		String flgEditabile = isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("flgEditabile") : null;
		return attributiCustomCablati == null || (flgEditabile != null && "1".equals(flgEditabile));
	}
	
	public boolean getFlgMostraFirmatarioAttributoCustomCablato(String attrName) {
		String flgMostraFirmatario = isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("flgMostraFirmatario") : null;
		return attributiCustomCablati == null || (flgMostraFirmatario != null && "1".equals(flgMostraFirmatario));
	}
	
	public boolean getFlgMostraVistoInRitornoIterAttributoCustomCablato(String attrName) {
		String flgMostraVistoInRitornoIter = isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("flgMostraVistoInRitornoIter") : null;
		return attributiCustomCablati == null || (flgMostraVistoInRitornoIter != null && "1".equals(flgMostraVistoInRitornoIter));
	}
	
	public String getFlgMostraMotiviAttributoCustomCablato(String attrName) {
		String flgMostraMotivi = isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("flgMostraMotivi") : null;
		return attributiCustomCablati == null ? _MANDATORY : flgMostraMotivi;
	}
	
	public Integer getAltezzaInRigheAttributoCustomCablato(String attrName) {
		String altezzaInRighe = isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("altezzaInRighe") : null;
		return altezzaInRighe != null && !"".equals(altezzaInRighe) ? Integer.parseInt(altezzaInRighe) : null;
	}	
	
	public LinkedHashMap<String, String> getValueMapAttributoCustomCablato(String attrName) {
		String valoriPossibili = isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("valoriPossibili") : null;
		String decodificheValoriPossibili = isPresenteAttributoCustomCablato(attrName) ? attributiCustomCablati.get(attrName).getAttribute("decodificheValoriPossibili") : null;
		if(valoriPossibili != null && decodificheValoriPossibili != null) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			StringSplitterClient stValoriPossibili = new StringSplitterClient(valoriPossibili, "|*|");
			StringSplitterClient stDecodificheValoriPossibili = new StringSplitterClient(decodificheValoriPossibili, "|*|");
			if(stValoriPossibili.getTokens().length == stDecodificheValoriPossibili.getTokens().length) {
				for(int i = 0; i < stValoriPossibili.getTokens().length; i++) {				
					valueMap.put(stValoriPossibili.getTokens()[i], stDecodificheValoriPossibili.getTokens()[i]);
				}
			} else {
				for(int i = 0; i < stValoriPossibili.getTokens().length; i++) {					
					valueMap.put(stValoriPossibili.getTokens()[i], stValoriPossibili.getTokens()[i]);
				}				
			}	
			return valueMap;
		}
		return new LinkedHashMap<String, String>();
	}
	
	public void aggiornaStato(String codStato, Record recordVersFileUnione, final ServiceCallback<Record> callback) {
		Record record = getRecordToSave();
		record.setAttribute("idUd", getValueAsString("idUd"));
		if(recordVersFileUnione != null) {
			record.setAttribute("uriFilePrimario", recordVersFileUnione.getAttribute("uriVersIntegrale"));
			record.setAttribute("nomeFilePrimario", recordVersFileUnione.getAttribute("nomeFileVersIntegrale"));
			record.setAttribute("infoFilePrimario", recordVersFileUnione.getAttributeAsRecord("infoFileVersIntegrale"));
			record.setAttribute("uriFilePrimarioOmissis", recordVersFileUnione.getAttribute("uri"));
			record.setAttribute("nomeFilePrimarioOmissis", recordVersFileUnione.getAttribute("nomeFile"));
			record.setAttribute("infoFilePrimarioOmissis", recordVersFileUnione.getAttributeAsRecord("infoFile"));
		}
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.extraparam.put("codStato", codStato);
		lNuovaPropostaAtto2CompletaDataSource.executecustom("aggiornaStato", record, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = response.getData()[0];
					Layout.addMessage(new MessageBean("Stato aggiornato con successo", "", MessageType.INFO));
					if(callback != null) {
						callback.execute(result);
					}
				}
			}
		});
	}
	
	public void recuperaListaEmendamenti(final ServiceCallback<Record> callback) {
		// Verifico se ho già caricatto gli emendamenti
		String idUd = getValueAsString("idUd");
		Record recordToPass = new Record();
		recordToPass.setAttribute("idUd", idUd);
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("recuperaListaEmendamenti", recordToPass, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = response.getData()[0];
					RecordList listaEmendamenti = result.getAttributeAsRecordList("listaEmendamenti");
					boolean listaEmendamentiBloccoRiordinoAut = result.getAttributeAsBoolean("listaEmendamentiBloccoRiordinoAut").booleanValue();
					listaEmendamentiItem.setValue(listaEmendamenti);
					listaEmendamentiBloccoRiordinoAutItem.setValue(listaEmendamentiBloccoRiordinoAut);
					if (callback !=  null) {
						callback.execute(result);
					}
				}
			}
		});
	}
	
	public void apriListaEmendamenti() {
		RecordList listaEmendamenti = hiddenForm.getValueAsRecordList("listaEmendamenti");
		boolean listaEmendamentiBloccoRiordinoAut = Boolean.parseBoolean(hiddenForm.getValueAsString("listaEmendamentiBloccoRiordinoAut"));
		
		if (listaEmendamenti != null && listaEmendamenti.getLength() > 0) {
			boolean isSubList = listaEmendamenti.get(0).getAttribute("nroSubEmendamento") != null && !"".equals(listaEmendamenti.get(0).getAttributeAsString("nroSubEmendamento"));
			listaEmendamentiPopup = new ListaEmendamentiPopup("Lista emendamenti", "lista_emendamenti_popup", tabSet, instance, isSubList, listaEmendamentiBloccoRiordinoAut) {

				@Override
				public void onClickOkButton(Record record, DSCallback callback) {
					
				}
			};  
			Record recordToPass = new Record();
			recordToPass.setAttribute("listaEmendamenti", listaEmendamenti);
			listaEmendamentiPopup.initContent(recordToPass);
			listaEmendamentiPopup.show();
		} else {
			Layout.addMessage(new MessageBean("Nessun emendamento trovato", "", MessageType.ERROR));
		}
	}
	
	public void apriEmendamentiWindow() {
		RecordList listaEmendamenti = hiddenForm.getValueAsRecordList("listaEmendamenti");
		if (listaEmendamenti != null && listaEmendamenti.getLength() > 0) {
			if (emendamentiWindow == null) {
				emendamentiWindow = new EmendamentiWindow("Lista emendamenti", "emendamenti_window", tabSet, instance);
				Record recordToPass = new Record();
				recordToPass.setAttribute("listaEmendamenti", listaEmendamenti);
				emendamentiWindow.initContent(recordToPass);
			}
			emendamentiWindow.show();
		} else {
			Layout.addMessage(new MessageBean("Nessun emendamento trovato", "", MessageType.ERROR));
		}
	}
	
	public static String getFLG_SI_SENZA_VLD_RIL_IMP() {
		if(AurigaLayout.isAttivoClienteCMMI()) {
			return "SI, senza validazione/rilascio impegni";
		}
		return "SI, ma senza movimenti contabili";	
	}
	
}
