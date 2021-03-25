package it.eng.auriga.ui.module.layout.client.scrivania;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.ArchivioDetail;
import it.eng.auriga.ui.module.layout.client.archivio.ArchivioFilter;
import it.eng.auriga.ui.module.layout.client.archivio.ArchivioList;
import it.eng.auriga.ui.module.layout.client.modelliDoc.ModelliDocDetail;
import it.eng.auriga.ui.module.layout.client.modelliDoc.ModelliDocList;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioPostaElettronica;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaDetail;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaFilter;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaList;
import it.eng.auriga.ui.module.layout.client.registrazioniEmergenza.RegistrazioniEmergenzaList;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.RichiestaAccessoAttiDetail;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.RichiestaAccessoAttiFilter;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.RichiestaAccessoAttiList;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomSimpleTree;

public class ScrivaniaTree extends CustomSimpleTree {

	public ScrivaniaTree(String nomeEntita) {

		super(nomeEntita);

		TreeGridField idFolder = new TreeGridField("idFolder");
		idFolder.setHidden(true);

		TreeGridField nome = new TreeGridField("nome");
		nome.setShowHover(true);
		nome.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (isAggiornamentoNroContenutiAttivo()) {
					Boolean flgContenuti = record.getAttributeAsBoolean("flgContenuti");
					String nroContenuti = record.getAttributeAsString("nroContenuti");
					if (flgContenuti != null && flgContenuti) {
						if (nroContenuti != null && !"".equals(nroContenuti) && !"0".equals(nroContenuti)) {
							return "<b>" + value + " (" + nroContenuti + ")</b></div>";
						} else {
							return value + " (0)";
						}
					}
				}
				return "" + value;
			}
		});
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			addBodyKeyPressHandler(new BodyKeyPressHandler() {
	            
	            @Override
	            public void onBodyKeyPress(BodyKeyPressEvent event) {
	                if (EventHandler.getKey().equalsIgnoreCase("Enter") == true) {
						Integer focusRow2 = getFocusRow();
						ListGridRecord record = getRecord(focusRow2);
						manageOnCellClick(record, focusRow2);
	//                    System.out.println("ENTER PRESSED !!!!" + listGrid.getSelectedRecord());
	                }
	            }
	        });		
		}
		nome.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String nome = record.getAttributeAsString("nome");
				String dettagli = record.getAttributeAsString("dettagli");
				if (dettagli != null && !"".equals(dettagli)) {
					return nome + "<br/>" + dettagli;
				}
				return nome;
			}
		});

		setFields(idFolder, nome);
	}

	public boolean isAggiornamentoNroContenutiAttivo() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_CONTEGGIO_NODI_SV");
	}

	public void aggiornaNroContenutiScrivania() {
		
		if (isAggiornamentoNroContenutiAttivo()) {
			if(instance != null && instance.getTree() != null) { 
				TreeNode[] nodes = instance.getTree().getAllNodes();
				for (int i = 0; i < nodes.length; i++) {
					TreeNode node = nodes[i];
					aggiornaNroContenutiNodo(node);
				}
			}
		}
	}

	public void aggiornaNroContenutiNodo(final TreeNode node) {
		
		((GWTRestDataSource) getDataSource()).setForceToShowPrompt(false);
		if (isAggiornamentoNroContenutiAttivo()) {
			Boolean flgContenuti = node.getAttributeAsBoolean("flgContenuti");
			if (flgContenuti != null && flgContenuti) {
				final String nroContenuti = (node.getAttribute("nroContenuti") != null && !"".equals(node.getAttribute("nroContenuti"))) ? node
						.getAttribute("nroContenuti") : "0";
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						getDataSource().performCustomOperation("getNroContenutiNodo", node, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Record lRecord = response.getData()[0];
									final String nroContenutiAggiornato = (lRecord.getAttribute("nroContenuti") != null && !"".equals(lRecord
											.getAttribute("nroContenuti"))) ? lRecord.getAttribute("nroContenuti") : "0";
									if (!nroContenuti.equals(nroContenutiAggiornato)) {
										node.setAttribute("nroContenuti", nroContenutiAggiornato);
										layout.markForRedraw();
									}

								}
							}
						}, new DSRequest());
					}
				});
			}
		}
	}
	
	// Commento questo metodo per disabilitare il conteggio automatico dei contenuti dopo il caricamento dell'albero, visto che la store me li da già 
