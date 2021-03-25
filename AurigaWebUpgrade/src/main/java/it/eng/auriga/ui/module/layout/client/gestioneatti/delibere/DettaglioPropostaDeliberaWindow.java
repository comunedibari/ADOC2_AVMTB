package it.eng.auriga.ui.module.layout.client.gestioneatti.delibere;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.NuovaPropostaAtto2CompletaDetail;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.FrecciaDetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DettaglioPropostaDeliberaWindow extends ModalWindow {

	private DettaglioPropostaDeliberaWindow _window;
	
	private NuovaPropostaAtto2CompletaDetail detail;

	private ToolStrip detailToolStrip;
	private DetailToolStripButton listaEmendamentiButton;
	private FrecciaDetailToolStripButton frecciaApriEmendamentiButton;
	private DetailToolStripButton anteprimaDispositivoAttoButton;
	private DetailToolStripButton anteprimaDatiSpesaButton;
	private DetailToolStripButton scaricaAttoFirmatoButton;
	private DetailToolStripButton scaricaFileButton;
	private DetailToolStripButton aggiornaStatoAttoPostDiscussioneButton;
	private DetailToolStripButton editButton;
	private DetailToolStripButton saveButton;
	private DetailToolStripButton reloadDetailButton;
	private DetailToolStripButton undoButton;
	
	private VLayout detailLayout;

	private String mode;
	
	private String idUd;
	private String idProcess;
	private String activityName;
	private String nomeFlussoWF;
	
	private String tipoDocumentoCorrente;
	private String rowidDocCorrente;
	
	private boolean isModificaTesto;
	
	private Record recordCallExecAtt;
	private Record recordEvento;
	
	public DettaglioPropostaDeliberaWindow(Record record, String title) {
		this(record, title, false);
	}
	
	public DettaglioPropostaDeliberaWindow(Record record, String title, final boolean isModificaTesto) {
	
		super("dettaglio_proposta_delibera", true);

		setTitle(title);

		_window = this;
		this.recordCallExecAtt = new Record(record.getJsObj());
		this.idUd = record.getAttributeAsString("idUd");
		this.idProcess = record.getAttributeAsString("idProcess");
		this.activityName = record.getAttributeAsString("activityName");
		String idDefProcFlow = record.getAttributeAsString("idDefProcFlow");
		this.nomeFlussoWF = (idDefProcFlow != null && idDefProcFlow.contains(":")) ? idDefProcFlow.substring(0, idDefProcFlow.indexOf(":")) : idDefProcFlow;
		this.isModificaTesto = isModificaTesto;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);						
		
		setOverflow(Overflow.AUTO);

		detailLayout = new VLayout();
		detailLayout.setOverflow(Overflow.HIDDEN);
		detailLayout.setHeight100();
		detailLayout.setWidth100();
		setBody(detailLayout);
				 
		if (idProcess != null && !"".equals(idProcess)) {
			callExecAtt(recordCallExecAtt, new ServiceCallback<Record>() {

				@Override
				public void execute(final Record lRecordEvento) {		
					recordEvento = lRecordEvento;
					String tabIDIniziale = isModificaTesto ? NuovaPropostaAtto2CompletaDetail._TAB_DATI_DISPOSITIVO_ID : null;
					manageLoadDetail(tabIDIniziale, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if(isModificaTesto) {
								editMode();
							} else {
								viewMode();
							}
							_window.show();		
						}
					});
				}
			});
		}
		
		setIcon("blank.png");
	}
	
	public void callExecAtt(final Record record, final ServiceCallback<Record> callback) {
		Layout.showWaitPopup("Caricamento dati in corso...");
		GWTRestService<Record, Record> lCallExecAttDatasource = new GWTRestService<Record, Record>("CallExecAttDatasource");
		lCallExecAttDatasource.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record output) {
				String warninMsg = output.getAttribute("warningMsg");
				if (warninMsg != null && !"".equals(warninMsg)) {
					AurigaLayout.showConfirmDialogWithWarning("Attenzione!", warninMsg, "Procedi", "Annulla", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value != null && value) {
								if (callback != null) {
									callback.execute(output);
								}
							}
						}
					});
				} else {
					if (callback != null) {
						callback.execute(output);
					}
				}
			}
		});
	}

	public void onSaveButtonClick() {
		final Record record = detail.getRecordToSave();
		if (detail.validate()) {
			realSave(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}

	protected void realSave(final Record record) {
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		final DSCallback callback = new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record savedRecord = response.getData()[0];
					if (savedRecord.getAttribute("flgIgnoreWarning") == null || savedRecord.getAttributeAsInt("flgIgnoreWarning") != 1) {
						try {
							reload(new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									viewMode();
									Layout.hideWaitPopup();
									Layout.addMessage(new MessageBean("Salvataggio effettuato con successo", "", MessageType.INFO));
								}							
							});
						} catch (Exception e) {
							Layout.hideWaitPopup();
						}
					} else {
						detail.getValuesManager().setValue("flgIgnoreWarning", "1");
						Layout.hideWaitPopup();
					}
				} else {
					Layout.hideWaitPopup();
				}
			}
		};
		try {
			final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
			lNuovaPropostaAtto2DataSource.updateData(record, new DSCallback() {

				@Override
				public void execute(final DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						detail.salvaAttributiDinamiciDoc(false, rowidDocCorrente, activityName, null, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								if (callback != null) {
									callback.execute(response, null, new DSRequest());
								}
							}
						});
					}
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	public void reload(final DSCallback callback) {
		if (idProcess != null && !"".equals(idProcess)) {
			callExecAtt(recordCallExecAtt, new ServiceCallback<Record>() {

				@Override
				public void execute(final Record lRecordEvento) {		
					recordEvento = lRecordEvento;
					manageLoadDetail(null, callback);
				}
			});
		}
	}

	public void manageLoadDetail(final String tabIDIniziale, final DSCallback callback) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(final DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
					RecordList attributiAddDocTabs = recordEvento != null ? recordEvento.getAttributeAsRecordList("attributiAddDocTabs") : null;
					if (attributiAddDocTabs != null) {
						for (int i = 0; i < attributiAddDocTabs.getLength(); i++) {
							Record tab = attributiAddDocTabs.get(i);
							tabs.put(tab.getAttribute("codice"), tab.getAttribute("titolo"));
						}
					}				
					
					detail = new NuovaPropostaAtto2CompletaDetail("modifica_testo_delibera", tabs) {
						
						@Override
						public void build() {
							this.recordParametriTipoAtto = recordEvento != null ? recordEvento.getAttributeAsRecord("parametriTipoAtto") : null;
							super.build();
						}
						
						@Override
						public String getIdModDispositivo() {
							String idModAssDocTask = recordEvento != null ? recordEvento.getAttribute("idModAssDocTask") : null;		
							return idModAssDocTask != null ? idModAssDocTask : "";
						}
						
						@Override
						public String getNomeModDispositivo() {
							String nomeModAssDocTask = recordEvento != null ? recordEvento.getAttribute("nomeModAssDocTask") : null;
							return nomeModAssDocTask != null ? nomeModAssDocTask : "";
						}
						
						@Override
						public String getDisplayFilenameModDispositivo() {
							String displayFilenameModAssDocTask = recordEvento != null ? recordEvento.getAttribute("displayFilenameModAssDocTask") : null;
							return displayFilenameModAssDocTask != null ? displayFilenameModAssDocTask : "";
						}
						
						@Override
						public String getIdModAppendice() {
							String idModAppendice = recordEvento != null ? recordEvento.getAttribute("idModAppendice") : null;
							return idModAppendice != null ? idModAppendice : "";
						}
						
						@Override
						public String getNomeModAppendice() {
							String nomeModAppendice = recordEvento != null ? recordEvento.getAttribute("nomeModAppendice") : null;
							return nomeModAppendice != null ? nomeModAppendice : "";
						}
						
						@Override
						public Record getRecordToSave() {
							Record lRecordToSave = super.getRecordToSave();
							lRecordToSave.setAttribute("idProcess", getIdProcessTask());
							lRecordToSave.setAttribute("idModello", getIdModDispositivo());
							lRecordToSave.setAttribute("nomeModello", getNomeModDispositivo());
							lRecordToSave.setAttribute("displayFilenameModello", getDisplayFilenameModDispositivo());
							return lRecordToSave;
						}
						
						@Override
						public void setCanEdit(boolean canEdit) {
							super.setCanEdit(canEdit);
							if (attributiAddDocDetails != null) {
								for (String key : attributiAddDocDetails.keySet()) {
									AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
									detail.setCanEdit(canEdit);
								}
							}
						}; 
						
						@Override
						public void editRecord(Record record) {
							tipoDocumentoCorrente = record.getAttribute("tipoDocumento");
							rowidDocCorrente = record.getAttribute("rowidDoc");
							super.editRecord(record);
							caricaAttributiDinamiciDoc(nomeFlussoWF, "", activityName, tipoDocumentoCorrente, rowidDocCorrente);
						}
						
						@Override
						protected void afterCaricaAttributiDinamiciDoc() {
							super.afterCaricaAttributiDinamiciDoc();
							if(tabIDIniziale != null && !"".equals(tabIDIniziale)) {								
								tabSet.selectTab(tabSet.getTabWithID(tabIDIniziale));
							}
							if(callback != null) {
								callback.execute(response, null, new DSRequest());
							}
						};
					};
					detail.setHeight100();
					detail.setWidth100();
					detail.editRecord(record);
					detail.getValuesManager().clearErrors(true);
					
					createDetailToolStrip(record);
					
					if(detailToolStrip != null) {
						detailLayout.setMembers(detail, detailToolStrip);
					} else {
						detailLayout.setMembers(detail);
					}
				}
			}
		});
	}
	
	public void createDetailToolStrip(Record record) {

		List<DetailToolStripButton> listaDetailToolStripButtons = new ArrayList<DetailToolStripButton>();
		
		Boolean flgPresentiEmendamenti = recordEvento != null ? recordEvento.getAttributeAsBoolean("flgPresentiEmendamenti") : null;
		if (flgPresentiEmendamenti != null && flgPresentiEmendamenti) {

			listaEmendamentiButton = new DetailToolStripButton("Lista emendamenti", "buttons/altreOp.png");
			listaEmendamentiButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					detail.recuperaListaEmendamenti(new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record record) {
							detail.apriListaEmendamenti();
						}
					});
				}
			});
			listaDetailToolStripButtons.add(listaEmendamentiButton);
			
			frecciaApriEmendamentiButton = new FrecciaDetailToolStripButton();
			frecciaApriEmendamentiButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					frecciaApriEmendamentiButton.focusAfterGroup();										
					Menu frecciaApriEmendamentiMenu = new Menu();					
					MenuItem apriEmendamentiMenuItem = new MenuItem("Scorri lista emendamenti");
					apriEmendamentiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detail.recuperaListaEmendamenti(new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record record) {
									detail.apriEmendamentiWindow();
								}
							});						
						}
					});					
					MenuItem apriListaEmendamentiMenuItem = new MenuItem("Lista emendamenti");
					apriListaEmendamentiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detail.recuperaListaEmendamenti(new ServiceCallback<Record>() {
								
								@Override
								public void execute(Record record) {
									detail.apriListaEmendamenti();
								}
							});
							
						}
					});					
					frecciaApriEmendamentiMenu.setItems(apriListaEmendamentiMenuItem, apriEmendamentiMenuItem);
					frecciaApriEmendamentiMenu.showContextMenu();
				}
			});			
			listaDetailToolStripButtons.add(frecciaApriEmendamentiButton);
		}
		
		anteprimaDispositivoAttoButton  = new DetailToolStripButton("Anteprima provvedimento", "pratiche/task/buttons/anteprima_dispositivo.png");
		anteprimaDispositivoAttoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				detail.anteprimaDispositivoDaModello(detail.hasPrimarioDatiSensibili());
			}
		});
		listaDetailToolStripButtons.add(anteprimaDispositivoAttoButton);
		
		final String nomeFilePrimario = record != null ? record.getAttributeAsString("nomeFilePrimario") : null;
		final String uriFilePrimario = record != null ? record.getAttributeAsString("uriFilePrimario") : null;
		final InfoFileRecord infoFilePrimario = record != null && record.getAttributeAsRecord("infoFilePrimario") != null ? new InfoFileRecord(record.getAttributeAsRecord("infoFilePrimario")) : null;

		String idModAppendice = recordEvento != null ? recordEvento.getAttribute("idModAppendice") : null;
			
		if(idModAppendice != null && !"".equals(idModAppendice)) {											
			anteprimaDatiSpesaButton = new DetailToolStripButton("Anteprima movimenti contabili", "pratiche/task/buttons/anteprima_dati_spesa.png");
			anteprimaDatiSpesaButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					anteprimaDatiSpesaButton.focusAfterGroup();
					detail.anteprimaDatiSpesaDaModello();	
				}
			});
			listaDetailToolStripButtons.add(anteprimaDatiSpesaButton);	
		} 

		if(infoFilePrimario != null && infoFilePrimario.isFirmato()) {		
			
			scaricaAttoFirmatoButton = new DetailToolStripButton("Scarica atto firmato", "pratiche/task/buttons/scaricaPdf.png");
			scaricaAttoFirmatoButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					scaricaAttoFirmatoButton.focusAfterGroup();														
					if (infoFilePrimario.getTipoFirma().startsWith("CAdES")) {																						
						MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
						firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								Record lRecord = new Record();
								lRecord.setAttribute("displayFilename", nomeFilePrimario);
								lRecord.setAttribute("uri", uriFilePrimario);
								lRecord.setAttribute("sbustato", "false");
								lRecord.setAttribute("remoteUri", "true");
								DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");	
							}
						});											
						MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
						sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								Record lRecord = new Record();
								lRecord.setAttribute("displayFilename", nomeFilePrimario);
								lRecord.setAttribute("uri", uriFilePrimario);
								lRecord.setAttribute("sbustato", "true");
								lRecord.setAttribute("remoteUri", "true");
								DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");	
							}
						});																						
						Menu scaricaAttoFirmatoMenu = new Menu();										
						scaricaAttoFirmatoMenu.setItems(firmato, sbustato);
						scaricaAttoFirmatoMenu.showContextMenu();											
					} else {											
						Record lRecord = new Record();
						lRecord.setAttribute("displayFilename", nomeFilePrimario);
						lRecord.setAttribute("uri", uriFilePrimario);
						lRecord.setAttribute("sbustato", "false");
						lRecord.setAttribute("remoteUri", "true");
						DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");	
					}	
				}									
			});	
			listaDetailToolStripButtons.add(scaricaAttoFirmatoButton);
		} else {								
			scaricaFileButton = new DetailToolStripButton("Scarica", "pratiche/task/buttons/scaricaPdf.png");
			scaricaFileButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					scaricaFileButton.focusAfterGroup();																			
					List<MenuItem> listaScaricaMenuItems = new ArrayList<MenuItem>();																				
					if(detail.hasDatiSensibili()) {											
						MenuItem scaricaFileUnioneVersIntegraleMenuItem = new MenuItem("Atto completo (vers. integrale)");
						scaricaFileUnioneVersIntegraleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {														
								String nomeFileUnione = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null;
								Record impostazioniUnioneFile = recordEvento != null ? recordEvento.getAttributeAsRecord("impostazioniUnioneFile") : null;
								detail.anteprimaFileUnioneVersIntegrale(nomeFileUnione, impostazioniUnioneFile, new ServiceCallback<Record>() {
									
									@Override
									public void execute(final Record recordFileUnione) {			
										final Record lFileUnione = new Record();
										lFileUnione.setAttribute("displayFilename", recordFileUnione.getAttribute("nomeFileVersIntegrale"));
										lFileUnione.setAttribute("uri", recordFileUnione.getAttribute("uriVersIntegrale"));
										lFileUnione.setAttribute("sbustato", "false");
										lFileUnione.setAttribute("remoteUri", "false");
										DownloadFile.downloadFromRecord(lFileUnione, "FileToExtractBean");
									}
								});			
							}
						});		
						listaScaricaMenuItems.add(scaricaFileUnioneVersIntegraleMenuItem);																							
						MenuItem scaricaFileUnioneVersOmissisMenuItem = new MenuItem("Atto completo (vers. per pubblicazione)");
						scaricaFileUnioneVersOmissisMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {														
								String nomeFileUnioneOmissis = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFileOmissis") : null;
								Record impostazioniUnioneFile = recordEvento != null ? recordEvento.getAttributeAsRecord("impostazioniUnioneFile") : null;
								detail.anteprimaFileUnioneVersXPubbl(nomeFileUnioneOmissis, impostazioniUnioneFile, new ServiceCallback<Record>() {
									
									@Override
									public void execute(final Record recordFileUnione) {			
										final Record lFileUnioneOmissis = new Record();
										lFileUnioneOmissis.setAttribute("displayFilename", recordFileUnione.getAttribute("nomeFile"));	
										lFileUnioneOmissis.setAttribute("uri", recordFileUnione.getAttribute("uri"));
										lFileUnioneOmissis.setAttribute("sbustato", "false");
										lFileUnioneOmissis.setAttribute("remoteUri", "false");
										DownloadFile.downloadFromRecord(lFileUnioneOmissis, "FileToExtractBean");
									}
								});			
							}
						});											
						listaScaricaMenuItems.add(scaricaFileUnioneVersOmissisMenuItem);																																
					} else {
						MenuItem scaricaFileUnioneMenuItem = new MenuItem("Atto completo");
						scaricaFileUnioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {														
								String nomeFileUnione = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null;
								Record impostazioniUnioneFile = recordEvento != null ? recordEvento.getAttributeAsRecord("impostazioniUnioneFile") : null;
								detail.anteprimaFileUnioneVersIntegrale(nomeFileUnione, impostazioniUnioneFile, new ServiceCallback<Record>() {
									
									@Override
									public void execute(final Record recordFileUnione) {			
										final Record lFileUnione = new Record();
										lFileUnione.setAttribute("displayFilename", recordFileUnione.getAttribute("nomeFileVersIntegrale"));
										lFileUnione.setAttribute("uri", recordFileUnione.getAttribute("uriVersIntegrale"));
										lFileUnione.setAttribute("sbustato", "false");
										lFileUnione.setAttribute("remoteUri", "false");
										DownloadFile.downloadFromRecord(lFileUnione, "FileToExtractBean");
									}
								});			
							}
						});		
						listaScaricaMenuItems.add(scaricaFileUnioneMenuItem);
					}											
					MenuItem versionePerVerificaOmissisItem = new MenuItem("Versione per verifica omissis");
					versionePerVerificaOmissisItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detail.generaDispositivoDaModelloVersPerVerificaOmissis(new ServiceCallback<Record>() {
								
								@Override
								public void execute(final Record recordDownload) {
									Record lRecord = new Record();
									lRecord.setAttribute("displayFilename", recordDownload.getAttribute("nomeFile"));
									lRecord.setAttribute("uri", recordDownload.getAttribute("uri"));
									lRecord.setAttribute("sbustato", "false");
									lRecord.setAttribute("remoteUri", "false");
									DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
								}
							});
						}
					});	
					listaScaricaMenuItems.add(versionePerVerificaOmissisItem);												
					Menu contextMenu = new Menu();										
					contextMenu.setItems(listaScaricaMenuItems.toArray(new MenuItem[listaScaricaMenuItems.size()]));
					contextMenu.showContextMenu();
				}
			});								
			listaDetailToolStripButtons.add(scaricaFileButton);							
		}
		
		if (isModificaTesto) {
			
			Boolean abilAggiornaStatoAttoPostDiscussione = recordEvento != null ? recordEvento.getAttributeAsBoolean("abilAggiornaStatoAttoPostDiscussione") : null;							
			final RecordList statiXAggAttoPostDiscussione = recordEvento != null ? recordEvento.getAttributeAsRecordList("statiXAggAttoPostDiscussione") : null;
			if (abilAggiornaStatoAttoPostDiscussione != null && abilAggiornaStatoAttoPostDiscussione && statiXAggAttoPostDiscussione != null && statiXAggAttoPostDiscussione.getLength() > 0) {
				aggiornaStatoAttoPostDiscussioneButton = new DetailToolStripButton("Aggiorna stato", "ok.png");
				aggiornaStatoAttoPostDiscussioneButton.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						aggiornaStatoAttoPostDiscussioneButton.focusAfterGroup();										
						Menu statiAttoPostDiscussioneMenu = new Menu();		
						List<MenuItem> statiAttoPostDiscussioneMenuItems = new ArrayList<MenuItem>();
						for(int i = 0; i < statiXAggAttoPostDiscussione.getLength(); i++) {
							final Record lRecordStato = statiXAggAttoPostDiscussione.get(i);
							MenuItem statoAttoPostDiscussioneMenuItem = new MenuItem(lRecordStato.getAttribute("nomeStato"));
							statoAttoPostDiscussioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
		
								@Override
								public void onClick(MenuItemClickEvent event) {
									final String codStato = lRecordStato.getAttribute("codStato");
									String flgGeneraFileUnione = lRecordStato.getAttribute("flgGeneraFileUnione");
									if(codStato != null && !"".equals(codStato)) {
										if(flgGeneraFileUnione != null && "1".equals(flgGeneraFileUnione)) {
											//TODO dobbiamo fare l'identica cosa di quando generiamo il file unione (dispositivo e allegati) in firma di adozione (o scarica file unione)
											String nomeFileUnione = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFile") : null;
											String nomeFileUnioneOmissis = recordEvento != null ? recordEvento.getAttribute("unioneFileNomeFileOmissis") : null;
											Record impostazioniUnioneFile = recordEvento != null ? recordEvento.getAttributeAsRecord("impostazioniUnioneFile") : null;
											detail.generaFileUnione(null, nomeFileUnione, nomeFileUnioneOmissis, impostazioniUnioneFile, new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record recordVersFileUnione) {
													//TODO poi dobbiamo versionare il file unione, sia vers. integrale che eventuale omissis
													detail.aggiornaStato(codStato, recordVersFileUnione, new ServiceCallback<Record>() {
														
														@Override
														public void execute(Record object) {
															reload(new DSCallback() {
																
																@Override
																public void execute(DSResponse response, Object rawData, DSRequest request) {
																	viewMode();
																}
															});
														}
													});	
												}
											});
										} else {
											detail.aggiornaStato(codStato, null, new ServiceCallback<Record>() {
												
												@Override
												public void execute(Record object) {
													reload(new DSCallback() {
														
														@Override
														public void execute(DSResponse response, Object rawData, DSRequest request) {
															viewMode();
														}
													});
												}
											});	
										}
									}		
								}
							});		
							statiAttoPostDiscussioneMenuItems.add(statoAttoPostDiscussioneMenuItem);
						}
						statiAttoPostDiscussioneMenu.setItems(statiAttoPostDiscussioneMenuItems.toArray(new MenuItem[statiAttoPostDiscussioneMenuItems.size()]));
						statiAttoPostDiscussioneMenu.showContextMenu();
					}
				});	
				listaDetailToolStripButtons.add(aggiornaStatoAttoPostDiscussioneButton);
			}
			
			editButton = new DetailToolStripButton(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
			editButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					editMode();
				}
			});
			listaDetailToolStripButtons.add(editButton);
	
			saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
			saveButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					onSaveButtonClick();
				}
			});
			listaDetailToolStripButtons.add(saveButton);
	
			reloadDetailButton = new DetailToolStripButton(I18NUtil.getMessages().reloadDetailButton_prompt(), "buttons/reloadDetail.png");
			reloadDetailButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					reload(new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							editMode();
						}
					});
				}
			});
			listaDetailToolStripButtons.add(reloadDetailButton);
	
			undoButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
			undoButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					reload(new DSCallback() {
	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							viewMode();
						}
					});
				}
			});
			listaDetailToolStripButtons.add(undoButton);			
		}
		
		if(listaDetailToolStripButtons.size() > 0) {
			detailToolStrip = new ToolStrip();
			detailToolStrip.setWidth100();
			detailToolStrip.setHeight(30);
			detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
			detailToolStrip.addFill(); // push all buttons to the right
			for(DetailToolStripButton button : listaDetailToolStripButtons) {
				detailToolStrip.addButton(button);
			}
		}
	}
	
	public void viewMode() {
		this.mode = "view";
		detail.setCanEdit(false);
		detail.viewMode();
		if(aggiornaStatoAttoPostDiscussioneButton != null) {
			aggiornaStatoAttoPostDiscussioneButton.show();
		}
		if(editButton != null) {
			editButton.show();
		}
		if(saveButton != null) {
			saveButton.hide();
		}
		if(reloadDetailButton != null) {
			reloadDetailButton.hide();
		}
		if(undoButton != null) {
			undoButton.hide();
		}		
	}

	public void editMode() {
		this.mode = "edit";
		detail.setCanEdit(true);
		detail.editMode();
		if(aggiornaStatoAttoPostDiscussioneButton != null) {
			aggiornaStatoAttoPostDiscussioneButton.hide();
		}
		if(editButton != null) {
			editButton.hide();
		}		
		if(saveButton != null) {
			saveButton.show();
		}
		if(reloadDetailButton != null) {
			reloadDetailButton.show();
		}
		if(undoButton != null) {
			undoButton.show();
		}
	}

	@Override
	public void manageOnCloseClick() {
		if (getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}
	}
	
}