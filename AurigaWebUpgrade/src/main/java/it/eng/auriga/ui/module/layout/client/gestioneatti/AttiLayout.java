package it.eng.auriga.ui.module.layout.client.gestioneatti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

/**
 * Visualizza la lista degli atti in lavorazione
 * 
 * @author massimo malvestio
 *
 */
public class AttiLayout extends CustomLayout {
	
	public static final String _RILASCIO_VISTO = "RILASCIO_VISTO";
	public static final String _RIFIUTO_VISTO = "RIFIUTO_VISTO";
	
	public static final String _EVENTO_AMC = "EVENTO_AMC";

	protected MultiToolStripButton smistaPropAttoMultiButton;
	protected MultiToolStripButton rilascioVistoMultiButton;
	protected MultiToolStripButton rifiutoVistoMultiButton;	
	protected MultiToolStripButton mandaAlLibroFirmaAttoMultiButton;
	protected MultiToolStripButton togliDalLibroFirmaAttoMultiButton;
	protected MultiToolStripButton segnaDaRicontrollareAttoMultiButton;
	protected MultiToolStripButton zipVistoContabileButton;
	protected MultiToolStripButton eventoAMCMultiButton;
	
	public AttiLayout(String nomeEntita, final GWTRestDataSource pDatasource, ConfigurableFilter pFilter, CustomList pList, CustomDetail pDetail,
			String pFinalita, Boolean pFlgSelezioneSingola, Boolean pShowOnlyDetail) {
		
		super(nomeEntita, pDatasource, pFilter, pList, pDetail, pFinalita, pFlgSelezioneSingola, pShowOnlyDetail);

		if (showMultiselectButtonsUnderList()) {
			setMultiselect(true);
		} else {
			multiselectButton.hide();
		}
		newButton.hide();
	}
	
	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}

	public static boolean isAttivoSmistamentoAtti() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_SMISTAMENTO_ATTI");
	}
	
	public static boolean isAbilOsservazioniNotifiche() {
		return AurigaLayout.isPrivilegioAttivo("ATT/OSS");
	}
	
	public static boolean isAbilMandaAlLibroFirma() {
		return AurigaLayout.isPrivilegioAttivo("ATT/ILF");
	}
	
	public static boolean isAbiltogliDalLibroFirma() {
		return AurigaLayout.isPrivilegioAttivo("ATT/ILF");
	}
	
	public static boolean isAbilSegnaDaRicontrollare() {
		return AurigaLayout.isPrivilegioAttivo("ATT/ISV");
	}
	
	public static boolean isAbilZipVistoContabile() {
		return AurigaLayout.isPrivilegioAttivo("ATT/FVC") || AurigaLayout.isPrivilegioAttivo("ATT/ILF");
	}	
	
	public static boolean isAbilRilascioVisto() {
		return AurigaLayout.isPrivilegioAttivo("ATT/RLV");
	}
	
	public static boolean isAbilRifiutoVisto() {
		return AurigaLayout.isPrivilegioAttivo("ATT/RFV");
	}
	
	public static boolean isAbilEventoAMC() {
		return AurigaLayout.isPrivilegioAttivo("ATT/AMC");
	}
	
	@Override
	public boolean showFunzGestioneSubordinati() {
		return Layout.isPrivilegioAttivo("#RESPONSABILE");
	}
	
	@Override
	public boolean showMultiselectButtonsUnderList() {
		return isAttivoSmistamentoAtti();
	}
	
	@Override
	public boolean showPaginazioneItems() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PAGINAZIONE_ATTI");
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {

		GWTRestDataSource dataSource = (GWTRestDataSource) getList().getDataSource();
		dataSource.setForceToShowPrompt(false);

		return dataSource;
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {

		List<MultiToolStripButton> listaMultiselectButtons = new ArrayList<MultiToolStripButton>();
		
		if (smistaPropAttoMultiButton == null) {
			smistaPropAttoMultiButton = new MultiToolStripButton("archivio/assegna.png", this, "Smista", false) {

				@Override
				public boolean toShow() {
					return isAttivoSmistamentoAtti();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					new SmistamentoAttiPopup((listaRecord.getLength() == 1) ? listaRecord.get(0) : null) {

						@Override
						public void onClickOkButton(final DSCallback callback) {
							Record record = new Record();
							record.setAttribute("listaRecord", listaRecord);
							if(AurigaLayout.isAttivoClienteCOTO()) {
								record.setAttribute("listaUfficioLiquidatore", _form.getValueAsRecordList("listaUfficioLiquidatore"));
							}
							record.setAttribute("listaSmistamento", _form.getValueAsRecordList("listaSmistamento"));
							Layout.showWaitPopup("Smistamento in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("SmistamentoAttiDataSource");
							try {
								lGwtRestDataSource.addData(record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveOperationCallback(response, listaRecord, "idProcedimento", "numeroProposta",
												"Smistamento effettuato con successo",
												"Tutti i record selezionati per lo smistamento sono andati in errore!",
												"Alcuni dei record selezionati per lo smistamento sono andati in errore!", callback);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						}
					};
				}
			};
		}
		listaMultiselectButtons.add(smistaPropAttoMultiButton);	
		
		if (rilascioVistoMultiButton == null) {
			rilascioVistoMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/rilascioVisto.png", this, "Rilascia visto", false) {

				@Override
				public boolean toShow() {
					return isAbilRilascioVisto();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {						
						final Record recordAtto = new Record();
						// questa operazione deve funzionare sia da lista atti completi che in quella vecchia degli atti
						// quindi passo un record con solo le property unitaDocumentariaId e idProcedimento (che si trovano in entrambi i bean)
						recordAtto.setAttribute("unitaDocumentariaId", list.getSelectedRecords()[i].getAttribute("unitaDocumentariaId"));
						recordAtto.setAttribute("idProcedimento", list.getSelectedRecords()[i].getAttribute("idProcedimento"));						
						listaRecord.add(recordAtto);
					}						
					final Record recordToSave = new Record();
					recordToSave.setAttribute("listaRecord", listaRecord);		
					recordToSave.setAttribute("azione", _RILASCIO_VISTO);
					MotivoOsservazioniAzioneSuListaAttiPopup lMotivoOsservazioniAzioneSuListaAttiPopup = new MotivoOsservazioniAzioneSuListaAttiPopup("Osservazioni/note a corredo del visto", recordToSave, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							Layout.showWaitPopup("Rilascio visto in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "AzioneSuListaDocAttiCompletiDataSource" : "AzioneSuListaDocAttiDataSource");
							try {
								lGwtRestDataSource.addData(object, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {					
										massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
												"Rilascio visto effettuato con successo",
												"Tutti i record selezionati per il rilascio visto sono andati in errore!",
												"Alcuni dei record selezionati per il rilascio visto sono andati in errore!", null);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}		
						}
					});
					lMotivoOsservazioniAzioneSuListaAttiPopup.show();					
				}
			};
		}
		listaMultiselectButtons.add(rilascioVistoMultiButton);	
		
		if (rifiutoVistoMultiButton == null) {
			rifiutoVistoMultiButton = new MultiToolStripButton("attiInLavorazione/azioni/rifiutoVisto.png", this, "Rifiuta visto", false) {

				@Override
				public boolean toShow() {
					return isAbilRifiutoVisto();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						final Record recordAtto = new Record();
						// questa operazione deve funzionare sia da lista atti completi che in quella vecchia degli atti
						// quindi passo un record con solo le property unitaDocumentariaId e idProcedimento (che si trovano in entrambi i bean)
						recordAtto.setAttribute("unitaDocumentariaId", list.getSelectedRecords()[i].getAttribute("unitaDocumentariaId"));
						recordAtto.setAttribute("idProcedimento", list.getSelectedRecords()[i].getAttribute("idProcedimento"));												
						listaRecord.add(recordAtto);
					}						
					final Record recordToSave = new Record();
					recordToSave.setAttribute("listaRecord", listaRecord);		
					recordToSave.setAttribute("azione", _RIFIUTO_VISTO);
					MotivoOsservazioniAzioneSuListaAttiPopup lMotivoOsservazioniAzioneSuListaAttiPopup = new MotivoOsservazioniAzioneSuListaAttiPopup("Compilazione motivo rifiuto", true, recordToSave, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							Layout.showWaitPopup("Rifiuto visto in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "AzioneSuListaDocAttiCompletiDataSource" : "AzioneSuListaDocAttiDataSource");
							try {
								lGwtRestDataSource.addData(object, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {					
										massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
												"Rifiuto visto effettuato con successo",
												"Tutti i record selezionati per il rifiuto visto sono andati in errore!",
												"Alcuni dei record selezionati per il rifiuto visto sono andati in errore!", null);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}			
						}
					});
					lMotivoOsservazioniAzioneSuListaAttiPopup.show();					
				}
			};
		}
		listaMultiselectButtons.add(rifiutoVistoMultiButton);
	
		if (mandaAlLibroFirmaAttoMultiButton == null) {
			mandaAlLibroFirmaAttoMultiButton = new MultiToolStripButton("attiInLavorazione/mandaLibroFirma.png", this, "Manda al libro firma", false) {
				
				@Override
				public boolean toShow() {
					return isAbilMandaAlLibroFirma();
				}
				
				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					
					Record record = new Record();
					record.setAttribute("listaRecord", listaRecord);
					Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioniLibroFirmaDataSource");
					try {
						lGwtRestDataSource.executecustom("mandaALibroFirma", record, new DSCallback() {

							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Layout.hideWaitPopup();
									customMassiveOperationCallback(response, listaRecord.getLength());
									doSearch();
								}
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
					
				}	
					
			};
		}
		listaMultiselectButtons.add(mandaAlLibroFirmaAttoMultiButton);	
		
		if (togliDalLibroFirmaAttoMultiButton == null) {
			togliDalLibroFirmaAttoMultiButton = new MultiToolStripButton("attiInLavorazione/togliLibroFirma.png", this, "Togli dal libro firma", false) {
				
				@Override
				public boolean toShow() {
					return isAbiltogliDalLibroFirma();
				}
				
				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					
					Record record = new Record();
					record.setAttribute("listaRecord", listaRecord);
					Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioniLibroFirmaDataSource");
					try {
						lGwtRestDataSource.executecustom("togliDaLibroFirma", record, new DSCallback() {

							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Layout.hideWaitPopup();
									customMassiveOperationCallback(response, listaRecord.getLength());
									doSearch();
								}
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
					
				}
			};
		}
		listaMultiselectButtons.add(togliDalLibroFirmaAttoMultiButton);	
		
		if (segnaDaRicontrollareAttoMultiButton == null) {
			segnaDaRicontrollareAttoMultiButton = new MultiToolStripButton("archivio/flgPresaInCarico/da_fare.png", this, "Segna da ricontrollare", false) {
				
				@Override
				public boolean toShow() {
					return isAbilSegnaDaRicontrollare();
				}
				
				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaRecord.add(list.getSelectedRecords()[i]);
					}
					
					Record record = new Record();
					record.setAttribute("listaRecord", listaRecord);
					Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");		
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioniLibroFirmaDataSource");
					try {
						lGwtRestDataSource.executecustom("segnaDaRicontrollare", record, new DSCallback() {

							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Layout.hideWaitPopup();
									customMassiveOperationCallback(response, listaRecord.getLength());
									doSearch();
								}
							}
						});
					} catch (Exception e) {
						Layout.hideWaitPopup();
					}
					
				}
			};
		}
		listaMultiselectButtons.add(segnaDaRicontrollareAttoMultiButton);	
		
		if (zipVistoContabileButton == null) {
			zipVistoContabileButton = new MultiToolStripButton("file/zip.png", this, "Zip VRC", false) {

				@Override
				public boolean toShow() {
					return isAbilZipVistoContabile();
				}

				@Override
				public void doSomething() {
					final RecordList listaUd = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						listaUd.add(list.getSelectedRecords()[i]);
					}
					if (!listaUd.isEmpty()) {
						downloadZipDocument(listaUd);
					} else {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().alert_archivio_massivo_alldoc_downloadDocsZip(), "", MessageType.WARNING));
					}
				}

				public void downloadZipDocument(final RecordList listaUdFolder) {
					Record record = new Record();
					record.setAttribute("listaRecord", listaUdFolder);

					final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("NuovaPropostaAtto2DataSource");
					lGwtRestDataSource.extraparam.put("messageError", I18NUtil.getMessages().alert_archivio_list_downloadDocsZip());
					lGwtRestDataSource.extraparam.put("limiteMaxZipError", I18NUtil.getMessages().alert_archivio_list_limiteMaxZipError());
					lGwtRestDataSource.executecustom("generaVRCzip", record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record recordVRC = response.getData()[0];
								String message = recordVRC.getAttribute("message");
								if (message != null && !"".equals(message)) {
									Layout.addMessage(new MessageBean(message, "", MessageType.ERROR));
								} else {
									String uri = recordVRC.getAttribute("storageZipRemoteUri");
									String nomeFile = recordVRC.getAttribute("zipName");
									Record infoFileRecord = recordVRC.getAttributeAsRecord("infoFile");
									Record lRecord = new Record();
									lRecord.setAttribute("displayFilename", nomeFile);
									lRecord.setAttribute("uri", uri);
									lRecord.setAttribute("sbustato", "false");
									lRecord.setAttribute("remoteUri", true);
									lRecord.setAttribute("infoFile", infoFileRecord);
									DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
								}
							}

						}
					});
				}
			};
		}
		listaMultiselectButtons.add(zipVistoContabileButton);
					
		if (eventoAMCMultiButton == null) {
			
			String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
			eventoAMCMultiButton = new MultiToolStripButton("buttons/gear.png", this, lSistAMC != null && !"".equals(lSistAMC) ? "Evento " + lSistAMC : "Evento AMC", false) {

				@Override
				public boolean toShow() {
					String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
					return lSistAMC != null && !"".equals(lSistAMC) && isAbilEventoAMC();
				}

				@Override
				public void doSomething() {
					final RecordList listaRecord = new RecordList();
					for (int i = 0; i < list.getSelectedRecords().length; i++) {
						final Record recordAtto = new Record();
						// questa operazione deve funzionare sia da lista atti completi che in quella vecchia degli atti
						// quindi passo un record con solo la property unitaDocumentariaId (che si trova in entrambi i bean)
						recordAtto.setAttribute("unitaDocumentariaId", list.getSelectedRecords()[i].getAttribute("unitaDocumentariaId"));
						recordAtto.setAttribute("flgRilevanzaContabile", "1"); // lo setto sempre a 1 altrimenti poi non mi esegue l'evento (tanto poi carica il dettaglio e riverifica se ha rilevanza contabile oppure no)															
						listaRecord.add(recordAtto);
					}
					final Record recordToSave = new Record();
					recordToSave.setAttribute("listaRecord", listaRecord);
					recordToSave.setAttribute("azione", _EVENTO_AMC);	
					final String lSistAMC = AurigaLayout.getParametroDB("SIST_AMC");
					EventoAMCPopup lEventoAMCPopup = new EventoAMCPopup("Evento " + lSistAMC, recordToSave, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							Layout.showWaitPopup("Evento " + lSistAMC + " in corso: potrebbe richiedere qualche secondo. Attendere…");
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "AzioneSuListaDocAttiCompletiDataSource" : "AzioneSuListaDocAttiDataSource");
							try {
								lGwtRestDataSource.addData(object, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {					
										massiveOperationCallback(response, listaRecord, "unitaDocumentariaId", "numeroProposta",
												"Evento " + lSistAMC + " effettuato con successo",
												"Tutti i record selezionati per l'evento " + lSistAMC + " sono andati in errore!",
												"Alcuni dei record selezionati per l'evento " + lSistAMC + " sono andati in errore!", null);
									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}			
						}
					});
					lEventoAMCPopup.show();					
				}
			};
		}
		listaMultiselectButtons.add(eventoAMCMultiButton);
		
		return listaMultiselectButtons.toArray(new MultiToolStripButton[listaMultiselectButtons.size()]);
	}

	public void massiveOperationCallback(DSResponse response, RecordList lista, String pkField, String nameField, String successMessage,
			String completeErrorMessage, String partialErrorMessage, DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			int[] recordsToSelect = null;
			RecordList listaErrori = new RecordList();
			if (errorMessages != null && errorMessages.size() > 0) {
				recordsToSelect = new int[errorMessages.size()];
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				int rec = 0;
				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						Record recordErrore = new Record();
						recordsToSelect[rec++] = list.getRecordIndex(record);
						errorMsg += "<br/>" + record.getAttribute(nameField) + ": " + errorMessages.get(record.getAttribute(pkField));
						recordErrore.setAttribute("idError", record.getAttribute(nameField));
						recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
						listaErrori.add(recordErrore);
					}
				}
			}
			doSearchAndSelectRecords(recordsToSelect);
			Layout.hideWaitPopup();
			if(listaErrori != null && listaErrori.getLength() > 0) {
				ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Estremi", listaErrori, lista.getLength(), 600, 300);
				errorePopup.show();
			} else if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		} else {
			Layout.hideWaitPopup();
		}
	}
	
	public void customMassiveOperationCallback(DSResponse dsResponse, int recorSelezionati) {
		Record data = dsResponse.getData()[0];
		Map errorMessages = data.getAttributeAsMap("errorMessages");

		RecordList listaErrori = new RecordList();
		List<String> listKey = new ArrayList<String>(errorMessages.keySet());

		for(String keyRecordError : listKey) {
			Record recordErrore = new Record();
			recordErrore.setAttribute("idError", keyRecordError);
			recordErrore.setAttribute("descrizione",errorMessages.get(keyRecordError));
			listaErrori.add(recordErrore);
		}

		if (listaErrori != null && listaErrori.getLength() > 0) {
			ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Atto",
					listaErrori, recorSelezionati, 600, 300);
			errorePopup.show();
		} else { 
			Layout.addMessage(new MessageBean("Operazione effettuato con successo", "", MessageType.INFO));
		}
	}
	
}