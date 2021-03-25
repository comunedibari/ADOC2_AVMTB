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
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaEsitoTaskWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.NuovoMessaggioWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.PropostaAttoInterface;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiGridItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowPageSelectionCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class TaskRevocaNuovaPropostaAtto2CompletaDetail extends RevocaNuovaPropostaAtto2CompletaDetail implements PropostaAttoInterface {

	protected TaskRevocaNuovaPropostaAtto2CompletaDetail instance;
	
	protected Record recordEvento;
	
	protected String idProcess;
	protected String nomeFlussoWF;
	protected String processNameWF;
	protected String idTipoEvento;
	protected String idEvento;
	protected String rowId;
	protected String activityName;
	protected String instanceId;
	protected String nome;	
	protected String alertConfermaSalvaDefinitivo;

	protected String idUd;
	protected String codTabDefault;
	protected String idTipoTaskDoc;
	protected String nomeTipoTaskDoc;
	protected Boolean flgParereTaskDoc;
	protected Boolean flgParteDispositivoTaskDoc;
	protected Boolean flgNoPubblTaskDoc;
	protected Boolean flgPubblicaSeparatoTaskDoc;
	
	protected String idModCopertina;
	protected String nomeModCopertina;
	protected String uriModCopertina;
	protected String tipoModCopertina;
	protected String idModCopertinaFinale;
	protected String nomeModCopertinaFinale;
	protected String uriModCopertinaFinale;
	protected String tipoModCopertinaFinale;
	protected String idModAllegatiParteIntSeparati;
	protected String nomeModAllegatiParteIntSeparati;
	protected String uriModAllegatiParteIntSeparati;
	protected String tipoModAllegatiParteIntSeparati;
	protected String idModAllegatiParteIntSeparatiXPubbl;
	protected String nomeModAllegatiParteIntSeparatiXPubbl;
	protected String uriModAllegatiParteIntSeparatiXPubbl;
	protected String tipoModAllegatiParteIntSeparatiXPubbl;
	protected Boolean flgAppendiceDaUnire;
	protected String idModAppendice;
	protected String nomeModAppendice;
	protected String uriModAppendice;
	protected String tipoModAppendice;
	protected String idModAssDocTask;
	protected String nomeModAssDocTask;
	protected String displayFilenameModAssDocTask;
	
	protected Boolean flgUnioneFile;
	protected Boolean flgFirmaFile;
	protected Boolean flgPubblica;
	protected Boolean flgInvioNotEmail;
	protected Boolean flgDatiSpesaEditabili;
	
	protected String tipoEventoSIB;
	protected Set<String> esitiTaskEventoSIB;	
	protected HashMap<String, String> mappaTipiEventoSIBXEsitoTask;	
	protected String idUoDirAdottanteSIB;
	protected String codUoDirAdottanteSIB;
	protected String desUoDirAdottanteSIB;
	
	protected Boolean flgAttivaRequestMovimentiDaAMC;
	protected Boolean flgAttivaSalvataggioMovimentiDaAMC;	
	protected Boolean flgEscludiFiltroCdCVsAMC;
	
	protected HashMap<String, String> mappaTipiEventoContabiliaXEsitoTask;
	
	protected HashMap<String, String> mappaTipiEventoSICRAXEsitoTask;
	
	protected HashMap<String, String> mappaWarningMsgXEsitoTask;
	
	protected String esitoTaskDaPreimpostare;
	protected String msgTaskDaPreimpostare;
	
	protected DettaglioPraticaLayout dettaglioPraticaLayout;
	
	protected RecordList listaRecordModelli;
	protected Record allegatoGeneratoDaModelloTask;
	
	protected Set<String> esitiTaskOk;	
	protected HashMap<String, Record> controlliXEsitiTaskDoc;
	protected HashSet<String> valoriEsito;
	
	protected Set<String> esitiTaskAzioni;
	
	protected HashSet<String> attributiAddDocTabsDatiStorici;

	protected Record attrEsito;
	protected Record attrEsitoNotEmail;
	protected String messaggio;	
	
	public TaskRevocaNuovaPropostaAtto2CompletaDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita, getAttributAddDocTabs(lRecordEvento));

		instance = this;

		this.recordEvento = lRecordEvento;
		this.recordParametriTipoAtto = lRecordEvento != null ? lRecordEvento.getAttributeAsRecord("parametriTipoAtto") : null;
		this.flgPubblicazioneAllegatiUguale = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgPubblicazioneAllegatiUguale") : null;
		this.flgSoloPreparazioneVersPubblicazione = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgSoloPreparazioneVersPubblicazione") : null;
		this.flgCtrlMimeTypeAllegPI = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgCtrlMimeTypeAllegPI") : null;
		
		this.idProcess = idProcess;
		this.nomeFlussoWF = nomeFlussoWF;
		this.processNameWF = processNameWF;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.rowId = lRecordEvento != null ? lRecordEvento.getAttribute("rowId") : null;
		this.activityName = lRecordEvento != null ? lRecordEvento.getAttribute("activityName") : null;
		this.instanceId = lRecordEvento != null ? lRecordEvento.getAttribute("instanceId") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;
		this.alertConfermaSalvaDefinitivo = lRecordEvento != null ? lRecordEvento.getAttribute("alertConfermaSalvaDefinitivo") : null;

		this.idUd = idUd;		
		this.codTabDefault = lRecordEvento != null ? lRecordEvento.getAttribute("codTabDefault") : null;
		this.idTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoTaskDoc") : null;
		this.nomeTipoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttribute("nomeTipoTaskDoc") : null;
		this.flgParereTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgParereTaskDoc") : null;
		this.flgParteDispositivoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgParteDispositivoTaskDoc") : null;
		this.flgNoPubblTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgNoPubblTaskDoc") : null;
		this.flgPubblicaSeparatoTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgPubblicaSeparatoTaskDoc") : null;
		
		this.idModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("idModCopertina") : null;
		this.nomeModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModCopertina") : null;
		this.uriModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("uriModCopertina") : null;
		this.tipoModCopertina = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModCopertina") : null;		
		this.idModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("idModCopertinaFinale") : null;
		this.nomeModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModCopertinaFinale") : null;
		this.uriModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("uriModCopertinaFinale") : null;
		this.tipoModCopertinaFinale = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModCopertinaFinale") : null;
		this.idModAllegatiParteIntSeparati = lRecordEvento != null ? lRecordEvento.getAttribute("idModAllegatiParteIntSeparati") : null;
		this.nomeModAllegatiParteIntSeparati = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModAllegatiParteIntSeparati") : null;
		this.uriModAllegatiParteIntSeparati = lRecordEvento != null ? lRecordEvento.getAttribute("uriModAllegatiParteIntSeparati") : null;
		this.tipoModAllegatiParteIntSeparati = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAllegatiParteIntSeparati") : null;
		this.idModAllegatiParteIntSeparatiXPubbl = lRecordEvento != null ? lRecordEvento.getAttribute("idModAllegatiParteIntSeparatiXPubbl") : null;
		this.nomeModAllegatiParteIntSeparatiXPubbl = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModAllegatiParteIntSeparatiXPubbl") : null;
		this.uriModAllegatiParteIntSeparatiXPubbl = lRecordEvento != null ? lRecordEvento.getAttribute("uriModAllegatiParteIntSeparatiXPubbl") : null;
		this.tipoModAllegatiParteIntSeparatiXPubbl = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAllegatiParteIntSeparatiXPubbl") : null;
		this.flgAppendiceDaUnire = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgAppendiceDaUnire") : null;
		this.idModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("idModAppendice") : null;
		this.nomeModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModAppendice") : null;
		this.uriModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("uriModAppendice") : null;
		this.tipoModAppendice = lRecordEvento != null ? lRecordEvento.getAttribute("tipoModAppendice") : null;
		this.idModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("idModAssDocTask") : null;		
		this.nomeModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("nomeModAssDocTask") : null;
		this.displayFilenameModAssDocTask = lRecordEvento != null ? lRecordEvento.getAttribute("displayFilenameModAssDocTask") : null;
		
		this.flgUnioneFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgUnioneFile") : null;
		this.flgFirmaFile = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaFile") : null;
		this.flgPubblica = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgPubblica") : null;
		this.flgInvioNotEmail = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgInvioNotEmail") : null;
		this.flgDatiSpesaEditabili = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgDatiSpesaEditabili") : null;
		this.flgProtocollazioneProsa = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgProtocollazioneProsa") : null;
		this.flgFirmaVersPubblAggiornata = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgFirmaVersPubblAggiornata") : null;
		
		this.tipoEventoSIB = lRecordEvento != null ? lRecordEvento.getAttribute("tipoEventoSIB") : null;
		this.idUoDirAdottanteSIB = lRecordEvento != null ? lRecordEvento.getAttribute("idUoDirAdottanteSIB") : null;
		this.codUoDirAdottanteSIB = lRecordEvento != null ? lRecordEvento.getAttribute("codUoDirAdottanteSIB") : null;
		this.desUoDirAdottanteSIB = lRecordEvento != null ? lRecordEvento.getAttribute("desUoDirAdottanteSIB") : null;
				
		this.flgAttivaRequestMovimentiDaAMC = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgAttivaRequestMovimentiDaAMC") : null;
		this.flgAttivaSalvataggioMovimentiDaAMC = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgAttivaSalvataggioMovimentiDaAMC") : null;
		this.flgEscludiFiltroCdCVsAMC = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("flgEscludiFiltroCdCVsAMC") : null;
		
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
		
		this.listaRecordModelli = dettaglioPraticaLayout.getListaModelliAttivita(activityName);

		RecordList listaEsitiTaskOk = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskOk") : null;
		if(listaEsitiTaskOk != null && listaEsitiTaskOk.getLength() > 0) {
			esitiTaskOk = new HashSet<String>();
			for(int i = 0; i < listaEsitiTaskOk.getLength(); i++) {				
				Record esito = listaEsitiTaskOk.get(i);
				esitiTaskOk.add(esito.getAttribute("valore"));
			}			
		}

		RecordList listaControlliXEsitiTaskDoc = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("controlliXEsitiTaskDoc") : null;
		if (listaControlliXEsitiTaskDoc != null && listaControlliXEsitiTaskDoc.getLength() > 0) {
			controlliXEsitiTaskDoc = new HashMap<String, Record>();
			for (int i = 0; i < listaControlliXEsitiTaskDoc.getLength(); i++) {
				Record recordControllo = listaControlliXEsitiTaskDoc.get(i);
				controlliXEsitiTaskDoc.put(recordControllo.getAttribute("esito"), recordControllo);
			}
		}
		
		RecordList listaValoriEsito = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("valoriEsito") : null;
		if (listaValoriEsito != null && listaValoriEsito.getLength() > 0) {
			valoriEsito = new HashSet<String>();
			for (int i = 0; i < listaValoriEsito.getLength(); i++) {
				valoriEsito.add(listaValoriEsito.get(i).getAttribute("valore"));
			}
		}
		
		RecordList listaEsitiTaskAzioni = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskAzioni") : null;
		if(listaEsitiTaskAzioni != null && listaEsitiTaskAzioni.getLength() > 0) {
			esitiTaskAzioni = new HashSet<String>();
			for(int i = 0; i < listaEsitiTaskAzioni.getLength(); i++) {				
				Record esito = listaEsitiTaskAzioni.get(i);
				esitiTaskAzioni.add(esito.getAttribute("valore"));
			}			
		}
		
		RecordList listaEsitiTaskEventoSIB = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("esitiTaskEventoSIB") : null;
		if(listaEsitiTaskEventoSIB != null && listaEsitiTaskEventoSIB.getLength() > 0) {
			esitiTaskEventoSIB = new HashSet<String>();
			for(int i = 0; i < listaEsitiTaskEventoSIB.getLength(); i++) {				
				Record esito = listaEsitiTaskEventoSIB.get(i);
				esitiTaskEventoSIB.add(esito.getAttribute("valore"));
			}			
		}
		
		RecordList listaTipiEventoSIBXEsitoTask = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("tipiEventoSIBXEsitoTask") : null;
		if(listaTipiEventoSIBXEsitoTask != null && listaTipiEventoSIBXEsitoTask.getLength() > 0) {
			mappaTipiEventoSIBXEsitoTask = new HashMap<String, String>();
			for(int i = 0; i < listaTipiEventoSIBXEsitoTask.getLength(); i++) {				
				Record eventoXEsito = listaTipiEventoSIBXEsitoTask.get(i);
				mappaTipiEventoSIBXEsitoTask.put(eventoXEsito.getAttribute("esito"), eventoXEsito.getAttribute("evento"));
			}			
		}
		
		RecordList listaTipiEventoContabiliaXEsitoTask = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("tipiEventoContabiliaXEsitoTask") : null;
		if(listaTipiEventoContabiliaXEsitoTask != null && listaTipiEventoContabiliaXEsitoTask.getLength() > 0) {
			mappaTipiEventoContabiliaXEsitoTask = new HashMap<String, String>();
			for(int i = 0; i < listaTipiEventoContabiliaXEsitoTask.getLength(); i++) {				
				Record eventoXEsito = listaTipiEventoContabiliaXEsitoTask.get(i);
				mappaTipiEventoContabiliaXEsitoTask.put(eventoXEsito.getAttribute("esito"), eventoXEsito.getAttribute("evento"));
			}			
		}
		
		RecordList listaTipiEventoSICRAXEsitoTask = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("tipiEventoSICRAXEsitoTask") : null;
		if(listaTipiEventoSICRAXEsitoTask != null && listaTipiEventoSICRAXEsitoTask.getLength() > 0) {
			mappaTipiEventoSICRAXEsitoTask = new HashMap<String, String>();
			for(int i = 0; i < listaTipiEventoSICRAXEsitoTask.getLength(); i++) {				
				Record eventoXEsito = listaTipiEventoSICRAXEsitoTask.get(i);
				mappaTipiEventoSICRAXEsitoTask.put(eventoXEsito.getAttribute("esito"), eventoXEsito.getAttribute("evento"));
			}			
		}
		
		RecordList listaWarningMsgXEsitoTask = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("warningMsgXEsitoTask") : null;
		if(listaWarningMsgXEsitoTask != null && listaWarningMsgXEsitoTask.getLength() > 0) {
			mappaWarningMsgXEsitoTask = new HashMap<String, String>();
			for(int i = 0; i < listaWarningMsgXEsitoTask.getLength(); i++) {				
				Record warningMsgXEsito = listaWarningMsgXEsitoTask.get(i);
				mappaWarningMsgXEsitoTask.put(warningMsgXEsito.getAttribute("esito"), warningMsgXEsito.getAttribute("warningMsg"));
			}			
		}
		
		this.esitoTaskDaPreimpostare = lRecordEvento != null ? lRecordEvento.getAttribute("esitoTaskDaPreimpostare") : null;		
		this.msgTaskDaPreimpostare = lRecordEvento != null ? lRecordEvento.getAttribute("msgTaskDaPreimpostare") : null;
		
		// lista dei tab di attributi dinamici che gestiscono i dati storici
		RecordList attributiAddDocTabs = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			attributiAddDocTabsDatiStorici = new HashSet<String>();
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				if (tab.getAttribute("flgDatiStorici") != null && "1".equals(tab.getAttribute("flgDatiStorici"))) {
					attributiAddDocTabsDatiStorici.add(tab.getAttribute("codice"));
				}
			}
		}
		
		build();
	}
	
	@Override
	public boolean skipSuperBuild() {
		return true; // evito di fare la build nel costruttore della superclasse, in modo da farla poi alla fine, dopo aver inizializzato recordEvento e le altre variabili che mi servono nella build
	}
	
	public static LinkedHashMap<String, String> getAttributAddDocTabs(Record lRecordEvento) {
		
		LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
		RecordList attributiAddDocTabs = lRecordEvento != null ? lRecordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				tabs.put(tab.getAttribute("codice"), tab.getAttribute("titolo"));
			}
		}
		return tabs;
	}
	
	public HashSet<String> getAttributiAddDocTabsDatiStorici() {
		return attributiAddDocTabsDatiStorici;
	}
	
	public void visualizzaDatiStorici() {

		if (attributiAddDocTabsDatiStorici != null && attributiAddDocTabsDatiStorici.size() > 0) {

			final TabSet tabSetDatiStorici = new TabSet();

			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			// lGwtRestService.addParam("suffisso", "_CMMI");
			lGwtRestService.addParam("nomeFlussoWF", nomeFlussoWF);
			lGwtRestService.addParam("processNameWF", processNameWF);
			lGwtRestService.addParam("activityNameWF", activityName);
			lGwtRestService.addParam("flgDatiStorici", "true");
			Record lAttributiDinamiciRecord = new Record();
			lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_DOCUMENTS");
			lAttributiDinamiciRecord.setAttribute("rowId", rowidDoc);
			lAttributiDinamiciRecord.setAttribute("tipoEntita", tipoDocumento);
			lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
					if (attributiAdd != null && !attributiAdd.isEmpty()) {
						for (final String key : attributiAddDocTabsDatiStorici) {
							RecordList attributiAddCategoria = new RecordList();
							for (int i = 0; i < attributiAdd.getLength(); i++) {
								Record attr = attributiAdd.get(i);
								if (attr.getAttribute("categoria") != null && attr.getAttribute("categoria").equalsIgnoreCase(key)) {
									attributiAddCategoria.add(attr);
								}
							}
							if (!attributiAddCategoria.isEmpty()) {
								AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
										.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
										.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null, null, null);
								detail.setCanEdit(false);
								String messaggioTabDatiStorici = getMessaggioTabDatiStorici(key);
								if (messaggioTabDatiStorici != null && !"".equals(messaggioTabDatiStorici)) {
									Label labelMessaggioTabDatiStorici = new Label(messaggioTabDatiStorici);
									labelMessaggioTabDatiStorici.setAlign(Alignment.LEFT);
									labelMessaggioTabDatiStorici.setWidth100();
									labelMessaggioTabDatiStorici.setHeight(2);
									labelMessaggioTabDatiStorici.setPadding(5);
									detail.addMember(labelMessaggioTabDatiStorici, 0);
								}

								VLayout layout = new VLayout();
								layout.setHeight100();
								layout.setWidth100();
								layout.setMembers(detail);

								VLayout layoutTab = new VLayout();
								layoutTab.addMember(layout);

								Tab tab = new Tab("<b>" + attributiAddDocTabs.get(key) + "</b>");
								tab.setPrompt(attributiAddDocTabs.get(key));
								tab.setPane(layoutTab);

								tabSetDatiStorici.addTab(tab);
							}
						}
						AurigaLayout.addModalWindow("datiStorici", "Dati storici", "protocollazione/variazioni.png", tabSetDatiStorici);
					}
				}
			});
		}
	}
	
	@Override
	public String getMessaggioTab(String tabID) {
		
		RecordList attributiAddDocTabs = recordEvento != null ? recordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				if (tab.getAttribute("codice") != null && tabID.equals(tab.getAttribute("codice"))) {
					return tab.getAttribute("messaggioTab");
				}
			}
		}
		return null;
	}

	public String getMessaggioTabDatiStorici(String tabID) {
		
		RecordList attributiAddDocTabs = recordEvento != null ? recordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
		if (attributiAddDocTabs != null) {
			for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
				Record tab = attributiAddDocTabs.get(i);
				if (tab.getAttribute("codice") != null && tabID.equals(tab.getAttribute("codice"))) {
					return tab.getAttribute("messaggioTabDatiStorici");
				}
			}
		}
		return null;
	}
	
	@Override
	public void afterCaricaAttributiDinamiciDoc() {
		
		super.afterCaricaAttributiDinamiciDoc();
		try {
			if (codTabDefault != null && !"".equals(codTabDefault)) {
				tabSet.selectTab(codTabDefault);
			} else {
				tabSet.selectTab(0);
			}
		} catch (Exception e) {
		}
		afterShow();
	}
	
	public boolean hasActionUnioneFile() {
		return flgUnioneFile != null && flgUnioneFile;
	}
	
	public boolean hasActionFirma() {
		return flgFirmaFile != null && flgFirmaFile;
	}
	
	public boolean hasActionPubblica() {
		return flgPubblica != null && flgPubblica;
	}
	
	public boolean hasActionInvioNotEmail() {
		return flgInvioNotEmail != null && flgInvioNotEmail;
	}
	
	public boolean isDatiSpesaEditabili() {
		return flgDatiSpesaEditabili != null && flgDatiSpesaEditabili;
	}
	
	public boolean hasActionProtocollazioneProsa() {
		return flgProtocollazioneProsa != null && flgProtocollazioneProsa;
	}
	
	public boolean hasActionFirmaVersPubblAggiornata() {
		return flgFirmaVersPubblAggiornata != null && flgFirmaVersPubblAggiornata;
	}
	
	public boolean hasModelloAllegatiParteIntSeparatiXPubbl() {
		return idModAllegatiParteIntSeparatiXPubbl != null && !"".equals(idModAllegatiParteIntSeparatiXPubbl);					
	}
	
	/*
	public boolean isEsitoTaskSelezionatoOk() {
		return isEsitoTaskOk(attrEsito);
	}
	*/
	
	public boolean isEsitoTaskOk(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskOk == null || (esito != null && esitiTaskOk != null && esitiTaskOk.contains(esito)));		
	}
	
	public boolean isEsitoTaskAzioni(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return (esito == null || esitiTaskAzioni == null || (esito != null && esitiTaskAzioni != null && esitiTaskAzioni.contains(esito)));		
	}
	
	public boolean isEsitoTaskEventoSIBValorizzato() {
		return esitiTaskEventoSIB != null;		
	}
		
	public boolean isEsitoTaskEventoSIB(Record attrEsito) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		return esito != null && esitiTaskEventoSIB != null && esitiTaskEventoSIB.contains(esito);		
	}
	
	public String getEventoSIBXEsitoTask(Record attrEsito) {		
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		if(esito != null && !"".equals(esito)) {
			if(mappaTipiEventoSIBXEsitoTask != null) {
				if(mappaTipiEventoSIBXEsitoTask.containsKey(esito)) {
					return mappaTipiEventoSIBXEsitoTask.get(esito);
				} else if(mappaTipiEventoSIBXEsitoTask.containsKey("#ANY")) {
					return mappaTipiEventoSIBXEsitoTask.get("#ANY");
				}				
			}
			return null;
		} else {
			return mappaTipiEventoSIBXEsitoTask != null ? mappaTipiEventoSIBXEsitoTask.get("#ANY") : null;			
		}			
	}

	public String getEventoContabiliaXEsitoTask(Record attrEsito) {		
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		if(esito != null && !"".equals(esito)) {
			if(mappaTipiEventoContabiliaXEsitoTask != null) {
				if(mappaTipiEventoContabiliaXEsitoTask.containsKey(esito)) {
					return mappaTipiEventoContabiliaXEsitoTask.get(esito);
				} else if(mappaTipiEventoContabiliaXEsitoTask.containsKey("#ANY")) {
					return mappaTipiEventoContabiliaXEsitoTask.get("#ANY");
				}				
			}
			return null;
		} else {
			return mappaTipiEventoContabiliaXEsitoTask != null ? mappaTipiEventoContabiliaXEsitoTask.get("#ANY") : null;			
		}		
	}
	
	public String getEventoSICRAXEsitoTask(Record attrEsito) {		
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
		if(esito != null && !"".equals(esito)) {
			if(mappaTipiEventoSICRAXEsitoTask != null) {
				if(mappaTipiEventoSICRAXEsitoTask.containsKey(esito)) {
					return mappaTipiEventoSICRAXEsitoTask.get(esito);
				} else if(mappaTipiEventoSICRAXEsitoTask.containsKey("#ANY")) {
					return mappaTipiEventoSICRAXEsitoTask.get("#ANY");
				}				
			}
			return null;			
		} else {
			return mappaTipiEventoSICRAXEsitoTask != null ? mappaTipiEventoSICRAXEsitoTask.get("#ANY") : null;			
		}		
	}
	
	public boolean isAttivaRequestMovimentiDaAMC() {
		return flgAttivaRequestMovimentiDaAMC;
	}
	
	public boolean isAttivaSalvataggioMovimentiDaAMC() {
		return flgAttivaSalvataggioMovimentiDaAMC;
	}	
	
	public boolean isEscludiFiltroCdCVsAMC() {
		return flgEscludiFiltroCdCVsAMC;
	}	
	
	public boolean isEseguibile() {
		boolean isEseguibile = true;
		if (recordEvento != null && recordEvento.getAttribute("flgEseguibile") != null) {
			isEseguibile = "1".equals(recordEvento.getAttribute("flgEseguibile"));
		}
		return isEseguibile;
	}
	
	public boolean isSoloPreparazioneVersPubblicazione() {
		boolean isSoloPreparazioneVersPubblicazione = false;
		if (recordEvento != null && recordEvento.getAttribute("flgSoloPreparazioneVersPubblicazione") != null) {
			isSoloPreparazioneVersPubblicazione = recordEvento.getAttributeAsBoolean("flgSoloPreparazioneVersPubblicazione");
		}
		return isSoloPreparazioneVersPubblicazione;
	}
	
	public boolean isReadOnly() {
		boolean isReadOnly = false;
		if (recordEvento != null && recordEvento.getAttribute("flgReadOnly") != null) {
			isReadOnly = recordEvento.getAttributeAsBoolean("flgReadOnly");
		}
		return isReadOnly;
	}
	
	@Override
	protected String getIdUd() {
		return idUd;
	}
	
	@Override
	public String getIdProcessTask() {
		return idProcess;
	}
	
	@Override
	public String getIdTaskCorrente() {
		return nome.substring(0, nome.indexOf("|*|"));
	}
	
	/*********************************************************************************************/
	
	@Override
	public boolean showDesUORegistrazioneItem() {
		return false; // nei task degli atti non va mostrato (si vede già nell'intestazione)
	}
		
