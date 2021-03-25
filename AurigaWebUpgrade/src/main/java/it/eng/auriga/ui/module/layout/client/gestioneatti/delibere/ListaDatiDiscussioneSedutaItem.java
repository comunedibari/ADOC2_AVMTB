package it.eng.auriga.ui.module.layout.client.gestioneatti.delibere;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DropCompleteEvent;
import com.smartgwt.client.widgets.events.DropCompleteHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.HeaderClickEvent;
import com.smartgwt.client.widgets.grid.events.HeaderClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SortChangedHandler;
import com.smartgwt.client.widgets.grid.events.SortEvent;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.iterAtti.SelezionaTipoAttoWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

public class ListaDatiDiscussioneSedutaItem extends GridItem {
	
	private ListaDatiDiscussioneSedutaItem instance = this;
	
	protected ListGridField idUd;
	protected ListGridField tipo;
	protected ListGridField codTipo;
	protected ListGridField estremiPropostaUDXOrd;
	protected ListGridField nrOrdineOdg;
	protected ListGridField oggetto;
	protected ListGridField nominativoProponente;
	protected ListGridField dtInoltro;
	protected ListGridField flgInoltro;
	protected ListGridField flgAggiunto;
	protected ListGridField nroAllegati;
	//protected ListGridField estremiPropostaDelibera;
	protected ListGridField idUdPropostaDelibera;
	protected ListGridField esitoDiscussione;
	protected ListGridField numeroFinaleAtto;
	protected ListGridField idProcessoAuriga;
	protected ListGridField idTipoFlussoActiviti;
	protected ListGridField idIstanzaFlussoActiviti;
	protected ListGridField nomeTaskActiviti;
	protected ListGridField strutturaProponente;
	protected ListGridField centroDiCosto;
	protected ListGridField nroCircoscrizione;
	protected ListGridField nomeCircoscrizione;
	protected ListGridField flgReinviata;
	protected ListGridField flgPresenteInOdg;
	protected ListGridField nrOrdUltimoOdgCons;
	protected ListGridField dtOdgConsolidato;
	protected ListGridField uriFilePrimario;
	protected ListGridField nomeFilePrimario;	
	protected ListGridField statoRevisioneTesto;
	protected ListGridField flgEmendamenti;
	protected ListGridField iniziativaDelibera;
	protected ListGridField dettTipoAtto;
	protected ListGridField flgImmEseguibile;
	protected ListGridField noteAtto;
	protected ListGridField firmeDaAcquisire;
	protected ListGridField firmeApposte;
	protected ListGridField attoDaFirmare;
	protected ListGridField flgFuoriPacco;
	
	protected ListGridField[] formattedFields = null;
	protected List<String> controlFields;
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modificaTestoButtonField;
	protected ControlListGridField anteprimaVersDefinitivaButtonField;
	protected ControlListGridField riepilogoFirmeVistiButtonField;
	protected ControlListGridField fileAssociatiButtonField;
	protected ControlListGridField scaricaFileCompletiXAttiButtonField;
	protected ControlListGridField presenzeVotiButtonField;
	
	protected String statoSeduta;
	protected String organoCollegiale;
	protected String codCircoscrizione;
	private String headerClicked;