//	public void manageCustomTreeNode(final TreeNode node) {
//		aggiornaNroContenutiNodo(node);
//	}

	@Override
	public void manageOnCellClick(Record record, int recordNum) {

		String idNode = record.getAttributeAsString("idNode");
		String idNodeSelected = getSelectedRecord() != null ? getSelectedRecord().getAttribute("idNode") : null;

		if (idNodeSelected == null || !idNodeSelected.equals(idNode)) {

			String nomeNodo = record.getAttributeAsString("nome") != null ? record.getAttributeAsString("nome") : "";
			String tipoNodo = record.getAttributeAsString("tipo") != null ? record.getAttributeAsString("tipo") : "";
			Boolean flgMultiselezione = record.getAttributeAsBoolean("flgMultiselezione");
			String codSezione = record.getAttributeAsString("codSezione");
			String azione = record.getAttributeAsString("azione");

			String searchNodeLabelContents = nomeNodo;
			Record rec = record;
			while (rec.getAttributeAsString("parentId") != null && !"".equals(rec.getAttributeAsString("parentId"))) {
				Record parent = getData().findById(rec.getAttributeAsString("parentId"));
				searchNodeLabelContents = parent.getAttributeAsString("nome") + "&nbsp;/&nbsp;" + searchNodeLabelContents;
				rec = parent;
			}

			if (idNode.startsWith("E.")) {

				if (azione != null && !"".equals(azione)) {

					String criteriAvanzati = record.getAttributeAsString("criteriAvanzati");

					((ScrivaniaLayout) layout).setSearchNodeLabelContents(searchNodeLabelContents);
					((ScrivaniaLayout) layout).setAzione(azione);

					String classifica = idNode.substring(2);
					String idUtenteModPec = record.getAttributeAsString("idUtenteModPec");

					((ScrivaniaLayout) layout).resettaParametriRicerca();
					((ScrivaniaLayout) layout).setIdNode(idNode);
					((ScrivaniaLayout) layout).setNomeNodo(nomeNodo);
					((ScrivaniaLayout) layout).setTipoNodo("E");
					((ScrivaniaLayout) layout).setCriteriAvanzati(criteriAvanzati);
					((ScrivaniaLayout) layout).setClassifica(classifica);
					((ScrivaniaLayout) layout).setIdUtenteModPec(idUtenteModPec);

					Map<String, String> extraparam = new HashMap<String, String>();
					extraparam.put("classifica", classifica);
					extraparam.put("idUtenteModPec", idUtenteModPec);

					PostaElettronicaFilter postaElettronicaFilter = null;
					if (layout.getFilter() instanceof PostaElettronicaFilter) {
						postaElettronicaFilter = ((PostaElettronicaFilter) layout.getFilter());
						postaElettronicaFilter.updateShowFilter(extraparam);
						// postaElettronicaFilter.markForRedraw();
					} else {
						postaElettronicaFilter = new PostaElettronicaFilter("posta_elettronica", extraparam);
					}

					if (!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {

						layout.changeLayout("posta_elettronica", new GWTRestDataSource("AurigaPostaElettronicaDataSource", "idEmail", FieldType.TEXT),
								new PostaElettronicaFilter("posta_elettronica", extraparam), new PostaElettronicaList("posta_elettronica", idNode),
								new PostaElettronicaDetail("posta_elettronica"));
					} else {

						layout.changeLayout("posta_elettronica", new GWTRestDataSource("AurigaPostaElettronicaDataSource", "idEmail", FieldType.TEXT),
								new PostaElettronicaFilter("posta_elettronica", extraparam), new PostaElettronicaList("posta_elettronica", idNode),
								new DettaglioPostaElettronica("posta_elettronica"));
					}

					layout.getRightLayout().show();
					selectSingleRecord(record);
					currentRecord = record;
					layout.markForRedraw();
					layout.reloadListAndFilter();

					if (layout.hasMultiselectButtons()) {
						layout.setMultiselect(flgMultiselezione);
					}
				}

			} else {

				if (azione != null && !"".equals(azione)) {

					((ScrivaniaLayout) layout).setSearchNodeLabelContents(searchNodeLabelContents);
					((ScrivaniaLayout) layout).setAzione(azione);

					if ("archivio".equals(azione)) {

						String parametri = record.getAttributeAsString("parametri");
						String criteriAvanzati = record.getAttributeAsString("criteriAvanzati");

						HashMap<String, String> parameters = new HashMap<String, String>();
						StringSplitterClient st = new StringSplitterClient(parametri, "|*|");
						if (st.getTokens().length > 0 && st.getTokens().length % 2 == 0) {
							for (int i = 0; i < st.getTokens().length; i += 2) {
								String key = st.getTokens()[i];
								String value = st.getTokens()[i + 1];
								parameters.put(key, value);
							}
						}
						String idFolder = parameters.get("CercaInFolderIO");
						String flgUdFolder = parameters.get("FlgUDFolderIO");
						if (flgUdFolder != null && flgUdFolder.equalsIgnoreCase("F"))
							flgUdFolder = "FS";

						((ScrivaniaLayout) layout).resettaParametriRicerca();
						((ScrivaniaLayout) layout).setIdNode(idNode);
						((ScrivaniaLayout) layout).setNomeNodo(nomeNodo);
						((ScrivaniaLayout) layout).setTipoNodo(tipoNodo);
						((ScrivaniaLayout) layout).setIdFolder(idFolder);
						((ScrivaniaLayout) layout).setFlgUdFolder(flgUdFolder);
						((ScrivaniaLayout) layout).setCriteriAvanzati(criteriAvanzati);
						((ScrivaniaLayout) layout).setCodSezione(codSezione);

						GWTRestDataSource archivioDS = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
						archivioDS.addParam("setNullFlgSubfolderSearch", "true");
						// nella sezione Archiviati (doc e fasc) della scrivania va passato InteresseCessato a R
						if (idNode != null && (idNode.equalsIgnoreCase("FD.21") || idNode.equalsIgnoreCase("F.21") || idNode.equalsIgnoreCase("D.21"))) {
							archivioDS.addParam("interesseCessato", "R");
						} else {
							archivioDS.addParam("interesseCessato", null);
						}
						archivioDS.addParam("idNode", idNode);

						Map<String, String> extraparam = new HashMap<String, String>();
						extraparam.put("fromScrivania", "true");						
						extraparam.put("tipoNodo", tipoNodo);
						extraparam.put("idNode", idNode);
						extraparam.put("showFlgRicorsiva", "false");
						
						// Se nella lista abbiamo FASCICOLI oppure FASCICOLI + DOCUMENTI
						if ((tipoNodo.equalsIgnoreCase("F") || tipoNodo.equalsIgnoreCase("FD"))) {

							// Mostro il filtro DATA APERTURA
							if (criteriAvanzati != null && (criteriAvanzati.contains("DataAperturaDa") || criteriAvanzati.contains("DataAperturaA"))) {
								extraparam.put("showFilterDataApertura", "N");
							} else {
								extraparam.put("showFilterDataApertura", "S");
							}
							// Mostro il filtro DATA CHIUSURA ( solo se il parametro ATTIVA_GEST_ARCH_DEPOSITO = true )
							if (criteriAvanzati != null && (criteriAvanzati.contains("DataChiusuraDa") || criteriAvanzati.contains("DataChiusuraA"))) {
								extraparam.put("showFilterDataChiusura", "N");
							} else {

								extraparam.put("showFilterDataChiusura", "S");
							}
						} else {
							extraparam.put("showFilterDataApertura", "N");
							extraparam.put("showFilterDataChiusura", "N");
						}

						if ((tipoNodo.equalsIgnoreCase("D") || tipoNodo.equalsIgnoreCase("FD"))) {
							// Mostro i filtri TIPO, ANNO, NUMERO e DATA PROTOCOLLO solo se nella lista abbiamo DOCUMENTI oppure FASCICOLI + DOCUMENTI e non
							// sono in BOZZE o in Documenti da protocollare
							if (idNode != null && (idNode.equalsIgnoreCase("D.BOZZE") || idNode.equalsIgnoreCase("D.12") || idNode.equalsIgnoreCase("D.11"))) {
								extraparam.put("showFilterProtocollo", "N");
							} else {
								extraparam.put("showFilterProtocollo", "S");
							}
							if (idNode != null
									&& (idNode.equalsIgnoreCase("D.BOZZE") || idNode.equalsIgnoreCase("D.12") || idNode.equalsIgnoreCase("D.11") || idNode
											.equalsIgnoreCase("D.18"))) {
								extraparam.put("showFilterRegEffettuataDa", "N");
							} else {
								extraparam.put("showFilterRegEffettuataDa", "S");
							}
							extraparam.put("showFilterBozze", "S");

						} else {
							extraparam.put("showFilterProtocollo", "N");
							extraparam.put("showFilterRegEffettuataDa", "N");
							extraparam.put("showFilterBozze", "N");
						}
						if (idNode != null && idNode.equalsIgnoreCase("D.23")) {
							extraparam.put("showFilterStampe", "S");
						} else {
							extraparam.put("showFilterStampe", "N");
						}
						// if (tipoNodo.equalsIgnoreCase("D")) {
						// extraparam.put("showFilterRestringiRicercaA", "N");
						// } else {
						// extraparam.put("showFilterRestringiRicercaA", "S");
						// }
						if (idNode != null && (idNode.startsWith("FD.2") || idNode.startsWith("D.2") || idNode.startsWith("F.2")) && 
							!idNode.startsWith("D.2CC.DL") && !idNode.startsWith("F.2CC.DL") && !idNode.startsWith("D.2NA.DL") && !idNode.startsWith("F.2NA.DL")) {
							extraparam.put("showFilterSoloDaLeggere", "S");
						} else {
							extraparam.put("showFilterSoloDaLeggere", "N");
						}
						if (idFolder != null && (idFolder.equals("-9999") || idFolder.equals("-99991"))) {
							extraparam.put("showFilterInvio", "S");
						} else {
							extraparam.put("showFilterInvio", "N");
						}
						if (idNode != null && (idNode.equalsIgnoreCase("D.21") || idNode.equalsIgnoreCase("F.21"))) {
							extraparam.put("showFilterArchiviazione", "S");
						} else {
							extraparam.put("showFilterArchiviazione", "N");
						}
						if (idFolder != null && idFolder.equals("-99999")) {
							extraparam.put("showFilterEliminazione", "S");
						} else {
							extraparam.put("showFilterEliminazione", "N");
						}
						if ((tipoNodo.equalsIgnoreCase("D") || tipoNodo.equalsIgnoreCase("FD")) && !(idNode != null && idNode.equalsIgnoreCase("D.BOZZE"))) {
							extraparam.put("showFilterRicevutiInviatiViaEmail", "S");
						} else {
							extraparam.put("showFilterRicevutiInviatiViaEmail", "N");
						}
						if (idNode != null && (idNode.startsWith("FD.") || idNode.startsWith("D.") || idNode.startsWith("F.")) && 
							!idNode.startsWith("D.2A.DP") && !idNode.startsWith("F.2A.DP")) {
							extraparam.put("showFilterStatoPresaInCarico", "S");
						} else {
							extraparam.put("showFilterStatoPresaInCarico", "N");
						}
						if (idNode != null && idNode.equals("D.13")) {
							extraparam.put("showFilterStatoRichAnnullamento", "S");
						} else {
							extraparam.put("showFilterStatoRichAnnullamento", "N");
						}
						if (idNode != null && idNode.equals("D.14")) {
							extraparam.put("showFilterStatoAutorizzazione", "S");
						} else {
							extraparam.put("showFilterStatoAutorizzazione", "N");
						}
						if (idNode != null && !(idNode.startsWith("FD.2CC") || idNode.startsWith("D.2CC") || idNode.startsWith("F.2CC") || idNode.equals("D.7"))) {
							extraparam.put("showFilterInCompetenzaA", "S");
						} else {
							extraparam.put("showFilterInCompetenzaA", "N");
						}
						if (idNode != null && (idNode.startsWith("FD.2A") || idNode.startsWith("D.2A") || idNode.startsWith("F.2A"))) {
							extraparam.put("showFilterRicevutiPerCompetenza", "S");
						} else {
							extraparam.put("showFilterRicevutiPerCompetenza", "N");
						}
						if (idNode != null && (idNode.startsWith("FD.2CC") || idNode.startsWith("D.2CC") || idNode.startsWith("F.2CC"))) {
							extraparam.put("showFilterRicevutiPerConoscenzaCC", "S");
						} else {
							extraparam.put("showFilterRicevutiPerConoscenzaCC", "N");
						}
						if (idNode != null && (idNode.startsWith("FD.2NA") || idNode.startsWith("D.2NA") || idNode.startsWith("F.2NA"))) {
							extraparam.put("showFilterRicevutiPerConoscenzaNA", "S");
						} else {
							extraparam.put("showFilterRicevutiPerConoscenzaNA", "N");
						}
						if (idNode != null && (idNode.equals("D.21"))) {
							extraparam.put("showFilterRicevutiPerConoscenzaCC", "S");
						}

						/***** ANTONIO *****/
						if (idNode != null) {

							if (idNode.startsWith("F.")) {
								extraparam.put("showFilterDocumenti", "N");
								extraparam.put("showFilterFascicoli", "S");
							}
							if (idNode.startsWith("D.")) {
								extraparam.put("showFilterDocumenti", "S");
								extraparam.put("showFilterFascicoli", "N");
							}
							if (idNode.equalsIgnoreCase("D.DIA")) {
								extraparam.put("showFilterDdIA", "N");
							}
						}

						/*
						 * Filtri di ricerca Altra Numerazione da non mostrare nella sezione BOZZE E STAMPA
						 */
						setFiltriAltraNumerazione(idNode, tipoNodo, extraparam);

						if (idNode != null && idNode.equalsIgnoreCase("F.DIA")) {
							extraparam.put("showFilterFdIA", "N");
						}

						/***** FINE ANTONIO ******/
						// Mostro il filtro RESTRINGI RICERCA A solo se nella lista abbiamo FASCICOLI + DOCUMENTI
						if (tipoNodo.equalsIgnoreCase("F")) {
							extraparam.put("showFilterMittente", "N");
							extraparam.put("showFilterDestinatario", "N");
							extraparam.put("showFilterOggetto", "N");
							extraparam.put("showFilterEsibente", "N");
						} else {
							extraparam.put("showFilterMittente", "S");
							extraparam.put("showFilterDestinatario", "S");
							extraparam.put("showFilterOggetto", "S");
							extraparam.put("showFilterEsibente", "S");
						}
						if (tipoNodo.equalsIgnoreCase("D")) {
							extraparam.put("showFilterNomeFascicolo", "N");
							extraparam.put("showFilterCapoFilaFasc", "N");
							extraparam.put("showFilterRegistroAltriRif", "N");
						} else {
							extraparam.put("showFilterNomeFascicolo", "S");
							extraparam.put("showFilterCapoFilaFasc", "S");
							extraparam.put("showFilterRegistroAltriRif", "S");
						}

						extraparam.put("showFilterRifFascicolo", "N");
						extraparam.put("showFilterRifClassificazione", "N");
						
						if ( idNode != null   && (idNode.startsWith("FD.") || idNode.startsWith("F."))) {								
								extraparam.put("showFilterRifFascicolo", "S");
								extraparam.put("showFilterRifClassificazione", "S");
						} 
						
						if ( idNode != null && (idNode.startsWith("FD.")  || idNode.startsWith("F.") || idNode.startsWith("D."))) {
							extraparam.put("showFilterRifClassificazione", "S");
						} 
						
						// Nascondo il filtro "Fasciolo - capo fila" se il parametro e' false
						if (!AurigaLayout.getParametroDBAsBoolean("ATTIVA_CAPOFILA")) {
							extraparam.put("showFilterCapoFilaFasc", "N");
						}
						
						if ( idNode != null && (idNode.startsWith("D.13") || idNode.startsWith("D.14"))) {	
							extraparam.put("showFilterEstremiAttoAutAnn", "S");
						}
						
						if ( idNode != null && (idNode.startsWith("D.2A.R") || idNode.startsWith("D.2A") || idNode.startsWith("F.2A.R") || idNode.startsWith("F.2A"))) {	
							extraparam.put("showFilterDataPresaInCarico", "S");
						}else {
							extraparam.put("showFilterDataPresaInCarico", "N");
						}
						
						// Filtro per ADSP
						if ( idNode != null  && idNode.startsWith("D.") && AurigaLayout.isAttivoClienteADSP()) {								
							extraparam.put("showFilterPerizia", "S");							
					    }
						else{
							extraparam.put("showFilterPerizia", "N");
						}
												
						// Filtro per COTO
						if ( idNode != null  && idNode.startsWith("D.") && AurigaLayout.isAttivoClienteCOTO()) {								
							extraparam.put("showFilterCentroDiCosto", "S");							
					    }
						else{
							extraparam.put("showFilterCentroDiCosto", "N");
						}
						
						if ( idNode != null  && idNode.startsWith("D.") && AurigaLayout.getParametroDBAsBoolean("ATTIVO_ITER_LIQUIDAZIONI")) {								
							extraparam.put("showFilterDataScadenza", "S");							
					    }
						else{
							extraparam.put("showFilterDataScadenza", "N");
						}
						
						extraparam.put("showFilterInConoscenzaA", "N");
						ArchivioFilter archivioFilter = null;
//						if (layout.getFilter() instanceof ArchivioFilter) {
//							archivioFilter = ((ArchivioFilter) layout.getFilter());
//							archivioFilter.updateShowFilter(extraparam);
//							// archivioFilter.markForRedraw();
//						} else {
							archivioFilter = new ArchivioFilter("scrivania", extraparam);
//						}

						layout.changeLayout("archivio", archivioDS, archivioFilter, new ArchivioList("archivio", true, idNode, tipoNodo, idFolder),
								new ArchivioDetail("archivio"));

						layout.getRightLayout().show();
						selectSingleRecord(record);

						currentRecord = record;
						layout.markForRedraw();
						layout.reloadListAndFilter();

						if (layout.hasMultiselectButtons()) {
							layout.setMultiselect(flgMultiselezione);
						}
						
					} else if ("modelliDoc".equals(azione)) {
						
						GWTRestDataSource modelliDocDS = new GWTRestDataSource("ModelliDocDatasource", "idModello", FieldType.TEXT);
						layout.changeLayout("modelli_doc", modelliDocDS, new ConfigurableFilter("modelli_doc"),
								new ModelliDocList("modelli_doc"), new ModelliDocDetail("modelli_doc"));
						layout.setDetailAuto(false);
						layout.getRightLayout().show();
						selectSingleRecord(record);
						currentRecord = record;
						layout.markForRedraw();
						layout.reloadListAndFilter();

						if (layout.hasMultiselectButtons()) {
							layout.setMultiselect(flgMultiselezione);
						}
						
					} else if ("emergenza".equals(azione)) {
						
						GWTRestDataSource registrazioniEmergenzaDS = new GWTRestDataSource("RegistrazioniEmergenzaDatasource", "idUdFolder", FieldType.TEXT);
						layout.changeLayout("registrazioniEmergenza", registrazioniEmergenzaDS, new ConfigurableFilter("registrazioniEmergenza"),
								new RegistrazioniEmergenzaList("registrazioniEmergenza"), new CustomDetail("registrazioniEmergenza"));
						layout.setDetailAuto(false);
						layout.getRightLayout().show();
						selectSingleRecord(record);
						currentRecord = record;
						layout.markForRedraw();
						layout.reloadListAndFilter();

						if (layout.hasMultiselectButtons()) {
							layout.setMultiselect(flgMultiselezione);
						}
						
					} else if ("richiestaAccessoAtti".equals(azione)) {
						
						Map<String, String> extraparam = new HashMap<String, String>();
						extraparam.put("fromScrivania", "true");
						extraparam.put("tipoNodo", tipoNodo);
						extraparam.put("idNode", idNode);
						extraparam.put("showFlgRicorsiva", "false");
						
						setFiltriUrbanistica(idNode, extraparam);
						
						layout.getRightLayout().show();
						selectSingleRecord(record);

						GWTRestDataSource richiestaAccessoAttiDS = new GWTRestDataSource("RichiestaAccessoAttiDatasource", "idUdFolder", FieldType.TEXT);
						layout.changeLayout("richiestaAccessoAtti", richiestaAccessoAttiDS, new RichiestaAccessoAttiFilter("richiestaAccessoAtti",extraparam),
								new RichiestaAccessoAttiList("richiestaAccessoAtti", idNode), new RichiestaAccessoAttiDetail("richiestaAccessoAtti"));
						layout.setDetailAuto(false);
						currentRecord = record;
						layout.markForRedraw();
						layout.reloadListAndFilter();
						
						String parametri = record.getAttributeAsString("parametri");
						String criteriAvanzati = record.getAttributeAsString("criteriAvanzati");

						HashMap<String, String> parameters = new HashMap<String, String>();
						StringSplitterClient st = new StringSplitterClient(parametri, "|*|");
						if (st.getTokens().length > 0 && st.getTokens().length % 2 == 0) {
							for (int i = 0; i < st.getTokens().length; i += 2) {
								String key = st.getTokens()[i];
								String value = st.getTokens()[i + 1];
								parameters.put(key, value);
							}
						}
						String idFolder = parameters.get("CercaInFolderIO");
						
						((ScrivaniaLayout) layout).resettaParametriRicerca();
						((ScrivaniaLayout) layout).setIdNode(idNode);
						((ScrivaniaLayout) layout).setNomeNodo(nomeNodo);
						((ScrivaniaLayout) layout).setTipoNodo(tipoNodo);
						((ScrivaniaLayout) layout).setIdFolder(idFolder);
						((ScrivaniaLayout) layout).setFlgUdFolder("U");
						((ScrivaniaLayout) layout).setCriteriAvanzati(criteriAvanzati);
						((ScrivaniaLayout) layout).setCodSezione(codSezione);
						
						if (layout.hasMultiselectButtons()) {
							layout.setMultiselect(flgMultiselezione);
						}
						
					}
				}
			}

			// Boolean flgContenuti = record.getAttributeAsBoolean("flgContenuti");
			// if(flgContenuti != null && flgContenuti) {
			// aggiornaNroContenutiNodo();
			// }

		}

	}

	private void setFiltriAltraNumerazione(String idNode, String tipoNodo, Map<String, String> extraparam) {
		
		if (AurigaLayout.getParametroDBAsBoolean("ALTRE_NUM_ATTIVE") && idNode != null && idNode.startsWith("D.") && !idNode.equalsIgnoreCase("D.BOZZE")
				&& !idNode.equalsIgnoreCase("D.23")) {
			extraparam.put("showFilterAltraNumerazione", "S");
		} else {
			extraparam.put("showFilterAltraNumerazione", "N");
		}
	}

	@Override
	public void manageContextClick(final Record record) {
		
		if (record != null) {
			String azione = record.getAttributeAsString("azione");
			if (azione != null && !"".equals(azione)) {
				TreeNodeBean node = new TreeNodeBean();
				node.setIdNode(record.getAttributeAsString("idNode"));
				node.setIdFolder(record.getAttributeAsString("idFolder"));

				final GWTRestDataSource autoSearchDS = UserInterfaceFactory.getPreferenceDataSource();
				autoSearchDS.addParam("prefKey", layout.getPrefKeyPrefix(record) + "autosearch");

				final MenuItem autoSearchMenuItem = new MenuItem(I18NUtil.getMessages().autoSearchMenuItem_title());
				autoSearchMenuItem.setCheckIfCondition(new MenuItemIfFunction() {

					@Override
					public boolean execute(Canvas target, Menu menu, MenuItem item) {
						return autoSearchMenuItem.getAttributeAsBoolean("value");
					}
				});
				autoSearchMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						AdvancedCriteria criteria = new AdvancedCriteria();
						criteria.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
						autoSearchDS.fetchData(criteria, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Record[] data = response.getData();
								final boolean checked = autoSearchMenuItem.getChecked();
								autoSearchMenuItem.setAttribute("value", !checked);
								Record record = data[0];
								record.setAttribute("value", !checked);
								autoSearchDS.updateData(record);
								Layout.addMessage(new MessageBean(checked ? I18NUtil.getMessages().afterDisattivaAutoSearch_message() : I18NUtil.getMessages()
										.afterAttivaAutoSearch_message(), "", MessageType.INFO));
							}
						});
					}
				});

				// final MenuItem aggiornaNroContenutiMenuItem = new MenuItem("Aggiorna numero contenuti");
				// aggiornaNroContenutiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				// @Override
				// public void onClick(MenuItemClickEvent event) {
				// aggiornaNroContenutiNodo(record);
				// }
				// });
				
				// Taglia 
				final MenuItem tagliaMenuItem = getTagliaMenuItem(record);
				
				// Incolla 
				final MenuItem incollaMenuItem = getIncollaMenuItem(record);

				AdvancedCriteria criteriaAutoSearch = new AdvancedCriteria();
				criteriaAutoSearch.addCriteria("prefName", OperatorId.EQUALS, "DEFAULT");
				autoSearchDS.fetchData(criteriaAutoSearch, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record[] data = response.getData();
						if (data.length != 0) {
							Record record = data[0];
							autoSearchDS.updateData(record);
							autoSearchMenuItem.setAttribute("value", new Boolean(record.getAttribute("value")));
						} else {
							Record record = new Record();
							record.setAttribute("prefName", "DEFAULT");
							record.setAttribute("value", "false");
							autoSearchDS.addData(record);
							autoSearchMenuItem.setAttribute("value", false);
						}
						contextMenu = new Menu();
						contextMenu = new Menu();
						contextMenu.setOverflow(Overflow.VISIBLE);
						contextMenu.setShowIcons(true);
						contextMenu.setSelectionType(SelectionStyle.SINGLE);
						contextMenu.setCanDragRecordsOut(false);
						contextMenu.setWidth("*");
						contextMenu.setHeight("*");
						contextMenu.addItem(autoSearchMenuItem);
						
//						if (record != null && record.getAttributeAsString("idNode") != null && 
//								!record.getAttributeAsString("idNode").startsWith("E.")) {
//							contextMenu.addItem(tagliaMenuItem);
//							contextMenu.addItem(incollaMenuItem);
//						}

						// Boolean flgContenuti = record.getAttributeAsBoolean("flgContenuti");
						//
						// if(flgContenuti != null && flgContenuti) {
						// contextMenu.addItem(aggiornaNroContenutiMenuItem);
						// }

						Scheduler.get().scheduleDeferred(new ScheduledCommand() {

							@Override
							public void execute() {
								contextMenu.showContextMenu();
							}
						});
					}
				});
			}
		}
	}

	/**
	 * CONFIGURAZIONE FILTRI RELATIVI A PRATICA URBANISTICA COD NODO: D.RAA.I, D.RAA.DA, D.RAA.DV, D.RAA.ADF, D.RAA.AF, D.RAA.N, D.RAA.EN 
	 */
	private void setFiltriUrbanistica(String idNode, Map<String, String> extraparam) {

		if (idNode != null) {
			
			/**
			 * FILTRI COMUNI A TUTTI I COD.NODO
			 */
			if (idNode.equalsIgnoreCase("D.RAA.I") || idNode.equalsIgnoreCase("D.RAA.DA") || idNode.equalsIgnoreCase("D.RAA.DV") || idNode.equalsIgnoreCase("D.RAA.ADF") || idNode.equalsIgnoreCase("D.RAA.AF") || idNode.equalsIgnoreCase("D.RAA.N") || idNode.equalsIgnoreCase("D.RAA.EN")) {
				extraparam.put("showFilterEstremiRegistrazione", "S");
			}			
			// D.RAA.DV - D.RAA.ADF - D.RAA.AF - D.RAA.N - D.RAA.EN
			if (idNode.equalsIgnoreCase("D.RAA.DV") || idNode.equalsIgnoreCase("D.RAA.ADF") || idNode.equalsIgnoreCase("D.RAA.AF") || idNode.equalsIgnoreCase("D.RAA.N") || idNode.equalsIgnoreCase("D.RAA.EN")) {
				extraparam.put("showFilterDataApprovazione", "S");
				extraparam.put("showFilterRichApprovataDa", "S");
			}
			// D.RAA.DV - D.RAA.ADF - D.RAA.AF - D.RAA.N
			if (idNode.equalsIgnoreCase("D.RAA.DV") || idNode.equalsIgnoreCase("D.RAA.ADF") || idNode.equalsIgnoreCase("D.RAA.AF") || idNode.equalsIgnoreCase("D.RAA.N")) {
				extraparam.put("showFilterUnitaDiConservazione", "S");
			}
			// D.RAA.ADF - D.RAA.AF - D.RAA.N - D.RAA.EN
			if (idNode.equalsIgnoreCase("D.RAA.ADF") || idNode.equalsIgnoreCase("D.RAA.AF") || idNode.equalsIgnoreCase("D.RAA.N") || idNode.equalsIgnoreCase("D.RAA.EN")) {
				extraparam.put("showFilterDataEsitoCittadella", "S");
			}
			// D.RAA.DA
			if (idNode.equalsIgnoreCase("D.RAA.DA")) {
				extraparam.put("showFilterRichiesteDaApprovare", "S");
			}
			// D.RAA.DV
			if (idNode.equalsIgnoreCase("D.RAA.DV")) {
				extraparam.put("showFilterRichiesteDaVerificare", "S");
			}
			// D.RAA.ADF - D.RAA.N
			if (idNode.equalsIgnoreCase("D.RAA.ADF") || idNode.equalsIgnoreCase("D.RAA.N")) {
				extraparam.put("showFilterAppuntamentiDaFissare", "S");
			}
			// D.RAA.AF - D.RAA.N
			if (idNode.equalsIgnoreCase("D.RAA.AF") || idNode.equalsIgnoreCase("D.RAA.N")) {
				extraparam.put("showFilterRichiesteAppuntamento", "S");
			}			
			// D.RAA.N : filtro Data notifica (come tsNotificaNA di D.2NA.R)
			if (idNode.equalsIgnoreCase("D.RAA.N")) {
				extraparam.put("showFilterRichiesteNotifica", "S");
			}
		}
	}
	
	/**
	 * Gestione della funzionalità Taglia di documenti e fascicoli
	 */
	private MenuItem getTagliaMenuItem(final Record cutRecord) {
		
		MenuItem tagliaMenuItem = new MenuItem("Taglia", "buttons/cut.png");
		tagliaMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				((ScrivaniaLayout) layout).setCutNode(cutRecord);
			}
		});
		return tagliaMenuItem;
	}
	
	/**
	 * Gestione della funzionalità Copia di documenti e fascicoli
	 */
	private MenuItem getIncollaMenuItem(final Record currentRecord) {
		
		MenuItem incollaMenuItem = new MenuItem("Incolla", "incolla.png");
		incollaMenuItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				((ScrivaniaLayout) layout).paste(currentRecord,false);
			}
		});
		return incollaMenuItem;
	}
	
	
	
	

}