//	@Override
//	public boolean showDetailSectionPubblicazione() {
//		Boolean flgCompilaDatiPubblicazione = recordEvento != null ? recordEvento.getAttributeAsBoolean("flgCompilaDatiPubblicazione") : null;
//		if (isEseguibile() && /*hasActionPubblica() &&*/ flgCompilaDatiPubblicazione != null && flgCompilaDatiPubblicazione) {
//			return true;
//		}
//		return false;		
//	}
//	
//	@Override
//	public Date getDataInizioPubblicazioneValue() {
//		String dataInizioPubblicazione = recordEvento != null ? recordEvento.getAttributeAsString("dataInizioPubblicazione") : null;
//		return dataInizioPubblicazione != null ? DateTimeFormat.getFormat("dd/MM/yyyy").parse(dataInizioPubblicazione) : null;
//	}
//	
//	@Override
//	public String getGiorniPubblicazioneValue() {
//		String giorniPubblicazione = recordEvento != null ? recordEvento.getAttributeAsString("giorniPubblicazione") : null;
//		return giorniPubblicazione;
//	}
	
	/*********************************************************************************************/
	
	@Override
	public HashSet<String> getTipiModelliAttiInAllegati() {
		return dettaglioPraticaLayout != null ? dettaglioPraticaLayout.getTipiModelliAtti() : null;
	}

	@Override
	public String getNomeTastoSalvaProvvisorio() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaProvvisorio") : null;
	}

	@Override
	public String getNomeTastoSalvaDefinitivo() {
		return recordEvento != null ? recordEvento.getAttribute("nomeTastoSalvaDefinitivo") : null;
	}
	
	
	public Boolean getFlgSoloPreparazioneVersPubblicazione() {
		return flgSoloPreparazioneVersPubblicazione != null && flgSoloPreparazioneVersPubblicazione;		
	}
	
	@Override
	public boolean hasDocumento() {
		return false;
	}

	public void afterShow() {
		
	}
	
	public void soloPreparazioneVersPubblicazioneMode() {
		if (isReadOnly()) {
			readOnlyMode();
		} else {
			editMode();
		}
		if(listaAllegatiItem != null) {
			if(listaAllegatiItem instanceof AllegatiGridItem) {
				((AllegatiGridItem)listaAllegatiItem).soloPreparazioneVersPubblicazioneMode();
			} else if(listaAllegatiItem instanceof AllegatiItem) {
				((AllegatiItem)listaAllegatiItem).soloPreparazioneVersPubblicazioneMode();
			} 			
		}
	}

	public void readOnlyMode() {
		viewMode();
//		if(dataInizioPubblicazioneItem != null) {
//			dataInizioPubblicazioneItem.setCanEdit(true);
//		}		
//		if(giorniPubblicazioneItem != null) {
//			giorniPubblicazioneItem.setCanEdit(true);
//		}	
		if(listaAllegatiItem != null) {
			if(listaAllegatiItem instanceof AllegatiGridItem) {
				((AllegatiGridItem)listaAllegatiItem).readOnlyMode();
			} else if(listaAllegatiItem instanceof AllegatiItem) {
				((AllegatiItem)listaAllegatiItem).readOnlyMode();
			} 			
		}
		if(isDatiSpesaEditabili()) {
			for (DynamicForm form : getTabForms(_TAB_DATI_SPESA_CORRENTE_ID)) {
				setCanEdit(true, form);
			}
			for (DynamicForm form : getTabForms(_TAB_DATI_SPESA_CONTO_CAPITALE_ID)) {
				setCanEdit(true, form);
			}	
		}
	}
	
	public void editMode() {
		super.editMode();	
		if(!isDatiSpesaEditabili()) {
			for (DynamicForm form : getTabForms(_TAB_DATI_SPESA_CORRENTE_ID)) {
				setCanEdit(false, form);
			}
			for (DynamicForm form : getTabForms(_TAB_DATI_SPESA_CONTO_CAPITALE_ID)) {
				setCanEdit(false, form);
			}	
		}
	}

	public void reload() {
		dettaglioPraticaLayout.caricaDettaglioEvento(nome);
	}
	
	@Override
	public void back() {
		dettaglioPraticaLayout.caricaDettaglioEventoApp(nome);
	}

	public void next() {
		dettaglioPraticaLayout.caricaDettaglioEventoSuccessivo(nome);
	}

	public Map<String, Object> getAttributiDinamici() {
		return new HashMap<String, Object>();
	}

	public Map<String, String> getTipiAttributiDinamici() {
		return new HashMap<String, String>();
	}
	
	@Override
	public Record getRecordEvento() {
		return recordEvento;
	}
	
	@Override
	public void loadDati() {
		loadDettPropostaAtto(new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				caricaAttributiDinamiciDoc(nomeFlussoWF, processNameWF, activityName, tipoDocumento, rowidDoc);
				if (isEseguibile()) {
					if(isSoloPreparazioneVersPubblicazione()) {
						soloPreparazioneVersPubblicazioneMode();
					} else if (isReadOnly()) {
						readOnlyMode();
					} else {
						editMode();
					}
				} else {
					viewMode();
				}
			}
		});
	}
	
	public void loadDettPropostaAtto(final ServiceCallback<Record> callback) {
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.addParam("idProcess", idProcess);
		lNuovaPropostaAtto2CompletaDataSource.addParam("taskName", activityName);
		if(isAttivaRequestMovimentiDaAMC()) {
			lNuovaPropostaAtto2CompletaDataSource.addParam("flgAttivaRequestMovimentiDaAMC", "true");
		}								
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		lNuovaPropostaAtto2CompletaDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record lRecord = response.getData()[0];
					recordFromLoadDett = new Record(lRecord.getJsObj());
					rowidDoc = lRecord.getAttribute("rowidDoc");
					tipoDocumento = lRecord.getAttribute("tipoDocumento");
					if (isEseguibile() && !isReadOnly()) {
						if(listaRecordModelli != null && listaRecordModelli.getLength() > 0) {
							RecordList listaAllegati = lRecord.getAttributeAsRecordList("listaAllegati");
							for (int i = 0; i < listaRecordModelli.getLength(); i++) {
								final String idTipoModello = listaRecordModelli.get(i).getAttribute("idTipoDoc");
								int posModello = getPosModelloFromTipo(idTipoModello, listaAllegati);
								if (posModello != -1) {
									listaAllegati.removeAt(posModello);
								}
							}
							lRecord.setAttribute("listaAllegati", listaAllegati);
						}
					}
					editRecord(lRecord);
					// IMPORTANTE: quando ricarico i dati da DB devo finire la renderizzazione a maschera prima di chiamare la getRecordToSave(),
					// altrimenti l'html dei CKEditor risulta indentato male e questo crea problemi durante l'iniezione nel modello
					//TODO DA PORTARE ANCHE NELLE ALTRE MASCHERE DEI TASK		
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {		
							if(callback != null) {
								callback.execute(lRecord);
							}
						}
					});
				}
			}
		});
	}
	
	public void salvaDati(final boolean flgIgnoreObblig, final ServiceCallback<Record> callback) {
		salvaDati(flgIgnoreObblig, null, callback);
	}
	
	public void salvaDati(final boolean flgIgnoreObblig, final Map<String, String> otherExtraparam, final ServiceCallback<Record> callback) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {				
				final Record lRecordToSave = getRecordToSave();
				// Sbianco il valore in modo che non resti a maschera in caso di errori
				hiddenForm.setValue("uriDocGeneratoFormatoOdt", "");
				final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
				lNuovaPropostaAtto2CompletaDataSource.setTimeout(Integer.MAX_VALUE); // setto al massimo il timeout in salvataggio per evitare di eseguire la setMovimentiAtti di Sicra senza poi allineare i dati a maschera con le modifiche, causando una successiva richiamata identica a quella già eseguita (con conseguenti movimenti doppioni lato Sicra)
				if(otherExtraparam != null) {
					for(String key : otherExtraparam.keySet()) {
						lNuovaPropostaAtto2CompletaDataSource.addParam(key, otherExtraparam.get(key));
					}
				}
				lNuovaPropostaAtto2CompletaDataSource.addParam("idProcess", idProcess);
				lNuovaPropostaAtto2CompletaDataSource.addParam("taskName", activityName);
				lNuovaPropostaAtto2CompletaDataSource.addParam("esitoTask", attrEsito != null ? attrEsito.getAttribute("valore") : null);
				lNuovaPropostaAtto2CompletaDataSource.addParam("msgEsitoTask", messaggio);
				if(isReadOnly() || (isPresenteAttributoCustomCablato("#ALLEGATI_PARTE_INTEGRANTE") && !getFlgEditabileAttributoCustomCablato("#ALLEGATI_PARTE_INTEGRANTE"))) {
					lNuovaPropostaAtto2CompletaDataSource.addParam("idTaskCorrente", getIdTaskCorrente());
				}
				if(isAttivaSalvataggioMovimentiDaAMC()) {
					lNuovaPropostaAtto2CompletaDataSource.addParam("flgAttivaSalvataggioMovimentiDaAMC", "true");
				}
				final Map<String, Object> attributiDinamiciEvent = getAttributiDinamici();
				final Map<String, String> tipiAttributiDinamiciEvent = getTipiAttributiDinamici();
				final String esito = (!flgIgnoreObblig && attrEsito != null) ? attrEsito.getAttribute("valore") : null;
				// se è il salvataggio finale
				if (!flgIgnoreObblig) {
					lNuovaPropostaAtto2CompletaDataSource.addParam("flgSalvataggioDefinitivoPreCompleteTask", "true");
					if(hasActionProtocollazioneProsa() && isEsitoTaskAzioni(attrEsito)) {
						lRecordToSave.setAttribute("flgProtocollazioneProsa", "true");												
					}
					String eventoSIBXEsito = getEventoSIBXEsitoTask(attrEsito);
					if(eventoSIBXEsito != null && !"".equals(eventoSIBXEsito)) {
						lRecordToSave.setAttribute("eventoSIB", eventoSIBXEsito);
					} else if(isEsitoTaskEventoSIBValorizzato()) {
						if(isEsitoTaskEventoSIB(attrEsito)) {
							// se mi arriva valorizzata la lista di esiti per cui deve essere eseguito l'evento su SIB, e se l'esito selezionato è uno di questi, allora lo passo
							lRecordToSave.setAttribute("eventoSIB", tipoEventoSIB != null ? tipoEventoSIB : "");
						}
					} else if(isEsitoTaskOk(attrEsito)) {			
						// se l'esito è ok o non c'è nessun esito allora passo l'evento con cui richiamare il servizio aggiornaAtto() di SIB
						lRecordToSave.setAttribute("eventoSIB", tipoEventoSIB != null ? tipoEventoSIB : "");				
					}								
					lRecordToSave.setAttribute("eventoContabilia", getEventoContabiliaXEsitoTask(attrEsito));			
					lRecordToSave.setAttribute("eventoSICRA", getEventoSICRAXEsitoTask(attrEsito));													
					if(isDeterminaConSpesa() && tipoEventoSIB != null && "visto".equals(tipoEventoSIB)) {
//						if(listaRecordModelli != null && listaRecordModelli.getLength() > 0) {
//							for (int i = 0; i < listaRecordModelli.getLength(); i++) {
//								final String idTipoModello = listaRecordModelli.get(i).getAttribute("idTipoDoc");
//								RecordList listaAllegati = allegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
//								int posModello = getPosModelloFromTipo(idTipoModello, listaAllegati);
//								if (posModello != -1) {						
//									lRecordToSave.setAttribute("allegatoVistoContabile", listaAllegati.get(posModello));
//								}
//							}
//						}
						if(allegatoGeneratoDaModelloTask != null) {
							lRecordToSave.setAttribute("allegatoVistoContabile", allegatoGeneratoDaModelloTask);
						}								
					}
					if (!isAvvioPropostaAtto() && isEseguibile() && !isReadOnly()) {
						lNuovaPropostaAtto2CompletaDataSource.addParam("versionaFileDispositivo", "true");
						if(hasPrimarioDatiSensibili()) {
							lNuovaPropostaAtto2CompletaDataSource.addParam("hasPrimarioDatiSensibili", "true");
						}
					}
					if(attrEsito != null) {
						lNuovaPropostaAtto2CompletaDataSource.addParam("nomeAttrCustomEsito", attrEsito.getAttribute("nome"));
						lNuovaPropostaAtto2CompletaDataSource.addParam("valoreAttrCustomEsito", attrEsito.getAttribute("valore"));
						attributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("valore"));
						tipiAttributiDinamiciEvent.put(attrEsito.getAttribute("nome"), attrEsito.getAttribute("tipo"));
						attrEsitoNotEmail = new Record(attrEsito.toMap());
						attrEsito = null;
					}
				}
				Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());	
				lNuovaPropostaAtto2CompletaDataSource.updateData(lRecordToSave, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record lRecord = response.getData()[0];
							String esitoSetMovimentiAttoSICRA = lRecord != null ? lRecord.getAttribute("esitoSetMovimentiAttoSICRA") : null;
							String messaggioWarning = lRecord != null ? lRecord.getAttribute("messaggioWarning") : null;
							final String codXSalvataggioConWarning = lRecord != null ? lRecord.getAttribute("codXSalvataggioConWarning") : null;
							if(esitoSetMovimentiAttoSICRA != null && "OK".equalsIgnoreCase(esitoSetMovimentiAttoSICRA)) {
								listaInvioMovimentiContabiliSICRAItem.resetListaMovimentiToDeleteAndInsert();
								lRecordToSave.setAttribute("listaMovimentiSICRAToDelete", new RecordList());
								lRecordToSave.setAttribute("listaMovimentiSICRAToInsert", new RecordList());
							}
							if(messaggioWarning != null && !"".equals(messaggioWarning) && codXSalvataggioConWarning != null && !"".equals(codXSalvataggioConWarning)) {
								Layout.hideWaitPopup();
								salvaDatiConWarning(lNuovaPropostaAtto2CompletaDataSource, lRecordToSave, messaggioWarning, codXSalvataggioConWarning, new ServiceCallback<Record>() {
									
									@Override
									public void execute(Record object) {
										afterSalvaDati(flgIgnoreObblig, esito, attributiDinamiciEvent, tipiAttributiDinamiciEvent, callback);
									}
								});												
							} else {
								if(messaggioWarning != null && !"".equals(messaggioWarning)) {
									AurigaLayout.addMessage(new MessageBean(messaggioWarning, "", MessageType.WARNING));
								}
								afterSalvaDati(flgIgnoreObblig, esito, attributiDinamiciEvent, tipiAttributiDinamiciEvent, callback);
							}
						}
					}
				});
			}
		});
	}
	
	public void salvaDatiConWarning(final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource, final Record lRecordToSave, String messaggioWarning, final String codXSalvataggioConWarning, final ServiceCallback<Record> callbackSalvaDatiConWarning) {
		AurigaLayout.showConfirmDialogWithWarning("Attenzione!", messaggioWarning, "Ok", "Annulla", new BooleanCallback() {
			@Override
			public void execute(Boolean value) {
				if(value != null && value) {
					lNuovaPropostaAtto2CompletaDataSource.addParam("codXSalvataggioConWarning", codXSalvataggioConWarning);
					lNuovaPropostaAtto2CompletaDataSource.updateData(lRecordToSave, new DSCallback() {
						
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record lRecord = response.getData()[0];
								String esitoSetMovimentiAttoSICRA = lRecord != null ? lRecord.getAttribute("esitoSetMovimentiAttoSICRA") : null;
								String messaggioWarning2 = lRecord != null ? lRecord.getAttribute("messaggioWarning") : null;
								final String codXSalvataggioConWarning2 = lRecord != null ? lRecord.getAttribute("codXSalvataggioConWarning") : null;
								if(esitoSetMovimentiAttoSICRA != null && "OK".equalsIgnoreCase(esitoSetMovimentiAttoSICRA)) {
									listaInvioMovimentiContabiliSICRAItem.resetListaMovimentiToDeleteAndInsert();
									lRecordToSave.setAttribute("listaMovimentiSICRAToDelete", new RecordList());
									lRecordToSave.setAttribute("listaMovimentiSICRAToInsert", new RecordList());
								}
								if(messaggioWarning2 != null && !"".equals(messaggioWarning2) && codXSalvataggioConWarning2 != null && !"".equals(codXSalvataggioConWarning2)) {
									salvaDatiConWarning(lNuovaPropostaAtto2CompletaDataSource, lRecordToSave, messaggioWarning2, codXSalvataggioConWarning2, callbackSalvaDatiConWarning);
								} else {
									if(messaggioWarning2 != null && !"".equals(messaggioWarning2)) {
										AurigaLayout.addMessage(new MessageBean(messaggioWarning2, "", MessageType.WARNING));
									}
									if(callbackSalvaDatiConWarning != null) {
										callbackSalvaDatiConWarning.execute(null);
									}
								}
							}
						}
					});
				} else {
//					if(callbackSalvaDatiConWarning != null) {
//						callbackSalvaDatiConWarning.execute(null);
//					}
				}
			}
		});		
	}
	
	public void afterSalvaDati(final boolean flgIgnoreObblig, String esito, final Map<String, Object> attributiDinamiciEvent, final Map<String, String> tipiAttributiDinamiciEvent, final ServiceCallback<Record> callback) {
		salvaAttributiDinamiciDocAfterSalva(flgIgnoreObblig, rowidDoc, activityName, esito, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				Record lRecordEvent = new Record();
				lRecordEvent.setAttribute("idProcess", idProcess);
				lRecordEvent.setAttribute("idEvento", idEvento);
				lRecordEvent.setAttribute("idTipoEvento", idTipoEvento);
				lRecordEvent.setAttribute("idUd", idUd);
				lRecordEvent.setAttribute("desEvento", dettaglioPraticaLayout.getDisplayNameEvento(nome));
				if (messaggio != null) {
					lRecordEvent.setAttribute("messaggio", messaggio);
				}
				lRecordEvent.setAttribute("valori", attributiDinamiciEvent);
				lRecordEvent.setAttribute("tipiValori", tipiAttributiDinamiciEvent);
				GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("CustomEventDatasource");
				if (flgIgnoreObblig) {
					lGWTRestService.addParam("flgIgnoreObblig", "1");
				} 
				lGWTRestService.addParam("skipSuccessMsg", "true");							
				lGWTRestService.call(lRecordEvent, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						if (callback != null) {
							callback.execute(object);
						} else {
							Layout.hideWaitPopup();
						}
					}
				});
			}
		});
	}
	
	/*
	@Override
	public void editRecord(Record record) {
		
		vm.clearErrors(true);
		clearTabErrors(tabSet);

		super.editRecord(record);

		if (isEseguibile()) {
			if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
				AllegatoCanvas lAllegatoCanvas = listaAllegatiItem.getAllegatoCanvasFromTipo(idTipoTaskDoc);
				if (lAllegatoCanvas == null) {
					lAllegatoCanvas = (AllegatoCanvas) listaAllegatiItem.onClickNewButton();
					lAllegatoCanvas.setFixedTipoFileAllegato(idTipoTaskDoc, nomeTipoTaskDoc);	
					boolean flgParere = flgParereTaskDoc != null && flgParereTaskDoc;
					boolean flgParteDispositivo = flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc;									
					boolean flgNoPubbl = flgNoPubblTaskDoc != null && flgNoPubblTaskDoc;
					boolean flgPubblicaSeparato = flgPubblicaSeparatoTaskDoc != null && flgPubblicaSeparatoTaskDoc;										
					lAllegatoCanvas.getForm()[0].setValue("flgParere", flgParere);
					if(flgParere) {
						lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", false);
						lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", flgNoPubbl);
						lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", true);
					} else {
						lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", flgParteDispositivo);
						if(!flgParteDispositivo) {
							lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", true);
							lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", false);
						} else {
							lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", flgNoPubbl);	
							lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", flgPubblicaSeparato);						
						}
					}
				}					
			}
		}
	}
	*/
	
	@Override
	public boolean customValidate() {
		
		boolean valid = super.customValidate(); // perche estendo NuovaPropostaAtto2CompletaDetail
		
		/*
		if(isDatiSpesaEditabili() && isEsitoTaskSelezionatoOk()) {
			if(showTabDatiSpesaCorrente() && showDetailSectionInvioDatiSpesaCorrente() && !isPresentiDatiStoriciCorrente()) {
				invioDatiSpesaCorrenteForm.setFieldErrors("listaInvioDatiSpesaCorrente", "Nessun impegno / accertamento specificato nella griglia dei dati della spesa corrente: devi indicarne almeno uno");
				detailSectionInvioDatiSpesaCorrente.open();
				valid = false;
			}
			if(activityName != null && (activityName.equals("controllo_bilancio_corr") || activityName.equals("verifica_po_bilancio_corr"))) {
				if(showTabDatiSpesaCorrente() && showDetailSectionDatiContabiliSIBCorrente() && !isPresentiDatiContabiliSIBCorrente()) {
					invioDatiSpesaCorrenteForm.setFieldErrors("listaDatiContabiliSIBCorrente", "In SIB non risultano impegni/accertamenti o sub associati alla proposta per la parte di spesa corrente: deve essercene almeno uno per poter procedere");
					detailSectionDatiContabiliSIBCorrente.open();
					valid = false;
				}
			}
			if(showTabDatiSpesaContoCapitale() && showDetailSectionInvioDatiSpesaContoCapitale() && !isPresentiDatiStoriciContoCapitale()) {
				invioDatiSpesaContoCapitaleForm.setFieldErrors("listaInvioDatiSpesaContoCapitale", "Nessun impegno / accertamento specificato nella griglia dei dati della spesa in conto capitale: devi indicarne almeno uno");
				detailSectionInvioDatiSpesaContoCapitale.open();
				valid = false;
			}
			if(activityName != null && (activityName.equals("controllo_bilancio_cccap") || activityName.equals("verifica_po_bilancio_ccap") || activityName.equals("validazione_dirigente_ragioneria"))) {
				if(showTabDatiSpesaContoCapitale() && showDetailSectionDatiContabiliSIBContoCapitale() && !isPresentiDatiContabiliSIBContoCapitale()) {
					invioDatiSpesaContoCapitaleForm.setFieldErrors("listaDatiContabiliSIBContoCapitale", "In SIB non risultano impegni/accertamenti o sub associati alla proposta per la parte di spesa in conto capitale: deve essercene almeno uno per poter procedere");
					detailSectionDatiContabiliSIBContoCapitale.open();
					valid = false;
				}
			}	
			if(activityName != null && activityName.equals("verifica_contabilita")) {
				boolean isPresentiDatiContabiliSIBCorrente = showTabDatiSpesaCorrente() && showDetailSectionDatiContabiliSIBCorrente() && isPresentiDatiContabiliSIBCorrente();
				boolean isPresentiDatiContabiliSIBContoCapitale = showTabDatiSpesaContoCapitale() && showDetailSectionDatiContabiliSIBContoCapitale() && isPresentiDatiContabiliSIBContoCapitale();
				if(!isPresentiDatiContabiliSIBCorrente && !isPresentiDatiContabiliSIBContoCapitale) {
					AurigaLayout.addMessage(new MessageBean("In SIB non risultano impegni/accertamenti o sub associati alla proposta: deve essercene almeno uno per poter procedere", "", MessageType.ERROR));
					valid = false;
				}
			}	
		}
		*/
		
		if (idTipoTaskDoc != null && !"".equals(idTipoTaskDoc) && nomeTipoTaskDoc != null && !"".equals(nomeTipoTaskDoc)) {
			final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;
			Record recordControllo = esito != null && !"".equals(esito) ? controlliXEsitiTaskDoc.get(esito) : null;
			if (attrEsito != null && recordControllo == null) {
			 	final String label = attrEsito.getAttribute("label") != null ? attrEsito.getAttribute("label").toLowerCase() : null;
				String esitoCompleto = label + " " + esito;
				recordControllo = esitoCompleto != null && !"".equals(esitoCompleto) ? controlliXEsitiTaskDoc.get(esitoCompleto) : null;
			}
			if (recordControllo == null) {
				recordControllo = controlliXEsitiTaskDoc.get("#ANY");
			}
			final boolean flgObbligatorio = recordControllo != null && recordControllo.getAttribute("flgObbligatorio") != null
					&& "1".equals(recordControllo.getAttribute("flgObbligatorio"));
			final boolean flgFileObbligatorio = recordControllo != null && recordControllo.getAttribute("flgFileObbligatorio") != null
					&& "1".equals(recordControllo.getAttribute("flgFileObbligatorio"));
			final boolean flgFileFirmato = recordControllo != null && recordControllo.getAttribute("flgFileFirmato") != null
					&& "1".equals(recordControllo.getAttribute("flgFileFirmato"));
			
			if(listaAllegatiItem != null) {
				if(listaAllegatiItem instanceof AllegatiGridItem) {
					/* TODO NUOVA GESTIONE ALLEGATI CON GRIDITEM */
					ListGridRecord lAllegatoRecord = ((AllegatiGridItem)listaAllegatiItem).getAllegatoRecordFromTipo(idTipoTaskDoc);
					if (flgObbligatorio && lAllegatoRecord == null) {
						Record lNewAllegatoRecord = new Record();
						//TODO setFixedTipoFileAllegato
						lNewAllegatoRecord.setAttribute("listaTipiFileAllegato", idTipoTaskDoc);
						lNewAllegatoRecord.setAttribute("idTipoFileAllegato", idTipoTaskDoc);
						lNewAllegatoRecord.setAttribute("descTipoFileAllegato", nomeTipoTaskDoc);
						boolean flgParere = flgParereTaskDoc != null && flgParereTaskDoc;
						boolean flgParteDispositivo = flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc;									
						boolean flgNoPubbl = flgNoPubblTaskDoc != null && flgNoPubblTaskDoc;									
						boolean flgPubblicaSeparato = flgPubblicaSeparatoTaskDoc != null && flgPubblicaSeparatoTaskDoc;									
						lNewAllegatoRecord.setAttribute("flgParere", flgParere);
						if(flgParere) {
							lNewAllegatoRecord.setAttribute("flgParteDispositivo", false);
							lNewAllegatoRecord.setAttribute("flgNoPubblAllegato", flgNoPubbl);
							lNewAllegatoRecord.setAttribute("flgPubblicaSeparato", true);
						} else {
							lNewAllegatoRecord.setAttribute("flgParteDispositivo", flgParteDispositivo);
							if(!flgParteDispositivo) {
								lNewAllegatoRecord.setAttribute("flgNoPubblAllegato", true);
								lNewAllegatoRecord.setAttribute("flgPubblicaSeparato", false);
							} else {
								lNewAllegatoRecord.setAttribute("flgNoPubblAllegato", flgNoPubbl);
								lNewAllegatoRecord.setAttribute("flgPubblicaSeparato", flgPubblicaSeparato);						
							}
						}						
						((AllegatiGridItem)listaAllegatiItem).onClickNewButton(lNewAllegatoRecord);							
					}
					if (lAllegatoRecord != null) {
						String numeroAllegato = lAllegatoRecord.getAttribute("numeroAllegato");
						String uriFileAllegato = lAllegatoRecord.getAttribute("uriFileAllegato");
						InfoFileRecord infoFileAllegato = lAllegatoRecord.getAttribute("infoFile") != null ? new InfoFileRecord(lAllegatoRecord.getAttributeAsRecord("infoFile")) : null;
						List<String> listaErrori = new ArrayList<String>();
						if (flgFileObbligatorio && (uriFileAllegato == null || uriFileAllegato.equals("") || infoFileAllegato == null)) {
							listaErrori.add("Il file allegato n. " + numeroAllegato + " è obbligatorio");
							valid = false;
						} else if (flgFileFirmato && (uriFileAllegato != null && !uriFileAllegato.equals(""))
								&& (infoFileAllegato != null && !infoFileAllegato.isFirmato())) {
							listaErrori.add("Il file allegato n. " + numeroAllegato + " non è firmato");
							valid = false;
						} else if (flgFileFirmato && (uriFileAllegato != null && !uriFileAllegato.equals(""))
								&& (infoFileAllegato != null && !infoFileAllegato.isFirmaValida())) {
							listaErrori.add("Il file allegato n. " + numeroAllegato + " presenta una firma non valida alla data odierna");
							valid = false;
						}
						listaAllegatiItem.setErrors(listaErrori.toArray(new String[listaErrori.size()]));					
					}
				} else if(listaAllegatiItem instanceof AllegatiItem) {						
					/* TODO VECCHIA GESTIONE ALLEGATI CON REPLICABLEITEM */
					AllegatoCanvas lAllegatoCanvas = ((AllegatiItem)listaAllegatiItem).getAllegatoCanvasFromTipo(idTipoTaskDoc);
					if (flgObbligatorio && lAllegatoCanvas == null) {
						lAllegatoCanvas = (AllegatoCanvas) ((AllegatiItem)listaAllegatiItem).onClickNewButton();
						lAllegatoCanvas.setFixedTipoFileAllegato(idTipoTaskDoc, nomeTipoTaskDoc);
						boolean flgParere = flgParereTaskDoc != null && flgParereTaskDoc;
						boolean flgParteDispositivo = flgParteDispositivoTaskDoc != null && flgParteDispositivoTaskDoc;									
						boolean flgNoPubbl = flgNoPubblTaskDoc != null && flgNoPubblTaskDoc;									
						boolean flgPubblicaSeparato = flgPubblicaSeparatoTaskDoc != null && flgPubblicaSeparatoTaskDoc;									
						lAllegatoCanvas.getForm()[0].setValue("flgParere", flgParere);
						if(flgParere) {
							lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", false);
							lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", flgNoPubbl);
							lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", true);
						} else {
							lAllegatoCanvas.getForm()[0].setValue("flgParteDispositivo", flgParteDispositivo);
							if(!flgParteDispositivo) {
								lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", true);
								lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", false);
							} else {
								lAllegatoCanvas.getForm()[0].setValue("flgNoPubblAllegato", flgNoPubbl);
								lAllegatoCanvas.getForm()[0].setValue("flgPubblicaSeparato", flgPubblicaSeparato);							
							}
						}						
					}
					if (lAllegatoCanvas != null) {
						String uriFileAllegato = lAllegatoCanvas.getFormValuesAsRecord().getAttribute("uriFileAllegato");
						InfoFileRecord infoFileAllegato = lAllegatoCanvas.getForm()[0].getValue("infoFile") != null ? new InfoFileRecord(
								lAllegatoCanvas.getForm()[0].getValue("infoFile")) : null;
						if (flgFileObbligatorio && (uriFileAllegato == null || uriFileAllegato.equals("") || infoFileAllegato == null)) {
							lAllegatoCanvas.getForm()[0].setFieldErrors("nomeFileAllegato", "Il file è obbligatorio");
							valid = false;
						} else if (flgFileFirmato && (uriFileAllegato != null && !uriFileAllegato.equals(""))
								&& (infoFileAllegato != null && !infoFileAllegato.isFirmato())) {
							lAllegatoCanvas.getForm()[0].setFieldErrors("nomeFileAllegato", "Il file non è firmato");
							valid = false;
						} else if (flgFileFirmato && (uriFileAllegato != null && !uriFileAllegato.equals(""))
								&& (infoFileAllegato != null && !infoFileAllegato.isFirmaValida())) {
							lAllegatoCanvas.getForm()[0].setFieldErrors("nomeFileAllegato", "Il file presenta una firma non valida alla data odierna");
							valid = false;
						}
					}	
				}
			}
							
//			if (detailSectionAllegati != null) {
//				detailSectionAllegati.open();
//			}

		}
		return valid;
	}

	public Boolean validateSenzaObbligatorieta() {
		
		clearTabErrors(tabSet);
		vm.clearErrors(true);
		boolean valid = true;
		if (attributiAddDocDetails != null) {
			for (String key : attributiAddDocDetails.keySet()) {
				boolean esitoAttributiAddDocDetail = attributiAddDocDetails.get(key).validateSenzaObbligatorieta();
				valid = valid && esitoAttributiAddDocDetail;
			}
		}
		showTabErrors(tabSet);
		if (valid) {
			setSaved(valid);
		} else {
			reopenAllSections();			
		}
		return valid;
	}
	
	@Override
	public void salvaDatiProvvisorio() {
		
		if(AurigaLayout.getParametroDBAsBoolean("VERS_DISPOSITIVO_NUOVA_PROPOSTA_ATTO_2") && AurigaLayout.isPrivilegioAttivo("ATT/SF") && !isAvvioPropostaAtto() && isEseguibile() && !isReadOnly()) {
			SC.ask("Vuoi salvare i dati attuali in una nuova versione pdf dell'atto?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					continuaSalvaDatiProvvisorio(value != null && value);
				}
			});
		} else {
			continuaSalvaDatiProvvisorio(false);
		}
	}
	
	private void continuaSalvaDatiProvvisorio(boolean flgAggiornaVersDispositivo) {

		if (validateSenzaObbligatorieta()) {
			Map<String, String> otherExtraparam = new HashMap<String, String>();									
			otherExtraparam.put("flgSalvataggioProvvisorioInBozza", "true");
			if(flgAggiornaVersDispositivo) {
				otherExtraparam.put("versionaFileDispositivo", "true");
				if(hasPrimarioDatiSensibili()) {
					otherExtraparam.put("hasPrimarioDatiSensibili", "true");
				}
			}
			salvaDati(true, otherExtraparam, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

						@Override
						public void execute(Record record) {
							Layout.hideWaitPopup();
							// TODO: qui dovrei ricaricare i tab degli attributi dinamici per vedere le eventuali variazioni
							reload();
						}
					});
				}
			});
		}
	}

	@Override
	public void salvaDatiDefinitivo() {
		
		if (validate()) {		
			//TODO gestire nuovi campi flgOpCommerciale, flgEscludiCIG e motivoEsclusioneCIG
			if(showAttributoCustomCablato("DATI_CONTABILI") && showDetailSectionCIG() && listaCIGItem != null && listaCIGItem.getEditing()
			   && (isDeterminaConSpesa() || isDeterminaConSpesaSenzaImpegni() || isDeterminaAggiudicaProceduraGara())) {
				boolean isListaCIGEmpty = false;
				if(listaCIGItem != null) {
					RecordList listaCIG = CIGForm.getValueAsRecordList("listaCIG");
					isListaCIGEmpty = true;
					for(int i=0; i < listaCIG.getLength(); i++) {
						if(listaCIG.get(i).getAttribute("codiceCIG") != null &&
								!"".equals(listaCIG.get(i).getAttribute("codiceCIG"))) {
							isListaCIGEmpty = false;
							break;
						}
					}
				}
				if (!isEsclusoCIG() && isListaCIGEmpty) {
					SC.ask("CIG non valorizzato. Vuoi procedere comunque?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if (value != null && value) {
								continuaSalvaDatiDefinitivo();
							} else {
								messaggio = null;
								attrEsito = null;								
							}
						}
					});
				} else {
					continuaSalvaDatiDefinitivo();
				}
			} else {
				continuaSalvaDatiDefinitivo();
			}
		} else {
			messaggio = null;
			attrEsito = null;
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}
	
	private void continuaSalvaDatiDefinitivo() {
		final String nomeAttrCustomEsitoTask = recordEvento.getAttribute("nomeAttrCustomEsitoTask");
		if (nomeAttrCustomEsitoTask != null && !"".equals(nomeAttrCustomEsitoTask)) {
			GWTRestService<Record, Record> lAttributiDinamiciDatasource = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			Record lAttributiDinamiciRecord = new Record();
			lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_PROCESS_HISTORY");
			lAttributiDinamiciRecord.setAttribute("rowId", rowId);
			lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoEvento);
			lAttributiDinamiciDatasource.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(final Record object) {
					RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
					for (int i = 0; i < attributiAdd.getLength(); i++) {
						final Record attr = attributiAdd.get(i);
						if (attr.getAttribute("nome").equals(nomeAttrCustomEsitoTask)) {
							SelezionaEsitoTaskWindow selezionaEsitoTaskWindow = new SelezionaEsitoTaskWindow(attr, true, esitiTaskOk, valoriEsito, mappaWarningMsgXEsitoTask, esitoTaskDaPreimpostare, msgTaskDaPreimpostare,
								new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {	
										messaggio = object.getAttribute("messaggio");
										attrEsito = new Record(attr.toMap());
										attrEsito.setAttribute("valore", object.getAttribute(nomeAttrCustomEsitoTask));
										saveAndGoAlert();
									}
								}
							);
							selezionaEsitoTaskWindow.show();
							break;
						}
					}
				}
			});
		} else {
			messaggio = null;
			attrEsito = null;
			saveAndGoAlert();
		}
	}
	
	public String getValidateErrorMessage() {
		return I18NUtil.getMessages().validateError_message();
	}
	
	public void saveAndGoAlert() {
		if (validate()) {
			if (alertConfermaSalvaDefinitivo != null && !"".equals(alertConfermaSalvaDefinitivo)) {
				SC.ask(alertConfermaSalvaDefinitivo, new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value != null && value) {
							saveAndGo();
						} else {
							messaggio = null;
							attrEsito = null;	
						}
					}
				});
			} else {
				saveAndGo();
			}
		} else {
			messaggio = null;
			attrEsito = null;
			Layout.addMessage(new MessageBean(getValidateErrorMessage(), "", MessageType.ERROR));
		}
	}
	
	public RecordList getListaRecordModelliXEsito(String esito) {
		if (listaRecordModelli != null && listaRecordModelli.getLength() > 0) {	
			RecordList listaRecordModelliConEsitoUguale = new RecordList();		
			RecordList listaRecordModelliSenzaEsito = new RecordList();		
			for (int i = 0; i < listaRecordModelli.getLength(); i++) {
				String listaEsitiXGenModello = listaRecordModelli.get(i).getAttribute("esitiXGenModello");							
				if (listaEsitiXGenModello != null && !"".equals(listaEsitiXGenModello)) {
					for (String esitoXGenModello : new StringSplitterClient(listaEsitiXGenModello, "|*|").getTokens()) {
						if (esito != null && esito.equalsIgnoreCase(esitoXGenModello)) {
							listaRecordModelliConEsitoUguale.add(listaRecordModelli.get(i));							
						}
					} 
				} else {
					listaRecordModelliSenzaEsito.add(listaRecordModelli.get(i));				
				}
			}	
			if(listaRecordModelliConEsitoUguale != null && listaRecordModelliConEsitoUguale.getLength() > 0) {
				return listaRecordModelliConEsitoUguale;
			} else if(listaRecordModelliSenzaEsito != null && listaRecordModelliSenzaEsito.getLength() > 0) {
				return listaRecordModelliSenzaEsito;
			} 
		}
		return null;
	}
	
	@Override
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();
		lRecordToSave.setAttribute("idProcess", getIdProcessTask());
		lRecordToSave.setAttribute("idModCopertina", idModCopertina != null ? idModCopertina : "");
		lRecordToSave.setAttribute("nomeModCopertina", nomeModCopertina != null ? nomeModCopertina : "");
		lRecordToSave.setAttribute("idModCopertinaFinale", idModCopertinaFinale != null ? idModCopertinaFinale : "");
		lRecordToSave.setAttribute("nomeModCopertinaFinale", nomeModCopertinaFinale != null ? nomeModCopertinaFinale : "");
		lRecordToSave.setAttribute("idModAllegatiParteIntSeparati", idModAllegatiParteIntSeparati != null ? idModAllegatiParteIntSeparati : "");
		lRecordToSave.setAttribute("nomeModAllegatiParteIntSeparati", nomeModAllegatiParteIntSeparati != null ? nomeModAllegatiParteIntSeparati : "");
		lRecordToSave.setAttribute("uriModAllegatiParteIntSeparati", uriModAllegatiParteIntSeparati != null ? uriModAllegatiParteIntSeparati : "");
		lRecordToSave.setAttribute("tipoModAllegatiParteIntSeparati", tipoModAllegatiParteIntSeparati != null ? tipoModAllegatiParteIntSeparati : "");
		lRecordToSave.setAttribute("idModAllegatiParteIntSeparatiXPubbl", idModAllegatiParteIntSeparatiXPubbl != null ? idModAllegatiParteIntSeparatiXPubbl : "");
		lRecordToSave.setAttribute("nomeModAllegatiParteIntSeparatiXPubbl", nomeModAllegatiParteIntSeparatiXPubbl != null ? nomeModAllegatiParteIntSeparatiXPubbl : "");
		lRecordToSave.setAttribute("uriModAllegatiParteIntSeparatiXPubbl", uriModAllegatiParteIntSeparatiXPubbl != null ? uriModAllegatiParteIntSeparatiXPubbl : "");
		lRecordToSave.setAttribute("tipoModAllegatiParteIntSeparatiXPubbl", tipoModAllegatiParteIntSeparatiXPubbl != null ? tipoModAllegatiParteIntSeparatiXPubbl : "");
		lRecordToSave.setAttribute("flgAppendiceDaUnire", flgAppendiceDaUnire);		
		lRecordToSave.setAttribute("idModAppendice", idModAppendice != null ? idModAppendice : "");
		lRecordToSave.setAttribute("nomeModAppendice", nomeModAppendice != null ? nomeModAppendice : "");
		lRecordToSave.setAttribute("idModello", idModAssDocTask != null ? idModAssDocTask : "");
		lRecordToSave.setAttribute("nomeModello", nomeModAssDocTask != null ? nomeModAssDocTask : "");
		lRecordToSave.setAttribute("displayFilenameModello", displayFilenameModAssDocTask != null ? displayFilenameModAssDocTask : "");
		lRecordToSave.setAttribute("idUoDirAdottanteSIB", idUoDirAdottanteSIB != null ? idUoDirAdottanteSIB : "");
		lRecordToSave.setAttribute("codUoDirAdottanteSIB", codUoDirAdottanteSIB != null ? codUoDirAdottanteSIB : "");
		lRecordToSave.setAttribute("desUoDirAdottanteSIB", desUoDirAdottanteSIB != null ? desUoDirAdottanteSIB : "");		
		if(getFlgSoloPreparazioneVersPubblicazione() && getValueAsBoolean("isChangedFilePrimarioOmissis")) {
			lRecordToSave.setAttribute("flgDatiSensibili", true);
			lRecordToSave.setAttribute("flgPrivacy", _FLG_SI);			
		}
		return lRecordToSave;
	}
	
	public void controlloFormatiAllegPICompletamentoTaskConEsitoOk(BooleanCallback callback) {
		boolean isFormatiAmmessi = true;
		if(flgCtrlMimeTypeAllegPI != null && flgCtrlMimeTypeAllegPI) {
			String mimetypeAmmessiAllegatiParteIntegranteAtti = AurigaLayout.getParametroDB("MIMETYPE_AMM_ALL_PI_ATTO");
			if(mimetypeAmmessiAllegatiParteIntegranteAtti != null && !"".equals(mimetypeAmmessiAllegatiParteIntegranteAtti)) {
				String modalitaControlloMimetypeAllegatiParteIntegranteAtti = AurigaLayout.getParametroDB("MOD_CTRL_MIMETYPE_ALL_PI_ATTO");
				if(modalitaControlloMimetypeAllegatiParteIntegranteAtti != null && "completamento_task".equalsIgnoreCase(modalitaControlloMimetypeAllegatiParteIntegranteAtti)) {				
					StringSplitterClient st = new StringSplitterClient(mimetypeAmmessiAllegatiParteIntegranteAtti, ";");
					HashSet<String> setMimetypeAmmessi = new HashSet<String>();
					for(int i = 0; i < st.getTokens().length; i++) {
						setMimetypeAmmessi.add(st.getTokens()[i]);						
					}
					RecordList listaAllegati = allegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
					for(int i = 0; i < listaAllegati.getLength(); i++) {
						Record allegato = listaAllegati.get(i);
						if(allegato.getAttribute("flgParteDispositivo") != null && allegato.getAttributeAsBoolean("flgParteDispositivo")) {
							if (allegato.getAttribute("uriFileAllegato") != null && !"".equals(allegato.getAttribute("uriFileAllegato"))) {
								final InfoFileRecord infoFileAllegato = allegato.getAttributeAsRecord("infoFile") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(allegato.getAttributeAsRecord("infoFile").getJsObj())) : null;
								String mimetype = infoFileAllegato != null ? infoFileAllegato.getMimetype() : null;
								if(!setMimetypeAmmessi.contains(mimetype)) {
									isFormatiAmmessi = false;
									break;
								}
							} 
							if (allegato.getAttribute("flgDatiSensibili") != null && allegato.getAttributeAsBoolean("flgDatiSensibili") && 
							    allegato.getAttribute("uriFileOmissis") != null && !"".equals(allegato.getAttribute("uriFileOmissis"))) {
								final InfoFileRecord infoFileOmissis = allegato.getAttributeAsRecord("infoFileOmissis") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(allegato.getAttributeAsRecord("infoFileOmissis").getJsObj())) : null;
								String mimetypeOmissis = infoFileOmissis != null ? infoFileOmissis.getMimetype() : null;
								if(!setMimetypeAmmessi.contains(mimetypeOmissis)) {
									isFormatiAmmessi = false;
									break;
								}
							}
						}
					}					
				}
			}
		}
		if(callback != null) {
			callback.execute(isFormatiAmmessi);
		}
	}
	
	public void controlloNumerazioneUnioneFile(BooleanCallback callback) {
		if(hasActionUnioneFile()) {
			final Record lRecord = getRecordToSave();
			String numeroRegistrazione = lRecord.getAttribute("numeroRegistrazione");
			String siglaRegistroAtto = recordEvento != null ? recordEvento.getAttribute("siglaRegistroAtto") : null;
			String messaggioFirma = "";
			// Date dataRegistrazione = lRecord.getAttribute("dataRegistrazione") != null ? DateUtil.parseInput(lRecord.getAttributeAsString("dataRegistrazione")) : null;
			Date dataRegistrazione = lRecord.getAttribute("dataRegistrazione") != null ? lRecord.getAttributeAsDate("dataRegistrazione") : null;
			if (siglaRegistroAtto != null && !"".equalsIgnoreCase(siglaRegistroAtto) && (numeroRegistrazione == null || "".equalsIgnoreCase(numeroRegistrazione))) {
				if(isDataRegistrazioneSameToday(dataRegistrazione) && "BLOCCANTE".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avvisoNumerazioneConRegistrazione();
				} else if(isDataRegistrazioneSameToday(dataRegistrazione) && "WARNING".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avviso_Warning_NumerazioneConRegistrazione();
				}
				
				if(messaggioFirma != null && !"".equalsIgnoreCase(messaggioFirma)) {					
					SC.say(messaggioFirma, callback);
				} else if(callback != null){
					callback.execute(true);
				}
			} else if (siglaRegistroAtto != null && !"".equalsIgnoreCase(siglaRegistroAtto)) {
				if(isDataRegistrazioneSameToday(dataRegistrazione) && "BLOCCANTE".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avvisoNumerazioneSenzaRegistrazione();
				} else if(isDataRegistrazioneSameToday(dataRegistrazione) && "WARNING".equalsIgnoreCase(AurigaLayout.getParametroDB("MSG_FIRMA_ATTI_ENTRO_GIORNO"))) {
					messaggioFirma = I18NUtil.getMessages().nuovaPropostaAtto2_detail_avviso_Warning_NumerazioneSenzaRegistrazione();
				}
				
				if(messaggioFirma != null && !"".equalsIgnoreCase(messaggioFirma)) {					
					SC.say(messaggioFirma, callback);
				} else if(callback != null){
					callback.execute(true);
				}
				
			} else if(callback != null){
				callback.execute(true);
			}
		} else if(callback != null){
			callback.execute(true);
		}
	}
	
	public void saveAndGo() {
		final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;	
		final RecordList listaRecordModelliXEsito = getListaRecordModelliXEsito(esito);		
		if(isEsitoTaskOk(attrEsito)) {		
			controlloFormatiAllegPICompletamentoTaskConEsitoOk(new BooleanCallback() {
				
				@Override
				public void execute(Boolean valueCtrlFmtAllPI) {
					if(valueCtrlFmtAllPI != null && valueCtrlFmtAllPI) {
						controlloNumerazioneUnioneFile(new BooleanCallback() {
							
							@Override
							public void execute(Boolean valueCtrlNumUnioneFile) {
								if(valueCtrlNumUnioneFile != null && valueCtrlNumUnioneFile) {
									Map<String, String> otherExtraparam = new HashMap<String, String>();									
									otherExtraparam.put("flgSalvataggioProvvisorioPreCompleteTask", "true");
									if(hasActionUnioneFile()) {
										otherExtraparam.put("siglaRegistroAtto", recordEvento != null ? recordEvento.getAttribute("siglaRegistroAtto") : null);		
										otherExtraparam.put("siglaRegistroAtto2", recordEvento != null ? recordEvento.getAttribute("siglaRegistroAtto2") : null);								
									}
									salvaDati(true, otherExtraparam, new ServiceCallback<Record>() {
										@Override
										public void execute(Record object) {
											idEvento = object.getAttribute("idEvento");
											Layout.hideWaitPopup();
											loadDettPropostaAtto(new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record dett) {
													if(hasActionFirmaVersPubblAggiornata()) {
														firmaFileVersPubblAggiornata(new ServiceCallback<Record>() {
															
															@Override
															public void execute(Record object) {
																if (listaRecordModelliXEsito != null) {
																	salvaAttributiDinamiciDocAfterSalva(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

																		@Override
																		public void execute(Record object) {
																			saveAndGoWithListaModelliGenAutomatica(listaRecordModelliXEsito, esito, new ServiceCallback<Record>() {

																				@Override
																				public void execute(Record recordModello) {
																					salvaDati(false, new ServiceCallback<Record>() {

																						@Override
																						public void execute(Record object) {								
																							if(hasActionPubblica()) {
																								pubblica();
																							} else {
																								callbackSalvaDati(object);
																							}
																						}
																					});	
																				}
																			});
																		}
																	});
																} else {
																	salvaDati(false, new ServiceCallback<Record>() {

																		@Override
																		public void execute(Record object) {								
																			if(hasActionPubblica()) {
																				pubblica();
																			} else {
																				callbackSalvaDati(object);
																			}
																		}
																	});		
																}																						
															}
														});
													} else if(hasActionUnioneFile()) {
														// nell'unione dei file se ho dei file firmati pades devo prendere la versione precedente (quella che usiamo per l'editor, e la convertiamo in pdf) se c'è, altrimenti quella corrente 
														// se non sono tutti i convertibili i file do errore nell'unione e blocco tutto				
														unioneFileAndReturn();			
													} else if(hasActionFirma()) {
														getFileDaFirmare(new DSCallback() {

															@Override
															public void execute(DSResponse response, Object rawData, DSRequest request) {
																if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
																	Record lRecord = response.getData()[0];
																	Record[] files = lRecord.getAttributeAsRecordArray("files");
																	if (files != null && files.length > 0) {
																		firmaFile(files, new ServiceCallback<Record>() {
																			
																			@Override
																			public void execute(final Record recordAfterFirma) {
																				aggiornaFile(recordAfterFirma, new ServiceCallback<Record>() {
																					
																					@Override
																					public void execute(Record object) {
																						if (listaRecordModelliXEsito != null) {
																							salvaAttributiDinamiciDocAfterSalva(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

																								@Override
																								public void execute(Record object) {
																									saveAndGoWithListaModelliGenAutomatica(listaRecordModelliXEsito, esito, new ServiceCallback<Record>() {

																										@Override
																										public void execute(Record recordModello) {
																											salvaDati(false, new ServiceCallback<Record>() {

																												@Override
																												public void execute(Record object) {								
																													if(hasActionPubblica()) {
																														pubblica();
																													} else {
																														callbackSalvaDati(object);
																													}
																												}
																											});	
																										}
																									});
																								}
																							});
																						} else {
																							salvaDati(false, new ServiceCallback<Record>() {

																								@Override
																								public void execute(Record object) {								
																									if(hasActionPubblica()) {
																										pubblica();
																									} else {
																										callbackSalvaDati(object);
																									}
																								}
																							});		
																						}																						
																					}
																				});	
																			}
																		});	
																	} else {
																		if (listaRecordModelliXEsito != null) {
																			salvaAttributiDinamiciDocAfterSalva(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

																				@Override
																				public void execute(Record object) {
																					saveAndGoWithListaModelliGenAutomatica(listaRecordModelliXEsito, esito, new ServiceCallback<Record>() {

																						@Override
																						public void execute(Record recordModello) {
																							salvaDati(false, new ServiceCallback<Record>() {

																								@Override
																								public void execute(Record object) {								
																									if(hasActionPubblica()) {
																										pubblica();
																									} else {
																										callbackSalvaDati(object);
																									}
																								}
																							});	
																						}
																					});
																				}
																			});
																		} else {
																			salvaDati(false, new ServiceCallback<Record>() {

																				@Override
																				public void execute(Record object) {								
																					if(hasActionPubblica()) {
																						pubblica();
																					} else {
																						callbackSalvaDati(object);
																					}
																				}
																			});		
																		}			
																	}
																}
															}
														});				
													} else {
														if (listaRecordModelliXEsito != null) {
															salvaAttributiDinamiciDocAfterSalva(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

																@Override
																public void execute(Record object) {
																	saveAndGoWithListaModelliGenAutomatica(listaRecordModelliXEsito, esito, new ServiceCallback<Record>() {

																		@Override
																		public void execute(Record recordModello) {
																			salvaDati(false, new ServiceCallback<Record>() {

																				@Override
																				public void execute(Record object) {								
																					if(hasActionPubblica()) {
																						pubblica();
																					} else {
																						callbackSalvaDati(object);
																					}
																				}
																			});	
																		}
																	});
																}
															});
														} else {
															salvaDati(false, new ServiceCallback<Record>() {

																@Override
																public void execute(Record object) {								
																	if(hasActionPubblica()) {
																		pubblica();
																	} else {
																		callbackSalvaDati(object);
																	}
																}
															});		
														}			
													}						
												}
											});					
										}
									});
								}
							}
						});
					} else {
						AurigaLayout.addMessage(new MessageBean("Alcuni degli allegati parte integrante hanno formato non ammesso per avanzare l'iter come richiesto: serve convertirli manualmente in pdf e ricaricarne la versione", "", MessageType.ERROR));
					}
				}
			});	
		} else {
			if (listaRecordModelliXEsito != null) {
				// qui la chiamata per salvare i valori dei tab dinamici la devo lasciare perchè non c'è un salvataggio provvisorio che me li salva prima
				salvaAttributiDinamiciDoc(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						saveAndGoWithListaModelliGenAutomatica(listaRecordModelliXEsito, esito, new ServiceCallback<Record>() {

							@Override
							public void execute(Record recordModello) {
								salvaDati(false, new ServiceCallback<Record>() {

									@Override
									public void execute(Record object) {								
										callbackSalvaDati(object);
									}
								});	
							}
						});
					}
				});
			} else {
				salvaDati(false, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {								
						callbackSalvaDati(object);
					}
				});		
			}	
		}
	}
	
	public void getFileDaFirmare(DSCallback callback) {
		final Record lRecord = getRecordToSave();
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("getFileDaFirmare", lRecord, callback);
	}
	
	public void getFileVersPubblAggiornataDaFirmare(DSCallback callback) {
		final Record lRecord = getRecordToSave();
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("getFileVersPubblAggiornataDaFirmare", lRecord, callback);
	}
	
	// recupera i file allegati da firmare assieme al file unione nel task di Firma di adozione
	public void getFileAllegatiDaFirmareWithFileUnione(DSCallback callback) {
		final Record lRecord = getRecordToSave();
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("getFileAllegatiDaFirmareWithFileUnione", lRecord, callback);
	}
	
	protected void firmaFile(Record[] files, final ServiceCallback<Record> callback) {
		if(hasActionFirma()) {
			String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
			FirmaUtility.firmaMultipla(appletTipoFirmaAtti, hsmTipoFirmaAtti, files, new FirmaMultiplaCallback() {
	
				@Override
				public void execute(Map<String, Record> signedFiles, Record[] filesAndUd) {
					Record lRecord = new Record();
					lRecord.setAttribute("protocolloOriginale", getRecordToSave());
					Record lRecordFileFirmati = new Record();
					lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[] {}));
					lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
					if(callback != null) {
						callback.execute(lRecord);
					}					
				}
			});
		} else {
			Record lRecord = new Record();
			lRecord.setAttribute("protocolloOriginale", getRecordToSave());
			Record lRecordFileFirmati = new Record();
			lRecordFileFirmati.setAttribute("files", files);
			lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
			if(callback != null) {
				callback.execute(lRecord);
			}
		}
	}
	
	protected void firmaFileVersPubblAggiornata(final ServiceCallback<Record> callback) {
		final List<Record> listaFiles = new ArrayList<Record>();
		getFileVersPubblAggiornataDaFirmare(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordFileDaFirmare = response.getData()[0];
					Record[] filesDaFirmare = lRecordFileDaFirmare.getAttributeAsRecordArray("files");
					Record lFileUnione = null;
					Record lFileUnioneOmissis = null;
					if(filesDaFirmare != null) {
						for(int i = 0; i < filesDaFirmare.length; i++) {
							if(filesDaFirmare[i].getAttribute("idFile").startsWith("primarioOmissis")) {
								lFileUnioneOmissis = filesDaFirmare[i];
							} else if(filesDaFirmare[i].getAttribute("idFile").startsWith("primario")) {
								lFileUnione = filesDaFirmare[i];
							} 
							listaFiles.add(filesDaFirmare[i]);	
						}
					}
					final Record[] files = listaFiles.toArray(new Record[listaFiles.size()]);
					if(lFileUnioneOmissis != null) {
						String uriFileUnioneOmissis = lFileUnioneOmissis.getAttribute("uri");
						String nomeFileUnioneOmissis = lFileUnioneOmissis.getAttribute("nomeFile");
						InfoFileRecord infoFileUnioneOmissis = lFileUnioneOmissis.getAttributeAsRecord("infoFile") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(lFileUnioneOmissis.getAttributeAsRecord("infoFile").getJsObj())) : null;													
						new PreviewWindowWithCallback(uriFileUnioneOmissis, true, infoFileUnioneOmissis, "FileToExtractBean", nomeFileUnioneOmissis, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record recordPreview) {	
								afterPreviewFirmaFileVersPubblAggiornata(files, callback);			
							}
						});
					} else if(lFileUnione != null) {
						String uriFileUnione = lFileUnione.getAttribute("uri");
						String nomeFileUnione = lFileUnione.getAttribute("nomeFile");
						InfoFileRecord infoFileUnione = lFileUnione.getAttributeAsRecord("infoFile") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(lFileUnione.getAttributeAsRecord("infoFile").getJsObj())) : null;						
						new PreviewWindowWithCallback(uriFileUnione, true, infoFileUnione, "FileToExtractBean", nomeFileUnione, new ServiceCallback<Record>() {
							
							@Override
							public void execute(Record recordPreview) {
								afterPreviewFirmaFileVersPubblAggiornata(files, callback);			
							}
						});		
					} else {
						afterPreviewFirmaFileVersPubblAggiornata(files, callback);
					}					
				}
			}
		});	
	}		
	
	protected void afterPreviewFirmaFileVersPubblAggiornata(Record[] files, final ServiceCallback<Record> callback) {
		// Leggo gli eventuali parametri per forzare il tipo d firma
		String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
		String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
		FirmaUtility.firmaMultipla(appletTipoFirmaAtti, hsmTipoFirmaAtti, files, new FirmaMultiplaCallback() {
			@Override
			public void execute(Map<String, Record> signedFiles, Record[] filesAndUd) {
				Record lRecord = new Record();
				lRecord.setAttribute("protocolloOriginale", getRecordToSave());
				Record lRecordFileFirmati = new Record();
				lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[]{}));
				lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
				aggiornaFileFirmati(lRecord,  callback);				
			}
		});
	}
	
	protected void aggiornaFile(Record record, final ServiceCallback<Record> callback) {
		if(hasActionFirma()) {
			aggiornaFileFirmati(record,  callback);
		} else if(callback != null) {
			callback.execute(getRecordToSave());			
		}		
	}
	
	protected void aggiornaFileFirmati(Record record, final ServiceCallback<Record> callback) {
		aggiornaFileFirmati(record, null, callback);
	}

	protected void aggiornaFileFirmati(Record record, final Record lFileUnioneOmissisNonFirmato, final ServiceCallback<Record> callback) {
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.addParam("idTaskCorrente", getIdTaskCorrente());
		lNuovaPropostaAtto2CompletaDataSource.executecustom("aggiornaFileFirmati", record, new DSCallback() {
		
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					if(lFileUnioneOmissisNonFirmato != null) {
						// se il file unione omissis è stato escluso dalla firma lo devo comunque salvare
						InfoFileRecord infoFileUnioneOmissis = lFileUnioneOmissisNonFirmato.getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(lFileUnioneOmissisNonFirmato.getAttributeAsRecord("infoFile")) : null;
						lRecord.setAttribute("nomeFilePrimarioOmissis", lFileUnioneOmissisNonFirmato.getAttribute("nomeFile"));
						lRecord.setAttribute("uriFilePrimarioOmissis", lFileUnioneOmissisNonFirmato.getAttribute("uri"));
						lRecord.setAttribute("remoteUriFilePrimarioOmissis", false);
						InfoFileRecord precInfoFileOmissis = lRecord.getAttributeAsRecord("infoFilePrimarioOmissis") != null ? new InfoFileRecord(lRecord.getAttributeAsRecord("infoFilePrimarioOmissis")) : null;
						String precImprontaOmissis = precInfoFileOmissis != null ? precInfoFileOmissis.getImpronta() : null;
						lRecord.setAttribute("infoFilePrimarioOmissis", infoFileUnioneOmissis);
						if (precImprontaOmissis == null || !precImprontaOmissis.equals(infoFileUnioneOmissis.getImpronta())) {
							lRecord.setAttribute("isChangedFilePrimarioOmissis", true);
						}
					}
					editRecord(lRecord);
					// dopo l'editRecord devo risettare il mode del dettaglio, perchè altrimenti sulle replicableItem compaiono i bottoni di remove delle righe anche quando non dovrebbero
					if (isEseguibile()) {
						if(isSoloPreparazioneVersPubblicazione()) {
							soloPreparazioneVersPubblicazioneMode();
						} else if (isReadOnly()) {
							readOnlyMode();
						} else {
							editMode();
						}
					} else {
						viewMode();
					}
					if(callback != null) {
						callback.execute(lRecord);
					}
				}
			}
		});
	}
	
	protected void pubblica() {
		Record lRecordPubblica = getRecordToSave();
		// Nel caso di determina con spesa, se sono nel task di firma del visto contabile devo passare il file allegato generato da modello e firmato in quel task, per poterlo pubblicare come allegato
		if(isDeterminaConSpesa() && tipoEventoSIB != null && "visto".equals(tipoEventoSIB)) {
//			if(listaRecordModelli != null && listaRecordModelli.getLength() > 0) {
//				for (int i = 0; i < listaRecordModelli.getLength(); i++) {
//					final String idTipoModello = listaRecordModelli.get(i).getAttribute("idTipoDoc");
//					RecordList listaAllegati = allegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
//					int posModello = getPosModelloFromTipo(idTipoModello, listaAllegati);
//					if (posModello != -1) {						
//						lRecordPubblica.setAttribute("allegatoVistoContabile", listaAllegati.get(posModello));
//					}
//				}
//			}
			if(allegatoGeneratoDaModelloTask != null) {
				lRecordPubblica.setAttribute("allegatoVistoContabile", allegatoGeneratoDaModelloTask);
			}	
		}
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		Layout.showWaitPopup("Pubblicazione all'Albo Pretorio in corso...");				
		lNuovaPropostaAtto2CompletaDataSource.executecustom("pubblica", lRecordPubblica, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					callbackSalvaDati(lRecord);
				} else {
					// Se va in errore l'invio in pubblicazione ricarico il dettaglio del task: in questo modo 
					// la volta successiva viene abilitata solo la pubblicazione e non di nuovo l'unione e la firma
					reload();
				}												
			}
		});		
	}
	
	public void generaFileUnione(final ServiceCallback<Record> callback) {
		String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;	
		String nomeFileUnione = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null;
		String nomeFileUnioneOmissis = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFileOmissis") : null;
		Record impostazioniUnioneFile = recordEvento != null ? recordEvento.getAttributeAsRecord("impostazioniUnioneFile") : null;
		generaFileUnione(esito, nomeFileUnione, nomeFileUnioneOmissis, impostazioniUnioneFile, callback);
	}
	
	public void anteprimaFileUnioneVersIntegrale(final ServiceCallback<Record> callback) {
		String nomeFileUnione = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null;
		Record impostazioniUnioneFile = recordEvento != null ? recordEvento.getAttributeAsRecord("impostazioniUnioneFile") : null;
		anteprimaFileUnioneVersIntegrale(nomeFileUnione, impostazioniUnioneFile, callback);
	}
	
	public void anteprimaFileUnioneVersXPubbl(final ServiceCallback<Record> callback) {
		String nomeFileUnioneOmissis = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFileOmissis") : null;
		Record impostazioniUnioneFile = recordEvento != null ? recordEvento.getAttributeAsRecord("impostazioniUnioneFile") : null;
		anteprimaFileUnioneVersXPubbl(nomeFileUnioneOmissis, impostazioniUnioneFile, callback);
	}
	
	public void unioneFileAndReturn() {		
		generaFileUnione(new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordUnioneFile) {
				if (recordEvento != null && recordEvento.getAttribute("exportAttoInDocFmt") != null && "true".equalsIgnoreCase(recordEvento.getAttribute("exportAttoInDocFmt"))) {
					hiddenForm.setValue("uriDocGeneratoFormatoOdt", recordUnioneFile.getAttribute("uriFileOdtGenerato"));
				} else {
					hiddenForm.setValue("uriDocGeneratoFormatoOdt", "");
				}
				previewFileUnioneWithFirmaAndCallback(recordUnioneFile, new ServiceCallback<Record>() {
					
					@Override
					public void execute(final Record recordUnioneFileAfterFirma) {
						final String esito = attrEsito != null ? attrEsito.getAttribute("valore") : null;	
						final RecordList listaRecordModelliXEsito = getListaRecordModelliXEsito(esito);									
						if (listaRecordModelliXEsito != null) {
							salvaAttributiDinamiciDocAfterSalva(false, rowidDoc, activityName, esito, new ServiceCallback<Record>() {

								@Override
								public void execute(Record object) {
									saveAndGoWithListaModelliGenAutomatica(listaRecordModelliXEsito, esito, new ServiceCallback<Record>() {

										@Override
										public void execute(Record recordModello) {
											salvaDati(false, new ServiceCallback<Record>() {

												@Override
												public void execute(Record object) {								
													if(hasActionPubblica()) {
														pubblica();
													} else {
														callbackSalvaDati(object);
													}
												}
											});	
										}
									});
								}
							});
						} else {
							salvaDati(false, new ServiceCallback<Record>() {

								@Override
								public void execute(Record object) {								
									if(hasActionPubblica()) {
										pubblica();
									} else {
										callbackSalvaDati(object);
									}
								}
							});		
						}									
					}
				});
			}
		});
	}
	
	public void previewFileUnioneWithFirmaAndCallback(final Record record, final ServiceCallback<Record> callback) {			
		final String uriFileUnione = record.getAttribute("uriVersIntegrale");	
		final String nomeFileUnione = record.getAttribute("nomeFileVersIntegrale");		
		final InfoFileRecord infoFileUnione = record.getAttributeAsRecord("infoFileVersIntegrale") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFileVersIntegrale").getJsObj())) : null;
		final Record lFileUnione = (uriFileUnione != null && !"".equals(uriFileUnione)) ? new Record() : null;
		if(lFileUnione != null) {			
			lFileUnione.setAttribute("idFile", "primario" + uriFileUnione);
			lFileUnione.setAttribute("uri", uriFileUnione);
			lFileUnione.setAttribute("nomeFile", nomeFileUnione);
			lFileUnione.setAttribute("infoFile", infoFileUnione);
		}
		final String uriFileUnioneOmissis = record.getAttribute("uri");	
		final String nomeFileUnioneOmissis = record.getAttribute("nomeFile");		
		final InfoFileRecord infoFileUnioneOmissis = record.getAttributeAsRecord("infoFile") != null ? InfoFileRecord.buildInfoFileString(JSON.encode(record.getAttributeAsRecord("infoFile").getJsObj())) : null;				
		final Record lFileUnioneOmissis = (uriFileUnioneOmissis != null && !"".equals(uriFileUnioneOmissis)) ? new Record() : null;
		if(lFileUnioneOmissis != null) {
			lFileUnioneOmissis.setAttribute("idFile", "primarioOmissis" + uriFileUnioneOmissis);
			lFileUnioneOmissis.setAttribute("uri", uriFileUnioneOmissis);
			lFileUnioneOmissis.setAttribute("nomeFile", nomeFileUnioneOmissis);
			lFileUnioneOmissis.setAttribute("infoFile", infoFileUnioneOmissis);
		}		
		PreviewWindowWithCallback lPreviewWindowWithCallback = new PreviewWindowWithCallback(uriFileUnione, false, infoFileUnione, "FileToExtractBean",	nomeFileUnione, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordPreview) {		
				if(lFileUnioneOmissis != null) {
					PreviewWindowWithCallback lPreviewWindowWithCallbackOmissis = new PreviewWindowWithCallback(uriFileUnioneOmissis, false, infoFileUnioneOmissis, "FileToExtractBean", nomeFileUnioneOmissis, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record recordPreview) {		
							afterPreviewFileUnioneWithFirma(lFileUnione, lFileUnioneOmissis, callback);							
						}
					}) {
						
						@Override
						public boolean hideAnnullaButton() {
							return true;
						}
					};		
				} else {
					afterPreviewFileUnioneWithFirma(lFileUnione, null, callback);
				}
			}
		}) {
			
			@Override
			public boolean hideAnnullaButton() {
				return true;
			}
		};		
	}
	
	public void afterPreviewFileUnioneWithFirma(Record lFileUnione, Record lFileUnioneOmissis, final ServiceCallback<Record> callback) {
		if (hasActionFirma()) {	
			final List<Record> listaFiles = new ArrayList<Record>();
			if(lFileUnione != null) {
				listaFiles.add(lFileUnione);
			}
			boolean skipOmissisInFirmaAdozioneAtto = AurigaLayout.getParametroDBAsBoolean("ESCLUDI_FIRMA_OMISSIS_IN_ADOZ_ATTO");
			if(!skipOmissisInFirmaAdozioneAtto && lFileUnioneOmissis != null) {
				listaFiles.add(lFileUnioneOmissis);
			}	
			final Record lFileUnioneOmissisNonFirmato = skipOmissisInFirmaAdozioneAtto ? lFileUnioneOmissis : null;			
			getFileAllegatiDaFirmareWithFileUnione(new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecordFileAllegati = response.getData()[0];
						Record[] filesAllegati = lRecordFileAllegati.getAttributeAsRecordArray("files");
						for(int i = 0; i < filesAllegati.length; i++) {
							listaFiles.add(filesAllegati[i]);	
						}
						// Leggo gli eventuali parametri per forzare il tipo d firma
						String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
						String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
						Record[] files = listaFiles.toArray(new Record[listaFiles.size()]);
						FirmaUtility.firmaMultipla(appletTipoFirmaAtti, hsmTipoFirmaAtti, files, new FirmaMultiplaCallback() {
							@Override
							public void execute(Map<String, Record> signedFiles, Record[] filesAndUd) {
								final Record lRecord = new Record();
								lRecord.setAttribute("protocolloOriginale", getRecordToSave());
								Record lRecordFileFirmati = new Record();
								lRecordFileFirmati.setAttribute("files", signedFiles.values().toArray(new Record[]{}));
								lRecord.setAttribute("fileFirmati", lRecordFileFirmati);								
								aggiornaFileFirmati(lRecord, lFileUnioneOmissisNonFirmato, callback);								
							}
						});
					}
				}
			});	
		} else {
			if(lFileUnione != null) {				
				InfoFileRecord infoFileUnione = lFileUnione.getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(lFileUnione.getAttributeAsRecord("infoFile")) : null;
				hiddenForm.setValue("nomeFilePrimario", lFileUnione.getAttribute("nomeFile"));
				hiddenForm.setValue("uriFilePrimario", lFileUnione.getAttribute("uri"));
				hiddenForm.setValue("remoteUriFilePrimario", false);
				InfoFileRecord precInfoFile = hiddenForm.getValue("infoFilePrimario") != null ? new InfoFileRecord(hiddenForm.getValue("infoFilePrimario")) : null;
				String precImpronta = precInfoFile != null ? precInfoFile.getImpronta() : null;
				hiddenForm.setValue("infoFilePrimario", infoFileUnione);
				if (precImpronta == null || !precImpronta.equals(infoFileUnione.getImpronta())) {
					hiddenForm.setValue("isChangedFilePrimario", true);
				}
				if (infoFileUnione.isFirmato() && !infoFileUnione.isFirmaValida()) {
					GWTRestDataSource.printMessage(new MessageBean("Il file primario presenta una firma non valida alla data odierna", "", MessageType.WARNING));
				}
			}
			if(lFileUnioneOmissis != null) {
				InfoFileRecord infoFileUnioneOmissis = lFileUnioneOmissis.getAttributeAsRecord("infoFile") != null ? new InfoFileRecord(lFileUnioneOmissis.getAttributeAsRecord("infoFile")) : null;
				hiddenForm.setValue("nomeFilePrimarioOmissis", lFileUnioneOmissis.getAttribute("nomeFile"));
				hiddenForm.setValue("uriFilePrimarioOmissis", lFileUnioneOmissis.getAttribute("uri"));
				hiddenForm.setValue("remoteUriFilePrimarioOmissis", false);
				InfoFileRecord precInfoFileOmissis = hiddenForm.getValue("infoFilePrimarioOmissis") != null ? new InfoFileRecord(hiddenForm.getValue("infoFilePrimarioOmissis")) : null;
				String precImprontaOmissis = precInfoFileOmissis != null ? precInfoFileOmissis.getImpronta() : null;
				hiddenForm.setValue("infoFilePrimarioOmissis", infoFileUnioneOmissis);
				if (precImprontaOmissis == null || !precImprontaOmissis.equals(infoFileUnioneOmissis.getImpronta())) {
					hiddenForm.setValue("isChangedFilePrimarioOmissis", true);
				}
				if (infoFileUnioneOmissis.isFirmato() && !infoFileUnioneOmissis.isFirmaValida()) {
					GWTRestDataSource.printMessage(new MessageBean("Il file primario (vers. con omissis) presenta una firma non valida alla data odierna", "", MessageType.WARNING));
				}
			}
			if(callback != null) {
				callback.execute(getRecordToSave());
			}
		}
	}
	
	public void convertiFileNonFirmato(Record pFileNonFirmato, final ServiceCallback<Record> callback) {
		if (pFileNonFirmato != null && AurigaLayout.getParametroDBAsBoolean("CONV_PDF_PRE_FIRMA")) {
			Record lRecordFiles = new Record();
			RecordList files = new RecordList();
			files.add(pFileNonFirmato);
			lRecordFiles.setAttribute("files", files);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("ConversionePdfDataSource");
			lGwtRestService.addParam("SCOPO", "FIRMA");
			// Eseguo la chiamata al datasource
			lGwtRestService.call(lRecordFiles, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					Record lFileNonFirmatoPdf = object.getAttributeAsRecordList("files").get(0);
					if(callback != null) {
						callback.execute(lFileNonFirmatoPdf);
					}
				}
			});
		} else {
			if(callback != null) {
				callback.execute(pFileNonFirmato);
			}
		}
	}
	
	public void aggiornaVersDaPubblicare() {
		
		final String nomeFilePrimario = getValueAsString("nomeFilePrimario");
		final String uriFilePrimario = getValueAsString("uriFilePrimario");
		final InfoFileRecord infoFilePrimario = hiddenForm.getValue("infoFilePrimario") != null ? new InfoFileRecord(hiddenForm.getValue("infoFilePrimario")) : null;
		
		final String nomeFilePrimarioOmissis = getValueAsString("nomeFilePrimarioOmissis");
		final String uriFilePrimarioOmissis = getValueAsString("uriFilePrimarioOmissis");
		final InfoFileRecord infoFilePrimarioOmissis = hiddenForm.getValue("infoFilePrimarioOmissis") != null ? new InfoFileRecord(hiddenForm.getValue("infoFilePrimarioOmissis")) : null;
		
		PreviewWindowPageSelectionCallback lWindowPageSelectionCallback = new PreviewWindowPageSelectionCallback() {
			
			@Override
			public void executeSalvaVersConOmissis(Record record) {
				
			}
			
			@Override
			public void executeSalva(Record record) {				
				String uri = record.getAttribute("uri");
				InfoFileRecord info = new InfoFileRecord(record.getAttributeAsRecord("infoFile"));
				InfoFileRecord precInfo = null;
				if(uriFilePrimarioOmissis != null && !"".equals(uriFilePrimarioOmissis)) {
					precInfo = infoFilePrimarioOmissis;
				} else {
					precInfo = infoFilePrimario;
				}
				String precImpronta = precInfo != null ? precInfo.getImpronta() : null;
				hiddenForm.setValue("infoFilePrimarioOmissis", info);
				if (precImpronta == null || !precImpronta.equals(info.getImpronta())) {
					hiddenForm.setValue("isChangedFilePrimarioOmissis", true);					
				}
				hiddenForm.setValue("nomeFilePrimarioOmissis", "ATTO_COMPLETO_VERS_DA_PUBBLICARE.pdf");
				hiddenForm.setValue("uriFilePrimarioOmissis", uri);
				hiddenForm.setValue("remoteUriFilePrimarioOmissis", false);				
			}
			
			@Override
			public void executeOnError() {	
				
			}
			
		};
 
		if(uriFilePrimarioOmissis != null && !"".equals(uriFilePrimarioOmissis)) {
			PreviewControl.switchPreview(uriFilePrimarioOmissis, true, infoFilePrimarioOmissis, "FileToExtractBean", nomeFilePrimarioOmissis, lWindowPageSelectionCallback, false, false);			
		} else {
			PreviewControl.switchPreview(uriFilePrimario, true, infoFilePrimario, "FileToExtractBean", nomeFilePrimario, lWindowPageSelectionCallback, false, false);			
		}
	}
	
	public void riportaVersConOmissisAIntegrale() {
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("getVersIntegraleSenzaFirma", recordFromLoadDett, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {

				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = dsResponse.getData()[0];
					if (lRecord != null) {
						String nomeFile = lRecord.getAttribute("nomeFile");
						String estensione = nomeFile != null ? nomeFile.substring(nomeFile.lastIndexOf(".") + 1) : null;			
						Record infofile = lRecord.getAttributeAsRecord("infoFile");
						String uriFile = lRecord.getAttributeAsString("uri");
						hiddenForm.setValue("infoFilePrimarioOmissis", infofile);
						hiddenForm.setValue("isChangedFilePrimarioOmissis", true);							
						hiddenForm.setValue("nomeFilePrimarioOmissis", "ATTO_COMPLETO_VERS_DA_PUBBLICARE." + estensione);
						hiddenForm.setValue("uriFilePrimarioOmissis", uriFile);
						hiddenForm.setValue("remoteUriFilePrimarioOmissis", true);	
						Layout.addMessage(new MessageBean("La vers. con omissis da pubblicare è stata riportata alla versione integrale", "", MessageType.INFO));
					}
				}
			}
		});		
	}
	
	public void riportaVersConOmissisALastVersPubblicazioneFirmata() {
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("getLastVersPubblicazioneFirmata", recordFromLoadDett, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {

				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = dsResponse.getData()[0];
					if (lRecord != null) {
						String nomeFile = lRecord.getAttribute("nomeFile");
						String estensione = nomeFile != null ? nomeFile.substring(nomeFile.lastIndexOf(".") + 1) : null;			
						Record infofile = lRecord.getAttributeAsRecord("infoFile");
						String uriFile = lRecord.getAttributeAsString("uri");
						hiddenForm.setValue("infoFilePrimarioOmissis", infofile);
						hiddenForm.setValue("isChangedFilePrimarioOmissis", true);							
						hiddenForm.setValue("nomeFilePrimarioOmissis", "ATTO_COMPLETO_VERS_DA_PUBBLICARE." + estensione);
						hiddenForm.setValue("uriFilePrimarioOmissis", uriFile);
						hiddenForm.setValue("remoteUriFilePrimarioOmissis", true);	
					}
				}
			}
		});
	}

	protected void callbackSalvaDati(Record object) {
		
		idEvento = object.getAttribute("idEvento");
		
		final Record lRecordSalvaTask = new Record();
		lRecordSalvaTask.setAttribute("instanceId", instanceId);
		lRecordSalvaTask.setAttribute("activityName", activityName);
		lRecordSalvaTask.setAttribute("idProcess", idProcess);
		lRecordSalvaTask.setAttribute("idEventType", idTipoEvento);
		lRecordSalvaTask.setAttribute("idEvento", idEvento);
		lRecordSalvaTask.setAttribute("idUd", idUd);
		lRecordSalvaTask.setAttribute("note", messaggio);

		boolean invioNotEmailFlgConfermaInvio = recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgConfermaInvio");		
		boolean flgCallXDettagliMail = recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgCallXDettagliMail");
				
		if (hasActionInvioNotEmail() && flgCallXDettagliMail) {
			//chiamo la store del dettaglio mail, poi se ho il flgConfermaInvio apro la popup e infine chiamo salvatask
			getDatiXInvioNotEmail(new DSCallback() {
				
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
						recordEvento = dsResponse.getData()[0];
						if (recordEvento != null && recordEvento.getAttributeAsBoolean("flgInvioNotEmail")) {
							attrEsitoNotEmail = null;

							if ( recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgConfermaInvio")) {

								invioNotEmail(new BooleanCallback() {

									@Override
									public void execute(Boolean value) {

										if(value) {
											GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
											lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

												@Override
												public void execute(DSResponse response, Object rawData, DSRequest request) {
													if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
														dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

															@Override
															public void execute(Record record) {
																Layout.hideWaitPopup();
																Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
																next();
															}
														});
													} else {
														reload();
													}
												}
											});
										} 
//										else {
//											reload();
//										}
									}				
								});
							} else {
								lRecordSalvaTask.setAttribute("invioNotEmailSubject", recordEvento != null ? recordEvento.getAttribute("invioNotEmailSubject") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailBody", recordEvento != null ? recordEvento.getAttribute("invioNotEmailBody") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailDestinatari", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatari") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCC", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCC") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCCN", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCCN") : null);						
								lRecordSalvaTask.setAttribute("invioNotEmailIdCasellaMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIdCasellaMittente") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIndirizzoMittente") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailAliasIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailAliasIndirizzoMittente") : null);
								lRecordSalvaTask.setAttribute("invioNotEmailFlgPEC", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgPEC") : null);				
								lRecordSalvaTask.setAttribute("invioNotEmailFlgInvioMailXComplTask", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgInvioMailXComplTask") : null);				
//								lRecordSalvaTask.setAttribute("invioNotEmailFlgCallXDettagliMail", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgCallXDettagliMail") : null);				
								lRecordSalvaTask.setAttribute("invioNotEmailAttachment", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") : null);				
								
								GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");		
								lAurigaTaskDataSource.addParam("flgInvioNotEmail", "true");
												
								lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

												@Override
												public void execute(Record record) {
													Layout.hideWaitPopup();
													Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
													next();
												}
											});
										} else {
											reload();
										}
									}
								});
							}
						} else {
							
							GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");													
							lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

											@Override
											public void execute(Record record) {
												Layout.hideWaitPopup();
												Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
												next();
											}
										});
									} else {
										reload();
									}
								}
							});
						}
					}
				}
			}, false);

		} else if (hasActionInvioNotEmail() && invioNotEmailFlgConfermaInvio) {
			// se ho azione invio notifica e ho il flgconferma (ma non flgCallXDettagliMail) apro la popup
			// con i dati della call execatt
			
			attrEsitoNotEmail = null;
			getDatiXInvioNotEmail(new DSCallback() {

				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
						invioNotEmail(new BooleanCallback() {

							@Override
							public void execute(Boolean value) {

								if(value) {
									GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
									lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

													@Override
													public void execute(Record record) {
														Layout.hideWaitPopup();
														Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
														next();
													}
												});
											} else {
												reload();
											}
										}
									});
								}