	public ListaDatiDiscussioneSedutaItem(String name, String organoCollegiale, String codCircoscrizione,String statoSeduta) {
		
		super(name, "lista_dati_discussione_seduta");
		
		this.statoSeduta = statoSeduta;
		this.organoCollegiale = organoCollegiale;
		this.codCircoscrizione = codCircoscrizione;
		
		setGridPkField("idUd");
		setShowPreference(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(false);
		
		idUd = new ListGridField("idUd");
		idUd.setHidden(true);
		idUd.setCanHide(false); 
		idUd.setCanSort(false);
		
		tipo = new ListGridField("tipo", "Tipo");
		tipo.setCanSort(true);
		
		codTipo = new ListGridField("codTipo","Cod. tipo");
		codTipo.setCanSort(true);
		
		estremiPropostaUDXOrd = new ListGridField("estremiPropostaUDXOrd", "N°");
		estremiPropostaUDXOrd.setCanSort(true);
		estremiPropostaUDXOrd.setDisplayField("estremiPropostaUD");
		estremiPropostaUDXOrd.setSortByDisplayField(false);
		estremiPropostaUDXOrd.setWidth(100);
		estremiPropostaUDXOrd.setAlign(Alignment.LEFT);
		estremiPropostaUDXOrd.setCanDragResize(true);
		estremiPropostaUDXOrd.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(event.getRecord().getAttributeAsString("idUd") != null && !"".equals(event.getRecord().getAttributeAsString("idUd"))) {											
					onClickDetailButton(event.getRecord());	
				}	
			}
		});
		estremiPropostaUDXOrd.setBaseStyle(it.eng.utility.Styles.cellTextBlueClickable);
		
		nrOrdineOdg = new ListGridField("nrOrdineOdg", "N° in Odg");
		nrOrdineOdg.setType(ListGridFieldType.INTEGER);
		nrOrdineOdg.setCanSort(true);
		
		oggetto = new ListGridField("oggetto", "Oggetto");
		oggetto.setCanSort(false);
		
		nominativoProponente = new ListGridField("nominativoProponente", "Proponente");
		nominativoProponente.setCanSort(true);
		 
		dtInoltro = new ListGridField("dtInoltro", "Inoltro alla seduta in data");
		dtInoltro.setType(ListGridFieldType.DATE);
		dtInoltro.setCanSort(true);
		
		flgInoltro = new ListGridField("flgInoltro", "Inoltro fuori termine");
		flgInoltro.setType(ListGridFieldType.ICON);
		flgInoltro.setWidth(30);
		flgInoltro.setIconWidth(16);
		flgInoltro.setIconHeight(16);
		Map<String, String> flgInoltroIcons = new HashMap<String, String>();		
		flgInoltroIcons.put("1", "buttons/clock.png");
		flgInoltroIcons.put("0", "blank.png");
		flgInoltro.setValueIcons(flgInoltroIcons);
		flgInoltro.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgInoltro") != null && "1".equals(record.getAttribute("flgInoltro"))) {
					return "Fuori termine";
				} else {
					return "Normale";
				}
			}
		});
		flgInoltro.setCanSort(true);
		
		flgAggiunto = new ListGridField("flgAggiunto", "Aggiunto");
		flgAggiunto.setType(ListGridFieldType.ICON);
		flgAggiunto.setWidth(30);
		flgAggiunto.setIconWidth(16);
		flgAggiunto.setIconHeight(16);
		Map<String, String> flgAggiuntoIcons = new HashMap<String, String>();		
		flgAggiuntoIcons.put("1", "delibere/A.png");
		flgAggiuntoIcons.put("0", "blank.png");
		flgAggiunto.setValueIcons(flgAggiuntoIcons);
		flgAggiunto.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgAggiunto") != null && "1".equals(record.getAttribute("flgAggiunto"))) {
					return "Atto aggiunto in OdG dalla segreteria";
				} else {
					return "Atto inoltrato in OdG dal proponente";
				}
			}
		});
		flgAggiunto.setCanSort(true);
		
		nroAllegati = new ListGridField("nroAllegati", "N° allegati");
		nroAllegati.setType(ListGridFieldType.INTEGER);
		nroAllegati.setCanSort(true);
		
		//estremiPropostaDelibera = new ListGridField("estremiPropostaDelibera", "Relativo alla proposta N°");
		//estremiPropostaDelibera.setCanSort(true);
		
		idUdPropostaDelibera = new ListGridField("idUdPropostaDelibera");
		idUdPropostaDelibera.setHidden(true);
		idUdPropostaDelibera.setCanHide(false); 
		idUdPropostaDelibera.setCanSort(false);

		esitoDiscussione = new ListGridField("esitoDiscussione", "Esito");
		esitoDiscussione.setAttribute("custom", true);
		esitoDiscussione.setCanSort(false);
		esitoDiscussione.setCanEdit(true);
		esitoDiscussione.setCellFormatter(new CellFormatter() {
		
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				RecordList esitiXtipoArgomento = getEsitiXtipoArgomento();
				String codTipo = record.getAttribute("codTipo");					
				LinkedHashMap<String, String> esitoDiscussioneValueMap = new LinkedHashMap<String, String>();
				if (esitiXtipoArgomento != null){
					for(int i = 0; i < esitiXtipoArgomento.getLength(); i++) {
						if(codTipo != null && codTipo.equals(esitiXtipoArgomento.get(i).getAttribute("tipo"))) {
							esitoDiscussioneValueMap.put(esitiXtipoArgomento.get(i).getAttribute("esito"), esitiXtipoArgomento.get(i).getAttribute("esito"));
						}
					}
					if(esitoDiscussioneValueMap.containsKey(record.getAttribute("esitoDiscussione"))) {
						return esitoDiscussioneValueMap.get(record.getAttribute("esitoDiscussione"));
					}
				}
				return null;								
			}
		});
		
		numeroFinaleAtto = new ListGridField("numeroFinaleAtto", "N° atto definitivo");
		numeroFinaleAtto.setCanSort(false);
		
		idProcessoAuriga = new ListGridField("idProcessoAuriga");
		idProcessoAuriga.setHidden(true);
		idProcessoAuriga.setCanHide(false); 
		idProcessoAuriga.setCanSort(false);
		
		idTipoFlussoActiviti = new ListGridField("idTipoFlussoActiviti");
		idTipoFlussoActiviti.setHidden(true);
		idTipoFlussoActiviti.setCanHide(false); 
		idTipoFlussoActiviti.setCanSort(false);
		
		idIstanzaFlussoActiviti = new ListGridField("idIstanzaFlussoActiviti");
		idIstanzaFlussoActiviti.setHidden(true);
		idIstanzaFlussoActiviti.setCanHide(false); 
		idIstanzaFlussoActiviti.setCanSort(false);
		
		nomeTaskActiviti = new ListGridField("nomeTaskActiviti");
		nomeTaskActiviti.setHidden(true);
		nomeTaskActiviti.setCanHide(false); 
		nomeTaskActiviti.setCanSort(false);
		
		strutturaProponente = new ListGridField("strutturaProponente", "Struttura prop.");
		strutturaProponente.setCanSort(true);
		
		centroDiCosto = new ListGridField("centroDiCosto", "CdC");
		centroDiCosto.setCanSort(true);
		
		nroCircoscrizione = new ListGridField("nroCircoscrizione", "N° circ");
		nroCircoscrizione.setCanSort(true);
		
		nomeCircoscrizione = new ListGridField("nomeCircoscrizione", "Nome circ.");
		nomeCircoscrizione.setCanSort(true);

		flgReinviata = new ListGridField("flgReinviata", "Reinviata");
		flgReinviata.setType(ListGridFieldType.ICON);
		flgReinviata.setWidth(30);
		flgReinviata.setIconWidth(16);
		flgReinviata.setIconHeight(16);
		Map<String, String> flgReinviataIcons = new HashMap<String, String>();		
		flgReinviataIcons.put("1", "buttons/send.png");
		flgReinviataIcons.put("0", "blank.png");
		flgReinviata.setValueIcons(flgReinviataIcons);
		flgReinviata.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgReinviata") != null && "1".equals(record.getAttribute("flgReinviata"))) {
					return "Reinviata";
				}						
				return null;
			}
		});
		flgReinviata.setCanSort(true);
		
		flgPresenteInOdg = new ListGridField("flgPresenteInOdg", "Presente in OdG già consolidato");
		flgPresenteInOdg.setType(ListGridFieldType.ICON);
		flgPresenteInOdg.setWidth(30);
		flgPresenteInOdg.setIconWidth(16);
		flgPresenteInOdg.setIconHeight(16);
		Map<String, String> flgPresenteInOdgIcons = new HashMap<String, String>();		
		flgPresenteInOdgIcons.put("1", "ok.png");
		flgPresenteInOdgIcons.put("0", "blank.png");
		flgPresenteInOdg.setValueIcons(flgPresenteInOdgIcons);
		flgPresenteInOdg.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgPresenteInOdg") != null && "1".equals(record.getAttribute("flgPresenteInOdg"))) {
					return "Presente";
				}						
				return null;
			}
		});
		flgPresenteInOdg.setCanSort(true);

		nrOrdUltimoOdgCons = new ListGridField("nrOrdUltimoOdgCons", "N° ord. in ultimo OdG consolidato");
		nrOrdUltimoOdgCons.setType(ListGridFieldType.INTEGER);		
		nrOrdUltimoOdgCons.setCanSort(true);
		
		dtOdgConsolidato = new ListGridField("dtOdgConsolidato", "Presente in OdG consolidato il");
		dtOdgConsolidato.setType(ListGridFieldType.DATE);
		dtOdgConsolidato.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		dtOdgConsolidato.setCanSort(true);
		
		uriFilePrimario = new ListGridField("uriFilePrimario");
		uriFilePrimario.setHidden(true);
		uriFilePrimario.setCanHide(false); 
		uriFilePrimario.setCanSort(false);
		
		nomeFilePrimario = new ListGridField("nomeFilePrimario");
		nomeFilePrimario.setHidden(true);
		nomeFilePrimario.setCanHide(false); 
		nomeFilePrimario.setCanSort(false);
		
		statoRevisioneTesto = new ListGridField("statoRevisioneTesto", "Stato");
		statoRevisioneTesto.setType(ListGridFieldType.ICON);
		statoRevisioneTesto.setWidth(30);
		statoRevisioneTesto.setIconWidth(16);
		statoRevisioneTesto.setIconHeight(16);
		Map<String, String> statoRevisioneTestoIcons = new HashMap<String, String>();		
		statoRevisioneTestoIcons.put("firmato", 			 "delibere/statoRevisioneTesto/firmato.png");
		statoRevisioneTestoIcons.put("firmato_parzialmente", "delibere/statoRevisioneTesto/firmato_parzialmente.png");
		statoRevisioneTestoIcons.put("pronto_da_firmare", 	 "delibere/statoRevisioneTesto/pronto_da_firmare.png");
		statoRevisioneTestoIcons.put("testo_in_lavorazione", "delibere/statoRevisioneTesto/testo_in_lavorazione.png");
		statoRevisioneTestoIcons.put("lavorazione_conclusa", "delibere/statoRevisioneTesto/lavorazione_conclusa.png");
		statoRevisioneTestoIcons.put("rinviata", 			 "delibere/statoRevisioneTesto/rinviata.png");
		statoRevisioneTestoIcons.put("ritirato", 			 "delibere/statoRevisioneTesto/ritirato.png");
		statoRevisioneTestoIcons.put("", "blank.png");
		statoRevisioneTesto.setValueIcons(statoRevisioneTestoIcons);
		statoRevisioneTesto.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("statoRevisioneTesto") != null) {
					if("firmato".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Firmato: apposte tutte le firme previste";
					} else if("firmato_parzialmente".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "In raccolta firme (NON tutte le firme previste)";
					} else if("pronto_da_firmare".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Testo coordinato completato";
					} else if("testo_in_lavorazione".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Testo coordinato da ultimare";
					} else if("lavorazione_conclusa".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Lavorazione conclusa";
					} else if("rinviata".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Rinviata";
					}  else if("ritirato".equals(record.getAttribute("statoRevisioneTesto"))) {
						return "Ritirato";
					}
				}
				return null;
			}
		});
		statoRevisioneTesto.setCanSort(true);
		
		flgEmendamenti = new ListGridField("flgEmendamenti", "Presenza emendamenti");
		flgEmendamenti.setType(ListGridFieldType.ICON);
		flgEmendamenti.setWidth(30);
		flgEmendamenti.setIconWidth(16);
		flgEmendamenti.setIconHeight(16);
		Map<String, String> flgEmendamentiIcons = new HashMap<String, String>();		
		flgEmendamentiIcons.put("1", "attiInLavorazione/EM.png");
		flgEmendamentiIcons.put("0", "blank.png");
		flgEmendamenti.setValueIcons(flgEmendamentiIcons);
		flgEmendamenti.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgEmendamenti") != null && "1".equals(record.getAttribute("flgEmendamenti"))) {
					return "Presenti emendamenti";
				}						
				return null;
			}
		});
		flgEmendamenti.setCanSort(true);
		
		iniziativaDelibera = new ListGridField("iniziativaDelibera", "Iniziativa");
		iniziativaDelibera.setCanSort(true);
		
		dettTipoAtto = new ListGridField("dettTipoAtto", "Tipo di dettaglio");
		dettTipoAtto.setCanSort(true);
		
		flgImmEseguibile = new ListGridField("flgImmEseguibile", "I.E.");
		flgImmEseguibile.setType(ListGridFieldType.ICON);
		flgImmEseguibile.setWidth(30);
		flgImmEseguibile.setIconWidth(16);
		flgImmEseguibile.setIconHeight(16);
		Map<String, String> flgImmEseguibileIcons = new HashMap<String, String>();		
		flgImmEseguibileIcons.put("1", "attiInLavorazione/IE.png");
		flgImmEseguibileIcons.put("0", "blank.png");
		flgImmEseguibile.setValueIcons(flgImmEseguibileIcons);
		flgImmEseguibile.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgImmEseguibile") != null && "1".equals(record.getAttribute("flgImmEseguibile"))) {
					return "I.E.";
				}						
				return null;
			}
		});
		flgImmEseguibile.setCanSort(true);
		
		noteAtto = new ListGridField("iconaNoteAtto", "Note atto in seduta");
		noteAtto.setType(ListGridFieldType.ICON);
		noteAtto.setWidth(30);
		noteAtto.setIconWidth(16);
		noteAtto.setIconHeight(16);
		noteAtto.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("noteAtto") != null && !"".equalsIgnoreCase(record.getAttribute("noteAtto"))) {
					return buildImgButtonHtml("buttons/note_seduta.png");
				} else if(!fromStoricoSeduta()) {
					return buildImgButtonHtml("buttons/add_note_seduta.png");
				}	
				return null;
			}
		});
		noteAtto.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("noteAtto") != null && !"".equalsIgnoreCase(record.getAttribute("noteAtto"))) {
					return "Annotazioni apposte";
				} else if(!fromStoricoSeduta()) {
					return "Apponi annotazioni";
				}	
				return null;	
			}
		});
		noteAtto.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord record = event.getRecord();
				if(record.getAttribute("noteAtto") != null && !"".equalsIgnoreCase(record.getAttribute("noteAtto"))) {
					Record recordNote = new Record();
					recordNote.setAttribute("noteAtto", record.getAttribute("noteAtto"));
					recordNote.setAttribute("storico" , fromStoricoSeduta());
					NoteAttoPopup noteAttoPopup = new NoteAttoPopup(recordNote) {
						
						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							if(!fromStoricoSeduta()) {
								grid.deselectAllRecords();
								record.setAttribute("noteAtto", object.getAttribute("noteAtto"));
								updateGridItemValue();		
							}
							callback.execute(new DSResponse(), null, new DSRequest());
						}
					};
					noteAttoPopup.show();
				} else if(!fromStoricoSeduta()) {
					NoteAttoPopup noteAttoPopup = new NoteAttoPopup(new Record()) {
						
						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							grid.deselectAllRecords();
							record.setAttribute("noteAtto", object.getAttribute("noteAtto"));
							updateGridItemValue();						
							callback.execute(new DSResponse(), null, new DSRequest());
						}
					};
					noteAttoPopup.show();
				}
			}
		});
		noteAtto.setCanSort(true);
		
		firmeDaAcquisire = new ListGridField("firmeDaAcquisire", "Firme da acquisire");
		firmeDaAcquisire.setCanSort(true);
		
		firmeApposte = new ListGridField("firmeApposte", "Firme apposte");
		firmeApposte.setCanSort(true);
		
		attoDaFirmare = new ListGridField("attoDaFirmare", "Atto da firmare dall'utente collegato");
		attoDaFirmare.setType(ListGridFieldType.ICON);
		attoDaFirmare.setWidth(30);
		attoDaFirmare.setIconWidth(16);
		attoDaFirmare.setIconHeight(16);
		Map<String, String> attoDaFirmareIcons = new HashMap<String, String>();		
		attoDaFirmareIcons.put("1", "pratiche/task/buttons/firma.png");
		attoDaFirmareIcons.put("0", "blank.png");
		attoDaFirmare.setValueIcons(attoDaFirmareIcons);
		attoDaFirmare.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("attoDaFirmare") != null && "1".equals(record.getAttribute("attoDaFirmare"))) {
					return "atto in attesa di tua firma";
				}						
				return null;
			}
		});
		flgImmEseguibile.setCanSort(true);
		
		flgFuoriPacco = new ListGridField("flgFuoriPacco", "Fuori pacco");
		flgFuoriPacco.setType(ListGridFieldType.ICON);
		flgFuoriPacco.setWidth(30);
		flgFuoriPacco.setIconWidth(16);
		flgFuoriPacco.setIconHeight(16);
		Map<String, String> flgFuoriPaccoIcons = new HashMap<String, String>();		
		flgFuoriPaccoIcons.put("1", "delibere/fuoripacco.png");
		flgFuoriPaccoIcons.put("0", "blank.png");
		flgFuoriPacco.setValueIcons(flgFuoriPaccoIcons);
		flgFuoriPacco.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("flgFuoriPacco") != null && "1".equals(record.getAttribute("flgFuoriPacco"))) {
					return "Fuori pacco";
				}						
				return null;
			}
		});
		flgImmEseguibile.setCanSort(true);
		
		if(codCircoscrizione != null && !"".equalsIgnoreCase(codCircoscrizione)) {
			setGridFields(
					idUd,
					tipo,
					codTipo,
					estremiPropostaUDXOrd,
					nrOrdineOdg,
					oggetto,
					nominativoProponente,
					dtInoltro,
					flgInoltro,
					flgAggiunto,
					nroAllegati,
					//estremiPropostaDelibera,
					idUdPropostaDelibera,
					esitoDiscussione,
					numeroFinaleAtto,
					idProcessoAuriga,
					idTipoFlussoActiviti,
					idIstanzaFlussoActiviti,
					nomeTaskActiviti,
					strutturaProponente,
					centroDiCosto,
					flgReinviata,
					flgPresenteInOdg,
					nrOrdUltimoOdgCons,
					dtOdgConsolidato,
					uriFilePrimario,
					nomeFilePrimario,
					statoRevisioneTesto,
					flgEmendamenti,
					iniziativaDelibera,
					dettTipoAtto,
					flgImmEseguibile,
					noteAtto,
					firmeDaAcquisire,
					firmeApposte,
					attoDaFirmare,
					flgFuoriPacco
			);
			
		} else {
			setGridFields(
					idUd,
					tipo,
					codTipo,
					estremiPropostaUDXOrd,
					nrOrdineOdg,
					oggetto,
					nominativoProponente,
					dtInoltro,
					flgInoltro,
					flgAggiunto,
					nroAllegati,
					//estremiPropostaDelibera,
					idUdPropostaDelibera,
					esitoDiscussione,
					numeroFinaleAtto,
					idProcessoAuriga,
					idTipoFlussoActiviti,
					idIstanzaFlussoActiviti,
					nomeTaskActiviti,
					strutturaProponente,
					centroDiCosto,
					nroCircoscrizione,
					nomeCircoscrizione,
					flgReinviata,
					flgPresenteInOdg,
					nrOrdUltimoOdgCons,
					dtOdgConsolidato,
					uriFilePrimario,
					nomeFilePrimario,
					statoRevisioneTesto,
					flgEmendamenti,
					iniziativaDelibera,
					dettTipoAtto,
					flgImmEseguibile,
					noteAtto,
					firmeDaAcquisire,
					firmeApposte,
					attoDaFirmare,
					flgFuoriPacco
			);
		}
		 	
	}
	
	@Override
	public ListGrid buildGrid() {
		
		final ListGrid grid = super.buildGrid();
		
		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);
		grid.setEditorCustomizer(new ListGridEditorCustomizer() {
			
			@Override
			public FormItem getEditor(ListGridEditorContext context) {
				
				if(context.getEditField().getName().equals("esitoDiscussione")) {
					
					RecordList esitiXtipoArgomento = getEsitiXtipoArgomento();
					String codTipo = context.getEditedRecord().getAttribute("codTipo");
						
					RadioGroupItem radioEsitoItem = new RadioGroupItem("radioEsito");
					radioEsitoItem.setShowTitle(false);
					LinkedHashMap<String, String> esitoDiscussioneValueMap = new LinkedHashMap<String, String>();
					if (esitiXtipoArgomento != null) {
						for(int i = 0; i < esitiXtipoArgomento.getLength(); i++) {
							if(codTipo != null && codTipo.equals(esitiXtipoArgomento.get(i).getAttribute("tipo"))) {
								esitoDiscussioneValueMap.put(esitiXtipoArgomento.get(i).getAttribute("esito"), esitiXtipoArgomento.get(i).getAttribute("esito"));
							}
						}
					}
					radioEsitoItem.setValueMap(esitoDiscussioneValueMap);
					radioEsitoItem.setVertical(true);
					radioEsitoItem.setWrap(false);
					
					return radioEsitoItem; 
				}
				
				return null;				
			}
		});
		grid.addDropCompleteHandler(new DropCompleteHandler() {
			
			@Override
			public void onDropComplete(DropCompleteEvent event) {
				refreshNroOrdineGiorno();
			}
		});
		grid.addHeaderClickHandler(new HeaderClickHandler() {

		    @Override
		    public void onHeaderClick(HeaderClickEvent event) {
		       
		    	headerClicked = grid.getField(event.getFieldNum()).getName();
		    }
		});
		grid.addSortChangedHandler(new SortChangedHandler() {
			
			@Override
			public void onSortChanged(SortEvent event) {
				if(headerClicked != null && !"nrOrdineOdg".equalsIgnoreCase(headerClicked)) {
					AurigaLayout.showConfirmDialogWithWarning("Attenzione", "Vuoi aggiornare il valore del campo 'N° in OdG' in base all'ordinamento che stai chiedendo?", "Si", "No", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								refreshNroOrdineGiorno();
							}
						}
					});
				}
			}
		});
		
		return grid;
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {

		int length = fields.length;
		length += getButtonsFields() != null ? getButtonsFields().size() : 0; //buttons			
		
		ListaBean configLista = Layout.getListConfig("lista_dati_convocazione_seduta");
		
		formattedFields = new ListGridField[length];
		controlFields = new ArrayList<String>();

		int count = 0;
		
		for (final ListGridField field : fields){	

			String fieldName = field.getName();
			
			boolean skip = false;
			for(ControlListGridField buttonField : getButtonsFields()) {
				if(fieldName.equals(buttonField.getName())) {
					skip = true;
					break;
				}
			}
							
			if(!skip) {

				try {
					
					if("esitoDiscussione".equalsIgnoreCase(field.getName())) {
						field.setCanEdit(true);
					} else {
						field.setCanEdit(false);
					}

					if(field instanceof ControlListGridField) {
						controlFields.add(fieldName);
					}

					field.setAlign(Alignment.CENTER);					
					if(field.getTitle() != null && !"".equals(field.getTitle().trim())) {
						field.setPrompt(field.getTitle());					
					}		
					
					//fare un controllo di non nullità sul bean non ha senso, Layout ritorna sempre un bean
					if(configLista.getColonneOrdinabili() != null) {
						//					if(configLista.getColonneDefault().contains(fieldName)) {
						//						field.setHidden(false);
						//					} else {
						//						field.setHidden(true);
						//					}
						if(configLista.getColonneOrdinabili().contains(fieldName)) {
							field.setCanSort(true);
						} else {
							field.setCanSort(false);
						}		
					}

					//Recupero il tipo relativo
					ListGridFieldType fieldType = field.getType();

					if (fieldType == null || fieldType.equals(ListGridFieldType.TEXT)) {
						if(field.getAttribute("custom") == null || !field.getAttributeAsBoolean("custom")) {
							field.setCellAlign(Alignment.LEFT);	
						}
					} else if (fieldType.equals(ListGridFieldType.INTEGER) 
							|| fieldType.equals(ListGridFieldType.BINARY) 
							|| fieldType.equals(ListGridFieldType.FLOAT)) {
						field.setCellAlign(Alignment.RIGHT);
					} else if (fieldType.equals(ListGridFieldType.DATE) 
							|| fieldType.equals(ListGridFieldType.TIME)) {
						field.setCellAlign(Alignment.CENTER);										
					} else if (fieldType.equals(ListGridFieldType.IMAGE) 
							|| fieldType.equals(ListGridFieldType.IMAGEFILE) 
							|| fieldType.equals(ListGridFieldType.LINK) 
							|| fieldType.equals(ListGridFieldType.ICON)) {
						field.setCellAlign(Alignment.CENTER);
					} else {
						field.setCellAlign(Alignment.LEFT);
					}

					if (fieldType == null || fieldType.equals(ListGridFieldType.TEXT)) {
						if(field.getAttribute("custom") == null || !field.getAttributeAsBoolean("custom")) {
							field.setCellFormatter(new CellFormatter() {							
								@Override
								public String format(Object value, ListGridRecord record, int rowNum,
										int colNum) {
									if(record != null) {
										record.setAttribute("realValue"+colNum, value);
									}
									if (value==null) return null;
									String lStringValue = value.toString();
									if (lStringValue.length()>Layout.getGenericConfig().getMaxValueLength()){
										return lStringValue.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
									} else return lStringValue;
								}
							});
							field.setHoverCustomizer(new HoverCustomizer() {
								@Override
								public String hoverHTML(Object value, ListGridRecord record, int rowNum,
										int colNum) {
									Object realValue = record != null ? record.getAttribute("realValue"+colNum) : null;								
									return (realValue != null) ? (String) realValue : (String) value;
								}
							});			
						}			
					} else if (fieldType.equals(ListGridFieldType.INTEGER)) {	
						field.setSortNormalizer(new SortNormalizer() {			
							@Override
							public Object normalize(ListGridRecord record, String fieldName) {
								String value = record.getAttribute(fieldName);
								return value != null && !"".equals(value) ? Long.parseLong(value.replace(".", "")) : 0;																						
							}
						});		
					} else if (fieldType.equals(ListGridFieldType.DATE)) {			
						LinkedHashMap<String, String> groupingModes = new LinkedHashMap<String, String>();
						groupingModes.put("date", I18NUtil.getMessages().groupingModePerGiorno_title()); 
						groupingModes.put("month", I18NUtil.getMessages().groupingModePerMese_title());
						groupingModes.put("year", I18NUtil.getMessages().groupingModePerAnno_title());         
						field.setGroupingModes(groupingModes);  															
						field.setGroupValueFunction(new GroupValueFunction() {             
							public Object getGroupValue(Object value, ListGridRecord record, ListGridField field, String fieldName, ListGrid grid) { 
								Date date = record != null ? DateUtil.parseInput(record.getAttributeAsString(fieldName)) : null;                  								
								DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
								if (date == null) return " ";								
								if(field.getGroupingMode() != null) {
									DateTimeFormat dateFormatYear = DateTimeFormat.getFormat("yyyy");	
									String year = dateFormatYear.format(date);
									if(field.getGroupingMode().equals("year")) {
										return year;
									} else if(field.getGroupingMode().equals("month")) {
										DateTimeFormat dateFormatMonth = DateTimeFormat.getFormat("MM");
										switch(Integer.parseInt(dateFormatMonth.format(date))) {
										case 1: return I18NUtil.getMessages().decodificaMese_1() + " " + year;
										case 2: return I18NUtil.getMessages().decodificaMese_2() + " " + year;
										case 3: return I18NUtil.getMessages().decodificaMese_3() + " " + year;
										case 4: return I18NUtil.getMessages().decodificaMese_4() + " " + year;
										case 5: return I18NUtil.getMessages().decodificaMese_5() + " " + year;
										case 6: return I18NUtil.getMessages().decodificaMese_6() + " " + year;
										case 7: return I18NUtil.getMessages().decodificaMese_7() + " " + year;
										case 8: return I18NUtil.getMessages().decodificaMese_8() + " " + year;
										case 9: return I18NUtil.getMessages().decodificaMese_9() + " " + year;
										case 10: return I18NUtil.getMessages().decodificaMese_10() + " " + year;
										case 11: return I18NUtil.getMessages().decodificaMese_11() + " " + year;
										case 12: return I18NUtil.getMessages().decodificaMese_12() + " " + year;
										}										
									} 	
								}
								return dateFormat.format(date); 								      																																			
							}
						}); 	
						field.setSortNormalizer(new SortNormalizer() {			
							@Override
							public Object normalize(ListGridRecord record, String fieldName) {
								return formatDateForSorting(record, fieldName);																									
							}
						});		
						field.setCellFormatter(new CellFormatter() {	
							@Override
							public String format(Object value, ListGridRecord record, int rowNum,
									int colNum) {
								return manageDateCellFormat(value, field, record);
							}
						});																										
					} else if (fieldType.equals(ListGridFieldType.ICON)){						
						final Map<String, String> valueHovers = field.getAttributeAsMap("valueHovers");
						if (valueHovers != null) {
							final ListGridField iconfield = field;
							field.setHoverCustomizer(new HoverCustomizer() {
								@Override
								public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
									if(record != null) return valueHovers.get(record.getAttribute(iconfield.getName()));
									else return (String) value;
								}
							});
						}
					}

					formattedFields[count] = field;					

				} catch (Exception e) {
					formattedFields[count] = field;
				}			

				count++;
			}
		}				
		
		if(getButtonsFields() != null) {
			for(int i = 0; i < getButtonsFields().size(); i++) {
				controlFields.add(getButtonsFields().get(i).getName());
				formattedFields[count] = getButtonsFields().get(i);
				count++;
			}					
		}				
		
		super.setGridFields(formattedFields);
	}	
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();	
		
		ToolStripButton addPropostaButton = getAddPropostaButton();  
		buttons.add(addPropostaButton);
		
		ToolStripButton nuovoAttoArgomentoButton = getAddAttoArgomentoButton();  
		buttons.add(nuovoAttoArgomentoButton);
		
		ToolStripButton refreshButton = new ToolStripButton();   
		refreshButton.setIcon("buttons/refreshList.png");   
		refreshButton.setIconSize(16);
		refreshButton.setPrefix("Ricarica");
		refreshButton.setPrompt("Ricarica");
		refreshButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				onClickRefreshListButton();
			}   
		});  
		buttons.add(refreshButton);
			
		return buttons;
	}

	private ToolStripButton getAddAttoArgomentoButton() {
		ToolStripButton nuovoAttoArgomentoButton = new ToolStripButton();   
		nuovoAttoArgomentoButton.setIcon("buttons/new.png");   
		nuovoAttoArgomentoButton.setIconSize(16);
		nuovoAttoArgomentoButton.setPrefix("aggiunta_atto");
		nuovoAttoArgomentoButton.setPrompt("Nuovo atto/argomento/nota");
		nuovoAttoArgomentoButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				nuovoAttoArgomento();
			}   
		});
		return nuovoAttoArgomentoButton;
	}
	
	public void nuovoAttoArgomento() {
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("LoadComboAttoConFlussoWFDataSource");
		lGWTRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record[] data = response.getData();
				if (data.length > 0) {
					SelezionaTipoAttoWindow lSelezionaTipoAttoWindow = new SelezionaTipoAttoWindow(getIdSeduta(), organoCollegiale,
						"DISCUSSIONE", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
									
								onClickRefreshListButton();
							}
						});
				    lSelezionaTipoAttoWindow.show();
				} else {
					Layout.addMessage(new MessageBean("Nessun tipo atto disponibile", "", MessageType.ERROR));
				}
			}
		});

	}

	private ToolStripButton getAddPropostaButton() {
		ToolStripButton addPropostaButton = new ToolStripButton();   
		addPropostaButton.setIcon("buttons/importaFileDocumenti.png");   
		addPropostaButton.setIconSize(16);
		addPropostaButton.setPrefix("aggiunta_proposta");
		addPropostaButton.setPrompt("Aggiungi");
		addPropostaButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				aggiungiProposta();     	
			}   
		});
		return addPropostaButton;
	}
	
	public void aggiungiProposta() {
		String idNodoRicerca = getIdNodoRicerca();
		AttiOdgMultiLookupArchivio lookupMultiplaArchivio = new AttiOdgMultiLookupArchivio(null, idNodoRicerca);				
		lookupMultiplaArchivio.show(); 
	}
	
	public void onClickRefreshListButton() {
		
		Record lRecord = new Record();
		lRecord.setAttribute("organoCollegiale", organoCollegiale);
		lRecord.setAttribute("idSeduta", getIdSeduta());
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		lGWTRestDataSource.call(lRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				RecordList recordList = object.getAttributeAsRecordList("listaArgomentiOdg");
				setData(recordList);
			}
		});
	}
	
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					

		if(detailButtonField == null) {
			detailButtonField = buildDetailButtonField();					
		}
		if(modificaTestoButtonField == null) {
			modificaTestoButtonField = buildModificaTestoButtonField();
		}
		if(anteprimaVersDefinitivaButtonField == null) {
			anteprimaVersDefinitivaButtonField = buildAnteprimaVersDefinitivaButtonField();
		}
		if (riepilogoFirmeVistiButtonField == null) {
			riepilogoFirmeVistiButtonField = buildRiepilogoFirmeVistiButtonField();
		}		
		if(fileAssociatiButtonField == null) {
			fileAssociatiButtonField = buildFileAssociatiButtonField();
		}
		if(scaricaFileCompletiXAttiButtonField == null) {
			scaricaFileCompletiXAttiButtonField = buildScaricaFileCompletiXAttiButtonField();
		}		
		if(presenzeVotiButtonField == null) { 
			presenzeVotiButtonField = buildPresenzeVotiButtonField();	
		}	
		
		buttonsList.add(detailButtonField);

		if(isButtonEnabled()) {
			buttonsList.add(modificaTestoButtonField);
		}

		buttonsList.add(anteprimaVersDefinitivaButtonField);
		buttonsList.add(riepilogoFirmeVistiButtonField);
		buttonsList.add(fileAssociatiButtonField);
		buttonsList.add(scaricaFileCompletiXAttiButtonField);
		
		if(isButtonEnabled()) {
			buttonsList.add(presenzeVotiButtonField);
		}
			
		return buttonsList;
	}
	
	protected boolean isButtonEnabled() {
		if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/GNT/G");
		} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CNS/G");
		} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CMM/G");
		} else {
			return false;
		}
	}
	
	protected ControlListGridField buildDetailButtonField() {
		
		ControlListGridField detailButtonField = new ControlListGridField("detailButton");  
		detailButtonField.setAttribute("custom", true);	
		detailButtonField.setShowHover(true);		
		detailButtonField.setCanReorder(false);
		detailButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {											
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {	
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {										
					return "Dettaglio proposta/argomento";
				}
				return null;
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(event.getRecord().getAttributeAsString("idUd") != null && !"".equals(event.getRecord().getAttributeAsString("idUd"))) {											
					onClickDetailButton(event.getRecord());	
				}	
			}
		});		
		return detailButtonField;
	}
	
	public void onClickDetailButton(final ListGridRecord record) {		
		String title = "Dettaglio proposta/argomento " + record.getAttributeAsString("estremiPropostaUD");		
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttributeAsString("idUd"));		
		if(record.getAttributeAsString("idProcessoAuriga") != null && !"".equals(record.getAttributeAsString("idProcessoAuriga"))) {
//			lRecord.setAttribute("idProcess", record.getAttributeAsString("idProcessoAuriga"));
//			lRecord.setAttribute("activityName", "#POST_DISCUSSIONE_AULA_" + organoCollegiale);
//			lRecord.setAttribute("idDefProcFlow", record.getAttributeAsString("idTipoFlussoActiviti"));
//			lRecord.setAttribute("idInstProcFlow", record.getAttributeAsString("idIstanzaFlussoActiviti"));
//			new DettaglioPropostaDeliberaWindow(lRecord, title, false);
			AurigaLayout.apriDettaglioPratica(record.getAttributeAsString("idProcessoAuriga"), title, null);
		} else {
			new DettaglioRegProtAssociatoWindow(lRecord, title);		
		}
	}	
	
	protected boolean isRecordAbilToModificaTesto(ListGridRecord record) {
		if(record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {						
			if(record.getAttributeAsString("idProcessoAuriga") != null && !"".equals(record.getAttributeAsString("idProcessoAuriga"))) {			
				boolean isFirmato = record.getAttribute("statoRevisioneTesto") != null && "firmato".equalsIgnoreCase(record.getAttribute("statoRevisioneTesto"));
				return !isFirmato;
			}
		}
		return false;
	}
	
	protected ControlListGridField buildModificaTestoButtonField() {
		
		ControlListGridField modificaTestoButtonField = new ControlListGridField("modificaTestoButton");  
		modificaTestoButtonField.setAttribute("custom", true);	
		modificaTestoButtonField.setShowHover(true);		
		modificaTestoButtonField.setCanReorder(false);
		modificaTestoButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isRecordAbilToModificaTesto(record)) {				
					return buildImgButtonHtml("file/editDoc.png");
				}
				return null;
			}
		});
		modificaTestoButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordAbilToModificaTesto(record)) {				
					return "Modifica testo";
				}
				return null;
			}
		});		
		modificaTestoButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(isRecordAbilToModificaTesto(event.getRecord())) {			
					onClickModificaTestoButton(event.getRecord());
				}
			}
		});		
		return modificaTestoButtonField;
	}
	
	public void onClickModificaTestoButton(final ListGridRecord record) {
		String title = "Modifica testo "+ record.getAttributeAsString("estremiPropostaUD");
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttributeAsString("idUd"));
		lRecord.setAttribute("idProcess", record.getAttributeAsString("idProcessoAuriga"));
		lRecord.setAttribute("activityName", "#POST_DISCUSSIONE_AULA_" + organoCollegiale);
		lRecord.setAttribute("idDefProcFlow", record.getAttributeAsString("idTipoFlussoActiviti"));
		lRecord.setAttribute("idInstProcFlow", record.getAttributeAsString("idIstanzaFlussoActiviti"));
		new DettaglioPropostaDeliberaWindow(lRecord, title, true);
	}
	
	protected boolean isRecordAbilToAnteprimaVersDefinitiva(ListGridRecord record) {
		if(record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {						
			if(record.getAttributeAsString("idProcessoAuriga") != null && !"".equals(record.getAttributeAsString("idProcessoAuriga"))) {
				return true;
			}
		}
		return false;
	}
	
	protected ControlListGridField buildAnteprimaVersDefinitivaButtonField() {
		
		ControlListGridField anteprimaVersDefinitivaButtonField = new ControlListGridField("anteprimaVersDefinitivaButton");  
		anteprimaVersDefinitivaButtonField.setAttribute("custom", true);	
		anteprimaVersDefinitivaButtonField.setShowHover(true);		
		anteprimaVersDefinitivaButtonField.setCanReorder(false);
		anteprimaVersDefinitivaButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isRecordAbilToAnteprimaVersDefinitiva(record)) {								
					return buildImgButtonHtml("file/preview.png");
				}
				return null;
			}
		});
		anteprimaVersDefinitivaButtonField.setHoverCustomizer(new HoverCustomizer() {				
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordAbilToAnteprimaVersDefinitiva(record)) {								
					return "Anteprima vers. definitiva con copertina";
				}
				return null;
			}
		});		
		anteprimaVersDefinitivaButtonField.addRecordClickHandler(new RecordClickHandler() {				
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(isRecordAbilToAnteprimaVersDefinitiva(event.getRecord())) {			
					onClickAnteprimaVersDefinitiva(event.getRecord());
				}
			}
		});		
		return anteprimaVersDefinitivaButtonField;
	}
	
	public void onClickAnteprimaVersDefinitiva(ListGridRecord record) {
		final String idProcess = record.getAttributeAsString("idProcessoAuriga");
		final String idUd = record.getAttributeAsString("idUd");
		final String taskName = "#POST_DISCUSSIONE_AULA_" + organoCollegiale;
		Record recordCallExecAtt = new Record();
		recordCallExecAtt.setAttribute("idUd", idUd);
		recordCallExecAtt.setAttribute("idProcess", idProcess);
		recordCallExecAtt.setAttribute("activityName", taskName);
		recordCallExecAtt.setAttribute("idDefProcFlow", record.getAttributeAsString("idTipoFlussoActiviti"));
		recordCallExecAtt.setAttribute("idInstProcFlow", record.getAttributeAsString("idIstanzaFlussoActiviti"));
		if (idProcess != null && !"".equals(idProcess)) {
			callExecAtt(recordCallExecAtt, new ServiceCallback<Record>() {

				@Override
				public void execute(final Record lRecordEvento) {		
					
					final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
					lNuovaPropostaAtto2CompletaDataSource.addParam("idProcess", idProcess);
					lNuovaPropostaAtto2CompletaDataSource.addParam("taskName", taskName); // se non lo passo in teoria carica l'ultimo task
					if (idUd != null && !idUd.equalsIgnoreCase("")) {
						Record lRecordToLoad = new Record();
						lRecordToLoad.setAttribute("idUd", idUd);
						lNuovaPropostaAtto2CompletaDataSource.getData(lRecordToLoad, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									final Record detailRecord = response.getData()[0];
									boolean hasDatiSensibili = detailRecord.getAttributeAsBoolean("flgDatiSensibili") != null && detailRecord.getAttributeAsBoolean("flgDatiSensibili");
									detailRecord.setAttribute("idModello", lRecordEvento != null && lRecordEvento.getAttribute("idModAssDocTask") != null ? lRecordEvento.getAttribute("idModAssDocTask") : "");
									detailRecord.setAttribute("nomeModello", lRecordEvento != null && lRecordEvento.getAttribute("nomeModAssDocTask") != null ? lRecordEvento.getAttribute("nomeModAssDocTask") : "");
									detailRecord.setAttribute("displayFilenameModello", lRecordEvento != null && lRecordEvento.getAttribute("displayFilenameModAssDocTask") != null ? lRecordEvento.getAttribute("displayFilenameModAssDocTask") : "");
									if(hasDatiSensibili) {
										generaDispositivoDaModelloVersIntegrale(detailRecord, new ServiceCallback<Record>() {
											
											@Override
											public void execute(final Record recordPreview) {
												generaDispositivoDaModelloVersConOmissis(detailRecord, new ServiceCallback<Record>() {
													
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
										generaDispositivoDaModelloVersIntegrale(detailRecord, new ServiceCallback<Record>() {
											
											@Override
											public void execute(Record recordPreview) {
												preview(recordPreview);
											}
										});
									}
								}
							}
						});
					}
				}
			});
		}
	}
	
	public void generaDispositivoDaModelloVersIntegrale(Record record) {
		generaDispositivoDaModelloVersIntegrale(record, null);
	}
	
	public void generaDispositivoDaModelloVersIntegrale(Record record, final ServiceCallback<Record> callback) {
		generaDispositivoDaModello(record, true, callback);
	}
	
	public void generaDispositivoDaModelloVersConOmissis(Record record) {
		generaDispositivoDaModelloVersConOmissis(record, null);
	}
	
	public void generaDispositivoDaModelloVersConOmissis(Record record, final ServiceCallback<Record> callback) {
		generaDispositivoDaModello(record, false, callback);
	}
	
	public void generaDispositivoDaModello(Record record, boolean flgMostraDatiSensibili, final ServiceCallback<Record> callback) {
		
		record.setAttribute("flgMostraDatiSensibili", flgMostraDatiSensibili);
		
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
	
	protected boolean isRecordAbilToRiepilogoFirmeVisti(ListGridRecord record) {
		if(AurigaLayout.isAttivaNuovaPropostaAtto2Completa()) {
			return (record.getAttributeAsString("uriRiepilogoFirmeVisti") != null && !"".equals(record.getAttributeAsString("uriRiepilogoFirmeVisti"))) ||
					(record.getAttributeAsString("idDocRiepilogoFirmeVisti") != null && !"".equals(record.getAttributeAsString("idDocRiepilogoFirmeVisti")));			
		}
		return false;
	}
	
	protected ControlListGridField buildRiepilogoFirmeVistiButtonField() {
		
		ControlListGridField riepilogoFirmeVistiButtonField = new ControlListGridField("riepilogoFirmeVistiButton");
		riepilogoFirmeVistiButtonField.setAttribute("custom", Boolean.TRUE);
		riepilogoFirmeVistiButtonField.setShowHover(Boolean.TRUE);
		riepilogoFirmeVistiButtonField.setCanReorder(Boolean.FALSE);
		riepilogoFirmeVistiButtonField.setCellFormatter(new CellFormatter() {
	
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				if(isRecordAbilToRiepilogoFirmeVisti(record)) {
					return buildImgButtonHtml("file/attestato.png");
				} 
				return null;
			}
		});
		riepilogoFirmeVistiButtonField.setHoverCustomizer(new HoverCustomizer() {
	
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {						
				if(isRecordAbilToRiepilogoFirmeVisti(record)) {
					return "Foglio riepilogo";
				} 
				return null;
			}
		});
		riepilogoFirmeVistiButtonField.addRecordClickHandler(new RecordClickHandler() {
	
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord record = event.getRecord();									
				if(isRecordAbilToRiepilogoFirmeVisti(record)) {
					Menu menu = buildRiepilogoFirmeVistiMenu(record);
					menu.showContextMenu();
				}
			}
		});		
		return riepilogoFirmeVistiButtonField;
	}
	
	public Menu buildRiepilogoFirmeVistiMenu(final Record record) {
		final Menu riepilogoFirmeVistiMenu = new Menu();
		if (record.getAttribute("uriRiepilogoFirmeVisti") != null && !"".equals(record.getAttribute("uriRiepilogoFirmeVisti"))) {
			MenuItem visualizzaRiepilogoFirmeVistiMenuItem = new MenuItem("Visualizza", "file/preview.png");
			visualizzaRiepilogoFirmeVistiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {					
					final String uriRiepilogoFirmeVisti = record.getAttribute("uriRiepilogoFirmeVisti");
					final String nomeFileRiepilogoFirmeVisti = record.getAttribute("nomeFileRiepilogoFirmeVisti");					
					Record lRecordFileRiepilogoFirmeVisti = new Record();
					lRecordFileRiepilogoFirmeVisti.setAttribute("uri", uriRiepilogoFirmeVisti);
					lRecordFileRiepilogoFirmeVisti.setAttribute("nomeFile", nomeFileRiepilogoFirmeVisti);
					new OneCallGWTRestDataSource("ProtocolloDataSource").executecustom("getInfoFromFile", lRecordFileRiepilogoFirmeVisti, new DSCallback() {
						
						@Override
						public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
							if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record result = dsResponse.getData()[0];
								InfoFileRecord infoFileRiepilogoFirmeVisti = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
								PreviewControl.switchPreview(uriRiepilogoFirmeVisti, false, infoFileRiepilogoFirmeVisti, "FileToExtractBean", nomeFileRiepilogoFirmeVisti);					
							}
						}
					});		
				}
			});
			riepilogoFirmeVistiMenu.addItem(visualizzaRiepilogoFirmeVistiMenuItem);
		}		
		if (record.getAttribute("idDocRiepilogoFirmeVisti") != null && !"".equals(record.getAttribute("idDocRiepilogoFirmeVisti"))) {
			MenuItem rigeneraRiepilogoFirmeVistiMenuItem = new MenuItem("Rigenera", "protocollazione/generaDaModello.png");
			rigeneraRiepilogoFirmeVistiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String idUd = record.getAttribute("idUd");
					final String idDocRiepilogoFirmeVisti = record.getAttribute("idDocRiepilogoFirmeVisti");
					final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
					lNuovaPropostaAtto2CompletaDataSource.addParam("idProcess", record.getAttribute("idProcedimento"));
					lNuovaPropostaAtto2CompletaDataSource.addParam("taskName", null); // se non lo passo in teoria carica l'ultimo task
					if (idUd != null && !idUd.equalsIgnoreCase("")) {
						Record lRecordToLoad = new Record();
						lRecordToLoad.setAttribute("idUd", idUd);
						lNuovaPropostaAtto2CompletaDataSource.getData(lRecordToLoad, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									final Record detailRecord = response.getData()[0];
									detailRecord.setAttribute("idModello", record.getAttribute("idModRiepilogoFirmeVisti") != null ? record.getAttribute("idModRiepilogoFirmeVisti") : "");
									detailRecord.setAttribute("nomeModello", record.getAttribute("nomeModRiepilogoFirmeVisti") != null ? record.getAttribute("nomeModRiepilogoFirmeVisti") : "");
									detailRecord.setAttribute("displayFilenameModello", record.getAttribute("displayFilenameModRiepilogoFirmeVisti") != null ? record.getAttribute("displayFilenameModRiepilogoFirmeVisti") : "");
									generaRiepilogoFirmeVistiDaModello(detailRecord, new ServiceCallback<Record>() {
										
										@Override
										public void execute(final Record recordPreview) {
											previewWithCallback(recordPreview, new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record object) {													
													Record recordToVers = new Record();
													recordToVers.setAttribute("idFile", idDocRiepilogoFirmeVisti);
													recordToVers.setAttribute("uri", recordPreview.getAttributeAsString("uri"));
													recordToVers.setAttribute("nomeFile", recordPreview.getAttributeAsString("nomeFile"));		
													recordToVers.setAttribute("infoFile", recordPreview.getAttributeAsRecord("infoFile"));		
													Layout.showWaitPopup("Salvataggio file in corso...");				
													lNuovaPropostaAtto2CompletaDataSource.executecustom("versionaDocumento", recordToVers, new DSCallback() {
														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															Layout.hideWaitPopup();
															if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
																onClickRefreshListButton();
															}									
														}		
													});
												}
											});
											
											
										}
									});
								}
							}
						});				
					}					
				}
			});
			riepilogoFirmeVistiMenu.addItem(rigeneraRiepilogoFirmeVistiMenuItem);
		}		
		return riepilogoFirmeVistiMenu;
	}
	
	public void generaRiepilogoFirmeVistiDaModello(Record record, final ServiceCallback<Record> callback) {

		Layout.showWaitPopup("Generazione riepilogo firme e visti in corso...");				
		final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lNuovaPropostaAtto2CompletaDataSource.executecustom("generaRiepilogoFirmeVistiDaModello", record, new DSCallback() {
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
	
	protected ControlListGridField buildFileAssociatiButtonField() {
		
		ControlListGridField fileAssociatiButtonField = new ControlListGridField("fileAssociatiButton");  
		fileAssociatiButtonField.setAttribute("custom", true);	
		fileAssociatiButtonField.setShowHover(true);		
		fileAssociatiButtonField.setCanReorder(false);
		fileAssociatiButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {							
				if(record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {						
					return buildImgButtonHtml("archivio/file.png");
				}
				return null;
			}
		});
		fileAssociatiButtonField.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"))) {						
					return "File";
				}
				return null;
			}
		});		
		fileAssociatiButtonField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(event.getRecord().getAttributeAsString("idUd") != null && !"".equals(event.getRecord().getAttributeAsString("idUd"))) {						
					GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
					lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", event.getRecord().getAttributeAsString("idUd"));
					lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {					
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {						
								final Record detailRecord = response.getData()[0];	
								final Menu fileAssociatiMenu = buildFileAssociatiMenu(detailRecord);
								fileAssociatiMenu.showContextMenu();
							}
						}
					});
				}
			}
		});		
		return fileAssociatiButtonField;
	}
	
	public Menu buildFileAssociatiMenu(final Record detailRecord) {
		
		Menu fileAssociatiMenu = new Menu();
		fileAssociatiMenu.setEmptyMessage("Nessun file su cui<br/>hai diritti di accesso");
		
		Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
		
		//File primario senza omissis
		if ((detailRecord.getAttributeAsString("uriFilePrimario") != null
				&& !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") == null) || "".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			MenuItem filePrimarioIntegraleMenuItem = new MenuItem("File primario - " + detailRecord.getAttributeAsString("nomeFilePrimario"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioIntegraleMenuItem, true);
			fileAssociatiMenu.addItem(filePrimarioIntegraleMenuItem);
			
		}
			
		//File primario con versione omissis
		else if((detailRecord.getAttributeAsString("uriFilePrimario") != null
				&& !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") != null) && !"".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			//File primario integrale
			MenuItem filePrimarioIntegraleMenuItem = new MenuItem("File primario (vers. integrale) - " + detailRecord.getAttributeAsString("nomeFilePrimario"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioIntegraleMenuItem, true);
			fileAssociatiMenu.addItem(filePrimarioIntegraleMenuItem);
			
			//File primario omissis
			MenuItem filePrimarioOmissisMenuItem = new MenuItem("File primario (vers. con omissis) - " +filePrimarioOmissis.getAttributeAsString("nomeFile"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioOmissisMenuItem, false);
			fileAssociatiMenu.addItem(filePrimarioOmissisMenuItem);
		}
		
		//File primario solo omissis
		else if((detailRecord.getAttributeAsString("uriFilePrimario") == null
				|| "".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") != null) && !"".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			MenuItem filePrimarioOmissisMenuItem = new MenuItem("File primario (vers. con omissis) - " + filePrimarioOmissis.getAttributeAsString("nomeFile"));
			buildFilePrimarioMenuItem(detailRecord, filePrimarioOmissisMenuItem, false);
			fileAssociatiMenu.addItem(filePrimarioOmissisMenuItem);
		}
		
		//Aggiungo al menu gli allegati
		RecordList listaAllegati = detailRecord.getAttributeAsRecordList("listaAllegati");
		
		if (listaAllegati != null) {
			for (int n = 0; n < listaAllegati.getLength(); n++) {
				final int nroAllegato = n;
				final Record allegatoRecord = listaAllegati.get(n);
				
				boolean flgParteDispositivo = allegatoRecord.getAttribute("flgParteDispositivo") != null && "true".equals(allegatoRecord.getAttribute("flgParteDispositivo"));					
				
				//Allegato senza omissis
				if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					if(flgParteDispositivo) {
						fileAllegatoIntegraleMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato, true);
					fileAssociatiMenu.addItem(fileAllegatoIntegraleMenuItem);
				}
				
				//Entrambi versioni di allegati
				else if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					//Allegato integrale
					MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. integrale) - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					if(flgParteDispositivo) {
						fileAllegatoIntegraleMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato,true);
					fileAssociatiMenu.addItem(fileAllegatoIntegraleMenuItem);
					
					//Alegato omissis
					MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
					if(flgParteDispositivo) {
						fileAllegatoOmissisMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false);
					fileAssociatiMenu.addItem(fileAllegatoOmissisMenuItem);
				}
				
				//Allegato solo omissis
				else if((allegatoRecord.getAttributeAsString("uriFileAllegato")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
					if(flgParteDispositivo) {
						fileAllegatoOmissisMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false);
					fileAssociatiMenu.addItem(fileAllegatoOmissisMenuItem);
				}
			}
		}
		
		return fileAssociatiMenu;
	}

	private void buildFilePrimarioMenuItem(final Record detailRecord, MenuItem filePrimarioMenuItem, final boolean fileIntegrale) {
		
		Menu operazioniFilePrimarioSubmenu = new Menu();
		
		InfoFileRecord lInfoFileRecord;
		//file primario integrale
		if(fileIntegrale){
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(detailRecord.getAttributeAsObject("infoFile"));
		}
		//file primario omissis
		else {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(detailRecord.getAttributeAsRecord("filePrimarioOmissis").getAttributeAsObject("infoFile"));
		}
		
		MenuItem visualizzaFilePrimarioMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
		visualizzaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
			@Override
			public void onClick(MenuItemClickEvent event) {
				if(fileIntegrale) {
					String idUd = detailRecord.getAttributeAsString("idUd");
					String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
					String display = detailRecord.getAttributeAsString("nomeFilePrimario");
					String uri = detailRecord.getAttributeAsString("uriFilePrimario");
					String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
					Object infoFile = detailRecord.getAttributeAsObject("infoFile");
					visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
				}
				else {
					String idUd = detailRecord.getAttributeAsString("idUd");
					final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
					String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
					String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
					String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
					String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
					Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
					visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
				}
			}
		});
		visualizzaFilePrimarioMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
		operazioniFilePrimarioSubmenu.addItem(visualizzaFilePrimarioMenuItem);
		
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
			MenuItem visualizzaEditFilePrimarioMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(),
					"file/previewEdit.png");
			visualizzaEditFilePrimarioMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(fileIntegrale) {
								String idUd = detailRecord.getAttributeAsString("idUd");
								String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
								String display = detailRecord.getAttributeAsString("nomeFilePrimario");
								String uri = detailRecord.getAttributeAsString("uriFilePrimario");
								String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
								Object infoFile = detailRecord.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
							else {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
								String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
								String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
								String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
								String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
								Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
						}
					});
			visualizzaEditFilePrimarioMenuItem
					.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			operazioniFilePrimarioSubmenu.addItem(visualizzaEditFilePrimarioMenuItem);
		}
		
		MenuItem scaricaFilePrimarioMenuItem = new MenuItem("Scarica", "file/download_manager.png");
		// Se è firmato P7M
		if (lInfoFileRecord != null && lInfoFileRecord.isFirmato()
				&& lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
			Menu scaricaFilePrimarioSubMenu = new Menu();
			MenuItem firmato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
				}
			});
			MenuItem sbustato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						Object infoFile = detailRecord.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
				}
			});
			scaricaFilePrimarioSubMenu.setItems(firmato, sbustato);
			scaricaFilePrimarioMenuItem.setSubmenu(scaricaFilePrimarioSubMenu);
		} else {
			scaricaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
				}
			});
		}
		operazioniFilePrimarioSubmenu.addItem(scaricaFilePrimarioMenuItem);
	
		filePrimarioMenuItem.setSubmenu(operazioniFilePrimarioSubmenu);
	}
		
	private void buildAllegatiMenuItem(final Record detailRecord, final Record allegatoRecord, MenuItem fileAllegatoMenuItem,final int nroAllegato, final boolean allegatoIntegrale) {
		
		Menu operazioniFileAllegatoSubmenu = new Menu();
		
		InfoFileRecord lInfoFileRecord;
		//se è un allegato integrale
		if(allegatoIntegrale) {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
		}
		//versione con omissis
		else {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFileOmissis"));
		}
		
		MenuItem visualizzaFileAllegatoMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
		visualizzaFileAllegatoMenuItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						//se è un allegato integrale
						if(allegatoIntegrale) {
							String idUd = detailRecord.getAttributeAsString("idUd");
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
							String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
							String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
							String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
							Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
							visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
						}
						//versione con omissis
						else{
							String idUd = detailRecord.getAttributeAsString("idUd");
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
							String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
							String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
							String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
							Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
							visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
						}
					}
				});
		visualizzaFileAllegatoMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
		operazioniFileAllegatoSubmenu.addItem(visualizzaFileAllegatoMenuItem);
		
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
			MenuItem visualizzaEditFileAllegatoMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(), "file/previewEdit.png");
			visualizzaEditFileAllegatoMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							// se è un allegato integrale
							if (allegatoIntegrale) {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati")
										.get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
								String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
								Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
							// versione con omissis
							else {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati")
										.get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
								String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
								Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
						}
					});
			visualizzaEditFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			operazioniFileAllegatoSubmenu.addItem(visualizzaEditFileAllegatoMenuItem);
		}
		
		MenuItem scaricaFileAllegatoMenuItem = new MenuItem("Scarica", "file/download_manager.png");
		// Se è firmato P7M
		if (lInfoFileRecord != null && lInfoFileRecord.isFirmato()
				&& lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
			Menu scaricaFileAllegatoSubMenu = new Menu();
			MenuItem firmato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					//se è un allegato integrale
					if(allegatoIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
						String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					//versione con omissis
					else{
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
						String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					
				}
			});
			MenuItem sbustato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
				@Override
				public void onClick(MenuItemClickEvent event) {
					//se è un allegato integrale
					if(allegatoIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
						String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
						Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
					//versione con omissis
					else{
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
						String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
						Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
				}
			});
			scaricaFileAllegatoSubMenu.setItems(firmato, sbustato);
			scaricaFileAllegatoMenuItem.setSubmenu(scaricaFileAllegatoSubMenu);
		} else {
			scaricaFileAllegatoMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(allegatoIntegrale) {
								//se è un allegato integrale
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
								String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
								scaricaFile(idUd, idDoc, display, uri, remoteUri);
							}
							//versione con omissis
							else{
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
								String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
								scaricaFile(idUd, idDoc, display, uri, remoteUri);
							}
						}
					});
		}
		operazioniFileAllegatoSubmenu.addItem(scaricaFileAllegatoMenuItem);
	
		fileAllegatoMenuItem.setSubmenu(operazioniFileAllegatoSubmenu);
	}
	
	protected boolean isRecordAbilToScaricaFileCompletiXAtti(ListGridRecord record) {
		if(AurigaLayout.isPrivilegioAttivo("ATT/SDC")) {
			return record.getAttributeAsString("idUd") != null && !"".equals(record.getAttributeAsString("idUd"));			
		}
		return false;
	}
	
	protected ControlListGridField buildScaricaFileCompletiXAttiButtonField() {
		
		ControlListGridField scaricaFileCompletiXAttiButtonField = new ControlListGridField("scaricaFileCompletiXAttiButton");  
		scaricaFileCompletiXAttiButtonField.setAttribute("custom", true);	
		scaricaFileCompletiXAttiButtonField.setShowHover(true);		
		scaricaFileCompletiXAttiButtonField.setCanReorder(false);
		scaricaFileCompletiXAttiButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {							
				if(isRecordAbilToScaricaFileCompletiXAtti(record)) {
					return buildImgButtonHtml("buttons/export.png");
				}
				return null;
			}
		});
		scaricaFileCompletiXAttiButtonField.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(isRecordAbilToScaricaFileCompletiXAtti(record)) {	
					return "Scarica documentazione completa";
				}
				return null;
			}
		});		
		scaricaFileCompletiXAttiButtonField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord listRecord = event.getRecord();
				if(isRecordAbilToScaricaFileCompletiXAtti(listRecord)) {						
					GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
					lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", listRecord.getAttributeAsString("idUd"));
					lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {					
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {						
								final Record detailRecord = response.getData()[0];														
								// devo fare uno zip con tutti i file: testo, allegati, foglio riepilogo, emandamenti e pareri emedamento
								final GWTRestDataSource datasource = new GWTRestDataSource("ProtocolloDataSource");
								if(detailRecord.getAttribute("segnatura") != null && !"".equals(detailRecord.getAttribute("segnatura"))) {
									datasource.extraparam.put("segnatura", detailRecord.getAttribute("segnatura"));
								} else {
									datasource.extraparam.put("segnatura", listRecord.getAttribute("estremiPropostaUD"));
								}
								datasource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
								datasource.extraparam.put("operazione", "scaricaCompletiXAtti");
								datasource.setForceToShowPrompt(false);
								
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_list_downloadDocsZip_wait(), "", MessageType.WARNING));
								datasource.executecustom("generateDocsZip", detailRecord, new DSCallback() {
				
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {		
											Record result = response.getData()[0];
											String message = result.getAttribute("message");
											if (message != null) {
												Layout.addMessage(new MessageBean(message, "", MessageType.ERROR));
											} else if (result.getAttribute("errorCode") == null || result.getAttribute("errorCode").isEmpty()) {
												String zipUri = response.getData()[0].getAttribute("storageZipRemoteUri");
												String zipName = response.getData()[0].getAttribute("zipName");							
												scaricaFile(listRecord.getAttribute("idUdFolder"), "", zipName, zipUri, zipUri);															
											}
										}
									}
								});
							}
						}
					});													
				}
			}
		});		
		return scaricaFileCompletiXAttiButtonField;
	}
	
	protected ControlListGridField buildPresenzeVotiButtonField() {
		
		ControlListGridField presenzeVotiButtonField = new ControlListGridField("presenzeVotiButton");  
		presenzeVotiButtonField.setAttribute("custom", true);	
		presenzeVotiButtonField.setShowHover(true);		
		presenzeVotiButtonField.setCanReorder(false);
		presenzeVotiButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				
				return buildImgButtonHtml("delibere/presenze_voti.png");
			}
		});
		presenzeVotiButtonField.setHoverCustomizer(new HoverCustomizer() {	
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
		
				return "Presenze e voti";
			}
		});		
		presenzeVotiButtonField.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				onClickPresenzeVotiButton(event.getRecord());				
			}
		});		
		return presenzeVotiButtonField;
	}
	
	public void onClickPresenzeVotiButton(final ListGridRecord record) {
		
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		Record recordToSave = new Record();
		recordToSave.setAttribute("idSeduta", getIdSeduta());
		recordToSave.setAttribute("idUdArgomentoOdG", record.getAttribute("idUd"));
		lGWTRestDataSource.executecustom("getPresenzeVotiSuContOdG", recordToSave, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					
					Record recordToGet = dsResponse.getData()[0];
					String title = "Presenze e voti su "+ record.getAttributeAsString("tipo") +" / " + record.getAttributeAsString("nrOrdineOdg");
					PresenzaVotoArgomentoWindow lPresenzePrimaConvocazioneWindow = new PresenzaVotoArgomentoWindow("presenze_prima_convocazione_window",title,recordToGet,organoCollegiale);
					lPresenzePrimaConvocazioneWindow.show();
				}
			}
		});
	}
	
	public void visualizzaFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		PreviewControl.switchPreview(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
	}

	public void visualizzaEditFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), idUd
				,rotazioneTimbroPref,posizioneTimbroPref);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadTimbraDefault");
		lGwtRestService.call(lFileDaTimbrareBean, new ServiceCallback<Record>() {

			@Override
			public void execute(Record lOpzioniTimbro) {
				boolean timbroEnabled = detailRecord != null && detailRecord.getAttribute("flgTipoProv") != null
						&& !"".equals(detailRecord.getAttribute("flgTipoProv")) && detailRecord.getAttribute("idUd") != null
						&& !"".equals(detailRecord.getAttribute("idUd"));
				PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, Boolean.valueOf(remoteUri), timbroEnabled, lOpzioniTimbro) {

					@Override
					public void uploadCallBack(String filePdf, String uriPdf, String record) {

					}
				};
				previewDocWindow.show();
			}
		});
	}

	public void scaricaFile(String idUd, String idDoc, String display, String uri, String remoteUri) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	public void scaricaFileSbustato(String idUd, String idDoc, String display, String uri, String remoteUri, Object infoFile) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", remoteUri);
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}
	
	public void preview(final Record recordPreview) {
		previewWithCallback(recordPreview, null);
	}
	
	public void previewWithCallback(final Record recordPreview, final ServiceCallback<Record> closeCallback) {
		if (recordPreview.getAttribute("nomeFile") != null && recordPreview.getAttribute("nomeFile").endsWith(".pdf")) {
			new PreviewWindow(recordPreview.getAttribute("uri"), false, new InfoFileRecord(recordPreview.getAttributeAsRecord("infoFile")), "FileToExtractBean",	recordPreview.getAttribute("nomeFile")) {
			
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
		
	public void addToRecent(String idUd, String idDoc) {
		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

				}
			});
		}
	}
	
	private void refreshNroOrdineGiorno() {
		grid.deselectAllRecords();
		int n = 1;
		for (ListGridRecord record : grid.getRecords()) {
			record.setAttribute("nrOrdineOdg", n);
			n++;
		}
		updateGridItemValue();
	}

	public String getIdSeduta() {
		return null;
	}
	
	public RecordList getEsitiXtipoArgomento() {
		return new RecordList();
	}
	
	private String getIdNodoRicerca() {
		return "/";
	}
	
	protected Object formatDateForSorting(ListGridRecord record, String fieldName) {
		String value = record != null ? record.getAttributeAsString(fieldName) : null;
		return value != null && !"".equals(value) ? DateUtil.parseInput(value) : null;
	}
	
	public String manageDateCellFormat(Object value, ListGridField field, ListGridRecord record) {
		if (value==null) return null;
		String lStringValue = value.toString();	
		if(field.getDateFormatter() != null && field.getDateFormatter().equals(DateDisplayFormat.TOEUROPEANSHORTDATE)) {									
			lStringValue = (lStringValue != null && !"".equals(lStringValue)) ? lStringValue.substring(0, 10) : "";
		}
		return lStringValue;
	}
	
	protected String buildImgButtonHtml(String src) {
		return "<div style=\"cursor:pointer\" align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
	}
	
	protected String buildIconHtml(String src) {
		return "<div align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";
	}
	
	public void callExecAtt(final Record record, final ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lCallExecAttDatasource = new GWTRestService<Record, Record>("CallExecAttDatasource");
		lCallExecAttDatasource.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record output) {
				if (callback != null) {
					callback.execute(output);
				}
			}
		});
	}
	
	public String getFinalitaAttiOdgLookupArchivio() {
		String finalita = "IMPORT_ATTI_IN_ODG_" + organoCollegiale.toUpperCase();
		if (codCircoscrizione != null && !"".equalsIgnoreCase(codCircoscrizione)) {
			finalita += "_CIRC_" + codCircoscrizione; 
		}
		return finalita;
	}
	
	public class AttiOdgMultiLookupArchivio extends LookupArchivioPopup {

		private RecordList multiLookupList = new RecordList(); 

		public AttiOdgMultiLookupArchivio(Record record) {
			super(record, null, false);
		}
		
		public AttiOdgMultiLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, false);
		}
		
		@Override
		public String getFinalita() {
			return getFinalitaAttiOdgLookupArchivio();
		}

		@Override
		public void manageMultiLookupBack(Record record) {
			RecordList listaRecordOdgPresenti = instance.getData();
			int nrOrdineOdgMax = 1;
			if (listaRecordOdgPresenti != null && !listaRecordOdgPresenti.isEmpty()) {
				for (int i = 0; i < listaRecordOdgPresenti.getLength(); i++) {
					Record recordOdg = listaRecordOdgPresenti.get(i);
					int nrOrdineOdg = recordOdg.getAttribute("nrOrdineOdg") != null ? Integer.parseInt(recordOdg.getAttribute("nrOrdineOdg")): 0;
					if (nrOrdineOdg > nrOrdineOdgMax) {
						nrOrdineOdgMax = nrOrdineOdg;
					}
				}
			}
			
			Record recordToInsert = new Record();
			recordToInsert.setAttribute("idUd", record.getAttribute("idUdFolder"));
			recordToInsert.setAttribute("tipo", record.getAttribute("tipo"));
			//recordToInsert.setAttribute("nomeIconaTipo", record.getAttribute(""));
			recordToInsert.setAttribute("estremiPropostaUD", record.getAttribute("segnatura"));
			recordToInsert.setAttribute("nrOrdineOdg", nrOrdineOdgMax + 1);
			recordToInsert.setAttribute("oggetto", record.getAttribute("oggetto"));
			recordToInsert.setAttribute("strutturaProponente", record.getAttribute("uoProponente"));
			//recordToInsert.setAttribute("nominativoProponente", "");
			//recordToInsert.setAttribute("dtInoltro", record.getAttribute(""));
			//recordToInsert.setAttribute("flgInoltro", record.getAttribute(""));
			recordToInsert.setAttribute("flgAggiunto", "1");
			recordToInsert.setAttribute("nroAllegati", record.getAttribute("nroDocConFile") != null
					&& !"".equals(record.getAttribute("nroDocConFile")) ? record.getAttribute("nroDocConFile") : "0");
			//recordToInsert.setAttribute("estremiPropostaDelibera", record.getAttribute(""));
			//recordToInsert.setAttribute("idUdPropostaDelibera", record.getAttribute(""));
			recordToInsert.setAttribute("flgCanRemove", "1");
			
			multiLookupList.add(recordToInsert);
			instance.addData(recordToInsert);
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			String idUdToRemove = record.getAttribute("id") != null ? record.getAttribute("id") : "";
			int posToRemove = -1;
			if (multiLookupList != null) {
				for (int i = 0; i < multiLookupList.getLength(); i++) {
					if (idUdToRemove.equalsIgnoreCase(multiLookupList.get(i).getAttribute("idUd"))) {
						posToRemove = i;
					}
				}
				if (posToRemove > -1) {
					multiLookupList.removeAt(posToRemove);
				}
				
				posToRemove = -1;
				if (instance.getData() != null) {
					RecordList listaOdg = instance.getData();
					for (int i = 0; i < listaOdg.getLength(); i++) {
						Record odg = listaOdg.get(i);
						if (odg.getAttribute("idUd") != null && idUdToRemove.equalsIgnoreCase(odg.getAttribute("idUd")) && odg.getAttribute("flgCanRemove") != null && "1".equalsIgnoreCase(odg.getAttribute("flgCanRemove"))) {
							posToRemove = i;
						}
					}
					
					if (posToRemove > -1) {
						listaOdg.removeAt(posToRemove);
					}
				}
			}
		}

		@Override
		public void manageLookupBack(Record record) {
		
		}
	}
	
	public Boolean showButtons() {
		return getIdSeduta() != null && !"".equalsIgnoreCase(getIdSeduta());
	}
	
	public boolean fromStoricoSeduta() {
		return false;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		
		super.setCanEdit(canEdit);
		
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("aggiunta_proposta")) {
					if(showButtons() && !fromStoricoSeduta()) {
						member.show();	
					} else {
						member.hide();						
					}
				}
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("aggiunta_atto")) {
					if(showButtons() && !fromStoricoSeduta()) {
						member.show();	
					} else {
						member.hide();						
					}		
				}
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("ricarica")) {
					if(showButtons()) {
						member.show();	
					} else {
						member.hide();						
					}		
				}
			}		
			layoutListaSelectItem.show();
			saveLayoutListaButton.show();
			getGrid().setCanReorderRecords(canEdit);
			redrawRecordButtons();
		}
	}

}