//								else {
//									reload();
//								}
							}
						});
					}
				}
			}, true);

		} else {
						
			lRecordSalvaTask.setAttribute("invioNotEmailSubject", recordEvento != null ? recordEvento.getAttribute("invioNotEmailSubject") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailBody", recordEvento != null ? recordEvento.getAttribute("invioNotEmailBody") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailDestinatari", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatari") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCC", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCC") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailDestinatariCCN", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCCN") : null);						
			lRecordSalvaTask.setAttribute("invioNotEmailIdCasellaMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIdCasellaMittente") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIndirizzoMittente") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailAliasIndirizzoMittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailAliasIndirizzoMittente") : null);
			lRecordSalvaTask.setAttribute("invioNotEmailFlgPEC", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgPEC") : null);				
			lRecordSalvaTask.setAttribute("invioNotEmailFlgInvioMailXComplTask", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgInvioMailXComplTask") : null);				
//			lRecordSalvaTask.setAttribute("invioNotEmailFlgCallXDettagliMail", recordEvento != null ? recordEvento.getAttributeAsBoolean("invioNotEmailFlgCallXDettagliMail") : null);				
			lRecordSalvaTask.setAttribute("invioNotEmailAttachment", recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") : null);				
			
			GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");		
			if(hasActionInvioNotEmail() /*&& isEsitoTaskAzioni(attrEsitoNotEmail)*/) {
				lAurigaTaskDataSource.addParam("flgInvioNotEmail", "true");
				attrEsitoNotEmail = null;
			}					
			lAurigaTaskDataSource.executecustom("salvaTask", lRecordSalvaTask, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

							@Override
							public void execute(Record record) {
								Layout.hideWaitPopup();
								Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
								next();
							}
						});
					} else {
						reload();
					}
				}
			});
		}
			
	}
	
	protected void invioNotEmail(final BooleanCallback callback) {

		final boolean invioNotEmailFlgInvioMailXComplTask = recordEvento != null && recordEvento.getAttributeAsBoolean("invioNotEmailFlgInvioMailXComplTask");		

		DSCallback sendCallback = new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if(callback != null) {
						callback.execute(true);
					}
				} else {
					if(callback != null) {
						callback.execute(invioNotEmailFlgInvioMailXComplTask ? false : true);
					}
				}
			}				
		};
		final NuovoMessaggioWindow lNuovoMessaggioWindow = new NuovoMessaggioWindow("nuovo_messaggio","invioNuovoMessaggio", instance, sendCallback) {
			
			@Override
			public boolean isHideSalvaBozzaButton() {
				return true;
			}
			
			@Override
			public boolean getDefaultSaveSentEmail() {
				return true; // forzo il valore di default del check salvaInviati a true
			}
			
			@Override
			public Record getInitialRecordNuovoMessaggio() {
				Record lRecord = new Record();
				lRecord.setAttribute("oggetto", recordEvento != null ? recordEvento.getAttribute("invioNotEmailSubject") : null);
				lRecord.setAttribute("bodyHtml", recordEvento != null ? recordEvento.getAttribute("invioNotEmailBody") : null);				
				RecordList invioNotEmailDestinatari = recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatari") : null;
				if(invioNotEmailDestinatari != null && invioNotEmailDestinatari.getLength() > 0) {
					String destinatari = null;
					for(int i = 0; i < invioNotEmailDestinatari.getLength(); i++) {				
						if(destinatari == null) {
							destinatari = invioNotEmailDestinatari.get(i).getAttribute("value");
						} else {
							destinatari += "; " + invioNotEmailDestinatari.get(i).getAttribute("value");
						}
					}	
					lRecord.setAttribute("destinatari", destinatari);
				}				
				RecordList invioNotEmailDestinatariCC = recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCC") : null;
				if(invioNotEmailDestinatariCC != null && invioNotEmailDestinatariCC.getLength() > 0) {
					String destinatariCC = null;
					for(int i = 0; i < invioNotEmailDestinatariCC.getLength(); i++) {				
						if(destinatariCC == null) {
							destinatariCC = invioNotEmailDestinatariCC.get(i).getAttribute("value");
						} else {
							destinatariCC += "; " + invioNotEmailDestinatariCC.get(i).getAttribute("value");
						}
					}	
					lRecord.setAttribute("destinatariCC", destinatariCC);
				}				
				RecordList invioNotEmailDestinatariCCN = recordEvento != null ? recordEvento.getAttributeAsRecordList("invioNotEmailDestinatariCCN") : null;
				if(invioNotEmailDestinatariCCN != null && invioNotEmailDestinatariCCN.getLength() > 0) {
					String destinatariCCN = null;
					for(int i = 0; i < invioNotEmailDestinatariCCN.getLength(); i++) {				
						if(destinatariCCN == null) {
							destinatariCCN = invioNotEmailDestinatariCCN.get(i).getAttribute("value");
						} else {
							destinatariCCN += "; " + invioNotEmailDestinatariCCN.get(i).getAttribute("value");
						}
					}	
					lRecord.setAttribute("destinatariCCN", destinatariCCN);
				}				
				lRecord.setAttribute("mittente", recordEvento != null ? recordEvento.getAttribute("invioNotEmailIndirizzoMittente") : null);
				
				//TODO FEDERICA BUONO invioNotEmailAttachment
				RecordList files = recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") != null ? recordEvento.getAttributeAsRecordList("invioNotEmailAttachment") : new RecordList();
//				String idUd = null;
//				for (Record file : files) {
//					if(idUd == null) {
//						idUd = file.getAttribute("idUd");
//					} else {
//						idUd += ";" + file.getAttribute("idUd");
//					}
//				}
//				lRecord.setAttribute("idUD", idUd); // TODO idUd da collegare alla mail
				RecordList attachList = new RecordList();
				for (int i = 0; i < files.getLength(); i++) {
					Record attach = new Record();
					attach.setAttribute("fileNameAttach", files.get(i).getAttribute("nomeFile"));
					attach.setAttribute("infoFileAttach", files.get(i).getAttributeAsRecord("infoFile"));
					attach.setAttribute("uriAttach", files.get(i).getAttribute("uri"));
					attachList.add(attach);
				}
				lRecord.setAttribute("attach", attachList);

				return lRecord;
			};
			
			@Override
			public void manageOnCloseClick() {
				if(invioNotEmailFlgInvioMailXComplTask) {
					SC.warn("Se chiudi la finestra di invio mail senza effettuare l'invio il passo dell'iter non verrà  completato. Confermi di voler chiudere?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								_window.markForDestroy();
								if(callback != null) {
									callback.execute(false);
								}
							}
						}
					});
				} else {
					SC.warn("Se chiudi la finestra di invio mail senza effettuare l'invio il passo dell'iter verrà  completato ugualmente. Confermi di voler chiudere?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if(value) {
								_window.markForDestroy();
								if(callback != null) {
									callback.execute(true);
								}
							}
						}
					});
				}			
			}
		};

	}

	protected void getDatiXInvioNotEmail(final DSCallback callback, boolean prepareAttach) {
		
		GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");	
		lAurigaTaskDataSource.addParam("prepareAttach", String.valueOf(prepareAttach));
		lAurigaTaskDataSource.executecustom("getDatiXInvioNotifMail", recordEvento, callback);
	}
	
	@Override
	public void caricaModelloInAllegati(String idModello, String tipoModello, final String flgConvertiInPdf, final ServiceCallback<Record> callback) {
		
		Record lRecordCaricamentoModello = new Record();
		lRecordCaricamentoModello.setAttribute("idModello", idModello);
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ModelliDocDatasource", "idModello", FieldType.TEXT);
		lGwtRestDataSource.getData(lRecordCaricamentoModello, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecordModello = dsResponse.getData()[0];							
					Record lRecordCompilaModello = new Record();
					lRecordCompilaModello.setAttribute("processId", idProcess);
					lRecordCompilaModello.setAttribute("idUd", idUd);
					lRecordCompilaModello.setAttribute("idModello", lRecordModello.getAttribute("idModello"));
					lRecordCompilaModello.setAttribute("nomeModello", lRecordModello.getAttribute("nomeModello"));
					lRecordCompilaModello.setAttribute("tipoModello", lRecordModello.getAttribute("tipoModello"));
					lRecordCompilaModello.setAttribute("dettaglioBean", getRecordToSave());
					final String nomeFileModello = lRecordModello.getAttribute("nomeModello") + ".pdf";					
					final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
					lNuovaPropostaAtto2CompletaDataSource.executecustom("compilazioneAutomaticaModelloPdf", lRecordCompilaModello, new DSCallback() {
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								final Record result = response.getData()[0];
								Record fileToUpload = new Record();								
								fileToUpload.setAttribute("nomeFilePrimario", nomeFileModello);
								fileToUpload.setAttribute("uriFilePrimario", result.getAttribute("uri"));
								fileToUpload.setAttribute("infoFile", result.getAttributeAsRecord("infoFile"));
								callback.execute(fileToUpload);								
							}
						}
					});										
				}
			}
		});
	}

	public void saveAndGoWithListaModelliGenAutomatica(RecordList listaRecordModelli, String esito, ServiceCallback<Record> callback) {
		compilazioneAutomaticaListaModelliPdf(listaRecordModelli, esito, callback);
	}
	
	/**
	 * <ul>
	 * <li>Carica il modello specificato</li>
	 * <li>inietta i valori</li>
	 * <li>genera la versione pdf</li>
	 * <li>se richiesto, il file viene firmato digitalmente</li>
	 * <li>viene aggiunto agli allegati</li>
	 * </ul>
	 * 
	 * @param callback
	 */
	public void compilazioneAutomaticaListaModelliPdf(final RecordList listaRecordModelli, final String esito, final ServiceCallback<Record> callback) {

		if (listaRecordModelli != null && listaRecordModelli.getLength() > 0) {

			Record lRecordCompilaModello = new Record();
			lRecordCompilaModello.setAttribute("processId", idProcess);
			lRecordCompilaModello.setAttribute("idUd", idUd);
			lRecordCompilaModello.setAttribute("listaRecordModelli", listaRecordModelli);
			lRecordCompilaModello.setAttribute("dettaglioBean", getRecordToSave());
			
			final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
			lNuovaPropostaAtto2CompletaDataSource.addParam("esitoTask", esito);
			lNuovaPropostaAtto2CompletaDataSource.executecustom("compilazioneAutomaticaListaModelliPdf", lRecordCompilaModello, new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						
						RecordList listaRecordModelliGenerati = response.getData()[0].getAttributeAsRecordList("listaRecordModelli");
						
						if(listaRecordModelliGenerati != null && listaRecordModelliGenerati.getLength() > 0) {
							previewFileGenerati(0, listaRecordModelliGenerati, new ServiceCallback<RecordList>() {
								
								@Override
								public void execute(RecordList listaRecordModelliGenerati) {
									firmaAggiornaFileGenerati(listaRecordModelliGenerati, callback);
								}
							});
						}						
					}
				}
			});
		}
	}
	
	protected void previewFileGenerati(final int i, final RecordList listaRecordModelliGenerati, final ServiceCallback<RecordList> callback) {		
		
		if(i >= 0 && listaRecordModelliGenerati != null && listaRecordModelliGenerati.getLength() > 0 && i < listaRecordModelliGenerati.getLength()) {				
		
			Record recordModello = listaRecordModelliGenerati.get(i);
			
			final String uriFileGenerato = recordModello.getAttribute("uriFileGenerato");
			final InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(recordModello.getAttributeAsRecord("infoFileGenerato").getJsObj()));
			final String nomeFileModello = recordModello.getAttribute("nomeFile") + ".pdf";
			
			boolean flgSkipAnteprima = recordModello.getAttributeAsBoolean("flgSkipAnteprima") != null && recordModello.getAttributeAsBoolean("flgSkipAnteprima");						
			if(!flgSkipAnteprima) {
				new PreviewWindowWithCallback(uriFileGenerato, false, infoFileGenerato, "FileToExtractBean",	nomeFileModello, new ServiceCallback<Record>() {
	
					@Override
					public void execute(Record object) {									
						previewFileGenerati(i + 1, listaRecordModelliGenerati, callback);
					}
				});
			} else {
				previewFileGenerati(i + 1, listaRecordModelliGenerati, callback);
			}
		
		} else if(callback != null) {
			callback.execute(listaRecordModelliGenerati);
		}
	}
	
	protected void firmaAggiornaFileGenerati(final RecordList listaRecordModelliGenerati, final ServiceCallback<Record> callback) {
		
		RecordList listaFilesDaFirmare = new RecordList();
		for(int i = 0; i < listaRecordModelliGenerati.getLength(); i++) {
			
			String uriFileGenerato = listaRecordModelliGenerati.get(i).getAttribute("uriFileGenerato");
			InfoFileRecord infoFileGenerato = InfoFileRecord.buildInfoFileString(JSON.encode(listaRecordModelliGenerati.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj()));
			
			String nomeFileModello = listaRecordModelliGenerati.get(i).getAttribute("nomeFile") + ".pdf";
			String idTipoModello = listaRecordModelliGenerati.get(i).getAttribute("idTipoDoc");
			boolean flgDaFirmare = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare");
			
			if (flgDaFirmare) {
				Record record = new Record();
				record.setAttribute("idFile", idTipoModello); // uso questo come campo identificativo del record per poi riaggiornare l'altra lista
				record.setAttribute("uri", uriFileGenerato);
				record.setAttribute("infoFile", infoFileGenerato);
				record.setAttribute("nomeFile", nomeFileModello);
				listaFilesDaFirmare.add(record);				
			}		
		}
		
		if(listaFilesDaFirmare.getLength() > 0) {
			// Leggo gli eventuali parametri per forzare il tipo d firma
			String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
			String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");
			FirmaUtility.firmaMultipla(appletTipoFirmaAtti, hsmTipoFirmaAtti, listaFilesDaFirmare.toArray(), new FirmaMultiplaCallback() {

				@Override
				public void execute(Map<String, Record> signedFiles, Record[] filesAndUd) {
					
					aggiungiListaModelliAdAllegati(listaRecordModelliGenerati, signedFiles, callback);						
				}
			});	
		} else {
			aggiungiListaModelliAdAllegati(listaRecordModelliGenerati, null, callback);		
		}
		
	}
	
	protected void aggiungiListaModelliAdAllegati(RecordList listaRecordModelliGenerati, Map<String, Record> signedFiles, ServiceCallback<Record> callback) {
		if (allegatiForm != null) {
			
			RecordList listaAllegati = allegatiForm.getValuesAsRecord().getAttributeAsRecordList("listaAllegati");
		
			for(int i = 0; i < listaRecordModelliGenerati.getLength(); i++) {
				
				String descrizioneFileAllegato = listaRecordModelliGenerati.get(i).getAttribute("descrizione");
				String nomeFileAllegato = listaRecordModelliGenerati.get(i).getAttribute("nomeFile") + ".pdf";
				String uriFileAllegato = listaRecordModelliGenerati.get(i).getAttribute("uriFileGenerato");
				String infoFileAllegato = JSON.encode(listaRecordModelliGenerati.get(i).getAttributeAsRecord("infoFileGenerato").getJsObj());						
				InfoFileRecord infoAllegato = InfoFileRecord.buildInfoFileString(infoFileAllegato);						
				
				String idTipoModello = listaRecordModelliGenerati.get(i).getAttribute("idTipoDoc");
				String nomeTipoModello = listaRecordModelliGenerati.get(i).getAttribute("nomeTipoDoc");
				boolean flgDaFirmare = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgDaFirmare");
										
				if (flgDaFirmare && signedFiles != null) {							
					Record lRecordFileFirmato = signedFiles.get(idTipoModello);							
					nomeFileAllegato = lRecordFileFirmato.getAttribute("nomeFile");
					uriFileAllegato = lRecordFileFirmato.getAttribute("uri");
					infoAllegato = InfoFileRecord.buildInfoFileString(JSON.encode(lRecordFileFirmato.getAttributeAsRecord("infoFile").getJsObj()));
				}
				
				int posModello = getPosModelloFromTipo(idTipoModello, listaAllegati);
				
				Record lRecordModello = new Record();		
				if (posModello != -1) {
					lRecordModello = listaAllegati.get(posModello);
				}
				
				boolean flgParere = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParere") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParere");									
				boolean flgParteDispositivo = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParteDispositivo") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgParteDispositivo");									
				boolean flgNoPubbl = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgNoPubbl") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgNoPubbl");									
				boolean flgPubblicaSeparato = listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgPubblicaSeparato") != null && listaRecordModelliGenerati.get(i).getAttributeAsBoolean("flgPubblicaSeparato");									
				lRecordModello.setAttribute("flgParere", flgParere);
				if(flgParere) {
					lRecordModello.setAttribute("flgParteDispositivo", false);
					lRecordModello.setAttribute("flgNoPubblAllegato", flgNoPubbl);
					lRecordModello.setAttribute("flgPubblicaSeparato", true);
				} else {
					lRecordModello.setAttribute("flgParteDispositivo", flgParteDispositivo);
					if(!flgParteDispositivo) {
						lRecordModello.setAttribute("flgNoPubblAllegato", true);
						lRecordModello.setAttribute("flgPubblicaSeparato", false);
					} else {
						lRecordModello.setAttribute("flgNoPubblAllegato", flgNoPubbl);	
						lRecordModello.setAttribute("flgPubblicaSeparato", flgPubblicaSeparato);
					}
				}
				
				lRecordModello.setAttribute("nomeFileAllegato", nomeFileAllegato);
				lRecordModello.setAttribute("uriFileAllegato", uriFileAllegato);
				lRecordModello.setAttribute("descrizioneFileAllegato", descrizioneFileAllegato);

				lRecordModello.setAttribute("listaTipiFileAllegato", idTipoModello);
				lRecordModello.setAttribute("idTipoFileAllegato", idTipoModello);
				lRecordModello.setAttribute("descTipoFileAllegato", nomeTipoModello);

				lRecordModello.setAttribute("nomeFileAllegatoTif", "");
				lRecordModello.setAttribute("uriFileAllegatoTif", "");
				lRecordModello.setAttribute("remoteUri", false);
				lRecordModello.setAttribute("isChanged", true);
				lRecordModello.setAttribute("nomeFileVerPreFirma", nomeFileAllegato);
				lRecordModello.setAttribute("uriFileVerPreFirma", uriFileAllegato);
				lRecordModello.setAttribute("infoFileVerPreFirma", infoAllegato);
				lRecordModello.setAttribute("improntaVerPreFirma", infoAllegato.getImpronta());
				lRecordModello.setAttribute("infoFile", infoAllegato);
				
				String idTipoDocAllegatoVistoContabile = AurigaLayout.getParametroDB("ID_DOC_TYPE_VISTO_CONTAB_ITER_ATTI");
				if(idTipoDocAllegatoVistoContabile != null && idTipoModello != null && idTipoModello.equals(idTipoDocAllegatoVistoContabile)) {
					allegatoGeneratoDaModelloTask = lRecordModello;
				}
				
				if (posModello == -1) {
					if (listaAllegati == null || listaAllegati.getLength() == 0) {
						listaAllegati = new RecordList();
					}
					listaAllegati.addAt(lRecordModello, 0);
				} else {
					listaAllegati.set(posModello, lRecordModello);
				}
			}
				
			Record lRecordForm = new Record();
			lRecordForm.setAttribute("listaAllegati", listaAllegati);
			allegatiForm.setValues(lRecordForm.toMap());
								
			if(listaAllegatiItem != null) {
				if(listaAllegatiItem instanceof AllegatiGridItem) {
					((AllegatiGridItem)listaAllegatiItem).resetCanvasChanged();
				} else if(listaAllegatiItem instanceof AllegatiItem) {
					((AllegatiItem)listaAllegatiItem).resetCanvasChanged();
				}
			}

//			if (detailSectionAllegati != null) {
//				detailSectionAllegati.openIfhasValue();
//			}	
			
		}
		
		if(callback != null) {
			callback.execute(new Record());
		}	
	}
	
	public int getPosModelloFromTipo(String idTipoModello, RecordList listaAllegati) {
		if (listaAllegati != null) {
			for (int i = 0; i < listaAllegati.getLength(); i++) {
				Record allegato = listaAllegati.get(i);
				if (allegato.getAttribute("listaTipiFileAllegato") != null && allegato.getAttribute("listaTipiFileAllegato").equalsIgnoreCase(idTipoModello)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public boolean isDataRegistrazioneSameToday(Date dataRegistrazione){
		Date today = new Date();
		
		if ( dataRegistrazione == null)
			return true;
		
		if ( CalendarUtil.isSameDate(dataRegistrazione, today) )
			return true;
		else
			return false;
		
	}
	
	@Override
	public String getIdModDispositivo() {
		return idModAssDocTask != null ? idModAssDocTask : "";
	}
	
	@Override
	public String getNomeModDispositivo() {
		return nomeModAssDocTask != null ? nomeModAssDocTask : "";
	}
	
	@Override
	public String getDisplayFilenameModDispositivo() {
		return displayFilenameModAssDocTask != null ? displayFilenameModAssDocTask : "";
	}
	
	@Override
	public String getIdModAppendice() {
		return idModAppendice != null ? idModAppendice : "";
	}
	
	@Override
	public String getNomeModAppendice() {
		return nomeModAppendice != null ? nomeModAppendice : "";
	}
	
//	public void saveAndReloadTask() {
//	salvaDatiProvvisorio();
//}
	
	@Override
	public boolean showSalvaModello() {
		return true;
	}
	
	@Override
	public String getPrefKeyModelliDSprefix() {
		return "evento" + dettaglioPraticaLayout.getIdTipoProc();
	}
}
